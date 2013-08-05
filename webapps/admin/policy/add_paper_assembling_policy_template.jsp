<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script>
j(function(){
	ValidateForm({
		"fid":"assemble_paper_policy_template_form"
		,"vs":[
			{n:"assemblePaperPolicyTemplate.name",sn:"模块名称"}
		]
	});
})
</script>
</head>

<body>
<form action="" id="assemble_paper_policy_template_form">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 新增组卷策略模板</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="textfield9" id="textfield9"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="papercategory">
    		<option>手工组卷</option>
    		<option>自动组卷</option>
    		<option>动态出题</option>
    	</select>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF">
	<table width="300" border="0"><tr><td ><input type="submit" class="btn_2k3" value="  保 存  " />&nbsp;&nbsp; <input type="button" opt=back  value="  取 消  " class="btn_2k3"/></td></tr></table>
	</td>
  </tr>
</table>
</form>
</body>
</html>
