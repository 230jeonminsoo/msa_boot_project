<%@page import="com.reco.dto.PageDTO2"%>
<%@page import="com.reco.board.vo.Comment"%>
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
String msg1 = (String)request.getAttribute("msg1");
 PageDTO<Board> boardPageDTO = (PageDTO)request.getAttribute("boardPageDTO");
 List<Board> boardList = new ArrayList<>();
if(boardPageDTO != null){
	boardList = boardPageDTO.getList();
}

PageDTO2<Board> commentPageDTO = (PageDTO2)request.getAttribute("commentPageDTO");
List<Comment> listComments = new ArrayList<>();
if(commentPageDTO != null) {
listComments = commentPageDTO.getComments(); 
}
String msg2 = (String)request.getAttribute("msg2");


/* PageDTO<Comment> pageDTO = (PageDTO)request.getAttribute("commentPageDTO");
List<Comment> commentList = pageDTO.getList();   */
%>  


<script src="./js/mycommunity.js"></script>
<link href="./css/mycommunity.css" rel=stylesheet>
<script>
$(function(){
	//공지사항 글 클릭시 해당 글 옆에 보여줌
	noticeDetail("mycommunity");
	//내 공지사항 글 삭제
	myNoticerm();
	
	//자유게시판 글 클릭시 해당 글 옆에 보여줌
	boardDetail("mycommunity");
	//내 자유게시판 글 삭제
	myBoardrm();
	
	//댓글 클릭시 해당 게시글로 이동
	commentDetail();
	
	//내가 쓴 댓글 삭제
	myCommentrm();
	
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
		<%if (noticePageDTO == null) {%>
			<span class="noNtc" ><%=msg%></span>
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
					 <span id="<%=ntcIdx%>"><%=ntcTitle%><%if(ntcAttachment != null){ %><img src="./images/클립.png"><%} %></span>
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

<%
if (session.getAttribute("loginInfo") != null) { 
%>
<h1>내가 작성한 자유게시글</h1>
<hr>
<!-- 자유게시판 클릭시 출력될 자유게시판글 목록 출력 start-->
<div class="brd_list">
	<ul class="brd_top">
		<li>
		<input type='checkbox'
	    	    style='width:25px; height:25px'
	    	    name='checkBoxAll'
	    	    onclick='checkBoxAll(this)'
	    	    />	
			<span>글번호</span>
			<span>분류</span>
			<span>제목</span>
			<span>닉네임</span>
			<span>조회수</span>
			<span>댓글수</span>
			<span>작성일</span>
		</li>
	</ul> 
 
	<%if (boardPageDTO == null) {%>
			<span class="noBrd" ><%=msg1%></span>
	<%} else{%> 
	<%
	for(Board b: boardList){
	  int brdIdx = b.getBrdIdx();
	  int brdType = b.getBrdType();
	  String brdTitle = b.getBrdTitle();
	  String BrdUNickName = b.getBrdUNickName();
	  String brdAttachment = b.getBrdAttachment();
	  int brdViews = b.getBrdViews();
	  int cmtCount = b.getCmtCount();
	  Date brdCreatAt = b.getBrdCreateAt();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	  String brdCrt = sdf.format(brdCreatAt);
	%>
<div class="boardlist" id="<%=brdIdx%>"> 
	<ul>
	    <li>
	    	<input type='checkbox'
				    	   style='width:25px; height:25px'	
					       name='brdIdx' 
					       value=<%=brdIdx%> />	  
		    <span id="brdIdx"><%=brdIdx%></span>
		    <span> <% if(brdType == 0){%>
		    <%="잡담"%>&nbsp;
		    <% }%><% else if(brdType == 1){%>
		    <%="정보"%>&nbsp;
		    <% }%><% else{ brdType =2;%>
		    <%="기타" %>
		    <%} %></span>
		    <span><%=brdTitle%> <%if(brdAttachment != null){ %><img src="./images/클립.png"><%} %></span>
		    <span><%=BrdUNickName%></span>
		    <span><%=brdViews%></span>
		    <span><%=cmtCount%></span>
		     <span><%=brdCrt%></span>
	    </li>
	  </ul>
</div>

<%} %>
<button class="myboardrm">글 삭제</button>
<%} %>

<%if(boardPageDTO != null) {%>
<div class="boardpagegroup">
		 <%  
		 String backContextPath = request.getContextPath();
		 if(boardPageDTO.getStartPage() > 1){%>			
		 	<span class="<%= backContextPath%><%=boardPageDTO.getUrl()%>/<%=boardPageDTO.getStartPage()-1%> active">prev</span>
		 <%} %>
 
 		<%	for(int i = boardPageDTO.getStartPage() ; i<=boardPageDTO.getEndPage() ; i++){ %>
			<span class="<%= backContextPath%><%=boardPageDTO.getUrl() %>/<%=i%> <%if(i != boardPageDTO.getCurrentPage()){ %>active<%}%>"><%=i%></span>
		<%}%>
		
		<% 
		if(boardPageDTO.getEndPage() < boardPageDTO.getTotalPage()){%>
			<span class="<%= backContextPath%><%=boardPageDTO.getUrl()%>/<%=boardPageDTO.getEndPage()+1%> active">next</span>
		<%} %>
</div>
<%} %><!-- 페이징 완료 -->

<%} else{%>
	<script>location.href="./";</script>
<%} %><!--if (session.getAttribute("loginInfo") END  -->

</div> <!-- brd_list div END-->
	
	 <div class="brddetail">
 	</div> 
 	
</fieldset>

<!-- 내가 작성한 댓글. 누를시 새탭에 띄울예정-->
<fieldset>	



<!------------ 댓글 시작 ---------->	
	<%
if (session.getAttribute("loginInfo") != null) { 
%>
<h1>내가 작성한 댓글</h1>
<hr>

<div class="cmt_list">
	<ul class="cmt_top">
		<li>
		<input type='checkbox'
	    	    style='width:25px; height:25px'
	    	    name='cmtCheckBoxAll'
	    	    onclick='cmtCheckBoxAll(this)'
	    	    />	
			<span>내가 쓴 댓글</span>
			<span>작성일</span>
			<span>게시글제목</span>
		
		</li>
	</ul> 
	<%if (commentPageDTO  == null) {%>
	<span class="noCmt"><%=msg2%></span>
<%} else{%>    
	<%
	for(Comment comment: listComments){
		int brdIdxMy = comment.getBrd().getBrdIdx();
	  String brdTitleMy = comment.getBrd().getBrdTitle();
	  int cmtIdxMy = comment.getCmtIdx();
	  Date cmtCreateAtMy = comment.getCmtCreateAt();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	  String cmtCreateAtMyCrt = sdf.format(cmtCreateAtMy);
	  String cmtContentMy = comment.getCmtContent();
	  /* String brdTitle = c.; */
	  /* int cmtCountMy = b.getCmtCount(); */
	
	  /* int brdType = b.getBrdType(); */
	  /* String BrdUNickName = b.getBrdUNickName();
	  String brdAttachment = b.getBrdAttachment();
	  int brdViews = b.getBrdViews(); */	 
	 /*  Date brdCreatAt = b.getBrdCreateAt(); */
	/*   SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	  String brdCrt = sdf.format(brdCreatAt); */
	%>
<div class="commentlist" > 
	<ul>
	    <li>
	    	<input type='checkbox' 
	    		   style='width:25px; height:25px'
	    		   name='cmtIdxMy' 
	    		   value1= <%=brdIdxMy%>
	    		   value2= <%=cmtIdxMy%>>
	    	</input>	  
		   
		    	
		  
		  	<span id="brdIdxMy" style="display:none;"><%=brdIdxMy%></span>  		     	    	    
		    <span><%=cmtContentMy%> </span>
		    <span><%=cmtCreateAtMyCrt%></span>	    		    	    	
		    <span><%=brdTitleMy %></span>
		  	
		    <%-- <span><%=cmtCount%></span> --%>
	    </li>
	  </ul>
</div>

<%} %>
<button class="mycommentrm">댓글 삭제</button>
<%} %>

 <%if(commentPageDTO != null) {%>
<div class="commentpagegroup">
		 <%  
		 String backContextPath = request.getContextPath();
		 if(commentPageDTO.getStartPage() > 1){%>			
		 	<span class="<%= backContextPath%><%=commentPageDTO.getUrl()%>/<%=commentPageDTO.getStartPage()-1%> active">prev</span>
		 <%} %>
 
 		<%	for(int i = commentPageDTO.getStartPage() ; i<=commentPageDTO.getEndPage() ; i++){ %>
			<span class="<%= backContextPath%><%=commentPageDTO.getUrl() %>/<%=i%> <%if(i != commentPageDTO.getCurrentPage()){ %>active<%}%>"><%=i%></span>
		<%}%>
		
		<% 
		if(commentPageDTO.getEndPage() < commentPageDTO.getTotalPage()){%>
			<span class="<%= backContextPath%><%=commentPageDTO.getUrl()%>/<%=commentPageDTO.getEndPage()+1%> active">next</span>
		<%} %>
</div>
<%} %> <!-- 페이징 완료 -->

<%} else{%>
	<script>location.href="./";</script>
<%} %><!--if (session.getAttribute("loginInfo") END  -->

</div> <!-- brd_list div END-->
 </fieldset>  
