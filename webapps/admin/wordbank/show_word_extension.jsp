<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
</head>

<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  <td class="location">当前位置：词库 &gt; 单词管理 &gt; 单词详情</td>
	  </tr>
	</table>

<!--单词基本信息开始-->
	<table width="100%" border="0" align="center" cellpadding="6" cellspacing="0" bgcolor="#BEDAF1">
	  <tr>
		<td width="50%" align="left" class="txt12blueh">单词基本信息：</td>  
		<td width="50%" align="right"  class="txt12blue" >
			<span  style="cursor:hand"  onclick="show('add_word_type_label1');show('add_word_type_label2');show('add_word_type');$E('add_word_type').focus();">添加词性</span>|
			<span  style="cursor:hand"  onclick="show('word_edit');hide('word_show');">修改</span>|
			<span  style="cursor:hand"  onclick="if (confirm('确定要删除吗？')) location.href='wordExtension!delete.jhtml?id=${wordExtension.id}'">删除</span>|
			<span  style="cursor:hand"  onclick="show('word_show');">显示</span>|
			<span  style="cursor:hand"  onClick="hide('word_show');">隐藏</span>
		</td>
	  </tr>
	</table>

	<table id='word_show' class="txt12555555line-height" width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
		  <td width="12%" align="right" bgcolor="#F7F7F7"  class="txt12blue">单词：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">${wordExtension.wordBasic.word}</td>
		  <td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">音标：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">${wordExtension.wordBasic.phoneticSymbol}</td>
	  </tr>
	  <tr>
		  <td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">常用搭配：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">${wordExtension.wordBasic.commonUsage}</td>
		  <td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">词汇用法：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">${wordExtension.wordBasic.wordUsage}</td>
	  </tr>
	    <tr>
		  <td width="12%" align="right"  bgcolor="#F7F7F7" class="txt12blue">联想记忆：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">${wordExtension.wordBasic.associationMemory}</td>
		  <td width="12%" align="right"  bgcolor="#F7F7F7" class="txt12blue">适用学级：</td>
		  <td width="38%" align="left" bgcolor="#FFFFFF">${wordExtension.grade.name}</td>
	  </tr>
	  <tr>
		  <td width="12%" align="right"  bgcolor="#F7F7F7" class="txt12blue">单词级别：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">${wordExtension.wordLevelName}</td>
		  <td width="12%" align="right"  bgcolor="#F7F7F7" class="txt12blue">单词分类：</td>
		  <td width="38%" align="left" bgcolor="#FFFFFF">${wordExtension.wordCategoryName.value}</td>
	  </tr>
	</table>

	<table id='word_edit' class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" style="display:none">
	<form method="post" action = "wordExtension!saveWord.jhtml">
	  <tr>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">单词：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">
		  	<input class="logininputmanage" type="text" name="wordExtension.wordBasic.word" value="${wordExtension.wordBasic.word}" size="30" />
		  </td>
		  <td width="12%" align="right"  bgcolor="#F7F7F7" class="txt12blue">音标：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">
		  	<input class="logininputmanage" type="text" name="wordExtension.wordBasic.phoneticSymbol" value="${wordExtension.wordBasic.phoneticSymbol}" size="30" />
		  </td>
	  </tr> 
	  <tr>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">常用搭配：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">
		    <textarea class="logininputmanage" rows="3" style="width:100%;" name="wordExtension.wordBasic.commonUsage">${wordExtension.wordBasic.commonUsage}</textarea>
		  </td>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">词汇用法：</td>
		  <td width="38%" align="left" bgcolor="#FFFFFF">
		    <textarea class="logininputmanage" rows="3" style="width:100%;" name="wordExtension.wordBasic.wordUsage">${wordExtension.wordBasic.wordUsage}</textarea>
		  </td>
	  </tr>
	  <tr>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">联想记忆：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">
		  	<input class="logininputmanage" type="text" name="wordExtension.wordBasic.associationMemory" value="${wordExtension.wordBasic.associationMemory}" size="60"/>
		  </td>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
		  <td width="38%" align="left" bgcolor="#FFFFFF">
		  	<select name="wordExtension.grade.code">
	 	    	<option value="">请选择</option>
	             	<c:forEach items="${gradeList}" var="grade" varStatus="gradeStatus">
	          		<option value="${grade.code}" ${grade.code eq wordExtension.grade.code?'selected="selected"':''}>${grade.name}</option>
	 	        </c:forEach>
	         </select>
		  </td>
	  </tr>
	  <tr>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">单词级别：</td>
		  <td width="38%" align="left"  bgcolor="#FFFFFF">
		  	<select name="wordExtension.wordLevel">
	   	    	<option value="">请选择</option>
	               <c:forEach items="${wordLevelList}" var="wordLevel" varStatus="wordLevelStatus">                  
	            	<option value="${wordLevel.value}" ${wordLevel.value eq wordExtension.wordLevel?'selected="selected"':''}>${wordLevel}</option>
	   	        </c:forEach>
	         </select>
		  </td>
		  <td width="12%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">单词分类：</td>
		  <td width="38%" align="left" bgcolor="#FFFFFF">
		  	 <select name="wordExtension.wordCategory">
	    	    	<option value="">请选择</option>
	                <c:forEach items="${wordCategoryList}" var="wordCategory" varStatus="wordCategoryStatus">                  
		            	<option value="${wordCategory}" ${wordCategory eq wordExtension.wordCategory?'selected="selected"':''}>${wordCategory.value}</option>
	    	        </c:forEach>
	         </select>
		  </td>
	  </tr>
	  <tr>
	  	<td colspan="4" align="center" bgcolor="#FFFFFF" height="70">
	  		<input type="hidden" name="wordExtension.wordBasic.id" value="${wordExtension.wordBasic.id}"/>
	   		<input type="hidden" name="wordExtension.id" value="${wordExtension.id}"/>
			<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
			<input type="button" value="  取 消  " class="btn_2k3" onClick="show('word_show');hide('word_edit');"/>
	  	</td>
	  </tr>
	</form>	
	</table>
<!--单词基本信息结束-->

	<!--添加词性-->	
	<table id="add_word_type_label1" width="100%" border="0" align="center" cellspacing="0" cellpadding="0" style="display:none">
  		<tr height="1">
  			<td></td>
  		</tr>
	</table>
	<table id="add_word_type_label2" width="100%" border="0" align="center" cellpadding="6" cellspacing="0" style="display:none">
		<tr bgcolor="#A5D5FC">
	    	<td align="left"  class="txt12blueh">添加词性</td>
	    	<td align="right" class="txt12blue"></td>
		</tr>
	</table>
	<table id="add_word_type" width="100%" border="0" align="center" cellpadding="6" cellspacing="1"
	  bgcolor="#E3E3E3" class="txt12555555line-height"  style="display:none" >
	  <form method="post" action = "wordExtension!saveWordType.jhtml">	
			 <tr>
				<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">词性代码：</td>
				<td width="38%" align="left"  bgcolor="#FFFFFF">
					<input class="logininputmanage" type="text" name="wordType.code" value=""/>
				</td>
				<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">词性名称：</td>
				<td width="38%" align="left" bgcolor="#FFFFFF">
					<input class="logininputmanage" type="text" name="wordType.name" value=""/>
				</td>
			 </tr>
			 <tr>
				<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">存在不同发音：</td>
				<td width="38%" align="left"  bgcolor="#FFFFFF">
					<input type="checkbox" name="wordType.hasDifferentPronunciations"  value="0"
						 onClick="checkboxifchecked(this, 'add_word_type_span1', 'add_word_type_span2');">
				</td>
				<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">
					<span id="add_word_type_span1" style="display:none">音标：</span>
				</td>
				<td width="38%" align="left" bgcolor="#FFFFFF">
					<span id="add_word_type_span2" style="display:none">
						<input class="logininputmanage" type="text" name="wordType.phoneticSymbol" value=""/>
					</span>
				</td>
			 </tr>
			 <tr>
				<td align="center" colspan="4"  bgcolor="#FFFFFF" height="50">
					<input type="hidden" name="wordExtension.id" value="${wordExtension.id}"/>
					<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
					<input type="button" value="  取 消  " class="btn_2k3" onClick="hide('add_word_type_label1');hide('add_word_type_label2');hide('add_word_type');"/>
				</td>
			 </tr>
	</form>
	</table>
	<!--添加词性结束-->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr height="2">
	  	<td></td>
	  </tr>
	</table>
<!--词性开始-->
<c:forEach items="${wordExtension.wordTypes}" var="wordType" varStatus="wordTypeStatus">
    <table height="30" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tilte_bg2">
		<tr id="word_type${wordType.id}_show">
		  <td align="left"  class="txt12blueh" width="50%">&nbsp;${wordType.code}&nbsp;&nbsp;${wordType.name}</td>
		  <td align="right" class="txt12blue">
			<span  style="cursor:hand"  onclick="show('add_chinese${wordType.id}');$E('add_chinese${wordType.id}').focus();">添加中文释义</span>|
			<span  style="cursor:hand"  onclick="show('word_type${wordType.id}_edit'); hide('word_type${wordType.id}_show');">修改</span>|
			<span  style="cursor:hand"  onclick="if (confirm('确定要删除吗？')) location.href='wordExtension!deleteWordType.jhtml?id=${wordExtension.id}&wordTypeId=${wordType.id}'">删除</span>|
	  	  	<span  style="cursor:hand"  onclick="show('word_type${wordType.id}');">显示</span>|
			<span  style="cursor:hand"  onClick="hide('word_type${wordType.id}');">隐藏</span>
		  </td>
		</tr>
		<!--修改词性开始-->
		<tr id="word_type${wordType.id}_edit" style="display:none" height="30">
			<td align="left" class="txt12blueh" width="50%" colspan="2">
				<form method="post" action="wordExtension!saveWordType.jhtml">
				<table width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" class="txt12555555line-height" >
				    <tr bgcolor="#A5D5FC">
	   					<td align="left"  class="txt12blueh" colspan="4">修改词性</td>	   					
	  		 		</tr>
					<tr>
						<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">词性代码：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF">
							<input class="logininputmanage" type="text" name="wordType.code" value="${wordType.code}"/>						
						</td>
						<td width="12%" align="right"bgcolor="#F7F7F7" class="txt12blue">词性名称：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF">
							<input class="logininputmanage" type="text" name="wordType.name" value="${wordType.name}"/>						
						</td>
					</tr>
					<tr>
						<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">存在不同发音：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF">
							<input type="checkbox" name="wordType.hasDifferentPronunciations" value="${wordType.hasDifferentPronunciations}"
							    ${wordType.hasDifferentPronunciations eq 1?'checked="checked"':''} 
								contrlId="add_word_type" onClick="checkboxifchecked(this, 'add_word_type${wordType.id}_span1', 'add_word_type${wordType.id}_span2');">
						</td>
						<td width="12%" align="right" bgcolor="#F7F7F7" class="txt12blue">
							<span id="add_word_type${wordType.id}_span1" ${wordType.hasDifferentPronunciations eq 1?'':'style="display:none"'} >音标：</span>
						</td>
						<td width="38%" align="left" bgcolor="#FFFFFF">
							<span id="add_word_type${wordType.id}_span2" ${wordType.hasDifferentPronunciations eq 1?'':'style="display:none"'} >
								<input class="logininputmanage" type="text" name="wordType.phoneticSymbol" value="${wordType.phoneticSymbol}"/>
							</span>
						</td>
					</tr>
					<tr>
						<td width="100%"  colspan="4" align="center" bgcolor="#FFFFFF" height="50">
							<input type="hidden" value="${wordExtension.id}" name="wordExtension.id"/>
							<input type="hidden" value="${wordType.id}" name="wordType.id"/>
							<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
							<input type="button" value="  取 消  " class="btn_2k3" onClick="show('word_type${wordType.id}_show');hide('word_type${wordType.id}_edit');"/>
						</td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
		<!--修改词性结束-->
	</table>

	<table id="word_type${wordType.id}" width="100%" border="0" bgcolor="#F5F8F7" cellpadding="0" cellspacing="0">
		<!--添加中文释义开始-->
		<tr id="add_chinese${wordType.id}" style="display:none">
		<td>
			<table width="100%" border="0" align="center" cellpadding="6" cellspacing="0">
		        <tr bgcolor="#A5D5FC" height="30">
		            <td align="left" class="txt12blueh">添加中文释义</td>
		            <td align="right" class="txt12blue"></td>		            
		        </tr>
		    </table>
			<table id="add_chinese_table" width="100%" border="0" align="center" bgcolor="#E3E3E3" cellpadding="6" cellspacing="1" class="txt12555555line-height">						 		
				<form method="post" action="wordExtension!saveChineseMeaning.jhtml">
				<tr>
					<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">释  义：</td>
					<td width="38%" bgcolor="#FFFFFF" align="left" class="txt12blue">
						<input class="logininputmanage" style="width:100%;" type="text" name="chineseMeaning.meaning" value=""/>
					</td>
					<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" ></td>
					<td width="38%" align="left" class="txt12blue"  bgcolor="#FFFFFF" ></td>
				</tr>
				<tr>
					<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">同义词：</td>
					<td width="38%" bgcolor="#FFFFFF" align="left">
						<input class="logininputmanage" style="width:100%;" type="text" name="chineseMeaning.synonym" value=""/>
					</td>
					<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">反义词：</td>
					<td width="38%" bgcolor="#FFFFFF" align="left">
						<input class="logininputmanage" style="width:100%;" type="text" name="chineseMeaning.antonym" value=""/>
					</td>					
				</tr>
				<tr>
					<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">易混/辨析词：</td>
					<td width="38%" bgcolor="#FFFFFF" align="left">
						<textarea class="logininputmanage" rows="3" style="width:100%;" name="chineseMeaning.confusableWord"></textarea>
					</td>
					<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">易混/辨析词解析：</td>
					<td width="38%" bgcolor="#FFFFFF" align="left">
						<textarea class="logininputmanage" rows="3" style="width:100%;" name="chineseMeaning.confusableWordAnalysis"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4" bgcolor="#FFFFFF" height="50">						
						<input type="hidden" value="${wordType.id}" name="wordType.id"/>
						<input type="hidden" value="${wordExtension.id}" name="wordExtension.id"/>	
						<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
						<input type="button" value="  取 消  " class="btn_2k3" onClick="hide('add_chinese${wordType.id}');"/>
					</td>
				</tr>
				</form>
			</table>			
		</td>
		</tr>
		<!--添加中文释义结束-->
	
		<!--中文释义开始-->
		<c:forEach items="${wordType.chineseMeanings}" var="chineseMeaning" varStatus="chineseMeaningStatus">
		<tr>
		<td>
			<table id="chinese1" width="100%" border="0" bgcolor="#F5F8F7" cellpadding="0" cellspacing="0">
	 		<tr align="left">
			<td width="100%">
				<span id="chinese${chineseMeaning.id}_show">
				<table border="0" width="100%" class="txt12555555line-height" cellpadding="6">
					<tr>
					  <td align="left"  class="txt12blue" colspan="2"><b>${chineseMeaningStatus.index+1}. ${chineseMeaning.meaning}</b></td>
					  <td align="right" class="txt12blue" colspan="2">
		                   <span style="cursor:hand" onclick="show('word_sentence${chineseMeaning.id}_add');$E('word_sentence${chineseMeaning.id}_add').focus();">添加例句</span>|
		                   <span style="cursor:hand" onclick="show('chinese${chineseMeaning.id}_edit');hide('chinese${chineseMeaning.id}_show');">修改</span>|
		                   <span style="cursor:hand" onclick="if (confirm('确定要删除吗？')) location.href='wordExtension!deleteChineseMeaning.jhtml?id=${wordExtension.id}&chineseMeaningId=${chineseMeaning.id}'">删除</span>
		              </td>
					</tr>
				</table>
				<table border="0" width="100%" class="txt12555555line-height" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">	
					<tr>
						<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">同义词：</td>
						<td width="38%" bgcolor="#FFFFFF" align="left">${chineseMeaning.synonym}</td>
						<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">反义词：</td>
						<td width="38%" bgcolor="#FFFFFF" align="left">${chineseMeaning.antonym}</td>
					</tr>
					<tr>				
						<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">易混/辨析词：</td>
						<td width="38%" bgcolor="#FFFFFF" align="left">${chineseMeaning.confusableWord}</td>
						<td width="12%" bgcolor="#F7F7F7" align="right" class="txt12blue">易混/辨析词解析：</td>
						<td width="38%" bgcolor="#FFFFFF" align="left">${chineseMeaning.confusableWordAnalysis}</td>
					</tr>
			  	</table>
				</span>
				<!--修改中文释义开始-->
				<span id="chinese${chineseMeaning.id}_edit" style="display:none">		
				<table width="100%" border="0" class="txt12555555line-height" bgcolor="#E3E3E3" cellpadding="6" cellspacing="1">								
					<form method="post" action="wordExtension!saveChineseMeaning.jhtml">
				    <tr bgcolor="#A5D5FC" height="30">
            			<td align="left" class="txt12blueh" colspan="4">修改中文释义</td>            	
        			</tr>  
					<tr>
						<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" >释  义：</td>
						<td width="38%" align="left"  bgcolor="#FFFFFF" >
							<input class="logininputmanage" style="width:100%;" type="text" name="chineseMeaning.meaning" value="${chineseMeaning.meaning}"/>
						</td>
						<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" ></td>
						<td width="38%" align="left" class="txt12blue"  bgcolor="#FFFFFF" ></td>
					</tr>
					<tr>
						<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" >同义词：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF" >
							<input class="logininputmanage" style="width:100%;" type="text" name="chineseMeaning.synonym" value="${chineseMeaning.synonym}"/>
						</td>
						<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" >反义词：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF" >
							<input class="logininputmanage" style="width:100%;" type="text" name="chineseMeaning.antonym" value="${chineseMeaning.synonym}"/>
						</td>					
					</tr>
					<tr>
						<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" >易混/辨析词：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF" >
							<textarea class="logininputmanage" rows="3" style="width:100%;" name="chineseMeaning.confusableWord">${chineseMeaning.confusableWord}</textarea>
						</td>
						<td width="12%" align="right" class="txt12blue" bgcolor="#F7F7F7" >易混/辨析词解析：</td>
						<td width="38%" align="left" bgcolor="#FFFFFF" >
							<textarea class="logininputmanage" rows="3" style="width:100%;" name="chineseMeaning.confusableWordAnalysis">${chineseMeaning.confusableWordAnalysis}</textarea>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="4" height="50" bgcolor="#FFFFFF" >			
							<input type="hidden" value="${chineseMeaning.id}" name="chineseMeaning.id"/>
							<input type="hidden" value="${wordType.id}" name="wordType.id"/>
							<input type="hidden" value="${wordExtension.id}" name="wordExtension.id"/>	
							<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
							<input type="button" value="  取 消  " class="btn_2k3" onClick=" show('chinese${chineseMeaning.id}_show');hide('chinese${chineseMeaning.id}_edit');"/>
						</td>
					</tr>
					</form>
				</table>				
				</span>
				<!--修改中文释义结束-->
				
				<!--词性例句开始-->
				<table border="0" width="100%" class="txt12555555line-height" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr bgcolor="#F5F8F7">
						<td width="12%" align="right"  class="txt12blue" valign="top" bgcolor="#F7F7F7">
                  		例 句：
                		</td>		                						
						<td width="88%" align="left" colspan="3"  bgcolor="#FFFFFF">
	            			<table id="table4" class="txt12555555line-height" height="100%" width="100%" border="0" align="left" cellpadding="6" cellspacing="0">
	            				<tr>
	            				  <td align="left" width="10%"  class="txt12blue">序号</td>	
	            				  <td align="left" width="30%" class="txt12blue">英文例句</td>
	            				  <td align="left" width="30%" class="txt12blue">译文</td>
	            				  <td align="left" width="15%"  class="txt12blue">来源</td>
	            				  <td align="left" width="15%" class="txt12blue">操作</td>
	            				</tr>
	            				<c:set var="esnum" value="1"/>
							  	<c:forEach items="${chineseMeaning.exampleSentences}" var="exampleSentence" varStatus="exampleSentenceStatus">
							  	<tr id="word_sentence${exampleSentence.id}_show">
								  <td class="txt12blue">${exampleSentenceStatus.index+1}：</td>
								  <td>${exampleSentence.content}</td>
								  <td>${exampleSentence.translation}</td>							  
								  <td>${exampleSentence.source}</td>
								  <td class="txt12blue">								  	
									<span style="cursor:hand"  onclick="show('word_sentence${exampleSentence.id}_edit');hide('word_sentence${exampleSentence.id}_show');" >修改</span>|
									<span style="cursor:hand"  onclick="if (confirm('确定要删除吗？')) location.href='wordExtension!deleteExampleSentence.jhtml?id=${wordExtension.id}&exampleSentenceId=${exampleSentence.id}'">删除</span>
								  </td>
				                </tr>
								
								<!--修改例句开始-->									
								<form method="post" action="wordExtension!saveExampleSentence.jhtml">				
								<tr id="word_sentence${exampleSentence.id}_edit" style="display:none">								 				  	
								  <td class="txt12blue">${exampleSentenceStatus.index+1}：</td>
								  <td>
									<textarea class="logininputmanage" rows="2" style="width:100%;" name="exampleSentence.content">${exampleSentence.content}</textarea>
							      </td>
								  <td>
									<textarea class="logininputmanage" rows="2" style="width:100%;" name="exampleSentence.translation">${exampleSentence.translation}</textarea>
								  </td>
								  <td>
								    <input type="text" value="${exampleSentence.source}" name="exampleSentence.source" size="10"/>
								  </td>								    
								  <td>	
								    <input type="hidden" value="${exampleSentence.id}" name="exampleSentence.id"/>
									<input type="hidden" value="${chineseMeaning.id}" name="chineseMeaning.id"/>
									<input type="hidden" value="${wordExtension.id}" name="wordExtension.id"/>								
									<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;
									<input type="button" value="  取 消  " class="btn_2k3" onClick="show('word_sentence${exampleSentence.id}_show');hide('word_sentence${exampleSentence.id}_edit');"/>								
								  </td>										
					            </tr>
					            </form>				           
								<!--修改例句结束-->
								<c:set var="esnum" value="${esnum+1}" /> 
								</c:forEach>
								
	              			    <!--添加例句开始-->
	              			    <form method="post" action="wordExtension!saveExampleSentence.jhtml">	
							    <tr id="word_sentence${chineseMeaning.id}_add" style="display:none">
								  <td class="txt12blue">${esnum}：</td>
								  <td>
									<textarea class="logininputmanage" rows="2" style="width:100%;" name="exampleSentence.content"></textarea>
								  </td>
								  <td>									
									<textarea class="logininputmanage" rows="2" style="width:100%;" name="exampleSentence.translation"></textarea>
								  </td>
								  <td>
								     <input type="text" name="exampleSentence.source" size="10" value=""/>
								  </td>								   
								  <td>								    
									<input type="hidden" value="${chineseMeaning.id}" name="chineseMeaning.id"/>
									<input type="hidden" value="${wordExtension.id}" name="wordExtension.id"/>		
									<input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;
									<input type="button" value="  取 消  " class="btn_2k3" onClick="hide('word_sentence${chineseMeaning.id}_add');"/>								
								  </td>              
				                </tr>			                
				                </form> 
							    <!--添加例句结束-->
	             			</table>
						</td>
					</tr>
				</table>
				<!--词性一例句结束-->
			</td>
	  </tr>
	</table>
	</td>
	</tr>
	</c:forEach>
	<!--中文释义结束-->
	</table>
</c:forEach>
<!--词性结束-->

</body>
</html>