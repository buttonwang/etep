<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
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
<script src="../js/all/ShowHide.js"></script>
<script src="../js/all/DeleteAll.js"></script>
<script src="../js/all/DeleteConfirm.js"></script>
<script src="../js/My2Select.js"></script>
<script>
$(function(){
	try{
		eval('var subject_grade = ${m_userSubjectGradeRole_json}');		 
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","","");
	}catch(e){
	}
})
</script>
<%----%>
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
		ShowHide("input[opt=optSH],span[opt=optSH]");
		DeleteAll();
})
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略模板 &gt;组卷条件模板详情</td>
	</tr>
</table>
<div id="s_paperAssemblingReqTemplate" >
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模版名称：</td>
			<td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${paperAssemblingReqTemplate.name} </td>
		</tr>
		<tr>
			<td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="369" border="0">
					<tr>
						<td width="363"><input type="submit" class="btn_2k3" value="  修  改   " opt=optSH v='{h:"s_paperAssemblingReqTemplate",s:"e_paperAssemblingReqTemplate"}'/>
							&nbsp;&nbsp;
							<!--<input type="button" value="  删 除  " class="btn_2k3" onClick='javascript:if(confirm ("确定要删除？")){window.location.href="../policy/paperAssemblingReqTemplate.jhtml?atype=delete&id="+"${paperAssemblingReqTemplate.id}";}'/>-->
							&nbsp;&nbsp;
							<input type="button" value="  返 回  " class="btn_2k3" onClick="javascript:window.history.back();"/>
							&nbsp;&nbsp; </td>
					</tr>
				</table></td>
		</tr>
	</table>
</div>
<div id="e_paperAssemblingReqTemplate" style="display:none">
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<form action="../policy/paperAssemblingReqTemplate.jhtml" method="post"  lang="en">
			<tr>
				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模版名称：
					<input type="hidden" name="atype" value="iupdate">
					<input type="hidden" name="paperAssemblingReqTemplate.id" value="${paperAssemblingReqTemplate.id}"></td>
				<td width="83%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="paperAssemblingReqTemplate.name" value="${paperAssemblingReqTemplate.name}"/></td>
			</tr>
			<tr>
				<td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
						<tr>
							<td ><input type="submit" class="btn_2k3" value="  保 存  " />
								&nbsp;&nbsp;
								<input type="button" opt=optSH v='{h:"e_paperAssemblingReqTemplate",s:"s_paperAssemblingReqTemplate"}'  value="  取 消  " class="btn_2k3" /></td>
						</tr>
					</table></td>
			</tr>
		</form>
	</table>
</div>
<table class="location" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>本组卷策略模板组卷条件列表</td>
		<td align="right"><input type="button" value=" 新增条件 " class="btn_2k3" onClick='javascript:j("#add_assemblingPaperReqTemplate").show();'/></td>
	</tr>
</table>
<table id="table17" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#E3E3E3">
	<tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
		<td width="30">全选
			<input t=top type="checkbox"/></td>
		<td>序号</td>
		<td>地区</td>
		<td>学科</td>
		<td>学级</td>
		<td>题型</td>
		<td>试题年份</td>
		<td>试题来源</td>
		<td>原始套卷</td>
		<td>试题难度</td>
		<td>试题效度</td>
		<td>题数</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${v.assemblingPaperReqTemplatesLst}" var="item" varStatus="itemStatus">
		<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
			<td><input t=son name="ids" type="checkbox" value="${item.id}"/></td>
			<td>${itemStatus.index+1}</td>
			<td><c:forEach items="${regionList}" var="i" varStatus="Status">
					<c:if test="${item.regionCode eq i.code}">${i.name}</c:if>
				</c:forEach></td>
			<td><c:forEach items="${subjectList}" var="i" varStatus="Status">
					<c:if test="${item.subjectCode eq i.code}">${i.name}</c:if>
				</c:forEach></td>
			<td><c:forEach items="${gradeList}" var="i" varStatus="Status">
					<c:if test="${item.gradeCode eq i.code}">${i.name}</c:if>
				</c:forEach></td>
			<td><c:forEach items="${itemTypeList}" var="i" varStatus="Status">
					<c:if test="${item.itemTypeCode eq i.code}">(${i.code})${i.name}</c:if>
				</c:forEach></td>
			<td>${item.year}</td>
			<td><c:forEach items="${itemSourceList}" var="i" varStatus="Status">
					<c:if test="${fn:contains(item.source,i.value)}"> ${i } </c:if>
				</c:forEach></td>
			<td>${item.originalPaperCode}</td>
			<td>${item.difficultyValue}</td>
			<td>${item.validityValue}</td>
			<td>${item.amount}</td>
			<td><a href="assemblingPaperReqTemplate.jhtml?atype=edit&id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href="assemblingPaperReqTemplate.jhtml?atype=idelete&id=${item.id}" onClick="return confirm('确定要删除吗？');">删除</a></td>
		</tr>
	</c:forEach>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
	<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td align="left"><input type="button" value="批量删除" onClick="batchdelete('assemblingPaperReqTemplate.jhtml?atype=ideleteBatch&id=${paperAssemblingReqTemplate.id}&ids=')" class="btn_2k3"/></td>
				</tr>
			</table></td>
	</tr>
</table>
<table id="add_assemblingPaperReqTemplate" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:none">
	<form action="assemblingPaperReqTemplate.jhtml" method="post">
		<input name="atype" value="iadd"  type="hidden" />
		<input name="assemblingPaperReqTemplate.paperAssemblingReqTemplate.id" value="${paperAssemblingReqTemplate.id}" type="hidden" />
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.regionCode">
					<option selected="selected" value="">全部</option>
					<c:forEach items="${regionList}" var="item" varStatus="itemStatus">
						<option value="${item.code}" ${assemblingPaperReqTemplate.regionCode eq item.code ? 'selected="selected"':''}>${item.name}</option>
					</c:forEach>
				</select></td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
			<td align="left" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.subjectCode" vt=subjectCode>
				</select></td>
		</tr>
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.gradeCode" vt=gradeCode>
				</select></td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
			<td align="left" bgcolor="#FFFFFF"><select name="assemblingPaperReqTemplate.itemTypeCode">
					<option selected="selected" value="">全部</option>
					<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus">
						<option value="${item.code}"  >${item.code}(${item.name})</option>
					</c:forEach>
				</select></td>
		</tr>
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><span n="assemblingPaperReqTemplate.year">
				<input v=1 value="" type="text"  size="3" />
				&nbsp;—
				<input v=2 value="" type="text"  size="3" />
				</span>
				<input type="hidden" name="assemblingPaperReqTemplate.year" value="${assemblingPaperReqTemplate.year}"></td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
			<td align="left" bgcolor="#FFFFFF"><c:forEach items="${itemSourceList}" var="item" varStatus="itemStatus">
					<input name="assemblingPaperReqTemplate.source"  type="checkbox" ${fn:contains(assemblingPaperReqTemplate.source, item.value)?'checked="checked"':''} 
    			 value="${item.value}" />
					${item}&nbsp; </c:forEach></td>
		</tr>
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><span n="assemblingPaperReqTemplate.difficultyValue">
				<input v=1 value="" type="text"  size="3" />
				&nbsp;—
				<input n="assemblingPaperReqTemplate.difficultyValue" v=2 value="" type="text"  size="3" />
				</span>
				<input type="hidden" name="assemblingPaperReqTemplate.difficultyValue" value="${assemblingPaperReqTemplate.difficultyValue}"  size="3" /></td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
			<td align="left" bgcolor="#FFFFFF"><span n="assemblingPaperReqTemplate.validityValue">
				<input v=1 value="" type="text" size="3" />
				&nbsp;—
				<input v=2 value="" type="text" size="3" />
				</span>
				<input type="hidden" name="assemblingPaperReqTemplate.validityValue" value="${assemblingPaperReqTemplate.validityValue}" size="3" /></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.amount" value="${assemblingPaperReqTemplate.amount}"/></td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.originalPaperCode" value="${assemblingPaperReqTemplate.originalPaperCode}"/></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">直观评价： </td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.opinion" value="${assemblingPaperReqTemplate.opinion}"/></td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select class="logininputmanage"  name="assemblingPaperReqTemplate.applicableObject">
					<option value='0' ${assemblingPaperReqTemplate.applicableObject==0?'selected':''}>文理科</option>
					<option value='1' ${assemblingPaperReqTemplate.applicableObject==1?'selected':''}>文科</option>
					<option value='2' ${assemblingPaperReqTemplate.applicableObject==2?'selected':''}>理科</option>
				</select></td>
		</tr>
		<tr><td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点： </td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="assemblingPaperReqTemplate.knowledgePointCode" value="${assemblingPaperReqTemplate.knowledgePointCode}"/></td>
		
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点： </td>
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
							<input type="button" value="  取 消  " class="btn_2k3" onClick='j("#add_assemblingPaperReqTemplate").hide()' /></td>
					</tr>
				</table></td>
		</tr>
	</form>
</table>
</body>
</html>
