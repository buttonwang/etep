<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" type "text/javascript">
<!--
i=50;
var scrollObj;
function scroll(objid, a){
scrollObj = document.getElementById(objid);
if(i){scrollObj.scrollLeft+=5*a;i--;setTimeout("scroll('"+objid+"', "+a+")",30);}else i=50;
}

//-->
</script>
<script type=text/javascript>
				<!--
				var rollText_k=6; //菜单总数
				var rollText_i=1; //菜单默认值
				//setFocus1(0);
				rollText_tt=setInterval("rollText(1)",8000);
				function rollText(a){
				clearInterval(rollText_tt);
				rollText_tt=setInterval("rollText(1)",8000);
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
</head>
<body>

<!--页面头部-->
<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
<div class="toplin">
    	<div class="toplin_l"><img src="../images/toplin_l.jpg" /></div>
        <div class="toplin_c">
        	<ul>
            	<li><a href="mainPage!ky.jhtml">首页</a></li>
                <li class="divli">|</li>
                <li> <a href="#">在线咨询</a></li>
                <li class="divli">|</li>
                <li><a href="#">帮助</a></li>
                <li class="divli">|</li>
                <li><a href="#" onclick="if(confirm('是否确认退出系统?')){location.href='../../logout.jsp';}">退出系统</a></li>
            </ul>
        </div>
	<div class="toplin_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="clear"></div>
</div>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2">

     <!-- 左栏 -->
	<div id="content_left" class="left">
   	  <div class="user bianf3">
       	  <div class="user_img biana9 left"><img src="../images/man.gif" width="32" height="32" align="absbottom" /></div>
          <div class="user_text left">欢迎你，<span class="f14px fB">${webuser.realName}</span> 同学，
		你已登录${userDataVO.trainingTimes}次<c:if test="${userDataVO.lastTrainingTime ne 'null'}">，上次登录时间是${userDataVO.lastTrainingTime}</c:if>。
        </div>
        <div class="clear"></div>
      </div>
      <div class="clear"></div>
 <!--流程任务未完成-start-->     
 <c:if test="${userDataVO.processStatus>-1}"> 
   <div class="Task">
	     <div class="task_l left bianad">
	      <!--流程正常状态当前任务-->
    		<c:if test="${userDataVO.processStatus<1}">  
		   		   <table width="428" border="0" cellspacing="0" cellpadding="0">
		 			 <tr>
		   				 <td width="63%" height="30" align="left"><span class="f14px fB">当前学习任务</span></td>
		   				 <td width="37%" rowspan="2" align="right" valign="bottom">
		                	 <a href="../exam/goExam.jhtml?nodeInstanceId=${userDataVO.currentNodeInstanceId}"><img src="../images/but_on.gif" width="133" height="45" border="0" /></a>                 </td>
		 			 </tr>
		  			 <tr>
		    			<td align="left">
		                	<span class="f16px fonth fB cYellow">${userDataVO.unitNodeName}</span>&nbsp;&nbsp;
		                    <span class="f14px fonth fB cYellow">${userDataVO.currentNodeName}</span> 
		               </td>
		   			 </tr>
		 			 <tr>
		    			<td height="25" colspan="2" align="left">
		            	<span>本训练<!--共${userDataVO.currentNodeItemNum}题， 限时${userDataVO.currentNodeTime}分, --> ${userDataVO.currentPassNum}人已通过，${userDataVO.currentTestingNum}人正在训练本卷</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <span><a href="studyTask.jhtml" class="cBlue">查看全部学习任务</a></span>           	   </td>
		 			 </tr>
				   </table>
		   </c:if>
		      <!--流程暂停状态当前任务-->
   			 <c:if test="${userDataVO.processStatus==1}">
   			 	<table width="428" border="0" cellspacing="0" cellpadding="0">
 			 <tr>
   				 <td width="63%" height="30" align="left">上次从${pauseInfoVO.titleInfo}</td>
   				 <td width="37%" rowspan="2" align="right" valign="bottom">
                	 <a href="../exam/goExam.jhtml?nodeInstanceId=${pauseInfoVO.nodeInstanceId}&isPause=1"><img src="../images/but_on.gif" width="133" height="45" border="0" /></a>                 </td>
 			 </tr>
  			 <tr>
    			<td align="left">                	
                	${pauseInfoVO.pauseType}中暂存退出，共${pauseInfoVO.totalItemsNum}题，已做${pauseInfoVO.finishedItemsNum}题。
                	<!--限时${pauseInfoVO.totalAnsweringTime}，-->已用${pauseInfoVO.consumeTime}。
               </td>
   			 </tr>
 			 <tr>
    			<td height="25" colspan="2" align="right">
                	<span ><a href="studyTask.jhtml" class="cBlue">查看全部学习任务</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
 			 </tr>  
 			 </table>
		   	</c:if>
		   	
		   	<!--流程生词训练状态当前任务-->
   			 <c:if test="${userDataVO.processStatus==2}">
   			 	<table width="428" border="0" cellspacing="0" cellpadding="0">
 			 <tr>
   				 <td width="63%" height="30" align="left">请你先进行</td>
   				 <td width="37%" rowspan="2" align="right" valign="bottom">
                	 <a href="../freshword/freshWordFace.jhtml"><img src="../images/but_on.gif" width="133" height="45" border="0" /></a>                 </td>
 			 </tr>
  			 <tr>
    			<td align="left">
                	<span class="f26px fonth fB cYellow">生词训练</span>，然后再继续学习。
               </td>
   			 </tr>
 			 <tr>
    			<td height="25" colspan="2" align="left">
            		本次训练共${wordNum}个生词。
                	&nbsp;&nbsp;&nbsp;&nbsp;<span ><a href="studyTask.jhtml" class="cBlue">查看全部学习任务</a></span>
				</td>
 			 </tr>  
 			 </table>
		   	</c:if>
	  </div>
  		  <div class="task_r right bianad">
          		<div class="task_img"><a href="../report/reportMain.jhtml"><img src="../images/but_repet.gif" border="0" /></a></div>
	  			<div class="task_ling"><a href="starPaper.jhtml">查看我的星卷榜</a></div>
	  </div>
          <div class="clear"></div>
	  </div>
	  
         <!-- 当前学习流 -->

         <div class="mark bianbc">
         	<div class="tilte f14px fB">当前学习流</div>
           <div class="content">
           	 <div class="funcs f14px fB">
		  <ul>
		    <li><img src="../images/arrow_left.gif" border="0" id="l" onclick="scroll('a2', -1)"/></li>
		    <li><div id="a2" class="outerDiv">
		      <div class="func" style="width:${userDataVO.nodeListDivWidth}px;">
		        <ul>         
		           <c:set var="practiceCount" value="${'1'}" ></c:set>
		           <c:forEach items="${userDataVO.nodeInstanceInfoList}" var="item" varStatus="itemStatus">
		           	<li class="${item.cssClassName}" title="${item.titleInfo}">
		           		<c:if test="${item.tag==1}">
		           		<a href="../report/reportMain.jhtml?nodeInstanceId=${item.nodeInstanceId}">
		           		</c:if>
		           		<font color="white">
		           		<c:if test="${item.nodeType eq 'PRACTICE'}">
		           		${practiceCount}
		           		<c:set var="practiceCount" value="${practiceCount+1}" ></c:set>
		           		</c:if>
		           		<c:if test="${item.nodeType eq 'EVALUATE'}">${item.nodeName}</c:if>
		           		<c:if test="${item.nodeType eq 'PHASETEST'}">${item.nodeName}</c:if>
		           		<c:if test="${item.nodeType eq 'UNITTEST'}">&nbsp;&nbsp;</c:if>
		           		</font> 
		           		<c:if test="${item.tag==1}">
		           		 </a>
		           		 </c:if>
		           	</li>
		           </c:forEach>
		        </ul>
		      </div>
		      </div>
		    </li>
		    <li class="right"><img src="../images/arrow_right.gif" border="0" id="r" onclick="scroll('a2', 1)" /></li>
		  </ul><div class="clear"></div>
			</div>
                <div class="biank_top"></div>
                <div class="funcs">
           		  <ul>
                    <li class="mar_5px f14px fB">图示：</li>
                    <li class="mar_5px cDGray"><img src="../images/bnt_red_s.gif" align="absmiddle" /> 评测</li>
                     <li class="mar_5px  cDGray"><img src="../images/bnt_ora_s.gif" align="absmiddle" /> 阶段测试</li>
                    <li class="mar_5px  cDGray"><img src="../images/bnt_green_s.gif" align="absmiddle" /> 训 练</li>
                     <li class="mar_5px  cDGray"><img src="../images/bnt_yellow_s.gif" align="absmiddle" /> 单元测试</li>
                     <li class="mar_5px  cDGray"><img src="../images/bnt_gray_s.gif" align="absmiddle" /> 未激活状态</li>
               	  </ul>
                    <div class="clear"></div>
                </div>
           </div>
     	 </div>
	</c:if>
    <!--流程任务未完成-end-->       
  <!--流程任务全部完成-start-->       
    <c:if test="${userDataVO.processStatus==-1}">
      <div class="Task">
	     <div class="task_l left bianad">
	     <c:if test="${pauseInfoNum==0}">
   		   <table width="428" border="0" cellspacing="0" cellpadding="0">
 			 <tr>
   				 <td width="72" height="40" align="center"><img src="../images/emoticon-1.png" width="32" height="32" /></td>
		         <td width="356" height="40" align="left"><span class="f16px fonth fB cYellow">祝贺你已完成所有学习任务！</span></td>
 			 </tr>
  			 
 			 <tr>
    			<td height="25" colspan="2" align="left">
	            	<span>建议继续训练“我的星卷榜”或“弱项强化”，提高掌握度。&nbsp;
	                <c:if test="${wordNum>0}">
	                	<span ><a href="../freshword/freshWordFace.jhtml" class="cBlue">生词训练</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
					</span>
				</td>
 			 </tr>
		   </table>
		   </c:if>
		   <c:if test="${pauseInfoNum>0}">
		   <table width="428" border="0" cellspacing="0" cellpadding="0">
 			 <tr>
   				 <td width="63%"  align="left">上次从${pauseInfoVO.titleInfo}</td>
   				 <td width="37%" rowspan="2" align="right" valign="center">
                	 <a href="../exam/goExam.jhtml?nodeInstanceId=${pauseInfoVO.nodeInstanceId}&isPause=1"><img src="../images/but_on.gif" width="133" height="45" border="0" /></a>                 
                	 <br>
                	 <c:if test="${wordNum>0}">
                	<span ><a href="../freshword/freshWordFace.jhtml" class="cBlue">生词训练</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if>
                </td>
 			 </tr>
  			 <tr>
    			<td align="left">                	
                	${pauseInfoVO.pauseType}中暂存退出，共${pauseInfoVO.totalItemsNum}题，已做${pauseInfoVO.finishedItemsNum}题。
                	<!--限时${pauseInfoVO.totalAnsweringTime}，-->已用${pauseInfoVO.consumeTime}。
                	<br><span>建议继续训练“我的星卷榜”或“弱项强化”，提高掌握度。&nbsp; <span >
               </td>
   			 </tr>
 			 <tr>
    			<td  colspan="2" align="right">
    				
				</td>
 			 </tr>  
 			 </table>
 			 </c:if>
		</div>
  		  <div class="task_r right bianad">
          		<div class="task_img">
				<a href="../report/reportMain.jhtml"><img src="../images/but_repet.gif" border="0" /></a>
				</div>
		</div>
          <div class="clear">
		  </div>
	</div>
         <!-- 当前学习流 -->
         <div class="mark bianbc">
         	<div class="tilte"><span class="f14px fB">我的星卷榜</span><span>（按掌握度升序排列）</span><span style=" padding-left:350px;"><a href="starPaper.jhtml">更多星卷</a></span></div>
           <div class="content">
             <table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="listab">
               <tr>
                 <td align="center"><strong>训练名</strong></td>
                 <td align="center"><strong>首答正确率</strong></td>
                 <td align="center"><strong>当前正确率</strong></td>
                 <td align="center"><strong>掌握度</strong></td>
                 <td align="center"><strong>答题日期</strong></td>
               </tr>
               <c:forEach items="${starPaperList}" var="item" varStatus="itemStatus">                  
						<tr class="whitetr">
	                      <td align="center"><a href="../report/reportMain.jhtml?nodeInstanceId=${item.instanceId}">${item.nodeName}</a></td>
	                      <td align="center">${item.firstAccuracyRate}%</td>
	                      <td align="center">${item.accuracyRate}%</td>
	                      <td align="center">${item.masteryRate}%</td>
	                      <td align="center">${item.time}</td>                    
	                    </tr>		
               </c:forEach>
             </table>
           </div>
   	  </div>
    </c:if>    
     <!--流程任务全部完成-end-->    
        
         <!-- 当前学习概况 -->
         <div class="mark bianbc">
         	<div class="tilte f14px fB">当前学习概况</div>
            <div class="content">
            <p class="cDGray">             
            标准总题数<span class="fonth f14px fB cGreen">${userDataVO.totalItemsNum}</span>，            
            已训练
            <span class="fonth f14px fB cGreen">${userDataVO.sumfinishedItems}</span>题，共做错<span class="fonth f14px fB cGreen">${userDataVO.sumIncorrectItems}</span>题。
            <br> <br>
              
			总进度<span class="fonth f14px fB cGreen">${userDataVO.learningProcessRate}%</span>| 第<span class="fonth f14px fB cGreen">${userDataVO.learningProcessRateOrder}</span>名；
		
            总掌握度<span class="fonth f14px fB cGreen">${userDataVO.totalMasteryRate}% </span>| 第<span class="fonth f14px fB cGreen">${userDataVO.totalMasteryRateOrder}</span>名；
            总正确率：<span class="fonth f14px fB cGreen">${userDataVO.totalAccuracyRate}%</span></p></div>
         </div>
          <!-- 训练状态图 -->
           <div class="mark bianbc">
           		<div class="tilte">
                	<div class="left"><span class="fB f14px">训练状态</span>&nbsp;&nbsp;&nbsp;&nbsp;最近10个训练的学习状况图</div>
               	  <div class="right tushi"><div class="tushi_g left"></div>
               	  <span class="left">掌握度</span>
           	      <div class="tushi_b left"></div><span class="left">正确率</span></div>
                  <div class="clear"></div>
                </div>
                <div class="content">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
                    <tr>
                      <td width="30" align="right" valign="bottom" class="heng1">100</td>
                <td rowspan="6" valign="bottom" style="border:1px solid #e4e4e4;height:100px;">
                	<table border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
                          <tr>
                           <c:forEach items="${userDataVO.practiceList}" var="item" varStatus="itemStatus">  
                            <td width="50" height="120" align="center"  valign="bottom">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="bottom" ><div class="bar1" style="height:${item.masteryRate/1}px;" title="${item.name},掌握度${item.masteryRate/1}%"></div></td>
                                  <td width="3">&nbsp;</td>
                                  <td valign="bottom" ><div class="bar2" style="height:${item.accuracyRate/1}px;" title="${item.name},正确率${item.accuracyRate/1}%"></div></td>
                                </tr>
                            </table>
                            </td>
                          </c:forEach>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td align="right" valign="bottom"  class="heng1">80</td>
                    </tr>
                    <tr>
                      <td align="right" valign="bottom"  class="heng1">60</td>
                    </tr>
                    <tr>
                      <td align="right" valign="bottom"  class="heng1">40</td>
                    </tr>
                    <tr>
                      <td align="right" valign="bottom"  class="heng1">20</td>
                    </tr>
                    <tr>
                      <td align="right" valign="bottom"  class="heng1">0</td>
                    </tr>
                    <tr>
                      <td align="right">时间</td>
                      <td>
                      <table border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
                          <tr>
                          <c:forEach items="${userDataVO.practiceList}" var="item" varStatus="itemStatus">
                            <td width="50" height="14" align="center" class="shu1">${item.date}</td>
                            </c:forEach>
                          </tr>
                      </table>
                      </td>
                    </tr>
                  </table>
                </div>
           </div>
   	</div>
    
<!-- 右栏 -->
    <div id="content_right" class="left">
    	<div class=" jihua biana9">
       		<div class="tilte f14px fB">我的学习计划</div>
            <div class="content">
            <c:if test="${days>0}">
           	  <div class="day bianbc f14px">
                距离<span class="fonth cYellow fB">${year}年</span>考研还有<span class="fonth cYellow fB"> ${days}天</span>
              </div>
            </c:if>
            <!--  
                <p><img src="../images/Tip.gif" align="absmiddle" />&nbsp;&nbsp;你尚未安排学习计划。<br /><br />
                  <span><img src="../images/clock.gif" width="17" height="17" align="absmiddle" />&nbsp;&nbsp;<a href="#" class="cBlue">制定学习计划 </a></span></p>
             -->     
                 <c:if test="${userDataVO.learningProcessRate>0&&userDataVO.learningProcessRate<100}">
                 <div class="tishi bianbc cDGray">以你目前的平均学习速度计，你将
于<span class="cGreen fonth fB f14px">${finishDate}</span>日学完本课程。</div>
</c:if>
            </div>
        </div>
        
        <!-- 权威提示-->
        <div class=" jihua biana9">
        	<div class="tilte f14px fB">权威提示</div>
             <div class="content cDGray">
             <span><div class=rollTextMenus>
     		 <div id=rollTextMenu1 style="DISPLAY: block; ">每天学习至少1小时，有利于获得最佳学习效果。</div>
     		 <div id=rollTextMenu2 style="DISPLAY: none">一个标准的学习安排是怎样的？</div>
     		 <div id=rollTextMenu3 style="DISPLAY: none">怎样获得更好的学习效果</div>
     		 <div id=rollTextMenu4 style="DISPLAY: none">怎样获得更好的学习效</div>
     		 <div id=rollTextMenu5 style="DISPLAY: none">怎样获得更好的学习效</div>
    		  <div id=rollTextMenu6 style="DISPLAY: none">4怎样获得更好的学习效</div></div>
    		  </span>&nbsp;&nbsp;
      <span><a title=上一条 href="javascript:rollText(-1);"><img src="../images/last.gif" 
      alt=上一条 width="11" height="11" border="0" align="absmiddle"/></a> <a title=下一条 href="javascript:rollText(1);"><img src="../images/next.gif" 
      alt=下一条 width="11" height="11" border="0" align="absmiddle" /></a></span>
      </div>
      <div class="clear"></div>
        </div>
        
        
                <!-- 写作批改-->
        
         <div id="swiediv" class=" jihua biana9">
        	<div class="tilte f14px fB">写作批改</div>
        	<div class="content">
        		<div id="swieinfo">Loading...</div>
            	<div class="xitong"><a href="${swieBean.postUrl}" target="_blank">
            		<img src="../images/btn_pigai0.gif" alt="进入批改系统"  /></a>
            	</div>
      		</div>
      		<div class="clear"></div>
        </div>
        
        
        
        
         <!-- 进度排名-->
          <div class=" jihua biana9">
        	<div class="tilte"><span class="left f14px fB">总进度排名</span><span class="right"><a href="orderInfo.jhtml" class="cBlue">查看更多</a></span></div>
           <div class="content cDGray">
               <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <c:set var="studentCount" value="${'0'}"></c:set>              
               		 <c:forEach items="${learningProcessRateTopList}" var="item" varStatus="itemStatus">                  
	                  		<c:set var="studentCount" value="${studentCount+1}"></c:set>
	                    <tr>
		                   <td width="34" align="center" class="line01">${studentCount}</td>
		                   <td width="102" class="line01">${item.userName}</td>
		                   <td width="88" class="line01">${item.learningProcessRate}%</td>
		                 </tr>		
                    </c:forEach> 
               </table>
            </div>
        </div>
    </div>
    <div class=" clear"></div>
</div>
<div class="bonnt wm1">© 2008年  安博教育集团  版权所有</div>
<iframe src="swie.jhtml" scrolling="no" style="display:none" id="swieframe" frameborder="0"></iframe>
</body>
</html>
