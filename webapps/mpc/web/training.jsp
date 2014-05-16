<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填空题输入练习</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script  type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.1.js"></script>
<script type="text/javascript" src="../js/formulator_test.js"></script>
<script type="text/javascript" src="../js/exam.js"></script>
<script  type="text/javascript">
$(function(){
	AutoHeight("test");//将所有name为test 的input ,textarea 变成自动宽度
	AutoHeight("test","name");//同上
	
	AutoHeight("test1","id");//将所有id为test1 的input ,textarea 变成自动宽度
	AutoHeight("test2","id");//将所有id为test2 的input ,textarea 变成自动宽度
	
	AutoHeight("test1,test2","id");//将所有id为test1,test2 的input ,textarea 变成自动宽度
})
function AutoHeight(nameOrId,type){
	var	arr = (nameOrId||"").split(",");
	if($.trim(arr[0])!=""){
		var typeArr = (type||"name").split(",");
		var setWidth = function(nameOrId){
			var nameOrId = nameOrId||"test";
			var defWidth = this.defaultWidth = "60px";
			this.type = type||"name";
			$("*["+this.type+"="+nameOrId  +"]").keyup(function(){
				if($(this).is("input")||$(this).is("textarea")){
					var font_size = Number(($(this).css("font-size")||"12px").replace("px",""));
					var  width = $.trim($(this).val()).length* font_size; 
					if (width<30) width = 30;
					$(this).width(width);
				}
			})
		}
		try{
			arr = $.trim(nameOrId).split(",");
			if(arr.length>0){
				for(var i in arr){
					setWidth(arr[0],type)
				}
			}
		}catch(e){
		}
	}	
}
	function checkAmbowPlugin()
{
	var obj;
	try {
		f1.GetMathMLStr();		
	}
	catch(e) {
		return 0;
	}
	document.write("<br>");
	try {
		obj = new ActiveXObject("MathPlayer.Factory.1");		
	}
	catch(e) {
		return 0;
	}
	return 1;
}

function doCheck(){
	if(checkAmbowPlugin()){
		doit(formulator_test_2);
		doit(formulator_test_3);
	}else
		doit(formulator_test_1);
}

</script>
<SCRIPT>
//通用技能测评
function playFlash(w,h,url){ 
LeftPosition = (screen.width) ? (screen.width-w)/2 : 0; 
TopPosition = (screen.height) ? (screen.height-h)/2 : 0; 
zz=window.open(url,'','toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'); 
zz.resizeTo(w,h); 
zz.moveTo(LeftPosition,TopPosition); 
} 
</script>
	
</head>

<body>
<div id="header" class="wm950">
  <div class="top_l"></div>
  <div class="top_c" style="text-align:center">
   <span class="f20px fonth cWhite" >填空题输入练习</span>&nbsp;&nbsp;&nbsp;
  </div>
  <div class="top_r"></div>
  <div class="clear"></div>
</div>
<div id="contentLayout" class="wm950">
 	<!--Satr left-->
  <div class="wm950">
  		<div class="content_bg">
    	<div class=ye_r_t>
		<div class=ye_l_t>
		<div class=ye_r_b>
		<div class=ye_l_b>
          <div class="content">
         <!--stars div2-->
<div class="floatBoxsx" id=sign02  style='z-index:20;display:xxx;'>

		<div class="floatBox">
		<div class="title_h1 cOra">欢迎进入填空题输入练习！</div>
		<div class="lianxi_ts" id="formulator_test_1"  style="display:none">还没有安装数理化学习环境！    <a class="cOra" href="../../download/ambow_ete_setup.exe"><img src="../images/down_ico.gif" width="14" height="16" align="absmiddle" />下载安装</a> &nbsp;&nbsp;&nbsp;<a class="cOra" href="mainPage!mpc.jhtml">检查是否安装成功</a></div>
		<div class="content box1"  id="formulator_test_2"  style="display:none">
		<ul>
			<li><strong>注：</strong><br />1.部分试题答案包含公式答案，需用公式编辑器录入，如果你看到<img src="../images/shuru.gif" width="88" height="25" border="0" align="absmiddle" /> ，请点击它，在弹出的编辑器中输入答案。
            </li>
            <li>2.点击<img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" align="absmiddle" />可查看动画演示。</li>
		</ul>
		</div>
		<div id="formulator_test_3"  style="display:none" class="lianxi1" onmouseout="this.className='lianxi1';" onmouseover="this.className='lianxi2';">
			<a href="javascript:void(null)" onclick="javascript:doit(sign02);doit(sign03)">
				<span>开始练习</span>
			</a>
        </div>
		 <OBJECT CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
		    <PARAM NAME="LPKPath" VALUE="../exam/fmlaxc.lpk">
		    <EMBED SRC = "../exam/fmlaxc.lpk">
		</OBJECT>
		<OBJECT classid="clsid:737B43F8-5A87-4014-8C6D-C7DB1A99360C" id="f1" height="90%" width="100%" style="display:none">
			<PARAM NAME="StatusBar" VALUE="0">
		</OBJECT>
		<script>
			doCheck();
		</script>
		</div></div>
          <!--End div-->
          
          <!--stars div3-->
		<div class="floatBoxsx" id=sign03  style='z-index:30;display:none;' > 
		<h3>填空练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第1题</div>
		<div class="content box1">
		<P>杆AC受的是<input class="input_text2 " id="question_1_input_1" name="test" type="text" />(“压力“或是“拉力”)，BC上受的是
		<input class="input_text2 " id="question_1_input_2" name="test" type="text" />(“压力“或是“拉力”)，受力大小分别是AC
		<input class="input_text2 " id="question_1_input_3" name="test" type="text" />N，
		BC<input class="input_text2 " id="question_1_input_4" name="test" type="text" />N。</P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong>压力；拉力；15；9</p>
        <div class="blank6"></div>
        <div class="lianxi_box">
        <span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span><strong>
		输入技巧：</strong><br />通过键盘直接输入答案，按 <img src="../images/tab.gif" width="50" height="28" />键在填空间快速前进，按<img src="../images/shift.gif" width="50" height="28" />+<img src="../images/tab.gif" alt="" width="50" height="28" />键在填空间快速后退
            <div class="clear"></div>
        </div>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign04);doit(sign03);doQuestion1();">提交</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
        
        <!--stars div4-->
		<div class="floatBoxsx" id=sign04  style='z-index:40;display:none;' > 
		<h3>填空练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第1题</div>
		<div class="content box1">
		<P>杆AC受的是<input id="question_1_answer_1" name="test" type="text" class="input_text2 " />
		  <span id="question_1_image_1"></span>(“压力“或是“拉力”)，BC上受的是<input id="question_1_answer_2" name="test" type="text" class="input_text2 " />
		  <span id="question_1_image_2"></span>(“压力“或是“拉力”)，受力大小分别是AC受是<input id="question_1_answer_3" name="test" type="text" class="input_text2 " />
		  <span id="question_1_image_3"></span>N，BC上受是<input id="question_1_answer_4" name="test" type="text" class="input_text2 "/>
		  <span id="question_1_image_4"></span>N。</P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong>压力；拉力；15；9</p>
        <span id="question_1_result"></span>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign05);doit(sign04)">下一题</button></span>
        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign03);doit(sign04)">重练</button></span>
         <div class="clear"></div>
		<div class="clear"></div>
		</div></div></div>
          <!--End div-->
        
        <!--stars div5-->
        <div class="floatBoxsx" id=sign05  style='z-index:50;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第2题</div>
		<div class="content box1">
		<P>此物质是<span class='formulaInput' name='mathSpan' v='' onmouseover=showblock(this); onmouseout=hideblock(this); onclick=getMathInput(this); >点击输入公式</span><input id="question_2_input_1" name="test" type="text" class="formula" style="display:none"/></P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong><img src="../images/img/image011.png" width="24" height="12" /></p>
        <div class="blank6"></div>
        <div class="lianxi_box">
        <span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span><strong>
		输入技巧：</strong><br />
		输入<img src="../images/img/image011.png" alt="" width="24" height="12" />这样的、带下标的分子式，<span class="cRed">请先切换到英文输入法！ </span></p>
        <p><strong>方法1<a href="#" onclick="playFlash(480,352,'../flv/replay_2_1.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>：</strong>按快捷键Ctrl+L(对应<img src="../images/image012.png" width="32" height="32" />)，输入H，按键盘上<img src="../images/right_jp.gif" width="50" height="28" />，输入2，再按<img src="../images/right_jp.gif" width="50" height="28" />，输入O。<span class="cOra">（<strong>推荐</strong>）</span></p><br />
        <div class="blank6"></div>
         <p> <strong>方法2<a href="#" onclick="playFlash(480,352,'../flv/replay_2_2.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>：</strong>点
           <img src="../images/image014.png" width="24" height="24" />选<img src="../images/image012.png" alt="" width="32" height="32" />，输入H，按键盘上<img src="../images/right_jp.gif" alt="" width="50" height="28" />，输入2，按键盘上<img src="../images/right_jp.gif" alt="" width="50" height="28" />，输入O。</p> 
          <div class="blank6"></div>
         <p> <strong>方法3<a href="#" onclick="playFlash(480,352,'../flv/replay_2_3.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>：</strong>输入H，选定H，点<img src="../images/image014.png" width="24" height="24" />选<img src="../images/image012.png" alt="" width="32" height="32" />，输入2，按键盘<img src="../images/right_jp.gif" alt="" width="50" height="28" />，输入O。</p> 
            <div class="clear"></div>
        </div>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign06);doit(sign05);doQuestion2();">提交</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
         <!--stars div6-->
		<div class="floatBoxsx" id=sign06  style='z-index:60;display:none;' > 
		<h3>填空练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第2题</div>
		<div class="content box1">
		<P>此物质是<span  class='input_text formula' id="question_2_answer_1"></span>
		  <span id="question_2_image_1"></span></P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong><img src="../images/img/image011.png" width="24" height="12" /></p>
        <div class="blank6"></div>
         <span id="question_2_result"></span>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign07);doit(sign06)">下一题</button></span>
        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign05);doit(sign06)">重练</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
        <!--stars div7-->
        <div class="floatBoxsx" id=sign07  style='z-index:70;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第3题</div>
		<div class="content box1">
		<P>此函数表达式为<span class='formulaInput' name='mathSpan' v='' onmouseover=showblock(this); onmouseout=hideblock(this); onclick=getMathInput(this); >点击输入公式</span><input id="question_3_input_1" name="test" type="text" class="formula"  style="display:none"/></P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong><img src="../images/img/image017.png"/></p>
        <div class="blank6"></div>
        <div class="lianxi_box">
        <span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span><strong>
		输入技巧：</strong><br />
		输入<img src="../images/img/image018.png" alt="" width="14" height="13" />这样的、带上标的分子式，<span class="cRed">请先切换到英文输入法！ </span></p>
        <p><strong>方法1<a href="#" onclick="playFlash(480,352,'../flv/replay_3_1.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>：</strong>输入y=，按快捷键Ctrl+H(对应<img src="../images/image019.png" width="32" height="32" />)，输入x，按键盘上<img src="../images/right_jp.gif" width="50" height="28" />，输入2，再按<img src="../images/right_jp.gif" width="50" height="28" />输入-3x+1。<span class="cOra">（<strong>推荐</strong>）</span></p>
        <div class="blank6"></div>
         <p> <strong>方法2<a href="#" onclick="playFlash(480,352,'../flv/replay_3_2.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>：</strong>输入y=，点<img src="../images/image014.png" width="24" height="24" />选<img src="../images/image019.png" alt="" width="32" height="32" />，输入x，按键盘上<img src="../images/right_jp.gif" alt="" width="50" height="28" />，输入2，再按<img src="../images/right_jp.gif" alt="" width="50" height="28" />，输入-3x+1。</p> 
          <div class="blank6"></div>
         <p> <strong>方法3<a href="#" onclick="playFlash(480,352,'../flv/replay_3_3.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>：</strong>输入y=x，选定x，点<img src="../images/image014.png" width="24" height="24" />选<img src="../images/image019.png" alt="" width="32" height="32" />，输入2，按键盘上<img src="../images/right_jp.gif" alt="" width="50" height="28" />，输入-3x+1。</p> 
            <div class="clear"></div>
        </div>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign08);doit(sign07);doQuestion3();">提交</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
         <!--stars div8-->
		<div class="floatBoxsx" id=sign08  style='z-index:80;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第3题</div>
		<div class="content box1">
		<P>此函数表达式为<span  class='input_text formula' id="question_3_answer_1"></span>
		  <span id="question_3_image_1"></span>
		  <div class="blank6"></div>
         <p><strong>正确答案：</strong><img src="../images/img/image017.png"/></p>
        <div class="blank6"></div>
	        <span id="question_3_result"></span>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign09);doit(sign08)">下一题</button></span>
        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign07);doit(sign08)">重练</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
           <!--stars div9-->
        <div class="floatBoxsx" id=sign09  style='z-index:90;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第4题</div>
		<div class="content box1">
		<P>此碱是<span class='formulaInput' name='mathSpan' v='' onmouseover=showblock(this); onmouseout=hideblock(this); onclick=getMathInput(this); >点击输入公式</span><input id="question_4_input_1" name="test" type="text" class="formula" style="display:none"/></P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong><img src="../images/img/image022.png"/></p>
        <div class="blank6"></div>
        <div class="lianxi_box">
        <span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span><strong>
		输入技巧：</strong>
        <p>上下标只限定左侧紧邻的<strong>那一个</strong>字符，如本题中的下标2仅限定“)”。输入大写M、小写g、英文左括号 (、大写O、大写H，按快捷键Ctrl+L，输入英文右括号)，按键盘上<img src="../images/right_jp.gif" width="50" height="28" />，输入2。
        <br> <br>
        <strong>动画演示:</strong><a href="#" onclick="playFlash(480,352,'../flv/replay_4_1.html')"><img src="../images/play_ico.gif" alt="播放动画演示" width="16" height="16" border="0" /></a>
        </p>
            <div class="clear"></div>
        </div>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign10);doit(sign09);doQuestion4();">提交</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
         <!--stars div10-->
		<div class="floatBoxsx" id=sign10  style='z-index:100;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第4题</div>
		<div class="content box1">
		<P>此碱是<span  class='input_text formula' id="question_4_answer_1"></span>
		  <span id="question_4_image_1"></span>
		  <div class="blank6"></div>
         <p><strong>正确答案：</strong><img src="../images/img/image022.png"/></p>
        <div class="blank6"></div>
      	 <span id="question_4_result"></span>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign11);doit(sign10)">下一题</button></span>
        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign09);doit(sign10)">重练</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
             <!--stars div11-->
        <div class="floatBoxsx" id=sign11  style='z-index:110;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第5题</div>
		<div class="content box1">
		<P>这是一个<input class="input_text2 " id="question_5_input_1" name="test" type="text" />反应，实验现象为溶液逐渐变<input class="input_text2 " id="question_5_input_2" name="test" type="text" />，有此<input class="input_text2 " id="question_5_input_3" name="test" type="text" />气体逸出。化学反应方程式为<span class='formulaInput' name='mathSpan' v='' onmouseover=showblock(this); onmouseout=hideblock(this); onclick=getMathInput(this); >点击输入公式</span><input id="question_5_input_4" name="test" type="text" class="formula" style="display:none"/> </P>
        <div class="blank6"></div>
        <p><strong>正确答案：</strong>氧化还原；蓝；红棕色；<img src="../images/img/image025.png"/></p>
        <div class="blank6"></div>
        <div class="lianxi_box">
        <span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span><strong>
		输入技巧：</strong>
        请使用<img src="../images/tab.gif" width="50" height="28" />键、快捷键Ctrl+L，用鼠标点选<img src="../images/image026.png" />中的<img src="../images/image027.png" alt="" />、<img src="../images/image028.png" alt="" />中的<img src="../images/top_jp.gif" alt="" />等完成输入过程。</p>
            <div class="clear"></div>
        </div>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign12);doit(sign11);doQuestion5();">提交</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
         <!--stars div12-->
		<div class="floatBoxsx" id=sign12  style='z-index:120;display:none;' > 
		<h3>公式输入练习</h3>
		<div class="floatBox">
		<div class="title_h1 cOra">第5题</div>
		<div class="content box1">
		<P>这是一个<input id="question_5_answer_1" name="test" type="text" class="input_text2 " /><span id="question_5_image_1"></span>反应，实验现象为溶液逐渐变<input id="question_5_answer_2" name="test" type="text" class="input_text2 " /><span id="question_5_image_2"></span>，有此<input id="question_5_answer_3" name="test" type="text" class="input_text2 " /><span id="question_5_image_3"></span>气体逸出。化学反应方程式为<span  class='input_text formula' id="question_5_answer_4"></span>
		  <span id="question_5_image_4"></span></P><div class="blank6"></div>
        <p><strong>正确答案：</strong>氧化还原；蓝；红棕色；<img src="../images/img/image025.png"/></p>
        <div class="blank6"></div>
	        <span id="question_5_result"></span>
		</div>
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign13);doit(sign12)">结束练习</button></span>
        <span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:doit(sign11);doit(sign12)">重练</button></span>
         <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          
           <!--stars div13-->
		<div class="floatBoxsx" id=sign13  style='z-index:130;display:none;' > 
		<h3>完成练习</h3>
		<div class="floatBox">
        <div class="lianxi_sm3"></div>
		<div class="title_h1 cOra">恭喜，你已掌握输入填空题答案的技巧！</div>
		<div class="content box1">
		<span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span>输入公式前，记得把输入法切换到英文状态。
        <div class="blank6"></div>
        <P><span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span>按<img src="../images/tab.gif" />键，在填空间快速前进；按<img src="../images/shift.gif" alt="" />+<img src="../images/tab.gif" alt="" />键在填空间快速后退。</P>
        <div class="blank6"></div>
        <P><span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span>上下标仅限定左侧紧邻的那一个字符<img src="../images/img/image032.png" />，一定要牢记！</P>
        <div class="blank6"></div>
        <P><span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span>在公式编辑器里，按快捷键能大大提高输入速度，最常用组合有<img src="../images/image019.png" />(Ctrl+H)、<img src="../images/image012.png" />(Ctrl+L)、<img src="../images/image034.png" />(Ctrl+J)等。</P>
        <div class="blank6"></div>
       <P> <span class="ui-icon-info" style="float: left; margin-right: 0.3em;"/></span><strong>注：</strong>进入学习首页后，可在右上角“我的工具箱”查看更多技巧。
        </P>
        <div class="blank6"></div>
		</div>
        <div class=" clear"></div>
		
		<div class="btn">
		<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onClick="javascript:window.close();">关闭页面</button></span>
        
			 <div class="clear"></div>
		</div></div></div>
          <!--End div-->
          <div class="clear"></div>
        </div>
        </div></div></div></div></div>
  </div>
  <!--End left-->
  <div class="clear"></div>
</div>
<div id="footde" class="wm950"><span>Copyright © 2013</span>  <span>版权所有</span></div>
	
<script language="JavaScript" src="../js/ocscript.js" type="text/javascript"></script>
</body>
</html>
