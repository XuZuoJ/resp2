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
 * @Description �����Customer��Ĳ�ѯ����
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
		Customer customer2 = testquery2(sql2, "�ܽ���");
		System.out.println(customer2);
		
	}

	@Test
	public void testQuery1() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		// ���ؽ����
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from customers where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, 1);
			resultSet = ps.executeQuery();
			// ��������
			if (resultSet.next()) {
				// next()���������� �жϽ������һ���Ƿ�������

				// ��ȡ��ǰ�������ݵĸ����ֶ�ֵ
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);

				// ��ʽ1
				// System.out.println(id+name+email+birth);

				// ��ʽ2
				// Object[] date =new Object[] {id,name,email,birth};

				// �����ݷ�װ��һ������
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		JDBCUtils.closeResource(conn, ps, resultSet);

	}

	// ͨ�ò�ѯ
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

			// ��ȡ�������Ԫ����
			ResultSetMetaData rsmd = rs.getMetaData();

			// ͨ��resultSetMetaData ��ȡ����
			int columnCount = rsmd.getColumnCount();

			if (rs.next()) {

				Customer cust = new Customer();

				// ��������һ�������е�ÿһ����
				for (int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					// ��ȡÿ���е�����
					String columnName = rsmd.getColumnName(i+1);

					// ��cust�����columnName���Ը�ֵcolumnValue��ͨ������
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
