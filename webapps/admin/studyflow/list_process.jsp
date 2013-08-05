<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.sql.*" %>
<%@ taglib uri="ambow" prefix="ambow"%>

<%
	String rtype = (String)request.getAttribute("rtype");
	String baseURL = "../";
	String theme = "studyflow/";
	
	String actionUrl=baseURL+theme+"process.jhtml?atype=";
	String showUrl= actionUrl+"show&";
	String editUrl = actionUrl+"edit&";
	String deleteUrl = actionUrl+"delete&";
	
	String itemURL = baseURL+theme;
	String addUrl = itemURL+"add_process.jsp";
	String saddUrl = actionUrl+"sadd&";
	
	request.setAttribute("addUrl",addUrl);
	request.setAttribute("showUrl",showUrl);
	request.setAttribute("editUrl",editUrl);
	request.setAttribute("deleteUrl",deleteUrl);
	request.setAttribute("onClick_createNew_js","javascript: window.location.href='"+saddUrl+"'");
	  
			
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/DeleteAll.js"></script>
<script src="../js/all/ConfirmIF.js"></script>
<script src="../js/all/studyflow/list_process.js"></script>
<script src="../js/jquery.min.js"></script>
<script language="javascript">
<!--
	//简单查询模式，查询FORM提交（simpleSearch置为yes）
	//AUTHOR: L.赵亚
	function subFormSearchSimple(){
		$("#simpleSearch").val("yes");
		$("#pageForm").submit();
	}
	//高级查询模式，查询FORM提交（simpleSearch置为no）
	//AUTHOR: L.赵亚
	function subFormSearchSuper(){
		$("#simpleSearch").val("no");
		$("#pageForm").submit();
	}

	$(document).ready(function(){
		/********AUTHOR: L.赵亚***************START******************/
		$("#simpleSearchButton").click(subFormSearchSimple);
		$("#SuperSearchButton").click(subFormSearchSuper);
		$("#simpleSearch").val("${simpleSearch }");
		/********AUTHOR: L.赵亚***************END******************/
	});
//-->
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 流程查询</td>
	</tr>
</table>
<form action="process.jhtml" method="post" id="pageForm">
			<input type="hidden" class="logininputmanage"  name="atype" value="search"/>
			<input type="hidden" name="simpleSearch" id="simpleSearch" value="yes"/>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td align="left" >
					<input type="button" value=" 新 建 " class="btn_2k3" onClick="${onClick_createNew_js}"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<select style="height:20px;" id="searchObjName" name="searchObjName"  >
							<option value="processName" ${searchObjName eq "processName"?"selected":"" }>流程名称：</option>
							<option value="processCreator" ${searchObjName eq "processCreator"?"selected":"" }>创建人：</option>
							<option value="processStatus" ${searchObjName eq "processStatus"?"selected":"" }>流程状态：</option>
						</select>
						<input  id="serchObjValue" type="text" class="logininputmanage" size="15" name="searchObjValue" value="${searchObjValue }"/>
						&nbsp;&nbsp;
						<input type="button" id="simpleSearchButton" value="查询" class="btn_2k3"/>
						&nbsp;
						<input type="button" value="高级" onClick='javascript:j("#hiddenDiv").toggle();' class="btn_2k3"/>
					</td>
					<td align="right">
<ambow:pagination actionName=""  total="${page.totalPageCount}" num="${page.currentPageNo}"  otherParams=""/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
	<td>
<div id="hiddenDiv" style="${simpleSearch eq "no"?"":"display:none" }" >
		<table class="txt12555555line-height"  width="100%" border="0" align="center" 
			cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" 
		id="search2">
	 	<tr>
		    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
		    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
		    	<input class="logininputmanage" type="text" id="processNameDetail" 
		    		name="processName" value="${processName }"/></td>
		    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">流程说明：</td>
		    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
		    	<input class="logininputmanage" id="processDescription" type="text" name="processDescription" 
		    		value="${processDescription }"/></td>
		    </tr>
		  <tr>
		    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
		    <td align="left" valign="top" bgcolor="#FFFFFF">
		    	<input type="text" name="processDefinitionVersion" value="${processDefinitionVersion }" 
		    		id="processDefinitionVersion" class="logininputmanage">
		     </td>
		    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">分类： </td>
		    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
		    	<select name="processCategorySearch">
		    		<option value="">全部</option>
					<c:forEach items="${processCategoryLst}" var="p" varStatus="status">
		      			<option value="${p.id}" ${processCategorySearch eq p.id?"selected":"" }>${p.name}</option>
					</c:forEach>
		    	</select>
		    </td>
		  </tr>
		  <tr>
		    <td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">创建人：</td>
		    <td align="left" valign="top" bgcolor="#FFFFFF">
		    	<input class="logininputmanage" id="processCreator type="text" name="processCreator" 
		    		value="${processCreator }"/></td>
		    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态： </td>
		    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
		    	<select name="processStatus" id="processStatus">
		    		<option value="">全部</option>
		      		<option ${processStatus eq "未发布"?"selected":"" }>未发布</option>
		      		<option ${processStatus eq "已发布"?"selected":"" }>已发布</option>
		      		<option ${processStatus eq "已作废"?"selected":"" }>已作废</option>
		    	</select>
		    </td>
		  </tr>
		  <tr><td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">更新人：</td>
		    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
		    	<input class="logininputmanage" type="text" id="processUpdator" 
		    		name="processUpdator" value="${processUpdator }"/></td>
		    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"> </td>
		    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF"> </td>
		  </tr>
		  <tr bgcolor="#F7F7F7" height="50">
		    <td align="center"  colspan="4">
		    	<input type="button" id="SuperSearchButton" value="   查 询   " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input type="button" value="   取 消   " onClick='javascript:j("#hiddenDiv").hide();' class="btn_2k3"/>
		    </td>
		  </tr>
		</table>
</div>
 	</td>
	</tr>
	<tr>
		<td><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
				<tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
					<td width="30">全选
						<input  type="checkbox"  t=top /></td>
					<td>流程名称</td>
					<td>流程分类</td>
					<td>创建人</td>
					<td>创建时间</td>
					<td>更新人</td>
					<td>更新时间</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${page.result}" var="p" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="checkbox" value="${p.id}" t="son" /></td>
						<td><a href="showItem.jhtml?itemType=ProcessDefinition&processDefinition.id=${p.id}">${p.name}</a></td>
						<td>${p.categoryName}</td>
						<td>${p.creator}</td>
						<td><fmt:formatDate value="${p.createTime}" pattern ="yyyy-MM-dd" /></td>
						<td>${p.updator}</td>
						<td><fmt:formatDate value="${p.updateTime}" pattern ="yyyy-MM-dd" /></td>
						<td>
						${p.releaseStatus=="UNRELEASE"?"未发布":""}
						${p.releaseStatus=="RELEASE"?"已发布":""}
						${p.releaseStatus=="ABANDON"?"已作废":""}
						</td> 
						<td><a href="process!copyProcess.jhtml?p.para.id=${p.id}">复制流程</a>  <a href="process.jhtml?atype=release&id=${p.id}">发布</a> <a href="process.jhtml?atype=abandon&id=${p.id}">作废</a> 
						<a href="process.jhtml?atype=edit&id=${p.id}">修改</a> 
						<a href="process.jhtml?atype=delete&id=${p.id}">删除</a> </td>
					</tr>
				</c:forEach>
			</table></td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td align="left"><input type="button" value="批量发布" class="btn_2k3"/>
						<input type="button" value="批量作废" class="btn_2k3"/>
					</td>
					<td align="left"></td>
					<td align="right">
<ambow:pagination actionName=""  total="${page.totalPageCount}" num="${page.currentPageNo}"  otherParams=""/>
					</td>
				</tr>
			</table></td>
	</tr>
</table>
</form>
</body>
</html>
