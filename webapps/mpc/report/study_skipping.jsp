<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="active" ><a class="cBlack" href="javascript:void(null)">训练报告</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til"><span>“&nbsp;<span>${viewControl.examName}</span>”</span>综合训练报告：</h1>
        <hr />
        <div class="bg">
                <h1>因前测成绩优异，此学习任务被跳过，现在你可随时进入学习（结果不会影响当前进度，可放心学习）。</h1>
            <div class="clear"></div>
        </div>
        <div class="blankW_6"></div>
        <h4><span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:top.location.href='../exam/overView.jhtml'">进入任务</button></span></h4>
        <div class="label box1">
        <div class="blankW_6"></div>
        	<ul>
            	<li class="f14px"><b>任务名称：</b>${viewControl.examTask}</li>
                <li class="f14px"><b>任务性质：</b>${viewControl.examType}</li>
                <li class="blankW_9"></li>
                <li><b>总题数：</b>${viewControl.examItemNum} 题</li>
                <li><b>总分：</b>${viewControl.examValue}</li>
                <li><b>限时：</b>${viewControl.examTimeStr}</li>
            </ul>
        </div>
        </div>
      </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
