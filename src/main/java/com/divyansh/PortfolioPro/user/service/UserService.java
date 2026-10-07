package com.divyansh.PortfolioPro.user.service;

import com.divyansh.PortfolioPro.user.dto.RegisterRequest;
import com.divyansh.PortfolioPro.user.dto.UserResponse;
import com.divyansh.PortfolioPro.user.entity.User;
import com.divyansh.PortfolioPro.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(RegisterRequest request)  {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setWalletBalance(new BigDecimal("100000.00"));
        User savedUser =  userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getWalletBalance(),
                savedUser.getCreatedAt()
        );
    }

//    private UserResponse mapToResponse(User user) {
//
//        UserResponse response = new UserResponse();
//
//        response.setId(user.getId());
//        response.setName(user.getName());
//        response.setEmail(user.getEmail());
//        response.setRole(user.getRole());
//        response.setWalletBalance(user.getWalletBalance());
//        response.setCreatedAt(user.getCreatedAt());
//
//        return response;
//    }
}
