<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${viewControl.flowName}</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
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
		<h2><span>${userDataVO.userName}</span>，欢迎进入测试结果：</h2>
		<h1 class="til">
			<c:if test="${viewControl.examNodeIns ne null}">
 				【${viewControl.chapterName}】-【${viewControl.sectionName}】&nbsp;&nbsp;&nbsp;<span>${viewControl.examName}</span>
			</c:if>
			<c:if test="${viewControl.examNodeIns eq null}">
				【${viewControl.flowName}】
			</c:if>			
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
        <div class="result_r5 box1 left ">
        	<div class="result_r5_1 bg">
        	  <table width="280" border="0" cellpadding="0" cellspacing="1">
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
                <tr>
                	<td colspan="5" align="left">
                		<span class="left">答题用时：${viewControl.examResultProperty.spendTimeStr2}</span>
                		<span class="right">正确率：${viewControl.examResultProperty.accuracyRate}%</span>
                	</td>
                </tr>
              </table>
              <div class="blankW_9"></div>
              <table width="280" border="0" cellpadding="0" cellspacing="1">
	            <tr>
	            	<td colspan="7" align="left"><h4 class="fB">星级题统计</h4></td>
	            </tr>
              	<tr>
	                <td width="76" height="20">
	                	<img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" />	                	
	                </td>
	                <td width="56" height="20">
	                	<img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" />	                	
	                </td>
	                <td width="55" height="20">
	                	<img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" />	                	
	                </td>
	                <td width="46" height="20">
	                	<img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" />
	                </td>
	                <td width="38" height="20">
	                	<img src="../images/star_m.gif" width="12" height="12" />
	                </td>
	                <td width="32" height="20">
	                	<img src="../images/star_m_k.gif" width="12" height="12" />
	                </td>
	                <td width="39" height="20">
	                	<img src="../images/smile_m.gif" width="10" height="10" />
	                </td>
              	</tr>
              	<tr>
	                <td>${viewControl.examResultProperty.fiveStarCount}</td>
	                <td>${viewControl.examResultProperty.fourStarCount}</td>
	                <td>${viewControl.examResultProperty.threeStarCount}</td>
	                <td>${viewControl.examResultProperty.twoStarCount}</td>
	                <td>${viewControl.examResultProperty.oneStarCount}</td>
	                <td>${viewControl.examResultProperty.halfStarCount}</td>
	                <td>${viewControl.examResultProperty.zeroStarCount}</td>	                	                	                
              	</tr>
              	<tr>
                	<td colspan="7" align="left"><span class="left">掌握度：${viewControl.examResultProperty.masteryRate}%</span></td>
                </tr>
            </table>
        	</div>
        	<div class="result_r5_r">
        		<img src="../../ky/report/getImage.jhtml?_zero=${viewControl.examResultProperty.zeroStarCount}&_half=${viewControl.examResultProperty.halfStarCount}&_one=${viewControl.examResultProperty.oneStarCount}&_two=${viewControl.examResultProperty.twoStarCount}&_three=${viewControl.examResultProperty.threeStarCount}&_four=${viewControl.examResultProperty.fourStarCount}&_five=${viewControl.examResultProperty.fiveStarCount}&_type=mpc"        			        			
        		 width="300" height="179" />
        		<h2>星级题分布饼图</h2>
        	</div>
            <div class="blank6"></div>
            <div class= "result_r5_r">
	            <h5 class="f14px cOra fB">得分：<span class="f32px Arial">${viewControl.examResultProperty.examScore}</span></h5>
	            <hr/>	                       				
            </div>
        </div>
        <div class="clear"></div>
        <hr/>
        <div class="bnt_box">         
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
	        	onclick="location.href='overView.jhtml'">回顾本次答题</button></span>                                                
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"

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
	            	>退出学习</button></span>                  
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