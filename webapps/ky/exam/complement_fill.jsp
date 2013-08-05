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
	<div class="nTab wm2">
<!-- 内容开始 -->
    <div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">
        
       <jsp:include page="include_div.jsp"></jsp:include>
       
          <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
              <td valign="top">
			  	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm"><img src="../images/info.gif" align="absbottom" /><strong>Section Ⅰ   Use of English </strong>
                    <p>${currentPage.instruction} <!-- （10 points）--></p></td>
                  </tr>
                  </table>
                  
				  <div id="content">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  	<c:set var="lengthCount" value="0"></c:set>
                    <c:forEach items="${currentPage.items}" var="item" varStatus="status">
					<tr>
                      <td valign="top">
                      	<table width="100%" cellpadding="3" cellspacing="0"  class="essay">
                    	<tr>
                           <td width="100%">
                           	${item.contentView}
                           	<c:if test="${viewControl.showModel!=1}">                        		                        	                        
	                           	<div align="right" style="margin:3px;display:none;" id="ywdiv" >
	                           		<span align="right" style="background-color:#f1f5fa;padding:6px;font-weight:bold;
	                           		border:1px solid #bfdeed;color:#2ab9e3;cursor:hand;" onclick="doit(yw000)">查看文章译文</span>                           		
	                            </div>
								<div class="d1" style="padding:10px;display:none;" id="yw000" >
	                            	${secondTransStr}</br>
	                            	${item.contentTranslation}
	                            </div>
                            </c:if>
                           </td>
                        </tr>
                      	</table>
                      	
                      	<!-- 子题开始 -->
                      	<div class="selecteds" style="width:720px;height:163px;overflow-y:scroll;">
                      	<table width="700" border="1" cellpadding="3" cellspacing="0" bordercolor="#EEEEEE">                  		
						<c:forEach items="${item.subItems}" var="subItem" varStatus="status2">
						<tr>
							<td width="5%">
								<img
	                				<c:if test="${currentPage.isMark[status2.index]==1}" var="mark">
	                					src="../images/icon_question.gif"
	                					value="1"
	                				</c:if>
	                 				<c:if test="${mark==false }">
	                 					src="../images/icon_noquestion.gif"
	                					value="0"
	                 				</c:if>
                  					border="0" align="absmiddle" style="cursor: hand" onclick="chgmark(this);"
                  					id="mark${status2.index}"
                  			    />
                  			    <input type="hidden" name="mapkey${status2.index }" id="mapkey${status2.index }" value="${item.id}::${subItem.id} "/>
                                <input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
							</td>
							
                        	<td width="3%" class="f16px fB">${subItem.itemNum}</td>                             
							  
							<c:forEach items="${subItem.answerOptions}" var="answerOption" varStatus="status3">
							<td width="18%">                          
                                <input name="userAnswer${status2.index}" type="radio" id="userAnswer${status2.index}" value="${answerOption.code }"
                                 <c:if test="${answerOption.code==currentPage.userAnswer[status2.index]}">
									 checked
								</c:if>
								<c:if test="${viewControl.isFilter==true&&subItem.filterShow==false}">
									 disabled
								</c:if>
								<c:if test="${subItem.enable==false}">
									 disabled
								</c:if>
                                 />
                                <c:if test="${status3.index==0}">[A]</c:if>
								<c:if test="${status3.index==1}">[B]</c:if>
								<c:if test="${status3.index==2}">[C]</c:if>
								<c:if test="${status3.index==3}">[D]</c:if>
								${answerOption.content}
								<c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
									<c:if test="${currentPage.isRight[status2.index]==1&&answerOption.code==currentPage.userAnswer[status2.index]}">
									 	<img src="../images/true.gif" width="16" height="15" align="texttop" />
									</c:if>
									<c:if test="${currentPage.isRight[status2.index]==0&&answerOption.code==currentPage.userAnswer[status2.index]}">
									 	<img src="../images/false.gif" width="16" height="15" align="texttop" />
									</c:if>
								</c:if>
							</td>			
                            </c:forEach>
                            
                            <td width="11%" class="cRed">
                            	<c:if test="${viewControl.showAnswer}">
                            	<span lang=EN-US style='font-size:13.0pt;color:red'>${subItem.correctAnswer}</span>
                            	</c:if>
                            	<c:if test="${currentPage.starInt[status2.index]>0||currentPage.starHalf[status2.index]>0}">                            	
									<c:forEach var="i" begin="1" end="${currentPage.starInt[status2.index]}"  step="1">  
          							★
  									</c:forEach>
  									<c:forEach var="i" begin="1" end="${currentPage.starHalf[status2.index]}" step="1">
  									☆
  									</c:forEach>
  								</c:if>
                            </td>
							
							<c:if test="${viewControl.showModel!=1}">
							<c:if test="${(viewControl.processInstance.node.nodeType ne 'PHASETEST')&&(viewControl.processInstance.node.nodeType ne 'EVALUATE') }">
                            <td ${(viewControl.showModel!=1 && (currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>0))?'width="11%"':'width="0%"'}>                         	
                        			<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=1)}">
										<a href="javascript:void(null)" onclick="doit(kp${status2.index}); closeit(aa${status2.index}); closeit(at${status2.index}); ">
	                            			<img src="../images/info_01.gif" alt="考点提示" width="16" height="16" /></a>
	                            	</c:if>	
	                            	<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=2)}">
	                            		<a href="javascript:void(null)" onclick="doit(aa${status2.index}); closeit(kp${status2.index}); closeit(at${status2.index}); ">
	                            			<img src="../images/info_02.gif" alt="详细解析" width="16" height="16" /></a>
	                            	</c:if>
	                            	<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=1)}">	
	                            		<a href="javascript:void(null)" onclick="doit(at${status2.index}); closeit(kp${status2.index}); closeit(aa${status2.index}); ">
	                            			<img src="../images/book_01.gif" alt="选项译文" width="16" height="16" /></a>
	                            	</c:if>
	                            	<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=2)}">
	                            		<script language="JavaScript" type="text/javascript">	  	                            			 
	                            			document.getElementById('ywdiv').style.display = "";				                            												
										</script>
	                            	</c:if>
	                            	<c:if test="${viewControl.showModel!=1&&(errorTimes[lengthCount]==0)}">
	                            		&nbsp;
	                            	</c:if>
                            </td>
                            </c:if>
                            </c:if>
                        </tr>
                        
                        <c:if test="${viewControl.showModel!=1}">                         
                        <tr id="kp${status2.index}" style="display:none;">
                        	<td colspan="8" class="d1" style="padding:8px;">                 
	                          	<c:if test="${subItem.knowledgePointsName ne ''}">
	                          		<p>${firstClueStr}${subItem.knowledgePointsName}题 </p>
	                          	</c:if>
	                          	<c:if test="${subItem.knowledgePointsName eq ''}">
	                          		<p>此题暂无考点信息</p>                    
	                          	</c:if>
	                        </td>
                        </tr>
 						
 						<tr id="aa${status2.index}" style="display:none;">
                        	<td colspan="8" class="d1" style="padding:8px;">                
	                          	<p>${secondClueStr}</p>
	                            <p>${subItem.answerAnalysis} </p>
	                        </td>
                        </tr>
                        
                        <tr id="at${status2.index}" style="display:none;">
                        	<td colspan="8" class="d1" style="padding:8px;">                        
	                          	<p>${firstTransStr}</p>
	                            <p>
	                            <c:forEach items="${subItem.answerOptions}" var="answerOption" varStatus="status3">						
	                                <c:if test="${status3.index==0}">[A]</c:if>
									<c:if test="${status3.index==1}">[B]</c:if>
									<c:if test="${status3.index==2}">[C]</c:if>
									<c:if test="${status3.index==3}">[D]</c:if>
									${answerOption.translation} &nbsp;&nbsp;&nbsp;&nbsp;
                            	</c:forEach>
	                            </p>
	                        </td>
                        </tr>
                        </c:if>
                        
                        <c:set var="lengthCount" value="${lengthCount+1}"></c:set>
                        </c:forEach>
                        </table>
                        </div>
                        <!-- 子题结束 -->
                       </td>
                    </tr>
				</c:forEach>
              </table>
			  </div>
			</td>
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
    <div class="clear">ui </div>
</div>
<jsp:include page="include_bottom.jsp"></jsp:include>
<!--  <div class="bonnt wm1">© 2008年  安博教育集团  版权所有</div>-->
</body>
</html>
