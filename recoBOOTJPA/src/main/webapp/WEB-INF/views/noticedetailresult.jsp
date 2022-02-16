

<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.reco.notice.vo.Notice"%>
<%
Notice n= (Notice)request.getAttribute("n");
int ntcIdx = n.getNtcIdx();
String ntcTitle = n.getNtcTitle();
String  ntcContent = n.getNtcContent();
String ntcAttachment = n.getNtcAttachment();
Date ntcCreateAt = n.getNtcCreateAt();
String ntcUNickname = n.getNtcUNickName();
int ntcViews = n.getNtcViews();
%>
<%String image = (String)request.getAttribute("image"); %>
<%String letter = (String)request.getAttribute("letter"); %>    
    <link href="./css/noticedetail.css" rel=stylesheet>
 	<script src="./js/noticedetail.js"></script>
	<script>
	$(function(){
		//이미지 다운로드후 보여주기
		<%if(image != null){%>
			let $img = $("div.ntcDetail>ul.ntcDetail>li>div.image>img");
			$.ajax({
				url: "./noticedownloadimage",
				method:'get',
				data:"imageFileName="+"<%=image%>",
				
				cache:false, //이미지 다운로드용 설정
		        xhrFields:{  //이미지 다운로드용 설정
		            responseType: 'blob'
		        },
				success:function(responseData){
					let url = URL.createObjectURL(responseData);
					$img.attr('src', url); 														
				},
				error:function(jqXHR, textStatus){
					alert("에러:" + jqXHR.status);
				}
			});
		<%}else{%>
			$('div.ntcDetail>ul.ntcDetail>li>div.image').css('display','none');
		<%}%>
	
		//수정버튼 클릭시
		noticeModifyClick();
		//삭제버튼 클릭시
		noticeRemoveClick();	
		//목록버튼 클릭시
		noticeListClick();
		//첨부파일 다운로드 시
		letter = "<%=letter%>";
		noticeDownloadClick(letter);
	});
	</script>
	
	
	<div class="ntcDetail">
    	<h1>공지사항 </h1>
        
		<ul class="ntcDetail">
			<li>
			<div class="ntcIdx">글번호 : <span id="ntcIdx"><%=ntcIdx %></span></div>
			
			<hr>
			<div class="ntcTitle"><strong>제목:<span id="ntcTitle"> <%=ntcTitle%></span></strong></div>
			<div class="ntcUNickname">작성자:<span> <%=ntcUNickname %></span></div>
			<div class="ntcCreateA">작성일: <span> <%=ntcCreateAt %></span></div>
			<div class="ntcViews">조회수:<span id="ntcViews"> <%=ntcViews %></span></div><br>
			<hr>
			<div class="image"><img style="width:500px; height:500px;"></div>
			<div class="ntcContent"><span id="ntcContent"> 
				<%=ntcContent %></span></div>
			<hr>
			<%if(ntcAttachment != null){ %>
			<div class="ntcAttachment">첨부파일: <span id="ntcAttachment"> <%=ntcAttachment %></span></div>
			<%}else{ %>
			첨부파일이 없습니다.
			<%} %>
			</li>
		</ul>	
		
		
		<div class="NoticeDetailButton">
		
<%
Customer c = (Customer) session.getAttribute("loginInfo"); 
%>
<%
if (session.getAttribute("loginInfo") != null) { 
%>
<%
int uAuthCode = c.getUAuthCode(); 
%>

     <% if(uAuthCode == 0 ) {%>
		<button class="notice_modify" id="<%=ntcIdx %>"  style= "visibility:visible">글 수정</button>
         <button class="notice_remove" id="<%=ntcIdx %>"  style= "visibility:visible">글 삭제</button>
		<button class="notice_list">글 목록</button> 
		<%} else{%> 
		 <button class="notice_modify" id="<%=ntcIdx %>"  style= "visibility:hidden">글 수정</button>
         <button class="notice_remove" id="<%=ntcIdx %>"  style= "visibility:hidden">글 삭제</button>
		 <button class="notice_list">글 목록</button> 
		<%
		}
		%>
		
<%} else {  %>
<script>location.href="./";</script>
<%} %>	

      </div>
 </div> 	
        
        
         
		
