<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="ambow" prefix="ambow"%>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script>
  function deleteBatch() {
  	var ids = getCheckedIntValue("checkitem");
    if (ids == '') {
    	alert('请选中单词后删除！');
    	return;
    }
    if (confirm("确定要删除吗？")) {
  		var actionurl = 'wordExtension!deleteBatch.jhtml?gradeCode=${gradeCode}&ids=' + ids;
  		window.location.href = actionurl;
  	}
  }
</script>
</head>
<body>
<form name="pageForm" action="wordExtension!list.jhtml" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：词库 &gt; 单词管理 &gt; 单词查询</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left">
	      	<input type="button" value=" 新 建 " class="btn_2k3" onclick="javascript: location.href='wordExtension!add.jhtml?gradeCode=${gradeCode}'"/>
	      	&nbsp;&nbsp;&nbsp;&nbsp;单词拼写：
			<input class="logininputmanage" type="text" name="queryValue" value="${queryValue}" size="15"/>&nbsp;&nbsp;
			<input type="submit" value="查询" class="btn_2k3" onclick="resetPageNo()" />&nbsp;
		</td>
        <td class="ReporterCol_2" align="right">
        	<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}"
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
			  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >			  	
					<td width="10%">全选<input type="checkbox" onclick="checkAll('checkitem')"/></td>			
				    <td>拼写</td>
				    <td>音标</td>
				    <td>级别</td>
				    <td>分类</td>
				    <td>学级</td>
				    <td>操作</td>
			  </tr>
			  <c:forEach items="${page.result}" var="wordExtension" varStatus="status">
			  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
			    	<td><input type="checkbox" name="checkitem" value="${wordExtension.id}"></td>
				    <td><a href="wordExtension!show.jhtml?id=${wordExtension.id}">${wordExtension.wordBasic.word}</a></td>
				    <td>${wordExtension.wordBasic.phoneticSymbol} <!--&nbsp;&nbsp;<a href="*.mp3">朗读</a> --></td>
				    <!--<td>${fn:substring(wordBasic.commonUsage,0,20)}</td>-->
				    <td>${wordExtension.wordLevelName}</td>
				    <td>${wordExtension.wordCategoryName.value}</td>
				    <td>${wordExtension.grade.name}</td>
				    <td>
				    	<a href="wordExtension!edit.jhtml?id=${wordExtension.id}">修改</a>
				    	<a href="wordExtension!delete.jhtml?gradeCode=${gradeCode}&id=${wordExtension.id}" onclick="return confirm('确定要删除吗?');">删除</a>
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
	      		<input type="hidden" value="${gradeCode}" name="gradeCode"/>
	      		<input type="button" value="批量删除" class="btn_2k3" onclick="deleteBatch()"/>
	      	</td>
	        <td class="ReporterCol_2" align="right">
        		<ambow:pagination actionName="" 
        	                  total="${page.totalPageCount}" 
        	                  totalCount="${page.totalCount}"
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
