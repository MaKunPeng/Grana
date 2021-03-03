package com.dust.ioc.test;

import com.dust.ioc.guice.module.AppModule;
import com.dust.ioc.requests.DrawRequest;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceDemo {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        DrawRequest instance = injector.getInstance(DrawRequest.class);
        instance.request();
    }
}
