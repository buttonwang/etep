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
		replacTextArea("item.content");
		replacTextArea("item.analys");
		replacTextArea("item.answer");
	}

	function doit(){
		if(document.getElementById('item.orderNum').value==''){
			alert('请填写顺序号！');
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
    <td class="location">当前位置：学习指导 &gt; 修改学习指导</td>
  </tr>
</table>
<form id="submitForm" name="submitForm" action="itemStudyGuide!save.jhtml?studyGuide.id=${studyGuide.id }&type=I" method="post">
<c:if test="${not empty item.id}">
<input type="hidden" name="item.id" value="${item.id}">
</c:if>
<input type="hidden" name="item.studyGuideParagraph.id" value="${item.studyGuideParagraph.id}">
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#F7F7F7">
		<tr>
	    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7" colspan="3">
					<textarea name="item.content" rows="5" cols="80">${item.content}</textarea>
    </td>
  </tr>
	<tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">解析：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7" colspan="3">
				<textarea name="item.analys" rows="5" cols="80">${item.analys}</textarea>
    </td>
  </tr>
	<tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答案：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7" colspan="3">
				<textarea name="item.answer" rows="5" cols="80">${item.answer}</textarea>
    </td>
  </tr>
	<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">类型：</td>		
			<td width="33%" align="left" bgcolor="#F7F7F7">
				<select name="item.type">
		    		<option value="1" <c:if test="${item.type eq 1}">selected</c:if>>例题</option>
		    		<option value="2" <c:if test="${item.type eq 2}">selected</c:if>>习题</option>
		    	</select>
   	</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">顺序号：</td>		
			<td width="33%" align="left" bgcolor="#F7F7F7">
				<input class="logininputmanage" type="text" name="item.orderNum" value="${item.orderNum }">
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