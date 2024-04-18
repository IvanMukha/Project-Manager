package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Override
    public List<Role> getAll() {
        return List.of();
    }

    @Override
    public Role save(Role entity) {
        return null;
    }

    @Override
    public Optional<Role> getById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> update(Integer integer, Role updatedEntity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {

    }
}
