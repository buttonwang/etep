<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="javascript1.2">
function redo(scope,flowItemId){	
	
		top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=3&scope="+scope;	
}
</script>
</head>
<body>

<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="normal" ><a class="cBlack" href="consolidate!training.jhtml?orderNum=${orderNum}&nodeType=${nodeType}" target="main">训练报告</a></li>
      <li class="active" ><a class="cBlack" href="mpcstudy!review.jhtml?nodeInstanceId=${nodeInstance.id}">回顾复习</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til"><span>“${nodeInstance.node.name}”</span>回顾复习：</h1>
        <hr  />
        <h4><span class="bbs1"><span class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:window.top.location='../exam/showExam.jhtml?pageNum=0'">逐题回顾</span></span></h4>
        </div>
        <div class="content_c">
            <iframe width="100%"  name="overview" id="frame_overview" src="../exam/viewExam.jhtml?nodeInstanceId=${nodeInstanceId}" scrolling="no" frameborder="0" onload="this.height=frame_overview.document.body.scrollHeight"></iframe>
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
        <div class="padding10px stars">
          <div class="title_ts box1"><b><img src="../images/guanzhu_ico.gif" width="16" height="16" align="absmiddle" />需关注试题</b></div>
    	<ul>
    	  <li class="weigh_box2"><span>五星题</span>
    	    <span>${currentTestStatus.sumFiveStarItems}题</span>
			<c:if test="${currentTestStatus.sumFiveStarItems>0}">
			<c:if test="${processStatus==1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label>	</span>
			</c:if>
			<c:if test="${processStatus!=1}">
			<c:choose>
				<c:when test="${nodeType == 'PHASETEST' && nodeStatus != 2}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
				<c:when test="${nodeType == 'UNITTEST' && nodeInstance.nodeStatus !='PASSED'}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
    			<c:otherwise>
<span class="bbs1">
			<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="self.location.href='javascript:redo(5,<c:if test="${nodeInstanceId == null}">0</c:if><c:if test="${nodeInstanceId != null}">${nodeInstanceId}</c:if>);'">重练</label></span>
			    </c:otherwise>
   			</c:choose>
			</c:if>
			</c:if>
    	    </li>
    	  <li class="weigh_box2"><span>四星题</span>
    	    <span>${currentTestStatus.sumFourStarItems}题</span>
			<c:if test="${currentTestStatus.sumFourStarItems>0}">
			<c:if test="${processStatus==1}">
<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label>	</span>
			</c:if>
			<c:if test="${processStatus!=1}">
			<c:choose>
				<c:when test="${nodeType == 'PHASETEST' && nodeStatus != 2}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
				<c:when test="${nodeType == 'UNITTEST' && nodeInstance.nodeStatus !='PASSED'}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
    			<c:otherwise>
<span class="bbs1">
			<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:redo(4,<c:if test="${nodeInstanceId == null}">0</c:if><c:if test="${nodeInstanceId != null}">${nodeInstanceId}</c:if>);">重练</label></span>
			    </c:otherwise>
   			</c:choose>
			</c:if>
			</c:if>
    	    </li>
    	  <li class="weigh_box2"><span>三星题</span>
    	    <span>${currentTestStatus.sumThreeStarItems}题</span>
			<c:if test="${currentTestStatus.sumThreeStarItems>0}">
			<c:if test="${processStatus==1}">
<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label></span>
			</c:if>
			<c:if test="${processStatus!=1}">
			<c:choose>
				<c:when test="${nodeType == 'PHASETEST' && nodeStatus != 2}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
				<c:when test="${nodeType == 'UNITTEST' && nodeInstance.nodeStatus !='PASSED'}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
    			<c:otherwise>
<span class="bbs1">
			<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="self.location.href='javascript:redo(3,<c:if test="${nodeInstanceId == null}">0</c:if><c:if test="${nodeInstanceId != null}">${nodeInstanceId}</c:if>);'">重练</label></span>
			    </c:otherwise>
   			</c:choose>
			</c:if>
			</c:if>
    	    </li>
    	  <li class="weigh_box2"><span>
		  <select id="selectType" name="selectType"> 
		  <c:if test="${currentTestStatus.sumTwoStarItems>0}">
          <option value="2">二星</option>
		  </c:if>
		  <c:if test="${currentTestStatus.sumOneStarItems>0}">
          <option value="1">一星</option>
		  </c:if>
		  <c:if test="${currentTestStatus.sumHalfStarItems>0}">
          <option value="0.5">半星</option>
		  </c:if>
		  <c:if test="${currentTestStatus.sumIncorrectItems>0}">
          <option value="12">错题</option>
		  </c:if>
		  <c:if test="${currentTestStatus.sumUnfinishedItems>0}">
          <option value="11">未答题</option>
		  </c:if>
		  <c:if test="${currentTestStatus.unsureMarkItems>0}">
          <option value="15">疑问题</option>
		  </c:if>
		  <c:if test="${currentTestStatus.sumCorrectItems>0}">
          <option value="14">做对题</option>
		  </c:if>
		  <c:if test="${currentTestStatus.sumCorrectItems+currentTestStatus.sumIncorrectItems>0}">
          <option value="-1">全部题</option>
		  </c:if>
        </select></span>
		<c:if test="${currentTestStatus.sumTwoStarItems>0 || currentTestStatus.sumOneStarItems>0 || currentTestStatus.sumHalfStarItems>0 || currentTestStatus.sumIncorrectItems>0 || currentTestStatus.sumUnfinishedItems>0 || currentTestStatus.unsureMarkItems>0 || currentTestStatus.sumCorrectItems>0 || currentTestStatus.sumCorrectItems+currentTestStatus.sumIncorrectItems>0}">
		<c:if test="${processStatus==1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label></span>
			</c:if>
			<c:if test="${processStatus!=1}">
			<c:choose>
				<c:when test="${nodeType == 'PHASETEST' && nodeStatus != 2}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
				<c:when test="${nodeType == 'UNITTEST' && nodeInstance.nodeStatus !='PASSED'}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.test_consolidate_training( );">重练</label></span>
				</c:when>
    			<c:otherwise>
		  <span class="bbs1">
		  <label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:redo(document.getElementById('selectType').value,<c:if test="${nodeInstanceId == null}">0</c:if><c:if test="${nodeInstanceId != null}">${nodeInstanceId}</c:if>);">重练</label></span>
			    </c:otherwise>
   			</c:choose>
			</c:if>
			</c:if>
		  </li>
    	  <div class="clear"></div>
  	  </ul>
        </div>
        <div class="clear"></div>
        </div>
         
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
