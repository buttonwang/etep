<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="<%=request.getContextPath()%>/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>

<body>
<div class="nTab wm5">
  <!-- 标题开始 -->
  <div class="TabTitle">
    <ul id="myTab1">
      <li class="active">训练</li>
    </ul>
    <div class="user_text12">欢迎你，<span class="f14px fB">${userDataVO.userName}</span> 同学</div>
  </div>
 <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
      <div class="style1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="middle">
            <table width="100%" height="264" border="0" cellpadding="0" cellspacing="0" class="listab">
                  <tr>
                    <td valign="top" colspan="2"><c:if test="${showVO.sumCorrectItems+showVO.sumIncorrectItems>0}"></c:if>              
                    </td>
                  </tr>
                  <tr>
                    <td valign="top" height="25" colspan="2"><p class="f14px fB">“${nodeInstance.node.name}”分析统计：</p>
                      <p>共${practiceNum}卷  已训练题数：${showVO.itemsNum}   错题数：${showVO.sumIncorrectItems}   <br/>
已经进行${practiceDoNum}卷训练，${practicePassNum}卷已通过  成  绩：${showVO.totalScore}分  正确率：${showVO.accuracyRate}% 总掌握度：${showVO.masteryRate}% </p></td>
                  </tr> 
                  
                  <tr>
                    <td width="373" valign="top"><img src="getImage.jhtml?_zero=${showVO.sumZeroStarItems}&_half=${showVO.sumHalfStarItems}&_one=${showVO.sumOneStarItems}&_two=${showVO.sumTwoStarItems}&_three=${showVO.sumThreeStarItems}&_four=${showVO.sumFourStarItems}&_five=${showVO.sumFiveStarItems}&_new=${showVO.sumUnfinishedItems}" /><br /></td>
                    <td width="398" valign="middle"><table width="100%" border="0" cellpadding="0" cellspacing="0"  class="listab">
                      
                      <tr class=" whitetr">
                        <td align="center">“${nodeInstance.node.name}”知识点</td>
                        <td align="center">正确率</td>
                        <td align="center">掌握度</td>
                      </tr>
                      <c:forEach items="${reportShowVOList}" var="item" varStatus="itemStatus">
	                      <tr class=" whitetr">
	                        <td>${item.nodeName}</td>
	                        <td align="center">${item.accuracyRate}%</td>
	                        <td align="center">${item.masteryRate}% </td>
	                      </tr>
                      </c:forEach>
                    </table></td>
                  </tr>
                </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="listab">
                  <tr>
                    <td align="center" colspan="14">&nbsp;&nbsp;已训练单元分析（点击表中相应的题数</b></font>进行强化训练，点击单元名称查看单元详情）</td>
                  </tr>
                  <tr class=" whitetr">
                    <td width="13%" align="center">单元名称</td>
                    <td width="10%" align="center">首答正确率</td>
                    <td width="10%" align="center">目前正确率</td>
                    <td width="7%" align="center">掌握度</td>
                    <td width="7%" align="center">总题数</td>
                    <td width="7%" align="center">错题数</td>
                    <td width="8%" align="center">未答题数</font></td>
                    <td width="6%" align="center"><span class="cRed">★★★<br />
                    ★★</span></td>
                    <td width="8%" align="center"><span class="cRed">★★<br />
                    ★★</span></td>
                    <td width="7%" align="center"><span class="cRed">★★★</span></td>
                    <td width="5%" align="center"><span class="cRed">★★</span></td>
                    <td width="4%" align="center"><span class="cRed">★</span></td>
                    <td width="3%" align="center"><span class="cRed">☆</span></td>
                    <td width="5%" align="center"><img src="../images/smile.gif" width="20" height="20" /></td>
                  </tr>
                  <tr class=" whitetr">
                    <td>${nodeInstance.node.name}</td>
                    <td align="center">${showVO.firstTestAccuracyRate}%</td>
                    <td align="center">${showVO.accuracyRate}%</td>
                    <td align="center">${showVO.masteryRate}%</td>
                    <td align="center">${showVO.itemsNum}</td>
                    <td align="center">${showVO.sumIncorrectItems}</td>
                    <td align="center">${showVO.sumUnfinishedItems}</td>
                    <td align="center">${showVO.sumFiveStarItems}</td>
                    <td align="center">${showVO.sumFourStarItems}</td>
                    <td align="center">${showVO.sumThreeStarItems}</td>
                    <td align="center">${showVO.sumTwoStarItems}</td>
                    <td align="center">${showVO.sumOneStarItems}</td>
                    <td align="center">${showVO.sumHalfStarItems}</td>
                    <td align="center">${showVO.sumZeroStarItems}</td>                    
                  </tr>
                  <c:forEach items="${reportShowVOList}" var="item" varStatus="itemStatus">
                  	<tr class=" whitetr">
                    <td><a href="ad_stu_report.jhtml?nodeId=${item.nodeInstanceId}&userId=${userId}" target="_self">${item.nodeName}</a></td>
                    <td align="center">${item.firstTestAccuracyRate}%</td>
                    <td align="center">${item.accuracyRate}%</td>
                    <td align="center">${item.masteryRate}% </td>
                    <td align="center">
                    	${item.itemsNum}
                    </td>
                    <td align="center">
                    	${item.sumIncorrectItems}
                    </td>
                    <td align="center">
	                    ${item.sumUnfinishedItems}
                    </td>
                    <td align="center">
	                    ${item.sumFiveStarItems}
                    </td>
                    <td align="center">
	                    ${item.sumFourStarItems}
                    </td>
                    <td align="center">
	                    ${item.sumThreeStarItems}
                    </td>
                    <td align="center">
	                    ${item.sumTwoStarItems}
                    </td>
                    <td align="center">
	                    ${item.sumOneStarItems}
                    </td>
                    <td align="center">
	                    ${item.sumHalfStarItems}
                    </td>
                    <td align="center">
	                    ${item.sumZeroStarItems}
                    </td>
                  </tr>
                  </c:forEach>
              </table></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
function redo(scope,flowItemId){	
		<c:if test="${userDataVO.processStatus>0}">
			doit(document.getElementById('selectType'));
			doit(window.parent.document.getElementById('sign01'));
			doit(window.parent.document.getElementById('sign02'));
			window.parent.document.getElementById('sign02').focus();
			return ;
		</c:if>		
		top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=3&scope="+scope;	
}
</script>
</body>
</html>
