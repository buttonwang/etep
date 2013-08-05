<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>后台管理系统</title>
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
.content {
	background-color:#E0EFF8;
}
</style>
</head>
<body leftmargin="0" topmargin="0" class="lefttree" style="padding:0px;">
<div id="container">
	<h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">流程分类</font></a></h1>
	<div class="content" > <span style="margin-top:10px;">
		<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0"> <a href="processCategory.jhtml?atype=list" target="right">分类管理</a></li>
		</span> </div>
	<h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">流程定义</font></a></h1>
	<div class="content" > <span style="margin-top:10px;">
		<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0"> <a href="process.jhtml?atype=list&pageNo=1" target="right">流程管理</a></li>
		</span> </div>
	<h1 class="title"><a href="javascript:void(0)"><font color="#e5f8ff">策略模板</font></a></h1>
	 <div class="content" > <span style="margin-top:10px;">
		<li class="left_menu" onclick="doit(submenu01)"><img src="../images/left_module.gif" align="absmiddle" border="0"> <a href="###">流转策略模板</a></li>
		<span class="left_submenu" id="submenu01" style="display:none;">
		<li><img src="../images/left_function.gif" align="absmiddle" border="0"> <a href="../policy/moduleEvalPolicyTemplate.jhtml?atype=list&pageNo=1" target="right">模块评测类策略模板</a></li>
		<li><img src="../images/left_function.gif" align="absmiddle" border="0"> <a href="../policy/phaseTestPolicyTemplate.jhtml?atype=list&pageNo=1" target="right">阶段测试类策略模板</a></li>
		<li><img src="../images/left_function.gif" align="absmiddle" border="0"> <a href="../policy/trainingUnitPolicyTemplate.jhtml?atype=list&pageNo=1" target="right">训练单元类策略模板</a></li>
		<li><img src="../images/left_function.gif" align="absmiddle" border="0"> <a href="../policy/unitTestPolicyTemplate.jhtml?atype=list&pageNo=1" target="right">单元测试类策略模板</a></li>
		</span>
		<li class="left_menu"><img src="../images/left_module.gif" align="absmiddle" border="0"> <a href="../policy/trainingPolicyTemplate.jhtml?atype=list&pageNo=1" target="right">训练策略模板</a></li>
		<li class="left_menu" onclick="doit(submenu02)"><img src="../images/left_module.gif" align="absmiddle" border="0"> <a href="###">组卷策略模板</a></li>
		<span class="left_submenu" id="submenu02" style="display:none;">
		<li><img src="../images/left_function.gif" align="absmiddle" border="0"> <a href="../policy/assemblePaperPolicyTemplate.jhtml?atype=list&pageNo=1" target="right">组卷策略模板</a></li>
		<li><img src="../images/left_function.gif" align="absmiddle" border="0"> <a href="../policy/paperAssemblingReqTemplate.jhtml?atype=list&pageNo=1" target="right">组卷条件模板</a></li>
		</span> </span> </div>
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
