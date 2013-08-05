<%@ page contentType="text/html; charset=UTF-8"%>
<%
String ClassCode=request.getParameter("ClassCode");
String urlTemp="ClassCode="+ClassCode;

String[] name={"单元训练状况","学生训练状况","学习记录"};
String[] url={
request.getContextPath()+"/report/ad_unit.jhtml?"+urlTemp,
request.getContextPath()+"/report/ad_stu.jhtml?"+urlTemp,
request.getContextPath()+"/report/ad_record.jhtml?"+urlTemp
};
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<style>
td,p{
font-size:12px;
line-height:24px;}
A
{
    COLOR: #666666;
    TEXT-DECORATION: none
}
A:hover
{
    COLOR: #666666;
    TEXT-DECORATION: none
}

</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" type="text/css">
<link href="<%=request.getContextPath()%>/css/pingce.css" rel="stylesheet" type="text/css">

</head>

<body bgcolor="#f5f5f5" text="#000000" leftmargin="4" topmargin="4" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="1">
<%
  for (int i=0;i<name.length;i++) {
    
%>
  
  <tr> 
    <td width="4"><img src="<%=request.getContextPath()%>/images/adviser/left01.gif" width="4" ></td>
    <td align="center" width="40"><img src="<%=request.getContextPath()%>/images/adviser/left02.gif" width="13" height="13" align="absmiddle"></td>
    <td><a href="<%=url[i]%>" target="main"><%=name[i]%></a></td>
  </tr>
  <tr> 
    <td bgcolor="#FFFFFF" height="1"></td>
    <td bgcolor="D9D9D9" height="1"></td>
    <td bgcolor="D9D9D9" height="1"></td>
  </tr>  
<%
}// end: for i
%>

</table>
</body>
</html>
