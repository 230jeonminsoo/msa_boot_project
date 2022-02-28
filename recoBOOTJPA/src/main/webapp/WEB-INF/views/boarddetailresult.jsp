<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.reco.dto.PageDTO2"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.board.vo.Comment"%>
<%@page import="com.reco.board.vo.Board"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


 <link href="./css/board_content.css" rel=stylesheet>
 <script src="./js/boarddetail.js"></script>



 
<%
Date nowTime = new Date();
SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
//Board b = (Board)request.getAttribute("b");
//List<Comment> comments = b.getComments();
PageDTO2<Board> pageDTO2 = (PageDTO2)request.getAttribute("PageDTO2");
Board b = pageDTO2.getBoard();
int brdIdx = b.getBrdIdx();
int brdType = b.getBrdType();
String brdTitle = b.getBrdTitle();
String brdUNickname = b.getBrdUNickName();
int brdViews = b.getBrdViews();
int brdThumbUp = b.getBrdThumbUp();
Date brdCreateAt = b.getBrdCreateAt();
String brdContent = b.getBrdContent();
String brdAttachment = b.getBrdAttachment();


List<Comment> comments = new ArrayList<>();
comments = pageDTO2.getComments();

%> 
<%String image = (String)request.getAttribute("image"); %>
<%String letter = (String)request.getAttribute("letter"); %>    

<script>
$(function(){
	let $formObj = $('fieldset>form');
	
	//이미지 다운로드후 보여주기
	<%if(image != null){%>
		let $img = $("div.brdDetail>ul.brdDetail>li>div.image>img");
		$.ajax({
			url: "./boarddownloadimage",
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
			$('div.brdDetail>ul.brdDetail>li>div.image').css('display','none');
	<%}%>

	//게시글 수정버튼 클릭시
		boardModifyClick();
	//게시글 삭제버튼 클릭시
		boardRemoveClick();	
	//게시글 목록버튼 클릭시
		boardListClick();
	//첨부파일 다운로드 시
	letter = "<%=letter%>";
	boardDownloadClick(letter);
	
	//댓글 등록버튼 클릭시
		commentAddClick();
		
	
	//댓글 수정버튼 클릭시 수정창 나옴.
		commentModifyBtClick();	
	//댓글 수정버튼 클릭시 수정내용 전송
		commentModifyClick();	
	//댓글 삭제클릭시
		commentRemoveClick();
	
	//대댓글 등록버튼 클릭시 등록창 나옴.
		comment2AddBtClick();
	//대댓글 등록버튼 클릭시 대댓글 내용전송
		comment2AddClick();
	
});

</script>           

      	<div class="brdDetail">
      		<h1>자유게시판</h1>
             	
          <ul class="brdDetail">
	    	 <li>
				<div class="brdIdx">글번호 :  
					<span id="brdIdx">
		          	<%=brdIdx%>  
		            </span> 
		         </div>
		            
			 	<div class="brdType">분류 :  
				 	 <span id="brdType">
		            <% if(brdType == 0){%>
				    <%="잡담"%>&nbsp;
				    <% }%><% else if(brdType == 1){%>
				    <%="정보"%>&nbsp;
				    <% }%><% else{ brdType =2;%>
				    <%="기타" %>
				    <%} %> 
		             </span>
	              </div>
	             
			 <hr>
			 
			 <div class="brdTitle"><strong>제목:
				 <span id="brdTitle">
		          <%=brdTitle%>   
		          </span></strong>	
	         </div>
				
		   	<div class="brdUNickname">작성자:
			     <span > 
			      <%=brdUNickname %>
			      </span>
		      </div>
		     
		     <div class="brdCreateAt">작성일:     
		            <span >
		             <%=sf.format(brdCreateAt)%>
		            </span>
	          </div>
	         
	         <div class="brdViews">조회수:
		         <span >
		          <%=brdViews%>
		          </span> 
			  </div><br> 
	           
	       <%--<div class="brdThumbUp">추천수:
		            <span >
		             <%=brdThumbUp%>
		            </span>
		        </div>
	             --%>
	         
	         <hr>
	         <div class="image"><img style="width:500px; height:500px;"></div>
	         <div class="brdContent"><span id="brdContent"> 
					<%=brdContent %></span></div>			
		     <hr>
		     <%if(brdAttachment != null){ %>
				<div class="brdAttachment">첨부파일: <span id="brdAttachment"> <%=brdAttachment %></span></div>
				<%}else{ %>
				첨부파일이 없습니다.
				<%} %>
			</li>
		</ul>
	
	
<div class="BoardDetailButton">	
<%
Customer c = (Customer) session.getAttribute("loginInfo"); 
%>
<%
if (session.getAttribute("loginInfo") != null) { 
%>
<%
int uAuthCode = c.getUAuthCode(); 
String uNickName = c.getUNickName(); 
%>
	
     <% if(uNickName.equals(brdUNickname) || uAuthCode == 0 ) {%>
		<button class="board_modify" id="<%=brdIdx %>"  style= "visibility:visible">글 수정</button>
         <button class="board_remove" id="<%=brdIdx %>"  style= "visibility:visible">글 삭제</button>
		<button class="board_list">글 목록</button> 
		<%} else{%> 
		 <button class="board_modify" id="<%=brdIdx %>"  style= "visibility:hidden">글 수정</button>
         <button class="board_remove" id="<%=brdIdx %>"  style= "visibility:hidden">글 삭제</button>
		 <button class="board_list">글 목록</button> 
		<%
		}
		%>
		
<%} else {  %>
<script>location.href="./";</script>
<%} %>

	</div>		
 </div> 
    			
         
  
 <!--게시글 끝-->     
       
       
<div class="commentwrap">       
   <!-- 게시글에 달린 댓글 갯수 -->   
<div class="size">댓글 <%=pageDTO2.getTotalCnt()%> </div><br>
   <!-- 게시글에 달린 댓글 갯수 end-->   


    <!-- 댓글작성 시작 -->
 
 <fieldset>
    <form method="post" action="./cmtadd" autocomplete="off">
   		 <div class="textarea"><textarea rows="4" cols="130" style="resize:none;" name="cmtContent" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
   			 <button class="comment_add" >댓글 등록</button>
   		<input class="cmtParentIdx" name="cmtParentIdx" value="0">	 
    </form>
</fieldset>
    <!-- 댓글작성 끝 -->
         	  

         	  
         	  
         <!-- 댓글 시작 -->	  
         	
       
         	<%if(pageDTO2.getTotalCnt() != 0) {%>
	         	<% for(Comment comment: comments) {       	
	         			int cmtIdx = comment.getCmtIdx();
	         			int cmtParentIdx = comment.getCmtParentIdx();
	        	   		String cmtContent = comment.getCmtContent();         	   		
	        	   		Date cmtCreateAt = comment.getCmtCreateAt();
	        	   		String cmtUNickName = comment.getCmtUNickName();       	   		
	         	%>    
	         	<!-- 대댓글 시작 -->
	         	<%if(cmtParentIdx != 0) {%> &emsp;&emsp;
	         	<div class="commentwrap2">
	         	<div class="community_comment" id="<%=cmtIdx%>">

	         		   		<span class="cmt" id="cmtIdx"><%=cmtIdx %></span><strong><div class="cmt"><%=cmtUNickName %><span class="mywrite"><%if(brdUNickname.equals(cmtUNickName)){ %>글쓴이<%} %></span></div></strong>
								<div class="cmt"><span id="cmtUNickNameReply"></span><%=cmtContent %></div> 
								<div class="cmt"><%=sf.format(cmtCreateAt) %></div>
								
								
							<div class="community_comment_button">
								<%if(! c.getUNickName().equals(cmtUNickName)) {%> 
									<button class="comment_comment_add" id="<%=cmtIdx %>">댓글 달기</button>
								<%} %> 	
		         				 <%if(c.getUNickName().equals(cmtUNickName)) {%> 
			         				<button class="comment_modify" id="<%=cmtIdx %>">수정</button>
									<button class="comment_remove" id="<%=cmtIdx %>">삭제</button>
								 <%} %> 
	         				</div>

							<div class="comment_modify_input" id="<%=cmtIdx%>">
								<input style="resize:none;" name="cmtContent" id="<%=cmtIdx %>" value="<%=cmtContent%>" required>
								<button class="comment_modify_complete">수정</button>	
							</div>
							<form method="post" action="./cmtadd" autocomplete="off">
								<div class="comment_comment_input" id="<%=cmtIdx%>">
							   		 <div class="textarea"><textarea rows="2" cols="50" style="resize:none;" name="cmtContent" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
							   			 <button class="comment_comment_add_complete" >등록</button>
							   		<input class="cmtParentIdx" name="cmtParentIdx" value=<%=cmtIdx%>>	
								</div>
							</form>
							</div>
						</div>
	         
	         	
	         	
					         	
	         	
	         	<!-- 대댓글 끝 -->
	         	<!-- 댓글 시작 -->
	         	<%} else{%>   
	         			 <div class="community_comment"id="<%=cmtIdx%>">
	         			 
	         			 <span class="cmt" id="cmtIdx"><%=cmtIdx %></span><strong><div class="cmt"><%=cmtUNickName %><span class="mywrite"><%if(brdUNickname.equals(cmtUNickName)){ %>글쓴이<%} %></span></div></strong> 
	         		  <div id="cmtUNickName" style="display:none"><%=cmtUNickName %></div>
	         		   <div class="cmt"><%=cmtContent %></div>
	         		   <div class="cmt"><%=sf.format(cmtCreateAt) %></div>
	         		   	<div class="community_comment_button">
	         		   	<%if(! c.getUNickName().equals(cmtUNickName)) {%> 
	         		   		<button class="comment_comment_add" id="<%=cmtIdx %>">대댓글 달기</button>   
	         		   	<%} %>	
	         		   		<%if(c.getUNickName().equals(cmtUNickName)) {%> 
		         		   		<button class="comment_modify" id="<%=cmtIdx %>">수정</button>
			         		 	<button class="comment_remove" id="<%=cmtIdx %>">삭제</button>
		         		 	 <%} %> 
		         		 </div>
		         		 	<div class="comment_modify_input" id="<%=cmtIdx%>">
								<input style="width:300px;height:30px; resize:none;" name="cmtContent" id="<%=cmtIdx %>" value="<%=cmtContent%>" required>
								<button class="comment_modify_complete">수정</button>
							</div>
							<form method="post" action="./cmtadd" autocomplete="off">
								<div class="comment_comment_input" id="<%=cmtIdx%>">
							   		 <div class="textarea"><textarea rows="2" cols="50" style="resize:none;" name="cmtContent" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
							   			 <button class="comment_comment_add_complete" >등록</button>
							   		<input class="cmtParentIdx" name="cmtParentIdx" value=<%=cmtIdx%>>	
								</div>
							</form>
	         		   </div>
	        	
	         		   
	         		   
	         		   
	         	<%
	         	}
	         	%>
	         	<!-- 댓글 끝 -->	      
	         	<%
	         	}
	         	%>
	         	
		<%} else{%>
					<span>댓글이 없습니다.</span>
				
			<%} %> 
<br>
<br>			
<%if(pageDTO2 != null) {%>
<div class="pagegroup">
		 <%  
		 String backContextPath = request.getContextPath();
		 if(pageDTO2.getStartPage() > 1){%>			
		 	<span class="<%= backContextPath%><%=pageDTO2.getUrl()%>/<%=pageDTO2.getStartPage()-1%> active">prev</span>
		 <%} %>
 
 		<%	for(int i = pageDTO2.getStartPage() ; i<=pageDTO2.getEndPage() ; i++){ %>
			<span class="<%= backContextPath%><%=pageDTO2.getUrl() %>/<%=i%> <%if(i != pageDTO2.getCurrentPage()){ %>active<%}%>"><%=i%></span>
		<%}%>
		
		<% 
		if(pageDTO2.getEndPage() < pageDTO2.getTotalPage()){%>
			<span class="<%= backContextPath%><%=pageDTO2.getUrl()%>/<%=pageDTO2.getEndPage()+1%> active">next</span>
		<%} %>
</div>
<%} %>

                  	
</div>
    <!--댓글 끝--> 	
	  	         	



	  	         	



 