<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%
	List<Map<String,Object>> recordList = null;
	if (request.getAttribute("list") != null)
		recordList = (List<Map<String,Object>>) request.getAttribute("list");
		
	System.out.println("size="+recordList.size());

%>
<html>
<head>
<title>试题对比</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：试题查重&gt;&gt;试题对比</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>&nbsp;</td>
	</tr>
<tr>
<td>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	    <!-- 显示记录列表 -->
<%
	if(recordList.size()>=2){
		Map<String,Object> item1Map = recordList.get(0);
		Map<String,Object> item2Map = recordList.get(1);
%>
		  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
			<td width="10%">编号</td>
			<td width="45%"><a href="item!show.jhtml?id=<%=item1Map.get("id")%>"><%=item1Map.get("code")%></a></td>
			<td width="45%"><a href="item!show.jhtml?id=<%=item2Map.get("id")%>"><%=item2Map.get("code")%></a></td>
		  </tr>
	     <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
	        <td>内容</td>
		    <td><%=item1Map.get("content")%></td>
			<td><%=item2Map.get("content")%></td>
	     </tr>

<%
	}
%>
</table>
</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
</table>
</body>
</html>