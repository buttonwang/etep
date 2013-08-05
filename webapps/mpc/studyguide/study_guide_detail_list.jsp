<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>学习指导</title>
	<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../js/tandiv.js"></script>
	<script language="javascript" src="../js/jquery.min.js"></script>
</head>
<body>
	<div id="contentLayout" class="wm950">
    <!--Start left-->
	<div class="content_right wm220 left">
    	<div class="content_bg">
        <div class="yr_bg3">
    	<div class=yr_r_t>
		<div class=yr_l_t>
		<div class=yr_r_b>
		<div class=yr_l_b>
        <div class="con_r con_r_tree">
		<ul id="browser" class="filetree">
			<li>针对本任务系统提供以下学习指导 </li>
			<c:forEach items="${sgList}" var="sg" varStatus="state">
				<li><a id='${sg.id}' class='sgHref' target="main" href="itemStudyGuide!getInfo.jhtml?id=${sg.id}">${sg.name}</a></li>
			</c:forEach>
		</ul>
        </div>
		</div></div></div></div></div></div>
	</div>
    <!--End left-->
    <div class="con_right2 wm720 right" id = "rightContent">
    	 <iframe width="720" name="main" id="frame_content" src=""
        	scrolling="no" frameborder="0" onload="this.height=100"></iframe>
			<script type="text/javascript">
				function reinitIframe(){
					var iframe = document.getElementById("frame_content");
					try{
						var bHeight = iframe.contentWindow.document.body.scrollHeight;
						var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
						var height = Math.max(bHeight, dHeight);
						iframe.height =  height;
					}catch (ex){}
				}
				window.setInterval("reinitIframe()", 200);
				
				function checkHeight() {
					var iframe = document.getElementById("frame_content");
					var bHeight = iframe.contentWindow.document.body.scrollHeight;
					var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				}
			</script>
	</div>
    <div class="clear"></div>
	</div>
	<script type="text/javascript">
		firstsghref = $('.sgHref').first().attr('href');
		$('#frame_content').attr('src', firstsghref);
	</script>
</body>
</html>