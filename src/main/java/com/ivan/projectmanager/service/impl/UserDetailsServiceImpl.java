package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.UserDetailsRepository;
import com.ivan.projectmanager.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ModelMapper modelMapper;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsServiceImpl(ModelMapper modelMapper, UserDetailsRepository userDetailsRepository) {
        this.modelMapper = modelMapper;
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<UserDetailsDTO> getAll() {
        return userDetailsRepository.getAll().stream().map(this::mapUserDetailsToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserDetailsDTO save(UserDetailsDTO userDetailsDTO) {
        checkUserDetailsDTO(userDetailsDTO);
        return mapUserDetailsToDTO(userDetailsRepository.save(mapDTOToUserDetails(userDetailsDTO)));
    }

    public Optional<UserDetailsDTO> getById(Long id) {
        checkId(id);
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.getById(id);
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    @Transactional
    public Optional<UserDetailsDTO> update(Long id, UserDetailsDTO updatedUserDetailsDTO) {
        checkId(id);
        checkUserDetailsDTO(updatedUserDetailsDTO);
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.update(id, mapDTOToUserDetails(updatedUserDetailsDTO));
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        userDetailsRepository.delete(id);
    }

    private void checkUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
        if (userDetailsDTO == null) {
            throw new HandleCustomNullPointerException("UserDetails cannot be null");
        }
        if (userDetailsDTO.getName() == null) {
            throw new HandleCustomNullPointerException("Name cannot be null");
        }
        if (userDetailsDTO.getName().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Name cannot be empty");
        }
        if (userDetailsDTO.getSurname() == null) {
            throw new HandleCustomNotFoundException("Surname cannot be null");
        }
        if (userDetailsDTO.getSurname().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Surname cannot be empty");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("User id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("User id must be greater than 0");
        }
    }

    private UserDetails mapDTOToUserDetails(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserDetails.class);
    }

    private UserDetailsDTO mapUserDetailsToDTO(UserDetails userDetails) {
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }
}
