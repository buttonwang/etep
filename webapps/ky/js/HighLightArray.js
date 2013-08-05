
$(function(){
	var i=0;
	$("span[v=highLight]").find("span").each(function(){
		$(this).replaceWith($(this).html());
		i++
	})
	//alert(i)
})
/**/
function HighLight_RegExp_Ext  (str,type){
	var type = type||"toNomal";
	var regs = [
		{
			"reg": /\\/g
			,"replaceTo":"^^^^^7"
			,"source":"\\"
			,"repTo":"\\\\"
			
		},{
			"reg":/\(/g
			,"replaceTo":"^^^^^1"
			,"source":"("
			,"repTo":"\\("
		},{
			"reg":new RegExp("/","g")
			,"replaceTo":"^^^^^1"
			,"source":"("
			,"repTo":"\\/"
		}
		,{
			"reg": /\)/g
			,"replaceTo":"^^^^^2"
			,"source":")"
			,"repTo":"\\)"
		}
		,{
			"reg": /\[/g
			,"replaceTo":"^^^^^3"
			,"source":"["
			,"repTo":"\\["
		}
		,{
			"reg": /\]/g
			,"replaceTo":"^^^^^4"
			,"source":"]"
			,"repTo":"\\]"
		}
		,{
			"reg": /\|/g
			,"replaceTo":"^^^^^5"
			,"source":"|"
			,"repTo":"\\|"
		}
		,{
			"reg": /\?/g
			,"replaceTo":"^^^^^6"
			,"source":"?"
			,"repTo":"\\?"
		}
		,{
			"reg": /\$/g
			,"replaceTo":"^^^^^7"
			,"source":"$"
			,"repTo":"\\$"
			
		},{
			"reg": /\^/g
			,"replaceTo":"^^^^^7"
			,"source":"^"
			,"repTo":"\\^"
			
		}
	]
	var t = str;
	for(var i = 0;i<regs.length;i++){
		/*if(type=="reg"){
			t = t.replace(regs[i].reg,regs[i].replaceTo);
		}else */
		if(type=="toNomal"){
			t = t.replace(regs[i].reg,regs[i].repTo);
		}else{
			t = t.replace(regs[i].replaceTo,regs[i].source);
		}
	}
	return t;
}
function  HighLight(map){
	
	var highlightStr = $.trim(map.highlightStr||"");
	var splitStr = map.splitStr||"*";
	var selj = map.sel||"div[name=content]";
	this.subItem = map.subItem||"";
	this.isHightLightAtStart = map.isHightLightAtStart||true;
	
	this.jsel =  $(selj);
	this.highLightCss = map.highLightCss||"";/*有可能是object*/
	this.highLightBackGroundCss = map.highLightBackGroundCss||"";/*有可能是object*/
	if(highlightStr!=""){
		/*分隔符中的单词*/
		var array =highlightStr.split(splitStr)||[];
		if(array.length>0){
			var eachSE=[];
			var pairNum = array.length/2 ;
			if(array.length%2!=0 ){
				++pairNum;
			}
			for(var i=0;i<pairNum;i++){
				var matchStr = $.trim(array[i*2-1]||"");
				if(matchStr!=""){
					array[i*2-1]="<span name=highLight>"+matchStr+"</span>";
				}
			}
		}
		/*原始字符串*/
		var plainText = highlightStr.replace(/\*/g,"");
		/*句子背景*/
		var translate = "<span name=highLightBackgroud>"+array.join("")+"</span>";
		$(selj).each(function(){
			var	tdHtml = $(this).html();
			try{ 
			
				//alert(["plainText",plainText,"tdHtml",tdHtml].join("\n"))
				eval('var tt = tdHtml.replace(/'+HighLight_RegExp_Ext(plainText)+'/g,translate)');
				$(this).html(tt);
			}catch(ee){
				//alert(ee.toString())
			}
		})
		/*加亮*/
		if(this.isHightLightAtStart){
			this.highLight();
		}
	}
}
HighLight.prototype={
	addClass:function(jobj,classOrStyle){
		if((jobj!=undefined)&&(classOrStyle!=undefined)){
			if(typeof classOrStyle == "object" ){
				jobj.css(classOrStyle)
			}
			if(typeof classOrStyle =="string"){
				jobj.addClass(classOrStyle);
			}
		}
	}
	,highLight:function(){
		var addClass_f = this.addClass;
		this.highLightBackGroundCss!=""?this.addClass (this.jsel.find("span[name=highLightBackgroud]"),this.highLightBackGroundCss):"";
		this.highLightCss!=""?this.addClass (this.jsel.find("span[name=highLight]"),this.highLightCss):"";
	}
	,disHighLight:function(){
		this.jsel.find("span[name=highLightBackgroud],span[name=highLight]").each(function(){
			$(this).removeAttr("class").removeAttr("style");
		})
	}
	,destroy:function(){
		this.jsel.find("span[name=highLight],span[name=highLightBackgroud]").each(function(){
			$(this).replaceWith($(this).html())
		})
	}
}
function HighLightArray(map){
	var allSplitByFenHaoArr = (map.highlightStr||"").split(";");
	var highlightStrArr = [];
	for(var i=0;i<allSplitByFenHaoArr.length;i++){
		var tempArray = allSplitByFenHaoArr[i].split("*^^*");
		for(var n=0;n<tempArray.length;n++){
			highlightStrArr[highlightStrArr.length]=tempArray[n];
		}
	}
	this.highLightArray =[];
	if(highlightStrArr.length>0){
		for(var i=0;i<highlightStrArr.length;i++){
			if( highlightStrArr[i]!=""){
				map.highlightStr = highlightStrArr[i];
				this.highLightArray[this.highLightArray.length]=new HighLight(map);
			}
		}
	}
	//alert(allSplitByFenHaoArr.join("\n----------------------\n")+"\n======================");
	//alert(highlightStrArr.join("\n----------------------\n")+"\n======================");	
}
HighLightArray.prototype={
	highLight:function(){
		 this.opt("highLight");
	}
	,disHighLight:function(){
		 this.opt("disHighLight");
	}
	,destroy:function(){
		this.opt("destroy");
	}
	,opt:function(type){
		if(this.highLightArray.length>0){
			for(var i =0;i<this.highLightArray.length;i++){
				if(type=="highLight"){
					this.highLightArray[i].highLight();
				}else if(type=="disHighLight"){
					this.highLightArray[i].disHighLight();
				}else if(type=="destroy"){
					this.highLightArray[i].destroy();
				}
			}
		}
	}
}