package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.model.User_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.UserRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ivan.projectmanager.model.User_.username;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User,Integer> implements  UserRepository {

    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public Optional<User> getById(Integer id) {
        return super.getById(id);
    }

    @Override
    public Optional<User> update(Integer id, User updatedEntity) {
        return super.getById(id).map(user -> {
            user.setUsername(updatedEntity.getUsername());
            user.setPassword(updatedEntity.getPassword());
            user.setEmail(updatedEntity.getEmail());
            entityManager.merge(user);
            return user;
        });
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    public List<User> getByUsernameCriteria(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = builder.equal(root.get(User_.username), username);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    public List<User> getByUsernameJPQL(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<User> getAllJpqlFetch() {
        return entityManager.createQuery(
                        "SELECT DISTINCT u FROM User u " +
                                "LEFT JOIN FETCH u.roles " +
                                "LEFT JOIN FETCH u.teams " +
                                "WHERE u.id IS NOT NULL", User.class)
                .getResultList();
    }

    public List<User> getAllCriteriaFetch() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Fetch<User, ?> rolesFetch = root.fetch("roles", JoinType.LEFT);
        Fetch<User, ?> teamsFetch = root.fetch("teams", JoinType.LEFT);
        query.select(root).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
    public List<User> getAllGraphFetch() {
        EntityGraph<User> entityGraph = entityManager.createEntityGraph(User.class);
        entityGraph.addAttributeNodes("roles", "teams");

        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.id IS NOT NULL", User.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }
}

