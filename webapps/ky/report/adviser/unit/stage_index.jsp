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
          <td width="100" align="center" class="tag00"><a href="<%=request.getContextPath()%>/report/ad_unit_test.jhtml?ClassCode=${ClassCode}">单元测试</a></td>
          <td width="10" align="center" class="tag02">&nbsp;</td>
          <td width="100" align="center" 
		  <c:if test="${nodeType=='PHASETEST'}"> class="tag01" </c:if>
		  <c:if test="${nodeType=='EVALUATE'}"> class="tag00" </c:if>
		  ><a href="<%=request.getContextPath()%>/report/ad_stage.jhtml?ClassCode=${ClassCode}&nodeType=PHASETEST">阶段测试</a></td>
          <td width="10" align="center" class="tag02">&nbsp;</td>
          <td width="100" align="center" 
		  <c:if test="${nodeType=='EVALUATE'}"> class="tag01" </c:if>
		  <c:if test="${nodeType=='PHASETEST'}"> class="tag00" </c:if>
		  ><a href="<%=request.getContextPath()%>/report/ad_stage.jhtml?ClassCode=${ClassCode}&nodeType=EVALUATE">评测</a></td>
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
                      <td colspan="3" align="center">人均</td>
                    </tr>
                    <tr class="question_info"> 
                      <td align="center"> 总数</td>
                      <td align="center">在练</td>
                      <td align="center">训练用时</td>
                      <td align="center">成绩</td>
                      <td align="center">正确率</td>
  
                    </tr>
        <!-- 显示列表 -->
       <c:forEach items="${stageList}" var="stageMap" varStatus="stageStatus" >    
	        <fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stageMap['avg_time']/60}" var="avgTimeM" />
       		<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stageMap['avg_time']%60}" var="avgTimeS" />
                    <tr> 
                      <td>${stageMap['name']}</td>
                      <td align="center"><a href="ad_stu.jhtml?ClassCode=${ClassCode}&status=-1&unitID=${stageMap['id']}">
                      	${stageMap['total_count']}
                      	</a></td>
                      <td align="center"><a href="ad_stu.jhtml?ClassCode=${ClassCode}&status=1&unitID=${stageMap['id']}">
                      	${stageMap['nopass_count']}</a></td>
                      <td align="center"><c:if test="${avgTimeM!=0}">${avgTimeM}分钟</c:if>${avgTimeS}秒</td>
                      <td align="center"><fmt:formatNumber value="${stageMap['avg_score']}" pattern="0.0"/></td>
                      <td align="center"><fmt:formatNumber value="${stageMap['avg_accuracy_rate']}" pattern="0.0"/></td>

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
