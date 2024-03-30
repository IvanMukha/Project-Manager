package org.example.repository;
import org.example.annotations.Autowire;
import org.example.annotations.Component;
import org.example.utils.ParametersHolder;
@Component
public class DatabaseRepository implements DatabaseInterface {
    private ParametersHolder parametersHolder;
    @Autowire
    public DatabaseRepository(ParametersHolder parametersHolder) {
        this.parametersHolder = parametersHolder;
    }

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }
}


