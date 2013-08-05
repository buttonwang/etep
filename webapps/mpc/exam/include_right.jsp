<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="INTRO_DIV" style="position:relative; TOP:0px;" class="content_right wm140 right"> 
    	<div class="content_bg"> 
    	<div class=yr_r_t> 
		<div class=yr_l_t> 
		<div class=yr_r_b> 
		<div class=yr_l_b> 
        <div class="con_r"> 
        <div class="time">
        	<h2 class="f12px Arial">本卷实际用时</h2>
            <h2 class="f24px fB Arial cOra">${viewControl.examResultProperty.spendTimeStr}</h2>
        </div>
        <div class="box6"></div> 
        <div class="bnt_box_r"> 
        	<div class="Exte_review1" onmouseout="this.className='Exte_review1';" onmouseover="this.className='Exte_review2';"
            	 onclick=
            	 <c:if test="${viewControl.showModel!=3}">
	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	'location.href="../report/reportMain.jhtml?showListType=0"'
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		'location.href="../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}"'
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		'location.href="../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}"'
		              		</c:if>
		              	</c:if>
	              	</c:if> 
				 	<c:if test="${viewControl.extPractice}">
					'location.href="../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}"'
					</c:if>
					<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">  
                 	'location.href="../web/loadSessionVar!mpc.jhtml"'
					</c:if>   
                 </c:if>
                 <c:if test="${viewControl.showModel==3}">
	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	'location.href="../report/reportMain.jhtml?showListType=0"'
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		'location.href="../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}"'
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		'location.href="../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}"'
		              		</c:if>
		              	</c:if>
	              	</c:if> 
				 	<c:if test="${viewControl.extPractice}">
					'location.href="../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}"'
					</c:if> 
					<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">                 
                 	'location.href="../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.currentTestStatusId}&hisId=${viewControl.historyTestStatusId}"'
					</c:if>    
                 </c:if>  >                
            	 <a href="#"><span>退出回顾</span></a>
            </div>
            </div> 
        <div class="box6"></div>
        <div class="bnt_box_r">
          <ul> 
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton"  
                	<c:if test="${viewControl.filterType==1}">checked</c:if>
                	onclick="javascript:filterItem(1,2)"/>
                                          查看所有题(<span name="All">${viewControl.examItemNum}</span>)	        	
	        </li> 
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton" 
                	<c:if test="${viewControl.filterType==2}">checked</c:if>
                	<c:if test="${viewControl.examResultProperty.errorCount==0}">disabled="disabled"</c:if>
                	onclick="javascript:filterItem(2,2)"/>
                                          只看答错题(<span name="ErrorItemNum" id="ErrorItemNum">${viewControl.examResultProperty.errorCount}</span>)	        	
	        </li>
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton"  
                	<c:if test="${viewControl.filterType==3}">checked</c:if>
                	<c:if test="${viewControl.examResultProperty.undoCount==0}">disabled="disabled"</c:if>
                	onclick="javascript:filterItem(3,2)"/>
                                          只看未答题(<span name="UndoItemNum" id="UndoItemNum">${viewControl.examResultProperty.undoCount}</span>)  
	        </li>
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton"
                	<c:if test="${viewControl.filterType==4}">checked</c:if>
                	<c:if test="${viewControl.examResultProperty.markCount==0}">disabled="disabled"</c:if>
                	onclick="javascript:filterItem(4,2)"/>
                                          只看疑问题(<span name="MarkItemNum" id="MarkItemNum">${viewControl.examResultProperty.markCount}</span>)
	        </li>
          </ul>
        </div>
        <div class="box6"></div> 
        <div class="bnt_box_r">
        	<div class="Review_paper1" onmouseout="this.className='Review_paper1';" onmouseover="this.className='Review_paper2';" 
        		onclick="javascript:overView();">
        		<a href="#"><span>回顾全卷</span></a>
        	</div>
        	<!-- V2.0 实现 
            <div class="View_guide1" onmouseout="this.className='View_guide1';" onmouseover="this.className='View_guide2';" 
            	onclick="location.href='#'">
            	<a href="#"><span>查看学习指导</span></a>            	
            </div>
        	-->
        </div>
        
        <div>
        	<form action="doExam.jhtml" method="post" name="examForm" id="examForm">	        	
				<input type="hidden" name="currentPageNum" id="currentPageNum0" value="${currentPageNum}"/>
				<input type="hidden" name="nextPageNum" id="nextPageNum" value="0"/><!-- 默认值为0 -->
				<input type="hidden" value="" name="userAnswers" id="userAnswers" />
				<input type="hidden" value="" name="nextAct" id="nextAct" />
				<input type="hidden" name="time" id="time" value="" />
				<input type="hidden" name="filterType" id="filterType" value="0"/>
				<input type="hidden" name="testPass" id="testPass" value="0"/>
			</form>
		</div>
						
		</div></div></div></div></div></div>
</div>
