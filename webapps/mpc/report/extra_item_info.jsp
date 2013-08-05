<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="../js/jquery-1.3.1.js"></script>
<script language="javascript1.2">
function test(num){
	
	if(num == null){
		urlStr="extra!info.jhtml?nodeGroupId=${nodeGroupId}";
	}else{
		urlStr="extra!info.jhtml?nodeGroupId=${nodeGroupId}&num="+num;
	}
	$.ajax({
			type: "get",
			url: urlStr,
			beforeSend: function(XMLHttpRequest){
				document.getElementById("bb").disabled=true;
			},
			success: function(data, textStatus){
			 	mystr=data.split(","); 
				document.getElementById("size").innerText=mystr[0];
				document.getElementById("score").innerText=mystr[1];
				document.getElementById("time").innerText=mystr[2];
				document.getElementById("bb").disabled=false;
				//
			},
			complete: function(XMLHttpRequest, textStatus){
				//
			},
			error: function(){
				//请求出错处理
			}
	});
}
</script>
<div class="label box1">
        <div class="blankW_6"></div>
        	<ul>
            	<li class="f14px"><b>任务名称：</b>【${viewControl.chapterName}】-【${viewControl.sectionName}】&nbsp;&nbsp;&nbsp;<span>${viewControl.examName}</li>
                <li class="f14px"><b>任务性质：</b>拓展练习</li>
                <li class="blankW_9"></li>
                <li><b>总题数：</b><span id="size">${currentSize} </span>题</li>
                <li><b>总分：</b><span id="score">${allScore}</span></li>
                <li><b>限时：</b><span id="time">${allAnsweringTime}</span></li>
                <hr />
                <li><span class="cRed">建议每次练习题量50以内，以免做题时间过长。</span>				
				<span><input id="item_num" name="item_num" type="radio" value="" <c:if test="${num == null}">checked="checked"</c:if> onclick="javascript:test();" />${allSize}（可练总题量）</span>
				<c:if test="${allSize >10}">
				<span><input id="item_num" name="item_num"  type="radio" value="10" <c:if test="${num == 10}">checked="checked"</c:if> onclick="javascript:test(10);" />10</span>
				</c:if>
				<c:if test="${allSize >20}">
				<span><input id="item_num" name="item_num"  type="radio" value="20" <c:if test="${num == 20}">checked="checked"</c:if> onclick="javascript:test(20);" />20</span>
				</c:if>
				<c:if test="${allSize >50}">
				<span><input id="item_num" name="item_num"  type="radio" value="50" <c:if test="${num == 50}">checked="checked"</c:if> onclick="javascript:test(50);" />50</span>
				</c:if></li>
            </ul>
        </div>