package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.UserDetailsRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsRepositoryImpl extends AbstractRepository<UserDetails, Long> implements UserDetailsRepository {
    public UserDetailsRepositoryImpl(EntityManager entityManager) {
        super(entityManager, UserDetails.class);
    }
}
