package com.connection;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class ConnectionTest {

	//��ʽһ
	@Test
	public void testConnection1() throws SQLException {
		Driver driver = new com.mysql.jdbc.Driver();
		//jdbc:mysql ��Э��
		String url ="jdbc:mysql://localhost:3306/test";
		//���û����������װ��properties��
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "xz2356");

		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}
	
	//��ʽ�� 
	@Test
	public void testConnection2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver driver=(Driver)clazz.newInstance();
		
		//��ŷ��Ҫ���ӵ����ݿ�
		String url="jdbc:mysql://localhost:3306/test";
		//���û����������װ��properties��
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "xz2356");
		
		Connection conn=driver.connect(url, info);
		System.out.println(conn);
	}
	
	//��ʽ��
	@Test
	public void testConnection3() throws Exception {
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver driver=(Driver)clazz.newInstance();
		//ע������
		DriverManager.registerDriver(driver);
		//��ȡ����
		String url="jdbc:mysql://localhost:3306/test";
		String user="root";
		String password="xz2356";
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
		
	}

	//��ʽ��
	@Test
	public void testConnection4() throws Exception {
		String url="jdbc:mysql://localhost:3306/test";
		String user="root";
		String password="xz2356";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn= DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	
	//���հ� �����ݿ�������Ҫ�Ļ�����Ϣд��������Ϣ��  ͨ����ȡ�����ļ��ķ�ʽ��ȡ����
	@Test
	public void getConnection5() throws Exception {
		//��ȡ�����ļ��еĻ�����Ϣ
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
