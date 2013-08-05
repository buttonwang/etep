<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${viewControl.flowName}</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<script language="JavaScript" src="../js/jquery-pack.js"></script>
<SCRIPT language="javascript" src="../js/floating.js" type="text/javascript"></SCRIPT>
<SCRIPT language="javascript" src="../js/exam.js" type="text/javascript"></SCRIPT>

<link href="../css/addWord.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/addWord.js"></script>

<script type='text/javascript' src="../../dwr/engine.js"></script>
<script type='text/javascript' src="../../dwr/interface/freshWordService.js"></script>
<script language="JavaScript" type="text/javascript">
	var processInstanceId="${viewControl.processInstance.id}";
</script>

</head>
<body onload="JavaScript:FloatingDIV();initSelect();">

<!--页面头部-->
<jsp:include page="include_head.jsp"></jsp:include>
<!--    end   -->


 <!-- 页面主题 -->
<div id="main" class="wm2" >
	<div class="nTab wm2">
<!-- 内容开始 -->
    <div class="TabContent">
    	<div id="myTab1_Content0">
        <div class="style1">

        <jsp:include page="include_div.jsp"></jsp:include>

          <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
              <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm"><img src="../images/info.gif" align="absbottom" /><strong>Section III writing</strong>
                      <br />
                      <p><strong>Part  A </strong><br />
                      </p>
                    </td>
                  </tr>
                </table>
				<div id="content">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top" style="">
					  
					  <c:forEach items="${currentPage.items}" var="item" varStatus="status">
					  <table width="100%" cellpadding="3" cellspacing="0" class="essay">                          
                        <tr class="question_info">
                          <td width="20">
                          	<strong>
                            <img 
                				<c:if test="${currentPage.isMark[status.index]==1}" var="mark">
                					src="../images/icon_question.gif"
                					value="1"
                				</c:if>
                 				<c:if test="${mark==false }">
                 					src="../images/icon_noquestion.gif"
                					value="0"
                 				</c:if>
                  				border="0" align="absmiddle" style="cursor: hand" onclick="chgmark(this);"
                  				id="mark${status.index}"
                  			/>
                            </strong>
                            <input type="hidden" name="mapkey${status.index }" id="mapkey${status.index }" value="${item.id}"/>
                          	<input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>          
                          </td>                          
                          <td width="500"><strong>${item.itemNum}.   Directions:</strong></td>
                          <td class="listab">
                          		<c:if test="${currentPage.starInt[status.index]>0||currentPage.starHalf[status.index]>0}">
                                      	（
                              			<span class="cRed">
										<c:forEach   var="i"   begin="1"   end="${currentPage.starInt[status.index]}"   step="1">  
          								    ★
  										</c:forEach>
  										<c:forEach   var="i"   begin="1"   end="${currentPage.starHalf[status.index]}"   step="1">
  										    ☆
  										</c:forEach>
  										</span>  
  										）
  										</c:if>
  						  </td>
                          <td width="60" class="listab">
							<c:if test="${viewControl.analysisPolicy==1
										||viewControl.showModel!=1&&viewControl.analysisPolicy==2&&currentPage.isDone[status.index]==1
										||viewControl.showModel!=1&&viewControl.analysisPolicy==3&&currentPage.isRight[status.index]==1}">													
								<a href="javascript:void(null)" onclick="openShutManager(this,'wt${status.index}')">
									<img src="../images/info_01.gif" width="16" height="16" alt="写作模板" />
								</a>
								<a href="javascript:void(null)" onclick="openShutManager(this,'aa${status.index}')">
									<img src="../images/info_02.gif" width="16" height="16" alt="答案解析" />
								</a>
								<a href="javascript:void(null)" onclick="openShutManager(this,'sn${status.index}')">
									<img src="../images/info_03.gif" width="16" height="16" alt="评分标准" />
								</a>
							</c:if>
						 </td>
                        
                        </tr>
                        <tr>
                          <td><p>&nbsp;</p></td>
                          <td colspan=3 style="width:670px;word-wrap:break-word;">${item.content}
                          	<c:if test="${viewControl.showAnswer}">
                          		<span lang=EN-US style='font-size:13.0pt;color:red'>StandAnswer:${item.correctAnswer}</span>
                          	</c:if>
                          </td>
                        </tr>
                      </table>
                      
                      <div id="wt${status.index}" style="display:none;" class="fanwen1">
                      	<div style="background-color:#f5f5f5;font-size:14px;height:25px; padding:0 10px; ">
                      		<strong>一个好的写作模板也许对你有用：</strong>
                      	</div>
                      	<div style="border:1px solid #cccccc;padding:10px;font-size:14px; width:700px;word-wrap:break-word;">
                      		<table width="700" border="0" cellspacing="0" cellpadding="0">
              				<tr><td style="width:700px;word-wrap:break-word;">
                      	 	${item.writingTemplate }
                      	 	</td></tr>
                      	 	</table>
                      	</div>
                      </div>
                      
                      <div id="aa${status.index}" style="display:none;" class="fanwen1">
                      	<div style="background-color:#f5f5f5;font-size:14px;height:25px; padding:0 10px; ">
                      		<strong>我们来看看试题的详细解析吧：</strong>
                      	</div>
                      	<div style="border:1px solid #cccccc;padding:10px;font-size:14px; width:700px;word-wrap:break-word;">
                      		<table width="700" border="0" cellspacing="0" cellpadding="0">
              				<tr><td style="width:700px;word-wrap:break-word;">
                      		${item.answerAnalysis }
                      		</td></tr>
                      	 	</table>
                      	</div>
                      </div>
                      
                      <div id="sn${status.index}" style="display:none;" class="fanwen1">
                      	<div style="background-color:#f5f5f5;font-size:14px;height:25px; padding:0 10px; ">
                      		<strong>评分标准是这样的：</strong>
                      	</div>
                      	<div style="border:1px solid #cccccc;padding:10px;font-size:14px; width:700px;word-wrap:break-word;">
                      		<table width="700" border="0" cellspacing="0" cellpadding="0">
              				<tr><td style="width:700px;word-wrap:break-word;">
                      		${item.scoringNorm }
                      		</td></tr>
                      	 	</table>
                      	</div>
                      </div>
                      
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">                          
                      	<tr>
	                        <td width="95%">
							<c:if test="${viewControl.showModel==1}">
							  <textarea name="userAnswer${status.index}" id="userAnswer${status.index}" cols="70" rows="5" class="bont_text2">${currentPage.userAnswer[status.index]}</textarea>
							</c:if>
							<c:if test="${viewControl.showModel!=1}">
							  <div style="background-color:#f5f5f5;font-size:14px;height:25px;">
							  	<strong>我的文章： </strong> 得分：${scoreList[status.index]}
							  </div>
	                          <div style="border:1px solid #cccccc;padding:10px;font-size:14px;">
	                          	${currentPage.userAnswer[status.index]}
	                          </div>
							</c:if>
							</td>
                        </tr>
                      </table>
                      
                     <c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
                      <c:set var="itemId" value='"${item.id}"' ></c:set>
                      <table id="swietab" width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                        <tr>
                          <td width="95%">
                          	<div>
                            	<div style="background-color:#f5f5f5;font-size:14px;height:25px; padding:0 10px;">
                            		<strong> 写作批改： </strong>
                            	</div>
                            	<div style="border:1px solid #cccccc;padding:10px;font-size:12px;">                            	  
								  <div id="swieinfo">Loading...</div>
                                  <form target="_blank" id="swiepostform" method="post" action="${swie.postUrl}" >                                  
	                                  <input name="title" type="hidden" value="${userDataVO.processCategoryName}-写作-${item.id}"/>
	                                  <textarea id="swieDirection" name="direction" style="display: none">${item.content}</textarea>
	                                  <textarea id="swieContent" name="content" style="display: none">${currentPage.userAnswer[status.index]}</textarea>	                                  
                                  		<input name="sysId" type="hidden" value="${webuser.id},${item.id}"/>
                                  </form>
	                              <img id="img01" onclick="swiePost();" src="../images/btn_pigai.gif" alt="进入批改系统" style="margin-left:500px;display: none;" />
	                              <a href="${swieBean.postUrl}" target="_blank"><img id="img02" src="../images/btn_pigai0.gif" alt="进入批改系统"  style="margin-left:500px;display: none;"/></a>
                            	</div>
                          	</div>
                          </td>
                        </tr>
                      </table>
                      <iframe src="../web/swieArticleStatus.jhtml?itemId=${item.id}" scrolling="no" style="display:none" name="swieframe" id="swieframe" frameborder="0"></iframe>
					  </c:if>
					  
					  </c:forEach>
					  </td>
                    </tr>
              </table>
			</div>
			</td>
              <td width="114" align="right" valign="top"><!--<div class="xScrollStick d1">
				<a href="http://www.163.com">俺是浮动条</a>
				</div>-->
               <c:if test="${viewControl.showModel==1}">
					<jsp:include page="include_left.jsp"></jsp:include>
                </c:if>
                <c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
					<jsp:include page="include_right.jsp"></jsp:include>
                </c:if>
               </td>
            </tr>
          </table>
        </div>
        </div>
    </div>
    </div>
    <div class="clear"> </div>
</div>

<jsp:include page="include_bottom.jsp"></jsp:include>
<script language="JavaScript" type="text/javascript">

	function swiePost() {
		var swiedir = $("#swieDirection").val().replace(/<(?!img)[^>]*>/g, "")
				.replace(/\/\w+\/upload/, "${swie.writeImgUrl}")
				.replace(/^\s+/, "").replace(/\s+$/, "");
		var swiecon = $("#swieContent").val().replace(/<br>/g, "\r\n");
		document.getElementById('swieDirection').value = swiedir;
		document.getElementById('swieContent').value = swiecon;
		//alert(document.getElementById('swieDirection').innerHTML);
		//alert(document.getElementById('swieContent').innerHTML);
		document.getElementById('swiepostform').submit();
	}
</script>
</body>
</html>
