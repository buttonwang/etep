var userAgent = navigator.userAgent.toLowerCase();
var browser = {
 version: (userAgent.match(/.+(?:rv|it|ra|ie)[/: ]([d.]+)/) || [])[1],
 safari: /webkit/.test(userAgent),
 opera: /opera/.test(userAgent),
 msie: /msie/.test(userAgent) && !/opera/.test(userAgent),
 mozilla: /mozilla/.test(userAgent) && !/(compatible|webkit)/.test(userAgent)
};

function initSelect(){
	var content=document.getElementById("content");
	content.onmouseup=function(evt){
		showAddWordBtn(evt);//TODO:add user info here!
	}
}

var enableAddWord=false;
var aw_showStatusPanel=false;
var aw_showResultPanel=false;
var inWordBank=false;//是否在词库中
var queryWord;
var wordExId;

var addWordStr="";

function enableOrDisable(value){
	if(value){
		enableAddWord=true;
	}else{
		enableAddWord=false;
		removeObj("addWordBtn");
		removeObj("addWordForm");
	}
}

function showAddWordBtn(evt){
	if(!enableAddWord)return;//如果不启用 直接返回。
	var selectStr=getTextSelection().toString();
	//alert("selectStr:"+selectStr);
	var addWordDiv=document.getElementById("addWordBtn");
	if(addWordDiv) removeObj("addWordBtn");
	if(selectStr!=""){
		addWordStr=selectStr;
		 var addWordDiv1 = document.createElement("div");
		 addWordDiv1.id = "addWordBtn";
		 addWordDiv1.className = "addwordbtn";
		addWordDiv1.innerHTML = "<a href=\"javascript:showAddWordForm();\"><img src=\"../images/addw_plug.png\" alt=\"添加生词\" width=\"22\" height=\"20\" /></a>";
		
		document.body.appendChild(addWordDiv1);
		var evt = (evt)?evt:(window.event)?window.event:"";
		if(browser.msie){
		var left=Math.max(document.body.scrollLeft,document.documentElement.scrollLeft);
		var top=Math.max(document.body.scrollTop,document.documentElement.scrollTop);
		addWordDiv1.style.left = p_x = parseInt(evt.x)+left+"px";
		addWordDiv1.style.top = p_y = parseInt(evt.y)+top+"px";
		}else{
		addWordDiv1.style.left = p_x = parseInt(evt.pageX)+"px";
		addWordDiv1.style.top = p_y = parseInt(evt.pageY)+"px";
	}
	}

}

function getTextSelection(){
 if (document.selection) {
 if (document.selection.createRange().text.length > 0) {
 return document.selection.createRange().text;
 }
 } else if (window.getSelection()) {
 return window.getSelection();
 }
 return "";
}

function showAddWordForm(){
	//alert(arguments[0]);
	removeObj("addWordBtn");
 if(document.getElementById("addWordForm")){removeObj("addWordForm");}

 //var offsetObj = getOffset();

 var addWordForm = document.createElement("div");
 addWordForm.className = "addwordform";
 addWordForm.id = "addWordForm";

 addWordForm.innerHTML = "<div class=\"close move\"><span>Add Fresh Word:</span><a title=\"close form\" href=\"javascript:removeObj('addWordForm');\">Close</a> </div>" +
 		"<ul><li><label for=\"Content\">选中的词:</label><textarea class=\"txt\" rows=\"2\" name=\"Content\" id=\"wordContent\" onChange=\"disableButton()\">"+addWordStr+"</textarea></li></ul>" +
 				"<div id=\"resultUL\" style=\"display:none;\"><label for=\"Content\">查询结果:</label><div class=\"btns\" float=\"right\" id=\"resultSpan\" >xxxxx<br>yyy<br>yyy<br>yyy<br>yyy</div></div>" +
 				"<div style=\"clear:both\" ></div><div id=\"statusUL\" style=\"display:none;\"><label for=\"Content\">操作状态:</label><div class=\"btns\" float=\"right\" id=\"statusSpan\" >xxxxx</div></div>" +
 				"<div style=\"clear:both\" ></div><div class=\"btns\"><input type=\"button\" value=\"查询单词\" onclick=\"findWord()\" id=\"wordQuery\" class=\"btn\" />" +
 				"<input type=\"button\" value=\"加入个人词库\" onclick=\"addWordBank()\" id=\"addWordBank\" class=\"btn\" disabled /></div>";

 document.body.appendChild(addWordForm);

 //var offsetObj2 = getOffset("addWordForm");

var left=Math.max(document.body.scrollLeft,document.documentElement.scrollLeft);
var top=Math.max(document.body.scrollTop,document.documentElement.scrollTop);
 addWordForm.style.left = left+300+"px";
 addWordForm.style.top = top+200+"px";
 //alert("addForm left:"+addWordForm.style.left);
 //alert("addForm top:"+addWordForm.style.top);
 dragIt(addWordForm);

}

function checkWord(){
	return true;
}


function removeObj(){//5
 var o;
 for(var i=0; i<arguments.length; i++){//4
 try {//3
 o = document.getElementById(arguments[i]);
 try {
 o.parentElement.removeChild(o);
 } catch(e){//2
 try {
 o.parentNode.removeChild(o);
 } catch(e){//1
  }//1
 }//2
 }//3
	catch(e){
    }
 }//4
}//5


function dragIt(obj){
 obj.onmousedown = function(evt){
 var evt = (evt)?evt:(window.event)?window.event:"";
 var tgt = (evt.target)?evt.target:evt.srcElement;
 if(!tgt) return;
 if(tgt.tagName!="DIV"&&tgt!=obj) return;
 var ox, oy;
 if(browser.msie){
 ox = parseInt(evt.offsetX);
 oy = parseInt(evt.offsetY);
 obj.setCapture();
 }else{
 ox = parseInt(evt.layerX);
 oy = parseInt(evt.layerY);
 window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
 }
// if(obj.style.zIndex < Liwf.floaters._zindex){
// obj.style.zIndex = ++Liwf.floaters._zindex;
//}

 document.onmousemove = function(evt){
 var evt = (evt)?evt:(window.event)?window.event:"";
 var tx, ty;
 var scrLeft=Math.max(document.body.scrollLeft,document.documentElement.scrollLeft);
 var scrTop=Math.max(document.body.scrollTop,document.documentElement.scrollTop);
 
 if(browser.msie){
 tx = parseInt(evt.clientX)-ox+scrLeft;
 ty = parseInt(evt.clientY)-oy+scrTop;
 }else{
 tx = parseInt(evt.pageX)-ox;
 ty = parseInt(evt.pageY)-oy;
 }
 if(tx<scrLeft)tx=scrLeft;
 if(ty<scrTop)ty=scrTop;
 if(tx>scrLeft+500) tx=scrLeft+500;
 if(ty>scrTop+500) ty=scrTop+500;
 //obj.style.left = tx<r[0]?r[0]:tx>r[1]?r[1]:tx + "px";
 //obj.style.top = ty<r[2]?r[2]:ty>r[3]?r[3]:ty + "px";
 obj.style.left = tx + "px";
 obj.style.top = ty + "px";
 };

 document.onmouseup = function(){
 var evt = (evt)?evt:(window.event)?window.event:"";
 if(browser.msie){
 obj.releaseCapture();
 }else{
 window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
 }
 document.onmousemove=null;
 document.onmouseup=null;
 };
 };
}

/*
*设置panel的显示属性
*根据panel的高度设置整个div的高度。。。
*/
function setPanelAttribute(){
	
	var resultPanel=document.getElementById("resultUL");
	var statusPanel=document.getElementById("statusUL");
	var addWordForm=document.getElementById("addWordForm");
	var height=167;//原div高度
	if(aw_showResultPanel){
		resultPanel.style.display="block";
		height=height+resultPanel.offsetHeight;
	}else{
		resultPanel.style.display="none";		
	}
	if(aw_showStatusPanel){
		statusPanel.style.display="block";
		height=height+25;
	}else{
		statusPanel.style.display="none";
	}
	
	
}
/*
*查询的queryBack
*0 未查到。
*1 查到了。
*/
function queryCallBack(data){
		var status=data[0];
		var resultSpan=document.getElementById("resultSpan");
		var statusSpan=document.getElementById("statusSpan");
		if(status==0){
			aw_showStatusPanel=true;
			aw_showResultPanel=false;
			statusSpan.innerHTML="<font style=\"color:red\">你所查询的单词"+data[1]+"不在当前词库中！</font>";
			inWordBank=false;
			}
		if(status==1){
			aw_showStatusPanel=true;
			aw_showResultPanel=true;
			statusSpan.innerHTML="<font style=\"color:green\">查询成功！</font>";
			resultSpan.innerHTML="<font style=\"color:black\">"+data[1]+"</font>";
			inWordBank=true;
			wordExId=data[2];
			var addButton=document.getElementById("addWordBank");
			addButton.disabled=false;	
			}
		//加入按钮enable
		setPanelAttribute();		
	}
/*
*3 此单词成功加入词库
*4 加入个人词库失败。。
*/
function addBankCallBack(data){
		var status=data[0];
		var fword=data[1];
		var statusSpan=document.getElementById("statusSpan");
		aw_showStatusPanel=true;
		if(status==3){
			statusSpan.innerHTML="<font style=\"color:green\">单词"+fword+"已成功加入您的个性化词库！</font>";
		}else if(status==4){
			statusSpan.innerHTML="<font style=\"color:red\">单词"+fword+"加入操作失败！原因:"+data[2]+"</font>";
		}
		setPanelAttribute();
		var addButton=document.getElementById("addWordBank");
		addButton.disabled=true;
	}

function findWord(){
	var word=document.getElementById("wordContent").value;
	queryWord=word;
	freshWordService.findWord(word,queryCallBack);
}
function addWordBank(){
	//alert("asd");
	var params=new Array();
	freshWordService.insertToBank(queryWord,wordExId,processInstanceId,addBankCallBack);
	
}
/*
* disabled=true
*/
function disableButton(){
	//alert("s");
	queryWord="";
	var addButton=document.getElementById("addWordBank");
	addButton.disabled=true;
}

