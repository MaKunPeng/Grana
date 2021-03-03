package com.dust.proxy;

import java.lang.reflect.InvocationTargetException;

/**
 * 拦截器接口
 * @author mkp
 *
 */
public interface Interceptor {
	public boolean before();
	
	public boolean after();
	
	public Object around(Invocation invocation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public void afterReturning();
	
	public void afterThrowing();
	
	public boolean useAround();
}
