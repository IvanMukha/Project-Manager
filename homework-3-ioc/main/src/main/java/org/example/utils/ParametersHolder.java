package org.example.utils;

import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.annotations.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
@Component
public class ParametersHolder {
    @Autowired
    @Value("${my.param.db}")
    private  String someText;



    public String getSomeText() {
        return someText;

    }


}

