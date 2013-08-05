<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="normal" ><a class="cBlack" href="consolidate!training.jhtml?orderNum=${orderNum}&nodeType=${nodeType}" target="main">训练报告</a></li>
      <li class="active" ><a class="cBlack" href="mpcstudy!review.jhtml?nodeInstanceId=${nodeInstance.id}&orderNum=${orderNum}">回顾复习</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
         <h1 class="til"><span>“${nodeInstance.node.name}”</span>回顾复习：</h1>
        <hr  />
                <div class="blank9"></div>
        <div class="bg">
          <div class="con_right_content fB box1" >历史训练数据</div>
          <table width="698" border="0" cellspacing="1">
            <tr>
              <td>训练次数</td>
              <td>题数</td>
              <td>做对</td>
              <td>做错</td>
              <td>未答</td>
              <td>疑问</td>
              <td>正确率要求</td>
              <td>正确率</td>
              <td>掌握度</td>
              </tr>
            <tr>
              <td>第${num}次</td>
              <td>${historyTestStatus.sumCorrectItems+historyTestStatus.sumIncorrectItems}</td>
              <td>${historyTestStatus.sumCorrectItems}</td>
              <td>${historyTestStatus.sumIncorrectItems}</td>
              <td>${historyTestStatus.sumUnfinishedItems}</td>
              <td>${historyTestStatus.unsureMarkItems}</td>
              <td>${historyTestStatus.requireAccuracyRate}%</td>
              <td>${historyTestStatus.accuracyRate}%</td>
              <td>${historyTestStatus.masteryRate}%</td>
              </tr>
          </table>
        </div>
        <h4><span class="bbs1"><span class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:window.top.location='../exam/showExam.jhtml?pageNum=0'">逐题回顾</span></span></h4>
        </div>
        <div class="content_c">
            <iframe width="100%"  name="overview" id="frame_overview" src="../exam/viewExam.jhtml?nodeInstanceId=${nodeInstanceId}&historyTestStatusId=${historyTestStatus.id}" scrolling="no" frameborder="0" onload="this.height=frame_overview.document.body.scrollHeight"></iframe>
        </div>
         
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
