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
      <c:if test="${evaluate>-1}"><li class="normal"><a href="study.jhtml?showListType=3&nodeInstanceId=${nodeInstanceId}">评测</a></li></c:if>
      <c:if test="${phasetest>-1}"><li class="active">阶段测试</li></c:if>
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
            <td valign="middle"><p><font color="#3366cc"><b>已做阶段测试分析</b></font><br />
            </p>
          <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" class="listab">
                  
                  <tr>
                    <td height="32" align="center" valign="top" bgcolor="#EEEEEE">测试名称 </td>
                    <td align="center" valign="top" bgcolor="#EEEEEE">总题数</td>
                    <td align="center" valign="top" bgcolor="#EEEEEE">总分</td>
                    <td align="center" valign="top" bgcolor="#EEEEEE">规定用时
（分钟）
</td>
                    <td align="center" valign="top" bgcolor="#EEEEEE">成绩</td>
                    <td align="center" valign="top" bgcolor="#EEEEEE">正确率</td>
                    <td align="center" valign="top" bgcolor="#EEEEEE">测试时间</td>
                  </tr>
                   <c:forEach items="${reportShowVOList}" var="item" varStatus="itemStatus">
	                  <tr class=" whitetr">
	                    <td align="center" valign="top">${item.nodeName}</td>
	                    <td align="center" valign="top">${item.itemsNum}</td>
	                    <td align="center" valign="top">${item.totalScore}</td>
	                    <td align="center" valign="top">${item.answeringTime}</td>
	                    <td align="center" valign="top">${item.score}</td>
	                    <td align="center" valign="top">${item.accuracyRate}%</td>
	                    <td align="center" valign="top">${item.endTime}</td>
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
</body>
</html>
