<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String itemType = (String )request.getAttribute("itemType");/*show edit add*/%>
<%String formType = (String )request.getAttribute("formType");%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/m.js"></script>
</head>
<body>
<%if ("".equals(formType)){%>
	<%if("show".equals(formType)){%>
		
		
	<%}else if("edit".equals(formType)){%>

	

	<%}else if("add".equals(formType)){%>

	

	<%}%>
<%}%>



















</body>
</html>
