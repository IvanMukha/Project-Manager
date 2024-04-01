package org.example.application;

import org.example.application.controller.MyController;
import org.example.di.Context;

public class Application {

    public static void main(String[] args) {
        Context context = Context.getInstance();
        String packageToScan = Application.class.getPackageName();
        context.initContext(packageToScan);
        MyController myController = context.getObject(MyController.class);
        myController.execute();
    }
}