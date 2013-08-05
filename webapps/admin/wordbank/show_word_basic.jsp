<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：词库 &gt; 基础 &gt; 查看单词</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">拼写：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordBasic.word}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">音标：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordBasic.phoneticSymbol}</td>
  </tr>
  <tr>  
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">常用搭配：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordBasic.commonUsage}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">词汇用法：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordBasic.wordUsage}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">联想记忆：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordBasic.associationMemory}</td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        <input type="hidden" name="wordBasic.id" value="${wordBasic.id}"/>
        <input type="button" value="  编 辑  " class="btn_2k3" onclick="javascript: window.location.href='wordBasic!edit.jhtml?id=${wordBasic.id}'"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="  取 消  " class="btn_2k3" onclick="history.back()"/>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</body>
</html>