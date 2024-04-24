package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.repository.RoleRepository;
import com.ivan.projectmanager.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> getAll() {
        return roleRepository.getAll().stream().map(this::mapRoleToDTO).collect(Collectors.toList());
    }

    @Transactional
    public RoleDTO save(RoleDTO roleDTO) {
        checkRoleDTO(roleDTO);
        return mapRoleToDTO(roleRepository.save(mapDTOToRole(roleDTO)));
    }

    public Optional<RoleDTO> getById(Long id) {
        checkId(id);
        Optional<Role> roleOptional = roleRepository.getById(id);
        if (roleOptional.isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Role with id " + id + " not found");
        }
        return roleOptional.map(this::mapRoleToDTO);
    }

    @Transactional
    public Optional<RoleDTO> update(Long id, RoleDTO updatedRoleDTO) {
        checkId(id);
        checkRoleDTO(updatedRoleDTO);
        Optional<Role> roleOptional = roleRepository.update(id, mapDTOToRole(updatedRoleDTO));
        if (roleOptional.isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Role with id " + id + " not found");
        }
        return roleOptional.map(this::mapRoleToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        roleRepository.delete(id);
    }

    private void checkRoleDTO(RoleDTO roleDTO) {
        if (roleDTO == null) {
            throw new HandleCustomNullPointerException("RoleDTO is null");
        }
        if (roleDTO.getName() == null) {
            throw new HandleCustomNullPointerException("Role name cannot be null");
        }
        if (roleDTO.getName().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Role name cannot be empty");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Role id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Role id must be greater than 0");
        }
    }

    private Role mapDTOToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    private RoleDTO mapRoleToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
