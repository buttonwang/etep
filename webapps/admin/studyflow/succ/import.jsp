<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.ambow.studyflow.domain.Node" %>
<%@ page import="com.ambow.studyflow.domain.ProcessDefinition" %>
<%@ page import="com.ambow.trainingengine.studyflow.service.ProcessAdminService" %>

<%
	 
	Node node = new Node();
	node.setName("node1");
	
	ProcessAdminService processAdminService = null;
	 
    Long pid =null;
	try{
		pid=Long.parseLong(request.getParameter("pid")) ;
	}catch(Exception e){
	}
	ProcessDefinition process = null;	
	 
	if(pid!=null&&processAdminService!=null){
		process =processAdminService.get(pid.longValue());;
	}
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
</body>
</html>
