package com.youbanking.ebankify.transaction;

import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.bank.bankAccount.BankAccountTypes;
import com.youbanking.ebankify.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.senderAccount.client.id = :userId OR t.receiverAccount.client.id = :userId")
    List<Transaction>findByUser(@Param("userId") Long userId);

    Optional<Transaction> findById(UUID id);

}
