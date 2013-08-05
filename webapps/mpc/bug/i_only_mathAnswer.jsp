<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="_before">&nbsp;&nbsp;|&nbsp;</c:set>
<c:set var="_middle"><span style="font-size: 10px">&nbsp;或&nbsp;</span></c:set>
<c:set var="_end">&nbsp;|&nbsp;&nbsp;</c:set>
<c:set var="_split" value="${_split==null?'；':_split}"/> 
<c:forEach items ="${fn:split(_correctAnswer,_split)}" var="answer" varStatus="answerStatus" > 
	${_before}
	<c:if test="${fn:contains(answer,'$')}">
	<c:forEach items="${fn:split(answer,'$')}" var="av" varStatus="avS">
		<c:set var="_data" value="${av}"/>
		<jsp:include page="i_dealTo_mathAnswer.jsp"/>
		${_tmp}
		<c:remove var="_tmp" scope="request" />
		<c:if test="${avS.count >0 && avS.count != fn:length(fn:split(answer,'$')) }">
		${_middle}
		</c:if>
	</c:forEach>
	</c:if>
	<c:if test="${fn:contains(answer,'$')==false}">
	${answer}
	</c:if>
	${_end}
</c:forEach>