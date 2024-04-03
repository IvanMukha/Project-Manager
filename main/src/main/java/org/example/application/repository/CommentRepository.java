package org.example.application.repository;

import org.example.application.model.Comment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    List<Comment> comments = new ArrayList<>();

    public List<Comment> getAll() {
        return new ArrayList<>(comments);
    }

    public void save(Comment comment) {
        comments.add(comment);
    }

    public Optional<Comment> getById(int id) {
        return comments.stream().filter(comment -> comment.getId() == id).findFirst();
    }

    public void update(int id, Comment updatedComment) {
        Optional<Comment> optionalComment = getById(id);
        optionalComment.ifPresent(comment -> comment.setText(updatedComment.getText()));
    }

    public void delete(int id) {
        comments.removeIf(comment -> comment.getId() == id);
    }
}
