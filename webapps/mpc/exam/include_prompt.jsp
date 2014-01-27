<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

	<c:if test="${viewControl.showModel!=1}">
		<c:if test="${viewControl.scorePolicy eq 1}">
			<p align="left" style="color: red">得分: ${xItem.examProperty.userScore}</p>
		</c:if>
	</c:if>

	<c:if test="${viewControl.showAnswer}">
     			<p>&nbsp;</p>
            	
				<c:if test="${xItemType eq 'choose'}">
					<p align="left" style="color: red">答案:${xItem.examProperty.correctAnswer}</p>								
			    </c:if>
			    
			    <c:if test="${xItemType eq 'truefalse'}">
	            	<c:if test="${xItem.examProperty.correctAnswer eq 'Y'}">
	            		<p align="left" style="color: red">答案:正确</p>
	            	</c:if>
					<c:if test="${xItem.examProperty.correctAnswer eq 'N'}">
	            		<p align="left" style="color: red">答案:错误</p>
	            	</c:if>          	
			    </c:if>
            	
            	<c:if test="${xItemType eq 'fill'}">
	             	<p align="left" style="color: red">答案:
	          		<c:forEach items="${xItem.answersView}" var="correctAnswer" varStatus="status">																  									
	          			<b>${status.count}.</b>
						<span class='formulaSpan'>									
						&nbsp;&nbsp;${fn:replace(correctAnswer.value, '$', ' 或 ')}&nbsp;&nbsp;
						</span>
		    		</c:forEach>
		    		</p>
					<c:if test="${xItem.answerGroup ne null}">
					<c:if test="${xItem.answerGroup ne ''}">
					<p>
						<span class="left f14px fB">答案分组：${xItem.answerGroup}</span>
					</p>
					</c:if>
					</c:if>
    			</c:if>
    			<p align="left" style="color: red">Code:${xItem.code} &nbsp;&nbsp; ${xItem.statusName}</p>
    </c:if>

	<!--提示层开始-->
    <div id="viewHint${xItem.id}" style="display:none;" v="close" class="prompt" >
	<h5>							
		<span class="left f14px fB">提示：</span>
		<span class="right"><img src="../images/close.gif" style="cursor: hand" /></span>
	</h5>
	<div class="blank3"></div>
	<div class="promptContent"><p>${xItem.hint}</p></div>
	<div class="blank3"></div>
    </div>
    <!--提示层结束-->
      
      
    <!--详解层开始-->
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
      <div id="viewAnaly${xItem.id}" style="display:none;" v="close" class="prompt">
		<h5>
		<span class="left f14px fB">详解：</span>
		<span class="right"><img src="../images/close.gif" style="cursor: hand" /></span>
		</h5>
		<div class="blank3"></div>
		<p>${xItem.examProperty.analysisAtLarge}</p>
		<div class="blank3"></div>
      </div>
    </c:if>
    <!--详解层结束-->
      
    <!--答案层开始-->
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
      <c:if test="${showAnalys}">
       <div id="viewAnswer${xItem.id}" style="display:none;" v="close" class="prompt">
		<h5>
			<span class="left f14px fB">答案：</span>
			
			<c:if test="${xItemType eq 'choose'}">
				<span class="left"><u>${xItem.examProperty.correctAnswer}</u></span>          	
		    </c:if>
            
            <c:if test="${xItemType eq 'truefalse'}">
            	<c:if test="${xItem.examProperty.correctAnswer eq 'Y'}">
            		<span class="left"><u>正确</u></span>
            	</c:if>
				<c:if test="${xItem.examProperty.correctAnswer eq 'N'}">
            		<span class="left"><u>错误</u></span>
            	</c:if>          	
		    </c:if>
              	
            <c:if test="${xItemType eq 'fill'}">
               	<p align="left" style="color: red">
               		<c:if test="${viewControl.revising ne 1}">
	           		<c:forEach items="${xItem.examProperty.fillCorrectAnswer}" var="correctAnswer" varStatus="status">				           																			  																										           			
	           			<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>		
						<span class="left">
							<c:if test="${fn:length(xItem.examProperty.fillCorrectAnswer) > 1}">
								<img src="../images/arrow_${status.count}.gif" />
							</c:if>
							<span id="viewAnswer_${xStatus.index}_${status.index}">
							${correctAnswer}
							</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</span>
			    	</c:forEach>
			    	</c:if>
			    	<c:if test="${viewControl.revising eq 1}">
			    	<c:forEach items="${xItem.answersView}" var="correctAnswer" varStatus="status">
			    		<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>		
						<span class="left">																  									
		          			<c:if test="${fn:length(xItem.answersView) > 1}">
								<img src="../images/arrow_${status.count}.gif" />
							</c:if>
							<span id="viewAnswer_${xStatus.index}_${status.index}">
							${fn:replace(correctAnswer.value, '$', '<font color="gray">或 </font>')}
							</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</span>
		    		</c:forEach>
					<c:if test="${xItem.answerGroup ne null}">
					<c:if test="${xItem.answerGroup ne ''}"><br>
					<p>
						<span class="left f14px fB">答案分组：${ xItem.answerGroup }</span>
					</p>
					</c:if>
					</c:if>
		    		</c:if>
			    </p>
		    </c:if>
			
			<span class="right"><img src="../images/close.gif" style="cursor: hand" /></span>
		</h5>
		<div class="blank3"></div>
       </div>
      </c:if>
      <!--答案层结束-->
	<c:if test="${viewControl.revising eq 1}">
		<script language="javaScript" type="text/javascript">
			$(".prompt").show()
		</script>
    </c:if>
