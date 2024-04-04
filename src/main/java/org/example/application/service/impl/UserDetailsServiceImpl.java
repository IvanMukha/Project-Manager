package org.example.application.service.impl;

import org.example.application.repository.UserDetailsRepository;
import org.example.application.service.UserDetailsService;
import org.example.application.dto.UserDetailsDTO;
import org.example.application.model.UserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ModelMapper modelMapper;
    private final UserDetailsRepository userDetailsRepositoryImpl;

    @Autowired
    public UserDetailsServiceImpl(ModelMapper modelMapper, UserDetailsRepository userDetailsRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.userDetailsRepositoryImpl = userDetailsRepositoryImpl;
    }

    public List<UserDetailsDTO> getAll() {
        return userDetailsRepositoryImpl.getAll().stream().map(this::mapUserDetailsToDTO).collect(Collectors.toList());
    }

    public UserDetailsDTO save(UserDetailsDTO userDetailsDTO) {
        return mapUserDetailsToDTO(userDetailsRepositoryImpl.save(mapDTOToUserDetails(userDetailsDTO)));
    }

    public Optional<UserDetailsDTO> getById(int id) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepositoryImpl.getById(id);
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }


    public Optional<UserDetailsDTO> update(int id, UserDetailsDTO updatedUserDetailsDTO) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepositoryImpl.update(id, mapDTOToUserDetails(updatedUserDetailsDTO));
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    public void delete(int id) {
        userDetailsRepositoryImpl.delete(id);
    }

    private UserDetails mapDTOToUserDetails(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserDetails.class);
    }

    private UserDetailsDTO mapUserDetailsToDTO(UserDetails userDetails) {
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }
}
