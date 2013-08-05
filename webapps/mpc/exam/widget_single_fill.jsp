<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<!--<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
	<link href="../css/thickbox.css" rel="stylesheet" type="text/css" />-->
	<script language="javascript" src="../js/jquery-1.3.2.min.js"></script>
	<script language="javascript" src="../js/tandiv.js"></script>
	<script language="javascript" src="../js/exam.js"></script>
	<script language="javascript" src="../js/thickbox.js"></script>
	<script type="text/javascript" src="../../admin/js/jquery.form.js"></script>	
	<script language="JavaScript" type="text/javascript">
	<!--
	
	jQuery(function($) {
		$(".promptContent").each(function(i, n) {changePromptImg(i, this); });
		$(".normal").each(function(){ AutoAdjustWidth(this); });
		$(".formula").each(function(){ ReplaceMathInput(this, "${viewControl.showModel}");});
		$(".correct").after("<img src='../images/true.gif' width='16' height='16' class='correctImg' />");
		$(".error").after("<img src='../images/false.gif'  width='16' height='16' class='errorImg' />");
	})
	//-->
	</script>
</head>
<body>
	 <c:if test="${viewControl.compareAnswerPolicy eq 1}">
		<jsp:include page="include_compare_answer_div.jsp"></jsp:include>
	</c:if>
	<input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
	<c:forEach items="${currentPage.items}" var="item" varStatus="status">
       	<c:set var="xItem" value="${item}" scope="request"/>
       	<c:set var="xStatus" value="${status}" scope="request"/>
       	<c:set var="xItemType" value="fill" scope="request"/>
		<c:set var="myItemType" value="sigleFill" scope="request"/>
       	<input type="hidden" name="mapkey${status.index}" id="mapkey${status.index}" value="${item.id}"/>
       	<div class="content_box"> 
       		<div class="content_titer"> 
	        	<jsp:include page="include_item_title.jsp"></jsp:include>
	        </div>
	        <div class="clear"></div>
	        <div class="answers">
	        	<p align="left" >${item.examProperty.contentView}</p>		        	
       			<div class="blank6"></div>        			
	        	<jsp:include page="include_prompt.jsp"></jsp:include>
	        </div>
	        <jsp:include page="include_widget.jsp"></jsp:include>
		</div> 
       	<div class="blank9"></div> 
	</c:forEach>

	<script language="javaScript" type="text/javascript">
	
		if (${viewControl.compareAnswerPolicy eq 1}) {
			$(".errorImg").live("click", function() { setValForCompareAnswer_single_file(this); compareAnswer(this); } );
		}
		changeWidth("${width}");
	</script>
</body>
</html>