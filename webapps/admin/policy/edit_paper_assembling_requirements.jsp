<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%	request.getParameter("nodeId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/My2Select.js"></script>
<script src="../js/policy_common.js"></script>
<script>
$(function(){
	try{
		eval('var subject_grade = ${m_userSubjectGradeRole_json}');		 
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","${paperAssemblingRequirements.subjectCode}","${paperAssemblingRequirements.gradeCode}");
	}catch(e){
	}
})
</script>
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
	$("#_sc,#_gc").change(function(){
		var g = new G();		
		var _kcDef = '${paperAssemblingRequirements.knowledgePointCode}';
		var _itemTypeCodeDef = '${paperAssemblingRequirements.itemTypeCode}';
		
		var v1 = $("#_sc").val();
		var v2 = $("#_gc").val();
		$("#_kc").html("");
		$("#_itemTypeCode").html("");
		var _kcStr =["<option >请选择</option>"];
		var _itemTypeCodeStr = ["<option >请选择</option>"];
		try{
			var j_kc = g.param("#_knowledgePoint li[_sc={0}][_gc={1}]",v1,v2);
			var j_itemTypeCode = g.param("#_itemType li[_sc={0}][_gc={1}]",v1,v2);
			$(j_kc).each(function(){
				var _kc = $(this).attr("_kc");
				var selected = (_kcDef == _kc )?"selected":"";
				_kcStr[_kcStr.length]=g.param("<option value={0} {2}>{1}</option>",_kc,$(this).html(),selected);
			})
			$(j_itemTypeCode).each(function(){
				var _ic = $(this).attr("_ic");
				var selected = (_itemTypeCodeDef == _ic )?"selected":"";
				_itemTypeCodeStr[_itemTypeCodeStr.length]=g.param("<option value={0} {2}>{1}</option>",_ic,$(this).html(),selected);
			})
		}catch(e){
		}
		$("#_kc").html(_kcStr.join(""));
		$("#_itemTypeCode").html(_itemTypeCodeStr.join(""));
	}).trigger("change");	
})
</script>
</head>
<body>
<table width="100%">
	<tr>
		<td>
<form action="../policy/paperAssemblingRequirements.jhtml" method="post">
	<input type="hidden" name="atype" value="${nodeType}update">
	<input type="hidden" name="paperAssemblingRequirements.id" value="${paperAssemblingRequirements.id}">
	<input type="hidden" name="paperAssemblingRequirements.asfNode.id" value="${paperAssemblingRequirements.asfNode.id}">
	<c:set var="_objName" value='paperAssemblingRequirements' scope="session"/>
	<c:set var="_obj" value='${paperAssemblingRequirements}'  scope="session"/>
	<c:set var="_cancleButtonEvent" scope="session">
	 onClick="${_type=='main'}javascript:window.history.back()"
	</c:set>
	<c:import url="../studyflow/__paperAssemblingRequirements_input.jsp"/>
	<c:remove var="_cancleButtonEvent" scope="session"/>
	<c:remove var="_objName" scope="session"/>
	<c:remove var="_obj" scope="session"/>
</form>		
		</td>
	</tr>
</table>
<c:set var="KnowledgePointNames" value="${KnowledgePointNames}" scope="session"/>
<c:set var="subject_code" value="${paperAssemblingRequirements.subjectCode}" scope="session"/>
<c:set var="grade_code" value="${paperAssemblingRequirements.gradeCode}" scope="session"/>
<jsp:include page="./item/getlist.jsp"></jsp:include>

<div id="_knowledgePoint" style="display:none">
<c:forEach items="${knowledgePointList}" var="item" varStatus="itemStatus"><li _kc="${item.code}" _sc="${item.subject.code}" _gc="${item.grade.code}">${item.code}/${item.name}</li>
</c:forEach>
</div>
<div id="_itemType" style="display:none">
<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus"><li  _ic="${item.code}" _sc="${item.subject.code}" _gc="${item.grade.code}">${item.code}/${item.name}</li>
</c:forEach>
</div>

</body>
</html>
