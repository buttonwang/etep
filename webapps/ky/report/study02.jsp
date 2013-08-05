<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="nTab wm5">
  <!-- 标题开始 -->
  <div class="TabTitle">
    <ul id="myTab1">
      <c:if test="${evaluate>-1}"><li class="normal"><a href="study.jhtml?showListType=3&nodeInstanceId=${nodeInstanceId}">评测</a></li></c:if>
      <c:if test="${phasetest>-1}"><li class="normal"><a href="study.jhtml?showListType=4&nodeInstanceId=${nodeInstanceId}">阶段测试</a></li></c:if>
      <c:if test="${practice>-1}"><li class="normal"><a href="study.jhtml?showListType=0&nodeInstanceId=${nodeInstanceId}">训练</a></li></c:if>
      <c:if test="${unittest>-1}"><li class="active" >单元测试</li></c:if>
    </ul>
    <div class="user_text12">欢迎你，<span class="f14px fB">${webuser.realName}</span> 同学</div>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content1">
      <div class="style1">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="232">
          
          <tr>
            <td valign="top"></td>
          </tr>
          <tr>
            <td valign="top"><strong>测试情况</strong>（点击<font color="#3366cc"><b>测试名称</b></font>查看测试详情）：
              <table width="100%" cellpadding="3" cellspacing="0"  class="listab">
                  <tr class=" whitetr">
                    <td align="center">测试名称</td>
                    <td align="center">总题数</td>
                    <td align="center">总分</td>
                    <td align="center">正确率
                      要求</td>
                    <td align="center">规定用时(分钟)</td>
                    <td align="center">状态</td>
                    <td align="center">首答
                      成绩</td>
                    <td align="center">当前
                      成绩</td>
                    <td align="center">掌握度</td>
                    <td align="center">星级数</td>
                    <td align="center">最后测试时间</td>
                  </tr>
                  <c:forEach items="${reportShowVOList}" var="item" varStatus="itemStatus">
	                  <tr class="whitetr">
	                    <td><a href="#" onclick="javascript:top.location.href='reportMain.jhtml?nodeInstanceId=${item.nodeInstanceId}';">${item.nodeName}</a></td>
	                    <td align="center">${item.itemsNum}</td>
	                    <td align="center">${item.totalScore}</td>
	                    <td align="center">${item.rightRateForPass}%</td>
	                    <td align="center">${item.answeringTime}</td>
	                    <td align="center">
	                     <c:if test="${item.nodeStatus==1}"><span class="cRed">未通过</span></c:if>
	                     <c:if test="${item.nodeStatus==2}"><font color="#009900">通过</font></c:if>
	                    </td>
	                    <td align="center">${item.firstTestScore}</td>
	                    <td align="center">${item.score}</td>
	                    <td align="center">${item.masteryRate}% </td>
	                    <td align="center">${item.starNum}</td>
	                    <td align="center">${item.endTime}</td>
	                  </tr>
                  </c:forEach>
                  
                  <tr class="whitetr">
                    <td>总体/平均</td>
                    <td align="center">${showVO.itemsNum}</td>
                    <td align="center">${showVO.totalScore}</td>
                    <td align="center">--</td>
                    <td align="center">--</td>
                    <td align="center">--</td>
                    <td align="center"> ${showVO.firstTestScore}</td>
                    <td align="center"> ${showVO.score}</td>
                    <td align="center"> ${showVO.masteryRate}%</td>
                    <td align="center">${showVO.starNum}</td>
                    <td align="center">--</td>
                  </tr>
              </table></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>

