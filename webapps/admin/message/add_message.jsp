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
		function ClickUser(){
			if(document.getElementById("source").value == "-1"){
				alert("请选择信息来源!");
				return false;
			}
			if(document.getElementById("type").value == "-1"){
				alert("请选择信息类型!");
				return false;
			}
			if(document.getElementById("content").value == ""){
				alert("请填写信息内容!");
				return false;
			}
			document.messageInfo.submit();
		}
		</script>
</head>

<body>
<form method="post" name="messageInfo" action="${basePath}/mpc/message/adminMessage!add.jhtml">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 前台用户 &gt; 信息管理 &gt; 新建信息</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">信息来源：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select id="source" name="message.source">
    		<option value="-1">- 请选择 -</option>
			<option value="1">系统通知</option>
	    </select>
    </td>
   </tr>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">信息类型：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
		<select id="type" name="message.type">
			<option value="-1">- 请选择 -</option>
			<option value="1">公开的</option>
			<option value="2">私人的</option>
	    </select>
	</td>
   </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">信息内容：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <textarea name="message.content" id="content" cols="45" rows="3"></textarea>
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