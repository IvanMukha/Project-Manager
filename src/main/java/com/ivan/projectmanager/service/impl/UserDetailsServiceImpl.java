package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.UserDetailsDTO;
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
        return mapUserDetailsToDTO(userDetailsRepository.save(mapDTOToUserDetails(userDetailsDTO)));
    }

    public Optional<UserDetailsDTO> getById(Long id) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.getById(id);
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    @Transactional
    public Optional<UserDetailsDTO> update(Long id, UserDetailsDTO updatedUserDetailsDTO) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.update(id, mapDTOToUserDetails(updatedUserDetailsDTO));
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    @Transactional
    public void delete(Long id) {
        userDetailsRepository.delete(id);
    }

    private UserDetails mapDTOToUserDetails(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserDetails.class);
    }

    private UserDetailsDTO mapUserDetailsToDTO(UserDetails userDetails) {
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }
}
