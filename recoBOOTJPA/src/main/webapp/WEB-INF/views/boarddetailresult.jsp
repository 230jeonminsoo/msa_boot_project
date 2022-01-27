<%@page import="com.my.customer.vo.Customer"%>
<%@page import="java.util.List"%>
<%@page import="com.my.board.vo.Comment"%>
<%@page import="com.my.board.vo.Board"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


 <link href="./css/board_content.css" rel=stylesheet>
 <script src="./js/boarddetail.js"></script>



 
<%
Board b = (Board)request.getAttribute("b");
int brdIdx = b.getBrdIdx();
int brdType = b.getBrdType();
String brdTitle = b.getBrdTitle();
String brdUNickname = b.getBrdUNickName();
int brdViews = b.getBrdViews();
int brdThumbUp = b.getBrdThumbUp();
Date brdCreateAt = b.getBrdCreateAt();
String brdContent = b.getBrdContent();
String brdAttachment = b.getBrdAttachment();
List<Comment> comments = b.getComments();
%> 


<script>
$(function(){
	let $formObj = $('fieldset>form');
	//게시글 수정버튼 클릭시
		boardModifyClick();
	//게시글 삭제버튼 클릭시
		boardRemoveClick();	
	//게시글 목록버튼 클릭시
		boardListClick();
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
					<span id="brdIdx" name="brdIdx">
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
		             <%=brdCreateAt %>
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
<script>location.href="index.jsp";</script>
<%} %>

	</div>		
 </div> 
    			
         
  
 <!--게시글 끝-->     
       
       
<div class="commentwrap">       
   <!-- 게시글에 달린 댓글 갯수 -->   
<div class="size">댓글 <%=comments.size()%> </div><br>
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
         	
         	
         	<%if(comments.size() != 0) {%>
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

	         		   		<span class="cmt" id="cmtIdx"><%=cmtIdx %></span><strong><div class="cmt"><%=cmtUNickName %></div><div class="cmt"><%=cmtCreateAt %></div></strong> 
								<div class="cmt"><%=cmtContent %></div> 
							
							<div class="community_comment_button">
								<button class="comment_comment_add" id="<%=cmtIdx %>">대댓글 달기</button>
		         				<button class="comment_modify" id="<%=cmtIdx %>">대댓글 수정</button>
								<button class="comment_remove" id="<%=cmtIdx %>">대댓글 삭제</button>
	         				</div>

							<div class="comment_modify_input" id="<%=cmtIdx%>">
								<input style="resize:none;" name="cmtContent" id="<%=cmtIdx %>" value="<%=cmtContent%>" required>
								<button class="comment_modify_complete">대댓글 수정</button>	
							</div>
							<form method="post" action="./cmtadd" autocomplete="off">
								<div class="comment_comment_input" id="<%=cmtIdx%>">
							   		 <div class="textarea"><textarea rows="2" cols="50" style="resize:none;" name="cmtContent" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
							   			 <button class="comment_comment_add_complete" >대댓글 등록</button>
							   		<input class="cmtParentIdx" name="cmtParentIdx" value=<%=cmtIdx%>>	
								</div>
							</form>
							</div>
						</div>
	         
	         	
	         	
					         	
	         	
	         	<!-- 대댓글 끝 -->
	         	<!-- 댓글 시작 -->
	         	<%} else{%>   
	         			 <div class="community_comment"id="<%=cmtIdx%>">
	         			 
	         			 <span class="cmt" id="cmtIdx"><%=cmtIdx %></span><strong><div class="cmt"><%=cmtUNickName %></div> <div class="cmt"><%=cmtCreateAt %></div></strong> 
	         		   <div class="cmt"><%=cmtContent %></div>
	         		   	<div class="community_comment_button">
	         		   		<button class="comment_comment_add" id="<%=cmtIdx %>">대댓글 달기</button>
	         		   		<button class="comment_modify" id="<%=cmtIdx %>">댓글 수정</button>
		         		 	<button class="comment_remove" id="<%=cmtIdx %>">댓글 삭제</button>
		         		 </div>
		         		 	<div class="comment_modify_input" id="<%=cmtIdx%>">
								<input style="width:300px;height:30px; resize:none;" name="cmtContent" id="<%=cmtIdx %>" value="<%=cmtContent%>" required>
								<button class="comment_modify_complete">댓글 수정</button>
							</div>
							<form method="post" action="./cmtadd" autocomplete="off">
								<div class="comment_comment_input" id="<%=cmtIdx%>">
							   		 <div class="textarea"><textarea rows="2" cols="50" style="resize:none;" name="cmtContent" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
							   			 <button class="comment_comment_add_complete" >대댓글 등록</button>
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
                  	
</div>
    <!--댓글 끝--> 	
	  	         	



	  	         	



 