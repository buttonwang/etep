<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弱项强化——评测</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/prototype.js"></script>
<script type="text/javascript">
function getContents(processInstanceId,num,version)
{
  var request_url="consolidateAdviser!node.jhtml";       // 请求url
  var request_pars = 'processInstanceId='+processInstanceId+'&version='+version; //请求参数

  var myAjax = new Ajax.Updater('prompt'+num, request_url,{
  method     : 'get', // http 请求方法,get or post
  parameters : request_pars, // 请求参数
  onFailure  : reportError, // 失败的时候调用 reportError 函数处理
  onLoading  : loading, // 加载时
  onComplete : done     // 读取完毕
  });
  
  openShutManager(this,'prompt'+num);
}


function loading()
{
	$('loading').style.display = 'block';
}

function done()
{
	$('loading').style.display = 'none';
}
	
function reportError(request)
{
  alert('Sorry. There was an error.');
}
</script>
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
	  <c:if test="${sxSize>0}">
      <li class="<c:if test="${categoryId==8}">active</c:if><c:if test="${categoryId!=8}">normal</c:if>" ><a class="cBlack" href="consolidateAdviser.jhtml?actor=${actor}&categoryId=8">爆破数学</a></li>
	  </c:if>
	  <c:if test="${wlSize>0}">
      <li class="<c:if test="${categoryId==7}">active</c:if><c:if test="${categoryId!=7}">normal</c:if>" ><a class="cBlack" href="consolidateAdviser.jhtml?actor=${actor}&categoryId=7">爆破物理</a></li>
	  </c:if>
	  <c:if test="${hxSize>0}">
      <li class="<c:if test="${categoryId==6}">active</c:if><c:if test="${categoryId!=6}">normal</c:if>" ><a class="cBlack" href="consolidateAdviser.jhtml?actor=${actor}&categoryId=6">爆破化学</a></li>
	  </c:if>
	  <c:if test="${kySize>0}">
      <li class="<c:if test="${categoryId==4}">active</c:if><c:if test="${categoryId!=4}">normal</c:if>" ><a class="cBlack" href="consolidateAdviser.jhtml?actor=${actor}&categoryId=4">${userDataVO.processCategoryName}</a></li>
	  </c:if>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
		<c:forEach items="${processList}" var="process" varStatus="processStatus">
        <h1 class="til"><span>“${process['name']}”</span>已学习${process['learning_process_rate']}%
		<c:if test="${process['learning_process_rate']>0}">
		<label class="plus f12px">
		<a href="javascript:void(null)" onclick="getContents(${process['id']},${processStatus.count},'${version}')">点击查看</a>
		</label>
		</c:if>
		</h1>
        <div class="bg" id="prompt${processStatus.count}" style="display:none;">

        </div>
        <div class="blank6"></div>
        </c:forEach>
        </div>
        <div class="lianxi_ts">
        <h1 class="cRed">*请特别注意掌握度低于60%的数据及有无星级题，并提醒学生重练星级题、提高掌握度。</h1></div>
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
