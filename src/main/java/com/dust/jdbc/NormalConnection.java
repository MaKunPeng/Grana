package com.dust.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class NormalConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/consult?autoReconnect=true&failOverReadOnly=false&useUnicode=true&allowMultiQueries=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai";
	public <T> T query(Class<T> clazz) throws SQLException {
		Connection connection = null;
		PreparedStatement stmt = null;
        ResultSet rs = null;

		try {
			connection = DriverManager.getConnection(URL, "root", "root");
			stmt = connection.prepareStatement("select * from user where user_name = ?");
			stmt.setString(1, "admin");
			rs = stmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("user_name") + " " + rs.getDate("birthday") + " " + rs.getDate("created_time"));
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} finally {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
		}
		return null;
	}
	
public void testDataSource() throws SQLException {
	// 创建数据源
	MysqlDataSource ds = new MysqlDataSource();
	// 设置数据源属性
	ds.setServerName("localhost");
	ds.setPort(3306);
	ds.setDatabaseName("consult");
	ds.setUser("root");
	ds.setPassword("root");
	ds.setServerTimezone("Asia/Shanghai");
	ds.setCharacterEncoding("utf8");
	// 获取连接并操作
	Connection connection = null;
	PreparedStatement stmt = null;
    ResultSet rs = null;
	try {
		connection = ds.getConnection();
		stmt = connection.prepareStatement("select * from user where user_name = ?");
		stmt.setString(1, "admin");
		rs = stmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString("user_name") + " " + rs.getDate("birthday") + " " + rs.getDate("created_time"));
		}
	} finally {
		// 省略关闭代码
	}
}
	
	public static void main(String[] args) throws SQLException {
		NormalConnection normalConnection = new NormalConnection();
		normalConnection.testDataSource();
	}
}
