try{
	$(function(){
		$("form").each(function(){ 
			var jcpTotalBase = $(this).find("input");
			var jcpTotal     = $(this).find("input[_t=cpTotal]");
			jcpTotalBase.each(function(){
				var cpTotalBase = $(this).attr("_t")||"";
				if(cpTotalBase=="cpTotal"){
					jcpTotal=$(this);
				}
			})
			jcpTotalBase.each(function(){
				var cpTotalBase = $(this).attr("_t")||"";
				if(cpTotalBase=="cpTotalBase"){
					$(this).keyup(function(){
						try{
							var items =$(this).val().split("；");
							var t = 0;
							for(var i in items){
								var number = 0;
							 	try{
									number=	 Number($.trim(items[i]));
								}catch(e){

								}

								if(!isNaN(number)){ 
									t+=number
								}
							}	
							jcpTotal.val(t);
						}catch(e){
							//alert("e")
						}
					})
				}
			})
			
		})
	})
}catch(e){
} 