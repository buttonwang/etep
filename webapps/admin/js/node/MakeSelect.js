/*j(function(){
	var select_nodeType={
			name:"node_type" //可以没有
			,selectV:0  //可以没有
			,opts:[
				 {"v":"PRACTICE","n":"缺省(训练单元)"}
				,{"v":"GROUP","n":"节点组"}
				,{"v":"EVALUATE","n":"模块评测"}
				,{"v":"PHASETEST","n":"阶段测试"}
				,{"v":"PRACTICE","n":"训练单元"}
				,{"v":"UNITTEST","n":"单元测试"}
			]
	}
	var s =new  MakeSelect(select_nodeType);
	j("#nodeType").html(s.selectHtml);
})
*/
function MakeSelect(select_nodeType){
	var map = select_nodeType||{};
	this.id = ' id="'+new G().nextStrId("_select")  +'" ';
	this.name =((map.name||"")==""?"":(' name="'+map.name+'" '));
	this.css =((map.css||"")==""?"":(' class="'+map.css+'" '));
	var selectHtml = '<select'+this.name+this.css+this.id+'>';
	var opts = map.opts;
	var selectV = map.selectV||0;
 	for(var i=0;i<opts.length;i++){ 
		var sel="";
		if(typeof(selectV)=="string" && selectV==opts[i].v){
			sel= " selected ";
		}else if(typeof(selectV)=="number"&&selectV==i){
			sel= " selected ";
		}
		selectHtml+= '<option value="'+opts[i].v+'"'+sel +'>'+opts[i].n+'</option>';
	}
	selectHtml+='</select>';
	this.selectHtml=selectHtml;
}