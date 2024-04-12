package com.ivan.projectManager.service.impl;

import com.ivan.projectManager.dto.UserDetailsDTO;
import com.ivan.projectManager.model.UserDetails;
import com.ivan.projectManager.repository.UserDetailsRepository;
import com.ivan.projectManager.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserDetailsDTO save(UserDetailsDTO userDetailsDTO) {
        return mapUserDetailsToDTO(userDetailsRepository.save(mapDTOToUserDetails(userDetailsDTO)));
    }

    public Optional<UserDetailsDTO> getById(int id) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.getById(id);
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }


    public Optional<UserDetailsDTO> update(int id, UserDetailsDTO updatedUserDetailsDTO) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.update(id, mapDTOToUserDetails(updatedUserDetailsDTO));
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    public void delete(int id) {
        userDetailsRepository.delete(id);
    }

    private UserDetails mapDTOToUserDetails(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserDetails.class);
    }

    private UserDetailsDTO mapUserDetailsToDTO(UserDetails userDetails) {
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }
}
