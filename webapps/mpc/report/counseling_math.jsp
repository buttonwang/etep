<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC" class="dtable">
    <tr bgcolor="#eeeeee">
      <td width="238" rowspan="2" align="center"><strong>章名</strong></td> 
      <td height="27" colspan="3" align="center"><strong>班级整体学习情况</strong></td>
      <td colspan="5" align="center"><strong>试题统计（点击试题数量查看详情、导出试题）</strong></td>
      </tr>
  <tr bgcolor="#ffffff">
    <td height="27" align="center">平均学习进度</td>
    <td align="center">平均掌握度</td>
    <td align="center">平均正确率</td>
    <td align="center">★★★★★</td>
    <td align="center">★★★★</td>
    <td align="center">★★★</td>
    <td align="center">错题数</td>
    <td align="center">疑问题</td>
  </tr>
  <c:forEach items="${groupList}" var="group" varStatus="groupStatus">
  <tr bgcolor="#ffffff">
    <td height="27" align="center">${group['node_name']}</td>
    <td align="center">${group['learning_process_rate']}%</td>
    <td align="center">${group['mastery_rate']}%</td>
    <td align="center" >${group['accuracy_rate']}%</td>
    <td align="center" >
    <c:if test="${group['sum_five_star_items']==null || group['sum_five_star_items']==0}">
    --
    </c:if>
    <c:if test="${group['sum_five_star_items']!=null && group['sum_five_star_items']!=0}">
    <a href="javascript:counselingDetail(${group['node_id']}, 5)">${group['sum_five_star_items']}</a>
    </c:if>
    </td>
    <td align="center" >
    <c:if test="${group['sum_four_star_items']==null || group['sum_four_star_items']==0}">
    --
    </c:if>
    <c:if test="${group['sum_four_star_items']!=null && group['sum_four_star_items']!=0}">
    <a href="#" onclick="counselingDetail(${group['node_id']}, 4)">${group['sum_four_star_items']}</a>
    </c:if>
    </td>
    <td align="center" >
    <c:if test="${group['sum_three_star_items']==null || group['sum_three_star_items']==0}">
    --
    </c:if>
    <c:if test="${group['sum_three_star_items']!=null && group['sum_three_star_items']!=0}">
    <a href="#" onclick="counselingDetail(${group['node_id']}, 3)">${group['sum_three_star_items']}</a>
    </c:if>
   </td>
    <td align="center" >
    <c:if test="${group['sum_incorrect_items']==null || group['sum_incorrect_items']==0}">
    --
    </c:if>
    <c:if test="${group['sum_incorrect_items']!=null && group['sum_incorrect_items']!=0}">
    <a href="#" onclick="counselingDetail(${group['node_id']}, 10)">${group['sum_incorrect_items']}</a>
    </c:if>
    </td>
    <td align="center" >
    <c:if test="${group['unsure_mark_items']==null || group['unsure_mark_items']==0}">
    --
    </c:if>
    <c:if test="${group['unsure_mark_items']!=null && group['unsure_mark_items']!=0}">
    <a href="#" onclick="counselingDetail(${group['node_id']}, 11)">${group['unsure_mark_items']}</a>
    </c:if>
    </td>
  </tr>
  </c:forEach>
  <tr bgcolor="#eeeeee">
    <td height="27" align="center"><strong>合计：</strong></td>
    <td align="center">
	<c:if test="${allMap['learning_process_rate']==null}">
    --
    </c:if>
    <c:if test="${allMap['learning_process_rate']!=null}">
    ${allMap['learning_process_rate']}%
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['mastery_rate']==null}">
    --
    </c:if>
    <c:if test="${allMap['mastery_rate']!=null}">
    ${allMap['mastery_rate']}%
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['accuracy_rate']==null}">
    --
    </c:if>
    <c:if test="${allMap['accuracy_rate']!=null}">
    ${allMap['accuracy_rate']}%
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['sum_five_star_items']==null || allMap['sum_five_star_items']==0}">
    --
    </c:if>
    <c:if test="${allMap['sum_five_star_items']!=null && allMap['sum_five_star_items']!=0}">
    <a href="javascript:counselingDetail(0, 5)">${allMap['sum_five_star_items']}</a>
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['sum_four_star_items']==null || allMap['sum_four_star_items']==0}">
    --
    </c:if>
    <c:if test="${allMap['sum_four_star_items']!=null && allMap['sum_four_star_items']!=0}">
    <a href="javascript:counselingDetail(0, 4)">${allMap['sum_four_star_items']}</a>
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['sum_three_star_items']==null || allMap['sum_three_star_items']==0}">
    --
    </c:if>
    <c:if test="${allMap['sum_three_star_items']!=null && allMap['sum_three_star_items']!=0}">
    <a href="javascript:counselingDetail(0, 3)">${allMap['sum_three_star_items']}</a>
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['sum_incorrect_items']==null || allMap['sum_incorrect_items']==0}">
    --
    </c:if>
    <c:if test="${allMap['sum_incorrect_items']!=null && allMap['sum_incorrect_items']!=0}">
    <a href="javascript:counselingDetail(0, 10)">${allMap['sum_incorrect_items']}</a>
    </c:if>
	</td>
    <td align="center">
	<c:if test="${allMap['unsure_mark_items']==null || allMap['unsure_mark_items']==0}">
    --
    </c:if>
    <c:if test="${allMap['unsure_mark_items']!=null && allMap['unsure_mark_items']!=0}">
	<a href="javascript:counselingDetail(0, 11)">${allMap['unsure_mark_items']}</a>
    </c:if>
	</td>
  </tr>
</table>
