<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/My2Select.js"></script>
<script src="../js/policy_common.js"></script>
<script>
$(function(){
	try{
		eval('var subject_grade = ${m_userSubjectGradeRole_json}');		 
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","${assemblingPaperReqTemplate.subjectCode}","${assemblingPaperReqTemplate.gradeCode}");
	}catch(e){
	}
})
</script><%----%>
<script>
j(function(){
	j("span[n!=]").each(function(){
			var jdiv = j(this);
			var iname = j(this).attr("n");
			var jselects = jdiv.find("input");
			var size = jselects.size();
			var varry = (j("input[name="+iname+"]").val()||"").split("-");
			jselects.each(function(vi){
				j(this).val(varry[vi]||"")
			})
			jselects.blur(function(){
				var html ="";
				if(size<=1){
					html =jselects.val();
				}else{
					jselects.each(function(i){
						html += j(this).val();					
						if(i>=0&&i<size-1){
							html += "-";
						}
					})
					j("input[name="+iname+"]").val(html)
				}
			})
		})
})
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 组卷条件模板</td>
	</tr>
</table><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td>
<form action="assemblingPaperReqTemplate.jhtml" method="post">
	<input name="atype" value="update"  type="hidden" />
	<input name="assemblingPaperReqTemplate.id" value="${assemblingPaperReqTemplate.id}"  type="hidden" />
	<input name="assemblingPaperReqTemplate.paperAssemblingReqTemplate.id" value="${assemblingPaperReqTemplate.paperAssemblingReqTemplate.id}" type="hidden" />	
<table class="txt12555555line-height"  width="100%" border="0" align="center"
 cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
   
    <tr>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
      <td align="left" valign="top" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.regionCode">
          <option selected="selected" value="">全部</option>
          <c:forEach items="${regionList}" var="item" varStatus="itemStatus">
            <option value="${item.code}" ${assemblingPaperReqTemplate.regionCode eq item.code ? 'selected="selected"':''}>${item.name}</option>
          </c:forEach>
        </select>
      </td>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
      <td align="left" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.subjectCode" vt=subjectCode> 
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
      <td align="left" valign="top" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.gradeCode" vt=gradeCode>
        </select>
      </td>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
      <td align="left" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.itemTypeCode">
          <option selected="selected" value="">全部</option>
          <c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus">
            <option value="${item.code}" ${assemblingPaperReqTemplate.itemTypeCode==item.code?'selected':''} >${item.code}(${item.name})</option>
          </c:forEach>
        </select>
      </td>
    </tr>
   
    <tr>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
      <td align="left" valign="top" bgcolor="#FFFFFF"><span n="assemblingPaperReqTemplate.year">
        <input v=1 value="" type="text"  size="3" />
        &nbsp;—
        <input v=2 value="" type="text"  size="3" />
        </span>
        <input type="hidden" name="assemblingPaperReqTemplate.year" value="${assemblingPaperReqTemplate.year}">
      </td>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
      <td align="left" bgcolor="#FFFFFF"><c:forEach items="${itemSourceList}" var="item" varStatus="itemStatus">
          <input name="assemblingPaperReqTemplate.source"  type="checkbox" ${fn:contains(assemblingPaperReqTemplate.source, item.value)?'checked="checked"':''} 
    			 value="${item.value}" />
          ${item}&nbsp; </c:forEach>
      </td>
    </tr>
    <tr>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
      <td align="left" valign="top" bgcolor="#FFFFFF"><span n="assemblingPaperReqTemplate.difficultyValue">
        <input v=1 value="" type="text"  size="3" />
        &nbsp;—
        <input n="assemblingPaperReqTemplate.difficultyValue" v=2 value="" type="text"  size="3" />
        </span>
        <input type="hidden" name="assemblingPaperReqTemplate.difficultyValue" value="${assemblingPaperReqTemplate.difficultyValue}"  size="3" />
      </td>
      <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
      <td align="left" bgcolor="#FFFFFF"><span n="assemblingPaperReqTemplate.validityValue">
        <input v=1 value="" type="text" size="3" />
        &nbsp;—
        <input v=2 value="" type="text" size="3" />
        </span>
        <input type="hidden" name="assemblingPaperReqTemplate.validityValue" value="${assemblingPaperReqTemplate.validityValue}" size="3" />
      </td>
    </tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.amount" value="${assemblingPaperReqTemplate.amount}"/>
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
		<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.originalPaperCode" value="${assemblingPaperReqTemplate.originalPaperCode}"/>
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">直观评价： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.opinion" value="${assemblingPaperReqTemplate.opinion}"/>
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
		<td width="33%" align="left" bgcolor="#FFFFFF"><select class="logininputmanage"  name="assemblingPaperReqTemplate.applicableObject">
					<option value='0' ${assemblingPaperReqTemplate.applicableObject==0?'selected':''}>文理科</option>
					<option value='1' ${assemblingPaperReqTemplate.applicableObject==1?'selected':''}>文科</option>
					<option value='2' ${assemblingPaperReqTemplate.applicableObject==2?'selected':''}>理科</option>
				</select>
		</td>
	</tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">主知识点： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >
		<input  type="text"   size="30"  id="knowledgePointNames" value="${KnowledgePointNames}"  readonly="readonly"/>
		<input  type="hidden" id="knowledgePointCodes" name="assemblingPaperReqTemplate.knowledgePointCode" value="${assemblingPaperReqTemplate.knowledgePointCode}" />&nbsp;&nbsp;
		<input type="button" value="选择" class="btn_2k3"  onClick="getList('knowledgePoint','knowledgePointNames','knowledgePointCodes');"/>		
		</td>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用版本： </td>
		<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >
			<c:set var="_objName" value='assemblingPaperReqTemplate' scope="session"/>
			<c:set var="_obj" value='${assemblingPaperReqTemplate}'  scope="session"/>
			<jsp:include page="../studyflow/__paperAssemblingRequirements_input_courseVersion.jsp?t=edit"/>
		</td>
	</tr>
    <tr>
      <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
          <tr>
            <td align="center"><input type="submit" value="  保 存  " class="btn_2k3"/>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" value="  取 消  " class="btn_2k3" onClick='javascript:window.history.back()'/>
            </td>
          </tr>
        </table></td>
    </tr>
 
</table>

</form>
</td></tr></table>
<jsp:include page="./item/getlist.jsp"></jsp:include>
</body>
</html>
