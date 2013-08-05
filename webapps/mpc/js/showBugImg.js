function showBugItem(itemId){
	if(itemId!=undefined){
		$("img[t=hasBugs][itemId="+itemId+"]").show(); 
	}else{
		if(typeof(______hasBugs)=="undefined"){
			______hasBugs= 1;
			$("img[t=hasBugs]").each(function(){
				 var jimg = $(this);
				 var itemId = jimg.attr("itemId");
				 try{
					$.ajax({
							type: "POST"
							,url: "../bug/bug!getBugCount.jhtml?p.para.itemId="+itemId
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
	showBugItem();
})