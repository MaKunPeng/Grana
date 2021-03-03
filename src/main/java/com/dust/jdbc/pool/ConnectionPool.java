package com.dust.jdbc.pool;

public class ConnectionPool {
	private static String driverClass;
	private static String username;
	private static String password;
	private static String url;
	private static int initalSize = 20;
	private static int maxSize = 2000;
	private static int currentSize;
	
	private static final ConnectionPool POOL = new ConnectionPool();
	
	private ConnectionPool() {
		
	}
	
	
}
