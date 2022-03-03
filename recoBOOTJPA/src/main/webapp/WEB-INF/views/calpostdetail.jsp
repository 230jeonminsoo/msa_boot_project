<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%
CalPost calpost = (CalPost)request.getAttribute("calpost");
if(calpost == null){ //컨트롤러에서 실패된 경우 
	return;
}
%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
<script>
$(function(){
	$('div.calpostdetail>form.f').submit(functio(){
		let ajaUrl = 'calpostmodify';
		let calIdx = <%=request.getParameter("calIdx")%>;
		let calMemo = $("input[name=calMemo]").val();
		
		let data = {calIdx : calIdx,
				calIdx:calIdx,
				calMemo:calMemo
		}
		$.ajax({
			url : ajaUrl,
			method : post,
			data: data, // formdata
			success: function(res){
				 let $articlesObj = $('section>div.articles');
	              $articlesObj.empty();
	              $articlesObj.html(responseData);
			}
		});
		return false;
	});
	
});
</script>
<div class="calpostdetail">
<form class="f">
<input type="text" name="calMemo" value="<%=calpost.getCalMemo() %>">
<input type="submit" value="수정하기">
</form>
</div>