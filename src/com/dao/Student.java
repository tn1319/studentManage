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
							 "   관리"+"\n"+
							 "****************"+"\n"+
							 "  1. 입력"+"\n"+
							 "  2. 검색"+"\n"+
							 "  3. 삭제"+"\n"+
							 "  4. 종료"+"\n"+
							 "****************"+"\n"+
							 "  번호선택 : "
					);
			
			num=scan.nextInt();
			
			switch(num){
			case 1: insertArticle(); break;
			case 2: selectArticle(); break;
			case 3: deleteArticle(); break;
			case 4: return;
			default: System.out.println("1~4 사이의 번호를 입력해주세요");
			}
			
		}//while
	}
	
	//========================커넥션 생성하는 메소드=============================
	public Connection getConnection(){
		
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "jsp", "itbank");
			System.out.println("접속 성공");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	//========================커넥션 생성하는 메소드=============================
	
	
	//========================삽입을 위한 메소드==================================
	public void insertArticle(){
		while(true){
			System.out.print("****************"+"\n"+
					 "  1. 학생"+"\n"+
					 "  2. 교수"+"\n"+
					 "  3. 관리자"+"\n"+
					 "  4. 이전메뉴"+"\n"+
					 "****************"+"\n"+
					 "  번호선택 : "
					);
			num=scan.nextInt();
			
			switch(num){
			case 1: stuInsert(); break;
			case 2: profInsert(); break;
			case 3: adminInsert(); break;
			case 4: return;
			default: System.out.println("1~4 사이의 번호를 입력해주세요");
			}
		}//while
	}
	
	public void stuInsert(){
		System.out.print("이름 입력 : ");
		name=scan.next();
		System.out.print("학번 입력 : ");
		value=scan.next();
		code=1;
		String sql="insert into student(name,value,code) values (?,?,?)";
		PreparedStatement pstmt=null;
		insertSql(sql,pstmt,name,value,code);
	}
	public void profInsert(){
		System.out.print("이름 입력 : ");
		name=scan.next();
		System.out.print("과목 입력 : ");
		value=scan.next();
		code=2;
		String sql="insert into student(name,value,code) values (?,?,?)";
		PreparedStatement pstmt=null;
		insertSql(sql,pstmt,name,value,code);
	}
	public void adminInsert(){
		System.out.print("이름 입력 : ");
		name=scan.next();
		System.out.print("부서 입력 : ");
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
	//========================삽입을 위한 메소드==================================
	
	
	//========================검색(select)을 위한 메소드===========================
	public void selectArticle(){
		while(true){
			System.out.print("****************"+"\n"+
					 "  1. 이름 검색 (1개 글자가 포함된 이름은 모두 검색)"+"\n"+
					 "  2. 전체 검색"+"\n"+
					 "  3. 이전메뉴"+"\n"+
					 "****************"+"\n"+
					 "  번호선택 : "
					);
			String sql=null;
			num=scan.nextInt();
			Connection conn=this.getConnection();
			PreparedStatement pstmt=null;
			
			if(num==1){
				System.out.print("검색할 이름 입력 : ");
				name=scan.next();
				sql="select * from student where name like ?";
			}
				
			else if(num==2){
				sql="select * from student";
			}
			else if(num==3)return;
			else {
				System.out.println("1~3 사이의 숫자를 입력해주세요");
				continue;
			}
			ResultSet rs=null;
			try {
				pstmt = conn.prepareStatement(sql);	
				if(num==1)pstmt.setString(1, "%"+name+"%");
				rs=pstmt.executeQuery();//ResultSet 반환
				
				while (rs.next()){
					System.out.print("이름 = "+rs.getString("name")+"\t");
					if(rs.getInt("code")==1)
						System.out.print("학번 = ");
					if(rs.getInt("code")==2)
						System.out.print("과목 = ");
					if(rs.getInt("code")==3)
						System.out.print("부서 = ");
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
	//========================검색(select)을 위한 메소드===========================
	
	
	//========================삭제를 위한 메소드====================================
	public void deleteArticle(){
		System.out.print("이름 입력 (full name) : ");
		name=scan.next();
	
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		String sql="delete student where name=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			int su = pstmt.executeUpdate();
			if(su==0){
				System.out.println("항목이 존재하지 않습니다");
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
	//========================삭제를 위한 메소드====================================
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Student st=new Student();
		st.menu();
	}

}
