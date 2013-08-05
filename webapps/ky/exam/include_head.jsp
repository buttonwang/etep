<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<SCRIPT LANGUAGE="JavaScript" src="../js/ahead.js"></script>
<c:if test="${viewControl.showModel==1}">
<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
  <div class="toplins">
    <div class="toplins_l"><img src="../images/toplin_l.jpg" /></div>
    <c:if test="${startExamTag==true}">
    <div class="toplins_c"><a href="#" onclick="javascript:setSpanStr();checkit('sign01');checkit('sign02');" class="cWhite"><img src="../images/exit.gif" align="absmiddle" />退出训练</a></div>
    </c:if>
    <c:if test="${startExamTag==false}">
    <div class="toplins_c"><a href="#" onclick="javascript:window.location='../web/mainPage!ky.jhtml';" class="cWhite"><img src="../images/exit.gif" align="absmiddle" />退出训练</a></div>
    </c:if>
    <div class="toplins_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="clear"></div>
</div>
</c:if>
<c:if test="${viewControl.showModel==2||viewControl.showModel==3 }">
	<div id="top">
  <div class="logo cWhite"><img src="../images/logo.jpg" /><span class="f26px fonth ">${userDataVO.processCategoryName}</span> <span class="f14px">${userDataVO.processName}</span></div>
  <div class="toplins">
    <div class="toplin_l"><img src="../images/toplin_l.jpg" /></div>
    <!--  
    <div class="toplin_c">
      <ul>
        <li><a href="../web/loadSessionVar.jhtml">首页</a></li>
        <li class="divli">|</li>
        <li> <a href="#">在线咨询</a></li>
        <li class="divli">|</li>
        <li><a href="#">帮助</a></li>
        <li class="divli">|</li>
        <li><a href="#"  onclick="if(confirm('是否确认退出系统?')){location.href='../../logout.jsp';}">退出系统</a></li>
      </ul>
    </div>
    -->
    <div class="toplins_c"><a href="#" onclick="javascript:window.location='../web/loadSessionVar!ky.jhtml';" class="cWhite"><img src="../images/exit.gif" align="absmiddle" />返回首页</a></div>
    <div class="toplin_r"><img src="../images/toplin_r.gif" /></div>
  </div>
  <div class="clear"></div>
 </div>
</c:if>