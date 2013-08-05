<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
</head>
<body onload="MM_preloadImages('../images/btn_post.gif')">

<!--页面头部-->
<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
  <div class="toplins">
    <div class="toplins_l"><img src="../images/toplin_l.jpg" /></div>
    <div class="toplins_c"><a href="javascript:location.href='../web/loadSessionVar.jhtml';" class="cWhite"><img src="../images/exit.gif" align="absmiddle" />退出训练</a></div>
    <div class="toplins_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="clear"></div>
</div>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2" >
	<div class="nTab wm2">
<!-- 内容开始 -->
    <div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" height="336">
            <tr>
              <td height="273" valign="top" style="padding-right:20px;" width="497">欢迎你，<span class="f14px fB">${userDataVO.userName}</span> 同学，你好！你将开始如下训练：<br />
                <div >
                  <p><span class="f16px fonth cBlue">${viewControl.examType}</span><br /><br />
                    <span class="f16px fonth cBlue">${viewControl.examName}</span><br />
                </p>
                  </div>
                <div class="newunit f14px fB cDGray">
                <li>总题数：${viewControl.examItemNum} 题</li>
                 <li>总分：${viewControl.examValue}</li>
                <li>难度：${viewControl.difficultyValue}</li>
                <li>限时：${viewControl.examTimeStr}</li>
                  <br />
                
                <c:if test="${!viewControl.isWeaknessEnhance}">  
                  <li>本卷正确率要求：
					<c:if test="${viewControl.requireRightRate>0}">
					 ${viewControl.requireRightRate}%
					</c:if>
					<c:if test="${viewControl.requireRightRate==0}">
					 --%
					</c:if>
				  </li>
                  </c:if>
                  </div>
                <br />              </td>
              <td width="40%" height="273" valign="top"><table width="100%" border="0" cellspacing="0">
                  <tr>
                    <td valign="top" class="comm">
                    	<p><img src="../images/Tip.gif" border="0" align="absmiddle" /> <span class="cGreen fB">学习谚语（Proverbs about learning）：</span><br/>
                      Experience is the father of wisdom and memory the mother. <br/>
                      经验是智慧之父，记忆是智慧之母。</p></td>
                  </tr>
                </table>
                  <div style="margin-top:10px; padding:10px; border:#bcbcbc 1px solid;"> <strong style="font-size:14px;"> 操作提示<br />
                    </strong>
                      <li>注意右侧的时间提示， 限时做题很重要！ </li>
                    <li>你可以跳过不会做的题，最后通过你可以选择“疑问题”、“未做题”单独做； </li>
                    <li>如果你对答题还有疑问，可以通过 <a href="#"><img src="../images/icon_noquestion.gif" border="0" /></a> <a href="#"><img src="../images/icon_question.gif" border="0" /></a> 来标记； </li>
                    <li><span class="cRed">★</span> 的多少表示这个题对你的重要程度。 <br />
                      </li>
                </div></td>
            </tr>
            <tr>
              <td colspan="2" valign="top" style="padding-right:20px;" width="847"><p align="center" style="font-size:14px;">准备好了吗？请开始吧！祝你取得好成绩！ </p>
                  <p align="center">
                    <input type="button" name="submit" value=" 开始训练" onclick="javascript:window.location='../exam/overView.jhtml'" />
                </p></td>
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
