<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%String nodeType = (String )request.getAttribute("nodeType");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script src="../js/all/ConfirmIF.js"></script>
<script src="../js/all/ShowHide.js"></script>
<script src="../js/all/selectTree_notParent.js"></script>
<script src="../js/all/initInput_setForm.js"></script>
<script src="../js/My2Select.js"></script>
<script>
function doSel() {
	window.open('showItem!getStudyGuideTree.jhtml?type=${categoryId}&itemType='+document.getElementById('studyGuideCodes').value,"newWindow", "height=600, width=500, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=300");
}
$(function(){
	try{
		eval('var subject_grade = ${m_userSubjectGradeRole_json}');		 
		new MySelect(subject_grade,"subjectCode,gradeCode","vt","${_kp.subject.code}","${_kp.grade.code}");
	}catch(e){
	}
})
</script>
<script>

j(function(){
	/*如果是结点组，则不能变换节点类型*/
	j("select[name=p.para.nodeType]").change(function(){
		if("${node.nodeType}"=="GROUP"){
			if(j(this).val!="GROUP"){
				alert("节点组类型不允许被改变成其它普通节点")
				j(this).val("GROUP");
			}
		}
	})
	ConfirmIF();
	j("input[type=button][value=选择模板]").click(function(e,map){
		var tType = j(this).attr("v")||"";
		if(tType=="PHASETEST"||tType=="paper_assembling_req_template"){
			window.location.href ="../policy/template.jhtml?atype=search&searchType="+tType+"&p.para.to=nodeShowAll&nid=${node.id}&tType="+tType;		 
		}else{
			window.open("../policy/template.jhtml?atype=search&searchType="+tType+"&p.para.to=nodeShowAll&nid=${node.id}&tType="+tType);
		}
	})
	j("input[opt=inherit]").click(function(){
		if(confirm("注意 此 继承 操作清除现有配置，确定要继续?")){
			window.location.href ="../policy/template.jhtml?atype=inherit&nid=${node.id}&tType="+j(this).attr("v");
			//window.open("../policy/template.jhtml?atype=inheritNew&nid=${node.id}&tType="+j(this).attr("v"));
		}
	})	
	j("#paperAssemblingMode").change(function(){
		 if(j(this).val()==0){
			j("#paperAssemblingMode_div").hide();
		 }else{
			j("#paperAssemblingMode_div").show();
		 }
	})
	ShowHide("input[opt=optSH],span[opt=optSH]");
	new ValidateForm({
		"fid":"eform"
		,"vs":[
			{n:"p.para.name",sn:"节点名称"}
		]
	});
	new initInput();
	selectTree({outSelect:"div[name=__out]"
		,dataLst:${processNodesJson==null||processNodesJson==""?"[]":processNodesJson}
		,selectName:"p.para.pid"
		,selected:"${node.nodeGroup.id}" 
		,sonKey:"nodes"
		,theId:"${node.id}"
	});
	 
	j("div[name=phaseTestNodePolicy_jumpPosition_select]").each(function(){
		var id = j(this).attr("id",j(this).attr("id")||new G().nextStrId("div_")).attr("id");
		var selectedId=j(this).attr("v")||"";
		selectTree({outId:id
			,dataLst:${processNodesJson==null||processNodesJson==""?"[]":processNodesJson}
			,selectName:"phaseTestNodePolicy.jumpPosition"
			,selected:selectedId
			,sonKey:"nodes"
			,withParentName:true
		});
	})
	j("input[name!=]").each(function(){
		j(this).val(j(this).val().replace(".0",""));
	})
})

function setpaper(paperArr){
	var paperArr = paperArr||[];
	if(paperArr.length==1){
		var p=paperArr[0];
		j("input[x!=]").each(function(){
			try{
				j(this).val((p[j(this).attr("x")]||"").replace(".0",""));
			}catch(e){
			}
		})
	}
	if(paperArr.length>1){
		var ids = "";
		var names = "";
		for(var i=0;i<paperArr.length;i++){						 
			var p=paperArr[i];
			if(i>0){
				ids+=","
				names+=","
			}
			ids += p["id"] ;
			names += p["name"];
		}
		j("input[x=id]").each(function(){j(this).val(ids);})
		j("input[x=name]").each(function(){j(this).val(names);})
		var p=paperArr[0];
		j("input[x!=id][x!=name][x!=]").each(function(){
			try{ 
				j(this).val((p[j(this).attr("x")]||"").replace(".0",""));
			}catch(e){
			}
		})
	}
}
function changeStr(inStr){
	var outStr='';
	while(inStr.indexOf(',')>-1){
		inStr=inStr.replace(',','');
		outStr+='----';
	}
	return outStr;
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 节点详情</td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
				<tr>
					<td width="50%" align="left"   class="txt12blueh">节点信息</td>
					<td width="50%" align="right"  class="txt12blue" ><span  style="cursor:hand"><a href="../studyflow/showItem.jhtml?itemType=ProcessDefinition&processDefinition.id=${node.processDefinition.id}">返回</a></span>| <span style="cursor:hand" opt=optSH v='{h:"s_node",s:"e_node"}'>修改</span>| <span style="cursor:hand"><a href="node.jhtml?atype=delete&id=${node.id}&p.para.prid=${node.processDefinition.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{s:"s_node",h:"e_node"}'>显示</span>| <span style="cursor:hand" opt=optSH v='{h:"s_node,e_node"}'>隐藏</span> </td>
				</tr>
			</table>
			<div id="s_node">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">节点名称：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${node.name}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">节点类型：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
							${node.nodeType=="GROUP"?"节点组":""}
							${node.nodeType=="PRACTICE"?"训练单元":""}
							${node.nodeType=="EVALUATE"?"模块测试":""}
							${node.nodeType=="PHASETEST"?"阶段测试":""}
							${node.nodeType=="UNITTEST"?"单元测试":""} 
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${orderNumForShow}
							<!--${node.orderNum}--></td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${node.nodeGroup.name==null?"无":node.nodeGroup.name}</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
						<td align="left" valign="top" bgcolor="#FFFFFF" colspan="3">${node.description}</td>
					</tr>
				</table>
			</div>
			<div style="display:none" id="e_node">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<form action="node.jhtml" method="post" id="eform" name="node_edit">
						<input type="hidden" name="atype" value="iupdate"/>
						<input class="logininputmanage" type="hidden" name="p.para.prid" value="${node.processDefinition.id}"  />
						<input class="logininputmanage" type="hidden" name="p.para.nid" value="${node.id}"/>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.name" value="${node.name}"/>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="p.para.nodeType" id="_select1">
									<option value="GROUP" ${node.nodeType=="GROUP"?"selected":""} >默认(节点组)</option>
									<option value="PRACTICE" ${node.nodeType=="PRACTICE"?"selected":""} >训练单元(训练)</option>
									<option value="EVALUATE" ${node.nodeType=="EVALUATE"?"selected":""} >模块测试（评测）</option>
									<option value="PHASETEST" ${node.nodeType=="PHASETEST"?"selected":""} >阶段测试(前测)</option>
									<option value="UNITTEST" ${node.nodeType=="UNITTEST"?"selected":""} >单元测试(后测)</option>
								</select></td>
						</tr>
						
						
						
						
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.orderNum" value="${orderNumForShow}" size="10" />
								<!--${node.orderNum}-->
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><div name="__out"></div></td>
						</tr>
						<tr>
							<td width="17%"  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
							<td align="left" valign="top" bgcolor="#FFFFFF" colspan="3"><textarea name="p.para.description" id="description_node" cols="35" rows="2" >${(node.description==null||node.description==" ")?"": node.description }</textarea>
							</td>
						</tr>
						<tr>
							<td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="369" border="0">
									<tr>
										<td width="363"><input type="submit" class="btn_2k3" value="  保 存  "/>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" value="  取 消  " class="btn_2k3"  opt=optSH v='{s:"s_node",h:"e_node"}'  /></td>
									</tr>
								</table></td>
						</tr>
					</form>
				</table>
			</div></td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td ><%if ("EVALUATE".equals(nodeType) ){%>
			<table width="100%" border="0" align="center" >
				<tr>
					<td ><table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
							<tr>
								<td  width="50%" align="left"    class="txt12blueh">流转策略</td>
								<td align="right"  class="txt12blue"><span  style="cursor:hand" onClick="show('table6');hide('table5');">修改</span>| <span  style="cursor:hand" onClick="">删除</span>| <span style="cursor:hand"  onclick="show('table5');" >显示</span>| <span  style="cursor:hand" onClick="hide('table5');">隐藏</span> </td>
							</tr>
						</table>
						<table id="table5" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
							<tr>
								<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">暂无</td>
								<td width="83%" align="left" bgcolor="#FFFFFF">暂无</td>
							</tr>
						</table></td>
				</tr>
			</table>
			<%}  if("PHASETEST".equals(nodeType)){%>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{t:"e_phaseTestNodePolicy_template"}'>使用模板&继承</span>| <span  style="cursor:hand" opt=optSH v='{t:"add_phaseTestNodePolicy"}'>增加条件</span>| <span  style="cursor:hand" ><a href="../policy/phaseTestNodePolicy.jhtml?atype=showNodedeleteAll&id=${node.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{h:"e_phaseTestNodePolicy",s:"s_phaseTestNodePolicy"}' >显示</span>| <span  style="cursor:hand" opt=optSH v='{h:"s_phaseTestNodePolicy,e_phaseTestNodePolicy_template,e_phaseTestNodePolicy"}' >隐藏</span> </td>
				</tr>
			</table>
			<div style="display:none" id="e_phaseTestNodePolicy_template">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr height="45">
						<td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="radio" id="radio"/>
							使用策略模板&nbsp;&nbsp;
							<input type="button" value="选择模板" class="btn_2k3" v="PHASETEST" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							${(node.nodeGroup!=null)?("
							<input type='radio' name='radio' id='radio'/>
							继承父节点策略&nbsp;&nbsp;
							<input type='button' value=' 继承 ' class='btn_2k3' opt=inherit v='PHASETEST'/>
							&nbsp;&nbsp;
							"):""} </td>
					</tr>
				</table>
			</div>
			<div style="display:none" id="e_phaseTestNodePolicy">
				<table id="table" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流转条件：</td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#E3E3E3">
								<tr align="center"  bgcolor="#FFFFFF" class="txt12blue" >
									<td width="10%">序号</td>
									<td width="10%">起始值</td>
									<td width="10%">结束值</td>
									<td width="10%">学习指导</td>
									<td width="10%">学习提示</td>
									<td width="30%">跳转位置</td>
									<td width="20%">操作</td>
								</tr>
								<c:forEach items="${v.phaseTestNodePolicyLst}" var="item" varStatus="itemStatus">
									<tr id="show_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12">
										<td>${itemStatus.index+1}</td>
										<td>${item.startValue}</td>
										<td>${item.endValue}</td>
										<td>${item.studyGuide}</td>
										<td>${item.studyTip}</td>
										<td><c:forEach items="${v.jumpToLst}" var="temp" varStatus="itemStatus"> ${(temp.id==item.jumpPosition)?(temp.name):("")} </c:forEach>
										</td>
										<td class="txt12blue"><span style="cursor:hand" onClick='javascript:j("#edit_${item.id},#show_${item.id}").toggle();'>修改</span>&nbsp; <span style="cursor:hand"><a href="../policy/phaseTestNodePolicy.jhtml?atype=idelete&id=${item.id}&phaseTestNodePolicy.asfNode.id=${node.id}">删除</a></span> </td>
									</tr>
									<tr id="edit_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
										<form action="../policy/phaseTestNodePolicy.jhtml?atype=iupdate" method="post" id="u${item.id}form" name="phaseTestNodePolicy_form">
											<td><input name="phaseTestNodePolicy.id" value="${item.id}" size="5" type="hidden"/>
												<input name="phaseTestNodePolicy.asfNode.id" value="${node.id}" size="5" type="hidden"/>
												${itemStatus.index+1}</td>
											<td><input  type="text" name="phaseTestNodePolicy.startValue" value="${item.startValue}" size="5"/></td>
											<td><input  type="text" name="phaseTestNodePolicy.endValue" value="${item.endValue}" size="5"/></td>
											<td><input  type="text" name="phaseTestNodePolicy.studyGuide" value="${item.studyGuide}" size="5"/></td>
											<td><input  type="text" name="phaseTestNodePolicy.studyTip" value="${item.studyTip}" size="5"/></td>											
											<td><select name="phaseTestNodePolicy.jumpPosition" >
													<c:forEach items="${v.jumpToLst}" var="temp1" varStatus="itemStatus">
														<option value="${temp1.id}" ${temp1.id==item.jumpPosition?"selected":""}>
														<script>document.write(changeStr('${temp1.orderNum}'));</script>
														${temp1.name}</option>
													</c:forEach>
												</select>
											</td> 
											<td><input type="submit" value=" 保存 " class="btn_2k3"/>
												&nbsp;&nbsp;
												<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#edit_${item.id},#show_${item.id}").toggle();'/>
											</td>
										</form>
									</tr>
								</c:forEach>
							</table></td>
					</tr>
				</table>
			</div>
			<div id="s_phaseTestNodePolicy">
				<%if (request.getAttribute("phaseTestNodePolicyLst")==null||((List)request.getAttribute("phaseTestNodePolicyLst")).size()==0){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
				<%}else{%>
				<table id="table" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流转条件：</td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#E3E3E3">
								<tr align="center"  bgcolor="#FFFFFF" class="txt12blue" >
									<td width="10%">序号</td>
									<td width="10%">起始值</td>
									<td width="10%">结束值</td>
									<td width="10%">学习指导</td>
									<td width="10%">学习提示</td>
									<td width="30%">跳转位置</td>
									<td width="20%">操作</td>
								</tr>
								<c:forEach items="${v.phaseTestNodePolicyLst}" var="item" varStatus="itemStatus">
									<tr id="show_${item.id}_s" align="center"  bgcolor="#FFFFFF" class="linkblueor12">
										<td>${itemStatus.index+1}</td>
										<td>${item.startValue}</td>
										<td>${item.endValue}</td>
										<td>${item.studyGuide}</td>
										<td>${item.studyTip}</td>
										<td><c:forEach items="${v.jumpToLst}" var="temp" varStatus="itemStatus"> ${ temp.id==item.jumpPosition ? temp.name : "" } </c:forEach>
										</td>
										<td class="txt12blue"><span style="cursor:hand" onClick='javascript:j("#edit_${item.id}_s,#show_${item.id}_s").toggle();'>修改</span>&nbsp; <span style="cursor:hand"><a href="../policy/phaseTestNodePolicy.jhtml?atype=idelete&id=${item.id}&phaseTestNodePolicy.asfNode.id=${node.id}">删除</a></span> </td>
									</tr>
									<tr id="edit_${item.id}_s" align="center"  bgcolor="#FFFFFF" class="linkblueor12" style="display:none">
										<form action="../policy/phaseTestNodePolicy.jhtml?atype=iupdate" method="post" id="u${item.id}form">
											<td><input name="phaseTestNodePolicy.id" value="${item.id}" size="5" type="hidden"/>
												<input name="phaseTestNodePolicy.asfNode.id" value="${node.id}" size="5" type="hidden"/>
												${itemStatus.index+1}</td>
											<td><input  type="text" name="phaseTestNodePolicy.startValue" value="${item.startValue}" size="5"/></td>
											<td><input  type="text" name="phaseTestNodePolicy.endValue" value="${item.endValue}" size="5"/></td>
											<td><input  type="text" name="phaseTestNodePolicy.studyGuide" value="${item.studyGuide}" size="5"/></td>
											<td><input  type="text" name="phaseTestNodePolicy.studyTip" value="${item.studyTip}" size="5"/></td>
											<td><div name="phaseTestNodePolicy_jumpPosition_select"  v="${item.jumpPosition}"></div></td>
											<td><input type="submit" value=" 保存 " class="btn_2k3"/>
												&nbsp;&nbsp;
												<input type="button" value=" 取消 " class="btn_2k3"  onClick='javascript:j("#edit_${item.id}_s,#show_${item.id}_s").toggle();'/>
											</td>
										</form>
									</tr>
								</c:forEach>
							</table></td>
					</tr>
				</table>
				<%}%>
			</div>
			<div id="add_phaseTestNodePolicy" style="display:none">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#E3E3E3">
								<tr align="center"  bgcolor="#FFFFFF" class="txt12blue" >
									<td width="10%"></td>
									<td width="10%">起始值</td>
									<td width="10%">结束值</td>
									<td width="10%">学习指导</td>
									<td width="10%">学习提示</td>
									<td width="30%">跳转位置</td>
									<td width="20%">操作</td>
								</tr><tr id="add_phaseTestNodePolicy" align="center"  bgcolor="#FFFFFF" class="linkblueor12" >
									<form action="../policy/phaseTestNodePolicy.jhtml?" method="post" id="aform">
										<td width="10%" ><input name="atype" value="iadd" type="hidden"/>
											<input name="phaseTestNodePolicy.asfNode.id" value="${node.id}" type="hidden"/></td>
										<td width="10%"><input  type="text" class="logininputmanage" name="phaseTestNodePolicy.startValue" size="5"/></td>
										<td width="10%"><input  type="text" class="logininputmanage" name="phaseTestNodePolicy.endValue" size="5"/></td>
										<td width="10%"><input  type="text" class="logininputmanage" name="phaseTestNodePolicy.studyGuide" size="5"/></td>
										<td width="10%"><input  type="text" class="logininputmanage" name="phaseTestNodePolicy.studyTip" size="5"/></td>
										<td width="30%"><select name="phaseTestNodePolicy.jumpPosition" >
												<c:forEach items="${v.jumpToLst}" var="temp1" varStatus="itemStatus">
													<option value="${temp1.id}" ${temp1.id==item.jumpPosition?"selected":""}>
													<script>document.write(changeStr('${temp1.orderNum}'));</script>
													${temp1.name}</option>
												</c:forEach>
											</select>
										</td>
										<td width="20%"><input type="submit" value=" 保存 " class="btn_2k3"/>
											&nbsp;&nbsp;
											<input type="button" value=" 取消 " class="btn_2k3"   opt=optSH v='{h:"add_phaseTestNodePolicy" }' />
										</td>
									</form>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
			<%}  if("PRACTICE".equals(nodeType)){%>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{h:"s_trainingUnitNodePolicy",s:"e_trainingUnitNodePolicy"}'  >修改</span>| <span  style="cursor:hand" ><a href="../policy/trainingUnitNodePolicy.jhtml?atype=showNodedelete&id=${node.id}">删除</a></span>| <span style="cursor:hand"opt=optSH v='{h:"e_trainingUnitNodePolicy",s:"s_trainingUnitNodePolicy"}'  >显示</span>| <span  style="cursor:hand" opt=optSH v='{h:"s_trainingUnitNodePolicy,e_trainingUnitNodePolicy"}' >隐藏</span> </td>
				</tr>
			</table>
			<div id="s_trainingUnitNodePolicy">
				<%if (request.getAttribute("trainingUnitNodePolicy")==null ){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
				<%}else{%>
				<table  class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingUnitNodePolicy.pass==1?"返回":"向前"} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">不通过：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingUnitNodePolicy.failed==1?"返回":"向前"} </td>
					</tr>
				</table>
				<%}%>
			</div>
			<div style="display:none" id="e_trainingUnitNodePolicy">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr height="45">
						<td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="radio" id="radio"/>
							使用策略模板&nbsp;&nbsp;
							<input type="button" value="选择模板" class="btn_2k3" v='PRACTICE'/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							${(node.nodeGroup!=null)?("
							<input type='radio' name='radio' id='radio'/>
							继承父节点策略&nbsp;&nbsp;
							<input type='button' value=' 继承 ' class='btn_2k3' opt=inherit v='PRACTICE'/>
							&nbsp;&nbsp;
							"):""} </td>
					</tr>
				</table>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<form action="../policy/trainingUnitNodePolicy.jhtml" method="post" name="trainingUnitNodePolicy_edit">
						<input type="hidden" name="atype" value="iupdate"/>
						<input type="hidden" name="trainingUnitNodePolicy.asfNode.id" value="${node.id}" />
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="trainingUnitNodePolicy.pass">
									<option value="1" ${trainingUnitNodePolicy.pass==1?"selected":""}>返回</option>
									<option value="0" ${trainingUnitNodePolicy.pass==0?"selected":""}>向前</option>
								</select>
							</td>
							<td width="7%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">不通过：</td>
							<td width="43%" align="left" valign="top" bgcolor="#FFFFFF"><select name="trainingUnitNodePolicy.failed">
									<option value="1" ${trainingUnitNodePolicy.failed==1?"selected":""}>返回</option>
									<option value="0" ${trainingUnitNodePolicy.failed==0?"selected":""}>向前</option>
								</select>
							</td>
						</tr>
						<tr>
							<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
									<tr>
										<td ><input type="submit" class="btn_2k3" value="  保 存  " />
											&nbsp;&nbsp;
											<input type="button" value="  取 消  " class="btn_2k3"  opt=optSH v='{s:"s_trainingUnitNodePolicy",h:"e_trainingUnitNodePolicy"}' /></td>
									</tr>
								</table></td>
						</tr>
					</form>
				</table>
			</div>
			<%}  if("UNITTEST".equals(nodeType) ){%>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{h:"s_UNITTEST",s:"e_UNITTEST"}'>修改</span>| <span  style="cursor:hand" ><a href="../policy/unitTestNodePolicy.jhtml?atype=showNodedelete&id=${node.id}">删除</a> </span>| <span style="cursor:hand" opt=optSH v='{h:"e_UNITTEST",s:"s_UNITTEST"}'>显示</span>| <span  style="cursor:hand"  opt=optSH  v='{h:"s_UNITTEST,e_UNITTEST"}'>隐藏</span> </td>
				</tr>
			</table>
			<div id="s_UNITTEST">
				<%if (request.getAttribute("unitTestNodePolicy")==null ){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
				<%}else{%>
				<table id="table11" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
						<td align="left" bgcolor="#FFFFFF" colspan="3"> ${unitTestNodePolicy.retrainingScope==0?"本级":""}
							${unitTestNodePolicy.retrainingScope==1?"上一级":""}
							${unitTestNodePolicy.retrainingScope==2?"上两级":""} </td>
					</tr>
				</table>
				<%}%>
			</div>
			<div style="display:none" id="e_UNITTEST">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr height="45">
						<td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="radio" id="radio"/>
							使用策略模板&nbsp;&nbsp;
							<input type="button" value="选择模板" class="btn_2k3" v='UNITTEST'/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							${(node.nodeGroup!=null)?("
							<input type='radio' name='radio' id='radio'/>
							继承父节点策略&nbsp;&nbsp;
							<input type='button' value=' 继承 ' class='btn_2k3' opt=inherit v='UNITTEST'/>
							&nbsp;&nbsp;
							"):""} </td>
					</tr>
				</table>
				<table id="table12" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<form action="../policy/unitTestNodePolicy.jhtml"  method="post"  lang="en" name="unitTestNodePolicy_edit">
						<input type="hidden" name="atype" value="iupdate"/>
						<input type="hidden" name="id" value="${unitTestNodePolicy.nodeId}">
						<input type="hidden" name="unitTestNodePolicy.asfNode.id" value="${node.id}">
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
							<td align="left" bgcolor="#FFFFFF" colspan="3"><select name="unitTestNodePolicy.retrainingScope">
									<option value="0" ${unitTestNodePolicy.retrainingScope==0?"selected":""}>本级</option>
									<option value="1" ${unitTestNodePolicy.retrainingScope==1?"selected":""}>上级</option>
									<option value="2" ${unitTestNodePolicy.retrainingScope==2?"selected":""}>上上级</option>
								</select>
							</td>
						</tr>
						<tr>
							<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
									<tr>
										<td ><input type="submit" class="btn_2k3" value="  保 存  " />
											&nbsp;&nbsp;
											<input type="button"  opt=optSH v='{h:"e_UNITTEST",s:"s_UNITTEST"}' value="  取 消  "  class="btn_2k3"/></td>
									</tr>
								</table></td>
						</tr>
					</form>
				</table>
			</div>
			<%}%>
		</td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td ><table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td width="50%" align="left" class="txt12blueh">训练策略</td>
					<td align="right"  class="txt12blue"><span style="cursor:hand" opt=optSH v='{h:"s_traniPolicy",s:"e_traniPolicy"}'>修改</span>| <span style="cursor:hand"><a href="../policy/trainingPolicy.jhtml?atype=showNodedelete&id=${node.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{h:"e_traniPolicy",s:"s_traniPolicy"}'>显示</span>| <span style="cursor:hand" opt=optSH v='{h:"s_traniPolicy,e_traniPolicy"}'>隐藏</span>
						</div></td>
				</tr>
			</table>
			<div id="s_traniPolicy">
				<%if (request.getAttribute("trainingPolicy")==null ){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
				<%}else{
					if("EVALUATE".equals(nodeType)||"UNITTEST".equals(nodeType)||"PHASETEST".equals(nodeType)){
				%>
				<table id="table13" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.overviewTime} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.allowUnsureMark==1?"允许":"不允许"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">何时允许查看解析：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> ${trainingPolicy.whenToSeeAnalysis==1?"随时":""}
							${trainingPolicy.whenToSeeAnalysis==2?"做题后":""}
							${trainingPolicy.whenToSeeAnalysis==3?"正确后":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.whenToCheckAnswer==1?"随时":""}
							${trainingPolicy.whenToCheckAnswer==2?"做题后":""}
							${trainingPolicy.whenToCheckAnswer==3?"正确后":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">是否随机出题：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.isRandom==0?"否":"是"}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.isRandomAnswerOptions==0?"否":"是"} </td>
					</tr>
					<%if("UNITTEST".equals(nodeType)){%>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.rightRateForPass}% </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.rightRateRetraining}% </td>
					</tr>
					<%}%>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
						<td width="83%" align="left"  valign="top" bgcolor="#FFFFFF" colspan="3">${names}</td>
					</tr>
				</table>
				<%	} else { %>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.overviewTime} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.allowUnsureMark==1?"允许":"不允许"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> ${trainingPolicy.whenToSeeAnalysis==1?"随时":""}
							${trainingPolicy.whenToSeeAnalysis==2?"做题后":""}
							${trainingPolicy.whenToSeeAnalysis==3?"正确后":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.whenToCheckAnswer==1?"随时":""}
							${trainingPolicy.whenToCheckAnswer==2?"做题后":""}
							${trainingPolicy.whenToCheckAnswer==3?"正确后":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">是否随机出题：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.isRandom==0?"否":"是"}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">是否颠倒答案顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.isRandomAnswerOptions==0?"否":"是"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.rightRateForPass}% </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.rightRateRetraining}% </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">重做出题类型：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.retrainingItemType==12?"错题":""}
							${trainingPolicy.retrainingItemType==11?"未答题":""} 
							${trainingPolicy.retrainingItemType==13?"错题&未答题":""} 
							${trainingPolicy.retrainingItemType==0?"零星题":""} 
							${trainingPolicy.retrainingItemType==0.5?"半星题":""} 
							${trainingPolicy.retrainingItemType==1?"一星题":""} 
							${trainingPolicy.retrainingItemType==2?"二星题":""} 
							${trainingPolicy.retrainingItemType==3?"三星题":""} 
							${trainingPolicy.retrainingItemType==4?"四星题":""} 
							${trainingPolicy.retrainingItemType==5?"五星题":""} 
							${trainingPolicy.retrainingItemType==-1?"全部":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.retrainingItemOrder}</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.retrainingRightRateTestFaile}
							% </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.retrainingItemTypeTestFaile==-2?"无":""}
							${trainingPolicy.retrainingItemTypeTestFaile==12?"错题":""} ${trainingPolicy.retrainingItemTypeTestFaile==11?"未答题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==13?"错题&未答题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==0?"零星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==0.5?"半星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==1?"一星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==2?"二星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==3?"三星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==4?"四星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==5?"五星题":""} 
							${trainingPolicy.retrainingItemTypeTestFaile==-1?"全部":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.retrainingItemOrderTestFaile} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">测试未通过是否动态出题：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.randomAssemItemsTestFaile=="1"?"是":"否"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.assemItemsTypeTestFaile==-2?"无":""}
							${trainingPolicy.assemItemsTypeTestFaile==12?"错题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==11?"未答题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==13?"错题&未答题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==0?"零星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==0.5?"半星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==1?"一星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==2?"二星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==3?"三星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==4?"四星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==5?"五星题":""} 
							${trainingPolicy.assemItemsTypeTestFaile==-1?"全部":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.assemItemsRateTestFaile}%</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.clueContentFirstFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">
							${trainingPolicy.clueRelActFirstFaile==1?"高亮子题考点相关处":""}
							${trainingPolicy.clueRelActFirstFaile==2?"高亮试题":""}
							${trainingPolicy.clueRelActFirstFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicy.clueRelActFirstFaile==4?"高亮选项":""} 
							${trainingPolicy.clueRelActFirstFaile==5?"查看答案":""} 
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.translationContentFirstFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7" class="txt12blue">第一次答错译文相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.translationRelActFirstFaile==1?"高亮子题考点相关处":""}
							${trainingPolicy.translationRelActFirstFaile==2?"高亮试题":""}
							${trainingPolicy.translationRelActFirstFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicy.translationRelActFirstFaile==4?"高亮选项":""} 
							${trainingPolicy.translationRelActFirstFaile==5?"查看答案":""} 
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.clueContentSecondFaile}</td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">
							${trainingPolicy.clueRelActSecondFaile==1?"高亮子题考点相关处":""}
							${trainingPolicy.clueRelActSecondFaile==2?"高亮试题":""}
							${trainingPolicy.clueRelActSecondFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicy.clueRelActSecondFaile==4?"高亮选项":""}
							${trainingPolicy.clueRelActSecondFaile==5?"查看答案":""}
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.translationContentSecondFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7" class="txt12blue">第二次答错译文相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">
							${trainingPolicy.translationRelActSecondFaile==1?"高亮子题考点相关处":""}
							${trainingPolicy.translationRelActSecondFaile==2?"高亮试题":""}
							${trainingPolicy.translationRelActSecondFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicy.translationRelActSecondFaile==4?"高亮选项":""}
							${trainingPolicy.translationRelActSecondFaile==5?"查看答案":""}
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.clueContentThirdFaile}</td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7" class="txt12blue">第三次答错提示相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">
							${trainingPolicy.clueRelActThirdFaile==1?"高亮子题考点相关处":""}
							${trainingPolicy.clueRelActThirdFaile==2?"高亮试题":""}
							${trainingPolicy.clueRelActThirdFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicy.clueRelActThirdFaile==4?"高亮选项":""}
							${trainingPolicy.clueRelActThirdFaile==5?"查看答案":""}
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7" class="txt12blue">第三次答错译文内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.translationContentThirdFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7" class="txt12blue">第三次答错译文相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">
							${trainingPolicy.translationRelActThirdFaile==1?"高亮子题考点相关处":""}
							${trainingPolicy.translationRelActThirdFaile==2?"高亮试题":""}
							${trainingPolicy.translationRelActThirdFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicy.translationRelActThirdFaile==4?"高亮选项":""}
							${trainingPolicy.translationRelActThirdFaile==5?"查看答案":""} 
						</td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
						<td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">${names}</td>
					</tr>
				</table>
				<% }
				}%>
			</div>
			<div style="display:none" id="e_traniPolicy">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr height="45">
						<td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="radio" id="radio"/>
							使用策略模板&nbsp;&nbsp;
							<input type="button" value="选择模板" class="btn_2k3" v="training_policy_template" f="i_e_trainPolicy"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							${(node.nodeGroup!=null)?("
							<input type='radio' name='radio' id='radio'/>
							继承父节点策略&nbsp;&nbsp;
							<input type='button' value=' 继承 ' class='btn_2k3' opt=inherit v='training_policy_template'/>
							&nbsp;&nbsp;
							"):""} </td>
					</tr>
				</table>
				<% if("EVALUATE".equals(nodeType)||"UNITTEST".equals(nodeType)||"PHASETEST".equals(nodeType)){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<form action="../policy/trainingPolicy.jhtml" method="post" name="trainingPolicy_edit" >
					
						<input type="hidden" name="atype" value="iupdate"/>
						<input type="hidden" name="trainingPolicy.asfNode.id" value="${node.id}">
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.overviewTime"  value="${trainingPolicy.overviewTime}" size="10"/>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.allowUnsureMark' vs="{'v':'0,1','s':'不允许,允许','rv':'0'}"  ${trainingPolicy.allowUnsureMark==1?"checked":""} />
								<input type="hidden" vvv=vvv name="trainingPolicy.allowUnsureMark" value='${trainingPolicy.allowUnsureMark==null||trainingPolicy.allowUnsureMark==""?0:trainingPolicy.allowUnsureMark}'>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="1" ${trainingPolicy.whenToSeeAnalysis==1?"checked":""} />
								随时
								<input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="2" ${trainingPolicy.whenToSeeAnalysis==2?"checked":""} />
								做题后
								<input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="3" ${trainingPolicy.whenToSeeAnalysis==3?"checked":""} />
								正确后 </td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicy.whenToCheckAnswer" value="1" ${trainingPolicy.whenToCheckAnswer==1?"checked":""} />
								随时
								<input type="radio" name="trainingPolicy.whenToCheckAnswer" value="2" ${trainingPolicy.whenToCheckAnswer==2?"checked":""} />
								做题后
								<input type="radio" name="trainingPolicy.whenToCheckAnswer" value="3" ${trainingPolicy.whenToCheckAnswer==3?"checked":""} />
								正确后 </td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.isRandom' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicy.isRandom==1?"checked":""} />
								<input type="hidden" vvv=vvv name="trainingPolicy.isRandom" value='${trainingPolicy.isRandom==null||trainingPolicy.isRandom==""?0:trainingPolicy.isRandom}'></td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.isRandomAnswerOptions' vs="{'v':'0,1','s':'不颠倒,颠倒','rv':'0'}"  ${trainingPolicy.isRandomAnswerOptions==1?"checked":""}   />
								<input type="hidden" vvv=vvv name="trainingPolicy.isRandomAnswerOptions" value='${trainingPolicy.isRandomAnswerOptions==null||trainingPolicy.isRandomAnswerOptions==""?0:trainingPolicy.isRandomAnswerOptions}'>
							</td>
						</tr>
						<%if("UNITTEST".equals(nodeType)){%>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.rightRateForPass" value="${trainingPolicy.rightRateForPass}" size="10"/>
								% </td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.rightRateRetraining" value="${trainingPolicy.rightRateRetraining}" size="10"/>
								% </td>
						</tr>
						<%}%>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" type="text" name="trainingPolicy.studyGuideNames" id="studyGuideNames" value="${names }" readonly="readonly"/> 
								<input type="hidden" name="trainingPolicy.studyGuideCodes" id="studyGuideCodes" value="${trainingPolicy.studyGuideCodes }" />&nbsp;&nbsp;		 		
								<input type="button" value="选择" class="btn_2k3"  onClick="doSel();"/>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue"></td>
							<td width="33%" align="left" bgcolor="#FFFFFF"></td>
						</tr>
						<tr>
							<td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
								<table width="300" border="0">
								<tr>
									<td >
									<input type="submit" class="btn_2k3" value=" 保 存  " />&nbsp;&nbsp;
									<input type="button" opt="optSH" v='{h:"e_traniPolicy",s:"s_traniPolicy"}' value=" 取 消  " class="btn_2k3"/>
									</td>
								</tr>
								</table>
							</td>
						</tr>
					</form>
				</table>
				<%}else{%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<form action="../policy/trainingPolicy.jhtml" method="post" name="trainingPolicy_edit" >
						<input type="hidden" name="atype" value="iupdate"/>
						<input type="hidden" name="trainingPolicy.asfNode.id" value="${node.id}">
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.overviewTime"  value="${trainingPolicy.overviewTime}" size="10"/>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.allowUnsureMark' vs="{'v':'0,1','s':'不允许,允许','rv':'0'}"  ${trainingPolicy.allowUnsureMark==1?"checked":""} />
								<input type="hidden" vvv=vvv name="trainingPolicy.allowUnsureMark" value='${trainingPolicy.allowUnsureMark==null||trainingPolicy.allowUnsureMark==""?0:trainingPolicy.allowUnsureMark}'>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="1" ${trainingPolicy.whenToSeeAnalysis==1?"checked":""} />
								随时
								<input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="2" ${trainingPolicy.whenToSeeAnalysis==2?"checked":""} />
								做题后
								<input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="3" ${trainingPolicy.whenToSeeAnalysis==3?"checked":""}/>
								正确后 </td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicy.whenToCheckAnswer" value="1" ${trainingPolicy.whenToCheckAnswer==1?"checked":""} />
								随时
								<input type="radio" name="trainingPolicy.whenToCheckAnswer" value="2" ${trainingPolicy.whenToCheckAnswer==2?"checked":""} />
								做题后
								<input type="radio" name="trainingPolicy.whenToCheckAnswer" value="3" ${trainingPolicy.whenToCheckAnswer==3?"checked":""} />
								正确后 </td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.isRandom' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicy.isRandom==1?"checked":""} />
								<input type="hidden" vvv=vvv name="trainingPolicy.isRandom" value='${trainingPolicy.isRandom==null||trainingPolicy.isRandom==""?0:trainingPolicy.isRandom}'></td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.isRandomAnswerOptions' vs="{'v':'0,1','s':'不颠倒,颠倒','rv':'0'}"  ${trainingPolicy.isRandomAnswerOptions==1?"checked":""}   />
								<input type="hidden" vvv=vvv name="trainingPolicy.isRandomAnswerOptions" value='${trainingPolicy.isRandomAnswerOptions==null||trainingPolicy.isRandomAnswerOptions==""?0:trainingPolicy.isRandomAnswerOptions}'>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.rightRateForPass" value="${trainingPolicy.rightRateForPass}" size="10"/>
								% </td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.rightRateRetraining" value="${trainingPolicy.rightRateRetraining}" size="10"/>
								% </td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="trainingPolicy.retrainingItemType">
									<option value="-2" ${trainingPolicy.retrainingItemType==-2?"selected":""} >无</option>
									<option value="12" ${trainingPolicy.retrainingItemType==12?"selected":""} >错题</option>
									<option value="11" ${trainingPolicy.retrainingItemType==11?"selected":""} >未答题</option>
									<option value="13" ${trainingPolicy.retrainingItemType==13?"selected":""} >错题&未答题</option>
									<option value="0" ${trainingPolicy.retrainingItemType==0?"selected":""} >零星题</option>
									<option value="0.5" ${trainingPolicy.retrainingItemType==0.5?"selected":""} >半星题</option>
									<option value="1" ${trainingPolicy.retrainingItemType==1?"selected":""} >一星题</option>
									<option value="2" ${trainingPolicy.retrainingItemType==2?"selected":""} >二星题</option>
									<option value="3" ${trainingPolicy.retrainingItemType==3?"selected":""} >三星题</option>
									<option value="4" ${trainingPolicy.retrainingItemType==4?"selected":""} >四星题</option>
									<option value="5" ${trainingPolicy.retrainingItemType==5?"selected":""} >五星题</option>
									<option value="-1" ${trainingPolicy.retrainingItemType==-1?"selected":""} >全部</option>
								</select>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><div n="trainingPolicy.retrainingItemOrder">
									<select >
										<option>无</option>
										<option>错题</option>
										<option>新题</option>
										<option>星题</option>
									</select>
									<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
									<select >
										<option>无</option>
										<option>错题</option>
										<option>新题</option>
										<option>星题</option>
									</select>
									<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
									<select >
										<option>无</option>
										<option>错题</option>
										<option>新题</option>
										<option>星题</option>
									</select>
									<input type="hidden" vvv=vvv name="trainingPolicy.retrainingItemOrder" value="${trainingPolicy.retrainingItemOrder}"/>
								</div></td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.retrainingRightRateTestFaile"  value="${trainingPolicy.retrainingRightRateTestFaile}"size="10"/>
								% </td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.retrainingItemTypeTestFaile">
									<option value="-2" ${(trainingPolicy.retrainingItemTypeTestFaile==-2)?"selected":""} >无</option>
									<option value="12" ${(trainingPolicy.retrainingItemTypeTestFaile==12)?"selected":""} >错题</option>
									<option value="11" ${trainingPolicy.retrainingItemTypeTestFaile==11?"selected":""} >未答题</option>
									<option value="13" ${trainingPolicy.retrainingItemTypeTestFaile==13?"selected":""} >错题&未答题</option>
									<option value="0" ${trainingPolicy.retrainingItemTypeTestFaile==0?"selected":""} >零星题</option>
									<option value="0.5" ${trainingPolicy.retrainingItemTypeTestFaile==0.5?"selected":""} >半星题</option>
									<option value="1" ${trainingPolicy.retrainingItemTypeTestFaile==1?"selected":""} >一星题</option>
									<option value="2" ${trainingPolicy.retrainingItemTypeTestFaile==2?"selected":""} >二星题</option>
									<option value="3" ${trainingPolicy.retrainingItemTypeTestFaile==3?"selected":""} >三星题</option>
									<option value="4" ${trainingPolicy.retrainingItemTypeTestFaile==4?"selected":""} >四星题</option>
									<option value="5" ${trainingPolicy.retrainingItemTypeTestFaile==5?"selected":""} >五星题</option>
									<option value="-1" ${trainingPolicy.retrainingItemTypeTestFaile==-1?"selected":""} >全部</option>
								</select></td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><div n="trainingPolicy.retrainingItemOrderTestFaile">
									<select >
										<option>无</option>
										<option>错题</option>
										<option>新题</option>
										<option>星题</option>
									</select>
									<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
									<select >
										<option>无</option>
										<option>错题</option>
										<option>新题</option>
										<option>星题</option>
									</select>
									<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
									<select >
										<option>无</option>
										<option>错题</option>
										<option>新题</option>
										<option>星题</option>
									</select>
									<input type="hidden" vvv=vvv name="trainingPolicy.retrainingItemOrderTestFaile" value="${trainingPolicy.retrainingItemOrderTestFaile}"/>
								</div></td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.randomAssemItemsTestFaile' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicy.randomAssemItemsTestFaile==1?"checked":""}   />
								<input type="hidden" vvv=vvv name="trainingPolicy.randomAssemItemsTestFaile" value='${trainingPolicy.randomAssemItemsTestFaile==null||trainingPolicy.randomAssemItemsTestFaile==""?0:trainingPolicy.randomAssemItemsTestFaile}'>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.assemItemsTypeTestFaile">
									<option value="-2" ${trainingPolicy.assemItemsTypeTestFaile==-2?"selected":""} >无</option>
									<option value="12" ${trainingPolicy.assemItemsTypeTestFaile==12?"selected":""} >错题</option>
									<option value="11" ${trainingPolicy.assemItemsTypeTestFaile==11?"selected":""} >未答题</option>
									<option value="13" ${trainingPolicy.assemItemsTypeTestFaile==13?"selected":""} >错题&未答题</option>
									<option value="0" ${trainingPolicy.assemItemsTypeTestFaile==0?"selected":""} >零星题</option>
									<option value="0.5" ${trainingPolicy.assemItemsTypeTestFaile==0.5?"selected":""} >半星题</option>
									<option value="1" ${trainingPolicy.assemItemsTypeTestFaile==1?"selected":""} >一星题</option>
									<option value="2" ${trainingPolicy.assemItemsTypeTestFaile==2?"selected":""} >二星题</option>
									<option value="3" ${trainingPolicy.assemItemsTypeTestFaile==3?"selected":""} >三星题</option>
									<option value="4" ${trainingPolicy.assemItemsTypeTestFaile==4?"selected":""} >四星题</option>
									<option value="5" ${trainingPolicy.assemItemsTypeTestFaile==5?"selected":""} >五星题</option>
									<option value="-1" ${trainingPolicy.assemItemsTypeTestFaile==-1?"selected":""} >全部</option>
								</select>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.assemItemsRateTestFaile" value="${trainingPolicy.assemItemsRateTestFaile}" size="10"/>
								% </td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.clueContentFirstFaile" id="textarea" cols="35" rows="2">${trainingPolicy.clueContentFirstFaile}</textarea>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.clueRelActFirstFaile">
									<option value="0" selected >无</option>
									<option value="1" ${trainingPolicy.clueRelActFirstFaile==1?"selected":""}>高亮子题考点相关处</option>
									<option value="2" ${trainingPolicy.clueRelActFirstFaile==2?"selected":""}>高亮试题</option>
									<option value="3" ${trainingPolicy.clueRelActFirstFaile==3?"selected":""}>高亮子题与文章相关处</option>
									<option value="4" ${trainingPolicy.clueRelActFirstFaile==4?"selected":""}>高亮选项</option>
									<option value="5" ${trainingPolicy.clueRelActFirstFaile==5?"selected":""}>查看答案</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.translationContentFirstFaile" id="textarea" cols="35" rows="2">${trainingPolicy.translationContentFirstFaile}</textarea>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.translationRelActFirstFaile">
									<option value="0" selected >无</option>
									<option value="1" ${trainingPolicy.translationRelActFirstFaile==1?"selected":""}>高亮子题考点相关处</option>
									<option value="2" ${trainingPolicy.translationRelActFirstFaile==2?"selected":""}>高亮试题</option>
									<option value="3" ${trainingPolicy.translationRelActFirstFaile==3?"selected":""}>高亮子题与文章相关处</option>
									<option value="4" ${trainingPolicy.translationRelActFirstFaile==4?"selected":""}>高亮选项</option>
									<option value="5" ${trainingPolicy.translationRelActFirstFaile==5?"selected":""}>查看答案</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.clueContentSecondFaile" id="textarea" cols="35" rows="2">${trainingPolicy.clueContentSecondFaile}</textarea>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.clueRelActSecondFaile">
									<option value="0" selected >无</option>
									<option value="1" ${trainingPolicy.clueRelActSecondFaile==1?"selected":""}>高亮子题考点相关处</option>
									<option value="2" ${trainingPolicy.clueRelActSecondFaile==2?"selected":""}>高亮试题</option>
									<option value="3" ${trainingPolicy.clueRelActSecondFaile==3?"selected":""}>高亮子题与文章相关处</option>
									<option value="4" ${trainingPolicy.clueRelActSecondFaile==4?"selected":""}>高亮选项</option>
									<option value="5" ${trainingPolicy.clueRelActSecondFaile==5?"selected":""}>查看答案</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.translationContentSecondFaile" id="textarea" cols="35" rows="2">${trainingPolicy.translationContentSecondFaile}</textarea>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.translationRelActSecondFaile">
									<option value="0" selected >无</option>
									<option value="1" ${trainingPolicy.translationRelActSecondFaile==1?"selected":""}>高亮子题考点相关处</option>
									<option value="2" ${trainingPolicy.translationRelActSecondFaile==2?"selected":""}>高亮试题</option>
									<option value="3" ${trainingPolicy.translationRelActSecondFaile==3?"selected":""}>高亮子题与文章相关处</option>
									<option value="4" ${trainingPolicy.translationRelActSecondFaile==4?"selected":""}>高亮选项</option>
									<option value="5" ${trainingPolicy.translationRelActSecondFaile==5?"selected":""}>查看答案</option>									 
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.clueContentThirdFaile" id="textarea" cols="35" rows="2">${trainingPolicy.clueContentThirdFaile}</textarea>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.clueRelActThirdFaile">
									<option value="0" selected >无</option>
									<option value="1" ${trainingPolicy.clueRelActThirdFaile==1?"selected":""}>高亮子题考点相关处</option>
									<option value="2" ${trainingPolicy.clueRelActThirdFaile==2?"selected":""}>高亮试题</option>
									<option value="3" ${trainingPolicy.clueRelActThirdFaile==3?"selected":""}>高亮子题与文章相关处</option>
									<option value="4" ${trainingPolicy.clueRelActThirdFaile==4?"selected":""}>高亮选项</option>
									<option value="5" ${trainingPolicy.clueRelActThirdFaile==5?"selected":""}>查看答案</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
							<textarea name="trainingPolicy.translationContentThirdFaile" id="textarea" cols="35" rows="2">${trainingPolicy.translationContentThirdFaile}</textarea>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF">
								<select name="trainingPolicy.translationRelActThirdFaile">
									<option value="0" selected >无</option>
									<option value="1" ${trainingPolicy.translationRelActThirdFaile==1?"selected":""}>高亮子题考点相关处</option>
									<option value="2" ${trainingPolicy.translationRelActThirdFaile==2?"selected":""}>高亮试题</option>
									<option value="3" ${trainingPolicy.translationRelActThirdFaile==3?"selected":""}>高亮子题与文章相关处</option>
									<option value="4" ${trainingPolicy.translationRelActThirdFaile==4?"selected":""}>高亮选项</option>
									<option value="5" ${trainingPolicy.translationRelActThirdFaile==5?"selected":""}>查看答案</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
								<input class="logininputmanage" type="text" name="trainingPolicy.studyGuideNames" id="studyGuideNames" value="${names }" readonly="readonly"/> 
								<input type="hidden" name="trainingPolicy.studyGuideCodes" id="studyGuideCodes" value="${trainingPolicy.studyGuideCodes }" />&nbsp;&nbsp;		 		
								<input type="button" value="选择" class="btn_2k3"  onClick="doSel();"/>
							</td>
							<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue"></td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"></td>
						</tr> 
						<tr>
							<td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
								<table width="300" border="0">
								<tr>
									<td>
										<input type="submit" class="btn_2k3" value="  保 存  " />&nbsp;&nbsp;
										<input type="button" opt="optSH" v='{h:"e_traniPolicy",s:"s_traniPolicy"}' value="  取 消  " class="btn_2k3"/>
									</td>
								</tr>
								</table>
							</td>
						</tr>
					</form>
				</table>
				<%}%>
			</div></td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td ><table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">组卷策略</td>
					<td align="right"  class="txt12blue"><span style="cursor:hand" opt="optSH" v='{s:"p,e_paperAssumingPolicy",h:"s_paperAssumingPolicy"}'>修改</span>| <span style="cursor:hand"><a href="../policy/paperAssemblingPolicy.jhtml?atype=showNodedelete&id=${node.id}">删除</a></span>| <span style="cursor:hand"  opt="optSH" v='{s:"p,s_paperAssumingPolicy",h:"e_paperAssumingPolicy"}' >显示</span>| <span style="cursor:hand" onClick='javascript:j("#p").slideUp();'>隐藏</span> </td>
				</tr>
			</table></td >
	</tr>
</table>
<table id="p" width="100%" border="0" align="center" >
	<tr>
		<td ><div id="s_paperAssumingPolicy">
				<%if (request.getAttribute("paperAssemblingPolicy")==null ){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
				<%}else{%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr >
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> 
							<c:set 	var="__paperAssemblingMode" value="${paperAssemblingPolicy.paperAssemblingMode}" scope="session"/>
							<c:import url= "__paperAssemblingMode_show.jsp" />
							<c:remove var="__paperAssemblingMode" scope="session"/>
						</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"><a href="../itembank/paper!show.jhtml?id=${paperAssemblingPolicy.paper.id}">${paperAssemblingPolicy.paper.name}</a></td>
					</tr>
					<tr >
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答卷时间：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${paperAssemblingPolicy.answeringTime!=null?paperAssemblingPolicy.answeringTime/60:""}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数(小题)：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${paperAssemblingPolicy.items_num}</td>
					</tr>
					<tr >
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">卷分：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${paperAssemblingPolicy.totalScore}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷难度：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${paperAssemblingPolicy.difficultyValue}</td>
					</tr>
					<tr >
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">标准答卷时间：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${paperAssemblingPolicy.standardAnsweringTime!=null?paperAssemblingPolicy.standardAnsweringTime/60:""}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">大题数量：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${paperAssemblingPolicy.big_items_num}</td>
					</tr>
				</table>
				<%}%>
			</div>
			<div style="display:none" id="e_paperAssumingPolicy">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr height="45">
						<td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="radio" id="radio"/>
							使用策略模板&nbsp;&nbsp;
							<input type="button" value="选择模板" class="btn_2k3" v="assemble_paperPolicy_template"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							${(node.nodeGroup!=null)?("
							<input type='radio' name='radio' id='radio'/>
							继承父节点策略&nbsp;&nbsp;
							<input type='button' value=' 继承 ' class='btn_2k3' opt=inherit v='assemble_paperPolicy_template'/>
							&nbsp;&nbsp;
							"):""} </td>
					</tr>
				</table>
				<table id="table19" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<form action="../policy/paperAssemblingPolicy.jhtml"  method="post" id="paperAssemblingPolicy_form" name="paperAssemblingPolicy_edit">
						<input type="hidden" name="atype" value="iupdate" id="atype_paperAssemblingPolicy"/>
						<input type="hidden" name="paperAssemblingPolicy.asfNode.id" value="${node.id}" id="xxxbbb"  />
						<tr id="table161">
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">组卷方式：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><select name="paperAssemblingPolicy.paperAssemblingMode" id="paperAssemblingMode" opt=optSH >								
									<c:set var="__paperAssemblingMode" value="${paperAssemblingPolicy.paperAssemblingMode}" scope="session"/>
									<c:import url= "__paperAssemblingMode_select.jsp"/>
									<c:remove var="__paperAssemblingMode" scope="session"/>
								</select>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">使用试卷：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF">
								<input x="id"  class="logininputmanage" type="hidden" name="paperIds" value="${paperIds}"/>
								<input x="name" class="logininputmanage" type="text" value="${paperNames}"/> 
								&nbsp;&nbsp;
								<input type="button" value="选择" onClick='javascript:window.open("../itembank/paper!list.jhtml?to=selectPaper&amp;p.para.pid=${paperAssemblingPolicy.asfNode.id}");'  class="btn_2k3"/>
								&nbsp;&nbsp; </td>
						</tr>
						<tr >
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答卷时间：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" x="answeringTime" type="text" name="paperAssemblingPolicy.answeringTime" value="${paperAssemblingPolicy.answeringTime!=null?paperAssemblingPolicy.answeringTime/60:""}"/></td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数(小题)：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" x="items_num" type="text" name="paperAssemblingPolicy.items_num" value="${paperAssemblingPolicy.items_num}"/></td>
						</tr>
						<tr >
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">卷分：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" x="totalScore" name="paperAssemblingPolicy.totalScore" value="${paperAssemblingPolicy.totalScore}"/></td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试卷难度：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" x="difficultyValue" name="paperAssemblingPolicy.difficultyValue" value="${paperAssemblingPolicy.difficultyValue}"/></td>
						</tr>
						<tr >
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">标准答卷时间：</td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="paperAssemblingPolicy.standardAnsweringTime" value="${paperAssemblingPolicy.standardAnsweringTime!=null?paperAssemblingPolicy.standardAnsweringTime/60:""}"/></td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
							<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" x="big_items_num" type="text" name="paperAssemblingPolicy.big_items_num" value="${paperAssemblingPolicy.big_items_num}"/></td>
						</tr>
						<tr id="table162">
							<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
									<tr>
										<td ><input type="submit" class="btn_2k3" value="  保 存  " />
											&nbsp;&nbsp;
											<input type="button" opt="optSH" v='{h:"e_paperAssumingPolicy",s:"s_paperAssumingPolicy"}' value="  取 消  " class="btn_2k3"/></td>
									</tr>
								</table></td>
						</tr>
					</form>
				</table>
			</div>
			<div id="paperAssemblingMode_div" style="display:${paperAssemblingPolicy.paperAssemblingMode==0?"none":"block"}">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td colspan="4" bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="6" cellspacing="1">
								<tr>
									<td width="50%" align="left" class="txt12blueh">组卷条件</td>
									<td width="50%" align="right"  class="txt12blue" ><div name=paperReqs opt=opt> <span style="cursor:hand" opt=optSH v='{t:"useTemplate_paperReqs"}'>使用模板&继承</span>| <span style="cursor:hand" opt=optSH v='{t:"add_paperReqs"}'>新增条件</span>| <span style="cursor:hand"><a href="../policy/paperAssemblingRequirements.jhtml?atype=deleteAllByNodeId&paperAssemblingRequirements.asfNode.id=${node.id}&id=${node.id}">删除全部</a></span>| <span style="cursor:hand"><a href="previewItem.jhtml?nodeId=${node.id}" target="_blank">预览试题</a></span> </div></td>
								</tr>
							</table>
							<div style="display:none" id="useTemplate_paperReqs">
								<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
									<tr height="45">
										<td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="radio" id="radio"/>
											使用策略模板&nbsp;&nbsp;
											<input type="button" value="选择模板" class="btn_2k3" v="paper_assembling_req_template"/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											${(node.nodeGroup!=null)?("
											<input type='radio' name='radio' id='radio'/>
											继承父节点策略&nbsp;&nbsp;
											<input type='button' value=' 继承 ' class='btn_2k3' opt=inherit v='paper_assembling_req_template'/>
											&nbsp;&nbsp;
											"):""} </td>
									</tr>
								</table>
							</div>
							<div style="display:none" id="add_paperReqs" >
								<table width="100%"><tr><td>
								
								<form action="../policy/paperAssemblingRequirements.jhtml" id="add_paper_assembling_requirements_form" method="post">
								<input type="hidden" name="atype" value="iadd"/>
								<input type="hidden" name="paperAssemblingRequirements.asfNode.id" value="${node.id}">
								<c:set var="_obj" value="${paperAssemblingRequirements}" scope="session"/>
								<c:set var="_objName" value="paperAssemblingRequirements" scope="session"/>
								<c:set var="_kcode" value="${_kp.code}" scope="session"/>
								<c:set var="_cancleButtonEvent" scope="session">
								 onClick="${_type=='main'}javascript:$('#add_paperReqs').hide()"
								</c:set>
								<c:import url="__paperAssemblingRequirements_input.jsp"/>
								<c:remove var="_cancleButtonEvent" scope="session"/>
								<c:remove var="_obj" scope="session"/>
								<c:remove var="_objName" scope="session"/>
								</form></td></tr></table>
							</div>
							<div id="s_paperReqs" >
								<%if (request.getAttribute("paperAssemblingRequirementsLst")==null||((List)request.getAttribute("paperAssemblingRequirementsLst")).size()==0 ){%>
								<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
									<tr>
										<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
										<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
									</tr>
								</table>
								<%}else{%>
								<table id="table17" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#E3E3E3">
									<tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
										<td width="4%">序号</td>
										<td width="5%">地区</td>
										<td width="5%">学科</td>
										<td width="7%">学级</td>
										<td width="18%">知识点</td>
										<td width="9%">题型</td>
										<td width="8%">试题年份</td>
										<td width="11%">试题来源</td>
										<td width="5%">原始套卷</td>
										<td width="5%">试题难度</td>
										<td width="5%">试题效度</td>
										<td width="6%">题数</td>
										<td width="12%">操作</td>
									</tr>
									<c:forEach items="${paperAssemblingRequirementsLst}" var="item" varStatus="itemStatus">
										<tr id="showPR_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12">
											<td>${itemStatus.index+1}</td>
											<td><c:forEach items="${regionList}" var="i" varStatus="Status">
													<c:if test="${item.regionCode eq i.code}">${i.name}</c:if>
												</c:forEach>
											</td>
											<td><c:forEach items="${subjectList}" var="i" varStatus="Status">
													<c:if test="${item.subjectCode eq i.code}">${i.name}</c:if>
												</c:forEach>
											</td>
											<td><c:forEach items="${gradeList}" var="i" varStatus="Status">
													<c:if test="${item.gradeCode eq i.code}">${i.name}</c:if>
												</c:forEach>
											</td>
											<td ><c:forEach items="${knowledgePointList}" var="kp"><c:forEach items='${fn:split(item.knowledgePointCode, ",")}' var="code" ><c:if test="${code==kp.code}">${kp.name}<br></c:if></c:forEach></c:forEach>
											</td>
											<td>
											<c:forEach items="${itemTypeList}" var="i" varStatus="Status">
													<c:forEach items="${fn:split(item.itemTypeCode,',')}" var="it_code">
														<c:if test="${it_code eq i.code}">(${i.code})${i.name}</c:if>
													</c:forEach>
												</c:forEach>
											</td>
											<td>${item.year}</td>
											<td><c:forEach items="${itemSourceList}" var="i" varStatus="Status">
													<c:if test="${fn:contains(item.source,i.value)}"> ${i } </c:if>
												</c:forEach>
											</td>
											<td>${item.originalPaperCode}</td>
											<td>${item.difficultyValue}</td>
											<td>${item.validityValue}</td>
											<td>${item.amount}</td>
											<td class="txt12blue">
												<span style="cursor:hand" >
													<a href="../policy/paperAssemblingRequirements.jhtml?atype=iedit&id=${item.id}&nodeType=${node.nodeType}">修改</a>
												</span>&nbsp; 
												<span style="cursor:hand">
													<a href="../policy/paperAssemblingRequirements.jhtml?atype=idelete&id=${item.id}&paperAssemblingRequirements.asfNode.id=${node.id}" >删除</a>
												</span>&nbsp; 
												<span style="cursor:hand">
													<a href="previewItem.jhtml?reqId=${item.id}&reqIndex=${itemStatus.index+1}" target="_blank" >预览</a>
												</span>
											</td>
										</tr>
									</c:forEach>
								</table>
								<%}%>
							</div></td>
					</tr>
				</table>
			</div></td>
	</tr>
	<tr height="10">
		<td></td>
	</tr>
</table>
<div id="_knowledgePoint" style="display:none">
<c:forEach items="${knowledgePointList}" var="item" varStatus="itemStatus"><li  _kc="${item.code}" _sc="${item.subject.code}" _gc="${item.grade.code}">${item.grade.code}/${item.name}</li>
</c:forEach>
</div>
<div id="_itemType" style="display:none">
<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus"><li  _ic="${item.code}" _sc="${item.subject.code}" _gc="${item.grade.code}">${item.grade.code}/${item.name}</li>
</c:forEach>
</div>
</body>
</html>
