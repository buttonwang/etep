<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	final String[] User_Flow_Status={
		"初始状态","未通过","通过","跳过"	
	};
	String status="-1";
	if (request.getAttribute("status")!=null)
		status=((String)request.getAttribute("status"));

%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" type="text/css">
<link href="<%=request.getContextPath()%>/css/pingce.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#000000">
  <form action="<%=request.getContextPath()%>/report/ad_stu.jhtml" name="frm" method="post">
	<input type="hidden" name="ClassCode" value="${ClassCode}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td valign="top"> 
       选择模块：
      <select name='unitID'>
      	<option value='-1'>---所有模块---</option>
      	<c:forEach items="${nodeList}" var="nodeMap" varStatus="paperQuestionStatus" >
      	   <c:set value="" var="sel"/>
      		<c:if test="${nodeMap['id']==unitID}">
      			<c:set value="selected" var="sel"/>
      		</c:if> 
			<c:if test="${nodeMap['node_type']=='GROUP'}">
        		<option value='${nodeMap['id']}' ${sel}>${nodeMap['name']}</option>
      		</c:if>   
			<c:if test="${nodeMap['node_type']=='PRACTICE'}">
        		<option value='${nodeMap['id']}' ${sel}>____${nodeMap['name']}</option>
      		</c:if>  
        </c:forEach>
      </select>
	  选择状态：
      <select name="status">
        <option value='-1'>---全部---</option>
		<%
		for (int i=0;i<User_Flow_Status.length;i++) {
			String sel=Integer.parseInt(status)==i?"selected":"";			
		%>       
        <option value='<%= i%>' <%=sel %>><%=User_Flow_Status[i] %></option>
        <%
		}
        %>
      </select>
      <input type='submit' value='确定'>
      <br>

	
     <table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="eeeeee">
        <tr class="question_info">
          <td>登录名</td>
          <td>姓名</td>
          <td>模块</td>
          <td>状态</td>          
          <td>成绩</td>
          <td>掌握度</td>
          <td>训练时长</td>
          <td>首次学习</td>
          <td>末次学习</td>
          <td>末次时长</td>          
        </tr>
        
       <c:forEach items="${stuUserList}" var="stuUserMap" varStatus="stuUserStatus" >
       		<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['time_count']/60}" var="timeCountM" />
       		<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['time_count']%60}" var="timeCountS" />
       		<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['time_used']/60}" var="timeUsedM" />
       		<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['time_used']%60}" var="timeUsedS" />
        <tr>
          <td>${stuUserMap['login_name']}</td>
          <td>${stuUserMap['real_name']}</td>
          <td><a href="<%=request.getContextPath()%>/report/ad_report.jhtml?nodeId=${stuUserMap['node_id']}&userId=${stuUserMap['login_id']}" target="_blank"  alt='查看模块学习分析'>${stuUserMap['name']}</a></td>
          <td>
          		<c:if test="${stuUserMap['node_status']==0}">初始状态</c:if>
	          	<c:if test="${stuUserMap['node_status']==1}">未通过</c:if>
	          	<c:if test="${stuUserMap['node_status']==2}">通过</c:if>
	          	<c:if test="${stuUserMap['node_status']==3}">跳过</c:if>
          </td>          
          <td>${stuUserMap['score']} </td>
          <td><fmt:formatNumber  value="${stuUserMap['mastery_rate']}"  pattern="0.0"/>% </td>
          <td>
          		<c:if test="${timeCountM!=0}">${timeCountM}分钟</c:if>${timeCountS}秒	
          </td>
          <td><fmt:formatDate value="${stuUserMap['first_test_time']}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td><fmt:formatDate value="${stuUserMap['end_time']}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td>
          		<c:if test="${timeUsedM!=0}">${timeUsedM}分钟</c:if>${timeUsedS}秒
          </td>          
        </tr>
        </c:forEach>

      </table>
      </td>
  </tr>
</table>

      	</form>
</body>
</html>
