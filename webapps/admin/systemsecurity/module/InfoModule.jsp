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
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 功能模块 &gt; 模块信息</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysModule.name}</td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父模块：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><c:if test="${sysModule.subModule.name==null}">无父模块</c:if><c:if test="${sysModule.subModule.name!=null}"><a href="${basePath}/admin/moduleManage!info.jhtml?id=${sysModule.subModule.id}"><font color="blue">${sysModule.subModule.name}</font></a></c:if></td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块路径：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysModule.path}</td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块状态：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><c:if test="${sysModule.status==0}">启用</c:if><c:if test="${sysModule.status==1}">未启用</c:if></td>
   </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">备　　注：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysModule.description}</td>
   </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        	<input type="button" value="  修 改  " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/moduleManage!add.jhtml?id=${sysModule.id}'"/>&nbsp;&nbsp;
        	<input type="button" value="  删 除  " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/moduleManage!delete.jhtml?id=${sysModule.id}'"/>&nbsp;&nbsp;
        	<input type="button" value="  返 回  " class="btn_2k3" onClick="history.back();"/>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
					<td>功能名称</td>
					<td>所属模块</td>
					<td>路  径</td>
					<td>动  作</td>
					<td>状  态</td>
					<td>操  作</td>
				</tr>
				<c:forEach items="${sysModule.sysFunction}" var="page" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><a href="${basePath}/admin/functionManage!info.jhtml?id=${page.id}">${page.name}</a></td>
						<td><a href="${basePath}/admin/moduleManage!info.jhtml?id=${page.sysModule.id}">${page.sysModule.name}</a></td>
						<td>${page.path}</td>
						<td>${page.action}</td>
						<td><c:if test="${page.status==0}">启用</c:if><c:if test="${page.status==1}">未启用</c:if></td>
						<td><a href="${basePath}/admin/functionManage!delete.jhtml?id=${page.id}" onclick="return confirm('确定要删除吗？')">删除</a></td>
					</tr>
				</c:forEach> 
  			</table></td>
   </tr>
</table>
</body>
</html>