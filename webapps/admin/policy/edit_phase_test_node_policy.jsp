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
	ValidateForm({
		"fid":"update_phase_test_node_policy_form"
		,"vs":[
			{n:"phaseTestPolicyReq.startValue",sn:"起始值"}
			,{n:"phaseTestPolicyReq.endValue",sn:"起始值"}
		]
	});
	ValidateForm({
		"fid":"add_phase_test_node_policy_form"
		,"vs":[
			{n:"phaseTestPolicyReq.startValue",sn:"起始值"}
			,{n:"phaseTestPolicyReq.endValue",sn:"结束值"}
		]
	});
})
</script>
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
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略维护 &gt; 阶段测试类节点策略</td>
	</tr>
</table>
<table id="table7" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr>
		<td width="83%" align="left" valign="top" bgcolor="#FFFFFF"  class="txt12blue"><strong> </strong></td>
		 
		<td width="17%" align="center" valign="top" bgcolor="#FFFFFF"><span class="txt12blue"><span  style="cursor:hand" onclick='javascript:j("#add_phaseTestNodePolicy").show();'>增加条件</span> <span  style="cursor:hand" >删除全部</span></span></td>
	</tr>
</table>
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
				<td>${item.jumpPosition}</td>
				<td class="txt12blue"><span style="cursor:hand" onClick='javascript:j("#show_${item.id}").hide();j("#edit_${item.id}").show();'>修改</span>&nbsp; <span style="cursor:hand"><a href="phaseTestNodePolicy.jhtml?atype=delete&id=${item.id}">删除</a></span> </td>
			</tr>
			<tr id="edit_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
			<form action="../policy/phaseTestNodePolicy.jhtml?atype=update" method="post" id="update_phase_test_node_policy_form">
				<td></td>
				<td><input  type="text" name="phaseTestNodePolicy.startValue" value="${item.startValue}" size="5"/></td>
				<td><input  type="text" name="phaseTestNodePolicy.endValue" value="${item.endValue}" size="5"/></td>
				<td><input  type="text" name="phaseTestNodePolicy.jumpPosition" value="???"/></td>
				<td><input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#edit_${item.id}").hide()'/>	
				</td>
			</form>
			</tr>
		</c:forEach>
		</table></td>
	</tr>
</table>
<form action="../policy/phaseTestNodePolicy.jhtml?atype=add" method="post" id="add_phase_test_node_policy_form" >
<input name="phaseTestNodePolicy.asfNode.id" value="${nodeId}" type="hidden"/>
<table width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" class="txt12555555line-height" id="add_phaseTestNodePolicy" style="display:none">
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">起始值：</td>
		<td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="phaseTestNodePolicy.startValue" value="${phaseTestNodePolicy.startValue}"/>
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">结束值：</td>
		<td width="83%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="phaseTestNodePolicy.endValue" value="${phaseTestNodePolicy.endValue}"/>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">调整位置：</td>
		<td align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="phaseTestNodePolicy.jumpPosition" value="${phaseTestNodePolicy.jumpPosition}"/>
		</td>
	</tr>
	<tr>
		<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="376" border="0">
			<tr>
				<td width="299"><input type="submit" value="  保 存  " class="btn_2k3"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="  取 消  " class="btn_2k3"  onclick='javascript:j("#add_phaseTestNodePolicy").hide();'/></td>
			</tr>
		</table></td>
	</tr>
</table>
</form>
</body>
</html>
