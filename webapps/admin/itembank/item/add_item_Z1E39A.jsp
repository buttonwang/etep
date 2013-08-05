<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script> 
<script type="text/javascript">	
	function save(){
		$E('pageForm').submit();
	}
	window.onload = function() {
		replacTextArea("item.content");
		replacTextArea("item.contentTranslation");
		replacTextArea("item.writingTemplate");
		replacTextArea("item.scoringNorm");
	}
</script>
</head>
<body>
<form method="post" action="addItem!save.jhtml" onsubmit="return validateItemForm()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 新增试题</td>
  </tr>
</table>
    <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
            <select name="item.region.code">
    			<c:forEach items="${regionList}" var="item" varStatus="itemStatus">                  
	               <option value="${item.code}">${item.name}</option>
             	</c:forEach>
            </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
            <select name="item.subject.code">
                <c:forEach items="${subjectList}" var="item" varStatus="itemStatus">                  
	                <option value="${item.code}">${item.name}</option>
             	</c:forEach>
            </select>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用年级：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	  <input type="text" id="gradeNames" name="gradeNames" value="${gradeNames}" size="30" readonly="true" class="logininputmanage" />
    	  <input type="text" id="gradeCodes" name="gradeCodes" value="${gradeCodes}" style="display:none"/>&nbsp;&nbsp;
    	  <input type="button" value="选择" class="btn_2k3" onClick="getList('grade');"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	  <input type="text" id="knowledgePointNames" name="knowledgePointNames" value="${knowledgePointNames}" size="30" readonly="true" class="logininputmanage" />
    	  <input type="text" id="knowledgePointCodes" name="knowledgePointCodes" value="${knowledgePointCodes}" style="display:none"/>&nbsp;&nbsp;
    	  <input type="button" value="选择" class="btn_2k3" onClick="getList('knowledgePoint');"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
            <!--<select name="item.itemType.code">
	    		<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus">                  
	            	<option value="${item.code}" ${itemTypeCode eq item.code? 'selected="selected"':''}>${item.name}</option>
    	        </c:forEach>
            </select>-->
            <input type="hidden" name="item.itemType.code" value="${itemTypeCode}"/>            
    		<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus">
    			<c:if test="${itemTypeCode eq item.code}">
    				(${item.code})${item.name}
    			</c:if>
   	        </c:forEach>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.year" value=""/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
            <select name="item.source">
                <c:forEach items="${itemSourceList}" var="item" varStatus="itemStatus">                  
	            	<option value="${item.value}">${item}</option>
    	        </c:forEach>
            </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.sourceBook"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.sourceFile"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.originalPaperCode" />
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.originalItemNum"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.score" size="10"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.difficultyValue" size="10"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text"  size="10" name="item.answeringTimeByMin"/>&nbsp;分钟
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题材：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    <input type="text" id="itemThemeNames" name="itemThemeNames" value="${itemThemeNames}" size="30" readonly="true" class="logininputmanage" />
	    	<input type="text" id="itemThemeCodes" name="itemThemeCodes" value="${itemThemeCodes}" style="display:none"/>&nbsp;&nbsp;
	    	<input type="button" value="选择" class="btn_2k3"  onClick="getList('itemTheme');"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	  <textarea name="item.content" cols="90" rows="6">${item.content}</textarea>
        </td>
	  </tr>
      <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干译文：</td>
	    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.contentTranslation" cols="90" rows="3"></textarea>
         </td>
      </tr>
      <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.correctAnswer" cols="90" rows="6"></textarea>
        </td>
	  </tr>
      <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答案解析：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.answerAnalysis" cols="90" rows="12"></textarea>
        </td>
      </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">判分关键词： </td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.scoringKeywords" cols="90" rows="8"></textarea>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">写作模板：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.writingTemplate" cols="90" rows="12"></textarea>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">评分标准：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.scoringNorm" cols="90" rows="12"></textarea>
        </td>
	  </tr>
      <tr>
          <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
		      	<tr>
		        	<td align="center">
		          	<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
		          	<input type="reset"  value="  取 消  " class="btn_2k3" onClick="javascript: history.back()"/>
		        	</td>
		        </tr>
	    	  </table>
          </td>
      </tr>
    </table>
    
    <jsp:include page="getlist.jsp"></jsp:include>
</form>        
</body>
</html>