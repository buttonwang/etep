<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
<object ID=MathPlayer CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"></object>
<?import namespace = m implementation = "#MathPlayer"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>试题审校</title>
<link rel="stylesheet" href="../css/style_blue.css" type="text/css" />
<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" /> 
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../js/exam.js"></script>
<script type="text/javascript">
	function lock(){
		$("#loading").show();
		$("#hasLocked").val("yes");
	}
	function releaseLock(){
		$("#loading").hide();
		$("#hasLocked").val("no");
	}
	function checkLock(){
		if($("#hasLocked").val()=="yes"){
			alert("正在保存数据！请稍候再执行其它操作...");
			return true;
		}
		return false;
	}
	function reviseAnswer() {
		if(checkLock()) return false;
		var form1=document.getElementById("examForm");
		form1.setAttribute("action","reviseExam.jhtml");
		var userAnswers=document.getElementById("userAnswers");
		if(userAnswers!=null){
			userAnswers.value = makeAnswerStr();
		}
		lock();
		form1.submit();
	}

	function revised() {
		if(checkLock()) return false;
		lock();
		$.post(
		  "reviseExam!revised.jhtml",
		  function() {
			releaseLock();
			if ("${ipidsVO.next}") nextAttention();
			else {
				$("#reviseStatusSpan").html("已通过");
				alert("审校已通过，若要继续审校，请返回试题列表并翻到下一页。");
			}
		  }
		);
	}

	function editItem() {
		window.open('../../admin/itembank/item!edit.jhtml?id=${itemId}', '_blank');
		//window.location.href="";
	}
	//跳转到上一题
	//AUTHOR: L.赵亚
	function preAttention(){
		<c:if test="${! (empty ipidsVO || empty ipidsVO.prev) }">
		if(checkLock()) return false;
		lock();
		window.location.href="examWidget!itemReviseWidget.jhtml?itemId=${ipidsVO.prev}";
		</c:if>
	}
	//跳转到下一题
	//AUTHOR: L.赵亚
	function nextAttention(){
		<c:if test="${! (empty ipidsVO || empty ipidsVO.next) }">
		if(checkLock()) return false;
		lock();
		window.location.href="examWidget!itemReviseWidget.jhtml?itemId=${ipidsVO.next}";
		</c:if>
	}
	//跳转回列表显示页面
	//AUTHOR: L.赵亚
	function backToList(){
		<c:if test="${! ( empty ipidsVO )}">
		if(checkLock()) return false;
		lock();
		window.location.href="examWidget!toReviseList.jhtml";
		</c:if>
	}
	function refreshItem() {
		if(checkLock()) return false;
		lock();
		window.location.href="examWidget!itemReviseWidget.jhtml?itemId=${itemId}";
	}
	$(function(){
	});
</script>
</head>

<body>
					<input type="hidden" value="no" name="hasLocked" id="hasLocked" />
<div id="contentLayout" class="wm650">
	<div class="content_left wm650">
		<div class="content_bg">
			<div class="yr_bg2">
			<div class=ye_r_t>
			<div class=ye_l_t>
			<div class=ye_r_b>
			<div class=ye_l_b>
			<div class="content">
				<!--试题层开始-->
				<div id="itemdiv">
					<jsp:include page="${widgetPage}.jsp"></jsp:include>
				</div>
				<div>
		        	<form action="doExam.jhtml" method="post" name="examForm" id="examForm">	        	
						<input type="hidden" value="" name="userAnswers" id="userAnswers" />
					</form>
				</div>
				<!--试题层结束-->
  				<div id="loading" style="position:absolute;left:300px;display:none;">
					<img src="../images/loading.gif" />
						<font color="blue">正在提交信息！请稍候...</font></div>
  				<div>
  					<c:if test="${viewControl.showModel ne 9}">
  						<jsp:include page="include_revise_record.jsp"></jsp:include>
  					</c:if>
  				</div>
  				
		        <div class="bnt_box1">
	            	<span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	            		onclick="reviseAnswer()">校验答案</button></span>
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
		              ${viewControl.showModel eq 1?"disabled":""} onclick="revised()">审校通过</button></span>
		            <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                  ${sessionScope.teacherLogin eq "yes"?"disabled":""} onclick="editItem()">编辑试题</button></span>    
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                	 onclick="refreshItem()">刷新试题</button></span>    
	            	<span class="pager" id="preAttention"><button class="pager1" onmouseout="this.className='pager1';"  onmouseover="this.className='pager2';"
	            		onclick="preAttention()" ${ (empty ipidsVO || empty ipidsVO.prev) ? "disabled":"" } >上一题</button></span>
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
		                onclick="backToList()" ${ (empty ipidsVO) ? "disabled":"" }>返回试题列表</button></span>
		            <span class="pager" id="nextAttention"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                	onclick="nextAttention()" ${ (empty ipidsVO || empty ipidsVO.next) ? "disabled":"" } >下一题</button></span>    
	                
		        </div>
			</div>
			</div>
			</div>
			</div>
			</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
<script type="text/javascript">
	if (${empty viewControl.itemRevise.itemReviseAnswers} && ${viewControl.showModel eq 1}) {
		$(".answerImg").hide();
		$(".analysImg").hide();
		$("div[id^='viewAnaly']").hide();
		$("div[id^='viewAnswer']").hide();
	}
	$(".errorImg").die("click");
</script>
</body>
</html>