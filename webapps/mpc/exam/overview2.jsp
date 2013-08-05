<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body >
<!-- 页面主题ss -->
<div id="main" class="wm2" >
   <div class="nTab wm2">
	
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
      <div class="content_c">
        <div class="title">
        <ul>
        	<li class="answer">已答题</li>
            <li class="query1">标记疑问的题</li>
            <li><span class="cDEGray">[灰色题号]</span>未答的题</li>
        </ul><div class="clear"></div>
        </div>
        <div class="blank9"></div>
        <div class="content_box">
		<c:forEach items="${viewControl.sectionList}" var="section" varStatus="status">
          <div class="con_right_content f14px"><b>${section.title}：</b><span class="cDGray">${section.instruction}</span></div>
          <div class="contenters">
            <ul>
	        	<c:forEach items="${section.pages}" var="page" varStatus="status1">
					<c:forEach items="${page.items}" var="item" varStatus="status2">
			            <li>
				            <a href="../exam/showExam.jhtml?pageNum=${page.pageNum}" title="提示" target="_top" 
								<c:if test="${item.examProperty.isDone}" var="isDone"> class="default"</c:if>													
								<c:if test="${!item.examProperty.isDone}" var="isDone"> class="cnone" </c:if>
							>[${item.itemNum}]</a>
						  	<c:if test="${item.examProperty.isDone}">
						  		<img src="../images/answer.gif" alt="已答" width="16" height="16"  />
						  	</c:if>
						  	<c:if test="${item.examProperty.isMark}">
						  		<img src="../images/query.gif" alt="疑问" width="16" height="16"  />
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
            <div class="clear"></div>
          </div>
		  </c:forEach>
        </div>
        <div class="blankW_9"></div>
        <div class="clear"></div>
        </div>         
        </div>
        </div>
    </div>
  </div>
  </div>
  <div class="clear"> </div>

</body>
</html>