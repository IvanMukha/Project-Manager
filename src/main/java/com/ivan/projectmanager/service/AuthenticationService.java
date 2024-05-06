package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.LoginRequest;
import com.ivan.projectmanager.dto.RegistrationRequest;

public interface AuthenticationService {
    String authenticate(LoginRequest loginRequest);

    void registerUser(RegistrationRequest registrationRequest);
}
