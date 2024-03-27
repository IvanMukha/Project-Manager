package org.example.post_processors;

import org.example.Context;
@FunctionalInterface
public interface PostProcessor {
    void process(Object t, Context context)throws IllegalAccessException;
}
