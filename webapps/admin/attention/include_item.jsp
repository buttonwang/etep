<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
								<div class="content_box920">
									<div class="content_titer920">
										<table width="100%">
											<tr><td width="40%">
												<b>题型：${item.itemType.name}；分值：${fn:replace(item.score,".0","")}分；难度：${fn:replace(item.difficultyValue,".0","")}&nbsp;</b>
											</td>
											<td width="20%">
											<c:set var="xiangJieId" value="xiangJie_${item.id}_${bStatus.index}" scope="request"/>
											<c:set var="answerId"  value="answer_${item.id}_${bStatus.index}" scope="request"/>
											<c:if test="${fn:contains(sigleAndMutilChoose,item.itemType.code)==false&&fn:contains(item.itemType.name,'一对多')==false}">
											<img alt="详解" src="../../mpc/images/dwell_ico.gif" onClick="show('${xiangJieId}')" style="cursor:pointer"/>
											<img alt="答案" src="../../mpc/images/Answer_ico.gif"  onclick="show('${answerId}')" style="cursor:pointer"/>
											</c:if>
											</td>
											<td width="30%"> 所属知识点：
											<c:forEach items="${item.knowledgePoints}" var="kp" >
												${kp.name}
											</c:forEach>
											</td>
										</tr>
										</table>
									</div>
									<div class="clear"></div>
									<div class="answers920">
										<div name=item_content >
											<p align="left">${item.content}</p>
											<p>&nbsp;</p>
											<div class="clear"></div>
											<div name="item_sourceChoose">
											<c:if test="${fn:contains(sigleAndMutilChoose,item.itemType.code)}">
											<div id="prompt13"    class="prompt">
												<h5>
												<span class="left f14px fB">原始选项：</span>
												<span class="left">
												<table width="100%">
													<c:forEach items="${item.answerOptions}" var="rav" varStatus="ravStatus">
															<tr><td width="10%"></td><td>${rav.code}. ${rav.content} &nbsp;&nbsp;</td></tr>
													</c:forEach>
												</table>
												</span> 
												</h5>
												<div class="blank3"></div>
											</div>	
											</c:if>
											</div>
											<div name="item_answer" id="${answerId}">
											<c:if test="${fn:length(item.subItems)==0}">
											<div id="prompt12" v="close"class="prompt">
													<h5><span class="left f14px fB">正确答案：</span><span class="right"><img src="images/close.gif" style="cursor:pointer" onClick="hide('${answerId}')"/></span></h5>
												<c:forEach items ="${item.answers}" var="av" varStatus="avStatus" >
																${fn:replace(av.value,'$','<font color="red">或</font>')}&nbsp;&nbsp;&nbsp;
													</c:forEach><p></p>
											</div>
											</c:if>
											</div>
											<div name="item_xiangjie" id="${xiangJieId}" style="display:none;">
												<div id="prompt12" v="close"class="prompt">
													<h5><span class="left f14px fB">详解：</span><span class="right"><img src="images/close.gif" style="cursor:pointer" onClick="hide('${xiangJieId}')"/></span></h5>
													<p>${item.analysisAtLarge1}</p>
												</div>
											</div>
											<%--答案层开始--><!--答案层结束--%>
										</div>
										<%--子题题目显示层开始--%>
										
										<c:if test="${fn:length(item.subItems)>0}">
											<c:forEach items="${item.subItems}" var="subItem" varStatus="svoStatus"> 
												<div name="oneSubItem">
													<div class="content_titer920">
														<ul>
															<li class="content_titer920_l"> <b>子题（${svoStatus.index+1}）  &nbsp;分值：${fn:replace(subItem.score,".0","")}&nbsp;分 ； 难度： ${fn:replace(subItem.difficultyValue,".0","")}&nbsp;</b>
															</li>
															<c:set var="xiangJieId" value="xianJie_${item.id}_${subItem.id}_${bStatus.index}" scope="request"/>
															<c:set var="answerId"  value="answer_${item.id}_${subItem.id}_${bStatus.index}" scope="request"/>
															<li class="content_titer920_c">
															<img alt="详解" src="../../mpc/images/dwell_ico.gif" onClick="show('${xiangJieId}')" style="cursor:pointer"/>
															<img alt="答案" src="../../mpc/images/Answer_ico.gif" onClick="show('${answerId}')" style="cursor:pointer"/></li>
														</ul>
													</div>
													<p align="left">${subItem.content}</p>
													<p>&nbsp;</p>
													<div class="clear"></div>	
													<div name="subItem_answer">
													<c:set var="_answer" value="${fn:split(subItem.correctAnswer,'；')}"/>
													<c:if test="${fn:length(_answer)>0}">
													<div id="${answerId}" >
													<div id="prompt12" v="close"class="prompt">
														<h5><span class="left f14px fB">正确答案：</span><span class="right"><img src="images/close.gif" style="cursor:pointer" onClick="hide('${answerId}')" alt="${answerId}"/></span></h5>
														<%--<div class="blank3"></div>--%>
														<p><c:set var="_answer" value="${fn:split(subItem.correctAnswer,'；')}"/>
															<c:forEach items ="${_answer}" var="__av" varStatus="avStatus" > 														 
																	<c:set var="_data" value="${__av}" scope="request"/>
																	<jsp:include page="i_dealTo_mathAnswer.jsp"/>${_tmp} 
																	<c:remove var="_tmp" scope="request" />
																	<c:remove var="_data" scope="request" />&nbsp;
																</span>
															</c:forEach>
														</p>
													</div>
													</div>
													<div name="item_xiangjie" id="${xiangJieId}" style="display:none">
													<div id="prompt12" v="close"class="prompt">
														<h5><span class="left f14px fB">详解：</span><span class="right"><img src="images/close.gif" style="cursor:pointer" onClick="hide('${xiangJieId}')" alt="${xiangJieId}"/></span></h5>
														<%--<div class="blank3"></div>--%>
														<p>${subItem.analysisAtLarge1}</p>
													</div>
													</div>
												</c:if>
											</div>
										</div>										 
										</c:forEach>
										</c:if>
										<%--子题题目显示结束--%>
										</div>
										<%--捉虫信息显示层结束--%>
									</div>
								</div>
							</div>