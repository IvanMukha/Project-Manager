package org.example.post_processors;

import org.example.Context;
import org.example.annotations.Autowire;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



public class AutowirePostProcessor implements PostProcessor {
    private static final Logger log= LoggerFactory.getLogger(AutowirePostProcessor.class);


    @Override
    public void process(Object object, Context context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        processFields(object, context);
        processConstructors(object, context);
        processSetters(object, context);
    }

    private void processFields(Object object, Context context) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowire.class)) {
                field.setAccessible(true);
                Object dependency = context.getObject(field.getType());
                field.set(object, dependency);
                field.setAccessible(false);
            }
        }
    }

    private void processConstructors(Object object, Context context) {
        for (Constructor<?> constructor : object.getClass().getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Autowire.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] dependencies = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    dependencies[i] = context.getObject(parameterTypes[i]);
                }
                try {
                    constructor.setAccessible(true);
                    object = constructor.newInstance(dependencies);
                } catch (Exception e) {
                    log.error("Error during autowiring constructor", e);
                }
            }
        }
    }

    private void processSetters(Object object, Context context) throws IllegalAccessException, InvocationTargetException {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Autowire.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length > 0) {
                    List<Object> dependencies = new ArrayList<>();
                    for (Class<?> parameterType : parameterTypes) {
                        Object dependency = context.getObject(parameterType);
                        dependencies.add(dependency);
                    }
                    method.setAccessible(true);
                    method.invoke(object, dependencies.toArray());
                    method.setAccessible(false);
                }
            }
        }
    }
}