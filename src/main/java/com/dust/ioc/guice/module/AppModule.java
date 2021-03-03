package com.dust.ioc.guice.module;

import com.dust.ioc.services.DrawShape;
import com.dust.ioc.services.impl.DrawCircle;
import com.google.inject.AbstractModule;

/**
 * Guice config
 * 
 * @author Aaron
 *
 */
public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DrawShape.class).to(DrawCircle.class);
    }
}
