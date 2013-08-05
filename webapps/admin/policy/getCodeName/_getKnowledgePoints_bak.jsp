<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%  
	request.setAttribute("subject_code",request.getParameter("sc"));
	request.setAttribute("grade_code",request.getParameter("gc"));
%>
<head>
<link href="../../css/admin.css" rel="stylesheet" type="text/css">
<script src="../../js/m.js" type="text/javascript"></script>
<script src="../../js/getCodesNames.js"></script>
<script src="../../js/FloatDiv.js"></script>
</head>
</html>
<body  onload="FloatDiv('opt',100,100)">
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选<input type="checkbox" id="selectAll"/></td>
	<td>知识点编码</td>
	<td>知识点名称</td>
	<td>学级</td>
	<td>学科</td>
</tr>
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg">
	<c:if test="${subject_code == usrg.subject.code}">
		<c:forEach items="${usrg.grades}" var="grade" varStatus="status">
			<c:forEach items="${applicationScope.knowledgePointList}" var="i" varStatus="status">
				<c:if test="${grade.code == i.grade.code&&subject_code==i.subject.code&& grade_code == grade.code&&i.state!=-1}">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="checkbox" name="checkitem" c="${i.code}" n="${i.name}"></td>
						<td>${i.code}</td>
						<td>${i.name}</td>
						<td>${grade.name}</td>
						<td>${usrg.subject.name}</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:forEach>
	</c:if>
</c:forEach>
</table>
<div id="opt" style="text-align:center">
<input type="button" class="btn_2k3" value="确 定" id="save">&nbsp;&nbsp;
<input type="button" class="btn_2k3" value="取 消" onClick="window.close();">
</div>
</body>