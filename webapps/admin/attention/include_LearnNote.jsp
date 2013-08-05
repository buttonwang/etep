<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML XMLNS:m="http://www.w3.org/1998/Math/MathML">
<HEAD>
<OBJECT
      ID=MathPlayer
      CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"
>
</OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关注</title>
<link rel="stylesheet" href="../../mpc/css/style_blue.css" type="text/css" />
<link rel="stylesheet" href="../css/thinkbox.css" type="text/css" />
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/thickbox2.js"></script>
<script type="text/javascript" src="../js/showHideSwap.js"></script>
<script>
$(function(){
	try	{
		window.parent.setIframeHeight($("body").height());}
	catch(e) {
	}
	
	try {
		var devoteValue = window.parent.getDevotes();
		var devoteArray = devoteValue.split(",");
		
		for(var i=0; i<devoteArray.length; i++) {
			k = devoteArray[i];
			$("input[user="+k+"]").attr("checked",true);
			$("input[user="+k+"]").next(".jinghua").show();
		};
	}catch(e) {
	}
	
	$("input[t=add]").click(function(){
		var jit = $(this);
		var uid =jit.attr("user");
		var note = $("#"+jit.attr("learnNoteId")).html();
		var checked= jit.attr("checked");
		try {
			window.parent.setUserLeanNote({uid:uid,note:note,checked:checked});
			if (checked) { jit.next(".jinghua").show();}
			else {jit.next(".jinghua").hide();}
		} catch (e){
		}
	})
})
</script>
</head>
<body>
<div class="answers920">
	<c:forEach items="${pageIaVO.result}" var="iaVO" varStatus="lnStatus">
		<c:set var="ln" value="${iaVO.learnNote}"/>
		<c:if test="${ln!=null}">
		<div class=ping_box1>
			<div class=ping_box_wai>
				<div class=ping_top>
					<ul>
						<li class="ping_tl"></li>
						<li class="left">
							<span>
								<input type="checkbox" t="add" user="${ln.webuser.loginName}" learnNoteId="learnNote_${ln.id}" />
								${ln.webuser.loginName}的笔记 
								<span id="jinghua_${item.id}_${ln.webuser.loginName}" class="jinghua" style="display:none">
									（<img src="../../mpc/images/Digest_ico.gif" width="11" height="17" />对精华有贡献）
								</span>
								发表于：<fmt:formatDate pattern= "yy-MM-dd HH:mm" value="${ln.insertTime}" type="both"/>&nbsp;&nbsp;
							</span>
							<c:choose>
								<c:when test="${ln.state==-1||ln.state==-2 }"> 
									<script>$(function(){swap("${ln.id}","hide")})</script>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<span>
								<img src="../../mpc/images/f.gif">
								（得${ln.flowerNum==null?0:ln.flowerNum}支）　
								&nbsp;&nbsp;
								<img src="../../mpc/images/e.gif">
								（得${ln.eggNum==null?0:ln.eggNum}个）
							</span>
							<span id="s_span_${ln.id}" style="display:none">
								此笔记已经被屏蔽<input type="button" value="恢复笔记" onclick='$("#showOneOrAll_${ln.id}").click()'/>
							</span>
							<span id="h_span_${ln.id}" >
								<input type="button" value="屏蔽笔记" onclick='$("#hideOneOrAll_${ln.id}").click()'/>
							</span>
							<span style="display:none">
								<a href="open_forbiddenOrReviewLearnNote.jsp?height=250&width=420&userId=${ln.webuser.id}&learnNoteId=${ln.id}&type=show" 
								 class="thickbox" id="showOneOrAll_${ln.id}">show</a>
								<a href="open_forbiddenOrReviewLearnNote.jsp?height=250&width=420&userId=${ln.webuser.id}&learnNoteId=${ln.id}"
								 class="thickbox" id="hideOneOrAll_${ln.id}">hide</a>
							</span>
						</li>
						<li class="ping_tr"></li>
					</ul>
				</div>
				<!-- 用户选项 -->
				<!--<div class=ping_box_nei id="userAnswer">
				<c:set var="itemAnswerVO" value="${iaVO.itemAnswerVO}" scope="request"/>
				<c:choose>
				<c:when test="${fn:contains(sigleAndMutilChoose,itemAnswerVO.item.itemType.code)}">
						<c:set var="obj" value="${itemAnswerVO.item}" scope="request"/> 
						<c:set var="_answers" value="${fn:split(itemAnswerVO.answerStr,'@:@')}" scope="request"/> 
						<c:set var="answerOptionOrder" value="${iaVO.itemAttention.historyAnswerStatus.answerOptionOrder}" scope="request"/> 
						<c:set var="xiangJieId" value="xianJie_${itemAnswerVO.item.id}_${bStatus.index}_${user.id}" scope="request"/>
						<c:set var="answerId"  value="answer_${itemAnswerVO.item.id}_${bStatus.index}_${user.id}" scope="request"/>
						<c:set var="iVO" value="${itemAnswerVO}" scope="request"/>
						<jsp:include page="include_answer_prototype_user.jsp"/>
				</c:when>
				<c:otherwise>
					<c:set var="itemAnswerVO" value="${iaVO.itemAnswerVO}" scope="request"/>
					<jsp:include page="include_user_item_answer.jsp"/>
				</c:otherwise>	
				</c:choose>
				</div>
				-->
				<div class=ping_box_nei >
					--<c:if test="${iaVO.itemAttention.state ne 0}">（*此试题用户已取消关注*）</c:if>
					<span id="learnNote_${ln.id}">${ln.content}</span>
				</div>
				<div class=ping_bottom>
					<ul>
						<li class="ping_bl">
						<li class="ping_br"></li>
					</ul>
				</div>
			</div>
			<div class="blank12"></div>
		</div>
		</c:if>
	</c:forEach>
	<div class="manu">
		<form action="attentionAdmin!showAdmin.jhtml?p.para.itemId=${item.id}&p.para.jsp=include_LearnNote" id="pageForm" method="post">
			<input type="hidden" value="${pageNo}" name="pageNo"/>
			<ambow:pagination actionName=""        					  
	  			total="${pageIaVO.totalPageCount}"
	  			totalCount="${pageIaVO.totalCount}" 
	  			num="${pageIaVO.currentPageNo}" 
	  			otherParams="" />
		</form>
	</div>
</div>
</body>
</html>