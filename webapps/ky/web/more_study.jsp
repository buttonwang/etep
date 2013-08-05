<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<!--页面头部-->
<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
  <div class="toplin">
    <div class="toplin_l"><img src="../images/toplin_l.jpg" /></div>
    <div class="toplin_c">
      <ul>
        <li><a href="mainPage!ky.jhtml">首页</a></li>
        <li class="divli">|</li>
        <li> <a href="#">在线咨询</a></li>
        <li class="divli">|</li>
        <li><a href="#">帮助</a></li>
        <li class="divli">|</li>
        <li><a href="#" onclick="if(confirm('是否确认退出系统?')){location.href='../../logout.jsp';}">退出系统</a></li>
      </ul>
    </div>
    <div class="toplin_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="user_texts ">欢迎你，<span class="f14px fB">${userDataVO.userName}</span> 同学</div>
  <div class="clear"></div>
</div>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2" >
	<div class="nTab">
        	<!-- 标题开始 -->
   	  <!-- 内容开始 -->
  <div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" height="232" >
            <tr>
              <td height="47" valign="middle">
              	<table width="100%" border="0" cellspacing="0" cellpadding="4">
                <tr>
                  <c:forEach items="${groupNodeStatList}" var="groupNode" varStatus="status">
                  	<td align="center" ${groupNode.id eq groupNodeId?'class="f14px fB"':''}>
	                  	<a href="studyTask.jhtml?groupNodeId=${groupNode.id}">${groupNode.name}</a>
	                    <div class="jindu_w">
	                    	<div class="jindu" style="width:${groupNode.userProgressRate}%;">${groupNode.userProgressRate}%</div>
	                    </div>
                    </td>
                  </c:forEach>
                </tr>
                </table>
              </td>
            </tr>
            
            <tr>
              <td valign="top">&nbsp;</td>
            </tr>
            <tr>
              <td valign="top">
              <table width="100%" cellpadding="3" cellspacing="0"  class="listab">
                  <tr class="whitetr">
                    <td colspan="2" align="center" bgcolor="#e8f1ff">训练名称/测试名称</td>
                    <td width="136" align="center" bgcolor="#e8f1ff">标准题目数量（道）</td>
                    <td width="138" align="center" bgcolor="#e8f1ff">正确率要求（百分比）</td>
                    <td width="118" align="center" bgcolor="#e8f1ff">做题限时（分钟）</td>
                    <td width="152" align="center" bgcolor="#e8f1ff">是否通过</td>
                  </tr>
                  <c:forEach items="${nodeListOfGroupNodeList}" var="node" varStatus="NodeStatus">
	                  <tr class="whitetr">
	                    <c:choose>
	                    	<c:when test="${node.nodeType == 'PHASETEST'}"><c:set var="color" value="ora"></c:set></c:when>
	                    	<c:when test="${node.nodeType == 'UNITTEST'}"><c:set  var="color" value="yellow"></c:set></c:when>
	                    	<c:when test="${node.nodeType == 'EVALUATE'}"><c:set  var="color" value="red"></c:set></c:when>
	                    	<c:when test="${node.nodeType == 'PRACTICE'}"><c:set  var="color" value="green"></c:set></c:when>
	                    </c:choose>
	                    <td width="22" align="right">
	                    	<img src="../images/bnt_${color}_s.gif" width="14" height="14" border="0" align="absmiddle" />
	                    </td>
	                    <td width="234">
	                   		<c:if test="${node.nodeStatus>0}">
		                    <a href="../report/reportMain.jhtml?nodeInstanceId=${node.nodeInstanceId}">
		                   </c:if>
		                    ${node.nodeName}
		                 <c:if test="${node.nodeStatus>0}">
		                    </a>
		                    </c:if>
	                    </td>
	                    <td align="center" width="136">${node.itemsNum}</td>
	                    <td align="center" width="138">
	                     <c:choose>
	                    	<c:when test="${node.nodeType == 'PHASETEST'}">--</c:when>	                    	
	                    	<c:when test="${node.nodeType == 'EVALUATE'}">--</c:when>
		                 	<c:otherwise> 
	     						<c:if test="${node.rightRateForPass eq null || node.rightRateForPass eq 0}">--</c:if>	                    	
	                    		<c:if test="${node.rightRateForPass > 0}">${node.rightRateForPass}%</c:if>
	    					</c:otherwise>
	                    </c:choose>
	                    </td>
	                    <td align="center" width="118">	                    
	                    	<c:if test="${node.answeringTime eq null || node.answeringTime eq 0}">--</c:if>	                    	
	                    	<c:if test="${node.answeringTime > 0}">${node.answeringTime}</c:if>
	                    </td>                    
	                    <td align="center" width="152">
	                    	<c:if test="${node.nodeStatus==1}"><span class="cRed">未通过</span></c:if>
	                    	<c:if test="${node.nodeStatus==2}"><font color="#009900">通过</font></c:if>
	                    	<c:if test="${node.nodeStatus==0}"><font color="#009900">--</font></c:if>
	                    	<c:if test="${node.nodeStatus==3}"><font color="#009900">通过</font></c:if>
	                    </td>
	                  </tr>
                  </c:forEach>
              </table>
              </td>
            </tr>
          </table>
        </div>
        </div>
    </div>
    </div>
  <div class="clear"> </div>
</div>

<div class="bonnt wm1">© 2008年  安博教育集团  版权所有</div>
</body>
</html>