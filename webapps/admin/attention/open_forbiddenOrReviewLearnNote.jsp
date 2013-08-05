<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String learnNoteId = request.getParameter("learnNoteId");
	String userId = request.getParameter("userId");
	String type=(String)request.getParameter("type");
	request.setAttribute("type",type);
	if("show".equals(type)){
		request.setAttribute("allButton","恢复此用户所有笔记");
		request.setAttribute("sigleButton","恢复此条笔记");
	}else{
		request.setAttribute("allButton","屏蔽此用户所有笔记");
		request.setAttribute("sigleButton","屏蔽此条笔记");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="../../mpc/css/style_blue.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>屏蔽或恢复</title>
<style>
body,form, div {
	padding:0;
	margin:0;
}
</style>
<script type="text/javascript" src="../attention/js/m.js"></script> 
<script type="text/javascript" src="../../admin/js/jquery.form.js"></script>
<script>
$(function(){
	$("form").submit(function(){
		var _info = $(this).attr("_info")||"";
		var opt  = {
			dataType :"html"
			,url: $(this).attr("action")
			,success: function(html){
				alert(_info+" 保存成功");
				try {tb_remove(); window.frames("learnNote_iframe").location.reload(true);  }catch(e){}
				try{swap("${learnNoteId}","${type}");}catch(e){}
			} 
			,error:function(e){
				alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));					
			}
		};
		$(this).ajaxSubmit(opt);
		return false;
	})
	 
})
</script>

</head>
<body>
&nbsp;&nbsp;
<div id="contentLayout" style="width:95%">
	<!--Satr left-->
	<div class="content_left">
	<div class="content_bg">
	<!--yuanjiao-->
	<div class="yr_bg2">
	<div class=ye_r_t>
	<div class=ye_l_t>
	<div class=ye_r_b>
	<div class=ye_l_b>
		<div  class="content">
			<c:choose>
			<c:when test="${'show'==type}">
					请恢复显示或取消
			</c:when>
			<c:otherwise>
					 请选择屏蔽类别或取消<br />
					1: "屏蔽此条笔记"将屏蔽当前这一条笔记 <br />
					2: "屏蔽此用户所有笔记”将屏蔽此用户所有笔记，请慎重操作<br/>
					3: "取消"将退出此对话框<br/>									 
			</c:otherwise>
			</c:choose>	
		</div>
		<div  class="content">
		<table>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;
					<form action="attentionAdmin!forbiddenOrReviewLearnNote.jhtml?p.para.learnNoteId=${param.learnNoteId}&p.para.type=${param.type}"
						 method="post" target="_parent">
						<input type="submit"  value="${sigleButton}" />
					</form>
				</td>
				<td>&nbsp;&nbsp;&nbsp;
					<form action="attentionAdmin!forbiddenOrReviewLearnNote.jhtml?p.para.userId=${param.userId}&p.para.type=${param.type}" 
					method="post" target="_self">
						<input type="submit"  value="${allButton}"/>
					</form>
				</td>
				<td>&nbsp;&nbsp;&nbsp;
					<form>
						<input type="button"  value="取消" onclick="tb_remove()"/>
					</form>
				</td>
		</table>
		</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
</div>
</body>
</html>