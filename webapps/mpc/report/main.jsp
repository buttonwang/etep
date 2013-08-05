<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>强化训练</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<link href="../css/dtree.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/dtree.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT language="javascript" src="../js/floating_new.js" type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript">
function consolidate_training(page){
		try{
			 doit($("#sign01").get(0));
			 doit($("#sign02").get(0)) ; 
			 FloatingDIVWithID('sign02');
		}catch(e){
			alert(e.toString())		
		}
}
function test_consolidate_training(page){
		try{
			 doit($("#testsign01").get(0));
			 doit($("#testsign02").get(0)) ; 
			 FloatingDIVWithID('testsign02');
		}catch(e){
			alert(e.toString())		
		}
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

<body>
<!--stars tan div-->
        <div class="floatBoxs" id=testsign02  style='z-index:33;display:none;'>
		<h3></h3>
		<div class="floatBox">
		<h1>抱歉，后测尚未通过，暂时不能重练</h1>
		<div class="btn">
		<span class="gbs1">
		<button class="gb1" onmouseout="this.className='gb1';" onmouseover="this.className='gb2';" onClick="javascript:doit(testsign01);doit(testsign02)" >取消</button></span>
         <div class="clear"></div>
		</div></div></div>
       <div id=testsign01 style='z-index:20;position:absolute; 
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
    <iframe style="position:absolute;top:0px; left:0px; width:100%; height:expression(document.body.scrollHeight); z-index:-1; background-color:#000;filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;"></iframe></div>
        <!--End tan div-->
<!--stars tan div-->
        <div class="floatBoxs" id=sign02  style='z-index:33;display:none;'>
		<h3>进入暂存学习任务</h3>
		<div class="floatBox">
		<h1>当前有暂存学习任务，请先完成它：</h1>
		<div class="content box1">
		<ul>
			<li><span>
			<c:if test="${nodeInstance != null && pauseInfoVO.nodeInstanceId >0}">【${nodeInstance.node.nodeGroup.name}】-【${nodeInstance.node.name}】</c:if> <c:if test="${pauseInfoVO.pauseType == '弱项强化'}"> 【${pauseInfoVO.titleInfo}】</c:if>
			</span></li>
		</ul>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="location.href='../exam/goExam.jhtml?nodeInstanceId=${pauseInfoVO.nodeInstanceId}&isPause=1&examType=3'">进入暂存的学习任务</button></span>
		<span class="gbs1">
		<button class="gb1" onmouseout="this.className='gb1';" onmouseover="this.className='gb2';" onClick="javascript:doit(sign01);doit(sign02)" >取消</button></span>
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
    <iframe style="position:absolute;top:0px; left:0px; width:100%; height:expression(document.body.scrollHeight); z-index:-1; background-color:#000;filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;"></iframe></div>
        <!--End tan div-->

<jsp:include page="../web/include_head.jsp"></jsp:include>

<div id="contentLayout" class="wm950">
 	<!--Satr left-->
  <!--End left-->
<div class="content_right wm220 left">
    	<div class="content_bg">
        <div class="yr_bg3">
    	<div class=yr_r_t>
		<div class=yr_l_t>
		<div class=yr_r_b>
		<div class=yr_l_b>
        <div class="con_r con_r_tree">
<ul id="navigation" class="filetree">
	<script type="text/javascript">
		var d = new dTree('d');
		<c:set var="size" value="7"/>
		<c:set var="modelSize" value="12"/>
		d.add(0,-1,'<c:if test="${fn:length(userDataVO.processName)>modelSize}"><c:out value="${fn:substring(userDataVO.processName,0,modelSize)}"/>...</c:if><c:if test="${fn:length(userDataVO.processName)<=modelSize}">${userDataVO.processName}</c:if>','consolidate.jhtml?nodeType=PRACTICE','${userDataVO.processName}','main');
		<c:forEach items="${nodeList}" var="item" > 
			<c:choose> 
    			<c:when test="${item['node_group_id'] eq null}"> 
					d.add(${item['node_id']},0,'<c:if test="${fn:length(item['name'])>modelSize}"><c:out value="${fn:substring(item['name'],0,modelSize)}"/>...</c:if><c:if test="${fn:length(item['name'])<=modelSize}">${item['name']}</c:if>','consolidate.jhtml?orderNum=${item['order_num']}&nodeType=PRACTICE','${item['name']}','main');
				</c:when> 
    			<c:when test="${item['node_status'] eq 3}"> 
					d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','../exam/goExam.jhtml?nodeInstanceId=${item['id']}&examType=4','${item['name']}','main');
				</c:when>
    			<c:otherwise>
					<c:choose> 
    					<c:when test="${item['node_type'] eq 'PRACTICE'}"> 
							d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','consolidate!training.jhtml?orderNum=${item['order_num']}&nodeType=PRACTICE','${item['name']}','main');
						</c:when>
						<c:when test="${item['node_type'] eq 'PHASETEST'}"> 
							d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','consolidate!training.jhtml?orderNum=${item['order_num']}&nodeType=PHASETEST','${item['name']}','main');
						</c:when>
						<c:when test="${item['node_type'] eq 'UNITTEST'}"> 
							d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','consolidate!training.jhtml?orderNum=${item['order_num']}&nodeType=UNITTEST','${item['name']}','main');
						</c:when> 
						<c:when test="${item['node_type'] eq 'EVALUATE'}"> 
							d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','consolidate!test.jhtml?orderNum=${item['order_num']}&nodeType=EVALUATE','${item['name']}','main');
						</c:when> 
						<c:when test="${item['node_type'] eq 'EXTRA'}"> 
							d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','extra.jhtml?nodeGroupId=${item['node_group_id']}','${item['name']}','main');
						</c:when> 
    					<c:otherwise>
							d.add(${item['node_id']},${item['node_group_id']},'<c:if test="${fn:length(item['name'])>size}"><c:out value="${fn:substring(item['name'],0,size)}"/>...</c:if><c:if test="${fn:length(item['name'])<=size}">${item['name']}</c:if>','consolidate.jhtml?orderNum=${item['order_num']}&nodeType=PRACTICE','${item['name']}','main');
			    		</c:otherwise>
   					</c:choose>
			    </c:otherwise>
   			</c:choose>
		</c:forEach>
	
		document.write(d);
		<c:if test="${defaultNodeInstance == null}">
		d.openTo(0, true,true);
		</c:if>
		<c:if test="${defaultNodeInstance != null}">
		d.openTo(${defaultNodeInstance}, true,false);
		</c:if>

		d.openAll();
	</script>

        </div>
		</div></div></div></div></div></div></div>
<div class="content_right wm720 right">
 <iframe width="720" name="main" id="frame_content" src="
 <c:if test="${showListType ==0}">
 consolidate.jhtml?nodeType=PRACTICE<c:if test="${orderNum != null}">&orderNum=${orderNum}</c:if>
 </c:if>
  <c:if test="${showListType ==1}">
 consolidate!training.jhtml?orderNum=${orderNum}&nodeType=${nodeType}
 </c:if>
  <c:if test="${showListType ==2}">
 consolidate!test.jhtml?orderNum=${orderNum}&nodeType=${nodeType}
 </c:if>
   <c:if test="${showListType ==3}">
   ../exam/goExam.jhtml?nodeInstanceId=${nodeInstanceId}&examType=4
 </c:if>
 <c:if test="${showListType ==4}">
   mpcstudy!review.jhtml?nodeInstanceId=${nodeInstanceId}<c:if test="${hisId != null}">&hisId=${hisId}</c:if>
 </c:if>
  <c:if test="${showListType ==5}">
   extra.jhtml?nodeGroupId=${nodeInstanceId}
 </c:if>
 " scrolling="no" frameborder="0" onload="this.height=100"></iframe>
		</div>
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
<!--页面尾部-->
<jsp:include page="../exam/include_bottom.jsp"></jsp:include></body>
</html>
