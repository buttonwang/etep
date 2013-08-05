<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
</head>
<body style="height:100px;">
<form action="../policy/nodeGroupPolicy.jhtml"  method="post"  >
<input type="hidden" name="atype" value="update">
<input type="hidden" name="nodeGroupPolicy.asfNode.id" value="${id}">
<table id="table3" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否为显示模块：</td>
		<td width="83%" align="left" bgcolor="#FFFFFF"><input type="radio" name="nodeGroupPolicy.isDisplayModule" value="1" ${nodeGroupPolicy.isDisplayModule==1?"checked":""} />
			是&nbsp;&nbsp;
			<input type="radio" name="nodeGroupPolicy.isDisplayModule" value="0"  ${nodeGroupPolicy.isDisplayModule==0?"checked":""}/>
			否 </td>
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
