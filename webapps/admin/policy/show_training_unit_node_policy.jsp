<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>

<body>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">不通过：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
	${trainingUnitNodePolicy.failed==1?"返回":"向前"}
	</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过</td>
	<td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${trainingUnitNodePolicy.pass==1?"返回":"向前"}</td>
  </tr>
  
</table>
 
</body>
</html>
