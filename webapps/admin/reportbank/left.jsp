<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.ambow.trainingengine.itembank.service.GradeService"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.lite.js" type="text/javascript"></script>
<script src="../js/moo.fx.js" type="text/javascript"></script>
<script src="../js/moo.fx.pack.js" type="text/javascript"></script>
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
  <h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">报表管理</font></a></h1>
  <div class="content" > 
   <span style="margin-top:10px;">
      <li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0">
      	<a href="wordBasic!list.jhtml" target="right">考试情况统计</a>
      </li>
      <li class="left_menu"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		<a href="importItem!show.jhtml" target="right">考试情况统计-按分公司</a>
	  </li>
	  <li class="left_menu"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		<a href="exportItem.jhtml" target="right">考试情况统计-按科室</a>
	  </li>
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