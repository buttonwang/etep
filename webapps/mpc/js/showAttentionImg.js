function showItemAttention(itemId){
	if(itemId!=undefined){
		$("img[t=hasAttentions][itemId="+itemId+"]").show(); 
	}else{
		if(typeof(______hasAttentions)=="undefined"){
			______hasAttentions= 1;
			$("img[t=hasAttentions]").each(function(){
				 var jimg = $(this);
				 var itemId = jimg.attr("itemId");
				
				 try{
					$.ajax({
							type: "POST"
							,url: "../attention/attention!itemAttentionCount.jhtml?p.para.itemId="+itemId
							,dataType: "html"
							,success: function(html){
								try{ 
									var num = Number(html);
									if(num>0){ jimg.show(); } 
								}catch(e){
								}
							}
					}); 
				 }catch(e){alert(e.toString());}
			})
		}
	}
}
$(function(){
	showItemAttention();
})