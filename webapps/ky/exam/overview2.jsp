<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
</head>
<body >
<!-- 页面主题ss -->
<div id="main" class="wm2" >
	<div class="nTab wm2">
	
<!-- 内容开始 -->
    <div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">
          <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
              <td valign="top">
              
               <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab" style="margin-top:10px;">
                    <tr class="question_info">
                      <td height="25"><p>${userDataVO.processCategoryName}${viewControl.flowName}——（${viewControl.examItemNum}题/${viewControl.examValue}分，限时${viewControl.examTimeStr2}，正确率要求:--） </p></td>
                    </tr>
                    <tr>
                      <td height="25">●实际用时${viewControl.spendTimeStr}</td>
                </tr>
                    <tr>
                      <td height="25" valign="bottom">● 本卷总题数：${viewControl.examItemNum} &nbsp; &nbsp;&nbsp;&nbsp;● 错题总数：${viewControl.errorItemNum}&nbsp; &nbsp;&nbsp;&nbsp;● 未答题数：${viewControl.undoItemNum}&nbsp;&nbsp; &nbsp;&nbsp; ● 标记疑问：${viewControl.markItemNum}&nbsp; &nbsp;&nbsp;&nbsp;<img src="../images/bnt_ztll.gif" onclick="javascript:window.top.location='../exam/showExam.jhtml?pageNum=0'" width="103" height="38" align="absmiddle"  style="cursor:hand"/></td>
                    </tr>
                    <tr>
                      <td height="25">&nbsp;&nbsp; <img src="../images/icon_yiwen.gif" width="16" height="16" /> 标记疑问的题  &nbsp;&nbsp;&nbsp;&nbsp;灰色题号表示未答的题 </td>
                  </tr>
              </table>
              
                <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
                    <tr>
                      <td height="30"><img src="../images/icon_edit.gif" width="16" height="15" align="texttop" /> 已答的题&nbsp;&nbsp;&nbsp;&nbsp; <img src="../images/icon_yiwen.gif" width="16" height="16" align="top" /> 标记疑问的题  &nbsp;&nbsp;&nbsp;&nbsp;灰色题号表示未答的题 </td>
                  </tr>
                  </table>
                  <table width="100%" cellpadding="3" cellspacing="0"  class="listab" style="margin-top:0px;">

                    
				  <c:forEach items="${SectionList}" var="section" varStatus="status">
				  <c:set var="lengthCount" value="${'1'}"></c:set>   
                    <tr class="question_info">
                      <td><p><strong>${section.instruction}</strong></p></td>
                    </tr>

                    <tr>
                      <td height="50">
                        <table width="" border="0" align="left" cellpadding="0" cellspacing="0" class="listab">
                          <tr>

                            <c:forEach items="${section.pages}" var="page" varStatus="status1">
								<c:forEach items="${page.items}" var="item" varStatus="status2">

								<c:if test="${page.hasSubItem==true}" var="hasSubItem">
									<c:forEach items="${item.subItems}" var="subItem" varStatus="status3">
									<c:if test="${subItem.enable==true}">
									<td width="70">
									<span 
									<c:if test="${page.isDone[status3.index]==1}" var="isDone">
										class="t01" 
									</c:if>
									<c:if test="${isDone==false}">
										class="t00" 
									</c:if>
							  
									 >[<a href="../exam/showExam.jhtml?pageNum=${page.pageNum}" title="提示" target="_top">${subItem.itemNum}</a>]</span>
									<c:if test="${isDone==true}">
										<img src="../images/icon_edit.gif" width="16" height="15" align="texttop" /> 
									</c:if>
									
									<c:if test="${(viewControl.showModel==2||viewControl.showModel==3)&&page.isRight[status3.index]==1}">
										<img src="../images/true.gif" width="16" height="15" align="texttop" /> 
									</c:if>
									<c:if test="${(viewControl.showModel==2||viewControl.showModel==3)&&page.isRight[status3.index]==0}">
										<img src="../images/false.gif" width="16" height="15" align="texttop" /> 
									</c:if>
									
									<c:if test="${page.isMark[status3.index]==1}">
										<img src="../images/icon_yiwen.gif" width="16" height="16" align="texttop" />
									</c:if>
								</td>
								
									<c:if test="${(lengthCount)%10==0}">
                      					</tr>
                      					<tr>
                      				</c:if>
								<c:set var="lengthCount" value="${lengthCount+1}"></c:set>
								
								</c:if>
									
								</c:forEach>
								</c:if>

								<c:if test="${hasSubItem==false}">
								
									<td width="70">
									<span 
									<c:if test="${page.isDone[status2.index]==1}" var="isDone">
										class="t01" 
									</c:if>
									<c:if test="${isDone==false}">
										class="t00" 
									</c:if>
							  
									 >[<a href="../exam/showExam.jhtml?pageNum=${page.pageNum}" title="提示" target="_top">${item.itemNum}</a>]</span>
									<c:if test="${isDone==true}">
										<img src="../images/icon_edit.gif" width="16" height="15" align="texttop" /> 
									</c:if>
									
									<c:if test="${(viewControl.showModel==2||viewControl.showModel==3)&&page.isRight[status2.index]==1}">
										<img src="../images/true.gif" width="16" height="15" align="texttop" /> 
									</c:if>
									<c:if test="${(viewControl.showModel==2||viewControl.showModel==3)&&page.isRight[status2.index]==0}">
										<img src="../images/false.gif" width="16" height="15" align="texttop" /> 
									</c:if>
									
									<c:if test="${page.isMark[status2.index]==1}">
										<img src="../images/icon_yiwen.gif" width="16" height="16" align="texttop" />
									</c:if>
								</td>
									<c:if test="${(lengthCount)%10==0}">
                      					</tr>
                      					<tr>
                      				</c:if>
								<c:set var="lengthCount" value="${lengthCount+1}"></c:set>
									
								</c:if>
								</c:forEach>
							</c:forEach>

                          </tr>
                        </table></td>
                    </tr>
                   </c:forEach>

              </table></td>
              <td width="114" align="right" valign="top"><!--<div class="xScrollStick d1">
	<a href="http://www.163.com">俺是浮动条</a>
</div>-->		                
                </td>
            </tr>
          </table>
        </div>
        </div>
    </div>
    </div>
    <div class="clear"> </div>
</div>
</body>
</html>