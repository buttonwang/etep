<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学习指导红宝书</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
 
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="active" ><a class="cBlack" href="studyGuideDescAction.jhtml?nodeId=${pageMap['nodeId']}&nodeGroupId=${pageMap['nodeGroupId']}" target="main" >知识讲解</a></li>
      <li class="normal" ><a class="cBlack" href="javascript:void(null)">典型例题</a></li>
    </ul>
  </div>
  
  <!-- 内容开始 -->
  <div class="TabContent">
	    <div id="myTab1_Content0">
		    <div class="style1">
				本节没有典型例题	
			</div>
		</div>
	</div>
   <!-- 载入内容结束 -->
        
        
        <div class="blank20"></div>
          <h4>
        	<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
        		onclick="location.href='studyGuideDescAction.jhtml?nodeId=${pageMap['nodeId']}&nodeGroupId=${pageMap['nodeGroupId']}'">知识讲解</button></span>
		  </h4>
          <div class="blank20"></div>
          <hr/>
          <h1>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <c:choose>
                	<c:when test="${pageMap['perviousName']==null}">
                		<td><strong>上一个：</strong>没有了</td>
                	</c:when>
                	
                	<c:otherwise> 
                		<td><strong>上一个：</strong><a  onclick="parent.sById(${pageMap['previousId'] })" href="studyGuideDescAction.jhtml?nodeId=${pageMap['previousId']}&nodeGroupId=${pageMap['previousGroupId']}&nodeName=${pageMap['perviousName']}"  target="main" >${pageMap['perviousName'] }</a></td>
                	</c:otherwise>
                </c:choose>              
                <td><strong>当前知识点：</strong>${pageMap['nodeName']}</td>
                <c:choose>
                	<c:when test="${pageMap['nextName']==null}"> 
                		<td><strong>下一个：</strong>没有了</td>
                	</c:when>
                	
                	<c:otherwise>
                		<td><strong>下一个：</strong><a onclick="parent.sById(${pageMap['nextId'] })" href="studyGuideDescAction.jhtml?nodeId=${pageMap['nextId']}&nodeGroupId=${pageMap['nextGroupId']}&nodeName=${pageMap['nextName']}"  target="main" >${pageMap['nextName'] }</a></td>
                	</c:otherwise>
                </c:choose>
              </tr>
            </table>
          </h1>
          <div class="blank20"></div>
  </div> 
  	
</body>
</html>