<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${viewControl.flowName}</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/tandiv.js"></script>
</head>

<body>

<!--页面头部-->
<jsp:include page="include_head.jsp"></jsp:include>
<!--    end   -->

 <!-- 页面主体-->
<div id="contentLayout" class="wm950">
 	<!--Satr left--> 
  <div class="wm950"> 
  		<div class="content_bg"> 
    	<div class=ye_r_t> 
		<div class=ye_l_t> 
		<div class=ye_r_b> 
		<div class=ye_l_b> 
        <div class="con_l"> 
		<h2><span>${userDataVO.userName}</span>同学，欢迎进入测试结果：</h2>
		<h1 class="til">
			【${viewControl.chapterName}】-【${viewControl.sectionName}】&nbsp;&nbsp;&nbsp;<span>${viewControl.examName}</span>
		</h1>        
        <h3 class="f14px fB">本次学习任务答题情况</h3> 
        <div class="result box1 left">
        	<h4 class="f14px fB">本次学习任务信息</h4>
        	<ul> 
            	<li class="f14px"><b>任务名称：</b>${viewControl.examTask}</li> 
                <li class="f14px"><b>任务性质：</b>${viewControl.examType}</li> 
                <li class="blankW_9"></li>
                <li><b>总题数：</b><span>${viewControl.examItemNum}</span>题</li>                  
                <li><b>总分：</b><span>${viewControl.examValue}</span></li>
                <li><b>难度：</b><span>${viewControl.difficultyValue}</span></li>
                <li><b>限时：</b><span>${viewControl.examTimeStr}</span></li>
            </ul>
        </div>
        <div class="result_r3 box1 left ">
        	<div class="result_r3_1 bg">
        	  <table width="220" border="0" cellpadding="0" cellspacing="1" class="left">
              	<tr>
                  <td colspan="5" align="left"><h4 class="fB">本次学习任务答题情况</h4></td>
                </tr>
                <tr>
                  <td>题数</td>
                  <td>做对</td>
                  <td>做错</td>
                  <td>未答</td>
                  <td>疑问</td>
                </tr>
                <tr>
                	<td>${viewControl.examResultProperty.itemCount}</td>
                	<td>${viewControl.examResultProperty.rightCount}</td>
                	<td>${viewControl.examResultProperty.errorCount}</td>
                	<td>${viewControl.examResultProperty.undoCount}</td>
                	<td>${viewControl.examResultProperty.markCount}</td>
                </tr>
              </table>
        	</div>
            <div class="blank6"></div>          
            <div class="result_r6_r">
	            <h5 class="f14px cOra fB">得分：<span class="f32px Arial">${viewControl.examResultProperty.examScore}</span></h5>
	            <hr />
	            
	            <h4><strong>评测结果</strong></h4>
				<div class="bg">
				<table width="100%" border="0" cellspacing="1" cellpadding="0">
		          <tr>
		            <td width="25%" rowspan="2" scope="row"><strong>章名</strong></td>
		            <td width="35%" rowspan="2"><strong>评测知识点</strong></td>
		            <td colspan="3"><strong>评测</strong></td>
		          </tr>
			      <tr>
		            <td width="15%">分值</td>
		            <td width="15%">得分</td>
		            <td width="20%">正确率</td>
		          </tr>			     
			      <c:forEach items="${viewControl.examResultProperty.evaluatingAnswerStatus}" var="evaluatingAnswer" varStatus="evalStatus">
	          	  <tr>
	          	  	<c:if test="${evalStatus.first}">
		             	<td rowspan="${viewControl.examResultProperty.evaluatingAnswerSize}" scope="row">
		              		${viewControl.sectionName}
		              	</td>
	              	</c:if>
	             	<td>${evaluatingAnswer.knowledgePoint.name}</td>
	             	<td>${evaluatingAnswer.totalScore}</td>
	            	<td>${evaluatingAnswer.score}</td>
	            	<td>
	            		<fmt:formatNumber type="number" pattern="##.##" value="${evaluatingAnswer.rightRate*100}"> </fmt:formatNumber>%
	            	</td>
	          	  </tr>
			      </c:forEach>
			    </table>
			    </div>
			    
			    <c:if test="${viewControl.examResultProperty.allEvaluatingAnswerSize ne 0}">
			    	<div class=" blankW_9"></div>
        			<h3>
        				努力过就会有收获，感受自己实在的进步！
        				<span><a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">查看章前评测</a></span>
        			</h3>
			        <div id="prompt" style="display:none;" class="bg">
			          <table width="100%" border="0" cellspacing="1" cellpadding="0">			          	
			            <tr>
			              <td width="32%" rowspan="2" scope="row"><strong>章名</strong></td>
			              <td width="27%" rowspan="2"><strong>评测知识点</strong></td>
			              <td colspan="3"><strong>学前评测</strong></td>
			              <td colspan="3"><strong>学后评测</strong></td>
			            </tr>
			            <tr>
			              <td width="6%">分值</td>
			              <td width="7%">得分</td>
			              <td width="8%">正确率</td>
			              <td width="6%">分值</td>
			              <td width="7%">得分</td>
			              <td width="8%">正确率</td>
			            </tr>
			            <c:forEach items="${viewControl.examResultProperty.allEvaluatingAnswerStatus}" var="allEvaluatingAnswer" varStatus="allEvalStatus">
			          	<tr>
			          		<c:if test="${allEvalStatus.first}">
				            	<td rowspan="${viewControl.examResultProperty.allEvaluatingAnswerSize}" scope="row">
				              		${viewControl.sectionName}
				              	</td>
			              	</c:if>
			             	<td>${allEvaluatingAnswer.knowledgePoint.name}</td>
			             	<td>${allEvaluatingAnswer.totalScore}</td>
			            	<td>${allEvaluatingAnswer.score}</td>
			            	<td>
			            		<fmt:formatNumber type="number" pattern="##.##" value="${allEvaluatingAnswer.rightRate*100}"> </fmt:formatNumber>%
			            	</td>
			            	<td>${allEvaluatingAnswer.totalScore2}</td>
			            	<td>${allEvaluatingAnswer.score2}</td>
			            	<td>
			            		<fmt:formatNumber type="number" pattern="##.##" value="${allEvaluatingAnswer.rightRate2*100}"> </fmt:formatNumber>%
			            	</td>
			          	</tr>
					    </c:forEach>       
			          </table>
			        </div>
			    </c:if>
			    <div class="blank6"></div>
			    <c:if test="${viewControl.stoped}">
	            	<h1>祝贺你，所有学习任务都已完成！</h1>
            		<h2>请回顾本次答题或退出学习！</h2>
	            </c:if>
	            <c:if test="${!viewControl.stoped}">
	            	<h1>评测已完成！请进入如下学习任务：</h1>
	            	<h2><a href="goExam.jhtml?nodeInstanceId=${nextNodeId}">${nextNodeName}</a></h2>	           				
	            </c:if>
            </div>
        </div>
        <div class="clear"></div>
        <hr/>
        <div class="bnt_box">                
        	<jsp:include page="include_study_guide.jsp"></jsp:include>
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
	        		onclick="location.href='overView.jhtml'">回顾本次答题</button></span>
	        <c:if test="${!viewControl.stoped}">                              
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
	        		onclick="location.href='goExam.jhtml?nodeInstanceId=${nextNodeId}'">进入新学习任务</button></span> 
	        </c:if>                              
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
	            	onclick="location.href='../web/loadSessionVar!mpc.jhtml'">退出学习</button></span>                  
	        <div class="clear"></div> 
        </div>
		</div></div></div></div></div></div> 
  </div> 
  <!--End left-->
  <div class="clear"></div>
</div>

<jsp:include page="include_bottom.jsp"></jsp:include>

</body>
</html>