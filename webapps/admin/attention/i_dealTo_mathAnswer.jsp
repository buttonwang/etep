<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--
	输入为 _data 
	输出为 _tmp
	逻辑如果是公式则进行替换
	注意事项：在多次使用时，可能需要主动清理request中的_tmp变量
	使用举例：
	<c:set var="_data" value="${item.correctAnswer}"/>
	公式处理<jsp:include page="i_dealTo_mathAnswer.jsp"/>
	使用公式：${_tmp}
	<c:remove var="_tmp" scope="request" /> 
--%>
<c:set var="_tmp" value="${_data}" scope="request"/>
<c:if test="${fn:contains(_tmp,'math>')}">
<c:set var="_tmp" value="${fn:replace(_tmp,'<none/>','<none></none>')}" scope="request"/>
<c:set var="_tmp" value="${fn:replace(_tmp,'<mprescripts/>','<mprescripts></mprescripts>')}" scope="request"/>
<c:set var="_tmp" value="${fn:replace(_tmp,'<','<m:')}" scope="request"/>
<c:set var="_tmp" value="${fn:replace(_tmp,'<m:/','</m:')}" scope="request"/>
</c:if>