<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>

<body>
<form method="post" action="importItem.jhtml" enctype="multipart/form-data">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 批量导入</td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr><td>
  <p>&nbsp;</p>
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
    <tr>
    	<td width="5%"/>
      	<td align="left">
      		文件名称：<input type="file" name="file" size="80" />&nbsp;&nbsp;
      	</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><b>${importInfo}</b></td>
	</tr>
	<tr>
   		<td width="5%" />
      	<td align="left" ><br/>
      		<input type="submit" value=" 导 入  " class="btn_2k3"/>&nbsp;&nbsp;
      		<input type="button" value=" 返 回  " class="btn_2k3" onclick="javascript:history.back()">
      	</td>  
    </tr>
  </table>
  <p>&nbsp;</p>
  </td></tr>
</table>
</form>

<form method="post" action="importItem!dirImport.jhtml" >
<dir style="display: none1">
	目录名称：<input type="text" name="dirName" value="" size="80" />&nbsp;&nbsp;<br>
	<input type="submit" value=" 导 入  " class="btn_2k3" />&nbsp;&nbsp;<br>
	<h><b>${batchimportInfo}</b></h><br>
	<h><b>${batchImportError}</b></h>
</dir>
</form>
</body>
</html>
