/*
*字符串处理
*/
	function ltrim(s){
		return s.replace(/^\s+/,"");
	}
	
	function rtrim(s){
		return s.replace(/\s+$/,"");
	}
	
	function trim(s){
		return rtrim(ltrim(s));
	}
	
 	function $E(){
		if(arguments.length==1)
			return document.getElementById(arguments[0]);
    }
	
	
	var rightImage='<img src="../images/true.gif" width="16" height="16" class="reimages"/>';
	var errorImage='<img src="../images/false.gif" width="16" height="16" class="reimages"/>';
	
	var smileImage='<div class="lianxi_sm1"></div>';
	var sadImage='<div class="lianxi_sm2"></div>';
	
function doQuestion1(){
	$E("question_1_answer_1").value=trim($E("question_1_input_1").value);
	$E("question_1_answer_2").value=trim($E("question_1_input_2").value);
	$E("question_1_answer_3").value=trim($E("question_1_input_3").value);
	$E("question_1_answer_4").value=trim($E("question_1_input_4").value);
	//压力；拉力；15；9
	var tag=1;
	if(trim($E("question_1_input_1").value)=='压力')
		$E("question_1_image_1").innerHTML=rightImage;
	else{
		$E("question_1_image_1").innerHTML=errorImage;
		tag=0;
	}
	if(trim($E("question_1_input_2").value)=='拉力')
		$E("question_1_image_2").innerHTML=rightImage;
	else{
		$E("question_1_image_2").innerHTML=errorImage;
		tag=0;
	}
	if(trim($E("question_1_input_3").value)=='15')
		$E("question_1_image_3").innerHTML=rightImage;
	else{
		$E("question_1_image_3").innerHTML=errorImage;
		tag=0;
	}
	if(trim($E("question_1_input_4").value)=='9')
		$E("question_1_image_4").innerHTML=rightImage;
	else{
		$E("question_1_image_4").innerHTML=errorImage;
		tag=0;
	}
	if(tag)
		$E("question_1_result").innerHTML=smileImage;
	else
		$E("question_1_result").innerHTML=sadImage;	
		
}
function removeAllImage(){
	$(".reimages").each(function(){
		$(this).remove();
	})
}


function getMathInput(obj) {
		
		//var objevent = obj.onmouseout;
		//obj.onmouseout=null;
		
		var objVal = $(obj).next(".formula").val();
		
		var mathVal = getFormulator(objVal);
		
		if (!mathVal) {
			window.focus();
			return;
		} else {
			obj.innerHTML = addMForMathML(mathVal);
						
			$(obj).next("input").val(mathVal);
				
			$(obj).css("width", "auto");
		}
		
		if (obj.onmouseout==null) {
			obj.onmouseout = objevent;
    	}
		window.focus();
	}
	
	
function doQuestion2(){
	removeAllImage();
	var answer='<math><mrow><msub><mi>H</mi><mn>2</mn></msub><mi>O</mi></mrow></math>';
	$E("question_2_answer_1").innerHTML = addMForMathML($E("question_2_input_1").value);
	if($E("question_2_input_1").value==answer){
		$E("question_2_result").innerHTML=smileImage;
		//$E("question_2_image_1").innerHTML=rightImage;
		$("#question_2_answer_1").after(rightImage);
		}
	else{
		//$E("question_2_image_1").innerHTML=errorImage;
		$("#question_2_answer_1").after(errorImage);
		$E("question_2_result").innerHTML=sadImage;	
		}
}	
	
function doQuestion3(){
	removeAllImage();
	var answer='<math><mrow><mi>y</mi><mo>=</mo><msup><mi>x</mi><mn>2</mn></msup><mo>&minus;</mo><mn>3</mn><mi>x</mi><mo>+</mo><mn>1</mn></mrow></math>';
	$E("question_3_answer_1").innerHTML = addMForMathML($E("question_3_input_1").value);
	var input = $E("question_3_input_1").value;
	input = input.replace("&plus;", "+");
	if(input==answer){
		$E("question_3_result").innerHTML=smileImage;
		//$E("question_3_image_1").innerHTML=rightImage;
		$("#question_3_answer_1").after(rightImage);	
		}
	else{
		//$E("question_3_image_1").innerHTML=errorImage;
		$("#question_3_answer_1").after(errorImage);
		$E("question_3_result").innerHTML=sadImage;	
		}
}

function doQuestion4(){
	removeAllImage();
	var answer="<math><mrow><mi>Mg</mi><mo stretchy='false'>(</mo><mi>OH</mi><msub><mo stretchy='false'>)</mo><mn>2</mn></msub></mrow></math>";
	$E("question_4_answer_1").innerHTML = addMForMathML($E("question_4_input_1").value);
	if($E("question_4_input_1").value==answer){
		$E("question_4_result").innerHTML=smileImage;
		//$E("question_4_image_1").innerHTML=rightImage;
		$("#question_4_answer_1").after(rightImage);
		}
	else{
		//$E("question_4_image_1").innerHTML=errorImage;
		$("#question_4_answer_1").after(errorImage);
		$E("question_4_result").innerHTML=sadImage;	
		}
}	

function doQuestion5(){
	removeAllImage();
	$E("question_5_answer_1").value=trim($E("question_5_input_1").value);
	$E("question_5_answer_2").value=trim($E("question_5_input_2").value);
	$E("question_5_answer_3").value=trim($E("question_5_input_3").value);
	//氧化还原；蓝；红棕色
	var tag=1;
	if(trim($E("question_5_input_1").value)=='氧化还原')
		$E("question_5_image_1").innerHTML=rightImage;
	else{
		$E("question_5_image_1").innerHTML=errorImage;
		tag=0;
	}
	if(trim($E("question_5_input_2").value)=='蓝')
		$E("question_5_image_2").innerHTML=rightImage;
	else{
		$E("question_5_image_2").innerHTML=errorImage;
		tag=0;
	}
	if(trim($E("question_5_input_3").value)=='红棕色')
		$E("question_5_image_3").innerHTML=rightImage;
	else{
		$E("question_5_image_3").innerHTML=errorImage;
		tag=0;
	}
	var answer="<math><mrow><mi>Cu</mi><mo>+</mo><mn>4</mn><mi>HN</mi><msub><mi>O</mi><mn>3</mn></msub><mo stretchy='false'>(</mo><mtext>浓</mtext><mo stretchy='false'>)</mo><mo>&trie;</mo><mi>Cu</mi><mo stretchy='false'>(</mo><mi>N</mi><msub><mi>O</mi><mn>3</mn></msub><msub><mo stretchy='false'>)</mo><mn>2</mn></msub><mo>+</mo><mn>2</mn><mi>N</mi><msub><mi>O</mi><mn>2</mn></msub><mo lspace='4px' rspace='4px'>&uarr;</mo><mo>+</mo><mn>2</mn><msub><mi>H</mi><mn>2</mn></msub><mi>O</mi></mrow></math>";	
	answer = answer.replace(/<mo[^>]+>/ig, "<mo>");
	
	$E("question_5_answer_4").innerHTML = addMForMathML($E("question_5_input_4").value);		
	var input = $E("question_5_input_4").value;
	input = input.replace(/&plus;/ig, "+");
	input = input.replace("↑", "&uarr;");
	input = input.replace(/<mo[^>]+>/ig, "<mo>");
	if(input==answer){
		//$E("question_5_image_4").innerHTML=rightImage;
		$("#question_5_answer_4").after(rightImage);
		}
	else{
		//$E("question_5_image_4").innerHTML=errorImage;
		$("#question_5_answer_4").after(errorImage);
		tag=0;
		}
	if(tag)
		$E("question_5_result").innerHTML=smileImage;
	else
		$E("question_5_result").innerHTML=sadImage;	
		
}
