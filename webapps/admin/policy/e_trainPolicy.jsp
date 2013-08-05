<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("tp",request.getAttribute("trainingPolicy"));%>
<%if (request.getAttribute("nid")!=null ){%>
<div h=1>
	<form action="../policy/trainingPolicy.jhtml" method="post">
	<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="tp.overviewTime" value="${tp.overviewTime}" size="10"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" name="tp.checkbox" id="checkbox" />
				<input type="text" name="tp.allowUnsureMark" id="checkbox" />
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="radio" name="tp.whenToSeeAnalysis" value="eveyTime"/>
				随时
				<input type="radio" name="tp.whenToSeeAnalysis" value="after"/>
				做题后
				<input type="radio" name="tp.whenToSeeAnalysis" value="afterRight"/>
				正确后 </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="radio" name="tp.radio" id="radio" value="radio" />
				随时
				<input type="radio" name="tp.radio" id="radio" value="radio" />
				做题后
				<input type="radio" name="tp.radio" id="radio" value="radio" />
				正确后 </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input type="checkbox" name="tp.isRandom" />
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input type="checkbox" name="tp.checkbox" id="checkbox" />
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="tp.rightRateForPass" size="10"/>
				% </td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="tp.rightRateRetraining" size="10"/>
				% </td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF"><select name="tp.retrainingItemType">
					<option value=100>全部</option>
					<option value="-1">错题</option>
					<option value="-2">未答题</option>
					<option value="-3">错题&未答题</option>
					<option value=10>零星题</option>
					<option value=16>半星题</option>
					<option value=1>一星题</option>
					<option value=2>二星题</option>
					<option value=3>三星题</option>
					<option value=4>四星题</option>
					<option value=5>五星题</option>
				</select>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF">
				
			</td>
		</tr>
		<tr>
			<td height="70" align="center" bgcolor="#FFFFFF" colspan="4"><table width="300" border="0">
				<tr>
					<td ><input type="submit" class="btn_2k3" value="  保 存  " />
						&nbsp;&nbsp;
						<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
				</tr>
			</table>
			</td>
		</tr><tr>
						<td width="265"><input type="text" name="tp.retrainingItemOrder" value="${tp.retrainingItemOrder}" >
							<input type="text" name="atype" value="update"> <input type="text" name="p.para.nid" value="${nid}">
						</td>
					</tr>
	</table>
	</form>
</div>
<%}else{%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" >节点 不存在 </td>
	</tr>
</table>
<%}%>
