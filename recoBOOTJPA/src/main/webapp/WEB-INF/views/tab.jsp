<%@page import="com.reco.calendar.vo.CalInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="./css/tab.css">
<script src="./js/tab.js"></script>

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>

<%
}else if(c != null){
%>
<ul class="caltab">
<%	List<CalInfo> list = (List)request.getAttribute("list");
	
for(CalInfo ci : list){
%>   <li><a href="./calpostlist?calIdx=<%= ci.getCalIdx()%>"><%=ci.getCalCategory() %></a></li>
<%} //end for 
for(int i=list.size(); i<5; i++){
%>
	 <li><a href="./html/calInfowrite.html" id = "clickadd" >ADD+</a></li>
<%}//end for
%>
</ul> 
    <ul class="communitytab">
	    <li><a href="ntclist">공지사항</a></li>
		<li><a href="./html/faqlist.html">FAQ</a></li>
		<li><a href="brdlist">자유게시판</a></li>
	</ul>
	
	<ul class="myinfotab">
		<li><a href="mycallist">캘린더 관리</a></li>
		<li><a href="mycommunity">커뮤니티 글관리</a></li>
		<li><a href="myprivate">개인정보 관리</a></li>
	</ul>
<%
}
%>