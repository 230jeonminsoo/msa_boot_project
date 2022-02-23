<%@page import="java.io.File"%>
<%@page import="com.reco.calendar.vo.CalInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<link rel="stylesheet" href="./css/title_list.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="./js/callist.js"></script>

<script>
$(function(){
	/*---두번째 div에서  모든 img태그 보여주기 START--*/
	let $img = $('div.calIdx img');
	$img.each(function(i, element){
		let imgId = $(element).attr('id'); <%-- <%=imageFileName %>값 = "s_cal_post_" + uIdx  + "_" + calIdx + ".jpg"; --%>	
		$.ajax({
			url: './calendardownloadimage?imageFileName='+imgId,
			
			cache:false, //이미지 다운로드용 설정
	         xhrFields:{ //이미지 다운로드용 설정
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
		});  //end $.ajax
	});//end each
	/*---두번째 div에서  모든 img태그 보여주기 END--*/
	
});
</script>

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>
<%
}
%> 
<%
if(c != null){
	
	List<CalInfo> list = (List)request.getAttribute("list");
	int uIdx = c.getUIdx();

	String saveDirectory = "C:\\reco\\calendar";
	File dir = new File(saveDirectory);
	File[] files = dir.listFiles();
	
%>

<%	
	for(CalInfo ci : list){
		int calIdx = ci.getCalIdx();
		String imageFileName = "s_cal_post_" + uIdx  + "_" + calIdx + ".jpg";
%> 	
	
<li>
	<div id="<%=calIdx %>" class="calIdx"> 
	  <div class="title_wrap" id="title5">
	    <a href="#"> <!-- 썸네일 -->
	     	<img id="<%=imageFileName %>" alt="thumbnailName" title="thumbnailName">
	    </a>
	    <!-- 썸네일 우측상단에 수정페이지 연결되는 버튼 -->
	    <input class="modifyBt" type="image" src="./images/three_dots.png"  width="20px" height="20px">
	  </div>
	  
	  <div class="title_info">
	   	 <p class="title_front"><%=ci.getCalCategory() %></p>
	  </div>
	</div>
</li>

<%} //end for 
	for(int i=list.size(); i<5; i++){
%> 
<li>
	  <div class="title_add" id="title">
	    <a href="./html/calInfowrite.html"> <!-- 캘린더 생성안된 add -->
		      <img src="./images/add.png" alt="ADD" title="ADD">
		      <div class="hidden_title">
			      <div class="title_detail">
			      	<p class="title_name">+</p>
			      </div>
		   	 </div>
	    </a>
	  </div>
	  
	  <div class="title_info">
	    <p class="title_front">ADD</p>
	  </div>
</li>

<%}//end for
%>    
     
	
<%
} //end if
%>