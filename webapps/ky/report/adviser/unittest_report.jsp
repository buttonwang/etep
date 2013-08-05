<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="<%=request.getContextPath()%>/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="nTab wm5">
  <!-- 标题开始 -->
  <div class="TabTitle">
    <ul id="myTab1">
      <li class="active">测试报告</li>
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
              <td valign="middle">
			<c:if test="${currentTestStatus==null}">
			<b><font size="3" color="#3366cc"> “${nodeInstance.node.name}”测试报告   </font></b>
            <br />
             <br />
                <p>
                因阶段测试成绩优秀，此测试卷被跳过。 </p>
                <!-- ，现在你可随时测试本卷
              <div class="bnt_xing left"><span class="cRed f14px fB"><a href="../exam/goExam.jhtml?nodeInstanceId=${nodeInstanceId}" target="_parent">开始测试本卷</a></span></div>
                 -->
                <div class="clear"></div>
            </c:if>
            <c:if test="${currentTestStatus!=null}">
            <p><b><font size="3" color="#3366cc">${nodeInstance.node.name}报告  </font></b>
              </p>
              </c:if>
              <table width="100%" border="0" cellspacing="0" cellpadding="0" height="89" class="listab">
                  <tr>
                        <td width="17%" align="right"><strong>总题数：</strong></td>
                        <td width="15%">${paperAssemblingPolicy.items_num}</td>
                        <td width="23%" align="right"><strong>首答时间/成绩/正确率：</strong></td>
                        <td width="24%"><c:if test="${currentTestStatus!=null}">${currentTestStatus.firstTestTime}(&nbsp;${currentTestStatus.firstTestScore}分;&nbsp;${currentTestStatus.firstTestAccuracyRate}% )</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                        <td width="15%" align="right"><strong>掌握度：</strong></td>
                        <td width="6%"><c:if test="${currentTestStatus!=null}">${currentTestStatus.masteryRate}%</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                      </tr>
                      <tr>
                        <td align="right"><strong>试卷分值：</strong></td>
                        <td>${paperAssemblingPolicy.totalScore}</td>
                        <td align="right"><strong>通过时间/成绩/正确率：</strong></td>
                        <td><c:if test="${currentTestStatus!=null}">${currentTestStatus.passedTime}(&nbsp;${currentTestStatus.passedScore}分; ${currentTestStatus.passedAccuracyRate}% )</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                        <td align="right"><strong>错题数：</strong></td>
                        <td><c:if test="${currentTestStatus!=null}">${currentTestStatus.sumIncorrectItems}</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                      </tr>
                      <tr>
                        <td align="right"><strong>标准时间：</strong></td>
                        <td><c:if test="${currentTestStatus!=null}">${answeringTime}</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                        <td align="right"><strong>目前成绩/正确率：</strong></td>
                        <td><c:if test="${currentTestStatus!=null}">${currentTestStatus.score}分;&nbsp;${currentTestStatus.accuracyRate}%</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                        <td align="right"><strong>未答题数：</strong></td>
                        <td><c:if test="${currentTestStatus!=null}">${currentTestStatus.sumUnfinishedItems}</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                      </tr>
                      <tr>
                        <td align="right"><strong>正确率要求：</strong></td>
                        <td>${rightRateForPass}%</td>
                        <td align="right"><strong>本卷训练总用时：</strong></td>
                        <td><c:if test="${currentTestStatus!=null}">${timeUsed}</c:if><c:if test="${currentTestStatus==null}">--</c:if></td>
                        <td align="right"><strong>训练状态：</strong></td>
                        <td>
                        	<c:if test="${nodeStatus==1}"><span class="cRed">未通过</span></c:if>
	                    	<c:if test="${nodeStatus==2}"><font color="#009900">通过</font></c:if>
	                    	<c:if test="${nodeStatus==0}"><font color="#009900">未作</font></c:if>
	                    	<c:if test="${nodeStatus==3}"><font color="#009900">通过</font></c:if>
						</td>
                      </tr>
                </table>
                <c:if test="${currentTestStatus!=null}">
              <table width="100%" height="264" border="0" cellpadding="0" cellspacing="0" class="listab">
                  <tr>
                    <td valign="top" height="25" colspan="2"><span  class="f14px">题目统计：</span><br/></td>
                  </tr>
                  <tr>
                    <td width="402" valign="top"><table cellpadding="3" cellspacing="0"  class="listab" width="173">
                      <tr>
                        <td align="right" width="86"><strong>总题数</strong></td>
                        <td width="69">${paperAssemblingPolicy.items_num} </td>
                      </tr>
                      <tr>
                        <td align="right" width="86"><strong>未训练题数</strong></td>
                        <td width="69">${currentTestStatus.sumUnfinishedItems}</td>
                      </tr>
                      <tr>
                        <td align="right" width="86"><span class="cRed">★★★★★</span></td>
                        <td width="69">${currentTestStatus.sumFiveStarItems}</td>
                      </tr>
                      <tr>
                        <td align="right" width="86"><span class="cRed">★★★★</span></td>
                        <td width="69">${currentTestStatus.sumFourStarItems}</td>
                      </tr>
                      <tr>
                        <td align="right" width="86"><span class="cRed">★★★</span></td>
                        <td width="69">${currentTestStatus.sumThreeStarItems}</td>
                      </tr>
                      <tr>
                        <td align="right" width="86"><span class="cRed">★★</span></td>
                        <td width="69">${currentTestStatus.sumTwoStarItems}</td>
                      </tr>
                      
                    </table></td>
                    <td width="369"><img src="getImage.jhtml?_zero=${currentTestStatus.sumZeroStarItems}&_half=${currentTestStatus.sumHalfStarItems}&_one=${currentTestStatus.sumOneStarItems}&_two=${currentTestStatus.sumTwoStarItems}&_three=${currentTestStatus.sumThreeStarItems}&_four=${currentTestStatus.sumFourStarItems}&_five=${currentTestStatus.sumFiveStarItems}" /></td>
                  </tr>
              </table>
              </c:if>
              </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
