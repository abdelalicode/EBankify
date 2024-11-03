package com.youbanking.ebankify.user;

import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.role.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class UserReturnDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String roleName;
    private List<BankAccount> bankAccountList;
}
