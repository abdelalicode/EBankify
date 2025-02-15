package com.youbanking.ebankify.user;

import com.youbanking.ebankify.bank.HQ.BankController;
import com.youbanking.ebankify.exception.NotFoundException;
import com.youbanking.ebankify.role.Role;
import com.youbanking.ebankify.role.RoleRepository;
import com.youbanking.ebankify.role.RoleType;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User createUser(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(null);

        Role defaultRole = roleRepository.findByname(RoleType.CLIENT);
        user.setRole(defaultRole);


        return userRepository.save(user);

    }

    public Optional<User> validateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public User updateUser(User user, Long userId) {

        Optional<User> existingUser = userRepository.findById(userId);

        if(existingUser.isEmpty()) {
            throw new NotFoundException("User not found.");
        }

        existingUser.get().setFirstName(user.getFirstName());
        existingUser.get().setLastName(user.getLastName());
        existingUser.get().setEmail(user.getEmail());
        existingUser.get().setAge(user.getAge());
        return userRepository.save(existingUser.get());

    }

    public Optional<List<User>> findAllUsers() {
        return Optional.of(userRepository.findAll());
    }


}
