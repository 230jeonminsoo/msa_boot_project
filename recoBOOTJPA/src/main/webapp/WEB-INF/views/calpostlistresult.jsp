<%@page import="com.my.calendar.vo.CalPost"%>
<%@page import="java.util.List"%>
<%@page import="com.my.customer.vo.Customer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>

<link rel=stylesheet href="./css/calendar.css" >
<script src="./js/calendar.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 

	List<CalPost> list = (List)request.getAttribute("list");
	
%>	
<div class="container">
	<div class="body"> 
		<div id="calendarForm"></div> 
	</div>
</div> 
	  
 <%	for(CalPost cp :list){ 
	/* int calIdx = cp.getCalinfo().getCalIdx(); */
	String calCategory = cp.getCalinfo().getCalCategory();
	String calMainImg = cp.getCalMainImg();
	
%>	
	
 <%    
    
 }%>