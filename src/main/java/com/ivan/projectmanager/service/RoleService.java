package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.model.Role;

public interface RoleService extends CrudService<RoleDTO> {
    Role getDefaultRole();
}
