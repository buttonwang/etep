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
    <td class="location">当前位置：系统管理 &gt; 功能模块 &gt; 功能信息</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">功能名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysFunction.name}</td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">功能所属模块：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><a href="${basePath}/admin/moduleManage!info.jhtml?id=${sysFunction.sysModule.id}"><font color="blue">${sysFunction.sysModule.name}</font></a></td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">功能路径：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysFunction.path}</td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">功能动作：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysFunction.action}</td>
   </tr>
   
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">功能状态：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><c:if test="${sysFunction.status==0}">正常</c:if><c:if test="${sysFunction.status==1}">未启动</c:if></td>
   </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">备　　注：</td>
    <td width="83%" align="center" valign="top" bgcolor="#FFFFFF">${sysFunction.description}</td>
   </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td><input type="button" value="  修 改  " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/functionManage!add.jhtml?id=${sysFunction.id}'"/>&nbsp;&nbsp;
            <input type="button" value="  删 除  " class="btn_2k3" onClick="javascript: if(confirm('确定要删除吗？')){window.location.href='${basePath}/admin/functionManage!delete.jhtml?id=${sysFunction.id}';}"/>&nbsp;&nbsp;
            <input type="button" value="  返 回  " class="btn_2k3" onClick="history.back();"/>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>