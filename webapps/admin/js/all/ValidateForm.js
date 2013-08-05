function ValidateForm(map){
	var m=map||{};
	var warnType = m.warnType||"alert";
	var formId = m.fid||"";
	var vs = m.vs||[];
	var compare = function(val,cval){
		return val==cval;
	}
	var getVD = function(v,def){
		return v!=undefined?v:def;
	};
	var getFocusId=function(jobj){
		var inputId = jobj.attr("id")||"";
		if(inputId ==""){
			jobj.attr("id",new G().nextId());
			inputId = jobj.attr("id");
		}
		return "#"+inputId;	
	}
	if(formId!=""){
		var jfid = "#"+formId;
		
		j(jfid).submit(function(e){
			var errorStr = "";
			var jfocusId = "";
			for(var i=0;i<vs.length;i++){
				var o = vs[i];
				var iName = o.n||"";
				var iType = o.t||"input";
				var ishowName = o.sn||"";
				var cval = o.cval||"";
				var eMsg = o.emsg;
				var equalAnyone = (o.equal||"true")=="false"?false:true;/*true 表示等于其中任何一个（输入不是指定值） false表示不等于其中任何一个值（过滤恶意）*/		 
				if(iName!=""){
					j(jfid +" "+iType+"[name="+iName+"]").each(function(){
						var val = j(this).val()||"";
						if(iType=="textarea"){
							val = j(this).text();
						}				
						if(cval instanceof Array  ){
							var isTrue = false;
							for(var i=0;i<cval.length;i++){
								isTrue = compare(val,cval[i]);
								if(isTrue){
									break;
								}
							}
							if(equalAnyone){
								/*任何一*/
								if(isTrue){}else{
									/*但一个也没有jfocusId = getFocusId(j(this));*/
									errorStr +=  getVD(eMsg,ishowName+" 的值不在指定的范围内")+"\n";
								}
							}else{
								/*全都不是*/
								if(isTrue){
									/*但是有一个是jfocusId = getFocusId(j(this));*/
									errorStr += getVD(eMsg,ishowName+" 值非法")+" \n";
								}else{}
							}
						}else if(compare(val,cval)){
							jfocusId = getFocusId(j(this));
							errorStr += getVD(eMsg,ishowName+ " 不能为"+(val==""?"空":val+"")+"\n");
							
						}
					})
				}
			}
			if(errorStr==""){
			 
			}else if("alert"==warnType){
				stopBD(e,1,2);
				alert(errorStr);
				if(jfocusId!=""){
					j(jfocusId).focus();
				}
			}			
		})
	}
};
 