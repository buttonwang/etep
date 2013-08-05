<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 地区 &gt; 地区查看</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">地区编码：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">${region.code}</td>
  </tr>  
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">地区名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">${region.name}</td>
  </tr>  
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
          <table border="0" width="100%">
		     <tr>
		      	<td align="center">
  		        	<input type="button" value="  编 辑  " class="btn_2k3" 
  		        			onclick="javascript: window.location.href='region!edit.jhtml?code=${region.code}'" />&nbsp;&nbsp;	  	        	
		          	<input type="button" value="  返 回  " class="btn_2k3" onclick="javascript: history.back()" />&nbsp;&nbsp;
		       	</td>
		     </tr>
	    </table>
    </td>
  </tr>
</table>
</body>
</html>