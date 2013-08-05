<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<table  class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3" id="search2">
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用地区：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><select name="${_objName}.regionCode">
					<option selected="selected" value="">全部</option>
					<c:forEach items="${regionList}" var="item" varStatus="itemStatus">
						<option value="${item.code}" ${_obj.regionCode eq item.code ? 'selected="selected"':''}>${item.name}</option>
					</c:forEach>
				</select>
			</td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">所属学科：</td>
			<td align="left" bgcolor="#FFFFFF"><select name="${_objName}.subjectCode" vt=subjectCode>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用学级：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><select name="${_objName}.gradeCode" vt=subjectCode>
					<option selected="selected" value="">全部</option>
					<c:forEach items="${gradeList}" var="item" varStatus="itemStatus">
						<option value="${item.code}" ${_obj.gradeCode eq item.code ? 'selected="selected"':''}>${item.levelFlag}${item.name}/${item.code}</option>
					</c:forEach>
				</select>
			</td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题型：</td>
			<td align="left" bgcolor="#FFFFFF"><select name="${_objName}.itemTypeCode">
					<option selected="selected" value="">全部</option>
					<c:forEach items="${itemTypeList}" var="item" varStatus="itemStatus">
						<option value="${item.code}" ${_obj.itemTypeCode eq item.code ? 'selected="selected"':''}>${item.code}(${item.name})</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题年份：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><span n="${_objName}.year">
				<input v=1 value="" type="text"  size="3" />
				&nbsp;—
				<input v=2 value="" type="text"  size="3" />
				</span>
				<input type="hidden" name="${_objName}.year" value="${_obj.year}">
			</td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题来源：</td>
			<td align="left" bgcolor="#FFFFFF"><c:forEach items="${itemSourceList}" var="item" varStatus="itemStatus">
					<input name="${_objName}.source"  type="checkbox" ${fn:contains(_obj.source, item.value)?'checked="checked"':''} 
    			 value="${item.value}" />
					${item}&nbsp; </c:forEach>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题难度：</td>
			<td align="left" valign="top" bgcolor="#FFFFFF"><span n="${_objName}.difficultyValue">
				<input v=1 value="" type="text"  size="3" />
				&nbsp;—
				<input n="${_objName}.difficultyValue" v=2 value="" type="text"  size="3" />
				</span>
				<input type="hidden" name="${_objName}.difficultyValue" value="${_obj.difficultyValue}"  size="3" />
			</td>
			<td align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">试题效度：</td>
			<td align="left" bgcolor="#FFFFFF"><span n="${_objName}.validityValue">
				<input v=1 value="" type="text" size="3" />
				&nbsp;—
				<input v=2 value="" type="text" size="3" />
				</span>
				<input type="hidden" name="${_objName}.validityValue" value="${_obj.validityValue}" size="3" />
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题数： </td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="${_objName}.amount" value="${_obj.amount}"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">原始套卷：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><input class="logininputmanage" type="text" name="${_objName}.originalPaperCode" value="${_obj.originalPaperCode}"/>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">直观评价： </td>
			<td width="33%" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="${_objName}.opinion" value="${_obj.opinion}"/>
			</td>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">适用对象：</td>
			<td width="33%" align="left" bgcolor="#FFFFFF"><select class="logininputmanage"  name="${_objName}.applicableObject">
					<option value='1' ${_obj.applicableObject==1?'selected':''}>文科</option>
					<option value='2' ${_obj.applicableObject==2?'selected':''}>理科</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">知识点： </td>
			<td width="33%" colspan="3" align="left" valign="top" bgcolor="#FFFFFF" ><input class="logininputmanage" type="text" name="${_objName}.knowledgePointCode" value="${_obj.knowledgePointCode}"/>
			</td>
		</tr>
		<tr>
			<td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table border="0" width="100%">
					<tr>
						<td align="center"><input type="submit" value="  保 存  " class="btn_2k3"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="  取 消  " class="btn_2k3" ${_cancleButtonEvent} />
						</td>
					</tr>
				</table></td>
		</tr>
	</table>