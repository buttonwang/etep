
    function $E(){		
			if(arguments.length==1)
				return document.getElementById(arguments[0]);
	}

	function getFormulator(str){
			showx = event.screenX - event.offsetX - 4 - 10 ; // + deltaX;
			showy = event.screenY - event.offsetY -168; // + deltaY;
			newWINwidth = 210 + 4 + 18;
			var tempStr=$E(str).value;
			var Args = new Array(window, new String(tempStr+' '));
			var codes = window.showModalDialog("../math/getMathMl.html", Args, "dialogWidth:670px; dialogHeight:400px; dialogLeft:"+
				showx+"px; dialogTop:"+showy+"px;status:yes;directories:yes;scrollbars:yes;Resizable:yes;");
			if(codes==undefined)
				return;	

			$E(str).value=codes;
			$E(str+'_show').innerHTML=addMForMathML(codes);
			window.focus();
		}



function addMForMathML(str){	
	return str.replace(/</ig,"<m:").replace(/<m:\//ig,"</m:");
}



function deleteLine(r)
{
 var root = r.parentNode;
  var allRows = root.getElementsByTagName('tr')
  if(allRows.length>0)
  {
      root.removeChild(r);

  }
}
function addRow(tbID,answerType){ 
	var table=document.getElementById(tbID);
	
	var answerType = answerType||0;
	if(typeof(answerType) == "string"){
		answerType = 1;
	}
	var count =table.rows.length+1;
	var tr =table.insertRow();
	tr.border="1";
	var td0 = tr.insertCell(0);
	//td0.height = "24";
	td0.width="5%";
	 
	var td1 = tr.insertCell(1);
	var td2 = tr.insertCell(2);
	//td1.height = "24";
	td1.width="75%";
	td2.width="20%";
	td0.innerHTML = ""+count+"";
	  if("1" == answerType)
		{
		  
		  td1.innerHTML   = "<input type=hidden  id='newcorrectAnswer_"+count+"' name=correctAnswer >"
				   	+"<span style=border-bottom:1px solid #000000;>&nbsp;&nbsp;<span id='newcorrectAnswer_"+count+"_show'></span>&nbsp;&nbsp;</span>"
		  td2.innerHTML   = "<a href=# onclick=getFormulator('newcorrectAnswer_"+count+"')>公式修改</a>&nbsp;&nbsp;"
					+"&nbsp;<a style='cursor:pointer' onclick='deleteLine(this.parentNode.parentNode);"+getMpc3x4xResetIdStr(tbID)+"'>删除</a>"; 
		}else {
		  td1.innerHTML   = "<input type=text  id='newcorrectAnswer_"+count+"' size='60' name=correctAnswer >";
		  td2.innerHTML  ="<a style='cursor:pointer' onclick='deleteLine(this.parentNode.parentNode);"+getMpc3x4xResetIdStr(tbID)+"'>删除</a>"; 
		}
	
}


function clearRow(value,answerType){ 

	document.getElementById(answerType).value=value;
	

}

function getMpc3x4xResetIdStr(tableId){
	if(tableId!=undefined){
		return "mpc3x4xResetId(\""+tableId+"\");"
	}
	return "";
};
