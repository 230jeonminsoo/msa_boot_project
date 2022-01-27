<%@page import="java.io.File"%>
<%@page import="com.my.calendar.vo.CalInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.my.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<link rel="stylesheet" href="./css/title_list.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
$(function(){
	/*---두번째 div에서  모든 img태그 보여주기 START--*/
	let $img = $('div.calIdx img');
	$img.each(function(i, element){
		let imgId = $(element).attr('id');	
		$.ajax({
			url: './download?filename='+imgId,
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

	String saveDirectory = "d:\\files";
	File dir = new File(saveDirectory);
	File[] files = dir.listFiles();
	
	List<CalInfo> list = (List)request.getAttribute("list");
	int uIdx = c.getUIdx();
	
	for(CalInfo ci : list){
		int calIdx = ci.getCalIdx();
		String thumbFileName = "cal_post_" + uIdx +"_" + calIdx + "." + ci.getCalThumbnail();
	/*	boolean isExist = false;		
		for(File f: files){
			String name = f.getName(); //파일이름
			if(name.equals(thumbFileName)){
				isExist = true;
				break;
			}
	*/			//Path path = f.toPath();
				//String contentType = Files.probeContentType(path); //파일형식 text/html, image/jpeg
	//			if(!contentType.contains("image")){
%> 	

<li>
	<div id="<%=calIdx %>" class="calIdx"> 
	  <div class="title_wrap" id="title5">
	    <a href="#"> <!-- 썸네일 -->
	     	<img id="<%=thumbFileName %>" alt="ADD" title="ADD">
	    </a>
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
	    <a href="#"> <!-- 캘린더 생성안된 add -->
	    <!-- <a href="calwrite.jsp" onclick="window.open(this.href, '_blank','top=300, left=600, width=500px, height=500px');return false;"> -->
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
}
%>