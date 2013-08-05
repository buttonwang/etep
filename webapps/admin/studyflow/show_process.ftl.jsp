<#assign p=item>
<#assign pid=p.id?default("")/>
<#global PRE_SIZE=3>
<#--	node节点类型 level isGroup  -->
<#global UTIL=adminService>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${p.name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../js/datepicker/ui.datepicker.css" type="text/css" media="screen" title="core css file" charset="utf-8" />
<script src="../js/m.js"></script>
<script src="../js/all/DeleteConfirm.js"></script>
<script src="../js/all/ShowHide.js"></script>
<script src="../js/all/selectTree.js"></script>
<script src="../js/datepicker/ui.datepicker_withCn.js"></script>
<script>
j(function(){
	j("#uform").submit(function(e){
	 	if(j.trim(j("#processDefinitionName").val())==""){
			 stopBD(e,1,2);
		 	 alert("流程名不能为空");
			 j("#processDefinitionName").focus();
		}else{
			if(j("#categoryId").val()==0){
				if(confirm("您没有选择流程分类，确定提交表单吗？")){
				}else{
					stopBD(e,1,2);
				}
			};		
		} 
	})
	j("#processPolicy_form").submit(function(e){
	 	if(j.trim(j("#projectVersion_s").val())==""){
			 stopBD(e,1,2);
		 	 alert("流程策略版本不能为空");
			 j("#projectVersion_s").focus();
		}
	})
 	ShowHide("span[opt=optSH],input[opt=optSH]");
	new DeleteConfirm();
	new selectTree({dataLst:${pcListJson},selectName:"processDefinition.categoryId",outId:"out",selected:"${p.categoryId}" });
	$("#dateinput").datepicker();
})
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：训练引擎 &gt; 流程定义 &gt; 流程详情</td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
  <tr>
	<td>
      <table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
          <tr>
            <td width="50%" align="left"   class="txt12blueh">流程信息</td>    
            <td width="50%" align="right"  class="txt12blue" >
              <span  style="cursor:hand" opt=optSH v='{t:"e_process",h:"s_process"}' >修改<a href="../studyflow/process.jhtml?atype=edit&id=${p.id}"></a></span>|
              <span  style="cursor:hand" onClick=""><a href="process.jhtml?atype=release&id=${p.id}">发布</a></span>|
              <span  style="cursor:hand" onClick=""><a href="process.jhtml?atype=abandon&id=${p.id}">作废</a></span>|
              <span  style="cursor:hand" onClick=""><a href="process.jhtml?atype=delete&id=${p.id}">删除</a></span>|
              <span style="cursor:hand"  opt=optSH v='{h:"e_process",s:"s_process"}' >显示</span>|
              <span  style="cursor:hand" opt=optSH v='{h:"s_process,e_process"}'>隐藏</span>
            </td>
          </tr> 
      </table>
	  <div id="s_process">
      <table id="table1" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${p.name}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程状态：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
<#switch p.releaseStatus >
  <#case "UNRELEASE">未发布<#break>
  <#case "RELEASE">已发布<#break>
  <#case "ABANDON">已作废<#break>
  <#default>
</#switch>
			</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${p.description?if_exists } </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程分类：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" > ${processCategoryService.getProcessCategoryName(p)} </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建人：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${p.creator?if_exists } </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><#if p.createTime?exists>${p.createTime?date}</#if></td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">更新人：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${p.updator?if_exists}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">更新时间：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><#if p.updateTime?exists>${p.updateTime?date}</#if></td>
          </tr>
		   <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">大题数量：</td>
            <td width="33%" align="left" valign="top"   bgcolor="#FFFFFF"  >${Request.bigItemsNum?default(0)}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">小题数量：</td>
            <td width="33%" align="left" valign="top"  bgcolor="#FFFFFF"  >${Request.itemsNum?default(0)}</td>
          </tr>
		   <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答题时间：</td>
            <td width="33%" align="left" valign="top"   bgcolor="#FFFFFF"  >${Request.totalAnsweringTimeHours?default(0)}小时&nbsp;${Request.totalAnsweringTime?default(0)}分钟</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
            <td width="33%" align="left" valign="top"  bgcolor="#FFFFFF"  >${p.defVersion?if_exists}</td>
          </tr>
      </table>
	  </div>
	  
	  <div id="e_process" style="display:none">
		<table id="table2" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
        <form action="process.jhtml"  method="post" id="uform">
		<input type="hidden" name="atype" value="iupdate" />
		<input type="hidden" name="processDefinition.id" value="${p.id}" /><tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" id="processDefinitionName" name="processDefinition.name" value="${p.name}" />
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程状态：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="p.para.releaseStatus" disabled="disabled" >
					<option value="0" <#if p.releaseStatus == "UNRELEASE">selected</#if> > 未发布</option>
					<option value="1" <#if p.releaseStatus == "RELEASE">selected</#if> >已发布</option>
					<option value="-1" <#if p.releaseStatus == "ABANDON">selected</#if> >已作废</option>
				</select>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    			<textarea name="processDefinition.description"  cols="35" rows="3" class="logininputmanage">${p.description?if_exists}</textarea>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程分类：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >
				<div id="out"></div>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3">
          		<input class="logininputmanage" type="text" id="defVersion" name="processDefinition.defVersion" value="${p.defVersion}" />
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
                    <input type="button"  class="btn_2k3" opt=optSH v='{h:"e_process",s:"s_process"}'  value="  取 消  "  />
                    </td>
                </tr>
              </table>
            </td>
          </tr>
	</form>
      </table>
	</div>
      
    </td>
  </tr>
</table>

<table width="100%" border="0" align="center" >
  <tr>
	<td>
      <table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#E9F0F6">
          <tr>
            <td width="50%" align="left"   class="txt12blueh">流程策略</td>    
            <td width="50%" align="right"  class="txt12blue" >
              <span style="cursor:hand" opt=optSH v='{h:"s_processPolicy",t:"e_processPolicy"}'>修改</span>|
              <span style="cursor:hand"><a href="../policy/processPolicy.jhtml?atype=idelete&id=${p.id}">删除</a></span>|
			  <span style="cursor:hand"  opt=optSH v='{s:"s_processPolicy",h:"e_processPolicy"}' >显示</span>|
              <span  style="cursor:hand" opt=optSH v='{h:"s_processPolicy,e_processPolicy"}'>隐藏</span>
            </td>
          </tr> 
      </table><div id="s_processPolicy">
	  
      
	  <#if processPolicy?exists>
	  <table id="table3" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">当天重复登录系统流转策略：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><#if processPolicy.reloginSameDayPolicy?exists && processPolicy.reloginSameDayPolicy =0 >原有流程<#else>第一个未通过训练节点</#if></td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">隔天再次登录系统流转策略：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><#if processPolicy.reloginAnotherDayPolicy?exists && processPolicy.reloginAnotherDayPolicy =0 >原有流程<#else>第一个未通过训练节点</#if> </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">转入生成训练生词数量：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.freshWordSum?if_exists} <#if processPolicy.freshWordSum?exists>个</#if></td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">建议训练时长：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >${processPolicy.suggestTrainingTime} <#if processPolicy.suggestTrainingTime?exists>小时</#if></td>
          </tr>
		  <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答题默认时长：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.defaultTestTime?if_exists} </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否显示答案：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  ><#if processPolicy.isShowAnswer?default(0)==1>是<#else>否</#if></td>
          </tr>
          <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >${processPolicy.studyDirection?if_exists}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否使用显示模块策略：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  ><#if processPolicy.isUsedDms?default(1)==0>是<#else>否</#if></td>
          </tr>
		   <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  ><#if processPolicy.projectVersion?exists><#if processPolicy.projectVersion=="ky">考研</#if><#if processPolicy.projectVersion=="mpc">数理化</#if></#if></td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">默认考试时间：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  ><#if processPolicy.defaultExamTime?exists>${processPolicy.defaultExamTime?date }</#if></td>
          </tr>
		  <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习信息：</td>
            <td width="83%" align="left" valign="top" colspan="3" bgcolor="#FFFFFF"  >${processPolicy.studyInfo?default("")}</td>            
          </tr>
      </table>
	  <#else>
	  <table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
					<tr>
						<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
						<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
					</tr>
	  </table>
	   </#if>
	  </div>
	  <div id="e_processPolicy" style="display:none" >	 
      <table id="table4" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
      <form action="../policy/processPolicy.jhtml" method="post"  id="processPolicy_form" > <input name="processPolicy.asfProcessDefinition.id" value="${p.id}" type="hidden"> 
	  <input name="processPolicy.asfProcessDefinition.categoryId" value="${p.categoryId}" type="hidden"> 
	  <input name="atype" value="add" type="hidden">   <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">当天重复登录系统流转策略：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="processPolicy.reloginSameDayPolicy" id="processPolicy.reloginSameDayPolicy">
                    <option value="0" <#if processPolicy?exists && processPolicy.reloginSameDayPolicy?exists && processPolicy.reloginSameDayPolicy ==0> selected</#if>>原有流程</option>
                    <option value="1" <#if processPolicy?exists && processPolicy.reloginSameDayPolicy?exists && processPolicy.reloginSameDayPolicy ==1> selected</#if>>第一个未通过训练节点</option>
                </select> 
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">隔天再次登录系统流转策略：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="processPolicy.reloginAnotherDayPolicy" id="processPolicy.reloginAnotherDayPolicy">
                    <option value="0" <#if processPolicy?exists && processPolicy.reloginAnotherDayPolicy?exists && processPolicy.reloginAnotherDayPolicy ==0> selected</#if>>原有流程</option>
                    <option value="1" <#if processPolicy?exists && processPolicy.reloginAnotherDayPolicy?exists && processPolicy.reloginAnotherDayPolicy ==1> selected</#if>>第一个未通过训练节点</option>
                </select>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">转入生成训练生词数量：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="processPolicy.freshWordSum" value="<#if processPolicy?exists >${processPolicy.freshWordSum?if_exists}</#if>"/>
                个
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">建议训练时长：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >
                <input class="logininputmanage" type="text" name="processPolicy.suggestTrainingTime" value="<#if processPolicy?exists >${processPolicy.suggestTrainingTime?if_exists}</#if>"/>
                小时
            </td>
          </tr>
		  <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答题默认时长：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="processPolicy.defaultTestTime" value="<#if processPolicy?exists >${processPolicy.defaultTestTime?if_exists}</#if>"/>小时</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否显示答案：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  > 
            	<input type="radio" name="processPolicy.isShowAnswer" value='1'  <#if processPolicy?exists><#if processPolicy?exists && processPolicy.isShowAnswer?default(0)==1>checked</#if></#if> />是&nbsp;
				<input type="radio" name="processPolicy.isShowAnswer" value='0'  <#if processPolicy?exists><#if processPolicy?exists && processPolicy.isShowAnswer?default(0)==0>checked</#if></#if> />否
                   	 
          </tr>
          <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  ><textarea name="processPolicy.studyDirection" cols="35" rows="3"><#if processPolicy?exists >${processPolicy.studyDirection?if_exists}</#if></textarea>&nbsp;</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否使用显示模块策略：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >
				<input type="radio" name="processPolicy.isUsedDms" value='0'  <#if processPolicy?exists><#if processPolicy.isUsedDms?default(1)==0>checked</#if></#if> />是&nbsp;
				<input type="radio" name="processPolicy.isUsedDms" value='1'  <#if processPolicy?exists><#if processPolicy.isUsedDms?default(1)==1>checked</#if></#if> />否</td>
          </tr>
		  <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >
			<select name=processPolicy.projectVersion id="projectVersion_s">
			 <option value="">请选择</option>
			 <option value="ky"  <#if processPolicy?exists && processPolicy.projectVersion?default("")=="ky" >selected</#if>>考研</option>
			 <option value="mpc" <#if processPolicy?exists && processPolicy.projectVersion?default("")=="mpc" >selected</#if>>数理化</option>			 
			</select>
			 </td>
           <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">默认考试时间：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  ><input type="text" id="dateinput" name="processPolicyDefaultExamTime" value='<#if processPolicy?exists ><#if processPolicy.defaultExamTime?exists>${processPolicy.defaultExamTime?string("yyyy-MM-dd")}</#if></#if>'   /></td>
		 </tr>
		  <tr>
		  	 
           <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习信息：</td>
            <td width="87%" align="left" colspan="3" bgcolor="#FFFFFF"  ><textarea type="text" id="dateinput" name="processPolicy.studyInfo"><#if processPolicy?exists >${processPolicy.studyInfo?default("")}</#if></textarea></td>
		 </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" opt=optSH v='{h:"e_processPolicy",s:"s_processPolicy"}'  />
                    </td>
                </tr>
              </table>
            </td>
          </tr></form>
      </table>
	
	  </div>
      
    </td>
  </tr>
</table>

<table width="100%" border="0" align="center" >
  <tr>
    <td >
	  <table width="100%" border="0" align="center"	 cellpadding="6" cellspacing="0" class="tilte_bg2">
	    <tr>
	      <td  width="50%" align="left"    class="txt12blueh">本流程节点列表</td>
          <td align="right"  class="txt12blue">
            <input type="button" value=" 新增节点 " class="btn_2k3" onClick="javascript: window.location.href='../studyflow/node.jhtml?atype=sadd&p.para.prid=${pid}&prid=${pid}';"/>
          </td>
	    </tr>
	  </table>
    </td>
  </tr>
</table>
<script src="../js/MzTreeView_ProcessNodes.js"></script>
<style>
body {font:normal 12px 宋体}
a.MzTreeview /* TreeView 链接的基本样式 */ { cursor: hand; color: #000080; margin-top: 5px; padding: 0 0 0 0; text-decoration: none; }
.MzTreeview a.select /* TreeView 链接被选中时的样式 */ { color: highlighttext; background-color: highlight; }
#_out input {
	vertical-align:middle;
}
.MzTreeViewRow {border:none;width:500px;padding:0px;margin:0px;border-collapse:collapse;color:#008BC7;}
.MzTreeViewCell0 {border-bottom:1px solid #CCCCCC;padding:0px;margin:0px;}
.MzTreeViewCell1 {border-bottom:1px solid #CCCCCC;border-left:1px solid #CCCCCC;width:200px;padding:0px;margin:0px;}
.wtb{width:100%}/*节点名称*/
.wtd1{text-align:left;width:40%}/*节点名称*/
.wtd2{text-align:left;width:20%}/*节点排序*/
.wtd3{text-align:left;width:15%}/*节点类型*/
.wtd4{width:25%;text-align:center}/*节点操作*/
</style>
<div id="_out" style="background:url(images/outDivBackGround.png)"></div>
<script language="javascript" type="text/javascript">
function getN(nodeForMz){
	var data=[];
	if(nodeForMz){
		for(var i in nodeForMz){
			var o = nodeForMz[i].obj;
			var url = "#";
			try{ url = "node.jhtml?atype=showAll&id="+o.id+"&prid="+o.prid;}catch(e){};
			if(	o.type=="节点组"){ 
				o._copyProcess ="<a href='process!copyProcess.jhtml?p.para.ngid="+o.id+"'>复制流程</a>";
				try{url ="node.jhtml?atype=showGroupAll&id="+o.id+"&prid="+o.prid; }catch(e){};
			}else{
				o._copyProcess ="　　　　";
			}
			data[nodeForMz[i].pid_id] = "ctrl:sel;checked:1;url:"+url+";T:"+(nodeForMz[i].obj.name||"")+";";
		}
	}
	return data;
}
 
try{
	var MzTreeViewTH="<table class='MzTreeViewRow wtb'><tr><td class='MzTreeViewCell0 wtd1'>";
	var MzTreeViewTD="</td><td class='MzTreeViewCell0 wtd2'>{orderNum}</td><td class='MzTreeViewCell0  wtd3'>{type}</td></td><td class='MzTreeViewCell1 wtd4'>&nbsp;{_copyProcess}&nbsp;&nbsp;<a href='node.jhtml?atype=edit&to=showProcess&id={id}&processId={prid}&prid={prid}'>修改</a>&nbsp;&nbsp;<a href='node.jhtml?atype=sadd&p.para.pid={id}&p.para.prid={prid}&p.para.nid={id}&prid={prid}'>增加</a>&nbsp;&nbsp;<a href='node.jhtml?atype=delete&id={id}&p.para.prid={prid}'>删除</a></td></tr></table>";	
	var nodeForMz= 	${Request.nodeJSTreeListJson} ;
	window.tree = new MzTreeView("tree",nodeForMz ,getN);
	tree.setIconPath("images/"); 
	tree.setURL("#");
	tree.wordLine = false;
	document.getElementById("_out").innerHTML=tree.toString();
	tree.expandAll();	
}catch(e){}
</script>
</body>
</html>