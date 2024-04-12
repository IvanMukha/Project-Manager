package com.ivan.projectManager.repository.impl;

import com.ivan.projectManager.model.Comment;
import com.ivan.projectManager.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private static final Logger log = LoggerFactory.getLogger(CommentRepositoryImpl.class);
    List<Comment> comments = new ArrayList<>();

    public List<Comment> getAll() {
        return comments;
    }

    public Comment save(Comment comment) {
        comments.add(comment);
        return comment;
    }

    public Optional<Comment> getById(int id) {
        Optional<Comment> optionalComment = comments.stream()
                .filter(comment -> comment.getId() == id)
                .findFirst();
        if (optionalComment.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalComment;
    }

    public Optional<Comment> update(int id, Comment updatedComment) {
        Optional<Comment> optionalComment = getById(id);
        optionalComment.ifPresent(comment -> comment.setText(updatedComment.getText()));
        return optionalComment;
    }

    public void delete(int id) {
        comments.removeIf(comment -> comment.getId() == id);
    }
}
