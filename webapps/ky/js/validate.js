function checkImageFormat(obj){
	var strFile = js_trim(obj.value);
	if(strFile!=""){
		var fileFormat = strFile.substring(file.lastIndexOf(".")+1).toLowerCase();
		if(fileFormat != "jpg" &&  fileFormat != "gif" && fileFormat != "jpeg" &&  fileFormat != "bmp"){
			return false;
		}
		return true;
	}
	return false;
}

function isFileExist(obj){
	var reg = /^(\w:)?[\\/]/;
	if(reg.test(js_trim(obj.value))){ 
		return true; 
	}
	return false;
}

function js_trim(deststr){
	if(deststr==null)return "".trim();
	var retStr=new String(deststr);
	var pos=retStr.length;
	if (pos==0) return retStr;
	retStr=js_ltrim(retStr);
	retStr=js_rtrim(retStr);
	return retStr;
//	return retStr.trim();
}

function js_ltrim(deststr){
	if(deststr==null)return "";
	var pos=0;
	var retStr=new String(deststr);
	if (retStr.lenght==0) return retStr;
	while (retStr.substring(pos,pos+1)==" ") pos++;
	retStr=retStr.substring(pos);
	return(retStr);
}

function js_rtrim(deststr){
	if(deststr==null)return "";
	var retStr=new String(deststr);
	var pos=retStr.length;
	if (pos==0) return retStr;
	while (pos && retStr.substring(pos-1,pos)==" " ) pos--;
	retStr=retStr.substring(0,pos);
	return(retStr);
}

function isNumeric(str) {
	var re = /[\D]/g;
	if (re.test(str)) {
		return false;
	}
	return true;
}

function isEmpty(str) {
	return (str == null) || (str.length == 0);
}

function  checkTags(obj){

	var tag = obj.value;
	tag = tag.replace('，', ',');

	if(tag==''){
		document.getElementById('tagDiv').style.display='';
		document.getElementById('tagMsg').innerHTML = "请选择或输入标签";
		tagStr = false;
	}else{
		var tags = tag.split(',');
		for (var i = 0; i < tags.length ; i++) {
			if (tags[i].replace(/^\s+/,'') == '') {
				tags.splice(i, 1);
				i--;
			}
		}

		if (tags.length > 3) {
			document.getElementById('tagMsg').innerHTML ="标签的数量超过3个了";
			tag = tags.join(',');
			obj.value = tag;

			return;
		}

		tag = tags.join(',');
		obj.value = tag;
		document.getElementById('tagDiv').style.display='none';
		document.getElementById('tagMsg').innerHTML = "";
		tagStr = true;
	}
}

function chooseTags(tagName,obj) {
	var tagValue = obj.value;
	tagValue = tagValue.replace('，', ',');
	var tags = tagValue.split(',');
	var tagArray = new Array();
	tagArray = tags;
	for (var i = 0; i < tagArray.length ; i++) {
		if (tagArray[i].replace(/^\s+/,'') == '') {
			tagArray.splice(i, 1);
			i--;
		}
	}
	if (tagArray.length >= 3) {
		alert("标签的数量已经足够了");
		return;
	}

	tagArray.push(tagName);

	tagValue = tagArray.join(',');
	obj.value = tagValue;
}
