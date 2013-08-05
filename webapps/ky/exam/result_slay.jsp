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
                    <td align="center" class="comm">本次评测总体情况 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="50%" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="10">
                          <tr>
                            <td valign="top"><table width="100%" cellpadding="3" cellspacing="0"  class="table_question">
                                
                                <tr>
                                  <td valign="top" style="padding:10px;" width="20%"><p align="left"><b>评测用时：</b><c:if test="${viewControl.spendTime==0}">小于1分钟</c:if><c:if test="${viewControl.spendTime>0}">${viewControl.spendTime}分钟</c:if><br />
                                        <b>总题数：</b>${viewControl.examItemNum}<br />
                                        <b>正确题数：</b>${viewControl.rightItemNum }<br />
                                        <b>总分数：</b>${viewControl.examValue }<br />
                                        <b>得分数：</b>${viewControl.score }<br />
                                        <b>正确率：</b>${viewControl.rightRate }%                                   
                                    </p></td>
                                    <td  valign="top" style="padding:10px;font-size:14px;" width="80%">
                                    <c:if test="${viewControl.rightRate<30}">
                                   		 总体分数低；基础语言知识掌握比较差，大部分词义不明确，不能分清句子结构，语法不能活用，导致无力把握贯穿文章主题的关键词句，难以判断文章上下文内容的衔接关系。<strong>“详细分值”</strong>表反映了你对本次评测涉及的主要知识点的掌握情况：
                                    </c:if>
                                     <c:if test="${viewControl.rightRate>=30 and viewControl.rightRate<50}">
                                    	 总体分数比较低；基础语言知识掌握比较差，有些词义不明确，部分句子结构不清晰，语法知识迁移能力不够，把握贯穿文章主题的关键词句能力欠佳，对于语法、句际的逻辑关系和篇章结构的把握不够到位。<strong>“详细分值”</strong>表反映了你对本次评测涉及的主要知识点的掌握情况：
                                     </c:if>
                                     <c:if test="${viewControl.rightRate>=50 and viewControl.rightRate<70}">
                                     	 总体分数在及格上下；基础语言知识掌握一般，有些词义不明确，具有一定把握贯穿文章主题的关键词句的能力，对于部分语法、句际的逻辑关系和篇章结构的把握欠到位。<strong>“详细分值”</strong>表反映了你对本次评测涉及的主要知识点的掌握情况：
                                     </c:if>
                                     <c:if test="${viewControl.rightRate>=70 and viewControl.rightRate<=90}">
                                     	 总体分数较好；基础语言知识比较牢固；有些词义不够明确；具有一定把握贯穿文章主题的关键词句的能力，具备一定的语法、句际逻辑关系和篇章结构的把握能力。<strong>“详细分值”</strong>表反映了你对本次评测涉及的主要知识点的掌握情况：
                                     </c:if>
                                     <c:if test="${viewControl.rightRate>90}">
                                    	 基础语言知识牢固；对于词义把握比较准确；具有把握贯穿文章主题的关键词句的能力，具备较强的语法、句际逻辑关系和篇章结构的把握能力。掌握了相应的解题技巧，经验比较丰富。<strong>“详细分值”</strong>表反映了你对本次评测涉及的主要知识点的掌握情况：
                                     </c:if>
                                    </td>
                                </tr>
                            </table></td>
                        </tr>
                          <tr>
                            <td valign="top"><div align="center" class="f14px"><b>详细分值</b>
                            </div>
                              <table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="listab">
                <tr>
                    <td width="30%" height="32" align="center" valign="top" bgcolor="#EEEEEE">评测项                  </td>
                    <td width="20%" align="center" valign="top" bgcolor="#EEEEEE">分值</td>
                    <td width="19%" align="center" valign="top" bgcolor="#EEEEEE">得分</td>
                    <td width="31%" align="center" valign="top" bgcolor="#EEEEEE">正确率</td>
                </tr>
                 <c:forEach items="${reportShowVOList}" var="item" varStatus="itemStatus">
                  <tr class=" whitetr">
                    <td align="center" valign="top">${item.knowledgePointName}</td>
                    <td align="center" valign="top">${item.totalScore}</td>
                    <td align="center" valign="top">${item.score}</td>
                    <td align="center" valign="top">${item.accuracyRate}%</td>
                  </tr>
                 </c:forEach>
                  <tr class=" whitetr">
                    <td align="center" valign="top"><strong>总计</strong></td>
                    <td align="center" valign="top"><strong>${showVO.totalScore}</strong></td>
                    <td align="center" valign="top"><strong>${showVO.score}</strong></td>
                    <td align="center" valign="top"><strong>${showVO.accuracyRate}%</strong></td>
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
