function EditSpan(str,jArray,value){
	if($.trim(str)=="好记性不如烂笔头，快记下对此题的体会吧！"){
		for(var jobj in jArray){
			try{jArray[jobj].val(value||"")}catch(e){}
			try{jArray[jobj].text(value||"");}catch(e){}
		}
	}
}
function GongShiEdit(id,htmlStr,showSpanOrEdit){
	var jit = $("#"+id);
	if(jit.size()>0){
		var g = new G();
		var jedit=jit.find(".edit");
		var jinsert=jit.find(".insert");
		var jshow=jit.find(".show");
		var jsave=jit.find(".save");
		jsave.hide();
		var jrealTextarea=jit.find(".realTextarea");
		this.jrealTextarea=jrealTextarea;
		this.jshow=jshow;
		if($.browser.mozilla ==true){
			jshow.html("<font color='red'>本功能暂时不支持firefox</font>")
		}
		this.htmlStr = htmlStr||"";
		if(this.htmlStr!=''){
			jshow.get(0).innerHTML = this.htmlStr;
		}
		this.gsArr =gsArr = [];
		this.gsData= gsData={};
		var initGSData_f = this.initGSData;
		var initGdiv_f = this.initGdiv;
		var initGS = this.initGS;
		var ParamObj_f=this.ParamObj;
		initGSData_f(jshow.find(".gongsi"),gsData,g); 
		jshow.click(function(){
			jshow.hide();
			jedit.show();
			jinsert.show();
			jit.find(".gdiv").hide();
			initGSData_f(jshow.find(".gongsi"),gsData,g)
			jshow.find(".gongsi").each(function( ){
				$(this).replaceWith('{'+$(this).attr("id")+'}');
			})
			var str = jshow.get(0).innerHTML;
	 		//var width = str.length *Number(jedit.css("font-size").replace("px",""))+20;
			//if(width<jshow.width()){
				//width=jshow.width();
			//}
			jedit.css("width",width )
			jedit.text(str);
			EditSpan(str,[jedit,jshow]);
		})
		
		initGS(jshow.find(".gongsi"));
		jsave.click(function(){
			var str = ParamObj_f(jedit.text(),gsData);
			jedit.hide();
			jinsert.hide();
		 
			jshow.get(0).innerHTML=str;
			
			jshow.show();
			initGS(jshow.find(".gongsi"));
			jedit.hide();
			jrealTextarea.text(jshow.get(0).innerHTML);
		})
		jinsert.click(function(){
			var nextgsId=g.nextStrId("gs");
			if(gsData[nextgsId]== undefined ){
				gsData[nextgsId]= "<span class=\"gongsi\" id=\""+nextgsId+"\"></span>";
			}
			var str = jedit.text()+"{"+nextgsId+"}";
	 		//var width = str.length *Number(jedit.css("font-size").replace("px",""))+10;
			//jedit.css("width",width)
			jedit.text(str);
			jsave .click();
			jit.find("#"+nextgsId).click();
		})
		jedit.unbind("blur").blur(function(){ 
			jsave.click();
		})
		var showSpanOrEdit = showSpanOrEdit||""
		if(showSpanOrEdit=="edit"){
			jshow.click();
		}
		jit.click(function(){return false;})
	}
}
GongShiEdit.prototype={
	initGSData : function(jgs,gsData,g){
		jgs.each(function( ){
			var jtemp=$(this);
			jtemp.attr("id",jtemp.attr("id")||g.nextStrId("gs"));
			var id=jtemp.attr("id");
			try{str=jtemp.get(0).innerHTML.replace("<?xml:namespace prefix = m />","")}catch(e){};
			gsData[id]= "<span class=\"gongsi\" id='"+id+"'>"+str+"</span>";
		});
		gsData.maxId=jgs.size();
	},initGS:function(jgongsiS){
		jgongsiS.each(function(){
			$(this).unbind("click").click(function(){					
				return false;
			}).addClass("spanGongSiIIIIIII");
			$(this).click(function(jrealTextarea){
				var Args = new Array(window, new String($(this).get(0).innerHTML));
				var codes = window.showModalDialog("../exam/getMathMl.html", Args,"dialogWidth:530px; dialogHeight:345px;status:no;help:no;directories:yes;scrollbars:yes;Resizable:no;");
				if(codes!=undefined){
					$(this).get(0).innerHTML=codes.replace(/</ig,"<m:").replace(/<m:\//ig,"</m:");	
				}
			})
		})
	}
	,ParamObj:function (){
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
}
$(function(){
	var g = new G();
	$("html").append('<style>.spanGongSiIIIIIII{BORDER-RIGHT:thingroove;BORDER-TOP:thingroove;DISPLAY:inline-block;BORDER-LEFT:thingroove;CURSOR:pointer;BORDER-BOTTOM:thingroove}</style>')
	$(".gongshiInputDiv").each(function(){ 
		$(this).attr("id",$(this).attr("id")||g.nextStrId("divgongsi"));
		new GongShiEdit($(this).attr("id"));
	})
})