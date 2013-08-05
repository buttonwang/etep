<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>更多排名</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script></head>

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
        <div class="ranking_con">
		<ul>
        	<li>        
            <div class="ranking_title">总进度排名</div>
            <table width="267" border="0" cellspacing="1" cellpadding="0">            
                  <c:set var="studentCount" value="${'0'}"></c:set>              
               		 <c:forEach items="${learningProcessRateTopList}" var="item" varStatus="itemStatus">                  
	                  	<c:set var="studentCount" value="${studentCount+1}"></c:set>
	                  	  <tr>
	                      <td width="30%">${studentCount}</td>
	                      <td width="31%">${item.userName}</td>
	                      <td width="39%">${item.learningProcessRate}%</td>
	                    </tr>		
                    </c:forEach> 
            </table>
            </li>
            <li>
            <div class="ranking_title">总掌握度排名</div>
            <table width="267" border="0" cellspacing="1" cellpadding="0">
               <c:set var="studentCount2" value="${'0'}"></c:set>
                     <c:forEach items="${totalMasteryRateTopList}" var="item" varStatus="itemStatus">                  
	                  			<c:set var="studentCount2" value="${studentCount2+1}"></c:set>
	                  	  <tr>
	                      <td width="30%">${studentCount2}</td>
	                      <td width="31%">${item.userName}</td>
	                      <td width="39%">${item.totalMasteryRate}%</td>
	                    </tr>		
                    </c:forEach> 
            </table>
            </li>
            <li>
            <div class="ranking_title">总正确率排名</div>
            <table width="267" border="0" cellspacing="1" cellpadding="0">
              <c:set var="studentCount3" value="${'0'}"></c:set>
                     <c:forEach items="${accuracyRateTopList}" var="item" varStatus="itemStatus">                  
	                  	<c:set var="studentCount3" value="${studentCount3+1}"></c:set>
	                  	  <tr>
	                      <td width="30%">${studentCount3}</td>
	                      <td width="31%">${item.userName}</td>
	                      <td width="39%">${item.accuracyRate}%</td>
	                    </tr>		
                    </c:forEach> 
            </table>
            </li>
            <div class="clear"></div>
        </ul>
		</div><div class="clear"></div>
        </div></div></div></div></div>
  </div>
  <!--End left-->
  <div class="clear"></div>
</div>
<!--页面尾部-->
<jsp:include page="../exam/include_bottom.jsp"></jsp:include>
</body>
</html>
