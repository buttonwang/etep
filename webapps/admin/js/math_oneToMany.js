function $E(){		
	if(arguments.length==1)
		return document.getElementById(arguments[0]);
}

function getFormulator(subItemId,nextId,front){
	try{
		showx = screen.availWidth - 670 ;//event.screenX - event.offsetX - 4 - 10 ; // + deltaX;
		showy = 0 ;//event.screenY - event.offsetY ; // + deltaY;
		var offXY =  "dialogLeft:"+showx+"px; dialogTop:"+showy+"px";
	}catch(e){}
	var jTextarea =$("#textarea_"+nextId+"_"+subItemId);
	var jSpan = $("#span_"+nextId+"_"+subItemId);
	var tempStr=jTextarea.text()||" ";
	var Args = [window,tempStr,front];
	 
	var codes = window.showModalDialog("../math/getMathMl.html", Args, "dialogWidth:670px; dialogHeight:400px;status:yes;directories:yes;scrollbars:yes;Resizable:yes;" +offXY );
	if(codes==undefined)
		return;
	 try{document.getElementById("textarea_"+nextId+"_"+subItemId).value=codes; }catch(e){}
	 try{document.getElementById("span_"+nextId+"_"+subItemId).innerHTML =addMForMathML(codes);}catch(e){}
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
function LparamObj (){
		var oStr = arguments[0];
		var obj = arguments[1];
		var isDebug =  arguments[2];
		var array = oStr.match(/\{.*?\}/g);
		var re = /\{|\}/g;
		var isDebug =isDebug||false;
		if(array!=null){
			for(var i=0;i<array.length;i++){
				var key = array[i].replace(re,"");
				var value ;
				var reFind;
				try{
					if(obj instanceof  Array){
						value = obj[i];
					}else{
						value = eval("obj."+key);
					}
					reFind=new RegExp(array[i],"g");
				}catch(e){
					continue;
				}
				if(reFind){
					if(isDebug){
						if(value!=undefined){
							oStr = oStr.replace(reFind,value);
						}
					}else{
						oStr = oStr.replace(reFind,value||"");
					}
				}
			}
		}
		return oStr;
	}
function getNextId(nextId,subItem_id){
	if($("#textarea_"+nextId+"_"+subItem_id).size()>0){
		getNextId(nextId++,subItem_id);
	}else{
		return nextId;
	}
}
function addRow( subItem_id ,answerType, inputToServerName,thisIsRow){
	var jtable = $("#subItemAnswerTable_"+subItem_id);
	var index=jtable.find("tr").size()+1;
	var nextId = getNextId(new G().nextId(),subItem_id);
	var teaxtAreaId= "textarea_"+nextId+"_"+subItem_id;
	var spanId= "span_"+nextId+"_"+subItem_id;
	
	var obj = {teaxtAreaId:teaxtAreaId,inputToServerName:inputToServerName,spanId:spanId,subItem_id:subItem_id,index:index++,nextId:nextId}
	var addStr = "";
		if("1" == answerType){
			addStr =['<tr>'
					,'<td width="5%" >{index}</td>'
					,'<td width="50%"><textarea id="{teaxtAreaId}" name="{inputToServerName}" style="display:none"></textarea>'
					,	'<span style="border-bottom:1px solid #000000;"><span id="{spanId}"></span></span>'
					,'</td>'
					,'<td width="20%" bgcolor="#F7F7F7" align="left"><a style="cursor:pointer" onClick="getFormulator(\'{subItem_id}\',\'{nextId}\');">公式修改</a>&nbsp;&nbsp; <a style="cursor:pointer" onClick="$(this).parent().parent().remove();LoadMath_resetId(\'{subItem_id}\')">删除</a> </Td>'
					,'<td width="25%" bgcolor="#F7F7F7" align="left">'
					,'	<input type="button" value="前插公式" onClick="addRow(\'{subItem_id}\',1,\'{inputToServerName}\',this);"  class="btn_2k3" />'
					,'	&nbsp;&nbsp;'
					,'	<input type="button" value="前插文本" onClick="addRow(\'{subItem_id}\',0,\'{inputToServerName}\',this);"  class="btn_2k3" />'
					,'	&nbsp;&nbsp;'
					,'</Td>'
				,'</tr>'].join("");
		}else{		 
			addStr =  ['<tr>'
					,'<td width="5%">{index}</td>'
					,'<td width="50%"><textarea type="text" size="50" name="{inputToServerName}"></textarea></td>'
					,'<td width="20%" bgcolor="#F7F7F7" align="left"><a style="cursor:pointer" onClick="$(this).parent().parent().remove();LoadMath_resetId(\'{subItem_id}\');">删除</a></Td>'
					,'<td width="25%" bgcolor="#F7F7F7" align="left">'
					,'	<input type="button" value="前插公式" onClick="addRow(\'{subItem_id}\',1,\'{inputToServerName}\',this);LoadMath_resetId(\'{subItem_id}\')"  class="btn_2k3" />'
					,'	&nbsp;&nbsp;'
					,'	<input type="button" value="前插文本" onClick="addRow(\'{subItem_id}\',0,\'{inputToServerName}\',this);LoadMath_resetId(\'{subItem_id}\')"  class="btn_2k3" />'
					,'	&nbsp;&nbsp;'
					,'</Td>'
				,'</tr>'].join("");
		}
		if(thisIsRow){
			$(thisIsRow).parent().parent().before(LparamObj(addStr,obj));
		}else{
			jtable.append(LparamObj(addStr,obj));
		}
		var i = 0;
		jtable.find("tr").each(function(){
			$(this).find("td:eq(0)").html(i++);
		})
		
}

function clearRow(value,answerType){
	document.getElementById(answerType).value=value;
}

function LoadMath_resetId(subItemId){
	var i=0;
	$("#subItemAnswerTable_"+subItemId).find("tr").each(function(){
		 $(this).find("td:eq(0)").html(++i);
	});
}