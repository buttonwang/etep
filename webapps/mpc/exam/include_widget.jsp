<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--插件模式-->
  <div class="blank6"></div>
  <p>
	<h1>
		<b>所属知识点：</b>【${viewControl.chapterName}】-【${viewControl.sectionName}】 
		<b>题型：</b>
		<c:if test="${xIsSubItem eq 'yes'}">
     		${xItem.item.itemType.name}
     	</c:if>
     	<c:if test="${xIsSubItem ne 'yes'}">
     		${xItem.itemType.name}
     	</c:if>
	</h1>
	<c:if test="${viewControl.revising eq 1}">
	<h1>
     			<b>编码：</b>${xItem.code}&nbsp;&nbsp;&nbsp;
     			<b>试题状态：</b>${xItem.statusName}&nbsp;&nbsp;&nbsp;
     			<b>审校状态：</b>
     				<font color="red">
     				<span id='reviseStatusSpan'>
     					<c:if test="${ 'reply' eq pageInclude }">
     						${ xItem.reviseStatusName }
     					</c:if>
     					<c:if test="${ !('reply' eq pageInclude) }">
     						${ viewControl.itemRevise.reviseStatusName}
     					</c:if>
     					</span>
     				</font>
    </h1>		
    </c:if>
  </p>
<!--插件模式-->