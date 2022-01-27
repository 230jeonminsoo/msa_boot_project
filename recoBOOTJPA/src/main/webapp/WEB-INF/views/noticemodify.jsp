<%@page import="com.my.notice.vo.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% System.out.println("in noticemodify.jsp -- 0"); %>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
%>
<link href="./css/noticewrite.css" rel=stylesheet>
<script src="./js/noticemodify.js"></script> 
<title>noticemodify.jsp</title>
<%String ntcIdx= request.getParameter("ntcIdx");
String ntcTitle= request.getParameter("ntcTitle");
String ntcContent= request.getParameter("ntcContent");
String ntcAttachment= request.getParameter("ntcAttachment");
%>
<script>
$(function(){
	
	let $formObj = $('fieldset>form');
	let $modifyNoticeBt = $('fieldset>form>input[type=submit]');
	//글수정버튼클릭시 수정된글 보낸후 수정한 글 다시 보기
	modifyNoticeSubmit($formObj);
	//수정취소버튼 클릭시 수정할려고 한글 다시보기
	modifyCancelBtClick();
});
</script>


<fieldset>
	<form method="post" action="./ntcmodify" autocomplete="off">
		<h1>공지사항 수정</h1>
		<table>
			<tr><td>날짜</td> <td><%= sf.format(nowTime)%></td></tr>
		</table>
		글번호: <input type ="text" id="ntcIdx" name="ntcIdx" value="<%=ntcIdx %>" readonly>   
		<br>
		<textarea rows="2" cols="100" style="resize:none;" name="ntcTitle" id="ntcTitle" placeholder="<%=ntcTitle %>" required><%=ntcTitle %></textarea>              
		<table>
			<tr><td><textarea rows="20" cols="100" style="resize:none;" name="ntcContent" id="ntcContent" placeholder="<%=ntcContent %>" required><%=ntcContent %></textarea></td></tr>		
		</table>
		<!--  <input type="file"  name="ntcattachment"   multiple><br>-->
		<button class="modifycancel">수정취소</button>
		<input type="submit" value="글 수정">
	</form>
</fieldset>
