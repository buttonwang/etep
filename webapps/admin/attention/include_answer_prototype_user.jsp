<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${obj!=null}"><c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<c:if test="${answerOptionOrder==null||answerOptionOrder==''}">
	<c:set var="answerOptionOrder" value="ABCDEFGHI"/>
</c:if>
<%--
默认顺序 ：${fn:substring("ABCDEFGHI",0,fn:length(iVO.answerOptions))}<br />
重排顺序 ：${fn:substring(answerOptionOrder,0,fn:length(iVO.answerOptions))}<br />
当时回答 ：${iVO.answerStr}<br />
原始正确答案 ：${iVO.item.correctAnswer}<br />
排序后正确答案：${iVO.correctAnswer} <br />
--%>
<c:if test="${answerOptionOrder!=''&&fn:contains(answerOptionOrder,';')==false}">
	<c:set var="_aoo" value="" scope="request" />
	<c:forEach items ="${obj.answerOptions}" var="v" varStatus="vstatus">
		<c:if  test ="${vstatus.index > 0}" >
			<c:set var="_aoo" value="${_aoo};" scope="request" />
		</c:if>
		<c:set var="_aoo" value="${_aoo}${fn:substring(answerOptionOrder,vstatus.index,vstatus.index+1)}" scope="request" />
	</c:forEach>
	<c:set var="answerOptionOrder" value="${_aoo}"/>
	<c:remove var="_aoo" scope="request" />
</c:if>
<script>
function show(cid){
	try{$("#"+cid).toggle();}catch(e){} 
}
function hide(cid){
	try{getE(cid).style.display= "none";}catch(e){} 
}
function getE(cid){
	var elem;
	if(cid!=undefined&&cid!=null){
		elem =  document.getElementById(cid);
	}
	if(elem==undefined){
		elem = null;
	}
	return elem;
}
</script>
<c:set var="answerOptionOrder" value="${fn:split(answerOptionOrder,';')}" scope="request"/> 
<c:if test="${fn:contains(sigleAndMutilChoose,obj.itemType.code)}">
		<div name="_answer"> 
			<div id="prompt13" class="prompt">
				<span class="left f14px fB">当时选项：</span> 
					<c:if test="${fn:length(obj.answerOptions)>0}">
						<table width="100%"><c:forEach items ="${iVO.answerOptions}" var="ao" begin="0" end="${fn:length(obj.answerOptions)-1}" varStatus="cNameStatus">
							  <tr><td width="10%"></td><td> <c:choose>
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
								 <li>${rcName} ${ao.content} </li>								 
								</ul>
							</td></tr>
						</c:forEach> 
						</table>
					</c:if>
					<div class="clear"></div>		
					<c:if test="${fn:length(obj.subItems)==0}">
						 <span class="left f14px fB">当时回答：</span>
						 <span class="left"> 
								${iVO.answerStr}
						</span>
						<%--
						<div>
							<h5><span class="left f14px fB">
							<img src="../images/dwell_ico.gif" onclick="show('${xiangJieId}')" style="cursor:pointer" alt="详解"/>
							<img src="../images/Answer_ico.gif"  onclick="show('${answerId}')"   style="cursor:pointer" alt="答案"/>
							</span></h5>
							<div class="blank3"></div>
						</div>
						<div class="clear"></div>		
						<div id="${xiangJieId}" style="display:none;" >
										<h5><span class="left f14px fB">详解：</span><span class="right"><img src="../images/close.gif" onclick="hide('${xiangJieId}')" alt="${xiangJieId}"/></span></h5>
										<p>${iVO.analysisAtLarge1}</p>
						</div>
						<div class="clear"></div>		
						<div id="${answerId}" style="display:none;">
										<h5><span class="left f14px fB">正确答案：</span><span class="right"><img src="../images/close.gif" onclick="hide('${answerId}')" alt="${answerId}"/></span></h5>
										<p>
										${iVO.correctAnswer}
										</p>
						</div>
						--%>
					</c:if>
					 
				<div class="blank3"></div>
				
			</div>
			
		</div>
	 
</c:if>
<c:if test="${fn:contains(sigleAndMutilChoose,obj.itemType.code)==false}">
		<div name="_answer">
			<c:if test="${fn:length(obj.subItems)==0}">
				<div id="prompt13" class="prompt">
					<h5><span class="left f14px fB">当时回答：</span><span class="left"> 
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
