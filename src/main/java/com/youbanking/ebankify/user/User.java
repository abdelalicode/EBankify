package com.youbanking.ebankify.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.role.Role;
import com.youbanking.ebankify.role.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "client")
    private List<BankAccount> bankAccounts;



}
