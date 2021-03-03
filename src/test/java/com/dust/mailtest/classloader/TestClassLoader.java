package com.dust.mailtest.classloader;

import java.util.ServiceLoader;

public class TestClassLoader {
	public static void main(String[] args) throws Exception {
//		ClassLoader classLoader = TestClassLoader.class.getClassLoader();
//		while (classLoader != null) {
//			System.out.println(classLoader);
//			classLoader = classLoader.getParent();
//		}
		
		MyClassLoader my = new MyClassLoader();
		Class<?> loadClass = my.loadClass("com.dust.mailtest.classloader.TestClassLoader");
		System.out.println(loadClass.getClassLoader());
		Thread.currentThread().setContextClassLoader(loadClass.getClassLoader());
		Object obj = loadClass.newInstance();
		System.out.println(obj);
//		while (classLoader3 != null) {
//			System.out.println(classLoader3);
//			classLoader3 = classLoader3.getParent();
//		}
		
//		System.out.println(obj instanceof TestClassLoader);
//		
//		Thread.currentThread().setContextClassLoader(my);
//		TestClassLoader t = new TestClassLoader();
//		System.out.println(t.getClass().getClassLoader());
	}
	
	public void printClassLoader() {
	    MyClassLoader ml = new MyClassLoader();
	    System.out.println("================" + ml.getClass().getClassLoader());
	}
}
