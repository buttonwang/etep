/*{emsg:"错误提示消息内容",cmpVal:"与这里的值进行比较"} */
function ConfirmIF(map ){
	var map = map||{};
	var eArr = (map.cmpVal||"删除,删除全部").split(",");
	var inItem = 
	j("a").click(function(e){
		var confirmItem =false;
		var smsg = map.emsg;
		for(var i=0;i<=eArr.length;i++){
			if(j(this).html()==eArr[i]){
				if(smsg==undefined){
					smsg ="你确定要 "+eArr[i] +" 吗？";
				}
				confirmItem=true;
				break;
			}
		}
		if(confirmItem){
			if(!confirm(smsg)){
				stopBD(e,1,2);
			}	
		}
	})
}
