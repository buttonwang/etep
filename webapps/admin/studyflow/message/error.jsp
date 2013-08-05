<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<link href="../../css/admin.css" rel="stylesheet" type="text/css">
<title> </title>
<script src="../js/m.js"></script>
<script>
j(function(){
	j("span").click(function(){
		 history.back();
	})
	setTimeout('j(document).trigger("click")',5000);
})
</script>
<style type="text/css">
<!--
.style6 {font-size: 16px}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">&nbsp;&nbsp;提示信息</td>
  </tr>
</table> 
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td height="120" width="33%" align="center" valign="middle" bgcolor="#FFFFFF">
		 <span class="txt14orange">
			<c:forEach items="${errorList}" var="item" varStatus="status">
                ${item}&nbsp;&nbsp;         
            </c:forEach>
            &nbsp;&nbsp; 点击返回   
         </span>             
     </td>
  </tr>
</table>
</body>
</html>
