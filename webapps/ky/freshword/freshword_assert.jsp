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
			function freshword_assertsok(newid){
				if(tag)
					return;
				tag=true;
				var newaction ="freshWordConfig!newWordOk.jhtml";
				document.newform.wordid.value=newid;
				var tmpNo=document.newform.pageNo.value;
				if(tmpNo*1<${newpage.totalCount}){
					document.newform.pageNo.value=tmpNo*1+1;
				}else{
					newaction="freshWordConfig!newWordOver.jhtml?isOk=true";
				}
				document.newform.action=newaction;
				document.newform.submit();				
			}
			function freshword_test(){
				if(tag)
					return;
				tag2=true;
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
		<input name="wordid" type="hidden" value=""/>
	</form>
		<%@ include file="include/freshword_top.jsp"%>
 <!-- 页面主题 -->
<div id="main" class="wm2 bianbf" >
	<div class="newword">
	<div class="newword_cs f14px">
	  中文意思和你想得一致吗？一致点击绿色对号，不一致点击红色叉号。
	</div>
    <div class="newword_cs bianf3">
      规则：    <img src="../images/ball_ok_s.gif" width="16" height="16" align="absmiddle" />代表“和我想得一致” <img src="../images/ball_error_s.gif" width="16" height="16" />代表“和我想得不一致”。。
      <div style="height:200px;">
	  <c:forEach items="${newpage.result}" var="page">
      <p class="danci f32px Arial">${page.wordExtension.wordBasic.word}</p>
      <p class="danci"><c:forEach items="${page.wordExtension.wordTypes}" var="newtype"><span class="f14px">${newtype.code}<c:forEach items="${newtype.chineseMeanings}" var="newchin">${newchin.meaning}  </c:forEach></span><br/></c:forEach></p>
  
   </div>
  
    <p><a href="#" onclick="freshword_assertsok('${page.id}')"><img src="../images/ball_ok.gif" width="36" height="36" /></a>&nbsp;&nbsp;<a href="#" onclick="freshword_test()"><img src="../images/ball_error.gif" width="36" height="36" /></a></p>
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