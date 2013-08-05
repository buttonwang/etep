<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<head>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js" type="text/javascript"></script>
<script src="../js/getCodesNames.js"></script>
<script src="../policy/getCodeName/MzTreeView12.js"></script>
<script src="../js/FloatDiv.js"></script>
<script type="text/javascript">
function setValue() {
	var temp =document.getElementsByName("sel");
	var ids = "";
	var names = "";
	var j = 0;
	for(var i=0;i<temp.length;i++){
		if (temp[i].checked) {
			var a = temp[i].value.split(",");
			//if(a[2].substring(5) != '00') {
			ids += a[0]+",";
			names += a[1]+",";
		}
	}
	window.opener.document.getElementById('studyGuideNames').value = names.substring(0,names.length-1);
	window.opener.document.getElementById('studyGuideCodes').value = ids.substring(0,ids.length-1);
    window.close();
}
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
</html>
<body  onload="FloatDiv('opt',100,100)">
<div id="treelist"></div>
	<script language="javascript" type="text/javascript">
		//var a = window.opener.document.getElementById('knowledgePointCodes').value.split(",");
		var MzTreeViewTH="<table class='MzTreeViewRow' width='100%'><tr height='30'><td class='MzTreeViewCell0'>";
		var MzTreeViewTD="\"</td></tr></table>\"";
		window.tree = new MzTreeView("tree");
		tree.setIconPath("../images/images/"); //可用相对路径
		<c:forEach items="${studyGuides }" var="item" varStatus="status">
		    <c:set var="f" value="false"/>
		    <c:forEach items="${codes}" var="i">
		    	<c:if test="${i eq item.id}">
		    		<c:set var="f" value="true"/>
		    	</c:if>
		    </c:forEach>
			<c:choose>
			   <c:when test="${item.parent == null}">
					tree.N["0_${item.id},${item.name},${item.code}"] = "ctrl:sel;checked: ${f?1:0};T:${item.code}  ${item.name};url: ../studyguide/itemStudyGuide!get.jhtml?id=${item.id};";
			   </c:when>
			   <c:otherwise>
			   		tree.N["${item.parent.id},${item.parent.name},${item.parent.code}_${item.id},${item.name},${item.code}"] = "ctrl:sel;checked: ${f?1:0};T:${item.code}  ${item.name};url: ../studyguide/itemStudyGuide!get.jhtml?id=${item.id};";
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
<div id="opt" style="text-align:center">
<input type="button" class="btn_2k3" value="确 定" id="save" onclick="javascript: setValue();">&nbsp;&nbsp;
<input type="button" class="btn_2k3" value="取 消" onClick="window.close();">
</div>
</body>