function show(cid){	
	try{
		var display=  getE(cid).style.display;
		if(display== "none"){
			 getE(cid).style.display="";
		}else{
			getE(cid).style.display="none";
		}
	}catch(e){}  
	 
}
function hide(cid){
	try{getE(cid).style.display= "none";}catch(e){} 
}
function getE(cid){
	var elem;
	if(cid!=undefined&&cid!=null){
		elem =  document.getElementById(cid);
	}
	if(elem==undefined){
		elem = null;
	}
	return elem;
}
function swap(id,type){
	if(type=="show"){
		$("#s_span_"+id).hide();
		$("#h_span_"+id).show();
	}else{
		$("#s_span_"+id).show();
		$("#h_span_"+id).hide();
	}
}
