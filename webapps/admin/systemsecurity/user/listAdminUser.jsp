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
	for(i=0;i<document.all.userid.length;i++)
	{
		if(document.all.userid[i].checked == true){
			rtn = rtn + document.all.userid[i].value + ',';}
	}
	if(rtn.length>0){
	if(confirm('确定要批量删除吗？')){
		rtn=rtn.substring(0,rtn.length-1);
		
		document.pageForm.userids.value=rtn;
		document.pageForm.action="${basePath}/admin/adminManage!delall.jhtml";
		document.pageForm.submit();
		}
	}else{
		alert('请选择要删除的数据');
	}
}
function jyong(){
	
	var rtn='';
	for(i=0;i<document.all.userid.length;i++)
	{
		if(document.all.userid[i].checked == true){
			rtn = rtn + document.all.userid[i].value + ',';}
	}
	if(rtn.length>0){
	if(confirm('确定要批量禁用吗？')){
		rtn=rtn.substring(0,rtn.length-1);
		document.pageForm.userids.value=rtn;
		document.pageForm.action="${basePath}/admin/adminManage!jyong.jhtml";
		document.pageForm.submit();
		}
	}else{
		alert('请选择要禁用的记录');
	}
}
function qyong(){
	
	var rtn='';
	for(i=0;i<document.all.userid.length;i++)
	{
		if(document.all.userid[i].checked == true){
			rtn = rtn + document.all.userid[i].value + ',';}
	}
	if(rtn.length>0){
	if(confirm('确定要批量启用吗？')){
		rtn=rtn.substring(0,rtn.length-1);
		document.pageForm.userids.value=rtn;
		document.pageForm.action="${basePath}/admin/adminManage!qyong.jhtml";
		document.pageForm.submit();
		}
	}else{
		alert('请选择要启用的记录');
	}
}
</script>
</head>
<body>
<form id="pageForm" name="pageForm" method="post" action="${basePath}/admin/adminManage.jhtml">
<input name="userids" value="" type="hidden"/> 
<input name="userid" value="0" type="hidden"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 系统用户 &gt; 用户列表</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left" >
	      	<input type="button" value=" 新 建 " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/admin/adminManage!add.jhtml'"/>&nbsp;&nbsp;&nbsp;&nbsp;
				  <select name="termstr" style="height:20px;">
      			  <option value="username">用户名称：</option>
			      <option value="realName">真实姓名：</option>
			      <option value="email">电子邮件：</option>
		      </select>
			    <input class="logininputmanage" type="text" name="term" id="term" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value="查询" class="btn_2k3"/>&nbsp;
		</td>
        <td align="right">
		   <ambow:pagination actionName=""
        	                  total="${userpage.totalPageCount}" 
        	                  num="${userpage.currentPageNo}" 
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
  <td width="30">全选<input  type="checkbox" onclick=checkAll('userid') /></td>
					<td>登陆名</td>
					<td>真实姓名</td>
					<td>性别</td>
					<td>电话</td>
					<td>电子邮件</td>
					<td>创建日期</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${userpage.result}" var="page" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
					<td width="30"><input  type="checkbox" value="${page.id}" name="userid" /></td>
						<td><a href="${basePath}/admin/adminManage!info.jhtml?id=${page.id}">${page.username}</a></td>
						<td>${page.realName}</td>
						<td><c:if test="${page.gender==1}">男</c:if><c:if test="${page.gender==0}">女</c:if></td>
						<td>${page.phoneNumber}</td>
						<td>${page.email}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${page.createTime}"/></td>
						<td><c:if test="${page.status==0}">启用</c:if><c:if test="${page.status==1}">未启用</c:if></td>
						<td><a href="${basePath}/admin/adminManage!add.jhtml?id=${page.id}">修改</a>　<a onclick="return confirm('确定要删除吗？')" href="${basePath}/admin/adminManage!delete.jhtml?id=${page.id}">删除</a> </td>
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
						<input type="button" value="批量启用" class="btn_2k3" onClick="qyong()"/>
						<input type="button" value="批量禁用" class="btn_2k3" onClick="jyong()"/>
      	</td>
        <td align="right">
		   <ambow:pagination actionName=""
        	                  total="${userpage.totalPageCount}" 
        	                  num="${userpage.currentPageNo}" 
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
