<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学习指导红宝书</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<link href="../css/dtree.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../js/dtree.js"></script>
<SCRIPT language="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT language="JavaScript" src="../js/common.js"></script>
<SCRIPT language="javascript" src="../js/floating_new.js" type="text/javascript"></SCRIPT>
</head>
<body>
<jsp:include page="../web/include_head.jsp"></jsp:include>

<div id="contentLayout" class="wm950">
    <!--Satr left-->
    <c:set var="len" value="${fn:length(mapList)}"></c:set>
	<div class="content_right wm220 left">
    	<div class="content_bg">
        <div class="yr_bg3">
    	<div class=yr_r_t>
		<div class=yr_l_t>
		<div class=yr_r_b>
		<div class=yr_l_b>
        <div class="con_r con_r_tree">
		<ul id="browser" class="filetree">
		<script type="text/javascript">
			var d = new dTree('d');
			d.add(0,-1,'学习指导红宝书','study_guide_desc.html','学习指导红宝书','main');

			<c:forEach items="${list}" var="item">
				var nodeName = "${item.name}";
				if (nodeName.length > 8) nodeName = nodeName.substr(0, 8) + '...';
			    <c:if test="${item.parent == null}">
					d.add(${item.id}, 0, nodeName,'itemStudyGuide!getInfo.jhtml?id=${item.id}',nodeName,'main');
			    </c:if>
			    <c:if test="${item.parent != null}">
					d.add(${item.id} ,${item.parent.id},nodeName,'itemStudyGuide!getInfo.jhtml?id=${item.id}',nodeName,'main');
			    </c:if>
			</c:forEach>
			document.write(d);
	
			d.openAll();
	        
 			/**    通过nodeId选中对应的树节点，并展开    **/
			function sById(id){
			     var index=d.gInd(id);
			     if(d.isOpen(index)){d.o(index)}
			     d.s(index);
				 if(!d.isOpen(d.aNodes[index].pid)){index=d.gInd(d.aNodes[index].pid);d.o(index)} 
			}
		</script>
		</ul>
        </div>
		</div></div></div></div></div></div>
	</div>
    <!--End left-->
    <div class="con_right2 wm720 right">
        <iframe width="720" name="main" id="frame_content" src="study_guide_desc.html"
        	scrolling="no" frameborder="0" onload="this.height=100"></iframe>
        	<a href="" target="main" id="whoweare_a"></a>
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
	  if('${nodeId}' != ''){
		  try {
 		    var index=d.gInd(${nodeId});
	   		if(d.isOpen(index)){d.o(index)}
		    d.s(index);
		    var node=d.aNodes[index].url
		    var whoweare_a=document.getElementById("whoweare_a");
		    whoweare_a.href=d.aNodes[index].url;
	        whoweare_a.click(); }
	      catch (e) {}
       }
</script>
<jsp:include page="../include_bottom.jsp"></jsp:include>
</body>
</html>