package com.dust.loader;

public class CustomClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        
        return super.findClass(name);
    }
}
