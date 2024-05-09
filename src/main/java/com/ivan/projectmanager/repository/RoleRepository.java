package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
