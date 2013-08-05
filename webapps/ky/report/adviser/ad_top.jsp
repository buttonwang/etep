<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="utf-8"%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" type="text/css">
<link href="<%=request.getContextPath()%>/css/pingce.css" rel="stylesheet" type="text/css">

<style>
td{font-size:12px;}
A
{
    COLOR: #000000;
    TEXT-DECORATION: none
}
</style>
</head>

<body bgcolor="eeeeee" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td background="<%=request.getContextPath()%>/images/adviser/top_bg.gif" height="36" width="276"><img src="<%=request.getContextPath()%>/images/adviser/top02.gif" width="276" height="39"></td>
    <td align="right" height="36" background="<%=request.getContextPath()%>/images/adviser/top_bg.gif" valign="bottom"> 

      <table border="0" cellspacing="0" cellpadding="0" height="29">
        <tr>
         
          <td  valign="middle" align="left">班级名称：${user['name']} 人数: ${user['amount']}</td>
          <td  valign="middle" align="left" width="40">&nbsp;</td>
         
          <td width="2"><img src="<%=request.getContextPath()%>/images/admin/top_navi1.gif" width="2" height="29"></td>           
          <td bgcolor="034CAC" width="40" valign="middle" align="center"><b><a href="#" target="_parent" onClick="javascript:window.close();"><font color="#FFFFFF">退 
            出</font></a></b></td>
		  <td width="2"><img src="<%=request.getContextPath()%>/images/admin/top_navi2.gif" width="2" height="29"></td>  
          <td width="10">&nbsp;</td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
