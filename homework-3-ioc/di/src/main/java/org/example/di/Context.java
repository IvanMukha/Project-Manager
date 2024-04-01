package org.example.di;

import org.example.di.annotations.Component;
import org.example.di.post_processors.AutowiredPostProcessor;
import org.example.di.post_processors.ValuePostProcessor;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.ArrayList;

public class Context {

    private final static Context instance = new Context();
    private static final Logger log = LoggerFactory.getLogger(Context.class);
    private final Map<Class<?>, Object> beans = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<Class<?>>> interface2implementations = new ConcurrentHashMap<>();
    private final BeanFactory beanFactory = new BeanFactory(this);

    public static Context getInstance() {
        return instance;
    }

    public static Set<Class<?>> scanComponents(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    public void initContext(String packageToScan) {
        Set<Class<?>> components = scanComponents(packageToScan);
        fillInterfaces2implementations(components);
        createInstances(components);
        applyPostProcessors();
    }

    public <T> T getObject(Class<T> clazz) {
        if (clazz.isInterface()) {
            final List<Class<?>> implementations = interface2implementations.get(clazz);
            if (implementations.size() > 1) {
                throw new UnsupportedOperationException("Several implementation of interface");
            }
            clazz = (Class<T>) implementations.getFirst();
        }
        if (beans.containsKey(clazz)) {
            return (T) beans.get(clazz);
        }
        return beanFactory.createObject(clazz);
    }

    private void applyPostProcessors() {
        AutowiredPostProcessor autowiredPostProcessor = new AutowiredPostProcessor();
        ValuePostProcessor valuePostProcessor = new ValuePostProcessor();

        for (Object component : getInstance().beans.values()) {
            try {
                try {
                    autowiredPostProcessor.process(component, getInstance());
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                valuePostProcessor.process(component, getInstance());
            } catch (IllegalAccessException e) {
                log.error("Error during autowiring post processors", e);

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void fillInterfaces2implementations(Set<Class<?>> components) {
        components.forEach(impl -> Arrays.stream(impl.getInterfaces()).forEach(intfc -> interface2implementations.computeIfAbsent(intfc, key -> new ArrayList<>()).add(impl)));
    }

    private void createInstances(Set<Class<?>> components) {
        components.forEach(component -> beans.put(component, beanFactory.createObject(component)));
    }




}





