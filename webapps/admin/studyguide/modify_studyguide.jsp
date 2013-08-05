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
		replacTextArea("studyGuide.content");
	}

	function doit(){
		//if(document.getElementById('studyGuide.code').value==''){
		//	alert('请填写学习指导编码！');
		//	return;
		//}
		//if(document.getElementById('studyGuide.name').value==''){
		//	alert('请填写学习指导名称！');
		//	return;
		//}
		//if(document.getElementById('studyGuide.content').value==''){
		//	alert('请填写学习指导内容！');
		//	return;
		//}
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
<form id="submitForm" name="submitForm" action="itemStudyGuide!save.jhtml" method="post">
<input type="hidden" name="studyGuide.id" value="${studyGuide.id}">
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#F7F7F7">
  <tr>
  	<td align="right" valign="top" bgcolor="#F7F7F7" colspan="4"><a href="itemStudyGuide!editParagraph.jhtml?studyGuide.id=${studyGuide.id }&paragraph.id=">增加段落</a></td>
  </tr>
  <tr>
  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">编码：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
		<input class="logininputmanage" type="text" name="studyGuide.code" value="${studyGuide.code}">
	</td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">名称：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    	<input class="logininputmanage" type="text" name="studyGuide.name" value="${studyGuide.name}">
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    	<a href="/resource/studyguide/${studyGuide.importFile}" target="black">${studyGuide.importFile}</a>
    </td>
  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
	    <select name="studyGuide.status">
	    	<option value="0" <c:if test="${studyGuide.status == 0}">selected</c:if>>未审核</option>
	    	<option value="1" <c:if test="${studyGuide.status == 1}">selected</c:if>>已审核</option>
	    	<option value="-1" <c:if test="${studyGuide.status == -1}">selected</c:if>>已废弃</option>
	    </select>
    </td>
  </tr>
  <tr>
  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    	<select name="studyGuide.parent.id">
    		<option value="">无父节点</option>
    		<c:forEach items="${sgList}" var="item" varStatus="state">
    			<option value="${item.id }" <c:if test="${item.id eq studyGuide.parent.id}">selected</c:if> >${item.level}${item.name }</option>
    		</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    	<select name="studyGuide.subject.code">
    		<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="i" >
				<option value="${i.subject.code }" <c:if test="${i.subject.code eq studyGuide.subject.code}">selected</c:if> >${i.subject.name}</option>
			</c:forEach>
    	</select>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">年级：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    	<select name="studyGuide.grade.code">
    		<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="i" >
    			<c:if test="${i.subject.code eq studyGuide.subject.code}">
	    			<c:forEach items="${i.grades}" var="grade" >
						<option value="${grade.code }" <c:if test="${grade.code eq studyGuide.grade.code}">selected</c:if> >${grade.name}</option>
	    			</c:forEach>
    			</c:if>
			</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建人：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    	${studyGuide.creater.username}
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7">
    <fmt:formatDate value="${studyGuide.createdTime}" pattern="yyyy-MM-dd"/>
    </td>
  </tr>
  <tr>
  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">修改人：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
    	${studyGuide.updater.username}
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">修改时间：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
    	 <fmt:formatDate value="${studyGuide.updatedTime}" pattern="yyyy-MM-dd"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习指导内容：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7" colspan="3">
    	<textarea name="studyGuide.content" rows="8" cols="80">${studyGuide.content}</textarea>
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