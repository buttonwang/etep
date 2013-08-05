<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="ambow" prefix="ambow"%>  
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/common.js"></script>
<script language="javascript" src="../js/admin.js" ></script>
</head>

<body>
<form method="post" action="paper!saveImport.jhtml" onsubmit="return validatePaperForm()" enctype="multipart/form-data">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试卷管理 &gt; 整卷导入</td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷名称：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input type="hidden" value="${id}" name="id"/>
    	<input type="hidden" value="${paper.id}" name="paper.id"/>
    	<input class="logininputmanage" type="text" id="name" name="paper.name" size="40" value="${paper.name}"/>    	
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">文件名称：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<input type="file" name="file"/>&nbsp;&nbsp;	
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷说明：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<textarea class="logininputmanage" name="paper.description" cols="30" rows="2" >${paper.description}</textarea>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="paper.region.code">
    	  <c:forEach items="${region}" var="region" varStatus="status">
    		<option value="${region.code}" ${region.code eq paper.region.code? 'selected="selected"':''}>${region.name}</option>	
    	  </c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷分类：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="paper.paperCategory.code">
    	  <c:forEach items="${paperCategory}" var="paperCategory" varStatus="status">
    		<option value="${paperCategory.code}" ${paperCategory.code eq paper.paperCategory.code? 'selected="selected"':''}>
    		${paperCategory.name}
    		</option>
    	  </c:forEach>
    	</select>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷类型：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<select name="paper.paperType.code">
    	  <c:forEach items="${paperType}" var="paperType" varStatus="status">
    		<option value="${paperType.code}" ${paperType.code eq paper.paperType.code? 'selected="selected"':''}>${paperType.name}</option>    		
    	  </c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<input type="text" id="subjectNames" name="subjectNames" value="${subjectNames}" size="30" readonly="true"/>
    	<input type="text" id="subjectCodes" name="subjectCodes" value="${subjectCodes}" style="display:none"/>&nbsp;&nbsp;
    	<input type="button" value="选择" class="btn_2k3" onclick="getList('subject');"/>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input type="text" id="gradeNames" name="gradeNames" value="${gradeNames}" size="30" readonly="true"/>
    	<input type="text" id="gradeCodes" name="gradeCodes" value="${gradeCodes}" style="display:none"/>&nbsp;&nbsp;
    	<input type="button" value="选择" class="btn_2k3" onclick="getList('grade');"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷难度：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="paper.difficultyValue" id="difficultyValue" size="10"
    	 value="<fmt:formatNumber value='${paper.difficultyValue}' pattern='#######.##'/>" />
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答卷时间：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" style="" name="paper.answeringTime" id="answeringTime" size="10"
    	 value=""  />（分钟）
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="paper.itemsNum" id="itemsNum" value="${paper.itemsNum}" size="10"/>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">卷分：</td>
    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="paper.totalScore" name="paper.totalScore" id="totalScore" value="${paper.totalScore}" size="10"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input type="text" id="knowledgePointNames" name="knowledgePointNames" value="${knowledgePointNames}" size="30" readonly="true"/>
    	<input type="text" id="knowledgePointCodes" name="knowledgePointCodes" value="${knowledgePointCodes}" style="display:none" /> &nbsp;&nbsp;
    	<input type="button" value="选择" class="btn_2k3" onclick="getList('knowledgePoint');"/>
    </td>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">包含题型：</td>
    <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input type="text" id="itemTypeNames" name="itemTypeNames" value="${itemTypeNames}" size="30" readonly="true"/>
    	<input type="text" id="itemTypeCodes" name="itemTypeCodes" value="${itemTypeCodes}" style="display:none" /> &nbsp;&nbsp;
    	<input type="button" value="选择" class="btn_2k3" onclick="getList('itemType');"/>
    </td>
  </tr>
  <c:if test="${errorInfo ne ''}">	
  <tr>
    <td colspan="4" align="left" bgcolor="#FFFFFF">     
      	<font size="+1" color="red">
      		<c:if test="${errorInfo eq 'SAMENAME'}">试卷名称已存在，请重新命名！</c:if>
      		<c:if test="${errorInfo ne 'SAMENAME'}">${errorInfo}</c:if>
      	</font>
    </td>
  </tr>
  </c:if>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
    <table width="121" border="0">
      <tr> 
        <td>
        	<input type="submit" value="  导 入  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" value="  取 消  " class="btn_2k3" onclick="javascript: history.back()"/>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
<jsp:include page="../item/getlist.jsp"></jsp:include>
</form>
</body>
</html>