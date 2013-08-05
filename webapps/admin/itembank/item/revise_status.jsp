<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题审校情况</title>
<link href="../css/admin.css" rel="stylesheet" type="text/css" />
<script src="../js/admin.js" type="text/javascript"></script>
<script src="../js/m.js"> </script>
<script type="text/javascript" src="../js/dateJs/WdatePicker.js" defer="defer"></script>

<script language="javascript">
<!--
	var numTest = /^[0-9]*$/;
	var stopQuery = true;

	//提交form表单
	//AUTHOR: L.赵亚
	function searchItems(){
		var reviseNum = $("#reviseNum").val();
		var revisedNum = $("#revisedNum").val();
		var reviseRecNum = $("#reviseRecNum").val();
		if(reviseNum==null||reviseNum.length==0){
			alert("请添写审校人数（至少添零）...");
			$("#reviseNum").focus();
			return false;
		} else if(!numTest.test(reviseNum)){
			alert("审校人数请添写数字...");
			$("#reviseNum").select();
			$("#reviseNum").focus();
			return false;
		} else if(parseInt(reviseNum)>${ rqvo.maxRevise }) {
			alert("审校人数请添写不大于${ rqvo.maxRevise }的数字");
			$("#reviseNum").select();
			$("#reviseNum").focus();
			return false;
		}
		if(revisedNum==null||revisedNum.length==0){
			alert("请添写审校通过人数（至少添零）...");
			$("#revisedNum").focus();
			return false;
		} else if(!numTest.test(revisedNum)){
			alert("审校通过人数请添写数字...");
			$("#revisedNum").select();
			$("#revisedNum").focus();
			return false;
		} else if(parseInt(revisedNum)>${ rqvo.maxRevised }) {
			alert("审校通过人数请添写不大于${ rqvo.maxRevised }的数字");
			$("#revisedNum").select();
			$("#revisedNum").focus();
			return false;
		} else if(stopQuery&&parseInt(reviseNum)<parseInt(revisedNum)) {
			if(confirm("审校通过人数不可能大于审校人数！继续查询么？\n\n点 '确定' 继续查询\n\n点 '取消' 返回修改")){
				stopQuery = false;
			} else {
				$("#reviseNum").select();
				$("#reviseNum").focus();
				return false;
			}
		}
		if(reviseRecNum==null||reviseRecNum.length==0){
			alert("请添写纠错数（至少添零）...");
			$("#reviseRecNum").focus();
			return false;
		} else if(!numTest.test(reviseRecNum)){
			alert("纠错数请添写数字...");
			$("#reviseRecNum").select();
			$("#reviseRecNum").focus();
			return false;
		} else if(parseInt(reviseNum)<parseInt(reviseRecNum)) {
			alert("纠错数不可能大于审校人数！请修改纠错数...");
			$("#reviseRecNum").select();
			$("#reviseRecNum").focus();
			return false;
		}
		
		$("#queryForm").submit();
	}
	function hrefFormSub (va, vn) {
		if(vn=='0') return false;
		<c:if test="${ 'yes' ne newPage }">
		$("#reviseStatusHref").val(va);
		$("#newPage").val("yes");
		$("#pageForm").attr("target", "_blank");
		var thisPage = $("#pageNo").val();
		$("#pageNo").val("");
		$("#pageForm").submit();
		$("#newPage").val("no");
		$("#pageNo").val(thisPage);
		$("#pageForm").attr("target", "");
		</c:if>
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
    <td class="location">当前位置：<c:if test="${!('yes' eq teacherLogin)}">题库 &gt; </c:if>审校情况 &gt; ${title}</td>
  </tr>
</table>
<form action="itemRevise!queryStatus.jhtml" method="post" name="queryForm" id="queryForm">
<input type="hidden" name="subject_code" value="${rqvo.subject_code}" />
<input type="hidden" name="pageInit" value="no" />
<table class="txt12555555line-height"  width="100%" border="0" align="center"
	<c:if test="${ 'yes' eq newPage }"> style="display:none" </c:if>
 cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2" >
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
								<option value="${kp.code}" <c:if test="${kp.code eq rqvo.knowledgePoint}">selected</c:if>>
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
					<option value="${grade.code}" <c:if test="${_gradeCode==grade.code}">selected</c:if>>${grade.levelFlag}${grade.name}/${grade.code}</option>
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
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">审校人数：</td>
    <td align="left" bgcolor="#FFFFFF">&lt;=
		<input type="text" id="reviseNum" name="rqvo.reviseNum" value="${rqvo.reviseNum }" title="最大取 ${ rqvo.maxRevise }"/>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">审校通过人数：</td>
    <td align="left" bgcolor="#FFFFFF">&lt;=
		<input type="text" id="revisedNum" name="rqvo.revisedNum" value="${rqvo.revisedNum }" title="最大取 ${ rqvo.maxRevised }"/>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#F7F7F7"  class="txt12blue">纠错数：</td>
    <td align="left" bgcolor="#FFFFFF">&gt;=
		<input type="text" id="reviseRecNum" name="rqvo.reviseRecNum" value="${rqvo.reviseRecNum }" />
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
	<table class="ctl"  width="100%" border="0" align="center"
	<c:if test="${ 'yes' eq newPage }"> style="display:none" </c:if> 
		cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" height="20px" >
	    <td >总题量</td>
	    <td >已审校通过</td>
	    <!-- td width="50">纠错状态</td -->
	    <td >未审校通过</td>
	  </tr>              
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
	    <td >${ allItemCount }</td>
	    <td ><a href="#" onclick="hrefFormSub('1', '${ passCount }');return false;">${ passCount }</a></td>
	    <!-- td width="50">纠错状态</td -->
	    <td ><a href="#" onclick="hrefFormSub('0', '${ unPassCount }');return false;">${ unPassCount }</a></td>
	  </tr>
	</table>

</form>
<form id="pageForm" name="pageForm" action="itemRevise!queryStatus.jhtml" method="post">
<c:if test="${ 'yes' eq newPage }">
<input type="hidden" name="newPage" id="newPage" value="yes" />
</c:if>
<c:if test="${ 'yes' ne newPage }">
<input type="hidden" name="newPage" id="newPage" value="no" />
</c:if>
<input type="hidden" name="subject_code" value="${rqvo.subject_code}" />
<input type="hidden" name="pageNo" id="pageNo" value="${ page.currentPageNo }" />
<input type="hidden" name="pageInit" value="no" />
<input type="hidden" name="rqvo.knowledgePoint" value="${ rqvo.knowledgePoint }" />
<input type="hidden" name="rqvo.grade" value="${rqvo.grade}" />
<input type="hidden" name="rqvo.reviewRound" value="${ rqvo.reviewRound }" />
<input type="hidden" name="rqvo.reviseStatus" value="${ rqvo.reviseStatus }" id="reviseStatusHref" />
<input type="hidden" name="rqvo.reviseNum" value="${ rqvo.reviseNum }" />
<input type="hidden" name="rqvo.revisedNum" value="${ rqvo.revisedNum }" />
<input type="hidden" name="rqvo.reviseRecNum" value="${ rqvo.reviseRecNum }" />
<input type="hidden" name="rqvo.code" value="${ rqvo.code }" />
<input type="hidden" name="rqvo.content" value="${ rqvo.content }" />
<input type="hidden" name="rqvo.importFile" value="${ rqvo.importFile }" />
<c:if test="${ (empty pageInit)||!('yes' eq pageInit) }">
	<table class="ctl"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
	  <tr>
        <td align="right" colspan="6" height="20">
        <ambow:pagination actionName=""
        	                  total="${page.totalPageCount}"
        	                  totalCount="${page.totalCount}" 
        	                  num="${page.currentPageNo}"
        	                  otherParams="" />
        </td>
      </tr>
	  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue" height="20px" >
	    <td width="150">题型</td>
	    <td >题干</td>
	    <!-- td width="50">纠错状态</td -->
	    <td width="50">审校状态</td>
	    <td width="50">审校人数</td>
	    <td width="50">审校通过人数</td>
	    <td width="50">纠错数量</td>
	  </tr>
	  <c:forEach items="${page.result}" var="item" varStatus="itemStatus">                  
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
	    <td>&nbsp;${item.item.itemType.name}(${item.item.itemType.code})</td>
	    <td>&nbsp;<a href="itemRevise!showReply.jhtml?itemId=${ item.item.id }&fromPage=reviseStatus">${fn:replace(fn:replace(item.item.content, "position:relative;", ""),"position: relative;","") }</a></td>
	    <td >&nbsp;<c:if test="${ 1==item.item.reviseStatus }">
	    				<font color="red" >已通过</font>
	    			</c:if><c:if test="${ 1!=item.item.reviseStatus }">
	    				未通过
	    			</c:if>
		</td>
	    <td>
	    	${ item.allPasses }
		</td>
	    <td>
	    	${ item.allPass }
		</td>
	    <td>
	    	${ item.reviseRec }
		</td>
	  </tr>
	  </c:forEach>
	  <c:if test="${ empty page.result }">
	  <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	  	<td width="100%" align="center" colspan="6" height="20px" >没有审校记录...</td>
	  </tr>
	  </c:if>
	  <tr>
        <td align="right" colspan="6" height="20">
        <ambow:pagination actionName="" total="${page.totalPageCount}"
        	              totalCount="${page.totalCount}" 
        	              num="${page.currentPageNo}"
        	              otherParams="" />
        </td>
      </tr>
	</table>
</c:if>
 </form>
</body>
</html>