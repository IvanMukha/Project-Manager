package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CrudService<UserDTO>, UserDetailsService {
}
