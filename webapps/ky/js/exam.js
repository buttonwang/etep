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
				if(userAnswers==null){
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
		if(head.display!="none")header.focus();
	}
//TODO: remove all the ; in the text field		
function getUserAnswer(userAnswers){
			var answer="";
			if(userAnswers[0].getAttribute("type")=="text"){
				var value;
				if(userAnswers.length==1){
					//单空的情况
					value=userAnswers[0].value;
					//value=trim(value);
					
					 if(value=="")value=" ";
					 answer=value;
				}else{
				 //多答案的情况下
				 for(var i=0;i<userAnswers.length;i++){
					 value=userAnswers[i].value;
					 value=trim(value);
					 //alert(value);
					 if(value=="")value=" ";
					 answer=answer+value+"@:@";
				 }
				}
			}
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
			if(userAnswers[0].getAttribute("type")=="checkbox"){
				answer=" ";
				for(var i=0;i<userAnswers.length;i++){
					if(userAnswers[i].checked==true){
						answer=answer+userAnswers[i].value+"@:@";
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
				'../exam/doExam!doParseAnswer.jhtml',
				{
					userAnswers:answerStr,
					currentPageNum:cuPageNum,
					time:timeValue,
					testPass:testPassV
					},
				function(data){
						//alert('test for ok!'+data);
						//$(data).find("mydata").each(function(){alert($(this).text())});
						var data2;
						eval(data);
						$.each(data2,function(i,n){
							//alert("name:"+i+" value:"+n+"  obj:"+$("span[name="+i+"]"));
							//$("span[name="+i+"]").each(this.html(n));
							$("span[name="+i+"]").html("<span>"+n+"</span>");//在此加Span为了0不显示的问题 =.=
							});
						
					}
				);
	
}

/*
 * ajax提交后跳转向主观题打分action
 */
function gotoResult(){
	window.location="../exam/subjectiveIyScore.jhtml";
}
/*
 * ajax提交答案后的暂停
 */
function gotoPause(){
	window.location="../exam/doExam.jhtml?nextAct=pause";
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
	if(mode==2)window.location="../exam/doExam.jhtml?nextAct=filter&filterType="+type;

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
	window.location="../exam/showExam.jhtml?pageNum="+pageNum;
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
			window.location="../exam/showExam.jhtml?pageNum="+prePage+"&nextAct='newPage'";
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
			window.location="../exam/showExam.jhtml?pageNum="+nextPage+"&nextAct='newPage'";
	    }
	}
	
	function overView(){
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
			form1.setAttribute("action","../exam/doExam.jhtml");
			//var questionType=document.getElementById("questionType");
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
//控制疑问标记
	function chgmark(obj){
		var img_question_src = "../images/icon_question.gif";
		var img_noquestion_src = "../images/icon_noquestion.gif";
		var temp = obj.getAttribute("value");
		if(temp == "0"){
			
			obj.setAttribute('value',"1");
			obj.setAttribute('src',img_question_src);			
			//obj.src = img_question_src;
		}else{
			
			obj.setAttribute('value',"0");	
			obj.setAttribute('src',img_noquestion_src);		
			//obj.src = img_noquestion_src;
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

//取消暂停
function cacelPause(){
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
  //auto submit
  autoSubmit();
    //doit(sign01);
	//doit(sign04);
	//sign04.focus();
	//getDivInfo('sign04');
	//doit(AdLayer);
  }
  actureTime=actureTime-1;
  if(actureTime>=0){
   timer=window.setTimeout('caTime()',1000);
  }
 }
 /*
 *合适的格式
 */
 function showTime(sCount){
  var str="";
  var second=sCount%60;
  var min=0;
  if(second==0){
   if(sCount>60){
    min=sCount/60;
   }
  }else{
   if(sCount>60){
    min=(sCount-second)/60;
    
   }
  }
 
  if(min==0){
   str="00'";
  }
  if(min>0){
   str=((min>9)?min:("0"+min) )+"'";
   
  }
  if(second==0){
   str=str+"00''";
  }
  if(second>0){
   str=str+((second>9)?second:("0"+second))+"''"
  }
  return str;
 
 }

