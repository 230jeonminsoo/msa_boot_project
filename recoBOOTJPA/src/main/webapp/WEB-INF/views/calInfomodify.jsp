<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.reco.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.reco.calendar.vo.CalInfo" %>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.List"%>
<% System.out.println("in calInfomodify.jsp -- 0"); %> 
 
<head>
    <meta charset="UTF-8"> <!--html에서 없으면 글씨 깨짐   -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>calInfowrite.html</title>
	<link href="./css/calInfomodify.css" rel=stylesheet>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="./js/calInfomodify.js"></script>
</head>

<script>
$(function(){		
	let $formObj = $('fieldset>form');
     
     /*--캘린더 수정 버튼이 클릭되었을때 START--*/
     calInfomodifyBtClick();
	/*--캘린더 수정 버튼이 클릭되었을때 END--*/
   });
</script>
	 

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
String calCategory = request.getParameter("calCategory");
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
int uIdx  = c.getUIdx();
/* String calIdx = request.getParameter("calIdx"); */
/* int calIdx = ci.getCalIdx();   */
int calIdx = Integer.parseInt(request.getParameter("calIdx"));

%>	            
<fieldset>
    <form  method="post" action="./calInfomodify" autocomplete="off" enctype="multipart/form-data">
        <input type="hidden" name="calIdx" value="<%=calIdx %>">
        <div class="Category" align="center">
        	<strong>캘린더이름&nbsp;:&nbsp;<%=calCategory %> </strong> <br>
        	<strong>calIdx값&nbsp;:&nbsp;<%=calIdx %> </strong>
        </div>
        
        <div class="input_image">
         	<strong>대표사진 수정(컴퓨터에서 불러오기)</strong><br>
         	<input type="file" name="calThumbnail" id="upload_file" accept="image/*" >
        	
        </div><br>
        
        <div class="input_text">
        	<strong>캘린더 이름 수정 </strong><br>
        	<input type="text" name="calCategory" id="input" placeholder="<%=calCategory %> : 캘린더 이름을 입력해주세요">
        </div><br>
        
		<div class="bt">
	        <button type="submit" >수정</button>
	        <button type="button" value="Back" onClick="history.go(-1)" >취소</button> <!-- 뒤로가기 처리 -->
		</div>
    </form>
</fieldset>

