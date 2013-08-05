<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="ambow" prefix="ambow"%>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script>
  function splitBatch() {
    var ids = getCheckedIntValue("checkitem");
    if (ids == '') {
    	alert('请选中试题后拆卷！');
    	return;
    }
    if (confirm("确定要拆卷吗？")) {
  		var actionurl = "paper!split.jhtml?id=${paper.id}&itemids=" + ids;
  		//alert(actionurl);
  		window.location.href = actionurl;
  	}
  }
</script>
</head>

<body>
<form name="pageForm" action="paper!show.jhtml" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试卷管理 &gt; 试卷详情</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷名称：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">${paper.name}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.statusName}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷说明：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">${paper.description}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.region.name}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷分类：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.paperCategory.name}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷类型：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">${paper.paperType.name}</td>
  </tr>  
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">试卷难度：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><fmt:formatNumber value='${paper.difficultyValue}' pattern='#######.##'/></td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答卷时间：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF"><fmt:formatNumber value='${paper.answeringTime/60}' pattern='0'/>（分钟）</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
    <td align="left" valign="top" bgcolor="#FFFFFF">大题 ${paper.bigItemsNum}  个  ,  共包含  ${paper.itemsNum}  个小题</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">卷分：</td>
    <td align="left" bgcolor="#FFFFFF">${paper.totalScore}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${gradeNames}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${subjectNames}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${knowledgePointNames}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"> 包含题型：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${itemTypeNames}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建人：<br /></td>
    <td align="left" valign="top" bgcolor="#FFFFFF">${paper.creater}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">${paper.createdTime}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">更新人：<br /></td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.updater}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">更新时间：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.updatedTime}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">审核人：<br /></td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.verifier}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">审核时间：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${paper.verifiedTime}</td>
  </tr>

  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
       <table border="0" width="100%">
     	<tr>
       	<td align="center">
       		<input type="hidden" value="${paper.id}" name="id"/>
	        <input type="button" value="  修 改  " class="btn_2k3" onClick="javascript: window.location.href='paper!edit.jhtml?id=${paper.id}&subject_code=${subject_code}'"/>&nbsp;&nbsp;
	       	<input type="button" value="  审 核  " class="btn_2k3" onClick="javascript: if (confirm('确定要审核吗？')) window.location.href='paper!verify.jhtml?id=${paper.id}'"/>&nbsp;&nbsp;
         	<input type="button" value="  作 废  " class="btn_2k3" onClick="javascript: if (confirm('确定要作废吗？')) window.location.href='paper!invalid.jhtml?id=${paper.id}'"/>&nbsp;&nbsp;
         	<input type="button" value="  删 除  " class="btn_2k3" onClick="javascript: if (confirm('确定要删除吗？')) window.location.href='paper!delete.jhtml?id=${paper.id}'"/>&nbsp;&nbsp;
         	<input type="button" value="  组 卷  " class="btn_2k3" onClick="javascript: window.location.href='paper!assembling.jhtml?id=${paper.id}&subject_code=${subject_code}'"/>&nbsp;&nbsp;
         	<input type="button" value="  返 回  " class="btn_2k3" onClick="javascript: history.back()"/>&nbsp;&nbsp;
       	</td>
       </tr>
  	  </table>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">本卷试题列表</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
<tr>
<td>
  <table class="txt12555555line-height"  width="100%" border="0" align="center" 
  		 cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
		<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
			<td width="30">全选<input type="checkbox" onClick="checkAll('checkitem')"/></td>
		    <td>编码</td>
		    <td>题型</td>
		    <td>年份</td>
		    <td>来源</td>
		    <td>难度</td>
		    <td>题干（文章）</td>
		    <td>分值</td>
		    <td>状态</td>
		</tr>
   <c:forEach items="${page.result}" var="item" varStatus="status">
   		<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	    	<td><input type="checkbox" name="checkitem" value="${item.id}"></td>
		    <td><a href="item!show.jhtml?id=${item.id}">${item.code}</a></td>
		    <td>${item.itemType.code}(${item.itemType.name})</td>
		    <td>${item.year}</td>
		    <td>${item.source}</td>
		    <td>${item.difficultyValue}</td>
		    <td>${item.content}</td>
		    <td>${item.score}</td>
		    <td>${item.statusName}</td>
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
      		<input type="hidden" value="${pageNo}" name="pageNo" id="pageNo"/>
      		<input type="button" value="批量拆卷" class="btn_2k3" onClick="splitBatch()"/>
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
