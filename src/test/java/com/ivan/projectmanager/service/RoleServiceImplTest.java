package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

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
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void testGetAllRoles() {
        Role role = new Role().setName("role1");
        Role role2 = new Role().setName("role2");
        when(roleRepository.getAll()).thenReturn(List.of(role, role2));
        List<RoleDTO> result = roleService.getAll();
        assertEquals(2, result.size());
        assertEquals(role.getName(), result.get(0).getName());
        assertEquals(role2.getName(), result.get(1).getName());
        verify(roleRepository).getAll();
    }

    @Test
    void testSaveRole() {
        Role role = new Role().setName("Test Role");
        RoleDTO roleDTO = new RoleDTO().setName("Test Role");
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        RoleDTO savedRoleDTO = roleService.save(roleDTO);
        assertNotNull(savedRoleDTO);
        assertEquals(role.getName(), savedRoleDTO.getName());
        verify(roleRepository).save(any());
    }

    @Test
    void testGetRoleById() {
        Role role = new Role().setName("Test Role");
        when(roleRepository.getById(1L)).thenReturn(Optional.of(role));
        Optional<RoleDTO> result = roleService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Role", result.get().getName());
        verify(roleRepository).getById(1L);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        roleService.delete(id);
        verify(roleRepository).delete(id);
    }
}
