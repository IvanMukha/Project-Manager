package com.ivan.projectmanager.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractRepository<T, ID> implements CrudRepository<T, ID> {

    protected final EntityManager entityManager;
    private final Class<T> entityClass;

    public AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public Optional<T> getById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public List<T> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public Optional<T> update(ID id, T entity) {
        entityManager.merge(entity);
        return Optional.of(entity);
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void delete(ID id) {
        getById(id).ifPresent(entity -> entityManager.remove(entity));
    }
}