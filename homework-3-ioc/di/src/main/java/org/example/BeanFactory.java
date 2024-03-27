package org.example;

import org.example.post_processors.PostProcessor;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanFactory    {
    private final Context context=Context.getInstance();
    private Set<PostProcessor> postProcessors;
    private static final BeanFactory instance=new BeanFactory();
public static BeanFactory getInstance(){
    return instance;
}
    public BeanFactory(){
        postProcessors = scanPostProcessors("org.example");

    }






public <T> T createObject(Class<T> clazz) {
    try {
        final T definition = create(clazz);
        postProcessors.forEach(processor -> {
            try{
                processor.process(definition,context);
            }catch (IllegalAccessException e){
                throw new RuntimeException();
            }
        });
        return definition;
        }catch (Exception e){
        e.printStackTrace();
    }
    return null;
}
private <T> T create(Class<T>clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Constructor<?>constructor =clazz.getDeclaredConstructor();
    List<Object> args= Arrays.stream(constructor.getParameterTypes())
            .map(context::getObject).collect(Collectors.toList());
    return (T) constructor.newInstance(args.toArray());
    }

    public static Set<PostProcessor> scanPostProcessors(String packageName) {
        Set<PostProcessor> postProcessors = new HashSet<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends PostProcessor>> processorClasses = reflections.getSubTypesOf(PostProcessor.class);

        for (Class<? extends PostProcessor> clazz : processorClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                try {
                    PostProcessor processor = clazz.getDeclaredConstructor().newInstance();
                    postProcessors.add(processor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return postProcessors;
    }
}
