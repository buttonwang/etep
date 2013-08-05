<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>试题审校</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<frameset rows="70,*" framespacing="0" frameborder="no" border="0">
  <frame src="${basePath}/admin/top_revise.jsp" frameborder="no" scrolling="no">
  <frame src="${basePath}/admin/itembank/itemRevise!toQueryTeacher.jhtml" name="main" id="main">
</frameset>
<noframes><body>

</body></noframes>
</html>
