<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
</head>
<body>

<!--页面头部-->
<jsp:include page="include_head.jsp"></jsp:include>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2" >
	<div class="nTab wm2">
<!-- 内容开始 -->
    <div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" class="comm">${viewControl.examName}</td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="50%" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="10">
                          <tr>
                            <td width="50%" valign="top">
                            <table width="50%" align="center" cellpadding="3" cellspacing="0"  class="table_question">
                                <tr class="question_info">
                                  <td height="30" align="center"><b>本次测试情况</b></td>
                                </tr>
                                <tr>
                                  <td height="200" valign="top" style="padding:10px;font-size:14px;"><p>本卷${viewControl.examItemNum}题 
                                    总分：${viewControl.examValue }分 限时：${viewControl.examTimeStr } 要求：${viewControl.requireRightRate}%</p>
                                       <p>
                                       	  <strong>测试用时：</strong>${viewControl.spendTimeStr }<br />
                                          <strong>做 题 数：</strong>${viewControl.examItemNum }<br />
                                          <strong>正确题数：</strong>${viewControl.rightItemNum }<br />
                                          <strong>得 分 数：</strong>${viewControl.score }<br />
                                          <strong>正 确 率：</strong>${viewControl.rightRate }%<br />
                                       </p>
                                      <c:if test="${viewControl.isPass==true}" var="pass">
                                    	<p align="center" style="color:#ff6600;font-weight:bold;"><img src="../images/heart.gif" width="79" height="51" align="absmiddle" /> 祝贺你！你已经顺利通过了本测试。</p>
                                    	<div align="center">请再接再厉，继续下面的训练。 </div>
                                    </c:if>
                                    <c:if test="${pass==false}">
                                    	<p align="center" style="color:#000000;font-weight:bold;"><img src="../images/face_sade.gif" width="60" height="56" align="absmiddle" /> 很遗憾！你没有通过本测试，请继续努力。 </p>
                                      		<div align="center"> 系统将自动安排你继续做如下训练卷中你最需要关注的试题，通过后将再次进入本测试：<br />
                                        		${redoInfo}
                                      		</div>
                                    </c:if>
                                    </td>
                                </tr>
                              </table>
                               
                                <table border="0" align="center" cellpadding="10" cellspacing="0">
                                  <tr align="center">
                                    <td><input type="button" name="Submit" value="  确定  " onclick="javascript:window.location='../exam/overView.jhtml'" /></td>
                                  </tr>
                                </table></td>
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
