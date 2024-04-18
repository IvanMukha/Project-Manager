package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Override
    public List<Comment> getAll() {
        return List.of();
    }

    @Override
    public Comment save(Comment entity) {
        return null;
    }

    @Override
    public Optional<Comment> getById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> update(Integer integer, Comment updatedEntity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {

    }
}
