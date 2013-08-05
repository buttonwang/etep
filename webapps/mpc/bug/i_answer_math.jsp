<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

 
<c:choose>
	<c:when test="${fn:contains(av,'math>')}">
${
	fn:replace(
		fn:replace(
			fn:replace(
				fn:replace(av
					,'<none/>'
					,'<none></none>'
				)
				,'<mprescripts/>'
				,'<mprescripts></mprescripts>'
			)
			,'<'
			,'<m:'
		)
		,'<m:/'
		,'</m:'
	)
}
	</c:when>
	<c:otherwise>
		<c:if test="${fn:trim(av)==''}">
				<c:choose>
								 <c:when test="${_type=='system'}">没有答案</c:when>
								 <c:otherwise>未做答</c:otherwise>
							 </c:choose>
		</c:if>
		${av}
</c:otherwise>
</c:choose>
 