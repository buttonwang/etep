<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../admin/css/admin.css" rel="stylesheet" type="text/css">
<script src="js/m.js"></script>
<script>
$(function(){
	$("input[@t=s]").click(function(){
		var sChecbox = "input[v="+$(this).attr("v")+"_grade]";
		$(sChecbox).attr("checked",true);	
	})
	$("input[@t=c]").click(function(){
		var sChecbox = "input[v="+$(this).attr("v")+"_grade]";
		$(sChecbox).attr("checked",false);	
	})
	$("input").click(function(){ 
		$("#sub").show();
	})
	
	
	$("form").submit(function(){
		var data = "[";
		$("tr[v!=]").each(function(j){			
			var str = "";
			var itemCode = $(this).attr("v");
			$("input[@v="+itemCode+"_grade]").each(function(i){
				if($(this).attr("checked")==true){
					if(i>0){
						str+=",";
					}
					str += '{"subjectCode":"'+itemCode+ '"'+',"roleId":"'+${rid}+'","gradeCode":"'+$(this).attr("value")+'"}';
				}
			})
			if(j>0&&str!=""){
				data+=",";
			}
			if(str!=""){
				data+=str;
			}
		})
		data+="]";
		data = data.replace(new RegExp(",,","g"),",").replace("[,{","[{");
		var formaction = 	$(this).attr("action")||"";
		formaction.indexOf("?")==-1?formaction+="?":formaction+="&"	;
		$("input[name=roles]").attr("value",data)
		return true;
	})
})
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：角色 &gt;年级学科管理</td>
	</tr>
</table>
<form action="roleSbujectGradeManager.jhtml" method="post" >
	<input type="hidden" name="rid"  value="${rid}">
	<input type="hidden" name="roles" >
	
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" valign="top" bgcolor="#F7F7F7"  class="txt12blue">&nbsp;&nbsp;学科 </td>
			<td  bgcolor="#F7F7F7"  class="txt12blue">&nbsp;&nbsp;操作 </td>
			<td  bgcolor="#F7F7F7"  class="txt12blue">年级</td>
		</tr>
		<c:forEach items="${subjectAll}" var="item" varStatus="itemStatus">
			<tr v="${item.code}">
				<td  bgcolor="#FFFFFF"> 
					${item.name}</td >
					<td bgcolor="#FFFFFF"> <input type="button" value="全部" t="s" v="${item.code}" >
					&nbsp;&nbsp;
					<input type="button" value="撤销全部" t="c" v="${item.code}"> </td>
				<td  bgcolor="#FFFFFF" name="grades">
					<c:forEach items="${gradeAll}" var="grade">
					<input type="checkbox" value="${grade.code}" 
					<c:forEach items="${rolesList}" var="roles"> 
					 <c:if test="${(grade.code == roles.rsg_pk.gradeCode) && item.code == roles.rsg_pk.subjectCode}"> checked=true </c:if>
					</c:forEach>	  
					v='${item.code}_grade'>
						${grade.name}						 
					</c:forEach>
					

				</td>				
			</tr>
		</c:forEach>
		<tr id="sub" style="display:none">
			<td height="70" colspan="3" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
					<tr>
						<td > <input type="submit" class="btn_2k3" value="  保 存  " />
							&nbsp;&nbsp;
							<input type="button" opt=back  value="  取 消  " class="btn_2k3"/> </td>
					</tr>
				</table></td>
		</tr>
	</table>
</form>
</body>
</html>

