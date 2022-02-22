<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <head>
    <!-- <link href="./css/signup.css" rel=stylesheet> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="./js/kakaologin.js"></script>
   
   
   
<%
String email = (String)request.getAttribute("email");
String pwd = (String)request.getAttribute("pwd");
String code = (String)request.getAttribute("code");
%>

   
    <script>
    
      $(function(){
    	 
    	 /* let $nicknameObj = $('div.signup>form>input[name=nickname]'); */
    	/* let $formObj = $('div.signup>form'); */
         //카카오 로그인 버튼 클릭
        	kakaoSignUpClick();
         
         
      });
    </script>
    
  </head>
  <body>
    
  <div class="signup"> 
	<form>       
		<h3>카카오 간편회원가입</h3>
		<!-- 카카오가입시 name은 사용안함 -->
		
		<input type="text" name="nickname" id="nickname" required placeholder="닉네임을 입력해주세요">		
		<span id="code" style="visibility:visible"><%=code%></span>
		<span id="email" style="visibility:visible"><%=email%></span>
		<span id="pwd" style="visibility:visible"><%=pwd %></span>
		<button class="kakaoLogin" type="submit">가입</button>
		<button type="reset">CLEAR</button>
	</form>             
   </div>
                          
  </body>
 