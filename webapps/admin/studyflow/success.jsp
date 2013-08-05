<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<title>成功消息页面</title>
<script src="../js/m.js"></script>
<script >
var level = -1;
<%	String rtype = (String)request.getAttribute("rtype");
	if("add".equals(rtype)||"update".equals(rtype)){/*"delete".equals(rtype)||"deleteBatch".equals(rtype)||*/
%>
level =-1;
<%}%>
j(function(){
	j(document).click(function(){
		window.parent.history.go(level);
		window.parent.location.reload();
	})
	setTimeout('j(document).trigger("click")',500);
})
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location"> </td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
     
    <td width="33%" align="center" valign="top" bgcolor="#FFFFFF">
		 <%	 
				if("add".equals(rtype)||"update".equals(rtype)){/*"delete".equals(rtype)||"deleteBatch".equals(rtype)||*/
			%>
			<span style="cursor:hand"  onclick="javascript:window.history.go(-2);">${atype=="update"?"更新":""}操作成功。</span> 
			<%}else{%>
			<span style="cursor:hand"  onclick="javascript:window.history.back( );">${atype=="update"?"更新":""}操作成功。</span> 
			<%}%>
    </td>
  </tr>
</table>
</body>
</html>
