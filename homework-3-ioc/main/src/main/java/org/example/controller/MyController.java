package org.example.controller;

import org.example.annotations.Autowire;
import org.example.annotations.Component;
import org.example.service.ServiceInterface;
@Component
public class MyController {
    @Autowire
    private ServiceInterface service;

    public void execute() {
        service.execute();
    }
}


