package org.example.application.controller;

import org.example.application.service.ServiceInterface;
import org.example.di.annotations.Autowired;
import org.example.di.annotations.Component;

@Component
public class MyController {
    @Autowired
    private ServiceInterface service;

    public void execute() {
        service.execute();
    }
}


