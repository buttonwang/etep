<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	session.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
if (window != top)  
	top.location.href = location.href; 
function loadimage(){ 
		    document.getElementById("randImage").src = "${basePath}/admin/image.jsp?"+Math.random(); 
} 
function CheckLogin(){
	if(document.adminlogin.username.value == ""){
		alert("请输入用户名");
		document.adminlogin.username.select();
		document.adminlogin.username.focus();
		return false;
	}
	if(document.adminlogin.password.value == ""){
		alert("请输入密码");
		document.adminlogin.password.select();
		document.adminlogin.password.focus();
		return false;
	}
	if(document.adminlogin.verifycode.value == ""){
		alert("请输入校验码");
		document.adminlogin.verifycode.select();
		document.adminlogin.verifycode.focus();
		return false;
	}
	return true;
}
function init(){ 
	var ctrl=document.getElementById("username"); 
	document.onkeydown= function (event) {   
      var e = event?event:window.event;
       if(e.keyCode=='13'){   
          document.getElementById("adminlogin_form").submit();
       }   
    }
	ctrl.focus(); 
}
</script>
</head>

<body onLoad="init()">
<form action="${basePath}/admin/adminLogin.jhtml" method="post" name="adminlogin" onSubmit="return CheckLogin();" id="adminlogin_form">
<table style="margin-top:100px;" width="567" border="0" align="center">
  <tr>
    <td><table width="567" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><img src="${basePath}/admin/images/login_01.gif" width="567" height="40"></td>
      </tr>
    </table>
      <table width="567" border="0" cellpadding="0" cellspacing="0" style="border-left:#2dbbe5 1px solid; border-right:#2dbbe5 1px solid; background: url(${basePath}/admin/images/login_02.gif) repeat-x; ">
        <tr>
          <td height="246" align="center"><table width="60%" border="0">
            <tr>
              <td height="50" style="font-weight:bold;color:#0492ce;">用户名：</td>
              <td ><input name="username" type="text" id="username" style="width:200px;height:30px;border:1px solid #75c4e6; background-color:#ffffff;padding-left:5px;font-size:14px;padding-top:6px;" value="">
              <c:if test="${isNameExist!=null}">
             		<div class="message" id="login-error"><font color="red">${isNameExist}</font></div>
          	  </c:if>
              </td>
            </tr>
            <tr>
              <td height="50" style="font-weight:bold;color:#0492ce;">密　码：</td>
              <td><input name="password" type="password" id="password" style="width:200px;height:30px;border:1px solid #75c4e6; background-color:#ffffff;padding-left:5px;font-size:14px;padding-top:6px;" value=""></td>
            </tr>
            <tr>
              <td height="50" style="font-weight:bold;color:#0492ce;">验证码：</td>
              <td style="font-size:12px">
              <input name="verifycode" type="text" id="verifycode" style="width:100px;height:30px;border:1px solid #75c4e6; background-color:#ffffff;padding-left:5px;font-size:14px;padding-top:6px;"> 
              <img name="randImage" id="randImage" src="${basePath}/admin/image.jsp" width="45" height="30" border="0" align="absmiddle"/>
              <a href="javascript:loadimage();">看不清楚？</a></td>
            </tr>
            <tr>
              <td height="70">&nbsp;</td>
              <td><img src="${basePath}/admin/images/login_03.gif" width="152" height="46" border="0" onClick="javascript:document.all.adminlogin.submit();" id="login_img" style="cursor:pointer" /></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table width="567" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="${basePath}/admin/images/login_04.gif" width="567" height="12"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
