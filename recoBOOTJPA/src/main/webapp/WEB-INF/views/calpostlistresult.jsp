<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.calendar.vo.CalInfo"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
String calCategory = request.getParameter("calCategory");
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
int uIdx  = c.getUIdx();
/* String calIdx = request.getParameter("calIdx"); */
/* int calIdx = ci.getCalIdx();   */
int calIdx = Integer.parseInt(request.getParameter("calIdx"));
String calDate = request.getParameter("calDate");
String calMemo = request.getParameter("calMemo");
String calMainImg = request.getParameter("calMainImg");
String saveDirectory = "c:\\reco\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles(); 
%>

<link rel=stylesheet href="./css/calendar.css" >
<script src="./js/calendar.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


<script>
	$(function(){
		/*--- div.calMainImg에서  모든 img태그 보여주기 START--*/
		let $img = $('div.main img');
		$img.each(function(i, element){
			let imgId = $(element).attr('id');	
			$.ajax({
				url: './calendar/downloadimage?thumbnailName='+imgId,
				 cache:false,
		         xhrFields:{
		            responseType: 'blob'
		        }, 
		        success: function(responseData, textStatus, jqXhr){
		        	let contentType = jqXhr.getResponseHeader("content-type");
		        	let contentDisposition = decodeURI(jqXhr.getResponseHeader("content-disposition"));
		       		var url = URL.createObjectURL(responseData);
		       		$(element).attr('src', url); 
		        },
		        error:function(){
		        }
			}); //end $.ajax
		});//end each
		/*---두번째 div에서  모든 img태그 보여주기 END--*/
		
		//달력에서 날짜 클릭시 발생하는 이벤트 
		dateClick(); /* calendar.js */
			   
	});
</script>


	<%
		List<CalPost> list = (List)request.getAttribute("list");
             		
         if( list != null) {
	      for(CalPost cp : list){
		  String Date = cp.getCalDate();
						}
	} %>
					
		<%if( list == null){ %>
		<a href="calpostlistresult.jsp"></a>
		<%} %>


<div class="calCategory"> 
	<br><p align="center">-&nbsp;<%=calCategory %>&nbsp;캘린더&nbsp;-</p> 
	<%-- <p><%=calIdx %></p> --%>
</div>

<div style = "float:left; width:30%; text-align:right;"> 
	  <button type = "button" onclick="calpostWrite()"> 캘린더글등록 </button>
	</div>


<div class="container">
      <div class="body">
        <div class="calendar">
        	  <form>
			  	<input type='month' id='currnetMonth' style="display:none" >
			  </form>
          <div class="header">
              <div class="year-month" style="display: block;" ></div>          
              <div class="nav">
                  <button class="nav-btn go-prev" onclick="prevMonth()">&lt;</button>
                  <button class="nav-btn go-today" onclick="goToday()">Today</button>
                  <button class="nav-btn go-next" onclick="nextMonth()">&gt;</button>
              </div>
          </div>

	<script>
			//
            var dateChange = () => {
            var date_input = document.getElementById("date");
            //var text_input = document.getElementById("text");
            text_input.value = date_input.value;
            }; 
            
               		
    		//달력에서 캘린더글등록 클릭시 발생하는 이벤트
    		function calpostWrite() {  	 	 
            //화면기준 팝업 가운데 정렬
    		var w = (window.screen.width/2) -100;
    		var h = (window.screen.height/2) -100;
    		var url = "calpostwrite?uIdx=<%=uIdx%>&calIdx=<%=calIdx%>&<%=calCategory %>";
    		window.open(url, "calpostwrite", "width = 800, height=800,left="+w+", top="+h); 
    	}
     </script>
     


          <div class="main">
              <div class="days">
                  <div class="day">일</div>
                  <div class="day">월</div>
                  <div class="day">화</div>
                  <div class="day">수</div>
                  <div class="day">목</div>
                  <div class="day">금</div>
                  <div class="day">토</div>
              </div>
              <a class="dates" <%=calMemo %> href ="<%=calCategory%>" id="<%=calIdx%>" >
   
	
         
			
              </a>
          </div>
      </div>
    </div>  
  </div>



 