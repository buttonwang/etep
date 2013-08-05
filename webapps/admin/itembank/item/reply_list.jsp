<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css" />
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../js/m.js"> </script>
<script type="text/javascript" src="../js/dateJs/WdatePicker.js" defer="defer"></script>

<script language="javascript">
<!--
	//提交form表单
	//AUTHOR: L.赵亚
	function searchItems(){
		$("#pageForm").submit();
	}

	$(document).ready(function(){
		$("#queryItem").click(searchItems);
	});
//-->
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：<c:if test="${!('yes' eq teacherLogin)}">题库 &gt; </c:if>试题审校纠错 &gt; ${title}</td>
  </tr>
</table>
<form action="itemRevise!toQueryReply.jhtml?subject_code=${rqvo.subject_code}" 
	method="post" id="pageForm" name="pageForm">
<table class="txt12555555line-height"  width="100%" border="0" align="center"
 cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
  <tr>
    <td align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">纠错状态：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
		<input type="radio" name="rqvo.replyType" value="0" ${ rqvo.replyType==0?"checked":"" }/>&nbsp;<img src="../images/ask.gif" />（未回复）
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="rqvo.replyType" value="1" ${ rqvo.replyType==1?"checked":"" }/>&nbsp;<img src="../images/pretty.gif" />（已回复）
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="rqvo.replyType" value="-1" ${ rqvo.replyType==-1?"checked":"" }/>&nbsp;全部<br>
    </td>
	</tr>
	<tr><td align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<input type="text" name="rqvo.code" value="${ rqvo.code }" />
	</td></tr></table>
	<table width="100%"><tr><td align="center">
		<input type="button" value=" 查 询 " id="queryItem" class="btn_2k3"/>
	</td></tr></table><br>
	<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
        <td align="right" colspan="3" height="20">
			<ambow:pagination actionName=""
								total="${iravo.page.totalPageCount}"
								totalCount="${iravo.page.totalCount}" 
								num="${iravo.page.currentPageNo}"
								otherParams="" />
        </td>
      </tr>
	  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" height="20px" >
	    <td width="150">题型</td>
	    <td width="50">纠错状态</td>
	    <td >题干</td>
	  </tr>
	  <c:forEach items="${iravo.result}" var="item" varStatus="itemStatus">                  
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
	    <td width="150">&nbsp;${item.type.name}(${item.type.code})</td>
	    <td width="50">&nbsp;
	    	<c:if test="${ item.replyType==-1 }" >
	    		无
	    	</c:if>
	    	<c:if test="${ item.replyType==0 }" >
	    		<img src="../images/ask.gif" />
	    	</c:if>
	    	<c:if test="${ item.replyType==1 }" >
	    		<img src="../images/pretty.gif" />
	    	</c:if>
	    </td>
	    <td>&nbsp;
	    	<a href="itemRevise!showReply.jhtml?itemId=${ item.id }&fromPage=reviseReply">
	    	<c:if test="${not empty item.content}">
	    		${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") }
	    	</c:if>
	    	<c:if test="${empty item.content}">
	    		（题干为空）
	    	</c:if>
	    	</a>
	    	</td>
	  </tr>
	  </c:forEach>
	  <c:if test="${ empty iravo.result }">
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	  	<td width="100%" align="center" colspan="3" height="20px" >没有纠错记录...</td>
	  </tr>
	  </c:if>
	  <tr>
        <td align="right" colspan="3" height="20">
			<ambow:pagination actionName="" total="${iravo.page.totalPageCount}"
							totalCount="${iravo.page.totalCount}" 
							num="${iravo.page.currentPageNo}"
							otherParams="" />
        </td>
      </tr>
	</table>
 </form>
</body>
</html>