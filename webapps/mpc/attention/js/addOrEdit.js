$(function(){
	$("#sub_i").click(function(){
		$(this).attr("disabled","disabled");
		$("#addOrEditForm").submit();
	})
	$('#realTextarea').change(function(){
		$("#sub_i").attr("disabled","");
	});
	$("#addOrEditForm").submit(function(){
		 if($.trim($("#realTextarea").html()).length==0||$.trim($("#realTextarea").html())=='好记性不如烂笔头，快记下对此题的体会吧！'){
			if($("#shareCheckBox").attr("checked")==true){
				$("#shareCheckBox").click();
				$('#realTextarea').text("");
				$('#realTextarea').focus();
				return false;
			}
			$('#realTextarea').text("");
		}
		//$('#realTextarea').text($('#realContent').get(0).innerHTML);
		 var _info = $(this).attr("_info")||"";
			var opt  = {
				dataType :"html"
				,url: $(this).attr("action")
				,success: function(html){ 
					if(html.indexOf("isSuccess")>0){
						alert(_info+" 保存成功");
						try{
							window.parent.tb_remove();
							var itemId =$("#itemId_i").val();
							$(window.parent.document).find("img[t=hasAttentions][itemId="+itemId+"]").show();
						}catch(e){}
					}else{
						alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));
					}
				} 
				,error:function(e){
					alert([_info," 保存失败！！！\n ","1.请检查输入项是否符合要求","2.请检查是否超时"].join("\n"));					
				}
			};
			$(this).ajaxSubmit(opt);
			return false;
	})
	
	$("*[t=iaLogicDel]").click(function(){
		if (!confirm("          确定要不再关注此题吗？\n（不再关注后，可在“逐题回顾”时再次关注它）")) return false;
		
		$(this).attr("disabled","disabled");
		var itemId = $(this).attr("itemId");
		var url = "../attention/attention!disAttention.jhtml?p.para.itemId="+itemId;
		$.ajax({
		  type: "POST",
		  url: url,
		  data: "name=John&location=Boston",
		  success: function(msg){
			if(msg.indexOf("isSuccess")>0){
				alert("操作成功");
				try{
					window.parent.tb_remove();				
					$(window.parent.document).find("img[t=hasAttentions][itemId="+itemId+"]").hide();
				}catch(e){}
			}
		  }
		});
	})
	$("#realTextarea").focus(function(){
		if($.trim($(this).html())=='好记性不如烂笔头，快记下对此题的体会吧！'){
			$(this).val("");
		}
	}).blur(function(){
		if($.trim($(this).html())==''){
			$(this).val("好记性不如烂笔头，快记下对此题的体会吧！");	
		}
	})
	$(".itag").click(function(){
		var tagArr = ($("#i_tags").val()||"").split(" ");
		var willAdd = $(this).html();
		var notInTags = true;
		for(i in tagArr){
			if(willAdd == tagArr[i]){
				tagArr[i]=null;
				notInTags=false;
				break;
			}
		}
		if(notInTags){
			tagArr[tagArr.length]=willAdd;
		}
		var narr = [];
		for(var j in tagArr){
			if(tagArr[j]!=null&&$.trim(tagArr[j])!="" )
				narr[narr.length] = tagArr[j]
		}
		$("#i_tags").val(narr.join(" "));
	})
	$("#shareCheckBox").click(function(){
		if($.trim($("#realTextarea").html()).length==0||$.trim($("#realTextarea").html())=='好记性不如烂笔头，快记下对此题的体会吧！'){
			alert("请先写笔记再共享");
			$(this).attr("checked",false);
		}
	})
})