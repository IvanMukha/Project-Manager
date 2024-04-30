package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.UserDetailsRepository;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ModelMapper modelMapper;
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(ModelMapper modelMapper, UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetailsDTO save(Long userId, UserDetailsDTO userDetailsDTO) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new CustomNotFoundException(userId, User.class));
        userDetailsDTO.setUserId(userId);
        return mapUserDetailsToDTO(userDetailsRepository.save(mapDTOToUserDetails(userDetailsDTO)));
    }

    public Optional<UserDetailsDTO> getById(Long userId) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.getById(userId);
        if (userDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(userId, UserDetails.class);
        }
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    @Transactional
    public Optional<UserDetailsDTO> update(Long userId, UserDetailsDTO updatedUserDetailsDTO) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.update(userId, mapDTOToUserDetails(updatedUserDetailsDTO));
        if (userDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(userId, UserDetails.class);
        }
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    @Transactional
    public void delete(Long userId) {
        userDetailsRepository.delete(userId);
    }

    private UserDetails mapDTOToUserDetails(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserDetails.class);
    }

    private UserDetailsDTO mapUserDetailsToDTO(UserDetails userDetails) {
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }
}
