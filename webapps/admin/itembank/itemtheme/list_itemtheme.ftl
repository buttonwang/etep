<#function isIn  it  itemsP >
   <#assign items = itemsP?split(",")>
   <#list items as it_i>
   		<#if it == it_i?trim>
   		<#return "t">
		</#if>
   </#list>
   <#return "f">
</#function>

<#function subjectGradeIsInUserSubjectGradeRole subject,grade,userSubjectGradeRoleList>
   <#list userSubjectGradeRoleList as usrg>
	<#if  subject==usrg.subject.code  >
		<#list usrg.grades as grade>
			<#if  grade == grade.code >
				<#return "t">
			</#if>
		</#list>
	</#if>
   </#list>
   <#return "f">
</#function>
 

<#function isInSubject  it  roleGradeSubjectS  >
   <#list roleGradeSubjectS as obj>
	<#if isIn(it,obj.rsg_pk.subjectCode)=="t" >
	 		<#return "t">
	</#if>
   </#list>
   <#return "f">
</#function>
<#function isInGrade  it  roleGradeSubjectS  >
   <#list roleGradeSubjectS as obj>
	<#if isIn(it,obj.rsg_pk.gradeCode)=="t" >
	 		<#return "t">
	</#if>
   </#list>
   <#return "f">
</#function>

<#global PRE_SIZE=3>
<#assign actionURL = "itemTheme.jhtml">
<#macro MItem obj level>
<#compress>
<#assign graceCode_forShow  = grade_code?default(obj.grade.code)/>
<#if isInGrade(graceCode_forShow,Session["m_RSGList"])=="t" &&  obj.subject.code == subject_code?default("") && graceCode_forShow==obj.grade.code >
<#-- if subjectGradeIsInUserSubjectGradeRole(subject_code,graceCode_forShow,Session["m_userSubjectGradeRole"])=="t"-->
	 
		<#assign code=obj.code?default("")/>
		<#assign showURL="itemTheme!show.jhtml?subject_code="+obj.subject.code+"&code="+code/>
        <#assign editURL="itemTheme!edit.jhtml?subject_code="+obj.subject.code+"&code="+code/>
		<#assign addURL ="itemTheme!edit.jhtml?subject_code="+obj.subject.code+"&pcode="+code/>
		<#assign delURL ="itemTheme!delete.jhtml?code="+code/>
		<#assign sons =obj.childrenItemThemes />
		<#if sons?exists&&sons?size gt 0>
			
			<div class="node" <#if level==0>t=hasson</#if>>
				<div class="node" v=item>
						<div class="view"><a href='${showURL}'>${obj.code}&nbsp;&nbsp;${obj.name}</a></div>
						<div class="action" t=${(level)*PRE_SIZE}><a href="${addURL}">增加</a>&nbsp;&nbsp;<a href='${editURL}'>修改</a>&nbsp;&nbsp;<a href="${delURL}" onclick="return confirm('确定要删除吗？')">删除</a></div>
						<div class="div_line"></div>
				</div>
				<#list obj.childrenItemThemes as nxxxxx>
					<@MItem  obj=nxxxxx level=level+1 />
				</#list>
            </div>
			
       	<#else>
			<#if isInGrade(graceCode_forShow,Session["m_RSGList"])=="t" &&  obj.subject.code == subject_code?default("") && graceCode_forShow==obj.grade.code > 
			<#--if subjectGradeIsInUserSubjectGradeRole(subject_code,graceCode_forShow,Session["m_userSubjectGradeRole"])=="t"-->
<div class="node" <#if level==0>t=hasson</#if> >
				<div class="view" ><a href='${showURL}'>${obj.code}&nbsp;&nbsp;${obj.name}</a></div>
				<div class="action" t=${level*PRE_SIZE}><a href="${addURL}">增加</a>&nbsp;&nbsp;<a href='${editURL}'>修改</a>&nbsp;&nbsp;<a href="${delURL}" onclick="return confirm('确定要删除吗？')">删除</a></div>
				<div class="div_line"></div>
			</div>
			</#if>
		</#if>
</#if>
</#compress>
</#macro>
<#macro MList objs>
	<#list objs as obj>
		<@MItem obj=obj level=0 />
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
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 题材 &gt; 题材列表</td>
  </tr>
</table>
<table width="100%" border="0" align="center" bgcolor="#E9F0F6">
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0">
	    <tr>
	      <td width="50%" align="left"    class="txt12blueh">所有题材</td>
          <td align="right"  class="txt12blue">
		  <form action="itemTheme!list.jhtml?queryType=1">
		   <input name="subject_code" type="hidden" value="${subject_code?default("")}">
		  学级：<select name="grade_code" id="grade_code">
		  <option value="">选择年级</option>
<#list  Session.m_userSubjectGradeRole as obj>
	<#if subject_code?default("") == obj.subject.code >
		<#list obj.grades as grades >
			<option value="${grades.code}" <#if grades.code == grade_code ?default("")>selected</#if> >${grades.name}</option>
		</#list>
	</#if>
</#list></select>
           &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"  class="btn_2k3" value=" 筛 选 " /> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value=" 新 增 "  class="btn_2k3" onClick="javascript: window.location.href='itemTheme!edit.jhtml?subject_code=${subject_code}';"/>&nbsp;
            <input type="button" value="删除全部" class="btn_2k3" onClick="javascript: if (confirm('确定要删除吗？')) window.location.href='itemTheme!deleteAll.jhtml';"/>
			</form>
          </td>
	    </tr>
	  </table>
    </td>
  </tr>
</table>
<div id="back">
</div>
<div id="menu" class="linkblueor12">
	<@MList objs=itemThemeList/>
</div>
</body>
</html>