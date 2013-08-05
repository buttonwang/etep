<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
<style>
.ctl {
	font-size:12px;
	color:#555555;
	line-height: 20px;
	background-color:#CFECF2;
	width:100%;
	margin-top:10px;
	table-layout:fixed
}
.ctl td{text-overflow:ellipsis;overflow:hidden;white-space: nowrap;padding:2px}
</style>
</head>
<body>
<form  id="pageForm" method="post" action="previewItem.jhtml">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程管理 &gt; 预览试题</td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="6">
   <tr>
   	<td align="left">      	       	  
   	  <input type="button" value="返回" class="btn_2k3" onClick="location.href='node.jhtml?atype=showAll&id=${nodeId}'"/>      	  
    </td>
    <td align="left">
    	<c:set var="info" value="${info}&nbsp;&nbsp;&nbsp;&nbsp;符合条件的大题数: ${countBigItem} &nbsp;, &nbsp;总共包含 ${countItem} 个小题"/> ${info}
    </td>
   </tr>
 </table>
 
<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >	
  	<td width="10%">序号</td>
    <td width="15%">编码</td>
    <td width="15%">题型</td>
    <td width="5%">年份</td>
    <td width="5%">来源</td>
    <td width="5%">难度</td>
    <td width="35%">题干（文章）</td>
    <td width="4%">分值</td>
    <td width="6%">状态</td>
  </tr>
  <c:forEach items="${page.result}" var="item" varStatus="itemStatus">                  
  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
  	<td>${itemStatus.index+1}</td>
    <td><a href="../itembank/item!show.jhtml?id=${item.id}">${item.code}</a></td>
    <td>${item.itemType.code}(${item.itemType.name})</td>
    <td>${item.year}</td>
    <td>${item.sourceName}</td>
    <td>${item.difficultyValue}</td>
    <td>${item.content}</td>
    <td>${item.score}</td>
    <td>${item.statusName}</td>    
  </tr>
  </c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="6">
   <tr>
   	<td align="left">      	       	  
   	  <input type="button" value="返回" class="btn_2k3" onClick="location.href='node.jhtml?atype=showAll&id=${nodeId}'"/>      	  
    </td>
    <td align="left">
    	${info}
    </td>
   </tr>
 </table>
 <input type="hidden" value="${nodeId}" name="nodeId"/>		
</form>
</body>
</html>
