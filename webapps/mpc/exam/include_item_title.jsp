<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="../js/showBugImg.js"></script>
<script src="../js/showAttentionImg.js"></script>
<c:set var="oneToManyItemTypeCode" value="J4C43,J4C44,J4C45,J4C46,J4M46,J4P43,J4P44,J4P45,J4P47,J4P48,S4C43,S4C44,S4C45,S4C46,S4M46,S4P43,S4P44,S4P45"/>
	<ul> 
      	<li class="content_titer_l">
      		<c:if test="${viewControl.markPolicy eq 1}">
	      		<c:if test="${fn:contains(oneToManyItemTypeCode,xItem.itemType.code)==false}">
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
      		<c:if test="${viewControl.revising ne 1}">
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
      		</c:if>
      	</li>
      <c:if test="${fn:contains(oneToManyItemTypeCode,xItem.itemType.code)==false}">
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
	      			style="cursor: hand" alt="详解" class="analysImg" />
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
	      			style="cursor: hand" alt="答案" class="answerImg" />    		
      		</c:if>
      	</li>
      </c:if>
      	<c:if test="${viewControl.showModel!=1 && viewControl.showModel!=4}">
      	<c:if test="${xIsSubItem ne 'yes'}">
      	<li class="onmouse1 right">
			<span>
			<c:choose>
			<c:when test="${viewControl.showModel ==2}">
				<c:set var="_nodeInstanceId_" value="${xItem.nodeInstanceId}"/>
			</c:when>
			<c:when test="${viewControl.showModel ==3}">
				<c:set var="_nodeInstanceId_" value="${viewControl.currentTestStatusId}"/>
			</c:when>
			</c:choose>
			<a href="../attention/attention!addOrEdit.jhtml?height=350&width=600&p.para.itemId=${xItem.id}&p.para.historyTestStatusId=${viewControl.historyTestStatusId}&p.para.nodeInstanceId=${_nodeInstanceId_}" 
				class="thickbox cBlack" title="收藏它，记下点滴体会！" >
					关注此题<img src="../images/Attention_ico.gif" width="12" height="12" t="hasAttentions" itemId="${xItem.id}" style="display:none"/>
			</a>
      		</span>
      	</li>
      	<li class="onmouse1  right" onmouseout="this.className='onmouse1';" onmouseover="this.className='onmouse2';">
      		<span>
			<c:choose>
			<c:when test="${viewControl.showModel ==2}">
				<c:set var="_nodeInstanceId_" value="${xItem.nodeInstanceId}"/>
			</c:when>
			<c:when test="${viewControl.showModel ==3}">
				<c:set var="_nodeInstanceId_" value="${viewControl.currentTestStatusId}"/>
			</c:when>
			</c:choose>
			<a href="../bug/bug!showItemBugInfoHistoryAnswerStatus.jhtml?height=350&width=600&b.bugInfo.bug.item.id=${xItem.id}&p.para.nodeInstanceId=${_nodeInstanceId_}&p.para.itemId=${xItem.id}&p.para.historyTestStatusId=${viewControl.historyTestStatusId}&p.para.jsp=add_bugInfo" 
				class="thickbox cBlack" title="有疑问？报告给老师！" >
					捉虫<img src="../images/bug.gif" width="12" height="12" t="hasBugs" itemId="${xItem.id}" style="display:none"/>
			</a>
      		</span>
      	</li>
      	</c:if>
      	</c:if>
  	</ul>