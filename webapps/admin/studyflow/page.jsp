<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<form  id="pageForm" method="post" action="../studyflow/${actionName}.jhtml?atype=list">
<ambow:pagination actionName=""  total="${page.totalPageCount}" num="${page.currentPageNo}"  otherParams=""/>
</form>

 