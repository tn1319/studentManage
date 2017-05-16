package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
	private Scanner scan = new Scanner(System.in);
	int num, code;
	String name,value;
	
	
	public Student() {
		System.out.println("constructor");
		// TODO Auto-generated constructor stub
	}

	public void menu(){
		while(true){
			System.out.print("****************"+"\n"+
							 "   ����"+"\n"+
							 "****************"+"\n"+
							 "  1. �Է�"+"\n"+
							 "  2. �˻�"+"\n"+
							 "  3. ����"+"\n"+
							 "  4. ����"+"\n"+
							 "****************"+"\n"+
							 "  ��ȣ���� : "
					);
			
			num=scan.nextInt();
			
			switch(num){
			case 1: insertArticle(); break;
			case 2: selectArticle(); break;
			case 3: deleteArticle(); break;
			case 4: return;
			default: System.out.println("1~4 ������ ��ȣ�� �Է����ּ���");
			}
			
		}//while
	}
	
	//========================Ŀ�ؼ� �����ϴ� �޼ҵ�=============================
	public Connection getConnection(){
		
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "jsp", "itbank");
			System.out.println("���� ����");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	//========================Ŀ�ؼ� �����ϴ� �޼ҵ�=============================
	
	
	//========================������ ���� �޼ҵ�==================================
	public void insertArticle(){
		while(true){
			System.out.print("****************"+"\n"+
					 "  1. �л�"+"\n"+
					 "  2. ����"+"\n"+
					 "  3. ������"+"\n"+
					 "  4. �����޴�"+"\n"+
					 "****************"+"\n"+
					 "  ��ȣ���� : "
					);
			num=scan.nextInt();
			
			switch(num){
			case 1: stuInsert(); break;
			case 2: profInsert(); break;
			case 3: adminInsert(); break;
			case 4: return;
			default: System.out.println("1~4 ������ ��ȣ�� �Է����ּ���");
			}
		}//while
	}
	
	public void stuInsert(){
		System.out.print("�̸� �Է� : ");
		name=scan.next();
		System.out.print("�й� �Է� : ");
		value=scan.next();
		code=1;
		String sql="insert into student(name,value,code) values (?,?,?)";
		PreparedStatement pstmt=null;
		insertSql(sql,pstmt,name,value,code);
	}
	public void profInsert(){
		System.out.print("�̸� �Է� : ");
		name=scan.next();
		System.out.print("���� �Է� : ");
		value=scan.next();
		code=2;
		String sql="insert into student(name,value,code) values (?,?,?)";
		PreparedStatement pstmt=null;
		insertSql(sql,pstmt,name,value,code);
	}
	public void adminInsert(){
		System.out.print("�̸� �Է� : ");
		name=scan.next();
		System.out.print("�μ� �Է� : ");
		value=scan.next();
		code=3;
		String sql="insert into student(name,value,code) values (?,?,?)";
		PreparedStatement pstmt=null;
		insertSql(sql,pstmt,name,value,code);
	}
	
	public void insertSql(String sql, PreparedStatement pstmt, String name, String value,int code){
		Connection conn=this.getConnection();
		pstmt=null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.setInt(3, code);
			int su = pstmt.executeUpdate();
			
			System.out.println(su+"row(s) inserted");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(pstmt!=null) pstmt.close();
				if (conn!=null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//========================������ ���� �޼ҵ�==================================
	
	
	//========================�˻�(select)�� ���� �޼ҵ�===========================
	public void selectArticle(){
		while(true){
			System.out.print("****************"+"\n"+
					 "  1. �̸� �˻� (1�� ���ڰ� ���Ե� �̸��� ��� �˻�)"+"\n"+
					 "  2. ��ü �˻�"+"\n"+
					 "  3. �����޴�"+"\n"+
					 "****************"+"\n"+
					 "  ��ȣ���� : "
					);
			String sql=null;
			num=scan.nextInt();
			Connection conn=this.getConnection();
			PreparedStatement pstmt=null;
			
			if(num==1){
				System.out.print("�˻��� �̸� �Է� : ");
				name=scan.next();
				sql="select * from student where name like ?";
			}
				
			else if(num==2){
				sql="select * from student";
			}
			else if(num==3)return;
			else {
				System.out.println("1~3 ������ ���ڸ� �Է����ּ���");
				continue;
			}
			ResultSet rs=null;
			try {
				pstmt = conn.prepareStatement(sql);	
				if(num==1)pstmt.setString(1, "%"+name+"%");
				rs=pstmt.executeQuery();//ResultSet ��ȯ
				
				while (rs.next()){
					System.out.print("�̸� = "+rs.getString("name")+"\t");
					if(rs.getInt("code")==1)
						System.out.print("�й� = ");
					if(rs.getInt("code")==2)
						System.out.print("���� = ");
					if(rs.getInt("code")==3)
						System.out.print("�μ� = ");
					System.out.print(rs.getString("value")+"\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if (conn!=null) conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//while
	}
	//========================�˻�(select)�� ���� �޼ҵ�===========================
	
	
	//========================������ ���� �޼ҵ�====================================
	public void deleteArticle(){
		System.out.print("�̸� �Է� (full name) : ");
		name=scan.next();
	
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		String sql="delete student where name=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			int su = pstmt.executeUpdate();
			if(su==0){
				System.out.println("�׸��� �������� �ʽ��ϴ�");
			}
			else System.out.println(su+"row(s) deleted");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(pstmt!=null) pstmt.close();
				if (conn!=null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//========================������ ���� �޼ҵ�====================================
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Student st=new Student();
		st.menu();
	}

}
