<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="../js/js4cnltreemenu.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript">
var nodeInstanceId='nodeInstanceId${nodeInstanceId}';
function clickNode(id){
	if(nodeInstanceId=='nodeInstanceId0')
		document.getElementById('nodeInstanceId0').className='f16px fB';
	else
		document.getElementById(nodeInstanceId).className='';
		
	if(id=='nodeInstanceId0')
		document.getElementById('nodeInstanceId0').className='f16px on_none';
	else
		document.getElementById(id).className='on_none';
	nodeInstanceId=id;
	
}
</script>
</head>  

<body>

<jsp:include page="report_top.jsp"></jsp:include>

 <!-- 页面主题 -->
<div id="main" class="wm4">
	<div class="CNLTreeMenu" id="CNLTreeMenu1">
      <div style="padding-left:10px;"><a href="study.jhtml" target="main" id="nodeInstanceId0" <c:if test="${nodeInstanceId==0}"> class="f16px on_none" </c:if><c:if test="${nodeInstanceId>0}">  class="f16px fB"  </c:if> onclick="clickNode('nodeInstanceId0');">${userDataVO.processName}</a></div>
    <c:set var="i" value="${'1'}" ></c:set>
    <c:forEach items="${nodeList}" var="item" varStatus="itemStatus">  	
    	<c:choose> 
    		<c:when test="${item.nodeType eq 'GROUP'}"> 
    				<c:if test="${i==0}"></ul></li></c:if>
     				<c:if test="${i==1}"><ul><c:set var="i" value="${'0'}" ></c:set></c:if>
     				<li><a id="${item.nodeDefinitionId}" href="study.jhtml?nodeInstanceId=${item.nodeInstanceId}" target="main"  onclick="clickNode('nodeInstanceId${item.nodeInstanceId}');"><span id="nodeInstanceId${item.nodeInstanceId}" <c:if test="${nodeInstanceId==item.nodeInstanceId}"> class="on_none" </c:if>>${item.nodeName}</span></a><ul class="Child">
	   		</c:when> 
    		<c:otherwise>
     				<li class="Child">     				
     				<a href="study.jhtml?nodeInstanceId=${item.nodeInstanceId}&showListType=0" target="main"  
     				   onclick="clickNode('nodeInstanceId${item.nodeInstanceId}');"><span id="nodeInstanceId${item.nodeInstanceId}" <c:if test="${nodeInstanceId==item.nodeInstanceId}"> class="on_none" </c:if>>${item.nodeName}</span>
     				 </a>
     				 </li>
    		</c:otherwise>
   		</c:choose>
    </c:forEach>
     </ul></li><!--Sub Node 2-->
  </ul>
</div>
<script type="text/javascript">
var MyCNLTreeMenu1=new CNLTreeMenu("CNLTreeMenu1","li");
MyCNLTreeMenu1.InitCss("Opened","Closed","Child","../images/s.gif");
<c:if test="${nodeGroupId>0}">
ExCls(document.getElementById('${nodeGroupId}'),'Opened','Closed',1);
</c:if>
</script>
	<div class="nTab wm5">
    <iframe width="795" name="main" id="frame_content" src="study.jhtml?nodeInstanceId=${nodeInstanceId}&showListType=${showListType}" scrolling="no" frameborder="0" onload="this.height=100"></iframe>
		</div>
		<script type="text/javascript">
			function reinitIframe(){
				var iframe = document.getElementById("frame_content");
				try{
					var bHeight = iframe.contentWindow.document.body.scrollHeight;
					var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
					var height = Math.max(bHeight, dHeight);
					iframe.height =  height;
				}catch (ex){}
			}
			window.setInterval("reinitIframe()", 200);
			
			function checkHeight() {
				var iframe = document.getElementById("frame_content");
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				alert("bHeight:" + bHeight + ", dHeight:" + dHeight);
			}
		</script>
    <div class="clear"> </div>
</div>
<jsp:include page="report_buttom.jsp"></jsp:include>

<div id=sign02 style='z-index:30; position:absolute; left:expression((body.clientWidth-500)/2);top:expression((body.clientHeight-500)/2);display:none;' align='center' > 
  <c:if test="${userDataVO.processStatus==1}">
  <table width="500" height="200" border="0" cellspacing="0">
    <tr> 
      <td align="center" class="comm" style="line-height:40px;">
      	<p><img src="../images/icon_alert.gif" align="bottom" /> 
        	 上次从${pauseInfoVO.titleInfo}
        	 <br>${pauseInfoVO.pauseType}中暂存退出,
        	 <br>你必须完成上次训练才能做其他训练。
         </span> <br/>         
          共${pauseInfoVO.totalItemsNum}题，已做${pauseInfoVO.finishedItemsNum}题。
          <br>限时${pauseInfoVO.totalAnsweringTime}，已用${pauseInfoVO.consumeTime}。</p>
        <p><font color="#CC3300"> 
          <input type="button" value=" 继续训练 " onclick="dosubmit();" id="submitButton"/> <input type="button" value=" 取 消 " id="cancel" onclick="cancel();" />
          </font>
        </p>
      </td>
    </tr>
  </table>
  </c:if>
  <c:if test="${userDataVO.processStatus==2}">
  <table width="500" height="200" border="0" cellspacing="0">
    <tr> 
      <td align="center" class="comm" style="line-height:40px;">
      	<p><img src="../images/icon_alert.gif" align="bottom" /> 
        	 你标记了${wordNum}个生词，请先进行生词训练，<br>解决掉这些生词吧！
         <br/>         
          </p>
        <p><font color="#CC3300"> 
          <input type="button" value=" 好，开始生词训练 " onclick="document.getElementById("submitButton").disabled='true';document.getElementById("cancel").disabled='true';top.location.href='../freshword/freshWordFace.jhtml';" id="submitButton"/> <input type="button" value=" 暂不训练 " id="cancel" onclick="cancel();" />
          </font>
        </p>
      </td>
    </tr>
  </table>
  </c:if>
</div>
<DIV id=sign01 style='z-index:20;position:absolute; top:0px; left:0px; WIDTH:100%; HEIGHT:expression(document.body.scrollHeight);
	background-color:#eeeeee; filter:alpha(opacity=70); -moz-opacity: 0.7; opacity: 0.7;display:none;' align='center'> 
</div>
<script type="text/javascript">	
	function dosubmit(){
		document.getElementById("submitButton").disabled='true';
		document.getElementById("cancel").disabled='true';
		location.href="../exam/goExam.jhtml?nodeInstanceId=${pauseInfoVO.nodeInstanceId}&isPause=1";	
	}
	function cancel(){
		doit(sign01);
		doit(sign02);
		obj=window.frames['frame_content'].document.getElementById('selectType');
		if(obj!=null)
			doit(obj);
	}
	function redo(scope,flowItemId){	
		<c:if test="${userDataVO.processStatus>0}">
			doit(sign01);
			doit(sign02);
			obj=window.frames['frame_content'].document.getElementById('selectType');
			if(obj!=null)
				doit(obj);
			sign02.focus();
			return ;
		</c:if>
		location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=3&scope="+scope;			
	}
</script>
</body>
</html>