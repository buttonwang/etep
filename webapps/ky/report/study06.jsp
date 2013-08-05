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
      <li class="normal"><a href="study.jhtml?nodeInstanceId=${nodeInstanceId}">训练报告</a></li>
      <li class="active" >回顾复习</li>
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
            <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
              <tr>
                <td width="34%"><strong>“${nodeInstance.node.nodeGroup.name}”回顾复习</strong></td>
                <td width="35%"><strong>掌握度</strong></td>
                <td width="31%"><strong>正确率</strong></td>
              </tr>
              <tr>
                <td>${nodeInstance.node.name}</td>
                <td>${currentTestStatus.masteryRate}%</td>
                <td>${currentTestStatus.accuracyRate}%</td>
              </tr>
              
            </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                <tr>
                  <td>
                    <div class="bnt_xing left"><span class="cRed">★★★★★</span>&nbsp;&nbsp;${currentTestStatus.sumFiveStarItems} &nbsp;&nbsp;<c:if test="${currentTestStatus.sumFiveStarItems>0}"><a href="javascript:redo(5,${nodeInstanceId});" target="_parent" class="f14px fb">重练</a></c:if></div>
					<div class="bnt_xing left"><span class="cRed">★★★★</span>&nbsp;&nbsp;${currentTestStatus.sumFourStarItems} &nbsp;&nbsp;<c:if test="${currentTestStatus.sumFourStarItems>0}"><a href="javascript:redo(4,${nodeInstanceId});" target="_parent" class="f14px fb">重练</a></c:if></div>
            		<div class="bnt_xing left"><span class="cRed">★★★</span>&nbsp;&nbsp;${currentTestStatus.sumThreeStarItems} &nbsp;&nbsp;<c:if test="${currentTestStatus.sumThreeStarItems>0}"><a href="javascript:redo(3,${nodeInstanceId});" target="_parent" class="f14px fb">重练</a></c:if></div>
                    <div class=" left bnt_xing2">
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
                   </td>
                </tr>
              </table>
            <iframe width="100%" name="overview" id="frame_overview" src="../exam/viewExam.jhtml?nodeInstanceId=${nodeInstanceId}" scrolling="no" frameborder="0" onload="this.height=100"></iframe>
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
			doit(document.getElementById('selectType'));
			doit(window.parent.document.getElementById('sign01'));
			doit(window.parent.document.getElementById('sign02'));
			window.parent.document.getElementById('sign02').focus();
			return ;
		</c:if>		
		top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=3&scope="+scope;	
}
		function reinitIframe(){
				var iframe = document.getElementById("frame_overview");
				try{
					var bHeight = iframe.contentWindow.document.body.scrollHeight;
					var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
					var height = Math.max(bHeight, dHeight);
					iframe.height =  height;
					//var bWidth = iframe.contentWindow.document.body.scrollWidth;
					//var dWidth = iframe.contentWindow.document.documentElement.scrollWidth;
					//var width = Math.max(bWidth, dWidth);
					//iframe.width =  width;
				}catch (ex){}
			}
		window.setInterval("reinitIframe()", 200);		
</script>
</body>
</html>
