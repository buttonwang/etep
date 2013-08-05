<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html XMLNS:m="http://www.w3.org/1998/Math/MathML">
<head>
<OBJECT
      ID=MathPlayer
      CLASSID="clsid:32F66A20-7614-11D4-BD11-00104BD3F987"
>
</OBJECT>
<?IMPORT NAMESPACE="m" IMPLEMENTATION="#MathPlayer" ?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学习指导红宝书</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.3.1.js"></script>
<script type="text/javascript" src="../js/tandiv.js"></script>
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
      <c:if test="${studyGuideKpcodes ne ''}">
      	<li class="normal" ><a class="cBlack" href="../studyguide/studyGuideDescAction!getStudyGuide.jhtml?kpCode=${studyGuideKpcodes}" target="main" >知识讲解</a></li>
      </c:if>
      <c:if test="${studyGuideKpcodes eq ''}">
      	<li class="normal" ><a class="cBlack" href="../studyguide/studyGuideDescAction.jhtml?nodeId=${pageMap['nodeId']}&nodeGroupId=${pageMap['nodeGroupId']}" target="main" >知识讲解</a></li>
      </c:if>
      <li class="active" ><a class="cBlack" href="javascript:void(null)" >典型例题</a></li>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
      <div class="style1">
        <div class="content_c">
          <c:forEach items="${pages}" var="page" varStatus="pageStauts">
          	<div class="content_box">
            <div class="con_right_content"><b class="f14px">${page.title}</b>&nbsp;&nbsp;&nbsp;&nbsp;${page.instruction}</div>
            <c:forEach items="${page.items}" var="item" varStatus="itemStauts">
            <div class="dircts">
             	<p><b>${itemStauts.index+1}.</b> ${item.examProperty.contentView}</p>
             	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1')}">
	        		<div class="blank6"></div>
		        	<ul>
		        		<c:forEach items="${item.answerOptions}" var="answerOption" varStatus="answerStatus">
		        			<c:if test="${answerStatus.index==0}"><c:set var="answerCode" value="A．"></c:set></c:if>
							<c:if test="${answerStatus.index==1}"><c:set var="answerCode" value="B．"></c:set></c:if>
							<c:if test="${answerStatus.index==2}"><c:set var="answerCode" value="C．"></c:set></c:if>
							<c:if test="${answerStatus.index==3}"><c:set var="answerCode" value="D．"></c:set></c:if>			        		         
							<li>
								<input type="${page.typeAlias eq 'MPC11'?'radio':'checkbox'}" 
									name="userAnswer${itemStauts.index}" id="userAnswer${itemStauts.index}"
								 />${answerCode} ${answerOption.content}					 	
							</li>
	                	</c:forEach>
		        	</ul>
	        	</c:if>
		        <div class="blank6"></div>
		        <c:if test="${page.typeAlias ne 'MPC4X'}">
			        <span class="dwell_btn left"><a onclick="openShutManager(this,'analy${item.id}')"><span>详解</span></a></span>
			        <span class="Answer_btn left"><a onclick="openShutManager(this,'answer${item.id}')"><span>答案</span></a></span>
	        		<div class="clear"></div>
	        		
					 <!--详解层开始-->
			        <div id="analy${item.id}" style="display:none" v="close" class="prompt">
			       		<h5><span class="left f14px fB">详解：</span>
			       		<span class="right"><a href="javascript:void(null)"><img src="../images/close.gif" /></a></span></h5>
			        	<div class="blank3"></div>
			        	<p>${item.examProperty.analysisAtLarge}</p>
			        </div>
	        		<!--详解层结束-->
	        		
			        <!--答案层开始-->
			        <div id="answer${item.id}" style="display:none;" v="close" class="prompt">
				        <h5><span class="left f14px fB">答案：</span>
				        <span class="left">
				        	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1')}">
								<span class="left"><u>${item.examProperty.correctAnswer}</u></span>          	
							</c:if>
				                	
		                	<c:if test="${fn:startsWith(page.typeAlias, 'MPC1') eq false}">
			                	<p align="left" style="color: red">
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
							    </p>
						    </c:if>
				        </span>
				        <span class="right"><a href="javascript:void(null)"><img src="../images/close.gif" /></a></span></h5>
				        <div class="clear"></div>
			        </div>
			        <!--答案层结束-->
		        </c:if>
		        
		        <c:if test="${page.typeAlias eq 'MPC4X'}">
		        	<c:forEach items="${item.subItems}" var="subItem" varStatus="subStatus">
		        		<p><b>子题${subStatus.index+1}.</b> ${subItem.examProperty.contentView}</p>
				        <span class="dwell_btn left"><a onclick="openShutManager(this,'analy${subItem.id}')"><span>详解</span></a></span>
		        		<span class="Answer_btn left"><a onclick="openShutManager(this,'answer${subItem.id}')"><span>答案</span></a></span>
        				<div class="clear"></div>
        				
        				 <!--详解层开始-->
				        <div id="analy${subItem.id}" style="display:none" v="close" class="prompt">
				       		<h5><span class="left f14px fB">详解：</span>
				       		<span class="right"><a href="javascript:void(null)"><img src="../images/close.gif" /></a></span></h5>
				        	<div class="blank3"></div>
				        	<p>${subItem.examProperty.analysisAtLarge}</p>
				        </div>
		        		<!--详解层结束-->
		        		
		        		<!--答案层开始-->
				        <div id="answer${subItem.id}" style="display:none;" v="close" class="prompt">
					        <h5><span class="left f14px fB">答案：</span>
					        <span class="left">
			                	<p align="left" style="color: red">
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
							    </p>
					        </span>
					        <span class="right"><a href="javascript:void(null)"><img src="../images/close.gif" /></a></span></h5>
					        <div class="clear"></div>
				        </div>
				        <!--答案层结束-->
				        <div class="clear"></div>
		        	</c:forEach>
		        </c:if>
        		<div class="blank20"></div><hr/>
            </div>
            </c:forEach>
          </div>
          <div class="blank9"></div>	
          </c:forEach>

        
		<div class="blank20"></div>
        <h4>
        	<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" 
        		onclick=studyGuideHref()>知识讲解</button></span>
		</h4>
        <div class="blank20"></div>
        <hr />
          <c:if test="${studyGuideKpcodes eq ''}">
          <h1>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <c:choose>
                	<c:when test="${pageMap['perviousName']==null}">
                		<td><strong>上一个：</strong>没有了</td>
                	</c:when>
                	
                	<c:otherwise> 
                		<td><strong>上一个：</strong>
                		<a onclick="parent.sById(${pageMap['previousId'] })" 
                			href="../studyguide/studyGuideDescAction!typicalExample2.jhtml?nodeId=${pageMap['previousId']}&nodeGroupId=${pageMap['previousGroupId']}&nodeName=${pageMap['perviousName']}" target="main">${pageMap['perviousName']}
                		</a></td>
                	</c:otherwise>
                </c:choose>              
                <td><strong>当前知识点：</strong>${pageMap['nodeName']}</td>
                <c:choose>
                	<c:when test="${pageMap['nextName']==null}"> 
                		<td><strong>下一个：</strong>没有了</td>
                	</c:when>
                	<c:otherwise>
                		<td><strong>下一个：</strong>
                		<a onclick="parent.sById(${pageMap['nextId'] })" 
                			href="../studyguide/studyGuideDescAction!typicalExample2.jhtml?nodeId=${pageMap['nextId']}&nodeGroupId=${pageMap['nextGroupId']}&nodeName=${pageMap['nextName']}" target="main">${pageMap['nextName']}
                		</a></td>
                	</c:otherwise>
                </c:choose>
              </tr>
            </table>
          </h1>
          <div class="blank20"></div>
          </c:if>
        </div>
      </div>
    </div>
    </div>
  </div>
  
  <script language="javaScript" type="text/javascript">
	//提示层的右上角关闭按钮的事件
	  $(function(){
	  	$("div[v=close]").each(function(){
	  		var jit = $(this);
	  		$(this).find("img").each(function(){
	  			var src = $(this).attr("src");
	  			if(src.indexOf("close.gif")>-1){
	  				$(this).click(function(){
	  					jit.hide();
	  				})
	  			}
	  		})
	  	})
	  })

	  function studyGuideHref() {
		  if (${studyGuideKpcodes} != '') {
			  location.href="../studyguide/studyGuideDescAction!getStudyGuide.jhtml?kpCode=" + "${studyGuideKpcodes}";
		  } else {
			  location.href="../studyguide/studyGuideDescAction.jhtml?nodeId=${pageMap['nodeId']}&nodeGroupId=${pageMap['nodeGroupId']}";
		  }
	  }
  </script>
</body>
</html>