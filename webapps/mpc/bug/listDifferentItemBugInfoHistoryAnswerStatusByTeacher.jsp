<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>试题捉虫列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../../admin/css/admin.css" rel="stylesheet" type="text/css">
<style>
.logininputmanage {
	height:18px;
}
</style>
<script src="../../admin/js/admin.js" type="text/javascript"></script>
<script src="../../admin/js/m.js"> </script>
<script>
function changeShow(isHideIframe){
	var is = isHideIframe||"true"
	if(isHideIframe=="true"){
		$("#item_iframe,#apDiv2").hide();
		$("#pageForm,#apDiv1").show();
	}else{
		$("#item_iframe,#apDiv2").show();
		$("#pageForm,#apDiv1").hide();
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
			reInitIframeHeight();
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
})</script>
<script type="text/javascript">
$(function(){
	try{
		var searchKey = '${searchKey}';
		if(searchKey!=''){
			$("#p_para_value").val($(searchKey.split(".","_").val()));
		}
	}catch(e){
	}
})
</script>
<script type="text/javascript">

function generalSubmit(queryType){
	var obj=document.getElementById('pageForm');
	document.getElementById('pageNo').value = 1;
	document.getElementById('queryType').value = queryType;
	obj.submit();
}

//批量删除
function batchdelete() {
    var codes = getCheckedIntValue("checkitem");
    if (codes=='') {
    	alert('请先选中项目，再删除试题！');
    	return;
    }
    if (confirm("确定要删除吗？")) {
  		var actionurl = 'item!deleteBatch.jhtml?ids='+codes+'&amp;subject_code=${subject_code}';
  		window.location.href = actionurl;
  	}
}
//批量审核
function verifyBatch() {
    var codes = getCheckedIntValue("checkitem");
    if (codes=='') {
    	alert('请先选中项目，再审核试题！');
    	return;
    }
    if (confirm("确定要审核吗？")) {
  		var actionurl = 'item!verifyBatch.jhtml?ids='+ codes+'&amp;subject_code=${subject_code}';
  		window.location.href = actionurl;
  	}	
}
//批量作废
function invalidBatch() {
    var codes = getCheckedIntValue("checkitem");
    if (codes=='') {
    	alert('请先选中项目，再作废试题！');
    	return;
    }       
    if (confirm("确定要作废吗？")) {
  		var actionurl = 'item!invalidBatch.jhtml?ids='+ codes+'&amp;subject_code=${subject_code}';
  		window.location.href = actionurl;
  	}	
}
  
//试卷中入卷  
function assembling() {	
    var ids = getCheckedIntValue("checkitem");
    var paperId = document.getElementById('paperId').value;
    
    if (ids == '') {
    	alert('请选中试题后入卷！');
    	return;
    }
    if (confirm("确定要入卷吗？")) {
  		var actionurl = 'paper!assemble.jhtml?id=${paperId}&itemids=' + ids+'&amp;subject_code=${subject_code}';
  		window.parent.window.location.href = actionurl;
  	}
}

//试题中入卷
function assembling2() {
    var ids = getCheckedIntValue("checkitem");
        
    if (ids == '') {
    	alert('请选中试题后入卷！');
    	return;
    }
    inPapers(ids);
}
</script>
<script language="javascript">
<!--
	$(function(){
		<c:if test="${ b.bugInfo.status==1 }" >
			$("#showMessReply_id").show();
			$("#replyInfo_id").show();
		</c:if>
		<c:if test="${ b.bugInfo.status!=1 }" >
			$("#showMessReply_id").hide();
			$("#replyInfo_id").hide();
		</c:if>
		$("input[name=bugInfoStatus]").click(function(){
			if($(this).val()=="1"){
				$("#showMessReply_id").show();
				$("#replyInfo_id").show();
			} else {
				$("#showMessReply_id").hide();
				$("#replyInfo_id").val("");
				$("#replyInfo_id").hide();
			}
		});
	})
//-->
</script>
<style>
.ctl {
	font-size:12px;
	color:#555555;
	line-height: 20px;
	background-color:#CFECF2;
	width:100%;
	margin-top:10px;
	table-layout:fixed
}
.ctl td {
	text-overflow:ellipsis;
	overflow:hidden;
	white-space: nowrap;
	padding:2px
}
#apDiv1, #apDiv2 {
	position:absolute;
	background-color:#22B0DF;
	left:300px;
	top:6px;
	width:400px;
	height:26px;
	vertical-align:middle;
	cursor:pointer;
}
.logininputmanage1 {
	height:18px;
}
</style>
</head>
<body>
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
<IFRAME id="item_iframe" border=0 name=ye_xy marginWidth=0 frameSpacing=0 marginHeight=0 src="" frameBorder=0 noResize width="100%" scrolling=auto height="100%" vspale="0" style="display:none"></IFRAME>
<form  id="pageForm" method="post" action="bug!listDifferentItemBugInfoHistoryAnswerStatusByTeacher.jhtml">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：题库 &gt; 试题管理 &gt; ${title}</td>
		</tr>
	</table>
	<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
		<tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left" ><input class="logininputmanage1" value="${pageNo}" name="pageNo" id="pageNo" type="hidden"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<select name="p.para.searchKey" style="height:20px;">
								<option value="b.historyAnswerStatus.item.id" ${searchKey eq 'b.historyAnswerStatus.item.id'? 'selected':''}>试题编号</option>
								<option value="b.historyAnswerStatus.subItem.id" ${searchKey eq 'b.historyAnswerStatus.subItem.id'? 'selected':''}>子题编号</option>
								<option value="b.historyAnswerStatus.item.content" ${searchKey eq 'b.historyAnswerStatus.item.content'? 'selected':''}>题干</option>
								<option value="b.historyAnswerStatus.item.subject.code" ${searchKey eq 'b.historyAnswerStatus.item.subject.code'? 'selected':''}>学科编号</option>
								<option value="b.bugInfo.bug.asfProcessInstance.id" ${searchKey eq 'b.bugInfo.bug.asfProcessInstance.id'? 'selected':''}>流程实例编号</option>
								<option value="b.historyAnswerStatus.id" ${searchKey eq 'b.historyAnswerStatus.id'? 'selected':''}>历史状态信息编号</option>
								<option value="b.bugInfo.bug.status" ${searchKey eq 'b.bugInfo.bug.status'? 'selected':''}>捉虫信息状态</option>
								<option value="b.bugInfo.status" ${searchKey eq 'b.bugInfo.status'? 'selected':''}>虫子回复状态</option>
							</select>
							<input class="logininputmanage"  type="text" name="p.para.value" id="p_para_value"  size="15" value="${value}"/>
							&nbsp;&nbsp;
							<input type="submit" value="查询" class="btn_2k3"/>
							&nbsp;
							<input type="button" value="高级" onClick="hiddenDiv.style.display='';" class="btn_2k3"/></td>
					</tr>
					<tr>
						<td align="right"><ambow:pagination actionName=""        					  
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams="" /></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td><div id="hiddenDiv"  style="display:xxx;">
					<table class="txt12555555line-height"  width="100%" border="0" align="center"
 cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
						<tr>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">试题编号：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" size=40 name="b.historyAnswerStatus.item.code" 
									id="b_historyAnswerStatus_item_code" 
									value="${b.historyAnswerStatus.item.code}"/></td>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" 
								class="txt12blue">学科：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<select style="width:100px" class="logininputmanage" 
										name="b.historyAnswerStatus.item.subject.code"
										id="b_historyAnswerStatus_item_subject_code" disabled>
									<option selected="selected" value="" >全部</option>
									<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usgr" > <option value="${usgr.subject.code}"   
										<c:if test="${b.historyAnswerStatus.item.subject.code==usgr.subject.code}">selected="selected"</c:if>
										>${usgr.subject.name}
										</option>
									</c:forEach>
								</select>
								<input type="hidden" name="b.historyAnswerStatus.item.subject.code" 
									value="${ b.historyAnswerStatus.item.subject.code }" />
								</td>
						</tr>
						<tr>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">试题题干：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" size=40 name="b.historyAnswerStatus.item.content" 
									id="b_historyAnswerStatus_item_content" 
									value="${b.historyAnswerStatus.item.content}"/></td>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">
								虫子状态：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" type="radio" name="bugStatus" 
								value="1" ${b.bugInfo.bug.status==1?"checked":""} />
								使用	&nbsp;
								<input class="logininputmanage" type="radio" name="bugStatus" 
									value="-1" ${b.bugInfo.bug.status==-1?"checked":""}/>
								已作废  &nbsp;
								<input class="logininputmanage" type="radio"  name="bugStatus" 
									value="" ${b.bugInfo.bug.status==null?"checked":""}/>
								全部
								<script >$(function(){$("input[name=bugStatus]").click(function(){
								$("#b_bugInfo_bug_status").val($("input[name=bugStatus]:checked").val());
							})})</script>
								<input class="logininputmanage" size=40 name="b.bugInfo.bug.status" 
									id="b_bugInfo_bug_status" value="${b.bugInfo.bug.status}" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">子题编号：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" size=40 name="b.historyAnswerStatus.subItem.id" 
									id="b_historyAnswerStatus_subItem_id" 
									value="${b.historyAnswerStatus.subItem.id}"/></td>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">
								虫子回复状态：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" type="radio"  name="bugInfoStatus" 
									value="0" ${b.bugInfo.status==0?"checked":""}/>
								未回复&nbsp;
								<input class="logininputmanage" type="radio" name="bugInfoStatus" 
									value="1" ${b.bugInfo.status==1?"checked":""}/>
								已回复&nbsp;
								<input class="logininputmanage" type="radio" name="bugInfoStatus" 
									value="-1" ${b.bugInfo.status==-1?"checked":""}/>
								已作废&nbsp;
								<input class="logininputmanage" type="radio"  name="bugInfoStatus" 
									value="" ${b.bugInfo.status==null?"checked":""} />
								全部
								<script >$(function(){$("input[name=bugInfoStatus]").click(function(){
								$("#b_bugInfo_status").val($("input[name=bugInfoStatus]:checked").val());
							})})</script>
								<input class="logininputmanage" size=40 name="b.bugInfo.status" 
									id="b_bugInfo_status" value="${b.bugInfo.status}" type="hidden"/></td>
						</tr>
						<tr>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">历史答题记录编号：</td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" size=40 name="b.historyAnswerStatus.id" 
									id="b_historyAnswerStatus_id" value="${b.historyAnswerStatus.id}"/></td>
							<td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">
								&nbsp;<font id="showMessReply_id">回复类型：</font></td>
							<td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
								<select name="b.bugInfo.replyInfo" id="replyInfo_id" >
									<option value="">全部</option>
									<option value="yzcw" ${ 'yzcw' eq b.bugInfo.replyInfo ? 'selected':'' }>严重错误</option>
									<option value="ybcw" ${ 'ybcw' eq b.bugInfo.replyInfo ? 'selected':'' }>一般错误</option>
									<option value="xwcw" ${ 'xwcw' eq b.bugInfo.replyInfo ? 'selected':'' }>细微错误</option>
									<option value="ww" ${ 'ww' eq b.bugInfo.replyInfo ? 'selected':'' }>无误</option>
									<option value="ey" ${ 'ey' eq b.bugInfo.replyInfo ? 'selected':'' }>恶意</option>
								</select>&nbsp;
							</td>
						</tr>
						<tr bgcolor="#F7F7F7" height="50">
							<td align="center"  colspan="4"><input type="submit" value="   查 询   " class="btn_2k3"/>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="   取 消   " onClick="hiddenDiv.style.display='none';" class="btn_2k3"/></td>
						</tr>
					</table>
				</div></td>
		</tr>
		<tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left">&nbsp;</td>
						<td align="right"><ambow:pagination actionName=""
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}" 
        	                  num="${page.currentPageNo}"
        	                  otherParams="" /></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td><table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
					<tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
						<td width="6%">全选
							<input type="checkbox"  onclick="checkAll('checkitem');"/></td>
						<td width="45%">题干（文章）</td>
						<td width="8%">题型</td>
						<td width="6%">年份</td>
						<td width="6%">来源</td>
						<td width="4%">难度</td>
						<td width="4%">分值</td>
						<td width="7%">答题时间</td>
					</tr>
					<!-- c:forEach items="${differentItemBugInfoHistoryAnswerStatusLst}" var="bihas" varStatus="bihasStatus" -->
					<c:forEach items="${ page.result }" var="item" varStatus="bihasStatus">
						<!--c:set var="item" value ="${bihas.historyAnswerStatus.item}" /-->
						<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
							<td width="3%"><input type="checkbox" name="checkitem" value="${item.id}"  /></td>
							<td><!-- a  t="iframe" _optIs="show" _i="${bihasStatus.index}"  
									href="bug!showUserItemBihasLstLst.jhtml?p.para.itemId=${bihas.historyAnswerStatus.item.id}&p.para.bugInfoStatus=${b.bugInfo.status}&p.para.bugStatus=${b.bugInfo.bug.status}">&nbsp;&nbsp; ${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") } &nbsp;&nbsp;</a -->
								<a  t="iframe" _optIs="show" _i="${bihasStatus.index}"  
									href="bug!showUserItemBihasLstLst.jhtml?p.para.itemId=${item.id}&p.para.bugInfoStatus=${b.bugInfo.status}&p.para.bugStatus=${b.bugInfo.bug.status}">&nbsp;&nbsp; ${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") } &nbsp;&nbsp;</a>
									</td>
							<td>${item.itemType.code}(${item.itemType.name})</td>
							<td>${item.year}</td>
							<td>${item.sourceName}</td>
							<td>${item.difficultyValue}</td>
							<td>${item.score}</td>
							<td>${item.answeringTimeByMin}</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left"><c:if test="${p.para.assemble eq 0}">
								<input type="button" value="批量审核" class="btn_2k3"  onclick="verifyBatch();" />
								<input type="button" value="批量删除" class="btn_2k3"  onclick="batchdelete();" />
								<input type="button" value="批量作废" class="btn_2k3"  onclick="invalidBatch();" />
								<input type="button" value="批量入卷" class="btn_2k3"  onclick="assembling2()"  />
								<input type="hidden" value="批量导出" class="btn_2k3"  onclick="location.href='exportItem.jhtml'" />
								<input type="hidden" value="批量刷新" class="btn_2k3"  onclick="location.href='fixItem.jhtml'"  />
							</c:if>
							<c:if test="${p.para.assemble eq 1}">
								<input type="button" value="批量入卷" class="btn_2k3" onClick="assembling()"  />
							</c:if></td>
						<td align="right"><ambow:pagination actionName=""
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}" 
        	                  num="${page.currentPageNo}"
        	                  otherParams="" /></td>
					</tr>
				</table></td>
		</tr>
	</table>
</form>
</body>
</html>
