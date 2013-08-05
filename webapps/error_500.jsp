<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%
	String contextPath = request.getContextPath();
	request.setAttribute("contextPath", contextPath);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keyword" content="教育,爆破学堂" />
    <title>系统服务繁忙</title>
    <style type="text/css">
      h1 {font-size: 14pt;color: #6293BB;}
    </style>
</head>
<body>
	    <div style="width:900px;margin:15px 15px 15px 15px;background:#FFF;padding:5px;overflow:hidden;">
      <table width="90%" border="0" align="center">
        <tr style="font-size:14pt;font-weight:bold;color:#6293BB;">
          <td align="center"></td>
          <td height="68"  align="left" >
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h1>系统服务繁忙</h1>
          </td>
        </tr>

        <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
          <td width="22%" align="right"><img src="<%=contextPath%>/ky/images/filenotfound.jpg" width="128" height="128" /></td>
          <td width="71%">
            <div style="padding:10px 10px;">
              <p>您访问的系统服务繁忙，请稍后再试！也可能是由于其它原因引起。 <br> 发生时间：<%=new Date()%></p>
            </div>
          </td>
        </tr>

        <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
          <td width="22%" align="right"></td>
          <td width="71%" align=center>
            <div style="padding:10px 10px">
              <p>评测训练引擎开发组 </p>
            </div>            
          </td>
        </tr>
      </table>
    </div>
	<div style="padding:10px 10px;">
            <table width="90%" border="0" align="center">
           	  <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
           	  	<td width="5%" align="right"> 名称:</td>
           	  	<td width="85%">${errorName}</td>
           	  </tr>
              <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
              	<td> 栈信息:</td>
              	<td>${errorStackTrace}</td>
              </tr>
              <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
              	<td> 位置:</td>
              	<td>${errorLocation}</td>
              </tr>
              <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
              	<td> 原因:</td>
              	<td>${errorCause}</td>
              </tr>
             <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
              	<td> 信息:</td>
              	<td>${errorMessage}</td>
             </tr>
             
             </table>
            </div>
</body>
</html>