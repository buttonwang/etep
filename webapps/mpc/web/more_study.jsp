<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>全部学习任务</title>
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
        <div class="task_top">
        	<ul>            	
                <c:forEach items="${groupNodeStatList}" var="groupNode" varStatus="status">                  
                    <li>
                	<h1><a href="studyTask.jhtml?groupNodeId=${groupNode.id}">${groupNode.name}</a></h1>
                	<div><label style="width:${groupNode.userProgressRate}%;">${groupNode.userProgressRate}%</label></div>
                	</li>
                  </c:forEach>                
            </ul>
            <div class="clear"></div>
        </div>
        
        <c:forEach items="${nodeListOfGroupNodeList}" var="node1" varStatus="NodeStatus">
	        <div class="task_con bg">
	        	<div class="task_title"><span>${node1.nodeName}</span>
	        </div>
	        <table width="908" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td width="295">节名称</td>
                <td width="145">学习任务名称</td>
                <td width="97">试题数量</td>
                <td width="121">正确率要求</td>
                <td width="145">限时（分钟）</td>
                <td width="98">是否通过</td>
              </tr>
        	<c:forEach items="${node1.nodeVOList}" var="node2" varStatus="NodeStatus">
        		<tr>
        		<c:if test="${node2.rowNum>0}">
                	<td rowspan="${node2.rowNum}">${node2.nodeGroupName}</td>
                </c:if>
                <td>${node2.nodeName}</td>
                <td>${node2.itemsNum}</td>
                <td> 
	                 <c:choose>
	                    	<c:when test="${node2.nodeType == 'PHASETEST'}">--</c:when>	                    	
	                    	<c:when test="${node2.nodeType == 'EVALUATE'}">--</c:when>
		                 	<c:otherwise> 
	     						<c:if test="${node2.rightRateForPass eq null || node2.rightRateForPass eq 0}">--</c:if>	                    	
	                    		<c:if test="${node2.rightRateForPass > 0}">${node2.rightRateForPass}%</c:if>
	    					</c:otherwise>
	                  </c:choose>
	            </td>
                <td>
					${node2.answeringTime}
                </td>
                <td>
					<c:if test="${node2.nodeStatus==1}"><span class="cRed">未通过</span></c:if>
	                <c:if test="${node2.nodeStatus==2}"><font color="#009900">通过</font></c:if>
	                <c:if test="${node2.nodeStatus==0}"><font color="#009900">--</font></c:if>
	                <c:if test="${node2.nodeStatus==3}"><font color="#009900">通过</font></c:if>
				</td>
              </tr>
        	</c:forEach>
        	 </table>
        	 </div>
        </c:forEach>
        <div class="clear"></div>
        </div></div></div></div></div>
  </div>
  <!--End left-->
  <div class="clear"></div>
</div>
<!--页面尾部-->
<jsp:include page="../exam/include_bottom.jsp"></jsp:include>
</body>
</html>
