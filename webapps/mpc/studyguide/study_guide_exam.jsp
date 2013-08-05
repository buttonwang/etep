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
  <div class="TabContent">
	    <div id="myTab1_Content0">
		<div class="style1">
		<div class="con_right">
       		<h1 class="til"><span>知识要点</span></h1>
      		<hr />
       	 	<div class="bg dirct">
				 <c:forEach items="${mapList}" var="item" >
				 	${item['content']}
				 </c:forEach> 
			</div>
		</div>
		</div>		
		</div>
     	<div class="blank20"></div>
  </div>
</div> 
</body>
</html>