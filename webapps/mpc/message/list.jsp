<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的消息</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<SCRIPT src="../js/tandiv.js" LANGUAGE="JavaScript"></script>
<SCRIPT src="../js/common.js" LANGUAGE="JavaScript"></script>

<SCRIPT type="text/javascript">
function goto(source,page){
	if(page != null && page !=''){
		param = "message!list.jhtml?pageNo="+page+"&source="+source;
	}
	self.location=param;
}

function checkAll(boxname){
	var boxs = document.getElementsByName(boxname); 
	for(i = 0; i < boxs.length; i++)
		boxs[i].checked=event.srcElement.checked 
}

function deleteAll(){
	var messageIds='';
	var checkboxs = document.getElementsByName('checkboxMessage');
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked == true){
			messageIds = messageIds + checkboxs[i].value +",";
		}
	}
	if(messageIds.length>0){
		doit(sign01);doit(sign02);
	}else{
		doit(sign01);doit(sign03);
	}
}

function submitbutton(){
	doit(sign01); doit(sign02);
	var messageIds='';
	var checkboxs = document.getElementsByName('checkboxMessage');
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked == true){
			messageIds = messageIds + checkboxs[i].value +",";
		}
	}
	messageIds=messageIds.substring(0,messageIds.length-1);
	var str = "../message/message!delete.jhtml?messageIds="+messageIds+"&source=${source}";
	window.location.href=str;
}
</SCRIPT>
</head>
<body>
<jsp:include page="../web/include_head.jsp"></jsp:include>
 
<div class="wm950 nTab">
 	<!-- 标题开始 -->
 	<div class="TabTitle" id="ceshi" style="line-height:30px;">
	 	<img src="../images/arrow-snslaba.gif" /> 社区小喇叭：共有<strong>${totalMessage}</strong>条短消息
	</div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
    <!--stars tan div-->
   
    <div class="floatBoxs" id=sign02  style='z-index:33;display:none; margin-top:1	00px;'>
		<h3>删除消息</h3>
		<div class="floatBox">
		<h1>确认要删除选中消息吗？</h1>
		<div class="btn">
		<span class="bbs1">
			<button id=submitbutton class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
				onClick="submitbutton()">确认</button>
		</span>
		<span class="bbs1">
			<button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
			onClick="javascript:doit(sign01);doit(sign02)" >取消</button>
		</span>
         <div class="clear"></div>
		</div></div>
	</div>
	<div class="floatBoxs" id=sign03  style='z-index:33;display:none; margin-top:100px;'>
		<h3>删除消息</h3>
		<div class="floatBox">
		<h1>请先选中要删除的消息！</h1>
		<div class="btn">
		<span class="bbs1">
			<button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
			   onClick="javascript:doit(sign01);doit(sign03)" >关闭</button>
		</span>
         <div class="clear"></div>
		</div></div>
	</div>
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
    <iframe style="position:absolute;top:0px; left:0px; width:100%; height:expression(document.body.scrollHeight); 
    z-index:-1; background-color:#000;filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;"></iframe>
     </div>
    <!--End tan div-->
    <!--Satr left-->
    <!--End left-->
	<div class="con_right2 wm925 right">
        <div class="tager">
        <div class="tager_in">
          <h2>
          	<span class="f14px cblue fB">消息类别</span>
          	<span><a href="../message/message!list.jhtml?source=-1" class=${source eq -1?"cBlackcurrent":"cBlack"}>全部</a></span>
          	<span><a href="../message/message!list.jhtml?source=2"  class=${source eq  2?"cBlackcurrent":"cBlack"}>关注</a></span>
          	<span><a href="../message/message!list.jhtml?source=3"  class=${source eq  3?"cBlackcurrent":"cBlack"}>捉虫</a></span>
          	<span><a href="../message/message!list.jhtml?source=1"  class=${source eq  1?"cBlackcurrent":"cBlack"}>系统通知</a></span>
          	<span></span>
          </h2>
        </div>
        </div>
 		<div class="blankW_9"></div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="gzlist">
          <tr class="gz_title">
            <td></td>
            <td>时间</td>
            <td width="11%" align="center">消息类别</td>
            <td width="55%" align="center">消息内容</td>
            <td width="16%" align="center">&nbsp;</td>
          </tr>
          <c:forEach items="${messageList}" var="message">
          	  <tr>
	            <td width="4%"><input name="checkboxMessage" type="checkbox" value="${message.id}"/></td>
	            <td width="14%"><fmt:formatDate pattern="yy-MM-dd HH:mm" value="${message.publishTime}"/></td>
	            <td align="center">
	            	<c:choose>
	            		<c:when test="${message.source==1}">系统通知</c:when>
	            		<c:when test="${message.source==2}">关注</c:when>
	            		<c:when test="${message.source==3}">捉虫</c:when>
	            	</c:choose>
	            </td>
	            <td>${message.content}</td>
	            <td align="center">
	            	<c:choose>
	            		<c:when test="${message.source==1}"></c:when>
	            		<c:when test="${message.source==2}">
	            			<a class="cGray" target="_blank" 
	            			  href="../attention/showAttention.jhtml?attentionId=${message.refId}&type=my">查看笔记</a>
	            		</c:when>
	            		<c:when test="${message.source==3}">
	            			<a class="cGray" target="_blank"
	            			  href="../bug/bug!showItemBug.jhtml?p.para.bugInfoId=${message.refId}">捉虫记录</a>
	            		</c:when>
	            	</c:choose>
	            </td>
	          </tr>
          </c:forEach>
        </table>
        <div class=" blankW_6"></div>
            <c:choose>
            	<c:when test="${fn:length(messageList) > 0}">
            	 	  <div class="list_gz_inp"><span class="inp">
            			<input type="checkbox" name="全选" value="" id="全选_0" onclick="checkAll('checkboxMessage');"/>全选</span><span class="inp">
            			<input type="submit" onclick="deleteAll();" name="button" id="button" value="删除消息" />
            		  </span></div>
            	</c:when>
            	<c:otherwise>
            		<div width="100%">没有查到任何消息!</div>
            	</c:otherwise>
            </c:choose>
        <div class="blank6"></div>
        <c:if test="${pag.totalCount >0}">
        <div class="manu">
		<c:if test="${pag.page == 1}">
		<span class="disabled">首页</span>
		<span class="disabled"> <  上一页</span>
		</c:if>
		<c:if test="${pag.page > 1}">
		<a href="#" onclick="javascript:goto(${source},1);">首页</a>
		<a href="#" onclick="javascript:goto(${source},${pag.page-1});"><  上一页</a>
		</c:if>
		<c:forEach var="offPage" begin="${pag.beginPage}" end="${pag.endPage}" step="1">
			<c:if test="${pag.page == offPage}">
         	<span class="current">${pag.page}</span>
			</c:if>
			<c:if test="${pag.page != offPage}">
         	<a href="#" onclick="javascript:goto(${source},${offPage});">${offPage}</a>
			</c:if>
		</c:forEach>
		<c:if test="${pag.page >= pag.totalPage}">
		<span class="disabled">下一页  > </span>
		<span class="disabled">最后一页</span>
		</c:if>
		<c:if test="${pag.page < pag.totalPage}">
		<a href="#" onclick="javascript:goto(${source},${pag.page+1});">下一页  > </a>
		<a href="#" onclick="javascript:goto(${source},${pag.totalPage});">最后一页</a>
		</c:if>
		</div>
		</c:if>
        <div class="blankW9"></div>
        </div>
        <div class="clear"></div>
    </div>
  </div>
</div>
</div>
<jsp:include page="../include_bottom.jsp"></jsp:include>
<script language="JavaScript" src="js/ocscript.js" type="text/javascript"></script>

</body>
</html>