<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML XMLNS:m="http://www.w3.org/1998/Math/MathML">
<HEAD>
<OBJECT
      ID=MathPlayer
      CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"
   >
   </OBJECT>
   <?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/math.js"></script>
<script type="text/javascript" src="../js/m.js"></script> 
<script type="text/javascript" src="../js/mpc3x4xResetId.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>

<script type="text/javascript">
	window.onload = function() {
		replacTextArea("item.content");
		replacTextArea("item.answerPrototype");
		replacTextArea("item.skills");
		replacTextArea("item.analysisAtLarge1");
		replacTextArea("item.analysisAtLarge2");
		replacTextArea("item.analysisAtLarge3");
		replacTextArea("item.hint");
		
	}
</script>
</head>
<body>
<form method="post" action="addItem!save.jhtml" onSubmit="return validateItemForm()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：题库 &gt; 试题管理 &gt; ${title}</td>
  </tr>
</table>
    <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	  	
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
            <select name="item.region.code">
    			<c:forEach items="${regionList}" var="region" varStatus="itemStatus">                  
	                  	<option value="${region.code}" ${region.code eq item.applicableObject ? 'selected' : ''}>${region.name}</option>
             	</c:forEach>
            </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
		      <c:forEach items="${applicableObjectList}" var="applicableObject" varStatus="itemStatus">
		    		<input name="item.applicableObject"  type="radio" ${applicableObject.value eq '2'?'checked="checked"':''} 
		    			 value="${applicableObject.value}" />${applicableObject}&nbsp;
		        </c:forEach>
        </td>
	  </tr>
	   
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	  <input  type="text"   size="50"  id="knowledgePointNames" name="knowledgePointNames" value="${knowledgePointNames}" readonly="true"/>
    	  <input  type="text" id="knowledgePointCodes" name="knowledgePointCodes" value="${knowledgePointCodes}" style="display:none"/>&nbsp;&nbsp;
    	  <input type="button" value="选择" class="btn_2k3" onClick="javascript: window.open('./knowledgePoint!list.jhtml?queryType=sw&subject_code=${item.subject.code}&code='+document.getElementById('knowledgePointCodes').value,','newWindow', 'height=600, width=500, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=300');"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
	    	<input type="hidden" name="item.itemType.code" value="${itemTypeCode}"/>            
    		<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus">
    			<c:if test="${itemTypeCode eq item.code}">
    				(${item.code})${item.name}
    			</c:if>
   	        </c:forEach>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.year" value="${item.year}"/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
             <c:forEach items="${itemSourceList}" var="source" varStatus="sourceStatus">
			   <c:if test="${source.value<=4}">
			    	<input name="item.source"  type="radio" ${source.value eq '1'?'checked="checked"':''} 
			    			 value="${source.value}" />${source}&nbsp;
			    </c:if>
		    </c:forEach>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.sourceBook" value=""/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.opinion" value=""/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.itemValue" value=""/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.sourceFile" value=""/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.originalPaperCode" value=""/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.originalItemNum" value=""/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.score" size="10" value=""/>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.difficultyValue" size="10" value=""/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" size="10" value="" name="item.answeringTimeByMin"/>&nbsp;分钟
        </td>
	  </tr>
	   <tr>
	          <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	    <input class="logininputmanage" type="text" name="item.validityValue" value=""/>
        </td>
		    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
		    <td width="33%" align="left"  bgcolor="#FFFFFF">
		     <input class="logininputmanage" type="text" name="item.abilityValue"/>
	        </td>
	  </tr>
	  <tr>
	        <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">复习轮次：</td>
	    	<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <input type="checkbox" name="reviewRound"  value="0" />不限&nbsp; 
			<input type="checkbox" name="reviewRound"  value="1" />一轮&nbsp;
			<input type="checkbox" name="reviewRound"  value="2" />二轮
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">    	  
    	  <textarea id="item.content" name="item.content" cols="90" rows="6"></textarea>
        </td>
	  </tr>
	   
          <tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
				<Table width="100%">
	     			<Tr>
					<td width="100%" colspan="2">
					<input type="button" value="增加公式型答案" onClick="addRow('correctAnswerName',1)"  class="btn_2k3" />&nbsp;&nbsp;
                    <input type="button" value="增加纯文本型答案" onClick="addRow('correctAnswerName',0)"  class="btn_2k3" />&nbsp;&nbsp; 
                    </td>
					</Tr> 
				</Table>
				<Table id="correctAnswerName" name="correctAnswerName"   width="100%">
				</Table>    
			</td>
        </tr>
	    <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">答案原型：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">    	  
    	   <textarea name="item.answerPrototype" cols="90" rows="6"></textarea>
        </td>
	  </tr>
	   <tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">提示：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
					<textarea class="logininputmanage" name="item.hint" cols="90" rows="3"></textarea>
			</td>
        </tr>
		
		   <tr>
		   <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">方法与技巧：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
    	    <textarea class="logininputmanage" name="item.skills" cols="90" rows="5"></textarea>
        </td>
         </tr>
          <tr>
			 <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解1：</td>
			 <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
		    	 <textarea class="logininputmanage" name="item.analysisAtLarge1" cols="90" rows="5"></textarea>
		     </td>
		  </tr>
		  <tr>
			 <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解2：</td>
			 <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
		    	  <textarea class="logininputmanage" name="item.analysisAtLarge2" cols="90" rows="5"></textarea>
		     </td>
		   </tr>
		   <tr>
			    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解3：</td>
			    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
		    	    <textarea class="logininputmanage" name="item.analysisAtLarge3" cols="90" rows="5"></textarea>
		        </td>
		   </tr>
      	  <tr>
	          <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
	              <table border="0" width="100%">
			      	<tr>
			        	<td align="center">
			        	   <!--所属学科  -->
						    <input type="hidden" name="subject_code" value="${subject_code}"/> 
						    <input type="hidden" name="item.subject.code" value="${subject_code}"/> 
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