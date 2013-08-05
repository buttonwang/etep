<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<c:forEach items="${mList}" var="m" varStatus="mStatus">
        	<div class="con_right_content fB box1" ><c:if test="${m['mName'] != null}">“${m['mName']}”</c:if><c:if test="${m['mName'] == null}">“决胜09考研”</c:if> （掌握度<fmt:formatNumber value="${m['masteryRate']}" pattern="#0" ></fmt:formatNumber>%，正确率<fmt:formatNumber value="${m['accuracyRate']}" pattern="#0" ></fmt:formatNumber>%）</div>
        	<table width="698" border="0" cellspacing="1">
        	  <tr>
        	    <td width="193">章名</td>
        	    <td width="228">节名</td>
        	    <td width="42">掌握度</td>
        	    <td width="42">总题数</td>
        	    <td width="42">错题数</td>
        	    <td><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
       	      </tr>
      	  </table>
		<c:forEach items="${m['cList']}" var="c" varStatus="cStatus">
            <table width="698" border="0" cellspacing="1">
              <tr>
                <td width="186" rowspan="${c['cNum']}">${c['cName']}<br/>（掌握度<fmt:formatNumber value="${c['masteryRate']}" pattern="#0" ></fmt:formatNumber>%，正确率<fmt:formatNumber value="${c['accuracyRate']}" pattern="#0" ></fmt:formatNumber>%）</td>

			  <c:forEach items="${c['nList']}" var="n" varStatus="nStatus">
			  <c:if test="${nStatus.count==1}">
			    <td width="220">${n['nName']}</td>
                <td width="40">${n['masteryRate']}%</td>
                <td width="40">${n['sumItems']}</td>
                <td width="43">${n['sumIncorrectItems']}</td>
                <td width="60">${n['fiveItems']}</td>
                <td width="48">${n['fourItems']}</td>
                <td width="36">${n['threeItems']}</td>
              </tr>
			  </c:if>
			  <c:if test="${nStatus.count!=1}">
			  <tr>
			    <td width="220">${n['nName']}</td>
                <td width="40">${n['masteryRate']}%</td>
                <td width="40">${n['sumItems']}</td>
                <td width="43">${n['sumIncorrectItems']}</td>
                <td width="60">${n['fiveItems']}</td>
                <td width="48">${n['fourItems']}</td>
                <td width="36">${n['threeItems']}</td>
              </tr>
			  </c:if>
			  </c:forEach>
            </table>
			</c:forEach>
</c:forEach>