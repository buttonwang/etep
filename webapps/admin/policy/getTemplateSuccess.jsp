<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script>
j(function(){
	j("a[opt=confirm]").click(function(e,map){	
		if(j(this).html()=="套用"){
			if(confirm("注意: 套用模板时首先会清除现有的策略，确定继续吗？")){
			}else{
				stopBD(e,1,2);
			}
		}
	})
})

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">请从下列列表中选择一个 &nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="返回" onClick="javascript:window.history.back()"/></td>
  </tr>
</table>
 
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
 <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" >
    <td width="20%" >策略模板名称</td>
    <td width="20%" >操作</td>
	<td width="60%" > </td>
  </tr>
<c:forEach items="${v.page.result}" var="item" varStatus="itemStatus">
          <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
            <td><a href="${v.showAction}.jhtml?id=${item.id}">${item.name}</a></td>
            <td><a href="${v.showAction}.jhtml?id=${item.id}">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../policy/template.jhtml?atype=set&tType=${v.tType}&nid=${v.nid}&tid=${item.id}" opt="confirm">套用</a></td>
			 <td > </td>
          </tr>
</c:forEach>
</table>
</body>
</html>
