<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/common.js"></script>
</head>

<body>
<form method="post" action="wordExtension!save.jhtml">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：词库 &gt; 基础 &gt; 编辑单词扩展</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">拼写：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordExtension.wordBasic.word}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">音标：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordExtension.wordBasic.phoneticSymbol}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">常用搭配：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordExtension.wordBasic.commonUsage}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">词汇用法：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordExtension.wordBasic.wordUsage}</td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">联想记忆：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">${wordExtension.wordBasic.associationMemory}</td>
  </tr>
  <tr>  
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">单词级别：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="wordExtension.wordLevel">
    	    	<option value="">请选择</option>
                <c:forEach items="${wordLevelList}" var="wordLevel" varStatus="wordLevelStatus">                  
	            	<option value="${wordLevel.value}" ${wordLevel.value eq wordExtension.wordLevel?'selected="selected"':''}>${wordLevel}</option>
    	        </c:forEach>
         </select>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">单词分类：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	 <select name="wordExtension.wordCategory">
    	    	<option value="">请选择</option>
                <c:forEach items="${wordCategoryList}" var="wordCategory" varStatus="wordCategoryStatus">                  
	            	<option value="${wordCategory}" ${wordCategory eq wordExtension.wordCategory?'selected="selected"':''}>${wordCategory.value}</option>
    	        </c:forEach>
         </select>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	 <select name="wordExtension.grade.code">
                <c:forEach items="${gradeList}" var="i" varStatus="gradeStatus">          
	            	<option value="${i.code}" ${i.code eq wordExtension.grade.code?'selected="selected"':''}>${i.levelFlag}${i.name}(${i.code})</option>
    	        </c:forEach>
         </select>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="2" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        <input type="hidden" name="wordExtension.id" value="${wordExtension.id}"/>
        <input type="hidden" name="gradeCode" value="${wordExtension.grade.code}"/>
        <input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="  取 消  " class="btn_2k3" onclick="history.back()"/>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>