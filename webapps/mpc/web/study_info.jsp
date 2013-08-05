<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
if(${isError}){
	alert("输入的日期不正确,请重新输入!");
}else{
	var studyInfoFirst="${studyInfoFirst}";
	var studyInfoSecond='${studyInfoSecond}';
	parent.document.getElementById("studyInfoFirst").innerHTML=studyInfoFirst;
	parent.document.getElementById("studyInfoSecond").innerHTML=studyInfoSecond;
	}
	
</script>


