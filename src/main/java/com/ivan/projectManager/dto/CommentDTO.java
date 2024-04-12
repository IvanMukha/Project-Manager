package com.ivan.projectManager.dto;

public class CommentDTO {
    private int id;
    private String text;

    public int getId() {
        return id;
    }

    public CommentDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public CommentDTO setText(String text) {
        this.text = text;
        return this;
    }
}
