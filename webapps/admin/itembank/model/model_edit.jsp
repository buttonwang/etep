<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/webwork" prefix="ww" %> 
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/MzTreeView12.js"></script>
<style>
body {font:normal 12px 宋体}
a.MzTreeview /* TreeView 链接的基本样式 */ { cursor: hand; color: #000080; margin-top: 5px; padding: 2 1 0 2; text-decoration: none; }
.MzTreeview a.select /* TreeView 链接被选中时的样式 */ { color: highlighttext; background-color: highlight; }
#kkk input {
vertical-align:middle;
}
.MzTreeViewRow {border:none;width:500px;padding:0px;margin:0px;border-collapse:collapse}
.MzTreeViewCell0 {border-bottom:1px solid #CCCCCC;padding:0px;margin:0px;}
.MzTreeViewCell1 {border-bottom:1px solid #CCCCCC;border-left:1px solid #CCCCCC;width:200px;padding:0px;margin:0px;}
</style>
</head>

<body>
<form method="post" action="model!save.jhtml?id=${model.id}&subject=${subject}&grade=${grade}">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 模块 &gt; 模块添加</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
    <td align="left" bgcolor="#FFFFFF"><input name="name" type="text" class="name" id="name" value="" size="15"/></td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<table class='MzTreeViewRow' style="background:#EEEEEE;border-top:1px solid #CCCCCC;"></table>
	<div id="kkk"></div>
	<script language="javascript" type="text/javascript">
	<!--
	var MzTreeViewTH="<table class='MzTreeViewRow'><tr><td class='MzTreeViewCell0'>";
	var MzTreeViewTD="\"</td></tr></table>\"";
	
	window.tree = new MzTreeView("tree");
	/*
	tree.icons["property"] = "property.gif";
	tree.icons["css"] = "collection.gif";
	tree.icons["event"] = "collection.gif";
	tree.icons["book"]  = "book.gif";
	tree.iconsExpand["book"] = "bookopen.gif"; //展开时对应的图片
	*/
	tree.setIconPath("../images/"); //可用相对路径
	<c:forEach items="${knowledgePointList}" var="knowledgePoint" >
		<c:set var="flag" value="1"/>
		<c:forEach items="${model.knowledgePoints}" var="modelKnowledgePoint" >
			<c:if test="${knowledgePoint.code == modelKnowledgePoint.code}">
				<c:set var="flag" value="2"/>
			</c:if>
		</c:forEach>
		<c:if test="${knowledgePoint.parentKnowledgePoint.code ==null}">
			tree.N["0_${knowledgePoint.code}"] = "ctrl:sel;checked:<c:if test="${flag ==2}">1</c:if><c:if test="${flag !=2}">0</c:if>;T:${knowledgePoint.name}";
		</c:if>
		<c:if test="${knowledgePoint.parentKnowledgePoint.code !=null}">
			tree.N["${knowledgePoint.parentKnowledgePoint.code}_${knowledgePoint.code}"] = "ctrl:sel;checked:<c:if test="${flag ==2}">1</c:if><c:if test="${flag !=2}">0</c:if>;T:${knowledgePoint.name}";
		</c:if>
	</c:forEach>


	tree.setURL("#");
	tree.wordLine = false;
	tree.setTarget("main");
	document.getElementById("kkk").innerHTML=tree.toString();


	//-->
	</script>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
      <table border="0" width="100%">
   		<tr>
    	<td align="center">
        	<input type="submit" value="  保 存  " class="btn_2k3" />&nbsp;&nbsp;	
        	<input type="button" value="  取 消  " class="btn_2k3" onClick="javascript: history.back()" />&nbsp;&nbsp;     	</td>
   		</tr>
 	  </table>    </td>
  </tr>
</table>
</form>
</body>
</html>