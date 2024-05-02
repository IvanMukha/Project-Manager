package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.UserDetailsRepository;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    private UserDetailsRepository userDetailsRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    private UserDetails userDetails;
    private UserDetailsDTO userDetailsDTO;

    @BeforeEach
    void setUp() {
        userDetails = new UserDetails()
                .setName("John")
                .setSurname("Doe")
                .setPhone("123456789")
                .setWorkPhone("987654321")
                .setWorkAdress("123 Main St")
                .setDepartment("IT");

        userDetailsDTO = new UserDetailsDTO()
                .setName("John")
                .setSurname("Doe")
                .setPhone("123456789")
                .setWorkPhone("987654321")
                .setWorkAdress("123 Main St")
                .setDepartment("IT");
    }

    @Test
    void testSaveUserDetails() {
        User user = new User().setId(1L).setUsername("username");
        when(modelMapper.map(userDetailsDTO, UserDetails.class)).thenReturn(userDetails);
        when(modelMapper.map(userDetails, UserDetailsDTO.class)).thenReturn(userDetailsDTO);
        when(userRepository.getById(1L)).thenReturn(Optional.ofNullable(user));
        when(userDetailsRepository.save(userDetails)).thenReturn(userDetails);
        UserDetailsDTO savedUserDetailsDTO = userDetailsService.save(1L, userDetailsDTO);
        assertNotNull(savedUserDetailsDTO);
        assertEquals(userDetails.getName(), savedUserDetailsDTO.getName());
        assertEquals(userDetails.getSurname(), savedUserDetailsDTO.getSurname());
        assertEquals(userDetails.getPhone(), savedUserDetailsDTO.getPhone());
        assertEquals(userDetails.getWorkPhone(), savedUserDetailsDTO.getWorkPhone());
        assertEquals(userDetails.getWorkAdress(), savedUserDetailsDTO.getWorkAdress());
        assertEquals(userDetails.getDepartment(), savedUserDetailsDTO.getDepartment());
        verify(userDetailsRepository).save(any());
    }

    @Test
    void testGetUserDetailsById() {
        when(modelMapper.map(userDetails, UserDetailsDTO.class)).thenReturn(userDetailsDTO);
        when(userDetailsRepository.getById(1L)).thenReturn(Optional.of(userDetails));
        Optional<UserDetailsDTO> result = userDetailsService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(userDetails.getName(), result.get().getName());
        assertEquals(userDetails.getSurname(), result.get().getSurname());
        assertEquals(userDetails.getPhone(), result.get().getPhone());
        assertEquals(userDetails.getWorkPhone(), result.get().getWorkPhone());
        assertEquals(userDetails.getWorkAdress(), result.get().getWorkAdress());
        assertEquals(userDetails.getDepartment(), result.get().getDepartment());
        verify(userDetailsRepository).getById(1L);
    }

    @Test
    void testDeleteUserDetails() {
        userDetailsService.delete(1L);
        verify(userDetailsRepository).delete(1L);
    }
}
