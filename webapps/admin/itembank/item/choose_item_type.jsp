<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<script type="text/javascript">
	function jumpTo(){
		location.href="add_item_" + document.all.itemType.value + ".htm";
	}
</script>
</head>
<body>
<form action="addItem!add.jhtml" method="post">
<input type="hidden" name="subject_code" value="${subject_code}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 新增试题</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择题型：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">  
    	 <select name="itemTypeCode">
 <c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
	<c:if test="${subject_code eq usrg.subject.code}">
		<c:forEach items="${usrg.grades }" var="grade">		 
			<c:forEach items="${itemTypeList}" var="itemType" varStatus="itemTypeStatus">
				<c:if test="${itemType.grade.code eq grade.code && subject_code eq itemType.subject.code}"><option value="${itemType.code}">(${itemType.code})${itemType.name}</option></c:if>
			 </c:forEach>
		</c:forEach>
	</c:if>
</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
	        <input type="submit" value="  确 定  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
    	    <input type="button" value="  返 回  " onClick="javascript: history.back()"  class="btn_2k3"/></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
