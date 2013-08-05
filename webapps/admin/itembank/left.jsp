﻿<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
.left_submenu_son li{
    list-style:none;
    padding-left:70px;
    height:22px;}
    
.left_menu_son li{
    height:28px;
    padding-top:3px;
    padding-left:40px;
    list-style:none;
    margin:0px;}
</style>
</head>
<body leftmargin="0" topmargin="0" class="lefttree" style="padding: 0px;">
	<div id="container">
	<h1 class="title">
		<a href="javascript:void(0)"><font color="#e5f8ff">试 卷</font></a>
	</h1>
	<div class="content">
	<span style="margin-top: 10px;">
	<li class="left_menu"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		<a href="paper!importPaper.jhtml" target="right">整卷导入</a>
	</li>
	<li class="left_menu" onClick="doit(submenu02)">
		<img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		试卷管理<a href="paper!list.jhtml" target="right"></a>
<!--s=======================================================================-->
<c:set var = "span_id" value="submenu02" scope="request"/>
<c:set var = "action_url" value="paper!list.jhtml?" scope="request"/>
		<span class="left_submenu" id="${span_id}" style="display:none;" > 
<c:forEach items="${sessionScope.m_subjects}" var="us" >
	<c:forEach items="${sessionScope.subjectAll}" var="subject" >
		 <c:if test="${subject.code == us.rsg_pk.subjectCode}">
			<li><img src="../images/left_function.gif" align="absmiddle" border="0">
				<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
			</li>
		</c:if>
	</c:forEach>
</c:forEach> 
		</span>
<!--e=======================================================================-->
	</li>
	<li class="left_menu" onClick="doit(submenu07)"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		试卷类型<a href="paperType!list.jhtml" target="right"></a>
<!--s=======================================================================-->
<c:set var = "span_id" value="submenu07" scope="request"/>
<c:set var = "action_url" value="paperType!list.jhtml?" scope="request"/>
		<span class="left_submenu" id="${span_id}"  style="display:none;"> 
<c:forEach items="${sessionScope.m_subjects}" var="us" >
	<c:forEach items="${sessionScope.subjectAll}" var="subject" >
		 <c:if test="${subject.code == us.rsg_pk.subjectCode}">
			<li><img src="../images/left_function.gif" align="absmiddle" border="0">
				<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
			</li>
		</c:if>
	</c:forEach>
</c:forEach> 
		</span>
<!--e=======================================================================-->
	</li>
	
	<li class="left_menu" onClick="doit(submenu08)"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		试卷分类<a href="paperCategory!list.jhtml" target="right"></a>
<!--s=======================================================================-->
<c:set var = "span_id" value="submenu08" scope="request"/>
<c:set var = "action_url" value="paperCategory!list.jhtml?" scope="request"/>
		<span class="left_submenu" id="${span_id}"  style="display:none;"> 
<c:forEach items="${sessionScope.m_subjects}" var="us" >
	<c:forEach items="${sessionScope.subjectAll}" var="subject" >
		 <c:if test="${subject.code == us.rsg_pk.subjectCode}">
			<li><img src="../images/left_function.gif" align="absmiddle" border="0">
				<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
			</li>
		</c:if>
	</c:forEach>
</c:forEach> 
		</span>
<!--e=======================================================================-->
	</li>
	</span>
	</div>

	<h1 class="title">
		<a href="javascript:void(0)"><font color="#e5f8ff">试 题</font></a>
	</h1>
	<div class="content"><span style="margin-top: 10px;">
	<li class="left_menu"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		<a href="importItem!show.jhtml" target="right">批量导入</a>
	</li>
	<li class="left_menu"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		<a href="exportItem.jhtml" target="right">批量导出</a>
	</li>	
	<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0">
		<a href="export/main.jsp" target="main">试题导出</a>
	</li>
	<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0">
		<a href="stat/main.jsp" target="main">试题统计</a>
	</li>	
	<li class="left_menu"  onClick="doit(submenu03)"><img src="../images/left_module.gif"
		align="absmiddle" border="0" alt="">
		试题管理<a href="item!list.jhtml" target="right"></a>
<!--s=======================================================================-->
<c:set var = "span_id" value="submenu03" scope="request"/>
<c:set var = "action_url" value="item!list.jhtml?" scope="request"/>

		<span class="left_submenu" id="${span_id}" style="display:none;" > 
<c:forEach items="${sessionScope.m_subjects}" var="us" >
	<c:forEach items="${sessionScope.subjectAll}" var="subject" >
		 <c:if test="${subject.code == us.rsg_pk.subjectCode}">
			 <c:choose  >
			 	<c:when test="${'EP' == us.rsg_pk.subjectCode}">
				<li><img src="../images/left_function.gif" align="absmiddle" border="0">
						<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
					</li>
		　　		</c:when>
				<c:when test="${'M' == us.rsg_pk.subjectCode}">
				<li><img src="../images/left_function.gif" align="absmiddle" border="0">
						<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
					</li>
		　　		</c:when>
				<c:when test="${'P' == us.rsg_pk.subjectCode}">
					<li><img src="../images/left_function.gif" align="absmiddle" border="0">
						<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
					</li>
		　　		</c:when>
		
				<c:when test="${'C' == us.rsg_pk.subjectCode}">
					<li><img src="../images/left_function.gif" align="absmiddle" border="0">
						<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
					</li>
		　　		</c:when>
				<c:otherwise>
				<li><img src="../images/left_function.gif" align="absmiddle" border="0">
				<a href="${action_url}subject_code=${subject.code}&queryType=1" target="right">${subject.name}</a>
			</li>
				</c:otherwise>
			</c:choose>
			
		</c:if>
	</c:forEach>
</c:forEach> 
		</span>
<!--e=======================================================================-->
	</li>
<!--e=======================================================================-->
</span>
	</div>

	<h1 class="title">
		<a href="javascript:void(0)"><font color="#e5f8ff">字 典</font></a>
	</h1>
	<div class="content"><span style="margin-top: 10px;">
	<!-- <li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0" alt=""> 
		<a href="region!list.jhtml" target="right">地区</a>
	</li> -->
	<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0" alt="">
		<a href="subject!list.jhtml" target="right">学科</a>
	</li>
	<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0" alt="">
		<a href="grade!list.jhtml" target="right">学级</a>
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