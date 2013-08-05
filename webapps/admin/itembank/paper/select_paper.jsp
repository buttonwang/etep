<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="ambow" prefix="ambow"%>   
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script>
  function assembleBatch() {
    var ids = getCheckedIntValue("checkitem");
    if (ids == '') {
    	alert('请选中试卷后入卷！');
    	return;
    }
    if (confirm("确定要入卷吗？")) {
  		var actionurl = 'paper!assembleBatch.jhtml?ids=' + ids + '&itemids=' + '${itemids}';
  		//alert(actionurl);
  		window.location.href = actionurl;
  	}
  }
  
  function verifyBatch() {
    var ids = getCheckedIntValue("checkitem");
    if (ids == '') {
    	alert('请选中试卷后审核！');
    	return;
    }
    if (confirm("确定要审核吗？")) {
  		var actionurl = 'paper!verifyBatch.jhtml?ids=' + ids;
  		window.location.href = actionurl;
  	}
  }
  
  function invalidBatch() {
  	var ids = getCheckedIntValue("checkitem");
    if (ids == '') {
    	alert('请选中试卷后作废！');
    	return;
    }
    if (confirm("确定要作废吗？")) {
  		var actionurl = 'paper!invalidBatch.jhtml?ids=' + ids;
  		window.location.href = actionurl;
  	}
  }
  
  function deleteBatch() {
  	var ids = getCheckedIntValue("checkitem");
    if (ids == '') {
    	alert('请选中试卷后删除！');
    	return;
    }
    if (confirm("确定要删除吗？")) {
  		var actionurl = 'paper!deleteBatch.jhtml?ids=' + ids;
  		window.location.href = actionurl;
  	}
  }
</script>
</head>
<body>
<form name="pageForm" action="paper!choose.jhtml" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试卷管理 &gt; 试卷查询</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
      	<td align="left" >
	     	  	<input type="hidden" value="${pageNo}" name="pageNo" id="pageNo"/>
				<select name="queryType" style="height:20px;">
			    	<option value="1" ${queryType eq 1? 'selected="selected"':''}>试卷名称：</option>
			    	<option value="2" ${queryType eq 2? 'selected="selected"':''}>适用地区：</option>
			    	<option value="3" ${queryType eq 3? 'selected="selected"':''}>所属学科：</option>
			    	<option value="4" ${queryType eq 4? 'selected="selected"':''}>适用学级：</option>
			    	<option value="5" ${queryType eq 5? 'selected="selected"':''}>试卷分类：</option>
			    	<option value="6" ${queryType eq 6? 'selected="selected"':''}>试卷类型：</option>
			    	<option value="7" ${queryType eq 7? 'selected="selected"':''}>试卷状态：</option>			    		        		 
		    	</select>
		      	<input name="queryValue" type="text" class="logininputmanage" size="15"/>&nbsp;&nbsp;
			    <input type="submit" value="查询" class="btn_2k3"  />&nbsp;
			    <input type="button" value="高级" onClick="hiddenDiv.style.display='';" class="btn_2k3"/>
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
<div id="hiddenDiv"  style="display:none;">
<table class="txt12555555line-height"  width="100%" border="0" align="center"
 	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
  <tr>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">试卷名称：</td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="name"/>
    </td>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="status">
    		<option value="" selected="selected">全部</option>
      		<option value="0">未审核</option>
      		<option value="1">已审核</option>
      		<option value="2">已组卷</option>
      		<option value="3">已使用</option>
      		<option value="-1">已作废</option>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷分类：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="categoryCode">
    		<option value="" selected="selected">全部</option>
    		<c:forEach items="${paperCategory}" var="paperCategory" varStatus="status">
    		<option value="${paperCategory.code}">${paperCategory.name}</option>
    	  	</c:forEach>
    	</select>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷类型：</td>
    <td align="left" bgcolor="#FFFFFF">
    	<select name="typeCode">
    		<option value="" selected="selected">全部</option>
    		<c:forEach items="${paperType}" var="paperType" varStatus="status">
    		<option value="${paperType.code}">${paperType.name}</option>
    	  	</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="subjectCode">
    		<option value="" selected="selected">全部</option>
    		<c:forEach items="${subject}" var="subject" varStatus="status">
    		<option value="${subject.code}">${subject.name}</option>
    	  	</c:forEach>
    	</select>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
    <td align="left" bgcolor="#FFFFFF">
    	<select name="gradeCode">
    		<option value="" selected="selected">全部</option>
    		<c:forEach items="${grade}" var="grade" varStatus="status">
    		<option value="${grade.code}">${grade.name}</option>
    	  	</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
    <td align="left" bgcolor="#FFFFFF">
    	<select name="regionCode">
    		<option  value="" selected="selected">全部</option>
    		<c:forEach items="${region}" var="region" varStatus="status">
    		<option value="${region.code}">${region.name}</option>	
    	  	</c:forEach>
    	</select>
    </td>
    <td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">试卷难度：</td>
    <td align="left" bgcolor="#FFFFFF">
  		<input type="text" class="logininputmanage" name="minDifficultyValue" size="3" />&nbsp;—
        <input type="text" class="logininputmanage" name="maxDifficultyValue" size="3" />
	  </td>
  </tr>
  <tr bgcolor="#F7F7F7" height="50">
    <td align="center"  colspan="4">
    	<input type="submit" value="   查 询   " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="reset"  value="   取 消   " class="btn_2k3" onClick="hiddenDiv.style.display='none';"/>
    </td>    
  </tr>
</table>
</div>
</td>
</tr>
<tr>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
	<td width="30">全选<input type="checkbox" onclick="checkAll('checkitem')"/></td>
    <td>试卷名称</td>
    <td>试卷分类</td>
    <td>试卷类型</td>
    <td>状态</td>
  </tr>
	  <c:forEach items="${page.result}" var="paper" varStatus="status">
	    <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	    	<td><input type="checkbox" name="checkitem" value="${paper.id}"></td>
		    <td><a href="paper!show.jhtml?id=${paper.id}">${paper.name}</a></td>
		    <td>${paper.paperCategory.name}</td>
		    <td>${paper.paperType.name}</td>
		    <td>${paper.statusName}</td>
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
	      		<input type="button" class="btn_2k3" value=" 入 卷 " onclick="assembleBatch()">&nbsp;&nbsp;
				<input type="button" class="btn_2k3" value=" 取 消 " onclick="history.back();">
				<input type="hidden" name ="itemids" value="${itemids}" />
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