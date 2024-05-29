package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.RoleRepository;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Page<UserDTO> getAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        return userRepository.getAll(PageRequest.of(page, size)).map(this::mapUserToDTO);
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

    public List<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList = userRepository.getByUsername(username);
        User user = userList.stream().findFirst().orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<Role> roles = userRepository.findRolesByUserUsername(username);
        Optional<User> userModel = Optional.of(user);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        }
        return new org.springframework.security.core.userdetails.User(
                userModel.get().getUsername(),
                userModel.get().getPassword(),
                grantedAuthorities
        );
    }

    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    public Optional<UserDTO> assignRoleToUser(Long userId, RoleDTO roleDTO) {
        User user = userRepository.getById(userId).stream().findFirst().orElseThrow(() -> new CustomNotFoundException(userId, User.class));
        Role role = roleRepository.getById(roleDTO.getId()).stream().findFirst().orElseThrow(() -> new CustomNotFoundException(roleDTO.getId(), Role.class));
        if (user.getRoles().stream().noneMatch(r -> r.getId().equals(role.getId()))) {
            user.getRoles().add(role);
        }
        Optional<User> userOptional = userRepository.update(userId, user);
        return userOptional.map(this::mapUserToDTO);
    }

    public Optional<UserDTO> removeRoleFromUser(Long userId, RoleDTO roleDTO) {
        User user = userRepository.getById(userId).stream().findFirst().orElseThrow(() -> new CustomNotFoundException(userId, User.class));
        Role role = roleRepository.getById(roleDTO.getId()).stream().findFirst().orElseThrow(() -> new CustomNotFoundException(roleDTO.getId(), Role.class));
        user.getRoles().removeIf(r -> r.getId().equals(role.getId()));
        Optional<User> userOptional = userRepository.update(userId, user);
        return userOptional.map(this::mapUserToDTO);
    }

    private User mapDTOToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (userDTO.getRoleId() != null) {
            Role role = new Role();
            role.setId(userDTO.getRoleId());
            user.setRoles(Collections.singleton(role));
        }
        return user;
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
