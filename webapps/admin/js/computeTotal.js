function ComputeId2ToId1(id1,id2){
	$("#"+id2).change(function(){
		var total = 0;
		var valArr =  ($(this).val()||"").split("；");
		for(var i in valArr){
			try{
				number=	 Number($.trim(valArr[i]));
				if(!isNaN(number)){					
					total+=number
				}else{
					alert("不能正确转换为分值！！！");
				}
			}catch(e){
			}
		}
		
		$("#"+id1).val(total).trigger("change");
	})
}

function ComputeTotal(map){
	var totalId = map.totalId;
	var showSplitId = map.showSplitId;//存储像 2，3，4，
	var ids = map.ids;
	var selIds = ComputeTotal_makeSelIds(ids);
	$(selIds).change(function(){ 
		ComputeTotal_changeValue( selIds ,totalId,showSplitId);
	})
	
}
function ComputeTotal_changeValue( selIds,totalId,showSplitId){
	var total=	0;
	var number = 0;
	var splitStr = "";
	var i = 0;
	$(selIds).each(function(){
		if(i++>0){
			splitStr+=",";
		}
		splitStr+=""+$(this).val();
		number =Number($.trim($(this).val()));
		if(!isNaN(number)){
			total += number;
		}else{
			alert("输入的值不正确");
		}
	})
	 
	if(totalId){
		$("#"+totalId).val(total);
	}
	
	if(showSplitId){ 
		$("#"+showSplitId).val(splitStr);
	}
};
function ComputeTotal_makeSelIds (idWithSplit){
	var ids = (idWithSplit||"").split(",");
	var selIds = "";
	if(ids.length>0){
		for(i in ids){
			if(i!=0){
				selIds+=",";
			}
			selIds+="#"+$.trim(ids[i]);
		}
	}
	return selIds;
}