<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我关注的试题</title>
<link href="../css/hig.css"  rel="stylesheet" type="text/css">
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<link href="../css/dtree.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.3.1.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/dtree.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT language="javascript" src="../js/floating_new.js" type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript">
function goto(order,page){
    var param = "topAttention!list.jhtml?order="+order;
	if(page != null && page !=''){
		param = "topAttention!list.jhtml?page="+page;
	}
	<c:if test="${orderNum != null && orderNum !=''}">
	param=param+"&orderNum=${orderNum}";
	</c:if>
	<c:if test="${type != null && type !=''}">
	param=param+"&type=${type}";
	</c:if>
	<c:if test="${tag != null && tag !=''}">
	param=param+"&tag=${tag}";
	</c:if>
	self.location=param;
}
function send(orderNum,type){
	var href = "topAttention!list.jhtml";
	if(orderNum !=null && orderNum !=''){
		href = href + "?orderNum="+orderNum;
	}
	if(type != null && type !=''){
		if(href.indexOf('?')>0){
			href = href + "&type="+type;
		}else{
			href = href + "?type="+type;
		}
	}
	self.location=href;
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
</head>
<body >
        <div class="tager">
        <div class="tager_in">
          <h2><span class="f14px cblue fB">题型：</span> 
		  	  <c:if test="${type == null || type == ''}">
			  <span><a href="#" onclick="javascript:send('${orderNum}',null)" style="color:gray;font-weight:bold">全部</a></span>
			  </c:if>
			  <c:if test="${type != null && type != ''}">
			  <span><a href="#" onclick="javascript:send('${orderNum}',null)">全部</a></span>
			  </c:if>
		  	  <c:forEach items="${itemTypeList}" var="itemType" >
			  	<c:if test="${type == itemType[0]}">
			  	<span><a href="#" onclick="javascript:send('${orderNum}','${itemType[0]}')" style="color:gray;font-weight:bold">${itemType[1]}</a></span>
				</c:if>
				<c:if test="${type != itemType[0]}">
				<span><a href="#" onclick="javascript:send('${orderNum}','${itemType[0]}')">${itemType[1]}</a></span>
				</c:if>
			  </c:forEach>
		  </h2>
          <h2><span class="f14px cblue fB">最热标签：</span> 
		  <c:forEach items="${tagList}" var="tagObj" >
		  <span>
		  <c:if test="${tag == tagObj['id']}">
		  <a href="topAttention!list.jhtml?orderNum=${orderNum}&type=${type}&tag=${tagObj['id']}" style="color:gray;font-weight:bold">${tagObj['tag']}</a>
		  </c:if>
		  <c:if test="${tag != tagObj['id']}">
		  <a href="topAttention!list.jhtml?orderNum=${orderNum}&type=${type}&tag=${tagObj['id']}" >${tagObj['tag']}</a>
		  </c:if>
		  </span> 
		  </c:forEach>
		  </h2>
        </div></div>
 <div class="blankW_9"></div>
 		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="gzlist">
          <tr class="gz_title">
            <td width="33%">
<div class="Toolbar">
                <ul>
				<% System.out.println("=="+request.getAttribute("order")+"==");%>
                    <li class="ToolbarItem c_mcp BigPaddingLeft" id="sortMenu">
                        <a href="#" class="c_ml cBlack" onclick="try{$menu.bind(event,0, 0, 1)}catch(e){};return false;"><span>选择排序方式</span>&nbsp;<span class="c_chev">▼</span></a>
                        <ul style="display: none; visibility: hidden; left: 33px; top: 28px;" class="c_m" onclick="$menu.closeCurrent();">
							  
                             <li aid="SortBySender">
							 	<c:if test="${order != null && order !='' && order != 'attention_asc' && order != 'attention_desc'}">
                                <a id="SortBySender" href="#" title="受关注人气" onclick="javascript:goto('attention_desc');">				                                <span>受关注人气</span>
								</a>
								</c:if>
							 	<c:if test="${order == 'attention_asc'}">
                                <a class="sel" id="SortBySender" href="#" title="受关注人气" onclick="javascript:goto('attention_desc');">				                                <span class="c_light">受关注人气</span>
								<img src="../images/clear.gif" class="ascend_rest_dark" alt="正向排序">
								</a>
								</c:if>
								<c:if test="${order == null || order =='' || order == 'attention_desc'}">
                                <a class="sel" id="SortBySender" href="#" title="受关注人气" onclick="javascript:goto('attention_asc');">				                                <span class="c_light">受关注人气</span>
								<img src="../images/clear.gif" class="descend_rest_dark" alt="反向排序">
								</a>
								</c:if>
                              </li>
								
                             <li aid="SortBySender">
							 	<c:if test="${order != 'amount_asc' && order != 'amount_desc'}">
                                <a id="SortBySender" href="#" title="笔记总数" onclick="javascript:goto('amount_desc');">				                                <span>笔记总数</span>
								</a>
								</c:if>
							 	<c:if test="${order == 'amount_asc'}">
                                <a class="sel" id="SortBySender" href="#" title="笔记总数" onclick="javascript:goto('amount_desc');">				                                <span class="c_light">笔记总数</span>
								<img src="../images/clear.gif" class="ascend_rest_dark" alt="正向排序">
								</a>
								</c:if>
								<c:if test="${order == 'amount_desc'}">
                                <a class="sel" id="SortBySender" href="#" title="笔记总数" onclick="javascript:goto('amount_asc');">				                                <span class="c_light">笔记总数</span>
								<img src="../images/clear.gif" class="descend_rest_dark" alt="反向排序">
								</a>
								</c:if>
                              </li>
							  
                             <li aid="SortBySender">
							 	<c:if test="${order != 'state_asc' && order != 'state_desc'}">
                                <a id="SortBySender" href="#" title="精华笔记" onclick="javascript:goto('state_desc');">				                                <span>精华笔记</span>
								</a>
								</c:if>
							 	<c:if test="${order == 'state_asc'}">
                                <a class="sel" id="SortBySender" href="#" title="精华笔记" onclick="javascript:goto('state_desc');">				                                <span class="c_light">精华笔记</span>
								<img src="../images/clear.gif" class="ascend_rest_dark" alt="正向排序">
								</a>
								</c:if>
								<c:if test="${order == 'state_desc'}">
                                <a class="sel" id="SortBySender" href="#" title="精华笔记" onclick="javascript:goto('state_asc');">				                                <span class="c_light">精华笔记</span>
								<img src="../images/clear.gif" class="descend_rest_dark" alt="反向排序">
								</a>
								</c:if>
                              </li>
							  
                             <li aid="SortBySender">
							 	<c:if test="${order != 'me_asc' && order != 'me_desc'}">
                                <a id="SortBySender" href="#" title="我的笔记" onclick="javascript:goto('me_desc');">				                                <span>我的笔记</span>
								</a>
								</c:if>
							 	<c:if test="${order == 'me_asc'}">
                                <a class="sel" id="SortBySender" href="#" title="我的笔记" onclick="javascript:goto('me_desc');">				                                <span class="c_light">我的笔记</span>
								<img src="../images/clear.gif" class="ascend_rest_dark" alt="正向排序">
								</a>
								</c:if>
								<c:if test="${order == 'me_desc'}">
                                <a class="sel" id="SortBySender" href="#" title="我的笔记" onclick="javascript:goto('me_asc');">				                                <span class="c_light">我的笔记</span>
								<img src="../images/clear.gif" class="descend_rest_dark" alt="反向排序">
								</a>
								</c:if>
                              </li>
							  
                             <li aid="SortBySender">
							 	<c:if test="${order != 'type_asc' && order != 'type_desc'}">
                                <a id="SortBySender" href="#" title="题型" onclick="javascript:goto('type_desc');">				                                <span>题型</span>
								</a>
								</c:if>
							 	<c:if test="${order == 'type_asc'}">
                                <a class="sel" id="SortBySender" href="#" title="题型" onclick="javascript:goto('type_desc');">				                                <span class="c_light">题型</span>
								<img src="../images/clear.gif" class="ascend_rest_dark" alt="正向排序">
								</a>
								</c:if>
								<c:if test="${order == 'type_desc'}">
                                <a class="sel" id="SortBySender" href="#" title="题型" onclick="javascript:goto('type_asc');">				                                <span class="c_light">题型</span>
								<img src="../images/clear.gif" class="descend_rest_dark" alt="反向排序">
								</a>
								</c:if>
                              </li>
							  
                             <li aid="SortBySender">
							 	<c:if test="${order != 'difficulty_asc' && order != 'difficulty_desc'}">
                                <a id="SortBySender" href="#" title="难度" onclick="javascript:goto('difficulty_desc');">				                                <span>难度</span>
								</a>
								</c:if>
							 	<c:if test="${order == 'difficulty_asc'}">
                                <a class="sel" id="SortBySender" href="#" title="难度" onclick="javascript:goto('difficulty_desc');">				                                <span class="c_light">难度</span>
								<img src="../images/clear.gif" class="ascend_rest_dark" alt="正向排序">
								</a>
								</c:if>
								<c:if test="${order == 'difficulty_desc'}">
                                <a class="sel" id="SortBySender" href="#" title="难度" onclick="javascript:goto('difficulty_asc');">				                                <span class="c_light">难度</span>
								<img src="../images/clear.gif" class="descend_rest_dark" alt="反向排序">
								</a>
								</c:if>
                              </li>
                              </ul>
                    <div class="c_shad" style="position: absolute; display: none; background-color: rgb(0, 0, 0); opacity: 0.3; visibility: hidden; width: 126px; height: 179px; left: 36px; top: 31px;"></div>
               	  </li>  
                </ul>
            </div>
			<script defer="defer" src="../js/shared.js"></script>
			</td>
            <td width="13%" align="center">受关注人气</td>
            <td width="14%" align="center">共享笔记总数</td>
            <td width="10%" align="center">精华笔记</td>
            <td width="12%" align="center">我的笔记</td>
            <td width="11%" align="center">题型</td>
            <td width="7%" align="center">难度</td>
            </tr>
			<c:forEach items="${attentionList}" var="attention" > 
          <tr>
            <td>
				<a href="#" onclick="javascript:parent.location.href='showAttention.jhtml?attentionId=${attention.id}&type=top' ">${attention['content']}</a>
			</td>
            <td align="center">${attention['popularity']}</td>
            <td align="center">${attention['amount']}</td>
            <td align="center"><c:if test="${attention['state'] !=null && attention['state'] !=''}"><img src="images/very.gif" width="16" height="16" /></c:if><c:if test="${attention['state'] ==null || attention['state'] ==''}">--</c:if></td>
            <td align="center"><c:if test="${attention['note_id'] != null}"><img src="images/note_ico.gif" width="16" height="14" /></c:if><c:if test="${attention['note_id'] == null}">--</c:if></td>
            <td align="center"><span class="l_3">${attention['name']}</span></td>
            <td align="center">${attention['difficulty_value']}</td>
            </tr>
			</c:forEach>
        </table>
        <div class=" blankW_6"></div>
		<c:if test="${pag.totalCount >0}">
        <div class="manu">
		<c:if test="${pag.page == 1}">
		<span class="disabled">首页</span>
		<span class="disabled"> <  上一页</span>
		</c:if>
		<c:if test="${pag.page > 1}">
		<a href="#" onclick="javascript:goto(null,1);">首页</a>
		<a href="#" onclick="javascript:goto(null,${pag.page-1});"><  上一页</a>
		</c:if>
		<c:forEach var="offPage" begin="${pag.beginPage}" end="${pag.endPage}" step="1">
			<c:if test="${pag.page == offPage}">
         	<span class="current">${pag.page}</span>
			</c:if>
			<c:if test="${pag.page != offPage}">
         	<a href="#" onclick="javascript:goto(null,${offPage});">${offPage}</a>
			</c:if>
		</c:forEach>
		<c:if test="${pag.page >= pag.totalPage}">
		<span class="disabled">下一页  > </span>
		<span class="disabled">最后一页</span>
		</c:if>
		<c:if test="${pag.page < pag.totalPage}">
		<a href="#" onclick="javascript:goto(null,${pag.page+1});">下一页  > </a>
		<a href="#" onclick="javascript:goto(null,${pag.totalPage});">最后一页</a>
		</c:if>
		</div>
		</c:if>
        <div class="blankW9"></div>
</body>
</html>
