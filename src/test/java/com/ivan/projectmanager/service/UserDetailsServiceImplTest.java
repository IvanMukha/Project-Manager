package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.UserDetailsRepository;
import com.ivan.projectmanager.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
@WebAppConfiguration
public class UserDetailsServiceImplTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    private UserDetailsRepository userDetailsRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    private UserDetails userDetails;
    private UserDetailsDTO userDetailsDTO;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

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
    void testGetAllUserDetails() {
        UserDetails userDetails2 = userDetails;
        UserDetailsDTO userDetailsDTO2 = userDetailsDTO;
        when(modelMapper.map(userDetails, UserDetailsDTO.class)).thenReturn(userDetailsDTO);
        when(modelMapper.map(userDetails2, UserDetailsDTO.class)).thenReturn(userDetailsDTO2);
        when(userDetailsRepository.getAll()).thenReturn(List.of(userDetails, userDetails2));
        List<UserDetailsDTO> result = userDetailsService.getAll();
        assertEquals(2, result.size());
        assertEquals(userDetails.getName(), result.getFirst().getName());
        assertEquals(userDetails.getSurname(), result.getFirst().getSurname());
        assertEquals(userDetails.getPhone(), result.getFirst().getPhone());
        assertEquals(userDetails.getWorkPhone(), result.getFirst().getWorkPhone());
        assertEquals(userDetails.getWorkAdress(), result.getFirst().getWorkAdress());
        assertEquals(userDetails.getDepartment(), result.getFirst().getDepartment());
        assertEquals(userDetails2.getName(), result.get(1).getName());
        assertEquals(userDetails2.getSurname(), result.get(1).getSurname());
        assertEquals(userDetails2.getPhone(), result.get(1).getPhone());
        assertEquals(userDetails2.getWorkPhone(), result.get(1).getWorkPhone());
        assertEquals(userDetails2.getWorkAdress(), result.get(1).getWorkAdress());
        assertEquals(userDetails2.getDepartment(), result.get(1).getDepartment());
        verify(userDetailsRepository).getAll();
    }

    @Test
    void testSaveUserDetails() {
        when(modelMapper.map(userDetailsDTO, UserDetails.class)).thenReturn(userDetails);
        when(modelMapper.map(userDetails, UserDetailsDTO.class)).thenReturn(userDetailsDTO);
        when(userDetailsRepository.save(userDetails)).thenReturn(userDetails);
        UserDetailsDTO savedUserDetailsDTO = userDetailsService.save(userDetailsDTO);
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
        Long id = 1L;
        userDetailsService.delete(id);
        verify(userDetailsRepository).delete(id);
    }
}
