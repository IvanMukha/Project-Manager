package org.example.post_processors;

import org.example.Context;
import org.example.annotations.Autowired;

import java.lang.reflect.Field;

public class AutowiredPostProcessor implements PostProcessor{
    @Override
    public void process(Object t, Context context) throws IllegalAccessException {
        for(Field field:t.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(Autowired.class)){
                field.setAccessible(true);
                Object object=context.getObject(field.getType());
                field.set(t,object);
                field.setAccessible(false);
            }
        }
    }
}
