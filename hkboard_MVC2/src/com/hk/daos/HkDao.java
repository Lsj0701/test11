package com.hk.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hk.db.DataBase;
import com.hk.dtos.HkDto;

public class HkDao extends DataBase{

	//1단계: 드라이버로딩
	public HkDao() {
		super();
	}
	
	//글목록조회:select문---> 여러 글(여러 row반환)---> List객체를 반환
	public List<HkDto> getAllList(){
		List<HkDto> list=new ArrayList<>();//책가방 미리 준비
		
		Connection conn=null;//DB연결 객체
		PreparedStatement psmt=null;//쿼리준비객체
		ResultSet rs=null;//결과받기 객체
		
		//쿼리준비를 위한 작업
		String sql=" SELECT SEQ,ID,TITLE,CONTENT,REGDATE "
				+ " FROM HKBOARD ORDER BY REGDATE DESC ";
		
		//3대 Exception --> 무조건 예외처리해라
		//1. java.sql, 2.java.net , 3.java.io
		try {
			conn=getConnection();
			System.out.println("2단계:DB연결성공");
			psmt=conn.prepareStatement(sql);//sqldeveloper에 쿼리를 작성한거와 같음
			System.out.println("3단계:쿼리준비성공");
			rs=psmt.executeQuery();//쿼리 실행시킴
			System.out.println("4단계:쿼리실행성공");
			//rs에 담은 결과값은 DB에 있는 값자체를 담았다(seq-> number, id-> varchar2)
			while(rs.next()) {
				HkDto dto=new HkDto();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setRegdate(rs.getDate(5));
				list.add(dto);
				System.out.println(dto);
			}
			System.out.println("5단계:쿼리결과받기");
		} catch (SQLException e) {
			System.out.println("jdbc실패:"+getClass()+":getAllList()");
			e.printStackTrace();
		}finally {
			close(conn, psmt, rs);
		}
		
		return list;
	}
	
	//글쓰기:insert문---> 쿼리가 실행했을때 테이블에 수정되는 row개수를 반환 int형
	//화면에서 입력받을 값---> id, title, content
	public boolean insertBoard(HkDto dto) {
		int count=0;//쿼리실행 결과를 받을 변수 0,1,2등등
		Connection conn=null;//DB연결 객체
		PreparedStatement psmt=null;//쿼리준비객체
		
	
		//쿼리준비를 위한 작업
		String sql=" INSERT INTO HKBOARD (SEQ,ID,TITLE,CONTENT,REGDATE) "
				+ " VALUES(HKBOARD_SEQ.NEXTVAL,?,?,?,SYSDATE) ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	//글상세조회:select문---> 글하나(row하나 반환)-->dto반환
	public HkDto getBoard(int seq) {
		HkDto dto=new HkDto();
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		
		//쿼리준비를 위한 작업
//		String sql=" SELECT SEQ,ID,TITLE,CONTENT1,REGDATE "
//				+ " FROM HKBOARD WHERE SEQ=? ";
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT SEQ,ID,TITLE,CONTENT1,REGDATE ");
		sb.append(" FROM HKBOARD WHERE SEQ=? ");
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sb.toString());
			psmt.setInt(1, seq);
			rs=psmt.executeQuery();
			while(rs.next()) {
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setRegdate(rs.getDate(5));
				System.out.println(dto);
			}
		} catch (SQLException e) {
			System.out.println("jdbc실패:"+getClass()+"getBoard()");
			e.printStackTrace();
		}finally {
			close(conn, psmt, rs);
		}
		
		return dto;
	}
	//글수정
	
	//글삭제
	public boolean delBoard(int seq) {
		int count=0;//쿼리실행 결과를 받을 변수 0,1,2등등
		Connection conn=null;//DB연결 객체
		PreparedStatement psmt=null;//쿼리준비객체
		
	
		//쿼리준비를 위한 작업
		String sql=" DELETE FROM HKBOARD WHERE SEQ=? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	//글여러개 삭제하기구현 
	public boolean mulDel(String[] chk) {
		boolean isS=true;
		int [] count=null;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" DELETE FROM HKBOARD WHERE SEQ=? ";
		
		try {
			conn=getConnection();
			conn.setAutoCommit(false);//수동커밋설정--> rollback가능하니깐
			psmt=conn.prepareStatement(sql);
			for (int i = 0; i < chk.length; i++) {
				psmt.setString(1, chk[i]);
				psmt.addBatch();//batch개념:동일한 작업을 여러번 실행할때 준비를 시켰다가 한번에 쭉 실행시킴
			}
			count=psmt.executeBatch();//[-2,-2,-2]
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close(conn, psmt, null);
			
			for (int i = 0; i < count.length; i++) {
				if(count[i]!=-2) {//-2가 아니면 쿼리실패임
					isS=false;
					break;
				}
			}
		}
		return isS;
	}
}
















