<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${viewControl.flowName}</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/tandiv.js"></script>
</head>

<body>

<jsp:include page="include_head.jsp"></jsp:include>

 <!-- 页面主体 -->
 
 <div id="contentLayout" class="wm950">
 	<!--Start left-->
  <div class="content_left wm640 left">
  		<div class="content_bg">
    	<div class=ye_r_t>
		<div class=ye_l_t>
		<div class=ye_r_b>
		<div class=ye_l_b>
        <div class="con_l">
		<h2><span>${userDataVO.userName}</span>同学，欢迎你进入如下章节的学习任务：</h2>
        <h1 class="til">
			<c:if test="${viewControl.examNodeIns ne null}">
 				【${viewControl.chapterName}】-【${viewControl.sectionName}】&nbsp;&nbsp;&nbsp;<span>${viewControl.examName}</span>
			</c:if>
			<c:if test="${viewControl.examNodeIns eq null}">
				【${viewControl.flowName}】
			</c:if>
        </h1>
        <div class="label box1">
        	<ul>
            	<li class="f14px"><b>任务名称：</b>${viewControl.examTask}</li>
                <li class="f14px"><b>任务性质：</b>${viewControl.examType}</li>
                <li class="blankW_9"></li>
                <li><b>总题数：</b><span>${viewControl.examItemNum}题</span></li>
                <li><b>总分：</b><span>${viewControl.examValue}</span></li>
                <li><b>限时：</b><span>${viewControl.examTimeStr}</span></li>
                <c:if test="${(viewControl.examTypePara eq 'practice') || (viewControl.examTypePara eq 'unitExam') }">
                <li><b>正确率要求：</b><span>${viewControl.requireRightRate}%</span></li>
                </c:if>
            </ul>
        </div>
		<c:if test="${viewControl.isWeaknessEnhance==true}">
        	<h3>
        		针对弱项，强化训练，可迅速提高掌握度！
        		<c:if test="${viewControl.examItemNum>10}">
        		<span><a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">调整题量</a></span>
        		</c:if>
        	</h3>
			<form action="overView.jhtml" method="post" id="form1">
		 	<div id="prompt" style="display:none;" class="label box1">
		 		每次弱项强化的题量，不超过50题为宜。
		 		<input id="itemNum" name="itemNum" type="radio" value="${viewControl.examItemNum}" checked="checked" />${viewControl.examItemNum}（默认）
		 		<c:if test="${viewControl.examItemNum>10}">
		 			<input id="itemNum" name="itemNum" type="radio" value="10" />10
		 		</c:if>
		 		<c:if test="${viewControl.examItemNum>20}">
		 			<input id="itemNum" name="itemNum" type="radio" value="20" />20
		 		</c:if>
		 		<c:if test="${viewControl.examItemNum>50}">
		 			<input id="itemNum" name="itemNum" type="radio" value="50" />50
		 		</c:if>
		 	</div>
        	<hr/>
        	<div class="bnt_box">        	
	        	<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
	        		onclick=":document.getElementById('form1').submit();">进入任务</button></span>          	
	          	<span class="gbs1"><button class="gb1" onmouseout="this.className='gb1';" onmouseover="this.className='gb2';"
	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	onclick="location.href='../report/reportMain.jhtml?showListType=0'"
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		onclick="location.href='../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}'"
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		onclick="location.href='../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}'"
		              		</c:if>
		              	</c:if>
	              	</c:if> 
	              	<c:if test="${viewControl.extPractice}">
					onclick="location.href='../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}'"
					</c:if> 
					<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">                 
                 	onclick="location.href='../web/loadSessionVar!mpc.jhtml'"
					</c:if>    
	              	>暂不进入</button></span>            

	          	<div class="clear"></div>
        	</div>
			</form>
		</c:if>
		<c:if test="${viewControl.isWeaknessEnhance==false}">   
			<c:if test="${viewControl.examTypePara eq 'evaluate'}">
        		<h3>
        			<c:if test="${fn:contains(viewControl.examName, '前')}"> 		  
        				开始本章学习前，系统先评估你对本章的掌握情况！
        			</c:if>
        			<c:if test="${fn:contains(viewControl.examName, '后')}">	  
        				完成此评测后将结束本章学习，努力过就会有收获！
        			</c:if>
        			<span><a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">评测知识点列表</a></span>
        		</h3>
        		<div id="prompt" style="display:none;" class="label box1 bg">
        			<table width="100%" border="0" cellspacing="1" cellpadding="0">
			          <tr>
			            <td width="35%" scope="row"><strong>章名</strong></td>
			            <td width="55%"><strong>评测知识点</strong></td>
			            <td width="10%"><strong>分值</strong></td>
			          </tr>
			          <c:forEach items="${viewControl.examBeginProperty.evaluatingAnswerStatus}" var="evaluatingAnswer" varStatus="evalStatus">
			          	 <tr>
			          	 	<c:if test="${evalStatus.first}">
			          	 	<td rowspan="${viewControl.examBeginProperty.evaluatingAnswerSize}" scope="row">			             
			              		${viewControl.sectionName}
			              	</td>
			              	</c:if>
			             	<td>${evaluatingAnswer.knowledgePoint.name}</td>
	             			<td>${evaluatingAnswer.totalScore}</td>
			          	 </tr>
			          </c:forEach>
			        </table>
        		</div>
        		<hr/>
        	</c:if>
        	<c:if test="${viewControl.examTypePara eq 'phaseExam'}">
        		<h3>前测成绩直接影响学习进度，请认真作答！
        			<span><a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">查看示意图</a></span>
        		</h3>
        		<div id="prompt" style="display:none;" class="label box1">
        			<img src="../images/info_01.gif" />
        		</div>	
        		<hr/>
        	</c:if>
        	<c:if test="${viewControl.examTypePara eq 'practice'}">
        		<h3>训练正确率达到要求即通过，否则需要重练，   请认真作答！
        			<span><a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">查看示意图</a></span>
        		</h3>
        		<div id="prompt" style="display:none;" class="label box1">
        			<img src="../images/info_02.gif" />
        		</div>	
        		<hr />
        	</c:if>
        	<c:if test="${viewControl.examTypePara eq 'unitExam'}">
        		<h3>后测正确率达到要求即进入下一学习任务，否则返回重练，   请认真作答！
        			<span><a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">查看示意图</a></span>
        		</h3>
        		<div id="prompt" style="display:none;" class="label box1">
        			<img src="../images/info_03.gif" />
        		</div>	
        		<hr />
        	</c:if>
	        <div class="bnt_box">	           	
	           	<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
	           			onclick="javascript:window.location='overView.jhtml'" >进入任务</button></span>	           	
	           	
	           	<span class="gbs1"><button class="gb1" onmouseout="this.className='gb1';" onmouseover="this.className='gb2';"

	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	onclick="location.href='../report/reportMain.jhtml?showListType=0'"
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		onclick="location.href='../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}'"
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		onclick="location.href='../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}'"
		              		</c:if>
		              	</c:if>
	              	</c:if> 
              		<c:if test="${viewControl.extPractice}">
					onclick="location.href='../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}'"
					</c:if> 
					<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">                 
                 	onclick="location.href='../web/loadSessionVar!mpc.jhtml'"
					</c:if>    
		           		>暂不进入</button></span>
	            <div class="clear"></div>
	        </div>
		</c:if>
        </div></div></div></div></div></div>
  </div>
 
  <!--End left-->
    <div class="content_right wm290 left">
    	<div class="content_bg">
    	<div class=yr_r_t>
		<div class=yr_l_t>
		<div class=yr_r_b>
		<div class=yr_l_b>
        <div class="con_r">
		<h2 class="left"><b>答题版式：</b>默认/自定义 </h2>
		<!--<span class="set right"><a href="setpage.jhtml">更改答题版式</a></span>-->
        <div class="clear"></div>
        <ul>
        <li>•选择题：${viewControl.processTrainingStatus.layout1}题/页</li>
        <li>•填空题小题：${viewControl.processTrainingStatus.layout2}题/页</li>
        <li>•填空题大题：${viewControl.processTrainingStatus.layout3}题/页</li>
        <li>•字号：
        	<c:choose>
        		<c:when test="${viewControl.processTrainingStatus.font=='L'}">小</c:when>
				<c:when test="${viewControl.processTrainingStatus.font=='M'}">中</c:when>
				<c:when test="${viewControl.processTrainingStatus.font=='B'}">大</c:when>
				<c:otherwise></c:otherwise>
        	</c:choose>
        </li>
        </ul>
		</div></div></div></div></div></div>
        <div class="blank20"></div>
        <div class="content_bg">
        <div class=yr_r_t>
		<div class=yr_l_t>
		<div class=yr_r_b>
		<div class=yr_l_b>
		<div class="con_r">
		<h2><span class="warn1 cGreen"><b>学习谚语：</b></span></h2>
        <h3><strong>Practice makes perfect.熟能生巧。</strong></h3>
        <div class="blank6"></div>
        <h4><span class="warn2 cOra"><b>操作提示</b></span></h4>
        <ul>
        <li>•&nbsp;注意右上角的倒计时提示，限时做题很重要！</li>
        <li>•&nbsp;你可以跳过不会做的题，最后选择“未做题”单独做； </li>
        <li>
        	<span class="left">•&nbsp;如果对某道题有疑问，可以通过</span>
        	<div class="query_c left"><span class="dis">疑问</span></div>
        	<span class="left">来标记。</span>
        </li>
		 <li>•&nbsp;如果对某道题毫无思路，可以点击<img src="../images/prompt_ico.gif" />获取提示。</li>
		 <div class="clear"></div>
        </ul>
		</div></div></div></div></div></div>
  </div>
  <div class="clear"></div>
</div>

<jsp:include page="include_bottom.jsp"></jsp:include>

</body>
</html>
