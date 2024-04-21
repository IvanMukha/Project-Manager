package com.ivan.projectmanager.dto;

public class CommentDTO {
    private Long id;
    private String text;

    public Long getId() {
        return id;
    }

    public CommentDTO setId(Long id) {
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
