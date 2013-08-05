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
        	<h1>倒计时</h1>
        	<h2 class="f24px fB Arial cOra"><span id="timeSpan">${viewControl.examTimeStr2}</span></h2>        
        </div> 
        <div class="box6"></div> 
        <div class="bnt_box_r"> 
	        <div class="Hold_exet1" onmouseout="this.className='Hold_exet1';" onmouseover="this.className='Hold_exet2';">
	        	<a href="#" onclick="javascript:setSpanStr();submitPaper2();checkit('sign01');checkit('sign03');">
	        		<span>暂存退出</span>
	        	</a>
	        </div>
	        <div class="bnt_sub1" onmouseout="this.className='bnt_sub1';" onmouseover="this.className='bnt_sub2';">
	        	<a href="#" onclick="javascript:setSpanStr();submitPaper2();checkit('sign01');checkit('sign02');">
	        		<span>交卷</span>
	        	</a>
	        </div>
	        <div class="bnt_stop1" onmouseout="this.className='bnt_stop1';" onmouseover="this.className='bnt_stop2';">
	        	<a href="#" onclick="javascript:pause();checkit('sign01');checkit('sign05');pauseTime(60*5);"><span>暂停答题</span></a> 
	        </div>
        </div>
        <div class="box6"></div> 
        <ul> 
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton"
                	<c:if test="${viewControl.filterType==1}">checked</c:if>
                	onclick="javascript:filterItem(1)"/>
                                          查看所有题(<span name="All">${viewControl.examItemNum}</span>)	        	
	        </li> 
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton"  
                	<c:if test="${viewControl.filterType==3}">checked</c:if>
                	<c:if test="${viewControl.undoItemNum==0}">disabled="disabled"</c:if>
                	onclick="javascript:filterItem(3)"/>
                                          只答未做题(<span name="UndoItemNum" id="UndoItemNum">${viewControl.undoItemNum}</span>)  
	        </li> 
	        <li>
	        	<input type="radio" name="radiobutton"  value="radiobutton"  
                	<c:if test="${viewControl.filterType==4}">checked</c:if>
                	<c:if test="${viewControl.markItemNum==0}">disabled="disabled"</c:if>
                	onclick="javascript:filterItem(4)"/>
                                          只答疑问题(<span name="MarkItemNum" id="MarkItemNum">${viewControl.markItemNum}</span>)   
	        </li>
        </ul>
        <div class="box6"></div> 
        <div class="bnt_box_r"> 
        	<div class="bnt_review1" onmouseout="this.className='bnt_review1';" onmouseover="this.className='bnt_review2';">
        		<a href="#" onclick="javascript:overView();"><span>浏览全卷</span></a>
        	</div>
        </div>
        
        
        <div>
        	<form action="doExam.jhtml" method="post" name="examForm" id="examForm">
	        	<c:if test="${viewControl.showAnswer}">
		        	<input type="checkbox" name="ifPass" id="ifPass" onclick="enableSelect(this.checked)" value="0"/>启动流程直通车:	        
					<select name="testPass" id="testPass" disabled >
						<option value ="0" ></option>
		  				<option value ="1">不通过</option>
		  				<option value ="2">通过</option>
					</select>
				</c:if>				
				<c:if test="${!viewControl.showAnswer}">
					<input type="hidden" value="0" name="testPass" id="testPass" />
				</c:if>
				<input type="hidden" name="currentPageNum" id="currentPageNum0" value="${currentPageNum}"/>
				<input type="hidden" name="nextPageNum" id="nextPageNum" value="0"/><!-- 默认值为0 -->
				<input type="hidden" value="" name="userAnswers" id="userAnswers" />
				<input type="hidden" value="" name="nextAct" id="nextAct" />
				<input type="hidden" name="time" id="time" value="" />
				<input type="hidden" name="filterType" id="filterType" value="0"/>
			</form>			
		</div>
						
		</div></div></div></div></div></div>
</div>

<script type="text/javascript">
	function enableSelect(enable){
		var testPass=document.getElementById("testPass");
 		if(enable){
			testPass.disabled=false;			
	 	}else{		 	
	 		testPass.disabled=true;	
		}
	}

	initTime(false, ${viewControl.actualTime});
</script>