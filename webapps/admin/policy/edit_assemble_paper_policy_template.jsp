<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>

<body>
<form action="assemblePaperPolicyTemplate.jhtml" method="post">
	<input type="hidden" name="atype" value="update"/>
	<input type="hidden" name="assemblePaperPolicyTemplate.id" value="${assemblePaperPolicyTemplate.id}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：策略维护 &gt; 组卷策略模 </td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
    <tr>
      <td  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：</td>
      <td  align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="assemblePaperPolicyTemplate.name" value="${assemblePaperPolicyTemplate.name}" />      </td>
    </tr>
   <tr id="table161" style="display:block;tt:xx">
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
            <td align="left" bgcolor="#FFFFFF">
			<select name="assemblePaperPolicyTemplate.paperAssemblingMode">
				<c:set var="__paperAssemblingMode" value="${assemblePaperPolicyTemplate.paperAssemblingMode}" scope="session"/>
                    <c:import url= "../studyflow/__paperAssemblingMode_select.jsp"/>
                    <c:remove var="__paperAssemblingMode" scope="session"/>	
			</select>            </td>
        </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
    	<tr>
    		<td ><input type="submit" class="btn_2k3" value="  保 存  " />
    			&nbsp;&nbsp;
				<input type="button" opt=back  value="  取 消  " class="btn_2k3"/></td>
    		</tr>
    	</table>    </td>
  </tr>
</table>
</form>
</body>
</html>
