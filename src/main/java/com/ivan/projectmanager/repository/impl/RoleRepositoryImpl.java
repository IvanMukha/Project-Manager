package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role, Long> implements RoleRepository {

    public RoleRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Role.class);
    }

    public Set<Role> findByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultStream().collect(Collectors.toSet());
    }
}

