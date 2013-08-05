$(function(){
	try{
		var arg =  window.dialogArguments||[];
		var selectedItemArr= (arg[1]).split(",");	
		
		var isInItem = function (v,varr){
			if(v){
				for(var i in varr){
					if(varr[i]==v){
						return v;
					}
				}
			}
		}
		$("input[name=checkitem]").each(function(){
			if(isInItem($(this).attr("c"),selectedItemArr)){
				$(this).attr("checked",true);
			};
		})
		
		$("#selectAll").click(function(){
			$(":checkbox").attr("checked",$(this).attr("checked"));
		})
		$("#save").click(function(){
			var codes = [];
			var names = [];
			$("input[name=checkitem]:checked").each(function(){
					codes[codes.length]=$(this).attr("c");
					names[names.length]=$(this).attr("n");
			})
			 
			window.returnValue =[codes.join(","),names.join(",")];
			window.close();
		})
	}catch(e){
		//alert(e.toStirng())
	
	}
})