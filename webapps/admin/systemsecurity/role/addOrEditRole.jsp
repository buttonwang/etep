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
				alert("角色名不能为空!");
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
			SecurityService.nameOnly("SysRole","name",document.all.name.value,function(val)
			{
				var vale = val*1;
				if(vale!=0){
					document.all.name.value="";
					DWRUtil.setValue("meg", "角色名已存在");
				}else{
					DWRUtil.setValue("meg", "");
				}
			});
		}
		</script>
</head>

<body>
<form method="post" name="userinfo" action="${basePath}/admin/roleManage!save.jhtml">
<input type="hidden" name="id" value="${sysRole.id}">
<input type="hidden" name="rname" value="${sysRole.name}"/>
<input type="hidden" name="funs" value="<c:forEach items="${sysRole.sysFunction}" var="funid">${funid.id},</c:forEach>"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 角色 &gt; <c:choose><c:when test="${sysRole.id>0}">角色编辑</c:when><c:otherwise>新增角色</c:otherwise></c:choose> </td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">角色名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input type="text" value="${sysRole.name}" onblur="nameOnly()" name="name"/>
    <font color="red"><span class="message" id="meg"></span></font></td>
   </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">备　　注：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <textarea name="description" id="description" cols="45" rows="3">${sysRole.description}</textarea>
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