package ivan.projectManager.application.service.impl;

import ivan.projectManager.application.dto.RoleDTO;
import ivan.projectManager.application.model.Role;
import ivan.projectManager.application.repository.RoleRepository;
import ivan.projectManager.application.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepositoryImpl;

    @Autowired
    public RoleServiceImpl(ModelMapper modelMapper, RoleRepository roleRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.roleRepositoryImpl = roleRepositoryImpl;
    }

    public List<RoleDTO> getAll() {
        return roleRepositoryImpl.getAll().stream().map(this::mapRoleToDTO).collect(Collectors.toList());
    }

    public RoleDTO save(RoleDTO roleDTO) {
        return mapRoleToDTO(roleRepositoryImpl.save(mapDTOToRole(roleDTO)));
    }

    public Optional<RoleDTO> getById(int id) {
        Optional<Role> roleOptional = roleRepositoryImpl.getById(id);
        return roleOptional.map(this::mapRoleToDTO);
    }

    public Optional<RoleDTO> update(int id, RoleDTO updatedRoleDTO) {
        Optional<Role> roleOptional = roleRepositoryImpl.update(id, mapDTOToRole(updatedRoleDTO));
        return roleOptional.map(this::mapRoleToDTO);
    }

    public void delete(int id) {
        roleRepositoryImpl.delete(id);
    }

    private Role mapDTOToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    private RoleDTO mapRoleToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
