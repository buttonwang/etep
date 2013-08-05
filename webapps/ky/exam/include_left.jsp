<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="INTRO_DIV"
	style="WIDTH: 120px; PADDING-TOP: 5px; position: relative; TOP: 0px;"
	class="d1">
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<div class="clocks"><span id="timeSpan" class="cWhite">${viewControl.examTimeStr2}</span></div>
		</td>
	</tr>
	<tr>
		<td height="60" align="center"><img src="../images/btn_jj.gif"
			onclick="javascript:setSpanStr();submitPaper2();checkit('sign01');checkit('sign03');"
			style="cursor: hand" name="Image23" width="103" height="38"
			border="0" id="Image23" /></td>
	</tr>
	<tr>
		<td height="60" align="center"><img src="../images/btn_zt.gif"
			onclick="javascript:setSpanStr();submitPaper2();checkit('sign01');checkit('sign02');"
			style="cursor: hand" width="103" height="38" border="0" /></td>
	</tr>
	<tr>
		<td height="60" align="center"><img src="../images/btn_ll.gif"
			onclick="javascript:overView()" style="cursor: hand" width="103"
			height="38" border="0" /></td>
	</tr>
	<tr>
		<td align="left"><br />
		<input type="radio" name="radiobutton" value="radiobutton"
			<c:if test="${viewControl.filterType==1}">
                     	checked
                     </c:if>
			onclick="javascript:filterItem(1)" /> 查看所有题(<span name="All">${viewControl.examItemNum
		}</span>)<br />
		<input type="radio" name="radiobutton" value="radiobutton"
			<c:if test="${viewControl.filterType==4}">
                     	checked
                     </c:if>
			onclick="javascript:filterItem(4)" /> 只答疑问题(<span name="MarkItemNum"
			id="MarkItemNum">${viewControl.markItemNum }</span>)<br />
		<input type="radio" name="radiobutton" value="radiobutton"
			<c:if test="${viewControl.filterType==3}">
                     	checked
                     </c:if>
			onclick="javascript:filterItem(3)" /> 只答未做题(<span name="UndoItemNum"
			id="UndoItemNum">${viewControl.undoItemNum }</span>)<br />
		</td>
	</tr>
	
	<tr>
		<td height="10" align="center"></td>
	</tr>
	
	<c:if test="${viewControl.showPreButton==true}">
		<tr>
			<td height="40" align="center"><img src="../images/btn_s.gif"
				onclick="javascript:showPage(${viewControl.prePageNum})"
				style="cursor: hand" width="103" height="38" border="0" /></td>
		</tr>
	</c:if>

	<c:if test="${viewControl.showNextButton==true}">
		<tr>
			<td height="40" align="center"><img src="../images/btn_x.gif"
				onclick="javascript:showPage(${viewControl.nextPageNum})"
				style="cursor: hand" width="103" height="38" border="0" /></td>
		</tr>
	</c:if>

	<tr>
		<td height="10" align="center"></td>
	</tr>

	<form action="../exam/doExam.jhtml" method="post" name="examForm" id="examForm">
	<c:if test="${viewControl.showAnswer}">
	<tr>
		<td height="40" align="center"><input type="checkbox"
			name="ifPass" id="ifPass" onclick="enableSelect(this.checked)"
			value="0" />启动流程直通车:
		<td>
	</tr>
	<tr>
		<td height="40" align="center">		
		<select name="testPass" id="testPass" disabled>
			<option value="0"></option>
			<option value="1">不通过</option>
			<option value="2">通过</option>
		</select>
		</td>
	</tr>
	</c:if>
</table>

<c:if test="${!viewControl.showAnswer}">
	<input type="hidden" value="0" name="testPass" id="testPass" />
</c:if>
<input type="hidden" name="currentPageNum" id="currentPageNum0" value="${currentPageNum}" />
<input type="hidden" name="nextPageNum" id="nextPageNum" value="0" />
<input type="hidden" value=""  name="userAnswers" id="userAnswers" />
<input type="hidden" value=""  name="nextAct" id="nextAct" /> 
<input type="hidden" value=""  name="time" id="time"  /> 
<input type="hidden" value="0" name="filterType" id="filterType" />

<!-- 默认值为0 -->
<!--  <input type="hidden" value="${itemType}" name="itemType" id="itemType" />-->
</form>
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
	initTime(false,${viewControl.actualTime});
</script>