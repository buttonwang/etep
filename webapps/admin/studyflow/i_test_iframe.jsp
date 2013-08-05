<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%
	request.setAttribute("iframeId","e_item");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<style>
body,div {
	padding:0px 0px 0px 0px;
	font-family:Arial, Helvetica, sans-serif;
	font-size:12px;
	color:#27b5e1;
	height:0px;
}
</style>
<script src="../js/m.js"></script>
<script src="../js/ParentIframeHeight.js"></script>
<script>
j(function(){
	/*不知为什么 body的默认高度是 15先减去*/
	new ParentIframeHeight("${iframeId}",(j("body").height()-15));
})
</script>
</head>
<body>sdfsdfsd<br />
<br />
<br />
<br />
<br />
<br />
SDf
s

fd

<br />
<br />
<br />
DF<br />
<br />
DF<br />
S<br />
DF<br />
S<br />
F<br />
S
</body>
</html>
