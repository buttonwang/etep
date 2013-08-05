<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/webwork" prefix="ww" %> 
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/interface/SecurityService.js"/>'></script>
<script src="../js/m.js"></script>
<script src="../js/My2Select.js"></script>
<script>
$(function(){
	try{
		eval('var subject_grade = ${m_userSubjectGradeRole_json}');		 
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","${itemType!=null?itemType.subject.code:subject_code}","${itemType!=null?itemType.grade.code:grade_code}");
	}catch(e){
	}
})
</script><%----%>
</head>

<body>
<div id="__out__"></div>
<form method="post" action="itemType!save.jhtml" onSubmit="return validateForm('itemType');">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 题型 &gt; 题型${code eq ''?'新增':'编辑'}</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型编码：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="code" name="itemType.code" value="${itemType.code}" size="15"
    	  ${code eq ''?'':'readonly="readonly"'} onblur="hasSameName('itemType', 'code')"/>    	
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="name" name="itemType.name" value="${itemType.name}" size="25"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">每页显示题数：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="itemNumPerpage" name="itemType.itemNumPerpage" value="${itemType.itemNumPerpage}" size="10"/>
    </td>
  </tr>
  <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学级：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="itemType.grade.code" id="grade_code" vt=gradeCode>
	</select>
    </td>
</tr>
 <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="itemType.subject.code" id="subject_code" class="logininputmanage" vt=subjectCode>
	</select>
    </td>
</tr>
 <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">项目版本：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="itemType.projectVersion" class="logininputmanage" >	 
	<option value="mpc" ${itemType.projectVersion eq 'mpc'?'selected':''}>数理化</option>
	<option value="ky" ${itemType.projectVersion eq 'ky'?'selected':''} >考研</option>	
	</select>
	 
    </td>
</tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
      <table border="0" width="100%">
   		<tr>
    	<td align="center">
        	<input type="submit" value="  保 存  " class="btn_2k3" />&nbsp;&nbsp;	
        	<input type="button" value="  取 消  " class="btn_2k3" onClick="javascript: history.back()" />&nbsp;&nbsp;
     	</td>
   		</tr>
 	  </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>