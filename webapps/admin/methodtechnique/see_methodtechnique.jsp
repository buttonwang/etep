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
<script language="javascript" src="../js/admin.js" ></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
	function audio(){
		if(confirm('是否确认废弃该学习指导?')){
			window.location.href='${pageContext.request.contextPath}/admin/studyguide/itemStudyGuide!abandon.jhtml?studyGuide.id=${studyGuide.id}&subject=${subject}&grade=${grade}&code=${code}'
			alert('废弃操作成功！');
			};
	}
</script>
</head>
<%System.out.println("----enter see_methodtechnique.jsp"); %>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：学习指导 &gt; 查看学习指导  </td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#F7F7F7">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
    <td width="33%" align="left" bgcolor="#F7F7F7" class="txt12blue">
    	<a href="/resource/studyguide/${studyGuide.importFile}" target="black">${studyGuide.importFile}</a>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
    	<c:choose>
    		<c:when test="${studyGuide.status == 0}">未审核</c:when>
    		<c:when test="${studyGuide.status == 1}">已审核</c:when>
    		<c:when test="${studyGuide.status == -1}">已废弃</c:when>
    	</c:choose>
    </td>
  </tr>
  <tr>
  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点：</td>
    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
    	${code}
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
    	${studyGuide.content}
    </td>
  </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#F7F7F7">
    <table width="121" border="0">
      <tr> 
        <td>
        	<input type="button" value="  审 核  " class="btn_2k3" <c:if test="${studyGuide==null}">disabled</c:if> onclick="javascript: window.location.href='${pageContext.request.contextPath}/admin/studyguide/itemStudyGuide!verify.jhtml?studyGuide.id=${studyGuide.id}&subject=${subject}&grade=${grade}&code=${code}';alert('审核已通过！');"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" value="  废 弃  " class="btn_2k3" <c:if test="${studyGuide==null}">disabled</c:if> onclick="audio()"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" value="  修 改  " class="btn_2k3" <c:if test="${studyGuide==null}">disabled</c:if> onclick="javascript: window.location.href='${pageContext.request.contextPath}/admin/studyguide/itemStudyGuide!modifyBefore.jhtml?studyGuide.id=${studyGuide.id}&subject=${subject}&grade=${grade}';"/>&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</body>
</html>