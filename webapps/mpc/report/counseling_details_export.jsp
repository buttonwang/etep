<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>爆破学堂 教学辅导</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="../css/style/style3.css" type=text/css rel=stylesheet>
<LINK href="../css/style/assistant.css" type=text/css rel=stylesheet>
<LINK href="../css/style/style8.26.css" type=text/css rel=stylesheet>


<style type="text/css">
<!--
div.MsoNormal {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
div.MsoNormal1 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal1 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal1 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
div.MsoNormal2 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal2 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal2 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
div.MsoNormal3 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
li.MsoNormal3 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal3 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal11 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal21 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoNormal22 {mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
-->
</style>
</head>
<body> 
	<div class="con_right2">
        <h2>请选中所需的试题复制到Word程序中编辑试题！</h2>
        <c:forEach items="${pages}" var="page" varStatus="pageStauts">
		<c:forEach items="${page.items}" var="item" varStatus="itemStauts">
            	<div class="dircts">
             	<p>${item.itemNum}.&nbsp;&nbsp;&nbsp; ${item.content}</p>
             	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1')}">
		        		<c:forEach items="${item.answerOptions}" var="answerOption" varStatus="answerStatus">
		        			<c:if test="${answerStatus.index==0}"><c:set var="answerCode" value="A．"></c:set></c:if>
							<c:if test="${answerStatus.index==1}"><c:set var="answerCode" value="B．"></c:set></c:if>
							<c:if test="${answerStatus.index==2}"><c:set var="answerCode" value="C．"></c:set></c:if>
							<c:if test="${answerStatus.index==3}"><c:set var="answerCode" value="D．"></c:set></c:if>			        		         
							<p>${answerCode} ${answerOption.content}</p>	 	
	                	</c:forEach>
	        	</c:if>
		        <c:if test="${page.typeAlias ne 'MPC4X'}">
					<!--详解层开始-->
			        <div id="analy${item.id}">
			        	<p>详解：${item.examProperty.analysisAtLarge}</p>
			        </div>
	        		<!--详解层结束-->
	        		<br></br>
			        <!--答案层开始-->
			        <div id="answer${item.id}">
				         <span class="left">
				         	答案：
				        	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1')}">
								<span class="left">${item.examProperty.correctAnswer}</span>          	
							</c:if>
				                	
		                	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1') eq false}">
		                		<span class="left">${item.answerPrototype}</span>
			                	<!--<p align="left">
					           		<c:forEach items="${item.answersView}" var="correctAnswer" varStatus="status">				           																			  																										           			
					           			<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>		
										<span class="left">								
											<b>${status.count}.</b>
											<span id="viewAnswer_${xStatus.index}_${status.index}">
												${fn:replace(correctAnswer.value, '$', ' 或 ')}
											</span>
											&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
							    	</c:forEach>
							    </p>-->
						   </c:if>
				        </span>
			        </div>
			        <!--答案层结束-->
		        </c:if>
		        
		        <c:if test="${page.typeAlias eq 'MPC4X'}">
		        	<c:forEach items="${item.subItems}" var="subItem" varStatus="subStatus">
		        		<p><b>子题${subStatus.index+1}.</b> ${subItem.content}</p>
        				
        				 <!--详解层开始-->
				        <div id="analy${subItem.id}">
				        	<p>详解：${subItem.examProperty.analysisAtLarge}</p>
				        </div>
		        		<!--详解层结束-->
		        		<br></br>
		        		<!--答案层开始-->
				        <div id="answer${subItem.id}">
					        <span class="left">
					        	答案：<span class="left">${subItem.answerPrototype}</span>
			                	<!--<p align="left">
					           		<c:forEach items="${subItem.answersView}" var="correctAnswer" varStatus="status">				           																			  																										           			
					           			<?import namespace = m urn = "http://www.w3.org/1998/Math/MathML" implementation = "#MathPlayer"/>		
										<span class="left">								
											<b>${status.count}.</b>
											<span id="viewAnswer_${xStatus.index}_${status.index}">
												${fn:replace(correctAnswer.value, '$', ' 或 ')}
											</span>
											&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
							    	</c:forEach>
							    </p>-->
					        </span>
				        </div>
				        <!--答案层结束-->
		        	</c:forEach>
		        </c:if>
            	</div>
           		
           		<div class="blank12"></div>
          </c:forEach>
          </c:forEach>
	</div>
</body>
</html>
