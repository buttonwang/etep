<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script> 
<script type="text/javascript">	
	function save(){
		$E('pageForm').submit();
	}
	window.onload = function() {
		replacTextArea("itemVO.content");
		replacTextArea("itemVO.contentTranslation");
		replacTextArea("itemVO.correctAnswer");
		replacTextArea("itemVO.answerAnalysis");
		replacTextArea("itemVO.writingTemplate");
		replacTextArea("itemVO.scoringNorm");
	}
</script>
</head>
<body>
<form  id="pageForm" method="post" action="item!save.jhtml" onSubmit="return validateItemForm()" _t=jform>
<input name="subject_code" value="${subject_code}"  type="hidden"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 修改试题</td>
  </tr>
</table>
    <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	   <input type="text" name="itemVO.code" size="30" value="${item.code}"/>
    	  </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
            <select name="itemVO.status">
      		<option value="0"  ${item.status eq '0' ? 'selected="selected"':''}>未审核</option>
      		<option value="1"  ${item.status eq '1' ? 'selected="selected"':''}>已审核</option>
      		<option value="2"  ${item.status eq '2' ? 'selected="selected"':''}>已组卷</option>
      		<option value="3"  ${item.status eq '3' ? 'selected="selected"':''}>已使用</option>
      		<option value="-1" ${item.status eq '-1' ? 'selected="selected"':''}>已作废</option>
    	    </select>
        </td>   
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
            <select name="itemVO.regionCode">    		
    		<c:forEach items="${regionList}" var="i" varStatus="itemStatus">                  
	                 <option value="${i.code}" ${item.region.code eq i.code ? 'selected="selected"':''}>${i.name}</option>
             </c:forEach>
             </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
              <select name="itemVO.subjectCode">    		
    		<c:forEach items="${subjectList}" var="i" varStatus="itemStatus">                  
	                  	<option value="${i.code}" ${item.subject.code eq i.code ? 'selected="selected"':''}>${i.name}</option>
             </c:forEach>
    	    </select>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用年级：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	  <input  type="text" id="gradeNames" name="itemVO.gradeNames"  size="30" value="${itemVO.gradeNames}" readonly="true"/>
    	  <input  type="text" id="gradeCodes" name="itemVO.gradeCodes" value="${itemVO.gradeCodes}" style="display:none"/>
    	  &nbsp;&nbsp;<input type="button" value="选择" class="btn_2k3" onClick="getList('grade');"/></td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	  <input  type="text"   size="30"  id="knowledgePointNames" name="itemVO.knowledgePointNames" value="${itemVO.knowledgePointNames}"  readonly="true"/>
    	  <input  type="text" id="knowledgePointCodes" name="itemVO.knowledgePointCodes" value="${itemVO.knowledgePointCodes}"  style="display:none"/>&nbsp;&nbsp;
    	  <input type="button" value="选择" class="btn_2k3"  onClick="getList('knowledgePoint');"/></td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
            <select name="itemVO.itemTypeCode">               		
    		<c:forEach items="${itemTypeList}" var="i" varStatus="itemStatus">                  
	             <option value="${i.code}" ${item.itemType.code eq i.code ? 'selected="selected"':''}>(${i.code})${i.name}</option>
             </c:forEach>
             </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	     <input  type="text" name="itemVO.year"  value="${item.year}"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
           <select name="itemVO.source">    		
    		<option value="1" ${item.source eq '1' ? 'selected="selected"':''}>真题</option>
            <option value="2" ${item.source eq '2' ? 'selected="selected"':''}>模拟</option>
            <option value="3" ${item.source eq '3' ? 'selected="selected"':''}>自编</option>    		
            <option value="4" ${item.source eq '4' ? 'selected="selected"':''}>专项</option>
			<option value="9" ${item.source eq '9' ? 'selected="selected"':''}>其它</option>
    	   </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input  type="text" name="itemVO.sourceBook" value="${item.sourceBook}"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input  type="text" name="itemVO.sourceFile"  value="${item.sourceFile}"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input  type="text" name="itemVO.originalPaperCode"  value="${item.originalPaperCode}"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input  type="text" name="itemVO.originalItemNum"  value="${item.originalItemNum}"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	   <input  type="text" name="itemVO.score"  size="10" value="${item.score}"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input  type="text" name="itemVO.difficultyValue"  size="10" value="${item.difficultyValue}"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input  type="text" name="itemVO.answeringTimeByMin"  size="10" value="${item.answeringTimeByMin}"/>&nbsp;分钟
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	  <textarea name="itemVO.content" cols="90" rows="6">${item.content}</textarea>   
    	</td>
	  </tr>
      <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干译文：</td>
	    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
    	   <textarea  name="itemVO.contentTranslation" cols="90" rows="6">${item.contentTranslation}</textarea>
    	   </td>
      </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    <textarea  name="itemVO.correctAnswer" cols="90" rows="6">${item.correctAnswer}</textarea>
        </td>
	  </tr>
      <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答案解析：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	   <textarea  name="itemVO.answerAnalysis" cols="90" rows="6">${item.answerAnalysis}</textarea>
        </td>
      </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">判分关键词： </td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    <textarea  name="itemVO.scoringKeywords" cols="90" rows="6">${item.scoringKeywords}</textarea>
    	     </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">写作模板：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <textarea  name="itemVO.writingTemplate" cols="90" rows="6">${item.writingTemplate}</textarea>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">评分标准：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <textarea  name="itemVO.scoringNorm" cols="90" rows="6">${item.scoringNorm}</textarea>
        </td>
	  </tr>
      <tr>
          <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
		      	<tr>
		        <td align="center">
		        <input type="submit" value="  保 存  " class="btn_2k3" />&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  取 消  " class="btn_2k3" onClick="location.href='item!list.jhtml';"/>
                </td>
		        </tr>
	    	  </table>
          </td>
      </tr>
    </table>
      <input  type="text" name="itemVO.id"  value="${item.id}" style="display:none"/>
  </form> 
  <jsp:include page="getlist.jsp"></jsp:include>
</body>
</html>