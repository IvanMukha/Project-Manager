package org.example.controller;

import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.service.ServiceInterface;
@Component
public class MyController {
    private final ServiceInterface service;
    @Autowired
    public MyController(ServiceInterface service){
        this.service=service;
    }
    public void execute() {
        service.execute();
    }
}


