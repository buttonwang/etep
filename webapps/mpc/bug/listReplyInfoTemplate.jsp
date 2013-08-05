<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../../admin/css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/DeleteAll.js"></script>
<script src="../js/all/ConfirmIF.js"></script>
<script src="../js/all/studyflow/list_process.js"></script>

</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：捉虫模板 &gt; 捉虫模板列表</td>
	</tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
	<%--<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
				<form action="process.jhtml" method="post" id="pageForm"><input type="hidden" class="logininputmanage"  name="atype" value="search"/>
				<tr>
					<td align="left" ><input type="button" value=" 新 建 " class="btn_2k3" onClick="${onClick_createNew_js}"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<select style="height:20px;" id="changeItemName" v="search_obj_name"  >
							<option value="p.para.name">流程名称：</option>
							<option value="p.para.categoryName">流程分类：</option>
							<option value="p.para.creator">创建人：</option>
							<option value="p.para.releaseStatus">流程状态：</option>
						</select>
						<input  id="search_obj_name" type="text" class="logininputmanage" size="15" name="p.para.name"/>
						&nbsp;&nbsp;
						<input type="submit" value="查询" class="btn_2k3"/>
						&nbsp;
						<input type="button" value="高级" onClick='javascript:j("#hiddenDiv").toggle();' class="btn_2k3"/>
					</td>
					 
				</tr>
				</form>
			</table></td>
	</tr>--%>
	<tr>
	<td>
<div id="hiddenDiv" style="display:none" ><form action="process.jhtml" method="post"><input type="hidden" class="logininputmanage"  name="atype" value="search"/>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" 
id="search2">
  <tr>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.name"/></td>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">分类： </td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="p.para.categoryName">
    		<option selected="selected">全部</option>
			<c:forEach items="${processCategoryLst}" var="p" varStatus="status">
      		<option>${p.name}</option>
			</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">创建人：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.creator"/></td>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态： </td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="p.para.releaseStatus" id="select3">
    		<option selected>全部</option>
      		<option>未发布</option>
      		<option>已发布</option>
      		<option>已作废</option>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">更新人：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.updator"/></td>
    <td width="15%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">  </td>
    <td width="35%" align="left" valign="top" bgcolor="#FFFFFF">
     
    </td>
  </tr>
  <tr bgcolor="#F7F7F7" height="50">
    <td align="center"  colspan="4">
    	<input type="submit" value="   查 询   " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="button" value="   取 消   " onClick='javascript:j("#hiddenDiv").hide();' class="btn_2k3"/>
    </td>
  </tr>
</table></form>
</div>
 	</td>
	</tr>
	<tr>
		<td><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
				<tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
					<td width="30">全选
						<input  type="checkbox"  t=top /></td>
					<td>捉虫模板名</td>
					<td>捉虫模板内容</td>
					<td>点数</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${replyInfoTemplates}" var="r" varStatus="status">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="checkbox" value="${r.id}" t="son" /></td>
						<td><a href="bug!showReplyInfoTemplate.jhtml?p.para.replyInfoTemplateId=${r.id}">${r.tempalteName}</a></td>
						<td>${r.replyContent}</td>
						<td>${r.replyPoint}</td>
						<td><a href="bug!editReplyInfoTemplate.jhtml?p.para.replyInfoTemplateId=${r.id}">修改</a></td>
					</tr>
				</c:forEach>
			</table></td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td align="left">&nbsp;</td>
					<td align="left"></td>
					<td align="right">&nbsp;</td>
				</tr>
			</table></td>
	</tr>
</table>
</body>
</html>
