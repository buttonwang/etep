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
  		var actionurl = 'itemType!deleteBatch.jhtml?codes=' + codes;
  		window.location.href = actionurl;
  	}
  } 
</script>
</head>
<body>
<form name="pageForm" action="itemType!list.jhtml" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 题型 &gt; 题型列表</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left" >
      		<input type="hidden" value="${pageNo}" name="pageNo" id="pageNo"/>
	      	<input type="button" value=" 新 建 " class="btn_2k3" onClick="javascript: window.location.href='itemType!edit.jhtml?subject_code=${subject_code}'" />&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="queryType" style="height:20px;">
      				<option value="2" ${queryType eq 2? 'selected="selected"':''}>题型名称：</option>
			    	<option value="1" ${queryType eq 1? 'selected="selected"':''}>题型编码：</option>
		        </select>
			    <input class="logininputmanage" type="text" name="queryValue" value="${queryValue}" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value=" 查 询 " class="btn_2k3"  onclick="toSumit()"/>&nbsp;
				 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<!--学科：
				<select name="subject_code" id="subject_code">
					<option value="" selected>所有</option>
					 
                <c:forEach items="${sessionScope.managerSubjectSet}" var="item" varStatus="itemStatus">                  
	                  	<option value="${item.code}" ${subject_code==item.code?"selected":""}>${item.name}</option>
             	</c:forEach>
           		</select>
				学级：				 
				<select name="grade_code" id="grade_code">
				 
				<c:forEach items="${sessionScope.managerGradeSet}" var="item" varStatus="itemStatus">                  
	                  	<option value="${item.code}" ${grade_code==item.code?"selected":""}>${item.name}</option>
             	</c:forEach>
				</select>-->
			 学级：<input type="hidden" value="${subject_code}" name="subject_code" id="subject_code"/>
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
				<input type="submit" value=" 筛 选 " class="btn_2k3"  onClick="toSumit()"/>
				 </td>
        <td class="ReporterCol_2" align="right">
        	<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  num="${page.currentPageNo}" 
        	                  otherParams=""/>    	</td>
      </tr>
      </table>
	</td>
	</tr>
<tr>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选<input type="checkbox" onclick=checkAll('checkitem') /></td>
    <td>题型编码</td>
    <td>题型名称</td>
	 <td>学级</td>
	 <td>学科</td>
    <td>每页显示题数</td>
    <td>操作</td>
  </tr>
  <c:forEach items="${page.result}" var="itemType" varStatus="status">
  	  <c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
		<c:if test="${itemType.subject.code == usrg.subject.code}">
			<c:forEach items="${usrg.grades}" var="grade" >
						<c:if test="${itemType.grade.code == grade.code}">
			<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	        <td><input type="checkbox" name="checkitem" value="${itemType.code}"></td>
		    <td><a href="itemType!show.jhtml?code=${itemType.code}">${itemType.code}</a></td>
		    <td>${itemType.name}</td>
			 <td><c:forEach items="${gradeList}" var="item" varStatus="itemStatus">                  
	                  	<c:if test="${itemType.grade.code eq item.code}"> ${item.name}</c:if>
             	</c:forEach>
			</td>
			  <td><c:forEach items="${subjectList}" var="item" varStatus="itemStatus">                  
	                  	<c:if test="${itemType.subject.code eq item.code}"> ${item.name}</c:if> 
             	</c:forEach>
				</td>
		    <td>${itemType.itemNumPerpage}</td>
		    <td><a href="itemType!edit.jhtml?code=${itemType.code}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<a href="itemType!delete.jhtml?code=${itemType.code}" onClick="return confirm('确定要删除吗？')">删除</a>
		    </td>
	      </tr>		
						</c:if> 
			</c:forEach>
		</c:if>
	</c:forEach> 
	     
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