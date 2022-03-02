<%@page import="java.io.File"%>
<%@page import="com.reco.calendar.vo.CalInfo"%>
<%@page import="com.reco.calendar.vo.CalPost"%>
<%@page import="com.reco.customer.vo.Customer"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
    
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
String calDate = calpost.getCalDate();

int uIdx  = c.getUIdx();
int calIdx = Integer.parseInt(request.getParameter("calIdx"));

String saveDirectory = "c:\\reco\\calendar";
File dir = new File(saveDirectory);
File[] files = dir.listFiles(); 

%>



<div class="calCategory"> 
	<br><p align="center">-&nbsp;<%=calCategory %>&nbsp;캘린더&nbsp;-</p> 
	<%-- <p><%=calIdx %></p> --%>
</div>

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

if(yy != null && mm != null && yy.equals("") && !yy.equals("")){
	y = Integer.parseInt(yy); // 아래 셀에 대입
	m = Integer.parseInt(mm)-1; // 시스템 month는 0부터 시작하기때문에 -1을 해야한다.
}

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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calpostlistresult.jsp</title>
</head>

<style>
body {
	font-size : 20pt;
	color: #555555;
}
table {
	border-collapse:collapse;
}
th, td {
	border : 1px solid #cccccc;
	width : 200px;
	height : 100px;
	text-align:center; 
}
td>a.calpostWR>img.calMainImg {
 width: 80px; 
 height: 80px;
}

caption {
	margin-botto:10px;
	font-size:30px;
}
</style>

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
    		//달력에서 캘린더글등록 클릭시 발생하는 이벤트
    		function calpostWrite() {  
	    		var data = "<%=calCategory %>";
	            //화면기준 팝업 가운데 정렬
	    		var w = (window.screen.width/2) -100;
	    		var h = (window.screen.height/2) -100;
	    		var url = "calpostwrite?uIdx=<%=uIdx%>&calIdx=<%=calIdx%>&calCategory=<%=calCategory %>";
	    		popupWindow = window.open(url, "calpostwrite", "width = 800, height=800,left="+w+", top="+h, data);
    		}
    		
   		    function submitToPopUp(){
   	    		popupWindow.document.all.zipcode1.value = document.all.zipcode1.value;
   	    	} 
   
 
  function my_function(v) {
	 var w = (window.screen.width/2) -100;
	 var h = (window.screen.height/2) -100;
	 var url = "calpostview?uIdx=<%=uIdx%>&calIdx=<%=calIdx%>&calDate="+v;
	  window.open(url, "calpostwrite", "width = 800, height=800,left="+w+", top="+h);   
  } 
    
 </script> 




<body>
<form name = "frm" method="get" action="calpostlistresult.jsp"> 
<input type="hidden" name="calIdx" value="<%=calIdx %>">
<input type="ahidden" name="calCategory" value="<%=calCategory %>">

<!-- <input type = "submit" value="캘린더보기"> -->
</form>
</body>

<body>
<%
	List<CalPost> list = (List)request.getAttribute("list");
             		
    if( list != null) {
	        for(CalPost cp : list){
			String Date = cp.getCalDate();
					}
					} %>
					
	<%if( list == null){ %>
		<a href="calpostlistresult.jsp"></a>
	<%} %>
<table class="container">
<!-- 월은 0 부터 시작해서 +1 처리 -->
	<caption>
		<div style = "float:left; width:30%;">&nbsp;</div>
		<div style = "float:left; width:40%;">
		  <%=y%>년 <%=m+1%>월
		</div>
		<div style = "float:left; width:30%; text-align:right;"> 
		  <button type = "button" onclick="calpostWrite()"> 캘린더글등록 </button>
		</div>
	<%-- 	<button type = "button" onclick="location='calpostlistresult.jsp?year=<%=b_y%>&month=<%=b_m%>' "> 이전</button>  --%>
	<%-- 	<button type = "button" onclick="location='calpostlistresult.jsp?year=<%=n_y%>&month=<%=n_m%>' " > 다음</button>  --%>
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
		
		String f_date = y + "-" + (m+1) + "-" + d; //선택한날짜
		
		String f_sql = "select count(*) cnt from cal_post_"+uIdx+"_"+calIdx+"";
			   f_sql += " where cal_date = '"+f_date+"'";
	    pstmt= conn.prepareStatement(f_sql);
		ResultSet f_rs = pstmt.executeQuery(f_sql);
		f_rs.next();
		
		String imageFileName = "s_cal_"+uIdx+"_"+calIdx+"_"+f_date+".jpg";
		
		int f_cnt = f_rs.getInt("cnt");	
		if(f_cnt == 1) {
			color = "pink";
		%>
			<td  style = "color: <%=color %>">
				<a href="javascript:my_function('<%=f_date%>')"><%=d %></a> <!-- 작성한 글리스트 보여줌 -->
				<a class="calpostWR" href="#">
					<img id="<%=imageFileName %>" class="calMainImg" title="calMainImg" >
				</a> 
			</td>
			
		<% 
		} else {
		%>	
		<td style = "color: <%=color %>;"><%=d %></td>
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
</html>


