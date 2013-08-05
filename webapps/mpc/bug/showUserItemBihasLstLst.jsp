<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html XMLNS:m="http://www.w3.org/1998/Math/MathML">
<OBJECT ID=MathPlayer CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987">
</OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>试题捉虫信息</title>
<link href="../../admin/css/admin.css" rel="stylesheet" type="text/css" />
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/thickbox.css" type="text/css" media="screen" />
<script src="../../admin/js/m.js" type="text/javascript"></script>
<script type="text/javascript" src="../../admin/js/jquery.form.js"></script>
<script>
function show(cid){
	try{$("#"+cid).toggle();}catch(e){} 
}
function hide(cid){
	try{getE(cid).style.display= "none";}catch(e){} 
}
function getE(cid){
	var elem;
	if(cid!=undefined&&cid!=null){
		elem =  document.getElementById(cid);
	}
	if(elem==undefined){
		elem = null;
	}
	return elem;
}
 
function center(id){
	$(window).resize(function(){
		try{
			with($("#"+id).get(0).style){
				left = (document.body.offsetWidth-parseInt(width))/2;
				top = (document.body.offsetHeight-parseInt(height))/2;
			}
		}catch(e){}
	})
}
$(function(){ 
	$("*[t=show]").each(function(){
		var id = $(this).attr("showId");
		var jid=$("#"+id);
		jid.click(function(){
			return false;
		})
		$(this).click(function(){
			try{
				jid.toggle(); 
				return false;
			}catch(e){}
		})
	})
	$("body").click(function(){
		$("*[t=showId]").hide();
	})
}) 
</script>
<script>
function setToText(toId ,formId){
	$("#"+toId).val($("#"+formId).attr("_v"));
} 
$(function(){
	$("form[name!=modiReplyForm]").submit(function( ){
			var _info = $(this).attr("_info")||"";
			var opt  = {
				dataType :"html"
				,url:   $(this).attr("action")
				,success: function(html){ 
					if(html.indexOf("isSuccess")>0){
						alert(_info+" 操作成功 ");
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
			return false; 
	})
	$("div[t=checkedPositionInfo]").each(function(){
		var pi=	$(this).attr("positionInfo");
		var i=0;	 
		$(this).find(":checkbox").each(function(){
			var checked=false;
			try{
				if(pi.substring(i++,i)==0){					
					checked =  true ;	
				}
			}catch(e){} 
			$(this).attr("checked",checked);
		})
	})
	$("div[id=_title]").click(function(){
		$(this).parent().find(">div[id=_content]").toggle();
	});
})
</script>
<script language="javascript">
<!--
	function toRevise() {
		window.open("../../admin/itembank/itemRevise!show.jhtml?itemId=${item.id}&subject_code=${item.subject.code}", "_blank");
	}
	function refreshItem() {
		if(checkLock())
			return;
		window.location.reload();
	}

	function editItem() {
		window.open('../../admin/itembank/item!edit.jhtml?id=${item.id}', '_blank');
	}
	function lock(info){
		//document.getElementById("loading").style.display = "";
		$("#L_" + $("#L_bugInfoId_form").val() + "_dataSubMess").show();
		$("#hasLocked").val("yes");
		$("#lockInfo").val(info);
	}
	function releaseLock(){
		//document.getElementById("loading").style.display = "none";
		$("#L_" + $("#L_bugInfoId_form").val() + "_dataSubMess").hide();
		$("#hasLocked").val("no");
		$("#lockInfo").val("");
	}
	function checkLock(){
		if($("#hasLocked").val()=="yes"){
			alert($("#lockInfo").val()+"！请稍后再执行此操作...");
			return true;
		}
		return false;
	}
	//点击“修改”链接时，需要把所有其它的修改界面隐藏，只打开当前要修改的回复信息
	function modiHref(idN){
		if(checkLock()) return false;
		$("[id^=L_][id$=_toModi]").hide();
		var content = $("#"+idN+"_content").val();
		var mess = "";
		if(content.indexOf("纠正了一个严重错误")>-1){
			document.getElementById(idN+"_reply_sel_500").checked = true;
			mess = "纠正了一个严重错误. ";
		} else if(content.indexOf("纠正了一个一般错误")>-1){
			document.getElementById(idN+"_reply_sel_200").checked = true;
			mess = "纠正了一个一般错误. ";
		} else if(content.indexOf("纠正了一个细微错误")>-1){
			document.getElementById(idN+"_reply_sel_100").checked = true;
			mess = "纠正了一个细微错误. ";
		} else if(content.indexOf("纠错无效，试题无误")>-1){
			document.getElementById(idN+"_reply_sel_20").checked = true;
			mess = "纠错无效，试题无误. ";
		} else if(content.indexOf("恶意纠错")>-1){
			document.getElementById(idN+"_reply_sel_0").checked = true;
			mess = "恶意纠错. ";
		}
		content = content.replace(mess, "");
		if(content)
			$("#"+idN+"_reply").val(content);
		$("#"+idN+"_toModi").show();
		$("#"+idN+"_href").hide();
		return false;
	}

	//AUTHOR: L.赵亚
	$(function(){
		$("textarea[id^=L_][id$=_reply]").blur(function(){
			if(!$(this).val()) $(this).val("在此给学生留言，可以不输。");
		});
		$(":button[name^=L_][name$=_sub]").click(function(){
			if($(":radio[name=L_" + $(this).attr("vtt") + "_reply_sel]:checked").length==0){
				alert("请先选择纠错类型，再回复...");
				return false;
			}
			$("#L_replyInfoPrefix_form").val($(":radio[name=L_" + $(this).attr("vtt") + "_reply_sel]:checked").attr("title"));
			$("#L_replyPoint_form").val($(":radio[name=L_" + $(this).attr("vtt") + "_reply_sel]:checked").val());
			$("#L_replyInfo_form").val($("#L_"+$(this).attr("vtt")+"_reply").val());
			$("#L_bugInfoId_form").val($(this).attr("vtt"));
			$("#modiReplyForm").submit();
			return false;
		});
		$(":button[name^=L_][name$=_reset]").click(function(){
			if(checkLock()) return false;
			var name = this.name.replace("_reset", "");
			$("#"+name+"_href").show();
			$("#"+name+"_toModi").hide();
		});
		//回复信息修改FORM表单数据的提交
		$("#modiReplyForm").submit(function( ){
			if(checkLock()) return false;
			if(!$("#L_replyInfoPrefix_form").val()){
				alert("请选择一个回复类型...");
				return false;
			}
			var opt  = {
				dataType :"html"
				,url : $(this).attr("action")
				,success: function(responseText, statusText){
					releaseLock();
					if(responseText.indexOf("yes")>-1){
						var texts = responseText.split("[L_split_L]");
						if(texts.length==2){
							$("#L_"+$("#L_bugInfoId_form").val()+"_reply_mess").html(texts[1]);
							$("#L_"+$("#L_bugInfoId_form").val()+"_content").val(texts[1]);
//							$("#L_"+$("#L_bugInfoId_form").val()+"_reply_time").html(texts[2]);
						}
						$("#L_"+$("#L_bugInfoId_form").val()+"_afterSub").show();
						$("#L_"+$("#L_bugInfoId_form").val()+"_href").show();
						$("#L_"+$("#L_bugInfoId_form").val()+"_toModi").hide();
						$("#L_"+$("#L_bugInfoId_form").val()+"_afterSub").fadeOut(3000);
					} else {
						alert("修改失败！请稍后重试..."); 
					}
				} 
				,error:function(responseText, statusText){
					releaseLock();
					alert("修改失败！请稍后再试...");
				},
				clearForm: true
			};
			lock("正在保存回复的修改信息！");
			$(this).ajaxSubmit(opt); 
			return false; 
		})
	});
//-->
</script>
<style>
.prefix{
	font:Verdana, Geneva, sans-serif;
	color:#036;
}
</style>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; ${title}</td>
  </tr>
</table>
<form name="modiReplyForm" id="modiReplyForm" action="bug!modiReplyBugInfo.jhtml" method="post">
	<input type="hidden" name="p.para.replyInfoPrefix" id="L_replyInfoPrefix_form" value="" />
	<input type="hidden" name="p.para.replyInfo" id="L_replyInfo_form" value="" />
	<input type="hidden" name="p.para.replyPoint" id="L_replyPoint_form" value="" />
	<input type="hidden" name="p.para.bugInfoId" id="L_bugInfoId_form" value="" />
</form>
	<input type="hidden" id="hasLocked" name="hasLocked" value="no"/>
	<input type="hidden" id="lockInfo" name="lockInfo" value=""/>
<div id="contentLayout" class="wm950">
	<!--Satr left-->
	<div class="wm950">
		<div class="content_bg">
			<div class="ye_r_t">
				<div class="ye_l_t">
					<div class="ye_r_b">
						<div class="ye_l_b">
							<div name="item">								
								<div class="content_box920">
									<div class="content_titer920">
										<table width="100%">
											<tr><td width="40%">
												<b>题型：${item.itemType.name}；分值：${fn:replace(item.score,".0","")}分；难度：${fn:replace(item.difficultyValue,".0","")}&nbsp;</b>
											</td>
											<td width="20%">
											<c:set var="tiShiId" value="tishi_${item.id}_${bStatus.index}" scope="request"/>
											<c:set var="xiangJieId" value="xiangJie_${item.id}_${bStatus.index}" scope="request"/>
											<c:set var="answerId"  value="answer_${item.id}_${bStatus.index}" scope="request"/>
											
											<img alt="提示" src="../images/prompt_ico.gif" onClick="show('${tiShiId}')" style="cursor:pointer"/>
											<img alt="详解" src="../images/dwell_ico.gif"  onClick="show('${xiangJieId}')" style="cursor:pointer"/>
											<img alt="答案" src="../images/Answer_ico.gif" onclick="show('${answerId}')" style="cursor:pointer"/>
											
											</td>
											<td width="30%">
											<c:forEach items="${item.knowledgePoints}" var="kp">
											 	所属知识点：${kp.name}
											</c:forEach>
											</td>
										</tr>
										</table>
									</div >
									<div class="clear"></div>
									<div class="answers920">
										<div name="item_content" >
											<p align="left">${item.content}</p>
											<p>&nbsp;</p>
											<div class="clear"></div>
											<div name="item_sourceChoose">
											<c:if test="${fn:contains(sigleAndMutilChoose,item.itemType.code)}">
												<div id="prompt13" class="prompt">
													<h5><span class="left f14px fB">原始选项：</span>
														<span class="left">
															<table width="100%">
															<c:forEach items="${item.answerOptions}" var="rav" varStatus="ravStatus">
																<tr><td width="10%"></td><td>${rav.code}. ${rav.content} &nbsp;&nbsp;</td></tr>
															</c:forEach>
															</table>
														</span>
													</h5>
												<div class="blank3"></div>
												</div>	
											</c:if>
											</div>
											<!--提示层开始-->
											<div name="item_tishi" id="${tiShiId}">
												<div id="prompt12" v="close"class="prompt">
													<h5><span class="left f14px fB">提示：</span><span class="right"><img src="../images/close.gif" style="cursor:pointer" onClick="hide('${tiShiId}')"/></span></h5>
													<p>${item.hint}</p>
												</div>
											</div> 
										    <!--提示层结束-->
											<!--详解层开始-->
											<div name="item_xiangjie" id="${xiangJieId}">
												<div id="prompt12" v="close"class="prompt">
													<h5><span class="left f14px fB">详解：</span><span class="right"><img src="../images/close.gif" style="cursor:pointer" onClick="hide('${xiangJieId}')"/></span></h5>
													<p>${item.analysisAtLarge1}</p>
												</div>
											</div> 
											<!--详解层结束-->
											<!--答案层开始-->
											<div name="item_answer" id="${answerId}">
												<c:if test="${fn:length(item.subItems)==0}">
													<div id="prompt12" v="close" class="prompt">
														<h5><span class="left f14px fB">正确答案：</span>
															<span class="right">
																<img src="../images/close.gif" style="cursor:pointer" 
																	onclick="hide('${answerId}')"/></span></h5>
														<c:forEach items ="${item.answers}" var="av" varStatus="avStatus" >
															<c:if test="${fn:length(item.answers) > 1}">
															<img src="../images/arrow_${avStatus.count}.gif" />
															</c:if>
															${fn:replace(av.value,'$','<font color="red">或</font>')}&nbsp;&nbsp;&nbsp;
														</c:forEach>
													</div>
												</c:if>
											</div>
											<!--答案层结束-->
											<p><b>code: ${item.code}</b></p>
										</div>
										
										<%--子题题目显示层开始--%>										
										<c:if test="${fn:length(item.subItems)>0}">
											<c:forEach items="${item.subItems}" var="subItem" varStatus="svoStatus"> 
												<div name="oneSubItem">
													<div class="content_titer920">
														<ul>
															<li class="content_titer920_l">
																<b> 子题（${svoStatus.index+1}）  &nbsp;
																	分值：${fn:replace(subItem.score,".0","")}	&nbsp;分 ；
																	 难度： ${fn:replace(subItem.difficultyValue,".0","")}&nbsp;</b></li>
																<c:set var="tiShiId" value="tishi_${item.id}_${subItem.id}_${bStatus.index}" scope="request"/>
																<c:set var="xiangJieId" value="xianJie_${item.id}_${subItem.id}_${bStatus.index}" scope="request"/>
																<c:set var="answerId"  value="answer_${item.id}_${subItem.id}_${bStatus.index}" scope="request"/>
															<li class="content_titer920_c">
																<img alt="提示" src="../images/prompt_ico.gif" onClick="show('${tiShiId}')"    style="cursor:pointer"/>
																<img alt="详解" src="../images/dwell_ico.gif"  onClick="show('${xiangJieId}')" style="cursor:pointer"/>
																<img alt="答案" src="../images/Answer_ico.gif" onClick="show('${answerId}')"   style="cursor:pointer"/>
															</li>
														</ul>
													</div>
													<p align="left">${subItem.content} </p>
													<p>&nbsp;</p>
													<div class="clear"></div>
													
													<div name="subItem_answer">
														<div name="item_tishi" id="${tiShiId}">
															<div id="prompt12" v="close"class="prompt">
																<h5><span class="left f14px fB">提示：</span>
																<span class="right"><img src="../images/close.gif" style="cursor:pointer"
																	 onClick="hide('${tiShiId}')"/></span></h5>
																<div class="blank3"></div>	 
																<p>${subItem.hint}</p>
															</div>
														</div> 
														<div name="item_xiangjie" id="${xiangJieId}">
															<div id="prompt12" v="close"class="prompt">
																<h5><span class="left f14px fB">详解：</span>
																	<span class="right">
																		<img src="../images/close.gif" style="cursor:pointer" 
																			onclick="hide('${xiangJieId}')" alt="${xiangJieId}"/></span></h5>
																<div class="blank3"></div>
																<p>${subItem.analysisAtLarge1}</p>
															</div>
														</div>
														<c:set var="_answer" value="${fn:split(subItem.correctAnswer,'；')}"/>
														<c:if test="${fn:length(_answer)>0}">
														<div id="${answerId}" >
															<div id="prompt12" v="close"class="prompt">
																<h5>
																	<span class="left f14px fB">正确答案：</span>
																	<span class="right">
																		<img src="../images/close.gif" style="cursor:pointer" 
																			onclick="hide('${answerId}')" alt="${answerId}"/>
																	</span></h5>
																<div class="blank3"></div>
																<p><c:set var="_answer" value="${fn:split(subItem.correctAnswer,'；')}"/>
																	<c:forEach items ="${_answer}" var="__av" varStatus="avStatus" > 														 
																		<c:set var="_data" value="${__av}" scope="request"/>
																		<jsp:include page="i_dealTo_mathAnswer.jsp"/>${_tmp} 
																		<c:remove var="_tmp" scope="request" />
																		<c:remove var="_data" scope="request" />&nbsp;
																	</c:forEach>
																</p>
															</div>
														</div>
														</c:if>
													</div>
												</div>										 
											</c:forEach>
										</c:if>
										<%--子题题目显示结束--%>
										
										<%--捉虫信息显示层开始--%>
										<div name="item_perBugInfo">
											<div class="blank6"></div>
											<c:forEach items="${userBihasItemAnswerVOLstLst}" var="userBIVOLst" varStatus="userBIVOLstS" >
												<c:set var="user" value="${userBIVOLst.user}"/>
												<br />
												<div  t="showHide">
													<div class="box1 padding10px" id="_title" style="cursor:pointer">
														<span class="left f14px fB">用户：</span> ${user.loginName}
													</div>
													<div id="_content" style="display:XXX" >
													<c:forEach items="${userBIVOLst.bihasItemAnswerVOLst}" var="bihasItemAnswerVO" varStatus="bStatus" >
														<c:set var="bihas" value="${bihasItemAnswerVO.bihas}"/>
														<c:set var="itemAnswerVO" value="${bihas.itemAnswerVO}" scope="request"/>
														<span class="prefix" >第${bStatus.count}条 提交的捉虫信息
														(<a href="../exam/examWidget!historyAnswerWidget.jhtml?itemId=${ item.id }&hisId=${ bihas.historyAnswerStatus.id }" 
															target="_blank" >查看作答情况</a>)：</span>
														<c:if test="${bihas.bugInfo.status!=-1}">
															<div class="box1 padding10px">
																<div  name="positionInfo" t="checkedPositionInfo" positionInfo="${bihas.positionInfo}">
																	<ul>
																	<c:choose>
																		<c:when test="${fn:length(itemAnswerVO.subItemAnswerVOLst)>0}"><h6>
																		<c:forEach items="${itemAnswerVO.subItemAnswerVOLst}" var="svo" 
																				varStatus="svoStatus"> 子题（${svoStatus.index+1}）：
																			<c:set var="answerViews" value="${svo.answerViews}"/>
																			<c:if test="${fn:length(answerViews)==0}"> 没有做答<br/></c:if>
																			<c:if test="${fn:length(answerViews)>0}">
																				<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
																					<input type="checkbox" name="tt" disabled="disabled" />
																					<c:if test="${fn:trim(av)!=''}">
																						<c:if test="${fn:contains(av,'math>')}">
																							<c:set var="gs_id" value="gs_${ user.id }_${itemAnswerVO.item.id}_${svo.subItem.id}_${ bStatus.index }_${avStatus.index}" />
																							<span>
																								<span id="${gs_id}" t="showId" style=" display:none;position:absolute;background-color:#CCC">																		
																								<table >
																									<tr>
																										<td>用户答案：</td>
																										<td><textarea rows="5" cols="60">${fn:replace(av," ","")}</textarea></td>
																									</tr>
																									<tr>
																										<td>标准答案：</td> 
																										<td><c:forEach items="${svo.subItem.answers}" var="a" varStatus="aS">
																											<c:if test="${aS.index==avStatus.index}">
																				 							<textarea id="t_${gs_id}" rows="5" cols="60" ></textarea>
																											 <script>
																											 	document.getElementById('t_${gs_id}').value= "${a.value}".replace(/\s/gi,'').replace(/\\$/g,'\r\r');
																											 </script>
																											</c:if>
																										</c:forEach></td>
																									</tr>
																									<tr><td colspan="2">
																										<c:choose>
																											<c:when test="${fn:length(fn:trim(svo.subItem.answerGroup))>0}">
																												<input type="button" class="btn_2k3" 
																													onclick="window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" 
																													value='转至试题编辑' />&nbsp;&nbsp;
																											</c:when>
																											<c:otherwise> 
																												<input type="button" class="btn_2k3" 
																													onclick="if(!confirm('确认增加？')) return false;$('#f_${gs_id}').trigger('submit');window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" 
																													value='增加答案并转到编辑' />&nbsp;&nbsp;
																												<input type="submit" class="btn_2k3" 
																													onclick="if(!confirm('确认增加？')) return false;$('#f_${gs_id}').trigger('submit');" value="增加答案"/>
																											</c:otherwise>
																										</c:choose>
																									</td></tr>
																								</table>
																								<form id="f_${gs_id}" action="../../admin/itembank/item!addAnswer.jhtml" method="post"  >
																									<input name="p.para.itemId" value="${itemAnswerVO.item.id}" style="display:none"/>
																									<input name="p.para.subItemId" value="${svo.subItem.id}" style="display:none"/>
																									<input name="p.para.addAnswerIndex" value="${avStatus.index}" style="display:none"/>
																									<textarea name="p.para.addAnswerContent" style="display:none">${av}</textarea>
																								</form>
																								<span>
																								
																								</span>
																								</span>
																								<span t="show" showId="${gs_id}" >
																									<font color="red" style="cursor:pointer"><b>对比</b></font></span>
																							</span>
																						</c:if>
																					</c:if>
																					<c:if test="${fn:trim(av)==''}">未作答</c:if>
																					&nbsp;&nbsp; 
																				</c:forEach>
																			</c:if>
																			<br />
																		</c:forEach>
																	</h6></c:when>
																	<c:otherwise>
																		<c:set var="realAnswerOptions" value="${itemAnswerVO.item.answerOptions}"/>
																		<c:set var="answerOptionOrder" value="${bihas.historyAnswerStatus.answerOptionOrder}" />
																		<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)}">
																			<c:set var="obj" value="${itemAnswerVO.item}" scope="request"/> 
																			<c:set var="_answers" value="${fn:split(itemAnswerVO.answerStr,'@:@')}" scope="request"/> 
																			<c:set var="answerOptionOrder" value="${bihas.historyAnswerStatus.answerOptionOrder}" scope="request"/> 
																			<c:set var="xiangJieId" value="xianJie_${itemAnswerVO.item.id}_${bStatus.index}_${user.id}" scope="request"/>
																			<c:set var="answerId"  value="answer_${itemAnswerVO.item.id}_${bStatus.index}_${user.id}" scope="request"/>
																			<c:set var="iVO" value="${bihas.itemAnswerVO}" scope="request"/>
																			<jsp:include page="answer_prototype_user.jsp"/>
																		</c:if>
																		<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
																			<c:set var="answerViews" value="${itemAnswerVO.answerViews}"/>
																			<span class="prefix" >疑问答案：</span> 
																			<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
																				<input type="checkbox" name="tt" disabled="disabled"/>
																				<c:if test="${fn:trim(av)!=''}">
																					<c:choose>
																						<c:when test="${fn:contains(av,'math>')}"> 
																							${av}
																							<c:set var="gs_id" value="gs_${ user.id }_${itemAnswerVO.item.id}_${svo.subItem.id}_${ bStatus.index }_${avStatus.index}" />
																							<span>
																								<span t="show" showId="${gs_id}" >
																									<font color="red" style="cursor:pointer"><b>对比</b></font>
																								</span>
																								<span id="${gs_id}" t="showId" 
																										style="display:none;position:absolute;background-color:#CCC">
																									<table>
																										<tr>
																											<td>用户答案：</td>
																											<td><textarea rows="5" cols="60" >${av}</textarea></td>
																										</tr>
																										<tr>
																											<td>标准答案：</td> 
																											<td><c:forEach items="${item.answers}" var="a" varStatus="aS">
																												<c:if test="${aS.index==avStatus.index}">
																		 											<textarea id="t_${gs_id}" rows="5" cols="60" ></textarea>
																												 	<script>
																														document.getElementById('t_${gs_id}').value =
																															"${a.value}".replace(/\s/gi,'').replace(/\\$/g,'\r\r');
																												 	</script>
																												</c:if>
																											</c:forEach></td>
																										</tr>
																										<tr><td colspan="2">
																											<c:choose>
																												<c:when test="${fn:length(fn:trim(itemAnswerVO.item.answerGroup))>0}">
																													<input type="button" class="btn_2k3" onclick="window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" 
																														value='转至试题编辑' />&nbsp;&nbsp;
																												</c:when>
																												<c:otherwise>
																													<input type="button" class="btn_2k3" 
																														onclick="if(!confirm('确认增加？')) return false;$('#f_${gs_id}').trigger('submit');window.open('../../admin/itembank/item!edit.jhtml?id=${itemAnswerVO.item.id}')" 
																														value='增加答案并转到编辑' />&nbsp;&nbsp;
																													<input type="submit" class="btn_2k3" 
																														onclick="if(!confirm('确认增加？')) return false;$('#f_${gs_id}').trigger('submit');" value="增加答案"/>																			
																												</c:otherwise>
																											</c:choose>
																										</td></tr>
																									</table>
																									<form id="f_${gs_id}" action="../../admin/itembank/item!addAnswer.jhtml" method="post"  >
																										<input name="p.para.itemId" value="${itemAnswerVO.item.id}" style="display:none"/>
																										<input name="p.para.subItemId" value="${svo.subItem.id}" style="display:none"/>
																										<input name="p.para.addAnswerIndex" value="${avStatus.index}" style="display:none"/>
																										<textarea name="p.para.addAnswerContent" style="display:none">${av}</textarea>
																									</form>
																									<span>
																								
																									</span>
																								</span>
																							</span>
																						</c:when>
																						<c:otherwise>${av}</c:otherwise>
																					</c:choose>
																				</c:if>
																				<c:if test="${fn:trim(av)==''}">未作答</c:if>
																				&nbsp;&nbsp; </c:forEach>
																			</c:if>
																		</c:otherwise>
																	</c:choose>
																	</ul>
																	<div class="clear"></div>
																</div>
																<h6 style="width:500px">
																	<span class="prefix" >虫子位置：</span>
																	<c:choose>
																	<c:when test="${bihas.bugInfo.bugSite==1}">
																		试题&nbsp;&nbsp;&nbsp;
																	</c:when>
																	<c:when test="${bihas.bugInfo.bugSite==2}">
																		详解&nbsp;&nbsp;&nbsp;
																	</c:when>
																	<c:when test="${bihas.bugInfo.bugSite==3}">
																		答案&nbsp;&nbsp;&nbsp;
																	</c:when>
																	<c:when test="${bihas.bugInfo.bugSite==4}">
																		其它&nbsp;&nbsp;&nbsp;
																	</c:when>
																	<c:otherwise>
																		&nbsp;
																	</c:otherwise>
																	</c:choose>
																</h6> 
																<div name="submit">
																	<h6 style="width:500px">
																		<span class="prefix" >留言信息：</span>
																		${bihas.bugInfo.submitInfo}&nbsp;&nbsp;&nbsp;
																		<span style="text-align:right; width:200px" > ${bihas.bugInfo.submitTime}</span>
																	</h6>
																</div>
																<div name="reply">
																	<div class="blank3"></div>
																	<c:if test="${bihas.bugInfo.replyInfo==null}">
																		<form action="bug!replyBugInfo.jhtml" method="post">
																			<table >
																		 		<tr><td>
																		 			<c:forEach items="${replyInfoTemplates}" var="r" varStatus="rStatus">
																						<input type="radio" name="p.para.replyPoint" 
																							value="${r.replyPoint}" _v="${r.replyContent}" 
																							id="r_${user.id}_${bihas.id}_${bStatus.index}_${rStatus.index}" 
						onclick="setToText('prefix_${user.id}_${bihas.id}_${bStatus.index}','r_${user.id}_${bihas.id}_${bStatus.index}_${rStatus.index}');$('#t_${user.id}_${bihas.id}_${bStatus.index}').trigger('click')" />${r.replyContent} &nbsp;&nbsp; 
																		 			</c:forEach>
																					<input type="hidden" name="p.para.replyInfoPrefix" value="" id="prefix_${user.id}_${bihas.id}_${bStatus.index}" />
																		 
																		 		</td></tr>
																				 <tr><td>
																					<input type="hidden" name="p.para.bugInfoId" value="${bihas.bugInfo.id}" />
																					<textarea name="p.para.replyInfo" cols="60" rows="5"  
																						id="t_${user.id}_${bihas.id}_${bStatus.index}" 
																						onclick="if(this.value=='在此给学生留言，可以不输。')this.value='';" 
																						type="hidden">在此给学生留言，可以不输。</textarea><br />
										
																					<button class="bb1" onmouseout="this.className='bb1';" 
																						onmouseover="this.className='bb2';"  type="submit" 
																						onclick="if(document.getElementById('prefix_${user.id}_${bihas.id}_${bStatus.index}').value==''){alert('请先选择一个回复类型');return false;}">回复</button>
																				</td></tr>
																			</table>
																		</form>
																	</c:if>
																	<c:if test="${bihas.bugInfo.replyInfo!=null}">
																		<h6>
																			管理员回复： <font id="L_${ bihas.bugInfo.id }_reply_mess" 
																				color="red">${bihas.bugInfo.replyInfo}</font>&nbsp;
																			<span style="text-align:right; width:200px" 
																				id="L_${ bihas.bugInfo.id }_reply_time">${bihas.bugInfo.replyTime}</span>
																			&nbsp;
																			<a href="#" id="L_${bihas.bugInfo.id}_href" 
																				vtt="${bihas.bugInfo.id}"  
																				onclick="return modiHref('L_${bihas.bugInfo.id}');"><b>修改</b></a>
																		</h6>
																		<div id="L_${bihas.bugInfo.id}_afterSub" 
																				style="position:absolute;left:250px;display:none;">
																			<img src="../images/loading.gif" />
																				<font color="blue">信息已成功保存...</font></div>
																		<input type="hidden" id="L_${ bihas.bugInfo.id }_content"
																			name="L_${ bihas.bugInfo.id }_content"
																			value="${bihas.bugInfo.replyInfo}" />
																		<table id="L_${ bihas.bugInfo.id }_toModi" style="display:none">
																			<tr><td>
																			 	<c:forEach items="${replyInfoTemplates}" var="r" varStatus="rStatus">
																					<input type="radio" name="L_${ bihas.bugInfo.id }_reply_sel" 
																						value="${r.replyPoint}" title="${r.replyContent}"
																						id="L_${ bihas.bugInfo.id }_reply_sel_${r.replyPoint}"
																						_v="${r.replyContent}" />${r.replyContent} &nbsp;&nbsp; 
																			 	</c:forEach>
																		 	</td></tr>
																			<tr><td>
																				<div id="L_${bihas.bugInfo.id}_dataSubMess" 
																					style="position:relative;left:250px;display:none;top:50px;">
																					<img src="../images/loading.gif" />
																						<font color="blue">操作正在执行！请稍候...</font>
																				</div>
																				<textarea name="p.para.replyInfo" cols="60" rows="5" 
																					id="L_${bihas.bugInfo.id}_reply" 
																						onclick="if(this.value=='在此给学生留言，可以不输。') this.value='';"
																						type="hidden">在此给学生留言，可以不输。</textarea><br />
																				<button class="bb1" name="L_${bihas.bugInfo.id}_sub" onmouseout="this.className='bb1';" 
																					onmouseover="this.className='bb2';"	vtt="${bihas.bugInfo.id}"  
																					type="button" >回复</button>&nbsp;
																				<button class="bb1" name="L_${bihas.bugInfo.id}_reset" onmouseout="this.className='bb1';" 
																					onmouseover="this.className='bb2';" type="button" >取消</button>
																			</td></tr>
																		</table>
																	</c:if>
																</div>
															</div>
														</c:if>
													</c:forEach>
													</div>
												</div>
											</c:forEach>
 										</div>
										<%--捉虫信息显示层结束--%>
										<br />
										<span class="pager">
										<button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';" onclick="toRevise()">审校试题</button></span>    
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--End left-->
 <!--stars tan div-->
<div class="floatBoxs" id="sign02" style='z-index:33;display:none;'>
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
<!--End tan div--> 
 
</body>
</html>