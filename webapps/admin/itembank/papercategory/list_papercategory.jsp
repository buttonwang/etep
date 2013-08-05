<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>   
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script>
  function batchdelete() {
    var codes = getCheckedValue("checkitem");
    if (codes=='') {
    	alert('请先选中项目，再删除！');
    	return;
    }
    if (confirm('确定要删除吗？')) {
  		var actionurl = 'paperCategory!deleteBatch.jhtml?codes=' + codes;
  		window.location.href = actionurl;
  	}	
  }
</script>
</head>
<body>
<form name="pageForm" action="paperCategory!list.jhtml" method="post">
<input type="hidden" name="subject_code" value="${subject_code}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试卷分类 &gt; 试卷分类列表</td>
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
	      	<input type="button" value=" 新 建 " class="btn_2k3" onClick="javascript: window.location.href='paperCategory!edit.jhtml?subject_code=${subject_code}'" />&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="queryType" style="height:20px;">
      				<option value="2" ${queryType eq 2? 'selected="selected"':''}>试卷分类名称：</option>
			    	<option value="1" ${queryType eq 1? 'selected="selected"':''}>试卷分类编码：</option>
		        </select>
			    <input class="logininputmanage" type="text" name="queryValue" value="${queryValue}" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value=" 查 询 " class="btn_2k3" onClick="toSumit()"/>&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 
							 学级： 
				<select name="grade_code" id="grade_code">
					<option value="">选择年级</option>
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
	<c:if test="${subject_code == usrg.subject.code}">
		<c:forEach items="${usrg.grades}" var="grade" >
					<option value="${grade.code}" ${grade_code==grade.code?"selected":""}>${grade.name}</option>
		</c:forEach>
	</c:if>
</c:forEach>  </select>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value=" 筛 选 " class="btn_2k3"  onClick="toSumit()"/>
		</td>
        <td class="ReporterCol_2" align="right">
        	<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/>
    	</td>
      </tr>
      </table>
	</td>
	</tr>
<tr>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选<input type="checkbox" onclick=checkAll('checkitem') /></td>
    <td>试卷分类编码</td>
    <td>试卷分类名称</td>
	<td>学级</td>
	<td>学科</td>
    <td>操作</td>
  </tr>
  <c:forEach items="${page.result}" var="paperCategory" varStatus="status">
	     <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	        <td><input type="checkbox" name="checkitem" value="${paperCategory.code}"></td>
		    <td><a href="paperCategory!show.jhtml?code=${paperCategory.code}">${paperCategory.code}</a></td>
		    <td>${paperCategory.name}</td>
			<td><c:forEach items="${gradeList}" var="item" varStatus="itemStatus">                  
	                  	<c:if test="${paperCategory.grade.code eq item.code}"> ${item.name}</c:if>
             	</c:forEach>
			</td>
			  <td><c:forEach items="${subjectList}" var="item" varStatus="itemStatus">                  
	                  	<c:if test="${paperCategory.subject.code eq item.code}"> ${item.name}</c:if> 
             	</c:forEach>
				</td>
		    <td><a href="paperCategory!edit.jhtml?code=${paperCategory.code}&subject_code=${subject_code}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<a href="paperCategory!delete.jhtml?code=${paperCategory.code}" onClick="return confirm('确定要删除吗？')">删除</a>
		    </td>
	      </tr>
  </c:forEach>  
</table>
</td>
</tr>
<tr>
	<td>
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left">
      		<input type="button" value="批量删除" class="btn_2k3" onClick="batchdelete()"/>
      	</td>
		<td class="ReporterCol_2" align="right">
        	<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/>
    	</td>
      </tr>
    </table>
	</td>
</tr>
</table>
</form>
</body>
</html>