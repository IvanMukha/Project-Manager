package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
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
        checkUserDTO(userDTO);
        return mapUserToDTO(userRepository.save(mapDTOToUser(userDTO)));
    }

    public Optional<UserDTO> getById(Long id) {
        checkId(id);
        Optional<User> userOptional = userRepository.getById(id);
        return userOptional.map(this::mapUserToDTO);
    }

    @Transactional
    public Optional<UserDTO> update(Long id, UserDTO updatedUserDTO) {
        checkId(id);
        checkUserDTO(updatedUserDTO);
        Optional<User> userOptional = userRepository.update(id, mapDTOToUser(updatedUserDTO));
        return userOptional.map(this::mapUserToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        userRepository.delete(id);
    }

    private void checkUserDTO(UserDTO userDTO) {
        if (userDTO.getUsername() == null) {
            throw new HandleCustomNullPointerException("Username cannot be null");
        }
        if (userDTO.getUsername().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Username cannot be empty");
        }
        if (userDTO.getPassword() == null) {
            throw new HandleCustomNullPointerException("Password cannot be null");
        }
        if (userDTO.getPassword().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Password cannot be empty");
        }
        if (userDTO.getEmail() == null) {
            throw new HandleCustomNullPointerException("Email cannot be null");
        }
        if (userDTO.getEmail().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Email cannot be empty");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("User id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomNullPointerException("User id cannot be greater than 0");
        }
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
