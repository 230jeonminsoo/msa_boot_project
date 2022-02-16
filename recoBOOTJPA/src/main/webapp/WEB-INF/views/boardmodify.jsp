<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
%>


<link href="./css/noticewrite.css" rel=stylesheet>
<script src="./js/boardmodify.js"></script> 

<title>boardmodify.jsp</title>
<%String image = (String)request.getAttribute("image"); %>
<%
String brdIdx = request.getParameter("brdIdx");
String brdType= request.getParameter("brdType");
String brdTitle= request.getParameter("brdTitle");
String brdContent= request.getParameter("brdContent");
String brdAttachment= request.getParameter("brdAttachment");
%>
<script>
$(function(){
	//이미지 다운로드후 보여주기
	<%if(image != null){%>
		let $img = $("fieldset.boardmodify>form>div.image>img");
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
	
	let $formObj = $('fieldset>form');
	let $modifyNoticeBt = $('fieldset>form>input[type=submit]');
	//글수정버튼클릭시 수정된글 보낸후 수정한 글 다시 보기
	modifyBoardSubmit($formObj);
	//수정취소버튼 클릭시 수정할려고 한글 다시보기
	modifyCancelBtClick();
});
</script>
	
	<%
	Customer c = (Customer) session.getAttribute("loginInfo"); 
	%>
	<%
	if (session.getAttribute("loginInfo") != null) { 
	%>
	<%} else {  %>
	<script>location.href="./";</script>
	<%} %>

<fieldset class="boardmodify">
	<form autocomplete="off">
		<h1>자유게시판 수정</h1>
		<table>
			<tr><td>날짜</td> <td><%= sf.format(nowTime)%></td></tr>
		</table>
		글번호: <input type ="text" id="brdIdx" name="brdIdx" value="<%=brdIdx %>" readonly>   
		<br>
	    분류: <span><select name="brdType">  
				<option id ="brdType"  value="0">잡담</option>
				<option id ="brdType"  value="1">정보</option>
				<option id ="brdType" value="2">기타</option>
			</select></span>
		<%if (image !=null){%><div class="image"><img style="width:300px; height:300px;"></div><%} %>
		<div><textarea rows="2" cols="100" style="resize:none;" name="brdTitle" id="brdTitle" placeholder="<%=brdTitle %>" required><%=brdTitle %></textarea></div>             
		<table>
			<tr><td><textarea rows="20" cols="100" style="resize:none;" name="brdContent" id="brdContent" placeholder="<%=brdContent %>" required ><%=brdContent %> </textarea></td></tr>	
			
			
		</table>
		<div class="data"><label>파일 첨부</label><input type="file" name="letterFiles"></div> 현재 저장된 첨부파일 : <%if(brdAttachment!=null){%> <%=brdAttachment %> <%}%><br>
		<button class="modifycancel">수정취소</button>
		<input type="button" value="글 수정">
	</form>
</fieldset>