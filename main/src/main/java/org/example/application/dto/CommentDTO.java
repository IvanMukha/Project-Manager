package org.example.application.dto;

public class CommentDTO {
    private int id;
    private String text;

    public String getText() {
        return text;
    }

    public CommentDTO setText(String text) {
        this.text = text;
        return this;
    }

    public int getId() {
        return id;
    }

    public CommentDTO setId(int id) {
        this.id = id;
        return this;
    }
}
