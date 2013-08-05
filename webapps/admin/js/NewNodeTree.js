function Tree_getRoot(data){
	var noParentData = []
	for(var i in data){
		if(data[i].pid== "-1" ){
			noParentData[noParentData.length]=data[i];
		}
	}
	return noParentData;
}

function Tree_findSon(obj,data){
	for(var i=0;i<data.length;i++ ){
		if(data[i]!=null&&data[i].pid==obj.id){
			obj.sons = obj.sons ||[];
			obj.sons[obj.sons.length]=data[i];
			data[i]=null;
			Tree_findSon(obj.sons[obj.sons.length-1],data)			
		}
	}
	return obj;
}

function Tree_LparamObj(){
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

function Tree_printOption(obj,str,selectedId,outStr,prefix){
	var str  = str||"" ;
	var sons =obj.sons||[];
	var thisSonsShowStr ="";
	obj._selected =  (selectedId||"")==obj.id?"selected":"";
	obj._prefix=str||"";
	outStr[outStr.length]=Tree_LparamObj("<option value={obj.id} {_selected}>{_prefix}{obj.name}</option><br>",obj) ;  
	str +=prefix; 
	for(var p in sons){
	 	Tree_printOption(sons[p],str,selectedId,outStr,prefix );
	}
}

function Tree(data,selectedId,outArr,prefix,getStr_f){
	var prefix = prefix||"--";
	var selectedId = selectedId||"";
	var out = outArr||[];
	var noParentData = Tree_getRoot(data);
	for(var i in noParentData){
		var np = noParentData[i]
		var obj =Tree_findSon(np,data)||{} ;
		Tree_printOption(obj,"",selectedId,out,prefix );
	}
	return out.join("");
}

function selectTree(map){
	var map=map||{};
	var sonKey = map.sonKey||"childrenCategories";
	var dataLst = map.dataLst||[];
	var prefix = map.prefix||"----";
	var outId=map.outId||"";
	var outSelect=map.outSelect;
	var selectName = map.selectName||"processDefinition.categoryId";
	var selectId = map.selectId||"categoryId";
	var selected = map.selected||"0";
	var evalOut = map.evalOut||'"<option value="+data.id+ " "+((selectedId == data.id)?"selected":"") +">"+str+data.name+"</option>"+t';
	var defaultSelected = "<option value=0>&#x65E0;</option>";
	if(evalOut==""){
		if(selected=="0"||selected==""){
			defaultSelected= "<option value=0 selected>&#x65E0;</option>";
		}
	}	
	var evalIn =map.evalIn||  '"<select name="+selectName+" id="+selectId+" >"+defaultSelected'; 
	var optionStr = "";
	optionStr =  eval(evalIn );
	optionStr += Tree( dataLst,selected,[],prefix);	 
	 
	this.__gen_select_html = optionStr+ "</select>" ;
	if(outSelect!=undefined){
		j(outSelect).append(this.__gen_select_html)
	}else{
		j("#"+outId).append(this.__gen_select_html);
	}
}
function isInArray(id,arr){
	for(var i in arr){
		 if(arr[i]!=null&&arr[i].id==id ){
		 	arr[i]=null;
		 	return true;
		 }
	}
	return false;
}

function Tree_nodeTree(obj,str,checkedArr,outStr,prefix,ulHtml){
	var str  = str||"" ;
	var sons =obj.sons||[];
	var thisSonsShowStr ="";
	var level = prefix+1;
	obj._mleft_width = prefix*30;	
	obj._checkedAttr =  isInArray(obj.id,checkedArr)==true?" checked=checked ":"";
	 
	if(obj.type=="节点组"){
		obj._show = Tree_LparamObj('<a href="node.jhtml?atype=showAll&id={obj.id}&prid={obj.prid}">{obj.name}</a>',obj);
	}else{
		obj._show = Tree_LparamObj('<a href="node.jhtml?atype=showGroupAll&id={obj.id}&prid={obj.prid}">{obj.name}</a>',obj);
	}
	
	obj._edit= Tree_LparamObj('<a href="node.jhtml?atype=edit&to=showProcess&id={obj.id}&processId={obj.prid}&prid={obj.prid}">修改</a>',obj);
	obj._add = Tree_LparamObj('<a href="node.jhtml?atype=sadd&p.para.pid={obj.id}&p.para.prid={obj.prid}&p.para.nid={obj.id}&prid={obj.prid}">增加</a>',obj);
	obj._del = Tree_LparamObj('<a href="node.jhtml?atype=delete&id={obj.id}&p.para.prid={obj.prid}">删除</a>',obj);
	var ulPHtml = ulHtml||'<span class="back"><span class="bc"><span class="p" style="width:{_mleft_width}px;text-align:right;"></span><span class="parent">-</span>{_show}</span><span class=orderNum>{obj.orderNum}</span><span class=nodeType>{obj.type}</span><span class=opt>{_edit}&nbsp;&nbsp;{_add}&nbsp;&nbsp;{_del}</span></span>';
	var ulSHtml = ulHtml||'<span class="back"><span class="bc"><span class="s" style="width:{_mleft_width}px;text-align:right;"></span><span class="son">&nbsp;</span>{_show}</span><span class=orderNum>{obj.orderNum}</span><span class=nodeType>{obj.type}</span><span class=opt>{_edit}&nbsp;&nbsp;{_add}&nbsp;&nbsp;{_del}</span></span>';
	if(sons.length>0){
		outStr[outStr.length]="<ul>";
		
		outStr[outStr.length] = Tree_LparamObj(ulPHtml,obj)
		for(var p in sons){
	 		Tree_nodeTree(sons[p],str,checkedArr,outStr,level );
		}
		outStr[outStr.length]="</ul>";
	}else{
		outStr[outStr.length] = Tree_LparamObj(['<ul>',ulSHtml,'</ul>'].join(""),obj)
	}
}
function NodeTree(data,checkedStr,outArr,prefix,getStr_f){
	var prefix = prefix||1;
	var checkedArr = (checkedStr||"").split(",");
	var out = outArr||[];
	var noParentData = Tree_getRoot(data);
	for(var i in noParentData){
		var np = noParentData[i]
		var obj =Tree_findSon(np,data)||{} ;
		Tree_nodeTree(obj,"",checkedArr,out,prefix );
	}
	return out.join("");
}

function NewNodeTree(outId,data){
	var jOut = $("#"+outId);
	jOut.append(NodeTree(data,"",[],1)) ;
	jOut.find("ul span[class=parent]").click(function(e){
		$(this).parent().parent().parent().find(">ul").toggle();
		$(this).html($(this).html()=="-"?"+":"-")
	})
	jOut.find("ul div[class=parent]").click(function(e){
		$(this).parent().parent().parent().find(">ul").each(function(){
			var jobj = $(this);
			var x =jobj.css("display")=="block"?"h":"s";
			$(this).attr("x",x).find("ul").attr("x",x);
			$(this).toggle();
		});
		$(this).html($(this).html()=="-"?"+":"-");
		setTimeout('jOut.find("ul").css("background-color","#FFF"),jOut.find("ul[x=s]:odd").find(">div.back").css("background-color","#E9F0F6")',300);
	})
	jOut.find("ul,li").css(
		{"padding":"0px",
		"list-style":"none",
		"margin":"0px"})
	jOut.find("span").css({
		"padding":"0px",
		"list-style":"none",
		"margin":"0px",
		"height":"30px",
		"text-align":"left",
		"vertical-align":"middle"
	})
 
	jOut.find("span").css({"float":"left"})
	jOut.find("span.back").css({
		"width":"100%"
	})
	jOut.find("span.bc").css({
		"width":"30%"
	})
	jOut.find("span.parent,span.son").css({
		"text-align":"right"
	})
	jOut.find("span.orderNum,span.nodeType").css({
		"width":"5%"
	})
	jOut.find("span.opt").css({
		"width":"20%"
	})
	jOut.find("span.nodeType").css({
		"width":"20%"
	})
	jOut.find("span.orderNum").css({
		"width":"20%"
	})
	jOut.css({
		"border-bottom-style":" groove"
	})
}