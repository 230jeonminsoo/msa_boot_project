<%@page import="com.reco.customer.vo.Customer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
Customer c = (Customer)session.getAttribute("loginInfo");%>
<!DOCTYPE html>
<html>
<head>
	<title>RECO</title>
	<meta charset="UTF-8">

	<link rel="stylesheet" href="./css/header.css">
	<link rel="stylesheet" href="./css/section.css">
	<link rel="stylesheet" href="./css/footer.css">

	<style>
	
	    html, body {
		  margin: 0;
		  padding: 0;
		}
		
		#wrap {
		  min-height: 100vh;
		  position: relative;
		  width: 100%; 
		}
		
	</style>

<%
String email = (String)request.getAttribute("email");
String pwd = (String)request.getAttribute("pwd");
String code = (String)request.getAttribute("code");
%>




	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="./js/menu.js"></script><!-- menu.jsp 이벤트-->
	<script src="./js/index.js"></script><!-- index.jsp 이벤트 -->


	<script>
	$(function(){
		
		<%if(email != null) {%>
			email = "<%=email%>";
			pwd = "<%=pwd%>";
			code = "<%=code%>";
			$.ajax({
				url: "./kakaopopup",
				method:'get',
				data: {email:email, pwd:pwd, code:code} , 
				success:function(responseData){
					let $articlesObj = $('section>div.articles0');
	               	 $articlesObj.empty();
	                 $articlesObj.html(responseData);
			         window.scrollTo(0, 0);
				}
			});
		<%}%>
		
		//로그인전은 before.html, 로그인 후는 callistresult.jsp로드
		loadBeforeAfter();

		//로그인메뉴버튼클릭시
		//loginClick();

		//회원가입메뉴버튼시클릭
		//signupClick();

		//communityClick();
		//로그인,회원가입 제외 각메뉴 클릭시 발생하는 이벤트
	    menuClick();

	  });
	</script>

</head>


<body>
	
	 <header>
	 	  <h1 class ="logo" >
		      	<a href="./" >RECO</a>
		  </h1>

	      <nav>
	        <jsp:include page="./menu.jsp"/>
	      </nav>
     </header>

	<div id="wrap">
		<section>
			<div class="articles0">
			<%--로그인 성공시 callistresult가 이곳에 나타남--%>
			</div>
		</section>
	
		<div style="position:fixed; bottom:100px; right:50px;">
			<a href="#"><img style="width:70px ;height:70px" src="./images/navi_top.png" title="위로 가기"></a>
		</div>
		<hr>
		<footer>
		     	<%@include file="./footer.jsp" %>     <!--맺음말-->
		</footer>
	</div>
</body>
</html>
