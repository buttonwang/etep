<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择答题版式--${viewControl.flowName}</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/tandiv.js"></script></head>
<script language="javascript">
function doRevert(){
	document.getElementById("layout1_5").checked=true;
	document.getElementById("layout2_5").checked=true;
	document.getElementById("layout3_1").checked=true;
	document.setpageForm.submit();
}
function doSubmit(){
	document.setpageForm.submit(); 
}
function doReset(){
	document.setpageForm.reset(); 
}
function doClose() {
	document.close();
}
</script>
<body>

<jsp:include page="include_head.jsp"></jsp:include>

<div id="contentLayout" class="wm950">
 	<!--Start left-->
  <div class="wm950">
  		<div class="content_bg">
    	<div class=ye_r_t>
		<div class=ye_l_t>
		<div class=ye_r_b>
		<div class=ye_l_b>
        <div class="con_l">
		<h2><span>${userDataVO.userName}</span>，欢迎进入答题版式：</h2>
        <h1><label class="set f14px">设置答题版式 </label>可以根据您喜好设置每页显示题目数以及字号大小</h1>
		<form action="setpage!save.jhtml" method="post" id="setpageForm" name="setpageForm">
        <div class="page box1 left">
        	<span>选择题（*题/页  默认5）</span>
        	<ul>
            	<li><label><input type="radio" name="layout1" id="layout1_1" value="1"  
				<c:if test="${processTrainingStatus.layout1==1}">checked="checked"</c:if>/>
            	</label><b>1</b></li>
                <li><label><input type="radio" name="layout1" id="layout1_5" value="5" 
				<c:if test="${processTrainingStatus.layout1==5}">checked="checked"</c:if>/>
                </label><b>5</b></li>
                <li><label><input type="radio" name="layout1" id="layout1_10" value="10" 
				<c:if test="${processTrainingStatus.layout1==10}">checked="checked"</c:if>/>
                </label><b>10</b></li>
                <li><label><input type="radio" name="layout1" id="layout1_20" value="20" 
				<c:if test="${processTrainingStatus.layout1==20}">checked="checked"</c:if>/>
                </label><b>20</b></li>
            </ul>
            <img src="../images/page_1.jpg" />
        </div>
        <div class="page box1 left">
        	<span>填空题小题（*题/页 默认5）</span>
        	<ul>
            	<li><label><input type="radio" name="layout2" id="layout2_1" value="1" 
				<c:if test="${processTrainingStatus.layout2==1}">checked="checked"</c:if>/>
            	</label><b>1</b></li>
                <li><label><input type="radio" name="layout2" id="layout2_5" value="5" 
				<c:if test="${processTrainingStatus.layout2==5}">checked="checked"</c:if>/>
                </label><b>5</b></li>
                <li><label><input type="radio" name="layout2" id="layout2_10" value="10" 
				<c:if test="${processTrainingStatus.layout2==10}">checked="checked"</c:if>/>
                </label><b>10</b></li>
                </ul>
            <img src="../images/page_2.jpg" />
        </div>
        <div class="page box1 left">
        	<span>填空题大题（*题/页 默认1）</span>
        	<ul>
            	<li><label><input type="radio" name="layout3" id="layout3_1" value="1" 
				<c:if test="${processTrainingStatus.layout3==1}">checked="checked"</c:if>/>
            	</label><b>1</b></li>
                <li><label><input type="radio" name="layout3" id="layout3_5" value="5" 
				<c:if test="${processTrainingStatus.layout3==5}">checked="checked"</c:if>/>
                </label><b>5</b></li>
                <li><label><input type="radio" name="layout3" id="layout3_10" value="10" 
				<c:if test="${processTrainingStatus.layout3==10}">checked="checked"</c:if>/>
                </label><b>10</b></li>
                </ul>
            <img src="../images/page_3.jpg" />
        </div>
        <div class="page1 box1 left">
        	<span>字号</span>
        	<ul>
            	<li><label><input type="radio" name="font" id="font" value="L" 
				<c:if test="${processTrainingStatus.font=='L'}">checked="checked"</c:if>/>
            	</label>
            	<b>小</b>&nbsp;&nbsp;&nbsp;&nbsp;<label class="f12px">字体</label></li>
                <li><label><input type="radio" name="font" id="font" value="M" 
				<c:if test="${processTrainingStatus.font=='M'}">checked="checked"</c:if>/>
                </label>
                <b>中</b>&nbsp;&nbsp;&nbsp;&nbsp;<label class="f14px">字体</label></li>
                <li><label><input type="radio" name="font" id="font" value="B" 
				<c:if test="${processTrainingStatus.font=='B'}">checked="checked"</c:if>/>
                </label>
                <strong>大</strong>&nbsp;&nbsp;&nbsp;&nbsp;<label class="f147px">字体</label>
                </li>
            </ul>
        </div>
        <div class="clear"></div>
        <hr />
        <div class="bnt_box">
				<!--<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';">预览当前版式</button></span>-->
                <span class="bbs1"><button class="bb1" onclick="doRevert();" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';">还原默认</button></span>
                <span class="bbs1"><button class="bb1" onclick="doSubmit();" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';">确定</button></span>
                <span class="gbs1"><button class="gb1" onclick="doReset();"  onmouseout="this.className='gb1';" onmouseover="this.className='gb2';">取消</button></span>
                <div class="clear"></div>
        </div>
		</form>
		</div></div></div></div></div></div>
		
  </div>
  <!--End left-->
  <div class="clear"></div>
</div>

<jsp:include page="include_bottom.jsp"></jsp:include>

</body>
</html>