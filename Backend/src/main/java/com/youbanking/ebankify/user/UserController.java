package com.youbanking.ebankify.user;

import com.youbanking.ebankify.authentication.LoginRequest;
import com.youbanking.ebankify.authentication.LoginResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EntityDtoMapper mapper;
    private final TokenUtil tokenUtil;
    private final PermissionService permissionService;


    public UserController(UserService userService, EntityDtoMapper mapper, TokenUtil tokenUtil, PermissionService permissionService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenUtil = tokenUtil;
        this.permissionService = permissionService;
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable long id, HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long authId = (Long) request.getAttribute("userId");


        if( authId == id || permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
            User user = mapper.mapToEntity(userDTO, User.class);

            User updatedUser = userService.updateUser(user, id);
            UserReturnDTO createdUserDTO = mapper.mapToDto(updatedUser, UserReturnDTO.class);
            return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.CREATED, createdUserDTO);
        }
        else {
            throw new UnAuthorizedException("You are not allowed");
        }



    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long authId = (Long) request.getAttribute("userId");

        if(permissionService.hasPermission(authId, PermissionType.FULL_ACCESS) || permissionService.hasPermission(authId, PermissionType.VIEW_ALL_ACCOUNTS)) {

            Optional<List<User>> allUsersOptional = userService.findAllUsers();
            if(allUsersOptional.isEmpty()) {
                return ResponseHandler.responseBuilder("All Users List", HttpStatus.OK, "No users found");
            }
            else  {
                List<User> allUsers = allUsersOptional.get();
                List<UserReturnDTO> userDTOs = mapper.mapToEntityList(allUsers, UserReturnDTO.class);
                return ResponseHandler.responseBuilder("All Users List", HttpStatus.OK, userDTOs);
            }
        }
        else {
            throw new UnAuthorizedException("You are not allowed");
        }
    }


}