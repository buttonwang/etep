<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,com.ambow.trainingengine.studyflow.service.ProcessDefinitionService;" errorPage="" %>
<%
	String processId = request.getParameter("processId");
	String parentNodeId  = request.getParameter("parentNodeId");
	if(processId==null){
		processId="";
	}
	if(parentNodeId==null){
		parentNodeId="";
	}
	request.setAttribute("processId",processId.trim());
	request.setAttribute("parentNodeId",parentNodeId.trim());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加节点</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/node/MakeSelect.js"></script>
<script src="../js/all/selectTree.js"></script>
<script>
j(function(){
	var select_nodeType={
			name:"p.para.nodeType"
			,selectV:"GROUP"
			,opts:[
				{"v":"GROUP","n":"默认"}
				,{"v":"EVALUATE","n":"模块评测"} 
				,{"v":"PHASETEST","n":"阶段测试"}
				,{"v":"PRACTICE","n":"训练单元"}
				,{"v":"UNITTEST","n":"单元测试"}
			]
	}
	var s =new  MakeSelect(select_nodeType);
	j("#nodeType").html(s.selectHtml);
	
	new selectTree({outSelect:"div[name=_out]"
		,dataLst:${processNodesJson==null||processNodesJson==""?"[]":processNodesJson}
		,selectName:"parentNodeId"
		,selected:"" 
		,sonKey:"nodes"
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
<form action="node.jhtml" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" css=displayInput style="display:none" > 
<tr>
    <td class="location">将来隐藏的</td>
  </tr>
  <tr>
    <td class="location"> 流程id<input class="logininputmanage" type="text" name="p.para.prid" value="${processId}"  />
     父节点id<input class="logininputmanage" type="text" name="p.para.pid" value="${parentNodeId}"/>
	<input class="logininputmanage" type="text" name="atype" value="add"/> 
	</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 新增节点</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="p.para.name" value="${node.name}"/>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<div id="nodeType"></div>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="p.para.orderNum" value="${node.orderNum}" size="10"/>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
	
	
		<div name="_out"></div>
    	<select name="parentNodeId">
    		<option value="0">无</option>
			
<% 
	if(request.getAttribute("nodes")!=null ){
%>
 <c:forEach items="${nodes}" var="pnode" varStatus="status">
	     <option  value="${pnode.id}" ${pnode.id==node.nodeGroup.id?"selected":""} ><script>document.write(changeStr('${pnode.orderNum}'));</script>${pnode.name}</option>
  </c:forEach>  
<%	}%>
    	</select>
    </td>
  </tr>
  <tr>
    <td width="17%"  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
		<textarea name="node.description" cols="35" rows="2" >${p.para.description==""?"":p.para.description}</textarea>
    </td>
  </tr>
  <tr>
    <td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="365" border="0">
      <tr>
        <td width="359"><input type="submit" class="btn_2k3" value="  保 存  "/>
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" class="btn_2k3" opt=back value="  取 消  "/></td>
      </tr>
    </table></td>
  </tr>
</table>


</form>
</body>
</html>
