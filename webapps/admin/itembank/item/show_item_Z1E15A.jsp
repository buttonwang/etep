<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script>
	function saveAOption(answerid) {
		var actionurl = 'answerOption!save.jhtml?id='+answerid+'&itemId=${item.id}';
		
		var obj=document.getElementById('pageForm');
		obj.action = actionurl;
		obj.submit();  		
	}
	
	function deleteAOption(answerid) {
		if (!confirm('确定要删除吗？')) return false;
		
		var actionurl = 'answerOption!delete.jhtml?id='+answerid+'&itemId=${item.id}';
		
  		var obj=document.getElementById('pageForm');
		obj.action = actionurl;
		obj.submit();  		
	}
</script>
</head>
<body>
<form method="post" action="" name="pageForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; 试题详情</td>
  </tr>
</table>
    <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.code}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.statusName}</td>
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
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.year}</td>
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
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.originalItemNum}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.score}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.difficultyValue}</td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">${item.answeringTimeByMin}&nbsp;分钟</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.content}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干译文：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.contentTranslation}</td>
      </tr>
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">正确答案：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.correctAnswer}</td>
	  </tr>	  
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">答案解析：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.answerAnalysis}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">关键词： </td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.keywords}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">关键句： </td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.keySentances}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">创建人：<br /></td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.creater}</td>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
	    <td width="33%"align="left"   bgcolor="#FFFFFF">${item.createdTime}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">更新人：<br /></td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.updater}</td>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">更新时间：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.updatedTime}</td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">审核人：<br /></td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >${item.verifier}</td>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">审核时间：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">${item.verifiedTime}</td>
	  </tr>
	  
	  <tr>
	    <td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">答案选项：<br><br>
        	<span style="cursor:hand" onClick="show('option_add_answer');$E('option_add_answer').focus();" >
        	<b>[ 添 加 ]</b>&nbsp;&nbsp;</span>
        </td>
	    <td align="left"  bgcolor="#FFFFFF" colspan="3">
          <table id="table4" class="txt12555555line-height" height="100%"  width="100%" border="0" align="left"
            cellpadding="6" cellspacing="0" bgcolor="#FFFFFF">  
             <tr class="txt12blue">
                <td width="17%" align="center">选项代码</td>
                <td width="33%" align="left">选项内容</td>
                <td width="33%" align="left">选项译文</td>
                <td width="17%" align="right">操作</td>
             </tr>               	
             <c:forEach items="${item.answerOptions}" var="i" varStatus="itemStatus">
             	<tr id="option_${i.code}_show">
	                <td width="17%" align="center" class="txt12blue">
	                	${i.code}
	                <td width="33%" align="left">
	                	${i.content}	                    
	                <td width="33%" align="left">
	                	${i.translation}	                	
	                </td>
	                <td width="17%" align="right"  class="txt12blue">
                    	<span style="cursor:hand"  onClick="show('option_${i.code}_edit'); hide('option_${i.code}_show');">修改</span>|
                    	<span style="cursor:hand"  onClick="deleteAOption(${i.id})">删除</span>
                	</td>
                </tr>
             	
             	<tr id="option_${i.code}_edit" style="display:none">
	                <td width="17%" align="center"  class="txt12blue">
	                	<input type="text" name="answerOptionId" value="${i.id}" size="5" style="display:none"/>
	                	<input type="text" name="answerOptionCode" value="${i.code}" size="5"/>
	                </td>
	                <td width="33%" align="left">
	                	<input type="text" name="answerOptionContent" value="${i.content}" size="30" />
	                </td>	                    
	                <td width="33%" align="left">
	                	<input type="text" name="answerOptionTranslation" value="${i.translation}" size="30" />	                
	                </td>
					<td width="17%" align="left">
                    	<input type="button" value=" 保存 " class="btn_2k3" onClick="saveAOption(${i.id})"/>&nbsp;&nbsp;
                    	<input type="button" value=" 取消 " class="btn_2k3" onClick="show('option_${i.code}_show');hide('option_${i.code}_edit');"/>
                	</td>
              	</tr>
             </c:forEach>
 
             <tr id="option_add_answer" style="display:none" class="txt12blue">
                <td width="17%" align="center">
                	<input type="text" name="answerOptionId" value="0" size="5" style="display:none"/>
                	<input type="text" name="answerOptionCode" value="C" size="5"/>
                </td>
                <td width="33%" align="left" >
                  	<input type="text" name="answerOptionContent" value=""  size="30"/>
                </td>
                <td width="33%" align="left">
                	<input type="text" name="answerOptionTranslation" value=""  size="30"/>
                </td>
                <td width="17%" align="left">
                  <input type="button" value=" 保存 " class="btn_2k3" onClick="saveAOption(0)"/>&nbsp;&nbsp;
                  <input type="button" value=" 取消 " class="btn_2k3" onClick="hide('option_add_answer');"/>
                </td>
              </tr>
           </table>
        </td>
	  </tr>	  
      <tr>
          <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
		      	<tr>
		        	<td align="center">
  		        	<input type="button" value="  修 改  " class="btn_2k3"  onClick="location.href='item!edit.jhtml?id=${item.id}&subject_code=${subject_code}';"/>&nbsp;&nbsp;
	  	        	<input type="button" value="  审 核  " class="btn_2k3"  onClick="if (confirm('确定要审核吗？')) location.href='item!verify.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
		          	<input type="button" value="  作 废  " class="btn_2k3"  onClick="if (confirm('确定要作废吗？')) location.href='item!invalid.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
		          	<input type="button" value="  删 除  " class="btn_2k3"  onClick="if (confirm('确定要删除吗？')) location.href='item!delete.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
		          	<input type="button" value="  入 卷  " class="btn_2k3"  onClick="location.href='paper!choose.jhtml?itemids=${item.id}';" />&nbsp;&nbsp;
		          	<input type="button" value="  返 回  " class="btn_2k3"  onClick="location.href='item!list.jhtml';"/>&nbsp;&nbsp;
		        	</td>
		        </tr>
	    	  </table>
          </td>
      </tr>
    </table>
    </form>
</body>
</html>