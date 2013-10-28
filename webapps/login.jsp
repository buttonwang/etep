<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%
String userID=request.getParameter("userID");
String loginName=request.getParameter("loginName");
String userName=request.getParameter("userName");
String classCode=request.getParameter("classCode");
String moduleID=request.getParameter("moduleID");
String refID=request.getParameter("refID");
String gradeID=request.getParameter("gradeID");
//userName = java.net.URLEncoder.encode(userName, "UTF-8");
String url=request.getContextPath()
		+"/web/loginFlow.jhtml?userID="+userID
		+"&loginName="+loginName
		+"&userName="+userName
		+"&classCode="+classCode
		+"&moduleID="+moduleID
		+"&refID="+refID
		+"&gradeID="+gradeID;
System.out.println(url);
response.sendRedirect(url);
%>