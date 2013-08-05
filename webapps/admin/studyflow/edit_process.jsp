<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script src="../js/all/selectTree.js"></script>
<script>
j(function(){
	j("#uform").submit(function(e){
	 	if(j.trim(j("#processDefinitionName").val())==""){
			 stopBD(e,1,2);
		 	 alert("流程名不能为空");
			 j("#processDefinitionName").focus();
		}else{
			if(j("#categoryId").val()==0){
				if(confirm("您没有选择流程分类，确定提交表单吗？")){
				}else{
					stopBD(e,1,2);
				}
			};		
		} 
	})
	new selectTree({dataLst:${pcListJson},selectName:"processDefinition.categoryId",outId:"out",selected:"${processDefinition.categoryId}" });
})
</script>
</head>
<body>
<form action="process.jhtml"  method="post" id="uform">
<input type="hidden" name="atype" value="update" />
<input type="hidden" name="processDefinition.id" value="${processDefinition.id}" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 流程编辑</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="processDefinitionName" 
    	name="processDefinition.name" value="${processDefinition.name}" />    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程分类：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">	
    	<div id="out"></div>
   </td>
  </tr>
  
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程说明：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<textarea name="processDefinition.description" cols="60" rows="3"
    	 class="logininputmanage">${processDefinition.description}</textarea>
    </td>
  </tr>
  
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="processDefinitionDefVersion"
    	 name="processDefinition.defVersion" value="${processDefinition.defVersion}" />
  </tr>
  
  <tr>
  	<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
  	<td align="left" valign="top" bgcolor="#FFFFFF">
		<select name="p.para.releaseStatus" disabled="disabled" >
			<option value="0" ${processDefinition.releaseStatus=="UNRELEASE"||processDefinition.releaseStatus==null?"selected":""}>未发布</option>
			<option value="1" ${processDefinition.releaseStatus=="RELEASE"?"selected":""} >已发布</option>
			<option value="-1" ${processDefinition.releaseStatus=="ABANDON"?"selected":""} >已作废</option>
		</select>
	</td>
  	</tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
    <table width="448" border="0">
      <tr>
        <td width="333">
        	<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" value="  取 消  " class="btn_2k3" opt=back onClick="window.history.back();"/>
        </td>
      </tr>
    </table>
    </td>
  </tr>

</table>
</form>
</body>
</html>