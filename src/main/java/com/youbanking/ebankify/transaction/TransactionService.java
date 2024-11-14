package com.youbanking.ebankify.transaction;

import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.bank.bankAccount.BankAccountRepository;
import com.youbanking.ebankify.exception.NotFoundException;
import com.youbanking.ebankify.exception.UnAuthorizedException;
import com.youbanking.ebankify.exception.UnsufficientFundException;
import com.youbanking.ebankify.role.RoleType;
import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final ScheduledExecutorService scheduler;

    public TransactionService(
            TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository,
            UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    @Cacheable("transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    public List<Transaction> getAllTransactionsByUser(Long userId) {

        List<Transaction> transactions = transactionRepository.findByUser(userId);

        if (transactions.isEmpty()) {
            throw new NotFoundException("No transactions found");
        }


        return transactions;
    }


    Transaction createTransaction(Transaction transaction, Long userId) {
        User authClient  = userRepository.findById(userId).orElse(null);

        BankAccount senderBkaccount = bankAccountRepository.findByClient(authClient)
                .orElseThrow( () -> new NotFoundException("No bank account Found"));


        BankAccount receiverBkaccount = bankAccountRepository.findByAccountNumber(transaction.getReceiverAccount().getAccountNumber())
                .orElseThrow( () -> new NotFoundException("No bank account Found"));

        if(senderBkaccount.getAccountNumber().equals(receiverBkaccount.getAccountNumber())) {
            throw new UnAuthorizedException("Can't send to your account");
        }

        transaction.setReceiverAccount(receiverBkaccount);
        transaction.setSenderAccount(senderBkaccount);

        BigDecimal amount = transaction.getAmount();
        System.out.println(senderBkaccount);
        BigDecimal userBalance = senderBkaccount.getBalance();

        if(userBalance.compareTo(amount) < 0) {
            throw new UnAuthorizedException("Not Enough Fund To Make Transaction");
        }

        if(!Objects.equals(receiverBkaccount.getBank().getBIC(), senderBkaccount.getBank().getBIC())) {
            if(transaction.getTransactionType().equals(TransactionType.CLASSIC)) {
                transaction.setTransactionFee(10.0);
                transaction.setExpectedTime(transaction.getTransactionDate().plusHours(24));
                scheduler.schedule(() -> makeTransaction(transaction), 5, TimeUnit.MINUTES);
            }
            else if(transaction.getTransactionType().equals(TransactionType.INSTANT)) {
                transaction.setTransactionFee(25.0);
                transaction.setExpectedTime(LocalDateTime.now());
                makeTransaction(transaction);
            }

        }
        else {
            transaction.setTransactionFee(0.0);
            transaction.setExpectedTime(LocalDateTime.now());
            makeTransaction(transaction);
        }

        return transaction;
    }


    Transaction makeTransaction(Transaction transaction) {
        BankAccount receiverBA = transaction.getReceiverAccount();
        BankAccount senderBA = transaction.getSenderAccount();

        BigDecimal totalFees = transaction.getAmount().add(BigDecimal.valueOf(transaction.getTransactionFee()));

        if(transaction.getAmount().compareTo(transaction.getSenderAccount().getBank().getBasicLimit()) < 0) {
            receiverBA.setBalance(receiverBA.getBalance().add(transaction.getAmount()));
            senderBA.setBalance(senderBA.getBalance().subtract(totalFees));
            bankAccountRepository.save(receiverBA);
            bankAccountRepository.save(senderBA);
            transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        }


        return transactionRepository.save(transaction);
    }

    Transaction approveTransaction(Transaction transaction, Long userId) {
        User authEmployee  = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("No User Found"));

        Transaction transactionUnapproved = transactionRepository.findById(transaction.getId()).orElseThrow(() -> new NotFoundException("No Transaction Found"));

        if(!transactionUnapproved.getTransactionStatus().equals(TransactionStatus.PENDING)) {
            throw new NotFoundException("The Transaction already completed.");
        }


        if(transactionUnapproved.getAmount().compareTo(transactionUnapproved.getSenderAccount().getBalance()) > 0) {
            throw new UnsufficientFundException("Not enough fund to make the transaction");
        }

        BankAccount receiverBA = transactionUnapproved.getReceiverAccount();
        BankAccount senderBA = transactionUnapproved.getSenderAccount();

        BigDecimal totalFees = transactionUnapproved.getAmount().add(BigDecimal.valueOf(transactionUnapproved.getTransactionFee()));

            receiverBA.setBalance(receiverBA.getBalance().add(transactionUnapproved.getAmount()));
            senderBA.setBalance(senderBA.getBalance().subtract(totalFees));
            bankAccountRepository.save(receiverBA);
            bankAccountRepository.save(senderBA);
            transactionUnapproved.setTransactionStatus(transaction.getTransactionStatus());
            transactionUnapproved.setApprovedBy(authEmployee);

            return transactionRepository.save(transactionUnapproved);

    }


}
