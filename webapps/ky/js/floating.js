// JavaScript Document
function FloatingDIV()
 {
       var introDiv = document.getElementById("INTRO_DIV");
       introDiv.style.top = 0;
       FloatingDIV_refresh();
 } 
 
 function FloatingDIV_refresh()
 {
        var StartPoint, EndPoint;
        var introDiv = document.getElementById("INTRO_DIV");
        var number = document.getElementById("number");
 
        StartPoint = parseInt(introDiv.style.top.replace("px",""));
        ///EndPoint = document.body.scrollTop;
        EndPoint = (document.documentElement && document.documentElement.scrollTop) ? document.documentElement.scrollTop : document.body.scrollTop;
        if (EndPoint < 0) EndPoint = 0;
 
        if (StartPoint != EndPoint ) {
         ScrollAmount = Math.ceil( Math.abs( EndPoint - StartPoint ) / 15 );
         introDiv.style.top = parseInt(introDiv.style.top) + ( ( EndPoint < StartPoint ) ? -ScrollAmount : ScrollAmount )+"px";
        }
 
        setTimeout ("FloatingDIV_refresh();", 10);
 }