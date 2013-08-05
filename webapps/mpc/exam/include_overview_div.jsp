<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="floatBoxs" id="sign02"  style='z-index:33;display:none;'> 
	<h3>确认交卷</h3> 
	<div class="floatBox">
		<h1>你确定交卷吗？本卷剩余时间<span id="leftTimeSpanA">${viewControl.actualTimeStr2}</span></h1> 
		<h2>本卷共${viewControl.examItemNum}道题，目前${viewControl.undoItemNum}道题未做；${viewControl.markItemNum}道题标记疑问</h2> 
		<div class="content box1"> 
			<ul> 
			<li>点击“确认交卷”将提交本卷，并得到答题的结果。</li> 
   		 	<li>点击“继续答题”将直接转到未做题、疑问题。</li> 
			</ul>
		</div>
		<div class="btn">		
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"  
				onClick="javascript:this.disabled='true';document.getElementById('sign02_button_2').disabled='true';overViewSubmit();">确认交卷</button></span>		 
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign02_button_2"
				onClick="javascript:doit(sign01);doit(sign02)">继续答题</button></span>		 
	        <div class="clear"></div> 
		</div>
	</div>
</div>

<div class="floatBoxs" id="sign05"  style='z-index:33;display:none;'> 
	<h3>确认退出</h3>
	<div class="floatBox">
		<h1>你确定退出吗？本卷剩余时间<span id="leftTimeSpanA">${viewControl.actualTimeStr2}</span></h1> 
		<h2>本卷共${viewControl.examItemNum}道题，目前${viewControl.undoItemNum}道题未做；${viewControl.markItemNum}道题标记疑问</h2> 
		<div class="content box1"> 
			<ul> 
				<li>点击“确认退出”将不存储当前答题数据，并返回首页。</li> 
	    		<li>点击“继续答题”将回到答题页面。</li> 
			</ul>
		</div>
		<div class="btn">
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"	              	
	              	<c:if test="${viewControl.weaknessEnhance}">
		              	<c:if test="${viewControl.examNodeIns==null}">
		              	onclick="location.href='../report/reportMain.jhtml?showListType=0'"
		              	</c:if>
		              	<c:if test="${viewControl.examNodeIns!=null}">
		              		<c:if test="${viewControl.examNodeIns.node.nodeType == 'GROUP'}">
		              		onclick="location.href='../report/reportMain.jhtml?showListType=0&nodeInstanceId=${viewControl.examNodeIns.id}&orderNum=${viewControl.examNodeIns.node.orderNum}'"
		              		</c:if>
		              		<c:if test="${viewControl.examNodeIns.node.nodeType != 'GROUP'}">
		              		onclick="location.href='../report/reportMain.jhtml?showListType=4&nodeInstanceId=${viewControl.examNodeIns.id}&hisId=${viewControl.historyTestStatusId}'"
		              		</c:if>
		              	</c:if>
	              	</c:if> 			
				<c:if test="${viewControl.extPractice}">
				onClick="javascript:location.href='../report/reportMain.jhtml?showListType=5&nodeInstanceId=${viewControl.examNodeIns.node.id}'"
				</c:if> 
				<c:if test="${!viewControl.extPractice && !viewControl.weaknessEnhance}">
				onClick="javascript:location.href='../web/loadSessionVar!mpc.jhtml'"
				</c:if> 
				>确认退出</button></span>
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
				onClick="javascript:doit(sign01);doit(sign05)">继续答题</button></span>
	        <div class="clear"></div>
		</div>
	</div>
</div>

<!--End div--> 