<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css" >
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../js/m.js" type="text/javascript"> </script>
<script type="text/javascript" src="../js/dateJs/WdatePicker.js" defer="defer"></script>

<script type="text/javascript" language="javascript">
<!--
	//提交form表单
	//AUTHOR: L.赵亚
	
	var numTest = /(^[0-9]+$)/;
	function searchItems(){
		//审校人不能不选
		if(""== $("#reviser_id").val()){
			alert("请选择审校人");
			return false;
		}
		//纠错数不能为空或字符或小数
		if(!numTest.test($("#correctnum_id").attr("value")) ){
			alert("请输入整数");
			return false;
		}
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
		
	});
//-->
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：<c:if test="${!('yes' eq teacherLogin)}">题库 &gt; </c:if>试题审校统计 &gt; ${title}</td>
  </tr>
</table>
<form action="itemRevise!toStatistics.jhtml" method="post" id="pageForm" name="pageForm">
<input type="hidden" name="subject_code" value="${rqvo.subject_code}" >
<input type="hidden" name="pageInit" value="no" >
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
								<option value="${kp.code}"  ${kp.code eq rqvo.knowledgePoint ? "selected" : ""}>
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
					<option value="${grade.code}" ${_gradeCode == grade.code ? "selected" : "" }>${grade.levelFlag}${grade.name}/${grade.code}</option>
				</c:forEach>
			</c:if>
			</c:forEach>
    	</select>
    </td>
  </tr>
 
  <tr>
    <td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">审校人：</td>
	    <td align="left" valign="top" bgcolor="#FFFFFF">
	    	<select name="rqvo.reviser" id="reviser_id" >
    		<option selected="selected" value="">全部</option>
    		<c:forEach items="${urlist }" var="urlist" >
    			<c:set var="_reviser" value="${rqvo.reviser}"/>
    			<option value="${urlist.loginId}" ${_reviser eq urlist.loginId ? "selected" : ""} >${urlist.userName}
				</option>
    		</c:forEach>
    		</select>
	    </td>
   </tr>
  
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">审校时间：</td>
    <td align="left" bgcolor="#FFFFFF">
		<input type="text" name="rqvo.reviseTimeBegin" value="${rqvo.reviseTimeBegin }" class="Wdate"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly">
			-
		<input type="text" name="rqvo.reviseTimeEnd" value="${ rqvo.reviseTimeEnd }"  class="Wdate"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly">
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">纠错数：</td>
    <td align="left" bgcolor="#FFFFFF">
		&gt;=<input type="text" name="rqvo.reviseRecNum" id="correctnum_id" value="${empty  rqvo.reviseRecNum?0: rqvo.reviseRecNum}" >
    </td>
  </tr>
  
  <tr>
    <td bgcolor="#FFFFFF" align="center" colspan="2">
		<input type="button" value=" 统 计 " id="queryItem" class="btn_2k3">
    </td></tr>
  </table>
<c:if test="${ (empty pageInit)||!('yes' eq pageInit) }">
	<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
        <td align="center" height="20">审校总题量
        
        </td>
      <td align="center" height="20">已通过</td>
        <td align="center" height="20">未通过</td>
	  </tr>
	  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" height="20" >
	    <td width="150">${statusmap["0"]+statusmap["1"] }</td>
	    <td ><a target="_blank" href="itemRevise!showDetail.jhtml?status=1" >${statusmap["1"] }</a></td>
	    <td width="200"><a target="_blank" href="itemRevise!showDetail.jhtml?status=0" >${statusmap["0"] }</a></td>
	    
	  </tr>
	                   
	  <tr align="center" >
	    <td colspan="3">有效纠错${errormap["1"]+errormap["2"]+errormap["3"] }，无效纠错${errormap["4"] }</td>
      </tr>
	</table>
<table class="ctl"  width="100%" border="1" align="center" cellpadding="2" cellspacing="1" >
	  <tr>
        <td height="20" align="center">严重错误</td>
      <td align="center">一般错误</td>
        <td align="center">细微错误</td>
        <td align="center">纠错无效</td>
        <td align="center">恶意纠错</td>
	  </tr>
	  
	  <tr align="center" class="txt12blue" height="20" >
	    <td><a target="_blank" href="itemRevise!showDetail.jhtml?reply=1" >${errormap["1"] }</a></td>
	    <td><a target="_blank" href="itemRevise!showDetail.jhtml?reply=2" >${errormap["2"] }</a></td>
	    <td><a target="_blank" href="itemRevise!showDetail.jhtml?reply=3" >${errormap["3"] }</a></td>
	    <td><a target="_blank" href="itemRevise!showDetail.jhtml?reply=4" >${errormap["4"] }</a></td>
	    <td><a target="_blank" href="itemRevise!showDetail.jhtml?reply=5" >${errormap["5"] }</a></td>
      </tr>
	                   
	  <tr align="center" >
	    <td colspan="5">做题总得分率：<font color='red'>${scorerate }%</font></td>
      </tr>
	</table>

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
	    <td width="200">审校状态</td>
	    
	  </tr>
	  <c:forEach items="${iravo.result}" var="item" varStatus="itemStatus">                  
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
	    <td>&nbsp;${item.type.name}(${item.type.code})</td>
	    <td>&nbsp;<a href="itemRevise!showReplyreviser.jhtml?itemId=${ item.id }&rqvo.reviser=${rqvo.reviser } ">${fn:replace(fn:replace(item.content, "position:relative;", ""),"position: relative;","") }</a></td>
	    <td >&nbsp;<font color="${item.reviserstatus==1?"red":"" }" >${ item.reviserstatus==1?"已通过":"未通过" }
	    	<br>
	    	<c:if test="${ item.replyType==0 }" >
	    		<img src="../images/ask.gif" />
	    	</c:if>
	    	<c:if test="${ item.replyType==1 }" >
	    		<img src="../images/pretty.gif" />
	    	</c:if></font></td>
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