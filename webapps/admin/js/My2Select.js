function MySelect(data,names,type,selected1,selected2){
	var nArry = names.split(",");
	var type = type||"name";
	var data = data||[];
	var subjectGradesMap =[];
	var sel_0  = 'select['+type+'='+nArry[0]+']';
	var sel_1  = 'select['+type+'='+nArry[1]+']';
	var opts='<option value="{0}" {1}>{2}<option>';
	var param_f = function () {
	  var num = arguments.length;
	  var oStr = arguments[0];
	  for (var i = 1; i < num; i++) {
		var pattern = "\\{" + (i-1) + "\\}";
		var re = new RegExp(pattern, "g");
		oStr = oStr.replace(re, arguments[i]);
	  }
	  return oStr;
	}  
	var getOptionStr_f =function(data,selected){
		var _str = '<option value="0">无<option>';
		for(var i in data){
			data[i]._selected = ((data[i].c == (selected||0))?'selected':'');
			_str+= param_f(opts,data[i].c,data[i]._selected,data[i].n);
		}
		return _str ;
	};
	var getFirst_f=function(data){
		var first = [];
		for(var i in data){
			first[first.length] = data[i].obj.subject;
		}
		return first;
	};
	var getSecond_f=function(data,selected2){
		var second = []
		for(var i in data){
			if(data[i].id==selected2){
				return data[i].obj.grades;
			}
		}
		return second;
	}; 
	$(sel_0).html(getOptionStr_f(getFirst_f(data),selected1) ); 
	$(sel_0).change(function(){
		
		$(sel_1).html( getOptionStr_f(getSecond_f(data,$(this).val()),selected2));
	}).trigger("change");
 }