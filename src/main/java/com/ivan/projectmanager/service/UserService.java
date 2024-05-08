package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends CrudService<UserDTO>, UserDetailsService {
    UserDetailsService userDetailsService();

    List<User> getByUsername(String username);
}
