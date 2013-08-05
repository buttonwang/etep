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
	for(i=0;i<document.all.funid.length;i++)
	{
		if(document.all.funid[i].checked == true){
			rtn = rtn + document.all.funid[i].value + ',';}
	}
	if(rtn.length>0){
	if(confirm('确定要批量删除吗？')){
		rtn=rtn.substring(0,rtn.length-1);
		document.pageForm1.batchMessgeId.value=rtn;
		document.pageForm1.action="${basePath}/mpc/message/adminMessage!batchDelete.jhtml";
		document.pageForm1.submit();
		}
	}else{
		alert('请选择要删除的数据');
	}
}
</script>
</head>
<body>
<form id="pageForm1" name="pageForm1" method="post" action="${basePath}/mpc/message/adminMessage!list.jhtml">
<input type="hidden" id="batchMessgeId" name="batchMessgeId">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 前台用户 &gt; 信息管理</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left" >
	      	<input type="button" value=" 新 建 " class="btn_2k3" onClick="javascript: window.location.href='${basePath}/mpc/message/adminMessage!addBefore.jhtml'"/>&nbsp;&nbsp;&nbsp;&nbsp;
				  <select name="source" style="height:20px;">
				  <option value="-1">全部</option>
      			  <option value="1">系统通知</option>
      			  <option value="2">关注</option>
      			  <option value="3">捉虫</option>
		      </select>
			    <input class="logininputmanage" type="text" name="content" id="term" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value="查询" class="btn_2k3"/>&nbsp;
		</td>
        <td align="right">
		 	</td>
      </tr>
    </table>
	</td>
	</tr>
<tr>
</form>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
  					<td width="30">全选<input  type="checkbox" onclick="checkAll('funid')"/></td>
					<td>消息来源</td>
					<td>消息类型</td>
					<td>消息内容</td>
					<td>发布人</td>
					<td>发布时间</td>
					<td>状　　态</td>
					<td>操　　作</td>
				</tr>
				<c:forEach items="${page.result}" var="message" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td width="30"><input type="checkbox" name="funid" value="${message.id}"/></td>
						<td>
							<a href="">
								<c:choose>
									<c:when test="${message.source==1}">系统消息</c:when>
									<c:when test="${message.source==2}">关注</c:when>
									<c:when test="${message.source==3}">捉虫</c:when>
								</c:choose>
							</a>
						</td>
						<td>
							<c:choose>
								<c:when test="${message.type==1}">公开的</c:when>
								<c:when test="${message.type==2}">私有的</c:when>
							</c:choose>
						</td>
						<td>${message.content}</td>
						<td>${message.publisher.username}</td>
						<td>${message.publishTime}</td>
						<td>
							<c:choose>
								<c:when test="${message.state==-1}">已删除</c:when>
								<c:when test="${message.state==0}">正常</c:when>
							</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${message.userId == ''}"><a href="${basePath}/mpc/message/adminMessage!deploy.jhtml?message.id=${message.id}&sourceModify=${source}&contentModify=${content}">发布</a></c:when>
						 	<c:otherwise>已发布</c:otherwise>
						</c:choose>
						<a href="${basePath}/mpc/message/adminMessage!modifyBefore.jhtml?message.id=${message.id}&sourceModify=${source}&contentModify=${content}">修改</a>　<a onclick="return confirm('确定要删除吗？')" href="${basePath}/mpc/message/adminMessage!delete.jhtml?message.id=${message.id}&source=${source}&content=${content}">删除</a> </td>
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
        <form action="adminMessage!list.jhtml?source=${source}&content=${content}" id="pageForm" method="post">
			<input type="hidden" value="${pageNo}" name="pageNo"/>
		   <ambow:pagination actionName=""
        	                  total="${page.totalPageCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/>
        </form>
		 	</td>
      </tr>
    </table>
	</td>
</tr>  
</table>

</body>
</html>
