<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%	request.setAttribute("nodeId",request.getParameter("nodeId"));%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script>
j(function(){
	ValidateForm({
		"fid":"add_paper_assembling_requirements_form"
		,"vs":[
			{n:"paperAssemblingReqTemplate.name",sn:"模块名称"}
			,{n:"paperAssemblingReqTemplate.name",sn:"模块名称"}
			,{n:"paperAssemblingRequirements.year",sn:"试题年份"}
			,{n:"paperAssemblingRequirements.originalPaperCode",sn:"原始套卷"}
			,{n:"paperAssemblingRequirements.difficultyValue",sn:"试题难易度"}
			,{n:"paperAssemblingRequirements.validityValue",sn:"试题效度"}
			,{n:"paperAssemblingRequirements.amount",sn:"题数"}
		]
	});
})
</script>
</head>
<body>
<form action="paperAssemblingRequirements.jhtml" id="add_paper_assembling_requirements_form" method="post" >
<input type="hidden" name="atype" value="update">
<input type="hidden" name="paperAssemblingRequirements.asfNode.id" value="${nodeId}">
	<table id="table18" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">地区：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="paperAssemblingRequirements.regionCode">
					<option value="c1" ${paperAssemblingRequirements.regionCode=="c1"?"selected":""}> 全国I</option>
					<option value="c" ${paperAssemblingRequirements.regionCode=="c"?"selected":""} >全国II</option>
					<option value="b" ${paperAssemblingRequirements.regionCode=="b"?"selected":""} >北京</option>
					<option value="s" ${paperAssemblingRequirements.regionCode=="s"?"selected":""} >上海</option>
					<option value="z" ${paperAssemblingRequirements.regionCode=="z"?"selected":""} >浙江</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="paperAssemblingRequirements.subjectCode">
					<option value="y">语文</option>
					<option value="m">数学</option>
					<option value="e" ${paperAssemblingRequirements.subjectCode=="e"?"selected":""}>英语</option>
					<option value="p">物理</option>
					<option value="c">化学</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="paperAssemblingRequirements.itemTypeCode">
					<option value="r">阅读理解</option>
					<option value="e" ${paperAssemblingRequirements.subjectCode=="e"?"selected":""}>完形填空</option>
					<option value="w" >写作</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="paperAssemblingRequirements.year"  value="${paperAssemblingRequirements.year}"  />
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="paperAssemblingRequirements.source">
					<option value="t">真题</option>
					<option value="v" ${paperAssemblingRequirements.source=="v"?"selected":""}>模拟</option>
					<option value="h">自编</option>
					<option value="p">专项</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="paperAssemblingRequirements.originalPaperCode" value="${paperAssemblingRequirements.originalPaperCode}"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难易度：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="paperAssemblingRequirements.difficultyValue" value="${paperAssemblingRequirements.difficultyValue}"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="paperAssemblingRequirements.validityValue" value="${paperAssemblingRequirements.validityValue}"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="paperAssemblingRequirements.amount" value="${paperAssemblingRequirements.amount}"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
			<td width="33%" align="left" bgcolor="#FFFFFF"></td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
				<tr>
					<td ><input type="submit" class="btn_2k3" value="  保 存  " />
						&nbsp;&nbsp;
						<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
<%if(false){%>
<table id="table17" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#E3E3E3">
	<tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
		<td>序号</td>
		<td>地区</td>
		<td>学科</td>
		<td>题型</td>
		<td>试题年份</td>
		<td>试题来源</td>
		<td>原始套卷</td>
		<td>试题难度</td>
		<td>试题效度</td>
		<td>题数</td>
		<td>操作</td>
	</tr>
	<tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
		<td>序号</td>
		<td><select name="papercategory2" class="logininputmanage" >
				<option>全国I</option>
				<option>全国II</option>
				<option>北京</option>
				<option>上海</option>
				<option>浙江</option>
			</select></td>
		<td><select name="papercategory " class="logininputmanage" >
				<option>语文</option>
				<option>数学</option>
				<option>英语</option>
				<option>物理</option>
				<option>化学</option>
			</select></td>
		<td><select name="papercategory3" class="logininputmanage" >
				<option>完形填空</option>
				<option>阅读理解</option>
				<option>写作</option>
			</select></td>
		<td><input class="logininputmanage" type="text" name="textfield4" id="textfield4"/></td>
		<td><select name="papercategory4">
				<option>真题</option>
				<option>模拟</option>
				<option>自编</option>
				<option>专项</option>
			</select></td>
		<td><input class="logininputmanage" type="text" name="textfield3" id="textfield3"/></td>
		<td><input class="logininputmanage" type="text" name="textfield" id="textfield"/></td>
		<td><input class="logininputmanage" type="text" name="textfield2" id="textfield2"/></td>
		<td><input class="logininputmanage" type="text" name="textfield9" id="textfield9"/></td>
		<td><input type="button" value="  保 存  " class="btn_2k3"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="  取 消  " class="btn_2k3" onclick="hide('table18');"/></td>
	</tr>
</table>
<%}%>
</body>
</html>
