package org.example;

import org.example.Context;
import org.example.controller.MyController;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Context context = Context.getInstance();
        context.initContext();
        MyController myController = context.getObject(MyController.class);
        myController.execute();
    }
}