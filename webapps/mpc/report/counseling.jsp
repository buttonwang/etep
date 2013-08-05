<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>爆破学堂 教学辅导</title>
<META http-equiv=Content-Type content="text/html; charset=GBK">
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<LINK href="../css/style/style3.css" type=text/css rel=stylesheet>
<LINK href="../css/style/assistant.css" type=text/css rel=stylesheet>
<LINK href="../css/style/style8.26.css" type=text/css rel=stylesheet>
<STYLE type=text/css>
.userth {
	FONT-WEIGHT: bold; CURSOR: pointer; BACKGROUND-COLOR: #d9d9d9
}
</STYLE>
<script language="JavaScript">
<!--


function counseling(categoryId){
	active(categoryId);
	$("#categoryId").val(categoryId);
	var para="&actor=<%=request.getAttribute("actor")%>";
	$.ajax({
 		url: "counseling.jhtml?categoryId="+categoryId,
 		type: 'POST',
 		dataType: 'text',
 		timeout: 20000,//��ʱʱ���趨
 		data:para,//��������
 		success: function(html){
  			//���سɹ�����
			//alert(html);
  			$('#math').html(html);
 		}
	});
}

function active(categoryId){
	if(categoryId==8){
		$("td[id='td2']")[0].className="jihuo";
		$("td[id='td3']")[0].className="nojihuo";
		$("td[id='td4']")[0].className="nojihuo";
	}if(categoryId==7){
		$("td[id='td2']")[0].className="nojihuo";
		$("td[id='td3']")[0].className="jihuo";
		$("td[id='td4']")[0].className="nojihuo";
	}else if(categoryId==6){
		$("td[id='td2']")[0].className="nojihuo";
		$("td[id='td3']")[0].className="nojihuo";
		$("td[id='td4']")[0].className="jihuo";
	}
}
function counselingDetail(nodeId, statType) {
	$("#nodeId").val(nodeId);
	$("#statType").val(statType);
	$("#detailsForm").submit();
}
</script>
</head>
<body onload="javascript:counseling(8);">
<table border="0" cellpadding="0" cellspacing="0" id="secTable">
  <tr>
  	<c:if test="${categoryId==null || categoryId==8}">
    <td class="jihuo" id="td2"><a href="javascript:counseling(8);">爆破数学</a></td>
    </c:if>
  	<c:if test="${categoryId !=null && categoryId!=8}">
    <td class="nojihuo" id="td2"><a href="javascript:counseling(8);">爆破数学</a></td>
    </c:if>
    <c:if test="${categoryId==7}">
    <td class="jihuo" id="td3"><strong>爆破物理</strong></td>
    </c:if>
  	<c:if test="${categoryId!=7}">
    <td class="nojihuo" id="td3"><a href="javascript:counseling(7);">爆破物理</a></td>
    </c:if>
    <c:if test="${categoryId==6}">
    <td class="jihuo" id="td4"><strong>爆破化学</strong></td>
    </c:if>
  	<c:if test="${categoryId!=6}">
    <td class="nojihuo" id="td4"><a href="javascript:counseling(6);">爆破化学</a></td>
    </c:if>
  </tr> 
</table>
<table  border="0"  cellspacing="0"  cellpadding="0"  width="100%" style="border:1px solid #aaaaaa;">
    <tr>
      <td valign="top" style="padding:18px;">
		<div id="math">
		</div>
	  </td>
    </tr>
</table>
<form action="counseling!details.jhtml" id="detailsForm" method="post" target="_blank" >
	<input type="hidden" id = "categoryId" name = "categoryId" value ="" />
	<input type="hidden" id = "actor"      name = "actor"      value ="${actor}" />
	<input type="hidden" id = "nodeId"     name = "nodeId"     value ="" />
	<input type="hidden" id = "statType"   name = "statType"   value ="" />
</form>
</body>
</html>