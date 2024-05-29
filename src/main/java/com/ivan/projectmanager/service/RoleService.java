package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.model.Role;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
    Page<RoleDTO> getAll(Integer page, Integer size);

    RoleDTO save(RoleDTO roleDTO);

    Optional<RoleDTO> getById(Long id);

    Optional<RoleDTO> update(Long id, RoleDTO updatedRoleDTO);

    void delete(Long id);

    Role getDefaultRole();
}
