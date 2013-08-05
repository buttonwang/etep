<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
    <div id="historyPoints">    
    	<a name="topHistoryPoints"></a>
        <div class="TabTitle" id="ceshi">
            <ul>
              <li class="active" ><a class="cBlack" href="javascript:void(null)">积分历史</a></li>
            </ul>
        </div>
        <div class="task_con bg">
		<div class=" blankW_6"></div>
		<div id="historyPage">
          <table width="908" border="0" cellspacing="1" >
            <tr>
              <td align="center"><strong>积分时间</strong></td>
              <td align="center"><strong>积分原因</strong></td>
              <td align="center"><strong>勤奋</strong></td>
              <td align="center"><strong>智慧</strong></td>
              <!-- td align="center"><strong>勇气</strong></td-->
              <td align="center"><strong>奉献</strong></td>
              <td align="center"><strong>洞察</strong></td>
            </tr>
            
            <c:forEach var="item" items="${historyPoints}">
            	<tr>
            		<td>${item["timestr"] }</td>
            		<td>${item["cause"] }</td>
            		<td>${item["point1"] }</td>
            		<td>${item['point2'] }</td>
            		<!-- td>${item['point3']}</td-->
            		<td>${item['point4'] }</td>
            		<td>${item['point5'] }</td>
            	</tr>
            </c:forEach>
          </table>
          <div class=" blankW_6"></div>
          <div class="blank6"></div>
          <jsp:include page="../include_pagination.jsp"></jsp:include>
 		</div>   
		</div>
	</div>	
</body>
</html>