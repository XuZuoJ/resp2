package com.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.bean.Customer;
import com.util.JDBCUtils;

public class BlobTest {
	@Test
	public void testInsert() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql="insert into customers(name,email,birth,photo)values(?,?,?,?)";
		System.out.println("hahaha");
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setObject(1, "哈哈");
		ps.setObject(2, "163@aqq");
		ps.setObject(3, "1992-09-08");
		FileInputStream is = new FileInputStream(new File("onepiece.jpg"));
		ps.setBlob(4, is);
		ps.execute();
		JDBCUtils.closeResource(conn, ps);
	}
	
	@Test
	public void testQuery() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream is=null;
		FileOutputStream fos=null;
		try {
			conn = JDBCUtils.getConnection();
			String sql="select id,name,email,birth,photo from customers where id=?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, 22);
			rs = ps.executeQuery();
			if(rs.next()) {
				int id=rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date birth=rs.getDate("birth");
				
				Customer cust =new Customer(id,name,email,birth);
				System.out.println(cust);
				Blob photo = rs.getBlob("photo");
				//将图片下载下来
				is = photo.getBinaryStream();
				fos = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\beijing.jpg"));
				byte[] bytes=new byte[1024];
				int len=0;
				while((len=is.read(bytes))!=-1) {
					fos.write(bytes, 0, len);
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				if(is!=null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(fos!=null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JDBCUtils.closeResource(conn, ps, rs);
		}
	}
}
