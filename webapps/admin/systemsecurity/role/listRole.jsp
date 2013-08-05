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
<script src="${basePath}/admin/js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
function dele(){
	var rtn='';
	for(i=0;i<document.all.roleid.length;i++)
	{
		if(document.all.roleid[i].checked == true){
			rtn = rtn + document.all.roleid[i].value + ',';}
	}
	if(rtn.length>0){
		if(confirm('确定要批量删除吗？')){
		rtn=rtn.substring(0,rtn.length-1);
		document.pageForm.roleids.value=rtn;
		document.pageForm.action="${basePath}/admin/roleManage!delall.jhtml";
		document.pageForm.submit();
		}
	}else{
		alert('请选择要删除的数据');
	}
}
function onaddFunction(v){
			showx = (event.screenX - event.offsetX)/2; // + deltaX;
			showy = (event.screenY - event.offsetY)/2; // + deltaY;
			window.showModalDialog('${basePath}/admin/roleManage!addFunction.jhtml?id='+v+'&test='+Math.random(), '', 'dialogWidth:800px; dialogHeight:500px; dialogLeft:'+showx+'px; dialogTop:'+showy+'px; status:no; directories:yes;scrollbars:no;Resizable=no; ');
		}
</script>
</head>
<body>
<form id="pageForm" name="pageForm" method="post" action="${basePath}/admin/roleManage.jhtml">
<input name="roleids" value="" type="hidden"/> 
<input name="roleid" value="0" type="hidden"/> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 用户角色 &gt; 角色列表</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left" >
	      	<input type="button" value=" 新 建 " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/roleManage!add.jhtml'"/>&nbsp;&nbsp;&nbsp;&nbsp;
				  <select name="termstr" style="height:20px;">
      			  <option value="name">角色名称：</option>
		      </select>
			    <input class="logininputmanage" type="text" name="term" id="term" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value="查询" class="btn_2k3"/>&nbsp;
		</td>
        <td align="right">
		   <ambow:pagination actionName=""
        	                  total="${rolepage.totalPageCount}" 
        	                  num="${rolepage.currentPageNo}" 
        	                  otherParams=""/>
		 	</td>
      </tr>
    </table>
	</td>
	</tr>
<tr>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
  	<td width="30">全选<input  type="checkbox" onclick=checkAll('roleid') /></td>
					<td>角色名称</td>
					<td>操　　作</td>
				</tr>
				<c:forEach items="${rolepage.result}" var="page" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
					<td width="30"><input  type="checkbox" value="${page.id}"  name="roleid" /></td>
						<td><a href="${basePath}/admin/roleManage!info.jhtml?id=${page.id}">${page.name}</a></td>
						 
						<td><a href="${basePath}/admin/roleSbujectGradeManager.jhtml?rid=${page.id}">配置学科学级</a>　<a href="#" onClick="onaddFunction(${page.id})">配置权限</a>　<a href="${basePath}/admin/roleManage!add.jhtml?id=${page.id}">修改</a>　<a href="${basePath}/admin/roleManage!delete.jhtml?id=${page.id}" onClick="return confirm('确定要删除吗？')">删除</a> </td>
					</tr>
				</c:forEach> 
  			</table>
		</td>
	</tr>
	<tr>
	<td>
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left">
      	<input type="button" value="批量删除" class="btn_2k3" onClick="dele()"/>
      	</td>
        <td align="right">
		   <ambow:pagination actionName=""
        	                  total="${rolepage.totalPageCount}" 
        	                  num="${rolepage.currentPageNo}" 
        	                  otherParams=""/>
		 	</td>
      </tr>
    </table>
	</td>
</tr>  
</table>
</form>
</body>
</html>
