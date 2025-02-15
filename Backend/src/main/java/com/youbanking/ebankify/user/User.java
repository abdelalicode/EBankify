package com.youbanking.ebankify.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.youbanking.ebankify.asset.Asset;
import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.role.Role;
import com.youbanking.ebankify.role.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;


    @OneToMany(mappedBy = "client")
    private List<BankAccount> bankAccounts;


    @OneToMany(mappedBy= "owner")
    private List<Asset> assets;

    @CreationTimestamp
    private LocalDateTime memberSince;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
