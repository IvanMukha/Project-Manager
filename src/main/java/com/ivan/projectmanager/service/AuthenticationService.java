package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDTO;

public interface AuthenticationService {
    String authenticate(UserDTO userDTO);

    void registerUser(UserDTO userDTO);
}
