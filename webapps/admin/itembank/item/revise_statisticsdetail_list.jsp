<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css" >
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../js/m.js" type="text/javascript"> </script>
<script type="text/javascript" src="../js/dateJs/WdatePicker.js" defer="defer"></script>

<script type="text/javascript" language="javascript">

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：<c:if test="${!('yes' eq teacherLogin)}">题库 &gt; </c:if>试题审校统计 &gt; ${title}</td>
  </tr>
</table>
<form action="itemRevise!showDetail.jhtml" method="post" id="pageForm" name="pageForm">
<input type="hidden" name="subject_code" value="${rqvo.subject_code}" >
<input type="hidden" name="pageInit" value="no" >
<input type="hidden" name="status" value="${status}" >
<input type="hidden" name="reply" value="${reply}" >
	

	<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
        <td align="right" colspan="4" height="20">
        <ambow:pagination actionName=""
        	                  total="${iravo.page.totalPageCount}"
        	                  totalCount="${iravo.page.totalCount}" 
        	                  num="${iravo.page.currentPageNo}"
        	                  otherParams="" />
        </td>
      </tr>
	  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" height="20px" >
	    <td width="150">题型</td>
	    <td >题干</td>
	    <td width="200">审校状态</td>
	    
	  </tr>
	  <c:forEach items="${iravo.result}" var="item" varStatus="itemStatus">                  
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
	    <td>&nbsp;${item.type.name}(${item.type.code})</td>
	    <td>&nbsp;${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") }</td>
	    <td >&nbsp;<font color="${ "已通过" eq item.reviseStatusName ? "red" : ""}" >${ item.reviseStatusName }
	    	<br>
	    	<c:if test="${ item.replyType==0 }" >
	    		<img src="../images/ask.gif" />
	    	</c:if>
	    	<c:if test="${ item.replyType==1 }" >
	    		<img src="../images/pretty.gif" />
	    	</c:if></font></td>
	  </tr>
	  </c:forEach>
	  
	  <c:if test="${ empty iravo.result }">
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	  	<td width="100%" align="center" colspan="4" height="20px" >没有审校记录...</td>
	  </tr>
	  </c:if>
	  <tr>
        <td align="right" colspan="4" height="20">
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