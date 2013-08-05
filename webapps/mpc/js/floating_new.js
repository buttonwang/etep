
// JavaScript Document
function FloatingDIVWithID(divids){	
	var idArr = divids.split(",")||[];
	//for(var obj in idArr){
		var divid = idArr[0];
	 	var introDiv = document.getElementById(divid);
    	introDiv.style.top = 0;
	   	setInterval ('FloatingDIVWithID_refresh("'+divid+'")', 10);
	//}
}
function FloatingDIVWithID_refresh(divid){
        var introDiv = document.getElementById(divid);
        var StartPoint = parseInt(introDiv.style.top.replace("px",""));
        var EndPoint = (document.documentElement && document.documentElement.scrollTop) ? document.documentElement.scrollTop : document.body.scrollTop;
        if (EndPoint < 0) EndPoint = 0;
 
        if (StartPoint != EndPoint ) {
         ScrollAmount = Math.ceil( Math.abs( EndPoint - StartPoint ) / 15 );
         introDiv.style.top = (parseInt(introDiv.style.top) + ( ( EndPoint < StartPoint ) ? -ScrollAmount : ScrollAmount ) + 10)+"px";
        }
 }