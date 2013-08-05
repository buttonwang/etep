<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String baseURL = "../";
	String theme = "policy/";
	String itemURL = baseURL+theme;
	String showUrl= itemURL+"paperAssemblingReqTemplate.jhtml?atype=show&";
	String editUrl = itemURL+"paperAssemblingReqTemplate.jhtml?atype=edit&";
	String deleteUrl = itemURL+"paperAssemblingReqTemplate.jhtml?atype=delete&";
	String deleteBatchUrl = itemURL+"paperAssemblingReqTemplate.jhtml?atype=deleteBatch&ids=";
	String addUrl = itemURL+"add_paper_assembling_req_template.jsp";
	
	request.setAttribute("addUrl",addUrl);
	request.setAttribute("showUrl",showUrl);
	request.setAttribute("editUrl",editUrl);
	request.setAttribute("deleteUrl",deleteUrl);
	request.setAttribute("deleteBatchUrl",deleteBatchUrl);
	request.setAttribute("onClick_createNew_js","javascript:window.location.href='"+addUrl+"';");			
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/DeleteAll.js"></script>
<script src="../js/all/DeleteConfirm.js"></script>
<script>
j(function(){
	new DeleteConfirm();
	DeleteAll();
})
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：策略维护 &gt;<a href="add_assembling_paper_req_template.html">组卷条件模板</a><a href="add_module_eval_policy_template.html"> </a> 维护</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="6" bgcolor="#E9F0F6">
	<tr>
		<td width="55%" align="left" ><form action="../policy/template.jhtml?atype=search" method="post">
				<input type="hidden" name="searchType" value="paper_assembling_req_template"/>
				 <input type="button" value=" 新 建 "  onClick="javascript: window.location.href='${addUrl}' " class="btn_2k3" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				模板名称：<input name="nameForSearch" type="text" class="logininputmanage" size="15"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询" class="btn_2k3"/>
			</form></td>
		<td width="45%" align="right"><jsp:include page="../policy/page.jsp"/>
		</td>
	</tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
   
<tr>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
	<td width="48">全选<input  type="checkbox" t=top id="checkall"/></td>
    <td width="410">策略模板名称</td>
    <td width="931">操作</td>
  </tr>
<c:forEach items="${page.result}" var="item" varStatus="itemStatus">
          <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
            <td><input type="checkbox" name="ids" value="${item.id}"/></td>
            <td><a href="${showUrl}id=${item.id}">${item.name}</a></td>
            <td><a href="${editUrl}id=${item.id}">修改</a> <a href="${deleteUrl}id=${item.id}">删除</a> </td>
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
      		<input type="button" value="批量删除" class="btn_2k3" onClick="batchdelete('${deleteBatchUrl}')"/></td>
      	<td align="left"></td>
        <td align="right"><jsp:include page="../policy/page.jsp"/></td>
      </tr>
    </table>
	</td>
</tr>  
</table>

</body>
</html>
