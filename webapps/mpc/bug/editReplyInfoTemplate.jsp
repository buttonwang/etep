<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../../admin/css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
</head>
<body>
<c:set var="r" value="${replyInfoTemplate}" />
<form method="post" action="bug!updateReplyInfoTemplate.jhtml" name="pageForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：捉虫模板&gt; 捉虫模板详情</td>
		</tr>
	</table>
	<table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">捉虫模板名：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF"><input type="hidden" value="${r.id}" name="p.para.replyInfoTemplateId" />
				<input type="text" value="${r.tempalteName}" name="p.para.replyInfoTemplateName"/></td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">捉虫模板内容：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF"><input type="text" value="${r.replyContent}" name="p.para.replyInfoTemplateContent" /></td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">点数：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF"><input type="text" value="${r.replyPoint}" name="p.para.replyPoint" /></td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><input  value="  保 存  " type="submit" class="btn_2k3" />
				&nbsp;&nbsp;
				<input type="button" value="  返 回  " class="btn_2k3"  onClick="window.history.back()"/></td>
		</tr>
	</table>
</form>
</body>
</html>
