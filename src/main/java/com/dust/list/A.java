package com.dust.list;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

class A {
    private Object param;
    
    public void print() {
        System.out.println(this.param);
    }
    
    public static void main() { 
//        MethodType methodType = MethodType.methodType(Void.class, Void.class);
//        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(A.class, "print", methodType);
//        methodHandle.invoke();
    	Map<String, String> map = new HashMap<>();
    	System.out.println(map.get("xx"));
    }
}