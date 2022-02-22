<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%Customer c = (Customer)session.getAttribute("loginInfo");
int uIdx =0;

if(c!=null){
	uIdx = c.getUIdx();
	
}
%>   

<link href="./css/myprivate.css" rel=stylesheet>   
<script src="./js/myprivate.js"></script>    

<script>
$(function(){
	uIdx = "<%=uIdx%>";
	
	modifypwdBtClick(uIdx);
	withdrawBtClick(uIdx);

});

</script>


	<%
	if (session.getAttribute("loginInfo") != null) { 
	%>

	<div class=myprivate>   
		<div class="modifypwd">    
			<h1 class="info">비밀번호 변경</h1>                     
			새비밀번호 : <input type="password" name="pwd" required><br>
			새비밀번호확인 : <input type="password" name="pwd1" required><br>
			<button class="modifypwdbutton">확인</button>
		</div>
		
		<div class="withdraw">       
			<h1 class="info">회원탈퇴</h1>
			현재 비밀번호 : <input type="password" name="pwd" required><br>
			현재 비밀번호 확인 : <input type="password" name="pwd1" required><br>
			<button class="withdrawbutton">확인</button>
		</div>
		
		<div class="kakaowithdraw">       
			<h1 class="info">카카오 가입회원 탈퇴</h1>
			<!-- <button class="kakaowithdrawbutton">확인</button>  -->
			
			<a href="https://kauth.kakao.com/oauth/authorize?client_id=2177f6a7b4ac54c3449ddca01ead7def&redirect_uri=http://localhost:9998/recoBOOTJPA/kakaoexit&response_type=code">카카오 간편가입 회원탈퇴 </a>

		</div>
	</div>

	<%} else{%>
		<script>location.href="./";</script>
	<%} %><!--if (session.getAttribute("loginInfo") 끝  -->	