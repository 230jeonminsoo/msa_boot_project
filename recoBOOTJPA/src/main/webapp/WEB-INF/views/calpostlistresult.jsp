<%@page import="java.io.File"%>
<%@page import="com.reco.calendar.vo.CalInfo"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
<link rel="stylesheet" href="./css/calpostlist.css"> 
<!-- <script src="./js/calpostwrite.js"></script>   -->
<script src="./js/calpostlist.js"></script>     

<head>
	<meta charset="UTF-8">
	<title>calpostlistresult.jsp</title>
</head>
    
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
String msg = (String)request.getAttribute("msg");
String calCategory = request.getParameter("calCategory");
CalInfo ci = (CalInfo)request.getAttribute("calinfo");
CalPost calpost = (CalPost)request.getAttribute("calpost");
//String calDate = calpost.getCalDate()  //값있음 null 아님
//String calDate = request.getParameter("calDate"); //얘가 null
String calMemo = request.getParameter("calMemo");
String calMainImg = request.getParameter("calMainImg");
int uIdx  = c.getUIdx();
int calIdx = Integer.parseInt(request.getParameter("calIdx"));

String saveDirectory = "c:\\reco\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles(); 

%>

<script> 
 
  $(function(){
	/*이미지 태그 보여주기*/
	let $img = $('td>a.calpostWR>img.calMainImg');
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
  
	var popupWindow = null;   		
	//달력에서 팝업창 캘린더글등록 클릭시 발생하는 이벤트
	function calpostWrite() {  
		var data = "<%=calCategory %>";
	      //화면기준 팝업 가운데 정렬
	var w = (window.screen.width/2) -100;
	var h = (window.screen.height/2) -100;
	var url = "calpostwritepage?uIdx=<%=uIdx%>&calIdx=<%=calIdx%>&calCategory=<%=calCategory %>";
		popupWindow = window.open(url, "calpostwritepage", "width = 800, height=800,left="+w+", top="+h, data);
	}
	
   function submitToPopUp(){
  		popupWindow.document.all.zipcode1.value = document.all.zipcode1.value;
  	} 
   
	//캘린더 글작성페이지로 이동하는 이벤트 
	dateClick(); /* calpostlist.js */
	
	function my_function(calDate) {
			
     	  console.log("글 상세보기 버튼 클릭");  

		  let calIdx = <%=calIdx%>;//3
		  let calCategory = $(this).attr('id'); 
		  let ajaxUrl = 'calpostdetail'; //2022-3-5
		  
		  $.ajax({
	          url: ajaxUrl,
	          method : 'get',
		      data:{calIdx:calIdx, calDate: calDate, calCategory:calCategory}, //calCategory:calCategory
	          success:function(responseData){
	              let $articlesObj = $('section>div.articles');
	              $articlesObj.empty();
	              $articlesObj.html(responseData);
	         }
		});
		
 	return false;
  }
	
</script> 

 <%
   //데이터베이스를 연결하는 관련 변수를 선언한다
  Connection conn= null;
  PreparedStatement pstmt = null;
   //데이터베이스를 연결하는 관련 정보를 문자열로 선언한다.
  String jdbc_driver= "oracle.jdbc.driver.OracleDriver"; //JDBC 드라이버의 클래스 경로
  String jdbc_url= "jdbc:oracle:thin:@localhost:1521";  //접속하려는 데이터베이스의 정보
   //JDBC 드라이버 클래스를 로드한다.
  Class.forName("oracle.jdbc.driver.OracleDriver");
   //데이터베이스 연결 정보를 이용해서 Connection 인스턴스를 확보한다.
  conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "cal", "cal");
  if (conn== null) {
   out.println("No connection is made!");
  }
  %>
    

<%
String yy = request.getParameter("year"); 
String mm = request.getParameter("month"); //사용자가 입력한 값

Calendar cal = Calendar.getInstance();

int h_y = cal.get(Calendar.YEAR); // 현재 연도
int h_m = cal.get(Calendar.MONTH);

int y = cal.get(Calendar.YEAR); // 현재연도가져오기 2021
int m = cal.get(Calendar.MONTH); // 현재월가져오기(월은 0 부터 시작한다) 2


/* if(yy != null && mm != null && yy.equals("") && !yy.equals("")){
	y = Integer.parseInt(yy); // 아래 셀에 대입
	m = Integer.parseInt(mm)-1; // 시스템 month는 0부터 시작하기때문에 -1을 해야한다.
} */



cal.set(y,m,1); //출력되는 연도월 1일날의 요일
int dayOfweek = cal.get(Calendar.DAY_OF_WEEK); // 1일날짜의요일가져오기 3(화요일) 1~7
//출력 년월의 마지막날짜,lastday는 2월의 마지막 28로 출력
int lastday = cal.getActualMaximum(Calendar.DATE);

//이전 버튼을 위한 설정
int b_y = y; // 이전연도
int b_m = m; // 이전달
if ( m == 0) {
	b_y = b_y -1; // 해가바뀜
	b_m = 12; 	
}

//다음 버튼을 위한 설정
int n_y = y;
int n_m = m+2;
if ( n_m == 13) {
	n_y = n_y +1;
	n_m = 1;
}


%>    
    
    
<!-- 캘린더 출력 -->   

<body>
<%-- <body>
	<form name = "frm" method="get" action="calpostlistresult.jsp"> 
		<input type="hidden" name="calIdx" value="<%=calIdx %>">
		<input type="hidden" name="calCategory" value="<%=calCategory %>">
	<!-- <input type = "submit" value="캘린더보기"> -->
	</form>
</body> --%>

<body>

	<div class="calCategory"> 
		<%-- <br><p align="center">-&nbsp;<%=calCategory %>&nbsp;캘린더&nbsp;-</p>  --%>
		<%-- <p>값테스트 : <%=calDate %></p> --%>
	</div>

	<table class="container">
	<!-- 월은 0 부터 시작해서 +1 처리 -->
		<caption>
			<div class="category" style = "float:left; width:30%;">
			  <p id="<%=calCategory %>" align="center" >-&nbsp;<%=calCategory %>&nbsp;캘린더&nbsp;-</p>
			</div>
			<div style = "float:left; width:30%;">
			  <p> <%=y%>년 <%=m+1%>월 </p><br>
			</div>
			<div class="button" style = "float:left; width:30%; text-align:right;"> 
			  	<br><br><button type = "button" onclick="calpostWrite()"> 캘린더 글등록 </button>
			</div>
			<%-- <button type = "button" onclick="location='calpostlistresult.jsp?year=<%=b_y%>&month=<%=b_m%>' "> 이전</button> 
			<button type = "button" onclick="location='calpostlistresult.jsp?year=<%=n_y%>&month=<%=n_m%>' " > 다음</button> --%> 
		</caption>
			
		
		    <tr>
			    <th>일</th>
				<th>월</th>
			    <th>화</th>
			    <th>수</th>
			    <th>목</th>
			    <th>금</th>
			    <th>토</th>	
		    </tr>
		    
			<tr class="date">
				
				<%
				int count = 0;
				
				//1일을 출력하기 전 빈칸을 출력하는 for문
				for(int s = 1; s<dayOfweek; s++) {
					out.print("<td></td>");
					count++;
				}
				
				//날짜 출력하는 설정 (td를 출력하기위한)
				for( int d= 1; d<= lastday; d++){
					count++;
					String color = "#555555";
					if ( count == 7 ) {
						color = " blue ";
					} else if (count == 1) {
						color = "red";
					}
				//calpost글이 있는곳 링크표시
				
				int z = m+1; // 현재월(m+1)을 변수에 넣기 
				String f_date = y + "-" + (m+1) + "-" + d; //선택한날짜 //2022-2-1 이런 형식이라 안됨. 2022-02-01 같은 형식이여야 이미지 불러오기가능!(이 형식으로 사진저장하기때문)
				
				String mon = String.format("%02d",z); //(1-9)월을 두자리수로 변환 (01,02...08,09월)
				String dday = String.format("%02d",d); //(1-9)일을 두자리수로 변환 (01,02...08,09일)
				
				String rDate = y + "-" + mon + "-" + dday; //입력하는 진짜 날짜, 2022-03-02의 형식 
				String imageFileName = "s_cal_"+uIdx+"_"+calIdx+"_"+rDate+".jpg"; //썸네일 불러오는 파일명 지정.
				
				//글작성한 날짜 찾아주는 쿼리 
				String f_sql = "select count(*) cnt from cal_post_"+uIdx+"_"+calIdx+"";
					   f_sql += " where cal_date = '"+f_date+"'";
			    pstmt= conn.prepareStatement(f_sql);
				ResultSet f_rs = pstmt.executeQuery(f_sql);
				f_rs.next();
				
				
			    
				int f_cnt = f_rs.getInt("cnt");	
				if(f_cnt == 1) {
					color = "rgb(197, 104, 250)";
				%>
					<td class="calimage" style = "color: <%=color %>">
						<span ><%=d %></span> <!-- 작성한 글리스트 보여줌 -->
						<a class="calpostWR" id="<%=calCategory %>" href="javascript:my_function('<%=f_date%>')">
								<img id="<%=imageFileName %>" class="calMainImg" title="calMainImg" >
						</a>
					</td>
					
				<% 
				} else {
				%> 
					<td class="cp" style = "color: <%=color %>;">
					  		<%=d %>
					  	  <a class="add"  href ="<%=calCategory%>" id="<%=calIdx%>" style="font-size: 20px; text-decoration:none; color:none;">
						  	<br><br><button class="cpbt" type = "button" width="30px"> add </button>
						  </a>
					</td>
				<% 
				     }
				// 한줄에 7개 찍어내기
					if( count % 7 == 0 ) {
						out.print("</tr><tr>");
					    count = 0; // 변수 초기화
						} 	
				}
				// 마지막주 빈공간 찍히기
				while( count < 7) {
					out.print("<td></td>");
					count++;
				}
				%>
			</tr>
	</table>
</body>

<%} //end if(c == null) %>


