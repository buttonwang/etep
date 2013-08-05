function mpc3x4xResetId(tableId){
	var i=0;
	$("#"+tableId).find("tr").each(function(){
		 if($(this).attr("name")!="save") 
			 $(this).find("td:eq(0)").html(++i);
	}) ;
}
$(function(){
	var g = new G();
	$("input[_t=reloadAnswerSource]").click(function(){
		var tbID = $(this).attr("_tableId")||"";
		var asId =  $(this).attr("_answerSourceId")||"";
		var count = $(this).attr("_count")||"";
		 
		if(tbID!=""&&asId!=""){
			var source = $("textarea[_id="+asId+"]").text().replace(/<img(\s|\S)+?>/igm,'|____|').replace(/<(\s|\S)+?>/igm,'').split("；");
			var tbIDHtml = "";
			try{
				for(var i=0;i<source.length;i++){
					if(source[i].indexOf("|____|")>-1){
						tbIDHtml+= "<tr><td>"+(i+1)+"</td><td><input type=hidden  id='newcorrectAnswer_"+count+"' name=correctAnswer >"
								+"<span style=border-bottom:1px solid #000000;>&nbsp;&nbsp;<span id='newcorrectAnswer_"+count+"_show'></span>&nbsp;&nbsp;</span>"
								+"</td>"
					   			+"<td><a href=# onclick=getFormulator('newcorrectAnswer_"+count+"')>公式修改</a>&nbsp;&nbsp;"
								+"&nbsp;<a style='cursor:pointer' onclick='deleteLine(this.parentNode.parentNode);"+getMpc3x4xResetIdStr(tbID)+"'>删除</a>"
								+"</td></tr>";
						//alert("test:\n\n\n"+tbIDHtml)
					}else{
						tbIDHtml+= "<tr>"
								+"<td >"+(i+1)+"</td>"
								+"<td ><input type=text  id='newcorrectAnswer_"+count+"' size='60' name=correctAnswer  value='"+ source[i] +"'></td>"
								+"<td><a style='cursor:pointer' onclick='deleteLine(this.parentNode.parentNode);"+getMpc3x4xResetIdStr(tbID)+"'>删除</a></td>" 
								+"</tr>";
					}
				}
			}catch(e){
				//alert(e.toString());
			}			 
			$("#"+tbID).html("<TBody>"+tbIDHtml+"</TBody>").find("tr").each(function(){
				$(this).find("td:eq(0)").css("width","5%");
				$(this).find("td:eq(1)").css("width","75%");
				$(this).find("td:eq(2)").css("width","20%");
			});
			
		}
	})
})