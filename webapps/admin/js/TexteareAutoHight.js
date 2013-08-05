function TexteareAutoHight(jobj){
		jobj.trigger("click");
		var text = jobj.val();
		var rowsArray =  jobj.val().split("\n");
		var perFontSize = Number(jobj.css("font-size").replace("px",""));
		var width=jobj.width();
		var appendRows = 0;
		//如果自动换行
		if(true){
			for(var i=0;i<rowsArray.length;i++){
			 appendRows += (perFontSize*rowsArray[i].length)/width;
			}
		}
		jobj.attr("rows",Math.floor (rowsArray.length+appendRows));
}