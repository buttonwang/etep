<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="nTab wm5">
  <!-- 标题开始 -->
  <div class="TabTitle">
    <ul id="myTab1">
      <c:if test="${evaluate>-1}"><li class="active">评测</li></c:if>
      <c:if test="${phasetest>-1}"><li class="normal"><a href="study.jhtml?showListType=4&nodeInstanceId=${nodeInstanceId}">阶段测试</li></a></c:if>
      <c:if test="${practice>-1}"><li class="normal"><a href="study.jhtml?showListType=0&nodeInstanceId=${nodeInstanceId}">训练</a></li></c:if>
      <c:if test="${unittest>-1}"><li class="normal" ><a href="study.jhtml?showListType=1&nodeInstanceId=${nodeInstanceId}">单元测试</a></li></c:if>
    </ul>
    <div class="user_text12">欢迎你，<span class="f14px fB">${webuser.realName}</span> 同学</div>
  </div>
  <!-- 内容开始 -->
   <div class="TabContent">
    <div id="myTab1_Content0">
      <div class="style1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="middle"><p><font color="#3366cc"><b>已做评测分析</b></font><br />
            </p> 
		      <c:forEach items="${evaluateGroupShowVOList}" var="item" varStatus="itemStatus">          
			     <table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="listab" >
	                 <tr>
	                   <td colspan="7" align="center" valign="top"><strong>${item.nodeGroupName}</strong></td>
	                 </tr>
	                 <tr valign="middle">
	                    <td width="30%" height="32" align="center"  bgcolor="#EEEEEE" rowspan="2">评测项</td>
	                    <td width="35%" align="center" valign="top" bgcolor="#EEEEEE" colspan="3">学前评测</td>
	                    <td width="35%" align="center" valign="top" bgcolor="#EEEEEE" colspan="3">学后评测</td>
	                 </tr>
	                  <tr>
	                    <td align="center" valign="top" bgcolor="#EEEEEE">分值</td>
	                    <td align="center" valign="top" bgcolor="#EEEEEE">得分</td>
	                    <td align="center" valign="top" bgcolor="#EEEEEE">正确率</td>
	                    <td align="center" valign="top" bgcolor="#EEEEEE">分值</td>
	                    <td align="center" valign="top" bgcolor="#EEEEEE">得分</td>
	                    <td align="center" valign="top" bgcolor="#EEEEEE">正确率</td>
	                 </tr>
	                  <c:forEach items="${item.knowledgePointList}" var="knowledge" varStatus="itemStatus">
		                 <tr class=" whitetr">
		                    <td align="center" valign="top">${knowledge.knowledgePointName}</td>
		                    <td align="center" valign="top">
			                    <c:if test="${knowledge.totalScore>-1}">${knowledge.totalScore}</c:if>
		                    	<c:if test="${knowledge.totalScore==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${knowledge.score>-1}">${knowledge.score}</c:if>
		                    	<c:if test="${knowledge.score==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${knowledge.accuracyRate>-1}">${knowledge.accuracyRate}%</c:if>
		                    	<c:if test="${knowledge.accuracyRate==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${knowledge.totalScoreTwo>-1}">${knowledge.totalScoreTwo}</c:if>
		                    	<c:if test="${knowledge.totalScoreTwo==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${knowledge.scoreTwo>-1}">${knowledge.scoreTwo}</c:if>
		                    	<c:if test="${knowledge.scoreTwo==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${knowledge.accuracyRateTwo>-1}">${knowledge.accuracyRateTwo}%</c:if>
		                    	<c:if test="${knowledge.accuracyRateTwo==-1}">--</c:if>
		                    </td>
			             </tr>
	                  </c:forEach>	                  	                  
	                  <tr class=" whitetr">
	                    <td align="center" valign="top">总计</td>
	                    <td align="center" valign="top">
			                    <c:if test="${item.totalInfo.totalScore>-1}">${item.totalInfo.totalScore}</c:if>
		                    	<c:if test="${item.totalInfo.totalScore==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${item.totalInfo.score>-1}">${item.totalInfo.score}</c:if>
		                    	<c:if test="${item.totalInfo.score==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${item.totalInfo.accuracyRate>-1}">${item.totalInfo.accuracyRate}%</c:if>
		                    	<c:if test="${item.totalInfo.accuracyRate==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${item.totalInfo.totalScoreTwo>-1}">${item.totalInfo.totalScoreTwo}</c:if>
		                    	<c:if test="${item.totalInfo.totalScoreTwo==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${item.totalInfo.scoreTwo>-1}">${item.totalInfo.scoreTwo}</c:if>
		                    	<c:if test="${item.totalInfo.scoreTwo==-1}">--</c:if>
		                    </td>
		                    <td align="center" valign="top">
			                    <c:if test="${item.totalInfo.accuracyRateTwo>-1}">${item.totalInfo.accuracyRateTwo}%</c:if>
		                    	<c:if test="${item.totalInfo.accuracyRateTwo==-1}">--</c:if>
		                    </td>
	                  </tr>
	              </table>     
		      </c:forEach>
          </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
