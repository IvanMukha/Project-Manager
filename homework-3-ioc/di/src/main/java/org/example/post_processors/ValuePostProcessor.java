package org.example.post_processors;

import org.example.Context;
import org.example.annotations.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ValuePostProcessor implements PostProcessor {

    @Override
    public void process(Object object, Context context) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                Value annotation = field.getAnnotation(Value.class);
                String key = annotation.value().substring(2, annotation.value().length() - 1);
                if (!key.isEmpty()) {
                    field.setAccessible(true);
                    field.set(object, getValueFromProperties(key));
                    field.setAccessible(false);
                }
            }
        }
    }

    private static final String PROPERTIES_FILE_PATH = "application.properties";

    private static String getValueFromProperties(String key) {
        Properties properties = new Properties();
        try (InputStream input = ValuePostProcessor.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
            if (input != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                properties.load(reader);
                return properties.getProperty(key);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
