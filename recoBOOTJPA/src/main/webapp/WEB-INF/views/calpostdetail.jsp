<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>
	<script>location.href="./";</script>
<%
	return;
}else{
%>


<%
CalPost calpost = (CalPost)request.getAttribute("calpost");

if(calpost == null){ //컨트롤러에서 실패된 경우 
	return;
}

String msg = (String)request.getAttribute("msg");
int uIdx  = c.getUIdx();
String calDate = calpost.getCalDate();
String calIdx = request.getParameter("calIdx");
String rDate = request.getParameter("rDate"); //null

String imageFileName = "s_cal_"+uIdx+"_"+calIdx+"_"+calDate+".jpg"; //썸네일 불러오는 파일명 지정.
%>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
<script>
$(function(){
	   /*이미지 태그 보여주기*/
	   let $img = $('div.calpostdetail>form.f>div.thumnail>img.calMainImg');
	   $img.each(function(i, element){
	      let imgId = $(element).attr('id');   
	      $.ajax({
	         url: './calendardownloadimage?imageFileName='+imgId,
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
	   /*이미지 보여주기 */
	  });


$(function(){
		$('div.calpostdetail>form.f').submit(function(){
			console.log("수정하기버튼클릭!");
			let ajaxUrl = 'calpostmodifypage';
			let calIdx = <%=request.getParameter("calIdx")%>;
			let calMemo = $("input[name=calMemo]").val();
			
			let data = {calIdx : calIdx,
					calMemo:calMemo
			}
			$.ajax({
				url : ajaxUrl,
				method : 'post',
				data: formdata,
				success: function(responseData){
					 let $articlesObj = $('section>div.articles');
		              $articlesObj.empty();
		              $articlesObj.html(responseData);
				}
			});
			return false;
		});
		
	});
		
		//캘린더 삭제하기 버튼 클릭했을때
		 removeCalPostClick();
			 
   });
</script>


<div class="calpostdetail">
   <h2>캘린더<%=calIdx %> 글 상세보기</h2>
   <p align="left" >글등록날짜 : <%=calDate %></p>
   
	<form class="f">
	<input type="hidden" name="calIdx" value="<%=calIdx %>">
	<div class="thumnail">
         <%-- <p>현재 캘린더 : <%=calCategory %></p> --%>
         <p>현재 등록된 이미지 : <%=imageFileName %></p>
         <img id="<%=imageFileName %>" class="calMainImg" title="calMainImg" width="300" height="300">
      </div>
	
		<ul>
			
			<li>
			<lable for = "calMemo">리뷰/메모</lable>
			<textarea cols="40" rows="4" name="calMemo" placeholder="<%=calpost.getCalMemo() %>"></textarea>
			</li>
			
			<li>
			<lable for = "calMainImg">메인이미지</lable>
			<input class="calMemo" value="<%=imageFileName %>">
			</li>
		
		</ul>
		
		      <button type="submit">수정하기</button>
		      <button type="button">삭제하기</button>
				
	</form>
</div>

<%} //end if(c == null) %>