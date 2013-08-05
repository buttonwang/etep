<%@ page contentType="text/html; charset=UTF-8"%>
<!--页面头部-->
<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
<div class="toplin">
    	<div class="toplin_l"><img src="../images/toplin_l.jpg" /></div>
        <div class="toplin_c">
        	<ul>
            	<li><a href="../web/mainPage!ky.jhtml">首页</a></li>
                <li class="divli">|</li>
                <li> <a href="#">在线咨询</a></li>
                <li class="divli">|</li>
                <li><a href="#">帮助</a></li>
                <li class="divli">|</li>
                <li><a href="#"  onclick="if(confirm('是否确认退出系统?')){location.href='../../logout.jsp';}">退出系统</a></li>
            </ul>
        </div>
	<div class="toplin_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="clear"></div>
</div>
<!--    end   -->