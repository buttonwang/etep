<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String baseURL = "../";
	String theme = "policy/";
	
	String actionUrl=baseURL+theme+"paperAssemblingRequirements.jhtml?atype=";
	String showUrl= actionUrl+"show&";
	String editUrl = actionUrl+"edit&";
	String deleteUrl = actionUrl+"delete&";
	String deleteBatchUrl = actionUrl+"deleteBatch&ids=";
	
	String itemURL = baseURL+theme;
	String addUrl = itemURL+"add_paper_assembling_requirements.jsp";
	
	request.setAttribute("addUrl",addUrl);
	request.setAttribute("showUrl",showUrl);
	request.setAttribute("editUrl",editUrl);
	request.setAttribute("deleteUrl",deleteUrl);
	request.setAttribute("deleteBatchUrl",deleteBatchUrl);
	 
			
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/DeleteConfirm.js"></script>
<script>
j(function(){
	new DeleteConfirm();
})
</script>
</head>
<body>
<table id="table17" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#E3E3E3">
	<tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
		<td>序号</td>
		<td>地区</td>
		<td>学科</td>
		<td>题型</td>
		<td>试题年份</td>
		<td>试题来源</td>
		<td>原始套卷</td>
		<td>试题难度</td>
		<td>试题效度</td>
		<td>题数</td>
		<td>操作</td>
	</tr>	
	<%if ("list".equals(request.getAttribute("rtype"))){%>
		<c:forEach items="${page.result}" var="item" varStatus="itemStatus">
		<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">	
			<td></td>		
			<td>${item.regionCode=="c"?"全国II":"TODO"}</td>
			<td>${item.subjectCode=="e"?"英语":"TODO"}</td>
			<td>${item.itemTypeCode=="e"?"完型填空":"TODO"}</td>
			<td>${item.year}</td>
			<td>${item.source}</td>
			<td>${item.originalPaperCode}</td>
			<td>${item.difficultyValue}</td>
			<td>${item.validityValue}</td>
			<td>${item.amount}</td>
			<td><a href="${editUrl}id=${item.id}" >修改</a> <a href="${deleteUrl}id=${item.id}" >删除</a> </td>
		</tr>
	</c:forEach>
	<%}else{%>
	<c:forEach items="${all}" var="item" varStatus="itemStatus">
		 <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">	
			<td></td>		
			<td>${item.regionCode=="c"?"全国II":"TODO"}</td>
			<td>${item.subjectCode=="e"?"英语":"TODO"}</td>
			<td>${item.itemTypeCode=="e"?"完型填空":"TODO"}</td>
			<td>${item.year}</td>
			<td>${paperAssemblingRequirements.source=="v"?"模拟":"TODO"}</td>
			<td>${item.originalPaperCode}</td>
			<td>${item.difficultyValue}</td>
			<td>${item.validityValue}</td>
			<td>${item.amount}</td>
			<td><a href="${editUrl}id=${item.id}" >修改</a> <a href="${deleteUrl}id=${item.id}" >删除</a> </td>
		</tr>
	</c:forEach>
	<%}%>
	 
</table>
</body>
</html>
