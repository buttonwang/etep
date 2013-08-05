<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="positionInfo" value="${bugInfoHistoryAnswerStatus.positionInfo}"/>
<c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<c:set var="oneTomany" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
<OBJECT ID=MathPlayer CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987" > </OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>试题捉虫信息</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/thickbox.css" type="text/css" media="screen" />
<style>
.prefix{
	font:Verdana, Geneva, sans-serif;
	color:#036;
}
</style>
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
</script>
<script>
$(function(){
	$("form").submit(function( ){
			var _info = $(this).attr("_info")||"";
			var opt  = {
				dataType :"html"
				,url:   $(this).attr("action")
				,success: function(html){ 
					if(html.indexOf("isSuccess")>0){
						alert(_info+" 操作成功 ");
						try{
							if($("form[name=delBugInfo]").size()<=1){
								window.parent.location.reload(true);
							}
						}catch(e){}
						window.location.reload(true);
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

	
	function lock(info){
		document.getElementById("loading").style.display = "";
		$("#hasLocked").val("yes");
		$("#lockInfo").val(info);
	}
	function releaseLock(){
		document.getElementById("loading").style.display = "none";
		$("#hasLocked").val("no");
		$("#lockInfo").val("");
	}
	function checkLock(){
		if($("#hasLocked").val()=="yes"){
			alert($("#lockInfo").val()+"！请稍后再执行其它操作...");
			return true;
		}
		return false;
	}

	//作了删除的链接，点删除链接的时候，执行的功能，
	$("[name=delBug]").click(function (){
		if(checkLock()) return ;
		if(confirm("即将删除这条捉虫信息！确定继续么？")){
			lock("正在删除捉虫信息");
			$.post(
				"../bug/bug!abadonBugInfosAddBug.jhtml?p.para.bugInfoIds="+$(this).attr("delId"),{
				},function(data, textStatus) {
					releaseLock();
					if("yes"==data){
						alert("删除成功！");
						window.location.reload();
					} else if("no"==data) {
						alert("删除过程执行出现错误...");
						return ;
					} else window.location.reload();
				}
			);
		}
		return false;
	});
})
</script>
</head>
<body>
	<input type="hidden" id="hasLocked" name="hasLocked" value="no"/>
	<input type="hidden" id="lockInfo" name="lockInfo" value=""/>
	<div id="loading"  name="top"
		style="position:absolute;left:250px;display:none;top:250px;">
		<img src="../images/loading.gif" />
			<font color="blue">操作正在执行！请稍候...</font></div>
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
									<c:set var="item" value="${itemAnswerVO.item}"/>
									<div class="content_titer920"> 
										<table width="100%"><tr>
											<td width="40%">
												<b>题型：${item.itemType.name}；分值：${fn:replace(item.score,".0","")}分；难度：${fn:replace(item.difficultyValue,".0","")}&nbsp;</b>
											 	<label>
													<c:if test="${itemAnswerVO.stars>0}">
														<c:forEach var="i" begin="1" end="${itemAnswerVO.stars}" step="1">  
															<img src="../images/star.gif" width="16" height="16" />
														</c:forEach> 
													</c:if> 
												</label>
											</td>
											<td width="20%">
												<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false&&fn:contains(itemAnswerVO.item.itemType.name,'一对多')==false}">
													<img alt="详解" src="../images/dwell_ico.gif" onclick="show('xiangJie_${item.id}_${subItem.id}')" style="cursor:point"/> 
													<img alt="答案" src="../images/Answer_ico.gif"  onclick="show('answer_${item.id}_${subItem.id}') " style="cursor:point"/> 
												</c:if> 
											</td>
											<td width="30%">
												<c:forEach items="${item.knowledgePoints}" var="kp" >
													 所属知识点：${kp.name}
												</c:forEach>
											</td>
										</tr>
										</table>
									</div>
									<div class="clear"></div>
									<div class="answers920">
										<div name="item_content" >
											<p align="left">${item.content}</p>
											<p>&nbsp;</p>
											<div class="clear"></div>
											<div name="item_xiangjie">
											<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
												<c:if test="${fn:trim(item.correctAnswer)!=''}">
													<div id="xiangJie_${item.id}_${subItem.id}"  v="close"class="prompt"  style="display:none;" >
														<h5><span class="left f14px fB">详解：</span><span class="right"><img src="../images/close.gif" onClick="hide('xiangJie_${item.id}_${subItem.id}')"/></span></h5>
														<div class="blank3"></div>
														${itemAnswerVO.analysisAtLarge1}${itemAnswerVO.analysisAtLarge2}${itemAnswerVO.analysisAtLarge3}
													</div>
												</c:if>
												<c:if test="${fn:trim(item.correctAnswer)!=''}">
													<div id="answer_${item.id}_${subItem.id}"  v="close"class="prompt"  style="display:none;" >
														<h5><span class="left f14px fB">答案：</span><span class="right"><img src="../images/close.gif" onClick="hide('answer_${item.id}_${subItem.id}')"/></span></h5>
														<div class="blank3"></div> 
														<c:set var="_correctAnswer" value="${item.correctAnswer}" scope="request"/>
														<jsp:include page="i_only_mathAnswer.jsp"/>
													</div>
												</c:if>
											</c:if>
											</div> 
										</div>
										<%--子题题目显示层开始--%>
										
										<c:if test="${fn:length(itemAnswerVO.subItemAnswerVOLst)>0}" >
											<c:forEach items="${itemAnswerVO.subItemAnswerVOLst}" var="svo" varStatus="svoStatus">
												<c:set var="obj" value="${svo.subItem}"/>
												<div name="oneSubItem">
												<div class="content_titer920">
												<table width="100%"><tr>
													<td width="40%" align="left"> <b> 子题（${svoStatus.index+1}）  &nbsp;分值：${fn:replace(obj.score,".0","")}&nbsp;分 ； 难度： ${fn:replace(obj.difficultyValue,".0","")}&nbsp;</b></td>	
													<td><c:if test="${svo.stars>0}">                        	
																<c:forEach   var="i"   begin="1"   end="${svo.stars}"  step="1">  
																				<img src="../images/star.gif" width="16" height="16" />
																</c:forEach> 
																</c:if>
													</td>
													<td width="20%" align="left">
														<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
															<c:set var="subItem" value="${svo.subItem}"/>
															<img src="../images/dwell_ico.gif" onClick="show('xiangJie_${item.id}_${subItem.id}')" alt="详解" style="cursor:point"/>
															<img src="../images/Answer_ico.gif"  onclick="show('answer_${item.id}_${subItem.id}')" alt="答案" style="cursor:point"/>
														</c:if>
													</td> 
													<td width="20%" align="left">
													</td>
												</tr></table>
												</div>
												<p align="left">${obj.content}</p>
												<div name="subItem_xiangjie">
													<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
														<c:set var="subItem" value="${svo.subItem}"/>
														<div id="xiangJie_${item.id}_${subItem.id}"  v="close"class="prompt"  style="display:none;" >
															<h5><span class="left f14px fB">详解：</span><span class="right"><img src="../images/close.gif" onClick="hide(xiangJie_${item.id}_${subItem.id}')" style="cursor:point"/></span></h5>
															<div class="blank3"></div>
															${svo.analysisAtLarge1}${svo.analysisAtLarge2}${svo.analysisAtLarge2}
														</div>
														<div id="answer_${item.id}_${subItem.id}"  v="close"class="prompt"  style="display:none;" >
															<h5><span class="left f14px fB">答案：</span><span class="right"><img src="../images/close.gif" onClick="hide('answer_${item.id}_${subItem.id}')" style="cursor:point"/></span></h5>
														 	<c:forEach items="${svo.answers}" var="a" varStatus="aS">															 
																 <b><font color="red">${aS.count}.</font></b> &nbsp;&nbsp;${fn:replace(a.value,'$','&nbsp;或&nbsp;')}&nbsp;&nbsp;
															</c:forEach>
															<div class="blank3"></div>
														</div>
														</c:if>
												</div>
												<p>&nbsp;</p>
												<div class="clear"></div>	
												</div>										 
											</c:forEach>
										</c:if>
										<%--子题题目显示结束--%>
										<%--捉虫信息显示层开始--%>
										
			<c:forEach items="${bugInfoHistoryAnswerStatusList}" var="bugInfoHistoryAnswerStatus" 
				varStatus="bStatus">
			<c:if test="${bugInfoHistoryAnswerStatus.bugInfo.status!=-1}">
			<div name="item_perBugInfo">
				<div class="blank6"></div>
				<b>第&nbsp;${fn:length(bugInfoHistoryAnswerStatusList)-bStatus.index }&nbsp;条捉虫信息</b>
				<div class="box1 padding10px">
					<div  name="positionInfo" t="checkedPositionInfo" positionInfo="${bugInfoHistoryAnswerStatus.positionInfo}">
						<c:if test="${fn:contains(sigleAndMutilChoose,bugInfoHistoryAnswerStatus.itemAnswerVO.item.itemType.code)==false}">
							<span class="prefix" >疑问答案：</span> 
							<c:choose>
							<c:when test="${fn:length(bugInfoHistoryAnswerStatus.itemAnswerVO.subItemAnswerVOLst)>0}">
							<h6> <c:forEach items="${bugInfoHistoryAnswerStatus.itemAnswerVO.subItemAnswerVOLst}" var="svo" varStatus="svoStatus">
									子题（${svoStatus.index+1}）：
									<c:set var="answerViews" value="${svo.answerViews}"/>
									<c:if test="${fn:length(answerViews)==0}">没有做答<br/></c:if>
									<c:if test="${fn:length(answerViews)>0}">
										<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
											<input type="checkbox" name="tt" disabled="disabled" />
											<c:if test="${fn:trim(av)!=''}">${av}</c:if>
											<c:if test="${fn:trim(av)==''}">未作答</c:if>&nbsp;&nbsp;
										</c:forEach>
										<br />
									</c:if>
								</c:forEach></h6>
							</c:when>
							<c:otherwise>
								<c:set var="answerViews" value="${bugInfoHistoryAnswerStatus.itemAnswerVO.answerViews}"/>
								<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
									<input type="checkbox" name="tt" disabled="disabled"/>
									<c:if test="${fn:trim(av)!=''}">${av}</c:if>
									<c:if test="${fn:trim(av)==''}">未作答</c:if>&nbsp;&nbsp;
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${fn:contains(sigleAndMutilChoose,bugInfoHistoryAnswerStatus.itemAnswerVO.item.itemType.code)}">
							<c:set var="obj" value="${bugInfoHistoryAnswerStatus.itemAnswerVO.item}" scope="request"/> 
						    <c:if test="${bugInfoHistoryAnswerStatus.itemAnswerVO.answerStr==''}"> 	
							<c:set var="_answers" value="${fn:split(bugInfoHistoryAnswerStatus.itemAnswerVO.item.correctAnswer,'@:@')}" scope="request"/>	
							</c:if>
							<c:if test="${bugInfoHistoryAnswerStatus.itemAnswerVO.answerStr!=''}">
							<c:set var="_answers" value="${fn:split(bugInfoHistoryAnswerStatus.itemAnswerVO.answerStr,'@:@')}" scope="request"/>	
							</c:if>
							<c:set var="answerOptionOrder" value="${bugInfoHistoryAnswerStatus.historyAnswerStatus.answerOptionOrder}" scope="request"/> 
							<c:set var="xiangJieId" value="xianJie_${bugInfoHistoryAnswerStatus.itemAnswerVO.item.id}_${bStatus.index}" scope="request"/>
							<c:set var="answerId"  value="answer_${bugInfoHistoryAnswerStatus.itemAnswerVO.item.id}_${bStatus.index}" scope="request"/>
							<c:set var="iVO" value="${bugInfoHistoryAnswerStatus.itemAnswerVO}" scope="request"/>
							<jsp:include page="answer_prototype_user.jsp"/>
						</c:if>
						<h6 style="width:500px">
							<span class="prefix" >虫子位置：</span>
							<c:choose>
							<c:when test="${bugInfoHistoryAnswerStatus.bugInfo.bugSite==1}">
								试题&nbsp;&nbsp;&nbsp;
							</c:when>
							<c:when test="${bugInfoHistoryAnswerStatus.bugInfo.bugSite==2}">
								详解&nbsp;&nbsp;&nbsp;
							</c:when>
							<c:when test="${bugInfoHistoryAnswerStatus.bugInfo.bugSite==3}">
								答案&nbsp;&nbsp;&nbsp;
							</c:when>
							<c:when test="${bugInfoHistoryAnswerStatus.bugInfo.bugSite==4}">
								其它&nbsp;&nbsp;&nbsp;
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
							</c:choose>
						</h6>
						<h6 style="width:500px">
							<span class="prefix" >留言信息：</span>
							${bugInfoHistoryAnswerStatus.bugInfo.submitInfo}&nbsp;&nbsp;&nbsp;
						</h6>
						<table width="95%"><tr><td align="right">
								<span style="text-align:right; width:200px" > 
									提交时间：${bugInfoHistoryAnswerStatus.bugInfo.submitTime}
								</span>&nbsp;
								<a href="#top" name="delBug" delId="${ bugInfoHistoryAnswerStatus.bugInfo.id }">
									<font color="blue">删除</font></a>
							</td>
						</tr></table>
					</div>
					<div name="reply">
						<div class="blank3"></div>
						<c:if test="${bugInfoHistoryAnswerStatus.bugInfo.replyInfo==null}">
						<h6>正在处理中...... 请耐心等待</h6> 
						</c:if>
						<c:if test="${bugInfoHistoryAnswerStatus.bugInfo.replyInfo!=null}">
							<h6>
								<span class="prefix" >管理员回复：</span>
								<font color="red">${bugInfoHistoryAnswerStatus.bugInfo.replyInfo} </font>
								<span style="text-align:right; width:200px" > ${bugInfoHistoryAnswerStatus.bugInfo.replyTime}</span>
							</h6>
						</c:if>
					</div>
				</div>
			</div>
			</c:if>
			</c:forEach>
										<%--捉虫信息显示层结束--%>
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
<!--End tan div--> 
<script language="javascript">
<!--
<c:if test="${ 'yes' eq delBugFlag }">
	alert("无法浏览捉虫信息：该捉虫信息不存在！\n\n出现此提示原因为：您在提交了该捉虫信息后，在管理员回复之前执行了删除操作\n\n点 确定 关闭本窗口");
	window.close();
</c:if>
//-->
</script>
</body>
</html>
