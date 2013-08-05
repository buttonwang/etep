<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
// -->
</script>
</head>
<frameset cols="200,12,*" rows="*" id="main" frameborder="no" border="0"> 
  <frame src="left_knowledge.jsp" name="left" frameborder="no" scrolling="auto" noresize>
  <frame src="left_controlbar.jsp" name="left_controlbar" scrolling="no">
  <frame src="right.jsp" name="right" frameborder="no" noresize id="right">
</frameset>
<noframes> 
<body bgcolor="#FFFFFF" text="#000000">
</body>
</noframes> 
</html>
