<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>  
<html>
<head>
<title>菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="MSHTML 5.00.3502.5390" name=Generator>
<script src="../js/jsframework.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var data={};
	data["-1_1"] = "text: 方法与技巧;";
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
	data['1_${usrg.subject.code}'] ='text:${usrg.subject.name};';
		<c:forEach items="${usrg.grades}" var="grade" >
	data['${usrg.subject.code}_${grade.code}'] ='text:${grade.name};url:mt!menu.jhtml?subject=${usrg.subject.code}&grade=${grade.code}';	
		</c:forEach>
</c:forEach>
	data["-1_2"] = "text: 返回上一级菜单;url:left.jsp;";
        Using("System.Web.UI.WebControls.MzTreeView");
        var a = new MzTreeView();
        a.dataSource = data;

        a.autoSort=false;
        //a.useCheckbox=true;
        a.canOperate=true;
        document.write(a.render());
        a.expandLevel(1);
</script>
</head>
<body >
</body>
</html>