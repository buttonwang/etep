<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%if(request.getParameter("t")!=null){%>
<input type="radio" name="itemVO.courseVersion"  ${item.courseVersion==0?"checked":""} value="0" />新旧版本适用&nbsp; 
<input type="radio" name="itemVO.courseVersion"  ${item.courseVersion==1?"checked":""} value="1" />新版本适用&nbsp;
<input type="radio" name="itemVO.courseVersion"  ${item.courseVersion==2?"checked":""} value="2" />旧版本适用
<%}else{%>
${item.courseVersion==0?"新旧版本适用":""}${item.courseVersion==1?"新版本适用":""}${item.courseVersion==2?"旧版本适用":""}
<%}%>
