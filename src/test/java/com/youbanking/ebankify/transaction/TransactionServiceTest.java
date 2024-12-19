package com.youbanking.ebankify.transaction;

import com.youbanking.ebankify.bank.HQ.Bank;
import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.bank.bankAccount.BankAccountRepository;
import com.youbanking.ebankify.exception.NotFoundException;
import com.youbanking.ebankify.exception.UnAuthorizedException;
import com.youbanking.ebankify.exception.UnsufficientFundException;
import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User testUser;
    private BankAccount senderAccount;
    private BankAccount receiverAccount;
    private Transaction testTransaction;
    private Bank senderBank;
    private Bank receiverBank;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(1L);

        senderBank = new Bank();
        senderBank.setBIC("SENDER123");
        senderBank.setBasicLimit(new BigDecimal("10000"));

        receiverBank = new Bank();
        receiverBank.setBIC("RECEIVER456");
        receiverBank.setBasicLimit(new BigDecimal("10000"));

        senderAccount = new BankAccount();
        senderAccount.setAccountNumber("SA123456");
        senderAccount.setBalance(new BigDecimal("1000"));
        senderAccount.setBank(senderBank);

        receiverAccount = new BankAccount();
        receiverAccount.setAccountNumber("RA789012");
        receiverAccount.setBalance(new BigDecimal("500"));
        receiverAccount.setBank(receiverBank);

        testTransaction = new Transaction();
        testTransaction.setId(transactionId);
        testTransaction.setAmount(new BigDecimal("100"));
        testTransaction.setTransactionType(TransactionType.CLASSIC);
        testTransaction.setTransactionDate(LocalDateTime.now());
        testTransaction.setReceiverAccount(receiverAccount);
    }

    @Test
    void createTransaction_InsufficientFunds() {
        senderAccount.setBalance(new BigDecimal("50"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(bankAccountRepository.findByClient(testUser)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByAccountNumber("RA789012")).thenReturn(Optional.of(receiverAccount));

        assertThrows(UnAuthorizedException.class, () ->
                transactionService.createTransaction(testTransaction, 1L)
        );
    }


    @Test
    void approveTransaction_AlreadyCompleted() {
        // Arrange
        testTransaction.setTransactionStatus(TransactionStatus.COMPLETED);

        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(testTransaction));

        assertThrows(NotFoundException.class, () ->
                transactionService.approveTransaction(testTransaction, 2L)
        );
    }

    @Test
    void approveTransaction_InsufficientFunds() {
        testTransaction.setTransactionStatus(TransactionStatus.PENDING);
        testTransaction.setSenderAccount(senderAccount);
        testTransaction.setAmount(new BigDecimal("2000"));

        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(testTransaction));

        assertThrows(UnsufficientFundException.class, () ->
                transactionService.approveTransaction(testTransaction, 2L)
        );
    }
}