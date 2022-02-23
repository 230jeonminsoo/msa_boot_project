<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>   
<%@page import="java.util.List"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="com.reco.calendar.vo.CalInfo"%>

<script src="./js/mycallist.js"></script>
<link rel="stylesheet" href="./css/mycallist.css">


<script>
   $(function(){
		$('div.tab>ul.myinfotab').css('display','table');
		$('div.tab>ul.communitytab').css('display','none');
		$('div.tab>ul.caltab').css('display','none');
		
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
		
		//캘린더 삭제버튼 클릭시 발생하는 이벤트
		/* removeBtnClick(); */
	
   });
</script>
    
    
<section><!--callist.js -->
<div class="articles" >
	 <div class="rm_title">	
	 	<p class="mycalRM" style="font-size: 30px;"><b> <캘린더 관리> </b></p>
	 	<button class="mycalRM" >글 삭제</button>
	 </div>
	 <ul class="title_list">
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
				
				for(CalInfo ci : list){
					int calIdx = ci.getCalIdx();
					String imageFileName = "cal_post_" + uIdx  + "_" + calIdx + ".jpg";
					String thumbnailName = "s_"+ imageFileName;
			%> 	
				
			<li>
				<div id="<%=calIdx %>" class="calIdx"> 
				  <div class="title_wrap" id="title5">
				    <a href="#"> <!-- 썸네일 -->
				     	<img id="<%=thumbnailName %>" alt="thumbnailName" title="thumbnailName">
				    </a>
				  </div>
				  
				  <div class="title_info">
				   	 <p class="title_front">
				   	 	<input type='checkbox'
				    	   style='width:25px; height:25px'	
					       name='calIdx' 
					       value=<%=calIdx%> />
				   	 	<%=ci.getCalCategory() %>
				   	 </p>
				  </div>
				</div>
			</li>
			
			<%} //end for 
				/* for(int i=list.size(); i<5; i++){ */
			%> 
			
		<%
		}
		%>

	 </ul>
 </div>
</section>