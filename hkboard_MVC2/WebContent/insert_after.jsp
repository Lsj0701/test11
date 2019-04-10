<%@page import="com.hk.dtos.HkDto"%>
<%@page import="com.hk.daos.HkDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//파라미터 전달: name에 정의된 이름으로 값을 받는다!!  key:value
	String id=request.getParameter("id");
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	
	HkDao dao=new HkDao();
	boolean isS=dao.insertBoard(new HkDto(id,title,content));
	if(isS){
// 		response.sendRedirect("boardlist.jsp");
		%>
		<script type="text/javascript">
			//core(문법), bom(브라우저 객체:window,history,location,screen,navigator), dom(문서객체)
			alert("글추가성공입니다.");
			location.href="boardlist.jsp";
		</script>
		<%
	}else{
		%>
		<script type="text/javascript">
			//core(문법), bom(브라우저 객체:window,history,location,screen,navigator), dom(문서객체)
			alert("글추가실패입니다.");
			location.href="insertboard.jsp";
		</script>
		<%
	}
%>
</body>
</html>














