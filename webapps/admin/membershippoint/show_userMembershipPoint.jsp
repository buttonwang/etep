<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../js/m.js"> </script>
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
	var jeditA = $("a[t=iframe][_optIs=edit]");
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
	$("a[t=ajaxDel]").click(function(e){
		var url = $(this).attr("href");
		var ja = $(this);
		var errorMsg = "删除失败！！！ \n1. 可能为该题目已经被使用，不能删除\n2.查看是否超时";
		$.ajax({
			dataType : "html",
			url: url,			
			success: function(html){
				if(html.indexOf("")>-1){
					ja.parent().parent().remove();
					alert("删除成功");
				}else{
					alert(errorMsg);
				}
			 
			},error: function(){
				alert(errorMsg);	 
			}
		});
		stopBD(e,1,2);
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
			if(--now<0){
				now=0;
				alert("已到所在页第一题")
				return;
			}
			triggleA( );
		}else if(t=="next"){
			if(++now>total){
				now=total;
				alert("已到所在页最后一题");
				return;
			} 
			triggleA();
		}
	})
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
 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：积分 &gt;积分详情</td>
	</tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
	<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td align="left"><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
							<tr>
								<td width="50%" align="left"  class="txt12blueh">${webuser.loginName}&nbsp;&nbsp;的积分</td>
								<td width="50%" align="right" class="txt12blue" >&nbsp;</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td align="left" ><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">勤奋积分：</td>
								<td width="33%" align="left" bgcolor="#FFFFFF">${userMembershipPoint.diligence} </td>
								<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">智慧积分：</td>
								<td width="33%" align="left" bgcolor="#FFFFFF">${userMembershipPoint.wisdom}</td>
							</tr>
							<tr>
								<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">勇气积分：</td>
								<td width="33%" align="left" bgcolor="#FFFFFF">${userMembershipPoint.courage}</td>
								<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">奉献积分：</td>
								<td width="33%" align="left" bgcolor="#FFFFFF">${userMembershipPoint.dedicate}</td>
							</tr>
							<tr>
								<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">洞察积分：</td>
								<td width="33%" align="left" bgcolor="#FFFFFF">${userMembershipPoint.percipience}</td>
								<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">&nbsp;</td>
								<td width="33%" align="left" bgcolor="#FFFFFF">&nbsp;</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td align="left"><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
							<tr>
								<td width="50%" align="left"  class="txt12blueh">${webuser.loginName}&nbsp;&nbsp;的积分历史</td>
								<td width="50%" align="right" class="txt12blue" >&nbsp;</td>
							</tr>
						</table> </td>
				</tr>
			</table></td>
	</tr>
	<tr>
		<td><table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
				<tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
					<td width="10%"> 积分</td>
					<td width="8%">积分类型</td>
					<td width="6%">时间</td>
				</tr>
				<c:forEach items="${userMembershipPointHistoryLst}" var="mh" varStatus="mhStatus">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td>${mh.point}</td>
						<td><c:choose>
								<c:when test="${mh.pointType==1}">勤奋积分</c:when>
								<c:when test="${mh.pointType==2}">智慧积分</c:when>
								<c:when test="${mh.pointType==3}">奉献积分</c:when>
								<c:when test="${mh.pointType==4}">洞察积分</c:when>
							</c:choose></td>
						<td>${mh.operateTime}</td>
					</tr>
				</c:forEach>
			</table></td>
	</tr>
</table>
</form>
</body>
</html>
