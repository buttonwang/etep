function reInitIframeHeight(iframeIds){
	var idArr = (iframeIds||"").split(",");
	for(var i in idArr){
		var iframeId = idArr[i];
		if($.trim(iframeId)!=""){
			setInterval('var jo = $("#"+"'+iframeId+'");jo.height(jo.show().contents().find("body").height()); ',1500);
		}
	}
}; 
$(function(){
	$("iframe[t=reInitIframeHeight]").each(function(){
		var iframeStartId =1;
		var iframeId = $(this).attr("id")||"";
		if(iframeId==""){
			for(var i=iframeStartId;i<iframeStartId+1000;i++){
				if($("#_iframeid_"+i).size()==0){ 
					iframeId = "_iframeid_"+i;
					$(this).attr("id",iframeId);
					break;
				}
			}
		}
		reInitIframeHeight(iframeId);
	})
})