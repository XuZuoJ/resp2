package com.blob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.util.JDBCUtils;

//ʹ��PreparedStatementʵ���������ݵĲ���

public class InsertTest {
	
	//��ʽһ
	@Test
	public void testInsert1() {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = JDBCUtils.getConnection();
			String sql ="insert into goods(name) values(?)";
			ps = conn.prepareStatement(sql);
			for(int i=1;i<=20000;i++) {
				ps.setObject(1, "name_"+i);
				ps.execute();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, ps);
		}
	}
	
	
	//��ʽ��
	@Test
	public void testInsert2() {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = JDBCUtils.getConnection();
			String sql ="insert into goods(name) values(?)";
			ps = conn.prepareStatement(sql);
			for(int i=1;i<=1000000;i++) {
				ps.setObject(1, "name_"+i);
				ps.addBatch();
				if(i%500==0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, ps);
		}
	}
	
	//��ʽ3
	@Test
	public void testInsert3() {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = JDBCUtils.getConnection();
			//���ò������Զ��ύ
			conn.setAutoCommit(false);
			String sql ="insert into goods(name) values(?)";
			ps = conn.prepareStatement(sql);
			for(int i=1;i<=1000000;i++) {
				ps.setObject(1, "name_"+i);
				ps.addBatch();
				if(i%500==0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, ps); 
		}
	}
	
}
