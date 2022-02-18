<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.calendar.vo.CalInfo"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
		        } , 
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
Customer c = (Customer)session.getAttribute("loginInfo"); 
String calCategory = request.getParameter("calCategory");
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
int uIdx  = c.getUIdx();
/* int calIdx = (Integer)request.getAttribute("calIdx"); */
/* int calIdx = ci.getCalIdx(); */

String saveDirectory = "d:\\files\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles(); 

/* String calCategory = calinfo.getCalCategory(); */

%>

<div class="calCategory"> 
	<br><p align="center">-&nbsp;<%=calCategory %>&nbsp;캘린더&nbsp;-</p> 
	<%-- <p><%=calIdx %></p> --%>
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
     </script>
        <!-- <input type="date" id="date" onchange="dateChange();" /> -->
        <!-- <input type="text" id="text" /> -->

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
              <a class="dates" href ="./calpostlistresult" target="_blank">
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
					
					   
					
				<%-- <div id="<%=calMainjImg %>" class="calMainjImg"> 
					  <div class="dateImg" id="dateImg">
					    <a href="#"> <!-- 썸네일 -->
					     	<img id="<%=thumbnailName %>" alt="ADD" title="ADD">
					    </a>
					</div> --%>
              </a>
          </div>
      </div>
    </div>  
  </div>



 