<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> 
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script src="../js/m.js"></script>
<script>
j(function(){
	j("img").click( function() {
		if (!confirm("确定要修改图片文件吗？")) return false;
		document.getElementById("src").value = this.src;
		document.importImage.submit();
		//location.href= "importImage!show.jhtml?itemId=${item.id}&src=" + this.src;
		}
	);
})
</script>
</head>
<body>
<form action="importImage!show.jhtml?itemId=${item.id}" name="importImage" id="importImage" method="post">
<input type="hidden" value="" name="src" id="src"/>
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 试题详情</td>
  </tr>
</table>
    <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.code}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.statusName}</td>
	  </tr>
	   <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.region.name}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.subject.name}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用年级：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${itemVO.gradeNames}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${itemVO.knowledgePointNames}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.itemType.code}(${item.itemType.name})</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.year}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.sourceName}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.sourceBook}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.sourceFile}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.originalPaperCode} </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.originalItemNum}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.score}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.difficultyValue}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.answeringTimeByMin}&nbsp;分钟</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题材：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${itemVO.itemThemeNames}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.content}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干译文：</td>
	    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">${item.contentTranslation}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.correctAnswer}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答案解析：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">${item.answerAnalysis}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">判分关键词： </td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.scoringKeywords}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">写作模板：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">${item.writingTemplate}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">评分标准：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">${item.scoringNorm}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">创建人：<br /></td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.creater}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
	    <td width="33%"align="left"  bgcolor="#FFFFFF">${item.createdTime}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新人：<br /></td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.updater}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新时间：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.updatedTime}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核人：<br /></td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.verifier}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核时间：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.verifiedTime}</td>
	  </tr>
      <tr>
          <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
		      	<tr>
		        	<td align="center">
  		        	<input type="button" value="  修 改  " class="btn_2k3"  onClick="location.href='item!edit.jhtml?id=${item.id}&subject_code=${subject_code}';"/>&nbsp;&nbsp;
	  	        	<input type="button" value="  审 核  " class="btn_2k3"  onClick="location.href='item!verify.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
		          	<input type="button" value="  作 废  " class="btn_2k3"  onClick="location.href='item!invalid.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
		          	<input type="button" value="  删 除  " class="btn_2k3"  onClick="if (confirm('确定要删除吗？')) location.href='item!delete.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
		          	<input type="button" value="  入 卷  " class="btn_2k3"  onClick="location.href='paper!choose.jhtml?itemids=${item.id}';" />&nbsp;&nbsp;
		          	<input type="button" value="  返 回  " class="btn_2k3"  onClick="location.href='item!list.jhtml';"/>&nbsp;&nbsp;
		        	</td>
		        </tr>
	    	  </table>
          </td>
      </tr>
    </table>
</body>
</html>