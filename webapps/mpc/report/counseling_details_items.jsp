<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

		<c:forEach items="${pages}" var="page" varStatus="pageStauts">
		<c:forEach items="${page.items}" var="item" varStatus="itemStauts">
		<tr>
			<c:set var="itemId" value="${item.id}" scope="request"/>
            <td width="3%">
            	<input id="checkItemId" type="checkbox" class="checkItem" value="${item.id}" name="checkItemId" />
            </td>
            <td width="76%">
            	<div class="dircts">
             	<p>${item.itemNum}.&nbsp;&nbsp;&nbsp; ${item.content}</p>
             	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1')}">
		        		<c:forEach items="${item.answerOptions}" var="answerOption" varStatus="answerStatus">
		        			<c:if test="${answerStatus.index==0}"><c:set var="answerCode" value="A．"></c:set></c:if>
							<c:if test="${answerStatus.index==1}"><c:set var="answerCode" value="B．"></c:set></c:if>
							<c:if test="${answerStatus.index==2}"><c:set var="answerCode" value="C．"></c:set></c:if>
							<c:if test="${answerStatus.index==3}"><c:set var="answerCode" value="D．"></c:set></c:if>			        		         
							<p>${answerCode} ${answerOption.content}</p>	 	
	                	</c:forEach>
	        	</c:if>
		        <c:if test="${page.typeAlias ne 'MPC4X'}">
					<!--详解层开始-->
			        <div id="analy${item.id}">
			        	<p>详解：${item.examProperty.analysisAtLarge}</p>
			        </div>
	        		<!--详解层结束-->
	        		<br></br>
			        <!--答案层开始-->
			        <div id="answer${item.id}">
				         <span class="left">
				         	答案：
				        	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1')}">
								<span class="left">${item.examProperty.correctAnswer}</span>          	
							</c:if>
				                	
		                	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1') eq false}">
		                		<span class="left">${item.answerPrototype}</span>
			                	<!--<p align="left">
					           		<c:forEach items="${item.answersView}" var="correctAnswer" varStatus="status">				           																			  																										           			
					           			<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>		
										<span class="left">								
											<b>${status.count}.</b>
											<span id="viewAnswer_${xStatus.index}_${status.index}">
												${fn:replace(correctAnswer.value, '$', ' 或 ')}
											</span>
											&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
							    	</c:forEach>
							    </p>
						    	-->
						    </c:if>
				        </span>
			        </div>
			        <!--答案层结束-->
		        </c:if>
		        
		        <c:if test="${page.typeAlias eq 'MPC4X'}">
		        	<c:forEach items="${item.subItems}" var="subItem" varStatus="subStatus">
		        		<p><b>子题${subStatus.index+1}.</b> ${subItem.content}</p>
        				
        				 <!--详解层开始-->
				        <div id="analy${subItem.id}">
				        	<p>详解：${subItem.examProperty.analysisAtLarge}</p>
				        </div>
		        		<!--详解层结束-->
		        		<br></br>
		        		<!--答案层开始-->
				        <div id="answer${subItem.id}">
					        <span class="left">
					        	答案：<span class="left">${subItem.answerPrototype}</span>
			                	<!--<p align="left">
					           		<c:forEach items="${subItem.answersView}" var="correctAnswer" varStatus="status">				           																			  																										           			
					           			<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>		
										<span class="left">								
											<b>${status.count}.</b>
											<span id="viewAnswer_${xStatus.index}_${status.index}">
												${fn:replace(correctAnswer.value, '$', ' 或 ')}
											</span>
											&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
							    	</c:forEach>
							    </p>-->
					        </span>
				        </div>
				        <!--答案层结束-->
		        	</c:forEach>
		        </c:if>
            	</div>
            <td>${page.title}</td>
            <%
				Map<Integer, String> actorItemMap = (Map<Integer, String>)request.getAttribute("actorItemMap");
				Integer itemId = (Integer)request.getAttribute("itemId");
				String actores =  actorItemMap.get(itemId);
				Integer actorNum = actores.split(",").length;
			%>
            <td>
            	<%=actorNum%>人
            	<span class="con_l">
            		<a href="#" onmouseover="MM_showHideLayers('actor${item.id}','','show')" 
            			onmouseout="MM_showHideLayers('actor${item.id}', '','hide')">查看全部</a>
            	</span>
		        <div class="help" id="actor${item.id}" 
		        	onmouseover="MM_showHideLayers('actor${item.id}', '', 'show')" 
		        	onmouseout ="MM_showHideLayers('actor${item.id}', '', 'hide')">
	                <div class="help_c">
						<table width="400">
						<tr>
							<td style="table-layout:fixed; word-break : break-all; border:0;">	
							<%=actores%>
							</td>
						</tr>
						</table>
	                </div>
                </div>
             </td>
          </tr>
          </c:forEach>
          </c:forEach>