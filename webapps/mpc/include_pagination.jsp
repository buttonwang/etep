<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<c:set var="pageGroup"  value="10"></c:set>
	
	<c:if test="${totalCount > pageSize}">
		<div id="pagination" class="manu">
		</div>
	</c:if>
	
	<script type="text/javascript">
		start = "${start}";
		pageSize = "${pageSize}";
		totalCount = "${totalCount}";
		pageGroup = "${pageGroup}";
		
		curPageNo = parseInt(start/pageSize);
		lastPageNo = parseInt((totalCount-1)/pageSize);
		
		firstGroup = parseInt(curPageNo/pageGroup)*pageGroup;
		lastGroup  = firstGroup + pageGroup -1;

		if (lastGroup > lastPageNo) lastGroup = lastPageNo;

		//alert("curPageNo: "+ curPageNo + ", lastPageNo:" + lastPageNo + ", firstGroup:" + firstGroup + ", lastGroup:" + lastGroup);

		pageHtml = "";
		if (curPageNo==0) {
			pageHtml += '<span class="disabled">首页</span><span class="disabled">&lt;前页 </span>';
		} else {
			pageHtml += '<a href="#" p="0">首页</a><a href="#" p="' + (curPageNo-1) + '">&lt;前页</a>'
		}

		for (var i=firstGroup; i<=lastGroup; i++) {
			if (i==curPageNo) {
				pageHtml += '<span class="current">'+(i+1)+'</span>';
			} else {
				pageHtml += '<a href="#" p="'+i+'">'+(i+1)+'</a>';
			}
		}
		
		if (curPageNo==lastPageNo) {
			pageHtml += '<span class="disabled">后页&gt;</span><span class="disabled">末页</span>';
		} else {
			pageHtml += '<a href="#" p="'+(curPageNo+1)+'">后页&gt;</a>'+'<a href="#" p="'+lastPageNo+'">末页</a>'
		}
		
		pageHtml += '<span class="count">(共' + totalCount + '条)</span>';
		
		$("#pagination").html(pageHtml);
	</script>
	
	<script type="text/javascript">
		$("#pagination a").each(
			function() {
				$(this).click( function () {
									var pageSize = "${pageSize}";
									var start = $(this).attr("p") * pageSize;
							   		pageCallBack(start, pageSize); 
								} 
							); 
			}
		 )
	</script>