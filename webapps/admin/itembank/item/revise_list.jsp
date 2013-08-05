<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css" />
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../js/m.js"> </script>
<script type="text/javascript" src="../js/dateJs/WdatePicker.js" defer="defer"></script>

<script language="javascript">
<!--
	//提交form表单
	//AUTHOR: L.赵亚
	function searchItems(){
		$("#pageForm").submit();
	}

	//初始化页面，轮次信息和审校状态信息都是多选框，如果多选时，单纯的再用EL表达式表现会比较麻烦，或者要嵌入JAVA代码
	//故做此方法
	//AUTHOR: L.赵亚
	function init(){
		var reviewRound = ",${ rqvo.reviewRound },";
		if(reviewRound){
			if(reviewRound.indexOf("1,")>-1)
				$("#reviewRound1").attr("checked", true);
			if(reviewRound.indexOf("2,")>-1)
				$("#reviewRound2").attr("checked", true);
		}
		var reviseStatus = ",${ rqvo.reviseStatus },";
		if(reviseStatus){
			if(reviseStatus.indexOf("0,")>-1)
				$("#reviseStatus1").attr("checked", true);
			if(reviseStatus.indexOf("1,")>-1)
				$("#reviseStatus2").attr("checked", true);
		}
		var replyType = ",${ rqvo.replyType },";
		if(replyType){
			if(replyType.indexOf("0,")>-1)
				$("#replyType1").attr("checked", true);
			if(replyType.indexOf("1,")>-1)
				$("#replyType2").attr("checked", true);
		}
	}
	$(document).ready(function(){
		$("#queryItem").click(searchItems);
		init();
	});
//-->
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：<c:if test="${!('yes' eq teacherLogin)}">题库 &gt; </c:if>试题审校 &gt; ${title}</td>
  </tr>
</table>
<form action="itemRevise!toQuery.jhtml" method="post" id="pageForm" name="pageForm">
<input type="hidden" name="subject_code" value="${rqvo.subject_code}" />
<input type="hidden" name="pageInit" value="no" />
<table class="txt12555555line-height"  width="100%" border="0" align="center"
 cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知 识 点：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
         <select style="width:250px;" name="rqvo.knowledgePoint" id="knowledgePoint">
    		<option selected="selected" value="">全部</option>
			<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
				<c:if test="${subject_code == usrg.subject.code}">
					<c:forEach items="${usrg.grades}" var="grade" >							
						<c:forEach items="${knowledgePointList}" var="kp" varStatus="itemStatus">
							<c:if  test="${kp.grade.code== grade.code && kp.state!=-1}">
								<option value="${kp.code}"  ${kp.code eq rqvo.knowledgePoint ? "selected":""}>
									${kp.levelFlag}${kp.code}/${kp.name}
								</option>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:if>
			</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
    <td align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="rqvo.grade" id="grade_q" >
    		<option selected="selected" value="">全部</option>
			<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
			<c:if test="${subject_code == usrg.subject.code}">
				<c:if test="${rqvo.grade eq ''}">
					<c:set var="_gradeCode" value="${grade_code}"/>
				</c:if>
				<c:if test="${rqvo.grade ne ''}">
					<c:set var="_gradeCode" value="${rqvo.grade}"/>
				</c:if>
				<c:forEach items="${usrg.grades}" var="grade" >
					<option value="${grade.code}" ${_gradeCode==grade.code?"selected":""}>${grade.levelFlag}${grade.name}/${grade.code}</option>
				</c:forEach>
			</c:if>
			</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">复习轮次：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="checkbox" id="reviewRound1" name="rqvo.reviewRound" value="1" />一轮
		<input type="checkbox" id="reviewRound2" name="rqvo.reviewRound" value="2" />二轮
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">审校状态：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="checkbox" id="reviseStatus1" name="rqvo.reviseStatus" value="0" />未通过
		<input type="checkbox" id="reviseStatus2" name="rqvo.reviseStatus" value="1" />已通过
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">审校时间：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="text" name="rqvo.reviseTimeBegin" value="${rqvo.reviseTimeBegin }" class="Wdate"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly/>
			-
		<input type="text" name="rqvo.reviseTimeEnd" value="${ rqvo.reviseTimeEnd }"  class="Wdate"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly/>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">纠错状态：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="checkbox" id="replyType1" name="rqvo.replyType" value="0" /><img src="../images/ask.gif" />（未回复）
		<input type="checkbox" id="replyType2" name="rqvo.replyType" value="1" /><img src="../images/pretty.gif" />（已回复）
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">纠错时间：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="text" name="rqvo.reviseRecordTimeBegin" value="${rqvo.reviseRecordTimeBegin }" class="Wdate"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly/>
			-
		<input type="text" name="rqvo.reviseRecordTimeEnd" value="${ rqvo.reviseRecordTimeEnd }"  class="Wdate"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly/>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="text" name="rqvo.code" value="${rqvo.code }" />
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">题　　干：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="text" name="rqvo.content" value="${rqvo.content }" />
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="text" name="rqvo.importFile" value="${rqvo.importFile }" />
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" align="center" colspan="2">
		<input type="button" value=" 查 询 " id="queryItem" class="btn_2k3"/>
    </td></tr>
  </table>
<c:if test="${ (empty pageInit)||!('yes' eq pageInit) }">
	<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
        <td align="right" colspan="4" height="20">
        <ambow:pagination actionName=""
        	                  total="${iravo.page.totalPageCount}"
        	                  totalCount="${iravo.page.totalCount}" 
        	                  num="${iravo.page.currentPageNo}"
        	                  otherParams="" />
        </td>
      </tr>
	  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" height="20px" >
	    <td width="150">题型</td>
	    <td >题干</td>
	    <!-- td width="50">纠错状态</td -->
	    <td width="50">审校状态</td>
	    <td width="150">审校记录</td>
	  </tr>
	  <c:forEach items="${iravo.result}" var="item" varStatus="itemStatus">                  
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
	    <td>&nbsp;${item.type.name}(${item.type.code})</td>
	    <td>&nbsp;${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") }</td>
	    <!--td width="50">&nbsp;
	    	<c:if test="${ item.replyType==-1 }" >
	    		无
	    	</c:if>
	    	<c:if test="${ item.replyType==0 }" >
	    		<img src="../images/ask.gif" />
	    	</c:if>
	    	<c:if test="${ item.replyType==1 }" >
	    		<img src="../images/pretty.gif" />
	    	</c:if>
	    </td-->
	    <td >&nbsp;<font color="${ "已通过" eq item.reviseStatusName ? "red" : ""}" >${ item.reviseStatusName }
	    	<br>
	    	<c:if test="${ item.replyType==0 }" >
	    		<img src="../images/ask.gif" />
	    	</c:if>
	    	<c:if test="${ item.replyType==1 }" >
	    		<img src="../images/pretty.gif" />
	    	</c:if></font></td>
	    <td>
	    	${ item.reviseInfo }
	    	<c:if test="${ !(empty item.reviseInfo) }"><br></c:if>
	    	<input type="button" value="审校" 
	    	onclick="window.location.href='itemRevise!show.jhtml?itemId=${item.id}&subject_code=${rqvo.subject_code}'"/>
	    </td>
	  </tr>
	  </c:forEach>
	  <c:if test="${ empty iravo.result }">
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	  	<td width="100%" align="center" colspan="4" height="20px" >没有审校记录...</td>
	  </tr>
	  </c:if>
	  <tr>
        <td align="right" colspan="4" height="20">
        <ambow:pagination actionName="" total="${iravo.page.totalPageCount}"
        	              totalCount="${iravo.page.totalCount}" 
        	              num="${iravo.page.currentPageNo}"
        	              otherParams="" />
        </td>
      </tr>
	</table>
</c:if>
 </form>
</body>
</html>