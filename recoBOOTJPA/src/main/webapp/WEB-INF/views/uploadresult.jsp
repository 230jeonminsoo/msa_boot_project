<%@page import="java.nio.file.Files"%>
<%@page import="java.nio.file.Path"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uploadresult.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	/*---첫번째 div에서  a태그가 클릭되었을때 START--*/
	$('div.download>ul>li>a').click(function(){
		//class속성값얻기
		let contentType = $(this).attr('class');
		//class속성값은 파일형식으로 저장되어있다(예: image/jpeg, image/png, text/html)
		//class속성값이 이미지파일형식인 경우 무조건다운로드받지말고 응답결과를 img.src에 대입해준다
		if(contentType.indexOf('image') > -1){
			let ajaxUrl = $(this).attr("href");
			$.ajax({
	            url:ajaxUrl,
	            cache:false,
	             xhrFields:{
	                responseType: 'blob'
	            } , 
	            success: function(responseData, textStatus, jqXhr){
	            	let contentType = jqXhr.getResponseHeader("content-type");
	            	let contentDisposition = decodeURI(jqXhr.getResponseHeader("content-disposition"));
					console.log("content-type=" + contentType + ", content-disposition="+contentDisposition);
	           		var url = URL.createObjectURL(responseData);
					var $img = $('div.download>img');
	               	$img.attr('src', url);
	            },
	            error:function(){
	            }
	        });//end $.ajax
			return false;
		}//end if
	});
	/*---첫번째 div에서  a태그가 클릭되었을때 END--*/
	
	/*---두번째 div에서  모든 img태그 보여주기 START--*/
	let $img = $('div.download2>ul>li>img');
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
</head>
<body>
<h1>업로드 목록보기</h1>
<% 
String saveDirectory = "d:\\files";
File dir = new File(saveDirectory);
File[] files = dir.listFiles();
%>
<div class="download" style="border:1px solid; margin:10px;width:50%;">
  <h3>일반파일은 클릭하면 다운로드되고, 이미지파일은 클릭하면 img태그에 보여줍니다</h3>

  <img style="max-width: 50%; ">
   <ul>
<%
for(File f: files){
	String name = f.getName(); //파일이름
	Path path = f.toPath();
	String contentType = Files.probeContentType(path); //파일형식 text/html, image/jpeg
%>
     <li><a href="./download?filename=<%=name%>" class="<%=contentType%>"><%=name%></a></li>
<%}%>
   </ul>
</div>

<div class="download2" style="border:1px solid; margin:10px;width:50%;">
  <h3>일반파일은 클릭하면 다운로드되고, 이미지파일은 img태그에 보여줍니다</h3>

 <ul>
<%
for(File f: files){
	String name = f.getName(); //파일이름
	Path path = f.toPath();
	String contentType = Files.probeContentType(path); //파일형식 text/html, image/jpeg
%>
   <li>
   	<%if(!contentType.contains("image")){ %>
    	<a href="./download?filename=<%=name%>"  download="<%=name%>"><%=name%></a>
   <%}else{ %>
       <img id="<%=name%>"   style="max-width: 50%;">
   <%} %>
   </li>
<%}%>
 </ul>
</div>

</body>
</html>