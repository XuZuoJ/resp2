package com.mytest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import org.junit.Test;

import com.util.JDBCUtils;

public class Test1 {
	@Test
	public void insertDate() {
		Scanner scanner=new Scanner(System.in);
		System.out.print("�������û�����");
		String name = scanner.next();
		System.out.print("������email��");
		String email = scanner.next();
		System.out.print("���������գ�");
		String birth = scanner.next();
		
		
		String sql="insert into customers(name,email,birth)values(?,?,?)";
		int count = update(sql,name,email,birth);
		if(count>0)
			System.out.println("success");
		else
			System.out.println("fail");
		
		
	}
	
	
	
	
	public int update(String sql,Object...args)  {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			//���ռλ��
			for(int i=0;i<args.length;i++) {
				ps.setObject(i+1,args[i]);
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps);			
		}
		return 0;
	}
}
