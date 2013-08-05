<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${viewControl.flowName}</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" src="../js/jquery-1.3.2.min.js"></script>
<script language="javascript" src="../js/floating.js"></script>
<script language="javascript" src="../js/tandiv.js"></script>
<script language="javascript" src="../js/exam.js"></script>

</head>

<body onload=FloatingDIV();>

<jsp:include page="include_head.jsp"></jsp:include>

<jsp:include page="include_overview.jsp"></jsp:include>

<jsp:include page="include_bottom.jsp"></jsp:include>

<form id="examForm" action="../exam/doExam.jhtml" method="post">
	<input type="hidden" value="submit" name="nextAct" id="nextAct" />
</form>

</body>
</html>