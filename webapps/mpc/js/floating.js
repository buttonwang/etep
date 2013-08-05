
jQuery.fn.floatdiv=function(location,z_index){
	var isIE6=false;
	if($.browser.msie && $.browser.version=="6.0"){
		$("html").css("overflow-x","auto").css("overflow-y","hidden");
		isIE6=true;
	};
	$("body").css({margin:"0px",padding:"0px",
		border:"0px",
		height:"100%",
		overflow:"auto"
	});
	return this.each(function(){
		var loc;
		if(location==undefined || location.constructor == String){
			switch(location){
				case("rightbottom"):
					loc={right:"0px",bottom:"0px"};
					break;
				case("leftbottom"):
					loc={left:"0px",bottom:"0px"};
					break;	
				case("lefttop"):
					loc={left:"0px",top:"0px"};
					break;
				case("righttop"):
					loc={right:"0px",top:"0px"};
					break;
				case("middle"):
					var l=0;
					var t=0;
					var windowWidth,windowHeight;
					
					if (self.innerHeight) {
						windowWidth=self.innerWidth;
						windowHeight=self.innerHeight;
					}else if (document.documentElement&&document.documentElement.clientHeight) {
						windowWidth=document.documentElement.clientWidth;
						windowHeight=document.documentElement.clientHeight;
					} else if (document.body) {
						windowWidth=document.body.clientWidth;
						windowHeight=document.body.clientHeight;
					}
					l=windowWidth/2-$(this).width()/2;
					t=windowHeight/2-$(this).height()/2;
					loc={left:l+"px",top:t+"px"};
					break;
				default:
					loc={right:"0px",bottom:"0px"};
					break;
			}
		}else{
			loc=location;
		}
		if(z_index){
			$(this).css("z-index",z_index )
		}
		$(this) .css(loc).css("position","fixed");
		if(isIE6){
			if(loc.right!=undefined){				
				if($(this).css("right")==null || $(this).css("right")==""){
					$(this).css("right","18px");
				}
			}
			$(this).css("position","absolute");
		}
	});
};
$(function(){
	$("div[id~=sign]").each(function(){
		$(this).floatdiv("middle");
	})
})
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