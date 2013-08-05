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
		<script type="text/javascript">
			function freshword_asserts(){
				document.newform.action="freshWordConfig!asserts.jhtml";
				document.newform.submit();
			}
			function freshword_test(){
				var newaction ="freshWordConfig!newWordNo.jhtml";
				var tmpNo=document.newform.pageNo.value;
				if(tmpNo*1+1<=${newpage.totalCount}){
					document.newform.pageNo.value=tmpNo*1+1;

				}else{
					newaction="freshWordConfig!newWordOver.jhtml";
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
				学习已经完成
  				</div>
			</div>		
		<%@ include file="include/freshword_foot.jsp"%>
	</body>
</html>