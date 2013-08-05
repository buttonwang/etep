//全选或全取消
function checkAll(boxname){
	var boxs = document.getElementsByName(boxname); 
	for(i = 0; i < boxs.length; i++)
		boxs[i].checked=event.srcElement.checked 
}

// 获取并返回checkboxValue, 被checked的值
function getCheckedValue(boxname) {
	var boxs = document.getElementsByName(boxname);
	var str = "";
	if (boxs.length > 0) {
		for (var i = 0; i < boxs.length; i++) {
			if (boxs[i].checked) str += "'" + boxs[i].value + "'" + ",";
		}
	}
	return str.replace(new RegExp(',$'), '');	 
}

// 获取并返回checkboxValue, 被checked的值
function getCheckedIntValue(boxname) {
	var boxs = document.getElementsByName(boxname);
	var str = "";
	if (boxs.length > 0) {
		for (var i = 0; i < boxs.length; i++) {
			if (boxs[i].checked) str += boxs[i].value + ",";
		}
	}
	return str.replace(new RegExp(',$'), '');	
}

//页面提交时pageNO置为1
function toSumit(){	
	var obj=document.getElementById('pageForm');
	document.getElementById('pageNo').value = 1;
	//document.getElementById('queryType').value = "0";
	obj.submit();
}

/***************** 校验相关的函数 ****************************/

function ltrim(s){
	return s.replace(/^\s+/,"");
}

function rtrim(s){
	return s.replace(/\s+$/,"");
}

function trim(s){
	return rtrim(ltrim(s));
}

function isInteger(intValue) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	return re.test(intValue);
}

function isNumeric(numValue) {
	return !isNaN(numValue);
	//ValidationExpression= /^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;
	//return ValidationExpression.test(numValue);
}

function validateEmpty(id, warnInfo) {
	var element = document.getElementById(id);
	if (element!=null) {
		if (trim(element.value)=="") {
			alert(warnInfo);
			element.focus();
			return false;
		}
	}
	return true;
}

function validateReadOnly(id) {
	var element = document.getElementById(id);
	if (element!=null) {
		//alert(element.getAttribute("readonly"));
		return element.readOnly;
	}
	return false;
}

function validateInteger(id, warnInfo) {
	var element = document.getElementById(id);	
	if (element!=null) {
		if (element.value!="") {
			if (!isInteger(element.value)) {
				alert(warnInfo+", 请输入整数!");
				element.focus();
				return false;
			}
		}
	}
	return true;
}

function validateNumeric(id, warnInfo) {
	var element = document.getElementById(id);
	if (element!=null) {
		if (element.value!="") {
			if (!isNumeric(element.value)) {
				alert(warnInfo+", 请输入数值!");
				element.focus();
				return false;
			}
		}
	}
	return true;
}

function validateEmpty2(name, warnInfo) {
	var elements = document.getElementsByName(name);		
	if (elements.length>0) {
		var element = elements[0];
		if (trim(element.value)=="") {
			alert(warnInfo);
			element.focus();
			return false;
		}
	}
	return true;
}

function validateInteger2(name, warnInfo) {
	var elements = document.getElementsByName(name);	
	if (elements.length>0) {
		var element = elements[0];
		if (element.value!="") {
			if (!isInteger(element.value)) {
				alert(warnInfo+", 请输入整数!");
				element.focus();
				return false;
			}
		}
	}
	return true;
}

function validateNumeric2(name, warnInfo) {
	var elements = document.getElementsByName(name);
	if (elements.length>0) {
		var element = elements[0];
		if (element.value!="") {
			if (!isNumeric(element.value)) {
				alert(warnInfo+", 请输入数值!");
				element.focus();
				return false;
			}
		}
	}
	return true;
}

/* 判断输入是否唯一 */
function hasSameName(formname, fieldname){
	if (validateReadOnly(fieldname)) return false;

	var tabname =  tableName(formname);
	if (tabname == '') return false;
	var element =  document.getElementById(fieldname);
	if (element == null) return false;
	var fieldvalue = document.getElementById(fieldname).value;
	if (fieldvalue == '') return false;
	SecurityService.nameOnly(tabname, fieldname, fieldvalue, 
		function(val) {
			var vale = val*1;
			if (vale!=0) {
				alert("编码已存在，请重新输入！ ");
				element.focus();				
			};
		}
	);
}

/* 分钟转化成秒保存 */
function minute2second(name) {
	var ret = 0;
	var elements = document.getElementsByName(name);
	if (elements.length>0) {
		var element = elements[0];		
		if (element.value!="") {
			ret = Math.round(parseFloat(element.value)*60);			
			element.value = ret;			
		}
	}
	return ret;
}

/* 秒转化成分钟显示 */
function second2minute(name) {	
	var ret = 0;
	var elements = document.getElementsByName(name);
	if (elements.length>0) {
		var element = elements[0];
		alert(element.value);
		if (element.value!="") {
			ret = parseFloat(element.value)/60;
			element.value = ret.toFixed(2);
		}
	}
	return ret;
}

/* 处理FCK编辑 */
function PrepareFCKSave() {
	// If the textarea isn't visible update the content from the editor.
	if ( document.getElementById( 'Textarea' ) != null ) {
		if ( document.getElementById( 'Textarea' ).style.display == 'none' )
		{
			var oEditor = FCKeditorAPI.GetInstance( 'DataFCKeditor' ) ;
			document.getElementById( 'DataTextarea' ).value = oEditor.GetXHTML() ;
		}
	}
}

/* 	常规页面校验  */
function validateForm(formName) {
	var cnname = formCnName(formName);
	
	if (!validateEmpty("code", cnname + "编码不能为空！")) return false;	
	if (!validateEmpty("name", cnname + "名称不能为空！")) return false;
	if (!validateInteger("itemNumPerpage", "每页显示题数的输入格式不正确")) return false;
		
	return true;
}


/* Paper页面校验*/
function validatePaperForm() {
	if (!validateEmpty("name", "试卷名称不能为空！")) return false;	
	if (!validateNumeric("difficultyValue", "试卷难度的输入格式不正确")) return false;
	if (!validateInteger("answeringTime", "答卷时间的输入格式不正确")) return false;
	if (!validateInteger("itemsNum", "题数的输入格式不正确")) return false;
	if (!validateInteger("totalScore", "卷分的输入格式不正确")) return false;	
	
	return true;
}

/* Item页面校验*/
function validateItemForm() {
	if (!validateNumeric2("item.score", "试题分值的输入格式不正确")) return false;
	if (!validateNumeric2("item.difficultyValue", "试题难度的输入格式不正确")) return false;
	if (!validateInteger2("item.wordCount", "文章字数的输入格式不正确")) return false;	
	if (!validateNumeric2("item.readingTime", "阅读用时的输入格式不正确")) return false;
	if (!validateNumeric2("item.answeringTime", "答题用时的输入格式不正确")) return false;
	if (!checkYear("item.year", "年份的输入格式不正确")) return false;
	
	if (!validateNumeric2("itemVO.score", "试题分值的输入格式不正确")) return false;
	if (!validateNumeric2("itemVO.difficultyValue", "试题难度的输入格式不正确")) return false;
	if (!validateInteger2("itemVO.wordCount", "文章字数的输入格式不正确")) return false;	
	if (!validateNumeric2("itemVO.readingTime", "阅读用时的输入格式不正确")) return false;
	if (!validateNumeric2("itemVO.answeringTime", "答题用时的输入格式不正确")) return false;
	if (!checkYear("itemVO.year", "年份的输入格式不正确")) return false;
	minute2second("item.readingTime");
	minute2second("item.answeringTime");
	
	minute2second("itemVO.readingTime");
	minute2second("itemVO.answeringTime");
	
	PrepareFCKSave();
	
	return true;
}

/* Word页面校验*/
function validateWordForm(){
	if (!validateEmpty2("wordBasic.word", "单词拼写不能为空")) return false;
	if (!validateEmpty2("wordExtension.wordBasic.word", "单词拼写不能为空")) return false;
	return true;
}

function formCnName(formName) {
	var cnname = "";
	switch (formName) {
		case "grade" : cnname = "学级"; break; 
		case "itemTheme" : cnname = "题材"; break; 
		case "itemType" :  cnname = "题型"; break; 
		case "knowledgePoint" : cnname = "知识点"; break; 
		case "paperCategory" :  cnname = "试卷分类"; break; 
		case "paperType" : cnname = "试卷类型"; break;
		case "region" : cnname = "地区"; break;
		case "subject" : cnname = "学科"; break;            
		default : cnname = "";
	}
	return cnname;
}

function tableName(formName) {
	var tabname = "";
	switch (formName) {
		case "grade" : tabname = "Grade"; break; 
		case "itemTheme" : tabname = "ItemTheme"; break;
		case "itemType" :  tabname = "ItemType"; break;
		case "knowledgePoint" : tabname = "KnowledgePoint"; break; 
		case "paperCategory" :  tabname = "PaperCategory"; break;
		case "paperType" : tabname = "PaperType"; break;
		case "region" : tabname = "Region"; break;
		case "subject" : tabname = "Subject"; break;
		default : tabname = "";
	}
	return tabname;
}

function checkedGetList_show_mpc_4x(){
	try{checkedCheckItem(window.dialogArguments[2],"checkitem")}catch(e){};
}
function checkedCheckItem(checkedStrBySplit,checkBoxName){
	var checkedArr = checkedStrBySplit.split(",");
	var checkBoxs = document.getElementsByName(checkBoxName);
	for (var i = 0; i < checkBoxs.length; i++) {
		var checkBoxCode = checkBoxs[i].value.split(",")[0];
		//alert(checkBoxCode);
		for(var j=0; j < checkedArr.length; j++ ) {
			//alert(checkedArr[j]);
			if (checkedArr[j] == checkBoxCode) {
				 checkBoxs[i].checked = true;
				 break;
			}
		}
	}
}

/* 弹出页面的列表选中 */
function checkedGetList() {
	var listname = document.getElementById("listname").value + "Codes";
	var parent = window.dialogArguments[0];
	var listValue = parent.document.getElementById(listname).value;
	
	if (listValue !="") {
		var listArray = listValue.split(",");
		var checkBoxs = document.getElementsByName("checkitem");
		for (var i = 0; i < checkBoxs.length; i++) {
			var checkBoxCode = checkBoxs[i].value.split(",")[0];
			//alert(checkBoxCode);
			for(var j=0; j < listArray.length; j++ ) {
				//alert(listArray[j]);
				if (listArray[j] == checkBoxCode) {
					 checkBoxs[i].checked = true;
					 break;
				}
			}
		}
	}
	
	return true;
}

function inPapers(itemids) {
	window.location.href = "paper!choose.jhtml?itemids="+itemids;	
}

function resetPageNo(){
	document.getElementById('pageNo').value = 1;
}

function checkYear( name,warnInfo)
{
	var elements = document.getElementsByName(name);	
	if (elements.length>0) {
		var element = elements[0];
		if (element.value!="") {
			if (!isInteger(element.value) || element.value.length!=4) {
				alert(warnInfo+", 请输入年份!");
				element.focus();
				return false;
			}
		}
	}
	return true;
}