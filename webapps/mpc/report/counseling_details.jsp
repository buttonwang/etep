<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>爆破学堂 教学辅导</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="../css/style/style3.css" type=text/css rel=stylesheet>
<LINK href="../css/style/assistant.css" type=text/css rel=stylesheet>
<LINK href="../css/style/style8.26.css" type=text/css rel=stylesheet>
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/popdiv_1.js"></script>

<script language="JavaScript">
function counselingDetail(statType) {
	$("#checkItemIds").val("");
	$("#statType").val(statType);
	$("#detailsForm").attr("action", "counseling!details.jhtml");
	$("#detailsForm").attr("target", "_self");
	$("#detailsForm").submit();
}

function counselingExportAll() {
	$("#start").val(0);
	$("#detailsForm").attr("action", "counseling!exportAll.jhtml");
	$("#detailsForm").attr("target", "_blank");
	$("#detailsForm").submit();
}

function counselingExportAny() {
	checkAnyItem();
	$("#start").val(0);
	$("#detailsForm").attr("action", "counseling!exportAny.jhtml");
	$("#detailsForm").attr("target", "_blank");
	$("#detailsForm").submit();
}

function pageCallBack(start, pageSize) {
	checkAnyItem();
	 
	$("#start").val(start);
	$("#pageSize").val(pageSize);
	$("#detailsForm").attr("action", "counseling!details.jhtml");
	$("#detailsForm").attr("target", "_self");
	$("#detailsForm").submit();
}

function checkAllItem() {
	var checked = $("#checkAll").attr("checked");
	$(".checkItem").attr("checked", checked);
}

function checkAnyItem() {
	var checkItemIds = $("#checkItemIds").val();
	var curChecked = "";
	$(".checkItem").each( 
			function() {
				itemId = $(this).val() + ",";
				if ($(this).attr("checked")) {
					if (checkItemIds.indexOf(itemId) == -1) curChecked += itemId;
				} else {
					if (checkItemIds.indexOf(itemId) != -1) checkItemIds = checkItemIds.replace(itemId, "");
				}
			}
	);

	checkItemIds += curChecked;
	$("#checkItemIds").val(checkItemIds);
}

</script>

<style type="text/css">
<!--
div.MsoNormal {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
div.MsoNormal1 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal1 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal1 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
div.MsoNormal2 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal2 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal2 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
div.MsoNormal3 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal3 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal3 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal11 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal21 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal22 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
-->
</style>
</head>
<body> 
	<form action="counseling!details.jhtml" id="detailsForm" method="post" >
		<input type="hidden" id = "actor"      name = "actor"      value ="${actor}" />
		<input type="hidden" id = "categoryId" name = "categoryId" value ="${categoryId}" />
		<input type="hidden" id = "nodeId"     name = "nodeId"     value ="${nodeId}" />
		<input type="hidden" id = "statType"   name = "statType"   value ="${statType}" />
		
		<input type="hidden" id = "start"      name = "start"      value ="${start}" />
		<input type="hidden" id = "pageSize"   name = "pageSize"   value ="${pageSize}" />
		
		<input type="hidden" id = "checkItemIds" name = "checkItemIds"  value ="${checkItemIds}" />
	</form>
	<div class="con_right2">
        <div class="tager">
        <div class="tager_in">
		  <span class="f14px cblue fB">正在浏览的章： </span>
		    <label>
		      <select name="selectCapter" id="selectCapter">
		      	<option value="0">全部</option>
		        <c:forEach items="${groupList}" var="group" varStatus="groupStatus">
		        	<option value="${group['node_id']}">${group['node_name']}</option>
		        </c:forEach>
	          </select>
	        </label>
	        <br/><br/>
          <h2>
          	<span class="f14px cblue fB">试题类别：</span>
          	<span id="allSpan"><a href="javascript:counselingDetail(0)"  class="cBlack">全部</a></span>
          	<span id="fiveSpan"><a href="javascript:counselingDetail(5)"  class="cBlack">五星题</a></span>
          	<span id="fourSpan"><a href="javascript:counselingDetail(4)"  class="cBlack">四星题</a></span>
          	<span id="threeSpan"><a href="javascript:counselingDetail(3)"  class="cBlack">三星题</a></span>
          	<span id="errorSpan"><a href="javascript:counselingDetail(10)" class="cBlack">错题</a></span>
          	<span id="unsureSpan"><a href="javascript:counselingDetail(11)" class="cBlack">疑问题</a></span>
          </h2>
          <br/>
          <h2 class="f12px cblue fB">备注：不同用户做过的相同的试题，在此页面只显示一次。 </h2>
        </div>
        </div>
 		<div class="blankW_9"></div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="gzlist">
          <tr class="gz_title">
            <td>			</td>
            <td>题干</td>
            <td width="8%">题型</td>
            <td width="13%">谁的<span id="statTypeTitle"></span>题？</td>
          </tr>
          <jsp:include page="counseling_details_items.jsp"></jsp:include>
        </table>
        <div class=" blankW_6"></div>
        <div class="list_gz_inp">
            <span class="inp"><input type="checkbox" name="全选" value="" id="checkAll" onclick="checkAllItem()" />全选</span>
        </div>
        <div class="blank6"></div>
        <div class="manu">
        <jsp:include page="../include_pagination.jsp"></jsp:include>
        <br/><br/><br/>
		<input type="submit" name="button" value="导出选定试题…" onclick="counselingExportAny()" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="button" value="导出全部试题…" onclick="counselingExportAll()" />
		</div>
        <div class="blankW9"></div>
	</div>
	
	<script language="JavaScript">
		var nodeId = $("#nodeId").val();
		$("#selectCapter ").val(nodeId);

		$("#selectCapter").change( function() {
			$("#nodeId").val($("#selectCapter").val());
			 counselingDetail($("#statType").val());
		});
		
 		var statType = $("#statType").val();
 		if (statType==0) {
 	   		$("#allSpan").addClass("cBlackcurrent");
 	   		$("#allSpan").html("全部");
 	   		$("#statTypeTitle").html("全部");
 		}
 		if (statType==5) {
 	   		$("#fiveSpan").addClass("cBlackcurrent");
 	   		$("#fiveSpan").html("五星题");
 	   		$("#statTypeTitle").html("五星");
 		}
 		if (statType==4) {
 	   		$("#fourSpan").addClass("cBlackcurrent");
 	   		$("#fourSpan").html("四星题");
 	   		$("#statTypeTitle").html("四星");
 		}
 		if (statType==3) {
 	   		$("#threeSpan").addClass("cBlackcurrent");
 	   		$("#threeSpan").html("三星题");
 	   		$("#statTypeTitle").html("三星");
 		}
 		if (statType==10) {
 	   		$("#errorSpan").addClass("cBlackcurrent");
 	   		$("#errorSpan").html("错题");
 	   		$("#statTypeTitle").html("错");
 		}
 		if (statType==11) {
 	   		$("#unsureSpan").addClass("cBlackcurrent");
 	   		$("#unsureSpan").html("疑问题");
 	   		$("#statTypeTitle").html("疑问");
 		}

 		function setCheckItem() {
 	 		var checkItemIds = $("#checkItemIds").val();
 	 		$(".checkItem").each(
 	 				function() {
 	 					itemId = $(this).val();
 	 					if (checkItemIds.indexOf(itemId + ",") != -1) $(this).attr("checked", true);
 	 				}
 	 		);
 		}

 		setCheckItem();
	</script>
</body>
</html>
