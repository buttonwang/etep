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
    <td class="location">当前位置：系统管理 &gt; 系统用户 &gt; 用户信息</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">登陆名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysUser.username}</td>
   </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">真实名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysUser.realName}</td>
   </tr>
    <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">性　　别：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><c:if test="${sysUser.gender==0}">女</c:if><c:if test="${sysUser.gender==1}">男</c:if>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状　　态：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><c:if test="${sysUser.status==0}">启用</c:if><c:if test="${sysUser.status==1}">未启用</c:if></td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">电　　话：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysUser.phoneNumber}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">电子邮件：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysUser.email}</td>
    </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${sysUser.createTime}</td>
    </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        	<input type="button" value="  修 改  " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/adminManage!add.jhtml?id=${sysUser.id}'"/>&nbsp;&nbsp;
        	<input type="button" value="  删 除  " class="btn_2k3" onClick="javascript: if(confirm('确定要删除吗？')){window.location.href='${basePath}/admin/adminManage!delete.jhtml?id=${sysUser.id}';}"/>&nbsp;&nbsp;
        	<input type="button" value="  返 回  " class="btn_2k3" onClick="history.back();"/>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
  <tr>
    <td  width="50%" align="left"    class="txt12blueh">本用户拥有角色</td>
       <td align="right"  class="txt12blue">
         <input type="button" value="  修 改  " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/adminManage!add.jhtml?id=${sysUser.id}';"/>
       </td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
                <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
					<td>角色名称</td>
					<td>备　　注</td>
				</tr>
				<c:forEach items="${sysUser.sysRole}" var="page" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><a href="${basePath}/admin/roleManage!info.jhtml?id=${page.id}">${page.name}</a></td>
						<td title="${page.description}">${fn:substring(page.description,0,35)}</td>
					</tr>
				</c:forEach> 
</table>
</body>
</html>