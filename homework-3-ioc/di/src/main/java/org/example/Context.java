package org.example;

import org.example.annotations.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


import org.example.post_processors.AutowirePostProcessor;
import org.example.post_processors.ValuePostProcessor;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Context {

    private final static Context instance=new Context();
    private final Map<Class<?>,Object> beans= new ConcurrentHashMap<>();
    private final Map<Class<?>, List<Class<?>>> interface2implementations=new ConcurrentHashMap<>();
    private static final Logger log= LoggerFactory.getLogger(Context.class);

    private final BeanFactory beanFactory= new BeanFactory(this);

public static Context getInstance(){
    return instance;
}
    public void initContext(String packageToScan){
        Set<Class<?>> components = scanComponents(packageToScan);
        fillInterfaces2implementations(components);
        createInstances(components);
            applyPostProcessors();
    }


    private void applyPostProcessors() {
        AutowirePostProcessor autowirePostProcessor = new AutowirePostProcessor();
        ValuePostProcessor valuePostProcessor = new ValuePostProcessor();

        for (Object component : getInstance().beans.values()) {
            try {
                try {
                    autowirePostProcessor.process(component, getInstance());
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

    private void fillInterfaces2implementations(Set<Class<?>> components){
        components.forEach(impl-> Arrays.stream(impl.getInterfaces()).forEach(intfc->interface2implementations.computeIfAbsent(intfc,key->new ArrayList<>()).add(impl)));
    }

    private void createInstances(Set<Class<?>> components){
        components.forEach(component->beans.put(component,beanFactory.createObject(component)));
    }

    public static Set<Class<?>> scanComponents(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    public <T> T getObject(Class<T> clazz){
        if(clazz.isInterface()){
            final List<Class<?>> implementations =interface2implementations.get(clazz);
        if(implementations.size()>1) {
            throw new UnsupportedOperationException("Several implementation of interface");
        }
        clazz=(Class<T>) implementations.getFirst();
        }
        if(beans.containsKey(clazz)){
            return (T) beans.get(clazz);
        }
        return beanFactory.createObject(clazz);
        }



    }





