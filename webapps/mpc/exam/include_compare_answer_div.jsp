<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script language="javascript">
<!--

	//加为答案
	function addAnswerAndJump(jumpOrNot) {
		$("#jumpOrNot_L").val(jumpOrNot);
		$("#addAnswer_form_L").submit();
	}

//-->
</script>
<div class="floatBoxs" id="sign06" style='z-index:23; display:none; width: 430px;'> 
	<h3>比较答案</h3> 
	<div class="floatBox"> 
		<h1>请仔细比较用户输入的答案与标准答案的区别。</h1>
		<h2>公式型答案以MathML格式显示，以方便比较。</h2>
		<div>
			用户答案：<textArea id="userAnswer_compare" style="width: 380px" cols="6" rows="2"></textArea>
			<div class="clear"></div>
			标准答案：<textArea id="viewAnswer_compare" style="width: 380px" cols="6" rows="2"></textArea>
		</div>
		<div class="btn">		
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" 
				onmouseover="this.className='bb2';"  id="sign06_button_1"  
				onClick="addAnswerAndJump(false)">加为答案</button></span>
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" 
				onmouseover="this.className='bb2';"  id="sign06_button_1"  
				onClick="addAnswerAndJump(true);">加为答案并转入编辑</button></span>	
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" 
				onmouseover="this.className='bb2';"  id="sign06_button_1"  
				onClick="javascript:doit(sign06);">关闭</button></span>	
     	 	<div class="clear"></div> 
		</div>
		<form id="addAnswer_form_L" action="../../admin/itembank/item!addAnswer.jhtml" method="post"  >
			<input name="jumpOrNot_L" id="jumpOrNot_L" style="display:none" value=""/>
			<input name="p.para.itemId" id="itemId_add_L" style="display:none" value=""/>
			<input name="p.para.subItemId" id="subItemId_add_L" style="display:none" value=""/>
			<input name="p.para.addAnswerIndex" id="ddAnswerIndex_add_L" style="display:none" value=""/>
			<textarea name="p.para.addAnswerContent" id="addAnswerContent_add_L" style="display:none"></textarea>
		</form>
	</div>
</div>

<script language="JavaScript" type="text/javascript">
	$("#sign06_button_1").blur( function () { $("#sign06_button_1").focus() } ); 
	$("#addAnswer_form_L").submit(function( ){
		var _info = $(this).attr("_info")||"";
		var opt  = {
			dataType :"html"
			,url: $(this).attr("action")
			,success: function(html){ 
				if(html.indexOf("isSuccess")>0){
					alert(_info+" 操作成功 ");
					if($("#jumpOrNot_L").val()=="true")
						window.location.href = "../../admin/itembank/item!edit.jhtml?id=" + $("#itemId_add_L").val();
					else window.location.reload(false);
				}else{
					alert([_info," 操作失败！！！,请稍后再试"].join("\n")); 
				}
			} 
			,error:function(e){
				alert([_info," 操作失败！！！,请稍后再试"].join("\n"));
			}
		};
		$(this).ajaxSubmit(opt);
		return false; 
	})
</script>