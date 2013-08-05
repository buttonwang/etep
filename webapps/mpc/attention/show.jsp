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
<title>关注</title>
<link rel="stylesheet" href="../css/style_blue.css" type="text/css" />
<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" /> 
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../js/tandiv.js"></script>
<script type="text/javascript" src="../js/thickbox.js"></script>
</head>
<body>
<jsp:include page="../web/include_head.jsp"></jsp:include>

<div id="contentLayout" class="wm950">
	<div class="content_left wm950">
		<div class="content_bg">
			<div class="yr_bg2">
			<div class=ye_r_t>
			<div class=ye_l_t>
			<div class=ye_r_b>
			<div class=ye_l_b>
			<div class="content">
			
				<!--试题层开始-->
				<div id="itemdiv"></div>
				
				<!--试题层结束-->
				
				<!--关注层开始-->
				<div id="attentiondiv">
					<jsp:include page="include_attention.jsp"></jsp:include>
				</div>
				<!--关注层结束-->
				
				<!--笔记层开始-->
				<div id="notediv"></div>
        		<!--笔记层结束-->
        		
        		<div class="blankW_9"></div>
		        <div class="bnt_box">
	            	<span class="pager" id="preSpan"><button class="pager1" onmouseout="this.className='pager1';"  onmouseover="this.className='pager2';"
	            		onclick="preAttention()">上一题</button></span>
	                <span class="pager" id="nextSpan"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                	onclick="nextAttention()">下一题</button></span>
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
		                onclick="returnAttention()">返回试题列表</button></span>
	                <div class="clear"></div>
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
<jsp:include page="../include_bottom.jsp"></jsp:include>

<script type="text/javascript">
	$("#itemdiv").load("../exam/examWidget.jhtml", 
					{itemId: ${itemId},
					 hisId:  ${hisId},
					 mode:   1,
					 width:  920},
					function(){}
	 );
</script>

<c:set var="pageSize" value="5"></c:set>
<script type="text/javascript">
		function loadNote(start, pageSize) {
			$("#notediv").load("../attention/learnNote!list.jhtml", 
					{itemId: ${itemId},
					 start:  start,
					 pageSize: pageSize},
					function(){  }
			);
		}

		function pageCallBack(start, pageSize) {
			loadNote(start, pageSize);
			location.href = "#topNote";
		}
		//pageCallBack = loadNote;

		if ("${canAttention}"=="true") loadNote(0, "${pageSize}");
</script>
				
<script type="text/javascript">

	if ("${preAttentionId}"==0)  $("#preSpan").hide();
	if ("${nextAttentionId}"==0) $("#nextSpan").hide();
	
	function preAttention() {
		if ("${preAttentionId}"==0) {
			alert("已是试题列表的第一题，请先返回试题列表翻页后查看。");
			return;
		}
		window.location.href = "showAttention.jhtml?attentionId=${preAttentionId}&type=${type}";
	}

	function nextAttention() {
		if ("${nextAttentionId}"==0) {
			alert("已是试题列表的最后一题，请先返回试题列表翻页后查看。");
			return;
		}
		window.location.href = "showAttention.jhtml?attentionId=${nextAttentionId}&type=${type}";
	}

	function returnAttention() {
		var param =  "${sessionScope.attentionParams}";
		if ("${type}"=="my") {
			window.location.href = "myAttention.jhtml?"  + param;
		} else {
			window.location.href = "topAttention.jhtml?" + param;
		}
	}
</script>
</body>
</html>