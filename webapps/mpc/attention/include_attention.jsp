<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<form id="showAttentionForm" action="showAttention!save.jhtml" method="post" >
			<input name="attentionId" type="hidden"" value="${attentionId}" />
			<input name="itemId" type="hidden"" value="${itemId}" />
			<input name="hisId"  type="hidden"" value="${hisId}" />
			<input name="type"   type="hidden"" value="${type}" />
			
			<div class="blank9"></div>
      		<h6>
      			<c:if test="${attentionVO ne null && attentionVO.state eq 0}">
      				<span><b>关注时间：</b>
      				<fmt:formatDate pattern= "yy-MM-dd HH:mm" value="${attentionVO.insertDate}" /></span>
      			</c:if>	
       			<span><b>受关注人气：</b>${eall}</span>
       			<span>
       				<b>评价：</b>
       				<img src="../images/good.gif" width="16" height="16" align="absmiddle"/>${egood}&nbsp;&nbsp;
       				<img src="../images/Poor.gif" width="16" height="16" align="absmiddle"/>${ebad}&nbsp;&nbsp;
       				说不清（${edim}）
       			</span>
      		</h6>
	        <div class="guanzhu">
	        	<c:set var="displayStyle" value="none"></c:set>
	        	<c:if test="${attentionVO eq null}">
	        		<ul>
	        		<c:if test="${!canAttention}">
	        			<li>你还没有做过此题，暂时不能关注试题和查看笔记！</li>
	        		</c:if>
	        		
	        		<c:if test="${canAttention}">
	        			<li>你尚未关注此题！</li>
	        			<li class="onmouse1" onmouseout="this.className='onmouse1';" onmouseover="this.className='onmouse2';"
	        				><span><a href="javascript:void(null)" onclick="openShutManager(this, 'prompt00')">关注此题</a></span></li>
	        			<input name="attentionVO.attentionId" type="hidden"" value="0" />
						<input name="attentionVO.noteState" type="hidden"" value="0" />
	        		</c:if>
	        		</ul>
	        	</c:if>
	        	
	        	<c:if test="${attentionVO ne null}">
	        		<c:set var="displayStyle" value=""></c:set>
	        		<c:if test="${attentionVO.state eq -1}">
	        		<ul>
		        		<li>你曾经关注过此题！</li>
		        		<li class="onmouse1" onmouseout="this.className='onmouse1';" 
		        			onmouseover="this.className='onmouse2';"><span><a href="javascript:void(null)" 
		        			onclick="openShutManager(this, 'prompt00')">重新关注</a></span></li>
		        		<c:set var="displayStyle" value="none"></c:set>
	        		</ul>
	        		</c:if>
	        		<input name="attentionVO.attentionId" type="hidden"" value="${attentionVO.attentionId}" />
	        		<input name="attentionVO.noteState" type="hidden"" value="${attentionVO.noteState}" />	        		
	        	</c:if>
	        </div>
	        
	        <div class="blank9"></div>
	        
        	<div class="content_box920" id="prompt00" style="display:${displayStyle};">
          		<div class="clear"></div>
		        <div class="answers920">
		        <div class="floatBox_n_care">
		        <ul>
		          <li><b><img src="../images/evaluation_ico.gif" width="16" height="16" align="absmiddle" />评价此题:</b></li>
		          <li><input name="attentionVO.evaluation" type="radio" value="1"  ${attentionVO.evaluation eq  1?'checked="checked"':''} /><img src="../images/good.gif" align="[=p-t54tabsmiddle" />好</li>
		          <li><input name="attentionVO.evaluation" type="radio" value="-1" ${attentionVO.evaluation eq -1?'checked="checked"':''} /><img src="../images/Poor.gif" align="absmiddle" />差</li>
		          <li><input name="attentionVO.evaluation" type="radio" value="0"  ${attentionVO.evaluation eq  0?'checked="checked"':''} />说不清</li>
		          <li><span class="cDGray">(点选你认为合适的评价，可以不选。)</span></li>
          		</ul>
          		<div class="clear"></div>
          		<ul>          
		          <li><b><img src="../images/tag_ico.gif" width="16" height="16" align="absmiddle" />个性标签：</b></li>
		          <li>
		          	<input id="attentionTag" name="attentionVO.tag" type="text" value="${attentionVO.tag}" />
		          	<span class="cDGray">多个标签以空格或逗号隔开</span>
		          </li>
		         </ul>
          		<div class="clear"></div>
	          	<ul>
		          <li><b><img src="../images/tags_ico.gif" width="16" height="16" align="absmiddle" />此题常用标签：</b></li>
		          <li>
		          	<c:forEach items="${recentItemTag}" var="rtag" varStatus="rtagstatus"> 
		          		<span class="itag" style="cursor:pointer"><font color="blue">${rtag}</font></span>
		          	</c:forEach>
		          </li>
	            </ul>
          		<div class="clear"></div>
          		<ul>
		          <li>
		          	<img src="../images/note_ico.gif" width="16" height="14" />&nbsp;&nbsp;<b>学习笔记：</b>
		          	<c:if test="${attentionVO.noteState==1}">
		          		（<img src="../images/Digest_ico.gif" align="absmiddle" /><span class="cDGray">对精华笔记有贡献</span>）
		          	</c:if>		
		          </li>
		          <li>
		          	<div>
			          	<textarea name="attentionVO.noteContent" id="realTextarea" cols="70" rows="5" 
			          		style="width:750px;">${attentionVO.noteContent}</textarea>
		          	</div>
		          	<div>
			          	<h3>
			          		<input type="checkbox" name="attentionVO.noteShare" id="shareCheckBox"
			          			value="1" ${attentionVO.noteShare eq 1?'checked="checked"':'' }/>
			          		共享此笔记（可获得积分奖励，并有机会成为精华）
			         	</h3>
			         	<c:if test="${attentionVO.noteState==1}">
			         		<h3><b>管理员回复</b>：你笔记的部分内容被引用成为精华笔记。作为贡献人，你因此获得50积分奖励。</h3>
			         	</c:if>
			         	<c:if test="${attentionVO.noteState==-1}">
			         		<h3><b>管理员回复</b>：你的笔记用词不当，被管理员屏蔽。</h3>
			         	</c:if>
			         	<c:if test="${attentionVO.noteState==-2}">
			         		<h3><b>管理员回复</b>：你的笔记用词不当，所有笔记被管理员屏蔽。</h3>
			         	</c:if>
		        	</div>
		          </li>
		        </ul>
		        <div class="clear"></div>
		        <div class="blank6"></div>
		        <div class="bnt_box">
		        	<span class="bbs1"><button id="submitBtn" class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
		        		type="submit">提交</button></span>
		        	<c:if test="${attentionVO ne null && attentionVO.state eq 0}">
		        	<span class="bbs1"><button id="cancelBtn" class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
			        	onclick="cancelAttention()" >
		        		不再关注此题</button></span>
		        	</c:if>
		        </div>
          		</div>
        		<div class="clear"></div>
        		</div>
        	</div>
        	<div class="blank9"></div>
			<!--关注层结束-->
	</form>
	
	<script type="text/javascript">
		function cancelAttention() {
			if (confirm("          确定要不再关注此题吗？\n（不再关注后，可在“逐题回顾”时再次关注它）")) {
				$("#submitBtn").attr("disabled","disabled");
				$("#cancelBtn").attr("disabled","disabled");
				var form = document.getElementById("showAttentionForm");
		  		var actionurl = "showAttention!cancel.jhtml";
		  		form.action = actionurl;
		  		form.submit();
		  	}
		}

		function redoAttention() {
			if (confirm("确定要重新关注此题吗？")) {
				$("#submitBtn").attr("disabled","disabled");
				$("#cancelBtn").attr("disabled","disabled");
				var form = document.getElementById("showAttentionForm");
		  		var actionurl = "showAttention!redo.jhtml";
		  		form.action = actionurl;
		  		form.submit();
		  	}
		}

		notePromt = '好记性不如烂笔头，快记下对此题的体会吧！';
		
		$("#realTextarea").click(function(){
			if ($.trim($(this).html())==notePromt) {
				$(this).val("");	
			}
		}).blur(function(){
			if ($.trim($(this).html())==''){
				$(this).val(notePromt);
				$("#shareCheckBox").attr("checked",false);
			}
		}).focus(function(){
			if($.trim($(this).html())==notePromt){
				$(this).val("");
			}
		})
		
		if ($.trim($("#realTextarea").html())=='') $("#realTextarea").val(notePromt);
		
		$("#shareCheckBox").click(function(){
			if($.trim($("#realTextarea").html()).length==0||$.trim($("#realTextarea").html())==notePromt){
				alert("请先写笔记再共享!");
				$(this).attr("checked",false);
			}
		})
		
		$(".itag").click(function(){
			var ctag = $(this).text();
			var atag = $("#attentionTag").val();
			if (atag.indexOf(ctag) == -1) {
				var newTag = $.trim( $.trim(atag) + " " + ctag );
				$("#attentionTag").val(newTag);
			} else {
				var re = new RegExp("\s*" + ctag + "\s*");
				$("#attentionTag").val(atag.replace(re, ''));
			}
		})
		
		$("#showAttentionForm").submit(	function() {
			$("#submitBtn").attr("disabled","disabled");
			$("#cancelBtn").attr("disabled","disabled");
			if ($.trim($("#realTextarea").html())==notePromt)	$('#realTextarea').text("");
		})
		
	</script>