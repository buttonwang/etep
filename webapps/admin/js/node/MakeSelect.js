/*j(function(){
	var select_nodeType={
			name:"node_type" //����û��
			,selectV:0  //����û��
			,opts:[
				 {"v":"PRACTICE","n":"ȱʡ(ѵ����Ԫ)"}
				,{"v":"GROUP","n":"�ڵ���"}
				,{"v":"EVALUATE","n":"ģ������"}
				,{"v":"PHASETEST","n":"�׶β���"}
				,{"v":"PRACTICE","n":"ѵ����Ԫ"}
				,{"v":"UNITTEST","n":"��Ԫ����"}
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