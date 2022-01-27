<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="com.my.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.my.calendar.vo.CalPost" %>

<link href="./css/calendar_write.css" rel=stylesheet>
<!-- <script src="./js/calendar_write.js"></script>  -->

<head>
<meta charset="UTF-8">
<title>RECO</title>

<!-- <script src="./js/calendar_write.js"></script> --> 
<script src="./js/imgpreview.js"></script>
<script src="./js/calpostwrite.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
		$(function(){
			//작성완료 버튼 클릭시
				addCalPostClick();
			//캘린더보기 버튼 클릭시
				calPostViewClick();	
		});
		</script>
	
	
	

</head>

<h2>캘린더 글 작성</h2>
<form name="writeFrm" method="post" enctype="multipart/form-data"
      action="./CalPostAdd" onsubmit="return validateForm(this);">
	<table border="1" width="90%">
	    <tr>
	        <td>대표 이미지 업로드</td>
	        <td>
	             <style>.dellink{display: none;}</style>
	             <script type="text/javascript" src="preview.js"></script>
	             <input type="file" name="calMainImg" 
	             class="hidden_input" id="imageSelector" name="imageSelector" accept="image/jpeg, image/jpg, image/png" required  />
	              <img src="" class="thumb"/>
	              <a href="javascript:void(0);" class="dellink">썸네일삭제</a>  
	        </td>
	    </tr>
	  
	       <td>날짜</td>
	       <td>
	       	<input type="date" name="calDate" value="xxx" min="yyy" max="zzz" required >
<!-- 	          <script> -->
<!--             var dateChange = () => {
	            var date_input = document.getElementById("date");
	            text_input.value = date_input.value;
 	            };--> 
<!-- 	           </script> -->
	       </td>
	    </tr>
	    <tr>
	        <td>리뷰/메모</td>
	        <td>
	            <textarea name="calMemo" cols="140" rows="30" placeholder="500자까지 자유작성/필수입력사항/캘린더에 작성하는 내용은 본인만 볼수 있다." required></textarea>
	        </td>
	    </tr>
	    <tr>
	        <td colspan="2" align="center">
	            <button id=1 type="submit" >작성 완료</button>
	            <button id=2 type="button" >캘린더보기</button>            
	        </td>
	    </tr>
	</table>    
</form>

<script type="text/javascript">
	    function validateForm(form) {  // 필수 입력 확인
	     
	        if (form.cal_memo.value == "") {
	            alert("리뷰/메모를 입력하세요.");
	            form.title.focus();
	            return false;
	        }
	        if (form.cal_main_img.value == "") {
	            alert("대표 이미지를 추가하세요.");
	            form.content.focus();
	            return false;
	        }
	    }
	</script>


