<%@page import="com.hk.dtos.HkDto"%>
<%@page import="java.util.List"%>
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
<script type="text/javascript">
	//전체선택체크박스의 체크여부를 구해서 나머지 체크박스에 체크상태에 적용
	//checked 속성: 체크박스의 체크여부를 나타냄--> true이면 체크, false이면 체크풀림
	function allchk(bool){// bool변수는 전체체크박스의 체크상태
		//나머지 체크박스객체 구하기
		var chks=document.getElementsByName("chk");//[input,input,input...]
		for (var i = 0; i < chks.length; i++) {
			chks[i].checked=bool;
		}
	}
</script>
</head>
<body>
<%//scriptlet java 실행코드작성영역 %>
<%!//java 메서드 선언 영역 %>
<%-- <%=java결과값 출력-->페이지에 %> --%>
<% 
	List<HkDto> list=(List<HkDto>)request.getAttribute("list");// Object타입에 저장되도록 설계
%>
<h1>글목록</h1>
<form action="HkController.do" method="post">
<input type="hidden" name="command" value="muldel"/>
<table border="1">
    <col width="50px"/>
	<col width="50px"/>
	<col width="100px"/>
	<col width="300px"/>
	<col width="100px"/>
	<tr>
		<th><input type="checkbox" name="all" onclick="allchk(this.checked)" /> </th>
		<th>번호</th>
		<th>작성자</th>
		<th>제목</th>
		<th>작성일</th>
	</tr>
	<%
		if(list.size()==0){
			%>
			<tr>
				<td colspan="4">---작성된 글이 없습니다.---</td>
			</tr>
			<%
		}else{
			for(int i=0;i<list.size();i++){
				HkDto dto=list.get(i);//list[dto,dto,dto]
				%>
				<tr>
				  	<td><input type="checkbox" name="chk" value="<%=dto.getSeq()%>" /></td>
					<td><%=dto.getSeq()%></td>
					<td><%=dto.getId()%></td>
					<td><a href="HkController.do?command=detail&seq=<%=dto.getSeq()%>"><%=dto.getTitle()%></a></td>
					<td><%=dto.getRegdate()%></td>
				</tr>
				<%
			}
		}
	%>
	<tr>
		<td colspan="5">
			<a href="HkController.do?command=insertform">글쓰기</a>
			<input type="submit" value="글삭제"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>










