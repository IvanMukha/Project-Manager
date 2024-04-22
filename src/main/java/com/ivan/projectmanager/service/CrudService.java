package com.ivan.projectmanager.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<D> {
    List<D> getAll();

    D save(D dto);

    Optional<D> getById(Long id);

    Optional<D> update(Long id, D updatedDto);

    void delete(Long id);
}
