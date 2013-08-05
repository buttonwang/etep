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
		
		if ("${paperType!=null}") {
			var subjectX = "${paperType.subject.code}";
			var gradeX  = "${paperType.grade.code}"; 	
		} else {
			var subjectX = "${subject_code}";
			var gradeX  = "${grade_code}"; 
		}
		new MySelect(subject_grade,"subjectCode,gradeCode","vt", subjectX, gradeX);
	}catch(e){
	}
})
</script><%----%>
</head>

<body>
<form method="post" action="paperType!save.jhtml" onSubmit="return validateForm('paperType');">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试卷类型 &gt; 试卷类型${code eq ''?'新增':'编辑'}</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷类型编码：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="code" name="paperType.code" value="${paperType.code}" size="15" 
    	 ${code eq ''?'':'readonly="readonly"'} onblur="hasSameName('paperType', 'code')" />    	
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷类型名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="name" name="paperType.name" value="${paperType.name}" size="15"/>
    </td>
  </tr><tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学级：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="paperType.grade.code" id="grade_code" vt=gradeCode>
	</select>
    	 
    </td>
</tr>
 <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="paperType.subject.code" id="subject_code" class="logininputmanage" vt=subjectCode>
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