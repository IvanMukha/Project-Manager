package org.example.service;

import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.repository.DatabaseInterface;
@Component
public class MyService implements ServiceInterface {
    @Autowired
    private DatabaseInterface database;

    @Override
    public void execute() {
        String result = database.execute();
        System.out.println("Result from database: " + result);
    }
}

