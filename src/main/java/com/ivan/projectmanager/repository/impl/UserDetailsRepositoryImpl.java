package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.UserDetails;
import com.ivan.projectmanager.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDetailsRepositoryImpl implements UserDetailsRepository {

    @Override
    public List<UserDetails> getAll() {
        return List.of();
    }

    @Override
    public UserDetails save(UserDetails entity) {
        return null;
    }

    @Override
    public Optional<UserDetails> getById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetails> update(Integer integer, UserDetails updatedEntity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {

    }
}
