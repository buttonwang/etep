<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 	<ul> 
      	<li class="content_titer_l">
      		<c:if test="${viewControl.markPolicy eq 1}">
	      		<img id="mark${xStatus.index}" 
	             		<c:if test="${xItem.examProperty.isMark}">
	             			src="../images/query.gif" value="1"              				
	             		</c:if>
	              		<c:if test="${!xItem.examProperty.isMark}">
	              			src="../images/query_c.gif" value="0"
	              		</c:if>
	              		<c:if test="${viewControl.showModel==1}">
	              			onclick="chgmark(this);" 
	              		</c:if>
	               		width="16" height="16" align="absmiddle" style="cursor: hand" alt="标记疑问"	                  		
	            />
            </c:if>
            <c:if test="${viewControl.markPolicy ne 1}">
            	<input type="hidden" id="mark${xStatus.index}" value="0" />
            </c:if>
            
            <c:if test="${xIsSubItem eq 'yes'}">
      			<b>（${xItem.itemNum}）  ${xItem.score}分  难度：${xItem.difficultyValue}</b>
      		</c:if>
      		<c:if test="${xIsSubItem ne 'yes'}">
      			<b>第${xItem.itemNum}题  ${xItem.score}分  难度：${xItem.difficultyValue}</b>
      		</c:if>
      		
      		<c:if test="${viewControl.showModel ne 1 || viewControl.redoType ne 0}">
      		<c:if test="${!viewControl.isExtPractice}">
      			<label>
      			<c:if test="${xItem.examProperty.starInt>0||xItem.examProperty.starHalf>0}">                        	
				<c:forEach   var="i"   begin="1"   end="${xItem.examProperty.starInt}"  step="1">  
 								<img src="../images/star.gif" width="16" height="16" />
				</c:forEach>
				<c:forEach   var="i"   begin="1"   end="${xItem.examProperty.starHalf}" step="1">
			    	<img src="../images/star.gif" width="16" height="16" />
				</c:forEach>
				</c:if>		
      			</label>
      		</c:if>
      		</c:if>
      	</li>
      	
      	<li class="content_titer_c">      		
      		<img src="../images/prompt_ico.gif" onclick="openShutManager(this,'viewHint${xItem.id}')"
      		    style="cursor: hand" alt="提示" class="promptImg" />
      		
      		<c:choose>
	        	<c:when test="${viewControl.analysisPolicy eq 1}">
					<c:set var="showAnalys" value="${1==1}"/>
				</c:when>
				<c:when test="${viewControl.analysisPolicy eq 2}">
					<c:set var="showAnalys" value="${viewControl.showModel!=1}"/>
				</c:when>
				<c:when test="${viewControl.analysisPolicy eq 3}">
					<c:set var="showAnalys" value="${(viewControl.showModel!=1)&&(xItem.examProperty.isRight)}"/>
				</c:when>
				<c:when test="${viewControl.analysisPolicy eq 0}">
					<c:set var="showAnalys" value="${1!=1}"/>
				</c:when>
				<c:otherwise>
					<c:set var="showAnalys" value="${1==1}"/>
				</c:otherwise>
			</c:choose>			
      		<c:if test="${showAnalys}">
	      		<img src="../images/dwell_ico.gif" onclick="openShutManager(this,'viewAnaly${xItem.id}')"
	      			style="cursor: hand" alt="详解" />
      		</c:if>
      		
      		<c:choose>
	        	<c:when test="${viewControl.answerPolicy eq 1}">
					<c:set var="showAnswer" value="${1==1}"/>
				</c:when>
				<c:when test="${viewControl.answerPolicy eq 2}">
					<c:set var="showAnswer" value="${viewControl.showModel!=1}"/>
				</c:when>
				<c:when test="${viewControl.answerPolicy eq 3}">
					<c:set var="showAnswer" value="${(viewControl.showModel!=1)&&(xItem.examProperty.isRight)}"/>
				</c:when>
				<c:when test="${viewControl.answerPolicy eq 0}">
					<c:set var="showAnswer" value="${1!=1}"/>
				</c:when>
				<c:otherwise>
					<c:set var="showAnswer" value="${1==1}"/>
				</c:otherwise>
			</c:choose>			
			<c:if test="${showAnswer}">
	      		<img src="../images/Answer_ico.gif"  onclick="openShutManager(this, 'viewAnswer${xItem.id}')"
	      			style="cursor: hand" alt="答案" />    		
      		</c:if>
      	</li>
  	</ul>	