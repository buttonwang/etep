<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我关注的试题</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<link href="../css/dtree.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/dtree.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT language="javascript" src="../js/floating_new.js" type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript">
var ids = "";
function attention(newIds){
	try{
		if(newIds.length>0){
			doit($("#sign01").get(0));
			doit($("#sign02").get(0)) ; 
			FloatingDIVWithID('sign02');
			ids = newIds;
		}else{
			doit($("#sign03").get(0));
			doit($("#sign04").get(0)) ; 
			FloatingDIVWithID('sign04');
		}
	}catch(e){
		alert(e.toString())		
	}
}
function test(){
	window.main.location="attention!disAttentionBatch.jhtml?ids="+ids;
}

function FloatDiv(divId,left,top){
	setInterval("FloatDiv_keep('"+divId+"',"+left+","+top+")",10);
}
function FloatDiv_keep(divId,left,top) {
	var div=document.getElementById(divId);
	div.style.position='absolute';
	div.style.left=(left||0+document.body.scrollLeft).toString()+'px';
	div.style.top=(top||0+document.body.scrollTop).toString()+'px';
}
</SCRIPT>
</head>
<body >
<jsp:include page="../web/include_head.jsp"></jsp:include>
 
<div class="wm950 nTab">
 <!-- 标题开始 -->
<div class="TabTitle" id="ceshi">
    <ul>
      <li class="active_2" ><a class="cBlack" href="myAttention.jhtml">我关注的试题</a></li>
      <li class="normal_2" ><a class="cBlack" href="topAttention.jhtml">最热关注Top500</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
    <!--stars tan div-->
        <div class="floatBoxs" id=sign02  style='z-index:33;display:none;'>
		<h3>不在关注</h3>
		<div class="floatBox">
		<h1>确定从你的关注列表中删除此题？</h1>
		<div class="content box1">
		<ul>
			<li>（删除后仍可在“逐题回顾”再次关注它）</li>
		</ul>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign01);doit(sign02);test();">确认</button></span>
		<span class="bbs1">
		<button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign01);doit(sign02)" >取消</button></span>
         <div class="clear"></div>
		</div></div></div>
        <div id=sign01 style='z-index:20;position:absolute; 
		left:0;
        top:0;
        *+WIDTH:100%;
        *_WIDTH:expression((screen.availwidth-27)+"px"); 
        HEIGHT:expression(document.body.scrollHeight);
	background-color:#000;
	filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;
    display:none;'  
    align='center'>

     </div>
	 
	 <div class="floatBoxs" id=sign04  style='z-index:33;display:none;'>
		<h3>不在关注</h3>
		<div class="floatBox">
		<h1>请选择你“不再关注”的试题！</h1>
		<br/>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign03);doit(sign04);">确认</button></span>
		<span class="bbs1">
		<button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign03);doit(sign04)" >取消</button></span>
         <div class="clear"></div>
		</div></div></div>
        <div id=sign03 style='z-index:20;position:absolute; 
		left:0;
        top:0;
        *+WIDTH:100%;
        *_WIDTH:expression((screen.availwidth-27)+"px"); 
        HEIGHT:expression(document.body.scrollHeight);
	background-color:#000;
	filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;
    display:none;'  
    align='center'>

     </div>
        <!--End tan div-->
    <!--Satr left-->
<div class="content_right wm220 left">
    	<div class="content_bg2">
        <div class="con_r">
	<ul id="browser" class="filetree">
	<script type="text/javascript">

		var d = new dTree('d');
		d.add(0,-1,'<c:if test="${fn:length(userDataVO.processName)>18}"><c:out value="${fn:substring(userDataVO.processName,0,15)}"/>...</c:if><c:if test="${fn:length(userDataVO.processName)<=18}">${userDataVO.processName}</c:if>','myAttention!list.jhtml','${userDataVO.processName}','main');
		<c:forEach items="${mapList}" var="item" > 
			<c:choose> 
				<c:when test="${item['node_group_id'] eq null}"> 
					d.add(${item['id']},0,'${item['name']}','myAttention!list.jhtml?orderNum=${item['order_num']}','${item['name']}','main');
				</c:when>
				<c:when test="${item['node_group_id'] != null}"> 
					d.add(${item['id']},${item['node_group_id']},'${item['name']}','myAttention!list.jhtml?orderNum=${item['order_num']}','${item['name']}','main');
				</c:when>
			</c:choose>
		</c:forEach>
		document.write(d);

		d.openAll();
	</script>
	</ul>
        </div>
		</div></div>
        <!--End left-->
        <div class="con_right2 wm690 right">
        <iframe width="690" name="main" id="frame_content" src="myAttention!list.jhtml?orderNum=${orderNum}&order=${order}&type=${type}&tag=${tag}&page=${page}"
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
				alert("bHeight:" + bHeight + ", dHeight:" + dHeight);
			}
		</script>
		        </div>
        <div class="clear"></div>
        </div>
        </div>
    </div>


</div>
<div id="footde" class="wm950"><span>Copyright © 2010</span>Ambow Education Group <a href="" target="_blank"></a> <span>版权所有</span></div>
</body>
</html>