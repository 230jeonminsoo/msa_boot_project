<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
%>

<link href="./css/noticewrite.css" rel=stylesheet>
<script src="./js/boardwrite.js"></script> 
<title>boardwrite.jsp</title>
<script>
$(function(){
	let $formObj = $('fieldset>form');
	let $addBoardBt = $('fieldset>form>input[type=submit]');
	//새로운 글 추가 후 작성한 글 호출
	boardSubmit($formObj);
	//저장취소버튼 클릭시 리스트 다시보기
	modifyCancelBtClick();
});
</script>

<fieldset>
	<form method="post" action="./brdadd" autocomplete="off">
		<h1>게시글 작성</h1>
		<table>
			<tr><td>날짜</td> <td><%= sf.format(nowTime)%></td></tr>
		</table>
		<br>   
		<select name="brdType">
			<option value="0">잡담</option>
			<option value="1">정보</option>
			<option value="2">기타</option>
		</select>
		<br>
		<textarea rows="2" cols="100" style="resize:none;" name="brdTitle" id="brdTitle" placeholder="제목을 입력해주세요." required></textarea>              
		<table>
			<tr><td><textarea rows="20" cols="100" style="resize:none;" name="brdContent" id="brdContent" placeholder="내용을 입력해주세요. 욕설/비방이 섞인글은 삭제될 수 있습니다." required></textarea></td></tr>		
		</table>
		<!--  <input type="file"  name="ntcattachment"   multiple><br>-->
		<input type="submit" value="글 저장">
		<button class="addcancel">저장취소</button>
	</form>
</fieldset>
