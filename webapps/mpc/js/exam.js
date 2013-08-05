	//取当前页答案
	function makeAnswerStr(){
			//alert("size");
			var userAnswerStr="";
			var sizeObj=document.getElementById("itemSize");
			if(sizeObj==null){
				alert("对不起，该页数据有问题，itemSize不存在，请联系程序开发人员！");
				return userAnswerStr;
			}
			var size=sizeObj.value;
			var mapKeyElement;
			var questionSonElement;
			var userAnswers;
			var userAnswer;
			var mark;
			var disable;
			for(var i=0;i<size;i++){
				mapKeyElement=document.getElementById("mapkey"+i);
				if(mapKeyElement==null){
					alert("对不起，题库数据有问题，题号不存在(key)，请联系程序开发人员！");
					continue;
				}
				userAnswers=document.getElementsByName("userAnswer"+i);
				
				if((userAnswers==null) || (userAnswers.length==0)){
					alert("对不起，题库数据有问题，用户作答不存在(userAnswer)，请联系程序开发人员！");
					continue;
				}
				mark=document.getElementById("mark"+i);
				userAnswer=getUserAnswer(userAnswers);
				userAnswerStr=userAnswerStr+mapKeyElement.getAttribute("value")+"#;#"+mark.getAttribute("value")+"#;#"+userAnswer+"@;@";
			}
			//alert(userAnswerStr);
			return userAnswerStr;
	}
	
	/*
	*
	*/	
	function checkit(headerName){
		var header=document.getElementById(headerName);
		var head=header.style;
		if (head.display=="none")
			head.display="";
		else
			head.display="none";
		if(head.display!="none") header.focus();
	}
	
	//取试题答案
	function getUserAnswer(userAnswers){
			var answer="";
			if(userAnswers[0].getAttribute("type")=="text"){
				var value;
				if(userAnswers.length==1){
					//单空的情况
					value=userAnswers[0].value;										
					if(value=="")value=" ";
					answer=value;
				}else{
					 //多答案的情况下
					for(var i=0;i<userAnswers.length;i++){
						value=userAnswers[i].value;
						value=trim(value);
						//alert(value);
						if(value=="") value=" ";
						answer=answer+value+"@:@";
					}
				}
			}
			//一对一单选
			if(userAnswers[0].getAttribute("type")=="radio"){
				answer=" ";
				for(var i=0;i<userAnswers.length;i++){					
					//alert("ch"+userAnswers[i].checked);
					if(userAnswers[i].checked==true){
						answer=userAnswers[i].value;
						break;
					}
				}
			}
			//一对一多择
			if(userAnswers[0].getAttribute("type")=="checkbox"){
				answer=" ";
				for(var i=0;i<userAnswers.length;i++){
					if(userAnswers[i].checked==true){
						answer=answer+userAnswers[i].value+"；";
					}
				}
			}
			if(userAnswers[0].tagName=="TEXTAREA"){
			   var value=userAnswers[0].value;
			   if(value=="")value=" ";
			   answer=value;
			}
			
			return answer;
	}
	
	/*
	submit the paper
	*/
	function submitPaper(){
		var nextAct=document.getElementById("nextAct");
		nextAct.setAttribute("value","SubmitPaper");
		var nextPageNum=document.getElementById("nextPageNum");
		  nextPageNum.setAttribute('value',0);
		submitForm();
	}

	/*
	 *利用JQuery的ajax调用功能 
	 *JSON进行数据处理 ，返回的数据更新各个数据项
	 *
	 */
	function submitPaper2(){
		var answerStr=makeAnswerStr();
		//alert(answerStr);
		if($('#currentPageNum0')==null){
			alert("error on page..no currentPageNum!");
			return;
		}
		//alert($('#currentPageNum0').val()+" yy :"+$('#currentPageNum0').name+" xx :"+ff);
		var cuPageNum=parseInt($('#currentPageNum0').val());
		var timeValue=$('#time').val();
		var testPassV=0;
		if($('#testPass')!=null) testPassV=$('#testPass').val();
		$.post(
				'doExam!doParseAnswer.jhtml',
				{
					userAnswers:answerStr,
					currentPageNum:cuPageNum,
					time:timeValue,
					testPass:testPassV
				},
				function(data){
						var data2;
						eval(data);
						
						$(".MarkItemNum").html(data2.MarkItemNum);
						$(".UndoItemNum").html(data2.UndoItemNum);
					}
				);
	
	}

	/*
	 * ajax提交后跳转向主观题打分action
	 */
	function gotoResult(){
		window.location="subjectiveIyScore.jhtml";
	}
	
	/*
	 * ajax提交答案后的暂停
	 */
	function gotoPause(){
		window.location="doExam.jhtml?nextAct=pause";
	}
	
	/*
	*
	*/
	function filterItem(type,mode){
		if(mode!=2) {
			//submitPaper2();
			var nextAct=document.getElementById("nextAct");
			nextAct.setAttribute("value","filter");
			var filterType=document.getElementById("filterType");
			filterType.setAttribute('value',type);
			submitForm();
		}
			//	if(type==2){//错题
			//		var ele=document.getElementById("ErrorItemNum");
			//		var errorInt=parseInt(ele.innerHTML);
			//		if(errorInt<=0){
			//			alert("对不起，没有错题！");
			//			return;
			//			}
			//	}
			//	if(type==3){//未答题
			//		var ele=document.getElementById("UndoItemNum");
			//		var errorInt=parseInt(ele.innerHTML);
			//		if(errorInt<=0){
			//			alert("对不起，没有未答题！");
			//			return;
			//			}
			//	}
			//	if(type==4){//疑问题
			//		var ele=document.getElementById("MarkItemNum");
			//		//alert("a"+ele.innerText);
			//		//alert("b"+ele.innerHTML);
			//		var errorInt=parseInt(ele.innerHTML);
			//		if(errorInt<=0){	
			//			alert("对不起，没有疑问题！");
			//			return;
			//			}
			//	}
		if(mode==2)window.location="doExam.jhtml?nextAct=filter&filterType="+type;

		//alert(nextPageNum.getAttribute('value'));
	}

	function showPage(pageNum){
		var nextAct=document.getElementById("nextAct");
		nextAct.setAttribute('value',"nextPage");
		var nextPageNum=document.getElementById("nextPageNum");
		nextPageNum.setAttribute('value',pageNum);
		var filterType=document.getElementById("filterType");
		filterType.setAttribute('value',0);
		submitForm();
	}
	
	/*
	*just for view
	*/
	function viewPage(pageNum){
		window.location="showExam.jhtml?pageNum="+pageNum;
	}

	function prePage(model){
	  var prePage=document.getElementById("prePageNum").value;
	  var pageNum=document.getElementById("pageNum");
	  pageNum.setAttribute("value",prePage);
	  var nextAct=document.getElementById("nextAct");
	  nextAct.setAttribute('value',"newPage");
	 // pageNum.value=prePage;
	  if(model==1){
			submitForm();
		}
	   if(model==2){
			window.location="showExam.jhtml?pageNum="+prePage+"&nextAct='newPage'";
		}
	}
	
	function nextPage(model){
		var nextPage=document.getElementById("nextPageNum").value;
	 	var pageNum=document.getElementById("pageNum");
		 pageNum.setAttribute("value",nextPage);
		 var nextAct=document.getElementById("nextAct");
		nextAct.setAttribute('value',"newPage");
		if(model==1){
	    	submitForm();
	    }
	    if(model==2){
			window.location="showExam.jhtml?pageNum="+nextPage+"&nextAct='newPage'";
	    }
	}
	
	function overView()	{
		var nextAct=document.getElementById("nextAct");
		nextAct.setAttribute("value","overView");
		submitForm();
	}
	
	function pause(){
		var nextAct=document.getElementById("nextAct");
		//if (nextAct.type=="hidden") {
		// 	cacelPause();checkit('sign01');checkit('sign02');
		// 	window.location="../web/loadSessionVar.jhtml";
		//} else {
			nextAct.setAttribute("value","pause");
			submitForm();
		//}
	}
	
	function submitPaper(){
		var nextAct=document.getElementById("nextAct");
		nextAct.setAttribute("value","submit");
		submitForm();
	}
	
	function submitForm(){
		var form1=document.getElementById("examForm");
		form1.setAttribute("action","doExam.jhtml");
		var userAnswers=document.getElementById("userAnswers");
		if(userAnswers!=null){
			userAnswers.value=makeAnswerStr();
		}
		form1.submit();
	}
	/*
	 * 全卷浏览页面交卷
	 */
	function overViewSubmit(){
		var form1=document.getElementById("examForm");
		form1.submit();
	}

	//控制疑问标记
	function chgmark(obj){
		var img_question_src = "../images/query.gif";
		var img_noquestion_src = "../images/query_c.gif";
		var temp = obj.getAttribute("value");
		if(temp == "0"){			
			obj.setAttribute('value',"1");
			obj.setAttribute('src',img_question_src);					
		}else{			
			obj.setAttribute('value',"0");	
			obj.setAttribute('src',img_noquestion_src);
		}
	}
	
	//暂停时间相关处理
	function setSpanStr(){
		flag=true;
		var timestr=showTime(actureTime+1);
		var spansa=document.getElementById("leftTimeSpanA");
		var spansb=document.getElementById("leftTimeSpanB");
		spansa.innerHTML=timestr;
		spansb.innerHTML=timestr;
	}
	
	//暂停
	function pause(){
		flag=true;
	}
	
	//取消暂停
	function cancelPause(){
		flag=false;
	}
	
	function autoSubmit(){
		flag=true;
		submitPaper2();
		checkit('sign01');
		checkit('sign04');
	}
		
	var timer;
	var flag;
	var actureTime;

	function initTime(isPause,secondCount){
	  flag=isPause;
	  actureTime=secondCount;
	  caTime();
	}
		
	function caTime(){
		  //alert(actureTime);
		  if(flag){
			  timer=window.setTimeout('caTime()',1000);
			  return;
		  }
		  var str=showTime(actureTime);
		  document.getElementById("timeSpan").innerHTML=str;
		  document.getElementById("time").value=actureTime;
		  if(actureTime==0){			 
			  autoSubmit();
		  }
		  actureTime=actureTime-1;
		  if(actureTime>=0){
			  timer=window.setTimeout('caTime()',1000);
		  }
	}
	
	var pauseTimer;
	//在指定时间内暂停
	function pauseTime(remindTime) {
		pauseTimer = window.setTimeout(
			function(){
				 if(remindTime==0) {
					document.getElementById("remindTimeSpan").innerHTML=""; 
					cancelPause();
					window.clearTimeout(pauseTimer);					
					checkit('sign01');
					checkit('sign05');
				} else {
					document.getElementById("remindTimeSpan").innerHTML=showTime(remindTime);
					remindTime=remindTime-1;
					pauseTime(remindTime);
				}
			}
			,1000);
	}
	
	 /*
	 *合适的格式
	 */
	function showTime(sCount){
		  var str="";
		  var second=sCount%60;
		  var min=0;
		  if(second==0) {
			  if(sCount>60) min=sCount/60;		   
		  }else{
			  if(sCount>60) min=(sCount-second)/60;		    
		  }
		 
		  if(min==0) str="00'";		  
		  if(min>0)  str=((min>9)?min:("0"+min) )+"'";
		  
		  if(second==0)  str=str+"00''";		  
		  if(second>0)   str=str+((second>9)?second:("0"+second))+"''"		 
		  return str;	 
	}
	 
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
	
	// 自动提交控件的宽度
	function AutoAdjustWidth(obj) {
		if ($(obj).val()!='') {
			var font_size = Number(($(obj).css("font-size")||"12px").replace("px",""));
			var width = $.trim($(obj).val()).length * font_size;
			$(obj).width(width + 10);
		}
					
		$(obj).keyup(function(){
			if($(this).is("input")||$(this).is("textarea")){
				var font_size = Number(($(this).css("font-size")||"12px").replace("px",""));
				var width = $.trim($(this).val()).length * font_size;
				if (width > 30) $(this).width(width + 10);
			}
		})
	}
	
	function popMathInput(obj) {
		var objevent = obj.onmouseout;
		//obj.onmouseout=null;
		
		var objVal = $(obj).next(".formula").val();
		var mathVal = getFormulator(objVal);
		
		if (!mathVal) {
			window.focus();
			return;
		} else {
			obj.innerHTML = addMForMathML(mathVal);
						
			$(obj).next(".formula").val(mathVal);
				
			$(obj).css("width", "auto");
		}
		
		if (obj.onmouseout==null) {
			obj.onmouseout = objevent;
    	}
		window.focus();
	}
	
	function ReplaceMathInput(obj, showModel) {
		if ((showModel==2) || (showModel==3)) {
			var mathSpan = "<span class='formulaInput' name='mathSpan'>点击输入公式 </span>";
		} else {
			var mathSpan = "<span class='formulaInput' name='mathSpan' v=''  " +
						  "		onmouseover=showblock(this); onmouseout=hideblock(this); " +
						  "		onclick=popMathInput(this); " +
						  " >" +
						  "点击输入公式" +
						  "</span>";
		}
		
		if ($(obj).val()) {
			var repSpan = '<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>' +
						addMForMathML($(obj).val());
			
			mathSpan = mathSpan.replace('点击输入公式', repSpan);
		}
		$(obj).before(mathSpan);
		$(obj).hide();
		
		var spanObj = $(obj).prev(".formulaInput");
		$(spanObj).css("width", "auto");
		if ($(spanObj).width() < 72) $(spanObj).width(72);
	}
	
    function getFormulator(str) {
		showx = event.screenX - event.offsetX - 4 - 10 ; // + deltaX;
		showy = event.screenY - event.offsetY -168; 	 // + deltaY;
		newWINwidth = 210 + 4 + 18;
		var tempStr= str;
		var Args = new Array(window, new String(tempStr+' '));
		var codes = window.showModalDialog("../exam/getMathMl.html", Args, "dialogWidth:530px; dialogHeight:345px; dialogLeft:"+
			showx+"px; dialogTop:"+showy+"px;status:no;help:no;directories:yes;scrollbars:yes;Resizable:no;");
		if(codes==undefined) return;
		return codes;
	}

    function addMForMathML(str) {
    	//防止带前置上下标的公式显示不正常
    	str = str.replace(/<mprescripts\/>/ig, "<mprescripts></mprescripts>");
    	str = str.replace(/<none\/>/ig, "<none></none>");
    	
    	str=str.replace(/</ig,"<m:");
    	str=str.replace(/<m:\//ig,"</m:");
    	
    	return str;
    }
    
    function delMForMathML(str) {
    	str=str.replace(/<m:/ig, "<").replace(/<\/m:/ig, "</");
    	str=str.replace(/>\s*</g, "><");
    	str=str.replace(/.*<math/ig, "<math");
    	str=str.replace(/&amp;/ig, "&");
    	return str;
    }
    
    function $E(){
		if(arguments.length==1)
			return document.getElementById(arguments[0]);
    }
    
    function showblock(area){
		if (trim($(area).text()) == "点击输入公式") {
    		return false;
    	} else {
    		var w = $(area).width();
    		area.v = area.innerHTML;
    		area.innerHTML = "点击输入公式";
    		if (w<72) w = 72;
    		$(area).width(w);
    	}
    }
       
    function hideblock(area) {
    	if (area.v == '') {
    		area.innerHTML = "点击输入公式";
    		$(area).css("width", "72px");
    	} else {
        	area.innerHTML = $(area).attr("v");        	
        	$(area).css("width", "auto");
        	var w = $(area).width();
        	if (w<72) $(area).css("width", "72px");
        }
    }
    
    function compareAnswer(imgObj) { 
    	var inputObj = $(imgObj).prev(".input_text");    	
    	
    	var userAnswerId = $(inputObj).attr("id");
    	var viewAnswerId = userAnswerId.replace("user", "view"); 
    	
    	var userAnswerVal = $(inputObj).val();
    	var viewAnswerVal = $("#" + viewAnswerId).html();
    	viewAnswerVal = delMForMathML(viewAnswerVal);    	
    	    	
    	$("#userAnswer_compare").text(userAnswerVal);
    	$("#viewAnswer_compare").text(viewAnswerVal);
    	
    	var offset = $(imgObj).offset();
    	var signwidth = $("#sign06").height();
    	var signHight = $("#sign06").width();
    	//$("#sign06").css("left", offset.left - signwidth/2);
    	//$("#sign06").css("top",  offset.top  - signHight/2);
    	$("#sign06").css("left", offset.left - 20);
    	$("#sign06").css("top",  offset.top + 20);
    	$("#sign06").show();
    }

	//将题目的信息：题目ID，答案的空序号 放到隐藏表单中去，以备使用加为答案功能
	function setValForCompareAnswer_single_file(imgObj, itemId) {
	    var inputObj = $(imgObj).prev(".input_text");
	    var userAnswerId = $(inputObj).attr("id");
	    var idNs = userAnswerId.substring(userAnswerId.indexOf("_")+1).split("_");
	    if(idNs.length==2) {
	    	$("#itemId_add_L").val($("#mapkey"+idNs[0]).val());
	    	$("#ddAnswerIndex_add_L").val(idNs[1]);
	    	$("#addAnswerContent_add_L").val(inputObj.val());
	    }
	    return ;
	}

	//将题目的信息：题目ID，答案的空序号 放到隐藏表单中去，以备使用加为答案功能
	function setValForCompareAnswer_many_fill(imgObj, itemId) {
	    var inputObj = $(imgObj).prev(".input_text");
	    var userAnswerId = $(inputObj).attr("id");
	    var idNs = userAnswerId.substring(userAnswerId.indexOf("_")+1).split("_");
	    return false;
	    if(idNs.length==2) {
		    var ids = $("#mapkey"+idNs[0]).val().split("::");
		    if(ids.length==2){
		    	$("#itemId_add_L").val(ids[0]);
		    	$("#subItemId_add_L").val(ids[1]);
		    	$("#ddAnswerIndex_add_L").val(idNs[1]);
		    	$("#addAnswerContent_add_L").val(inputObj.val());
		    }
	    }
	    return ;
	}
    
    function changePromptImg(index, promtObj) {
    	var content = $(promtObj).text();
    	if ($.trim(content) == "") {
    		var promptImg = $(".promptImg:eq(" + index + ")");
    		$(promptImg).attr("src", "../images/prompt_ico1.gif");
    		$(promptImg).attr("style", "cursor: none");
    		$(promptImg).attr("onclick", "");
    	}
    }
    
    function changeWidth(width) {
    	if (width==0) return;
		$(".content_box, .content_titsr, .content_titer, .answers").each(
				function () {
					var oldClass = $(this).attr("class");
					var newClass = oldClass + width;
					$(this).addClass(newClass);
					$(this).removeClass(oldClass);
				}
		)
    }
    
    // 提示层的右上角关闭按钮的事件
    $(function(){
    	$("div[v=close]").each(function(){
    		var jit = $(this);
    		$(this).find("img").each(function(){
    			var src = $(this).attr("src");
    			if(src.indexOf("close.gif")>-1){
    				$(this).click(function(){
    					jit.hide();
    				})
    			}
    		})
    	})    	
    })
    