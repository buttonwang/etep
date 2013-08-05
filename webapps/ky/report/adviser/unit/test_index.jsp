<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" type="text/css">
<link href="<%=request.getContextPath()%>/css/pingce.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td valign="top"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="20" align="center" class="tag02">&nbsp;</td>
          <td width="100" align="center" class="tag00"><a href="<%=request.getContextPath()%>/report/ad_unit.jhtml?ClassCode=${ClassCode}">单元训练</a></td>
          <td width="10" align="center" class="tag02">&nbsp;</td>
          <td width="100" align="center" class="tag01"><a href="<%=request.getContextPath()%>/report/ad_unit_test.jhtml?ClassCode=${ClassCode}">单元测试</a></td>
          <td width="10" align="center" class="tag02">&nbsp;</td>
          <td width="100" align="center" class="tag00"><a href="<%=request.getContextPath()%>/report/ad_stage.jhtml?ClassCode=${ClassCode}&nodeType=PHASETEST">阶段测试</a></td>
          <td width="10" align="center" class="tag02">&nbsp;</td>
          <td width="100" align="center" class="tag00"><a href="<%=request.getContextPath()%>/report/ad_stage.jhtml?ClassCode=${ClassCode}&nodeType=EVALUATE">评测</a></td>
          <td align="right" class="tag02">&nbsp;${examFlow.flowName}</td>
        </tr>
      </table>
      <table width="100%" height="100" border="0" cellpadding="0" cellspacing="0" >
        <tr> 
          <td valign="top" style="padding:10px;border-top:0px solid #ffffff;border-left:1px solid #80D2EA;border-right:1px solid #80D2EA;border-bottom:1px solid #80D2EA;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"> <table width="100%" border="1" cellpadding="3" cellspacing="0"  class="table_question">
                    <tr class="question_info"> 
                      <td rowspan="2" align="center">测试名称</td>
                      <td colspan="2" align="center"> 人数</td>
                      <td colspan="4" align="center">人均</td>
                      <td rowspan="2" align="center">平均通过成绩</td>
                      <td rowspan="2" align="center">掌握度</td>
                    </tr>
                    <tr class="question_info"> 
                      <td align="center"> 总数</td>
                      <td align="center">通过</td>
                      <td align="center">平均</td>
                      <td align="center">最高</td>
                      <td align="center">最低</td>
                      <td align="center">首次通过率</td>
                    </tr>
        <!-- 显示列表 -->
       <c:forEach items="${unitList}" var="unitMap" varStatus="unitStatus" >                       
                    <tr> 
                      <td>${unitMap['name']}</td>
                      <td align="center">
					  <a href="ad_stu.jhtml?ClassCode=${ClassCode}&status=-1&unitID=${unitMap['id']}">
                      	${unitMap['total_count']}
                      	</a></td>
                      <td align="center">
					  <a href="ad_stu.jhtml?ClassCode=${ClassCode}&status=2&unitID=${unitMap['id']}">
                      	${unitMap['pass_count']}
						</a>
						</td>
                      <td align="center"><fmt:formatNumber value="${unitMap['avg_score']}" pattern="0.0"/></td>
                      <td align="center"><fmt:formatNumber value="${unitMap['max_score']}" pattern="0.0"/></td>
                      <td align="center"><fmt:formatNumber value="${unitMap['min_score']}" pattern="0.0"/></td>
                      <td align="center"><fmt:formatNumber value="${unitMap['first_pass_rate']}" pattern="0.0"/>%</td>
                      <td align="center"><fmt:formatNumber value="${unitMap['avg_passed_score']}" pattern="0.0"/></td>
                      <td align="center"><fmt:formatNumber value="${unitMap['avg_mastery_rate']}" pattern="0.0"/>%</td>
                    </tr>
                    </c:forEach>
                  </table></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
