<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/tandiv.js"></script>
</head>
<body>
<div class="wm720 nTab">
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <!--
        <div class="bg">
                <h1>本知识点下还有<span>18 </span>道新题可供拓展练习。拓展练习的结果不会影响当前进度和总体成绩，可放心学习。</h1>
        </div>
        -->
        <div class="blankW_6"></div>
        <div class="label box1">
        <div class="blankW_6"></div>
        	<ul>
            	<li class="f14px"><b>任务名称：</b>${viewControl.examType}</li>
                <li class="f14px"><b>任务性质：</b>拓展练习</li>
                <li class="blankW_9"></li>
                <li><b>总题数：</b><span>${viewControl.examItemNum}</span>题</li>
                <li><b>总分：</b><span>${viewControl.examValue}</span></li>
                <li><b>限时：</b><span>${viewControl.examTimeStr}</span></li>
                <hr />
                <li><span class="cRed">上次从上面任务中暂存退出，已做${viewControl.doneItemNum}题，已用${viewControl.spendTimeStr2}。</span></li>
            </ul>
        </div>
        <div class="blankW_6"></div>
        <h4><span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
             onclick="window.parent.location.href='../exam/overView.jhtml'">恢复拓展练习</button></span></h4>
        </div>
        </div>
        </div>
    </div>
  </div>
</body>
</html>