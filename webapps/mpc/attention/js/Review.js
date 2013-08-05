// JavaScript Document
$(function(){	 
	$("div[v=close]").each(function(){
		var jit = $(this);
		$(this).find("img").each(function(){
			var src = $(this).attr("src");
			if(src.indexOf("close.gif")>-1){
				$(this).click(function(){
					jit.hide();
				})
			}
		})
	})   
})