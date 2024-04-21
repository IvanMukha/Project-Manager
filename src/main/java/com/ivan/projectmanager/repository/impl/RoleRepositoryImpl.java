package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role, Long> implements RoleRepository {

    public RoleRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Role.class);
    }
}

