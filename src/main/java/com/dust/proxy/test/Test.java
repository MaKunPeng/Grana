package com.dust.proxy.test;

import com.dust.proxy.ProxyFactory;

public class Test {
	public static void main(String[] args) {
		HelloService helloService = new HelloServiceImpl();
		HelloService hs = (HelloService) ProxyFactory.getProxy(helloService, new MyInterceptor());
		hs.hello();
	}
}
