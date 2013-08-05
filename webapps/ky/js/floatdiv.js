/*
说明：
  x系列是Hutia开发的系列JS代码，配合相应的css后可以实现快速的自定义标签
  目前xScrollStick支持的浏览器类型为：IE5.5, FF1.5

  xScrollStick的标签为
  	<div class="xScrollStick">
  		Content...
  	</div>
  
  支持属性：
    无
  支持方法：
    无
*/

//**载入CSS*****
var xScrollStickCssFilePath="sp\/css\/xframe.css";
//document.write("<style>@import url("+xScrollStickCssFilePath+");</style>");

//**检测浏览器种类*****
var BROWSERNAME="";
switch(navigator.appName.toLowerCase()){
	case "netscape":
		BROWSERNAME="ns";
	break;
	case "microsoft internet explorer":
	default:
		BROWSERNAME="ie";
	break;
}

//**设置初始化事件******
switch(BROWSERNAME){
	case "ns":
		window.addEventListener("load",_xScrollStick_init,false);
	break;
	case "ie":
	default:
		window.attachEvent("onload",_xScrollStick_init);
}

//**初始化函数******
function _xScrollStick_init(){
	var allTheScrollSticks=document.getElementsByTagName("div");
	for(var i=0;i<allTheScrollSticks.length;i++){
		if(allTheScrollSticks[i].className.match(/^((xScrollStick)|(.+ +xScrollStick)|(xScrollStick +.+)|(.+ +xScrollStick +.+))$/))_xScrollStick_event_doInit(allTheScrollSticks[i]);
	}
	window_event_scroll();
}


//**事件响应函数区******

function _xScrollStick_event_doInit(element){
	//初始化变量
	//element.offX=element.offsetLeft;
	element.offX=element.offsetRight;
	element.offY=element.offsetTop;
	
	//设置方法
	element.Stick=_xScrollStick_method_Stick;
	
	//设置事件
	switch(BROWSERNAME){
		case "ns":
			window.addEventListener("scroll",window_event_scroll,false);
		break;
		case "ie":
		default:
			window.attachEvent("onscroll",window_event_scroll);
	}
	
	//获取设置
	//无
	document.body.parentNode.onscroll=window_event_scroll;

}

function window_event_scroll(){
	var allTheScrollSticks=document.getElementsByTagName("div");
	for(var i=0;i<allTheScrollSticks.length;i++){
		if(allTheScrollSticks[i].className.match(/^((xScrollStick)|(.+ +xScrollStick)|(xScrollStick +.+)|(.+ +xScrollStick +.+))$/))try{allTheScrollSticks[i].Stick();}catch(e){}
	}
}

//**方法函数区******
function _xScrollStick_method_Stick(){
	var x=this.offX, y=this.offY, po=this;
	this.style.position="absolute";
	//x+=document.body.parentNode.scrollLeft;
	x+=document.body.parentNode.scrollRight;
	y+=document.body.parentNode.scrollTop;
	this.style.left=x+"px";
	this.style.top=y+"px";
	
}

//**通用函数区***
//无
//]]>