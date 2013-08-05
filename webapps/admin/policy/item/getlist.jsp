<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span id="grade" style="display:none;">
<input type="hidden" id="listname" value="grade" />
<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选
		<input type="checkbox" onclick=checkAll('checkitem') /></td>
	<td>学级编码</td>
	<td>学级名称</td>
</tr>
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg" >
	<c:if test="${subject_code == usrg.subject.code}">
		<c:forEach items="${usrg.grades}" var="grade">
			<c:if test="${grade_code == grade.code}">
				<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
					<td><input type="checkbox" name="checkitem" value="${grade.code},${grade.name}"></td>
					<td>${grade.code}</td>
					<td>${grade.name}</td>
				</tr>
			</c:if>
		</c:forEach>
	</c:if>
</c:forEach>
</span> <span id="knowledgePoint" style="display:none;">
<input type="hidden" id="listname" value="knowledgePoint" />
<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选
		<input type="checkbox" onclick=checkAll('checkitem') /></td>
	<td>知识点编码</td>
	<td>知识点名称</td>
	<br />
	<td>学级</td>
	<td>学科</td>
</tr>
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg">
	<c:if test="${subject_code == usrg.subject.code}">
		<c:forEach items="${usrg.grades}" var="grade" varStatus="status">
			<c:forEach items="${knowledgePointList}" var="i" varStatus="status">
				<c:if test="${grade.code == i.grade.code&&subject_code==i.subject.code&& grade_code == grade.code}">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="checkbox" name="checkitem" value="${i.code},${i.name}"></td>
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
</span> <span id="itemTheme" style="display:none;">
<input type="hidden" id="listname" value="itemTheme" />
<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选
		<input type="checkbox" onclick=checkAll('checkitem') /></td>
	<td>题材编码</td>
	<td>题材名称</td>
	<td>学级</td>
	<td>学科</td>
</tr>
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg">
	<c:if test="${subject_code == usrg.subject.code}">
		<c:forEach items="${usrg.grades}" var="grade" varStatus="status">
			<c:forEach items="${itemThemeList}" var="i" varStatus="status">
				<c:if test="${grade.code == i.grade.code&&subject_code==i.subject.code&& grade_code == grade.code}">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="checkbox" name="checkitem" value="${i.code},${i.name}"></td>
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
</span> <span id="itemType" style="display:none;">
<input type="hidden" id="listname" value="itemType" />
<tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="30">全选
		<input type="checkbox" onclick=checkAll('checkitem') /></td>
	<td>题型编码</td>
	<td>题型名称</td>
	<td>学级</td>
	<td>学科</td>
</tr>
<c:forEach items="${sessionScope.m_userSubjectGradeRole }" var="usrg">
	<c:if test="${subject_code == usrg.subject.code}">
		<c:forEach items="${usrg.grades}" var="grade" varStatus="status">
			<c:forEach items="${itemTypeList}" var="i" varStatus="status">
				<c:if test="${grade.code == i.grade.code&&subject_code==i.subject.code&& grade_code == grade.code}">
					<tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
						<td><input type="checkbox" name="checkitem" value="${i.code},${i.name}"></td>
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
</span> 