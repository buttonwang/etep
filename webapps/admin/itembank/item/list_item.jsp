<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>试题列表</title>
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
		if(confirm("确定要删除该题?")){
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
		}
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
.ctl td{text-overflow:ellipsis;overflow:hidden;white-space: nowrap;padding:2px}
#apDiv1,#apDiv2{
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
	<div id="apDiv1" style="display:none;"><input type="button" class="btn_2k3" value="刚才所在页面" _t=just /></div>
	<div id="apDiv2" style="display:none">
		<input type="button" class="btn_2k3" value="上一题" _t=prev />&nbsp;&nbsp;
		<input type="button" class="btn_2k3" value="返回题目列表页" _t=returnList />&nbsp;&nbsp;
		<input type="button" class="btn_2k3" value="下一题" _t=next />&nbsp;&nbsp;
	</div>
</div>
<IFRAME id="item_iframe" border=0 name=ye_xy marginWidth=0 frameSpacing=0 marginHeight=0 src="" frameBorder=0 noResize width="100%" scrolling=auto height="100%" vspale="0" style="display:none"></IFRAME>
<form  id="pageForm" method="post" action="item!list.jhtml">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; ${title}</td>
  </tr>
</table>

<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">	  
      <tr>
      	<td align="left" >
      			<input type="hidden" value="${pageNo}" name="pageNo" id="pageNo"/>
				<input type="hidden" value="${subject_code}" name="subject_code" id="subject_code"/>	
	      		<input type="button" value=" 新 建 " class="btn_2k3" ${assemble eq 1? 'style="display: none"':''} 
	      		onClick="javascript: window.location.href='addItem!choose.jhtml?subject_code=${subject_code}';" />&nbsp;&nbsp;&nbsp;&nbsp;
			    <select name="queryConditions.field" style="height:20px;">
                  <option value="code" ${queryConditions.field eq 'code'? 'selected="selected"':''}>试题编码：</option>
			      <option value="content" ${queryConditions.field eq 'content'? 'selected="selected"':''}>题干：</option>
			      <option value="regionName" ${queryConditions.field eq 'regionName'? 'selected="selected"':''}>适用地区：</option>
			      <option value="subjectName" ${queryConditions.field eq 'subjectName'? 'selected="selected"':''}>所属学科：</option>
			      <option value="gradeName" ${queryConditions.field eq 'gradeName'? 'selected="selected"':''}>适用学级：</option>
			      <option value="itemTypeName" ${queryConditions.field eq 'itemTypeName'? 'selected="selected"':''}>题型：</option>
			      <option value="knowledgePointName" ${queryConditions.field eq 'knowledgePointName'? 'selected="selected"':''}>知识点：</option>
			      <option value="itemThemeName" ${queryConditions.field eq 'itemThemeName'? 'selected="selected"':''}>题材：</option>
			      <option value="year" ${queryConditions.field eq 'year'? 'selected="selected"':''}>试题年份：</option>
			      <option value="source" ${queryConditions.field eq 'source'? 'selected="selected"':''}>试题来源：</option>
			      <option value="difficultyValue" ${queryConditions.field eq 'difficultyValue'? 'selected="selected"':''}>试题难度：</option>
			      <option value="validityValue" ${queryConditions.field eq 'validityValue'? 'selected="selected"':''}>试题效度：</option>
			      <option value="applicableObject" ${queryConditions.field eq 'applicableObject'? 'selected="selected"':''}>适用对象：</option>
			      <option value="status" ${queryConditions.field eq 'status'? 'selected="selected"':''}>状态：</option>
		      </select>
			    <input  type="text" name="queryConditions.value" id="textfield9" size="15" value="${queryConditions.value}"/>&nbsp;&nbsp;
			    <input type="button" value="查询" class="btn_2k3" onClick="generalSubmit(0);"/>&nbsp;
			    <input type="button" value="高级" onClick="hiddenDiv.style.display='';" class="btn_2k3"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="grade_code" id="grade_code">
							<option value="">选择年级</option>
					<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
						<c:if test="${subject_code == usrg.subject.code}">
							<c:forEach items="${usrg.grades}" var="grade" >
										<option value="${grade.code}" ${grade_code==grade.code?"selected":""}>${grade.name}</option>
							</c:forEach>
						</c:if>
					</c:forEach>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value=" 筛 选 " class="btn_2k3"  onClick="generalSubmit(1)"/>
				</td>
       
      </tr>
      <tr>
       <td align="right">         
        	<ambow:pagination actionName=""        					  
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams="" />
        </td>
      </tr>
    </table>
	</td>
	</tr>
	<tr>
	<td>
<div id="hiddenDiv"  style="display:none;">
<table class="txt12555555line-height"  width="100%" border="0" align="center"
 cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
  <tr>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF"><input  type="text" name="queryConditions.code" value="${queryConditions.code}" /></td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
    <td align="left" bgcolor="#FFFFFF">
        <select name="queryConditions.status" style="width:150px;">
    		<option <c:if test = "${queryConditions.status eq 9 }">selected="selected"</c:if>  value="9">全部</option>
      		<c:forEach items="${statusLst}" var="status" varStatus="itemStatus">
      		<option value="${status.v}" <c:if test = "${queryConditions.status eq status.v }">selected="selected"</c:if> >${status.n}</option>         
		 	</c:forEach>
    	</select>
	</td>
  </tr>
  <tr>
  	<td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
    <td width="35%" align="left"  valign="top" bgcolor="#FFFFFF">
    	<input name="queryConditions.importFile" type="text" value="${queryConditions.importFile}" />
    </td>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
    <td width="35%" align="left" bgcolor="#FFFFFF">
    	<input name="queryConditions.content" value="${queryConditions.content}" type="text" size="30"/>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="queryConditions.region">
    		<option selected="selected" value="">全部</option>
    		<c:forEach items="${regionList}" var="item" varStatus="itemStatus">                  
	        <option value="${item.code}" ${queryConditions.region eq item.code ? 'selected="selected"':''}>${item.name}</option>
            </c:forEach>
    	</select>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科</td>
    <td align="left" bgcolor="#FFFFFF">
		<input name="queryConditions.subject_code" value="${subject_code}" type ="hidden" />
		<c:forEach items="${subjectList}" var="item" varStatus="itemStatus">             
			<c:if test="${subject_code==item.code}">${item.name}</c:if>
	  	</c:forEach>
	    <input type="hidden" name="queryConditions.subject" value="${subject}" />
  	</td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="queryConditions.grade" id=grade_q >
    		<option selected="selected" value="">全部</option>
			<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
			<c:if test="${subject_code == usrg.subject.code}">
				<c:if test="${queryConditions.grade eq ''}">
					<c:set var="_gradeCode" value="${grade_code}"/>
				</c:if>
				<c:if test="${queryConditions.grade ne ''}">
					<c:set var="_gradeCode" value="${queryConditions.grade}"/>
				</c:if>								
				<c:forEach items="${usrg.grades}" var="grade" >							
					<option value="${grade.code}" ${_gradeCode==grade.code?"selected":""}>${grade.levelFlag}${grade.name}/${grade.code}</option>
				</c:forEach>
			</c:if>
			</c:forEach>
    	</select>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
    <td align="left" bgcolor="#FFFFFF">
        <select name="queryConditions.itemType" style="width:150px;">
    		<option selected="selected" value="">全部</option>
    		<option value="feiXuanTi" ${queryConditions.itemType eq 'feiXuanTi' ? 'selected="selected"':''}>非选题</option>
			  <c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
						<c:if test="${subject_code == usrg.subject.code}">
							<c:forEach items="${usrg.grades}" var="grade" >							
										<c:forEach items="${itemTypeList}" var="itemType" varStatus="itemStatus">
											<c:if  test="${itemType.grade.code== grade.code}">
												<option value="${itemType.code}"  ${queryConditions.itemType eq itemType.code ? 'selected="selected"':''}>${itemType.code}(${itemType.name})</option>
											</c:if>
										 </c:forEach>
							</c:forEach>
						</c:if>
					</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    
         <select style="width:250px;" name="queryConditions.knowledgePoint" id="queryConditions.knowledgePoint">
    		<option selected="selected" value="">全部</option>
			
			<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
						<c:if test="${subject_code == usrg.subject.code}">
							<c:forEach items="${usrg.grades}" var="grade" >							
										<c:forEach items="${knowledgePointList}" var="kp" varStatus="itemStatus">
											<c:if  test="${kp.grade.code== grade.code && kp.state!=-1}">
												<option value="${kp.code}"  ${queryConditions.knowledgePoint eq kp.code ? 'selected="selected"':''}>${kp.levelFlag}${kp.code}/${kp.name}</option>
											</c:if>
										 </c:forEach>
							</c:forEach>
						</c:if>
					</c:forEach>
    		
    	</select>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题材：</td>
    <td align="left" bgcolor="#FFFFFF">
        <select name="queryConditions.itemTheme" style="width:150px;">
    		<option value="" selected="selected" >全部</option>			 
			 <c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
						<c:if test="${subject_code == usrg.subject.code}">
							<c:forEach items="${usrg.grades}" var="grade" >							
										<c:forEach items="${itemThemeList}" var="itemTheme" varStatus="itemStatus">
											<c:if  test="${itemTheme.grade.code== grade.code}">
												<option value="${itemTheme.code}" ${queryConditions.itemTheme eq itemTheme.code ? 'selected="selected"':''}>${itemTheme.levelFlag}${itemTheme.name}/${itemTheme.code}</option>
             								</c:if>
										 </c:forEach>
							</c:forEach>
						</c:if>
					</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<input name="queryConditions.yearDown" value="${queryConditions.yearDown}" type="text"  size="3" />&nbsp;—
        <input name="queryConditions.yearUp" value="${queryConditions.yearUp}" type="text"  size="3" />
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
    <td align="left" bgcolor="#FFFFFF">       
    	<c:forEach items="${itemSourceList}" var="item" varStatus="itemStatus">
    		<input name="queryConditions.source"  type="checkbox" ${fn:contains(queryConditions.source, item.value)?'checked="checked"':''} 
    			 value="${item.value}" />${item}&nbsp;&nbsp;
        </c:forEach>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答题时间：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
  		<input name="queryConditions.answeringTimeByMinDown" value="${queryConditions.answeringTimeByMinDown}" type="text"  size="3" />&nbsp;—
        <input name="queryConditions.answeringTimeByMinUp" value="${queryConditions.answeringTimeByMinUp}" type="text"  size="3" />
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">分值：</td>
    <td align="left" bgcolor="#FFFFFF">
  		<input name="queryConditions.scoreDown" value="${queryConditions.scoreDown}" type="text" size="3" />&nbsp;—
        <input name="queryConditions.scoreUp" value="${queryConditions.scoreUp}" type="text"  size="3" />
    </td>
  </tr> 
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
  		<input name="queryConditions.difficultyValueDown" value="${queryConditions.difficultyValueDown}" type="text"  size="3" />&nbsp;—
        <input name="queryConditions.difficultyValueUp" value="${queryConditions.difficultyValueUp}" type="text"  size="3" />
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
    <td align="left" bgcolor="#FFFFFF">
  		<input name="queryConditions.validityValueDown" value="${queryConditions.validityValueDown}" type="text" size="3" />&nbsp;—
        <input name="queryConditions.validityValueUp" value="${queryConditions.validityValueUp}" type="text"  size="3" />
    </td>
  </tr> 
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
  		<input name="queryConditions.originalPaperCode" value="${queryConditions.originalPaperCode}" type="text" />
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">来源文件：</td>
    <td align="left" bgcolor="#FFFFFF">
  		<input name="queryConditions.sourceFile" value="${queryConditions.sourceFile}" type="text" />
    </td>
  </tr>
  <tr>
  	<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	 <c:forEach items="${applicableObjectList}" var="applicableObject" varStatus="itemStatus">
			<input name="queryConditions.applicableObjects"  type="checkbox" 
				<c:forEach items="${queryConditions.applicableObjects}" var="qa" >
		    		${applicableObject.value eq qa?'checked="checked"':''}
				</c:forEach>
			 value="${applicableObject.value}" />${applicableObject}&nbsp;
		 </c:forEach>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用版本 ：</td>
    <td align="left" bgcolor="#FFFFFF">
	<input type="checkbox" name="queryConditions.courseVersions" <c:forEach items="${queryConditions.courseVersions}" var="courseVersion" >
      		<c:if test ="${courseVersion==0}">checked</c:if>       
	</c:forEach> value="0" />新旧版本适用&nbsp; 
	
	<input type="checkbox" name="queryConditions.courseVersions"  <c:forEach items="${queryConditions.courseVersions}" var="courseVersion" >
      		<c:if test ="${courseVersion==1}">checked</c:if>       
	</c:forEach>
	 value="1" />新版本适用&nbsp; 
	 
	 <input type="checkbox" name="queryConditions.courseVersions" <c:forEach items="${queryConditions.courseVersions}" var="courseVersion" >
      		<c:if test ="${courseVersion==2}">checked</c:if>       
	</c:forEach>
	  value="2" />旧版本适用	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">提示：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
  		<input name="queryConditions.hint" value="${queryConditions.hint}" type="text" />
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">详解1：</td>
    <td align="left" bgcolor="#FFFFFF"> 
  		<input name="queryConditions.analysisAtLarge1" value="${queryConditions.analysisAtLarge1}" type="text" />
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">典型例题：</td>
    <td align="left"  valign="top" bgcolor="#FFFFFF">
  		<input name="queryConditions.typicalExample"  type="checkbox" ${queryConditions.typicalExample eq 1?'checked="checked"':''} 
    		value="1" />典型例题&nbsp;&nbsp;
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
    <td align="left" bgcolor="#FFFFFF">
    	<input name="queryConditions.abilityValue"  type="checkbox" ${queryConditions.abilityValue eq 1?'checked="checked"':''}
    		value="1" />能力要求&nbsp;&nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">创建时间：</td>
    <td align="left"  valign="top" bgcolor="#FFFFFF">
  		<input name="queryConditions.createdTime" type="text" value="${queryConditions.createdTime}" />
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">正确答案：</td>
    <td align="left" bgcolor="#FFFFFF">
    	<input name="queryConditions.correctAnswer" type="text" value="${queryConditions.correctAnswer}" />
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">复习轮次 ：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="checkbox" name="queryConditions.reviewRound" <c:if test ="${queryConditions.reviewRound==1}">checked</c:if> value="1" />一轮&nbsp; 
		<input type="checkbox" name="queryConditions.reviewRound" <c:if test ="${queryConditions.reviewRound==2}">checked</c:if> value="2" />二轮&nbsp; 
	</td>
	<td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue"></td>
    <td align="left" bgcolor="#FFFFFF"></td>
  </tr>
  <tr bgcolor="#F7F7F7" height="50">
    <td align="center"  colspan="4">
    	<input type="button" value="   查 询   " class="btn_2k3" onClick="generalSubmit(1)"/>&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="button" value="   取 消   " onClick="hiddenDiv.style.display='none';" class="btn_2k3"/>
    </td>
  </tr> 
</table>
</div>
</td>
</tr>
<tr>
<td>
<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
	<td width="6%">全选<input type="checkbox"  onclick="checkAll('checkitem');"/></td>
    <td width="10%">编码</td>
    <td width="8%">题型</td>
    <td width="6%">年份</td>
    <td width="6%">来源</td>
    <td width="4%">难度</td>
    <td width="35%">题干（文章）</td>	
    <td width="4%">分值</td>
	<td width="7%">答题时间</td>
    <td width="8%">状态</td>
    <c:if test="${assemble eq 0}">
    <td width="12%">操作</td>
    </c:if>
  </tr>
  <c:forEach items="${page.result}" var="item" varStatus="itemStatus">                  
  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	<td width="3%"><input type="checkbox" name="checkitem" value="${item.id}"/></td>
    <td><a t=iframe _optIs=show _i=${itemStatus.index} href="item!show.jhtml?id=${item.id}">${item.code}</a></td>
    <td>${item.itemType.code}(${item.itemType.name})</td>
    <td>${item.year}</td>
    <td>${item.sourceName}</td>
    <td>${item.difficultyValue}</td>
    
    <td> ${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") }</td>
   
    <td>${item.score}</td>
	<td>${item.answeringTimeByMin}</td>
    <td>${item.statusName}</td>
    <c:if test="${assemble eq 0}">
    <td>
    	<c:if test="${item.subject.code eq 'E'}">
    		<a t=iframe _optIs=show _i=${itemStatus.index} href="item!show.jhtml?id=${item.id}">修改</a>
    	</c:if>
    	<c:if test="${item.subject.code ne 'E'}">
    		<a t=iframe _optIs=edit _i=${itemStatus.index} href="item!edit.jhtml?id=${item.id}">修改</a>
    	</c:if>
    	<a t=ajaxDel href="item!delete.jhtml?id=${item.id}" >删除</a>    	
    </td>
    </c:if>
  </tr>         
  </c:forEach>

</table>
</td></tr><tr><td>
<table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left">
      	  <c:if test="${assemble eq 0}">
	      	  <input type="button" value="批量审核" class="btn_2k3"  onclick="verifyBatch();" />
	          <input type="button" value="批量删除" class="btn_2k3"  onclick="batchdelete();" />
	          <input type="button" value="批量作废" class="btn_2k3"  onclick="invalidBatch();" />
	          <input type="button" value="批量入卷" class="btn_2k3"  onclick="assembling2()"  />
	          <input type="hidden" value="批量导出" class="btn_2k3"  onclick="location.href='exportItem.jhtml'" />
	          <input type="hidden" value="批量刷新" class="btn_2k3"  onclick="location.href='fixItem.jhtml'"  />
      	  </c:if>
      	  <c:if test="${assemble eq 1}">
      	  	  <input type="button" value="批量入卷" class="btn_2k3" onClick="assembling()"  />
      	  </c:if>
        </td>
        <td align="right">
        <ambow:pagination actionName=""
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}" 
        	                  num="${page.currentPageNo}"
        	                  otherParams="" />
        </td>
      </tr>
 </table>
 </td></tr></table>
 <input type="hidden" value="${queryType}"  name="queryType" id="queryType"/>
 <input type="hidden" value="${paperId}"   name="paperId"   id="paperId"/>
 <input type="hidden" value="${assemble}"  name="assemble"  id="assemble"/>			
</form>
</body>
</html>
