<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*,com.ambow.trainingengine.studyflow.util.ProcessNodeJson" errorPage=""%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	ProcessNodeJson toJson = new ProcessNodeJson();
	String baseURL = "../";
	String theme = "policy/";
	
	String action_name = "";
	String add_page ="";
	String formName ="";
	String prefixx="";
	String searchType = (String)request.getAttribute("searchType");
		if("training_policy_template".equals(searchType)){
			action_name = "trainingPolicyTemplate.jhtml";
			add_page = "add_training_policy_template.jsp";
			formName = "trainingPolicy_edit";
			prefixx = "trainingPolicy";
		}else if("assemble_paperPolicy_template".equals(searchType)){
			action_name = "assemblePaperPolicyTemplate.jhtml";			
			add_page = "add_assemble_paper_policy_template.jsp";
			
			formName = "paperAssemblingPolicy_edit";
			prefixx = "paperAssemblingPolicy";
		}else if("paper_assembling_req_template".equals(searchType)){
			action_name = "paperAssemblingReqTemplate.jhtml";
			add_page = "add_paper_assembling_req_template.jsp";			
			
		}else if("PRACTICE".equals(searchType)){
			action_name = "trainingUnitPolicyTemplate.jhtml";
			add_page = "add_training_unit_policy_template.jsp";
			formName = "trainingUnitNodePolicy_edit";
			prefixx = "trainingUnitNodePolicy";
		}else if("EVALUATE".equals(searchType)){
			action_name = "moduleEvalPolicyTemplate.jhtml";
			add_page = "add_module_eval_policy_template.jsp";
		}else if("PHASETEST".equals(searchType)){
			action_name = "phaseTestPolicyTemplate.jhtml";
			add_page = "add_phase_test_policy_template.jsp";
			formName = "trainingUnitNodePolicy_edit";
			prefixx = "trainingUnitNodePolicy";
		}else if("UNITTEST".equals(searchType)){
			action_name = "unitTestPolicyTemplate.jhtml";
			add_page = "add_unit_test_policy_template.jsp";
			formName = "unitTestNodePolicy_edit";
			prefixx = "unitTestNodePolicy";
		}

	String actionUrl=baseURL+theme+action_name+"?atype=";
	String showUrl= actionUrl+"show&";
	String editUrl = actionUrl+"edit&";
	String deleteUrl = actionUrl+"delete&";
	String deleteBatchUrl = actionUrl+"deleteBatch&ids=";
	
	String itemURL = baseURL+theme;
	String addUrl = itemURL+add_page;
	
	request.setAttribute("addUrl",addUrl);
	request.setAttribute("showUrl",showUrl);
	request.setAttribute("editUrl",editUrl);
	request.setAttribute("deleteUrl",deleteUrl);
	request.setAttribute("deleteBatchUrl",deleteBatchUrl);
	request.setAttribute("formName",formName);
	request.setAttribute("prefix",prefixx);
	List sList = (List)request.getAttribute("searchList");
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script>
j(function(){
 	j("#selectTemplate").click(function(){
		var selectTemplateId;
		var varStr = "";
		j("input[name=ids]").each(function(){
			if(j(this).attr("checked")==true){
				selectTemplateId = j(this).val();
				varStr = j("#" +j(this).attr("vid")).text();
			}
		}); 
		if(selectTemplateId!=undefined){
			if("${searchType}"=="PHASETEST"||"${searchType}"=="paper_assembling_req_template"){
				window.location.href='../policy/template.jhtml?atype=set&tType=${searchType}&nid=${nid}&tid='+selectTemplateId;
			}else{
				if(varStr!=undefined){
					window.opener.setForm ("${formName}","${prefix}",varStr);
					window.close();
				}
			}
		}else{
			alert("请先选择一模板");
		};
	})
})
</script>
</head>
<body>
<div style="display:none">
<%	
	if(sList!=null&&sList.size()>0){
		int i=0;
		for (Iterator iterator = sList.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
%>		
	<textarea cols="60" id="data<%=i++%>"><%=toJson.getStr(obj)%></textarea>
<%		}
	}
%>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：策略维护 &gt;<a href="add_unit_test_policy_template.html">单元测试节点策略模板</a> 维护</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="6">
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
	<tr>
		<td align="left" ></td>
		<td align="right"></td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="6" bgcolor="#E9F0F6">
	<form action="../policy/template.jhtml?atype=search" method="post"><tr>
		<td width="55%" align="left" >
				<input type="hidden" name="searchType" value="${searchType}"/>
				<input type="hidden" name="p.para.to" value="${to}"/>
				<input type="hidden" name="nid" value="${nid}">
				模板名称：
				<input name="nameForSearch" type="text" class="logininputmanage"  value="${nameForSearch}" size="15"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询" class="btn_2k3"/>
			</td>
		<td width="45%" align="right"><jsp:include page="../policy/page.jsp"/>
		</td>
	</tr></form>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
	<tr>
		<td><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
				<tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
					<td width="15%">选择模板</td>
					<td width="85%">策略模板名称</td>
				</tr>
				<% if(((List)request.getAttribute("searchList")).size()==0){%>
				<tr align="left" bgcolor="#FFFFFF" class="linkblueor12">
					<td></td>
					<td>没有数据可显示</td>
					 
				</tr>
				<%}else{%>
			 
				<c:forEach items="${ searchList}" var="item" varStatus="itemStatus">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="radio" name="ids" value="${item.id}" vid="data${itemStatus.index}"/></td>
						<td><a href="${showUrl}id=${item.id}" >${item.name}</a> </td>
					</tr>
				</c:forEach>
				<%}%>
			</table></td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="6">
				<tr>
					<td align="left"><input type="button" id="selectTemplate" value="使用模板" class="btn_2k3" /></td>
					<td align="left"></td>
					<td align="right"><jsp:include page="../policy/page.jsp"/></td>
				</tr>
			</table></td>
	</tr>
</table>

</body>
</html>
