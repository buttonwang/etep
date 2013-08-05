<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
<OBJECT
      ID=MathPlayer
      CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"
>
</OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<head>
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
        	<c:set var="xItemType" value="choose" scope="request"/>
        	<input type="hidden" name="mapkey${status.index}" id="mapkey${status.index}" value="${item.id}"/>
        	<div class="content_box">
        		<div class="content_titer">
		        	<jsp:include page="include_item_title.jsp"></jsp:include>
		        </div>
		        <div class="clear"></div>
		        <div class="answers">
		        	<p align="left">${item.content}
		        		<c:if test="${viewControl.showModel!=1}">
							<c:if test="${item.examProperty.isRight}">
								<img src="../images/true.gif" width="16" height="16"/>
							</c:if>
							<c:if test="${!item.examProperty.isRight}">
								<img src="../images/false.gif" width="16" height="16"/>
							</c:if>
						</c:if>
		        	</p>
		        	<div class="blank6"></div> 
		        	<ul>
		        		<c:forEach items="${item.answerOptions}" var="answerOption" varStatus="answerStatus">
		        			<c:if test="${answerStatus.index==0}"><c:set var="answerCode" value="A．"></c:set></c:if>
							<c:if test="${answerStatus.index==1}"><c:set var="answerCode" value="B．"></c:set></c:if>
							<c:if test="${answerStatus.index==2}"><c:set var="answerCode" value="C．"></c:set></c:if>
							<c:if test="${answerStatus.index==3}"><c:set var="answerCode" value="D．"></c:set></c:if>
			        		<li>
								<input type="checkbox" name="userAnswer${status.index}" id="userAnswer${status.index}" value="${answerOption.code}"
									<c:if test="${fn:contains(item.examProperty.userAnswer, answerOption.code)}">checked</c:if>
							 	/>${answerCode} ${answerOption.content}			 								 		                           
							</li>
                        </c:forEach>
		        	</ul>
		        	<div class="clear"></div>
		        	
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