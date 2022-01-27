<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
%>

<link href="./css/noticewrite.css" rel=stylesheet>
<script src="./js/noticewrite.js"></script> 
<title>noticewrite.jsp</title>
<script>
$(function(){
	let $formObj = $('fieldset>form');
	let $addNoticeBt = $('fieldset>form>input[type=submit]');
	//새로운 글 추가 후 작성한 글 호출
	noticeSubmit($formObj);
	//저장취소버튼 클릭시 리스트 다시보기
	modifyCancelBtClick();
	//파일 업로드 
	uploadNoticeFile();
});
</script>

<fieldset>
	<form method="post" action="./ntcadd" autocomplete="off">
		<h1>공지사항 작성</h1>
		<table>
			<tr><td>날짜</td> <td><%= sf.format(nowTime)%></td></tr>
		</table>   
		<textarea rows="2" cols="100" style="resize:none;" name="ntcTitle" id="ntcTitle" placeholder="제목을 입력해주세요." required></textarea>              
		<table>
			<tr><td><textarea rows="20" cols="100" style="resize:none;" name="ntcContent" id="ntcContent" placeholder="내용을 입력해주세요. 욕설/비방이 섞인글은 삭제될 수 있습니다." required></textarea></td></tr>		
		</table>
		<button class="fileUpload">글저장</button>
		<button class="addcancel">저장취소</button>
	</form>
</fieldset>
