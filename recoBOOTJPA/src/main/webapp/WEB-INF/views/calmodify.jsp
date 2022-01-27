<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--캘린더 추가등록 페이지입니다. -->

<title>calwrite.jsp</title>
<link href="./css/calwrite.css" rel=stylesheet>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="./js/calmodify.js"></script>

<script>
$(function(){		
	//let $thumbnailObj = $('fieldset>form>div.input_image>input[name=thumbnail]');
	//let $categoryObj = $('fieldset>form>div.input_text>input[name=category]');
	let $formObj = $('fieldset>form');
	let $submitBtObj = $('fieldset>form>button[type=submit]')
	
     /*--캘린더 추가 등록버튼이 클릭되었을때 START--*/
	 addCalSubmit($formObj);
     /*--캘린더 추가 등록버튼이 클릭되었을때 END--*/
     
     /*--캘린더 수정 버튼이 클릭되었을때 START--*/
     /*  modifyCalClick(); */
	/*--캘린더 수정 버튼이 클릭되었을때 END--*/
   });
</script>
            
<fieldset>
    <form  method="post" action="./caladd" autocomplete="off">
        <div class="input_image">
         	<strong>대표사진 추가(컴퓨터에서 불러오기)</strong><br>
         	<input type="file" name="thumbnail" id="upload_file" accept="image/*" >
        	
        </div><br>
        
        <div class="input_text">
        	<strong>캘린더 이름 입력 </strong><br>
        	<input type="text" name="category" id="input" placeholder="캘린더 이름을 입력해주세요.">
        </div><br>
        

        <button type="submit">등록</button>
        <button type="cancle"  onClick='self.close()'>취소</button>

    </form>
</fieldset>
    
    <!-- <script type="text/javascript">
	    function validateForm(form) {  // 필수 입력 확인
	     
	        if (form.category.value == "") {
	            alert("캘린더 이름를 입력하세요.");
	            form.title.focus();
	            return false;
	        }
	        if (form.thumbnail.value == "") {
	            alert("썸네일를 추가하세요.");
	            form.content.focus();
	            return false;
	        }
	    }
	</script>
   -->
