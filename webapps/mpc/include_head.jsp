<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header" class="wm950">
  	<div class="top_l"></div>
  	<div class="top_c">
    <label class="left">
    	<span class="f20px fonth cWhite">${userDataVO.processCategoryName}</span>&nbsp;&nbsp;&nbsp;
    	<span class="f147px cWhite fonth">${userDataVO.processName}</span>
    </label>
    <div id="menu" class="imrcmain0 imgl">
	<div class="imcm imde" id="imouter0">
		<ul id="imenus0">
		<li style="width:45px;"><a href="../web/mainPage!mpc.jhtml">首页</a></li>
		<li class="imatm"  style="width:85px;*_width:90px;">
			<a href="#"><span class="imea imeam"><span></span></span>我的工具箱</a>
			<div class="imsc">
			<div class="imsubc" style="width:145px;top:0px;left:0px;">
				<div class="imunder"></div>
				<div></div>
				<ul style="">
					<li><a href="../message/message!list.jhtml?source=-1">我的消息</a></li>
					<li><a href="../point/myPointAction.jhtml">我的积分</a></li>
					<!-- <li><a href="../studyguide/showStudyGuideAction.jhtml">学习指导红宝书</a></li> -->
				    <!-- <li><a href="../bug/bug!listDifferentItemBugInfoHistoryAnswerStatus.jhtml?b.bugInfo.status=1">捉虫记录</a></li> -->
				    <li><a href="../attention/myAttention.jhtml">我关注的试题</a></li>
				    <li><a href="../exam/setpage.jhtml">更改答题版式</a></li>
				   	<!-- <li><a href="../web/training.jsp" target="_blank">填空题输入练习</a></li>
				   	<li><a href="../download/ambow_ete_setup.exe">学习环境软件下载</a></li> -->
				</ul>
			</div>
			</div>
		</li>
		<!--<li style="width:45px;"><a href="#">帮助</a></li>-->
		<li style="width:70px;"><a href="#" onclick="if(confirm('是否确认退出系统?')){location.href='../logout.jsp';}">退出系统</a></li>	
		</ul>
		<div class="imclear">&nbsp;</div>
	</div>
	</div>
  	</div>
  	<div class="top_r"></div>
  	<div class="clear"></div>
</div>
<script language="JavaScript" src="js/ocscript.js" type="text/javascript"></script>