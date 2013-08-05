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
<script src="../js/node/MakeSelect.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script src="../js/all/ConfirmIF.js"></script>
<script src="../js/all/ShowHide.js"></script>
<script src="../js/all/selectTree_notParent.js"></script>
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
		if(tType!=""){
			var url = "../policy/template.jhtml?atype=get&nid=${node.id}&tType="+tType; 
		 	window.location.href =url;
		}
	})
	j("input[opt=inherit]").click(function(){
		if(confirm("注意 此 继承 操作清除现有配置，确定要继续?")){
			window.location.href ="../policy/template.jhtml?atype=inherit&nid=${node.id}&tType="+j(this).attr("v");
		}
	})
	ShowHide("input[opt=optSH],span[opt=optSH]");
	j("input[opt=save]").click(function(){
		 var tid = j("#"+j(this).attr("v")).val();
		 var tType = j(this).attr("v");
		 var url = [
		 	"../studyflow/node.jhtml?atype=nodeGroupAddTemplate"
			,"&p.para.nodeGroupId=${node.id}"
			,"&p.para.tType="
			,tType
			,"&p.para.tid="
			,tid
		 ]
		 if(tid==0){
		  	if(confirm("确定要设置为空吗")){
				window.location.href=  url.join(""); 
			}
		  }else{
		 	 window.location.href=  url.join(""); 
		  }
		 
		 	 
	})
	/*edit node*/
	new ValidateForm({
		"fid":"eform"
		,"vs":[
			{n:"p.para.name",sn:"节点名称"}
		]
	});
	selectTree({outSelect:"div[name=__out]"
		,dataLst:${processNodesJson==null||processNodesJson==""?"[]":processNodesJson}
		,selectName:"p.para.pid"
		,selected:"${node.nodeGroup.id}" 
		,sonKey:"nodes"
		,theId:"${node.id}"
	}); 
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
					<td width="50%" align="right"  class="txt12blue" ><span style="cursor:hand" opt=optSH v='{h:"s_node",s:"e_node"}'>修改</span>| <span style="cursor:hand"><a href="node.jhtml?atype=delete&id=${node.id}&p.para.prid=${node.processDefinition.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{s:"s_node",h:"e_node"}'>显示</span>| <span style="cursor:hand" opt=optSH v='{h:"s_node,e_node"}'>隐藏</span> </td>
				</tr>
			</table>
			<div id="s_node">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${node.name}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> ${node.nodeType=="GROUP"?"节点组":""}
							${node.nodeType=="PRACTICE"?"训练单元":""}
							${node.nodeType=="EVALUATE"?"模块测试":""}
							${node.nodeType=="PHASETEST"?"阶段测试":""}
							${node.nodeType=="UNITTEST"?"单元测试":""} </td>
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
					<form action="node.jhtml" method="post" id="eform">
						<input type="hidden" name="atype" value="iupdateNodeGroup"/>
						<input class="logininputmanage" type="hidden" name="p.para.prid" value="${node.processDefinition.id}"  />
						<input class="logininputmanage" type="hidden" name="p.para.nid" value="${node.id}"/>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点名称：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.name" value="${node.name}"/>
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点类型：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><span id="nodeType_edit"></span>
								<select name="p.para.nodeType">
									<option value="GROUP" ${node.nodeType=="GROUP"?"selected":""} >默认(节点组)</option>
									<option value="PRACTICE" ${node.nodeType=="PRACTICE"?"selected":""} >训练单元(训练)</option>
									<option value="EVALUATE" ${node.nodeType=="EVALUATE"?"selected":""} >模块测试（评测）</option>
									<option value="PHASETEST" ${node.nodeType=="PHASETEST"?"selected":""} >阶段测试(前测)</option>
									<option value="UNITTEST" ${node.nodeType=="UNITTEST"?"selected":""} >单元测试(后测)</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">训练顺序：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="p.para.orderNum" value="${orderNumForShow}" size="10" />
								<!--${node.orderNum}-->
							</td>
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
							<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
							
							<div name="__out"></div>
							</td>
						</tr>
						<tr>
							<td width="17%"  align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">节点说明：</td>
							<td align="left" valign="top" bgcolor="#FFFFFF" colspan="3"><textarea name="p.para.description" cols="35" rows="2" >${node.description}</textarea>
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
		<td ><table width="100%" border="0" align="center" >
				<tr>
					<td><table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
							<tr>
								<td  width="50%" align="left" class="txt12blueh">流程策略</td>
								<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{s:"e_nodeGroupPolicy",h:"s_nodeGroupPolicy"}'>修改</span>| <span  style="cursor:hand" ><a href="../policy/nodeGroupPolicy.jhtml?atype=showNodedelete&id=${node.id}">删除</a></span>| <span style="cursor:hand"  opt=optSH v='{h:"e_nodeGroupPolicy",s:"s_nodeGroupPolicy"}'>显示</span>| <span  style="cursor:hand" opt=optSH v='{h:"s_nodeGroupPolicy,e_nodeGroupPolicy"}'>隐藏</span> </td>
							</tr>
						</table>
						<div id="s_nodeGroupPolicy">
							<%if (request.getAttribute("nodeGroupPolicy")==null ){%>
							<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
								<tr>
									<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
									<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
								</tr>
							</table>
							<%}else{%>
							<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
								<tr>
									<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否为显示模块：</td>
									<td width="33%" align="left" bgcolor="#FFFFFF">${nodeGroupPolicy.isDisplayModule==1?("是"):("否")} </td>
									<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
									<td width="33%" align="left" bgcolor="#FFFFFF">${nodeGroupPolicy.studyGuide} </td>
								</tr>
							</table>
							<%}%>
						</div>
						<div id="e_nodeGroupPolicy" style="display:none">
							<table  class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
								<form action="../policy/nodeGroupPolicy.jhtml"  method="post"  >
									<input type="hidden" name="atype" value="iupdateNodeGroup">
									<input type="hidden" name="nodeGroupPolicy.asfNode.id" value="${node.id}">
									<tr>
										<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否为显示模块：</td>
										<td width="33%" align="left" bgcolor="#FFFFFF"><input type="radio" name="nodeGroupPolicy.isDisplayModule" value="1" ${nodeGroupPolicy.isDisplayModule==1?"checked":""} />
											是&nbsp;&nbsp;
											<input type="radio" name="nodeGroupPolicy.isDisplayModule" value="0" ${nodeGroupPolicy.isDisplayModule==0?"checked":""}/>
											否 </td>
										<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
										<td width="33%" align="left" bgcolor="#FFFFFF"><input type="text" name="nodeGroupPolicy.studyGuide" value="${nodeGroupPolicy.studyGuide}" /> </td>
									</tr>
									<tr>
										<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
												<tr>
													<td ><input type="submit" class="btn_2k3" value="  保 存  " />
														&nbsp;&nbsp;
														<input type="button"  value="  取 消  " class="btn_2k3" opt=optSH v='{s:"s_nodeGroupPolicy",h:"e_nodeGroupPolicy"}' /></td>
												</tr>
											</table></td>
									</tr>
								</form>
							</table>
						</div></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">模块评测类节点流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{h:"s_ModuleEvalPolicyTemplate",s:"e_ModuleEvalPolicyTemplate"}'  >修改</span>| <span  style="cursor:hand" ><a href="../studyflow/node.jhtml?atype=nodeGroupDeleteTemplate&p.para.tType=ModuleEvalPolicyTemplate&p.para.nodeGroupId=${node.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{h:"e_ModuleEvalPolicyTemplate",s:"s_ModuleEvalPolicyTemplate"}' >显示</span>| <span  style="cursor:hand" opt=optSH v='{h:"s_ModuleEvalPolicyTemplate,e_ModuleEvalPolicyTemplate"}' >隐藏</span> </td>
				</tr>
			</table>
			<div style="display:none" id="e_ModuleEvalPolicyTemplate">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
			</div>
			<div id="s_ModuleEvalPolicyTemplate">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
			</div>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">阶段测试类节点流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{h:"s_PhaseTestPolicyTemplate",s:"e_PhaseTestPolicyTemplate"}'  >修改</span>| <span  style="cursor:hand" ><a href="../studyflow/node.jhtml?atype=nodeGroupDeleteTemplate&p.para.tType=PhaseTestPolicyTemplate&p.para.nodeGroupId=${node.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{h:"e_PhaseTestPolicyTemplate",s:"s_PhaseTestPolicyTemplate"}' >显示</span>| <span  style="cursor:hand" opt=optSH v='{h:"s_PhaseTestPolicyTemplate,e_PhaseTestPolicyTemplate"}' >隐藏</span> </td>
				</tr>
			</table>
			<div style="display:none" id="e_PhaseTestPolicyTemplate">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><select id="PhaseTestPolicyTemplate">
								<option  value="0" >无</option>
								<c:forEach items="${v.phaseTestPolicyTemplateList}" var="item" varStatus="itemStatus">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
								<tr>
									<td align="center"><input type="button" value="  保 存  " class="btn_2k3" opt=save v="PhaseTestPolicyTemplate"/>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" value="  取 消  " class="btn_2k3" opt=optSH v='{h:"e_PhaseTestPolicyTemplate",s:"s_PhaseTestPolicyTemplate"}'/>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
			<div id="s_PhaseTestPolicyTemplate">
				<%if(request.getAttribute("phaseTestPolicyTemplate")==null){%>
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
									<td width="15%">起始值</td>
									<td width="15%">结束值</td>
									<td width="40%">跳转位置</td>
								</tr>
								<c:forEach items="${phaseTestPolicyReqsList}" var="item" varStatus="itemStatus">
									<tr id="show_${item.id}" align="center"  bgcolor="#FFFFFF" class="linkblueor12">
										<td></td>
										<td>${item.startValue}</td>
										<td>${item.endValue}</td>
										<td><c:forEach items="${v.jumpToLst}" var="temp" varStatus="itemStatus"> ${(temp.id==item.jumpPosition)?(temp.name):("")} </c:forEach>
										</td>
									</tr>
								</c:forEach>
							</table></td>
					</tr>
				</table>
				<%}%>
			</div>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">训练单元类节点流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{h:"s_TrainingUnitPolicyTemplate",s:"e_TrainingUnitPolicyTemplate"}'  >修改</span>| <span  style="cursor:hand" ><a href="../studyflow/node.jhtml?atype=nodeGroupDeleteTemplate&p.para.tType=TrainingUnitPolicyTemplate&p.para.nodeGroupId=${node.id}">删除</a></span>| <span style="cursor:hand"opt=optSH v='{h:"e_TrainingUnitPolicyTemplate",s:"s_TrainingUnitPolicyTemplate"}'  >显示</span>| <span  style="cursor:hand" opt=optSH v='{h:"s_TrainingUnitPolicyTemplate,e_TrainingUnitPolicyTemplate"}' >隐藏</span> </td>
				</tr>
			</table>
			<div id="s_TrainingUnitPolicyTemplate">
				<%if (request.getAttribute("trainingUnitPolicyTemplate")==null ){%>
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
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingUnitPolicyTemplate.pass==1?"返回":"向前"} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">不通过：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingUnitPolicyTemplate.failed==1?"返回":"向前"} </td>
					</tr>
				</table>
				<%}%>
			</div>
			<div style="display:none" id="e_TrainingUnitPolicyTemplate">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><select name="tid" id="TrainingUnitPolicyTemplate">
								<option  value="0" >无</option>
								<c:forEach items="${v.trainingUnitPolicyTemplateList}" var="item" varStatus="itemStatus">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
								<tr>
									<td align="center"><input type="button" value="  保 存  " class="btn_2k3" opt=save v="TrainingUnitPolicyTemplate"/>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" value="  取 消  " class="btn_2k3" opt=optSH v='{h:"e_TrainingUnitPolicyTemplate",s:"s_TrainingUnitPolicyTemplate"}'/>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
			<table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td  width="50%" align="left" class="txt12blueh">单元测试类节点流转策略</td>
					<td align="right"  class="txt12blue"><span  style="cursor:hand" opt=optSH v='{h:"s_UnitTestPolicyTemplate",s:"e_UnitTestPolicyTemplate"}'>修改</span>| <span  style="cursor:hand" ><a href="../studyflow/node.jhtml?atype=nodeGroupDeleteTemplate&p.para.tType=UnitTestPolicyTemplate&p.para.nodeGroupId=${node.id}">删除</a> </span>| <span style="cursor:hand" opt=optSH v='{h:"e_UnitTestPolicyTemplate",s:"s_UnitTestPolicyTemplate"}'>显示</span>| <span  style="cursor:hand"  opt=optSH  v='{h:"s_UnitTestPolicyTemplate,e_UnitTestPolicyTemplate"}'>隐藏</span> </td>
				</tr>
			</table>
			<div id="s_UnitTestPolicyTemplate">
				<%if (request.getAttribute("unitTestPolicyTemplate")==null ){%>
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
						<td align="left" bgcolor="#FFFFFF" colspan="3"> ${unitTestPolicyTemplate.retrainingScope==0?"本级":""}
							${unitTestPolicyTemplate.retrainingScope==1?"上一级":""}
							${unitTestPolicyTemplate.retrainingScope==2?"上两级":""} </td>
					</tr>
				</table>
				<%}%>
			</div>
			<div style="display:none" id="e_UnitTestPolicyTemplate">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><select name="tid" id="UnitTestPolicyTemplate">
								<option  value="0" >无</option>
								<c:forEach items="${v.unitTestPolicyTemplateList}" var="item" varStatus="itemStatus">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
								<tr>
									<td align="center"><input type="button" value="  保 存  " class="btn_2k3" opt=save v="UnitTestPolicyTemplate"/>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" value="  取 消  " class="btn_2k3" opt=optSH v='{h:"e_UnitTestPolicyTemplate",s:"s_UnitTestPolicyTemplate"}'/>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div></td>
	</tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td ><table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
				<tr>
					<td width="50%" align="left" class="txt12blueh">训练策略</td>
					<td align="right"  class="txt12blue"><span style="cursor:hand" opt=optSH v='{h:"s_TrainingPolicyTemplate",s:"e_TrainingPolicyTemplate"}'>修改</span>| <span style="cursor:hand"><a href="../studyflow/node.jhtml?atype=nodeGroupDeleteTemplate&p.para.tType=TrainingPolicyTemplate&p.para.nodeGroupId=${node.id}">删除</a></span>| <span style="cursor:hand" opt=optSH v='{h:"e_TrainingPolicyTemplate",s:"s_TrainingPolicyTemplate"}'>显示</span>| <span style="cursor:hand" opt=optSH v='{h:"s_TrainingPolicyTemplate,e_TrainingPolicyTemplate"}'>隐藏</span>
						</div></td>
				</tr>
			</table>
			<div id="s_TrainingPolicyTemplate">
				<%if (request.getAttribute("trainingPolicyTemplate")==null ){%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
				</table>
				<%}else{%>
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.overviewTime} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.allowUnsureMark==1?"允许":"不允许"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.whenToSeeAnalysis==1?"随时":""}
							${trainingPolicyTemplate.whenToSeeAnalysis==2?"做题后":""}
							${trainingPolicyTemplate.whenToSeeAnalysis==3?"正确":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.whenToCheckAnswer==1?"随时":""}
							${trainingPolicyTemplate.whenToCheckAnswer==2?"做题后":""}
							${trainingPolicyTemplate.whenToCheckAnswer==3?"正确":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.isRandom==0?"不随机":"随机"}</td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicyTemplate.isRandomAnswerOptions==0?"不颠倒":"颠倒"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.rightRateForPass}% </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicyTemplate.rightRateRetraining}% </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.retrainingItemType==12?"错题":""} 
							${trainingPolicyTemplate.retrainingItemType==12?"错题":""} 
							${trainingPolicyTemplate.retrainingItemType==11?"未答题":""} 
							${trainingPolicyTemplate.retrainingItemType==13?"错题&未答题":""} 
							${trainingPolicyTemplate.retrainingItemType==0?"零星题":""} 
							${trainingPolicyTemplate.retrainingItemType==0.5?"半星题":""} 
							${trainingPolicyTemplate.retrainingItemType==1?"一星题":""} 
							${trainingPolicyTemplate.retrainingItemType==2?"二星题":""} 
							${trainingPolicyTemplate.retrainingItemType==3?"三星题":""} 
							${trainingPolicyTemplate.retrainingItemType==4?"四星题":""} 
							${trainingPolicyTemplate.retrainingItemType==5?"五星题":""} 
							${trainingPolicyTemplate.retrainingItemType==-1?"全部":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.retrainingItemOrder} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.retrainingRightRateTestFaile}
							% </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.retrainingItemTypeTestFaile==12?"错题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==12?"错题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==11?"未答题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==13?"错题&未答题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==0?"零星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==0.5?"半星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==1?"一星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==2?"二星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==3?"三星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==4?"四星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==5?"五星题":""} 
							${trainingPolicyTemplate.retrainingItemTypeTestFaile==-1?"全部":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.retrainingItemOrderTestFaile} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.randomAssemItemsTestFaile=="1"?"是":"否"} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.assemItemsTypeTestFaile==12?"错题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==12?"错题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==11?"未答题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==13?"错题&未答题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==0?"零星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==0.5?"半星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==1?"一星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==2?"二星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==3?"三星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==4?"四星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==5?"五星题":""} 
							${trainingPolicyTemplate.assemItemsTypeTestFaile==-1?"全部":""} </td>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.assemItemsRateTestFaile}% </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.clueContentFirstFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.clueRelActFirstFaile==1?"高亮子题考点相关处":""}
							${trainingPolicyTemplate.clueRelActFirstFaile==2?"高亮试题":""}
							${trainingPolicyTemplate.clueRelActFirstFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicyTemplate.clueRelActFirstFaile==4?"高亮选项":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.translationContentFirstFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.translationRelActFirstFaile==1?"高亮子题考点相关处":""}
							${trainingPolicyTemplate.translationRelActFirstFaile==2?"高亮试题":""}
							${trainingPolicyTemplate.translationRelActFirstFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicyTemplate.translationRelActFirstFaile==4?"高亮选项":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.clueContentSecondFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.clueRelActSecondFaile==1?"高亮子题考点相关处":""}
							${trainingPolicyTemplate.clueRelActSecondFaile==2?"高亮试题":""}
							${trainingPolicyTemplate.clueRelActSecondFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicyTemplate.clueRelActSecondFaile==4?"高亮选项":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.translationContentSecondFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.translationRelActSecondFaile==1?"高亮子题考点相关处":""}
							${trainingPolicyTemplate.translationRelActSecondFaile==2?"高亮试题":""}
							${trainingPolicyTemplate.translationRelActSecondFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicyTemplate.translationRelActSecondFaile==4?"高亮选项":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.clueContentThirdFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.clueRelActThirdFaile==1?"高亮子题考点相关处":""}
							${trainingPolicyTemplate.clueRelActThirdFaile==2?"高亮试题":""}
							${trainingPolicyTemplate.clueRelActThirdFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicyTemplate.clueRelActThirdFaile==4?"高亮选项":""} </td>
					</tr>
					<tr>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
						<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.translationContentThirdFaile} </td>
						<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
						<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.translationRelActThirdFaile==1?"高亮子题考点相关处":""}
							${trainingPolicyTemplate.translationRelActThirdFaile==2?"高亮试题":""}
							${trainingPolicyTemplate.translationRelActThirdFaile==3?"高亮子题与文章相关处":""}
							${trainingPolicyTemplate.translationRelActThirdFaile==4?"高亮选项":""} </td>
					</tr>
				</table>
				<%}%>
			</div>
			<div style="display:none" id="e_TrainingPolicyTemplate">
				<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择策略模板：</td>
						<td width="83%" align="left" bgcolor="#FFFFFF"><select name="tid" id="TrainingPolicyTemplate">
								<option  value="0" >无</option>
								<c:forEach items="${v.trainingPolicyTemplateList}" var="item" varStatus="itemStatus">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
								<tr>
									<td align="center"><input type="button" value="  保 存  " class="btn_2k3" opt=save v="TrainingPolicyTemplate"/>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" value="  取 消  " class="btn_2k3" opt=optSH v='{h:"e_TrainingPolicyTemplate",s:"s_TrainingPolicyTemplate"}'/>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
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
							<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">大题数量：</td>
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
								<div style="display:none"><script>
								$(function(){
									$("#_sc,#_gc").change(function(){
										var g = new G();		
										var _kcDef = '${paperAssemblingRequirements.knowledgePointCode}';
										var _itemTypeCodeDef = '${paperAssemblingRequirements.itemTypeCode}';
										
										var v1 = $("#_sc").val();
										var v2 = $("#_gc").val();
										$("#_kc").html("");
										$("#_itemTypeCode").html("");
										var _kcStr =["<option >请选择</option>"];
										var _itemTypeCodeStr = ["<option >请选择</option>"];
										try{
											var j_kc = g.param("#_knowledgePoint li[_sc={0}][_gc={1}]",v1,v2);
											var j_itemTypeCode = g.param("#_itemType li[_sc={0}][_gc={1}]",v1,v2);
											$(j_kc).each(function(){
												var _kc = $(this).attr("_kc");
												var selected = (_kcDef == _kc )?"selected":"";
												_kcStr[_kcStr.length]=g.param("<option value={0} {2}>{1}</option>",_kc,$(this).html(),selected);
											})
											$(j_itemTypeCode).each(function(){
												var _ic = $(this).attr("_ic");
												var selected = (_itemTypeCodeDef == _ic )?"selected":"";
												_itemTypeCodeStr[_itemTypeCodeStr.length]=g.param("<option value={0} {2}>{1}</option>",_ic,$(this).html(),selected);
											})
										}catch(e){
										}
										$("#_kc").html(_kcStr.join(""));
										$("#_itemTypeCode").html(_itemTypeCodeStr.join(""));
									}).trigger("change");
								})
								</script></div>
								<form action="../policy/paperAssemblingRequirements.jhtml" id="add_paper_assembling_requirements_form" method="post">
								<input type="hidden" name="atype" value="iadd"/>
								<input type="hidden" name="paperAssemblingRequirements.asfNode.id" value="${node.id}">
								<c:set var="_obj" value="${paperAssemblingRequirements}" scope="session"/>
								<c:set var="_objName" value="paperAssemblingRequirements" scope="session"/>
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
											<td><c:forEach items="${itemTypeList}" var="i" varStatus="Status">
													<c:if test="${item.itemTypeCode eq i.code}">(${i.code})${i.name}</c:if>
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
