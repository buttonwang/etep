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
<script type="text/javascript">
function onaddFunction(v){
			showx = (event.screenX - event.offsetX)/2; // + deltaX;
			showy = (event.screenY - event.offsetY)/2; // + deltaY;
			window.showModalDialog('${basePath}/admin/roleManage!addFunction.jhtml?id='+v+'&test='+Math.random(), '', 'dialogWidth:800px; dialogHeight:500px; dialogLeft:'+showx+'px; dialogTop:'+showy+'px; status:no; directories:yes;scrollbars:no;Resizable=no; ');
		}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 角色 &gt; 角色信息</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">角色名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysRole.name}</td>
   </tr>
  <tr>
    <td width="17%" align="center" valign="top" bgcolor="#F7F7F7"  class="txt12blue">备　　注：</td>
    <td width="83%" align="center" valign="top" bgcolor="#FFFFFF">${sysRole.description}
    </td>
   </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        	<input type="button" value="  修 改  " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/roleManage!add.jhtml?id=${sysRole.id}'"/>&nbsp;&nbsp;
        	<input type="button" value="  删 除  " class="btn_2k3" onClick="javascript: if(confirm('确定要删除吗？')){window.location.href='${basePath}/admin/roleManage!delete.jhtml?id=${sysRole.id}';}"/>&nbsp;&nbsp;
        	<input type="button" value="  返 回  " class="btn_2k3" onClick="history.back();"/>
        </td>
      </tr></table>
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
  <tr>
    <td  width="50%" align="left"    class="txt12blueh">本角色功能权限</td>
       <td align="right"  class="txt12blue">
         <input type="button" value=" 配置权限 " class="btn_2k3"  onclick="onaddFunction(${sysRole.id})"/>
       </td>
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
				<c:forEach items="${sysRole.sysFunction}" var="page" varStatus="status">
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