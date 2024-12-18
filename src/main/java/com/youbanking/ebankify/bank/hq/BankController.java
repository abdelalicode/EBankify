package com.youbanking.ebankify.bank.HQ;



import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankRepository bankRepo;

    public BankController(BankRepository bankRepo) {
        this.bankRepo = bankRepo;
    }


    @GetMapping
    List<Bank> getAllBanks() {
        return bankRepo.findAll();
    }

    @GetMapping("/{id}")
    Bank findById(@PathVariable Long id) {
        Optional<Bank> bank = bankRepo.findById(id);
        if (bank.isEmpty()) {
            throw new BankNotFoundException();
        }
        return bank.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Bank createBank(@Valid @RequestBody Bank bank) {
        return bankRepo.save(bank);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    Bank updateBank(@RequestBody Bank bank, @PathVariable Long id) {
        return bankRepo.save(bank);
    }


}
