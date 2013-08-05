<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %> 
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