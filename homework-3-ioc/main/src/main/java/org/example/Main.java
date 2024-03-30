package org.example;

import org.example.Context;
import org.example.controller.MyController;
import org.example.post_processors.ValuePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Context context = Context.getInstance();
        String packageToScan=Main.class.getPackageName();
        context.initContext(packageToScan);
        MyController myController = context.getObject(MyController.class);
        myController.execute();

    }
}