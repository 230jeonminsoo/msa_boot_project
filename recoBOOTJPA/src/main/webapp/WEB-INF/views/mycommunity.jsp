<%@page import="com.reco.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%Customer c = (Customer)session.getAttribute("loginInfo"); %>    
<script src="./js/mycommunity.js"></script>

<script>
$(function(){
	
});
</script>    

<fieldset>
	<!-- 작성한 공지사항 글 (관리자가 아닐경우 보이지않음). 누를시 새탭에 띄울예정-->
	<%if(c.getUAuthCode() == 0){ %>
	<h1>내가 작성한 공지사항</h1>
	<hr>
	<table>
		<tr>
			<th>제목</th>
			<th></th>
			<th>작성일</th>
			<th>추천수</th>
		</tr>
		<tr>
			<td>1111</td>
			<td>와와 축하해주세요. </td>
			<td>2021.12.11</td>
			<td>1</td>
		</tr>
	</table>
	<%} %>
	
	<!-- 내가 작성한 게시글. 누를시 새탭에 띄울예정-->
	<h1>내가 작성한 게시글</h1>
	<hr>
	<table>
		<tr>
			<th>제목</th>
			<th></th>
			<th>작성일</th>
			<th>추천수</th>
		</tr>
		<tr>
			<td>1111</td>
			<td>와와 축하해주세요. </td>
			<td>2021.12.11</td>
			<td>1</td>
		</tr>
	</table>
	
	<!-- 내가 작성한 댓글. 누를시 새탭에 띄울예정-->
	<h1>내가 쓴 댓글</h1>
	<hr>
	<table>
		<tr>
			<th>댓글</th>
			<th></th>
			<th></th>
			<th>작성일</th>
		</tr>
		<tr>
			<td>1111</td>
			<td>와와 축하해주세요. </td>
			<td>2021.12.11</td>
			<td>1</td>
		</tr>
	</table>	
</fieldset>    