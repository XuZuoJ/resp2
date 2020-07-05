package com.preparedstatement.crud;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import com.util.JDBCUtils;

public class PreparedStatementUpdateTest {
	//��������һ����¼
	@Test
	public void testInsert() {
		//��ȡ�����ļ��е�4��������Ϣ
		//��ȡ����
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
			Properties pros = new Properties();
			pros.load(is);
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			String url = pros.getProperty("url");
			String driverClass = pros.getProperty("driverClass");
			//��������
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			//Ԥ����ʱ��䣬����psʵ��
			String sql="insert into customers(name,email,birth)values(?,?,?)";
			ps = conn.prepareStatement(sql);
			//���ռλ��
			ps.setString(1, "UZI");
			ps.setString(2, "UZI@163.com");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse("2020-6-21");
			
			ps.setDate(3,  new java.sql.Date(date.getTime()));
			//ִ��SQL����
			ps.execute();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			//��Դ�Ĺر�
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
		
	}
	//�޸ı��е�һ����¼
	@Test
	public void testUpdate()  {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//��ȡ���ݿ������
			conn = JDBCUtils.getConnection();
			
			//Ԥ����sql��䣬����PrepareStatement���
			String sql="update customers set name=? where id= ?";
			ps = conn.prepareStatement(sql);
			
			//���ռλ��
			ps.setObject(1, "Ī����");
			ps.setObject(2, 18);
			
			//ִ��
			ps.execute();
		}  catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//��Դ�Ĺر�
		JDBCUtils.closeResource(conn, ps);
	}
	//ͨ�õ���ɾ��
	public void update(String sql,Object...args)  {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			//���ռλ��
			for(int i=0;i<args.length;i++) {
				ps.setObject(i+1,args[i]);
			}
			ps.execute();
			/**
			 * ps.execute()
			 * ���ִ�е��ǲ�ѯ�������з��ؽ�����򷵻ص���true;
			 * ���ִ�е�����ɾ�Ĳ飬û�з��ؽ�������ص���false��
			 */
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		JDBCUtils.closeResource(conn, ps);
	}
	
	//ͨ����ɾ�Ĳ���
	@Test
	public void testCommonUpdate() {
		String sql="delete from customers where id = ?";
		update(sql,3);
		
		String sql2="update `order` set order_name=? where order_id=?";
		update(sql2,"DD","2");
		
	}

}

