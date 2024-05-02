package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDetailsDTO;

import java.util.Optional;

public interface UserDetailsService {

    UserDetailsDTO save(Long userId, UserDetailsDTO userDetailsDTO);

    Optional<UserDetailsDTO> getById(Long userId);

    Optional<UserDetailsDTO> update(Long userId, UserDetailsDTO updatedUserDetailsDTO);

    void delete(Long userId);

}
