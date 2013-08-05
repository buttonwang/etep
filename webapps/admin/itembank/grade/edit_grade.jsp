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
</head>

<body>
<form method="post" action="grade!save.jhtml" onsubmit="return validateForm('grade');">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 学级 &gt; 学级${code eq ''?'新增':'编辑'}</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学级编码：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="code" name="grade.code" value="${grade.code}" size="15" 
    	 ${code eq ''?'':'readonly="readonly"'} onblur="hasSameName('grade', 'code')"/>	
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学级名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="name" name="grade.name" value="${grade.name}" size="15"/>
    </td>
  </tr>
  <tr>  
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父学级名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	 <select name="grade.parentGrade.code">
    	 	<option value="">无父学级</option>
    		<c:forEach items="${gradeList}" var="i" varStatus="itemStatus">        
	        	<option value="${i.code}" ${pcode eq i.code ? 'selected="selected"':''}>${i.levelFlag}${i.name}(${i.code})</option>
            </c:forEach>
         </select>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
      <table border="0" width="100%">
   		<tr>
    	<td align="center">
        	<input type="submit" value="  保 存  " class="btn_2k3" />&nbsp;&nbsp;	
        	<input type="button" value="  取 消  " class="btn_2k3" onclick="javascript: history.back()" />&nbsp;&nbsp;
     	</td>
   		</tr>
 	  </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>