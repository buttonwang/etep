<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--审校记录信息-->
  <script type="text/javascript">

  </script>
  <div class="blank6"></div>
  <p>
	<h1>
		试题纠错：<textarea id="reviseRecord" cols="60" rows="4"></textarea>
		<span class="pager"><button class="pager1" onmouseout="this.className='pager1';"
			 onmouseover="this.className='pager2';" onclick="record()">保存</button></span>
	    <span id="recordResult"></span>        		
	</h1>
	<h1>
		纠错回复：<span id="reviseReplyType"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="reviseReply"></span>
    </h1>	
  </p>
<!--审校记录信息-->
  <script type="text/javascript">
  		if (${viewControl.itemRevise.id ne 0}) {
	  		$.getJSON(
	  		  "reviseExam!queryReviseRecord.jhtml?random="+Math.random(),
	  		  {id: ${viewControl.itemRevise.id}},
	  		  function(json){
	  	  	    //var obj = jQuery.parseJSON(json);
	  			$("#reviseRecord").text(json.reviseRecord);
	  			$("#reviseReplyType").html(json.reviseReplyType);
	  			$("#reviseReply").html(json.reviseReply);
	  			return true;
			 });
  		}
  		
		function record() {
  			if(checkLock()) return false;
			if ($.trim($("#reviseRecord").html()) == "") {
				$("#recordResult").html("不能发送空白文字");
				return false;
			}
			lock();
			$.post(
				"reviseExam!saveReviseRecord.jhtml",
				{id: ${viewControl.itemRevise.id},
				 reviseRecord: $("#reviseRecord").html()},
  		  		function(){
			  		releaseLock();
		  		  	//alert("保存成功！");	
  	  	  			$("#recordResult").html("保存成功!");
		 		}
			);
		}
  </script>
