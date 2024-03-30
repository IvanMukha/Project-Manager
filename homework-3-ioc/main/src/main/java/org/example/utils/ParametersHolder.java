package org.example.utils;

import org.example.annotations.Component;
import org.example.annotations.Value;

@Component
public class ParametersHolder {

    @Value("${my.param.db}")
    private  String someText;


    public String getSomeText() {
        return someText;

    }


}

