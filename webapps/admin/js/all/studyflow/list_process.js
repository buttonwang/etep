
j(function(){
	/*
		用于list_process.jsp 批量删除流程 
		不能工作时检查是否 导入了 m.js DeleteAll.js 两个文件
	*/
	var countItem =function (jobjs,attrName,compareValue){
			var t=0;
			jobjs.each(function(){
				if(j(this).attr(attrName)==compareValue){
					 t++;
				}
			})
			return t;
	} 
	var  makeStr=function(jobjs,attrName,selAttrName,selAttrEqual){	
		var size = jobjs.size()||0;
		var items="";
		var checkTotal = countItem (jobjs,"checked",true);
		if(size>0){
			var i =0;
			jobjs.each(function(n){
				if(j(this).attr(selAttrName)==selAttrEqual){
					items += j(this).attr(attrName);
					if(++i<checkTotal){
						items += ","
					}
				}
				
			})
		}
		return items;
		
	};
	DeleteAll("son");
	j("input[value=批量发布]").click(function(){
		var jobjs = j("input[type=checkbox][t=son]");
		if(countItem (jobjs,"checked",true)==0){
			alert("您需要至少勾选一个项目");
			return;
		}else{
			if(!confirm("确定要批量发布吗？")){
				stopBD(e,1,2);
			}else{
				var ids =makeStr(jobjs,"value","checked",true);
				window.location.href = "../studyflow/process.jhtml?atype=releases&ids="+ ids;
			}
		}
	})
	j("input[value=批量作废]").click(function(){
		var jobjs = j("input[type=checkbox][t=son]");
		if(countItem (jobjs,"checked",true)==0){
			alert("您需要至少勾选一个项目");
			return;
		}else{
			if(!confirm("确定批量作废？")){
				stopBD(e,1,2);
			}else{
				var ids =makeStr(jobjs,"value","checked",true);
				window.location.href = "../studyflow/process.jhtml?atype=abandons&ids="+ ids;
			}
		}
	})
	/*删除确认*/
	ConfirmIF()
	/*作废确认*/
	ConfirmIF({emsg:"您确定要作废该流程？",cmpVal:"作废"});
	
	j("#changeItemName").change(function(){
		j("#"+j(this).attr("v")).attr("name",j(this).val());
	})
})
