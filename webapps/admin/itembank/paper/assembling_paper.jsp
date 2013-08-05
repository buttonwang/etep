<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script>
</script>
</head>

<body>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试卷管理 &gt; 组卷</td>
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
    <td align="left" valign="top" bgcolor="#FFFFFF">大题 ${paper.bigItemsNum}  个  ,  共包含  ${paper.itemsNum}  个小题
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">卷分：</td>
    <td align="left" bgcolor="#FFFFFF">${paper.totalScore}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">适用学级：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${gradeNames}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">所属学科：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${subjectNames}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${knowledgePointNames}</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue"> 包含题型：</td>
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
	       	<input type="button" value="  查看试卷  " class="btn_2k3" onClick="javascript: window.location.href='paper!show.jhtml?id=${paper.id}&subject_code=${subject_code}'"/>&nbsp;&nbsp;
	       	<input type="button" value="  返 回  "   class="btn_2k3" onClick="javascript: history.back()"/>&nbsp;&nbsp;	        
       	</td>
       </tr>
	   </table>
    </td>
  </tr>
</table>  
  	<iframe src="item!list.jhtml?paperId=${paper.id}&assemble=1&subject_code=${subject_code}&queryType=1" id="itemlistpage" frameborder="0" 
  		scrolling="no" width="100%" height="1200"  onload="this.height=1200">
 	</iframe>
 	<script type="text/javascript">
		function reinitIframe(){
			var iframe = document.getElementById("itemlistpage");
			try{
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				var height = Math.max(bHeight, dHeight);
				iframe.height = height;
			}catch (ex){}
		}
		window.setInterval("reinitIframe()", 200);
	</script>
</body>
</html>
