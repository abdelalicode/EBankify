package com.youbanking.ebankify.bank.bankAccount;



import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
        return new ResponseEntity<>(bankAccounts, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    BankAccountDTO findById(@PathVariable Long id) {
        Optional<BankAccountDTO> account = bankAccountService.getBankAccount(id);
        if (account.isEmpty()) {
            throw new BankAccountNotFoundException();
        }
        return account.get();

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<BankAccountDTO> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccount) {
        BankAccountDTO accountCreated = bankAccountService.createAccount(bankAccount);
        return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
    }

//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/{id}")
//    BankAccountDTO updateBankAccount(@RequestBody BankAccountDTO bankAccount, @PathVariable Long id) {
//        return bankAccountService.update(bankAccount);
//    }


}
