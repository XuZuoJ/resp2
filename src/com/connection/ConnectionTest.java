package com.connection;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class ConnectionTest {

	//方式一
	@Test
	public void testConnection1() throws SQLException {
		Driver driver = new com.mysql.jdbc.Driver();
		//jdbc:mysql ：协议
		String url ="jdbc:mysql://localhost:3306/test";
		//将用户名和密码封装在properties中
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "xz2356");

		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}
	
	//方式二 
	@Test
	public void testConnection2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver driver=(Driver)clazz.newInstance();
		
		//提欧共要连接的数据库
		String url="jdbc:mysql://localhost:3306/test";
		//将用户名和密码封装在properties中
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "xz2356");
		
		Connection conn=driver.connect(url, info);
		System.out.println(conn);
	}
	
	//方式三
	@Test
	public void testConnection3() throws Exception {
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver driver=(Driver)clazz.newInstance();
		//注册驱动
		DriverManager.registerDriver(driver);
		//获取连接
		String url="jdbc:mysql://localhost:3306/test";
		String user="root";
		String password="xz2356";
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
		
	}

	//方式四
	@Test
	public void testConnection4() throws Exception {
		String url="jdbc:mysql://localhost:3306/test";
		String user="root";
		String password="xz2356";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn= DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	
	//最终版 将数据库连接需要的基本信息写在配置信息中  通过读取配置文件的方式获取连接
	@Test
	public void getConnection5() throws Exception {
		//读取配置文件中的基本信息
		InputStream is=ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		pros.load(is);
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		
		Class.forName(driverClass);
		Connection conn= DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
}
