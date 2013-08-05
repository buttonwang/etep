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
<body onload="MM_preloadImages('../images/btn_post.gif')">

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
                    <td align="center" class="comm">${viewControl.examName} </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="50%" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="10">
                          <tr>
                            <td width="33%" valign="top"><table width="100%" cellpadding="3" cellspacing="0"  class="table_question">
                                <tr class="question_info">
                                  <td height="30" align="center"><b>本次答题情况</b></td>
                                </tr>
                                <tr>
                                  <td height="200" valign="top" style="padding:10px;"><p><strong>训练用时：</strong>${viewControl.spendTimeStr}<br />
                                          <strong>总题量：</strong>${viewControl.examItemNum }<br />
                                          <strong>做题量：</strong>${viewControl.doneItemNum }<br />
                                            <strong>正确数：</strong>${viewControl.rightItemNum}<br />
                                              <strong>得分数</strong>：${viewControl.score}<br />
                                            <strong>正确率：</strong>${viewControl.rightRate }% </p></td>
                                </tr>
                            </table></td>
                            <td width="33%" valign="top"><table width="100%" cellpadding="3" cellspacing="0"  class="table_question">
                                <tr class="question_info">
                                  <td height="30" align="center"><b>本单元训练结果</b></td>
                                </tr>
                                <tr>
                                  <td height="200" valign="top" style="padding:10px;"><p><strong>训练用时：</strong>${statvo.groupTimeUsed}<br />
                                          <!--  <strong>标准总题量：</strong>${statvo.groupItemsNum}<br />
                                          --> <strong> 实际做题量：</strong>${statvo.groupFinishItemsNum}<br />
                                          <strong>得分数：</strong>${statvo.groupScore}<br />
                                            <strong>正确率：</strong>${statvo.groupAccuracyRate}%</p>
                                      <p><strong>星级数：</strong>${statvo.groupStarNum}<br />
                                          <strong>掌握度：</strong>${statvo.groupMasteryRate}%<br />
                                    </p></td>
                                </tr>
                            </table></td>
                            <td width="34%" valign="top"><table width="100%" cellpadding="3" cellspacing="0"  class="table_question">
                                <tr class="question_info">
                                  <td height="30" align="center"><b>总体训练结果</b></td>
                                </tr>
                                <tr>
                                  <td height="200" valign="top" style="padding:10px;"><p><strong>总训练用时：</strong>${statvo.processTimeUsed}<br />
                                          <!-- <strong>标准总题量：</strong>${userDataVO.totalItemsNum}<br/>
                                           --> <strong>实际做题量：</strong>${statvo.processFinishItemsNum}<br/>
                                          <strong>得分数：</strong>${statvo.processScore}<br/>
                                           <strong> 正确率：</strong>${statvo.processAccuracyRate}%<br/>
                                          <strong>掌握度：</strong>${statvo.processMasteryRate}%</p>
                                      <p><strong>总成绩排名：</strong>${statvo.processScoreOrderNum}<br/>
                                          <strong>掌握度排名：</strong>${statvo.processMasteryRateOrderNum}<br/>
                                    </p></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
            </tr>
          </table>
          <table cellspacing="0" cellpadding="10" align="center" border="0">
            <tbody>
              <tr align="middle">
                <td><input onclick="javascript:window.location='../exam/overView.jhtml'" type="button" value="  确定  " name="Submit" /></td>
              </tr>
            </tbody>
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
