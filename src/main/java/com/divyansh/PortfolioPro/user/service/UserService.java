package com.divyansh.PortfolioPro.user.service;

import com.divyansh.PortfolioPro.exception.EmailAlreadyExistException;
import com.divyansh.PortfolioPro.security.JwtService;
import com.divyansh.PortfolioPro.user.dto.LoginRequest;
import com.divyansh.PortfolioPro.user.dto.LoginResponse;
import com.divyansh.PortfolioPro.user.dto.RegisterRequest;
import com.divyansh.PortfolioPro.user.dto.UserResponse;
import com.divyansh.PortfolioPro.user.entity.User;
import com.divyansh.PortfolioPro.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse registerUser(RegisterRequest request)  {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException("Email already registered");
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

    public LoginResponse loginUser(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                )
        );

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!passwordMatches) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token, "bearer");
    }
}
