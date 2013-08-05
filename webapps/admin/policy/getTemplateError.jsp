<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
</head>
<body>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	<c:forEach items="${v.errorLst}" var="item" varStatus="itemStatus">
		<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
			<td>${item}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
