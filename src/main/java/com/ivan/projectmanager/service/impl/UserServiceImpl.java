package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
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
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException(id, User.class);
        }
        return userOptional.map(this::mapUserToDTO);
    }

    @Transactional
    public Optional<UserDTO> update(Long id, UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepository.update(id, mapDTOToUser(updatedUserDTO));
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException(id, User.class);
        }
        return userOptional.map(this::mapUserToDTO);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsernameCriteria(username).getFirst();
        if (username.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Optional<User> userModel = Optional.ofNullable(user);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : userModel.get().getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        }
        return new org.springframework.security.core.userdetails.User(
                userModel.get().getUsername(),
                userModel.get().getPassword(),
                grantedAuthorities
        );
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
