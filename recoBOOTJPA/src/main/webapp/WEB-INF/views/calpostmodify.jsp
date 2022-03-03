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
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
CalPost cp = (CalPost)request.getAttribute("cp");

String calDate = request.getParameter("calDate");
String calMainImg = request.getParameter("originalcalMainImg");

//String calMainImg1 = cp.getCalMainImg(); //메인이미지 원본이름 

String calMemo = request.getParameter("calMemo");
String calCategory = request.getParameter("calCategory");
int uIdx  = c.getUIdx();
int calIdx = Integer.parseInt(request.getParameter("calIdx"));

String saveDirectory = "C:\\reco\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles();

String imageFileName = "cal_" + uIdx  + "_" + calIdx + "_" +calDate +".jpg";
String thumbnailName = "s_"+ imageFileName;
%>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">

	<title>캘린더글수정페이지</title>
	  <style>
        .dellink{
          display: none;
        }
      </style>

	<link href="./css/calpostwrite.css" rel=stylesheet>
	<script src="./js/calpostmodify.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
$(function(){		

		//캘린더 수정완료 버튼 클릭되었을때
		 modifyCalPostClick();
   });
</script>



<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">

	<title>캘린더글수정</title>
	   <style>
        .dellink{
          display: none;
        }
      </style>
	
	<link rel="stylesheet" href="./css/calpostmodify.css"> 
	<script src="./js/calpostmodify.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<style>


</style>


	
	<form name="writeFrm" method="post" enctype="multipart/form-data">
		<h2 align="center"><%=calCategory %>&nbsp;캘린더 글 수정</h2>
		
	     <input type="hidden" name="calIdx" value="<%=calIdx %>">
	     <input type="hidden" name="calCategory" value="<%=calCategory %>">
	     <input type="hidden" name="originalcalMainImg" value="<%=calMainImg %>">
	      <%-- <input type="hidden" name="calDate" value="<%=calDate%>"> --%>
	   	<table>
		   <tr>
		        <td width = "50%">대표이미지수정</td>
		        <td width = "50%">
		             
		             <div class="image-container">
                       <input style="display: block;" type="file" name="calMainImg" id="input-image"accept="image/*"  >
                     </div>   
		        </td>
		    </tr>
		   	    
		    <tr>
		        <td>리뷰/메모수정</td>
		         <td>
		            <textarea name="calMemo" cols="100" rows="15" placeholder="500자까지 자유작성/필수입력사항/캘린더에 작성하는 내용은 본인만 볼수 있다." ></textarea>
		        </td>
		    </tr>
		    
		    <tr>
		        <td colspan="2" align="center">
		            <button type="submit" >수정 완료</button>
		            <button type="button" onclick="self.close()" >닫기</button>
		        </td>
		    </tr>
		</table>
	</form>
