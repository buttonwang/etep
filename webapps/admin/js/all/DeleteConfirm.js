function DeleteConfirm(map){
	var map = map||{};
	var msg =map.msg ||"您确认要执行此操作吗？";
	var sel = map.sel||"";
	j("a"+sel).click(function(e){
		var cStr = "";
		if((sel==""&&(j(this).html()=="删除"))||(sel!="")){
			if(sel!=""){
				cStr=msg;
			}else{
				cStr="确定删除吗？";
			}
			if(!confirm(cStr)){stopBD(e,1,2);}
		}
		
		
	})	
}

function batchdelete(url) {
   	var ids = getCheckedIntValue("ids");
   	if (ids=='') {
   		alert('请先选中项目，再删除！');
   		return;
   	}
   	if (confirm('确定要删除吗？')) {
 		var actionurl = url + ids;
 		window.location.href = actionurl;
 	}
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