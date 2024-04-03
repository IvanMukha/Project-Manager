package org.example.application.service;

import org.example.application.dto.RoleDTO;
import org.example.application.model.Role;
import org.example.application.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> getAll() {
        return roleRepository.getAll().stream().map(this::mapRoleToDTO).collect(Collectors.toList());
    }

    public void save(RoleDTO roleDTO) {
        roleRepository.save(mapDTOToRole(roleDTO));
    }

    public Optional<RoleDTO> getById(int id) {
        Optional<Role> roleOptional = roleRepository.getById(id);
        return roleOptional.map(this::mapRoleToDTO);
    }

    public void update(int id, RoleDTO updatedRoleDTO) {
        roleRepository.update(id, mapDTOToRole(updatedRoleDTO));
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
