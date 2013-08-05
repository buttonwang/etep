<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
              
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm"><img src="../images/info.gif" align="absbottom" />${viewControl.flowName}——${viewControl.examName}（${viewControl.examItemNum}题/${viewControl.examValue}分，限时${viewControl.examTimeStr2}
                  <c:if test="${viewControl.isWeaknessEnhance==false}">  
                    	，正确率要求：
                    	<c:if test="${viewControl.requireRightRate>0}">
					 		${viewControl.requireRightRate}%
					    </c:if>
						<c:if test="${viewControl.requireRightRate==0}">
					 		--%
						</c:if>
                  </c:if>
                    
                   ）。</td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
                    <tr>
                      <td height="30"><img src="../images/icon_edit.gif" width="16" height="15" align="texttop" /> 已答的题&nbsp;&nbsp;&nbsp;&nbsp; <img src="../images/icon_yiwen.gif" width="16" height="16" align="top" /> 标记疑问的题  &nbsp;&nbsp;&nbsp;&nbsp;灰色题号表示未答的题 </td>
                  </tr>
                  </table>
                  <table width="100%" cellpadding="3" cellspacing="0"  class="listab" style="margin-top:0px;">

                    
				  <c:forEach items="${viewControl.sectionList}" var="section" varStatus="status">
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
												 >[<a href="../exam/showExam.jhtml?pageNum=${page.pageNum}" title="提示">${subItem.itemNum}</a>]</span>
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
													</tr><tr>
												</c:if>
												<c:set var="lengthCount" value="${lengthCount+1}"></c:set>
											</c:if>
										
										</c:forEach>
									</c:if>

									<c:if test="${page.hasSubItem==false}">						
										<td width="70">
										<span 
										<c:if test="${page.isDone[status2.index]==1}" var="isDone">
											class="t01" 
										</c:if>
										<c:if test="${isDone==false}">
											class="t00" 
										</c:if>							  
										 >[<a href="../exam/showExam.jhtml?pageNum=${page.pageNum}" title="提示">${item.itemNum}</a>]</span>
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
											</tr><tr>                      					
										</c:if>
										<c:set var="lengthCount" value="${lengthCount+1}"></c:set>									
									</c:if>																
								</c:forEach>
							</c:forEach>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </c:forEach>
              </table>
              </td>
              <td width="114" align="right" valign="top"><!--<div class="xScrollStick d1">
	<a href="http://www.163.com">俺是浮动条</a>
</div>-->		                <div id="INTRO_DIV" style="WIDTH: 120px; PADDING-TOP: 5px; position:relative; TOP: 0px;" class="d1">
				
				<c:if test="${viewControl.showModel==1}">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><div class="clocks"><span class="cWhite">${viewControl.actualTimeStr2}</span></div></td>
                    </tr>
                    
                    <tr>
                      <td height="60" align="center">
                      <a href="#" onclick="javascript:doit(sign01);doit(sign03)">
                      <img src="../images/btn_jj.gif" name="image231" width="103" height="38" border="0" id="image23" />
                      </a>
                      </td>
                    </tr>

                    <tr>
                      <td align="left"><br />
                        ● 本卷总题数：${viewControl.examItemNum}<br />
                        ● 未答题数：${viewControl.undoItemNum}<br />
                        ● 标记疑问：${viewControl.markItemNum}<br /></td>
                    </tr>
                    <tr>

                      <td height="60" align="center">
                      <a href="../exam/showExam.jhtml?pageNum=${viewControl.currentPageNum}">
                      <img src="../images/bnt_paly.gif" name="image23" width="103" height="38" border="0" id="image23" />
                      </a>
                      </td>
                    </tr>
                  </table>
                 </c:if>
                 
                 <c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
                   <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td align="center">本卷限时：${viewControl.examTimeStr2 }<br />
                          实际用时：<br />
                          <strong><font color="#FF6600" style="font-size:24px;">${viewControl.spendTimeStr }</font></strong></td>
                      </tr>
                      <c:if test="${showNextButton==true}">
                      <tr>
                        <td height="60" align="center"><a href="#" onclick="javascript:window.location='${trainUrl}'"><img src="../images/btn_xl.gif" name="Image23" width="103" height="38" border="0" id="Image23" /></a></td>
                      </tr>
                     </c:if>
                      <tr>
                        <td height="60" align="left">● 本卷总题数：${viewControl.examItemNum }<br />
													 ● 错题总数：${viewControl.errorItemNum }<br />
													 ● 未答题数：${viewControl.undoItemNum } <br />
													 ● 标记疑问：${viewControl.markItemNum }</td>
                      </tr>
                      <tr>
                        <td height="60" align="center"><a href="../exam/showExam.jhtml?pageNum=0"><img src="../images/bnt_ztll.gif" name="Image23" width="103" height="38" border="0" id="Image23" /></a></td>
                      </tr>
                      
                      <tr>
                        <td height="60" align="center">
                        <a
                        <c:if test="${viewControl.showModel!=3}">
                      		href="../web/loadSessionVar!ky.jhtml"
                      	</c:if>
                      	<c:if test="${viewControl.showModel==3}">
                      		href="../web/loadSessionVar!ky.jhtml?nodeInstanceId=${viewControl.currentTestStatusId}&jumpType=1&showListType=2"
                      	</c:if>
                      	>
                         <!--  onclick="javascript:window.location='../web/loadSessionVar.jhtml'"-->
                         <img src="../images/btn_exte.gif" name="Image23" width="103" height="38" border="0" id="Image23" /></a></td>
                      </tr>
                      
                      <tr>
                        <td align="center">&nbsp;</td>
                      </tr>
                    </table>
                   
                 </c:if>
                  
                  
              </div>
                </td>
            </tr>
          </table>
        </div>
        </div>
    </div>
    </div>
    <div class="clear"> </div>
</div>