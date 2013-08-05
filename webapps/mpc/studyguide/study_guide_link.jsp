<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学习指导红宝书</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
 
<div class="wm720 nTab">
 
  
  <!-- 内容开始 -->
  <div class="TabContent">
           <div  class="dirct_list box1">
            <ul>
 				    <c:forEach items="${list}" var="item" > 
				    	 <strong> <li><a onclick="parent.sById(${item['map']['id']})" href="studyGuideDescAction.jhtml?nodeId=${item['map']['id']}&nodeGroupId=${item['map']['node_group_id']}&nodeName=${item['map']['name']}"  target="main" >${item['map']['name'] }</a></li></strong>
			 			 <c:forEach items="${item['list'] }" var="subitem" >
			 			 <li><a onclick="parent.sById(${subitem['id']})" href="studyGuideDescAction.jhtml?nodeId=${subitem['id']}&nodeGroupId=${subitem['node_group_id']}&nodeName=${subitem['name']}"  target="main" >${subitem['name'] }</a></li>
			 			 </c:forEach>
			 			 <br/>
					</c:forEach>
					
 
				 
            </ul>
            <div class="clear"></div>
          </div>
		 	
	</div>
   <!-- 载入内容结束 -->
  
  </div> 
  	
</body>
</html>