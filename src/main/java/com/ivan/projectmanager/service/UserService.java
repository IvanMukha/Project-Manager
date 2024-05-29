package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Page<UserDTO> getAll(Integer page, Integer size);

    UserDTO save(UserDTO userDTO);

    Optional<UserDTO> getById(Long id);

    Optional<UserDTO> update(Long id, UserDTO updatedUserDTO);

    void delete(Long id);

    UserDetails loadUserByUsername(String username);

    UserDetailsService userDetailsService();

    List<User> getByUsername(String username);

    Optional<UserDTO> assignRoleToUser(Long userId, RoleDTO roleDTO);

    Optional<UserDTO> removeRoleFromUser(Long userId, RoleDTO roleDTO);
}
