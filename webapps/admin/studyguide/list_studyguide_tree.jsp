<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<html>
<head>
<title>学习指导</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../itembank/knowledgepoint/MzTreeView12.js" type="text/javascript"></script>
<script>
</script>
<style type="text/css">
body {font:normal 12px 宋体}
a.MzTreeview /* TreeView 链接的基本样式 */ { cursor: hand; color: #000080; margin-top: 5px; padding: 2 1 0 2; text-decoration: none; }
.MzTreeview a.select /* TreeView 链接被选中时的样式 */ { color: highlighttext; background-color: highlight; }
#kkk input {
vertical-align:middle;
}
.MzTreeViewRow {border:none;width:100%;padding:0px;margin:0px;border-collapse:collapse}
.MzTreeViewCell0 {border-bottom:1px solid #CCCCCC;padding:0px;margin:0px;width:80%;}
.MzTreeViewCell1 {border-bottom:1px solid #CCCCCC;border-left:1px solid #CCCCCC;width:20%;padding:0px;margin:0px;}
</style>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：学习指导 &gt; 学习指导 &gt; 学习指导列表</td>
  </tr>
</table>
<form action="itemStudyGuide!list.jhtml">
<input name="subjectCode" type="hidden" value="${subjectCode}">
<table width="100%" border="0" align="center" bgcolor="#E9F0F6">
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0">
	    <tr>
	      <td width="50%" align="left"    class="txt12blueh">学习指导</td>
          <td align="right"  class="txt12blue">
			  学级：<select name="gradeCode" id="gradeCode">
		  			<option value="">选择年级</option>
		  			<c:forEach items="${sessionScope.m_userSubjectGradeRole}" var="obj" varStatus="status">
		  				<c:if test="${subjectCode eq obj.subject.code}">
		  					<c:forEach items="${obj.grades}" var="grade" varStatus="s">
		  						<option value="${grade.code }" ${grade.code eq gradeCode ? 'selected' : ''}>${grade.name }</option>
		  					</c:forEach>
		  				</c:if>
		  			</c:forEach>
				</select>
           &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value=" 筛 选 " class="btn_2k3" /> &nbsp;&nbsp;&nbsp;&nbsp; 
		   <input type="button" value=" 新 增 "  class="btn_2k3" onClick="javascript: window.location.href='itemStudyGuide!add.jhtml?subjectCode=${subjectCode}&studyGuide.id=';"/>&nbsp;
           <input type="button" value="删除全部" class="btn_2k3" onClick="javascript: if (confirm('确定要删除吗？')) window.location.href='itemStudyGuide!deleteAll.jhtml?subjectCode=${subjectCode}&gradeCode=${gradeCode }';"/>
          </td>
	    </tr>
	  </table>
    </td>
  </tr>
</table>
<div id="treelist"></div>
	<script language="javascript" type="text/javascript">
	var MzTreeViewTH="<table class='MzTreeViewRow' width='100%'><tr height='30'><td class='MzTreeViewCell0'>";
	var MzTreeViewTD="\"</td><td class='MzTreeViewCell1' align='center'><a href='./itemStudyGuide!add.jhtml?subjectCode=${subjectCode}&studyGuide.id=\"+sid+\"'>新增</a>&nbsp;<a href='./itemStudyGuide!modifyBefore.jhtml?subject=${subjectCode}&studyGuide.id=\"+sid+\"'>修改</a>&nbsp;<a href='./itemStudyGuide!abandon.jhtml?studyGuide.id=\"+sid+\"'>废弃</a></td></tr></table>\"";
	window.tree = new MzTreeView("tree");
	tree.setIconPath("../images/images/"); //可用相对路径
	<c:forEach items="${studyGuideList }" var="item" varStatus="status">
		<c:choose>
		   <c:when test="${empty item.parent.id}">
				tree.N["0_${item.id}"] = "T:${item.code} ${item.name};url:itemStudyGuide!get.jhtml?id=${item.id};";
		   </c:when>
		   <c:otherwise>
		   		tree.N["${item.parent.id}_${item.id}"] = "T:${item.code} ${item.name};url:itemStudyGuide!get.jhtml?id=${item.id};";
		   </c:otherwise>
		</c:choose>
	</c:forEach>
	tree.setURL("#");
	tree.wordLine = false;
	tree.setTarget("main");
	document.getElementById("treelist").innerHTML=tree.toString();
	tree.expandAll();
	</script>
<table width="100%" height="100" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
  <tr>
    <td></td>
  </tr>
</table>

</form>
</body>
</html>