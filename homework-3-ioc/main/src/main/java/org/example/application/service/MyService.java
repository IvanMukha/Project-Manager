package org.example.application.service;

import org.example.application.repository.DatabaseInterface;
import org.example.di.annotations.Autowired;
import org.example.di.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class MyService implements ServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    private DatabaseInterface database;

    @Autowired
    public void setDatabase(DatabaseInterface database) {
        this.database = database;
    }

    @Override
    public void execute() {
        String result = database.execute();
        log.info(result);

    }
}

