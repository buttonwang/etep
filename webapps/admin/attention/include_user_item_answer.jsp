<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div  name="positionInfo" t=checkedPositionInfo positionInfo="${bihas.positionInfo}">
							<ul>
								<c:choose>
									<c:when test="${fn:length(itemAnswerVO.subItemAnswerVOLst)>0}">
										<h6>
											<c:forEach items="${itemAnswerVO.subItemAnswerVOLst}" var="svo" varStatus="svoStatus"> 子题（${svoStatus.index+1}）：
												<c:set var="answerViews" value="${svo.answerViews}"/>
												<c:if test="${fn:length(answerViews)==0}"> 没有做答<br/>
												</c:if>
												<c:if test="${fn:length(answerViews)>0}">
													<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
														<input type="checkbox" name="tt" disabled="disabled" />
														<c:if test="${fn:trim(av)!=''}">
															${av}
															<c:if test="${fn:contains(av,'math>')}">
																	<c:set var="gs_id" value="gs_${itemAnswerVO.item.id}_${svo.subItem.id}_${avStatus.index}" />
																	<span>
																		<span id="${gs_id}" t="showId" style=" display:none;position:absolute;background-color:#CCC">																		
																			<table >
																			<tr>
																			<td>用户答案：</td>
																			<td><textarea rows="5" cols="60">${fn:replace(av," ","")}</textarea></td>
																			</tr>
																			<tr>
																			<td>标准答案：</td> 
																			<td><c:forEach items="${svo.subItem.answers}" var="a" varStatus="aS">
																						<c:if test="${aS.index==avStatus.index}">
																				 <textarea id="t_${gs_id}" rows="5" cols="60" ></textarea>
																				 <script>
																				 	document.getElementById('t_${gs_id}').value= "${a.value}".replace(/\s/gi,'').replace(/\\$/g,'\r\r');
																				 </script>
																						</c:if>
																					</c:forEach></td>
																			</tr>
																			<%--<tr><td colspan="2">
																			<c:choose>
																				<c:when test="${fn:length(fn:trim(svo.subItem.answerGroup))>0}">
																					<input type="button" class="btn_2k3" onClick="window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" value='转至试题编辑' />&nbsp;&nbsp;
																				</c:when>
																				<c:otherwise> 
																					<input type="button" class="btn_2k3" onClick="$('#f_${gs_id}').trigger('submit');window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" value='增加答案并转到编辑' />&nbsp;&nbsp;
																					<input type="submit" class="btn_2k3" onClick="$('#f_${gs_id}').trigger('submit');" value="增加答案"/>
																				</c:otherwise>
																			</c:choose>
																			</td></tr>--%>
																			</table>
																			<%--<form id="f_${gs_id}" action="../../admin/itembank/item!addAnswer.jhtml" method="post" onSubmit="alert (23);if(confirm('确认增加？')){return true;}return false;" >
																				<input name="p.para.itemId" value="${itemAnswerVO.item.id}" style="display:none"/>
																				<input name="p.para.subItemId" value="${svo.subItem.id}" style="display:none"/>
																				<input name="p.para.addAnswerIndex" value="${avStatus.index}" style="display:none"/>
																				<textarea name="p.para.addAnswerContent" style="display:none">${av}</textarea>
																			</form>--%>
																		<span>
																		
																		</span>
																	</span>
																	<%--<span t="show" showId="${gs_id}" ><font color="red" style="cursor:pointer"><b>对比</b></font></span>--%>
																	</span>
															</c:if>
														</c:if>
														<c:if test="${fn:trim(av)==''}">未作答</c:if>
														&nbsp;&nbsp; </c:forEach>
												</c:if>
												<br />
											</c:forEach>
										</h6>
									</c:when>
									<c:otherwise>
										<c:set var="realAnswerOptions" value="${itemAnswerVO.item.answerOptions}"/>
										<c:set var="answerOptionOrder" value="${bihas.historyAnswerStatus.answerOptionOrder}" />										
										<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)}">
											<c:set var="obj" value="${itemAnswerVO.item}" scope="request"/> 
											<c:set var="_answers" value="${fn:split(itemAnswerVO.answerStr,'@:@')}" scope="request"/> 
											<c:set var="answerOptionOrder" value="${bihas.historyAnswerStatus.answerOptionOrder}" scope="request"/> 
											<c:set var="xiangJieId" value="xianJie_${itemAnswerVO.item.id}_${bStatus.index}_${user.id}" scope="request"/>
											<c:set var="answerId"  value="answer_${itemAnswerVO.item.id}_${bStatus.index}_${user.id}" scope="request"/>
											<c:set var="iVO" value="${bihas.itemAnswerVO}" scope="request"/>
											<jsp:include page="answer_prototype_user.jsp"/>
										</c:if>
										<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
										<c:set var="answerViews" value="${itemAnswerVO.answerViews}"/>
										<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
											<input type="checkbox" name="tt" disabled="disabled"/>
											<c:if test="${fn:trim(av)!=''}">
												<c:choose>
													<c:when test="${fn:contains(av,'math>')}"> 
														${av}
														<c:set var="gs_id" value="gs_${itemAnswerVO.item.id}_${svo.subItem.id}_${avStatus.index}" />
														<span>
															<span id="${gs_id}" t="showId" style=" display:none;position:absolute;background-color:#CCC">
																<table >
																<tr>
																<td>用户答案：</td>
																<td><textarea rows="5" cols="60" >${av}</textarea></td>
																</tr>
																<tr>
																<td>标准答案：</td> 
																<td><c:forEach items="${item.answers}" var="a" varStatus="aS">
																			<c:if test="${aS.index==avStatus.index}">
																	 <textarea id="t_${gs_id}" rows="5" cols="60" ></textarea>
																	 <script>
																		document.getElementById('t_${gs_id}').value= "${a.value}".replace(/\s/gi,'').replace(/\\$/g,'\r\r');
																	 </script>
																			</c:if>
																		</c:forEach></td>
																</tr>
																<%--<tr><td colspan="2">
																<c:choose>
																	<c:when test="${fn:length(fn:trim(itemAnswerVO.item.answerGroup))>0}">
																		<input type="button" class="btn_2k3" onClick="window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" value='转至试题编辑' />&nbsp;&nbsp;
																	</c:when>
																	<c:otherwise>
																		<input type="button" class="btn_2k3" onClick="$('#f_${gs_id}').trigger('submit');window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" value='增加答案并转到编辑' />&nbsp;&nbsp;
																		<input type="submit" class="btn_2k3" onClick="$('#f_${gs_id}').trigger('submit');" value="增加答案"/>																			
																	</c:otherwise>
																</c:choose>
																</td></tr>--%>
																</table>
																<%--<form id="f_${gs_id}" action="../../admin/itembank/item!addAnswer.jhtml" method="post" onSubmit="alert (23);if(confirm('确认增加？')){return true;}return false;" >
																	<input name="p.para.itemId" value="${itemAnswerVO.item.id}" style="display:none"/>
																	<input name="p.para.subItemId" value="${svo.subItem.id}" style="display:none"/>
																	<input name="p.para.addAnswerIndex" value="${avStatus.index}" style="display:none"/>
																	<textarea name="p.para.addAnswerContent" style="display:none">${av}</textarea>
																</form>--%>
															<span>
															
															</span>
														</span>
														<%--<span t="show" showId="${gs_id}" ><font color="red" style="cursor:pointer"><b>对比</b></font></span>--%>
														</span>
													</c:when>
													<c:otherwise>${av}</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${fn:trim(av)==''}">未作答</c:if>
											&nbsp;&nbsp; </c:forEach>
										</c:if>
									</c:otherwise>
								</c:choose>
							</ul>
							<div class="clear"></div>
						</div>