package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.AuthenticationResponse;
import com.ivan.projectmanager.dto.LoginRequest;
import com.ivan.projectmanager.dto.RegistrationRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(LoginRequest loginRequest);

    AuthenticationResponse registerUser(RegistrationRequest registrationRequest);
}
