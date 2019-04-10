package com.hk.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

	//1단계
	public DataBase() {
		try {
			//ojdbc6.jar에 있는 OracleDriver클래스를 강제 객체생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("1단계:드라이버로딩성공");
		} catch (ClassNotFoundException e) {
			System.out.println("1단계:드라이버로딩실패:"+getClass());
			e.printStackTrace();
		}
	}
	
	//2단계
	public Connection getConnection() throws SQLException {
		Connection conn=null;
		//DB연결할때 필요한 정보 정의: DB계정 접속 정보
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="BOARD";
		String password="BOARD";
		conn=DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	public void close(Connection conn,PreparedStatement psmt,ResultSet rs) {
		try {
			if(rs!=null) {
				rs.close();				
			}
			if(psmt!=null) {
				psmt.close();				
			}
			if(conn!=null) {
				conn.close();				
			}
			System.out.println("6단계:DB닫기성공");
		} catch (SQLException e) {
			System.out.println("6단계:DB닫기실패");
			e.printStackTrace();
		}
	}
}









