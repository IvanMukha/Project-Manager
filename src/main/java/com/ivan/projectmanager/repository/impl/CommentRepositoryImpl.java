package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends AbstractRepository<Comment, Long> implements CommentRepository {

    public CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Comment.class);
    }
}

