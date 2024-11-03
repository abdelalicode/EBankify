package com.youbanking.ebankify.authentication;

import com.youbanking.ebankify.response.ResponseHandler;
import com.youbanking.ebankify.user.*;
import com.youbanking.ebankify.utils.EntityDtoMapper;
import com.youbanking.ebankify.utils.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;
    private final EntityDtoMapper mapper;
    private final TokenUtil tokenUtil;



    public AuthController(UserService userService, EntityDtoMapper mapper, TokenUtil tokenUtil) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = mapper.mapToEntity(userDTO, User.class);
        User createdUser = userService.createUser(user);
        UserReturnDTO createdUserDTO = mapper.mapToDto(createdUser, UserReturnDTO.class);
        return ResponseHandler.responseBuilder("Registered successfully", HttpStatus.CREATED, createdUserDTO);

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.validateUser(loginRequest.email(), loginRequest.password());
        if (user.isPresent()) {
            UserReturnDTO userReturnDTO = mapper.mapToDto(user.get(), UserReturnDTO.class);
            String token = tokenUtil.generateToken(user.get().getId());
            LoginResponse loginResponse = new LoginResponse(token, userReturnDTO);
            return ResponseHandler.responseBuilder("Login successful", HttpStatus.OK, loginResponse);
        }
        return ResponseHandler.errorBuilder("Invalid email or password", HttpStatus.UNAUTHORIZED, "403");
    }


}
