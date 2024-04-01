package org.example.application.utils;

import org.example.di.annotations.Component;
import org.example.di.annotations.Value;

@Component
public class ParametersHolder {
    @Value("${my.param.db}")
    private String someText;

    public String getSomeText() {
        return someText;

    }


}

