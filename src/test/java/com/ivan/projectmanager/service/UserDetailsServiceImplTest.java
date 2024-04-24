package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.UserDetailsRepository;
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
public class UserDetailsServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserDetailsService userDetailsService;


    @Test
    void testGetUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setName("John").setSurname("Doe").setPhone("123456789").setWorkPhone("987654321")
                .setWorkAddress("123 Main St").setDepartment("IT");
    }

    @Test
    void testSaveUserDetails() {
        UserDetails userDetails = new UserDetails()
                .setName("John")
                .setSurname("Doe")
                .setPhone("123456789")
                .setWorkPhone("987654321")
                .setWorkAddress("123 Main St")
                .setDepartment("IT");

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO()
                .setName("John")
                .setSurname("Doe")
                .setPhone("123456789")
                .setWorkPhone("987654321")
                .setWorkAddress("123 Main St")
                .setDepartment("IT");

        Mockito.when(userDetailsRepository.save(userDetails)).thenReturn(userDetails);
        UserDetailsDTO savedUserDetailsDTO = userDetailsService.save(userDetailsDTO);
        assertNotNull(savedUserDetailsDTO);
        assertEquals(userDetails.getName(), savedUserDetailsDTO.getName());
        assertEquals(userDetails.getSurname(), savedUserDetailsDTO.getSurname());
        assertEquals(userDetails.getPhone(), savedUserDetailsDTO.getPhone());
        assertEquals(userDetails.getWorkPhone(), savedUserDetailsDTO.getWorkPhone());
        assertEquals(userDetails.getWorkAddress(), savedUserDetailsDTO.getWorkAddress());
        assertEquals(userDetails.getDepartment(), savedUserDetailsDTO.getDepartment());
        verify(userDetailsRepository).save(any());
    }

    @Test
    void testGetUserDetailsById() {
        UserDetails userDetails = new UserDetails()
                .setName("John")
                .setSurname("Doe")
                .setPhone("123456789")
                .setWorkPhone("987654321")
                .setWorkAddress("123 Main St")
                .setDepartment("IT");

        when(userDetailsRepository.getById(1L)).thenReturn(Optional.of(userDetails));
        Optional<UserDetailsDTO> result = userDetailsService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(userDetails.getName(), result.get().getName());
        assertEquals(userDetails.getSurname(), result.get().getSurname());
        assertEquals(userDetails.getPhone(), result.get().getPhone());
        assertEquals(userDetails.getWorkPhone(), result.get().getWorkPhone());
        assertEquals(userDetails.getWorkAddress(), result.get().getWorkAddress());
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
