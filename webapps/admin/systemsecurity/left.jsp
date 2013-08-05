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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${basePath}/admin/css/admin.css" rel="stylesheet" type="text/css">
<script src="${basePath}/admin/js/prototype.lite.js" type="text/javascript"></script>
<script src="${basePath}/admin/js/moo.fx.js" type="text/javascript"></script>
<script src="${basePath}/admin/js/moo.fx.pack.js" type="text/javascript"></script>
<script>
  //显示隐藏
  var head="display:''"
  function doit(header) {
    var head=header.style
    if (head.display=="none")
      head.display=""
    else
      head.display="none"
  }
</script>
<style>
body {
	font:12px Arial, Helvetica, sans-serif;
	color: #000;
}
#container {
	width: 100%;
}
h1 {
	font-size: 12px;
	margin: 0px;
	width:100%;
	cursor: pointer;
	height: 28px;
	line-height: 28px;
	text-align:center;
}
h1 a {
	display: block;
	font-size:14px;
	padding-top: 1px;
	padding-right: 8px;
	padding-bottom: 0px;
	padding-left: 8px;
	color: #000;
	height: 27px;
	text-decoration: none;
	moz-outline-style: none;
	background-image: url(../images/title.gif);
	background-repeat: repeat-x;
}
.content{
	background-color:#E0EFF8;
}
</style>
</head>
<body leftmargin="0" topmargin="0" class="lefttree" style="padding:0px;">
<div id="container"> 
  <h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">系统用户</font></a></h1>
  <div class="content" > 
   <span style="margin-top:10px;">
      <li class="left_menu"><img src="${basePath}/admin/images/left_module.gif" align="absmiddle" border="0"> <a href="${basePath}/admin/adminManage!list.jhtml" target="right">用户管理</a></li>
    </span>
  </div>
       	
  <h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">用户角色</font></a></h1>
  <div class="content" > 
    <span style="margin-top:10px;">
      <li class="left_menu"><img src="${basePath}/admin/images/left_module.gif" align="absmiddle" border="0"> <a href="${basePath}/admin/roleManage!list.jhtml" target="right">角色管理</a></li>
    </span>
  </div>

  <h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">功能模块</font></a></h1>
  <div class="content" > 
    <span style="margin-top:10px;">
      <li class="left_menu"><img src="${basePath}/admin/images/left_module.gif" align="absmiddle" border="0"> <a href="${basePath}/admin/moduleManage!list.jhtml" target="right">模块</a></li>
      <li class="left_menu"><img src="${basePath}/admin/images/left_module.gif" align="absmiddle" border="0"> <a href="${basePath}/admin/functionManage!list.jhtml" target="right">功能</a></li>
    </span>
  </div>
  <h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">前台用户</font></a></h1>
  <div class="content" > 
    <span style="margin-top:10px;">
      <li class="left_menu"><img src="${basePath}/admin/images/left_module.gif" align="absmiddle" border="0"> <a href="${basePath}/admin/membershippoint/membershipPoint!userList.jhtml" target="right">用户积分</a></li>
      <li class="left_menu"><img src="${basePath}/admin/images/left_module.gif" align="absmiddle" border="0"> <a href="${basePath}/mpc/message/adminMessage!list.jhtml" target="right">信息管理</a></li>
    </span>
  </div>
</div>

<script type="text/javascript">
	var contents = document.getElementsByClassName('content');
	var toggles = document.getElementsByClassName('title');

	var myAccordion = new fx.Accordion(
		toggles, contents, {opacity: true, duration: 400}
	);
	myAccordion.showThisHideOpen(contents[0]);
</script>
</body>
</html>