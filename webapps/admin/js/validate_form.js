/*
	form校验
*/
function ltrim(s){
	return s.replace(/^\s+/,"");
}

function rtrim(s){
	return s.replace(/\s+$/,"");
}

function trim(s){
	return rtrim(ltrim(s));
}

function validateForm(formName) {
   var cnName = formcnname(formname);

	var code = document.getElementsByName(formname + ".code");	
	if (code!=null) {
		if (trim(code.value)=="") {
			alter(cnName + "代码不能为空！");
			return false;
		}
	}
	
	var name = document.getElementsByName(formname + ".name");
	if (name!=null) {
		if (trim(name.value)=="") {
			alter(cnName + "名称不能为空！");
			return false;
		}
	}
	return true；
}

function formCnName(formName) {
	var cnname = "";
	switch (formName) {
		case "grade" : 
		   cnname = "学级"; 
		   break; 
		case "itemtheme" : 
		   cnmname = "题材"; 
		   break; 
		case "itemtype" : 
		   cnmname = "题型"; 
		   break; 
		case "knowledgepoint" : 
		   cnmname = "知识点"; 
		   break; 
		case "papercategory" : 
		   cnmname = "试卷分类"; 
		   break; 
		case "papertype" : 
		   cnmname = "试卷类型"; 
		   break;
		case "region" : 
		   cnmname = "地区"; 
		   break;
		case "subject" : 
		   cnmname = "学科"; 
		   break;            
		default : 
		   document.write("Sorry, we are out of " + i + ".<BR>"); 
	}
	return cnname;
}