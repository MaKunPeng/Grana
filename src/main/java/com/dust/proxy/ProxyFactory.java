package com.dust.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler{
	private Object target;
	private Interceptor interceptor;
	
	public static Object getProxy(Object target, Interceptor interceptor) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.target = target;
		proxyFactory.interceptor = interceptor;
		
		Object proxyInstance = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), proxyFactory);
		return proxyInstance;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean exceptionFlag = false;
		Invocation invocation = new Invocation(target, method, args);
		Object retObj = null;
		
		this.interceptor.before();
		try {
			if (this.interceptor.useAround()) {
				retObj = this.interceptor.around(invocation);
			} else {
				retObj = method.invoke(target, args);
			}
		} catch (Exception ex) {
			exceptionFlag = true;
		}
		this.interceptor.after();
		
		if (exceptionFlag) {
			this.interceptor.afterThrowing();
		} else {
			this.interceptor.afterReturning();
			return retObj;
		}
		return null;
	}
	
}
