<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>
<body>
<form action="../policy/moduleEvalPolicyTemplate.jhtml" method="post"  lang="en">
<input type="hidden" name="atype" value="edit"><input type="hidden" name="id" value="${moduleEvalPolicyTemplate.id}">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 新增模块评测类节点策略模板</td>
    </tr>
  </table>
  <table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
    <tr>
      <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
      <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${moduleEvalPolicyTemplate.name}
      </td>
    </tr>
    <tr>
      <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
			<tr>
				<td><input type="submit" value="  修 改  " class="btn_2k3"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" opt=back value="  取 消  " class="btn_2k3"/></td>
			</tr>
		</table></td>
    </tr>
  </table>
</form>
</body>
</html>
