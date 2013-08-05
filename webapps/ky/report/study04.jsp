<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/common.js"></script>
</head>

<body>
<div class="nTab wm5">
  <!-- 标题开始 -->
  <div class="TabTitle">
    <ul id="myTab1">
      <li class="active">训练报告</li>
     <c:if test="${currentTestStatus!=null}"> <li class="normal" ><a href="study.jhtml?nodeInstanceId=${nodeInstanceId}&showListType=2">回顾复习</a></li></c:if>
    </ul>
    <div class="user_text12">欢迎你，<span class="f14px fB">${webuser.realName}</span> 同学</div>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
      <div class="style1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="middle">
            <c:if test="${currentTestStatus==null}">
            <b><font size="3" color="#3366cc"> “${nodeInstance.node.name}”综合训练报告   </font></b>
            <br />
             <br />
                <p>
                因阶段测试成绩优秀，此测试卷被跳过，现在你可随时测试本卷。 </p>
              <div class="bnt_xing left"><span class="cRed f14px fB"><a href="#" onclick="redo('',${nodeInstanceId});">开始测试本卷</a></span></div>
                <div class="clear"></div>
                </c:if> 
             <c:if test="${currentTestStatus!=null}">                 
            <p><b><font  color="#3366cc"><span class="f14px">综合训练报告</span></font></b>（${nodeInstance.node.name}）<br/>
            </p>
            </c:if>
                <table width="100%" height="264" border="0" cellpadding="0" cellspacing="0" class="listab">
                  <tr>
                    <td valign="top" height="25" colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                      <tr>
                        <td width="17%" align="right"><strong>总题数：</strong></td>
                        <td width="15%">${itemNum}</td>
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
					</td>
                  </tr>
                   <c:if test="${currentTestStatus!=null}">
                  <tr>
                    <td width="373" valign="top">
                    <div class="bnt_xing"><span class="cRed">★★★★★</span>&nbsp;&nbsp;${currentTestStatus.sumFiveStarItems} &nbsp;&nbsp;<c:if test="${currentTestStatus.sumFiveStarItems>0}"><a href="javascript:redo(5,${nodeInstanceId});" target="_parent" class="f14px fb">重练</a></c:if></div>
					<div class="bnt_xing"><span class="cRed">★★★★</span>&nbsp;&nbsp;${currentTestStatus.sumFourStarItems} &nbsp;&nbsp;<c:if test="${currentTestStatus.sumFourStarItems>0}"><a href="javascript:redo(4,${nodeInstanceId});" target="_parent" class="f14px fb">重练</a></c:if></div>
            		<div class="bnt_xing"><span class="cRed">★★★</span>&nbsp;&nbsp;${currentTestStatus.sumThreeStarItems} &nbsp;&nbsp;<c:if test="${currentTestStatus.sumThreeStarItems>0}"><a href="javascript:redo(3,${nodeInstanceId});" target="_parent" class="f14px fb">重练</a></c:if></div>
                    <c:if test="${currentTestStatus.sumCorrectItems+currentTestStatus.sumIncorrectItems>0}">
                    <div class="bnt_xing2">                    
                       <select name="select" id="selectType">
                       <c:if test="${currentTestStatus.sumTwoStarItems>0}"><option value="2">★★题</option></c:if>
                        <c:if test="${currentTestStatus.sumOneStarItems>0}"><option value="1">★题</option></c:if>
                        <c:if test="${currentTestStatus.sumHalfStarItems>0}"><option value="0.5">☆题</option></c:if>
                        <c:if test="${currentTestStatus.sumCorrectItems>0}"><option value="14">做对题</option></c:if>
                        <c:if test="${currentTestStatus.sumIncorrectItems>0}"><option value="12">错题</option></c:if>
                        <c:if test="${currentTestStatus.sumUnfinishedItems>0}"><option value="11">未答题</option></c:if>
                        <c:if test="${currentTestStatus.unsureMarkItems>0}"><option value="15">疑问题</option></c:if>
                        <option value="-1">全部题</option>
                    </select>&nbsp;&nbsp;<span onmouseover="this.className='f14px fb like_a_hover';" onmouseout="this.className='f14px fb like_a';" onclick="javascript:redo(document.getElementById('selectType').value,${nodeInstanceId});" class="f14px fb like_a">重练</span> 
                    </div> 
                    </c:if>           		
                      </td>
                    <td width="398" valign="middle"><img src="getImage.jhtml?_zero=${currentTestStatus.sumZeroStarItems}&_half=${currentTestStatus.sumHalfStarItems}&_one=${currentTestStatus.sumOneStarItems}&_two=${currentTestStatus.sumTwoStarItems}&_three=${currentTestStatus.sumThreeStarItems}&_four=${currentTestStatus.sumFourStarItems}&_five=${currentTestStatus.sumFiveStarItems}" /></td>
                  </tr>
                  </c:if>
                </table>
                <c:if test="${currentTestStatus!=null}">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" height="162">
                  <tr>
                    <td valign="top"></td>
                  </tr>
                  <tr>
                    <td height="162" valign="top"><p><b><font  color="#3366cc"><span class="f14px">历史训练报告</span></font></b>
                      （${nodeInstance.node.name}）
                <table width="100%" cellpadding="3" cellspacing="0"  class="listab">
                          <tr class=" whitetr">
                            <td width="13%" align="center">历次训练</td>		                    
		                    <td width="10%" align="center">正确率</td>
		                    <td width="7%" align="center">掌握度</td>		                    
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
		                    <td width="5%" align="center"><img src="../images/smile.gif" width="20" height="20" />
                          </tr>
                       <c:forEach items="${reportShowVOList}" var="item" varStatus="itemStatus">
		                  <tr class=" whitetr">
		                    <td>第${item.times}次</td>		                   
		                    <td align="center">${item.accuracyRate}%</td>
		                    <td align="center">${item.masteryRate}% </td>		                    
		                    <td align="center">${item.sumIncorrectItems}</td>
		                    <td align="center">${item.sumUnfinishedItems}</td>
		                    <td align="center">${item.sumFiveStarItems}</td>
		                    <td align="center">${item.sumFourStarItems}</td>
		                    <td align="center">${item.sumThreeStarItems}</td>
		                    <td align="center">${item.sumTwoStarItems}</td>
		                    <td align="center">${item.sumOneStarItems}</td>
		                    <td align="center">${item.sumHalfStarItems}</td>
		                    <td align="center">${item.sumZeroStarItems}</td>
		                  </tr>
	                  </c:forEach>
                      </table></td>
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
<script type="text/javascript">
function redo(scope,flowItemId){
		<c:if test="${userDataVO.processStatus>0}">		
			if(document.getElementById('selectType')!=null)
				doit(document.getElementById('selectType'));
			doit(window.parent.document.getElementById('sign01'));
			doit(window.parent.document.getElementById('sign02'));
			window.parent.document.getElementById('sign02').focus();
			return ;
		</c:if>
		if(document.getElementById('selectType')!=null)
			top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=3&scope="+scope;
		else 
			top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId;	
}
</script>
</body>
</html>
