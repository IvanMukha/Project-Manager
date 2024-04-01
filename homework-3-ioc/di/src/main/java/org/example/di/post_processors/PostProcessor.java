package org.example.di.post_processors;

import org.example.di.Context;
import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface PostProcessor {
    void process(Object t, Context context) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
