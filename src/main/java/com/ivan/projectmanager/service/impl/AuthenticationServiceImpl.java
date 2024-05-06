package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.LoginRequest;
import com.ivan.projectmanager.dto.RegistrationRequest;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.AuthenticationService;
import com.ivan.projectmanager.service.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public String authenticate(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            return jwtTokenService.generateJwt(authentication);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public void registerUser(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        if (userRepository.getByUsernameCriteria(username) != null) {
            throw new RuntimeException("User with username " + username + " already exists.");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepository.save(user);
    }
}
