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
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","${knowledgePoint!=null?knowledgePoint.subject.code:subject_code}","${knowledgePoint!=null?knowledgePoint.grade.code:grade_code}");
	}catch(e){
	}
})
</script>
</head>

<body>
<form method="post" action="knowledgePoint!save.jhtml" onSubmit="return validateForm('knowledgePoint');">
<input type=hidden value="${subject_code}" name="subject_code"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 知识点 &gt; 知识点${code eq ''?'新增':'编辑'}</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点编码：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="code" name="knowledgePoint.code" value="${knowledgePoint.code}" size="15"
    	  ${code eq ''?'':'readonly="readonly"'} onblur="hasSameName('knowledgePoint', 'code')" />    	
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="name" name="knowledgePoint.name" value="${knowledgePoint.name}" size="15"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父知识点：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
 
    	 <select name="knowledgePoint.parentKnowledgePoint.code">
    	 	<option value="">无父知识点</option>
		<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg">
			<c:if test="${subject_code == usrg.subject.code}">
				<c:forEach items="${usrg.grades}" var="grade" varStatus="status">
				  	<c:forEach items="${knowledgePointList}" var="i" varStatus="status">
						<c:if test="${grade.code == i.grade.code&&subject_code==i.subject.code}">						 
			     <option value="${i.code}" ${pcode eq i.code ? 'selected="selected"':''}>${i.levelFlag}${i.name}/${i.code}</option>
				  		</c:if>
		  			</c:forEach>
		  		</c:forEach>
			</c:if>
		</c:forEach>
         </select>
    </td> 
  </tr>  
  <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学级：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="knowledgePoint.grade.code" id="grade_code" vt=gradeCode>
	</select>
    </td>
</tr>
 <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
	<select name="knowledgePoint.subject.code" id="subject_code" class="logininputmanage" vt=subjectCode>
	</select>
    </td>
</tr>
<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
		<td width="83%" align="left" bgcolor="#FFFFFF">
		<select name="knowledgePoint.state" class="logininputmanage">
			<option value="0" ${knowledgePoint.state==0?'selected':''}>正常</option>
			<option value="-1" ${knowledgePoint.state==-1?'selected':''}>废弃</option>
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