(function () {
    calendarMaker($("#calendarForm"), new Date());
})();

var nowDate = new Date();

function calendarMaker(target, date) {
    if (date == null || date == undefined) {
        date = new Date();
    }
    nowDate = date;
    if ($(target).length > 0) {
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1;
        $(target).empty().append(assembly(year, month));
      
    } else {
        console.error("custom_calendar Target is empty!!!");
        return;
    }

    var thisMonth = new Date(nowDate.getFullYear(), nowDate.getMonth(), 1);
    var thisLastDay = new Date(nowDate.getFullYear(), nowDate.getMonth() + 1, 0);


    var tag = "<tr>";
    var cnt = 0;
    //빈 공백 만들어주기
    for (i = 0; i < thisMonth.getDay(); i++) {
        tag += "<td></td>";
        cnt++;
    }

    //날짜 채우기
    for (i = 1; i <= thisLastDay.getDate(); i++) {
        if (cnt % 7 == 0) { tag += "<tr>"; }

        tag += "<td>" + i + "</td>";
        cnt++;
        if (cnt % 7 == 0) {
            tag += "</tr>";
        }
               
    }
    
	
	
    // 오늘 날짜 표시
    $(".nowDate").each(function(target,date){ 
        if(Year==nowDate.getFullYear() && Month==nowDate.getMonth() && $(".nowDate").eq(target,date).text()==nowDate.getDate()) {
            $(".nowDate").eq(target,date).addClass('coltoday');
        }
    }) 


    $(target).find("#custom_set_date").append(tag);
    calMoveEvtFn();


   

    function assembly(year, month) {
        var calendar_html_code =
            "<table class='custom_calendar_table'>" +
	            "<colgroup>" +
		            "<col style='width:150px'/>" +
		            "<col style='width:150px'/>" +
		            "<col style='width:150px'/>" +
		            "<col style='width:150px'/>" +
		            "<col style='width:150px'/>" +
		            "<col style='width:150px'/>" +
		            "<col style='width:150px'/>" +
	            "</colgroup>" +
	            "<thead class='cal_date'>" +
		            "<th><button type='button' class='prev'><</button></th>" + //이전달 
		            "<th colspan='5'><p><span>" + year + "</span>년 <span>" + month + "</span>월</p></th>" +  //년/월
		            //"<th><button type='button' class='today'>오늘</button></th>" +
		            "<th><button type='button' class='next'>></button></th>" + //다음달
	            "</thead>" + 
	            "<thead class='cal_date1'>" +
	            	"<th><button type='button' class='today'>today</button></th>" + //오늘날짜 돌아가기 버튼 
	            "</thead>" +
	            "<thead  class='cal_week'>" +
	            	"<th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>" +
	            "</thead calss='day'>" +
	            "<tbody id='custom_set_date' >" +   
	            "</tbody>" +
            "</table>";
        return calendar_html_code;
    }
    

	
    function calMoveEvtFn() {
        //전달 클릭
        $(".custom_calendar_table").on("click", ".prev", function () {
            nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth() - 1, nowDate.getDate());
            calendarMaker($(target), nowDate);
        });
        //다음날 클릭
        $(".custom_calendar_table").on("click", ".next", function () {
            nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate());
            calendarMaker($(target), nowDate);
        });
        //today 클릭
        $(".custom_calendar_table").on("click", ".today", function(){
            nowDate = new Date();
            calendarMaker($(target), nowDate);
        });
        //일자 선택 클릭
        $(".custom_calendar_table").on("click", "td", function () {
            $(".custom_calendar_table .select_day").removeClass("select_day");
            $(this).removeClass("select_day").addClass("select_day");
            //location.href="calpostwrite.jsp"
			//팝업창 방법
			/*let url = 'calpostwrite.jsp';
	        let target = 'calpost';
	        let features = 'top=1000, left=1000, width=1000px, height=700px';
			window.open(url, target, features);
			return false;*/
			
			//에이작스 페이지 전환
			let ajaxUrl = "calpostwrite.jsp"; 
			         
				$.ajax({
	            url: ajaxUrl,
	            method : 'get',
	            success:function(responseData){
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
	           }
			});
			 return false;
        });
        
    }
	


}

