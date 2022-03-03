<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.io.File"%>
<%@page import="com.reco.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.reco.calendar.vo.CalPost" %>
<%@page import="com.reco.calendar.vo.CalInfo" %>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.List"%>

<%
Customer c = (Customer)session.getAttribute("loginInfo");
String calCategory = request.getParameter("calCategory");
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
int uIdx  = c.getUIdx();
int calIdx = Integer.parseInt(request.getParameter("calIdx"));
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">

	<title>캘린더글작성</title>
	   <style>
        .dellink{
          display: none;
        }
      </style>

	<link href="./css/calpostwrite.css" rel=stylesheet>
	<script src="./js/calpostwrite.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<script>

		$(function(){

			//오늘날짜값 받아오기
			/* document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10); */ //외국시간기준??
			document.getElementById('currentDate').valueAsDate = new Date();
			
			//작성완료 버튼 클릭시, 캘린더 리스트 보기
			addCalPostClick();
            
			//calMainImg 미리보기
			readImage(input);
			
			
		});
	</script>
</head>



<body>

	<h2 align="center"><%=calCategory %>&nbsp;캘린더 글 작성</h2>
	<form name="writeFrm" method="post" enctype="multipart/form-data">
	     <input type="hidden" name="calIdx" value="<%=calIdx %>">
	     <input type="hidden" name="calCategory" value="<%=calCategory %>">
	   	<table>
		    <tr>
		        <td width = "20%">대표이미지</td>
		        <td width = "80%">
		             <style>.dellink{display: none;}</style>
		             <script type="text/javascript" src="./js/imgpreview.js"></script>
		             <div class="image-container">
                        <img style="width: 300px;" id="preview-image" src="https://dummyimage.com/200x200/ffffff/000000.png&text=preview+image">
                        <input style="display: block;" type="file" name="calMainImg" id="input-image"accept="image/jpeg, image/jpg, image/png"  >
                     </div>
		             <a href="javascript:void(0);" class="dellink">대표이미지삭제</a>
		        </td>
		    </tr>
		    
			<tr>
		       <td>날짜</td>
		        <td>
		       	 <input type="date" id="currentDate" name="calDate">
		        </td>
		    </tr>
		    
		    <tr>
		        <td>리뷰/메모</td>
		         <td>
		            <textarea name="calMemo" cols="150" rows="20" placeholder="500자까지 자유작성/필수입력사항/캘린더에 작성하는 내용은 본인만 볼수 있다." ></textarea>
		        </td>
		    </tr>
		    
		    <tr>
		        <td colspan="2" align="center">
		            <button type="submit" >작성 완료</button>
		            <button type="button" onclick="self.close()" >닫기</button>
		        </td>
		    </tr>
		</table>
	</form>



</body>
