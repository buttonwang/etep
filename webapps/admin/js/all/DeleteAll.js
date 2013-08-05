function DeleteAll(son,top){
	var top = top ||"top";
	var son = son ||"";
	var sSon  ;
	if(son==""){
		sSon = "[t!="+top+"]";
	}else{
		sSon = "[t="+son+"]";
	}
	var top_sel="input[type=checkbox][t="+top+"]";
	var all_sel="input[type=checkbox]"+sSon;
	var g_checkBoxSize_key = "checkboxSize";
	GMAP(g_checkBoxSize_key,j(all_sel).size());
	j(top_sel).click(function(){
		j(all_sel).attr("checked",j(this).attr("checked"))
	})
	
	j(all_sel).click(function(){
		var t=0;
		j(all_sel).each(function(){
			if(j(this).attr("checked")==true){
				 t++;
			}
		})
		j(top_sel).attr("checked",t==GMAP(g_checkBoxSize_key));
	})
}