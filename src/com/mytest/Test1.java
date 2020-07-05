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
		System.out.print("请输入用户名：");
		String name = scanner.next();
		System.out.print("请输入email：");
		String email = scanner.next();
		System.out.print("请输入生日：");
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
			//填充占位符
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
