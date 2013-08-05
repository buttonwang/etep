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
<form action="../policy/trainingPolicyTemplate.jhtml" method="post" >
	<input type="hidden" name="atype" value="edit">
	<input type="hidden" name="id" value="${trainingPolicyTemplate.id}" >
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
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模板名称：</td>
			<td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3"> ${trainingPolicyTemplate.name} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.overviewTime} </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.allowUnsureMark==1?"允许":"不允许"} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.whenToSeeAnalysis==1?"随时":""}
				${trainingPolicyTemplate.whenToSeeAnalysis==2?"做题后":""}
				${trainingPolicyTemplate.whenToSeeAnalysis==3?"正确后":""} </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.whenToCheckAnswer==1?"随时":""}
				${trainingPolicyTemplate.whenToCheckAnswer==2?"做题后":""}
				${trainingPolicyTemplate.whenToCheckAnswer==3?"正确后":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.isRandom==0?"否":"是"}</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicyTemplate.isRandomAnswerOptions==0?"否":"是"} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.rightRateForPass}% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">${trainingPolicyTemplate.rightRateRetraining}% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
				${trainingPolicyTemplate.retrainingItemType==-2?"无":""}
				${trainingPolicyTemplate.retrainingItemType==12?"错题":""}
				${trainingPolicyTemplate.retrainingItemType==11?"未答题":""} 
				${trainingPolicyTemplate.retrainingItemType==13?"错题&未答题":""} 
				${trainingPolicyTemplate.retrainingItemType==0?"零星题":""} 
				${trainingPolicyTemplate.retrainingItemType==0.5?"半星题":""} 
				${trainingPolicyTemplate.retrainingItemType==1?"一星题":""} 
				${trainingPolicyTemplate.retrainingItemType==2?"二星题":""} 
				${trainingPolicyTemplate.retrainingItemType==3?"三星题":""} 
				${trainingPolicyTemplate.retrainingItemType==4?"四星题":""} 
				${trainingPolicyTemplate.retrainingItemType==5?"五星题":""} 
				${trainingPolicyTemplate.retrainingItemType==-1?"全部":""} </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.retrainingItemOrder} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.retrainingRightRateTestFaile}
				% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==-2?"无":""}
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==12?"错题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==11?"未答题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==13?"错题&未答题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==0?"零星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==0.5?"半星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==1?"一星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==2?"二星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==3?"三星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==4?"四星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==5?"五星题":""} 
				${trainingPolicyTemplate.retrainingItemTypeTestFaile==-1?"全部":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.retrainingItemOrderTestFaile} </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.randomAssemItemsTestFaile=="1"?"是":"否"} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==-2?"无":""}
				${trainingPolicyTemplate.assemItemsTypeTestFaile==12?"错题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==11?"未答题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==13?"错题&未答题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==0?"零星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==0.5?"半星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==1?"一星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==2?"二星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==3?"三星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==4?"四星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==5?"五星题":""} 
				${trainingPolicyTemplate.assemItemsTypeTestFaile==-1?"全部":""} </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.assemItemsRateTestFaile}% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.clueContentFirstFaile} </td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.clueRelActFirstFaile==1?"高亮子题考点相关处":""}
				${trainingPolicyTemplate.clueRelActFirstFaile==2?"高亮试题":""}
				${trainingPolicyTemplate.clueRelActFirstFaile==3?"高亮子题与文章相关处":""}
				${trainingPolicyTemplate.clueRelActFirstFaile==4?"高亮选项":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.translationContentFirstFaile} </td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.translationRelActFirstFaile==1?"高亮子题考点相关处":""}
				${trainingPolicyTemplate.translationRelActFirstFaile==2?"高亮试题":""}
				${trainingPolicyTemplate.translationRelActFirstFaile==3?"高亮子题与文章相关处":""}
				${trainingPolicyTemplate.translationRelActFirstFaile==4?"高亮选项":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.clueContentSecondFaile} </td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.clueRelActSecondFaile==1?"高亮子题考点相关处":""}
				${trainingPolicyTemplate.clueRelActSecondFaile==2?"高亮试题":""}
				${trainingPolicyTemplate.clueRelActSecondFaile==3?"高亮子题与文章相关处":""}
				${trainingPolicyTemplate.clueRelActSecondFaile==4?"高亮选项":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.translationContentSecondFaile} </td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.translationRelActSecondFaile==1?"高亮子题考点相关处":""}
				${trainingPolicyTemplate.translationRelActSecondFaile==2?"高亮试题":""}
				${trainingPolicyTemplate.translationRelActSecondFaile==3?"高亮子题与文章相关处":""}
				${trainingPolicyTemplate.translationRelActSecondFaile==4?"高亮选项":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.clueContentThirdFaile} </td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.clueRelActThirdFaile==1?"高亮子题考点相关处":""}
				${trainingPolicyTemplate.clueRelActThirdFaile==2?"高亮试题":""}
				${trainingPolicyTemplate.clueRelActThirdFaile==3?"高亮子题与文章相关处":""}
				${trainingPolicyTemplate.clueRelActThirdFaile==4?"高亮选项":""} </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${trainingPolicyTemplate.translationContentThirdFaile} </td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"> ${trainingPolicyTemplate.translationRelActThirdFaile==1?"高亮子题考点相关处":""}
				${trainingPolicyTemplate.translationRelActThirdFaile==2?"高亮试题":""}
				${trainingPolicyTemplate.translationRelActThirdFaile==3?"高亮子题与文章相关处":""}
				${trainingPolicyTemplate.translationRelActThirdFaile==4?"高亮选项":""} </td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
					<tr>
						<td><input type="submit" value="  修 改  " class="btn_2k3"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="  取 消  " class="btn_2k3" onClick="javascript:window.history.back();"/></td>
					</tr>
				</table></td>
		</tr>
		<% 	if( "iShow".equals(rType)){%>
		<%}%>
	</table>
</form>
</body>
</html>
