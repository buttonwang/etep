<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page import="java.util.List,java.util.Map,com.ambow.core.util.NumberUtil"%>
<%
	String ctx = request.getContextPath();
	List<Map<String,Object>> reportList = (List<Map<String,Object>>)request.getAttribute("reportList");
	Map<String,Object> amountMap = (Map<String,Object>)request.getAttribute("amountMap");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弱项强化——评测</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <li class="active" ><a class="cBlack" href="consolidate!test.jhtml?orderNum=${orderNum}&nodeType=EVALUATE">评测</a></li>
	  <c:if test="${fn:length(orderNum)!=8}">
	  <c:if test="${phasetestNum !=0}">
      <li class="normal" ><a class="cBlack" href="consolidate!otherTest.jhtml?orderNum=${orderNum}&nodeType=PHASETEST">前测</a></li>
	  </c:if>
	  <c:if test="${practiceNum !=0}">
      <li class="normal" ><a class="cBlack" href="consolidate.jhtml?orderNum=${orderNum}&nodeType=PRACTICE">训练</a></li>
	  </c:if>
	  <c:if test="${unittestNum !=0}">
      <li class="normal" ><a class="cBlack" href="consolidate!otherTest.jhtml?orderNum=${orderNum}&nodeType=UNITTEST">后测</a></li>
	  </c:if>
	  </c:if>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til"><span>
		<c:if test="${nodeInstance == null}">“${userDataVO.processName}”</c:if>
		<c:if test="${nodeInstance != null}">“${nodeInstance.node.name}”</c:if>
		</span>已做评测统计分析：</h1>
        <hr />
        <div class="blankW_9"></div>
		<%
			String model = "";
			String chapter = "";
			String section = "";
			int sectionFlag=1;
			int chapterFlag=1;
			for(int i=0;i<reportList.size();i++){
				chapterFlag=2;
				Map<String,Object> reportMap = reportList.get(i);
				if(reportMap.get("m_name") != null && !reportMap.get("m_name").equals(model)){

					if(!model.equals("")){
		%>
		</table>
		</div>
		<%
					}
					model = (String)reportMap.get("m_name");
		%>
        <div class="bg">
        	<div class="con_right_content fB box1" ><%=model%></div>
        	<table width="698" border="0" cellspacing="1">
        	  <tr>
        	    <td width="193" rowspan="2">章名</td>
        	    <td width="228" rowspan="2">评测知识点</td>
        	    <td colspan="3">学前评测</td>
        	    <td colspan="3">学后评测</td>
       	      </tr>
        	  <tr>
        	    <td width="42">分值</td>
        	    <td width="42">得分</td>
        	    <td width="42">正确率</td>
        	    <td width="42">分值</td>
        	    <td width="42">得分</td>
        	    <td width="42">正确率</td>
        	  </tr>
      	  </table>
		 <%	
			  }
			  if(reportMap.get("c_name") != null && !reportMap.get("c_name").equals(chapter)){
			  		chapterFlag=1;
					chapter = (String)reportMap.get("c_name");
					if(i>0 &&sectionFlag==1){
		%>
				<td width="42">--</td>
				<td width="42">--</td>
				<td width="42">--</td>
				</tr>
		<%
					}
		%>
		</table>
		<table width="698" border="0" cellspacing="1">
		     <tr>
                <td width="193" rowspan="<%=amountMap.get(chapter)%>"> <%=chapter%></td>
		<%
			  }

		%>
		<%
			  
			  String name = (String)reportMap.get("name");
			  if((reportMap.get("k_name") != null && !reportMap.get("k_name").equals(section))){
			  	if(chapterFlag == 2 && sectionFlag==1){
		%>
				<td width="42">--</td>
				<td width="42">--</td>
				<td width="42">--</td>
				</tr>
		<%
				}
					section = (String)reportMap.get("k_name");
					if(name != null && name.equals("章前评测")){
						sectionFlag=1;
		%>
		        <td width="228"><%=section%></td>
			    <td width="42"><%=reportMap.get("total_score")%></td>
                <td width="42"><%=reportMap.get("score")%></td>
                <td width="42"><%=NumberUtil.getDecimal((Float)reportMap.get("score")/(Float)reportMap.get("total_score")*100,2)%>%</td>
		<%
					}else if(name !=null && name.equals("章后评测")){
						sectionFlag=2;
		%>
				<td width="228"><%=section%></td>
				<td width="42">--</td>
				<td width="42">--</td>
				<td width="42">--</td>
			    <td width="42"><%=reportMap.get("total_score")%></td>
                <td width="42"><%=reportMap.get("score")%></td>
                <td width="42"><%=NumberUtil.getDecimal((Float)reportMap.get("score")/(Float)reportMap.get("total_score")*100,2)%>%</td>
				</tr>
		<%
					}
		%>


		<%
			  }else{
			  		if(name != null && name.equals("章后评测")){
						sectionFlag=2;
		%>
		        <td width="42"><%=reportMap.get("total_score")%></td>
                <td width="42"><%=reportMap.get("score")%></td>
                <td width="42"><%=NumberUtil.getDecimal((Float)reportMap.get("score")/(Float)reportMap.get("total_score")*100,2)%>%</td>
			</tr>

		<%
					}
			  }
			}
			if(reportList.size()>1 && sectionFlag==1){
		%>
				<td width="42">--</td>
				<td width="42">--</td>
				<td width="42">--</td>
				</tr>
		<%
			}
		%>
		</table>
        </div>

        </div>
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
