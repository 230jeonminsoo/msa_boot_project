<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 현재 메뉴   
<span class="currentMenu"></span>
 -->
 
<ul>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>
    <li><img src="./images/beforelogin.png"></li>
    <li><a href="login.html" id="login">로그인</a></li>
    <li><a href="signup.html" id="signup">회원가입</a></li>
    <li><a href="login.html" id="community">커뮤니티</a></li>
<%
}else{
%>  <% if(c.getUAuthCode() == 1){
	%><li><%=c.getUNickName()%>님 반갑습니다.</li>
	<% 
	}else{
	%><li><%=c.getUNickName()%>(관리자)님 반갑습니다.</li>
	<%
	} 
	%>
<%
}
%> 
<%
if(c != null){
%>    
	<li><img src="./images/afterlogin.png"></li>
    <li><a href="logout" id="logout">로그아웃</a></li>
    <li><a href="pwdcheck">마이페이지</a></li>
    <li><a href="ntclist">커뮤니티</a></li>
<%
}
%>    
  
</ul>

