<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.io.File"%>
<%@page import="com.reco.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.reco.calendar.vo.CalPost" %>
<%@page import="com.reco.calendar.vo.CalInfo" %>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.List"%>
<link href="./css/calpostwrite.css" rel=stylesheet>
<script src="./js/calpostmodify.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">

<title>캘린더글수정페이지</title>
<style>
  .dellink{
    display: none;
  }
</style>

    
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>
	<script>location.href="./";</script>
<%
	return;
}else{
%>

<%
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
CalPost cp = (CalPost)request.getAttribute("cp"); 
int uIdx  = c.getUIdx();
int calIdx = Integer.parseInt(request.getParameter("calIdx")); //calpostdetail.jsp에서 수정버튼 클릭할때 값 보내줌

String calDate = request.getParameter("calDate"); //calpostdetail.jsp에서 수정버튼 클릭할때 값 보내줌
String calMemo = request.getParameter("calMemo"); //calpostdetail.jsp에서 수정버튼 클릭할때 값 보내줌
 
//String calCategory = ci.getCalCategory(); //null
//String calCategory = request.getParameter("calCategory"); //calpostdetail.jsp에서 보내줘야 받아오기 가능

//String calMainImg = request.getParameter("originalcalMain"); //기존메인이미지명
String pastcalMainImg = request.getParameter("calMainImg"); //메인이미지 원본이름

String saveDirectory = "C:\\reco\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles();

String imageFileName = "cal_" + uIdx  + "_" + calIdx + "_" +calDate +".jpg";
String thumbnailName = "s_"+ imageFileName;
%>
	

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

<script>
$(function(){	
		let $formObj = $('fieldset>form');
		/*---두번째 div에서  모든 img태그 보여주기 START--*/
		let $img = $('div.calIdx img');
		$img.each(function(i, element){
			let imgId = $(element).attr('id');	
			$.ajax({
				url: './calendardownloadimage?imageFileName='+imgId,
				
				cache:false, //이미지 다운로드용 설정
		         xhrFields:{ //이미지 다운로드용 설정
		            responseType: 'blob'
		        } , 
		        success: function(responseData, textStatus, jqXhr){
		        	let contentType = jqXhr.getResponseHeader("content-type");
		        	let contentDisposition = decodeURI(jqXhr.getResponseHeader("content-disposition"));
		       		var url = URL.createObjectURL(responseData);
		       		$(element).attr('src', url); 
		        },
		        error:function(){
		        }
			});  //end $.ajax
		});//end each
	

		//캘린더 수정완료 버튼 클릭되었을때
		 modifyCalPostClick();
		
		//calMainImg 미리보기
		readImage(input);
   });
</script>

<body>
	<h2 align="center">&nbsp;캘린더 글 수정</h2>
	<form name="modifyFrm" method="post" enctype="multipart/form-data">
		<div class="test"><%=calIdx %>,<%=calDate %>,<%=pastcalMainImg %>, <%=calMemo %></div>
		     <input type="hidden" name="calIdx" value="<%=calIdx %>">
		     <%--  <input type="hidden" name="calCategory" value="<%=calCategory %>"> --%>
		     <input type="hidden" name="originalcalMain" value="<%=pastcalMainImg %>">
		     <input type="hidden" name="calDate" value="<%=calDate%>">
		     <input type="hidden" name="calMemo" value="<%=calMemo%>">
	   	<table>
		   <tr>
		        <td width = "50%">대표이미지수정</td>
		        <td width = "50%">
		        	<style>.dellink{display: none;}</style>
		            <!-- <script type="text/javascript" src="./js/imgpreview.js"></script> -->
		            <div id="<%=calIdx %>" class="calIdx" style="display:show">
					    <a> <!-- 수정페이지 이미지 띄우기 -->
					     	<img id="<%=thumbnailName %>" class="thumbnailName" alt="thumbnailName"
					     	     style="display: block; margin: 0 auto; width:400px; height:400px;">
					    </a>
					</div>
					
	             	<div class="image-container">
	             	  <!-- 파일첨부 -->
                      <input style="display: block;" type="file" name="calMainImg" id="input-image"accept="image/*"  >
                    </div>   
		        </td>
		    </tr>
		   	    
		    <tr>
		        <td>리뷰/메모수정</td>
		         <td>
		            <textarea name="calMemo" value="<%=calMemo %>" cols="100" rows="15" placeholder="기존 리뷰 : <%=calMemo %>" ></textarea>
		            <%-- <p>기존 리뷰 : <input type="text" name="calMemo" value="<%=calMemo %>"  style="width:100px; height:15px;"></p> --%>
		        </td>
		    </tr>
		    
		    <tr>
		        <td colspan="2" align="center" class="cpmodifyBt">
		            <button type="submit" >수정 완료</button>
		            <button type="button" onclick="self.close()" >닫기</button>
		        </td>
		    </tr>
		</table>
	</form>
</body>
<%} //end if(c == null) %>
