package com.mytest;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import org.junit.Test;

import com.util.JDBCUtils;

public class Test2 {
	//向examstudent表中添加一条记录
	@Test
	public void testInsert() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("四级/六级:");
		int type = scanner.nextInt();
		System.out.print("身份证号:");
		String IdCard = scanner.next();
		System.out.print("准考证号:");
		String  examCard= scanner.next();
		System.out.print("学生姓名:");
		String  studentName= scanner.next();
		System.out.print("所在城市:");
		String  location= scanner.next();
		System.out.print("考试成绩:");
		int grade = scanner.nextInt();
		
		String sql="insert into examstudent(type,IDcard,examCard,studentName,location,grade)values(?,?,?,?,?,?)";
		
		int count = update(sql,type,IdCard,examCard,studentName,location,grade);
		if(count>0)
			System.out.println("添加成功");
		else
			System.out.println("添加失败");
	}
//.........................................................................
	//问题2:根据身份证号或者准考证号查询学生成绩信息
	@Test
	public void queryWithIDCardOrExamCard() {
		System.out.println("请选择您要输入的类型:");
		System.out.println("a.准考证号");
		System.out.println("b.身份证号");
		Scanner scanner = new Scanner(System.in);
		String selection = scanner.next();
		if("a".equalsIgnoreCase(selection)) {
			System.out.println("请输入准考证号:");
			String examCard = scanner.next();
			String sql="select flowid flowID,type type,idcard IDCard,examCard examCard,StudentName name,location location,grade grade from examstudent where examCard=?";
			Student student = getInstance(Student.class, sql, examCard);
			System.out.println(student);
		}else if("b".equalsIgnoreCase(selection)){
			System.out.println("请输入身份证号:");
			String IDCard = scanner.next();
			String sql="select flowid flowID,type type,idcard IDCard,examCard examCard,StudentName name,location location,grade grade from examstudent where examCard=?";
			Student student = getInstance(Student.class, sql, IDCard);
			System.out.println(student);
		}else {
			System.out.println("你的输入有误，请重新进入程序");
		}
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
