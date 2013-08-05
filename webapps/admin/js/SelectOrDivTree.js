
function SelectOrDivTree_getRoot(data){
	var noParentData = []
	for(var i in data){
		if(data[i].pid== "-1" ){
			noParentData[noParentData.length]=data[i];
		}
	}
	return noParentData;
}

function SelectOrDivTree_findSon(obj,data){
	for(var i=0;i<data.length;i++ ){
		if(data[i]!=null&&data[i].pid==obj.id){
			obj.sons = obj.sons ||[];
			obj.sons[obj.sons.length]=data[i];
			data[i]=null;
			SelectOrDivTree_findSon(obj.sons[obj.sons.length-1],data)			
		}
	}
	return obj;
}

function SelectOrDivTree_LparamObj(){
	var oStr = arguments[0];
	var obj = arguments[1];
	var isDebug =  arguments[2];
	var array = oStr.match(/\{.*?\}/g);
	var re = /\{|\}/g;
	var isDebug =isDebug||false;
	if(array!=null){
		for(var i=0;i<array.length;i++){
			var key = array[i].replace(re,"");
			var value ;
			var reFind;
			try{
				if(obj instanceof  Array){
					value = obj[i];
				}else{
					value = eval("obj."+key);
				}
				reFind=new RegExp(array[i],"g");
			}catch(e){
				continue;
			}
			if(reFind){
				if(isDebug){
					if(value!=undefined){
						oStr = oStr.replace(reFind,value);
					}
				}else{
					oStr = oStr.replace(reFind,value||"");
				}
			}
		}
	}
	return oStr;
}

function SelectOrDivTree_printOption(obj,str,selectedId,outStr,prefix){
	var str  = str||"" ;
	var sons =obj.sons||[];
	var thisSonsShowStr ="";
	obj._selected =  (selectedId||"")==obj.id?"selected":"";
	obj._prefix=str||"";
	outStr[outStr.length]=[SelectOrDivTree_LparamObj("<option value={obj.id} {_selected}>{_prefix}{obj.name}</option>",obj)].join("") ;  
	str +=prefix; 
	for(var p in sons){
	 	SelectOrDivTree_printOption(sons[p],str,selectedId,outStr,prefix );
	}
}
function SelectOrDivTree(data,selectedId,outArr,prefix,getStr_f){
	var prefix = prefix||"--";
	var selectedId = selectedId||"";
	var out = outArr||[];
	var noParentData = SelectOrDivTree_getRoot(data);
	for(var i in noParentData){
		var np = noParentData[i]
		var obj =SelectOrDivTree_findSon(np,data)||{} ;
		SelectOrDivTree_printOption(obj,"",selectedId,out,prefix );
	}
	return out.join("");
}