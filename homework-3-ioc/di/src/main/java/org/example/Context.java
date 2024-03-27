package org.example;

import org.example.annotations.Component;

import java.util.*;

import org.example.post_processors.AutowiredPostProcessor;
import org.example.post_processors.ValuePostProcessor;
import org.reflections.Reflections;
public class Context {

    private final static Context instance=new Context();
    private final Map<Class<?>,Object> beans= new HashMap<>();
    private final Map<Class<?>, List<Class<?>>> interface2implementations=new HashMap<>();

private final BeanFactory beanFactory=BeanFactory.getInstance();
public static Context getInstance(){
    return instance;
}
    public void initContext(){
        Set<Class<?>> components = scanComponents("org.example");
        createInstances(components);
            applyPostProcessors();
    }


    private static void applyPostProcessors() {
        AutowiredPostProcessor autowiredPostProcessor = new AutowiredPostProcessor();
        ValuePostProcessor valuePostProcessor = new ValuePostProcessor();

        for (Object component : getInstance().beans.values()) {
            try {
                autowiredPostProcessor.process(component, getInstance());
                valuePostProcessor.process(component, getInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
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





