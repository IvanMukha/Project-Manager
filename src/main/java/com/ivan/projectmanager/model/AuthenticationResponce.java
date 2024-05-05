package com.ivan.projectmanager.model;

public class AuthenticationResponce {
    private final String token;

    public AuthenticationResponce(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}