package com.divyansh.PortfolioPro.user.controller;

import com.divyansh.PortfolioPro.user.dto.LoginRequest;
import com.divyansh.PortfolioPro.user.dto.LoginResponse;
import com.divyansh.PortfolioPro.user.dto.RegisterRequest;
import com.divyansh.PortfolioPro.user.dto.UserResponse;
import com.divyansh.PortfolioPro.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        UserResponse response = userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = userService.loginUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
