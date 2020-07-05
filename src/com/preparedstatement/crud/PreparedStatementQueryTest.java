package com.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bean.Customer;
import com.util.JDBCUtils;

public class PreparedStatementQueryTest {
	// 通用表查询———多条记录
	@Test
	public void testGetForList() {
		String sql ="select id,name,email from customers";
		List<Customer> list =getForList(Customer.class,sql);
//		for(Customer c:list) {
//			System.out.println(c);
//		}
		list.forEach(System.out::println);
	}
	
	
	public <T> List<T> getForList(Class<T> clazz,String sql,Object...args){
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
			//创建集合对象
			List<T> list=new ArrayList<T>();
			
			while(rs.next()) {
				T t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					// String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				list.add(t);
			}
			return list;
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
	
//....................................................................................
	// 通用表查询———单条记录
	@Test
	public void testGetInstance() {
		String sql = "select id,name,email from customers where id=?";
		Customer customer = getInstance(Customer.class, sql, 12);
		System.out.println(customer);
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
