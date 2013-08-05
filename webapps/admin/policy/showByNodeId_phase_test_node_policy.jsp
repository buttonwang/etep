<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
	Long nodeId = (Long)request.getAttribute("nodeId");
	if(nodeId==null||nodeId==0){
		 request.setAttribute("nodeId",request.getParameter("nodeId"));
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<style>
form{
padding:0px 0px 0px 0px;
}
</style>
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script>
j(function(){
	j("span[opt=opt],input[opt=opt]").click(function(){
		clickShowOrHide(j(this));
	})
})
function clickShowOrHide(jobj,tKey,vKey){
		var _t =tKey||"_t";
		var v = vKey||"v";
		var t = jobj.attr(_t);
		var id = jobj.attr(v);
		if(t=="hide"){
			j("#"+id).hide();
		}
		if(t=="show"){
			j("#"+id).show();
		}
}
function changeStr(inStr){
	var outStr='';
	while(inStr.indexOf(',')>-1){
		inStr=inStr.replace(',','');
		outStr+='----';
	}
	return outStr;
}
</script>
<script>
j(function(){
	<c:forEach items="${v.phaseTestNodePolicyLst}" var="item" varStatus="itemStatus">
	new ValidateForm({
		"fid":"u${item.id}form"
		,"vs":[
			{n:"phaseTestNodePolicy.startValue",sn:"起始值"}
			,{n:"phaseTestNodePolicy.endValue",sn:"结束值"}
		]
	});
	</c:forEach>

	new ValidateForm({
		"fid":"aform"
		,"vs":[
			{n:"phaseTestNodePolicy.startValue",sn:"起始值"}
			,{n:"phaseTestNodePolicy.endValue",sn:"结束值"}
		]
	});
})
</script>
</head>
<body>
<!--<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略维护 &gt; 阶段测试类节点策略</td>
	</tr>
</table>-->

<table id="table7" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr>
		<td width="83%" align="left" valign="top" bgcolor="#FFFFFF"  class="txt12blue"><strong> </strong></td>
		 
		<td width="17%" align="center" valign="top" bgcolor="#FFFFFF"><span class="txt12blue"><span  style="cursor:hand" onclick='javascript:j("#add_phaseTestNodePolicy").show();'>增加条件</span> <span  style="cursor:hand" >删除全部</span></span></td>
	</tr>
</table>
<%if(request.getAttribute("showOpt")!=null){%><%}%>
<table id="table" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流转条件：</td>
		<td width="83%" align="left" bgcolor="#FFFFFF"><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#E3E3E3">
			<tr align="center"  bgcolor="#FFFFFF" class="txt12blue" >
				<td width="10%">序号</td>
				<td width="15%">起始值</td>
				<td width="15%">结束值</td>
				<td width="40%">跳转位置</td>
				<td width="20%">操作</td>
			</tr>
		<c:forEach items="${v.phaseTestNodePolicyLst}" var="item" varStatus="itemStatus">
			<tr id="show_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12">
				<td> </td>
				<td>${item.startValue}</td>
				<td>${item.endValue}</td>
				<td> 
				<c:forEach items="${v.jumpToLst}" var="temp" varStatus="itemStatus">						
				 ${temp.id==item.jumpPosition?temp.name:""} 
				  
				</c:forEach>
				 </td>
				<td class="txt12blue"><span style="cursor:hand" onClick='javascript:j("#edit_${item.id},#show_${item.id}").toggle();'>修改</span>&nbsp; <span style="cursor:hand"><a href="phaseTestNodePolicy.jhtml?atype=delete&id=${item.id}">删除</a></span> </td>
			</tr>
			<tr id="edit_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
			<form action="../policy/phaseTestNodePolicy.jhtml?atype=update" method="post" id="u${item.id}form">
				 
				<td> <input name="phaseTestNodePolicy.id" value="${item.id}" size="5" type="hidden"/><input name="phaseTestNodePolicy.asfNode.id" value="${item.asfNode.id}" size="5" type="hidden"/></td>
				<td><input  type="text" name="phaseTestNodePolicy.startValue" value="${item.startValue}" size="5"/></td>
				<td><input  type="text" name="phaseTestNodePolicy.endValue" value="${item.endValue}" size="5"/></td>
				<td><select name="phaseTestNodePolicy.jumpPosition" >
						<c:forEach items="${v.jumpToLst}" var="temp1" varStatus="itemStatus">						
						<option value="${temp1.id}" ${temp1.id==item.jumpPosition?"selected":""}><script>document.write(changeStr('${temp1.orderNum}'));</script>${temp1.name}</option>
						</c:forEach>
					</select>
					</td>
				<td><input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#edit_${item.id},#show_${item.id}").toggle();'/>	
				</td>
			</form>
			</tr>
		</c:forEach>
		<tr id="add_phaseTestNodePolicy" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
			<form action="../policy/phaseTestNodePolicy.jhtml?atype=add" method="post" id="aform">
				<td><input name="phaseTestNodePolicy.asfNode.id" value="${nodeId}" type="hidden"/></td>
				<td><input  type="text" class="logininputmanage"  name="phaseTestNodePolicy.startValue" value="${item.startValue}" size="5"/></td>
				<td><input  type="text" class="logininputmanage"  name="phaseTestNodePolicy.endValue" value="${item.endValue}" size="5"/></td>
				<td><select name="phaseTestNodePolicy.jumpPosition" >
						<c:forEach items="${v.jumpToLst}" var="temp" varStatus="itemStatus">						
						<option value="${temp.id}">${temp.name}</option>
						</c:forEach>
					</select>
					</td>
				<td><input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#add_phaseTestNodePolicy").hide();'/>	
				</td>
			</form>
			</tr>
		</table></td>
	</tr>
</table>
</form>
</body>
</html>
