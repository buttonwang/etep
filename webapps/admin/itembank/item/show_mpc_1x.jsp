<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script>
	function saveAOption(answerid) {
		var actionurl = 'answerOption!save.jhtml?id='+answerid+'&itemId=${item.id}';
		
		var obj=document.getElementById('pageForm');
		obj.action = actionurl;
		obj.submit();  		
	}
	
	function deleteAOption(answerid) {
		if (!confirm('确定要删除吗？')) return false;
		
		var actionurl = 'answerOption!delete.jhtml?id='+answerid+'&itemId=${item.id}';
		
  		var obj=document.getElementById('pageForm');
		obj.action = actionurl;
		obj.submit();  		
	}
</script>
</head>
<body>
<form method="post" action="" name="pageForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：题库 &gt; 试题管理 &gt; ${title}</td>
		</tr>
	</table>
	<table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
		<tr>
			<td width="50%" align="left"  class="txt12blueh">试题信息</td>
			<td width="50%" align="right" class="txt12blue" ></td>
		</tr>
	</table>
	<table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" > ${item.code} </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF">
				<c:forEach items="${statusLst}" var="status"  >
					<c:if test="${item.status==status.v}"><font color="#ff0000">${status.n}</font></c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<c:forEach items="${applicableObjectList}" var="app" varStatus="itemStatus">
					<c:if test="${item.applicableObject==app.value}">${app}</c:if>
				</c:forEach>
			</td>
			<td align="right"  bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
			<td align="left" bgcolor="#FFFFFF"><a href="/resource/mpc/${itemVO.importFile}">${itemVO.importFile} </a></td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<c:forEach items="${regionList}" var="i" varStatus="itemStatus">
					<c:if test="${item.region.code == i.code}"> ${i.name} </c:if>
				</c:forEach>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<c:forEach items="${subjectList}" var="i" varStatus="itemStatus">
					<c:if test="${item.subject.code == i.code}"> ${i.name} </c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.validityValue} </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${itemVO.knowledgePointNames} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题适用版本：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><jsp:include page="item_courseVersion.jsp"/></td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含次知识点：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${itemVO.knowledgePointNames2} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF">
				<c:forEach items="${itemTypeList}" var="type" varStatus="itemStatus">
					<c:if test="${item.itemType.code==type.code}"> (${type.code}) ${type.name} </c:if>
				</c:forEach>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.year} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF">${item.sourceName}</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.sourceBook} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.sourceFile} </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.originalPaperCode} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.originalItemNum} </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.score} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难易度：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.difficultyValue} </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.answeringTimeByMin}&nbsp;分钟</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度 ：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" > ${item.itemValue} </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" > ${item.opinion} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">复习轮次：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${item.reviewRound}轮	</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue"></td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">创建人：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >${item.creater}</td>
			<td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
			<td width="33%"align="left"   bgcolor="#FFFFFF">${item.createdTime}</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新人：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${item.updater}</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新时间：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${item.updatedTime}</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核人：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${item.verifier}</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核时间：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${item.verifiedTime}</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3"> ${item.content} </td>
		</tr>		
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答案选项：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><table id="table4" class="txt12555555line-height" height="100%"  width="100%" border="0" align="left"
            cellpadding="6" cellspacing="0" bgcolor="#FFFFFF">
					<c:forEach items="${item.answerOptions}" var="i" varStatus="itemStatus">
						<tr>
							<td width="20%" align="center" class="txt12blue"> ${i.code}</td>
							<td width="80%" align="left"> ${ i.content }</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ><c:forEach items="${commonOptionsList}" var="commonOption" varStatus="itemStatus">
					<c:if test="${fn:contains(item.correctAnswer, commonOption)}"> ${commonOption} </c:if>
				</c:forEach>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.abilityValue} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">提示：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"> ${item.hint} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">方法与技巧：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan=3> ${item.skills} </td>
		</tr>		
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解1：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"> ${item.analysisAtLarge1} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解2：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"> ${item.analysisAtLarge2} </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解3：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"> ${item.analysisAtLarge3} </td>
		</tr>
	</table>
	<table width="100%" height="80px" p=editPage>
		<tr style="display: none">
			<td align="center">
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
			<input type="button" value="  修 改  " class="btn_2k3"  onClick="location.href='item!edit.jhtml?id=${item.id}&subject_code=${subject_code}';"/>&nbsp;&nbsp;
			<input type="button" value="  审 核  " class="btn_2k3"  onClick="if (confirm('确定要审核吗？')) location.href='item!verifyInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  作 废  " class="btn_2k3"  onClick="if (confirm('确定要作废吗？')) location.href='item!invalidInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  启 用  " class="btn_2k3"  onClick="if (confirm('确定要启用吗？')) location.href='item!activationInShowPage.jhtml?id=${item.id}'"/>&nbsp;&nbsp;
			<input type="button" value="  删 除  " class="btn_2k3"  onClick="if (confirm('确定要删除吗？')) location.href='item!delete.jhtml?id=${item.id}'"/>&nbsp;&nbsp;
			<input type="button" value="  入 卷  " class="btn_2k3"  onClick="location.href='paper!choose.jhtml?itemids=${item.id}';"/>&nbsp;&nbsp;			
			</td>
		</tr>		
	</table>
</form>
</body>
</html>
