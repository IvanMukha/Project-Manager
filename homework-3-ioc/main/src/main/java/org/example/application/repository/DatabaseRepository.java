package org.example.application.repository;

import org.example.application.utils.ParametersHolder;
import org.example.di.annotations.Autowired;
import org.example.di.annotations.Component;

@Component
public class DatabaseRepository implements DatabaseInterface {
    private ParametersHolder parametersHolder;

    @Autowired
    public DatabaseRepository(ParametersHolder parametersHolder) {
        this.parametersHolder = parametersHolder;
    }

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }
}


