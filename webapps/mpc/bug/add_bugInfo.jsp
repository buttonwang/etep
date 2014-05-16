<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<c:if test="${p.para.itemId!=null&&p.para.itemId[0]!=null&&p.para.historyTestStatusId!=null&&p.para.historyTestStatusId[0]!=null}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>捉虫</title>
	<link href="css/style_blue.css" rel="stylesheet" type="text/css" />
	<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
	<script src="../../admin/js/m.js"></script>
	<script type="text/javascript" src="../../admin/js/jquery.form.js"></script>
<script>
<!--
	$(function(){
		$("textarea").one("click", function(){
			$(this).text("");
		}); 
		$("form").submit(function( ){
				var _info = $(this).attr("_info")||"";
				var opt  = {
					dataType :"html"
					,url:   $(this).attr("action")
					,success: function(html){ 
						if(html.indexOf("isSuccess")>0){
							alert(_info+" 保存成功");
							try{showBugItem($("#itemId_i").val())}catch(e){}
							try{tb_remove();}catch(e){}
						}else{
							alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));
						}
					} 
					,error:function(e){
						alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));					
					}
				};
//				if(checkDataSel())
				$(this).ajaxSubmit(opt);
				<%--//window.location.reload(false);--%>
				return false; 
		})
		$("div[t=checkedPositionInfo]").each(function(){
			var pi=	$(this).attr("positionInfo");
			var i=0;	 
			$(this).find(":checkbox").each(function(){
				var checked=false;
				try{
					if(pi.substring(i++,i)==0){
						checked =  true;	
					}
				}catch(e){} 
				$(this).attr("checked",checked);
			})
		})
		$("#cancle_b").click(function(){
			window.parent.tb_remove();
		})
	})
	function firstIsObject(obj){
		return typeof(obj)=="object"&&obj!=null;
	}
	function Init(sel,obj){
		$(sel).find("input").each(function(){
			jit = $(this);
			var val = obj[jit.attr("n")]||"";
			if(jit.is("input")){
				jit.val(val)
			}else if(jit.is("textarea")){
				jit.val(val)
			}else if($.inArray("John", ["span","div","label","ul","li"])){
				jit.html(val);
			}
		})
	}
	function BitItem(size,defaultStatus){
		this.arr = [];//0真1假
		for(var i=0;i<size;i++){
			this.arr[i]=(defaultStatus==="0"||defaultStatus===true||defaultStatus===0)?"0":"1";
		}
	}
	BitItem.prototype={
		set:function(index,status){
			if(index>0){
				this.arr[index-1]=(status==="0"||status===true||status===0)?"0":"1";
			}
		}
		,get:function(index){
			if(index>0){
				return this.arr[index-1];
			}
			return "1";
		}
		,regionTo:function(start,end,status){
			if(start>0&&end>0){
				if(start>end){
					var _t = start;
					start = end;
					end = _t;
				}
				for(var i=start-1;i<end;i++){
					this.arr[i]=(status==="0"||status===true||status===0)?"0":"1";
				}
			}
		}
		,toString:function(){
			return this.arr.join("");
		}
	}
	function isEmpty(jo){
		var v = false;
		var trimIt = false;
		var type = jo.attr("type") ;
		if((jo.is("input")&&(type=="text"||type=="hidden"))||jo.is("textarea")){
			var s =jo.val() ;
			if(trimIt){
				s= $.trim(s);
			}
			v = s.length>0;
		}else if(jo.is("input")&&(type=="checkbox"||type=="radio")){
			v = jo.attr("checked")||false;
		}
		return v;
	}
	function BitItemInstance( defaultStatus,inputId,changeSel){
		var size = $(changeSel).size();
		
		var trimIt = true;
		var bitItem = new BitItem (size,defaultStatus);	
		var positionInfo = function(bitItem){
			$(inputId).val(bitItem.toString());
		}
		var ifRadio=function(jo){
			return jo.is("input")&&jo.attr("type")=="radio";
		}
		var _i = 0;
		var _iKey ="__index__";
		var jRadios ={};
		$(changeSel).each(function(){
			var jo = $(this);
			
			jo.data(_iKey, jo.data(_iKey)||++_i);
			
			if(isEmpty(jo)){
				bitItem.set(jo.data(_iKey),isEmpty(jo));
			}
			if(ifRadio(jo)){
				jo.attr("name",jo.attr("name")||"___name___"+_i);
				var name = jo.attr("name");
				jRadios[name] = jRadios[name]||[];
				var length = jRadios[name].length;
				jRadios[name][length]=jo;
			}
			var type = jo.attr("type");
			jo.change(function(){
				if(ifRadio(jo)){
					var sameNameRadios = jRadios[jo.attr("name")];
					for(var i in sameNameRadios){
						bitItem.set(sameNameRadios[i].data(_iKey),isEmpty(sameNameRadios[i]));
					}
					bitItem.set(jo.data(_iKey),isEmpty(jo));
				}else{
					bitItem.set(jo.data(_iKey),isEmpty(jo));
				}
				positionInfo(bitItem);
			})
		})
		positionInfo(bitItem)
	}
	
	function catchBug(){
		var args = arguments;
		var m  = {};
		var itemId,processInstanceId,historyAnswerStatusId,itemType ;
		if(args.length==1){
			if(firstIsObject(args[0])){
				var m = args[0];
			}
		}else if(args.length>1){
			itemId = m.itemId = args[0]||0;
			processInstanceId = m.processInstanceId = args[1]||0;
			historyAnswerStatusId = m.historyAnswerStatusId = args[2]||0;
			itemType = m.itemType = args[3]||"选择题";
			itemSize = m.itemSize = args[4]||30;
		}
		Init("div[_t=init]",m);
		new BitItemInstance(false,"#positionInfo","div[_t=init]  :input" );
		if(itemType=="选择题"){
		}
	}
	
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

	//提交捉虫信息的时候，验证虫子的位置是否已经选择。如果已经选择，则提交，如果没有选择，则返回，让
	//用户选择后再提交
	//AUTHOR: L.赵亚　DATE: 2010.04.23.16.27
	function checkDataSel(){
		if(checkLock()) return false;
		var p1 = document.getElementById("bugSite1").checked;
		var p2 = document.getElementById("bugSite2").checked;
		var p3 = document.getElementById("bugSite3").checked;
		var p4 = document.getElementById("bugSite4").checked;
//		var textArea = $("#textarea").val();
//		if(textArea=="关于试题本身的任何疑问，请在这里留言！"){
//			$("#textarea").val("");
//		}
		if(p1||p2||p3||p4){
			$("#formSub").submit();
			lock("正在保存捉虫信息");
		} else {
			alert("请先选择虫子位置再提交！");
			return false;
		}
		return true;
	}

	$(function(){ 
		$("#subButton").click(checkDataSel);
		$("#textarea").focus(function(){
			if($(this).val()=="关于试题本身的任何疑问，请在这里留言！"){
				$(this).val("");
				$(this).focus();
			}
		}).blur(function(){
			if(!$(this).val())
				$(this).val("关于试题本身的任何疑问，请在这里留言！");
		});

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
		catchBug( );
	})
//-->
</script>
<style>
<!--
	.floatBox_care_this{padding:5px 0 5px 20px;}
	.floatBox_care_this ul li{float:left; margin-right:30px;}
	.floatBox_care_this img,.floatBox img,.floatBox_tt img{padding:0 3px;}
	.prefix{
		font:Verdana, Geneva, sans-serif;
		color:#036;
	}
//-->
</style>
	</head>
	<body>
	<input type="hidden" id="hasLocked" name="hasLocked" value="no"/>
	<input type="hidden" id="lockInfo" name="lockInfo" value=""/>
	<div id="loading"  name="top"
		style="position:absolute;left:250px;display:none;top:250px;">
		<img src="../images/loading.gif" />
			<font color="blue">操作正在执行！请稍候...</font></div>
	<div class="floatBox">
		<div class="clear"></div>
		<div id="addNewBugInfo">
			<form id="formSub" action="../bug/bug!addBugInfo.jhtml" method="post" >
				<div id="itemBugInfoPosition">
					<div class="floatBox_tt"><b><img src="../bug/images/bug_b.gif" width="16" height="16" align="absmiddle" />试题捉虫</b></div>
					<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
					<div name="content"  _t="init">
						<h3><span class="cDEGray">在有疑问的答案前打勾</span></h3>
						<div class="floatBox_care_this">
							<ul>
							<c:choose>
							<c:when test="${fn:length(itemAnswerVO.subItemAnswerVOLst)>0}">
								<h6><c:forEach items="${itemAnswerVO.subItemAnswerVOLst}" var="svo" varStatus="svoStatus">
										子题（${svoStatus.index+1}）：
									<c:set var="answerViews" value="${svo.answerViews}"/>
									<c:if test="${fn:length(answerViews)==0}">
										没有做答<br/>
									</c:if>
									<c:if test="${fn:length(answerViews)>0}">
										<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
											<input type="checkbox" name="p.para.cccccc"/>
											<c:if test="${fn:trim(av)!=''}">
												<c:choose>
													<c:when test="${fn:contains(av,'math>')}">
														${av}
													</c:when>
													<c:otherwise>${av}</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${fn:trim(av)==''}">未作答</c:if>&nbsp;&nbsp;
										</c:forEach>
										<br />
									</c:if>
								</c:forEach></h6>
							</c:when>
							<c:otherwise> 
								<c:set var="answerViews" value="${itemAnswerVO.answerViews}"/>
								<c:forEach items="${answerViews}" var="av" varStatus="avStatus">
									<input type="checkbox" name="p.para.cccccc"/>
									<c:if test="${fn:trim(av)!=''}">
										<c:choose>
											<c:when test="${fn:contains(av,'math>')}">
												${av}
											</c:when>
											<c:otherwise>${av}</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${fn:trim(av)==''}">未作答</c:if>&nbsp;&nbsp;
								</c:forEach>
							</c:otherwise>
							</c:choose>
							</ul>
						</div>
					</div>
					</c:if>
				</div>
				<c:if test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)==false}">
				<!-- 
				<div class="floatBox_tt">
					<b><img src="../bug/images/bug_b.gif" width="16" height="16" 
							align="absmiddle" />新捉虫信息</b></div>
							 -->
				</c:if>
				<input name="p.para.nodeInstanceId" n="nodeInstanceId" value="${p.para.nodeInstanceId[0]}" 
					style="display:none" id="nodeInstanceId_i"/>
				<input name="p.para.itemId" n="itemId" value="${p.para.itemId[0]}"  title="试题id" 
					style="display:none" id="itemId_i"/>
				<input name="p.para.subItemId" n="subItemId" value="${p.para.subItemId[0]}"  title="子题id" 
					style="display:none" />
				<input name="p.para.historyTestStatusId" n="historyTestStatusId" 
					value="${p.para.historyTestStatusId[0]}"  title="历史答题信息状态id" style="display:none"/>
				<font color="red">选择虫子位置</font>
				<br />
				<input type="radio" name="p.para.bugSite" id="bugSite1" value="1"/>试题　&nbsp;
				<input type="radio" name="p.para.bugSite" id="bugSite2" value="2"/>详解　&nbsp;
				<input type="radio" name="p.para.bugSite" id="bugSite3" value="3"/>答案　&nbsp;
				<input type="radio" name="p.para.bugSite" id="bugSite4" value="4"/>其它（如发现错别字等，尽量详细描述）
				<textarea class="padding10px box1 cDGray" name="p.para.submitInfo" id="textarea" 
					cols="45" rows="5">关于试题本身的任何疑问，请在这里留言！</textarea>
				<input name="p.para.positionInfo" id="positionInfo"  title="有疑问的答案" size="60" 
					style="display:none"/>
				<div class="btn">
					<span class="bbs1">
						<button id="subButton" type="button" class="bb1" onMouseOut="this.className='bb1';" 
							onMouseOver="this.className='bb2';" >提交</button>
					</span>
					<span class="gbs1">
						<button class="gb1" onMouseOut="this.className='gb1';" 
							onMouseOver="this.className='gb2';" id="cancle_b">取消</button>
					</span> </div>
			</form>
		</div>
		<div name="item_bugInfos">
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
									<c:set var="answerViewsN" value="${svo.answerViews}"/>
									<c:if test="${fn:length(answerViews)==0}">没有做答<br/></c:if>
									<c:if test="${fn:length(answerViews)>0}">
										<c:forEach items="${answerViewsN}" var="av" varStatus="avStatus">
											<input type="checkbox" name="tt" disabled="disabled" />
											<c:if test="${fn:trim(av)!=''}">${av}</c:if>
											<c:if test="${fn:trim(av)==''}">未作答</c:if>&nbsp;&nbsp;
										</c:forEach>
										<br />
									</c:if>
								</c:forEach></h6>
							</c:when>
							<c:otherwise>
								<c:set var="answerViewsN" value="${bugInfoHistoryAnswerStatus.itemAnswerVO.answerViews}"/>
								<c:forEach items="${answerViewsN}" var="av" varStatus="avStatus">
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
		</div>
	</div>
	</body>
	</html>
</c:if>