package org.example.post_processors;

import org.example.Context;
import org.example.annotations.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AutowiredPostProcessor implements PostProcessor{
    @Override
    public void process(Object object, Context context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        processFields(object, context);
        processConstructors(object, context);
        processSetters(object, context);
    }

    private void processFields(Object object, Context context) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object dependency = context.getObject(field.getType());
                field.set(object, dependency);
                field.setAccessible(false);
            }
        }
    }

    private void processConstructors(Object object, Context context) {
        for (Constructor<?> constructor : object.getClass().getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] dependencies = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    dependencies[i] = context.getObject(parameterTypes[i]);
                }
                try {
                    constructor.setAccessible(true);
                    object = constructor.newInstance(dependencies);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processSetters(Object object, Context context) throws IllegalAccessException, InvocationTargetException {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    method.setAccessible(true);
                    Object dependency = context.getObject(parameterTypes[0]);
                    method.invoke(object, dependency);
                    method.setAccessible(false);
                }
            }
        }
    }
}
