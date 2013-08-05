function ShowHide(sel){
	j(sel).click(function(){
		var vStr = j(this).attr("v");
		eval("var v = "+vStr);
		var showS = (v.s||"").split(",");
		var hideS = (v.h||"").split(",");
		var toggleS =(v.t||"").split(",");
		var slideUps =(v.su||"").split(",");
		for(var i=0;i<showS.length;i++){
			j("#"+ showS[i]).show();
		}
		for(var i=0;i<hideS.length;i++){
			j("#"+ hideS[i]).hide();
		}
		for(var i=0;i<toggleS.length;i++){
			j("#"+ toggleS[i]).toggle();
		}
		for(var i=0;i<slideUps.length;i++){
			j("#"+ slideUps[i]).slideUp();
		}
	})
}