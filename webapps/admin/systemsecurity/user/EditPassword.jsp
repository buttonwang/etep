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
function sub(){
	var ps=document.useredit.password.value;
	var rps=document.useredit.termstr.value;
	if(ps==""||ps==null){
		alert('密码不能为空！');
		return false;
	}
	if(rps!=ps){
		alert('确认密码与新密码不匹配！');
		return false;
	}
	document.useredit.submit();
}
</script>
</head>

<body>
<form method="post" name="useredit" action="${basePath}/admin/adminManage!psdeit.jhtml">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：修改用户密码</td>
  </tr>
</table>
<c:if test="${termstr!=null}"><script type="text/javascript">alert('修改成功!');window.close();</script></c:if>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">登陆名称：<input type="hidden" name="id" value="${adminuser.id}"/></td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${adminuser.username}</td>
   </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">真实名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${adminuser.realName}</td>
   </tr>
    <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">新密码：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="password" name="password" value="" id="password" size="32"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">确认密码：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="password" name="termstr" value="" id="termstr" size="32"/></td>
   </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        	<input type="button" value="  修 改  " class="btn_2k3" onClick="sub();"/>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>