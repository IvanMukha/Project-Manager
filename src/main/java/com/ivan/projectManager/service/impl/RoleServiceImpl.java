package com.ivan.projectManager.service.impl;

import com.ivan.projectManager.dto.RoleDTO;
import com.ivan.projectManager.model.Role;
import com.ivan.projectManager.repository.RoleRepository;
import com.ivan.projectManager.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RoleDTO save(RoleDTO roleDTO) {
        return mapRoleToDTO(roleRepository.save(mapDTOToRole(roleDTO)));
    }

    public Optional<RoleDTO> getById(int id) {
        Optional<Role> roleOptional = roleRepository.getById(id);
        return roleOptional.map(this::mapRoleToDTO);
    }

    public Optional<RoleDTO> update(int id, RoleDTO updatedRoleDTO) {
        Optional<Role> roleOptional = roleRepository.update(id, mapDTOToRole(updatedRoleDTO));
        return roleOptional.map(this::mapRoleToDTO);
    }

    public void delete(int id) {
        roleRepository.delete(id);
    }

    private Role mapDTOToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    private RoleDTO mapRoleToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
