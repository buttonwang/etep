<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/index.css" rel="stylesheet" type="text/css" />
<link href="../css/calendar-blue.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../js/jquery.jcarousel.css" />
<link rel="stylesheet" type="text/css" href="../css/skin.css" />
<link type="text/css" href="../css/ui.all.css" rel="stylesheet" />
<link href="../css/pop.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../css/demos.css" media="all" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../js/jquery-1.3.1.js"></script>
<script type="text/javascript" src="../js/ui.core.js"></script>
<script type="text/javascript" src="../js/ui.datepicker.js"></script>
<script type="text/javascript" src="../js/jquery.jcarousel.js"></script>
<link rel="icon" href="../../favicon.ico" type="image/x-icon" />

<STYLE type="text/css">
.btn_container { width: 40px; height: 65px; display: table;margin:11px 4px;}
.btn_pos { display: table-cell; vertical-align: middle;}
.btn_content{ text-align:center;}
.btn_content a{display:block;width:14px; padding:10px 13px;margin:auto;}
.nowarpbox li{width:260px; white-space:nowrap; text-overflow:ellipsis; overflow: hidden;}
</STYLE>
<!--[if IE]>

	<STYLE type="text/css">

	.btn_container { position: relative; }
	.btn_pos { position: absolute; top: 50%; }
	.btn_content { position: relative; top: -50%;text-align:center;}

	</STYLE>

<![endif]-->
<style type="text/css">

/**
 * Additional styles for the controls.
 */
.jcarousel-control {
    margin-bottom: 10px;
    text-align: center;
}

.jcarousel-control a {
    font-size: 75%;
    text-decoration: none;
    padding: 0 5px;
    margin: 0 0 5px 0;
    border: 1px solid #fff;
    color: #eee;
    background-color: #4088b8;
    font-weight: bold;
}

.jcarousel-control a:focus,
.jcarousel-control a:active {
    outline: none;
}

.jcarousel-scroll {
    margin-top: 10px;
    text-align: center;
}

.jcarousel-scroll form {
    margin: 0;
    padding: 0;
}

.jcarousel-scroll select {
    font-size: 75%;
}

#mycarousel-next,
#mycarousel-prev {
    cursor: pointer;
    margin-bottom: -10px;
    text-decoration: underline;
    font-size: 11px;
}

</style>

<SCRIPT language="javascript" src="../js/floating_new.js" type="text/javascript"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT type="text/javascript">

function consolidate_training(page){
		try{
			doit(sign01);
			doit(sign00);
			FloatingDIVWithID('sign00');
		}catch(e){
			alert(e.toString())		
		}
}
function FloatDiv(divId,left,top){
	setInterval("FloatDiv_keep('"+divId+"',"+left+","+top+")",10);
}
function FloatDiv_keep(divId,left,top) {
	var div=document.getElementById(divId);
	div.style.position='absolute';
	div.style.left=(left||0+document.body.scrollLeft).toString()+'px';
	div.style.top=(top||0+document.body.scrollTop).toString()+'px';
}
</SCRIPT>
<script type="text/javascript">
function stopBubble(e) {
	if (  e.stopPropagation  ){
		e.stopPropagation();
	}else{
		window.event.cancelBubble = true;		 
	}
}
function stopDefault( e ) {
	if ( e && e.preventDefault )
		e.preventDefault();
	else 
		window.event.returnValue = false;
	return false;
}

function stopBD(e,bubble ,def){
	if(bubble!=undefined){
		stopBubble(e)
	}
	if(def!=undefined){
		stopDefault(e);
	}
}

$(function() {
	$("#datepicker").datepicker().click(function(e){
	 	stopBD(e,1 ,2);
	});
	$("#showTimeDiv").click(function(){
	 	$("#setTimeDiv").show();
	})
	$("#setTimeDiv").click(function(){
		$(this).hide()
	});
});

function updateFormulatorTest(){
	$.ajax({
			type: "get",
			url: "mainPage!updateFormulatorTest.jhtml",
			beforeSend: function(XMLHttpRequest){
				//ShowLoading();
			},
			success: function(data, textStatus){
				//
			},
			complete: function(XMLHttpRequest, textStatus){
				//HideLoading();
			},
			error: function(){
				//请求出错处理
			}
	});
	 doit(sign01);doit(sign02);

}
	</script>
</head>
<body>
<!--stars tan div-->
        <div class="floatBoxs" id=sign00  style='z-index:33;display:none;'>
		<h3></h3>
		<div class="floatBox">
		<h1>你尚未开始答题，暂时不能查看</h1>
		<div class="btn">
		<span class="gbs1">
		<button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
			onClick="javascript:doit(sign01);doit(sign00)" >取消</button></span>
         <div class="clear"></div>
		</div></div></div>
       <div id=sign01 style='z-index:20;position:absolute; 
		left:0;
        top:0;
        *+WIDTH:100%;
        *_WIDTH:expression((screen.availwidth-27)+"px"); 
        HEIGHT:expression(document.body.scrollHeight<700?700:document.body.scrollHeight);
	background-color:#000;
	filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;
    display:none;'  
    align='center'>
    <iframe style="position:absolute;top:0px; left:0px; width:100%; height:expression(document.body.scrollHeight);
    	 z-index:-1; background-color:#000;filter:alpha(opacity=30);
	-moz-opacity: 0.3;
	opacity: 0.3;"></iframe></div>
	
	<%-- <c:if test="${1 eq 1}">
		<jsp:include page="formulator_training.jsp"></jsp:include>
	</c:if>	 --%>
<!--End tan div-->
<div id="wraps">
<!--start top-->
  <div id="heder">
   	  <div class="logo"> <img src="../images/${userDataVO.logo}" /></div>
  <div class="top_con">
      	<div class="logo_l">
		<span>${userDataVO.processName}</span>
        </div>           
		<div id="menu" class="imrcmain0 imgl">
		<div class="imcm imde" id="imouter0">
		<ul id="imenus0">
		<li style="width:70px;"></li>
		<li class="imatm" style="width:85px;*_width:90px;">
			<a href="#"><span class="imea imeam"><span></span></span>我的工具箱</a>
			<div class="imsc">
			<div class="imsubc" style="width:145px;top:0px;left:0px;">
			<div class="imunder"></div><div></div>
			<ul style="">
			<li><a href="../message/message!list.jhtml?source=-1">我的消息</a></li>
			<li><a href="../point/myPointAction.jhtml">我的积分</a></li>
			<!-- <li><a href="../studyguide/showStudyGuideAction.jhtml">学习指导红宝书</a></li> -->
		    <!-- <li><a href="../bug/bug!listDifferentItemBugInfoHistoryAnswerStatus.jhtml?b.bugInfo.status=1">捉虫记录</a></li> -->
		    <li><a href="../attention/myAttention.jhtml">我关注的试题</a></li>   
			<!--<li><a href="../exam/setpage.jhtml">更改答题版式</a></li>-->
			<!-- <li><a href="../web/training.jsp" target="_blank">填空题输入练习</a></li>
			<li><a href="../../download/ambow_ete_setup.exe">学习环境软件下载</a></li> -->
			</ul>
			</div>
			</div>
		</li>
		<!--<li style="width:45px;"><a href="#">帮助</a>	</li>-->
		<li style="width:70px;"><a href="#" onclick="if(confirm('是否确认退出系统?')){location.href='../../logout.jsp';}">退出系统</a>
		</li>
		</ul><div class="imclear">&nbsp;</div></div></div>
      
      <div class="clear"></div>
      <h2>
      	欢迎<span>${webuser.realName}</span>，你已登录${userDataVO.trainingTimes}次
      	<c:if test="${userDataVO.lastTrainingTime ne 'null'}">，上次登录时间是${userDataVO.lastTrainingTime}</c:if>。
      </h2>
      </div>
  </div><!--End top-->
  <!--start content-->
    	<div class="container">
    	
    	 <c:if test="${userDataVO.processStatus>-1}"> 
        	<div id="con_left" class="left">
             <c:if test="${userDataVO.processStatus==0}">  
            	<div class="study_box">
                	<div class="study_l"></div>
                    <div class="study_c">
                    	<div class="study_c_tilte">
                        <h1>当前学习任务：</h1>
                        <h2><span>${userDataVO.unitNodeName}</span>&nbsp;&nbsp;&nbsp;&nbsp;${userDataVO.currentNodeName}</h2>
                        <h1>本训练 ${userDataVO.currentPassNum} 人通过，${userDataVO.currentTestingNum}人正在训练</h1>
                        </div>
                        <div class="study_c_c">
                        <div class="btn_study1" onmouseout="this.className='btn_study1';" onmouseover="this.className='btn_study2';"
                             onclick="location.href='../exam/goExam.jhtml?nodeInstanceId=${userDataVO.currentNodeInstanceId}'">
                             <a href="#"><span>开始学习</span></a>
                        </div>
                       <div class="btn_review1" onmouseout="this.className='btn_review1';" onmouseover="this.className='btn_review2';">
					   <c:if test="${nodeCount >0}">
					   <a href="../report/reportMain.jhtml" ><span>弱项强化</span></a>
					   </c:if>
					   <c:if test="${nodeCount ==0}">
					   <a href="#" onclick="consolidate_training();"><span>弱项强化</span></a>
					   </c:if>
					   </div>
                       <div class="blank3"></div>
                       <h1 class="left"><img src="../images/View_ico.gif" align="absmiddle" />
                       		<a class="cWhite" href="studyTask.jhtml">查看全部学习任务</a>
                       </h1>
                       <h2 class="left"><img src="../images/star_ico.gif" width="12" height="12" align="absmiddle" />
                       <c:if test="${nodeCount >0}">
					   <a class="cWhite" href="starPaper.jhtml"><span>查看我的星卷榜</span></a>
					   </c:if>
					   <c:if test="${nodeCount ==0}">
					   <a href="#" onclick="consolidate_training();"  class="cWhite"><span>查看我的星卷榜</span></a>
					   </c:if>
                       </h2>
                      </div>
                    </div>
                    <div class="study_r"></div>
                </div>
               </c:if>
                <c:if test="${userDataVO.processStatus==1}">  
            	<div class="study_box">
                	<div class="study_l"></div>
                    <div class="study_c">
                    	<div class="study_c_tilte">
                        <h1>上次从</h1>
                        <h2><span>${pauseInfoVO.titleInfo}</span></h2>
                        <h1>${pauseInfoVO.pauseType}中暂存退出,已做${pauseInfoVO.finishedItemsNum}题，已用${pauseInfoVO.consumeTime}</h1>
                        </div>
                        <div class="study_c_c">
                        <div class="btn_study1" onmouseout="this.className='btn_study1';" onmouseover="this.className='btn_study2';"
                             onclick="location.href='../exam/goExam.jhtml?nodeInstanceId=${pauseInfoVO.nodeInstanceId}&isPause=1'">
                             <a href="#"><span>开始学习</span></a>
                        </div>
                       <div class="btn_review1" onmouseout="this.className='btn_review1';" onmouseover="this.className='btn_review2';">
					   <c:if test="${nodeCount >0}">
					   <a href="../report/reportMain.jhtml" ><span>弱项强化</span></a>
					   </c:if>
					   <c:if test="${nodeCount ==0}">
					   <a href="#" onclick="consolidate_training();"><span>弱项强化</span></a>
					   </c:if>
					   </div>
                       <div class="blank3"></div>
                       <h1 class="left">
                       		<img src="../images/View_ico.gif" align="absmiddle" />
                       		<a class="cWhite" href="studyTask.jhtml">查看全部学习任务</a>
                       	</h1>
                       <h2 class="left">
                       		<img src="../images/star_ico.gif" width="12" height="12" align="absmiddle" />
                       		<a class="cWhite" href="starPaper.jhtml">查看我的星卷榜</a>
                       	</h2>
                      </div>
                    </div>
                    <div class="study_r"></div>
                </div>
               </c:if> 
             
                <div class="blank10"></div>
                
				<iframe width="605" height="150" name="flow_box_frame" id="flow_box_frame" src="flowBox.jhtml" 
					scrolling="no" frameborder="0" ></iframe>
                
            </div>
          
          </c:if>
          
<c:if test="${userDataVO.processStatus==-1}">   
          
 		<div id="con_left" class="left">
          
            	<div class="study_box">
                	<div class="study_l"></div>
                    <div class="study_c">
                    <c:if test="${pauseInfoVO==null}"> 
                    	<div class="study_c_tilte_f">
                        <ul>
                        	<li class="img left"><img src="../images/emoticon.gif" /></li>
                            <li class="left"><h2>祝贺你已完成所有学习任务！</h2></li>
                        </ul>
                        <div class="clear"></div>
                        <h1>建议继续训练“我的星卷榜”或进行“弱项强化”，提高掌握度。</h1>
                        </div>
                    </c:if>
                     <c:if test="${pauseInfoVO!=null}"> 
	                    <div class="study_c_tilte_f2">
	                        <h1>上次从</h1>
	                        <h2>${pauseInfoVO.titleInfo}</h2>
	                        <h1>暂存退出，已做${pauseInfoVO.finishedItemsNum}题，已用${pauseInfoVO.consumeTime}</h1>
	                        </div>
                    </c:if> 
                        
                        <div class="study_c_c_f">
                       <div class="btn_review_21" onmouseout="this.className='btn_review_21';" 
                       	onmouseover="this.className='btn_review_22';"><a href="../report/reportMain.jhtml" >
                       	<span>弱项强化</span></a></div>
                      </div>
                    </div>
                    <div class="study_r"></div>
                </div>
              
                <div class="blank10"></div>
                
                <div class="flow_box">
                  <div class="flow_box_tit">
                    <h1><b>我的星卷榜</b><span>（掌握度越低，越需要训练）</span></h1>
                    <h3><a href="starPaper.jhtml">更多星卷</a></h3>
                    <div class="clear"></div>
                  </div>
                  <div class="starexam_con">
                  
               <c:set var="countNum" value="${'0'}"></c:set>              
              <c:forEach items="${starPaperList}" var="item" varStatus="itemStatus">                  
	            <c:set var="countNum" value="${countNum+1}"></c:set>
	              <div class="starexam">
                  <div class="starexam_titl left">${countNum}</div>
                      <div class="starexam_titr right">${item.nodeGroupName}</div>
                      <div class="clear"></div>
                      <div class="starexam_c">
                        <div class="starexam_cl">
                          <ul>
                            <li class="f147px">${item.nodeName}</li>
                            <li class="cOra">掌握度:${item.masteryRate}%</li>
                          </ul>
                        </div>
                        <div class="starexam_cr">
						<!-- 
                        <span class="bbs1"><label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
                         onclick="parent.location.href='../report/reportMain.jhtml?nodeInstanceId=${item.instanceId}&showListType=4'">
                         	重练</label></span> 
						 -->
                        <span class="bbs1">
                        <label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
                         onclick="parent.location.href='../report/reportMain.jhtml?orderNum=${item.orderNum}&nodeType=${item.nodeType}&showListType=1'">
                         	查看</label></span> 

                        </div>
                        <div class="clear"></div>
                      </div>
                    </div>  
              </c:forEach>
                  </div>
                </div>
          </div>
</c:if>

            <div id="con_right" class="right">
                     	
               <div class="time">
                <h1 id="studyInfoFirst">${studyInfoFirst}</h1>
                <p id="studyInfoSecond">${studyInfoSecond}</p>
                <div class="time_tup"><a class="cdblue" href="#" id="showTimeDiv">调整考试日期</a></div>
                <div class="pop"  style="display:none" id="setTimeDiv">
                <div class="pop_menu">
                	<form action="studyInfo.jhtml" target="studyInfo">
                   <p>
                   	<input type="text" id="datepicker" name="datepicker" value="${examTime}"/>
                   	<span class="bbs1">
                   	<input type="submit" class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" value="确定"/>
                   	</span>
                   </p>
                  </form>
                   <div class="blankW_6"></div>
                   </div></div>                   
                </div>
                
                <div class="blank10"></div>
                <div class="tishi">
                <h1>
	           <div class="rollTextMenus">
	     			<div id=rollTextMenu1 style="DISPLAY: block;">点击“开始学习”尽早完成学习任务</div>
			        <div id=rollTextMenu2 style="DISPLAY: none">通过“弱项强化”重练最该训练的题</div>
			        <div id=rollTextMenu3 style="DISPLAY: none">答题时，善用“疑问”按钮标记疑问</div>
			        <div id=rollTextMenu4 style="DISPLAY: none">答题时，可点“提示”按钮获取提示</div>
			        <div id=rollTextMenu7 style="DISPLAY: none">出现五星题、四星题，马上重练它们！</div>
			        <div id=rollTextMenu8 style="DISPLAY: none">每天学习1小时，有利于取得最佳效果</div>
    		  </div>
              </h1>
              <span>
              	<a href="javascript:rollText(-1);"><img src="../images/tishi_a.gif" alt="上一条" border="0"/></a><a href="javascript:rollText(1);"><img src="../images/tishi_b.gif" alt="下一条" border="0"/></a>
              </span>
              </div>
                <div class="blank10"></div>
                <div class="study_profile">
                <h1 class="cdblue fB">学习概况分析</h1>
                <h2 class="cdblue"><a href="orderInfo.jhtml">更多排名</a></h2>
                <h3>
                	标准总题数<span>${userDataVO.totalItemsNum}</span>题 - 已做<span>
                	${userDataVO.sumfinishedItems}</span>题 - 共做错<span>${userDataVO.sumIncorrectItems}</span>题 
                </h3>
                <h3>
                	总进度<span> ${userDataVO.learningProcessRate}%</span>  - 第<span>
                	${userDataVO.learningProcessRateOrder}</span>名
                </h3>
                <h3>
                	总掌握度<span> ${userDataVO.totalMasteryRate}%</span> - 第<span>${userDataVO.totalMasteryRateOrder}</span>
                	名     总正确率<span> ${userDataVO.totalAccuracyRate}%</span>
                </h3>        
                </div>
            </div>
            <div class="clear"></div>
        </div><!--End content-->
         
        <div class="container">
        	<div class="sidebox left">
            	<div class="sidebox_tit">
					<!--<h1>学习指导红宝书</h1> -->
                	<h1>电力安全工作规程</h1>
               	 	<h2 class="cdblue"><a href="../studyguide/showStudyGuideAction.jhtml">更多</a></h2>
              	</div>
                <div class="sidebox_con">
                	<ul class="nowarpbox">
						<c:forEach items="${mapList}" var="sg" > 
							<li class="o">
							  <a class="cBlack"
								href="../studyguide/showStudyGuideAction.jhtml?nodeId=${sg['study_guide']}">${sg['name']}</a>
							</li>
						</c:forEach>
                    </ul>
                </div>
            </div>
            
            <div class="sidebox_in left"></div>
            
            <div class="sidebox left">
	            <div class="sidebox_tit">
	            	<h1>最新关注试题</h1>
	            	<h2 class="cdblue"><a href="../attention/myAttention.jhtml">更多</a></h2>
	            </div>
                <div class="sidebox_con">
                	<ul>
                		<c:if test="${fn:length(attentionList) eq 0}">
                			<li class="o">回顾时，点击"关注此题"。</li>
                		</c:if>
                    	<c:forEach items="${attentionList}" var="attention">
							<li class="o">
						 	  <a class="cBlack" 
						 		href="../attention/showAttention.jhtml?attentionId=${attention.id}&type=my">${attention['content']}</a>
							</li>
						</c:forEach>
                    </ul>
                </div>
            </div>
            
            <div class="sidebox_in left"></div>
            <div class="sidebox right">
	            <div class="sidebox_tit">
	            	<h1>社区小喇叭</h1><img src="../images/arrow-snslaba.gif" />
	            	<h3 class="cdblue"><a href="../message/message!list.jhtml?source=-1">全部消息</a></h3>
	            </div>
                <div class="sidebox_con">
                	<ul>
                		<c:if test="${fn:length(messageList) eq 0}">
                			<li class="o">当前尚无消息。</li>
                		</c:if>
	                	<c:forEach items="${messageList}" var="message" begin="0" end="3">
	                		<li class="o">${message.content}</li>
	                	</c:forEach>
                    </ul>
                </div>
            </div>
            
            <div class=" clear"></div>
        </div>
         
         
       <jsp:include page="main_page_bottom.jsp"></jsp:include>
</div>
<iframe id="studyInfo" name="studyInfo" style="display:none"></iframe>
</body>
</html>
<script type="text/javascript">
				<!--
				var rollText_k=8; //菜单总数
				var rollText_i=1; //菜单默认值
				//setFocus1(0);
				rollText_tt=setInterval("rollText(1)",3000);
				function rollText(a){
				clearInterval(rollText_tt);
				rollText_tt=setInterval("rollText(1)",3000);
				rollText_i+=a;
				if (rollText_i>rollText_k){rollText_i=1;}
				if (rollText_i==0){rollText_i=rollText_k;}
				//alert(i)
				 for (var j=1; j<=rollText_k; j++){
				 document.getElementById("rollTextMenu"+j).style.display="none";
				 }
				 document.getElementById("rollTextMenu"+rollText_i).style.display="block";
				} 

//-->
</script>
<script language="JavaScript" src="../js/ocscript.js" type="text/javascript"></script>