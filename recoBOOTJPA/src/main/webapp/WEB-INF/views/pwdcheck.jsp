<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
Customer c = (Customer)session.getAttribute("loginInfo");
String pwd = null;
if(c!=null){
pwd = c.getUPwd();
}
%>

<link rel="stylesheet" href="./css/tab.css">
<script src="./js/pwdcheck.js"></script>



<script>
	$(function(){
		pwd = "<%=pwd%>";
		checkBtClick(pwd);		
		cancelBtClick();
	});		
</script>

	<%
	if (session.getAttribute("loginInfo") != null) { 
	%>
		<div class="pwdcheck">
			<h1 class="info">비밀번호 확인</h1>  
			기존 비밀번호 확인 : <input type="password" name="pwd0"><br>
			<button class="pwdcheck" type="submit">확인</button>
			<button class="cancel" class="button_cancel">취소</button>
		</div>
	<%} else{%>
		<script>location.href="./";</script>
	<%} %><!--if (session.getAttribute("loginInfo") 끝  -->