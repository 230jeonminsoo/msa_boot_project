<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="com.my.calendar.dao.CalendarDAOOracle" %>
<%@page import="com.my.calendar.vo.CalPost" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RECO</title>

<link href="./css/calendar_write.css" rel=stylesheet>
<script src="./js/calendar_write.js"></script> 
<script src="./js/imgpreview.js"></script>

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

</head>
<h2>캘린더 글 수정</h2>
<form name="writeFrm" method="post" enctype="multipart/form-data"
      action="../calelendarwrite.jsp" onsubmit="return validateForm(this);">
<input type="hidden" name="u_Idx" value="${ vo.uidx }"/> 
<input type="hidden" name="cal_Idx" value="${ vo.calIdx }" />

      
<table border="1" width="90%">
    <tr>
        <td>대표 이미지 업로드</td>
        <td>
             <style>.dellink{display: none;}</style>
             <script type="text/javascript" src="preview.js"></script>
             <input type="file" name="cal_main_img" value="${cal_main_img}" 
             class="hidden_input" id="imageSelector" name="imageSelector" accept="image/jpeg, image/jpg, image/png" required /> <!-- 등록한 값 가져오기 -->
              <img src="" class="thumb"/>
              <a href="javascript:void(0);" class="dellink">썸네일삭제</a>   <!-- 등록한 값 가져오기 -->
            
        </td>
    </tr>
    <tr>
        <td>추가 이미지 업로드(3개)</td>
        <td>
            
             <input multiple="multiple" type="file" name="cal_img[]" value="${vo.cal_img1}"> 
           
        </td>
    </tr>
    <tr>
       <td>날짜</td>
       <td>
       	<input type="date" id="cal_date" onchange="dateChange();" />
          <script>
            var dateChange = () => {
            var date_input = document.getElementById("date");
            text_input.value = date_input.value;
            };
           </script>
       </td>
    </tr>
    <tr>
        <td>리뷰/메모</td>
        <td>
            <textarea name="cal_memo" cols="140" rows="30" placeholder="500자까지 자유작성/필수입력사항/캘린더에 작성하는 내용은 본인만 볼수 있다." value="${vo.cal_memo}" required></textarea>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button type="submit">수정 완료</button>
            <button type="button" onclick="location.href='../calpostlistresult.jsp';">캘린더보기</button>            
        </td>
    </tr>
</table>    
</form>
</body>
</html>