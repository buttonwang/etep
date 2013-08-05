<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	List<Map> listUser = null;
	if (request.getAttribute("listUser") != null)
		listUser = (List<Map>) request.getAttribute("listUser");

	String ClassCode = (String) request.getAttribute("ClassCode");
	String userId = (String) request.getAttribute("userId");
	List<Map> recordList = null;
	if (request.getAttribute("recordList") != null)
		recordList = (List<Map>) request.getAttribute("recordList");
	

	
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin.css" type="text/css">
<link href="<%=request.getContextPath()%>/css/pingce.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript">
function do_stu() {
	var frm=document.from_1;
	if (frm.userId.value=='-1')
		return;
	frm.submit();
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000">

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<tr>
		<td valign="top">
		<table width="100%" border="1" cellpadding="3" cellspacing="0"
			class="table_question">

			<form name="from_1" action='<%=request.getContextPath()%>/report/ad_record.jhtml' method='post'>
			<input type='hidden' name='ClassCode' value='<%=ClassCode%>'>
			<tr>
				<td colspan="5" align="right">学生：<select name="userId" onchange='do_stu()'>
					<option value='-1'>----请选择学生----</option>
					<%
							for (Map theUser : listUser) {
								String sel ="";
								if(userId != null && userId.equals((String) theUser.get("id"))){
									sel = "selected";
								}
					%>
					<option value="<%=theUser.get("id")%>" <%=sel%>><%=theUser.get("login_name")%></option>
					<%
							}
					%>
				</select></td>
			</tr>
			</form>
		 <tr class="question_info"> 
          <td width="20%" align="center"><strong>登录名</strong></td>
          <td width="20%" align="center"><strong>开始学习<br>
            </strong></td>
          <td width="20%" align="center"><strong>结束学习</strong></td>
          <td width="20%" align="center"><strong>学习时长</strong></td>
          <td width="20%" align="center"><strong>学习内容</strong></td>
    </tr>
        <!-- 显示记录列表 -->
        <%
        if(recordList != null){
        	Integer total = (Integer) request.getAttribute("total");
        	Integer totalPage = (Integer) request.getAttribute("totalPage");
        	Integer pageNo = (Integer) request.getAttribute("pageNo");
        	Integer previousPage = pageNo -1 >0?pageNo-1:1;
        	Integer nextPage = pageNo + 1 >totalPage?totalPage:pageNo+1;
		%>
		<c:forEach items="${recordList}" var="recordMap" varStatus="recordStatus" >
		<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${recordMap['time_used']/60}" var="timeUsedM" />
       	<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${recordMap['time_used']%60}" var="timeUsedS" />
        <tr align="center"> 
          <td>${recordMap['login_name']}</td>
          <td><fmt:formatDate value="${recordMap['start_time']}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td><fmt:formatDate value="${recordMap['end_time']}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td><c:if test="${timeUsedM!=0}">${timeUsedM}分钟</c:if>${timeUsedS}秒</td>
          <td>${recordMap['name']}</td>
        </tr>
        </c:forEach>
        <tr>
        	<td colspan=5>
		<table>
	  	<tr class='ReporterRow_level2'> 
	  	<td colspan='2' style='background-color:#fff;padding-left:8px;'>
	  	<form id='pageForm' action='ad_record.jhtml' method='post'>
	  	<table width='100%' border='0' cellspacing='0' cellpadding='0'>
	  	<tr>
	  	<td bgcolor='#FFFFFF' class='TdRight'>
	  	共有 <%=total%> 记录 <%=totalPage%>页 
	  	<a href='javascript:page(1)'>首页</a> | <a href='javascript:page(<%=previousPage%>)'>上一页</a> | <a href='javascript:page(<%=nextPage%>)'>下一页</a> | <a href='javascript:page(<%=totalPage%>)'>末页</a> <input id='pageNo' name='pageNo' type='text' style='width:34px;' align='absmiddle' maxlength='2' value='<%=pageNo%>'><input name='go' type='button' id='go' value='GO' align='absmiddle' onclick='page(-1)'><input type='hidden' name='' value=''>
	  	<input type='hidden' name='ClassCode' value='<%=ClassCode %>'><input type='hidden' name='userId' value='<%=userId%>'></td></tr></table></form></td></tr><script>function page(toPageNo){if(toPageNo!='-1'){document.getElementById('pageNo').value = toPageNo;}if(!isPlus(document.getElementById('pageNo').value)){alert('请您输入大于0的数字');return;}if(document.getElementById('pageNo').value > <%=totalPage%>){alert('您输入的页数已经超过最大页数，最大页数是<%=totalPage%>');return;}document.getElementById('pageForm').submit();}function isPlus(value){ValidationExpression=/^[0-9]+$/;if (ValidationExpression.test(value))return true;return false;}</script>
				</table>
			</td>
  	</tr> 
  	<%
        }
  	%>
		</table>
		</td>
	</tr>

</table>
</body>
</html>
