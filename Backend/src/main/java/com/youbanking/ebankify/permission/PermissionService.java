package com.youbanking.ebankify.permission;

import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {

    private UserRepository userRepository;

    public PermissionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean hasPermission(Long userId, PermissionType permission) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null || user.getRole() == null){
            return false;
        }

        return user.getRole().getPermissions().stream()
                .anyMatch(p -> p.getName().equals(permission));

    }
}
