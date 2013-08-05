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
<form method="post" action="region!save.jhtml" onsubmit="return validateForm('region');">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 地区 &gt; 地区${code eq ''?'新增':'编辑'}</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">地区编码：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="code" name="region.code" value="${region.code}" size="15" 
    		${code eq ''?'':'readonly="readonly"'}  onblur="hasSameName('region', 'code')" />
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">地区名称：</td>
    <td width="83%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" id="name" name="region.name" value="${region.name}" size="15"/>
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