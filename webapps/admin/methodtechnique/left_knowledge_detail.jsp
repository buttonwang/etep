<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="ambow" prefix="ambow"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="MSHTML 5.00.3502.5390" name=Generator>
<script src="../js/jsframework.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var data={};
	data["-1_0"] = "text: 方法与技巧;";
	<c:forEach items="${mts }" var="item" varStatus="status">
		<c:choose>
			<c:when test="${fn:substring(item.code,5,7) eq '00'}">
				data["0_${item.code}"] = 'text: ${item.code}; url: mt!see.jhtml?id=${item.id};target:right';
			</c:when>
			<c:otherwise>
				data["${fn:substring(item.code,0,5)}00_${item.code}"] = 'text: ${item.code}; url: mt!see.jhtml?id=${item.id};target:right';
			</c:otherwise>
		</c:choose>
	</c:forEach>
	data["-1_2"] = "text: 返回上一级菜单;url:left_knowledge.jsp;";
        Using("System.Web.UI.WebControls.MzTreeView");
        var a = new MzTreeView();
        a.dataSource = data;

        a.autoSort=false;
        //a.useCheckbox=true;
        a.canOperate=true;
        document.write(a.render());
        a.expandLevel(1);
</script>
</head>
<body >
</body>
</html>