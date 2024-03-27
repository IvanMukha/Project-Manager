package org.example.controller;

import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.service.ServiceInterface;
@Component
public class MyController {
    @Autowired
    private ServiceInterface service;

    public void execute() {
        service.execute();
    }
}


