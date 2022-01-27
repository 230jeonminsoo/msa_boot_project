
<%@page import="com.my.customer.vo.Customer"%>
<%@page import="java.util.Date"%>
<%@page import="com.my.notice.vo.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  
    <meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="./css/noticelist.css" rel=stylesheet>
  

<!--noticelist서블릿에서 결과값 setting해서 noticelistresult.jsp로 보낸값 받아옴-->
<%
List<Notice> list = (List)request.getAttribute("list");
%>
<!--END-->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="./js/noticelist.js"></script>
<script>
	$(function(){
		<!--공지사항 목록에서 글 쓰기 버튼 클릭되었을때 START-->
			noticeWriteClick();
		<!--공지사항 목록에서 글 쓰기 버튼 클릭되었을때 END-->
		
		<!-- 공지사항 목록에서 글 하나 클릭되었을때 START-->  
			noticeDetail();
		<!-- 공지사항 목록에서 글 하나 클릭되었을때 END-->
		
		<!-- 공지사항 목록에서 검색 클릭시 START-->
			searchClick();
		<!-- 공지사항 목록에서 검색 클릭시 END-->
	}); 

</script>
  

<div class="container">

<h1>공지사항</h1>

	<table class="ntc_info"> 
		<tr>
			<td>공지사항이 올라오는 게시판입니다. <br>                 
			</td>
		</tr>
	</table>  
 

 <form>
	<div class="search" style="float:right;">           
		<select  name="f">  <!-- 즉 url부분 쿼리스트링값이 선택한거랑 같으면 검색바에 춣력해라 -->
			<option ${(param.f == "ntc_title")? "selected" : "" }value="ntc_title">제목</option>
			<option ${(param.f == "ntc_content")? "selected" : "" } value="ntc_content">제목+내용</option>
		</select>
		
		<input type="text" name="q" value="${param.q}" placeholder="검색어를 입력하세요" > <!-- ${param.word}은 쿼리스트링에 있는word를 검색바에 출력해달라 -->
		
		<button class="searchButton">검색</button>             
	</div>
</form>



<!--공지사항 클릭시 출력될 공지사항 글 목록 출력 start-->

<div class="ntc_list">
	<ul class="ntc_top">
		<li>
			<span>글번호</span>
			<span>제목</span>
			<span>닉네임</span>
			<span>조회수</span>
			<span>작성일</span>
		</li>
	</ul>
   
<%for(Notice n: list){
  int ntcIdx = n.getNtcIdx();
  
  String ntcTitle = n.getNtcTitle();
  String ntcuNickName = n.getNtcUNickName();
  int ntcViews = n.getNtcViews();
  Date ntcCreatAt = n.getNtcCreateAt();
%>
<div id="<%=ntcIdx%>"> 
	 <ul>
	    <li>
		 <span><%=ntcIdx%></span>
		 <span><%=ntcTitle%></span>
		 <span><%=ntcuNickName%></span>
		 <span><%=ntcViews%></span>
		 <span><%=ntcCreatAt%></span>
		 </li> 
	  </ul>
</div>

<%} %>


<%
Customer c = (Customer) session.getAttribute("loginInfo"); 
%>
<%
if (session.getAttribute("loginInfo") != null) { 
%>
<%
int uAuthCode = c.getUAuthCode(); 
%>
<% 		if(uAuthCode == 1) {%>
			
	<div class="notice_write_button" style= "visibility:hidden">
		<label>
			<img src="./images/pencil.png">글쓰기
		</label>	
	</div>

	<%} else {%> <% uAuthCode = 0; %>
		
		<div class="notice_write_button" style= "visibility:visible">
			<label>
				<img src="./images/pencil.png">글쓰기
			</label>	
		</div>
		
		<%
		}
		%>
<%} else {  %>
<script>location.href="index.jsp";</script>
<%} %>



</div> 
</div>



