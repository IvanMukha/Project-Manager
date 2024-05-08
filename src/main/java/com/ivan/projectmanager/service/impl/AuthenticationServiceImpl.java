package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.AuthenticationResponse;
import com.ivan.projectmanager.dto.LoginRequest;
import com.ivan.projectmanager.dto.RegistrationRequest;
import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.AuthenticationService;
import com.ivan.projectmanager.service.JwtTokenService;
import com.ivan.projectmanager.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()));
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            String jwt = jwtTokenService.generateToken(userDetails);
            return new AuthenticationResponse(jwt);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }

    @Transactional
    public AuthenticationResponse registerUser(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        if (!userService.getByUsername(username).isEmpty()) {
            throw new RuntimeException("User with username " + username + " already exists.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setRoles(userService.getDefaultRole());

        userRepository.save(user);
        Role role = userService.getDefaultRole().stream().findFirst().get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
        String jwt = jwtTokenService.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }
}
