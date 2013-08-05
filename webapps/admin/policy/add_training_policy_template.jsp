<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
<script src="../js/all/ValidateForm.js"></script>
<script>
j(function(){
	ValidateForm({
		"fid":"add_training_policy_template_form"
		,"vs":[
			{n:"trainingPolicyTemplate.name",sn:"模块名称"}
			,{n:"trainingPolicyTemplate.overviewTime",sn:"全卷预览时间"}
		]
	});
	j("div[n!=]").each(function(){
		var jdiv = j(this);
		var iname = j(this).attr("n");
		var jselects = jdiv.find("select");
		var size = jselects.size();
		var varry = (j("input[name="+iname+"]").val()||"").split(",");
		jselects.each(function(vi){
			var v = varry[vi]||"无";
			j(this).find("option").each(function(){
				if(j(this).html()==v){
					j(this).attr("selected",true);
				}
			})
		})
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
	j("input[n!=]").each(function(){
		eval("var data="+j(this).attr("vs"));
		j(this).click(function(){
			var vArr = data.v.split(",");
			var iname = j(this).attr("n");
			if(j(this).attr("checked")==true){
				j("input[name="+iname+"]").val(vArr[1]);
			}else{
				j("input[name="+iname+"]").val(vArr[0]);
			}
		})
	})
})
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="location">当前位置：训练引擎 &gt; 策略模板 &gt; 新增训练策略模板</td>
	</tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
	<form action="../policy/trainingPolicyTemplate.jhtml" method="post" id="add_training_policy_template_form">
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">模板名称：</td>
			<td width="83%" align="left" valign="top" bgcolor="#FFFFFF" colspan="3"><input class="logininputmanage" type="text" name="trainingPolicyTemplate.name" size="40"/>
			</td>
		</tr>
		<input type="hidden" name="atype" value="add">
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicyTemplate.overviewTime"  value="${trainingPolicyTemplate.overviewTime}" size="10"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicyTemplate.allowUnsureMark' vs="{'v':'0,1','s':'不允许,允许','rv':'0'}"  ${trainingPolicyTemplate.allowUnsureMark==1?"checked":""} />
				<input type="hidden" vvv=vvv name="trainingPolicyTemplate.allowUnsureMark" value="${trainingPolicyTemplate.allowUnsureMark}">
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicyTemplate.whenToSeeAnalysis" value="1" ${trainingPolicyTemplate.whenToSeeAnalysis==1?"checked":""} />
				随时
				<input type="radio" name="trainingPolicyTemplate.whenToSeeAnalysis" value="2" ${trainingPolicyTemplate.whenToSeeAnalysis==2?"checked":""} />
				做题后
				<input type="radio" name="trainingPolicyTemplate.whenToSeeAnalysis" value="3" ${trainingPolicyTemplate.whenToSeeAnalysis==3?"checked":""} />
				正确后 </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="radio" name="trainingPolicyTemplate.whenToCheckAnswer" value="1" ${trainingPolicyTemplate.whenToCheckAnswer==1?"checked":""} />
				随时
				<input type="radio" name="trainingPolicyTemplate.whenToCheckAnswer" value="2" ${trainingPolicyTemplate.whenToCheckAnswer==2?"checked":""} />
				做题后
				<input type="radio" name="trainingPolicyTemplate.whenToCheckAnswer" value="3" ${trainingPolicyTemplate.whenToCheckAnswer==3?"checked":""} />
				正确后 </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicyTemplate.isRandom' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicyTemplate.isRandom==1?"checked":""} />
				<input type="hidden" vvv=vvv name="trainingPolicyTemplate.isRandom" value="${trainingPolicyTemplate.isRandom}"></td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicyTemplate.isRandomAnswerOptions' vs="{'v':'0,1','s':'否,是','rv':'0'}"  ${trainingPolicyTemplate.isRandomAnswerOptions==1?"checked":""}   />
				<input type="hidden" vvv=vvv name="trainingPolicyTemplate.isRandomAnswerOptions" value="${trainingPolicyTemplate.isRandomAnswerOptions}">
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicyTemplate.rightRateForPass" value="${trainingPolicyTemplate.rightRateForPass}" size="10"/>
				% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicyTemplate.rightRateRetraining" value="${trainingPolicyTemplate.rightRateRetraining}" size="10"/>
				% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.retrainingItemType">
					<option value="-2" ${trainingPolicyTemplate.assemItemsTypeTestFaile==-2?"selected":""} >无</option>
					<option value="12" ${trainingPolicyTemplate.retrainingItemType==12?"selected":""} >错题</option>
					<option value="11" ${trainingPolicyTemplate.retrainingItemType==11?"selected":""} >未答题</option>
					<option value="13" ${trainingPolicyTemplate.retrainingItemType==13?"selected":""} >错题&未答题</option>
					<option value="0" ${trainingPolicyTemplate.retrainingItemType==0?"selected":""} >零星题</option>
					<option value="0.5" ${trainingPolicyTemplate.retrainingItemType==0.5?"selected":""} >半星题</option>
					<option value="1" ${trainingPolicyTemplate.retrainingItemType==1?"selected":""} >一星题</option>
					<option value="2" ${trainingPolicyTemplate.retrainingItemType==2?"selected":""} >二星题</option>
					<option value="3" ${trainingPolicyTemplate.retrainingItemType==3?"selected":""} >三星题</option>
					<option value="4" ${trainingPolicyTemplate.retrainingItemType==4?"selected":""} >四星题</option>
					<option value="5" ${trainingPolicyTemplate.retrainingItemType==5?"selected":""} >五星题</option>
					<option value="-1" ${trainingPolicyTemplate.retrainingItemType==-1?"selected":""} >全部</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><div n="trainingPolicyTemplate.retrainingItemOrder">
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
					<input type="hidden" vvv=vvv name="trainingPolicyTemplate.retrainingItemOrder" value="${trainingPolicyTemplate.retrainingItemOrder}"/>
				</div></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicyTemplate.retrainingRightRateTestFaile"  value="${trainingPolicyTemplate.retrainingRightRateTestFaile}"size="10"/>
				% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.retrainingItemTypeTestFaile">
					<option value="-2" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==-2?"selected":""} >无</option>
					<option value="12" ${(trainingPolicyTemplate.retrainingItemTypeTestFaile==12)?"selected":""} >错题</option>
					<option value="11" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==11?"selected":""} >未答题</option>
					<option value="13" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==13?"selected":""} >错题&未答题</option>
					<option value="0" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==0?"selected":""} >零星题</option>
					<option value="0.5" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==0.5?"selected":""} >半星题</option>
					<option value="1" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==1?"selected":""} >一星题</option>
					<option value="2" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==2?"selected":""} >二星题</option>
					<option value="3" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==3?"selected":""} >三星题</option>
					<option value="4" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==4?"selected":""} >四星题</option>
					<option value="5" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==5?"selected":""} >五星题</option>
					<option value="-1" ${trainingPolicyTemplate.retrainingItemTypeTestFaile==-1?"selected":""} >全部</option>
				</select></td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><div n="trainingPolicyTemplate.retrainingItemOrderTestFaile">
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
					<input type="hidden" vvv=vvv name="trainingPolicyTemplate.retrainingItemOrderTestFaile" value="${trainingPolicyTemplate.retrainingItemOrderTestFaile}"/>
				</div></td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过是否动态出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" n='trainingPolicyTemplate.randomAssemItemsTestFaile' vs="{'v':'0,1','s':'不随机,随机','rv':'0'}"  ${trainingPolicyTemplate.randomAssemItemsTestFaile==1?"checked":""}   />
				<input type="hidden" vvv=vvv name="trainingPolicyTemplate.randomAssemItemsTestFaile" value="${trainingPolicyTemplate.randomAssemItemsTestFaile}">
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题类型：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.assemItemsTypeTestFaile">
					<option value="-2"  ${trainingPolicyTemplate.assemItemsTypeTestFaile==-2?"selected":""} >无</option>
					<option value="12"  ${trainingPolicyTemplate.assemItemsTypeTestFaile==12?"selected":""} >错题</option>
					<option value="11"  ${trainingPolicyTemplate.assemItemsTypeTestFaile==11?"selected":""} >未答题</option>
					<option value="13"  ${trainingPolicyTemplate.assemItemsTypeTestFaile==13?"selected":""} >错题&未答题</option>
					<option value="0"   ${trainingPolicyTemplate.assemItemsTypeTestFaile==0?"selected":""} >零星题</option>
					<option value="0.5" ${trainingPolicyTemplate.assemItemsTypeTestFaile==0.5?"selected":""} >半星题</option>
					<option value="1"   ${trainingPolicyTemplate.assemItemsTypeTestFaile==1?"selected":""} >一星题</option>
					<option value="2"   ${trainingPolicyTemplate.assemItemsTypeTestFaile==2?"selected":""} >二星题</option>
					<option value="3"   ${trainingPolicyTemplate.assemItemsTypeTestFaile==3?"selected":""} >三星题</option>
					<option value="4"   ${trainingPolicyTemplate.assemItemsTypeTestFaile==4?"selected":""} >四星题</option>
					<option value="5"   ${trainingPolicyTemplate.assemItemsTypeTestFaile==5?"selected":""} >五星题</option>
					<option value="-1"  ${trainingPolicyTemplate.assemItemsTypeTestFaile==-1?"selected":""} >全部</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">测试未通过动态出题比率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="trainingPolicyTemplate.assemItemsRateTestFaile" value="${trainingPolicyTemplate.assemItemsRateTestFaile}" size="10"/>
				% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicyTemplate.clueContentFirstFaile" id="textarea" cols="35" rows="2">${trainingPolicyTemplate.clueContentFirstFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				<select name="trainingPolicyTemplate.clueRelActFirstFaile">
					<option value="0" selected >无</option>
					<option value="1" ${trainingPolicyTemplate.clueRelActFirstFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicyTemplate.clueRelActFirstFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicyTemplate.clueRelActFirstFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicyTemplate.clueRelActFirstFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicyTemplate.clueRelActFirstFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicyTemplate.translationContentFirstFaile" id="textarea" cols="35" rows="2">${trainingPolicyTemplate.translationContentFirstFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第一次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.translationRelActFirstFaile">
					<option value="0" selected >无</option>
					<option value="1" ${trainingPolicyTemplate.translationRelActFirstFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicyTemplate.translationRelActFirstFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicyTemplate.translationRelActFirstFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicyTemplate.translationRelActFirstFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicyTemplate.translationRelActFirstFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicyTemplate.clueContentSecondFaile" id="textarea" cols="35" rows="2">${trainingPolicyTemplate.clueContentSecondFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.clueRelActSecondFaile">
					<option value="0" selected >无</option>
					<option value="1" ${trainingPolicyTemplate.clueRelActSecondFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicyTemplate.clueRelActSecondFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicyTemplate.clueRelActSecondFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicyTemplate.clueRelActSecondFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicyTemplate.clueRelActSecondFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicyTemplate.translationContentSecondFaile" id="textarea" cols="35" rows="2">${trainingPolicyTemplate.translationContentSecondFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第二次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.translationRelActSecondFaile">
					<option value="0" selected >无</option>
					<option value="1" ${trainingPolicyTemplate.translationRelActSecondFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicyTemplate.translationRelActSecondFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicyTemplate.translationRelActSecondFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicyTemplate.translationRelActSecondFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicyTemplate.translationRelActSecondFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicyTemplate.clueContentThirdFaile" id="textarea" cols="35" rows="2">${trainingPolicyTemplate.clueContentThirdFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错提示相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.clueRelActThirdFaile">
					<option value="0" selected >无</option>
					<option value="1" ${trainingPolicyTemplate.clueRelActThirdFaile==1?"selected":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicyTemplate.clueRelActThirdFaile==2?"selected":""}>高亮试题</option>
					<option value="3" ${trainingPolicyTemplate.clueRelActThirdFaile==3?"selected":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicyTemplate.clueRelActThirdFaile==4?"selected":""}>高亮选项</option>
					<option value="5" ${trainingPolicyTemplate.clueRelActThirdFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文内容：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><textarea name="trainingPolicyTemplate.translationContentThirdFaile" id="textarea" cols="35" rows="2">${trainingPolicyTemplate.translationContentThirdFaile}</textarea>
			</td>
			<td width="17%" align="right" valign="middle" bgcolor="#F7F7F7"  class="txt12blue">第三次答错译文相关动作：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select name="trainingPolicyTemplate.translationRelActThirdFaile">
					<option value="0" selected >无</option>
					<option value="1" ${trainingPolicyTemplate.translationRelActThirdFaile==1?"checked":""}>高亮子题考点相关处</option>
					<option value="2" ${trainingPolicyTemplate.translationRelActThirdFaile==2?"checked":""}>高亮试题</option>
					<option value="3" ${trainingPolicyTemplate.translationRelActThirdFaile==3?"checked":""}>高亮子题与文章相关处</option>
					<option value="4" ${trainingPolicyTemplate.translationRelActThirdFaile==4?"checked":""}>高亮选项</option>
					<option value="5" ${trainingPolicyTemplate.translationRelActThirdFaile==5?"selected":""}>查看答案</option>
				</select>
			</td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
					<tr>
						<td ><input type="submit" class="btn_2k3" value="  保 存  " />
							&nbsp;&nbsp;
							<input type="button" value="  取 消  "  onClick="javascript:window.history.back()"class="btn_2k3"/></td>
					</tr>
				</table></td>
		</tr>
	</form>
</table>
</body>
</html>
