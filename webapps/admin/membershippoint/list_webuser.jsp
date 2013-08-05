<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>
<body>
<form name="pageForm" action="membershipPoint!userList.jhtml" method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：积分 &gt; 用户列表</td>
		</tr>
	</table>
	<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
		<tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left" >&nbsp;&nbsp;&nbsp;用户id &nbsp;
							<input class="logininputmanage" type="text" name="p.para.userId" value="${p.para.userId[0]}" size="15"/>
							&nbsp;&nbsp;
							用户名：
							<input class="logininputmanage" type="text" name="p.para.userLoginName" value="${p.para.userLoginName[0]}" size="15"/>
							&nbsp;&nbsp;
							真实姓名：
							<input class="logininputmanage" type="text" name="p.para.userRealName" value="${p.para.userRealName[0]}" size="15"/>
							<input type="submit" value=" 查 询 " class="btn_2k3"  /></td>
						<td class="ReporterCol_2" align="right"><ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
					<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
						<td>用户名</td>
						<td>真实姓名</td>
						<td>最后登录时间</td>
						<td>操作</td>
					</tr>
					 
					<c:forEach items="${page.result}" var="user" varStatus="status">
						<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
							<td>${user.loginName}</td>
							<td>${user.realName}</td>
							<td>${user.lastLoginTime}</td>
							<td><a href="membershipPoint.jhtml?p.para.userId=${user.id}">查看积分</a></td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left">&nbsp;</td>
						<td class="ReporterCol_2" align="right"><ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/></td>
					</tr>
				</table></td>
		</tr>
	</table>
</form>
</body>
</html>