<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
</head>

<body>
<form action="unitTestPolicyTemplate.jhtml" method="post">
<input type="hidden" name="atype" value="edit"><input type="hidden" name="id" value="${unitTestPolicyTemplate.id}"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 新增单元测试类节点策略模板</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	${unitTestPolicyTemplate.name}
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">    	
    		${unitTestPolicyTemplate.retrainingScope==0?"本级":""}
    		${unitTestPolicyTemplate.retrainingScope==1?"上级":""}
    		${unitTestPolicyTemplate.retrainingScope==2?"上上级":""}
    	
    </td>
  </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="400" border="0">
      <tr>
        <td><input type="button" opt=back value="  返 回  " class="btn_2k3"/></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
