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
  		var actionurl = 'wordBasic!deleteBatch.jhtml?ids=' + ids;
  		window.location.href = actionurl;
  	}
  }
  
  function refreshBatch() {
  	var actionurl = 'wordBasic!refreshBatch.jhtml';
  	window.location.href = actionurl;
  }
</script>
</head>
<body>
<form name="pageForm" action="wordBasic!list.jhtml" method="post">
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
      	<td align="left" >
	      	<input type="button" value=" 新 建 " class="btn_2k3" onclick="javascript: window.location.href='wordBasic!edit.jhtml'"/>&nbsp;&nbsp;&nbsp;&nbsp;
			    单词拼写：<input class="logininputmanage" type="text" name="queryValue" value="${queryValue}" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value="查询" class="btn_2k3" onclick="resetPageNo()"/>&nbsp;
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
			    <td width="10%">拼写</td>
			    <td width="10%">音标</td>
			    <td width="20%">常用搭配</td>
			    <td width="20%">词汇用法</td>
			    <td width="20%">联想记忆</td>
			    <td width="10%">操作</td>
			  </tr>
			  <c:forEach items="${page.result}" var="wordBasic" varStatus="status">
			  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
			    	<td><input type="checkbox" name="checkitem" value="${wordBasic.id}"></td>
				    <td><a href="wordBasic!show.jhtml?id=${wordBasic.id}">${wordBasic.word}</a></td>
				    <td>${wordBasic.phoneticSymbol} <!--&nbsp;&nbsp;<a href="*.mp3">朗读</a> --></td>
				    <!--<td>${fn:substring(wordBasic.commonUsage,0,20)}</td>-->
				    <td>${wordBasic.commonUsage}</td>
				    <td>${wordBasic.wordUsage}</td>
				    <td>${wordBasic.associationMemory}</td>
				    <td>				    	
				    	<a href="wordBasic!edit.jhtml?id=${wordBasic.id}">修改</a>
				    	<a href="wordBasic!delete.jhtml?id=${wordBasic.id}" onclick="return confirm('确定要删除吗?');">删除</a>
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
	      		<input type="button" value="批量删除" class="btn_2k3" onclick="deleteBatch()"/>
	      		<input type="hidden" value="批量刷新" class="btn_2k3" onclick="refreshBatch()"/>
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
