package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
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
        return mapRoleToDTO(roleRepository.save(mapDTOToRole(roleDTO)));
    }

    public Optional<RoleDTO> getById(Long id) {
        Optional<Role> roleOptional = roleRepository.getById(id);
        if (roleOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Role.class);
        }
        return roleOptional.map(this::mapRoleToDTO);
    }

    @Transactional
    public Optional<RoleDTO> update(Long id, RoleDTO updatedRoleDTO) {
        Optional<Role> roleOptional = roleRepository.update(id, mapDTOToRole(updatedRoleDTO));
        if (roleOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Role.class);
        }
        return roleOptional.map(this::mapRoleToDTO);
    }

    @Transactional
    public void delete(Long id) {
        roleRepository.delete(id);
    }

    private Role mapDTOToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    private RoleDTO mapRoleToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
