<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="css/admin.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #0000FF}
.STYLE7 {color: #FF0000}
.STYLE8 {font-size: 14px}
-->
</style>
<script type="text/javascript">
function showTime()
{
	window.setInterval("displayTime()",1000);
}
function displayTime()
{
	var today = new Date();
	var timeStr = today.getYear() + '年' + (parseInt(today.getMonth())+1) + '月'
	 +today.getDate() +'日'+' ' + today.getHours()+ '时' + today.getMinutes()  +'分'+
	 + today.getSeconds()+'秒';
	document.all.timeDiv.innerHTML = timeStr;
}
</script>
</head>

<body style="padding:0px;margin:0px;" onLoad="showTime()">
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/top_bg.gif" class="managetop">
  <tr>
    <td width="210" height="40" style="border-bottom:#018fcc 5px solid"></td>
    <td align="right" valign="bottom" >
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="32" width="30%"></td>
            <td align="right"><div><span class="STYLE7"><c:choose>
					<c:when test="${adminuser!=null}">
						<c:out value="${adminuser.realName}"/>
					</c:when>
					<c:otherwise>
					  <c:out value="admin"/>
					</c:otherwise>
				</c:choose></span>，你好！&nbsp;&nbsp;现在是 <span id="timeDiv" class="STYLE1"></span>&nbsp;&nbsp;&nbsp;</div></td>
				<td width="75"><div id="button"><span class="linkblue"><a href="${basePath}/admin/systemsecurity/user/EditPassword.jsp" target="_break">修改密码</a></span></div></td>
            <td width="75"><div id="button"><span class="linkblue"><a href="${basePath}/admin/adminLogout.jhtml" target="_parent">退 出</a></span></div></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="86" height="38" align="right" style="background:url(images/top_bgline.gif) repeat-x left bottom"><img src="images/top_arc.gif" width="86" height="38"></td>
            <td align="left" background="images/top_modulebg2.gif"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="managenav">
                <tr>
                <c:forEach items="${m_subjects}" var="item" varStatus="itemStatus">   
                  <c:if test="${itemStatus.index>0}" >
                  <td width="2" align="center" valign="middle"><img src="images/top_splitline.gif" width="2" height="36" align="right" /></td>
                  </c:if>
                  <td width="80" align="center" valign="middle">
                  	<c:if test="${ 'E' eq item.rsg_pk.subjectCode }">
                  		<a href="${basePath}/admin/itembank/itemRevise!toQueryTeacher.jhtml?subject_code=E" 
                  			target="main">英 语</a>
                  	</c:if>
                  	<c:if test="${ 'M' eq item.rsg_pk.subjectCode }">
                  		<a href="${basePath}/admin/itembank/itemRevise!toQueryTeacher.jhtml?subject_code=M" 
                  			target="main">数 学</a>
                  	</c:if>
                  	<c:if test="${ 'P' eq item.rsg_pk.subjectCode }">
                  		<a href="${basePath}/admin/itembank/itemRevise!toQueryTeacher.jhtml?subject_code=P" 
                  			target="main">物 理</a>
                  	</c:if>
                  	<c:if test="${ 'C' eq item.rsg_pk.subjectCode }">
                  		<a href="${basePath}/admin/itembank/itemRevise!toQueryTeacher.jhtml?subject_code=C" 
                  			target="main">化 学</a>
                  	</c:if>
                  </td>
				</c:forEach>
                <td align="center" valign="middle" >&nbsp;</td>
                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
