<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.reco.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.reco.calendar.vo.CalInfo" %>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.io.File"%>  
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
	
     /*--캘린더 수정 버튼이 클릭되었을때 START--*/
     //calInfomodifyBtClick();
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


String saveDirectory = "C:\\reco\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles();

String imageFileName = "cal_post_" + uIdx  + "_" + calIdx + ".jpg";
String thumbnailName = "s_"+ imageFileName;
%>	            
<fieldset>
    <form  method="post" action="./calInfomodify" autocomplete="off" enctype="multipart/form-data">
        <input type="hidden" name="calIdx" value="<%=calIdx %>">
        <div id="<%=calIdx %>" class="calIdx" style="display:show">
		    <a> <!-- 수정페이지 이미지 띄우기 -->
		     	<img id="<%=thumbnailName %>" alt="thumbnail"
		     	     style="display: block; margin: 0 auto; width:100px; height:80px;">
		    </a>
		</div>
        
        
        <div class="Category" align="center">        	
			<strong>현재&nbsp;캘린더이름&nbsp;:&nbsp;<%=calCategory %> </strong> <br>
        	<strong style="display: none;">calIdx값&nbsp;:&nbsp;<%=calIdx %> </strong>
        </div>
        
        
        <div class="input_image">
         	<strong>대표사진 수정(컴퓨터에서 불러오기)</strong><br>
         	<input type="file" name="calThumbnail" id="upload_file" accept="image/*" >
        </div><br>
        
        <div class="input_text">
        	<strong>캘린더 이름 수정 </strong><br>
        	<input type="text" name="calCategory" id="input" placeholder="새로운 캘린더 이름을 입력해주세요" required>
        </div><br>
        
		<div class="bt">
	        <button type="submit" >수정</button>
	        <button type="button" value="Back" onClick="history.go(-1)" >취소</button> <!-- 뒤로가기 처리 -->
		</div>
    </form>
</fieldset>


