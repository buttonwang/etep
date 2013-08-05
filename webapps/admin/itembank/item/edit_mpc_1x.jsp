<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common_new.js"></script>
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="../js/show_mpc_4x.jsp.js"></script>
<script>
$(function(){
	$("input[_t=cancle]").click(function(e){
		try{window.parent.changeShow();}catch(e){}
	})
	$("#addAnswer_b").click(function(e){
		var jtr = $(['<tr>'
		,	'<td width="20%" align="center" class="txt12blue">'
		,		'<input  type="text" name="p.para.answerOptionCodes" value="" size="5"/></td>'
		,	'<td width="60%" align="left"><textarea fck="fck"  name="p.para.answerOptionContents" rows="3"></textarea></td>'
		,	'<td width="20%" align="left"><input class="btn_2k3" type="button" value="删除" onClick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)"></td>'
		,'</tr>'].join(""));
		$("#answers").append(jtr);
		makeFCKeditorByJobj(jtr.find("textarea[fck=fck]"));		
	});
})
</script>

</head>
<body>
<form  id="pageForm" method="post" action="item!save.jhtml" onSubmit="return validateItemForm()" _t=jform>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：题库 &gt; 试题管理 &gt; ${title}</td>
		</tr>
	</table>
	<table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ><input type="hidden" name="subject_code" value="${item.subject.code}"/>
				<input type="text" name="itemVO.code" size="30" value="${item.code}" _READONLY="readonly"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><c:forEach items="${statusLst}" var="status"  >
							<c:if test="${item.status==status.v}"><font color="#ff0000">${status.n}</font></c:if>
						</c:forEach>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="itemVO.regionCode" style="width: 100px">
					<c:forEach items="${regionList}" var="i" varStatus="itemStatus">
						<option value="${i.code}" ${item.region.code eq i.code ? 'selected="selected"':''}>${i.name}</option>
					</c:forEach>
				</select>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${subjectList}" var="sub" varStatus="itemStatus">
					<c:if test="${item.subject.code==sub.code}">${sub.name}</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><c:forEach items="${itemSourceList}" var="source" varStatus="sourceStatus">
					
						<input name="itemVO.source"  type="radio" ${source.value eq item.source?'checked="checked"':''} 
			    			 value="${source.value}" />
						${source}&nbsp; <c:if test="${source.value<=4}"></c:if>
				</c:forEach>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${applicableObjectList}" var="applicableObject" varStatus="itemStatus">
					<input name="itemVO.applicableObject"  type="radio" ${fn:contains(item.applicableObject, applicableObject.value)?'checked="checked"':''} 
    			 value="${applicableObject.value}" />
					${applicableObject}&nbsp; </c:forEach>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.validityValue" value="${item.validityValue}"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			    <input  type="text" size="30"  id="knowledgePointNames" name="itemVO.knowledgePointNames" value="${itemVO.knowledgePointNames}"  readonly="readonly"/>
				<input  type="text" id="knowledgePointCodes" name="itemVO.knowledgePointCodes" value="${itemVO.knowledgePointCodes}"  style="display:none"/>
				&nbsp;&nbsp;
				<input type="button" value="选择" class="btn_2k3"  onClick="javascript: window.open('./knowledgePoint!list.jhtml?queryType=sw&subject_code=${item.subject.code}&code='+document.getElementById('knowledgePointCodes').value,'newWindow', 'height=600, width=500, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=300')"/>
			</td>
		</tr>
		<tr>
			<td align="right"  bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
			<td align="left" bgcolor="#FFFFFF"><a href="/resource/mpc/${itemVO.importFile}">${itemVO.importFile} </a></td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含次知识点：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text"   size="30"  id="knowledgePointNames2" name="itemVO.knowledgePointNames2" value="${itemVO.knowledgePointNames2}"  readonly="readonly"/>
				<input  type="text" id="knowledgePointCodes2" name="itemVO.knowledgePointCodes2" value="${itemVO.knowledgePointCodes2}"  style="display:none"/>
				&nbsp;&nbsp;
				<input type="button" value="选择" class="btn_2k3"  onClick="getList('knowledgePoint','knowledgePointNames2','knowledgePointCodes2',event);"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF">
				<select name="itemVO.itemTypeCode">
					<c:forEach items="${itemTypeList}" var="i" varStatus="itemStatus">
						<option value="${i.code}" ${item.itemType.code eq i.code ? 'selected="selected"':''}>(${i.code})${i.name}</option>
					</c:forEach>
				</select>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.year"  value="${item.year}"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" name="itemVO.sourceFile"  value="${item.sourceFile}"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.sourceBook" value="${item.sourceBook}"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" name="itemVO.originalItemNum"  value="${item.originalItemNum}"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.score"  size="10" value="${item.score}" _t="cpTotal"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难易度：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" name="itemVO.difficultyValue"  size="10" value="${item.difficultyValue}"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">各答案分值：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.score2"  size="10" value="${item.score2}" _t="cpTotalBase"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度 ：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="itemVO.itemValue" value="${item.itemValue}" />
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="text" name="itemVO.answeringTimeByMin"  size="10" value="${item.answeringTimeByMin}"/>
				&nbsp;分钟 </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.abilityValue" value="${item.abilityValue}"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="itemVO.opinion" value="${item.opinion}" />
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" id="correctAnswer_id">
			<c:forEach items="${commonOptionsList}" var="commonOption" varStatus="itemStatus">
					<input _id=correctAnswer name="itemVO.correctAnswer"  type="checkbox" ${fn:contains(item.correctAnswer, commonOption)?'checked="checked"':''} 
    			 value="${commonOption}" />
					${commonOption}&nbsp; </c:forEach>
			<br>
			(注：如果是单选，当选择两个以上时系统只记录第一个)</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.originalPaperCode"  value="${item.originalPaperCode}"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题版本：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ><jsp:include page="item_courseVersion.jsp?t=edit"/></td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">复习轮次：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<input type="checkbox" name="reviewRound"  
					<c:forEach var="selected" items="${selectedReviewRound}" >
						<c:if test="${selected==0 }">checked</c:if>
					</c:forEach> 
					value="0" />不限&nbsp; 
				<input type="checkbox" name="reviewRound" 
				 <c:forEach var="selected" items="${selectedReviewRound}" >
					<c:if test="${selected==1 }">checked</c:if>
				</c:forEach>
				  value="1" />一轮&nbsp;
				<input type="checkbox" name="reviewRound" 
				<c:forEach var="selected" items="${selectedReviewRound}" >
					<c:if test="${selected==2 }">checked</c:if>
				</c:forEach>
				 value="2" />二轮
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  name="itemVO.content" cols="90" rows="8">${item.content}</textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答案选项：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3">
			
			<table id="answers" class="txt12555555line-height" height="100%"  width="100%" border="0" align="left"
            cellpadding="6" cellspacing="0" bgcolor="#FFFFFF">
						<tr>
							<td width="20%" align="left" colspan="3" class="txt12blue"><input type="button" value="增加选项" id="addAnswer_b" class="btn_2k3" ></td>
							 
						</tr>
					<c:forEach items="${item.answerOptions}" var="i" varStatus="itemStatus">
						<tr>
							<td width="20%" align="center" class="txt12blue"><input  type="text" name="itemVO.answerOptionIds" value="${i.id}" size="5" style="display:none"/>
								<input  type="text" name="itemVO.answerOptionCodes" value="${i.code}" size="5"/></td>
							<td width="60%" align="left"><textarea fck="fck"  name="itemVO.answerOptionContents" rows="3">${i.content}</textarea></td>
							<td width="20%" align="left"><input class="btn_2k3" type="button" value="删除" onClick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)"></td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">提示：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.hint" cols="90" rows="8">${item.hint}</textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题与技巧：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan=3><input class="logininputmanage" name="itemVO.skills" value="${item.skills}" size=90/>
			</td>
		</tr>
		
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解1：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.analysisAtLarge1" cols="90" rows="8">${item.analysisAtLarge1}</textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解2：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.analysisAtLarge2"  cols="90" rows="2">${item.analysisAtLarge2}</textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解3：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.analysisAtLarge3"  cols="90" rows="2">${item.analysisAtLarge3}</textarea>
			</td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
					<tr>
						<td align="center"><input type="submit" value="  保 存  " class="btn_2k3" /> 
						</td>
					</tr>
				</table></td>
		</tr>
	</table>
	<input  type="text" name="itemVO.id"  value="${item.id}" style="display:none"/>
</form>
	<table width="100%" height="80px" p=editPage>
		<tr>
			<td align="center" style="display: none">
				<c:if test="${item.typicalExample eq null}">
					<input type="button" value="设为典型例题" class="btn_2k3" onClick="if (confirm('确定要设为典型例题吗？')) location.href='item!addTypicalExample.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
				</c:if>
				<c:if test="${item.typicalExample ne null}">
					<input type="button" value="取消典型例题" class="btn_2k3" onClick="if (confirm('确定要取消典型例题吗？')) location.href='item!delTypicalExample.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
				</c:if>	
			</td>
		</tr>
		<tr>
			<td align="center">
			<input type="button" value="  审 核  " class="btn_2k3"  onClick="if (confirm('确定要审核吗？')) location.href='item!verifyInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  作 废  " class="btn_2k3"  onClick="if (confirm('确定要作废吗？')) location.href='item!invalidInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  启 用  " class="btn_2k3"  onClick="if (confirm('确定要启用吗？')) location.href='item!activationInShowPage.jhtml?id=${item.id}'"/>&nbsp;&nbsp;
			<input type="button" value="  删 除  " class="btn_2k3"  onClick="if (confirm('确定要删除吗？')) location.href='item!delete.jhtml?id=${item.id}'"/>&nbsp;&nbsp;
			<input type="button" value="  入 卷  " class="btn_2k3"  onClick="location.href='paper!choose.jhtml?itemids=${item.id}';"/>&nbsp;&nbsp;
			</td>
		</tr>
	</table>
<jsp:include page="getlist.jsp"></jsp:include>
</body>
</html>
