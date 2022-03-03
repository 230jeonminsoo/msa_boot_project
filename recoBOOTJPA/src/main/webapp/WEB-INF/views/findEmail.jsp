<%@page import="com.reco.customer.vo.Customer"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%Customer c = (Customer)request.getAttribute("c"); %>
<link href="./css/findEmail.css" rel=stylesheet>
    <script src="./js/findEmail.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
    $(function(){
    	//주민번호 인증
    	cerRRN();
    	
    	//먼저 주민번호 필드 추가
    	//입력한 이름,주민등록번호로 기존 이메일 보내기
    	//API Key : NCSD2AAHIAUW3DUN	
		//API Secret : 2BQ7T5VHYNUYYCTBVYQQ08UMXOPLTMK6
		
		//참고사이트
		//https://1-7171771.tistory.com/84
		//https://developer.coolsms.co.kr/download

		
     	CNSend();
    });
	</script>
  	
   	<div class="findEmail">   
      
        <strong>이메일 찾기</strong>
        <h3>이메일를 잊으셨나요?</h3>
        <span>자신의 주민등록번호와 이름을 적어주세요.</span> 
        <br>  
        <br>
        <!-- 먼저 주민번호로 회원확인후 2단계로 휴대폰 본인인증 -->
        <input type="text" name="myName" required placeholder="이름">
        <input type="number" name="RRN" required placeholder="-없이 주민등록번호를 입력해주세요" value="4444444444444">
        <br>    
        <br>
        <button class="RRN"> 주민번호 본인인증 </button><br><br><br>          
        <div class="CNSend">
       		<input type="text" name="phoneNumber" required placeholder="-없이 핸드폰번호를 입력해주세요"><br><br>
       		<button class="RRN"> 휴대폰 본인인증 </button><br><br><br>
       		
       		<input type="text" name="CN" required placeholder="인증번호를 입력해주세요"><br><br>
       		<button class="CN">확인</button><br><br><br>
       		이메일 : <input type="text" name="email" value="" readonly="readonly">
        	
     
        </div>
          
     </div>     


