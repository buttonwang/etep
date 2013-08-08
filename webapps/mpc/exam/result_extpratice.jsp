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
        	  <table width="280" border="0" cellpadding="0" cellspacing="1" >
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
              <h4>答题用时：${viewControl.examResultProperty.spendTimeStr2}</h4>
        	</div>
            <div class="blank6"></div>
            <div class="result_r1_r">
	            <h5 class="f14px cOra fB">得分：<span class="f32px Arial">${viewControl.examResultProperty.examScore}</span></h5>
	            <hr />
            </div>
        </div>
        <div class="clear"></div> 
        <hr/>
        <div class="bnt_box">
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
	         		onclick="location.href='overView.jhtml'">回顾本次答题</button></span>                                 
	        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
	             	onclick="location.href='../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}'">退出学习</button></span>              
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