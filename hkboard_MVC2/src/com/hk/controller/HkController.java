package com.hk.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.daos.HkDao;
import com.hk.dtos.HkDto;

@WebServlet("/HkController.do")
public class HkController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//controller에서는 요청내용을 확인해서 해당하는 다오기능을 실행하고 어디로 응답할건지 구현
		HkDao dao=new HkDao();
		
		String command=request.getParameter("command");
		
		if(command.equals("list")) {
			List<HkDto> list=dao.getAllList();
			//글목록을 구한 list객체를 boardlist.jsp로 전달해야 됨
			//Scope 개념: 객체전달범위 , 종류: application, session, request, page
			request.setAttribute("list", list);//스코프객체에 list객체를 저장
			//페이지 이동: RequestDispatcher객체가 실행해줌
			request.getRequestDispatcher("boardlist.jsp").forward(request, response);
			//자바에서 페이지 이동하는 방법2가지 
			//RequestDispatcher(객체전달가능), response.sendRedirect() (객체전달X)
		}else if(command.equals("detail")) {
			//글의 상세보기 기능 : 제목을 클릭한 내용을 구하는 기능 ,PK에 해당하는 seq값을 전달
			String seq=request.getParameter("seq");
			HkDto dto=dao.getBoard(Integer.parseInt(seq));
			request.setAttribute("dto", dto);
			dispatch("detailboard.jsp", request, response);
		}else if(command.equals("insertform")) {
			//페이지이동--> 객체를 담아서 이동X --> response.sendRedirect(url)
			response.sendRedirect("insertboard.jsp");
		}else if(command.equals("insert")) {
			String id=request.getParameter("id");
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			
			boolean isS=dao.insertBoard(new HkDto(id,title,content));
			if(isS) {
				response.sendRedirect("HkController.do?command=list");
			}else {
				request.setAttribute("msg", "글쓰기실패");
				dispatch("error.jsp", request, response);
			}
		}else if(command.equals("delboard")) {
			int seq=Integer.parseInt(request.getParameter("seq"));
			boolean isS=dao.delBoard(seq);
			if(isS) {
//				response.sendRedirect("HkController.do?command=list");
				jsForward("글삭제성공", "HkController.do?command=list", response);
			}else {
				request.setAttribute("msg", "글삭제실패");
				dispatch("error.jsp", request, response);
			}
		}else if(command.equals("muldel")) {
			String[] chks=request.getParameterValues("chk");//chk[3,4,5,6,7..]
			//mulDel(String[] chk)
			boolean isS=dao.mulDel(chks);
			
			if(isS) {
				jsForward("글을 삭제했습니다.", "HkController.do?command=list", response);
			}else {
				request.setAttribute("msg", "글여러개삭제 실패!!ㅜㅜ");
				dispatch("error.jsp", request, response);
			}
		}
	}

	//객체전달시에 페이지이동을 위한 메서드
	public void dispatch(String url,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch=request.getRequestDispatcher(url);
		dispatch.forward(request,response);
	}
	
	//자바스크립트를 실행해서 이동하는 메서드(PrintWriter 객체: java에서 브라우저로 text를 실행시키는 (출력)기능)
	public void jsForward(String msg,String url,HttpServletResponse response) throws IOException {
		String str=
				"<script type='text/javascript'>"+
				"alert('"+msg+"');"+
				"location.href='"+url+"';"+
				"</script>";
		PrintWriter pw=response.getWriter();
		pw.print(str);
	}
	
}








