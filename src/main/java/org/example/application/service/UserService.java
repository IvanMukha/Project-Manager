package org.example.application.service;

import org.example.application.RepositoryInterfaces.UserRepositoryInterface;
import org.example.application.ServiceInterfaces.UserServiceInterface;
import org.example.application.dto.UserDTO;
import org.example.application.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    private final ModelMapper modelMapper;
    private final UserRepositoryInterface userRepository;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepositoryInterface userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAll() {
        return userRepository.getAll().stream().map(this::mapUserToDTO).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        return mapUserToDTO(userRepository.save(mapDTOToUser(userDTO)));
    }

    public Optional<UserDTO> getById(int id) {
        Optional<User> userOptional = userRepository.getById(id);
        return userOptional.map(this::mapUserToDTO);
    }


    public Optional<UserDTO> update(int id, UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepository.update(id, mapDTOToUser(updatedUserDTO));
        return userOptional.map(this::mapUserToDTO);
    }

    public void delete(int id) {
        userRepository.delete(id);
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
