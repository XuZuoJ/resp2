package com.statement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import org.junit.Test;

import com.util.JDBCUtils;

public class PreparedStatementTest {
	
	@Test
	public void testLogin() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入用户名：");
		String user = scanner.next();
		System.out.print("请输入密码：");
		String password = scanner.next();
		String sql="SELECT user,password FROM user_table WHERE user= ? AND password = ? " ;
		User returnUser=getInstance(User.class,sql,user,password);
		if(returnUser !=null)
			System.out.println("登录成功");
		else
			System.out.println("登录失败");
	}
	
	
	
	
	
	public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				T t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					// String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				return t;
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
}
