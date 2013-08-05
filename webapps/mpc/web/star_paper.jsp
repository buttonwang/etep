<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>星卷榜</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />

<body>
<jsp:include page="include_head.jsp"></jsp:include>

<div id="contentLayout" class="wm950">
 	<!--Satr left-->
  <div class="wm950">
  		<div class="content_bg">
    	<div class=ye_r_t>
		<div class=ye_l_t>
		<div class=ye_r_b>
		<div class=ye_l_b>
        <div class="starexam_top"><span class="f16px fB">星卷榜</span></div>
        <div class="starexam_con">
            
             <c:set var="countNum" value="${'0'}"></c:set>              
              <c:forEach items="${starPaperList}" var="item" varStatus="itemStatus">                  
	                  	<c:set var="countNum" value="${countNum+1}"></c:set>
	                    
	              <div class="starexam">
		            <div class="starexam_titl left">${countNum}</div>
		            <div class="starexam_titr right">${item.nodeGroupName}</div>
		            <div class="clear"></div>
		            <div class="starexam_c">
		            <div class="starexam_cl">
	            	<ul>
	                	<li class="f147px">${item.nodeName}</li>
	                    <li class="cOra">掌握度:<span>${item.masteryRate}%</span></li>
	                    <li>总分:${item.totalScore} </li>
	                    <li>得分:${item.score}</li>
	                    <li>题数:${item.totalItems}</li>
	                    <li>做对:${item.correctItems}</li>
	                </ul>
	            </div>
	            <div class="starexam_cr">
	           <span class="bbs1"><label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="parent.location.href='../report/reportMain.jhtml?orderNum=${item.orderNum}&nodeType=${item.nodeType}&showListType=1'">回顾复习</label></span>
	            </div>
	            <div class="clear"></div>
	            </div>
	            </div>
	                    	
              </c:forEach> 
            
            </div>
            </div>
            </div>
  </div>
  <!--End left-->
  <div class="clear"></div>
</div>
<!--页面尾部-->
<jsp:include page="../exam/include_bottom.jsp"></jsp:include>
</body>
</html>