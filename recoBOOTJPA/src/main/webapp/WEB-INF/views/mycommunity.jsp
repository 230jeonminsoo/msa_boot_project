<%@page import="com.reco.board.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.notice.vo.Notice"%>
<%@page import="com.reco.dto.PageDTO"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%Customer c = (Customer)session.getAttribute("loginInfo"); %> 
<%
String msg = (String)request.getAttribute("msg");
PageDTO<Notice> noticePageDTO = (PageDTO)request.getAttribute("noticePageDTO");
List<Notice> noticeList = new ArrayList<>();
if(noticePageDTO != null){
	noticeList = noticePageDTO.getList();
}
 PageDTO<Board> boardPageDTO = (PageDTO)request.getAttribute("boardPageDTO");
 List<Board> boardList = new ArrayList<>();
if(boardPageDTO != null){
	boardList = boardPageDTO.getList();
}
/* PageDTO<Comment> pageDTO = (PageDTO)request.getAttribute("commentPageDTO");
List<Comment> commentList = pageDTO.getList();   */
%>  
<%String image = (String)request.getAttribute("noticelistimage"); %>

<script src="./js/mycommunity.js"></script>
<link href="./css/mycommunity.css" rel=stylesheet>

<script>
$(function(){
	//공지사항 글 클릭시 해당 글을 새탭으로 여는 함수
	noticeDetail("mycommunity");
	//내 공지사항 글 삭제
	myNoticerm();
});
</script>    



<!-- 작성한 공지사항 글 (관리자가 아닐경우 보이지않음). 누를시 새탭에 띄울예정-->
<fieldset>
	<div class="ntc_list">
	  
	
	<%
	if (session.getAttribute("loginInfo") != null) { 
	%>
	
		<%if(c.getUAuthCode() == 0){ %>
		<h1>내가 작성한 공지사항</h1>
		<hr>
			<ul class="ntc_top">
			<li>
			 	<input type='checkbox'
	    	    style='width:25px; height:25px'
	    	    name='selectAll'
	    	    onclick='selectAll(this)'
	    	    />	
				<span>글번호</span>
				<span>제목</span>
				<span>닉네임</span>
				<span>조회수</span>
				<span>작성일</span>
			</li>
		</ul>
		<%if (noticePageDTO  == null) {%>
			<span class="noNtc" ><%=msg %></span>
		<%} else{%> 
			<%for(Notice n: noticeList){
			  int ntcIdx = n.getNtcIdx();
			  
			  String ntcTitle = n.getNtcTitle();
			  String ntcuNickName = n.getNtcUNickName();
			  String ntcAttachment = n.getNtcAttachment();
			  int ntcViews = n.getNtcViews();
			  Date ntcCreatAt = n.getNtcCreateAt();
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			  String ntcCrt = sdf.format(ntcCreatAt);
			%>
			<div class ="noticelist" id="<%=ntcIdx%>"> 
				 <ul>	
				    <li>			    
			 		<input type='checkbox'
				    	   style='width:25px; height:25px'	
					       name='ntcIdx' 
					       value=<%=ntcIdx%> />	     
					 <span id="<%=ntcIdx%>"><%=ntcIdx%></span>
					 <span id="<%=ntcIdx%>"><%=ntcTitle%><%if(ntcAttachment != null){ %><img src="./images/클립.png"><%} %><%if(image != null){ %><img src="./images/이미지.png"><%} %></span>
					 <span id="<%=ntcIdx%>"><%=ntcuNickName%></span>
					 <span id="<%=ntcIdx%>"><%=ntcViews%></span>
					 <span id="<%=ntcIdx%>"><%=ntcCrt%></span>
					 </li> 
				  </ul>
			</div>
			<%} %><!-- for문 끝 -->
			<button class="myNoticerm">글 삭제</button>
		<%} %><!-- if (noticePageDTO  == null) 끝 -->
	
		<%if(noticePageDTO != null) {%>
			<div class="pagegroup">
				 <%  
				 String backContextPath = request.getContextPath();
				 if(noticePageDTO.getStartPage() > 1){%>			
				 	<span class="<%= backContextPath%><%=noticePageDTO.getUrl()%>/<%=noticePageDTO.getStartPage()-1%> active">prev</span>
				 <%} %>
		 
		 		<%	for(int i = noticePageDTO.getStartPage() ; i<=noticePageDTO.getEndPage() ; i++){ %>
					<span class="<%= backContextPath%><%=noticePageDTO.getUrl() %>/<%=i%> <%if(i != noticePageDTO.getCurrentPage()){ %>active<%}%>"><%=i%></span>
				<%}%>
				
				<% 
				if(noticePageDTO.getEndPage() < noticePageDTO.getTotalPage()){%>
					<span class="<%= backContextPath%><%=noticePageDTO.getUrl()%>/<%=noticePageDTO.getEndPage()+1%> active">next</span>
				<%} %>
			</div>
		<%} %><!-- 페이지그룹 끝 -->	
		
		<%} %><!-- if(c.getUAuthCode() == 0) 끝 -->
	<%} else{%>
		<script>location.href="./";</script>
	<%} %><!--if (session.getAttribute("loginInfo") 끝  -->
	</div>
	
	 <div class="detail">
 	</div> 
	
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
