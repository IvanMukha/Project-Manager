package org.example.application.service.impl;

import org.example.application.repository.UserRepository;
import org.example.application.service.UserService;
import org.example.application.dto.UserDTO;
import org.example.application.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepositoryImpl;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public List<UserDTO> getAll() {
        return userRepositoryImpl.getAll().stream().map(this::mapUserToDTO).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        return mapUserToDTO(userRepositoryImpl.save(mapDTOToUser(userDTO)));
    }

    public Optional<UserDTO> getById(int id) {
        Optional<User> userOptional = userRepositoryImpl.getById(id);
        return userOptional.map(this::mapUserToDTO);
    }


    public Optional<UserDTO> update(int id, UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepositoryImpl.update(id, mapDTOToUser(updatedUserDTO));
        return userOptional.map(this::mapUserToDTO);
    }

    public void delete(int id) {
        userRepositoryImpl.delete(id);
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
