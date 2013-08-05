<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="ambow" prefix="ambow"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script type="text/javascript">
function doo() {
	document.getElementById('flag').value = true;
}

</script>
<script type="text/javascript">
function checkCondition() {
	var name = document.getElementById('processDefinition.name').value;
	var discription = document.getElementById("descri").value;
	var flag = document.getElementById('flag').value;
	//alert(flag);
	if(flag=='false' && !name && !discription) {
		alert('请选择一个查询条件');
		document.getElementById('processDefinition.name').focus();
		return false;
	}
	document.pageForm.submit();
}
</script>
</head>

<body>
<form action="../studyflow/processCategory.jhtml" method="post"  lang="en">
<input type="hidden" name="atype" value="edit">
<input type="hidden" name="id" value="${processCategory.id}">
<input type="hidden" name="parentId" value="${processCategory.parentCategory.id}">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程分类 &gt; 流程查看</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">分类名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	${processCategory.name}
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父分类：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${processCategory.parentCategory.name}</td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="400" border="0">
      <tr>
        <td><input type="submit"  value="  修 改  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" opt=back  value="  取 消  " class="btn_2k3"/></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
<table class="location" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>本分类流程列表</td>
    <td align="right">
    	<input type="button" value=" 新增流程 " class="btn_2k3" 
    	onClick="javascript: window.location.href='../studyflow/process.jhtml?atype=sadd'"/>
    </td>
  </tr>
</table>
<form action="process!findByCondition.jhtml" method="post" name="pageForm" id="pageForm">
	<input type="hidden" name="processDefinition.categoryId" value="${not empty id ? id :(processDefinition.categoryId)}"/>
	<input type="hidden" name="flag" value="${empty flag? 'false' : flag }"/>
	<table width="100%" border="0" cellspacing="1" cellpadding="6" bgcolor="">
	<tr>
		<td width="15%" align="right" bgcolor="#E9F0F6">流程名称：</td>
		<td width="35%" align="left" bgcolor="#E9F0F6" >
			<input name="processDefinition.name" type="text" class="logininputmanage" 
					size="15" value="${processDefinition.name }"/>
		</td>
		<td width="15%" bgcolor="#E9F0F6" align="right">流程说明：</td>
		<td width="35%" bgcolor="#E9F0F6" align="left"><input name="processDefinition.description" id="descri" 
									type="text" class="logininputmanage" 
					size="15" value="${processDefinition.description }"/>
		</td>
	</tr>
	<tr>
		<td width="15%" bgcolor="#E9F0F6" align="right">流程版本：</td>
		<td width="35%" bgcolor="#E9F0F6" align="left">
				<c:forEach items="${versions}" var="v" varStatus="status">
					<input name="processDefinition.defVersion" type="radio" value="${v}" 
						<c:if test="${flag && v eq processDefinition.defVersion}">checked</c:if> 
						onclick="doo();"/>${v }&nbsp;
				</c:forEach>
		</td>
		<td width="15%" bgcolor="#E9F0F6" align="center" colspan="2" >
				<input type="button" value="查询" class="btn_2k3" 
					onclick="javascript: checkCondition();" />&nbsp;</td>
	</tr>
	</table>
	<table width="100%" ><tr><td width="100%" align="right">
        		&nbsp;<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  totalCount="${page.totalCount}"
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/></td></tr></table>
</form>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
    <td>流程名称</td>
    <td>流程版本</td>
    <td>创建人</td>
    <td>创建时间</td>
    <td>更新人</td>
    <td>更新时间</td>
    <td>状态</td>
  </tr>

	<c:forEach items="${not empty v.processLst ? v.processLst: (not empty page.result? page.result : null)}"  var="p" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						 
						<td><a href="showItem.jhtml?itemType=ProcessDefinition&processDefinition.id=${p.id}">${p.name}</a></td>
						<td>${p.defVersion }</td>
						<td>${p.creator}</td>
						<td>${p.createTime}</td>
						<td>${p.updator}</td>
						<td>${p.updateTime}</td>
						<td>
						${p.releaseStatus=="UNRELEASE"?"未发布":""}
						${p.releaseStatus=="RELEASE"?"已发布":""}
						${p.releaseStatus=="ABANDON"?"已作废":""}
						</td>
					</tr>
	</c:forEach>
</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="6">
	      <tr>      	
	      	<td align="left">&nbsp;
	      	</td>
	        <td class="ReporterCol_2" align="right">
        		<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  totalCount="${page.totalCount}"
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/>
    		</td>
	      </tr>
	    </table>
</body>
</html>
