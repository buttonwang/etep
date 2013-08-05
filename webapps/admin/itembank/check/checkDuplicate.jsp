<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%
	List<Map> recordList = null;
	if (request.getAttribute("recordList") != null)
		recordList = (List<Map>) request.getAttribute("recordList");
	String subject = (String)request.getAttribute("subject");
	String grade = (String)request.getAttribute("grade");
	String type = (String)request.getAttribute("type");
	String sub ="";
	if(subject != null){
		sub = "?subject="+subject;
	}
	if(grade != null){
		sub = sub+"&grade="+grade;
	}
	if(type != null){
		sub = sub+"&type="+type;
	}
%>
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/admin.js" type="text/javascript"></script>
<script>
	function getCheckedValueNew(boxname) {
		var strArr = new Array();
		strArr[0] = "";
		strArr[1] = "";
		var boxs = document.getElementsByName(boxname);
		if (boxs.length > 0) {
			for (var i = 0; i < boxs.length; i++) {
				if (boxs[i].checked){
					str=boxs[i].value.split(",");   
					strArr[0] += str[0] + ",";
					strArr[1] += str[1] + ",";
				}
			}
		}
		strArr[0] = strArr[0].replace(new RegExp(',$'), '');
		strArr[1] = strArr[1].replace(new RegExp(',$'), '');
		return strArr;	 
	}
  function batchdelete() {
    var codes1 = getCheckedValueNew("checkitem1");
	var codes2 = getCheckedValueNew("checkitem2");
    if (codes1=='' && codes2 =='') {
    	alert('请先选中项目，再删除！');
    	return;
    }
    if (confirm('确定要删除吗？')) {
		if(codes1[0] == ""){
			itemIds = codes2[0];
		}else if(codes2[0] == ""){
			itemIds = codes1[0];
		}else{
			itemIds =codes1[0]+","+codes2[0];
		}
		
		if(codes1[1] == ""){
			dupIds = codes2[1];
		}else if(codes2[1] == ""){
			dupIds = codes1[1];
		}else{
			dupIds = codes1[1]+","+codes2[1];
		}
		
  		var actionurl = 'duplicate!deleteBatch.jhtml?itemIds=' + itemIds + '&dupIds='+dupIds;
		
  		window.location.href = actionurl;
  	}
  } 
  function check(){
  	this.location = "duplicate!seek.jhtml<%=sub%>";
  }
</script>
</head>
<body>
<form name="pageForm" action="duplicate!query.jhtml<%=sub%>" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="location">
  <tr >
    <td width="95%" >当前位置：试题查重      </td>
    <td width="5%" ></td>
  </tr>
</table>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>&nbsp;</td>
	</tr>
<tr>
<td>
<table style="table-layout:fixed" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#E3E3E3">
  <tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
	<td width="15%">编号</td>
    <td width="30%" nowrap style="overflow:hidden;">内容</td>
    <td width="15%">编号</td>
	 <td width="30%" nowrap style="overflow:hidden;">内容</td>
	 <td width="5%">相似度</td>
	 <td width="5%">操作</td>
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
	     <tr align="center"  bgcolor="#FFFFFF" class="linkblueor12">
	        <td><input type="checkbox" name="checkitem1" value="${recordMap['item1']},${recordMap['id']}"> 
	          <a href="item!show.jhtml?id=${recordMap['item1_id']}">${recordMap['item1_code']}</a></td>
		    <td>${recordMap['item1_content']}</td>
		    <td><input type="checkbox" name="checkitem2" value="${recordMap['item2']},${recordMap['id']}"> 
		      <a href="item!show.jhtml?id=${recordMap['item2_id']}">${recordMap['item2_code']}</a></td>
			<td>${recordMap['item2_content']}</td>
			<td>
			  ${recordMap['sim']}			</td>
			<td>
			  <a href="duplicate!compare.jhtml?ids=${recordMap['item1']},${recordMap['item2']}" target="_blank">对比</a>			</td>
		</tr>
        </c:forEach> 
</table>
</td>
</tr>
<tr>
	<td>
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
      <tr>
        <td colspan="2" align="left"><table>
          <tr class='ReporterRow_level2'>
            <td colspan='2' style='background-color:#fff;padding-left:8px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'>
                <tr>
                  <td bgcolor='#FFFFFF' class='TdRight'> 共有 <%=total%> 记录 <%=totalPage%>页 <a href='javascript:page(1)'>首页</a> | <a href='javascript:page(<%=previousPage%>)'>上一页</a> | <a href='javascript:page(<%=nextPage%>)'>下一页</a> | <a href='javascript:page(<%=totalPage%>)'>末页</a>
                      <input id='pageNo' name='pageNo' type='text' style='width:34px;' align='absmiddle' maxlength='5' value='<%=pageNo%>'>
                    <input name='go' type='button' id='go' value='GO' align='absmiddle' onclick='page(-1)'>

                   </td>
                </tr>
            </table></td>
          </tr>
          <script>function page(toPageNo){if(toPageNo!='-1'){document.getElementById('pageNo').value = toPageNo;}if(!isPlus(document.getElementById('pageNo').value)){alert('请您输入大于0的数字');return;}if(document.getElementById('pageNo').value > <%=totalPage%>){alert('您输入的页数已经超过最大页数，最大页数是<%=totalPage%>');return;}document.getElementById('pageForm').submit();}function isPlus(value){ValidationExpression=/^[0-9]+$/;if (ValidationExpression.test(value))return true;return false;}</script>
        </table></td>
        </tr>
  	<%
        }
  	%>
      <tr>
      	<td align="left">
      		<input type="button" value="批量删除" class="btn_2k3" onClick="batchdelete()"/>      	</td>
		<td class="ReporterCol_2" align="right"></td>
      </tr>
    </table>
	</td>
</tr>
</table>
</form>
</body>
</html>