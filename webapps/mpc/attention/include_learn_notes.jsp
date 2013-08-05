<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
	
	<!--学习笔记开始-->
	<div class="content_box920" id="notediv">
       	<div class="content_titer920">
             <ul>
               <li>
               	<a name="topNote"></a>
               	<img src="../images/note_ico.gif" width="16" height="14" />&nbsp;&nbsp;
               	<b>共享的学习笔记</b>&nbsp;&nbsp;&nbsp;&nbsp;
               </li>
             </ul>
       	</div>
       	
       	<!--精华笔记开始-->
       	<c:if test="${itemExtraInfo.noteSummary ne null && itemExtraInfo.noteSummary !=''}">
	       	<div class="answers920">
	            <div class=ping_box1>
	            <div class=ping_box_wai>
	            <div class=ping_top>
		            <ul>
		            	<li class="ping_tl"></li>
		            	<li class="left">
		            		<img src="../images/very.gif" width="16" height="16" /> 
		            		<c:if test="${itemExtraInfo.devote eq ''}"><span>由管理员发布</span></c:if>
		            		<c:if test="${itemExtraInfo.devote ne ''}"><span>（对精华笔记有贡献者：${itemExtraInfo.devote}）</span></c:if>
		            		发表于：<fmt:formatDate pattern= "yy-MM-dd HH:mm" value= "${itemExtraInfo.insertTime}"/>
		            	</li>
		            	<li class="ping_tr"></li>
		            </ul>
	            </div>
	            <div class=ping_box_nei>${itemExtraInfo.noteSummary}</div>
	            <div class=sp_huifu>
	            <div class=clear1></div> 
	            </div>
	            <div class=ping_bottom>
		            <ul>
		            	<li class="ping_bl">
		            	<li class="ping_br"></li>
		            </ul>
	            </div>
	            </div>
				<div class="blankW_9"></div>
	            <hr />
	            </div>
	       	</div>
	    </c:if>
       	<!--精华笔记结束-->
       			
       	<!--其它笔记开始-->
		<div class="answers920">
			<c:forEach items="${noteList}" var="note" varStatus="noteStatus"> 
	        <div class=ping_box1>
	            <div class=ping_box_wai>
	            <div class=ping_top>
	            <ul>
	            <li class="ping_tl"></li>
	            <li class="left">
	            	<span>
	            		<c:if test="${note.webuser.id eq webuserId}">你的笔记</c:if>
	            		<c:if test="${note.webuser.id ne webuserId}"><a href="#">${note.webuser.loginName}</a>的笔记</c:if>
	            		<c:if test="${note.state eq 1}">
	            		（<img src="../images/Digest_ico.gif" width="11" height="17" />对精华有贡献）
	            		</c:if>
	            	</span>
	            	发表于：<fmt:formatDate pattern= "yy-MM-dd HH:mm" value= "${note.insertTime}"/>
	            </li>
	            <li class="ping_tr"></li>
	            </ul>
	            </div>
	           
	            <div class="ping_box_nei">${note.content}</div>
	            <div class=sp_huifu>
		            <div class=sp_huifuright>
		            	<c:if test="${note.voted}">
		            		<c:if test="${note.webuser.id eq webuserId}"></c:if>
		            		<c:if test="${note.webuser.id ne webuserId}">你已投票！</c:if>
		            	</c:if>
		            	<c:if test="${!note.voted}"><span id="hi_${note.id}">你觉得呢？</span></c:if>
		            	<img src="../images/f.gif" />
		            	<c:if test="${note.voted}">鲜花</c:if>
		            	<c:if test="${!note.voted}">
		            		<span id="fa_${note.id}">
		            			<a class=brown12 href="#" onclick="vote(${note.id}, 1)">送鲜花</a>
		            		</span>
		            	</c:if>
		            	（得<span id="fv_${note.id}" class="orange12">${note.flowerNum}</span>支）
		            	<img src="../images/e.gif" /> 
		            	<c:if test="${note.voted}">鸡蛋</c:if>
		            	<c:if test="${!note.voted}">
		            		<span id="ea_${note.id}">
		            			<a class=brown12 href="#" onclick="vote(${note.id}, 2)">扔鸡蛋</a>
		            		</span>
		            	</c:if>
		            	（得<span id="ev_${note.id}" class="orange12">${note.eggNum}</span>个） 
		            </div>
		            <div class=clear1></div>
	            </div>
	            <div class=ping_bottom>
		            <ul><li class="ping_bl"><li class="ping_br"></li></ul>
	            </div>
	            </div>
				<div class="blank12"></div>
			</div>
			</c:forEach>
			
			<jsp:include page="../include_pagination.jsp"></jsp:include>
		</div>
  	</div>
  	
  	<c:if test="${itemExtraInfo.noteSummary eq null && totalCount eq 0 }">
	  	<script language="JavaScript" type="text/javascript">
	  		$("#notediv").hide();
	  	</script>
  	</c:if>
  	
  	<script language="JavaScript" type="text/javascript">
	  	function vote(id, type){
			$.get(
					'learnNote!vote.jhtml',
					{noteId: id, voteType: type},
					function(data){
						var vote;
						eval(data);
						$("#fa_" + id).html("鲜花");
						$("#ea_" + id).html("鸡蛋");
						$("#fv_" + id).html(vote.flowerNum);
						$("#ev_" + id).html(vote.eggNum);
						$("#hi_" + id).html("你已投票！");

						location.href = "#topNote";
					}
			);
		}
  	</script>
</body>
</html>