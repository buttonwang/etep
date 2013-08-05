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
 	var k=0;
 	var names="",codes="";
 	
 	window.onload = function() {
		replacTextArea("itemVO.content");
		replacTextArea("itemVO.contentTranslation");
		var subAS = document.getElementsByName("subItemVO.answerAnalysis");
		
		for (i=0; i<subAS.length; i++) {
			subAS[i].setAttribute("id", "subAs"+i);
			replacTextArea(subAS[i].id);
		}
	}
 </script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 试题详情</td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
	<td>
       <table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
          <tr>
            <td width="50%" align="left"   class="txt12blueh">试题信息</td>
            <td width="50%" align="right"  class="txt12blue" >
              <span  style="cursor:hand"  onClick="show('table2');hide('table1');">修改</span>|
              <span  style="cursor:hand"  onClick="location.href='item!verifyInShowPage.jhtml?id=${item.id}'">审核</span>|
              <span  style="cursor:hand"  onClick="location.href='item!invalidInShowPage.jhtml?id=${item.id}'">作废</span>|
              <span  style="cursor:hand"  onClick="if (confirm('确定要删除吗？')) location.href='item!delete.jhtml?id=${item.id}'">删除</span>|
              <span  style="cursor:hand"  onClick="location.href='paper!choose.jhtml?itemids=${item.id}';">入卷</span>|
              <span  style="cursor:hand"  onClick="show('add_subitem_label','add_subitem');$E('add_subitem').focus();" >添加子题</span>|
              <span  style="cursor:hand"  onClick="show('table1');hide('table2');" >显示</span>|
              <span  style="cursor:hand"  onClick="hide('table1','table2');">隐藏</span>
            </td>
          </tr> 
      </table>
	  <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.code}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF"><c:choose> 
		    		<c:when test="${item.status eq '0'}"> 
		     				未审核
		    		</c:when>
		    		<c:when test="${item.status eq '1'}"> 
		     				已审核
		    		</c:when>
		    		<c:when test="${item.status eq '2'}"> 
		     				已组卷
		    		</c:when>
		    		<c:when test="${item.status eq '3'}"> 
		     				已使用
		    		</c:when>
		    		<c:when test="${item.status eq '-1'}"> 
		     				已作废
		    		</c:when> 
		    		<c:otherwise>
		    		</c:otherwise>
		   		</c:choose></td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${item.region.name}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${item.subject.name}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用年级：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${itemVO.gradeNames}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${itemVO.knowledgePointNames}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.itemType.code}(${item.itemType.name})</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${item.year} </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.sourceName}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${item.sourceBook}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.sourceFile}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${item.originalPaperCode} </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${item.score}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.difficultyValue}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">文章原始号：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.originalItemNum}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">文章题材：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${itemVO.itemThemeNames}</td>
          </tr>
         <tr>
            <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">文章内容：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.content}</td>
            </tr>
          <tr>
            <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">文章译文：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">${item.contentTranslation}</td>
            </tr>
          <tr>  
          	<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">文章字数：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.wordCount}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">阅读用时：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF" colspan="3">${item.readingTimeByMin}&nbsp;分钟</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答题总用时：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.answeringTimeByMin}&nbsp;分钟</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">思路分析：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.thinkingAnalyse}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">做题技巧：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF" colspan="3">${item.skills}</td>
          </tr>
            <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">创建人：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.creater}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.createdTime}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新人：</td>
            <td align="left"  bgcolor="#FFFFFF" >${item.updater}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新时间：</td>
            <td align="left"  bgcolor="#FFFFFF">${item.updatedTime}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核人：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.verifier}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核时间：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${item.verifiedTime}</td>
          </tr>   
      </table>
	  <table id="table2" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3"  style="display:none">
	  	 <form  method="post" action="item!editItem.jhtml" onSubmit="return validateItemForm()">
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
    	  		<input type="text" name="itemVO.code" size="30" value="${item.code}"/> 
            	<input type="hidden" name="itemVO.id" size="30" value="${item.id}"/>       
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
    	  &nbsp;&nbsp;<input type="button" value="选择" class="btn_2k3" onClick="getList('grade');"/>
            </td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含知识点：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
    	         <input  type="text"   size="30"  id="knowledgePointNames" name="itemVO.knowledgePointNames" value="${itemVO.knowledgePointNames}"  readonly="true"/>
    	  <input  type="text" id="knowledgePointCodes" name="itemVO.knowledgePointCodes" value="${itemVO.knowledgePointCodes}"  style="display:none"/>&nbsp;&nbsp;
    	  <input type="button" value="选择" class="btn_2k3"  onClick="getList('knowledgePoint');"/>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">
                <select name="itemVO.itemTypeCode">               		
    		<c:forEach items="${itemTypeList}" var="i" varStatus="itemStatus">                  
	             <option value="${i.code}" ${item.itemType.code eq i.code ? 'selected="selected"':''}>${i.code}(${i.name})</option>
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
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
    	    	 <input  type="text" name="itemVO.score"  size="10" value="${item.score}"/>
            </td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">
	    	    <input  type="text" name="itemVO.difficultyValue"  size="10" value="${item.difficultyValue}"/>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">文章原始号：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    	<input  type="text" name="itemVO.originalItemNum"  value="${item.originalItemNum}"/>
            </td>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">文章题材：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
    	           <input  type="text"   size="30"  id="itemThemeNames" name="itemVO.itemThemeNames" value="${itemVO.itemThemeNames}"  readonly="true"/>
    	  <input  type="text" id="itemThemeCodes" name="itemVO.itemThemeCodes" value="${itemVO.itemThemeCodes}"  style="display:none"/>&nbsp;&nbsp;
    	  <input type="button" value="选择" class="btn_2k3"  onClick="getList('itemTheme');"/>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">文章内容：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">            	
            	<textarea name="itemVO.content" cols="90" rows="6">${item.content}</textarea>  
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">文章译文：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
          <textarea  name="itemVO.contentTranslation" cols="90" rows="6">${item.contentTranslation}</textarea>
          </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">文章字数：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    	<input  type="text" name="itemVO.wordCount" value="${item.wordCount}"/>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">阅读用时：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    	<input  type="text" name="itemVO.readingTimeByMin"  size="10" value="${item.readingTimeByMin}"/>&nbsp;分钟
            </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答题总用时：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">
    	    	 <input  type="text" name="itemVO.answeringTimeByMin"  size="10" value="${item.answeringTimeByMin}"/>&nbsp;分钟
            </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">思路分析：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF" colspan="3">
            <textarea  name="itemVO.thinkingAnalyse" cols="90" rows="6">${item.thinkingAnalyse}</textarea>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">做题技巧：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF" colspan="3">
           <textarea  name="itemVO.skills" cols="90" rows="6">${item.skills}</textarea>
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
		      	<tr>
		        	<td align="center">
		          	<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
		          	<input type="button" value="  取 消  " class="btn_2k3" onClick="show('table1');hide('table2');"/>
		        	</td>
		        </tr>
	    	  </table>
            </td>
          </tr>
          </form>
      </table>
    </td>
  </tr>
</table>
<!--添加子题开始-->
<table id="add_subitem_label"   width="100%" border="0" align="center" cellpadding="6" cellspacing="0" style="display:none">
	<tr bgcolor="#A5D5FC">
	    <td align="left"    class="txt12blueh">添加子题</td>
	    <td align="right"  class="txt12blue">	    	  	
	    </td>
	</tr>
</table>
<table id="add_subitem" class="txt12555555line-height"  width="100%" border="0" align="center"
  cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" style="display:none">
<form id="add_subitem_form" method="post" action="item!saveSubItem.jhtml">
 <input  type="hidden" name="subItemVO.itemId" value="${item.id}"/>
   <tr>
    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题原始题号：</td>
    <td width="33%" align="left"  bgcolor="#FFFFFF">
        <input class="logininputmanage" type="text" name="subItemVO.originalSubItemNum"/>
    </td>
    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题难度：</td>
    <td width="33%" align="left"  bgcolor="#FFFFFF">
        <input class="logininputmanage" type="text" name="subItemVO.difficultyValue" />
    </td>
  </tr>
  <tr>
    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题顺序号：</td>
    <td width="33%" align="left"  bgcolor="#FFFFFF">
        <input class="logininputmanage"  type="text" name="subItemVO.orderNum" />
    </td>
    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答案：</td>
    <td width="33%" align="left"  bgcolor="#FFFFFF">
        <input class="logininputmanage" type="text" name="subItemVO.correctAnswer" />
    </td>
  </tr>
    <tr>
    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">包含知识点：</td>
    <td align="left"  bgcolor="#FFFFFF" colspan="3">
    	<input type="text"   size="30"  id="knowledgePoint_addNames" name="subItemVO.knowledgePointNames"   readonly="true" class="logininputmanage"/>
    	<input type="text" id="knowledgePoint_addCodes" name="subItemVO.knowledgePointCodes" style="display:none"/>&nbsp;&nbsp;
    	<input type="button" value="选择" class="btn_2k3"  onClick="getList('knowledgePoint_add');"/>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题答案解析：</td>
    <td align="left"  bgcolor="#FFFFFF" colspan="3">
   		<textarea class="logininputmanage" name="subItemVO.answerAnalysis" cols="90" rows="5"></textarea>
    </td>
  </tr>
  <tr>
    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答案关键词：</td>
    <td width="33%" align="left"  bgcolor="#FFFFFF">
        <textarea class="logininputmanage" name="subItemVO.keyAnswerWords"  cols="35" rows="3"></textarea>
    </td>
    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题关键句分析：</td>
    <td width="33%" align="left"  bgcolor="#FFFFFF">
        <textarea class="logininputmanage" name="subItemVO.keySentanceAnalysis" cols="35" rows="3"></textarea>
    </td>
  </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
      <table border="0" width="100%">
       <td align="center">
            <input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="  取 消  " class="btn_2k3" onClick="hide('add_subitem','add_subitem_label');"/>
            </td>
      </table>
    </td>
  </tr>
   </form>
 </table>
<!--添加子题结束-->
<table width="100%" border="0" align="center" >
<!--子题21开始-->
<c:set var="itemCount" value="${'1'}"></c:set>
 <c:forEach items="${item.subItems}" var="subItem" varStatus="itemStatus">
  <tr>
    <td >
	  <table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  class="tilte_bg">
	     <tr>
	      <td  width="50%" align="left"    class="txt12blueh">子题 ${itemCount}</td>
          <td align="right"  class="txt12blue">
				<span style="cursor:hand" onClick="show('edit_subitem_${itemCount}','option_${itemCount}');hide('show_subitem_${itemCount}');">修改</span>|
				<span style="cursor:hand" onClick="if (confirm('确定要删除吗？')) location.href='item!deleteSubItem.jhtml?subItemVO.id=${subItem.id}&subItemVO.itemId=${item.id}';">删除</span>|
                <span style="cursor:hand; display: none"  onClick="show('add_option_${itemCount}');$E('add_option_${itemCount}').focus();" >添加答案选项|</span>
	    	    <span style="cursor:hand" onClick="show('show_subitem_${itemCount}','option_${itemCount}');hide('edit_subitem_${itemCount}');">显示</span>|
				<span style="cursor:hand" onClick="hide('show_subitem_${itemCount}','edit_subitem_${itemCount}','option_${itemCount}');">隐藏</span>
		  </td>
	    </tr>
	  </table>      
  	  <table id="show_subitem_${itemCount}" class="txt12555555line-height"  width="100%" border="0" align="center"
 			 cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" >
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题原始题号：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.originalSubItemNum}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题难度：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF"> ${subItem.difficultyValue}</td>
          </tr>
		  <tr>
			<td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">子题顺序号：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.orderNum}</td>
			<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">子题答案：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.correctAnswer}</td>
		  </tr>
		  <tr>
			<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">包含知识点：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3" id="show_knowledgePoint_names_${itemCount}"></td>
		  </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题答案解析：</td>
            <td align="left"  bgcolor="#FFFFFF" colspan="3">${subItem.answerAnalysis}</td>
          </tr>
          <tr>
            <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答案关键词：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.keyAnswerWords}</td>
            <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题关键句分析：</td>
            <td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.keySentanceAnalysis}</td>
          </tr>
     </table>

    <table id="edit_subitem_${itemCount}" class="txt12555555line-height"  width="100%" border="0" align="center"
      cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" style="display:none">
       <form method="post" action="item!saveSubItem.jhtml">
		  <input type="hidden" name="subItemVO.id" value="${subItem.id}"/>
		  <input type="hidden" name="subItemVO.itemId" value="${item.id}"/>
      <tr>
        <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题原始题号：</td>
        <td width="33%" align="left"  bgcolor="#FFFFFF">
            <input type="text" name="subItemVO.originalSubItemNum" value="${subItem.originalSubItemNum}" class="logininputmanage"/>
        </td>
        <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题难度：</td>
        <td width="33%" align="left"  bgcolor="#FFFFFF">
           <input type="text" name="subItemVO.difficultyValue" value="${subItem.difficultyValue}" class="logininputmanage"/>
        </td>
      </tr>
      <tr>
    	<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题顺序号：</td>
    	<td width="33%" align="left"  bgcolor="#FFFFFF">
    		<input type="text" name="subItemVO.orderNum" value="${subItem.orderNum}" class="logininputmanage"/>
    	</td>
    	<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答案：</td>
    	<td width="33%" align="left"  bgcolor="#FFFFFF">
    		<input type="text" name="subItemVO.correctAnswer" value="${subItem.correctAnswer}" class="logininputmanage"/>
    	</td>
  	  </tr>
      <tr>
    	<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">包含知识点：</td>
    	<td align="left"  bgcolor="#FFFFFF" colspan="3">
        	<input type="text" size="30" id="knowledgePoint_edit${itemCount}Names" name="subItemVO.knowledgePointNames" readonly="true" value="" class="logininputmanage"/>
    		<input type="text" id="knowledgePoint_edit${itemCount}Codes" name="subItemVO.knowledgePointCodes" style="display:none" value=""/>&nbsp;&nbsp;
    		<input type="button" value="选择" class="btn_2k3" onClick="getList('knowledgePoint_edit${itemCount}');"/>
        </td>
  	  </tr>
      <tr>
        <td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">子题答案解析：</td>
        <td align="left"  bgcolor="#FFFFFF" colspan="3">
        	<textarea class="logininputmanage" name="subItemVO.answerAnalysis" cols="90" rows="5">${subItem.answerAnalysis}</textarea>
        </td>
      </tr>
      <tr>
        <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题答案关键词：</td>
        <td width="33%" align="left"  bgcolor="#FFFFFF">
            <textarea class="logininputmanage" name="subItemVO.keyAnswerWords"  cols="35" rows="3">${subItem.keyAnswerWords}</textarea>
        </td>
        <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题关键句分析：</td>
        <td width="33%" align="left"  bgcolor="#FFFFFF">
            <textarea class="logininputmanage" name="subItemVO.keySentanceAnalysis" cols="35" rows="3">${subItem.keySentanceAnalysis}</textarea>
        </td>
      </tr>
      <tr>
        <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
          <table border="0" width="100%">
            <tr>
                <td align="center">
                <input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  取 消  " class="btn_2k3" onClick="hide('edit_subitem_${itemCount}');show('show_subitem_${itemCount}');"/>
                </td>
            </tr>
          </table>
        </td>
      </tr>
      </form>
     </table>
     
	  <!--子题21答案选项开始-->
	 <table id="option_${itemCount}" class="txt12555555line-height"  width="100%" border="0" align="center" style="display:none"
  	  	cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" >	  
	   <tr>
	     <td  width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blueh">答案选项：</td>
	     <td align="left"  bgcolor="#FFFFFF" colspan="4">
          <table id="table4" class="txt12555555line-height" height="100%"  width="100%" border="0" align="left" style="display:none"
            cellpadding="6" cellspacing="0" bgcolor="#FFFFFF">
              <tr>
                <td width="17%" align="center" class="txt12blue">选项代码</td>
                <td width="33%" align="center" class="txt12blue">选项内容</td>
                <td width="33%" align="center" class="txt12blue">选项译文</td>
                <td width="17%" align="center" class="txt12blue">操作</td>
              </tr>
              <c:forEach items="${subItem.answerOptions}" var="option" varStatus="itemStatus">              
		   	 	  <tr id="show_option_${itemCount}_${option.id}">
                <td width="17%" align="center"  class="txt12blue">${option.code}</td>
                <td width="33%" align="center" >${option.content}</td>
                <td width="33%" align="center" >${option.translation}</td>
                <td width="17%" align="center"  class="txt12blue">
                    <span style="cursor:hand"  onClick="show('edit_option_${itemCount}_${option.id}');hide('show_option_${itemCount}_${option.id}');" >修改</span>&nbsp;
                    <span style="cursor:hand"  onClick="if (confirm('确定要删除吗？')) location.href='item!deleteAnswerOption.jhtml?answerOptionVO.id=${option.id}&answerOptionVO.itemId=${item.id}';" >删除</span>
                </td>
              </tr>
              <tr id="edit_option_${itemCount}_${option.id}" style="display:none">
              <form method="post" action="item!saveAnswerOptionHasSubItem.jhtml">
                <td width="17%" align="center"  class="txt12blue">
                	<input  type="text" value="${option.code}" size="5" name="answerOptionVO.code"/></td>
                <td width="33%" align="center"  >
                    <input  type="text" value="${option.content}" size="30" name="answerOptionVO.content"/></td>
                <td width="33%" align="center">
                	<input  type="text" value="${option.translation}" size="30" name="answerOptionVO.translation"/>
                	<input  type="hidden" value="${option.subItem.id}" size="30" name="answerOptionVO.subItemId"/>
                	<input  type="hidden" value="${item.id}" size="30" name="answerOptionVO.itemId"/>
               		<input  type="hidden" value="${option.id}" size="30" name="answerOptionVO.id"/>
                </td>
                <td width="17%" align="center" >
                    <input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;
                    <input type="button" value=" 取消 " class="btn_2k3" onClick="show('show_option_${itemCount}_${option.id}');hide('edit_option_${itemCount}_${option.id}');"/>
                </td>
                </form>	
              </tr>              	
		   	 </c:forEach>
              <tr id="add_option_${itemCount}" style="display:none">
              <form method="post" action="item!saveAnswerOptionHasSubItem.jhtml">
                <td width="17%" align="center"  class="txt12blue">
                	<input  type="text" value="" size="5" name="answerOptionVO.code"/></td>
                <td width="33%" align="center"  >
                    <input  type="text" value="" size="30" name="answerOptionVO.content"/></td>
                <td width="33%" align="center">
                	<input  type="text" value="" size="30" name="answerOptionVO.translation"/>
                	<input  type="hidden" value="${subItem.id}" size="30" name="answerOptionVO.subItemId"/>
                	<input  type="hidden" value="${item.id}" size="30" name="answerOptionVO.itemId"/>
                </td>
                <td width="17%" align="center" >
                    <input type="submit" value=" 保存 " class="btn_2k3"/>&nbsp;&nbsp;
                    <input type="button" value=" 取消 " class="btn_2k3" onClick="hide('add_option_${itemCount}');"/>
                </td>
                </form>	
              </tr>
           </table>
        </td>
	  </tr>
    </table>
<!--子题21选项结束-->
  </td>
  </tr>
  <script type="text/javascript">
 			k=1;
 			names='';
 			codes='';
	   	 	<c:forEach items="${subItem.knowledgePoints}" var="knowledgePoint" varStatus="itemStatus">
	   	 		if(k>1){
	   	 			names+=',';
	   	 			codes+=',';
	   	 		}
	   	 		names+='${knowledgePoint.name}';
	   	 		codes+='${knowledgePoint.code}';
	   	 		k++;  		
	   	 	</c:forEach>		  
	   	 	$E('show_knowledgePoint_names_${itemCount}').innerHTML=names;
	   	 	$E('knowledgePoint_edit${itemCount}Names').value=names;
	   	 	$E('knowledgePoint_edit${itemCount}Codes').value=codes;
 </script>

 <c:set var="itemCount" value="${itemCount+1}"></c:set>
  </c:forEach>
<!--子题21结束-->
	<tr height="3">
  	  <td></td>
    </tr>
  </table>
 <jsp:include page="getlist.jsp"></jsp:include>
</body>
</html>