package com.util;
/**
 * @Description 操作数据的文档注释
 * @author Zuo
 *
 */
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;




public class JDBCUtils {
/**
 * @Description 获取数据库连接
 * @return
 * @throws Exception
 */
	public static Connection getConnection() throws Exception {
		InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		pros.load(is);
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		
		Class.forName(driverClass);
		Connection conn= DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	public static void closeResource(Connection conn, Statement ps ) {
		try {
			if (ps!=null) {
				ps.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			if (conn !=null) {
				conn.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public static void closeResource(Connection conn,Statement ps ,ResultSet rs) {
		try {
			if (ps!=null) {
				ps.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			if (conn !=null) {
				conn.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			if (rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
