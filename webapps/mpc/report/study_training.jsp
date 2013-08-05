<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String ctx = (String)request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="active" ><a class="cBlack" href="javascript:void(null)">训练报告</a></li>
      <li class="normal" ><a class="cBlack" href="tconsolidate_report.html" target="main" >回顾复习</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til"><span>“第1章化学反应及能量”</span>如“训练1”综合训练报告：</h1>
        <div class="blankW_9"></div>
        <div class="bg box1 padding10px star1">
          <div class="bg_left">
          <h4><span>恭喜你，已于****年**月**日通过本训练！</span><span class="bbs1"><label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"><a class=" cWhite" href="tconsolidate_report.html" target="main" >回顾复习</a></label></span></h4>
        <div class=" blank6"></div>
        <div class="bg">
        <h1 class="fB">训练概况</h1>
        <table width="370" border="0" cellspacing="1">
          <tr>
            <td>总次数</td>
            <td>总用时</td>
            <td>总正确率要求</td>
			<c:if test="${nodeType !='PRACTICE'}">
            <td>通过时总正确率</td>
			</c:if>
            <td>当前掌握度</td>
          </tr>
          <tr>
            <td>6</td>
            <td>**小时**分钟</td>
			
            <td>85%</td>
            <td>92%/--</td>
            <td>87%</td>
          </tr>
        </table>
        </div>
        </div>
 	<div class="bg_right"><img src="images/pic01.gif" width="300" height="179" /><h1>星级题分布饼图</h1>
    </div> 
    <div class="clear"></div>
 </div>
 <div class="blankW_9"></div>
 		<div class="bg">
        	<div class="con_right_content fB box1" >题目数、星级题统计</div>
        	<table width="698" border="0" cellspacing="1">
        	  <tr>
        	    <td width="81">已训练总题数</td>
        	    <td width="54">对题总数</td>
        	    <td width="54">错题总数</td>
        	    <td width="68">未答题总数</td>
        	    <td width="68">疑问题总数</td>
        	    <td width="71"><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /></td>
        	    <td width="58"><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /></td>
        	    <td width="44"><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /></td>
        	    <td width="39"><img src="images/star_m.gif" width="12" height="12" /><img src="images/star_m.gif" width="12" height="12" /></td>
        	    <td width="39"><img src="images/star_m.gif" width="12" height="12" /></td>
        	    <td width="39"><img src="images/star_m_k.gif" width="12" height="12" /></td>
        	    <td width="46"><img src="images/smile_m.gif" width="10" height="10" /></td>
      	    </tr>
        	  <tr>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>***</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
        	    <td>**</td>
      	    </tr>
      	  </table>
 		</div>
         <div class="blankW_9"></div>
        <div class="bg">
        	<div class="con_right_content fB box1" >历次训练数据（点击次数查看详情）</div>
            <table width="698" border="0" cellspacing="1">
  <tr>
    <td>训练次数</td>
    <td>题数</td>
    <td>对题数</td>
    <td>错题数</td>
    <td>未答题数</td>
    <td>疑问题数</td>
    <td>总正确率要求</td>
    <td>
      训练后达到的总正确率</td>
    <td>训练后掌握度</td>
    </tr>
  <tr>
    <td><a href="consolidate_review.html" target="main" >第6次</a></td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td rowspan="6">85%</td>
    <td>90%</td>
    <td>90%</td>
    </tr>
  <tr>
    <td><a href="#">第5次</a></td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>90%</td>
    <td>90%</td>
    </tr>
  <tr>
    <td><a href="#">第4次</a></td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>90%*</td>
    <td>90%*</td>
    </tr>
  <tr>
    <td>第3次</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>90%</td>
    <td>90%</td>
    </tr>
  <tr>
    <td>第2次</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>90%</td>
    <td>90%</td>
    </tr>
  <tr>
    <td>第1次</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>**</td>
    <td>90%*</td>
    <td>90%</td>
    </tr>
</table>
 </div>

        </div>
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
