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
  		var actionurl = 'grade!deleteBatch.jhtml?codes=' + codes;
  		window.location.href = actionurl;
  	}	
  }
</script>
</head>
<body>
<form name="pageForm" action="grade!list.jhtml" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 学级 &gt; 学级列表</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left" >
      		<input type="hidden" value="${pageNo}" name="pageNo" id="pageNo"/>
	      	<input type="button" value=" 新 建 " class="btn_2k3" onclick="javascript: window.location.href='grade!edit.jhtml'" />&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="queryType" style="height:20px;">
      				<option value="2" ${queryType eq 2? 'selected="selected"':''}>学级名称：</option>
			    	<option value="1" ${queryType eq 1? 'selected="selected"':''}>学级编码：</option>
		        </select>
			    <input class="logininputmanage" type="text" name="queryValue" value="${queryValue}" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value=" 查 询 " class="btn_2k3" onclick="toSumit()"/>&nbsp;
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
    <td>学级编码</td>
    <td>学级名称</td>
    <td>操作</td>
  </tr>
  <c:forEach items="${page.result}" var="grade" varStatus="status">
	     <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	        <td><input type="checkbox" name="checkitem" value="${grade.code}"></td>
		    <td><a href="grade!show.jhtml?code=${grade.code}">${grade.code}</a></td>
		    <td>${grade.name}</td>
		    <td><a href="grade!edit.jhtml?code=${grade.code}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<a href="grade!delete.jhtml?code=${grade.code}">删除</a>
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
      		<input type="button" value="批量删除" class="btn_2k3" onclick="batchdelete()"/>
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