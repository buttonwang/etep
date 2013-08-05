<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script>
j(function(){
	j("input[n!=]").each(function(){
		eval("var data="+j(this).attr("vs"));
		j(this).click(function(){
			var vArr = data.v.split(",");
			var iname = j(this).attr("n");
			if(j(this).attr("checked")==true){
				j("input[name="+iname+"]").val(vArr[1])
			}else{
				j("input[name="+iname+"]").val(vArr[0])
			}
		})
	})
	
	j("div[n!=]").each(function(){
		var jdiv = j(this);
		var iname = j(this).attr("n");
		var jselects = jdiv.find("select");
		var size = jselects.size();
		jselects.change(function(){
			var html ="";
			if(size<=1){
				html =jselects.val();
			}else{
				jselects.each(function(i){
					html += j(this).val();					
					if(i>=0&&i<size-1){
						html += ",";
					}
				})
				j("input[name="+iname+"]").val(html)
			}
		})
	})
})
</script>
</head>
<body>
<form action="../policy/trainingPolicy.jhtml" method="post" >
	<input type="hidden" name="atype" value="update">
	<input type="hidden" name="trainingPolicy.nodeId" value="${trainingPolicy.nodeId}">
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
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.overviewTime"  value="${trainingPolicy.overviewTime}" size="10"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.allowUnsureMark' vs="{'v':'0,1','s':'不允许,允许','rv':'0'}"  ${trainingPolicy.allowUnsureMark==1?"checked":""} />
				<input type="text" name="trainingPolicy.allowUnsureMark" value="${trainingPolicy.allowUnsureMark}">
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="1" ${trainingPolicy.whenToSeeAnalysis==1?"checked":""} />
				随时
				<input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="2" ${trainingPolicy.whenToSeeAnalysis==2?"checked":""} />
				做题后
				<input type="radio" name="trainingPolicy.whenToSeeAnalysis" value="3" ${trainingPolicy.whenToSeeAnalysis==3?"checked":""} />
				正确后 </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicy.whenToCheckAnswer" value="1" ${trainingPolicy.whenToCheckAnswer==1?"checked":""} />
				随时
				<input type="radio" name="trainingPolicy.whenToCheckAnswer" value="2" ${trainingPolicy.whenToCheckAnswer==2?"checked":""} />
				做题后
				<input type="radio" name="trainingPolicy.whenToCheckAnswer" value="3" ${trainingPolicy.whenToCheckAnswer==3?"checked":""} />
				正确 </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.isRandom' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicy.isRandom==1?"checked":""} />
				<input type="text" name="trainingPolicy.isRandom" value="${trainingPolicy.isRandom}"></td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicy.isRandomAnswerOptions' vs="{'v':'0,1','s':'不颠倒,颠倒','rv':'0'}"  ${trainingPolicy.isRandomAnswerOptions==1?"checked":""}   />
				<input type="text" name="trainingPolicy.isRandomAnswerOptions" value="${trainingPolicy.isRandomAnswerOptions}">
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.rightRateForPass" value="${trainingPolicy.rightRateForPass}" size="10"/>
				% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.rightRateRetraining" value="${trainingPolicy.rightRateRetraining}" size="10"/>
				% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="trainingPolicy.retrainingItemType">
					<option value="12" ${trainingPolicy.retrainingItemType==12?"selected":""} >错题</option>
					<option value="11" ${trainingPolicy.retrainingItemType==11?"selected":""} >未答题</option>
					<option value="13" ${trainingPolicy.retrainingItemType==13?"selected":""} >错题&未答题</option>
					<option value="0" ${trainingPolicy.retrainingItemType==0?"selected":""} >零星题</option>
					<option value="0.5" ${trainingPolicy.retrainingItemType==0.5?"selected":""} >半星题</option>
					<option value="1" ${trainingPolicy.retrainingItemType==1?"selected":""} >一星题</option>
					<option value="2" ${trainingPolicy.retrainingItemType==2?"selected":""} >二星题</option>
					<option value="3" ${trainingPolicy.retrainingItemType==3?"selected":""} >三星题</option>
					<option value="4" ${trainingPolicy.retrainingItemType==4?"selected":""} >四星题</option>
					<option value="5" ${trainingPolicy.retrainingItemType==5?"selected":""} >五星题</option>
					<option value="-1" ${trainingPolicy.retrainingItemType==-1?"selected":""} >全部</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			
			<div n="trainingPolicy.retrainingItemOrder">
				<select >
					<option>无</option>
					<option>错题</option>
					<option>新题</option>
					<option>星题</option>
				</select>
				<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
				<select >
					<option>无</option>
					<option>错题</option>
					<option>新题</option>
					<option>星题</option>
				</select>
				<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
				<select >
					<option>无</option>
					<option>错题</option>
					<option>新题</option>
					<option>星题</option>
				</select>
				<input  name="trainingPolicy.retrainingItemOrder" value="${trainingPolicy.retrainingItemOrder}"/>
				</div>
			
			
			 
				
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.retrainingRightRateTestFaile"  value="${trainingPolicy.retrainingRightRateTestFaile}"size="10"/>
% 				</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.retrainingItemTypeTestFaile">
					<option value="12" ${trainingPolicy.retrainingItemType==12?"selected":""} >错题</option>
					<option value="11" ${trainingPolicy.retrainingItemTypeTestFaile==11?"selected":""} >未答题</option>
					<option value="13" ${trainingPolicy.retrainingItemTypeTestFaile==13?"selected":""} >错题&未答题</option>
					<option value="0" ${trainingPolicy.retrainingItemTypeTestFaile==0?"selected":""} >零星题</option>
					<option value="0.5" ${trainingPolicy.retrainingItemTypeTestFaile==0.5?"selected":""} >半星题</option>
					<option value="1" ${trainingPolicy.retrainingItemTypeTestFaile==1?"selected":""} >一星题</option>
					<option value="2" ${trainingPolicy.retrainingItemTypeTestFaile==2?"selected":""} >二星题</option>
					<option value="3" ${trainingPolicy.retrainingItemTypeTestFaile==3?"selected":""} >三星题</option>
					<option value="4" ${trainingPolicy.retrainingItemTypeTestFaile==4?"selected":""} >四星题</option>
					<option value="5" ${trainingPolicy.retrainingItemTypeTestFaile==5?"selected":""} >五星题</option>
					<option value="-1" ${trainingPolicy.retrainingItemTypeTestFaile==-1?"selected":""} >全部</option>
				</select></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
			<div n="trainingPolicy.retrainingItemOrderTestFaile">
				<select >
					<option>无</option>
					<option>错题</option>
					<option>新题</option>
					<option>星题</option>
				</select>
				<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
				<select >
					<option>无</option>
					<option>错题</option>
					<option>新题</option>
					<option>星题</option>
				</select>
				<img src="../images/arrow3.gif" width="11" height="9" align="absmiddle">
				<select >
					<option>无</option>
					<option>错题</option>
					<option>新题</option>
					<option>星题</option>
				</select>
				<input  name="trainingPolicy.retrainingItemOrderTestFaile" value="${trainingPolicy.retrainingItemOrderTestFaile}"/>
				</div>
				
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF">
			<input type="checkbox" n='trainingPolicy.randomAssemItemsTestFaile' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicy.randomAssemItemsTestFaile==1?"checked":""}   />
				<input type="text" name="trainingPolicy.randomAssemItemsTestFaile" value="${trainingPolicy.randomAssemItemsTestFaile}">
			</td>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.assemItemsTypeTestFaile">
					<option value="12" ${trainingPolicy.assemItemsTypeTestFaile==12?"selected":""} >错题</option>
					<option value="11" ${trainingPolicy.assemItemsTypeTestFaile==11?"selected":""} >未答题</option>
					<option value="13" ${trainingPolicy.assemItemsTypeTestFaile==13?"selected":""} >错题&未答题</option>
					<option value="0" ${trainingPolicy.assemItemsTypeTestFaile==0?"selected":""} >零星题</option>
					<option value="0.5" ${trainingPolicy.assemItemsTypeTestFaile==0.5?"selected":""} >半星题</option>
					<option value="1" ${trainingPolicy.assemItemsTypeTestFaile==1?"selected":""} >一星题</option>
					<option value="2" ${trainingPolicy.assemItemsTypeTestFaile==2?"selected":""} >二星题</option>
					<option value="3" ${trainingPolicy.assemItemsTypeTestFaile==3?"selected":""} >三星题</option>
					<option value="4" ${trainingPolicy.assemItemsTypeTestFaile==4?"selected":""} >四星题</option>
					<option value="5" ${trainingPolicy.assemItemsTypeTestFaile==5?"selected":""} >五星题</option>
					<option value="-1" ${trainingPolicy.assemItemsTypeTestFaile==-1?"selected":""} >全部</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicy.assemItemsRateTestFaile" value="${trainingPolicy.assemItemsRateTestFaile}" size="10"/>
				% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.clueContentFirstFaile" id="textarea" cols="35" rows="2">${trainingPolicy.clueContentFirstFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.clueRelActFirstFaile">
					<option value="1" ${trainingPolicy.clueRelActFirstFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicy.clueRelActFirstFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicy.clueRelActFirstFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicy.clueRelActFirstFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicy.clueRelActFirstFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.translationContentFirstFaile" id="textarea" cols="35" rows="2">${trainingPolicy.translationContentFirstFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.translationRelActFirstFaile">
					<option value="1" ${trainingPolicy.translationRelActFirstFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicy.translationRelActFirstFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicy.translationRelActFirstFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicy.translationRelActFirstFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicy.translationRelActFirstFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.clueContentSecondFaile" id="textarea" cols="35" rows="2">${trainingPolicy.clueContentSecondFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.clueRelActSecondFaile">
					<option value="1" ${trainingPolicy.clueRelActSecondFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicy.clueRelActSecondFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicy.clueRelActSecondFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicy.clueRelActSecondFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicy.clueRelActSecondFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.translationContentSecondFaile" id="textarea" cols="35" rows="2">${trainingPolicy.translationContentSecondFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.translationRelActSecondFaile">
					<option value="1" ${trainingPolicy.translationRelActSecondFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicy.translationRelActSecondFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicy.translationRelActSecondFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicy.translationRelActSecondFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicy.translationRelActSecondFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.clueContentThirdFaile" id="textarea" cols="35" rows="2">${trainingPolicy.clueContentThirdFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.clueRelActThirdFaile">
					<option value="1" ${trainingPolicy.clueRelActThirdFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicy.clueRelActThirdFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicy.clueRelActThirdFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicy.clueRelActThirdFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicy.clueRelActFirstFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicy.translationContentThirdFaile" id="textarea" cols="35" rows="2">${trainingPolicy.translationContentThirdFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicy.translationRelActThirdFaile">
					<option value="1" ${trainingPolicy.translationRelActThirdFaile==1?"checked":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicy.translationRelActThirdFaile==2?"checked":""}>高亮试题</option>
					<option value="3" ${trainingPolicy.translationRelActThirdFaile==3?"checked":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicy.translationRelActThirdFaile==4?"checked":""}>高亮选项</option>
					<option value="5" ${trainingPolicy.translationRelActThirdFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
				<tr>
					<td ><input type="submit" class="btn_2k3" value="  保 存  " />
						&nbsp;&nbsp;
						<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
				</tr>
			</table></td>
		</tr>
	</table>
</form>
</body>
</html>
