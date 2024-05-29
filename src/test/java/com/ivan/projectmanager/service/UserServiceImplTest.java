package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void seUp() {
        user = new User()
                .setUsername("testUser")
                .setPassword("testPassword")
                .setEmail("test@example.com");

        userDTO = new UserDTO()
                .setUsername("testUser")
                .setPassword("testPassword")
                .setEmail("test@example.com");
    }

    @Test
    void getAllUsers() {
        User user2 = user;
        UserDTO userDTO2 = userDTO;
        List<User> users = List.of(user, user2);
        Page<User> page = new PageImpl<>(users, PageRequest.of(0, 10), users.size());

        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(user2, UserDTO.class)).thenReturn(userDTO2);
        when(userRepository.getAll(PageRequest.of(0, 10))).thenReturn(page);

        List<UserDTO> result = userService.getAll(0, 10).getContent();
        assertEquals(2, result.size());
        assertEquals(user.getUsername(), result.getFirst().getUsername());
        assertEquals(user.getPassword(), result.getFirst().getPassword());
        assertEquals(user.getEmail(), result.getFirst().getEmail());
        assertEquals(user2.getUsername(), result.getLast().getUsername());
        assertEquals(user2.getPassword(), result.getLast().getPassword());
        assertEquals(user2.getEmail(), result.getLast().getEmail());
    }

    @Test
    void testSaveUser() {
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        UserDTO savedUserDTO = userService.save(userDTO);
        assertNotNull(savedUserDTO);
        assertEquals(user.getUsername(), savedUserDTO.getUsername());
        assertEquals(user.getPassword(), savedUserDTO.getPassword());
        assertEquals(user.getEmail(), savedUserDTO.getEmail());
        verify(userRepository).save(any());
    }

    @Test
    void testGetUserById() {
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(userRepository.getById(1L)).thenReturn(Optional.of(user));
        Optional<UserDTO> result = userService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getPassword(), result.get().getPassword());
        assertEquals(user.getEmail(), result.get().getEmail());
        verify(userRepository).getById(1L);
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;
        userService.delete(id);
        verify(userRepository).delete(id);
    }
}
