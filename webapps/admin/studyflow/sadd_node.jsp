<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*,com.ambow.studyflow.domain.*,com.ambow.trainingengine.studyflow.service.ProcessDefinitionService;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加节点</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script src="../js/all/selectTree.js"></script>
<script>
j(function(){
	selectTree({outSelect:"div[name=__out]"
		,dataLst:${processNodesJson==null||processNodesJson==""?"[]":processNodesJson}
		,selectName:"p.para.pid"
		,selected:"${v.p.para.pid[0]}" 
		,sonKey:"nodes"
	})
	ValidateForm({
		"fid":"add_node_form"
		,"vs":[
			{n:"p.para.name",sn:"节点名称"}
		]
	});
}) 
</script>
</head>
<body>
<form action="node.jhtml"  method="post" id="add_node_form">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="test">
		<tr>
			<td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 新增节点</td>
		</tr>
	</table>
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.name" value=""/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><select name="p.para.nodeType" id="_select1">
					<option value="GROUP" ${node.nodeType=="GROUP"?"selected":""} >默认(节点组)</option>
					<option value="PRACTICE" ${node.nodeType=="PRACTICE"?"selected":""} >训练单元(训练)</option>
					<option value="EVALUATE" ${node.nodeType=="EVALUATE"?"selected":""} >模块测试（评测）</option>
					<option value="PHASETEST" ${node.nodeType=="PHASETEST"?"selected":""} >阶段测试(前测)</option>
					<option value="UNITTEST" ${node.nodeType=="UNITTEST"?"selected":""} >单元测试(后测)</option>
				</select></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.orderNum" value="" size="10"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><div name="__out"></div></td>
		</tr>
		<tr>
			<td width="17%"  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF" colspan="3"><textarea name="p.para.description" cols="35" rows="2" ></textarea>
			</td>
		</tr>
		<tr>
			<td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="365" border="0">
					<tr>
						<td width="359"><input type="submit" class="btn_2k3" value="  保 存  "/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="reset" class="btn_2k3" opt=back  value="  取 消  "/></td>
					</tr>
				</table></td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" css="displayInput"  style="display:none">
		<tr>
			<td class="location">将来隐藏的</td>
		</tr>
		<tr>
			<td class="location"> 流程id
				<input class="logininputmanage" type="text" name="p.para.prid" value='${v.p.para.prid[0]}'/>
				操作
				<input class="logininputmanage" type="text" name="atype" value="add"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
