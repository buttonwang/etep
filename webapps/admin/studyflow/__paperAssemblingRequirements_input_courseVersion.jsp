<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%if(request.getParameter("t")!=null){%>
<input type="checkbox" t=courseVersions ${fn:contains(_obj.courseVersions,'0')?"checked":""} value="0" />新旧版本适用&nbsp; 
<input type="checkbox" t=courseVersions  ${fn:contains(_obj.courseVersions,'1')?"checked":""} value="1" />新版本适用&nbsp;
<input type="checkbox" t=courseVersions  ${fn:contains(_obj.courseVersions,'2')?"checked":""} value="2" />旧版本适用
<input name="${_objName}.courseVersions" id=courseVersions  value="${_obj.courseVersions}" type="hidden"/>
<script>
function checkboxToSplit(inputId){
	var jcheckSel = $("input[t="+inputId+"]");
	var jinput = $("#"+inputId);
	 jcheckSel.change(function(){
		var str = ""
		var i =0;
		var jcheckedSel = $("input[t="+inputId+"]:checked");
		 jcheckedSel.each(function(){
			if(++i>1){
				str+=","
			}
			str+=$(this).val();
		})
		jinput.val(str);
	})
}
$(function(){
	checkboxToSplit("courseVersions");
})
</script>
<%}else{%>
${fn:contains(_obj.courseVersions,'0')?"新旧版本适用":""}${fn:contains(_obj.courseVersions,'1')?"新版本适用":""}${fn:contains(_obj.courseVersions,'2')?"旧版本适用":""}
<%}%>

