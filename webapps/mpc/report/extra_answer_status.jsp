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
		        <h1 class="til">
		        	<span>“拓展练习”</span>
		        	<c:if test="${param.num eq 0}">全部</c:if>
		        	<c:if test="${param.num ne 0}">第${param.num}次</c:if>答题详情：
		        </h1>
		        <hr/>
		        <h4>
		        	<span class="bbs1"><span class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
		        	  	onclick="javascript:window.top.location='../exam/showExam.jhtml?pageNum=0'"">逐题回顾</span></span>
		        	<span class="bbs1"><span class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
		        		onclick="location.href='javascript:history.back(-1)'">返回</span></span>
		        </h4>
	        </div>
	        <div class="content_c">
	        	<iframe width="100%"  name="overview" id="frame_overview" scrolling="no" frameborder="0" onload="this.height=frame_overview.document.body.scrollHeight"
	        		src="../exam/viewExam.jhtml?nodeInstanceId=${param.nodeInstanceId}&historyTestStatusId=${param.historyTestStatusId}&examType=6"></iframe>
	        </div>
	    </div>
	    </div>
	    </div>
	  </div>
	</div>
</body>
</html>