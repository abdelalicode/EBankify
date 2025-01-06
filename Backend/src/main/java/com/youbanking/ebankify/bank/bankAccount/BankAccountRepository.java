package com.youbanking.ebankify.bank.bankAccount;

import com.youbanking.ebankify.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT b FROM BankAccount b WHERE b.client.id = :userId AND b.bankAccountType = :accountType")
    Optional<BankAccount> findTypeAccountByUserId(@Param("userId") Long userId, @Param("accountType") BankAccountTypes accountType);
    Optional <BankAccount> findByClient(User client);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
