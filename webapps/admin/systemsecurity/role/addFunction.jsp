<#global PRE_SIZE=3>
<#macro MprocessCategory obj level>
<#compress>
		<#assign id=obj.id?default("")/>
		<#assign sons =obj.modules?if_exists />
		
		<#if sons?exists&&sons?size gt 0>
			<#if obj.status==0>
			<div class="node" <#if level==0>t=hasson</#if>>
					<div class="node" v=item>
							<div class="view"><a href='moduleManage!info.jhtml?id=${obj.id}'>${obj.name}</a></div>
							<div class="action" t=${(level)*PRE_SIZE}></div>
							<div class="div_line"></div>
					</div>
				<#list obj.modules as nxxxxx>
					<@MprocessCategory  obj=nxxxxx level=level+1 />
				</#list>
            </div>
           	</#if>
       	<#else>
       		<#if obj.status==0>
			<div class="node" <#if level==0>t=hasson</#if> >
					<div class="node" v=item>
							<div class="view"><a href='moduleManage!info.jhtml?id=${obj.id}'><input  type="checkbox"  name="subfun" v="${obj.id}" value="${obj.id}" onclick="oncheck(this.v)"/>${obj.name}</a></div>
							<div class="action" t=${(level)*PRE_SIZE}></div>
					<div class="div_line"></div>
					</div>
					<#list obj.sysFunction as objb>
					<#if objb.status==0>
					<div class="node">
						<div class="view"><a href='moduleManage!info.jhtml?id=${objb.id}'><input  type="checkbox" <#list sysRole.sysFunction as objf><#if objf.id== objb.id >checked</#if></#list> name="fun" value="${objb.id}" v="${objb.sysModule.id}"/>${objb.name}</a></div>
							<div class="action" t=${(level)*PRE_SIZE}></div>
						<div class="div_line"></div>
						</div>
					</#if>
					</#list>
			</div>
			</#if>
		</#if>
</#compress>
</#macro>
<#macro MprocessCategorys objs>
	<#list objs as obj>
		<@MprocessCategory obj=obj level=0/>
	</#list>
</#macro>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>配置权限</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="css/admin.css" rel="stylesheet" type="text/css">
<style>
.backgroud {
	padding:0px 0px 0px 0px;
	background-color:#E0EFF8;
	font:12px Arial, Helvetica, sans-serif;
	width:100%;
}
.node {
	padding:0px 0px 0px 0px;
	position:relative;
	left:30px;
	width:100%;
}
.action {
	position:absolute;
	padding:0px 0px 0px 0px;
	float:left;
	width:100%;
	top:0px;
}
.view {
	padding:0px 0px 0px 0px;
	width:500px;
	text-align:left;
	text-indent: 20px;
	height:30px;
	vertical-align: middle;
}
.root {
	padding:0px 0px 0px 0px;
	background-image: url(images/title.gif);
	background-repeat:repeat-x;
}
.nodisp {
	display:none;
	width:100%;
	height:55px;
}
.t {
	padding:0px 0px 0px 0px;
	background-color:#FFFFFF;
	list-style:none;
	height:30px;
	border-width:1px;
	border-bottom-style:solid;
	border-color:#CCCCCC;
}
.t1 {
	padding:0px 0px 0px 0px;
	background-color:#F7F7F7;
	list-style:none;
	height:30px;
	border-bottom-width:100%;
	border-width:1px;
	border-bottom-style:solid;
	border-color:#CCCCCC;
}
#back {
	padding:0px 0px 0px 0px;
	overflow:hidden;
}
div {
	font-family:Arial, Helvetica, sans-serif;
	font-size:12px;
	color:#27b5e1;
}
li {
	padding:0px 0px 0px 0px;
}
</style>
<script src="js/m.js"></script>
<script src="js/node/sysTree.js"></script>
<script src="js/node/node.html.js"></script>
<script type="text/javascript">
var t='1';
function oncheck(vid){
	var objs = document.getElementsByName("fun");
  for(var i=0; i<objs.length; i++) {
  	if(objs[i].v==vid){
      if(objs[i].checked == true){
  		objs[i].checked = false;
  	  }else{
  		objs[i].checked = true;
  	  }
  	 }
  }
}
function onchecksub(vid){
var objss = document.getElementsByName("subfun");
  for(var i=0; i<objss.length; i++) {
  	if(objss[i].value==vid){
      if(objss[i].checked == true){
  		objss[i].checked = false;
  	  }else{
  		objss[i].checked = true;
  	  }
  	 }
  	 oncheck(objss[i].v);
  }
  }
function oncheckall(){
var objs = document.getElementsByName("fun");
  for(var i=0; i<objs.length; i++) {
      if(t=='0'){
  		objs[i].checked = false;
  	}else{
  		objs[i].checked = true;
  	}
  }
  var objss = document.getElementsByName("subfun");
  for(var i=0; i<objss.length; i++) {
  	if(t=='0'){
  		objss[i].checked = false;
  	}else{
  		objss[i].checked = true;
  	}
  }
  if(t=='0'){
  	t='1';
  }else{
  	t='0';
  }
}
function conmit(){
	var rtn='';
	for(i=0;i<document.all.fun.length;i++)
	{
		if(document.all.fun[i].checked == true){
			rtn = rtn + document.all.fun[i].value + ',';}
	}
	if(rtn.length>0){
	rtn=rtn.substring(0,rtn.length-1);
	}
	document.form1.funs.value=rtn;
	document.form1.submit();
	window.close();
}
</script>
</head>
<body >
<form name=form1 action="roleManage!addfun.jhtml" target="right">
<input type="hidden" name="funs" value="">
<input type="hidden" name="id" value="${id}">
<input type="hidden" name="fun" value="0">
<table width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
      <td>
       	<input type="button" value="  保 存  " class="btn_2k3" onclick="conmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;
       	<input  type="button" value="  全 选  " class="btn_2k3" onClick="oncheckall()"/>
      </td>
  </tr>
</table>
<div id="back">
</div>
<div  id="menu" class="linkblueor12">
	<@MprocessCategorys objs=modulelist /> 
</div>
</form>
</body>
</html>
