<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();
	session.setAttribute("basePath",basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${basePath}/admin/css/admin.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/interface/SecurityService.js"/>'></script>
<script type="text/javascript">
		function ClickUser(){
			if(document.all.name.value==""){
				alert("模块名不能为空!");
				document.all.name.focus();
				document.all.name.select();
				return false;
			}
			document.userinfo.submit();
		}
		function nameOnly(){
			var rname=document.all.rname.value;
			var uname=document.all.name.value;
			if(rname!=null&&rname!=""){
				if(rname==uname){
					return;
				}
			}
			SecurityService.nameOnly("SysModule","name",document.all.name.value,function(val)
			{
				var vale = val*1;
				if(vale!=0){
					document.all.name.value="";
					DWRUtil.setValue("meg", "模块名已存在");
				}else{
					DWRUtil.setValue("meg", "");
				}
			});
		}
		</script>
</head>

<body>
<form method="post" name="userinfo" action="${basePath}/admin/moduleManage!save.jhtml">
<input type="hidden" name="id" value="${sysModule.id}">
<input type="hidden" name="rname" value="${sysModule.name}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 功能模块 &gt; <c:choose><c:when test="${sysModule.id>0}">模块编辑</c:when><c:otherwise>新增模块</c:otherwise></c:choose></td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input type="text" value="${sysModule.name}"  onblur="nameOnly()" name="name"/>
    <font color="red"><span class="message" id="meg"></span></font></td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父模块：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <select name="parentId">
    	<option value="0">请选择父模块</option>
    <c:forEach items="${modulelist}" var="subm">
    	<c:if test="${sysModule.id!=subm.id}">
        <option value="${subm.id}" <c:if test="${aid==subm.id}">selected="selected"</c:if><c:if test="${sysModule.subModule.id==subm.id}">selected="selected"</c:if>>${subm.levelFlag}${subm.name}</option></c:if>
    </c:forEach>
    </select>
    </td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块路径：<input type="hidden" name="isLeaf" value="<c:choose><c:when test="${sysModule.id>0}">${sysModule.isLeaf}</c:when><c:otherwise>1</c:otherwise></c:choose>"/></td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input type="text" value="${sysModule.path}" name="path" size="60"/></td>
   </tr>
   <!--tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否末层：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <select name="isLeaf">
        <option value="0">工作模块组</option>
    	<option value="1" <c:if test="${sysModule.isLeaf==1}">selected="selected"</c:if>>末层功能模块</option>
    </select>
    </td>
   </tr-->
   <c:choose><c:when test="${sysModule.id>0}"><tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块状态：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <select name="status">
        <option value="0">启用</option>
    	<option value="1" <c:if test="${sysModule.status==1}">selected="selected"</c:if>>未启用</option>
    </select>
    </td>
   </tr></c:when><c:otherwise><input type="hidden" name="status" value="1"/></c:otherwise></c:choose>
   
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">备　　注：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <textarea name="description" id="description" cols="45" rows="3">${sysModule.description}</textarea>
    </td>
   </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        	<input type="button" value="  保 存  " class="btn_2k3" onclick=" ClickUser()"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" value="  返 回  " class="btn_2k3" onClick="history.back();"/>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>