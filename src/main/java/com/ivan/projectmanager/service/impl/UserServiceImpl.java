package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    public List<UserDTO> getAll() {
        return userRepository.getAll().stream().map(this::mapUserToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO save(UserDTO userDTO) {
        return mapUserToDTO(userRepository.save(mapDTOToUser(userDTO)));
    }

    public Optional<UserDTO> getById(Long id) {
        Optional<User> userOptional = userRepository.getById(id);
        return userOptional.map(this::mapUserToDTO);
    }

    @Transactional
    public Optional<UserDTO> update(Long id, UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepository.update(id, mapDTOToUser(updatedUserDTO));
        return userOptional.map(this::mapUserToDTO);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}