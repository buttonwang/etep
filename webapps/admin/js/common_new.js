//��ʾ����
var head="display:''"
function doit(header){
var head=header.style
if (head.display=="none")
head.display=""
else
head.display="none"
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

	function show(){		
		for(var i=0;i<arguments.length;i++){
			if(arguments!='')
				$E(arguments[i]).style.display="";
		}
	}
	function hide(){
		for(var i=0;i<arguments.length;i++){
			if(arguments!='')
				$E(arguments[i]).style.display="none";
		}
		
	}
    function $E(){		
			if(arguments.length==1)
				return document.getElementById(arguments[0]);
	}


	function checkboxifchecked(obj){
		if(obj.checked){
			$E('add_word_type_span1').style.display="";
			$E('add_word_type_span2').style.display="";
			obj.value = 1;
		}
		else{
			$E('add_word_type_span1').style.display="none";
			$E('add_word_type_span2').style.display="none";
			obj.value = 0;
		}
	}
	
	function checkboxifchecked(obj, span1, span2){
		if(obj.checked){
			$E(span1).style.display="";
			$E(span2).style.display="";
			obj.value = 1;
		}
		else{
			$E(span1).style.display="none";
			$E(span2).style.display="none";
			obj.value = 0;
		}
	}
	
	function getList(str,setNames,setCodes,event){
		showx = event.screenX - event.offsetX - 4 - 10 ; // + deltaX;
		showy = event.screenY - event.offsetY -168; // + deltaY;
		newWINwidth = 210 + 4 + 18;			
		var strTemp=str;
		var html=document.getElementById(strTemp).innerHTML;
		var Args = new Array(window, new String(html+' '),$E(setCodes).value);
		var codes = window.showModalDialog("./item/getlist.html", Args, "dialogWidth:400px; dialogHeight:400px; dialogLeft:"+
			showx+"px; dialogTop:"+showy+"px;status:yes;directories:yes;scrollbars:yes;Resizable:yes;");
		if(codes==undefined)
			return;		
		var array=codes.split(",");		
        var names=new Array();
        var codes=new Array();
        
        for(var i=0;i<array.length;i++){
     	if(i%2==0)
     		codes[i/2]=array[i];
     	else
     		names[(i-1)/2]=array[i];
        }
        $E(setNames).value=names;
        $E(setCodes).value=codes;
	}
