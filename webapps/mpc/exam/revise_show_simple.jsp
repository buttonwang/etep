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
<title>试题回顾</title>
<link rel="stylesheet" href="../css/style_blue.css" type="text/css" />
<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" /> 
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/exam.js"></script>
<script type="text/javascript">
	$(function(){
		
	});
</script>
</head>

<body>
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
</script>
</body>
</html>