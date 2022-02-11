<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.notice.vo.Notice"%>
<%@page import="com.reco.dto.PageDTO"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%Customer c = (Customer)session.getAttribute("loginInfo"); %>   
<%
PageDTO<Notice> pageDTO = (PageDTO)request.getAttribute("pageDTO");
List<Notice> list = pageDTO.getList();
%> 
<script src="./js/mycommunity.js"></script>

<script>
$(function(){
	//로딩될시 회원의 글 가져오는 함수
	
	//각 글 클릭시 해당 글을 새탭으로 여는 함수
	
});
</script>    



<!-- 작성한 공지사항 글 (관리자가 아닐경우 보이지않음). 누를시 새탭에 띄울예정-->
<fieldset>
<%if(pageDTO != null) {%>
	<%if(c.getUAuthCode() == 0){ %>
	<h1>내가 작성한 공지사항</h1>
	<hr>
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
		  String ntcAttachment = n.getNtcAttachment();
		  int ntcViews = n.getNtcViews();
		  Date ntcCreatAt = n.getNtcCreateAt();
		%>
		<div class ="noticelist" id="<%=ntcIdx%>"> 
			 <ul>
			    <li>
				 <span><%=ntcIdx%></span>
				 <span><%=ntcTitle%><%if(ntcAttachment != null){ %><img src="./images/클립.png"><%} %></span>
				 <span><%=ntcuNickName%></span>
				 <span><%=ntcViews%></span>
				 <span><%=ntcCreatAt%></span>
				 
				 </li> 
			  </ul>
		</div>
		
		<%} %>
	<%} %>
<%} else {%>
작성한 공지사항 글이 없습니다.
<%} %>	
</fieldset>

<!-- 내가 작성한 게시글. 누를시 새탭에 띄울예정-->
<fieldset>		
	<h1>내가 작성한 게시글</h1>
	<hr>
	<table>
		<tr>
			<th>제목</th>
			<th></th>
			<th>작성일</th>
			<th>추천수</th>
		</tr>
		<tr>
			<td>1111</td>
			<td>와와 축하해주세요. </td>
			<td>2021.12.11</td>
			<td>1</td>
		</tr>
	</table>
</fieldset>

<!-- 내가 작성한 댓글. 누를시 새탭에 띄울예정-->
<fieldset>	
	
	<h1>내가 쓴 댓글</h1>
	<hr>
	<table>
		<tr>
			<th>댓글</th>
			<th></th>
			<th></th>
			<th>작성일</th>
		</tr>
		<tr>
			<td>1111</td>
			<td>와와 축하해주세요. </td>
			<td>2021.12.11</td>
			<td>1</td>
		</tr>
	</table>	
 </fieldset>   