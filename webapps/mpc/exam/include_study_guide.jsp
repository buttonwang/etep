<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${studyGuide ne ''}">
	<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
		onclick="window.open('../studyguide/itemStudyGuide!getInfos.jhtml?ids=${studyGuide}')">
		查看学习指导	</button></span>
</c:if>