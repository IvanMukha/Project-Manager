package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Page<Role> getAll(Pageable pageable);

    Role findByName(String name);
}
