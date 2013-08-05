<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${obj!=null}"><c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<c:if test="${answerOptionOrder==null||fn:trim(answerOptionOrder)==''}">
	<c:set var="answerOptionOrder" value="A;B;C;D;E;F;G;H;I"/>
</c:if>
<c:set var="answerOptionOrder" value="${fn:split(answerOptionOrder,';')}"/> 
<c:if test="${fn:contains(sigleAndMutilChoose,obj.itemType.code)}">
		<div name="_answer"> 
			<div id="prompt13" class="prompt">
				<span class="left f14px fB">原始选项：</span> 
					 <c:if test="${fn:length(obj.answerOptions)>0}">
						<c:forEach items ="${answerOptionOrder}" var="cName" begin="0" end="${fn:length(obj.answerOptions)-1}" varStatus="cNameStatus">
							  <c:choose>
									<c:when test="${cNameStatus.index==0}"><c:set var="rcName" value="A."/><c:set var="rcName" value="A."/></c:when>
									<c:when test="${cNameStatus.index==1}"><c:set var="rcName" value="B."/></c:when>
									<c:when test="${cNameStatus.index==2}"><c:set var="rcName" value="C."/></c:when>
									<c:when test="${cNameStatus.index==3}"><c:set var="rcName" value="D."/></c:when>
									<c:when test="${cNameStatus.index==4}"><c:set var="rcName" value="E."/></c:when>
									<c:when test="${cNameStatus.index==5}"><c:set var="rcName" value="F."/></c:when>
									<c:when test="${cNameStatus.index==6}"><c:set var="rcName" value="G."/></c:when>
									<c:when test="${cNameStatus.index==7}"><c:set var="rcName" value="H."/></c:when>
									<c:when test="${cNameStatus.index==8}"><c:set var="rcName" value="I."/></c:when>
								</c:choose> 
								<ul>
								 <c:forEach items="${obj.answerOptions}" var="rav" varStatus="ravStatus">
									 <c:if test="${rav.code eq cName}"> <li>${rcName} ${rav.content} </li></c:if>
								</c:forEach>
								</ul>
						</c:forEach> 
					</c:if>
					
					 
					 
<div class="clear"></div>		
					<c:if test="${fn:length(obj.subItems)==0}">
						 <span class="left f14px fB">正确答案：</span>
						 <span class="left"> 
								<c:forEach items ="${_answers}" var="answer" varStatus="avStatus" > <span class="box1">
									<c:set var="av" value="${answer}" scope="request"/>									
									<jsp:include page="i_answer_math.jsp"></jsp:include>
									&nbsp;&nbsp; </span> 
								</c:forEach>
						</span> 
					</c:if>
					 
				<div class="blank3"></div>
				
			</div>
			
		</div>
	 
</c:if>
<c:if test="${fn:contains(sigleAndMutilChoose,obj.itemType.code)==false}">
		<div name="_answer">
			<c:if test="${fn:length(obj.subItems)==0}">
				<div id="prompt13" class="prompt">
					<h5><span class="left f14px fB">正确答案：</span><span class="left"> 
						<c:forEach items ="${_answers}" var="av" varStatus="avStatus" > <span class="box1">
							<c:set var="av" value="${answer}" scope="request"/> 
							<jsp:include page="i_answer_math.jsp"></jsp:include>&nbsp; &nbsp;
							</span> </c:forEach>
						</span> </h5>
					<div class="blank3"></div>
				</div>
			</c:if>
		</div>
	 
</c:if>
</c:if>
