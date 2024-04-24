package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {
        User user = new User()
                .setUsername("testUser")
                .setPassword("testPassword")
                .setEmail("test@example.com");

        UserDTO userDTO = new UserDTO()
                .setUsername("testUser")
                .setPassword("testPassword")
                .setEmail("test@example.com");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDTO savedUserDTO = userService.save(userDTO);
        assertNotNull(savedUserDTO);
        assertEquals(user.getUsername(), savedUserDTO.getUsername());
        assertEquals(user.getPassword(), savedUserDTO.getPassword());
        assertEquals(user.getEmail(), savedUserDTO.getEmail());
        verify(userRepository).save(any());
    }

    @Test
    void testGetUserById() {
        User user = new User()
                .setId(1L)
                .setUsername("testUser")
                .setPassword("testPassword")
                .setEmail("test@example.com");

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
