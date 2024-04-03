package org.example.application.service;

import org.example.application.dto.UserDetailsDTO;
import org.example.application.model.UserDetails;
import org.example.application.repository.UserDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsService {
    private final ModelMapper modelMapper;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsService(ModelMapper modelMapper, UserDetailsRepository userDetailsRepository) {
        this.modelMapper = modelMapper;
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<UserDetailsDTO> getAll() {
        return userDetailsRepository.getAll().stream().map(this::mapUserDetailsToDTO).collect(Collectors.toList());
    }

    public void save(UserDetailsDTO userDetailsDTO) {
        userDetailsRepository.save(mapDTOToUserDetails(userDetailsDTO));
    }

    public Optional<UserDetailsDTO> getById(int id) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.getById(id);
        return userDetailsOptional.map(this::mapUserDetailsToDTO);
    }

    public void update(int id, UserDetailsDTO updatedUserDetailsDTO) {
        userDetailsRepository.update(id, mapDTOToUserDetails(updatedUserDetailsDTO));
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
