<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.ambow.trainingengine.itembank.domain.ItemType"%>
<%
	List<ItemType> nodeList = (List<ItemType>) request.getAttribute("list");
	String subject = (String)request.getAttribute("subject");
	String grade = (String)request.getAttribute("grade");
	String ctx = request.getContextPath();
%>
<html>
<head>
<title>菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="MSHTML 5.00.3502.5390" name=Generator>
<script src="<%=ctx%>/admin/js/jsframework.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var data={};
	data["-1_1"] = "text: 试题查重;url:duplicate!query.jhtml?subject=<%=subject%>&grade=<%=grade%>;target:right";
<%
	for(int i=0;i<nodeList.size();i++){
		ItemType itemType = nodeList.get(i);
%>
	data['1_<%=itemType.getCode()%>'] = 'text: <%=itemType.getName()%>; url:duplicate!query.jhtml?subject=<%=subject%>&type=<%=itemType.getCode()%>;target:right';
<%
		}
%>

	data["-1_2"] = "text: 返回上一级菜单;url:check/left_begin.jsp;";
        Using("System.Web.UI.WebControls.MzTreeView");
        var a = new MzTreeView();
        a.dataSource = data;

        a.autoSort=false;
        //a.useCheckbox=true;
        a.canOperate=true;
        document.write(a.render());
        a.expandLevel(1);
</script>
</head>
<body >
</body>
</html>