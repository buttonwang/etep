<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/popdiv.js"></script>
<script>
//什么是总掌握度
function commend()
{
   var showData = sampless.innerHTML;
   ScreenConvert();DialogShow(showData,530,300,530,400);
}
</script>
		<!--什么是总掌握度-->
        <pre id=sampless style="display:none" >
           <div class="win_Content">
            <div class="win_tilte f14px fB"> <div class="left"><img src="../images/i_icn.jpg" align="absmiddle" />&nbsp;什么是总掌握度</div><div class="right" style="padding-right:20px;"><a href="###" onClick="return DialogHide();"><img src="../images/close.gif" /></a></div>
            <div class="clear"></div>
            </div>
            <div class="win_text cDGray">
            <p><span class="cRed">掌握度是对一定范围（如某个单元、某份试卷）内知识掌握程度的自动评价。</span><br/>
        你要特别注意正确率很高，而掌握度较低的试卷，因为它表明你还需要继续努力，这部分知识还有很大的加强和巩固的空间。 
        要提高成绩，掌握度比正确率还重要！<br/> 
        <span class="cRed">怎样提高掌握度？</span><br/>
        通过“分析强化”，优先训练★级高的题目和试卷，降低试卷★级总数，可以大大提高掌握度。</p>
        </div>
        <div style="text-align:center; padding:5px 10px; "><input name="close" type="button" id="close" value="关闭" onClick="return DialogHide();"/>
        </div>
        </div>
        </pre>
<!-- pop window end -->
		
</head>
<body>

<!--页面头部-->
<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
<div class="toplin">
    	<div class="toplin_l"><img src="../images/toplin_l.jpg" /></div>
        <div class="toplin_c">
        	<ul>
            	<li><a href="mainPage!ky.jhtml">首页</a></li>
                <li class="divli">|</li>
                <li> <a href="#">在线咨询</a></li>
                <li class="divli">|</li>
                <li><a href="#">帮助</a></li>
                <li class="divli">|</li>
                <li><a href="#" onclick="if(confirm('是否确认退出系统?')){location.href='../../logout.jsp';}">退出系统</a></li>
            </ul>
        </div>
	<div class="toplin_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="clear"></div>
</div>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2" >
	<div class="nTab">
        	<!-- 内容开始 -->
<div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">
          <table width="860" border="0" cellspacing="0" cellpadding="0" height="232" >
            
            <tr>
              <td width="45%" valign="top"><span class="f12px"><a href="#" onClick="commend()">什么是掌握度？</a></span></td>
            </tr>
            
            <tr>
              <td valign="top">
              <table width="100%" border="0" cellspacing="0" cellpadding="3">
                <tr>
                  <td> 
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                    <tr class="whitetr">
                      <td colspan="3" align="center" bgcolor="#e8f1ff"><span class="f12px fB">总进度排名</span></td>
                      </tr>
                          <c:set var="studentCount" value="${'0'}"></c:set>              
               		 <c:forEach items="${learningProcessRateTopList}" var="item" varStatus="itemStatus">                  
	                  			<c:set var="studentCount" value="${studentCount+1}"></c:set>
	                  	  <tr class="whitetr">
	                      <td>${studentCount}</td>
	                      <td align="center">${item.userName}</td>
	                      <td align="center">${item.learningProcessRate}%</td>
	                    </tr>		
                    </c:forEach> 
                  </table>
                  </td>
                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                    <tr class="whitetr">
                      <td colspan="3" align="center" bgcolor="#e8f1ff"><span class="f12px fB">总掌握度排名</span></td>
                      </tr>
                       <c:set var="studentCount2" value="${'0'}"></c:set>
                     <c:forEach items="${totalMasteryRateTopList}" var="item" varStatus="itemStatus">                  
	                  			<c:set var="studentCount2" value="${studentCount2+1}"></c:set>
	                  	  <tr class="whitetr">
	                      <td>${studentCount2}</td>
	                      <td align="center">${item.userName}</td>
	                      <td align="center">${item.totalMasteryRate}%</td>
	                    </tr>		
                    </c:forEach> 
                  </table>
                  </td>
                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                    <tr class="whitetr">
                      <td colspan="3" align="center" bgcolor="#e8f1ff"><span class="f12px fB">总正确率排名</span></td>
                    </tr>
                     <c:set var="studentCount3" value="${'0'}"></c:set>
                     <c:forEach items="${accuracyRateTopList}" var="item" varStatus="itemStatus">                  
	                  			<c:set var="studentCount3" value="${studentCount3+1}"></c:set>
	                  	  <tr class="whitetr">
	                      <td>${studentCount3}</td>
	                      <td align="center">${item.userName}</td>
	                      <td align="center">${item.accuracyRate}%</td>
	                    </tr>		
                    </c:forEach> 
                  </table></td>
                </tr>
              </table></td>
            </tr>
          </table>
        </div>
        </div>
          </div>
  </div>
    <div class="clear"> </div>
</div>

<div class="bonnt wm1">© 2008年  安博教育集团  版权所有</div>
</body>
</html>

