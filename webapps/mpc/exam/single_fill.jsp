<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${viewControl.flowName}</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/jquery-1.3.2.min.js"></script>
<script language="javascript" src="../js/floating.js"></script>
<script language="javascript" src="../js/tandiv.js"></script>
<script language="javascript" src="../js/exam.js"></script>
<link href="../css/thickbox.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/thickbox.js"></script>
<script type="text/javascript" src="../js/reInitIfameHeight.js"></script>

<script language="JavaScript" type="text/javascript">
	var processInstanceId="${viewControl.processInstance.id}";

	jQuery(function($) {
		FloatingDIV();
		$(".promptContent").each(function(i, n) {changePromptImg(i, this); });
		$(".normal").each(function(){ AutoAdjustWidth(this); });
		$(".formula").each(function(){ ReplaceMathInput(this, "${viewControl.showModel}");});		
		$(".correct").after("<img src='../images/true.gif' width='16' height='16' class='correctImg' />");
		$(".error").after("<img src='../images/false.gif'  width='16' height='16' class='errorImg' />");
	})
</script>
</head>
<body>

<!--页面头部-->
<jsp:include page="include_head.jsp"></jsp:include>
		
 <!-- 页面主体 -->
<div id="contentLayout" class="wm950">
  <!--Start left--> 
  <div class="content_left wm790 left"> 
	<div class="content_bg">          
	<div class="yr_bg"> 
	<div class=ye_r_t> 
	<div class=ye_l_t> 
	<div class=ye_r_b> 
	<div class=ye_l_b> 
	<div class="content">
		<jsp:include page="include_div.jsp"></jsp:include>
        <jsp:include page="include_page_title.jsp"></jsp:include>
        <div class="title f14px"><b>${currentPage.title}：</b>
        	<span class="cDGray">${currentPage.instruction}</span>
        	<input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
        </div>
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
		        	<p align="left">${item.examProperty.contentView}</p>		        	
        			<div class="blank6"></div>        			
		        	<jsp:include page="include_prompt.jsp"></jsp:include>
		        </div>
			</div> 
        	<div class="blank9"></div> 
		</c:forEach>
                    
		<jsp:include page="include_pagination.jsp"></jsp:include>
	</div>
	</div></div></div></div></div></div>
  </div>
  <!--End left-->
  
  <!--Start right--> 
  <c:if test="${viewControl.showModel==1}">
  	<jsp:include page="include_left.jsp"></jsp:include>
  </c:if>
  <c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
  	<jsp:include page="include_right.jsp"></jsp:include>
  </c:if>
  <!--End right-->
	
  <div class="clear"></div> 
</div> 

<!--页面尾部-->
<jsp:include page="include_bottom.jsp"></jsp:include>
</body>
</html>