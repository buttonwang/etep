function setForm (formName,prefix,varStr){
	var getKey = function(jobj){
		return (jobj.attr("name")||"").replace(prefix+".","");
	}
	eval ('var obj='+varStr);
	j("form[name="+formName+"]").each(function(){
		var jinputs = j(this).find("*[name!=]");
		jinputs.each(function(){
			var jit = j(this);
			var varKey = (jit.attr("name")||"").replace(prefix+".","");
			var v = obj[varKey];
			if(v!=undefined){
				if(jit.is("input")){
					var type = jit.attr("type")||"";
					if(type=="radio" ||type=="checkbox"){
					   /*这里是特殊应用,将来可能要修改*/
						try{
							var __arry = v.split(",");
							for(var i=0;i<__arry.length;i++){
								if(jit.attr("value")==__arry[i]){
									jit.attr("checked",true);
								}
							}
						}catch(e){
						}
					}else{
						jit.val(v);
					}
				}else if(j(this).is("select")){
					jit.find("option").each(function(){
						if(j(this).attr("value")==v){/*||j(this).html()==v*/
							j(this).attr("selected",true);
						}
					})
				}else if(j(this).is("text")){
					jit.text(v);
				} 
			}
		});		
	})
	initInput();
}
function initInput(){
	j("input[vvv=vvv]").each(function(){
		var name = j(this).attr("name");
		var v = j(this).val();
		j("input[n="+name+"]").each(function(){
			var type = j(this).attr("type")||"";
			if(type=="radio" ){				
				if(j(this).val()==v){
					j(this).attr("checked",true);
				}
			}else if(type=="checkbox"){
				try{
				 	j(this).attr("checked", Number(v) >0);
				}catch(e){					
				}			 
			}
			eval("var data="+j(this).attr("vs"));
			var vArr = data.v.split(",");
			var iname = j(this).attr("n");
			var itv =  j("input[name="+iname+"]").val();
			j(this).click(function(){
				if(j(this).attr("checked")==true){
					j("input[name="+iname+"]").val(vArr[1]);
				}else{
					j("input[name="+iname+"]").val(vArr[0]);
				}
			})
		})
	})
		
	j("span[n!=]").each(function(){
		var jdiv = j(this);
		var iname = j(this).attr("n");
		var jselects = jdiv.find("input");
		var size = jselects.size();
		var varry = (j("input[name="+iname+"]").val()||"").split("-");
		jselects.each(function(vi){
			var v = varry[vi]||"";
			//j(this).find("option").each(function(){
				//if(j(this).html()==v){
				//	j(this).attr("selected",true);
				//}
			//})
			j(this).val(v);
		})
		jselects.blur(function(){
			var html ="";
			if(size<=1){
				html =jselects.val();
			}else{
				jselects.each(function(i){
					html += j(this).val();					
					if(i>=0&&i<size-1){
						html += "-";
					}
				})
				j("input[name="+iname+"]").val(html)
			}
		})
	})
	
	j("div[n!=]").each(function(){
		var jdiv = j(this);
		var iname = j(this).attr("n");
		var jselects = jdiv.find("select");
		var size = jselects.size();
		var varry = (j("input[name="+iname+"]").val()||"").split(",");
		jselects.each(function(vi){
			var v = varry[vi]||"无";
			j(this).find("option").each(function(){
				if(j(this).html()==v){
					j(this).attr("selected",true);
				}
			})
		})
		jselects.change(function(){
			var html ="";
			if(size<=1){
				html =jselects.val();
			}else{
				jselects.each(function(i){
					html += j(this).val();					
					if(i>=0&&i<size-1){
						html += ",";
					}
				})
				j("input[name="+iname+"]").val(html)
			}
		})
	})
}
