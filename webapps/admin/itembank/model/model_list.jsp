<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@page import="java.util.List,java.util.Set"%>
<%@page import="com.ambow.trainingengine.itembank.domain.Model"%>
<%@page import="com.ambow.trainingengine.itembank.domain.KnowledgePoint"%>
<%
	String ctx = request.getContextPath();
	List<Model> modelList= (List<Model>)request.getAttribute("modelList");
%> 
<html>
<head>
<title>模块列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="location">
  <tr >
    <td width="95%" >当前位置：题库 &gt; 模块 &gt; 模块列表 </td>
    <td width="5%" ></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="6" width="100%" align="center" border="0">
        <TR>
          <TD class=txt12blueh align=left width="50%"></TD>
          <TD class=txt12blue align=right>

            <INPUT class=btn_2k3 type=button onClick="javascript:tree.expandAll();" value=" 列表展开 "> 
            &nbsp;&nbsp;&nbsp; <INPUT class=btn_2k3 onClick="javascript: window.location.href='model!preAdd.jhtml?subject=${subject}&grade=${grade}';" type=button value=" 新 增 ">&nbsp; 
<INPUT class=btn_2k3 onClick="javascript: if (confirm('确定要删除吗？')) window.location.href='model!deleteAll.jhtml?subject=${subject}&grade=${grade}';" type=button value=删除全部> 
</TD></TR>
</table>
<table class='MzTreeViewRow' style="background:#EEEEEE;border-top:1px solid #CCCCCC;"></table>
	<div id="kkk"></div>
	<script language="javascript" type="text/javascript">
	<!--
	var MzTreeViewTH="<table class='MzTreeViewRow'><tr><td class='MzTreeViewCell0'>";
	var MzTreeViewTD="\"</td><td class='MzTreeViewCell1'>\"+ show(pid,sid) +\"</td></tr></table>\"";
	
	window.tree = new MzTreeView("tree");
	/*
	tree.icons["property"] = "property.gif";
	tree.icons["css"] = "collection.gif";
	tree.icons["event"] = "collection.gif";
	tree.icons["book"]  = "book.gif";
	tree.iconsExpand["book"] = "bookopen.gif"; //展开时对应的图片
	*/
	tree.setIconPath("../images/"); //可用相对路径
<%
		for(int i=0;i<modelList.size();i++){
			Model model = modelList.get(i);
%>
tree.N["0_<%=model.getId()%>"] = "T:<%=model.getName()%>";
<%
			Set<KnowledgePoint> set =  model.getKnowledgePoints();
			Object[] knowledgePoints = set.toArray();
			for(int ii=0;ii<set.size();ii++){
				KnowledgePoint knowledgePoint = (KnowledgePoint)knowledgePoints[ii];
%>
tree.N["<%=model.getId()%>_<%=knowledgePoint.getParentKnowledgePoint().getCode()%>"] = "T:<%=knowledgePoint.getParentKnowledgePoint().getName()%>";
tree.N["<%=knowledgePoint.getParentKnowledgePoint().getCode()%>_<%=knowledgePoint.getCode()%>"] = "T:<%=knowledgePoint.getName()%>";
<%

			}
		}

%>

	tree.setURL("#");
	tree.wordLine = false;
	tree.setTarget("main");
	document.getElementById("kkk").innerHTML=tree.toString();
	tree.expandAll();
	function show(pid,sid){
		if(pid =='0'){
			return "<a href=\"model!preEdit.jhtml?id="+sid+"&subject=${subject}&grade=${grade}\">修改</a> | <a href=\"model!del.jhtml?id="+sid+"&subject=${subject}&grade=${grade}\">删除</a>";
		}else{
			return "";
		}
	}
	//-->
	</script>
</body>
</html>