package com.travelapp.serviceImpl;

import com.travelapp.entity.User;
import com.travelapp.exception.NotFoundException;
import com.travelapp.exception.ValidationException;
import com.travelapp.mapper.UserMapper;
import com.travelapp.record.user.AuthenticationResponse;
import com.travelapp.record.user.UserLoginRecord;
import com.travelapp.record.user.UserSignUpRecord;
import com.travelapp.repository.UserRepository;
import com.travelapp.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public AuthenticationResponse register(UserSignUpRecord request) {
        Optional<User> existingUser = userRepository.findByUsername(request.username());
        if (existingUser.isPresent()) {
            throw new ValidationException("Username is already taken");
        }
        existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            throw new ValidationException("Email is already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
//        emailService.sendEmail(new EmailData(user.getEmail(), "Registration Successful", "Welcome to gift planner!"));

        return new AuthenticationResponse(token, userMapper.userToUserResponseRecord(user));
    }

    public AuthenticationResponse authenticate(UserLoginRecord request) {
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new NotFoundException("Username not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token, userMapper.userToUserResponseRecord(user));
    }

}