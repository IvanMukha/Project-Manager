package org.example.application.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, D> {
    List<D> getAll();

    D save(D dto);

    Optional<D> getById(int id);

    Optional<D> update(int id, D updatedDto);

    void delete(int id);
}
