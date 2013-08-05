<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="contentLayout" class="wm950"> 
  <!--Satr left-->
  <div class="content_left wm790 left"> 
  		<div class="content_bg"> 
    	<div class=ye_r_t> 
		<div class=ye_l_t> 
		<div class=ye_r_b> 
		<div class=ye_l_b> 
        <div class="content">
        	<jsp:include page="include_overview_div.jsp"></jsp:include>
			<jsp:include page="include_page_title.jsp"></jsp:include>	
	        <div class="title">
		        <ul>
		        	<li class="answer left">已答题</li> 
		            <li class="query1 left">标记疑问的题</li>
		            <li  class="left"><span class="cDEGray">[灰色题号]</span>未答的题</li>
		            <li class="right cDGray">
		            	<img src="../images/guanzhu_ico.gif" width="16" height="16" align="absmiddle"/>
		            	点击题号可直接${viewControl.showModel eq 1?'答题':'回顾 '}
		            </li>
		        </ul>
		        <div class="clear"></div>
	        </div>
        	<div class="content_box"> 
	        	<div class="content_tit f14px"><b>试题</b></div>
	        	<div class="contenter">
	        	<c:forEach items="${viewControl.sectionList}" var="section" varStatus="status">
                	<h3><b>${section.title}：</b><span class="cDGray">${section.instruction}</span></h3>
                	<h4 class="blank6"></h4>
                	<ul>
                    <c:forEach items="${section.pages}" var="page" varStatus="status1">
						<c:forEach items="${page.items}" var="item" varStatus="status2">
							<li>
								<a href="showExam.jhtml?pageNum=${page.pageNum}"
									<c:if test="${item.examProperty.isDone}" var="isDone"> class="default"</c:if>													
									<c:if test="${!item.examProperty.isDone}" var="isDone"> class="cnone"  </c:if>
								>[${item.itemNum}]</a>								
								<c:if test="${item.examProperty.isDone}">
									<img src="../images/answer.gif" width="16" height="16" alt="已答" /> 
								</c:if>
								<c:if test="${item.examProperty.isMark}">
									<img src="../images/query.gif" width="16" height="16" alt="疑问" />
								</c:if>
								<c:if test="${viewControl.showModel!=1}">
									<c:if test="${item.examProperty.isRight}">
										<img src="../images/true.gif" width="16" height="16" alt="答对" /> 
									</c:if>
									<c:if test="${!item.examProperty.isRight}">
										<img src="../images/false.gif" width="16" height="16" alt="答错" /> 
									</c:if>
								</c:if>																	
							</li>
						</c:forEach>
					</c:forEach>
					</ul>
					<div class="clear blankW_9"></div>
				</c:forEach>
	        	</div>
        	</div> 
        	<div class="blank9"></div> 
        
        </div> 
		</div></div></div></div></div>
  </div>
  <!--End left--> 
  
  <!--Begin right-->
  <div id="INTRO_DIV" style="position:relative; TOP:0px;" class="content_right wm140 right">
  	<c:if test="${viewControl.showModel==1}"> 
    	<div class="content_bg"> 
    	<div class=yr_r_t> 
		<div class=yr_l_t> 
		<div class=yr_r_b> 
		<div class=yr_l_b> 
        <div class="con_r"> 
		<div class="time"> 
        	<h1>倒计时</h1>
        	<h2 class="f24px fB Arial cOra">${viewControl.actualTimeStr2}</h2>			
        </div> 
        <div class="box6"></div> 
        <div class="bnt_box_r"> 
        <c:if test="${viewControl.entranceExam}">
        <div class="bnt_sub1" onmouseout="this.className='bnt_sub1';" onmouseover="this.className='bnt_sub2';">
	      	<a href="#" onclick="javascript:checkit('sign01');checkit('sign02');">
	        	<span>交卷</span>
	        </a>
	    </div>
        </c:if>
        <div class="Exet_exam1" onmouseout="this.className='Exet_exam1';" onmouseover="this.className='Exet_exam2';">
        	<a href="#" onclick="javascript:checkit('sign01');checkit('sign05');">
        		<span>退出答题</span>
        	</a>
        </div>
        </div> 
        <div class="box6"></div> 
        <ul> 
        <li>•本卷总题数：${viewControl.examItemNum}</li>
        <li>•未答题数：${viewControl.undoItemNum}</li>
        <li>•标记疑问：${viewControl.markItemNum}</li>
        </ul> 
        <div class="box6"></div> 
        <div class="bnt_box_r"> 
	        <div class="bnt_start1" onmouseout="this.className='bnt_start1';" onmouseover="this.className='bnt_start2';">
	        	<a href="showExam.jhtml?pageNum=${viewControl.currentPageNum}"><span>开始答卷</span></a>
	        </div>
        </div>
		</div></div></div></div></div></div>
	</c:if>
	
	<c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
    	<div class="content_bg">
    	<div class="yr_r_t">
        <div class="yr_l_t">
        <div class="yr_r_b">
        <div class="yr_l_b">
        <div class="con_r">
        <div class="time">
        	<h2 class="f12px Arial">本卷实际用时</h2>
            <h2 class="f24px fB Arial cOra">${viewControl.examResultProperty.spendTimeStr}</h2>
        </div>
        <div class="box6"></div>
        <div class="bnt_box_r">
        <div class="Exte_review1" onmouseout="this.className='Exte_review1';" onmouseover="this.className='Exte_review2';">
        	<a 
	   		 <c:if test="${viewControl.showModel!=3 }">
	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	href="../report/reportMain.jhtml?showListType=0"
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		href="../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}"
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		href="../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}"
		              		</c:if>
		              	</c:if>
	              	</c:if> 
			 	  	<c:if test="${viewControl.extPractice}">
						href="../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}"
					</c:if>
					<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">  
						href="../web/loadSessionVar!mpc.jhtml"
					</c:if>
	         </c:if>
	         <c:if test="${viewControl.showModel==3}">
	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	href="../report/reportMain.jhtml?showListType=0"
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		href="../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}"
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		href="../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}"
		              		</c:if>
		              	</c:if>
	              	</c:if> 
	         		<c:if test="${viewControl.extPractice}">
					href="../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}"
					</c:if> 
					<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">                 
                 	href="../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.currentTestStatusId}&hisId=${viewControl.historyTestStatusId}"
					</c:if>
	         </c:if>
        	>
        	<span>退出回顾</span>
         	</a>
        </div>
        <c:if test="${!viewControl.extPractice}"> 
        <div class="Get_task1" onmouseout="this.className='Get_task1';" onmouseover="this.className='Get_task2';">
        	<a href="goExam.jhtml?nodeInstanceId=${nextNodeId}"><span>进入新任务</span></a>
        </div>
        </c:if>
        </div>
        <div class="box6"></div>
        <div class="bnt_box_r">
        	<ul>
            	<li>•本卷总题数：${viewControl.examItemNum}</li>
                <li>•错题总数：${viewControl.examResultProperty.errorCount}</li>
                <li>•未答题数：${viewControl.examResultProperty.undoCount}</li>
                <li>•标记疑问：${viewControl.examResultProperty.markCount}</li>
       		</ul>
        </div>
       	<div class="box6"></div>
       	<div class="bnt_box_r">
           	<div class="One_review1" onmouseout="this.className='One_review1';" onmouseover="this.className='One_review2';">
           		<a href="showExam.jhtml?pageNum=0"><span>逐题回顾</span></a>
           	</div>
        </div>
        </div></div></div></div></div></div>
	</c:if>
	
  </div>
  <!--End right--> 
  <div class="clear"></div> 
</div>