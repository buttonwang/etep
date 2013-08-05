<%@ page contentType="text/html; charset=UTF-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script src="../js/all/selectTree.js"></script>

<script>
j(function(){
	ValidateForm({
		"fid":"processCategory_F"
		,"vs":[
			{n:"processCategory.name",sn:"分类名称"}
		]
	});	
	new selectTree({dataLst:${pcListJson},selectName:"processCategory.parentCategory.id",outId:"out",selected:"${parentCategory.id}" });
})
</script>
</head>
<body>
<form id="processCategory_F" action="../studyflow/processCategory.jhtml"
	 method="post"  >
<input type="hidden" name="atype" value="update"><input type="hidden" name="processCategory.id" value="${processCategory.id}">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程分类 &gt; 新增分类</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">分类名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="processCategory.name" value="${processCategory.name}"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父分类：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
		<div id="out"> </div>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="400" border="0">
      <tr>
        <td><input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  onClick="javascript:window.history.back();" value="  取 消  " class="btn_2k3"/></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
