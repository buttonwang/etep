<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>

<body>
<form action="trainingUnitNodePolicy.jhtml" method="post">
<input type="hidden" name="atype" value="update" /><input type="hidden" name="trainingUnitNodePolicy.asfNode.id" value="${id}" />
 
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">不通过：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="trainingUnitNodePolicy.failed">
    		<option value="1" ${trainingUnitNodePolicy.failed==1?"selected":""}>返回</option>
    		<option value="0" ${trainingUnitNodePolicy.failed==0?"selected":""}>向前</option>
    	</select>    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="trainingUnitNodePolicy.pass">
    		<option value="1" ${trainingUnitNodePolicy.pass==1?"selected":""}>返回</option>
    		<option value="0" ${trainingUnitNodePolicy.pass==0?"selected":""}>向前</option>
    	</select>    </td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
		<tr>
			<td ><input type="submit" class="btn_2k3" value="  保 存  " />
				&nbsp;&nbsp;
				<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
		</tr>
	</table></td>
  </tr>
</table>
</form>
</body>
</html>
