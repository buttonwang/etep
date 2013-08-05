<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="gbk"%>
<%
String ClassCode=request.getParameter("ClassCode");
String ClassName=request.getParameter("ClassName");//new String((request.getParameter("ClassName")).getBytes("ISO-8859-1"),"UTF-8");
String urlTemp="ClassCode="+ClassCode+"&ClassName="+ClassName;

String urlTop=request.getContextPath()+"/report/ad_top.jhtml?"+urlTemp;
session.setAttribute("ClassCode",ClassCode);
session.setAttribute("ClassName",ClassName);

%>
<html>
<head>
<title>教师班级管理</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
</head>
<frameset rows="39,*" border="0" framespacing="0" cols="*" frameborder="NO"> 
  <frame src="<%=urlTop%>" name="top" scrolling="NO" frameborder="NO" noresize>
  <frameset cols="160,*" rows="*" frameborder="NO"> 
    <frame src="ad_left.jsp?<%=urlTemp%>" frameborder="NO" scrolling="NO" noresize>
    <frame src="ad_main.jsp?<%=urlTemp%>" name="main">
  </frameset>
</frameset>
<noframes>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

</body>
</noframes> 
</html>
