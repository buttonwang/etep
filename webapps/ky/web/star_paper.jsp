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
//什么是星卷榜
function voucher()
{
   var showData = sample.innerHTML;
   ScreenConvert();DialogShow(showData,530,300,530,400);
}

//什么是星级

function voucher2()
{
   var showData = samples.innerHTML;
   ScreenConvert();DialogShow(showData,530,300,530,400);
}
</script>
        <!--什么是星卷榜-->
        <pre id=sample style="display:none" >
           <div class="win_Content">
            <div class="win_tilte f14px fB"> 
           		 <div class="left"><img src="../images/i_icn.jpg" align="absmiddle" />&nbsp;什么是星卷榜？</div>
            	<div class="right" style="padding-right:20px;">
            		<a href="###" onClick="return DialogHide();"><img src="../images/close.gif" /></a>
            	</div>
            	<div class="clear"></div>
            </div>
            <div class="win_text cDGray">
            <p><span class="cRed">星卷榜………</p>
        </div>
        <div style="text-align:center; padding:5px 10px; ">
        <input name="close" type="button" id="close" value="关闭" onClick="return DialogHide();"/>
        </div>
        </div>
        </pre>
<!-- pop window end -->

<!--什么是星级-->
        <pre id=samples style="display:none" >
           <div class="win_Content">
            <div class="win_tilte f14px fB"> 
       		  <div class="left"><img src="../images/i_icn.jpg" align="absmiddle" />&nbsp;什么是星级？</div>
            	<div class="right" style="padding-right:20px;">
            		<a href="###" onClick="return DialogHide();"><img src="../images/close.gif" /></a>
           	  </div>
            	<div class="clear"></div>
            </div>
            <div class="win_text cDGray">
            <p>
            <span class="cRed">一个题目的★级代表了该题目对你的难度。掌握得不好的题目★级高，掌握得好的★级低。所以，★级越高的题目，越需要你优先训练。<br />
</span><br/>

<span class="fB">题目的<span class="cRed">★</span>级跟难度有关系吗？</span><br/>
题目的难度是题目对所有人的平均难度，而<span class="cRed">★</span>级在一定程度上反映了它对你的个人难度。因此，<span class="cRed">★</span>级对你来说更重要。<br />
有了<span class="cRed">★</span>级，这些训练才是真正为你准备的。 
<br />
<br />
<span class="fB">题目的星级 怎样是确定的？</span><br />
你做过的每一道题都会有<span class="cRed">★</span>级，共有7个级别，5星、4星……1星、半星 ，还有一个0星<img src="../images/smile.gif" width="20" height="20" align="absmiddle" /> 。 表示这个题你从来都没有错过。<br />
根据你做题的情况，题目的星级会自动升高或者降低。<br />
<br />
 怎样快速降低题目的<span class="cRed">★</span>级？
 <br />
 1. 认真做题，争取第一次就做对
 <br />
 2. 反复复习训练，尽可能减少错误
 <br />
 3. 优先训练高<span class="cRed">★</span>级题目</p>
        </div>
        <div style="text-align:center; padding:5px 10px; ">
        <input name="close" type="button" id="close" value="关闭" onClick="return DialogHide();"/>
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
              <td valign="top"><a href="#" onClick="voucher()">什么是星卷榜？</a></td>
            </tr>
            
            <tr>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="3">
                <tr>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="listab">
                    <tr class="f12px fB">
                      <td align="center" bgcolor="#eefbdb">训练/测试 </td>
                      <td align="center" bgcolor="#eefbdb">首答正确率 </td>
                      <td align="center" bgcolor="#eefbdb">当前正确率</td>
                      <td align="center" bgcolor="#eefbdb">掌握度</td>
                      <td align="center" bgcolor="#eefbdb">答题日期</td>
                    </tr>
                    <c:forEach items="${starPaperList}" var="item" varStatus="itemStatus">                  
						<tr class="whitetr">
	                      <td align="center">${item.nodeName}</td>
	                      <td align="center">${item.firstAccuracyRate}%</td>
	                      <td align="center">${item.accuracyRate}%</td>
	                      <td align="center">${item.masteryRate}%</td>
	                      <td align="center">${item.time}</td>                    
	                    </tr>		
                    </c:forEach> 
                  </table></td>
                </tr>
                <tr>
                  <td><a href="#" onClick="voucher2()">什么是星级？</a></td>
                </tr>
                <tr>
                  <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="listab">
                    <tr class="f12px fB">
                      <td colspan="2" align="center" bgcolor="#f9fbdb">星级题统计</td>
                    </tr>
                    <tr class="whitetr">
                      <td width="50%" align="center"><span class="cRed">★★★★★</span></td>
                      <td width="50%" align="center">  ${userDataVO.sumFiveStarItems}题</td>
                    </tr>
                    <tr class="whitetr">
                      <td align="center"><span class="cRed">★★★★</span></td>
                      <td align="center">${userDataVO.sumFourStarItems}题</td>
                    </tr>
                    <tr class="whitetr">
                      <td align="center"><span class="cRed">★★★</span></td>
                      <td align="center">${userDataVO.sumThreeStarItems}题</td>
                    </tr>
                    <tr class="whitetr">
                      <td align="center"><span class="cRed">★★</span></td>
                      <td align="center">${userDataVO.sumTwoStarItems}题</td>
                    </tr>
                    <tr class="whitetr">
                      <td align="center"><span class="cRed">★</span></td>
                      <td align="center">${userDataVO.sumOneStarItems}题</td>
                    </tr>
                    <tr class="whitetr">
                      <td align="center"><span class="cRed">☆</span></td>
                      <td align="center">${userDataVO.sumHalfStarItems}题</td>
                    </tr>
                    <tr class="whitetr">
                      <td align="center"><img src="../images/smile.gif" width="20" height="20" /></td>
                      <td align="center">${userDataVO.sumZeroStarItems}题</td>
                    </tr>
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
