<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
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
	    <!--所属学科  -->
	    <input type="hidden" name="subject_code" value="${subject_code}"/> 
	    <input type="hidden" name="item.subject.code" value="${subject_code}"/> 
            <select name="item.region.code" style="width: 100px">
    			<c:forEach items="${regionList}" var="item" varStatus="itemStatus">                  
	                  	<option value="${item.code}">${item.name}</option>
             	</c:forEach>
            </select>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科:</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
       
            <c:forEach items="${subjectList}" var="item" varStatus="itemStatus">                  
			<c:if test="${subject_code==item.code}"> ${item.name} 
			</c:if>
		</c:forEach>
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
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
            
         <c:forEach items="${applicableObjectList}" var="applicableObject" varStatus="itemStatus">
    		<input name="item.applicableObject"  type="radio" ${applicableObject.value eq '2'?'checked="checked"':''} 
    			 value="${applicableObject.value}" />${applicableObject}&nbsp;
        </c:forEach>
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="item.validityValue" />
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
	    <td width="33%" align="left" bgcolor="#FFFFFF">
    	  <input  type="text"   size="30"  id="knowledgePointNames" name="knowledgePointNames" value="${knowledgePointNames}" readonly="true"/>
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
    	    <input class="logininputmanage" type="text" name="item.year" value=""/>
        </td>
	  </tr>
	  <tr>
	   
        <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
	     <input class="logininputmanage" type="text" name="item.abilityValue"/>
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
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF">
    		<input class="logininputmanage" type="text" name="item.opinion"/>
        </td>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度 ：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >
    		<input class="logininputmanage" type="text" name="item.itemValue" />
        </td>
	  </tr>
	    <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">正确答案：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >    	    
                <c:forEach items="${commonOptionsList}" var="item" varStatus="itemStatus">                  
	            	 ${item} <input type="checkbox" value="${item}" name="item.correctAnswer">
    	        </c:forEach>
        </td>
          <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">轮次复习 ：</td>
	    <td width="33%" align="left"  bgcolor="#FFFFFF" >
	    	<input type="checkbox" name="reviewRound"  value="0" />不限&nbsp; 
			<input type="checkbox" name="reviewRound"  value="1" />一次&nbsp;
			<input type="checkbox" name="reviewRound"  value="2" />两次
        </td>
	  </tr>
      
     
      <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答案选项：</td>
	    <td align="left"  bgcolor="#FFFFFF" colspan="3">
          <table id="table4" class="txt12555555line-height" height="100%" width="100%" border="0" align="left"
            cellpadding="6" cellspacing="0" bgcolor="#FFFFFF">
              <c:forEach items="${commonOptionsList}" var="i" varStatus="itemStatus"> 
             	<tr>
                <td width="20%" align="center" class="txt12blue">
                	<input  type="text" name="answerOptionCode" value="${i}" size="5"/></td>
                <td width="80%" align="left">
					<textarea fck="fck"  class="logininputmanage" name="answerOptionContent" id="answerOptionContent_${i}" cols="90" rows="3"></textarea></td>
               
              </tr>    
             </c:forEach>
           </table>
        </td>
	  </tr>
	   <tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">提示：</td>
			<td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
			   <textarea fck="fck"  class="logininputmanage" name="item.hint" cols="90" rows="3"></textarea>
			</td>
      	</tr>
      	 <tr>
	    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">方法与技巧：</td>
	    <td width="83%" align="left" bgcolor="#FFFFFF" colspan=3>
    	    <textarea fck="fck"  name="item.skills" cols="90" rows="5"></textarea>    
        </td>
	  </tr>
	  <tr>
	    <td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
	    <td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">    	  
    	  <textarea fck="fck"  name="item.content" cols="90" rows="6">${item.content}</textarea>
        </td>
	  </tr>
      
	
		       <tr>
			    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解1：</td>
			    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
		    	    <textarea fck="fck"  class="logininputmanage" name="item.analysisAtLarge1" cols="90" rows="5"></textarea>
		        </td>
		      </tr>
		      <tr>
			    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解2：</td>
			    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
		    	    <textarea fck="fck"  class="logininputmanage" name="item.analysisAtLarge2" cols="90" rows="5"></textarea>
		        </td>
		      </tr>
			   <tr>
			    <td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">详解3：</td>
			    <td width="83%" align="left" bgcolor="#FFFFFF" colspan="3">
		    	    <textarea fck="fck"  class="logininputmanage" name="item.analysisAtLarge3" cols="90" rows="5"></textarea>
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