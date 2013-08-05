<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
<OBJECT
      ID=MathPlayer
      CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"
>
</OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关注</title>
<link href="css/style_blue.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../attention/js/m.js"></script>
<%--<script type="text/javascript" src="../attention/js/GongShiEdit.js"></script>--%>
<script type="text/javascript" src="../../admin/js/jquery.form.js"></script>
<script type="text/javascript" src="../attention/js/addOrEdit.js"></script>
</head>
<body>
<c:if test="${item!=null}">
<form action="../attention/attention!saveOrUpdate.jhtml" method="post" id="addOrEditForm">
	<input type="hidden" name="p.para.itemId" value="${item.id}" id="itemId_i"/>
	<input type="hidden" name="p.para.evaluationId" value="${evaluation.id}"/>	
	<input type="hidden" name="p.para.learnNoteId" value="${learnNote.id}"/>
	<input type="hidden" name="p.para.tagContentId" value="${tagContent.id}"/>
	<input type="hidden" name="p.para.itemAttentionId" value="${itemAttention.id}"/>
	<input type="hidden" name="p.para.historyTestStatusId" value="${historyAnswerStatus.historyTestStatus.id}"/>
	<div class="floatBox">
		<div class="floatBox_tt"><b><img src="../attention/images/evaluation_ico.gif" width="16" height="16" align="absmiddle" />评价此题</b><span class="cDGray">点选你认为合适的评价，可以不选。</span></div>
		<div class="floatBox_care">
			<ul>
				<c:forEach items="${evaluationLst}" var="e" varStatus="eStatus">
					<c:set var="checked" value=""/>
					<c:if test="${e.k==evaluation.evaluation}">
						<c:set var="checked" value="checked='checked'"/>
					</c:if>
					<li>
						<input name="p.para.evaluation" type="radio" value="${e.k}" ${checked} />
						<c:choose>
						<c:when test="${e.k=='1'}"> <img src="../attention/images/good.gif" /> </c:when>
						<c:when test="${e.k=='-1'}"> <img src="../attention/images/Poor.gif" /> </c:when>
						</c:choose>
						${e.v} </li>
				</c:forEach>
			</ul>
		</div>
		<div class="blank6"></div>
		<div class="floatBox_tt"><b><img src="../attention/images/tag_ico.gif" width="16" height="16" align="absmiddle" />个性标签：</b>&nbsp;&nbsp;
			<input name="p.para.tags" type="text" value="${tagContent.tag}" id="i_tags"  class=" box1 cDGray"/>
			<span class="cDGray">多个标签以空格或逗号隔开</span></div>
		<div class="floatBox_tt"><b><img src="../attention/images/tags_ico.gif" width="16" height="16" align="absmiddle" />此题常用标签：</b><span> 
		<c:forEach items="${recentTagSet}" var="tagStr" varStatus="tagStrStatus">
		<span class="itag" style="cursor:pointer">${tagStr}</span>&nbsp;<a href="../tag/tag.show.jhtml?p.para.tagId=${tagStr}"></a>
		</c:forEach>
		 </span></div>
		<div class="blank6"></div>
		<div class="floatBox_tt"><img src="../attention/images/note_ico.gif" width="16" height="14" align="absmiddle" /><b>学习笔记</b>
		<c:if test="${learnNote.state==1}">（<img src="../attention/images/Digest_ico.gif" align="absmiddle" /><span class="cDGray">对精华笔记有贡献</span>）</c:if>		
		</div>
		
		<%--<textarea class="edit" rows="2" style="border-style:none;display:none;width:500px"></textarea>
		<br />
		<input class="insert" type="button" value="插入公式" style="display:none"/>
		<input class="save" type="button" value="保存" style="display:none"/>
		<div class="show" style=" border-color:#666; height:60px;width:400px" id="realContent">
		${learnNote.content==null||learnNote.content==""?'好记性不如烂笔头，快记下对此题的体会吧！':learnNote.content}
		</div>
		<div class="gongshiInputDiv box1" style="height:80px" ></div>
		--%>
		<textarea class="realTextarea padding10px box1 cDGray" name="p.para.content" style="width:500px;display:" rows="2" id="realTextarea" >${learnNote.content==null||learnNote.content==""?'好记性不如烂笔头，快记下对此题的体会吧！':learnNote.content}</textarea>
		<c:if test="${learnNote.isShare==1}">
			<c:set var="isShareChecked" >
				checked="chekced"
			</c:set>
		</c:if>
		<h3><input type="checkbox" name="p.para.isShare" value="1" ${isShareChecked} id="shareCheckBox"/>共享此笔记（可获得积分奖励，并有机会成为精华）</h3>
		
		<c:choose>
		<c:when test="${learnNote.state==1}"><h3><b>管理员回复</b>：你笔记的部分内容被引用成为精华笔记。作为贡献人，你因此获得50积分奖励。</h3></c:when>
		<c:when test="${learnNote.state==-2}"><h3><b>管理员回复</b>：<font color="#FF0000">你的所有笔记已经被管理员屏蔽。</font></h3></c:when>
		<c:when test="${learnNote.state==-1}"><h3><b>管理员回复</b>：<font color="#FF0000">此条笔记已被管理员屏蔽</font></h3></c:when>
		</c:choose>
		<div class="blank6"></div>
		<div class="btn"> <span class="bbs1">
			<button class="bb1" type="submit" id="sub_i">提交</button>
			</span> <c:if test="${itemAttention!=null && itemAttention.state != -1}"><span class="bbs1">
			<button class="bb1" t="iaLogicDel" itemId=${item.id} >不再关注此题</button>
			</span></c:if> <span class="gbs1">
			<button class="gb1" t='hide' onclick="try{window.tb_remove()}catch(e){};try{window.parent.tb_remove()}catch(e){};">取消</button>
			</span>
			<div class="clear"></div>
		</div>
	</div>
</form>
</c:if>
<c:if test="${item==null}">
<div id="contentLayout" >
	<div class="content_left ">
		<div class="content_bg">
			<div class="yr_bg2">
				<div class=ye_r_t>
					<div class=ye_l_t>
						<div class=ye_r_b>
							<div class=ye_l_b></div>
							<div class=ye_l_b><br />
								<br />
								<table align="center">
									<tr>
										<td>读取相关数据时出现问题，请从其它入口进行操作，<a onclick="try{window.tb_remove()}catch(e){};try{window.parent.tb_remove()}catch(e){};" style="cursor:pointer">关闭</a></td>
									</tr>
								</table>
								<br />
								<br />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</c:if>
</body>
</html>
