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
<title>글상세보기</title>

</head>
<body>
<%-- <%   --%>
<!-- 	HkDto dto=(HkDto)request.getAttribute("dto"); -->
<%-- %> --%>
<h1>글상세보기</h1>
<table border="1">
	<tr>
		<th>번호</th>
		<td>${dto.seq}</td>
	</tr>
	<tr>
		<th>아이디</th>
		<td>${dto.id}</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>${dto.title}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea rows="10" cols="60" readonly="readonly">${dto.content}</textarea></td>
	</tr>
	<tr>
		<td colspan="2">
			<button onclick="updateForm()">수정</button>
			<button onclick="delBoard()">삭제</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function updateForm(){
		//updateform.jsp ---> update_after.jsp(수정처리)--> detailboard.jsp
		location.href="updateBoard.jsp?seq=${dto.seq}";
	}
	function delBoard(){
		//delBoard.jsp: dao호출 delboard(seq)실행 
		//              --->성공 list.jsp--->실패 dedatil.jsp
		location.href="HkController.do?command=delboard&seq=${dto.seq}";
	}
</script>
</body>
</html>











