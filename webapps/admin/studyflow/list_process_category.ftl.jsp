<#global PRE_SIZE=3>
<#assign actionURL = "processCategory.jhtml">
<#macro MprocessCategory obj level>
<#compress>
		<#assign parentId ="" >
		<#if obj.parentCategory?exists>
			<#assign parentId =obj.parentCategory.id >
		</#if>
		<#assign id=obj.id?default("")/>
		<#assign showURL=actionURL+"?atype=show&id="+id/>
        <#assign editURL=actionURL+"?atype=edit&id="+id+"&parentId="+parentId/>
		<#assign addURL =actionURL+"?atype=sadd&parentId="+id/>
		<#assign delURL =actionURL+"?atype=delete&id="+id/>
		
		
		<#assign sons =obj.childrenCategories?if_exists />
		
		<#if  sons?exists&&sons?size gt 0>
			<div class="node" <#if level==0>t=hasson</#if>>
					<div class="node" v=item>
							<div class="view"><a href='${showURL}'>&nbsp;&nbsp;${obj.name?if_exists}</a></div>
							<div class="action" t=${(level)*PRE_SIZE}><a href="${addURL}">增加</a>&nbsp;&nbsp;<a href='${editURL}'>修改</a>&nbsp;&nbsp;<a href="${delURL}" >删除</a></div>
							<div class="div_line"></div>
					</div>
				<#list obj.childrenCategories as nxxxxx>
					<@MprocessCategory  obj=nxxxxx level=level+1 />
				</#list>
            </div>
       	<#else>
			<div class="node" <#if level==0>t=hasson</#if> >
					<div class="view" ><a href='${showURL}'>&nbsp;&nbsp;${obj.name?if_exists}</a></div>
					<div class="action" t=${level*PRE_SIZE}><a href="${addURL}">增加</a>&nbsp;&nbsp;<a href='${editURL}'>修改</a>&nbsp;&nbsp;<a href="${delURL}" >删除</a> </div>
					<div class="div_line"></div>
			</div>
		</#if>
</#compress>
</#macro>
<#macro MprocessCategorys objs>
	<#list objs as obj>
		<@MprocessCategory obj=obj level=0 />
	</#list>
</#macro>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
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
	background-image: url(../../../images/title.gif);
	background-repeat:repeat-x;
}
.nodisp {
	display:none;
	width:100%;
	height:55px;
}
</style>
<style>
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
<script src="../js/m.js"></script>
<script src="../js/node/Tree.js"></script>
<script src="../js/node/node.html.js"></script>
<script src="../js/all/DeleteConfirm.js"></script>
<script>
j(function(){
	new DeleteConfirm();
	j("input[value=删除全部]").click(function(e){
		if(confirm("确定要删除全部吗?")){
		}else{
			stopBD(e,1,2)
		}
	})
})
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程分类 &gt; 分类查询</td>
  </tr>
</table>
<table width="100%" border="0" align="center" bgcolor="#E9F0F6">
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0">
	    <tr>
	      <td  width="50%" align="left"    class="txt12blueh">所有分类</td>
          <td align="right"  class="txt12blue">
            <input type="button" value=" 新 增 " class="btn_2k3" onClick="javascript: window.location.href='${actionURL}?atype=sadd';"/>&nbsp;
            <input type="button" value="删除全部"  class="btn_2k3" onClick="javascript: window.location.href='${actionURL}?atype=deleteAll';"/>
          </td>
	    </tr>
	  </table>
    </td>
  </tr>
</table>
<div id="back">
</div>
<div  id="menu" class="linkblueor12">
	<@MprocessCategorys objs=viewMap.processCategoryLst /> 
</div>
</body>
</html>
