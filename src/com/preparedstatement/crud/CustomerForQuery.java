package com.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import com.bean.Customer;
import com.util.JDBCUtils;

/**
 * @Description 针对于Customer表的查询操作
 * @author Zuo
 *
 */
public class CustomerForQuery {

	@Test
	public void testQueryForCustomers() {
		String sql = "select id,name,birth,email from customers where id =?";
		Customer customer1 = testquery2(sql, 13);
		System.out.println(customer1);
		String sql2="select id from customers where name=?";
		Customer customer2 = testquery2(sql2, "周杰伦");
		System.out.println(customer2);
		
	}

	@Test
	public void testQuery1() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		// 返回结果集
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from customers where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, 1);
			resultSet = ps.executeQuery();
			// 处理结果集
			if (resultSet.next()) {
				// next()方法的作用 判断结果集下一条是否有数据

				// 获取当前这条数据的各个字段值
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);

				// 方式1
				// System.out.println(id+name+email+birth);

				// 方式2
				// Object[] date =new Object[] {id,name,email,birth};

				// 将数据封装成一个对象
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		JDBCUtils.closeResource(conn, ps, resultSet);

	}

	// 通用查询
	public Customer testquery2(String sql, Object... args) {
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

			// 获取结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();

			// 通过resultSetMetaData 获取列数
			int columnCount = rsmd.getColumnCount();

			if (rs.next()) {

				Customer cust = new Customer();

				// 处理结果集一行数据中的每一个列
				for (int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					// 获取每个列的列名
					String columnName = rsmd.getColumnName(i+1);

					// 给cust对象的columnName属性赋值columnValue：通过反射
					Field field = Customer.class.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(cust, columnValue);
				}
				return cust;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
}
