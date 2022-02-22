<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <head>
    <!-- <link href="./css/signup.css" rel=stylesheet> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="./js/signup.js"></script>
   
   
   
<%
String email = (String)request.getAttribute("email");
String pwd = (String)request.getAttribute("pwd");
%>

   
    <script>
    
      $(function(){
    	 let $submitBtObj = $('div.signup>form>button[type=submit]');
    	 let $formObj = $('div.signup>form');
    	
 
    	  
    	//가입클릭했을때 SRART  		
        	signupSubmit($formObj);
        //가입클릭했을때 END       
      });
    </script>
    
  </head>
  <body>
    
  <div class="signup"> 
	<form method="post" action="./signup" autocomplete="off">       
		<h3>카카오 간편회원가입</h3>
		<!-- 카카오가입시 name은 사용안함 -->
		
		<input type="text" name="nickname" id="nickname" required placeholder="닉네임을 입력해주세요">		
		<div style="visibility:hidden"><%=email%></div>
		<div class="password" style="visibility:hidden"><%=pwd %></div>
		<button type="submit">가입</button>
		<button type="reset">CLEAR</button>
	</form>             
   </div>
                          
  </body>
 