<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <html>
    <head>
    </head>
    <%
    Object obj=com.ambow.studyflow.common.ServiceLocator.getService("processService");
    out.print("xx:"+obj);
    %>
    <body>
    	i am here. i can see you!
    	
    	<li>page from ${nextAct} 
    </body>
    </html>