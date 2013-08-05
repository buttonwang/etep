<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script src="../js/all/selectTree.js"></script>
<script>
j(function(){
	j("#add_process_form").submit(function(e){
	 	if(j.trim(j("#processDefinitionName").val())==""){
			 stopBD(e,1,2);
		 	 alert("流程名不能为空");
			 j("#processDefinitionName").focus();
		}else{
			if(j("#categoryId").val()==0){
				alert("流程分类不能为空");
				return false;			
			};
		} 
	})
	new selectTree({dataLst:${pcListJson},selectName:"processDefinition.categoryId",outId:"out",selected:"0" });
})
</script>
</head>

<body>
<form action="process.jhtml"  method="post" id="add_process_form">
<input type="hidden" name="atype" value="add" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 新增流程</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input id="processDefinitionName" class="logininputmanage" type="text" 
    	name="processDefinition.name" value="${processDefinition.name}"  />
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input id="processDefinitionVersion" class="logininputmanage" type="text" 
    	name="processDefinition.defVersion" value="${processDefinition.defVersion}"  />
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程分类：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
	 <div id="out"></div>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程说明：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<textarea name="processDefinition.description" value="${processDefinition.description}" id="textarea" 
    	cols="60" rows="3" class="logininputmanage"></textarea>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF">
    <table width="399" border="0">
      <tr>
        <td width="393">
	        <input type="submit" class="btn_2k3" value="  保 存  "/>&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="reset"  class="btn_2k3" value="  取 消  " opt=back/>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>