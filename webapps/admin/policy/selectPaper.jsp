<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*,java.util.List" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择试卷</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>

<body>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
    <td>试卷名称</td>
    <td>试卷分类</td>
    <td>试卷类型</td>
    <td>试卷难度</td>
    <td>答卷时间</td>
    <td>分值</td>    
    <td>状态</td>
    <td>操作</td>
  </tr>
  	<% if (((List)request.getAttribute("paperLst")).size()>0){%>
	  <c:forEach items="${paperLst}" var="paper" varStatus="status">
	    <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
		    <td><a href="../itembank/paper!show.jhtml?id=${paper.id}">${paper.name}</a></td>
		    <td>${paper.paperCategory.name}</td>
		    <td>${paper.paperType.name}</td>
		    <td><fmt:formatNumber value='${paper.difficultyValue}' pattern='#######.##'/></td>
		    <td><fmt:formatNumber value='${paper.answeringTime/60}' pattern='0'/></td>
		    <td>${paper.totalScore}</td>
		    <td>${paper.statusName}</td>
		    <td><a href="../itembank/paper!show.jhtml?id=${paper.id}">查看</a>&nbsp;&nbsp;<a href="../policy/paperAssemblingPolicy.jhtml?atype=iupdate&paperAssemblingPolicy.asfNode.id=${paperAssemblingPolicy.asfNode.id}&paperAssemblingPolicy.paperAssemblingMode=${paperAssemblingPolicy.paperAssemblingMode}&p.para.paperId=${paper.id}">使用</a></td>
	    </tr>
	  </c:forEach>
	  <%}else{%>
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
		    <td colspan="8">没有试卷可供选择</td>
			</tr>
	   
	  <%}%>
  </table>
</body>
</html>
