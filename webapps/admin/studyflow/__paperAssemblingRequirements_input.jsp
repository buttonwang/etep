<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="../js/m.js"></script>
<script src="../js/My2Select.js"></script>
<script src="../js/ItemType_KnowledgePoint_codesNames.js"></script>

<script>
$(function(){
	try{
		eval('var subject_grade = ${m_userSubjectGradeRole_json}');		 
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","${_kp.subject.code}","${_kp.grade.code}");
	}catch(e){
	}
})
</script>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
	<tr>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<select name="${_objName}.regionCode">
				<option value="">请选择</option>
				<c:forEach items="${regionList}" var="item" varStatus="itemStatus">
				<option value="${item.code}" ${_obj.regionCode eq item.code ? 'selected="selected"':''}>${item.name}</option>
				</c:forEach>
			</select>
		</td>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
		<td align="left" bgcolor="#FFFFFF">
			<select name="${_objName}.subjectCode" vt="subjectCode" id="_sc"></select>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<select name="${_objName}.gradeCode" vt="gradeCode" id="_gc"></select>
		</td>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
		<td align="left" bgcolor="#FFFFFF"> 
		<input type="text"   id="itemTypeNames"  value="${_obj.itemTypeName}" readonly="readonly" size="30"/>
		<input type="hidden" id="itemTypeCode" name="${_objName}.itemTypeCode"  value="${_obj.itemTypeCode}" />&nbsp;&nbsp;		 		
		<input type="button" value="选择" class="btn_2k3" onClick="setItemTypeCodesNames('itemTypeCode','itemTypeNames',$('#_sc').val(),$('#_gc').val());"/>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<span n="${_objName}.year">
			<input v=1 value="" type="text"  size="3" />
			&nbsp;—
			<input v=2 value="" type="text"  size="3" />
			</span>
			<input type="hidden" name="${_objName}.year" value="${_obj.year}">
		</td>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
		<td align="left" bgcolor="#FFFFFF">
			<c:forEach items="${itemSourceList}" var="item" varStatus="itemStatus">
				<input name="${_objName}.source"  type="checkbox" ${fn:contains(_obj.source, item.value)?'checked="checked"':''} value="${item.value}" />
   			 	${item}&nbsp; 
			</c:forEach>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<span n="${_objName}.difficultyValue">
			<input v=1 value="" type="text"  size="3" />
			&nbsp;—
			<input n="${_objName}.difficultyValue" v=2 value="" type="text"  size="3" />
			</span>
			<input type="hidden" name="${_objName}.difficultyValue" value="${_obj.difficultyValue}"  size="3" />
		</td>
		<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
		<td align="left" bgcolor="#FFFFFF">
			<span n="${_objName}.validityValue">
			<input v=1 value="" type="text" size="3" />
			&nbsp;—
			<input v=2 value="" type="text" size="3" />
			</span>
			<input type="hidden" name="${_objName}.validityValue" value="${_obj.validityValue}" size="3" />
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">题数： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
			<input class="logininputmanage" type="text" name="${_objName}.amount" value="${_obj.amount}"/>
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
		<td width="33%" align="left" bgcolor="#FFFFFF">
			<input class="logininputmanage" type="text" name="${_objName}.originalPaperCode" value="${_obj.originalPaperCode}"/>
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">直观评价： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >
			<span n="${_objName}.opinion">
			<input v=1 value="" type="text" size="3" />
			&nbsp;—
			<input v=2 value="" type="text" size="3" />
			</span>
			<input type="hidden" name="${_objName}.opinion" value="${_obj.opinion}" size="3" />
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
		<td width="33%" align="left" bgcolor="#FFFFFF">
			<input name="${_objName}.applicableObject" type="checkbox" value="0"  ${fn:contains(_obj.applicableObject, '0')?"checked":""}/>文理科&nbsp;&nbsp;
			<input name="${_objName}.applicableObject" type="checkbox" value="1"  ${fn:contains(_obj.applicableObject, '1')?"checked":""}/>文科&nbsp;&nbsp;
			<input name="${_objName}.applicableObject" type="checkbox" value="2"  ${fn:contains(_obj.applicableObject, '2')?"checked":""}/>理科&nbsp;&nbsp;	
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">主知识点： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >
		<input type="text"   size="30"  id="knowledgePointNames"
		 value="<c:if test="${_obj.knowledgePointCode==null}">${_kp.name}</c:if><c:if test="${_obj.knowledgePointCode!=null}">${_obj.knowledgePointName}</c:if>"  readonly="readonly"/>
		<input type="hidden" id="knowledgePointCodes" name="${_objName}.knowledgePointCode" 
		 value="<c:if test="${_obj.knowledgePointCode==null}">${_kp.code}</c:if><c:if test="${_obj.knowledgePointCode!=null}">${_obj.knowledgePointCode}</c:if>"  />&nbsp;&nbsp;		 		
		<input type="button" value="选择" class="btn_2k3"  
		 onClick="setKnowledgePointCodesNames2(document.getElementById('knowledgePointCodes').value,'knowledgePointCodes','knowledgePointNames',$('#_sc').val(),$('#_gc').val());"/>
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用版本： </td>
		<td width="33%" colspan="3" align="left" valign="top" bgcolor="#FFFFFF" >
			<jsp:include page="../studyflow/__paperAssemblingRequirements_input_courseVersion.jsp?t=edit"/>
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">复习轮次： </td>
		<td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
			<input type="checkbox" name="reviewRound"  
				<c:forEach var="selected" items="${selectedReviewRound}" >
					<c:if test="${selected==0}">checked</c:if>
				</c:forEach> 
				value="0" />
				不限&nbsp; 
			<input type="checkbox" name="reviewRound" 
				 <c:forEach var="selected" items="${selectedReviewRound}" >
					<c:if test="${selected==1}">checked</c:if>
				</c:forEach>
			  value="1" />一轮&nbsp;
			<input type="checkbox" name="reviewRound" 
				<c:forEach var="selected" items="${selectedReviewRound}" >
					<c:if test="${selected==2}">checked</c:if>
				</c:forEach>
			 value="2" />二轮
		</td>
	</tr>
	<tr>
		<td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
			<table border="0" width="100%">
			<tr>
				<td align="center">
				<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="  取 消  " class="btn_2k3" ${_cancleButtonEvent} />
				</td>
			</tr>
			</table>
		</td>
	</tr>
</table>