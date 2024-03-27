package org.example.repository;
import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.utils.ParametersHolder;
@Component
public class DatabaseRepository implements DatabaseInterface {
    @Autowired
    private ParametersHolder parametersHolder;

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }
}


