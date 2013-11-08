<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
function goToStudyNew(userId,loginName,userName,classId,gradeId,refId,flag){	
	str = "login.jsp?userID="+userId+"&loginName="+loginName+"&userName="+userName+"&classCode="+classId
			+"&moduleID=000000003883&refID="+refId+"&gradeID="+gradeId;
	alert(str);
	window.open(str);
}
</script>
</head>

<body>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>

<body>
<br><br>
安规试题测试
<br>
李应基
<a href="login.jsp?userID=t000000000011&loginName=lyj&userName=李应基w&classCode=t00000000001&moduleID=100000000001&refID=627&gradeID=100000000001">登陆</a>
王伟
<a href="login.jsp?userID=t000000000012&loginName=wangw&userName=王伟&classCode=t00000000001&moduleID=100000000001&refID=627&gradeID=100000000001">登陆</a>
高三兵
<a href="login.jsp?userID=t000000000013&loginName=gsb&userName=高三兵&classCode=t00000000001&moduleID=100000000001&refID=627&gradeID=100000000001">登陆</a>
王永胜
<a href="login.jsp?userID=t000000000014&loginName=wys&userName=王永胜&classCode=t00000000001&moduleID=100000000001&refID=627&gradeID=100000000001">登陆</a>

</body>
</html>
<hr>
<br>
</body>
</html>