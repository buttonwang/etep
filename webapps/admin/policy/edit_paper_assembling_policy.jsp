<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组卷策略</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>
<body>
<form action="paperAssemblingPolicy.jhtml"  method="post">
	<input type="hidden" name="atype" value="update" />
	<input type="hidden" name="paperAssemblingPolicy.asfNode.id" value="${id}" />
	<table id="table19" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr id="table161">
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="paperAssemblingPolicy.paperAssemblingMode">
					<option value="0" ${paperAssemblingPolicy.paperAssemblingMode==0?"selected":""}>手工组卷</option>
					<option value="1" ${paperAssemblingPolicy.paperAssemblingMode==1?"selected":""}>动态组卷</option>
					<option value="2" ${paperAssemblingPolicy.paperAssemblingMode==2?"selected":""}>动态出题</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.paperId" value="${paperAssemblingPolicy.paper.id}"/>
				&nbsp;&nbsp;
				<input type="button" value="选择" class="btn_2k3"/>
				&nbsp;&nbsp; </td>
		</tr>
		<tr id="table162">
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
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
