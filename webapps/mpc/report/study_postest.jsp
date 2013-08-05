<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page import="java.util.List,java.util.Map,java.util.Date,com.ambow.trainingengine.util.DateUtil"%>
<%
	String ctx = request.getContextPath();
	List<Map<String,Object>> reportList = (List<Map<String,Object>>)request.getAttribute("reportList");
	Map<String,Object> amountMap = (Map<String,Object>)request.getAttribute("amountMap");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弱项强化——后测</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
	<c:if test="${fn:length(orderNum)!=8}">
	<c:if test="${evaluateNum !=0}">
      <li class="normal" ><a class="cBlack" href="consolidate!test.jhtml?orderNum=${orderNum}&nodeType=EVALUATE">评测</a></li>
	</c:if>
	</c:if>
	<c:if test="${phasetestNum !=0}">
      <li class="normal" ><a class="cBlack" href="consolidate!otherTest.jhtml?orderNum=${orderNum}&nodeType=PHASETEST">前测</a></li>
	</c:if>
	<c:if test="${practiceNum !=0}">
      <li class="normal" ><a class="cBlack" href="consolidate.jhtml?orderNum=${orderNum}&nodeType=PRACTICE">训练</a></li>
	</c:if>
	<c:if test="${unittestNum !=0}">
      <li class="active" ><a class="cBlack" href="consolidate!otherTest.jhtml?orderNum=${orderNum}&nodeType=UNITTEST">后测</a></li>
	</c:if>
    </ul>  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til"><span>		
		<c:if test="${nodeInstance == null}">“${userDataVO.processName}”</c:if>
		<c:if test="${nodeInstance != null}">“${nodeInstance.node.name}”</c:if>
		</span>已做前测统计分析：</h1>
        <hr />
        <div class="blankW_9"></div>
		<%
			String model = "";
			String chapter = "";
			String section = "";
			int sectionFlag=1;
			for(int i=0;i<reportList.size();i++){
				Map<String,Object> reportMap = reportList.get(i);
				if(reportMap.get("model_name") != null && !reportMap.get("model_name").equals(model)){

					if(!model.equals("")){
		%>
		</table>
		</div>
		<%
					}
					model = (String)reportMap.get("model_name");
		%>
        <div class="bg">
        	<div class="con_right_content fB box1" ><%=model%></div>
        	<table width="698" border="0" cellspacing="1">

              <tr>
                <td width="100">章名</td>
                <td width="177">后测名</td>
                <td width="36">题数</td>
                <td width="33">总分</td>
                <td width="69">正确率要求</td>
                <td width="41">限时</td>
                <td width="31">状态</td>
                <td width="56">当前成绩</td>
                <td width="40">掌握度</td>
                <td width="84">最后测试时间</td>
              </tr>
		<%	
			  }
			  if(reportMap.get("chapter_name") != null && !reportMap.get("chapter_name").equals(chapter)){
					chapter = (String)reportMap.get("chapter_name");
		%>
		     <tr>
                <td width="100" rowspan="<%=amountMap.get(chapter)%>"> <%=chapter%></td>
		<%

			  }

		%>
		<%
			  if(reportMap.get("section_name") != null && !reportMap.get("section_name").equals(section)){
					section = (String)reportMap.get("section_name");
			  }
			  String current = (String)reportMap.get("current_name");
			  String next_chapter = (String)reportMap.get("next_chapter_name");
			  String next_section = (String)reportMap.get("next_section_name");
			  String after = (String)reportMap.get("next_name");
		%>
                <td width="178">【<%=section%>】<%=current%></td>
                <td width="36"><%=reportMap.get("sum_items")%></td>
                <td width="33"><%=reportMap.get("total_score")%></td>
                <td width="69"><%=reportMap.get("accuracy_rate")%>%</td>
                <td width="40">
                <%
				Integer standardAnswerintTime = (Integer)reportMap.get("standard_answering_time");
				if(standardAnswerintTime != null){
					if(standardAnswerintTime/3600>0){
						out.print(standardAnswerintTime/3600+"小时");
					}
					if(standardAnswerintTime/60%60>0){
						out.print(standardAnswerintTime/60%60+"分钟");
					}
					if(standardAnswerintTime%60>0){
						out.print(standardAnswerintTime%60+"秒");
					}
				}
                %></td>
                <td width="31">
				<%
				Integer nodeStatus = (Integer)reportMap.get("node_status");
				if(nodeStatus == 1){
					out.print("不通过");
				}else if(nodeStatus == 2){
					out.print("通过");
				}else if(nodeStatus == 3){
					out.print("跳过");
				}
				%>
				</td>
                <td width="56"><%=reportMap.get("score")%></td>
                <td width="40"><%=reportMap.get("mastery_rate")%>%</td>
                <td width="84"><%=DateUtil.format((Date)reportMap.get("start_time"),"yyyy-MM-dd")%></td>
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
