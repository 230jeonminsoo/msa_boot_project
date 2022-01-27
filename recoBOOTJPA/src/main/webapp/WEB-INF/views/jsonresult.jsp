<%@ page contentType="application/json; charset=UTF-8"%><%
int status = (Integer)request.getAttribute("status"); 
String msg = (String)request.getAttribute("msg");
%>{"status":<%=status%>, "msg":"<%=msg%>"}