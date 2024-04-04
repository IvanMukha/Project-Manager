package org.example.application.service;

import org.example.application.repositoryInterfaces.UserDetailsRepositoryInterface;
import org.example.application.serviceInterfaces.UserDetailsServiceInterface;
import org.example.application.dto.UserDetailsDTO;
import org.example.application.model.UserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements UserDetailsServiceInterface {
    private final ModelMapper modelMapper;
    private final UserDetailsRepositoryInterface userDetailsRepository;

    @Autowired
    public UserDetailsService(ModelMapper modelMapper, UserDetailsRepositoryInterface userDetailsRepository) {
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
