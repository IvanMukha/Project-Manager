package org.example.post_processors;

import org.example.Context;
import org.example.annotations.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class ValuePostProcessor implements PostProcessor {

    @Override
    public void process(Object object, Context context) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                Value annotation = field.getAnnotation(Value.class);
                String value = annotation.value();
                if (!value.isEmpty()) {
                    field.setAccessible(true);
                    field.set(object, getValueFromProperties(value));
                    field.setAccessible(false);
                }
            }
        }
    }

    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";


    private static String getValueFromProperties(String key) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE_PATH));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
