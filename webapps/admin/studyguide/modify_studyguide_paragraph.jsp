<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="ambow" prefix="ambow"%>  
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script language="javascript" src="../js/admin.js" ></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
<script type="text/javascript">	
	window.onload = function() {
		replacTextArea("paragraph.title");
		replacTextArea("paragraph.content");
	}

	function doit(){
		if(document.getElementById('paragraph.orderNum').value==''){
			alert('请填写段落顺序号！');
			return;
		}
		document.submitForm.submit();
	}
		
	if (${kpSelectFailed != null}) {
		alert("${kpSelectFailed}");
	}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：学习指导 &gt; 修改学习指导段落</td>
  </tr>
</table>
<form id="submitForm" name="submitForm" action="itemStudyGuide!save.jhtml?studyGuide.id=${studyGuide.id }&type=P" method="post">
<c:if test="${not empty paragraph.id}">
	<input type="hidden" name="paragraph.id" value="${paragraph.id}">
</c:if>
<input type="hidden" name="paragraph.studyGuide.id" value="${studyGuide.id}">
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#F7F7F7">
    <tr>
  	  <td align="right" valign="top" bgcolor="#F7F7F7" colspan="4">
  	  <a href="itemStudyGuide!editItem.jhtml?studyGuide.id=${paragraph.studyGuide.id }&item.studyGuideParagraph.id=${paragraph.id }&item.id=">增加习题</a>
  	  </td>
    </tr>
    <tr>
	    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">段落标题：</td>
	    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7" colspan="3">
			<textarea name="paragraph.title" rows="2" cols="80">${paragraph.title}</textarea>
	    </td>
    </tr>
	<tr>
	    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">段落内容：</td>
	    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7" colspan="3">
			<textarea name="paragraph.content" rows="5" cols="80">${paragraph.content}</textarea>
	    </td>
    </tr>
	<tr>
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">段落顺序号：</td>	
		<td width="33%" align="left" bgcolor="#F7F7F7">
			<input class="logininputmanage" type="text" name="paragraph.orderNum" value="${paragraph.orderNum }">
		</td>	
		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>	
		<td width="33%" align="left" bgcolor="#F7F7F7">
		</td>
	</tr>
</table>
<table class="txt12555555line-height" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
  <tr> 
    <td align="center" valign="top">
    	<input type="button" value="  确 定  " class="btn_2k3" onclick="doit();"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="  取 消  " class="btn_2k3" onclick="javascript: history.back()"/>
    </td>
  </tr>
</table>
</form>
</body>
</html>