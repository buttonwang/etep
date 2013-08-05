<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>试题关注列表</title>
<link href="../../mpc/css/style_blue.css" rel="stylesheet" type="text/css" />
<style > 
a{text-decoration:none;}
a:hover{color:#67acdd;text-decoration:none;}
</style>
<script src="../../admin/js/m.js" type="text/javascript"></script>
<script>
function setIframeHeight(height){
	$("#item_iframe").height(height);
}
$(function(){
	$("#changeOrder_s").change(function(){
		if($(this).val()!="")
			$("#search_i").click();
		
	})
})
</script>
</head>
<body style="height:100%">
<script>
function changeShow(isHideIframe){
	var is = isHideIframe||"true"
	if(isHideIframe=="true"){
		$("#item_iframe,#apDiv2").hide();
		$("#contentLayout,#footde,#apDiv1").show();
	}else{
		$("#item_iframe,#apDiv2").show();
		$("#contentLayout,#apDiv1,#footde").hide();
	}
}
$(function(){
	var index = 0;
	var now = index;
	var jshowA = $("a[t=iframe][_optIs=show]");
	var jeditA = $("a[t=iframe][_optIs=bug]");
	var total = jshowA.size();
	var clickIsShowOrUpdate = "show";
	var time;
	wH = $(this).height();
	$(window).resize(function(){
		wH = $(this).height();
	});
	var reInitIframeHeight=function(time){
		if(time==undefined){
			time = setInterval(
			 ' $("#item_iframe").height(wH-15);',2000);
		}
	};
	$("a[t=iframe]").each(function(){
		$(this).attr("_index",index++).click(function(e){
			now = $(this).attr("_i");
			clickIsShowOrUpdate = $(this).attr("_optIs")||"show"; 
			$("#item_iframe").attr("src",$(this).attr("href")).show();
			//reInitIframeHeight();
			changeShow(1); 
			stopBD(e,1,2);
		})
	})
	 
	
	var triggleA = function( ){
		var a;
		if(clickIsShowOrUpdate=="show"){
			a =jshowA.get(now);
		}else{
			a =jeditA.get(now);
		}
		if(a){
			$(a).trigger("click");
		}
	}
	$("#opt").find("input").click(function(){
		var t = $(this).attr("_t");		 
		if(t=="returnList"){
			changeShow("true");
		}else if(t=="just"){
			changeShow("false");
		}else if(t=="prev"){
			if(--now< 0){
				now=0;
				alert("已到所在页第一题")
				return;
			}
			triggleA( );
		}else if(t=="next"){
			if(++now>=total){
				now=total-1;
				alert("已到所在页最后一题");
				return;
			} 
			triggleA();
		}
	})
})
</script>
<div id=opt align="center">
	<div id="apDiv1" style="display:none;">
		<input type="button" class="btn_2k3" value="刚才所在页面" _t=just />
	</div>
	<div id="apDiv2" style="display:none">
		<input type="button" class="btn_2k3" value="上一题" _t=prev />
		&nbsp;&nbsp;
		<input type="button" class="btn_2k3" value="返回题目列表页" _t=returnList />
		&nbsp;&nbsp;
		<input type="button" class="btn_2k3" value="下一题" _t=next />
		&nbsp;&nbsp; </div>
</div>
<IFRAME id="item_iframe" border=0 name=ye_xy marginWidth=0 frameSpacing=0 marginHeight=0 src="" 
frameBorder=0 noResize width="100%" scrolling=auto height="100%" vspale="0" style="display:none"></IFRAME>
<div id="contentLayout" class="wm950">
	<!--Satr left-->
	<div class="content_left wm950">
		<div class="content_bg">
			<!--yuanjiao-->
			<div class="yr_bg2">
				<div class=ye_r_t>
				<div class=ye_l_t>
				<div class=ye_r_b>
				<div class=ye_l_b>
				<form action="attentionAdmin!listAdmin.jhtml?p.para.subjectCode=${p.para.subjectCode[0]}" id="pageForm" method="post">
				<input type="hidden" value="${pageNo}" name="pageNo"/>
				<div class="content">
				<div class="con_right2"  align="center">  
				<div class="tager_in">
		        <h2 align="left">
		        	<span class="f14px cblue fB"></span>题型：
	        		<c:forEach items="${itemTypeNameLst}" var="itname">
	        			<c:choose>
	        				<c:when test="${p.para.itemTypeP[0]==itname}">
	        					<input name="p.para.itemTypeP" type="radio" value="${itname}" checked="checked"/>
	        				</c:when>
	        			<c:otherwise>
	        				<input name="p.para.itemTypeP" type="radio" value="${itname}"/>
	        			</c:otherwise>
	        			</c:choose>
	        			${itname}
	        		</c:forEach>
		        </h2>
		        <h2 align="left">
		        	<span class="f14px cblue fB"></span>标签：
		        	<c:forEach items="${Tags}" var="tag">
			        	<c:forEach items="${p.para.tags}" var="tagId">
			        		<c:if test="${tag.id==tagId}">
			        			<c:set var="tag_checked">checked="checked"</c:set>
			        		</c:if>
			        	</c:forEach>
		        		<input type="checkbox" value="${tag.id}" name="p.para.tags" ${tag_checked}/>
		        	${tag.tag}<c:set var="tag_checked" value=""/>
		        	</c:forEach>
		        </h2>
				<h2 align="left">
					<span class="f14px cblue fB"></span>显示：
					<input name="p.para.showType" type="radio" value="全部" ${p.para.showType[0]=='全部'?'checked="checked"':''}/>显示全部试题 &nbsp;
					<input name="p.para.showType" type="radio" value="无精华" ${p.para.showType[0]=='无精华'?'checked="checked"':''}/>只显示无精华笔记的题 &nbsp;
					<input name="p.para.showType" type="radio" value="有精华" ${p.para.showType[0]=='有精华'?'checked="checked"':''}/>只显示有精华笔记的题 &nbsp;
					<input name="p.para.showType" type="radio" value="无笔记" ${p.para.showType[0]=='无笔记'?'checked="checked"':''}/>只显示无笔记的题 &nbsp;
				</h2>
				<h6 style="height:50px; margin-top:10px">
					<table align="left">
					<tr>
					<td width="10%"></td>
					<td width="40%"><input value=" 查 询 " type="submit" id="search_i"/></td>
					<td width="40%">&nbsp;</td>
					</tr>
					</table>
				</h6>
				</div>       
 				<div class="blankW_9"></div>
        		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="gzlist">		
          		<tr style="height:30px;background:#f0f0f0; height:30px;" class="gz_title">
          		<td width="5%"></td>
		 		<td align="left">&nbsp;&nbsp;
            	<select name="p.para.orderBy" id="changeOrder_s" >
            		<option value="">选择排序方式</option>
            		<option value="popularity" ${p.para.orderBy[0]=='popularity'?'selected="selected"':''}>受关注人气↑</option>
            		<option value="totalNote" ${p.para.orderBy[0]=='totalNote'?'selected="selected"':''}>笔记总数↑</option>
            		<option value="noteSummary" ${p.para.orderBy[0]=='noteSummary'?'selected="selected"':''}>精华笔记↑</option>
            		<option value="itemType" ${p.para.orderBy[0]=='itemType'?'selected="selected"':''}>题    型↑</option>
            		<option value="itemDiffculty" ${p.para.orderBy[0]=='itemDiffculty'?'selected="selected"':''}>难    度↑</option>
       			</select>
	       		</td>
	            <td width="10%" align="center">受关注人气</td>
	            <td width="10%" align="center">笔记总数</td>
	            <td width="10%" align="center">精华笔记</td>
	            <td width="10%" align="center">题型</td>
	            <td width="5%" align="center">难度</td>
				<td width="5%"></td>
	            </tr>
				<c:forEach items="${page.result}" var="iaVO" varStatus="iaStatus">
          		<tr align="left">
          		<td width="5%"></td>
	            <td>&nbsp;&nbsp;
	            	<div style="overflow:hidden"><a t="iframe" _optIs="show"  _i="${iaStatus.index}" 
	            	href="attentionAdmin!showAdmin.jhtml?p.para.itemId=${iaVO.item.id}">
	            		${iaVO.item.content}</a></div>
	            </td>
	            <td align="center">${iaVO.itemExtraInfo.popularity}</td>
	            <td align="center">${iaVO.itemExtraInfo.noteAmount}</td>
	            <td align="center">
	            	${iaVO.itemExtraInfo.noteSummary!=null &&
	            	fn:trim(iaVO.itemExtraInfo.noteSummary)!=""?'<img src="images/very.gif" width="16" height="16" />':"--"}
	            </td>
	            <td align="center">${iaVO.item.itemType.name}</td>
	            <td align="center">${iaVO.item.difficultyValue}</td>
				<td width="5%"></td>
	            </tr>
				</c:forEach>
        		</table>
		        <div class="manu">
					<ambow:pagination actionName=""        					  
					  total="${page.totalPageCount}"
					  totalCount="${page.totalCount}" 
					  num="${page.currentPageNo}" 
					  otherParams="" />
				</div>
        </div></div></form>
		</div>
		</div>
		</div>
		</div>
		</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
</body>
</html>