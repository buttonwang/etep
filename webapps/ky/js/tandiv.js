function openShutManager(oSourceObj,oTargetObj,shutAble,oOpenTip,oShutTip)
{
	var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
	var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
	var openTip = oOpenTip || "";
	var shutTip = oShutTip || "";
	if(targetObj.style.display!="none"){
	   if(shutAble) return;
	   targetObj.style.display="none";
	   if(openTip  &&  shutTip){
		   sourceObj.innerHTML = shutTip; 
	   }
	} else {
	   targetObj.style.display="block";
	   if(openTip  &&  shutTip){
		   sourceObj.innerHTML = openTip; 
	   }
	}
}

function hideDiv(hdiv)
{
    var obj = document.getElementById(hdiv);
    obj.style.display = "none";
}

/*���ʵ���*/
function getposOffset(what, offsettype)
{ 
    var totaloffset=(offsettype=="left")? what.offsetLeft : what.offsetTop; 
    var parentEl=what.offsetParent; 
    while (parentEl!=null)
    { 
        totaloffset=(offsettype=="left")? totaloffset+parentEl.offsetLeft : totaloffset+parentEl.offsetTop; 
         parentEl=parentEl.offsetParent; 
    } 
    return totaloffset; 
} 

function ShowProductInfo(itemindex)
{
    var objpos = document.getElementById(itemindex.toString()+"showProduct");
    var obj = document.getElementById(itemindex.toString()+"productInfo");
    obj.style.top = (getposOffset(objpos,"top")-93)+"px";
    obj.style.left=(getposOffset(objpos,"left")+15)+"px";
    obj.style.display = "block";
}

function HideProductInfo(itemindex)
{
    var obj = document.getElementById(itemindex.toString()+"productInfo");
    obj.style.display = "none";
}

/*
 * 弹解析窗口
 */
function showResolveInfo(itemindex)
{
    var objpos = document.getElementById("resolveInfo"+itemindex.toString());
    var obj = document.getElementById("resolveDiv"+itemindex.toString());
    if (obj.style.display=="none")
        obj.style.display=""
        else
        obj.style.display="none"
    //var width=obj.style.width;
    //var clientWidth=obj.clientWidth;
    var height=obj.offsetHeight;
    //alert("w"+width+" "+clientWidth);
    //alert("h"+height);
    var myHeight=getposOffset(objpos,"top")-height;
    if(myHeight<0)myHeight=0;
    obj.style.top = (myHeight)+"px";
    obj.style.left=(getposOffset(objpos,"left"))+"px";
    
}

function hideResolveDiv(itemindex)
{
    var obj = document.getElementById("resolveDiv"+itemindex.toString());
    obj.style.display = "none";
}
