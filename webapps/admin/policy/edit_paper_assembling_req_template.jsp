<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script>
j(function(){
	var add = new GMAP().addHtml;
	
	j("a").each(function(){
		var html = j(this).html();
		if(html.indexOf("修改")!=-1){
			
		}
	})
	/*
	j("input[type=text],select").each(function(){
		var val = j(this).val();
		var jparent = j(this).parent()
		j(this).remove();
		add(jparent,val);
		
	})
	*/
	j("textarea").each(function(){
		 
		var val = j(this).text();
		var jparent = j(this).parent()
		j(this).remove();
		add(jparent,val);
	})
	j("input[type=radio]").each(function(){
		if(j(this).attr("checked")==true){
			j(this).parent().html("是");
		}
		j(this).remove();
	})
	var u = location.toString();
	/*if(u.indexOf("view_")!=-1){
		j("input[type=button][value~=保 存]").each(function(){
			var v = j(this).val();
			j(this).val(v.replace("保 存","修 改"));
			
		}) 
	}*/
})
</script>
</head>
<body>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 新增组卷条件模板</td>
	</tr>
</table>



<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <form action="../policy/paperAssemblingReqTemplate.jhtml" method="post"  lang="en">
<tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模块名称：<input type="hidden" name="atype" value="update"><input type="hidden" name="paperAssemblingReqTemplate.id" value="${paperAssemblingReqTemplate.id}"></td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="paperAssemblingReqTemplate.name" value="${paperAssemblingReqTemplate.name}"/>
    </td>
  </tr>  
	<tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
		<tr>
			<td ><input type="submit" class="btn_2k3" value="  保 存  " />
				&nbsp;&nbsp;
				<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
		</tr>
	</table></td>
  </tr></form>  
  </table> 
 

</body>
</html>
