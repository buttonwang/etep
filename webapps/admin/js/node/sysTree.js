function Tree(id,mapP){
	var map = mapP||{};
	if(id!=undefined){
		/*var webURL = GMAP("webURL")*/
		var webURL = "";
		this.plugs=map.plugs||(webURL+"images/plus.gif"); 
		this.minus=map.minus||(webURL+"images/minus.gif");
		this.node=map.node||(webURL+"images/file.png");
		this.left=map.left||30;
		var addHtml = map.addHtml_f||this.addHtml;
		var changeImg = map.changeImg_f||this.changeImg;
		var __left =this.left;
		var minus=this.minus;
		var plugs=this.plugs;
		var node=this.node;
		var jjid ="#"+id;
		
		j(jjid+" div[v=item]").each(function(){
			var jit=j(this);
			var jitP = j(this).parent();
			jit.removeClass("node");
			jit.offset(jitP.offset());
			jit.find(">.view").each(function(){
				addHtml(j(this),'<img name="mp" src="'+minus+'">  ');
			})
		}).click(function(){
			var jit=j(this);
			var jp = jit.parent(); 
			jp.find(">div[v!=item]").toggle();
			changeImg(jit,undefined,plugs,minus);
 		})
		j(jjid+" div[v!=item]").each(function(){
			var jit=j(this);
			jit.find(">.view").each(function(){
				addHtml(j(this),'<img name="mp" width="12" height="12" src="'+node+'">  ');
			})
		});
		
		j(jjid+" a[v=opt]").click(function(e){
			/*stopDefault(e);
			stopBubble(e);
			//alert(j(this).attr("t"))
			var type = j(this).attr("t");
			if(type=="add"){
				window.open(j(this).attr("href"));
			}else if(type=="del"){
				if(	confirm("确定删除")){
					alert("你选择了删除，这里还没有链接");
				} 
			}else if(type=="mod"){
				if(	confirm("修改")){
					alert("你选择了修改");
				}else{
					alert("不修改被点击");
				}
			}*/
		})
		j(jjid+" div[css=root]").each(function(){
			j(this).addClass("root");
		})
		j(jjid+" div[t=hasson]").each(function(){
			var offset = j(this).offset();
			offset.left -= __left;
			j(this).css({"left":(offset.left+"px")})
		})
		j(jjid+" .action").each(function(){
			j(this).css({"left":"90%"});
			var offset = j(this).offset();
			var left = offset.left -= __left;
	
			j(this).css({"left":(left+"px")});
			offset = j(this).offset();
	
			left = offset.left - j(this).attr("t")*__left; 
			j(this).css("left",(left+"px"));
		})
		j(jjid+" .node").each(function(){
			j(this).css("left",__left);
		})
	}
}
Tree.prototype={
	/*给 j容器 里加 str */
	addHtml:function(jit,str){
		try{
			var itHstr = jit.html();
			jit.html(str +itHstr);
		}catch(v){
			logger(v,"addhtml");
		}
	}
	,changeImg:function(jit,imgSelect,plugs,minus){
		var jimg= jit.find((imgSelect||"img[name=mp]"));
		if(jimg.attr("z")==undefined){
			jimg.attr("z","1");
		} 
		if(jimg.attr("z")==""){
			jimg.attr("z","1");
			jimg.attr("src",minus); 
			//logger(minus,"changeImg");
		}else{
			jimg.attr("z","");
			jimg.attr("src",plugs); 
			//logger(plugs,"changeImg");
		}
	}
}