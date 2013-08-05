<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<div name="item_bugInfos">
											<c:forEach items="${bugInfoHistoryAnswerStatusList}" var="bugInfoHistoryAnswerStatus">
												<div name=item_perBugInfo>
													<div class="blank6"></div>
													<div class="box1 padding10px">
														<div name="submit">
															${bugInfoHistoryAnswerStatus.bugInfo.bug.item.id}&nbsp;&nbsp;&nbsp;&nbsp;<h6 style="width:500px"> ${bugInfoHistoryAnswerStatus.bugInfo.submitInfo}&nbsp;&nbsp;&nbsp;<span style="text-align:right; width:200px" > ${bugInfoHistoryAnswerStatus.bugInfo.submitTime}</span></h6>
														</div>
														<div name="answer"> </div>
														<div name="reply">
															<div class="blank3"></div>
															<c:if test="${bugInfoHistoryAnswerStatus.bugInfo.replyInfo==null}">
																<h6>正在处理中...... 请耐心等待</h6>
															</c:if>
															<c:if test="${bugInfoHistoryAnswerStatus.bugInfo.replyInfo!=null}">
																<h6>管理员回复： <font color="red">${bugInfoHistoryAnswerStatus.bugInfo.replyInfo} </font><span style="text-align:right; width:200px" > ${bugInfoHistoryAnswerStatus.bugInfo.replyTime}</span></h6>
															</c:if>
														</div>
													</div>
												</div>
											</c:forEach>
										</div>