<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="INTRO_DIV" style="WIDTH: 120px; PADDING-TOP: 5px; position:relative; TOP: 0px;" class="d1">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="center">本卷限时：${viewControl.examTimeStr2}<br />
                      	  实际用时：<br />
                        <strong><font color="#FF6600" style="font-size:24px;">${viewControl.spendTimeStr}</font></strong></td>
                    </tr>
                    <tr>
                      <td height="30" align="left">
					   
                       <div class="biaoji">
                         <input type="checkbox" name="checkbox" id="checkbox" Onclick="javascript:enableOrDisable(this.checked)" />标记生词
                        </div>
						
                     </td>
                    </tr>
                    <!--  
                    <tr>
                      <td height="60" align="center"><a href="alert('继续训练')" ><img src="../images/btn_xl.gif" name="Image23" width="103" height="38" border="0" id="Image23" /></a></td>
                    </tr>
                    -->
                    <tr>
                      <td height="60" align="center"><a href="#" onclick="javascript:window.location='../exam/overView.jhtml'"><img src="../images/btn_ll.gif" style="cursor: hand" name="Image23" width="103" height="38" border="0" id="Image23" /></a></td>
                    </tr>
                    <tr>
                      <td height="60" align="center"><a 
                       <c:if test="${viewControl.showModel!=3}">
                      		href="../web/loadSessionVar!ky.jhtml"
                      </c:if>
                      <c:if test="${viewControl.showModel==3}">
                      		href="../web/loadSessionVar!ky.jhtml?nodeInstanceId=${viewControl.currentTestStatusId}&jumpType=1&showListType=2"
                      </c:if>
                      ><img src="../images/btn_exte.gif" style="cursor: hand" name="Image23" width="103" height="38" border="0" id="Image23" /></a></td>
                    </tr>
                    <tr>
                      <td align="left"><br />
                          <input type="radio" name="radiobutton" value="radiobutton" 
                           <c:if test="${viewControl.filterType==1}">
                          	checked
                          </c:if>
                          onclick="javascript:filterItem(1,2)" />
                       查看所有题(${viewControl.examItemNum })<br />
                        <input type="radio" name="radiobutton" value="radiobutton"
                          <c:if test="${viewControl.filterType==2}">
                          	checked
                          </c:if>
                         onclick="javascript:filterItem(2,2)"/>
                        只看答错题(<span name="ErrorItemNum" id="ErrorItemNum">${viewControl.errorItemNum }</span>)<br />
                        <input type="radio" name="radiobutton" value="radiobutton" 
                         <c:if test="${viewControl.filterType==3}">
                          	checked
                          </c:if>
                        onclick="javascript:filterItem(3,2)"/>
                        只看未答题(<span name="UndoItemNum" id="UndoItemNum">${viewControl.undoItemNum }</span>) <br />
                        <input type="radio" name="radiobutton" value="radiobutton" 
                         <c:if test="${viewControl.filterType==4}">
                          	checked
                          </c:if>
                         onclick="javascript:filterItem(4,2)"/>
                        只看疑问题(<span name="MarkItemNum" id="MarkItemNum">${viewControl.markItemNum }</span>)<br />
                        <br /></td>
                    </tr>
                    <c:if test="${viewControl.showPreButton==true}">
                    <tr>
                      <td height="40" align="center">
                      <img src="../images/btn_s.gif" onclick="javascript:viewPage(${viewControl.prePageNum})" style="cursor: hand" width="103" height="38" border="0" />
                      </td>
                    </tr>
                    </c:if>
                    
                    <tr>
                      <td align="center">&nbsp;</td>
                    </tr>
                    
                    <c:if test="${viewControl.showNextButton==true}">
                    <tr>
                      <td height="40" align="center">
                      <img src="../images/btn_x.gif" onclick="javascript:viewPage(${viewControl.nextPageNum})" style="cursor: hand" width="103" height="38" border="0" /></td>
                    </tr>
                    </c:if>
                    
                  </table>
              </div>
              <form action="../exam/doExam.jhtml" method="post" name="examForm" id="examForm">
				<input type="hidden" name="currentPageNum" id="currentPageNum" value="${currentPageNum}"/>
				<input type="hidden" name="nextPageNum" id="nextPageNum" value="0"/><!-- 默认值为0 -->
				<!--  <input type="hidden" value="${itemType}" name="itemType" id="itemType" />-->
				<input type="hidden" value="" name="userAnswers" id="userAnswers" />
				<input type="hidden" value="" name="nextAct" id="nextAct" />
				<input type="hidden" name="time" id="time" value="" />
				<input type="hidden" name="filterType" id="filterType" value="0"/>
				<input type="hidden" name="testPass" id="testPass" value="0"/>
			  </form>