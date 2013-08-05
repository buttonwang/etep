<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script>
j(function(){
	ValidateForm({
		"fid":"edit_phase_test_policy_template_form"
		,"vs":[
			{n:"phaseTestPolicyTemplate.name",sn:"模块名称"}
		]
	});
	ValidateForm({
		"fid":"add_phase_test_policy_req"
		,"vs":[
			{n:"phaseTestPolicyReq.startValue",sn:"起始值"}
			,{n:"phaseTestPolicyReq.endValue",sn:"起始值"}
		]
	});
	ValidateForm({
		"fid":"edit_phase_test_policy_req"
		,"vs":[
			{n:"phaseTestPolicyReq.startValue",sn:"起始值"}
			,{n:"phaseTestPolicyReq.endValue",sn:"起始值"}
		]
	});
})

function changeStr(inStr){
	var outStr='';
	while(inStr.indexOf(',')>-1){
		inStr=inStr.replace(',','');
		outStr+='----';
	}
	return outStr;
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略维护 &gt; 阶段测试模板类节点策略</td>
	</tr>
</table>
 
 

<form action="../policy/phaseTestPolicyTemplate.jhtml" method="post"  id="edit_phase_test_policy_template_form">
<input type="hidden" name="atype" value="update"/><input type="hidden" name="phaseTestPolicyTemplate.id" value="${phaseTestPolicyTemplate.id}"/>
<table id="table7" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr>
		<td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
    	<td width="67%" align="left" valign="top" bgcolor="#FFFFFF">
    		<input class="logininputmanage" type="text" name="phaseTestPolicyTemplate.name" value="${phaseTestPolicyTemplate.name}"/>
    	</td>
		<td width="18%" align="center" valign="top" bgcolor="#FFFFFF"><span class="txt12blue">
			<span  style="cursor:hand" onclick='javascript:j("#add_phaseTestPolicyReq").show();'>增加条件</span> 
			<span  style="cursor:hand" onClick='if (confirm("确定要删除吗？")) window.location.href="phaseTestPolicyReq.jhtml?atype=deleteAll&pid=${phaseTestPolicyTemplate.id}"'>删除全部</span></span>
		</td>
	</tr>	
</table>
<table id="table7" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr>
		<td height="70" colspan="3" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
				<tr>
					<td ><input type="submit" class="btn_2k3" value="  保 存  " />
						&nbsp;&nbsp;
						<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
				</tr>
			</table></td>
	</tr>
</table>
</form>
 

</form>
</body>
</html>
<table id="table" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	
	<tr>
		<td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流转条件：</td>
		<td width="85%" align="left" bgcolor="#FFFFFF"><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#E3E3E3">
			<tr align="center"  bgcolor="#FFFFFF" class="txt12blue" >
				<td width="10%">序号</td>
				<td width="10%">起始值</td>
				<td width="10%">结束值</td>
				<td width="10%">学习指导</td>
				<td width="10%">学习提示</td>
				<td width="30%">跳转位置</td>
				<td width="20%">操作</td>
			</tr>
		<c:forEach items="${v.phaseTestPolicyReqLst}" var="item" varStatus="itemStatus">
			<tr id="show_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12">
				<td>${itemStatus.index+1}</td>
				<td>${item.startValue}</td>
				<td>${item.endValue}</td>
				<td>${item.studyGuide}</td>
				<td>${item.studyTip}</td>
				<td> 
					<c:forEach items="${v.jumpToLst}" var="temp" varStatus="itemStatus">						
				 	${temp.id==item.jumpPosition?temp.name:""} 				  
					</c:forEach>
				 </td>
				<td class="txt12blue">
					<span style="cursor:hand" onClick='javascript:j("#edit_${item.id},#show_${item.id}").toggle();'>修改</span>&nbsp; 
					<span style="cursor:hand"><a href="phaseTestPolicyReq.jhtml?atype=delete&id=${item.id}&pid=${phaseTestPolicyTemplate.id}" onClick="return confirm('确定要删除吗？');">删除</a></span>
				</td>
			</tr>
			<tr id="edit_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
			<form action="../policy/phaseTestPolicyReq.jhtml?atype=iupdate" method="post" id="edit_phase_test_policy_req" >
				 
				<td> <input name="phaseTestPolicyReq.id" value="${item.id}" size="5" type="hidden"/><input name="phaseTestPolicyReq.phaseTestPolicyTemplate.id" value="${phaseTestPolicyTemplate.id}" size="5" type="hidden"/></td>
				<td><input  type="text" name="phaseTestPolicyReq.startValue" value="${item.startValue}" size="5"/></td>
				<td><input  type="text" name="phaseTestPolicyReq.endValue" value="${item.endValue}" size="5"/></td>
				<td><input  type="text" name="phaseTestNodePolicy.studyGuide" value="${item.studyGuide}" size="5"/></td>
				<td><input  type="text" name="phaseTestNodePolicy.studyTip" value="${item.studyTip}" size="5"/></td>
				<td><select name="phaseTestPolicyReq.jumpPosition" >
						<c:forEach items="${v.jumpToLst}" var="temp1" varStatus="itemStatus">						
						<option value="${temp1.id}" ${temp1.id==item.jumpPosition?"selected":""}><script>document.write(changeStr('${temp1.orderNum}'));</script>1${temp1.name}</option>
						</c:forEach> 
					</select>
					</td>
				<td><input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#edit_${item.id},#show_${item.id}").toggle();'/>	
				</td>
			</form>
			</tr>
		</c:forEach>
		<tr id="add_phaseTestPolicyReq" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
			<form action="../policy/phaseTestPolicyReq.jhtml?atype=iadd" method="post" id="add_phase_test_policy_req">
				<td><input name="phaseTestPolicyReq.phaseTestPolicyTemplate.id" value="${phaseTestPolicyTemplate.id}" type="hidden"/></td>
				<td><input  type="text" class="logininputmanage"  name="phaseTestPolicyReq.startValue" value="${item.startValue}" size="5"/></td>
				<td><input  type="text" class="logininputmanage"  name="phaseTestPolicyReq.endValue" value="${item.endValue}" size="5"/></td>
				<td><input  type="text" class="logininputmanage"  name="phaseTestNodePolicy.studyGuide" value="${item.studyGuide}" size="5"/></td>
				<td><input  type="text" class="logininputmanage"  name="phaseTestNodePolicy.studyTip" value="${item.studyTip}" size="5"/></td>
				<td><select name="phaseTestPolicyReq.jumpPosition" >
						<option value="0">无</option>
						<!--<forEach items=" v.jumpToLst " var="temp" varStatus="itemStatus">						
						<option value=" temp.id "> temp.name </option>
						</forEach>-->
					</select>
					</td>
				<td><input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#add_phaseTestPolicyReq").hide();'/>	
				</td>
			</form>
			</tr>
		</table></td>
	</tr>
</table>