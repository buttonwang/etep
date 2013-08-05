<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script language="javascript1.2">
function goPause(){
         window.location="doExam.jhtml?nextAct=pause";
}
</script>

<!--start div--> 
 
<div class="floatBoxs" id="sign02"  style='z-index:33;display:none;'> 
	<h3>确认交卷</h3> 
	<div class="floatBox">
		<h1>你确定交卷吗？本卷剩余时间<span id="leftTimeSpanA"></span></h1> 
		<h2>本卷共${viewControl.examItemNum}道题，目前<span class="UndoItemNum" id="sign02undo"></span>道题未做；<span class="MarkItemNum" id="sign02mark"></span>道题标记疑问</h2> 
		<div class="content box1"> 
			<ul> 
			<li>点击“确认交卷”将提交本卷，并得到答题的结果。</li> 
   		 	<li>点击“继续答题”将直接转到未做题、疑问题。</li> 
			</ul> 
		</div>
		<div class="btn">		
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign02_button_1" 
				onClick="javascript:this.disabled='true';document.getElementById('sign02_button_2').disabled='true';gotoResult();">确认交卷</button></span>				 
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign02_button_2"
				onClick="javascript:cancelPause();doit(sign01);doit(sign02)">继续答题</button></span>		 
	        <div class="clear"></div> 
		</div>
	</div>
</div>
		
<div class="floatBoxs" id="sign03"  style='z-index:33;display:none;' > 
	<h3>确认暂存退出</h3> 
	<div class="floatBox"> 
		<h1>你确定要暂存退出吗？本卷剩余时间<span id="leftTimeSpanB"></span></h1> 
		<h2>本卷共${viewControl.examItemNum}道题，目前<span class="UndoItemNum" id="sign02undoSize"></span>道题未做；<span class="MarkItemNum" id="sign02mark"></span>道题标记疑问</h2> 
		<div class="content box1">
			<ul> 
				<li>点击“确定”将存储当前答题数据，并退出学习任务。</li> 
	    		<li>点击“取消”将回到答题页面。</li> 
			</ul> 
		</div>
		<div class="btn"> 			
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign03_button_1"  
				onClick="javascript:this.disabled='true';document.getElementById('sign03_button_2').disabled='true';gotoPause();">确认</button></span>			 			
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign03_button_2"
				onClick="javascript:cancelPause();doit(sign01);doit(sign03)">取消</button></span>			
        	<div class="clear"></div>
		</div>
	</div>
</div>
		
<div class="floatBoxs" id="sign04"  style='z-index:33;display:none;'> 
	<h3>确认交卷</h3> 
	<div class="floatBox">
		<h1>本卷规定训练用时（${viewControl.examTimeStr2}）已到，请交卷。</h1> 
		<h2>本卷共${viewControl.examItemNum}道题，目前<span class="UndoItemNum" id="sign04undoSize"></span>道题未做；<span class="MarkItemNum" id="sign04mark"></span>道题标记疑问</h2> 
		<div class="content box1"> 
			<ul> 
				<li>点击“确认交卷”将提交本卷，并得到答题的结果。</li>    		 	
			</ul> 
		</div>
		<div class="btn">		
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign04_button_1" 
				onClick="javascript:this.disabled='true';gotoResult();">确认交卷</button></span>				
        	<div class="clear"></div>
		</div>
	</div>
</div> 
   
<div class="floatBoxs" id="sign05" style='z-index:23; display:none;'> 
	<h3>暂停答题</h3> 
	<div class="floatBox"> 
		<h1>你已暂停答题，倒计时结束将自动恢复答题状态。</h1> 
		<div class="content box1"> 
			<div class="Downtimer"><span id="remindTimeSpan"></span></div> 
		</div>
		<div class="btn">			
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" id="sign05_button_1" 
				onClick="javascript: pauseTime(0);">取消暂停，继续答题</button></span>			
     	 	<div class="clear"></div> 
		</div>
	</div>
</div>

<div class="floatBoxs" id="sign06" style='z-index:23; display:none;'> 
	<h3>比较答案</h3> 
	<div class="floatBox"> 
		<h1>请仔细比较你输入的答案与标准答案的区别。</h1>
		<h2>公式型答案以MathML格式显示，以方便比较。</h2>
		<div>
			你的答案：<textArea id="userAnswer_compare" cols="30" rows="5"></textArea>
			<div class="clear"></div>
			标准答案：<textArea id="viewAnswer_compare" cols="30" rows="5"></textArea>
		</div>
		<div class="btn">		
			<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"  id="sign06_button_1"  
				onClick="javascript:doit(sign06);">关闭</button></span>			
     	 	<div class="clear"></div> 
		</div>
	</div>
</div>

<script language="JavaScript" type="text/javascript">
	$("#sign02_button_1").blur( function () { $("#sign02_button_2").focus() } );
	$("#sign02_button_2").blur( function () { $("#sign02_button_1").focus() } );
	$("#sign03_button_1").blur( function () { $("#sign03_button_2").focus() } );
	$("#sign03_button_2").blur( function () { $("#sign03_button_1").focus() } );	
	$("#sign04_button_1").blur( function () { $("#sign04_button_1").focus() } );
	$("#sign05_button_1").blur( function () { $("#sign05_button_1").focus() } );
	$("#sign06_button_1").blur( function () { $("#sign06_button_1").focus() } ); 
</script>
<!--End div--> 