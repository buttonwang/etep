<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/node/MakeSelect.js"></script>
<script src="../js/ParentIframeHeight.js"></script>
<script>
j(function(){
	new ParentIframeHeight("s_node",j("body").height()+20);
	var select_nodeType={
			name:"p.para.nodeType"
			,selectV:"${node.nodeType}"
			,opts:[
				{"v":"GROUP","n":"默认"}
				,{"v":"EVALUATE","n":"模块评测"} 
				,{"v":"PHASETEST","n":"阶段测试"}
				,{"v":"PRACTICE","n":"训练单元"}
				,{"v":"UNITTEST","n":"单元测试"}
			]
	}
	var s =new  MakeSelect(select_nodeType);
	j("#nodeType_edit").html(s.selectHtml);

}) 
</script>
</head>
<body>
<div h=1>
<% if(request.getAttribute("node")!=null){%><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${node.name}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${node.nodeType}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${node.orderNum}</td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">${node.description}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${node.nodeGroup.name==null?"无":node.nodeGroup.name}</td>
  </tr>
</table>
</div>
<%}%>

