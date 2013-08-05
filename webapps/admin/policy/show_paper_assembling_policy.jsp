<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组卷策略</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>
<body>
<table id="table19" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr id="table15">
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
		<td width="33%" align="left" bgcolor="#FFFFFF">
		${paperAssemblingPolicy.paperAssemblingMode==0?"手工组卷":""}
		${paperAssemblingPolicy.paperAssemblingMode==1?"动态组卷":""}
		${paperAssemblingPolicy.paperAssemblingMode==2?"动态出题":""}						 
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
		<td width="33%" align="left" bgcolor="#FFFFFF"><a href="../itembank/show_paper.jhtml?id=${paperAssemblingPolicy.paper.id}">${paperAssemblingPolicy.paper.name}</a></td>
	</tr>
</table>
</body>
</html>
