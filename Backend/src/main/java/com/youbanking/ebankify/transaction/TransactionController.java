package com.youbanking.ebankify.transaction;

import com.youbanking.ebankify.bank.bankAccount.*;
import com.youbanking.ebankify.common.BaseController;
import com.youbanking.ebankify.exception.NotFoundException;
import com.youbanking.ebankify.exception.UnAuthorizedException;
import com.youbanking.ebankify.permission.PermissionService;
import com.youbanking.ebankify.permission.PermissionType;
import com.youbanking.ebankify.response.ResponseHandler;
import com.youbanking.ebankify.utils.EntityDtoMapper;
import com.youbanking.ebankify.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/transactions")
public class TransactionController extends BaseController {

        private final PermissionService permissionService;
        private final TransactionService transactionService;
        private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionController(PermissionService permissionService, TransactionService transactionService, TransactionMapper transactionMapper, TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
            this.permissionService = permissionService;
            this.transactionService = transactionService;
            this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

        @GetMapping
        public ResponseEntity<Object> getAllBankAccounts(HttpServletRequest request) {
            Long userId = getUserId(request);

            if(permissionService.hasPermission(userId, PermissionType.VIEW_ALL_ACCOUNTS) || permissionService.hasPermission(userId, PermissionType.FULL_ACCESS)) {
                List<Transaction> transactions = transactionService.getAllTransactions();

                List<TransactionResponseDTO> allTransactionsDTO = transactionMapper.toDTOList(transactions);
                return ResponseHandler.responseBuilder("Retrieved Successfully", HttpStatus.OK , allTransactionsDTO);
            }
            else if(permissionService.hasPermission(userId, PermissionType.MANAGE_OWN_ACCOUNT)) {
                List<Transaction> transactions = transactionService.getAllTransactionsByUser(userId);
                List<TransactionResponseDTO> allTransactionsDTO = transactionMapper.toDTOList(transactions);
                return ResponseHandler.responseBuilder("All Your Transactions", HttpStatus.OK , allTransactionsDTO);
            }
            else {
                throw new UnAuthorizedException("You are not allowed to perform this operation.");
            }
        }


        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping
        ResponseEntity<Object> makeTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO,HttpServletRequest request) {
            Long userId = getUserId(request);

            if(permissionService.hasPermission(userId, PermissionType.MANAGE_OWN_ACCOUNT)) {
                Transaction transaction = transactionMapper.toTransaction(transactionRequestDTO);

                Transaction transactionCreated = transactionService.createTransaction(transaction, userId);
                TransactionResponseDTO transactionResponseDTO = transactionMapper.toDTO(transactionCreated);
                if(transactionCreated.getTransactionStatus().equals(TransactionStatus.COMPLETED)) {
                    return ResponseHandler.responseBuilder("Transaction sent successfully", HttpStatus.CREATED, transactionResponseDTO);
                }
                else {
                    return ResponseHandler.responseBuilder("Waiting for Approval", HttpStatus.CREATED, transactionResponseDTO);
                }
            }
            else {
                throw new UnAuthorizedException("You are not allowed to perform this operation.");
            }
        }

        @PutMapping("/approve")
        ResponseEntity<Object> approveTransaction(@RequestBody TransApproveRequestDTO trApproveRequestDTO, HttpServletRequest request) {
            Long userId = getUserId(request);
            if(permissionService.hasPermission(userId, PermissionType.APPROVE_TRANSACTIONS)) {

                Transaction transaction = transactionMapper.toTransaction(trApproveRequestDTO);

                Transaction transactionApproved = transactionService.approveTransaction(transaction, userId);
                TransactionResponseDTO transactionResponseDTO = transactionMapper.toDTO(transactionApproved);
                return ResponseHandler.responseBuilder("Transaction updated successfully", HttpStatus.OK, transactionResponseDTO);
            }
            else {
                throw new UnAuthorizedException("You are not allowed to perform this operation.");
            }
        }
//
//
//        @GetMapping("/{id}")
//        BankAccountDTO findById(@PathVariable Long id, HttpServletRequest request) {
//
//            Long authId = (Long) request.getAttribute("userId");
//
//            if(permissionService.hasPermission(authId, PermissionType.VIEW_ALL_ACCOUNTS) || permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
//                Optional<BankAccount> account = bankAccountService.getBankAccount(id);
//
//                if (account.isEmpty()) {
//                    throw new NotFoundException("Account Not Found");
//                }
//                return account.map(BankAccountMapper::toDTO).get();
//            }
//            else {
//                Optional<BankAccount> account = bankAccountService.getBankAccount(id);
//                if(account.isPresent() && Objects.equals(account.get().getClient().getId(), authId)) {
//                    return account.map(BankAccountMapper::toDTO).get();
//                }
//
//
//                throw new UnAuthorizedException("You are not allowed to perform this operation.");
//            }
//
//
//        }
//
//
//
//        @ResponseStatus(HttpStatus.OK)
//        @PutMapping("/{id}")
//        ResponseEntity<Object> updateBankAccount(@RequestBody BankAccountDTO bankAccount, @PathVariable Long id, HttpServletRequest request) {
//            String token = (String) request.getAttribute("token");
//            Long authId = (Long) request.getAttribute("userId");
//
//            Optional<BankAccount> account = bankAccountService.getBankAccount(id);
//
//            try {
//                BankAccountTypes accountType = BankAccountTypes.valueOf(bankAccount.bankAccountType());
//            } catch (IllegalArgumentException e) {
//                throw new IllegalArgumentException("Invalid bank account type provided.");
//            }
//
//            BankAccount bkaccount = BankAccountMapper.toEntity(bankAccount);
//
//            if(permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
//                if(account.isPresent()) {
//                    BankAccount baupdated = bankAccountService.update(bkaccount, id);
//                    BankAccountResponseDTO baDTO = BankAccountMapper.toResponseDTO(baupdated);
//
//                    return ResponseHandler.responseBuilder("Account Updated successfully", HttpStatus.OK , baDTO);
//                }
//                else {
//                    throw new NotFoundException("Account Not Found");
//                }
//
//
//            }
//            else {
//                throw new UnAuthorizedException("You are not allowed to perform this operation.");
//            }
//
//        }



}


