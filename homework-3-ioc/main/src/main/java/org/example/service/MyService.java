package org.example.service;

import org.example.annotations.Autowire;
import org.example.annotations.Component;
import org.example.controller.MyController;
import org.example.repository.DatabaseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class MyService implements ServiceInterface {
    private DatabaseInterface database;
    private static final Logger log = LoggerFactory.getLogger(MyService.class);


    @Autowire
    public void setDatabase(DatabaseInterface database) {
        this.database = database;
    }


    @Override
    public void execute() {
        String result = database.execute();
        log.info(result);

    }
}

