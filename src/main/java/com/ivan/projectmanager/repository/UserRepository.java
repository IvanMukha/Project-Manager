package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> getAll(Pageable pageable);

    List<User> getByUsername(String username);

    Set<Role> findRolesByUserUsername(String username);
}
