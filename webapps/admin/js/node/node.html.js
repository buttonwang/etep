/*//
GMAP("logger",22);
//*/
var newItemDiv =""; 
j(function(){
	new  Tree("menu");
 	var nid =new G().nextId ;
	j(".view").each(function(){	
		j(this).attr("id","view_"+nid());
	})
	j("a[href=show_node.htm]").click(function(){
		stopBD(e,1,2)										  
	})
	j("a[z!=]").click(function(e){
		stopBD(e,1,2);
		var jgp=j(this).parent().parent();
		if(jgp.find(">div[class=nodisp]").size()==0){
			jgp.append(newItemDiv);
		}
		jgp.find(">div[class=nodisp]").toggle();
	})
	j("div[id~=_more]").click(function(e){
		stopBD(e,1,2);
	})
	j("a[v=opt][t=del]").click(function(e){
		stopBD(e,1,2);
		var jgp = j(this).parent().parent();
		if(confirm("确认删除")){
			if(jgp.attr("v")=="item"){
				jgp.parent().remove();
			}else{
				jgp.remove();
			}
			j("#back").height(j("#menu").height());
		}
	})
	var of = j("#back").offset();
	j("#menu").css({"position":"absolute","left":"0px","top":of.top +"px","padding":"5px 0px 0px 0px "});
	j(window).resize(function(){
		of = j("#back").offset();
		j("#menu").css({"position":"absolute","left":"0px","top":of.top +"px","padding":"5px 0px 0px 0px "});
		var window_width  = j(window).width();
		var nodeType_left = window_width*0.6;
		var orderNum_left = window_width*0.4;
	 	var action_left   = window_width*0.85;
		j("#menu"+" .action").each(function(){
			j(this).css({"left":action_left+"px"});
			var offset = j(this).offset();
			var left = offset.left -= 30;
			j(this).css({"left":(left+"px")});
			offset = j(this).offset();
			left = offset.left - j(this).attr("t")*30; 
			j(this).css("left",(left+"px"));
		})
		j("#menu"+" .nodeType").each(function(){
			j(this).css({"left":nodeType_left+"px"});
			var offset = j(this).offset();
			var left = offset.left -= 30;
			j(this).css({"left":(left+"px")});
			offset = j(this).offset();
			left = offset.left - j(this).attr("t")*30; 
			j(this).css("left",(left+"px"));
		})
		j("#menu"+" .orderNum").each(function(){
			j(this).css({"left":orderNum_left+"px"});
			var offset = j(this).offset();
			var left = offset.left -= 30;
			j(this).css({"left":(left+"px")});
			offset = j(this).offset();
			left = offset.left - j(this).attr("t")*30; 
			j(this).css("left",(left+"px"));
		})
	}) 
	j("body").click(function(){
		j(window).trigger("resize");
	})
	j("div[v=item]").click(function(){
		var jp = j(this).parent();
		logger( j("#menu").height(),"height")
		j("#back").height(j("#menu").height());
	 
	}).each(function(){
		var jp = j(this).parent();
		if(jp.attr("yyy")==undefined){
			jp.attr("yyy","show");
		}
	})
	var ALL_VIEW_NUM = j(".view").size();
	for(var i=0;i<ALL_VIEW_NUM;i++){
		var css="t";
		if(i%2==1){
			css="t1";
		}
		j("#back").append('<li class="'+css+'"></li>');
	}
	j("#apDiv1").height(j("#back").height());
})