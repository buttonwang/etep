<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
</head>
<body>
<form action="assemblePaperPolicyTemplate.jhtml" method="post">
	<input type="hidden" name="atype" value="edit"/>
	<input type="hidden" name="id" value="${assemblePaperPolicyTemplate.id}"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：策略维护 &gt; 组卷策略模 </td>
		</tr>
	</table>
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
			<td  align="left" valign="top" bgcolor="#FFFFFF" colspan="3">${assemblePaperPolicyTemplate.name}
			</td>
		</tr>
		<tr id="table161" style="display:block;tt:xx">
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			<c:choose>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '0'}"> 手工组卷 </c:when>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '1'}"> 自动组卷 </c:when>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '2'}"> 动态出题 </c:when>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '11'}"> 动态组卷(过滤本级已做题) </c:when>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '12'}"> 动态组卷(过滤上一级已做题) </c:when>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '13'}"> 动态组卷(过滤上两级已做题) </c:when>
				<c:when test="${assemblePaperPolicyTemplate.paperAssemblingMode == '14'}"> 动态组卷(从所属训练中取题)</c:when>
			</c:choose>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> 37 </td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="283" border="0">
					<tr>
						<td width="277"><input type="submit" opt=back value="  修 改  " class="btn_2k3"/>
							&nbsp;&nbsp;
							<input type="reset" opt=back value="  返 回  " class="btn_2k3"/></td>
					</tr>
				</table></td>
		</tr>
	</table>
</form>
</body>
</html>
