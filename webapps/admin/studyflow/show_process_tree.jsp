<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="ambow" prefix="ambow"%>
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
function doSel() {
	window.open('showItem!getStudyGuideTree.jhtml?type=${p.categoryId}&itemType='+document.getElementById('studyGuideCodes').value,
			"newWindow", 
			"height=600, width=500, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=300");
}
</script>
</head>
<body>

<c:set var="pid" value="${p.id}" scope="request"/>

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
            ${p.releaseStatus eq 'UNRELEASE' ? '未发布':(p.releaseStatus eq 'RELEASE'?'已发布':(p.releaseStatus eq 'ABANDON'?'已作废':'')) }
			</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">流程说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${p.description}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">流程分类：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >${pc}</td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">创建人：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${p.creator} </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">创建时间：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><fmt:formatDate pattern="yyyy年M月dd日 " value="${p.createTime }"/></td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">更新人：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${p.updator}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">更新时间：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF"><fmt:formatDate pattern="yyyy年M月d日 " value="${p.updateTime }"/></td>
          </tr>
		   <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">大题数量：</td>
            <td width="33%" align="left" valign="top"   bgcolor="#FFFFFF">${bigItemsNum}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">小题数量：</td>
            <td width="33%" align="left" valign="top"  bgcolor="#FFFFFF">${itemsNum}</td>
          </tr>
		  <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">答题时间：</td>
            <td width="33%" align="left" valign="top"   bgcolor="#FFFFFF">${totalAnsweringTimeHours}小时&nbsp;${totalAnsweringTime}分钟</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">流程版本：</td>
            <td width="33%" align="left" valign="top"  bgcolor="#FFFFFF">${p.defVersion}</td>
          </tr>
      </table>
	  </div>
	  
	  <div id="e_process" style="display:none">
	  	<form action="process.jhtml"  method="post" id="uform">
		<input type="hidden" name="atype" value="iupdate" />
		<input type="hidden" name="processDefinition.id" value="${p.id}"/>
		<table id="table2" class="txt12555555line-height"  width="100%" border="0" align="center" 
			cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" >
		  <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程名称：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
            	<input class="logininputmanage" type="text" id="processDefinitionName" name="processDefinition.name" value="${p.name}" />
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程状态：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="p.para.releaseStatus" disabled="disabled" >
					<option value="0" ${p.releaseStatus eq 'UNRELEASE'?'selected':''}> 未发布</option>
					<option value="1" ${p.releaseStatus eq 'RELEASE'?'selected':''}>已发布</option>
					<option value="-1" ${p.releaseStatus eq 'ABANDON'?'selected':''}>已作废</option>
				</select>
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
    			<textarea name="processDefinition.description"  cols="35" rows="3" class="logininputmanage">${p.description}</textarea>
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
      </table>
      </form>
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
      </table>
 <div id="s_processPolicy">
 <c:choose>
 <c:when test="${not empty processPolicy}">
	 <table id="table3" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" 
	 		cellspacing="1" bgcolor="#E3E3E3">
        <tr>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">当天重复登录系统流转策略：</td>
          <td width="33%" align="left" bgcolor="#FFFFFF">
	      	<c:if test="${processPolicy.reloginSameDayPolicy ne null}">
	          	<c:if test="${processPolicy.reloginSameDayPolicy eq 0}">原有流程</c:if>
	          	<c:if test="${processPolicy.reloginSameDayPolicy eq 1}">第一个未通过训练节点</c:if>
	      	</c:if>
          </td>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">隔天再次登录系统流转策略：</td>
          <td width="33%" align="left" bgcolor="#FFFFFF">
          	<c:if test="${processPolicy.reloginAnotherDayPolicy ne null}">
	      		<c:if test="${processPolicy.reloginAnotherDayPolicy eq 0}">原有流程</c:if>
	          	<c:if test="${processPolicy.reloginAnotherDayPolicy eq 1}">第一个未通过训练节点</c:if>
	        </c:if>
          </td>
        </tr>
        <tr>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">转入生成训练生词数量：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.freshWordSum} 个</td>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">建议训练时长：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.suggestTrainingTime}小时</td>
        </tr>
  		<tr>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答题默认时长：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.defaultTestTime} </td>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否显示答案：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.isShowAnswer eq 1 ?'是':'否'}</td>
        </tr>
   		<tr>
  	     <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习说明：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF" >${processPolicy.studyDirection}</td>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">是否使用显示模块策略：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${processPolicy.isUsedDms eq 1 ?'是':'否'}</td>
   		</tr>
   		<tr>
  		  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
          ${processPolicy.projectVersion eq 'ky' ? '考研' :(processPolicy.projectVersion eq 'mpc' ? '数理化' : '')}</td>
          <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">默认考试时间：</td>
          <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
          <fmt:formatDate pattern="yyyy年M月d日" value="${processPolicy.defaultExamTime}"/></td>
   		</tr>
	    <tr>
	  	  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">学习信息：</td>
	      <td width="33%" align="left"  valign="top" bgcolor="#FFFFFF">${processPolicy.studyInfo}</td>            
	  	  <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">学习指导：</td>
	      <td width="33%" align="left"  valign="top" bgcolor="#FFFFFF">${names}</td>            
	   </tr>
	</table>
  </c:when>
  <c:otherwise>
	  <table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
			<tr>
				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue"></td>
				<td width="83%" align="left" bgcolor="#FFFFFF">没有配置</td>
			</tr>
	  </table>
  </c:otherwise>
  </c:choose>
	  </div>
	  <div id="e_processPolicy" style="display:none">
	  <form action="../policy/processPolicy.jhtml?ids="+document.getElementById('studyGuideNames').value 
	  	method="post" id="processPolicy_form">
      <input name="processPolicy.asfProcessDefinition.id" value="${p.id}" type="hidden"> 
	  <input name="processPolicy.asfProcessDefinition.categoryId" value="${p.categoryId}" type="hidden"> 
	  <input name="atype" value="add" type="hidden">   
      <table id="table4" class="txt12555555line-height" width="100%" border="0" align="center" cellpadding="6" 
      	cellspacing="1" bgcolor="#E3E3E3">
	  	<tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">当天重复登录系统流转策略：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="processPolicy.reloginSameDayPolicy" id="processPolicy.reloginSameDayPolicy">
                	<option value="0" ${not empty processPolicy.reloginSameDayPolicy && processPolicy.reloginSameDayPolicy eq 0 ? 'selected' : ''}>原有流程</option>
                    <option value="1" ${not empty processPolicy.reloginSameDayPolicy && processPolicy.reloginSameDayPolicy eq 1 ? 'selected' : ''}>第一个未通过训练节点</option>
                </select> 
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">隔天再次登录系统流转策略：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">
                <select name="processPolicy.reloginAnotherDayPolicy" id="processPolicy.reloginAnotherDayPolicy">
                    <option value="0" ${not empty processPolicy.reloginAnotherDayPolicy && processPolicy.reloginAnotherDayPolicy eq 0 ? 'selected' : ''}>原有流程</option>
                    <option value="1" ${not empty processPolicy.reloginAnotherDayPolicy && processPolicy.reloginAnotherDayPolicy eq 1 ? 'selected' : ''}>第一个未通过训练节点</option>
                </select>
            </td>
     	</tr>
        <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">转入生成训练生词数量：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
                <input class="logininputmanage" type="text" name="processPolicy.freshWordSum" value="${processPolicy.freshWordSum}"/>个
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">建议训练时长：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >
                <input class="logininputmanage" type="text" name="processPolicy.suggestTrainingTime" value="${processPolicy.suggestTrainingTime}"/>小时
            </td>
        </tr>
		<tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7" class="txt12blue">答题默认时长：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            	<input class="logininputmanage" type="text" name="processPolicy.defaultTestTime" value="${processPolicy.defaultTestTime}"/>小时
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否显示答案：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  > 
            	<input type="radio" name="processPolicy.isShowAnswer" value='1' ${processPolicy.isShowAnswer eq 1 ? 'checked' : '' }/>是&nbsp;
				<input type="radio" name="processPolicy.isShowAnswer" value='0' ${processPolicy.isShowAnswer eq 0 ? 'checked' : '' }/>否
        </tr>
        <tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习说明：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            	<textarea name="processPolicy.studyDirection" cols="35" rows="3">${processPolicy.studyDirection}</textarea>&nbsp;
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否使用显示模块策略：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
				<input type="radio" name="processPolicy.isUsedDms" value='1' ${not empty processPolicy.isUsedDms && processPolicy.isUsedDms eq 1 ? 'checked' : '' } />是&nbsp;
			    <input type="radio" name="processPolicy.isUsedDms" value='0' ${empty processPolicy.isUsedDms || processPolicy.isUsedDms eq 0 ? 'checked' : '' } />否
		   </td>
        </tr>
		<tr>
		  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">流程版本：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"  >
			<select name=processPolicy.projectVersion id="projectVersion_s">
			 <option value="">请选择</option>
			 <option value="ky" ${processPolicy.projectVersion eq 'ky' ? 'selected':''}>考研</option>
			 <option value="mpc" ${processPolicy.projectVersion eq 'mpc' ? 'selected':''}>数理化</option>			 
			</select>
			</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">默认考试时间：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
            	<input type="text" id="dateinput" name="processPolicyDefaultExamTime"
             		value="<fmt:formatDate pattern="yyyy-MM-dd" value="${processPolicy.defaultExamTime}"/>" />
            </td>
		</tr>
		<tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习信息：</td>
            <td width="33%" align="left"  valign="top" bgcolor="#FFFFFF">
            	<textarea type="text" id="dateinput" name="processPolicy.studyInfo">${processPolicy.studyInfo}</textarea>
            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学习指导：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> 
            	<input class="logininputmanage" type="text" name="processPolicy.studyGuideNames" id="studyGuideNames" value="${names }" readonly="readonly"/> 
				<input type="hidden" name="processPolicy.studyGuideCodes" id="studyGuideCodes" value="${processPolicy.studyGuideCodes }" />&nbsp;&nbsp;		 		
				<input type="button" value="选择" class="btn_2k3"  onClick="doSel();"/>
			</td>
		</tr>
        <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF">
              <table border="0" width="100%">
                <tr>
                    <td align="center">
                    <input type="submit" value="  保 存  " class="btn_2k3"/>&nbsp;&nbsp;
                    <input type="button" value="  取 消  " class="btn_2k3" opt=optSH v='{h:"e_processPolicy",s:"s_processPolicy"}'/>
                    </td>
                </tr>
              </table>
            </td>
         </tr>
      </table>
      </form>
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
            <input type="button" value=" 新增节点 " class="btn_2k3" 
            	onClick="javascript: window.location.href='../studyflow/node.jhtml?atype=sadd&p.para.prid=${pid}&prid=${pid}';"/>
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
	var MzTreeViewTD="</td><td class='MzTreeViewCell0 wtd2'>{orderNum}</td><td class='MzTreeViewCell0  wtd3'>{type}</td></td>" +
					 "<td class='MzTreeViewCell1 wtd4'>&nbsp;{_copyProcess}&nbsp;&nbsp;" +
					 "<a href='node.jhtml?atype=edit&to=showProcess&id={id}&processId={prid}&prid={prid}'>修改</a>" +
					 "&nbsp;&nbsp;<a href='node.jhtml?atype=sadd&p.para.pid={id}&p.para.prid={prid}&p.para.nid={id}&prid={prid}'>增加</a>" +
					 "&nbsp;&nbsp;<a href='node.jhtml?atype=delete&id={id}&p.para.prid={prid}'>删除</a></td></tr></table>";	
	var nodeForMz= 	${nodeJSTreeListJson} ;
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