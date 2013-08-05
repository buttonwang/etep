<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
</head>
<body>
<script src="../js/m.js"></script>
<script src="../js/ParentIframeHeight.js"></script>
<script>
j(function(){
	//new ParentIframeHeight("s_trainPolicy",j("body").height()+20);
});
</script>
<form action="../policy/trainingPolicy.jhtml" method="post" >
	<input type="hidden" name="atype" value="edit">
	<input type="hidden" name="id" value="${trainingPolicy.nodeId}">
<%String rType =(String) request.getAttribute("rType");
	if( "iShow".equals(rType)){
	
%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 新增训练策略模板</td>
		</tr>
	</table>
<%	}
%>
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		 
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.overviewTime}
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			${trainingPolicy.allowUnsureMark==1?"允许":"不允许"}
				 
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
			${trainingPolicy.whenToSeeAnalysis==1?"随时":""}
			${trainingPolicy.whenToSeeAnalysis==2?"做题后":""}
			${trainingPolicy.whenToSeeAnalysis==3?"正确":""}
		 	</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				${trainingPolicy.whenToCheckAnswer==1?"随时":""}
				${trainingPolicy.whenToCheckAnswer==2?"做题后":""}
				${trainingPolicy.whenToCheckAnswer==3?"正确":""}
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.isRandom==0?"不随机":"随机"}</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.isRandomAnswerOptions==0?"不颠倒":"颠倒"} 
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.rightRateForPass}% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicy.rightRateRetraining}% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
					${trainingPolicy.retrainingItemType==12?"错题":""} 
					${trainingPolicy.retrainingItemType==12?"错题":""} 
					${trainingPolicy.retrainingItemType==11?"未答题":""} 
					${trainingPolicy.retrainingItemType==13?"错题&未答题":""} 
					${trainingPolicy.retrainingItemType==0?"零星题":""} 
					${trainingPolicy.retrainingItemType==0.5?"半星题":""} 
					${trainingPolicy.retrainingItemType==1?"一星题":""} 
					${trainingPolicy.retrainingItemType==2?"二星题":""} 
					${trainingPolicy.retrainingItemType==3?"三星题":""} 
					${trainingPolicy.retrainingItemType==4?"四星题":""} 
					${trainingPolicy.retrainingItemType==5?"五星题":""} 
					${trainingPolicy.retrainingItemType==-1?"全部":""} 
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.retrainingItemOrder} 
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.retrainingRightRateTestFaile}
% 				</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> 
					${trainingPolicy.retrainingItemTypeTestFaile==12?"错题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==12?"错题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==11?"未答题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==13?"错题&未答题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==0?"零星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==0.5?"半星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==1?"一星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==2?"二星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==3?"三星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==4?"四星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==5?"五星题":""} 
					${trainingPolicy.retrainingItemTypeTestFaile==-1?"全部":""}
				 </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicy.retrainingItemOrderTestFaile} 
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.randomAssemItemsTestFaile=="1"?"是":"否"}
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> 
				${trainingPolicy.assemItemsTypeTestFaile==12?"错题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==12?"错题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==11?"未答题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==13?"错题&未答题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==0?"零星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==0.5?"半星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==1?"一星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==2?"二星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==3?"三星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==4?"四星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==5?"五星题":""} 
					${trainingPolicy.assemItemsTypeTestFaile==-1?"全部":""}
				 
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.assemItemsRateTestFaile}% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.clueContentFirstFaile}
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> 
			${trainingPolicy.clueRelActFirstFaile==1?"高亮子题考点相关处":""}
			${trainingPolicy.clueRelActFirstFaile==2?"高亮试题":""}
			${trainingPolicy.clueRelActFirstFaile==3?"高亮子题与文章相关处":""}
			${trainingPolicy.clueRelActFirstFaile==4?"高亮选项":""}
			${trainingPolicy.clueRelActFirstFaile==5?"查看答案":""}
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.translationContentFirstFaile}
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			${trainingPolicy.translationRelActFirstFaile==1?"高亮子题考点相关处":""}
			${trainingPolicy.translationRelActFirstFaile==2?"高亮试题":""}
			${trainingPolicy.translationRelActFirstFaile==3?"高亮子题与文章相关处":""}
			${trainingPolicy.translationRelActFirstFaile==4?"高亮选项":""}
			${trainingPolicy.translationRelActFirstFaile==5?"查看答案":""}
			
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.clueContentSecondFaile}
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			${trainingPolicy.clueRelActSecondFaile==1?"高亮子题考点相关处":""}
			${trainingPolicy.clueRelActSecondFaile==2?"高亮试题":""}
			${trainingPolicy.clueRelActSecondFaile==3?"高亮子题与文章相关处":""}
			${trainingPolicy.clueRelActSecondFaile==4?"高亮选项":""}
			${trainingPolicy.clueRelActSecondFaile==5?"查看答案":""}
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.translationContentSecondFaile}
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			${trainingPolicy.translationRelActSecondFaile==1?"高亮子题考点相关处":""}
			${trainingPolicy.translationRelActSecondFaile==2?"高亮试题":""}
			${trainingPolicy.translationRelActSecondFaile==3?"高亮子题与文章相关处":""}
			${trainingPolicy.translationRelActSecondFaile==4?"高亮选项":""}
			${trainingPolicy.translationRelActSecondFaile==5?"查看答案":""}
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.clueContentThirdFaile} 
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			${trainingPolicy.clueRelActThirdFaile==1?"高亮子题考点相关处":""}
			${trainingPolicy.clueRelActThirdFaile==2?"高亮试题":""}
			${trainingPolicy.clueRelActThirdFaile==3?"高亮子题与文章相关处":""}
			${trainingPolicy.clueRelActThirdFaile==4?"高亮选项":""}
			${trainingPolicy.clueRelActThirdFaile==5?"查看答案":""}
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicy.translationContentThirdFaile}
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			${trainingPolicy.translationRelActThirdFaile==1?"高亮子题考点相关处":""}
			${trainingPolicy.translationRelActThirdFaile==2?"高亮试题":""}
			${trainingPolicy.translationRelActThirdFaile==3?"高亮子题与文章相关处":""}
			${trainingPolicy.translationRelActThirdFaile==4?"高亮选项":""}
			${trainingPolicy.translationRelActThirdFaile==5?"查看答案":""}
			</td>
		</tr>
<% 	if( "iShow".equals(rType)){%>
<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF">

				<table width="300" border="0">
					<tr>
						<td><input type="submit" value="  修 改  " class="btn_2k3"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="  取 消  " class="btn_2k3"/></td>
					</tr>
				</table></td>
		</tr>
<%}%>
	</table>
</form>
</body>
</html>
