package com.youbanking.ebankify.bank.bankAccount;



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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final PermissionService permissionService;
    private final TokenUtil tokenUtil;
    private final EntityDtoMapper mapper;

    public BankAccountController(BankAccountService bankAccountService, PermissionService permissionService, TokenUtil tokenUtil, EntityDtoMapper mapper) {
        this.bankAccountService = bankAccountService;
        this.permissionService = permissionService;
        this.tokenUtil = tokenUtil;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('full:access')")
    public ResponseEntity<Object> getAllBankAccounts(HttpServletRequest request) {

            List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
            return ResponseHandler.responseBuilder("Retrieved Successfully", HttpStatus.OK , bankAccounts);

    }


//    @PostMapping("/example")
//    public String handlePostRequest(@RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//
//        System.out.println("Authorization Token: " + token);
//
//        Long UserId = tokenUtil.getUserFromToken(token);
//
//        System.out.println(UserId);
//
//        return "Token received";
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<Object> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccount, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        Long userId = tokenUtil.getUserFromToken(token);

        if(permissionService.hasPermission(userId, PermissionType.MANAGE_OWN_ACCOUNT)) {
            BankAccountResponseDTO accountCreated = bankAccountService.createAccount(bankAccount, userId);
            return ResponseHandler.responseBuilder("Account Created successfully", HttpStatus.CREATED, accountCreated);
        }
        else {
            throw new UnAuthorizedException("You are not allowed to perform this operation.");
        }
    }


    @GetMapping("/{id}")
    BankAccountDTO findById(@PathVariable Long id, HttpServletRequest request) {

        Long authId = (Long) request.getAttribute("userId");

        if(permissionService.hasPermission(authId, PermissionType.VIEW_ALL_ACCOUNTS) || permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
            Optional<BankAccount> account = bankAccountService.getBankAccount(id);

            if (account.isEmpty()) {
                throw new NotFoundException("Account Not Found");
            }
            return account.map(BankAccountMapper::toDTO).get();
        }
        else {
            Optional<BankAccount> account = bankAccountService.getBankAccount(id);
                if(account.isPresent() && Objects.equals(account.get().getClient().getId(), authId)) {
                    return account.map(BankAccountMapper::toDTO).get();
                }


            throw new UnAuthorizedException("You are not allowed to perform this operation.");
        }


    }



    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    ResponseEntity<Object> updateBankAccount(@RequestBody BankAccountDTO bankAccount, @PathVariable Long id, HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long authId = (Long) request.getAttribute("userId");

        Optional<BankAccount> account = bankAccountService.getBankAccount(id);

        try {
            BankAccountTypes accountType = BankAccountTypes.valueOf(bankAccount.bankAccountType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid bank account type provided.");
        }

        BankAccount bkaccount = BankAccountMapper.toEntity(bankAccount);

        if(permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
            if(account.isPresent()) {
                BankAccount baupdated = bankAccountService.update(bkaccount, id);
                BankAccountResponseDTO baDTO = BankAccountMapper.toResponseDTO(baupdated);

                return ResponseHandler.responseBuilder("Account Updated successfully", HttpStatus.OK , baDTO);
            }
            else {
                throw new NotFoundException("Account Not Found");
            }


        }
        else {
            throw new UnAuthorizedException("You are not allowed to perform this operation.");
        }

    }


}
