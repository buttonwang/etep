<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${viewControl.flowName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<script language="JavaScript" src="../js/jquery-pack.js"></script>
<SCRIPT language="javascript" src="../js/floating.js" type="text/javascript"></SCRIPT>
<SCRIPT language="javascript" src="../js/exam.js" type="text/javascript"></SCRIPT>
<link href="../css/addWord.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/addWord.js"></script>

<script type='text/javascript' src="../../dwr/engine.js"></script>
<script type='text/javascript' src="../../dwr/interface/freshWordService.js"></script>
<script language="JavaScript" type="text/javascript">
	var processInstanceId="${viewControl.processInstance.id}";
</script>
</head>
<body onload="JavaScript:FloatingDIV();initSelect();">

<!--页面头部-->
<jsp:include page="include_head.jsp"></jsp:include>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2" >
	<div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">

<jsp:include page="include_div.jsp"></jsp:include>


          <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm"><img src="../images/info.gif" align="absbottom" />
                      ${currentPage.instruction}</td>
                </tr>
                </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top"><table width="100%" cellpadding="3" cellspacing="0"  class="essay">
                          <tr>
                            <td width="100%"><p>按句子结构&amp;关键词给分。<!--（共**题，**分）--></p>                            
                            </td>
                          </tr>
                        </table>
						<div id="content">
                          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">

                            <c:forEach items="${currentPage.items}" var="item" varStatus="status">
							<tr class="question_info">
                              <td width="5%" class="f16px fB">
                             
                              <img 
                				<c:if test="${currentPage.isMark[status.index]==1}" var="mark">
                					src="../images/icon_question.gif"
                					value="1"
                				</c:if>
                 				<c:if test="${mark==false }">
                 					src="../images/icon_noquestion.gif"
                					value="0"
                 				</c:if>
                  			border="0" align="absmiddle" style="cursor: hand" onclick="chgmark(this);"
                  			id="mark${status.index}"/>
                            </td>
                              <input type="hidden" name="mapkey${status.index }" id="mapkey${status.index }" value="${item.id} "/>
                            	<input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
                            
                              <td width="3%" class="f16px fB">${item.itemNum}</td>
                              <td width="70%">${item.content}
                              <c:if test="${viewControl.showAnswer}">
                              	<span lang=EN-US style='font-size:13.0pt;color:red'>standAnswer: ${item.correctAnswer}</span><br/>                              
							  	<span lang=EN-US style='font-size:13.0pt;color:green'>KeyWord: ${item.scoringKeywords}</span>
							  </c:if>	
                              </td>
                              <td width="17%">
                              <c:if test="${currentPage.starInt[status.index]>0||currentPage.starHalf[status.index]>0}">
                                      	（
                              			<span class="cRed">
										<c:forEach   var="i"   begin="1"   end="${currentPage.starInt[status.index]}"   step="1">  
          								    ★
  										</c:forEach>
  										<c:forEach   var="i"   begin="1"   end="${currentPage.starHalf[status.index]}"   step="1">
  										    ☆
  										</c:forEach>
  										</span>  
  										）
  										</c:if>
  										</td>
                              <td width="5%">
								<c:if test="${viewControl.analysisPolicy==1||viewControl.showModel!=1&&viewControl.analysisPolicy==2&&currentPage.isDone[status.index]==1||viewControl.showModel!=1&&viewControl.analysisPolicy==3&&currentPage.isRight[status.index]==1}">
									<a href="javascript:void(null)" onclick="openShutManager(this,'fanwen${status.index}')"><img src="../images/icon_jiexi.gif" align="absmiddle" border="0" alt="查看解析" /></a>
								</c:if>
								</td>
                            
                            </tr>
                            
                            <tr>
                              <td colspan="2">&nbsp;</td>
                              <td colspan="3">
                               <div id="fanwen${status.index}" style="display:none;" class="fanwen1"><div style="background-color:#f5f5f5;font-size:14px;height:25px; padding:0 10px;"> <strong> 范文： </strong></div>
                                <div style="border:1px solid #cccccc;padding:10px;font-size:14px;"> ${item.correctAnswer } </div>
                          	   </div>
							<c:if test="${viewControl.showModel==1}">
                              <p>
                                <textarea name="userAnswer${status.index}" id="userAnswer${status.index}" cols="65" rows="5" class="bont_text">${currentPage.userAnswer[status.index]}</textarea>
                              </p>
							  </c:if>
							  <c:if test="${viewControl.showModel!=1}">
								<div>
                                    <div style="background-color:#f5f5f5;font-size:14px;height:25px; padding:0 10px;"> <strong>我的译文： </strong>得分（${scoreList[status.index]}分）</div>
                                    <div style="border:1px solid #cccccc;padding:10px;font-size:14px;">${currentPage.userAnswer[status.index]}</div>
                                </div>
							</c:if>
							</td>
                            </tr>
						  </c:forEach>
                      </table>
					</div>
					</td>
                    </tr>
                  </table></td>
              <td width="114" align="right" valign="top"><!--<div class="xScrollStick d1">
	<a href="http://www.163.com">俺是浮动条</a>
</div>-->
              <c:if test="${viewControl.showModel==1}">
					<jsp:include page="include_left.jsp"></jsp:include>
                </c:if>
                <c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
					<jsp:include page="include_right.jsp"></jsp:include>
                </c:if>
                </td>
            </tr>
          </table>
        </div>
        </div>
    </div>
</div>

<jsp:include page="include_bottom.jsp"></jsp:include>
</body>
</html>
