<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>试题审校</title>
<link rel="stylesheet" href="../css/style_blue.css" type="text/css" />
<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" /> 
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/exam.js"></script>
<script type="text/javascript">
	function reviseAnswer() {
		var form1=document.getElementById("examForm");
		<c:if test="${ 'reviseStatus' eq fromPage }">
		form1.setAttribute("action","reviseExam.jhtml?fromPage=${fromPage}");
		</c:if>
		<c:if test="${ 'reviseStatus' ne fromPage }">
		form1.setAttribute("action","reviseExam.jhtml?fromPage=reviseReply");
		</c:if>
		var userAnswers=document.getElementById("userAnswers");
		if(userAnswers!=null){
			userAnswers.value = makeAnswerStr();
		}
		form1.submit();
	}

	function lock(){
		var idn = "loading_" + $("#reviseId").val();
		document.getElementById(idn).style.display = "";
		$("#hasLocked").val("yes");
	}
	function releaseLock(){
		var idn = "loading_" + $("#reviseId").val();
		document.getElementById(idn).style.display = "none";
		$("#hasLocked").val("no");
	}
	function checkLock(){
		if($("#hasLocked").val()=="yes"){
			alert("正在保存数据！请稍候再执行其它操作...");
			return true;
		}
		return false;
	}

	//如果ajax调用失败
	function missFun(infor, idn){
		$("#after_miss_con_"+idn).html(infor);
		$("#after_miss_" + idn).show();
		$("#after_miss_" + idn).fadeOut(3000);
	}

	//提交回复信息
	function revised() {
		lock();
		$.post(
		  "reviseExam!reply.jhtml",{
			  reviseReply : $("#reviseReply").val(),
			  replyType : $("#replyType").val(),
			  reviseId : $("#reviseId").val()
		  },function(data, textStatus) {
			releaseLock();
			var idn = $("#reviseId").val();
			if (!data){
				missFun("程序未响应...", idn);
				return ;
			}else if(data.indexOf("yes")>-1) {
				$("#after_loading_" + idn).show();
				$("#after_loading_" + idn).fadeOut(3000);
				var str = data.split(";");
				if(str.length==3){
//					$("#showInfo_" + idn).css("background", "#E3E3E3");
					$("#showInfo_" + idn).html("　最后回复人："+str[2]+"　回复时间："+str[1]);
					$("#showInfo_" + idn).show();
					$("#table_" + idn).css("background", "");
					//document.getElementById("table_" + idn).style.background = "black";
					//alert(document.getElementById("table_" + idn).style.bgcolor);
					//alert($("#table_" + idn).css("bgcolor"));
				}
			} else if(data=="no_0") {
				missFun("回复信息保存失败：没有得到要回复的纠错信息的ID！", idn);
			} else if(data=="no_1") {
				missFun("回复信息保存失败：没有得到对应的审校纠错信息！", idn);
			} else if(data=="no_2") {
				missFun("回复信息保存失败：保存回复过程中发生错误！", idn);
			}
		  }
		);
	}
	function editItem() {
		if(checkLock())
			return;
		window.open('../../admin/itembank/item!edit.jhtml?id=${ipidsVO.id}', '_blank');
		//window.location.href="";
	}

	//跳转到上一题
	//AUTHOR: L.赵亚
	function preAttention(){
		if(checkLock())
			return;
		window.location.href="examWidget!itemReviseReplyWidget.jhtml?itemId=${ipidsVO.prev}&fromPage=${fromPage}";
	}
	//跳转到下一题
	//AUTHOR: L.赵亚
	function nextAttention(){
		if(checkLock())
			return;
		window.location.href="examWidget!itemReviseReplyWidget.jhtml?itemId=${ipidsVO.next}&fromPage=${fromPage}";
	}
	//跳转回列表显示页面
	//AUTHOR: L.赵亚
	function backToList(){
		if(checkLock())
			return;
		window.location.href="examWidget!toReviseReplyList.jhtml?fromPage=${fromPage}";
	}
	function refreshItem() {
		if(checkLock())
			return;
		window.location.href = "examWidget!itemReviseReplyWidget.jhtml?itemId=${ipidsVO.id}&fromPage=${fromPage}";
	}
	$(function(){
		$("textarea").focus(function(){
			if($(this).val()=="选择纠错类型，在此给审校人留言，可以不输"){
				$(this).select();
			}
		}).blur(function(){
			if(!$(this).val())
				$(this).val("选择纠错类型，在此给审校人留言，可以不输");
		});
		$(":button[attclick=reply]").click(function(){
			if(checkLock())
				return;
			var a = $(this).attr("name");
			var b = true;
			for(var i=1; i<6; i++){
				if(document.getElementById("replyType_"+a+"_"+i).checked){
					b = false;
					$("#replyType").val(document.getElementById("replyType_"+a+"_"+i).value);
					break;
				}
			}
			if(b){
				alert("请选择一种纠错类型，然后再行提交...");
				return false;
			}
			var rReply = document.getElementById("reviseReply_"+a).value;
			if(rReply=="选择纠错类型，在此给审校人留言，可以不输")
				rReply = "";
			$("#reviseReply").val(rReply);
			$("#reviseId").val($(this).attr("attId"));
			revised();
		});
	});
</script>
</head>
<body>
<div id="contentLayout" class="wm650">
	<div class="content_left wm650">
		<div class="content_bg">
			<div class="yr_bg2">
			<div class="ye_r_t">
			<div class="ye_l_t">
			<div class="ye_r_b">
			<div class="ye_l_b">
			<div class="content">
				<!--试题层开始-->
				<div id="itemdiv">
					<jsp:include page="${widgetPage}.jsp"></jsp:include>
				</div>
				<div>
		        	<form action="doExam.jhtml" method="post" name="examForm" id="examForm">	    	
						<input type="hidden" value="" name="userAnswers" id="userAnswers" />
						<input type="hidden" value="" name="reviseReply" id="reviseReply" />
						<input type="hidden" value="" name="replyType" id="replyType" />
						<input type="hidden" value="" name="reviseId" id="reviseId" />
						<input type="hidden" value="no" name="hasLocked" id="hasLocked" />
					</form>
				</div>
				<!--试题层结束-->
  
				<div>
	  <table bgcolor="#FFFFFF" width="100%"><tr><td>
	  
	  <c:forEach items="${irvo.allRevises}" var="item" varStatus="itemStatus">
	  <br />
	  <table bgcolor="${ empty item.reviseReplyTime ? "black":"" }" 
	  	width="100%" cellspacing="1px" id="table_${ item.id }"><tr><td bgcolor="#FFFFFF">
	  
	  <table width="200px" cellspacing="1px" bgcolor="#E3E3E3">
	  	<tr><td bgcolor="#FFFFFF" height="30px" nowrap>　<font color="blue">
	  		用户：${ item.reviseName }
	  		&nbsp;
	  		审校情况：<font color="${ "未通过" eq item.reviseStatusName ? "red" : ""}" >${ item.reviseStatusName }</font>
	  		</font>
			<a href="./examWidget!itemReviseSimpleWidget.jhtml?itemId=${ipidsVO.id}&sysUserId=${item.revisor}"
				target="_blank"><font color="green">查看作答情况</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
	  	</td></tr>
	  </table>
	  <table width="100%" cellspacing="1px" bgcolor="#E3E3E3">
	  	<tr><td bgcolor="#FFFFFF"  height="30px">　<b>${ item.reviseRecord }</b>
	  		(<fmt:formatDate value="${item.reviseRecordTime}" type="Date" pattern="yyyy-MM-dd HH:mm"/>)
	  	</td></tr>
	  </table>
	  <table width="100%" >
	  	<tr><td height="30px">
	  		　<input type="radio" name="replyType_${ item.id }" id="replyType_${ item.id }_1"
	  			 value="1" ${ item.replyType==1 ? "checked" : "" }/> 纠正了一个严重错误
	  		<input type="radio" name="replyType_${ item.id }" id="replyType_${ item.id }_2"
	  			 value="2" ${ item.replyType==2 ? "checked" : "" } /> 纠正了一个一般错误
	  		<input type="radio" name="replyType_${ item.id }" id="replyType_${ item.id }_3"
	  			 value="3" ${ item.replyType==3 ? "checked" : "" } /> 纠正了一个细微错误
	  		<input type="radio" name="replyType_${ item.id }" id="replyType_${ item.id }_4"
	  			 value="4" ${ item.replyType==4 ? "checked" : "" } /> 纠错无效，试题无误
	  		<input type="radio" name="replyType_${ item.id }" id="replyType_${ item.id }_5"
	  			 value="5" ${ item.replyType==5 ? "checked" : "" } /> 恶意纠错
	  	</td></tr>
	  	<tr><td>
	  		　<textarea name="reviseReply_${ item.id }" id="reviseReply_${ item.id }" rows="3" 
	  			cols="80">${ empty item.reviseReply ? "选择纠错类型，在此给审校人留言，可以不输":item.reviseReply }</textarea><br/>
		<div id="loading_${ item.id }" 
			style="position:absolute;left:300px;display:none;">
			<img src="../images/loading.gif" />
				<font color="blue">正在提交信息！请稍候...</font></div>
	  	<div id="after_loading_${ item.id }" 
			style="position:absolute;left:330px;display:none;">
			<img src="../images/ok.gif" />
				<font color="blue">回复成功！</font></div>
	  	<div id="after_miss_${ item.id }" 
			style="position:absolute;left:330px;display:none;">
			<img src="../images/miss.gif" />
				<font color="blue" id="after_miss_con_${ item.id }"></font></div>
	  	</td></tr>
	  </table>
	  <div id="showInfo_${ item.id }" style="display:${ empty item.reviseReplyTime ? "none":"''" }" >
	  		　最后回复人：${ item.reviseReplyerName }　回复时间：<fmt:formatDate value="${item.reviseReplyTime}" type="Date" pattern="yyyy-MM-dd HH:mm"/></div>
	  	　<input type="button" value="　回 复　" name="${ item.id }" 
	  			attId="${ item.id }" attclick="reply"/>
	  	</td></tr></table>
	  	
	  </c:forEach>
	   <br>
	  </td></tr></table>
				</div>
		        <div class="bnt_box1">
	            	<span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	            		onclick="reviseAnswer()">校验答案</button></span>
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                  ${ sessionScope.teacherLogin eq "yes"?"disabled":"" } onclick="editItem()">编辑试题</button></span>    
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                	 onclick="refreshItem()">刷新试题</button></span>    
	            	<span class="pager" id="preAttention"><button class="pager1" onmouseout="this.className='pager1';"  onmouseover="this.className='pager2';"
	            		onclick="preAttention()" ${ empty ipidsVO.prev ? "disabled":"" } >上一题</button></span>
	                <span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
		                onclick="backToList()">返回试题列表</button></span>
		            <span class="pager" id="nextAttention"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
	                	onclick="nextAttention()" ${empty ipidsVO.next? "disabled":"" } >下一题</button></span>    
	                
		        </div>
			</div>
			</div>
			</div>
			</div>
			</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
</body>
</html>