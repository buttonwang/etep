<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML XMLNS:m="http://www.w3.org/1998/Math/MathML">
<HEAD><OBJECT
      ID=MathPlayer
      CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"
   ></OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common_new.js"></script>
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="../js/math_oneToMany.js"></script>
<script type="text/javascript" src="../js/mpc3x4xResetId.js"></script>
<script type="text/javascript" src="../js/computeScoreByScore2.js"></script>
<script>
$(function(){
	$("input[_t=cancle]").click(function(e){
		try{window.parent.changeShow();}catch(e){}
	})
})

function getKP() {
	var url = "./knowledgePoint!list.jhtml?queryType=sw&subject_code=${item.subject.code}&code="
	     + document.getElementById('knowledgePointCodes').value;
    var options = "height=600, width=500, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, "
         + "location=no, status=yes,top=100,left=300";
	window.open(url, 'newWindow', options);
}
</script>
</head><body>
<form  id="pageForm" method="post" action="item!save.jhtml" onSubmit="return validateItemForm()"  _t=jform>
	<input type="hidden" value="oneToMany_mAnswer" name="p.para.type">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：题库 &gt; 试题管理 &gt;${title}</td>
		</tr>
	</table>
	<table id="table2" class="txt12555555line-height"  width="100%" border="0" align="center" 
    cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${subjectList}" var="sub" varStatus="itemStatus">
					<c:if test="${item.subject.code==sub.code}">${sub.name}</c:if>
				</c:forEach>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${statusLst}" var="status"  >
							<c:if test="${item.status==status.v}"><font color="#ff0000">${status.n}</font></c:if>
						</c:forEach>
			</td>
		</tr>
		<tr>
			<!--所属学科  -->
			<input type="hidden" name="subject_code" value="${subject_code}"/>
			<input type="hidden" name="item.subject.code" value="${subject_code}"/>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="text" name="itemVO.code" size="30" value="${item.code}" _readonly="readonly"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题题型：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><select name="itemVO.itemTypeCode">
					<c:forEach items="${itemTypeList}" var="i" varStatus="itemStatus">
						<option value="${i.code}" ${item.itemType.code eq i.code ? 'selected="selected"':''}>(${i.code})${i.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">登记时间：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${item.createdTime}</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${applicableObjectList}" var="applicableObject" varStatus="itemStatus">
					<input name="itemVO.applicableObject"  type="radio" ${applicableObject.value eq item.applicableObject?'checked="checked"':''} 
		    			 value="${applicableObject.value}" />
					${applicableObject}&nbsp; </c:forEach>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="itemVO.regionCode">
					<c:forEach items="${regionList}" var="i" varStatus="itemStatus">
						<option value="${i.code}" ${item.region.code eq i.code ? 'selected="selected"':''}>${i.name}</option>
					</c:forEach>
				</select>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<input type="text" size="30"  id="knowledgePointNames" name="itemVO.knowledgePointNames" value="${itemVO.knowledgePointNames}"  readonly="true"/>
				<input type="text" id="knowledgePointCodes" name="itemVO.knowledgePointCodes" value="${itemVO.knowledgePointCodes}"  style="display:none"/>
				&nbsp;&nbsp;
				<input type="button" value="选择" class="btn_2k3" onClick="getKP()"/>	
				</td>
		</tr>
		<tr>
			<td align="right"  bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
			<td align="left" bgcolor="#FFFFFF"><a href="/resource/mpc/${itemVO.importFile}">${itemVO.importFile} </a></td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含次知识点：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<input type="text" size="30"  id="knowledgePointNames2" name="itemVO.knowledgePointNames2" value="${itemVO.knowledgePointNames2}"  readonly="true"/>
				<input type="text" id="knowledgePointCodes2" name="itemVO.knowledgePointCodes2" value="${itemVO.knowledgePointCodes2}"  style="display:none"/>
				&nbsp;&nbsp;
				<input type="button" value="选择" class="btn_2k3" onClick="getList('knowledgePoint','knowledgePointNames2','knowledgePointCodes2',event);"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.year"  value="${item.year}"/>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.originalItemNum"  value="${item.originalItemNum}"/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><c:forEach items="${itemSourceList}" var="source" varStatus="status">
					
						<input name="itemVO.source"  type="radio" ${source.value eq item.source?'checked="checked"':''} 
			    			 value="${source.value}" />
						${source}&nbsp; <c:if test="${source.value<=4}"></c:if>
				</c:forEach>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.sourceBook" value="${item.sourceBook}"/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" name="itemVO.sourceFile"  value="${item.sourceFile}"/>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.originalPaperCode"  value="${item.originalPaperCode}"/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input  type="text" name="itemVO.validityValue"  size="10" value="${item.validityValue}"/>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" name="itemVO.difficultyValue"  size="10" value="${item.difficultyValue}"/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.opinion" value="${item.opinion}"/>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.itemValue" value="${item.itemValue}"/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.abilityValue" value="${item.abilityValue}"/>			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.score" size="10" value="${item.score}" _t="cpTotal"/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.answeringTimeByMin" size="10" value="${item.answeringTimeByMin}"/>
				&nbsp;分钟 </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">各答案分值：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.score2" size="10" value="${item.score2}" _t="cpTotalBase"/>			</td>
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
					value="0" />
					不限&nbsp; 
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
			<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  id="itemVO.content" name="itemVO.content" cols="90" rows="8">${item.content}</textarea>			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
			<c:set var = "subItem_id" value="0"/>
			<c:set var = "toServerName" value="correctAnswer"/>
			<Table>				
				<Tr>
					<td width="100%" colspan="2">							
						<input type="button" value="增加公式型答案" onClick="addRow('${subItem_id}',1,'${toServerName}')"  class="btn_2k3" />
									&nbsp;&nbsp;
						<input type="button" value="增加纯文本型答案" onClick="addRow('${subItem_id}',0,'${toServerName}')"  class="btn_2k3" />
									&nbsp;&nbsp; 
						</td>
				</Tr>
			</Table>
			<Table id='subItemAnswerTable_${subItem_id}' width="100%">
				<c:set var="i" value="0"/>
				<c:forEach items="${item.answers}" var="correctAnswer" varStatus="status">
				<c:set var="i" value="${i+1}"/>
					<c:if test="${correctAnswer.type eq 'gongShi'}">
						<Tr>
							<td width="5%" > ${i} </td>
							<td width="50%"><textarea id="textarea_${i}_${subItem_id}" name="${toServerName}" style="display:none"></textarea>
							<script>$(function(){$("#textarea_${i}_${subItem_id}").val("${correctAnswer.value}");})</script>
								<span style="border-bottom:1px solid #000000;" > <span id="span_${i}_${subItem_id}">&nbsp;&nbsp;${correctAnswer.value}&nbsp;&nbsp; </span></span> </td>
							<td width="20%" bgcolor="#F7F7F7" align="left"><a style="cursor:pointer" onClick="getFormulator('${subItem_id}','${i}');">公式修改</a>&nbsp;&nbsp; 
							<a style="cursor:pointer" onClick="deleteLine(this.parentNode.parentNode);LoadMath_resetId('${subItem_id}')">删除</a> </Td>
							<td width="25%" bgcolor="#F7F7F7" align="left">
						<input type="button" value="前插公式" onClick="addRow('${subItem_id}',1,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
						&nbsp;&nbsp;
						<input type="button" value="前插文本" onClick="addRow('${subItem_id}',0,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
						&nbsp;&nbsp;
							</Td>
						</Tr>
					</c:if>
					<c:if test="${correctAnswer.type eq 'text'}">
						<Tr >
							<td width="5%"> ${status.index+1} </td>
							<td width="50%"><textarea type="text" size="50" name="${toServerName}">${correctAnswer.value}</textarea>
							</td>
							<td width="20%" bgcolor="#F7F7F7" align="left"><a style="cursor:pointer" onClick="deleteLine(this.parentNode.parentNode);LoadMath_resetId('${subItem_id}');">删除</a> </Td>
							<td width="25%" bgcolor="#F7F7F7" align="left">
							<input type="button" value="前插公式" onClick="addRow('${subItem_id}',1,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
						&nbsp;&nbsp;
						<input type="button" value="前插文本" onClick="addRow('${subItem_id}',0,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
						&nbsp;&nbsp;
							</Td>
						</Tr>
					</c:if>
				</c:forEach>
			</Table></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">答案分组：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3"><input name="itemVO.answerGroup" cols="90" rows="4" value="${item.answerGroup}" />			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">答案原型：</td>
			<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  name="itemVO.answerPrototype" cols="90" rows="4" _id="answerPrototype">${item.answerPrototype}</textarea>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">提示：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.hint" cols="90" rows="8">${item.hint}</textarea>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题与技巧：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><input class="logininputmanage" name="itemVO.skills" value="${item.skills}" size=90/>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解1：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.analysisAtLarge1" cols="90" rows="8">${item.analysisAtLarge1}</textarea>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解2：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.analysisAtLarge2" cols="90" rows="2">${item.analysisAtLarge2}</textarea>			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解3：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3"><textarea fck="fck"  class="logininputmanage" name="itemVO.analysisAtLarge3" cols="90" rows="2">${item.analysisAtLarge3}</textarea>			</td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
					<tr>
						<td align="center"><input type="submit" value="  保 存  " class="btn_2k3" />
							<input  type="text" name="itemVO.id"  value="${item.id}" style="display:none"/>						</td>
					</tr>
				</table></td>
		</tr>
	</table>
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
