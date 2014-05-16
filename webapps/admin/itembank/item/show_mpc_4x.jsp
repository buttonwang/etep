<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<HEAD>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<style>
#INTRO_DIV {
	MARGIN-RIGHT: auto;
	MARGIN-LEFT: auto;
	height:200px;
	background:#F00;
	width:400px;
	vertical-align:middle;
	line-height:200px;
}
</style>
<script type="text/javascript" src="../js/common_new.js"></script>
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/jQuery.floatdiv.js"></script>
<script type="text/javascript" src="../js/admin.js"></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="../js/math_oneToMany.js"></script>
<script type="text/javascript" src="../js/computeTotal.js"></script>
<script type="text/javascript">	
try{
	$(function(){
		var t = new ComputeId2ToId1("id1","id2");
		var	g = new G();
		var showTableWithSaveButton = function (){
			$("#tableWithSaveButton").show();
		}
		var	isEdit = g.urlParam("isEdit","?isEdit=${isEdit}")||"";
		if(isEdit=="true"){
			$("span[_EshowFckId!=]").each(function(){
				if($(this).html()!="添加子题"){
					$(this).trigger("click");
				}
			});
			showTableWithSaveButton();
		}
		$("span").click(function(e){
			if($.trim($(this).html())=="修改"){
				showTableWithSaveButton();
			}
		})
		//答案及子题答案自动计算
		try{
			var subItemIds =[<c:forEach items="${item.subItems}" var="subItem" varStatus="itemStatus">
								<c:if test ="${itemStatus.index>0}">,</c:if>'score_${subItem.id}'
								</c:forEach>
			];
			new ComputeTotal({totalId:"score_item", showSplitId:"score2_item",ids:subItemIds.join(",")});
			<c:forEach items="${item.subItems}" var="subItem" varStatus="itemStatus">
			new ComputeId2ToId1( "score_${subItem.id}","score2_${subItem.id}");
			</c:forEach>
			new ComputeId2ToId1("score_newSubItem","score2_newSubItem");
		}catch(e){
		}
		$("form").submit(function( ){
			var _info = $(this).attr("_info")||"";
			var opt  = {
				dataType :"html"
				,url:   $(this).attr("action")
				,success: function(html){ 
					if(html.indexOf("isSuccess")>0){
						//$("#INTRO_DIV").append(_info+" 保存成功<br>");
						alert(_info+" 保存成功");
					}else{
						alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));
						//$("#INTRO_DIV").append([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("<br>"));
					}
					//$("#INTRO_DIV").show().focus();
					//setTimeout (' $("#INTRO_DIV").trigger("click") ', 5000);
				} 
				,error:function(e){
					alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));
					//$("#INTRO_DIV").append(_info+" 保存失败！！！  <br> <br>1.请检查输入项是否符合要求<br>2.请检查是否超时 <br>3.网络是否连通.<br>");
				}
			};
			$(this).ajaxSubmit(opt); 
			return false; 
		})
	})
}catch(e){
}

function getKP() {
	var url = "./knowledgePoint!list.jhtml?queryType=sw&subject_code=${item.subject.code}&code="
	     + document.getElementById('knowledgePointCodes').value;
    var options = "height=600, width=500, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, "
         + "location=no, status=yes,top=100,left=300";
	window.open(url, 'newWindow', options);
}
</script>
</head><body>
<div id="isSuccess" style="display:none">true</div>
<form id="pageForm" method="post" action="itemOneForm!saveAll.jhtml"  _info="试题" >
<input type="hidden" value="oneToMany_itemOneForm" name="p.para.type">
<input type="hidden" name="p.para.isEdit" value="${isEdit}"/>
<div id=INTRO_DIV style='z-index:32;position:absolute;width:100px;height:75px;background-color:#BEDAF1;line-height:20px;text-align:left;display:none' align='center'> </div>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：题库 &gt; 试题管理 &gt; 试题详情</td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
				<tr>
					<td width="50%" align="left"  class="txt12blueh">试题信息</td>
					<td width="50%" align="right" class="txt12blue" ><span  style="cursor:hand"  onClick="show('edit_table1');hide('show_table1');" _t= "replaceText" _xg='replaceItemText();' _EshowFckId=edit_table1>修改</span>| <span  style="cursor:hand"  onClick="show('add_subitem_label','add_subitem');$E('add_subitem').focus();" _t= "replaceText" _xg='replaceSubItem();' _EshowFckId=add_subitem>添加子题</span>| <span  style="cursor:hand"  onClick="show('show_table1');hide('edit_table1');" >显示</span>| <span  style="cursor:hand"  onClick="hide('show_table1','edit_table1');">隐藏</span> </td>
				</tr>
			</table>
			<table id="show_table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  	cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF">${item.code}</td>
					<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"><c:forEach items="${statusLst}" var="status"  >
							<c:if test="${item.status==status.v}"><font color="#ff0000">${status.n}</font></c:if>
						</c:forEach>					</td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题题型：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"><c:forEach items="${itemTypeList}" var="type" varStatus="itemStatus">
							<c:if test="${item.itemType.code==type.code}">(${type.code}) 
								${type.name} </c:if>
						</c:forEach>
					</td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"><!--所属学科  -->
						<c:forEach items="${subjectList}" var="item" varStatus="itemStatus">
							<c:if test="${subject_code==item.code}">${item.name}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF">${item.sourceName}</td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${applicableObjectList}" var="obj" varStatus="applicableObjectStatus">
							<c:if test="${item.applicableObject==obj.value}"> ${obj}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${regionList}" var="region" varStatus="itemStatus">
							<c:if test="${item.region.code==region.code}"> ${region.name}</c:if>
						</c:forEach>
					</td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.year} </td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.validityValue} </td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度 ：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.itemValue} </td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.abilityValue} </td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.sourceBook} </td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.sourceFile} </td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.originalPaperCode} </td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.originalItemNum} </td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.score} </td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.difficultyValue} </td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF"> ${item.opinion} </td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF" id=show_itemKnowledgePointNames></td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含次知识点：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF" id=show_itemKnowledgePoint2Names></td>
				</tr>
				<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF"> ${item.answeringTimeByMin}&nbsp;分钟</td>
					<td align="right"  bgcolor="#F7F7F7" class="txt12blue">导入文件：</td>
					<td align="left" bgcolor="#FFFFFF"><a href="/resource/mpc/${itemVO.importFile}">${itemVO.importFile} </a></td>
				</tr>	
				<tr>					
					<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">试题版本：</td>
					<td width="33%" align="left"  bgcolor="#FFFFFF">
						<jsp:include page="item_courseVersion.jsp?t=edit"/>
					</td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">复习轮次：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF">${item.reviewRound}轮	</td>
				</tr>
				<tr>
					<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>
					<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${item.content}</td>
				</tr>
			</table>
			<table id="edit_table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
	  			cellpadding="6" cellspacing="1" bgcolor="#E3E3E3"  style="display:none">
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题编码：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF" ><input type="hidden" name="itemVO.id" size="30" value="${item.id}"/>
							<input type="text" name="itemVO.code" size="30" value="${item.code}" _READONLY="readonly"/>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">状态：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><c:forEach items="${itemStatus}" var="status" varStatus="itemStatus">
								<c:if test="${item.status==status.value}"><font color="#ff0000">${status}</font></c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题题型：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF">
						<select name="itemVO.itemType.code">
							<c:forEach items="${itemTypeList}" var="i" varStatus="itemStatus">
								<option value="${i.code}" ${item.itemType.code eq i.code ? 'selected="selected"':''}>(${i.code})${i.name}</option>
							</c:forEach>
						</select>
						 
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">所属学科:</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><!--所属学科  -->
							<c:forEach items="${subjectList}" var="subject" varStatus="itemStatus">
								<c:if test="${subject_code==subject.code}"> ${subject.name} </c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><select name="itemVO.source" style="width: 100px">
								<c:forEach items="${itemSourceList}" var="source" varStatus="itemStatus">
									<option value="${source.value}" ${item.source eq source.value ? 'selected="selected"':''}>${source}</option>
								</c:forEach>
							</select>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><c:forEach items="${applicableObjectList}" var="applicableObject" varStatus="itemStatus">
							<input name="itemVO.applicableObject"  type="radio" ${fn:contains(item.applicableObject, applicableObject.value)?'checked="checked"':''} 
    			 				value="${applicableObject.value}" />
								${applicableObject}&nbsp; </c:forEach>
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><select name="itemVO.region.code" style="width: 100px">
								<c:forEach items="${regionList}" var="region" varStatus="itemStatus">
									<option value="${region.code}" ${item.region.code eq region.code ? 'selected="selected"':''}>${region.name}</option>
								</c:forEach>
							</select>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.year" value="${item.year}"/>
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.validityValue" value="${item.validityValue}"/>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">价值度 ：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.itemValue" value="${item.itemValue}"/>
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">能力要求：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.abilityValue" value="${item.abilityValue}"/>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源书目：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.sourceBook" value="${item.sourceBook}"/>
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题来源文件：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.sourceFile" value="${item.sourceFile}"/>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始套卷编码：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.originalPaperCode" value="${item.originalPaperCode}" />
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">原始题号：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.originalItemNum" value="${item.originalItemNum}"/>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题分值：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.score" value="${item.score}" size="10"  id="score_item" />
							&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.difficultyValue" value="${item.difficultyValue}" size="10"/>
						</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">各子题分数:</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.score2" disabled value="${item.score2}" size="20" id="score2_item" /></td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><a href="/resource/mpc/${itemVO.importFile}">${itemVO.importFile}</a></td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">直观评价：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="itemVO.opinion" value="${item.opinion}"/></td>
					</tr>
					<tr>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含主知识点：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF" >
						<input type="text" id="knowledgePointNames" value="" size="30" />
						<input type="text" id="knowledgePointCodes" name="p.para.itemKnowledgePointCodes" style="display:none" value=""/>&nbsp;&nbsp;
						<input type="button" value="选择" class="btn_2k3" onClick="getKP()"/>
					</td>
					<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">包含次知识点：</td>
					<td width="33%" align="left" bgcolor="#FFFFFF">
					<input  type="text" size="30" id="itemKnowledgePoint2Names" value=""/>
					<input name="p.para.itemKnowledgePointCodes2" type="text" size="30" id="itemKnowledgePoint2Codes" style="display:none"  readonly="true" value="" class="logininputmanage"/>
								&nbsp;&nbsp;
					<input type="button" value="选择" class="btn_2k3" onClick="getList('knowledgePoint','itemKnowledgePoint2Names','itemKnowledgePoint2Codes',event);"/> 
					
					</td>
				</tr>
				<script type="text/javascript">
							<c:set var ="codes_i" value="0"/>
							$E('knowledgePointCodes').value = [<c:forEach items="${item.knowledgePoints}"  var="knowledgePoint"><c:set var ="codes_i" value="${codes_i+1}"/><c:if test="${codes_i>1}">,</c:if>'${knowledgePoint.code}'</c:forEach>].join(",");
							<c:set var ="names_i" value="0"/>
							$E('show_itemKnowledgePointNames').innerHTML=$E('knowledgePointNames').value= [<c:forEach items="${item.knowledgePoints}" var="knowledgePoint"><c:set var ="names_i" value="${names_i+1}"/><c:if test="${names_i>1}">,</c:if>'${knowledgePoint.name}'</c:forEach>].join(",");
							<c:set var ="codes_i2" value="0"/>
							$E('itemKnowledgePoint2codes').value=[<c:forEach items="${item.knowledgePoints2}"  var="knowledgePoint2"><c:set var ="codes_i2" value="${codes_i2+1}"/><c:if test="${codes_i2>1}">,</c:if>'${knowledgePoint2.code}'</c:forEach>].join(",");
							<c:set var ="names_i2" value="0"/>
							$E('show_itemKnowledgePoint2Names').innerHTML=$E('itemKnowledgePoint2Names').value= [<c:forEach items="${item.knowledgePoints2}" var="knowledgePoint2"><c:set var ="names_i2" value="${names_i2+1}"/><c:if test="${names_i2>1}">,</c:if>'${knowledgePoint2.name}'</c:forEach>].join(",");
				</script>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">答题用时：</td>
						<td width="33%" align="left"  bgcolor="#FFFFFF">
							<input class="logininputmanage" type="text" name="itemVO.answeringTimeByMin" size="10" 
								value="${item.answeringTimeByMin}" readonly/>&nbsp;分钟
						</td>
						<td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">试题版本：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><jsp:include page="item_courseVersion.jsp?t=edit"/></td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">复习轮次：</td>
						<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">
							<input type="checkbox" name="reviewRound"  
							<c:forEach var="selected" items="${selectedReviewRound}" >
								<c:if test="${selected==0 }">checked</c:if>
							</c:forEach> 
							value="0" />
							不限&nbsp; 
							<input type="checkbox" name="reviewRound" 
							 <c:forEach var="selected" items="${selectedReviewRound}" >
								<c:if test="${selected==1 }">checked</c:if>
							</c:forEach>
							  value="1" />一轮&nbsp;
							<input type="checkbox" name="reviewRound" 
							<c:forEach var="selected" items="${selectedReviewRound}" >
								<c:if test="${selected==2 }">checked</c:if>
							</c:forEach>
							 value="2" />二轮
						</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">创建人：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${item.creater}</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${item.createdTime}</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新人：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${item.updater}</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">更新时间：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${item.updatedTime}</td>
					</tr>
					<tr>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核人：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${item.verifier}</td>
						<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">审核时间：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${item.verifiedTime}</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">题干：</td>						
						<td width="83%" align="left" colspan="3" bgcolor="#FFFFFF">
							<textarea fck=fck name="itemVO.content"  cols="90" rows="6">${item.content}</textarea>
						</td>						
					</tr>
			 
			</table></td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<!--子题21开始-->
	<c:set var="itemCount" value="1"></c:set> 
	<c:forEach items="${item.subItems}" var="subItem">
				<c:set var="i" value="${itemCount-1}"></c:set>
			<tr>
				<td><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  class="tilte_bg">
						<tr>
							<td  width="50%" align="left" class="txt12blueh">子题 ${itemCount} </td>
							<td align="right"  class="txt12blue"><span style="cursor:hand" onClick="show('edit_subitem_${itemCount}');hide('show_subitem_${itemCount}');" _t= "replaceText" _xg="replaceSubItemText('${itemCount}');" _EshowFckId="edit_subitem_${itemCount}">修改</span>| <span style="cursor:hand" onClick="if (confirm('确定要删除吗？')) location.href='item!deleteSubItem.jhtml?subItem.id=${subItem.id}';">删除</span>| <span style="cursor:hand; display: none"  onClick="show('add_option_${itemCount}');$E('add_option_${itemCount}').focus();" >添加答案选项|</span> <span style="cursor:hand" onClick="show('show_subitem_${itemCount}','option_${itemCount}');hide('edit_subitem_${itemCount}');">显示</span>| <span style="cursor:hand" onClick="hide('show_subitem_${itemCount}','edit_subitem_${itemCount}');">隐藏</span> </td>
						</tr>
					</table>
					<table id="show_subitem_${itemCount}" class="txt12555555line-height"  width="100%" border="0" align="center"
 			 cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" >
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题原始题号：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.originalSubItemNum}</td>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题难度：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"> ${subItem.difficultyValue}</td>
						</tr>
						<tr>
							<td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">子题顺序号：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF">${subItem.orderNum}</td>
							<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">包含主知识点：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"  id="show_knowledgePoint_names_${subItem.id}">${_knowledgePointNames}</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">分值：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"> ${subItem.score} </td>
							<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">包含次知识点：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"  id="show_knowledgePoint2_names_${subItem.id}">${_knowledgePoint2Names}</td>
						</tr>
						
						<tr>
						 	<td width="17%" align="right" bgcolor="#F7F7F7"  class="txt12blue">各子答案分值：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"> ${subItem.score2} </td>
							<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">做题时间：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"> ${subItem.answeringTimeByMin}&nbsp;分钟</td>
						</tr>
						<tr>
							<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">题干：</td>
							<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${subItem.content }</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题答案：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><Table >
									<c:forEach items="${subItem.answers}" var="correctAnswer" varStatus="status">
										<Tr >
											<td width="20%" align="right"> ${status.count} </td>
											<td width="80%" style="padding-left:30px">
											
												<span style="border-bottom:1px solid #000000;"><c:if test="${correctAnswer.type eq 'gongShi'}">
											&nbsp;&nbsp;${correctAnswer.value}</c:if>
											<c:if test="${correctAnswer.type eq 'text'}">
											&nbsp;&nbsp; ${fn:replace(correctAnswer.value,"<","&lt;")}
											</c:if></span> </td>
										</Tr>
									</c:forEach>
								</Table></td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答案分组：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.answerGroup} </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答案原型：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.answerPrototype} </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">提示：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.hint } </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答题与技巧：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.skills} </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解1：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.analysisAtLarge1 } </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解2：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.analysisAtLarge2 } </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解3：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.analysisAtLarge3 } </td>
						</tr>
					</table>
					<table id="edit_subitem_${itemCount}" class="txt12555555line-height"  width="100%" border="0" align="center"
      cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" style="display:none">						
						<input type="hidden" name="itemVO.subItems[${i}].id" value="${subItem.id}"/>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题原始题号：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"><input type="text" name="itemVO.subItems[${i}].originalSubItemNum" value="${subItem.originalSubItemNum}" class="logininputmanage"/>
							</td>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题难度：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"><input type="text" name="itemVO.subItems[${i}].difficultyValue" value="${subItem.difficultyValue}" class="logininputmanage"/>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题顺序号：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"><input type="text" name="itemVO.subItems[${i}].orderNum" value="${subItem.orderNum}" class="logininputmanage"/>
							</td>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">包含主知识点：</td>
							<td  width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" size="30" id="knowledgePoint_edit${subItem.id}Names" value=""/>
								<input name="p.para.subItemKnowledgePointCodes${subItem.id}" type="text" size="30" id="knowledgePoint_edit${subItem.id}Codes" style="display:none"  readonly="true" value="" class="logininputmanage"/>
								&nbsp;&nbsp;
								<input type="button" value="选择" class="btn_2k3" onClick="getList('knowledgePoint','knowledgePoint_edit${subItem.id}Names','knowledgePoint_edit${subItem.id}Codes',event);"/>							 	 
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">分值：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage"  type="text" name="itemVO.subItems[${i}].score"  value="${subItem.score}" id="score_${subItem.id}" />
								&nbsp;&nbsp; </td>
							<td width="17%" align="right" bgcolor="#F7F7F7" class="txt12blue">包含次知识点：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"><input  type="text" size="30" id="knowledgePoint2_edit${subItem.id}Names" value=""/>
								<input name="p.para.subItemKnowledgePointCodes2${subItem.id}" type="text" size="30" id="knowledgePoint2_edit${subItem.id}Codes" style="display:none"  readonly="true" value="" class="logininputmanage"/>
								&nbsp;&nbsp;
								<input type="button" value="选择" class="btn_2k3" onClick="getList('knowledgePoint','knowledgePoint2_edit${subItem.id}Names','knowledgePoint2_edit${subItem.id}Codes',event);"/></td>
							
						</tr>
						<script type="text/javascript">
							<c:set var ="codes_i" value="0"/>
							$E('knowledgePoint_edit${subItem.id}Codes').value=[<c:forEach items="${subItem.knowledgePoints}"  var="knowledgePoint"><c:set var ="codes_i" value="${codes_i+1}"/><c:if test="${codes_i>1}">,</c:if>'${knowledgePoint.code}'</c:forEach>].join(",")
							<c:set var ="names_i" value="0"/>
							$E('show_knowledgePoint_names_${subItem.id}').innerHTML =$E('knowledgePoint_edit${subItem.id}Names').value= [<c:forEach items="${subItem.knowledgePoints}" var="knowledgePoint"><c:set var ="names_i" value="${names_i+1}"/><c:if test="${names_i>1}">,</c:if>'${knowledgePoint.name}'</c:forEach>].join(",")
							 
							
							<c:set var ="codes_i2" value="0"/>
							$E('knowledgePoint2_edit${subItem.id}Codes').value=[<c:forEach items="${subItem.knowledgePoints2}"  var="knowledgePoint2"><c:set var ="codes_i2" value="${codes_i2+1}"/><c:if test="${codes_i2>1}">,</c:if>'${knowledgePoint2.code}'</c:forEach>].join(",")
							<c:set var ="names_i2" value="0"/>
							$E('show_knowledgePoint2_names_${subItem.id}').innerHTML=$E('knowledgePoint2_edit${subItem.id}Names').value= [<c:forEach items="${subItem.knowledgePoints2}" var="knowledgePoint2"><c:set var ="names_i2" value="${names_i2+1}"/><c:if test="${names_i2>1}">,</c:if>'${knowledgePoint2.name}'</c:forEach>].join(",")
						</script>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">各子答案分值：</td>
							<td align="33%"  bgcolor="#FFFFFF" ><input class="logininputmanage"  type="text" name="itemVO.subItems[${i}].score2" value="${subItem.score2}" id="score2_${subItem.id}"/></td>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">做题时间：</td>
							<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage"  type="text" name="itemVO.subItems[${i}].answeringTimeByMin" value="${subItem.answeringTimeByMin}"/>
								&nbsp;分钟 </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">题干：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="itemVO.subItems[${i}].content" cols="90" rows="8">${subItem.content}</textarea>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题答案：</td>								
							<td align="left"  bgcolor="#FFFFFF" colspan="3"  >
							<c:set var = "subItem_id" value="${subItem.id}"/>
							<c:set var = "toServerName" value="p.para.subItemCorrectAnswers${subItem.id}"/>
							<Table>
								<Tr>
									<td width="100%" colspan="2"><input type="button" value="增加公式型答案" onClick="addRow('${subItem_id}',1,'${toServerName}')"  class="btn_2k3" />
										&nbsp;&nbsp;
										<input type="button" value="增加纯文本型答案" onClick="addRow('${subItem_id}',0,'${toServerName}')"  class="btn_2k3" />
										&nbsp;&nbsp; </td>
								</Tr>
							</Table>
							<Table id='subItemAnswerTable_${subItem_id}' width="100%">
								<c:set var="i_subItemcorrectAnswer" value="0"/>
								<c:forEach items="${subItem.answers}" var="correctAnswer" varStatus="status">
									<c:set var="i_subItemcorrectAnswer" value="${i_subItemcorrectAnswer+1}"/>
									<c:if test="${correctAnswer.type eq 'gongShi'}">
										<Tr>
											<td width="5%" > ${i_subItemcorrectAnswer} </td>
											<td width="50%"><textarea id="textarea_${i_subItemcorrectAnswer}_${subItem_id}" name="${toServerName}" style="display:none">${correctAnswer.value}</textarea>
											<script>$(function(){$("#textarea_${i_subItemcorrectAnswer}_${subItem_id}").val("${correctAnswer.value}");})</script>
												<span style="border-bottom:1px solid #000000;" > <span id="span_${i_subItemcorrectAnswer}_${subItem_id}">&nbsp;&nbsp;${correctAnswer.value}&nbsp;&nbsp; </span></span> </td>
											<td width="20%" bgcolor="#F7F7F7" align="left"><a style="cursor:pointer" onClick="getFormulator('${subItem_id}','${i_subItemcorrectAnswer}');">公式修改</a>&nbsp;&nbsp; <a style="cursor:pointer" onClick="deleteLine(this.parentNode.parentNode);LoadMath_resetId('${subItem_id}')">删除</a> </Td>
											<td width="25%" bgcolor="#F7F7F7" align="left"><input type="button" value="前插公式" onClick="addRow('${subItem_id}',1,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
												&nbsp;&nbsp;
												<input type="button" value="前插文本" onClick="addRow('${subItem_id}',0,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
												&nbsp;&nbsp; </Td>
										</Tr>
									</c:if>
									<c:if test="${correctAnswer.type eq 'text'}">
										<Tr >
											<td width="5%"> ${i_subItemcorrectAnswer} </td>
											<td width="50%"><textarea type="text" size="50" name="${toServerName}">${correctAnswer.value}</textarea>
											</td>
											<td width="20%" bgcolor="#F7F7F7" align="left"><a style="cursor:pointer" onClick="deleteLine(this.parentNode.parentNode);LoadMath_resetId('${subItem_id}');">删除</a> </Td>
											<td width="25%" bgcolor="#F7F7F7" align="left"><input type="button" value="前插公式" onClick="addRow('${subItem_id}',1,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
												&nbsp;&nbsp;
												<input type="button" value="前插文本" onClick="addRow('${subItem_id}',0,'${toServerName}',this);LoadMath_resetId('${subItem_id}')"  class="btn_2k3" />
												&nbsp;&nbsp; </Td>
										</Tr>
									</c:if>
								</c:forEach>
							</Table></td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答案分组：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><input class="logininputmanage" name="itemVO.subItems[${i}].answerGroup"  value="${subItem.answerGroup}" /> 
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答案原型：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"> ${subItem.answerPrototype} </td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答题与技巧：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><input class="logininputmanage" name="itemVO.subItems[${i}].skills" valie="${subItem.skills}" size="40"/>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">提示：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="itemVO.subItems[${i}].hint" cols="90" rows="8">${subItem.hint}</textarea>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解1：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="itemVO.subItems[${i}].analysisAtLarge1" cols="90" rows="8">${subItem.analysisAtLarge1}</textarea>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解2：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="itemVO.subItems[${i}].analysisAtLarge2" cols="90" rows="2">${subItem.analysisAtLarge2}</textarea>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解3：</td>
							<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="itemVO.subItems[${i}].analysisAtLarge3" cols="90" rows="2">${subItem.analysisAtLarge3}</textarea>
							</td>
						</tr>
						<!--			
				 
			-->
					</table>
					<!--子题21选项结束-->
				</td>
			</tr>
			<c:set var="itemCount" value="${itemCount+1}"></c:set>
		</c:forEach>
	<!--子题21结束-->
	<tr height="3">
		<td></td>
	</tr>
</table>
<table width="100%" id="tableWithSaveButton"  style="display:none">
	<tr>
		<td height="70" colspan="4" align="center" ><input type="submit" value="  保 存  " class="btn_2k3"  id="save" /></td>
	</tr>
</table>
</form>
<!--添加子题开始-->
<table id="add_subitem_label"   width="100%" border="0" align="center" cellpadding="6" cellspacing="0" style="display:none">
	<tr bgcolor="#A5D5FC">
		<td align="left"    class="txt12blueh">添加子题</td>
		<td align="right"  class="txt12blue"></td>
	</tr>
</table>
<table id="add_subitem" class="txt12555555line-height"  width="100%" border="0" align="center"
  cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" style="display:none">
	 <form id="add_subitem_form" method="post" action="item!saveSubItem.jhtml">
	 	<input type="hidden" value="oneToMany_saveSubItem" name="p.para.type">
		<input  type="hidden" name="subItemVO.itemId" value="${item.id}"/>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题原始题号：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="subItemVO.originalSubItemNum"/>
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">子题难度：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="subItemVO.difficultyValue" />
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">子题顺序号：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage"  type="text" name="subItemVO.orderNum" />
			</td>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">包含主知识点：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF" ><input type="text"   size="30"  id="knowledgePoint_addNames" name="subItemVO.knowledgePointNames"   readonly="true" class="logininputmanage"/>
				<input type="text" id="knowledgePoint_addCodes" name="subItemVO.knowledgePointCodes" style="display:none"/>
				&nbsp;&nbsp;
				<input type="button" value="选择" class="btn_2k3"  onClick="getList('knowledgePoint','knowledgePoint_addNames','knowledgePoint_addCodes');"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">分值：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage"  type="text" name="subItemVO.score" id="score_newSubItem"/>
				&nbsp;&nbsp; </td>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">做题时间：</td>
			<td width="33%" align="left"  bgcolor="#FFFFFF"><input class="logininputmanage"  type="text" name="subItemVO.answeringTimeByMin"/>
				&nbsp;分钟 </td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">各子答案分数：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><input class="logininputmanage"  type="text" name="subItemVO.score2" id="score2_newSubItem"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">题干：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="subItemVO.content" cols="90" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">提示：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="subItemVO.hint" cols="90" rows="2"></textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答题与技巧：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><input class="logininputmanage" name="subItemVO.skills" size="40"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答案：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><Table width="100%">
					<Tr>
						<td width="100%" colspan="2"> 
							
							<input type="button" value="增加公式型答案"   onClick="addRow('newSubItem',1,'p.para.newSubItemcorrectAnswers')"  class="btn_2k3" />
							&nbsp;&nbsp;
							<input type="button" value="增加纯文本型答案" onClick="addRow('newSubItem',0,'p.para.newSubItemcorrectAnswers')"  class="btn_2k3" />
							&nbsp;&nbsp;
			
						
						</td>
					</Tr>
				</Table>
				<Table id="subItemAnswerTable_newSubItem"  width="100%">
				</Table></td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">答案原型：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="subItemVO.answerPrototype" cols="90" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解1：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="subItemVO.analysisAtLarge1" cols="90" rows="2"></textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解2：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="subItemVO.analysisAtLarge2" cols="90" rows="2"></textarea>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right"  bgcolor="#F7F7F7" class="txt12blue">详解3：</td>
			<td align="left"  bgcolor="#FFFFFF" colspan="3"><textarea fck=fck class="logininputmanage" name="subItemVO.analysisAtLarge3" cols="90" rows="2"></textarea>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF" colspan="4" class="txt12blue" align="center"><input type="submit" value="保存子题"  class="btn_2k3" > </td>			 
		</tr>
	 </form>
</table>
<!--添加子题结束-->
	<table width="100%" height="80px" p=editPage>
		<tr>
			<td align="center" style="display: none">
				<c:if test="${item.typicalExample eq null}">
					<input type="button" value="设为典型例题" class="btn_2k3" onClick="if (confirm('确定要设为典型例题吗？')) location.href='item!addTypicalExample.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
				</c:if>
				<c:if test="${item.typicalExample ne null}">
					<input type="button" value="取消典型例题" class="btn_2k3" onClick="if (confirm('确定要取消典型例题吗？')) location.href='item!delTypicalExample.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
				</c:if>	
			</td>
		</tr>
		<tr>
			<td align="center">
			<input type="button" value="  审 核  " class="btn_2k3"  onClick="if (confirm('确定要审核吗？')) location.href='item!verifyInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  作 废  " class="btn_2k3"  onClick="if (confirm('确定要作废吗？')) location.href='item!invalidInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  启 用  " class="btn_2k3"  onClick="if (confirm('确定要启用吗？')) location.href='item!activationInShowPage.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  删 除  " class="btn_2k3"  onClick="if (confirm('确定要删除吗？')) location.href='item!delete.jhtml?id=${item.id}';"/>&nbsp;&nbsp;
			<input type="button" value="  入 卷  " class="btn_2k3"  onClick="location.href='paper!choose.jhtml?itemids=${item.id}';"/>&nbsp;&nbsp;
			</td>
		</tr>
	</table>
<jsp:include page="getlist.jsp"></jsp:include>
</body>
</html>
	