<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<link href="./css/myprivate.css" rel=stylesheet>   
   
<div class=myprivate>   
	<div class="modifypwd">    
		<h1 class="info">비밀번호 변경</h1>                     
		새비밀번호 : <input type="password" name="pwd"><br>
		새비밀번호확인 : <input type="password" name="pwd1"><br>
		<button class="button" type="submit">확인</button>
		<button type="reset" class="button_cancel">취소</button>
	</div>
	
	<div class="withdraw">       
		<h1 class="info">회원탈퇴</h1>
		현재 비밀번호 : <input><br>
		현재 비밀번호 확인 : <input type="password" name="pwd"><br>
		<button class="button" type="submit">확인</button>
		<button type="reset" class="button_cancel">취소</button>
	</div>
</div>	