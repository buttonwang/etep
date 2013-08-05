<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%	/*Node node=(Node)request.getAttribute("item");
	request.setAttribute( "node",node);*/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${node.name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>

<body>
<form action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 新增节点</td>
  </tr>
</table>
<table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${node.name}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${node.nodeType}</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${node.orderNum}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
            <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${node.nodeGroup.name}</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">${node.description}</td>
          </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td><input type="button" value="  修 改  " onClick="javascript: window.location.href='node.jhtml?atype=edit&id=${node.id}&processId=${node.processDefinition.id}'" class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" opt=back value="  返 回  " class="btn_2k3"/></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
