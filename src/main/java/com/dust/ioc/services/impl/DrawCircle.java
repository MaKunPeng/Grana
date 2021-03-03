package com.dust.ioc.services.impl;

import com.dust.ioc.services.DrawShape;

public class DrawCircle implements DrawShape {

    @Override
    public void draw() {
        System.out.println("Draw a circle.");
    }
}
