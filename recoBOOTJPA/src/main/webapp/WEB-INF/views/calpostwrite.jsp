<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="com.reco.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.reco.calendar.vo.CalPost" %>
<%@page import="com.reco.calendar.vo.CalInfo" %>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.List"%>


<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	
<!-- 	<script src="./js/calendar_write.js"></script> -->
	<!-- <script src="./js/imgpreview.js"></script> -->
	<title>calpostwrite.jsp</title>
	   <style>
        .dellink{
          display: none;
        }
      </style>
	
	<link href="./css/calendar_write.css" rel=stylesheet>
	<script src="./js/calpostwrite.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<script>
	
		$(function(){
			
			//오늘날짜값 받아오기
			/* document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10); */ //외국시간기준?? 
			document.getElementById('currentDate').valueAsDate = new Date();

			
			//작성완료 버튼 클릭시, 캘린더 리스트 보기 
			addCalPostClick();
			
			//캘린더보기 버튼 클릭시 캘린더 리스트(calpostlist) 다시보기 
			calPostViewClick();	
			
		});
	</script>
</head>

<%
Customer c = (Customer)session.getAttribute("loginInfo");
String calCategory = request.getParameter("calCategory");
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
int uIdx  = c.getUIdx();
int calIdx = Integer.parseInt(request.getParameter("calIdx"));

%>

<body>
	<h2><%=calIdx%><%=calCategory %>&nbsp;캘린더 글 작성</h2>
	<form name="writeFrm" method="post" enctype="multipart/form-data" >
	     <input type="hidden" name="calIdx" value="<%=calIdx %>">
	     <input type="hidden" name="calCategory" value="<%=calCategory %>">
	   		<table style="margin-left: auto; margin-right: auto;" border="1" width="90%">
		
		    <tr>
		        <td>대표이미지</td>
		        <td>
		             <style>.dellink{display: none;}</style>
		             <script type="text/javascript" src="./js/imgpreview.js"></script>
		             <div class="image-container">
                      <img style="width: 300px;" id="preview-image" src="https://dummyimage.com/200x200/ffffff/000000.png&text=preview+image">
                      <input style="display: block;" type="file" name="calMainImg" id="input-image"accept="image/jpeg, image/jpg, image/png"  >
                     </div>
                     	
		             <a href="javascript:void(0);" class="dellink">대표이미지삭제</a>    
		        </td>
		    </tr>
		  
		       <td>날짜</td>
		       <td>
		       	<input type="date" id="currentDate" name="calDate">
		       
	<!-- 	          <script> -->
	<!--             var dateChange = () => {
		            var date_input = document.getElementById("date");
		            text_input.value = date_input.value;
	 	            };--> 
	<!-- 	           </script> -->
		       </td>
		    </tr>
		    <tr>
		        <td>리뷰/메모</td>
		        <td>
		            <textarea name="calMemo" cols="150" rows="30" placeholder="500자까지 자유작성/필수입력사항/캘린더에 작성하는 내용은 본인만 볼수 있다." ></textarea>
		            
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="center">
		           
		            <button type="submit" >작성 완료</button>
		            <button type="button" id="listcal" >캘린더보기</button>  
			            <%-- <input type="hidden" th:value="${calIdx}" th:name="calIdx" /> --%>
		           
		        </td>
		    </tr>
		</table>    
	</form>



</body>
