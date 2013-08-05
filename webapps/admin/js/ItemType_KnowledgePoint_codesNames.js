function ItemType_KnowledgePoint_codesNames(subject_code,grade_code,selectedCodes,type){
		var type = type||"knowledgePoint";
		var url = "../itembank/knowledgePoint!list.jhtml?queryType=sw&subject_code="+subject_code+"&grade_code="+grade_code;
		if(type=="itemType"){
			url ="../../admin/policy/getCodeName/_getItemType.jsp";
			url +="?gc="+grade_code+"&sc="+subject_code;
			return window.showModalDialog(url,[window,selectedCodes], "dialogWidth:400px; dialogHeight:400px;status:yes;directories:yes;scrollbars:yes;Resizable:yes;");
		}		
		return window.open(url,"newWindow", "height=600, width=500, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=300");
}
function ItemType_KnowledgePoint_codesNames2(kpcodes,subject_code,grade_code,selectedCodes,type){
	var type = type||"knowledgePoint";
	var url = "../itembank/knowledgePoint!list.jhtml?queryType=sw&subject_code="+subject_code+"&grade_code="+grade_code+"&code="+kpcodes;
	if(type=="itemType"){
		url ="../../admin/policy/getCodeName/_getItemType.jsp";
		url +="?gc="+grade_code+"&sc="+subject_code;
		return window.showModalDialog(url,[window,selectedCodes], "dialogWidth:400px; dialogHeight:400px;status:yes;directories:yes;scrollbars:yes;Resizable:yes;");
	}		
	return window.open(url,"newWindow", "height=600, width=500, toolbar= no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes,top=100,left=300");
}
function set(codesInputId,namesInputId,subject_code,grade_code,type){
	var selectedCodes =$("#"+codesInputId).val();
	var type = ((type == "knowledgePoint")?"knowledgePoint":"itemType");
	var obj = ItemType_KnowledgePoint_codesNames(subject_code,grade_code,selectedCodes,type,selectedCodes);
	 
	try{
		
		$("#"+codesInputId).val(obj[0]);
		$("#"+namesInputId).val(obj[1]);
	}catch(e){
		//alert(e.toString());
	};
}
function set2(kpcodes,codesInputId,namesInputId,subject_code,grade_code,type){
	var selectedCodes =$("#"+codesInputId).val();
	var type = ((type == "knowledgePoint")?"knowledgePoint":"itemType");
	var obj = ItemType_KnowledgePoint_codesNames2(kpcodes,subject_code,grade_code,selectedCodes,type,selectedCodes);
	 
	try{
		
		$("#"+codesInputId).val(obj[0]);
		$("#"+namesInputId).val(obj[1]);
	}catch(e){
		//alert(e.toString());
	};
}
function setItemTypeCodesNames(codesInputId,namesInputId,subject_code,grade_code ){
	 set(codesInputId,namesInputId,subject_code,grade_code,"itemType")
}
function setKnowledgePointCodesNames(codesInputId,namesInputId,subject_code,grade_code ){
	 set(codesInputId,namesInputId,subject_code,grade_code,"knowledgePoint")
}
function setKnowledgePointCodesNames2(kpcodes,codesInputId,namesInputId,subject_code,grade_code ){
	 set2(kpcodes,codesInputId,namesInputId,subject_code,grade_code,"knowledgePoint")
}