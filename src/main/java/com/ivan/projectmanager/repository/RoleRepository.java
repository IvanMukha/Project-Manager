package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Role;

import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Set<Role> findByName(String name);
}
