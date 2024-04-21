package com.ivan.projectmanager.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    List<T> getAll();

    T save(T entity);

    Optional<T> getById(ID id);

    Optional<T> update(ID id, T updatedEntity);

    void delete(ID id);
}
