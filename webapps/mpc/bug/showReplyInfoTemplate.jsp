<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../../admin/css/admin.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:set var="r" value="${replyInfoTemplate}" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：试题捉虫模板管理 &gt; 捉虫模板列表 &gt; 捉虫模板详情</td>
	</tr>
</table>
<table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr>
		<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">模板名：</td>
		<td width="133%" align="left"  bgcolor="#FFFFFF">${r.tempalteName}</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">模板内容：</td>
		<td width="133%" align="left"  bgcolor="#FFFFFF">${r.replyContent}</td>
	</tr>
	<tr>
		<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">点数：</td>
		<td width="133%" align="left"  bgcolor="#FFFFFF">${r.replyPoint}</td>
	</tr>
	<tr>
		<td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
				<tr>
					<td align="center"><input type="button" value="  修 改  " class="btn_2k3"  onClick="location.href='bug!editReplyInfoTemplate.jhtml?p.para.replyInfoTemplateId=${r.id}';"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="  返 回  " class="btn_2k3" onClick="window.history.back()"/>
						&nbsp;&nbsp; </td>
				</tr>
			</table></td>
	</tr>
</table>
</body>
</html>
