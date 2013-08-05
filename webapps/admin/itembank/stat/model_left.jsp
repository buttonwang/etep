<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List,java.util.Set"%>
<%@page import="com.ambow.trainingengine.itembank.domain.KnowledgePoint"%>
<%@page import="com.ambow.trainingengine.itembank.domain.Model"%>
<%
	List<KnowledgePoint> nodeList = (List<KnowledgePoint>) request.getAttribute("list");
	List<Model> modelList = (List<Model>) request.getAttribute("modelList");
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
	data["-1_1"] = "text: 模块统计;";
<%
	for(int i=0;i<modelList.size();i++){
		Model model = modelList.get(i);
		
%>
	data['1_<%=model.getId()%>'] = 'text: <%=model.getName()%>; url:itemStat!modelStat.jhtml?model=<%=model.getId()%>&subject=<%=subject%>&grade=<%=grade%>;target:right';
<%
		Set<KnowledgePoint> set =  model.getKnowledgePoints();
		Object[] knowledgePoints = set.toArray();
		for(int ii=0;ii<knowledgePoints.length;ii++){
			KnowledgePoint knowledgePoint = (KnowledgePoint)knowledgePoints[ii];
%>
	data['<%=model.getId()%>_<%=model.getId()%><%=knowledgePoint.getParentKnowledgePoint().getCode()%>'] = 'text: <%=knowledgePoint.getParentKnowledgePoint().getName()%>; url:itemStat.jhtml?point=<%=knowledgePoint.getParentKnowledgePoint().getCode()%>&model=<%=model.getId()%>&subject=<%=subject%>&grade=<%=grade%>;target:right';
	data['<%=model.getId()%><%=knowledgePoint.getParentKnowledgePoint().getCode()%>_<%=knowledgePoint.getCode()%>'] = 'text: <%=knowledgePoint.getName()%>; url:itemStat.jhtml?point=<%=knowledgePoint.getCode()%>&subject=<%=subject%>&grade=<%=grade%>;target:right';
<%
		}
	}
%>
	data["-1_2"] = "text: 返回上一级菜单;url:stat/model_left_begin.jsp;";
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