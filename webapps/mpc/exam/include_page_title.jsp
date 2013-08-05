<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="content_top_l">
	<h1 class="til">
		<c:if test="${viewControl.examNodeIns ne null}">
	 		【${viewControl.chapterName}】-【${viewControl.sectionName}】&nbsp;&nbsp;&nbsp;<span>${viewControl.examName}</span>
		</c:if>
		<c:if test="${viewControl.examNodeIns eq null}">
			【${viewControl.flowName}】
		</c:if>
	</h1>
	<h2>
		${viewControl.examItemNum}题/${viewControl.examValue}分，限时${viewControl.examTimeStr2}	，正确率要求：
	 	<c:if test="${(viewControl.examTypePara eq 'practice') || (viewControl.examTypePara eq 'unitExam') }">
	 	${viewControl.requireRightRate}%
	 	</c:if>
	 	<c:if test="${(viewControl.examTypePara ne 'practice') && (viewControl.examTypePara ne 'unitExam') }">
	 	--
	 	</c:if>
	</h2>
</div>
          
<c:if test="${viewControl.showModel!=1}">
<div class="content_top_r">
	得分：<span>${viewControl.examResultProperty.examScore}</span>，
	正确率：<span>${viewControl.examResultProperty.accuracyRateInt}%	</span>	
</div>
</c:if>
 	
<hr/>