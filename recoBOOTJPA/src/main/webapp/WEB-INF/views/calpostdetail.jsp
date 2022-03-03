<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="com.reco.calendar.vo.CalInfo" %>
<%@page import="com.reco.calendar.dao.CalendarDAOOracle" %>
<%@page import="java.io.File"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<link rel="stylesheet" href="./css/calpostdetail.css">
<script src="./js/calpostdetail.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
String calIdx = request.getParameter("calIdx");
//String calCategory = request.getParameter("calCategory");
int uIdx  = c.getUIdx();
String calDate = calpost.getCalDate();
String calMainImg = calpost.getCalMainImg();
String calMemo = calpost.getCalMemo();

String imageFileName = "s_cal_"+uIdx+"_"+calIdx+"_"+calDate+".jpg"; //썸네일 불러오는 파일명 지정.
%>

	
<script>
$(function(){
   /*이미지 태그 보여주기*/
   let $img = $('div.thumbnail>img.MainImg');
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


<%-- $(function(){
		$('div.inputBT>button.modifycalpost').click(function(){
			console.log("수정페이지로 이동");		
			alert("수정페이지로 이동");
			/* console.log("수정페이지로 이동");
			if(confirm("해당 캘린더를 수정하시겠습니까?")==true){ //확인

			}else{ //취소 
				return false;
			} */
			
			let ajaxUrl = 'calpostmodifypage';
			//alert("수정페이지로 이동1");
			let calIdx = <%=request.getParameter("calIdx")%>;
			//let calMemo = $("input[name=calMemo]").val();
			
			//let data = {calIdx : calIdx, calMemo:calMemo }
			$.ajax({
				url : ajaxUrl,
				method : 'get',
				//processData: false, //파일업로드용 설정
				//contentType: false, //파일업로드용 설정
				//data: formdata,
				success: function(responseData){
					 let $articlesObj = $('section>div.articles');
		              $articlesObj.empty();
		              $articlesObj.html(responseData);
				},error:function(jqXHR){
					//location.href="calpostlistresult.jsp";
				}
				
			});
			return false;
	});
	
}); --%>
	//캘린더 수정페이지로 이동하는 이벤트 
	 calpostModiPageClick();

	 //캘린더 삭제하기 버튼 클릭했을때
	 //removeCalPostClick();
			 
</script>


<div class="calpostdetail">
    <h2>캘린더<%=calIdx %> 글 상세보기</h2>
    <p align="left" >글등록날짜 : <%=calDate %></p>
	
		
		<input type="hidden" name="calIdx" value="<%=calIdx %>">
		<div class="thumbnail">
	         <%-- <p>현재 캘린더 : <%=calCategory %></p> --%>
	         <p>(테스트)현재 등록된 이미지 : <%=imageFileName %></p>
			 <p>(테스트)현재 등록된 사진명 : <%=calMainImg %></p>
	         <img id="<%=imageFileName %>" class="MainImg" title="calMainImg" > 
	    </div>
		
		<ul>
			<li>
				<div class="calDate"> 작성날짜 : <span><%=calDate %></span></div>
				<div class="calMemo">리뷰/메모 : <span><%=calMemo %></span></lable>
				<%-- <textarea cols="40" rows="4" name="calMemo" placeholder="<%=calpost.getCalMemo() %>"></textarea> --%>
				<!-- <div class="calMainImg">메인이미지</lable> -->
				<%-- <input class="calMemo" value="<%=imageFileName %>"> --%>
			</li>
		
		</ul>
		
		<div class="inputBT">
	      <button class="modifycalpost" id="modifyBt" type="button">수정하기</button>
	      <button class="removecalpost" id="removeBt" type="button">삭제하기</button>
	    </div>
					
</div>

<%} //end if(c == null) %>