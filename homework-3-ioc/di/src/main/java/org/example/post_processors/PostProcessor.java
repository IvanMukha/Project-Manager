package org.example.post_processors;

import org.example.Context;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface PostProcessor {
    void process(Object t, Context context) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
