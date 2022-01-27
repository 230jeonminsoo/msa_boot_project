<%@page import="java.util.Date"%>
<%@page import="com.my.board.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="./css/boardlist.css" rel=stylesheet>


<!--boardlist서블릿에서 결과값 setting해서 boardlistresult.jsp로 보낸값 받아옴-->
<%
List<Board> list = (List)request.getAttribute("list");
%>
<!--END-->

<script src="./js/boardlist.js"></script>
<script>
$(function(){
	
	<!--자유게시판 목록에서 글 쓰기 버튼 클릭되었을때 START-->	
		boardWriteClick();
	<!--자유게시판 목록에서 글 쓰기 버튼 클릭되었을때 END-->
		
	<!-- 자유게시판 목록에서 글 하나 클릭되었을때 START-->  
		boardDetail();
	<!-- 자유게시판 목록에서 글 하나 클릭되었을때 END-->
	
	<!-- 자유게시판 목록에서 검색 클릭시 START-->
		searchClick();	
	<!-- 자유게시판 목록에서 검색 클릭시 END-->
	
	<!-- 자유게시판 목록에서 분류 클릭시 START-->
		brdTypeClick();
	<!-- 자유게시판 목록에서 분류 클릭시 END-->
	
}); 

</script>

<div class="container">

<h1>자유게시판</h1>

	<table class="brd_info"> 
		<tr>
			<td>자유게시글이 올라오는 게시판입니다. <br>                 
			</td>
		</tr>
	</table>  
	
	
<div class="dropdown">
  <button class="dropbtn" >분류</button>
	  <div class="dropdown-content">
		  <a href="#" id="3">전체</a>
		  <a href="#" id="0">잡담</a>
		  <a href="#" id="1">정보</a>
		  <a href="#" id="2">기타</a>
	  </div>
</div>

 <form>
	<div class="search">           
		<select  name="f">  <!-- 즉 url부분 쿼리스트링값이 선택한거랑 같으면 검색바에 춣력해라 -->
			<option ${(param.f == "brd_title")? "selected" : "" }value="brd_title">제목</option>
			<option ${(param.f == "brd_content")? "selected" : "" } value="brd_content">제목+내용</option>
			<option ${(param.f == "brd_UNickName")? "selected" : "" } value="brd_UNickName">닉네임</option>
		</select>
		
		<input type="text" name="q" value="${param.q}" placeholder="검색어를 입력하세요" > <!-- ${param.word}은 쿼리스트링에 있는word를 검색바에 출력해달라 -->
		
		<button class="searchButton">검색</button>             
	</div>
</form>
 



<!-- 자유게시판 클릭시 출력될 자유게시판글 목록 출력 start-->
<div class="brd_list">
	<ul class="brd_top">
		<li>
			<span>글번호</span>
			<span>분류</span>
			<span>제목</span>
			<span>닉네임</span>
			<span>조회수</span>
			<span>댓글수</span>
			<span>작성일</span>
		</li>
	</ul> 
   
<%for(Board b: list){
  int brdIdx = b.getBrdIdx();
  int brdType = b.getBrdType();
  String brdTitle = b.getBrdTitle();
  String BrdUNickName = b.getBrdUNickName();
  int brdViews = b.getBrdViews();
  int cmtCount = b.getCmtCount();
  Date brdCreatAt = b.getBrdCreateAt();
%>
<div id="<%=brdIdx%>"> 
	<ul>
	    <li>
		    <span><%=brdIdx%></span>
		    <span> <% if(brdType == 0){%>
		    <%="잡담"%>&nbsp;
		    <% }%><% else if(brdType == 1){%>
		    <%="정보"%>&nbsp;
		    <% }%><% else{ brdType =2;%>
		    <%="기타" %>
		    <%} %></span>
		    <span><%=brdTitle%></span>
		    <span><%=BrdUNickName%></span>
		    <span><%=brdViews%></span>
		    <span><%=cmtCount%></span>
		     <span><%=brdCreatAt%></span>
	    </li>
	  </ul>
</div>

<%} %>


		<div class="board_write_button">
			<label>
				<img src="./images/pencil.png">글쓰기
			</label>	
		</div>
	</div>
</div>
<!--  end  --> 

