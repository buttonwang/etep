<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的积分</title>
<link rel="stylesheet" href="../css/hig.css" type="text/css"/>
<link rel="stylesheet" href="../css/style_blue.css"  type="text/css"/>
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/popdiv_1.js"></script>
 
</head>
<body>
<jsp:include page="../web/include_head.jsp"></jsp:include>

<style type="text/css">
<!--
.STYLE3 {
	font-size:14px;
	color: #FFFFFF;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<div class="wm950 nTab">
 <!-- 标题开始 -->
 <div class="TabTitle" id="ceshi"></div>
 <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
	<div class="con_right2 wm925 right">
  	<div class="bg">
    <table width="925" border="0" cellspacing="1">
      <tr>
        <td align="center"><strong>积分类别</strong></td>
        <td align="center"><strong>总得分</strong></td>
        <td align="center"><strong>最近一周得分(${beforeWeekfirst} 至 ${beforeWeeklast})</strong></td>
        <td align="center"><strong>最近一月得分(${beforeMonthfist} 至 ${beforeMonthlast})</strong></td>
        <td align="center"><strong>积分开始日期</strong></td>
        <td align="center"><strong>积分截止日期</strong></td>
      </tr>

      <c:forEach var="pointStat" items="${pointList}"  varStatus="status">
	  <tr>
         <td>${pointStat.pointName}</td>
	     <td>${pointStat.countPoint}</td> 
		 <td>${pointStat.lastWeekPoint}</td> 
		 <td>${pointStat.lastMonthPoint}</td>
		 <td><fmt:formatDate pattern="yyyy-MM-dd" value="${pointStat.minOperateTime}"/></td>
		 <td><fmt:formatDate pattern="yyyy-MM-dd" value="${pointStat.maxOperateTime}"/></td>
	   </tr>
       </c:forEach>
    </table>
  	</div>
  	<div class=" blankW_9"></div>
  	
  	<div class="TabTitle" id="ceshi">
    	<ul>
        	<li class="active" ><a class="cBlack" href="javascript:void(null)">积分排名</a></li>
        </ul>
    </div>
	<div class="task_con">
	  <div class=" blankW_6"></div>
	  <table width="908" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    <td width="220" align="center">
	<table width="90%" cellpadding="0" cellspacing="0" bgcolor="#E4F1FD">
      <tr>
        <td width="23%" height="32" background="../images/cr_bg03n.gif">&nbsp;</td>
        <td width="42%" align="center" background="../images/cr_bg03n.gif">
        <span class="STYLE3">勤奋</span></td>
        <td width="35%" align="center" background="../images/cr_bg03n.gif">
		<a href="#" onmouseover="MM_showHideLayers('word1', '', 'show');" onmouseout="MM_showHideLayers('word1', '', 'hide');" class="gz">规则</a>
		              <div class="help" id="word1" onmouseover="MM_showHideLayers('word1','','show')" onmouseout="MM_showHideLayers('word1','','hide')">
                <h2>只要做题，不论对错，即可获得勤奋分</h2>
                <div class="help_c">
                  <p>规则：同一道题，做第1次可得等同于题分的积分，做第2次得1/2题分的积分，做第3次得1/3题分的积分，做第4次得1/4题分的积分，做第5次及以上不再得分。</p>
<p>示例：如一道题的题分为6，此题被做4遍后则共获得勤奋分6+3+2+1.5=12.5</p>
                </div>
              </div>
		</td>
      </tr>
      <tr>
        <td colspan="3">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<c:forEach var="item" items="${orderMap['diligenceList']}" varStatus="status">
		  <tr>
			 <td width="24%" height="25" align="center"><img src="../images/cr_icon_number${status.index+1 }.gif" width="15" height="14" /></td>
		     <td width="43%">${item[0] }</td>
             <td width="33%">${item[1] }</td>
          </tr>
		</c:forEach>
		
        </table>	
        </td>
        </tr>
      <tr>
        <td colspan="3">
		<c:if test="${orderMap['mpView']!=null}"><div class="mypoing">勤奋分：${orderMap['mpView'].diligence}，排名：${orderMap['diligence']}</div></c:if>
		</td>
      </tr>
    </table></td>
    <td width="220" align="center"><table width="90%" cellpadding="0" cellspacing="0" bgcolor="#E4F1FD">
      <tr>
        <td width="23%" height="32" background="../images/cr_bg03n.gif">&nbsp;</td>
        <td width="42%" align="center" background="../images/cr_bg03n.gif"><span class="STYLE3">智慧</span></td>
        <td width="35%" align="center" background="../images/cr_bg03n.gif">
		<a href="#" onmouseover="MM_showHideLayers('word2', '', 'show');" onmouseout="MM_showHideLayers('word2', '', 'hide');" class="gz">规则</a>
		              <div class="help" id="word2" onmouseover="MM_showHideLayers('word2','','show')" onmouseout="MM_showHideLayers('word2','','hide')">
                <h2>只有做对题，才能获得智慧分</h2>
                <div class="help_c">
                  <p>规则：同一道题，做第1次即对得5倍题分的积分，做第2次才对得3倍题分的积分，做第3次才对得2倍题分的积分，做第4次及以上才对得等同于题分的积分。</p>
<p>示例：如一道题的题分为6，此题被做3次后才做对则获得智慧分2*6=12</p>
                </div>
              </div>
		</td>
      </tr>
      <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
         <c:forEach var="item" items="${orderMap['wisdomList']}" varStatus="status">
		  <tr>
			 <td width="24%" height="25" align="center"><img src="../images/cr_icon_number${status.index+1 }.gif" width="15" height="14" /></td>
		     <td width="43%">${item[0] }</td>
             <td width="33%">${item[1] }</td>
          </tr>
		</c:forEach>
        </table></td>
      </tr>
      <tr>
        <td colspan="3"><c:if test="${orderMap['mpView']!=null}"><div class="mypoing">智慧分：${orderMap['mpView'].wisdom}，排名：${orderMap['wisdom']}</div></c:if></td>
       
      </tr>
    </table></td>
    <td width="220" align="center"><table width="90%" cellpadding="0" cellspacing="0" bgcolor="#E4F1FD">
      <tr>
        <td width="23%" height="32" background="../images/cr_bg03n.gif">&nbsp;</td>
        <td width="42%" align="center" background="../images/cr_bg03n.gif"><span class="STYLE3">奉献</span></td>
        <td width="35%" align="center" background="../images/cr_bg03n.gif">
		<a href="#" onmouseover="MM_showHideLayers('word3', '', 'show');" onmouseout="MM_showHideLayers('word3', '', 'hide');" class="gz">规则</a>
		              <div class="help" id="word3" onmouseover="MM_showHideLayers('word3','','show')" onmouseout="MM_showHideLayers('word3','','hide')">
                <h2>分享学习笔记、笔记被同学投鲜花或被管理员引用为精华笔记，均可获得奉献分。</h2>
                <div class="help_c">
                  <p>规则：共享一条学习笔记可得5个奉献分，笔记被同学投鲜花一次可得5个奉献分，笔记被管理员引用为精华可得20个奉献分。</p>
<p>示例：共享了学习笔记，被6位同学投了鲜花，还被管理员引用为精华笔记，则共获得奉献分5+6*5+20=55</p>
                </div>
              </div>
		</td>
      </tr>
      <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
		<c:forEach var="item" items="${orderMap['dedicateList']}" varStatus="status">
		  <tr>
			 <td width="24%" height="25" align="center"><img src="../images/cr_icon_number${status.index+1 }.gif" width="15" height="14" /></td>
		     <td width="43%">${item[0] }</td>
             <td width="33%">${item[1] }</td>
          </tr>
		</c:forEach>           
        </table></td>
      </tr>
      <tr>
         <c:if test="${orderMap['mpView']!=null}"><td colspan="3"><div class="mypoing">奉献分：${orderMap['mpView'].dedicate }，排名：${orderMap['dedicate']}</div></td></c:if>
      </tr>
    </table></td>
    <td width="220" align="center"><table width="90%" cellpadding="0" cellspacing="0" bgcolor="#E4F1FD">
      <tr>
        <td width="23%" height="32" background="../images/cr_bg03n.gif">&nbsp;</td>
        <td width="42%" align="center" background="../images/cr_bg03n.gif"><span class="STYLE3">洞察</span></td>
        <td width="35%" align="center" background="../images/cr_bg03n.gif">
		<a href="#" onmouseover="MM_showHideLayers('word4', '', 'show');" onmouseout="MM_showHideLayers('word4', '', 'hide');" class="gz">规则</a>
		              <div class="help" id="word4" onmouseover="MM_showHideLayers('word4','','show')" onmouseout="MM_showHideLayers('word4','','hide')">
                <h2>提交捉虫记录并成功纠正试题错误，即可获得洞察分</h2>
                <div class="help_c">
                  <p>规则：纠正一个严重错误可得500洞察分，纠正一般错误可得200洞察分，纠正细微错误可得100洞察分，纠错无效可得20洞察分</p>
                </div>
              </div>
		</td>
      </tr>
      <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
			<c:forEach var="item" items="${orderMap['percipienceList']}" varStatus="status">
			  <tr>
				 <td width="24%" height="25" align="center"><img src="../images/cr_icon_number${status.index+1 }.gif" width="15" height="14" /></td>
			     <td width="43%">${item[0] }</td>
	             <td width="33%">${item[1] }</td>
	          </tr>
			</c:forEach>           
        </table></td>
      </tr>
      <tr>
        <c:if test="${orderMap['mpView']!=null}"><td colspan="3"><div class="mypoing">洞察分：${orderMap['mpView'].percipience }，排名：${orderMap['percipience']}</div></td></c:if>
      </tr>
    </table></td>
    <!-- td width="180" align="center"><table width="90%" cellpadding="0" cellspacing="0" bgcolor="#E4F1FD">
      <tr>
        <td width="23%" height="32" background="../images/cr_bg03n.gif">&nbsp;</td>
        <td width="42%" align="center" background="../images/cr_bg03n.gif"><span class="STYLE3">勇气</span></td>
        <td width="35%" align="center" background="../images/cr_bg03n.gif">
		<a href="#" onmouseover="MM_showHideLayers('word5', '', 'show');" onmouseout="MM_showHideLayers('word5', '', 'hide');" class="gz">规则</a>
		              <div class="help" id="word5" onmouseover="MM_showHideLayers('word5','','show')" onmouseout="MM_showHideLayers('word5','','hide')">
                <h2>只要做题，不论对错，即可获得勤奋分</h2>
                <div class="help_c">
                  <p>规则：同一道题，做第1次可得等同于题分的积分，做第2次得1/2题分的积分，做第3次得1/3题分的积分，做第4次得1/4题分的积分，做第5次及以上不再得分。</p>
<p>示例：如一道题的题分为6，此题被做4遍后则共获得勤奋分6+3+2+1.5=12.5</p>
                </div>
              </div>
		</td>
      </tr>
      <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
			<c:forEach var="item" items="${orderMap['courageList']}" varStatus="status">
			  <tr>
				 <td width="24%" height="25" align="center"><img src="../images/cr_icon_number${status.index+1 }.gif" width="15" height="14" /></td>
			     <td width="43%">${item[0] }</td>
	             <td width="33%">${item[1] }</td>
	          </tr>
			</c:forEach>           
        </table></td>
      </tr>
      <tr>
        <td colspan="3"><div class="mypoing">勇气分：${orderMap['mpView'].courage  }，排名：${orderMap['courage']}</div></td>
        
      </tr>
    </table></td -->
  </tr>
</table>

        <div class="blankW9"></div>
        </div>
        
        <div id="historyPoints"></div>
        </div>
        <div class="clear"></div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="../include_bottom.jsp"></jsp:include>
<script type="text/javascript">
		function loadHistoryPoints(start, pageSize) {
			$("#historyPoints").load("myPointAction!historyPoints.jhtml", 
					{start:  start,
					 pageSize: pageSize},
					function(){  }
			);
		}

		function pageCallBack(start, pageSize) {
			loadHistoryPoints(start, pageSize);
			location.href = "#topHistoryPoints";
		}

		loadHistoryPoints(0, "${pageSize}");
</script>

<script language="JavaScript" src="js/ocscript.js" type="text/javascript"></script>
</body>
</html>