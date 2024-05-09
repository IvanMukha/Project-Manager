package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.model.User;

import java.util.List;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> getByUsernameJPQL(String username);

    List<User> getAllJpqlFetch();

    List<User> getAllCriteriaFetch();

    List<User> getAllGraphFetch();

    List<User> getByUsernameCriteria(String username);

    Set<Role> findRolesByUserUsername(String username);
}
