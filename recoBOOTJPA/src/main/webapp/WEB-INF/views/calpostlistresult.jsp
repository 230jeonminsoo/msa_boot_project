<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>

<link rel=stylesheet href="./css/calendar.css" >
<script src="./js/calendar.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(function(){
		//달력에서 날짜 클릭시 발생하는 이벤트 
		dateClick();
	});
</script>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
List<CalPost> list = (List)request.getAttribute("list");
int uIdx = c.getUIdx();
%>

<%-- <%

	String saveDirectory = "d:\\files";
	File dir = new File(saveDirectory);
	File[] files = dir.listFiles(); 
	
	List<CalPost> list = (List)request.getAttribute("list");
	int uIdx = c.getUIdx();
	
	for(CalPost cp : list){
		int calIdx = cp.getCalinfo().getCalIdx();
		String mainImgFileName = "cal_post_" + uIdx +"_" + calIdx + "." + cp.getCalMainImg();
%> 	 --%>

<%-- <%	for(CalPost cp : list){
	/* int calIdx = cp.getCalInfo().getCalIdx();
	String calDate = cp.getCalDate();  */
	String calCategory = cp.getCalinfo().getCalCategory();
	/* String calMainImg = cp.getCalMainImg(); */
%>
<div class="calCategory"> 
		<p><%= calCategory %></p>
</div>

<% } %> --%>

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
              <a class="dates" href ="#" target="_blank"></a>
          </div>
      </div>
    </div>  
  </div>
<%-- 
<%} %> --%>

    
 