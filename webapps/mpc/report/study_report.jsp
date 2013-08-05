<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
	String ctx = (String)request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="active" ><a class="cBlack" href="consolidate!training.jhtml?orderNum=${orderNum}&nodeType=${nodeType}"><c:if test="${nodeType =='PHASETEST'}">前测</c:if><c:if test="${nodeType =='PRACTICE'}">训练</c:if><c:if test="${nodeType =='UNITTEST'}">后测</c:if>报告</a></li>
      <li class="normal" ><a class="cBlack" href="mpcstudy!review.jhtml?nodeInstanceId=${nodeInstanceMap['id']}" target="main" >回顾复习</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til"><span>“${nodeInstance.node.name}”</span>综合训练报告：</h1>
        <div class="blankW_9"></div>
        <div class="bg box1 padding10px star1">
          <div class="bg_left">
          <h4>
		  <c:if test="${passMap['passed_time'] != null}">
		  <span>恭喜你，已于<fmt:formatDate pattern="yyyy年MM月dd日" value="${passMap['passed_time']}" />通过本训练！</span>
		  </c:if>
		  <span class="bbs1"><label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"><a class=" cWhite" href="mpcstudy!review.jhtml?nodeInstanceId=${nodeInstanceMap['id']}" target="main" >回顾复习</a></label></span></h4>
        <div class=" blank6"></div>
        <div class="bg">
        <h1 class="fB">概况总览</h1>
        <table width="370" border="0" cellspacing="1">
          <tr>
            <td>次数</td>
            <td>用时</td>
			<c:if test="${nodeType =='PRACTICE'}">
            <td>正确率要求</td>
			</c:if>
            <td>总正确率</td>
            <td>总掌握度</td>
          </tr>
          <tr>
            <td>${statHistoryMap['amount']}</td>
            <td>${statHistoryMap['time_used']}</td>
			<c:if test="${nodeType =='PRACTICE'}">
            <td>${accuracyRate}%</td>
			</c:if>
            <td>
            <c:choose> 
	            <c:when test="${passMap['accuracy_rate'] eq 0.0}"> 
				--
				</c:when>
				<c:otherwise>
				${passMap['accuracy_rate']}%
				</c:otherwise>
			</c:choose>
            </td>
            <td>
            <c:choose> 
	            <c:when test="${passMap['mastery_rate'] eq 0.0}"> 
				--
				</c:when>
				<c:otherwise>
				${passMap['mastery_rate']}%
				</c:otherwise>
			</c:choose>
			</td>
          </tr>
        </table>
        </div>
        </div>
 	<div class="bg_right"><img src="../../ky/report/getImage.jhtml?_zero=${currentTestStatus.sumZeroStarItems}&_half=${currentTestStatus.sumHalfStarItems}&_one=${currentTestStatus.sumOneStarItems}&_two=${currentTestStatus.sumTwoStarItems}&_three=${currentTestStatus.sumThreeStarItems}&_four=${currentTestStatus.sumFourStarItems}&_five=${currentTestStatus.sumFiveStarItems}&_type=mpc" width="300" height="179" /><h1>星级题分布饼图</h1>
     </div> 
    <div class="clear"></div>
 </div>
 <div class="blankW_9"></div>
 		<div class="bg">
        	<div class="con_right_content fB box1" >题目数、星级题统计</div>
        	<table width="698" border="0" cellspacing="1">
        	  <tr>
        	    <td width="51">已训练</td>
        	    <td width="50">做对</td>
        	    <td width="50">做错</td>
        	    <td width="50">未答</td>
        	    <td width="50">疑问</td>
        	    <td width="85"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="68"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="51"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="50"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="50"><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="50"><img src="../images/star_m_k.gif" width="12" height="12" /></td>
        	    <td width="56"><img src="../images/smile_m.gif" width="10" height="10" /></td>
      	    </tr>
        	  <tr>
        	    <td>${currentTestStatus.sumCorrectItems+currentTestStatus.sumIncorrectItems}</td>
        	    <td>${currentTestStatus.sumCorrectItems}</td>
        	    <td>${currentTestStatus.sumIncorrectItems}</td>
        	    <td>${currentTestStatus.sumUnfinishedItems}</td>
        	    <td>${currentTestStatus.unsureMarkItems}</td>
        	    <td>${currentTestStatus.sumFiveStarItems}</td>
        	    <td>${currentTestStatus.sumFourStarItems}</td>
        	    <td>${currentTestStatus.sumThreeStarItems}</td>
        	    <td>${currentTestStatus.sumTwoStarItems}</td>
        	    <td>${currentTestStatus.sumOneStarItems}</td>
        	    <td>${currentTestStatus.sumHalfStarItems}</td>
        	    <td>${currentTestStatus.sumZeroStarItems}</td>
      	    </tr>
      	  </table>
 		</div>
         <div class="blankW_9"></div>
        <div class="bg">
        	<div class="con_right_content fB box1" >历次训练数据（点击次数查看详情）</div>
            <table width="698" border="0" cellspacing="1">
  <tr>
    <td>训练次数</td>
    <td>题数</td>
    <td>做对</td>
    <td>做错</td>
    <td>未答</td>
    <td>疑问</td>
	<c:if test="${nodeType =='PRACTICE'}">
    <td>当次正确率要求</td>
	</c:if>
    <td>
      当次正确率</td>
    <td>当次掌握度</td>
    </tr>
  <c:forEach items="${historyList}" var="history" varStatus="historyStatus"> 
  <tr>
    <td><a href="mpcstudy!review.jhtml?nodeInstanceId=${nodeInstanceMap['id']}&hisId=${history['id']}&num=${historyStatus.count}" target="main" >第${historyStatus.count}次</a></td>
    <td>${history['sum_correct_items']+history['sum_incorrect_items']}</td>
    <td>${history['sum_correct_items']}</td>
    <td>${history['sum_incorrect_items']}</td>
    <td>${history['sum_unfinished_items']}</td>
    <td>${history['unsure_mark_items']}</td>
	<c:if test="${nodeType =='PRACTICE'}">
	<c:if test="${history['test_status'] ==1 || history['test_status'] ==2}">
    <td>${history['require_accuracy_rate']}%</td>
	</c:if>
	<c:if test="${history['test_status'] ==3 || history['test_status'] ==4}">
    <td>--</td>
	</c:if>
	</c:if>
    <td>${history['accuracy_rate']}%</td>
    <td>${history['mastery_rate']}%</td>
    </tr>
  </c:forEach>
</table>
 </div>

        </div>
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
