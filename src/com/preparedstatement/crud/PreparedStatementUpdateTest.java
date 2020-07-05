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
	//向表中添加一条记录
	@Test
	public void testInsert() {
		//读取配置文件中的4个基本信息
		//获取连接
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
			//加载驱动
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			//预编译时语句，返回ps实例
			String sql="insert into customers(name,email,birth)values(?,?,?)";
			ps = conn.prepareStatement(sql);
			//填充占位符
			ps.setString(1, "UZI");
			ps.setString(2, "UZI@163.com");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse("2020-6-21");
			
			ps.setDate(3,  new java.sql.Date(date.getTime()));
			//执行SQL操作
			ps.execute();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			//资源的关闭
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
	//修改表中的一条记录
	@Test
	public void testUpdate()  {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//获取数据库的连接
			conn = JDBCUtils.getConnection();
			
			//预编译sql语句，返回PrepareStatement语句
			String sql="update customers set name=? where id= ?";
			ps = conn.prepareStatement(sql);
			
			//填充占位符
			ps.setObject(1, "莫扎特");
			ps.setObject(2, 18);
			
			//执行
			ps.execute();
		}  catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//资源的关闭
		JDBCUtils.closeResource(conn, ps);
	}
	//通用的增删改
	public void update(String sql,Object...args)  {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			//填充占位符
			for(int i=0;i<args.length;i++) {
				ps.setObject(i+1,args[i]);
			}
			ps.execute();
			/**
			 * ps.execute()
			 * 如果执行的是查询操作，有返回结果，则返回的是true;
			 * 如果执行的是增删改查，没有返回结果，返回的是false；
			 */
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		JDBCUtils.closeResource(conn, ps);
	}
	
	//通用增删改测试
	@Test
	public void testCommonUpdate() {
		String sql="delete from customers where id = ?";
		update(sql,3);
		
		String sql2="update `order` set order_name=? where order_id=?";
		update(sql2,"DD","2");
		
	}

}

