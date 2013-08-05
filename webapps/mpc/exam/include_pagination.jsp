<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<hr/>
<div class="bnt_box">
	
		<c:if test="${viewControl.showPreButton==true}">
      	<span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
          		 onclick="javascript:showPage(${viewControl.prePageNum})">上${prePageSize}题</button></span>
      	</c:if>
      	<c:if test="${viewControl.showNextButton==true}">
      	<span class="pager"><button class="pager1" onmouseout="this.className='pager1';" onmouseover="this.className='pager2';"
      			onclick="javascript:showPage(${viewControl.nextPageNum})">下${nextPageSize}题</button></span>
      	</c:if> 	 
    <div class="clear"></div>
</div>