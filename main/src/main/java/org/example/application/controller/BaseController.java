package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface BaseController<D> {

    String getAll() throws JsonProcessingException;

    String save(D dto) throws JsonProcessingException;

    String getById(int id) throws JsonProcessingException;

    String update(int id, D dto) throws JsonProcessingException;

    void delete(int id);
}
