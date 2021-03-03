package com.dust.proxy.test;

import java.lang.reflect.InvocationTargetException;

import com.dust.proxy.Interceptor;
import com.dust.proxy.Invocation;

public class MyInterceptor implements Interceptor {

	@Override
	public boolean before() {
		System.out.println("before");
		return true;
	}

	@Override
	public boolean after() {
		System.out.println("after");
		return true;
	}

	@Override
	public Object around(Invocation invocation)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("around");
		return invocation.proceed();
	}

	@Override
	public void afterReturning() {
		System.out.println("after return");
	}

	@Override
	public void afterThrowing() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean useAround() {
		return true;
	}

}
