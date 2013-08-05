<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<!--<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
	<link href="../css/thickbox.css" rel="stylesheet" type="text/css" />-->
	<script language="javascript" src="../js/jquery-1.3.2.min.js"></script>
	<script language="javascript" src="../js/tandiv.js"></script>
	<script language="javascript" src="../js/exam.js"></script>
	<script language="javascript" src="../js/thickbox.js"></script>
</head>
<body>
		<input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
        <c:forEach items="${currentPage.items}" var="item" varStatus="status">
        	<c:set var="xItem" value="${item}" scope="request"/>
        	<c:set var="xStatus" value="${status}" scope="request"/>
        	<c:set var="xItemType" value="choose" scope="request"/>
        	<input type="hidden" name="mapkey${status.index}" id="mapkey${status.index}" value="${item.id}"/>
        	<div class="content_box">
        		<div class="content_titer">
		        	<jsp:include page="include_item_title.jsp"></jsp:include>
		        </div>
		        <div class="clear"></div>
		        <div class="answers">
		        	<p align="left">${item.content}
		        		<c:if test="${viewControl.showModel!=1}">
							<c:if test="${item.examProperty.isRight}">
								<img src="../images/true.gif" width="16" height="16"/>
							</c:if>
							<c:if test="${!item.examProperty.isRight}">
								<img src="../images/false.gif" width="16" height="16"/>
							</c:if>
						</c:if>
		        	</p>
		        	<div class="blank6"></div> 
		        	<ul>
		        		<c:forEach items="${item.answerOptions}" var="answerOption" varStatus="answerStatus">
		        			<c:if test="${answerStatus.index==0}"><c:set var="answerCode" value="A．"></c:set></c:if>
							<c:if test="${answerStatus.index==1}"><c:set var="answerCode" value="B．"></c:set></c:if>
							<c:if test="${answerStatus.index==2}"><c:set var="answerCode" value="C．"></c:set></c:if>
							<c:if test="${answerStatus.index==3}"><c:set var="answerCode" value="D．"></c:set></c:if>
			        		<li>
								<input type="checkbox" name="userAnswer${status.index}" id="userAnswer${status.index}" value="${answerOption.code}"
									<c:if test="${fn:contains(item.examProperty.userAnswer, answerOption.code)}">checked</c:if>
							 	/>${answerCode} ${answerOption.content}			 								 		                           
							</li>
                        </c:forEach>
		        	</ul>
		        	<div class="clear"></div>
		        	<jsp:include page="include_prompt.jsp"></jsp:include>
		        </div>
		        <jsp:include page="include_widget.jsp"></jsp:include>
			</div> 
        	<div class="blank9"></div>
		</c:forEach>
		
		<script language="javaScript" type="text/javascript">
			changeWidth("${width}");
		</script>
</body>
</html>