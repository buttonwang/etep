<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/freshword_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
		<link href="../css/base.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="../js/common.js"></script>
		<SCRIPT LANGUAGE="JavaScript" src="../js/ahead.js"></script>
		<script type="text/javascript">
			var tag=false;
			function freshword_asserts(){
				if(tag)
					return;
				tag=true;
				document.newform.action="freshWordConfig!asserts.jhtml";
				document.newform.submit();
				
			}
			function freshword_test(){
				if(tag)
					return;
				tag=true;
				var newaction ="freshWordConfig!newWordNo.jhtml";
				var tmpNo=document.newform.pageNo.value;
				if(tmpNo*1<${newpage.totalCount}){
					document.newform.pageNo.value=tmpNo*1+1;

				}else{
					newaction="freshWordConfig!newWordOver.jhtml?isOk=false";
				}
				document.newform.action=newaction;
				document.newform.submit();
			}
		</script>
	</head>
	<body onload="MM_preloadImages('../images/ball_green_s.gif','../images/ball_yellow_s.gif','../images/ball_red_s.gif')">
	<form action="" method="post" name="newform">
		<input name="id" type="hidden" value="${id}"/>
		<input name="pageNo" type="hidden" value="${pageNo}"/>
	</form>
		<%@ include file="include/freshword_top.jsp"%>
			 <!-- 页面主题 -->
			<div id="main" class="wm2 bianbf" >
				<div class="newword">
					<div class="newword_cs f14px">
	  				判断下列单词你认识否？根据实际情况点击相应颜色的灯。
					</div>
    				<div class="newword_cs bianf3">
      					规则：<img src="../images/ball_green_sm.gif" width="16" height="16" />    代表“认识”    <img src="../images/ball_yellow_sm.gif" width="16" height="16" />    代表“模糊不清楚”    <img src="../images/ball_red_sm.gif" width="16" height="16" />    代表“不认识”。 
						<div style="height:200px;"> 
      					<c:forEach items="${newpage.result}" var="page">
							<p class="danci f32px Arial">${page.wordExtension.wordBasic.word}</p><br />
					    </div>
                        	<p>
  							<a href="#" onclick="freshword_asserts()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','../images/ball_green_s.gif',1)"><img src="../images/ball_green.gif" alt="认识" name="Image7" width="36" height="36" border="0" id="Image7" /></a>  &nbsp;
  							<a href="#" onclick="freshword_test()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','../images/ball_yellow_s.gif',1)"> <img src="../images/ball_yellow.gif" alt="模糊，不清楚" name="Image8" width="36" height="36" border="0" id="Image8" /></a> &nbsp;
  							<a href="#" onclick="freshword_test()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','../images/ball_red_s.gif',1)"><img src="../images/ball_red.gif" alt="不认识" name="Image9" width="36" height="36" border="0" id="Image9" /></a></p>
                        </c:forEach>
  						<br />
      					<br />
      					<br />
      					<br />
  					</div>
  				</div>
			</div>		
		<%@ include file="include/freshword_foot.jsp"%>
	</body>
</html>