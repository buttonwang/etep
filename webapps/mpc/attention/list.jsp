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
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<style > 
a{text-decoration:none;}
a:hover{color:#67acdd;text-decoration:none;}
</style>
<%--
<script src="../../admin/js/m.js" type="text/javascript"></script>
<script type="text/javascript" src="../../admin/js/jquery.form.js"></script>
<script type="text/javascript" src="../js/reInitIfameHeight.js"></script>
<script>
function changeShow(isHideIframe){
	var is = isHideIframe||"true"
	if(isHideIframe=="true"){
		$("#item_iframe,#opt").hide();
		$("#differentItems").show();
	}else{
		$("#item_iframe,#opt").show();
		$("#differentItems").hide();
	}
}
$(function(){
	var index = 0;
	var now = index;
	var jshowA = $("a[t=iframe][_optIs=show]");
	var total = jshowA.size();
	var clickIsShowOrUpdate = "show";
	var time; 
	$("a[t=iframe]").each(function(){
		$(this).attr("_index",index++).click(function(e){
			now = $(this).attr("_i");
			clickIsShowOrUpdate = $(this).attr("_optIs")||"show"; 
			$("#item_iframe").attr("src",$(this).attr("href")).show();
			$("#itemIds").val($(this).attr("itemId"));
			$("#abadonBugInfoByItemIds").removeAttr("disabled");			 
			changeShow(1); 
			stopBD(e,1,2);
		})
	})
	$("#abadonBugInfoByItemIds_form").submit(function( ){
			if($.trim($("#itemIds").val())!=""){
				var _info = $(this).attr("_info")||"";
				var opt  = {
					dataType :"html"
					,url:   $(this).attr("action")
					,success: function(html){ 
						if(html.indexOf("isSuccess")>0){
							alert(_info+" 操作成功");
							window.location.reload(false);
						}else{
							alert([_info," 操作失败！！！,请稍后再试"].join("\n")); 
						}
					} 
					,error:function(e){
						alert([_info," 操作失败！！！,请稍后再试"].join("\n"));
					}
				};
				$(this).ajaxSubmit(opt); 
			} 
			return false; 
	})
	
	$("#abadonBugInfoByItemIds_button").click(function(){ 
		if(confirm("确认要删除")){
			$("#abadonBugInfoByItemIds_form").trigger("submit");
		}
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
	$("#opt").find("button").click(function(){
		var t = $(this).attr("_t");		 
		if(t=="returnList"){
			changeShow("true");
		}else if(t=="just"){
			changeShow("true"); 
		}else if(t=="prev"){
			if(--now<0){
				now=0;
				alert("已到所在页第一题");
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
	
	$("#selectAll_0").click(function(){
		$("input[name=bugInfoIds]").attr("checked",$(this).attr("checked"));
	})
	$("#delAll").submit(function(){
		var delBugInfoIds = "";
		var i=0;
		$("input[name=bugInfoIds]:checked").each(function(){
			if(i++>0){
				delBugInfoIds+=",";
			}
			delBugInfoIds+=$(this).attr("value");
		})
		if(delBugInfoIds!=""){
			$("#delAll #p_para_bugInfoIds").val(delBugInfoIds);
			var opt  = {
				dataType :"html"
				,url:   $(this).attr("action")
				,success: function(html){ 
					if(html.indexOf("isSuccess")>0){
						alert(_info+" 操作成功"); 
					}else{
						alert([_info,"操作失败！！！\n "].join("\n"));
					}
				} 
				,error:function(e){
					alert([_info," 保存失败！！！\n "].join("\n"));					
				}
			};
			if(confirm("确认删除")){
				$(this).ajaxSubmit(opt);
				window.location.href=window.location.href+"&___="+Math.random();
			}
		}
		return false;
	})
	
})
</script>
--%>
</head>
<body> 
<jsp:include page="../exam/include_head.jsp"></jsp:include>
<div id=differentItems  > 
	<div id="contentLayout" class="wm950">
		<!--Satr left-->
		<div class="wm950">
			<div class="content_bg">
				<div class=ye_r_t>
					<div class=ye_l_t>
						<div class=ye_r_b>
							<div class=ye_l_b> 
								 
								<div class="task_con bg">
									<div id="bugInfos" t="bugInfos_div">											
											 
											<div class="bg Buglist">
												<table  class="answers920">
												<tr>
													<td style="background-color:#F0F0F0;width:5%"></td>
													<td style="background-color:#F0F0F0;width:55%" align="left">&nbsp;&nbsp;题目内容</td>
													<td style="background-color:#F0F0F0;width:20%">最后关注时间</td>
													<td style="background-color:#F0F0F0;width:10%">题型</td>
													<td style="background-color:#F0F0F0;width:10%">难度</td>
												</tr>									
												<c:forEach items="${pageItemAttention.result}" var="ia" varStatus="iaStatus"> 
													<tr>
														<td><input name="bugInfoIds" type="checkbox" value="${bihas.bugInfo.id}"/></td>
														<td align="left">
														<a  t="iframe" _optIs="show" _i="${bihasStatus.index}" itemId="${_item.id}" 
															href="showAttention.jhtml?mode=1&itemId=${ia.item.id}&hisId=${ia.historyAnswerStatus.id}"  target="_blank">
															&nbsp;&nbsp;  ${ia.item.content} &nbsp;&nbsp;
														</a></td>
														<td>${ia.insertTime}</td>
														<td>${ia.item.itemType.name}</td>
														<td>${ia.item.difficultyValue}</td>
													</tr>  
												 </c:forEach>
												</table>
											</div>
											<div class=" blankW_6"></div>
									</div>
									<div class="blankW9"></div>
								</div>
								<div class="clear"></div>
								<div class="list_inp"> 
									<ambow:pagination actionName="" total="${page.totalPageCount}" totalCount="${page.totalCount}" num="${page.currentPageNo}"  otherParams="" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--End left-->
		<div class="clear"></div>
	</div>
</div>
<div id="confirmDel">
	<div class="floatBoxs" id=sign02  style='z-index:33;display:none;'>
		<h3>删除记录</h3>
		<div class="floatBox">
			<h1>确定删除选定的捉虫记录？</h1>
			<div class="content box1">
				<ul>
					<li style="text-align:center">（删除后仍可在“逐题回顾”再次捉虫）</li>
				</ul>
			</div>
			<div class="btn"> <span class="bbs1">
				<button class="bb1" onMouseOut="this.className='bb1';" onMouseOver="this.className='bb2';" onClick="javascript:doit(sign01);doit(sign02)">确认</button>
				</span> <span class="bbs1">
				<button class="bb1" onMouseOut="this.className='bb1';" onMouseOver="this.className='bb2';" onClick="javascript:doit(sign01);doit(sign02)" >取消</button>
				</span>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>
<div id=opt style="display:none">
	<div class="wm950">
		<div class="content_bg">
		<IFRAME id="item_iframe" t="reInitIframeHeight" border=0 name=ye_xy marginWidth=0 frameSpacing=0 marginHeight=0 src="" frameBorder=0 noResize width="100%" scrolling="no" height="100%" vspale="0" style="display:xxx"></IFRAME>
		<form id="abadonBugInfoByItemIds_form" action="bug!abadonBugsByItemIds.jhtml"><input id="itemIds" name="p.para.itemIds" type="hidden"/></form>
			<div class="bnt_box"> <span class="bbs1">
				<button class="bb1" onMouseOut="this.className='bb1';" onMouseOver="this.className='bb2';"  _t=prev >上一题</button>
				</span> <span class="bbs1">
				<button class="bb1" onMouseOut="this.className='bb1';" onMouseOver="this.className='bb2';" _t=next >下一题</button>
				</span><span class="bbs1">
				<button class="bb1" onMouseOut="this.className='bb1';" onMouseOver="this.className='bb2';" id="abadonBugInfoByItemIds_button">删除此题捉虫信息</button>
				</span><span class="bbs1">
				<button class="bb1" onMouseOut="this.className='bb1';" onMouseOver="this.className='bb2';"  _t=just >返回试题列表</button>
				</span> </div>
			<div class="clear"></div>
		</div>
	</div>
</div>  
<div id="footde" class="wm950"><span>Copyright © 2010</span>Ambow Education Group <a href="" target="_blank"></a> <span>版权所有</span></div>
</body>
</html>
