<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <div id=sign02 style='z-index:32;
        position:absolute;
        left:expression((body.clientWidth-500)/2);
		display:none;' align='center'> 
  <table width="500" height="200" border="0" cellspacing="0">
    <tr> 
      <td align="center" class="comm" style="line-height:40px;"> <p><img src="../images/icon_alert.gif" align="bottom" /> 
          <span style="font-size:18px;font-family:黑体;" >休息一会……本卷剩余时间<span id="leftTimeSpanA"></psan></span><br />
          <font color="#CC3300">本卷共 <font color="#FF0000">${viewControl.examItemNum}</font>道题，目前 <font color="#FF0000"><span name="UndoItemNum" id="sign02undoSize"></span> 
          </font>道题未做；<font color="#FF0000"><span name="MarkItemNum" id="sign02mark"></span> </font>道题标记疑问</font>
        <div style="background-color:#ffffff;border:1px solid #eeeeee;padding:10px;font-weight:normal;text-align:left;"> 
          <strong>应对考场怯场10法</strong><br />
          <br />
          要学会兴奋转移法。怯场现象一旦发生，应立即转移兴奋点和注意力。如抬头向窗外远望，观赏大自然的美景；或用自我暗示法令自己冷静不紧张；或闲目养神，甚至伏案休息片刻等，都是行之有效的可取方法。<br />
         </div>
        <font color="#CC3300"> <br />
        <input type="submit" name="Submit2" id="sign02_button_1"  value=" 继续训练 " onClick="javascript:cacelPause();checkit('sign01');checkit('sign02')" />
        <input type="submit" name="Submit22" id="sign02_button_2" value=" 全卷浏览 " onClick="javascript:overView()" />
        <input type="submit" name="Submit23" value=" 暂存退出 " onClick="javascript:this.disabled='true';document.getElementById('sign02_button_1').disabled='true';document.getElementById('sign02_button_2').disabled='true';gotoPause();" />
        </font></td>
    </tr>
  </table>
</DIV>
		<div id=sign03  style='z-index:33;
        position:absolute;
        left:expression((body.clientWidth-500)/2);
		display:none;' align='center'> 
  <table width="500" height="200" border="0" cellspacing="0">
    <tr> 
      <td align="center" class="comm" style="line-height:40px;"> <p><img src="../images/icon_alert.gif" align="bottom" /> 
          <span style="font-size:18px;font-family:黑体;">你确定要交卷吗？本卷剩余时间<span id="leftTimeSpanB"></span></span><br />
          <font color="#CC3300">本卷共 <font color="#FF0000">${viewControl.examItemNum}</font>道题，目前 <font color="#FF0000"><span name="UndoItemNum" id="sign03undoSize"></span> 
          </font>道题未做；<font color="#FF0000"><span name="MarkItemNum" id="sign03mark"></span> </font>道题标记疑问</font> 
        <div style="background-color:#ffffff;border:1px solid #eeeeee;padding:10px;font-weight:normal;text-align:left;"> 
          <li>点击“继续训练”将直接转到未做题目。<br />
          <li> 点击“确定交卷”将提交本卷，你将得到答题的结果。<br />
          <li>交卷后你还可以重新做本卷中“未做的题”。<br />
          <li>所有错题需要你重新训练直到正确。<br />
         </div>
        <font color="#CC3300"> <br />
        <input type="submit" name="Submit222" value=" 确定交卷 " onClick="javascript:this.disabled='true';document.getElementById('sign03_button_2').disabled='true';gotoResult();" />
        <input type="submit" name="Submit2" id="sign03_button_2" value=" 继续训练 " onClick="javascript:cacelPause();checkit('sign01');checkit('sign03');" />
        </font></td>
    </tr>
  </table>
</DIV>
  		 <div id=sign04  style='z-index:34;
            position:absolute;
            left:expression((body.clientWidth-500)/2);
            display:none;' align='center'> 
      <table width="500" height="200" border="0" cellspacing="0">
        <tr> 
          <td align="center" class="comm" style="line-height:40px;"> <p><img src="../images/icon_alert.gif" align="bottom" /> 
              <span style="font-size:18px;font-family:黑体;">本卷规定训练用时（${viewControl.examTimeStr2}）已到，请交卷。</span><br />
              <font color="#CC3300">本卷共 <font color="#FF0000">${viewControl.examItemNum} </font>道题，目前 <font color="#FF0000"><span name="UndoItemNum" id="sign04undoSize"></span> 
              </font>道题未做；<font color="#FF0000"><span name="MarkItemNum" id="sign04mark"></span></font>道题标记疑问</font> 
            <div style="background-color:#ffffff;border:1px solid #eeeeee;padding:10px;font-weight:normal;text-align:left;"> 
              <li> 请点击“确定交卷”提交本卷，你将得到答题的结果。
               <li> 交卷后你还可以重新做本卷中“未做的题”。所有错题需要你重新训练直到正确。
               <li> 请特别注意答题时间。
              
            </div>
            <font color="#CC3300"> <br />
            <input type="submit" name="Submit222" value=" 确定交卷 " onClick="javascript:this.disabled='true';gotoResult();" />
            </font></td>
        </tr>
      </table>
    </DIV>
<DIV id="sign01" style='z-index:20;position:absolute; 
         top:0px;
         left:0px;
        WIDTH:expression(document.body.scrollWidth); 
        HEIGHT:expression(document.body.scrollHeight);
	background-color:#eeeeee;
	filter:alpha(opacity=70);
	-moz-opacity: 0.7;
	opacity: 0.7;display:none;
       '  align='center'> 
       </div>
<script type="text/javascript">
<!--
var divName;
function getDivInfo(divName){	
	var undoSize=document.getElementById(divName+"undoSize");
	var mark=document.getElementById(divName+"mark");	
	undoSize.innerHTML=getundoNum();
	mark.innerHTML=getMarkNum();	
}
function getMarkNum(){
	var size=document.getElementById("itemSize").value;	
	var num=0;
	for(var i=0;i<size;i++){
		var obj=document.getElementById("mark"+i);
		var disEnable=document.getElementById("disEnable"+i);
		if(disEnable!=null){
			if(obj.getAttribute("value")=="1"&&disEnable.getAttribute("value")!="false")
				num++;
		}else{
			if(obj.getAttribute("value")=="1")
			num++;
		}
	}
	num+=${viewControl.markItemNum -currentPage.markNum};
	return num;
}
function getundoNum(){	
	var size=document.getElementById("itemSize").value;	
	var num=0;
	for(var i=0;i<size;i++){
		var userAnswers=document.getElementsByName("userAnswer"+i);
		var disEnable=document.getElementById("disEnable"+i);		
		if(disEnable!=null){		
			if(getContent(userAnswers)&&disEnable.getAttribute("value")!="false"){
				num++;			
			}
		}else{
			if(getContent(userAnswers))			
				num++;
		}	
	}	
	num=${viewControl.undoItemNum+currentPage.doneNum}-num;
	return num;
}
function getContent(userAnswers){
			var tag=0;
			if(userAnswers[0].getAttribute("type")=="text"||userAnswers[0].getAttribute("type")=="hidden"){
				var value;
				if(userAnswers.length==1){
					//单空的情况
					value=userAnswers[0].getAttribute("value");					
					 if(trim(value)!="")tag=1;										 
				}else{
				 //多答案的情况下
				 for(var i=0;i<userAnswers.length;i++){
					 value=userAnswers[i].getAttribute("value");					 
					 if(value!=null){
					 if(trim(value)!=""){
					 	tag=1;
					 	break;
				 	 	}
				 	 }
				 	}
				}
			}
			if(userAnswers[0].getAttribute("type")=="radio"){				
				for(var i=0;i<userAnswers.length;i++){				
					if(userAnswers[i].getAttribute("checked")==true){										
						tag=1;
					}
				}
			}
			if(userAnswers[0].getAttribute("type")=="checkbox"){				
				for(var i=0;i<userAnswers.length;i++){
					if(userAnswers[i].getAttribute("checked")==true){						
						tag=1;
						break;
					}
				}
			}
			if(userAnswers[0].getAttribute("TagName")=="TEXTAREA"){
				var value=userAnswers[0].getAttribute("value");
			   	if(trim(value)!=""){
			   		tag=1;
			   		}
			}
			return tag;
	}	
function ltrim(s){
	return s.replace(/^\s+/,"");
}

function rtrim(s){
	return s.replace(/\s+$/,"");
}

function trim(s){
	return rtrim(ltrim(s));
}
	-->
</script>