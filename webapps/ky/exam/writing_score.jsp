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
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript" src="../js/floating.js" type="text/javascript"></script>
              <script  language="JavaScript" type="text/javascript">
				function  secBoard(n){
					for(i=0;i<secTable.cells.length-1;i++) secTable.cells[i].className="tag00";
					secTable.cells[n].className="tag01";
					for(i=0;i<mainTable.tBodies.length;i++)mainTable.tBodies[i].style.display="none";
					mainTable.tBodies[n].style.display="block";
				}
				
				function checkValue(obj, v2) {
					var cValue=obj.value;
					if (isNaN(cValue)) {
						alert("请输入正确的数字！");
						obj.value="";
						obj.focus();
						return true;
					}
					if (cValue<0||cValue>v2) {			
						alert("输入值超出范围，请按提示信息重新输入");
						obj.value="";
						obj.focus();
					}
				}

				function writingScoreMe(){
					var flag=false;
					var size=document.getElementById("itemSize").value;
					var alertStr="";
					var resultStr="";
					//var score;
					//var sysScore;
					for(var i=0;i<size;i++){
						var score=document.getElementById("score"+i).value;
						var sysScore=document.getElementById("sysScore"+i).value;
						var itemNum=document.getElementById("itemNum"+i).value;
						var itemId=document.getElementById("itemId"+i).value;
						if(score==null||score==''){
							alert(itemNum+"题分值为空！请填入合适的分值！");
							return;
						}
						score=Number(score);
						sysScore=Number(sysScore);
						//alert("typeof"+typeof(score));
						//alert("typeof"+typeof(sysScore));
						//alert(itemNum+" "+score+" "+sysScore+" "+(score>sysScore)+""+alertStr);
						
						if(score>sysScore){
							flag=true;
							alertStr=alertStr+" 第"+itemNum+"题的自评分偏高！</br>";
						}
						resultStr=resultStr+itemId+":"+score+";";
						}
					document.getElementById("writingScore").value=resultStr;
					
					if(flag){
						//有不合适分值的情况下。
						var alertDiv=document.getElementById("alertDiv");
						alertDiv.innerHTML=alertStr;
						doit(sign01);
						doit(writingDiv);
					}else{
						writingSubmit();
					}	
				}
				
				function writingSubmit(){					
				 	var form=document.getElementById("writingForm");
				 	form.submit();
				}
            </script>
</head>
<body onkeydown="if(event.keyCode=='13'&&event.srcElement. type!='textarea') return false;"> 


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
   <DIV 
style="Z-INDEX: 33; ; LEFT: expression((body.clientWidth-500)/2); POSITION: absolute; DISPLAY: none; TOP: 80px" 
id=writingDiv align=center>
<TABLE border=0 cellSpacing=0 width=500 height=200>
  <TBODY>
  <TR>
    <TD style="LINE-HEIGHT: 40px" class=comm align=middle>
      <P><IMG align=bottom src="../images/icon_alert.gif"> <SPAN 
      style="FONT-SIZE: 16px; FONT-FAMILY: 黑体">书面表达的自评分有些偏高哦，需要返回重新打分么？<BR>
      <DIV 
      style="BORDER-RIGHT: #eeeeee 1px solid; PADDING-RIGHT: 10px; PADDING-LEFT: 10px; FONT-WEIGHT: normal; 
      BORDER-TOP: #eeeeee 1px solid; PADDING-BOTTOM: 10px; BORDER-LEFT: #eeeeee 1px solid; PADDING-TOP: 10px; 
      BORDER-BOTTOM: #eeeeee 1px solid; BACKGROUND-COLOR: #ffffff; TEXT-ALIGN: left"><SPAN 
      id=alertDiv></SPAN></DIV><FONT color=#cc3300><BR>
      <INPUT onclick=javascript:doit(sign01);doit(writingDiv); value=" 好，返回重新打分 " type=button name=Submit3> 
<INPUT onClick="javascript:writingSubmit()"; value=" 不，我要继续 " type=button name=Submit3> 
      </FONT></SPAN></TD></TR></TBODY></TABLE></DIV>
<DIV 
style="Z-INDEX: 33; ; LEFT: expression((body.clientWidth-500)/2); POSITION: absolute; DISPLAY: none; TOP: 80px" 
id=writingCloseDiv align=center>
<TABLE border=0 cellSpacing=0 width=500 height=200>
  <TBODY>
  <TR>
    <TD style="LINE-HEIGHT: 40px" class=comm align=middle>
      <P><IMG align=bottom src="../images/icon_alert.gif"> <SPAN 
      style="FONT-SIZE: 18px; FONT-FAMILY: 黑体">书面表达的自评分还未完成，确定要强行退出么？<BR>
      <DIV 
      style="BORDER-RIGHT: #eeeeee 1px solid; PADDING-RIGHT: 10px; PADDING-LEFT: 10px; 
      FONT-WEIGHT: normal; BORDER-TOP: #eeeeee 1px solid; PADDING-BOTTOM: 10px; BORDER-LEFT: #eeeeee 1px solid; PADDING-TOP: 10px; 
      BORDER-BOTTOM: #eeeeee 1px solid; BACKGROUND-COLOR: #ffffff; TEXT-ALIGN: left">强行退出的话将导致本次考试的答案丢失！ 
      <SPAN id=alertDiv1></SPAN></DIV><FONT color=#cc3300><BR>
      <INPUT onclick=javascript:doit(sign01);doit(writingCloseDiv); value=" 继续打分 " type=button name=Submit3> 
	  <INPUT onClick="javascript:location.href='index.html';" value=" 回退到首页 " type=button name=Submit3> 
      </FONT></SPAN></TD></TR></TBODY></TABLE></DIV>

<DIV style="Z-INDEX: 20; FILTER: alpha(opacity=70); LEFT: 0px; POSITION: absolute; WIDTH: 100%; DISPLAY: none; TOP: 0px; ;
 HEIGHT: expression(document.body.scrollHeight); BACKGROUND-COLOR: #eeeeee; -moz-opacity: 0.7; opacity: 0.7" 
id=sign01 align=center></DIV>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm" style="font-size:12px;">
                    	<img src="../images/icon_alert.gif" align="absbottom" />
                    	本次测试有写作题，系统需要你来协助给自己的大作打分，简单两步、轻松完成： <br />
	                    ① 参考评分标准和范文，<font color="#FF3300">客观公正地给自己的大作打分</font><br />
                     	② 完成所有作文评分后，点击页面右侧“<font color="#FF3300">提交</font>”按钮
                    </td>
                  </tr>
                </table>
                <br/>
                <table width="100%" border="0" cellpadding="0" cellspacing="0" id="secTable">
                    <tr>
					<c:forEach items="${writingItems}" var="item" varStatus="status">
                      <td align="center" 
						<c:if test="${status.index==0}">
							class="tag01" 
						</c:if>
						<c:if test="${status.index!=0}">
							class="tag00" 
						</c:if>
						onclick="secBoard(${status.index})" style="cursor:hand;"> 第${status.index+1}题</td>
                    </c:forEach>  
					<td class="tag02">&nbsp;</td>
                    </tr>      
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="50%" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td style="padding:10px;border-top:0px solid #ffffff;border-left:1px solid #80d2ea;
                            	border-right:1px solid #80d2ea;border-bottom:1px solid #80d2ea;">
							<table  border="0"  cellspacing="0"  cellpadding="4"  width="100%"  height="400"  id="mainTable">
                              
                              <c:forEach items="${writingItems}" var="item" varStatus="status00">
								<tbody 
									<c:if test="${status00.index==0}">
									 style="display:block;"
									</c:if>
									<c:if test="${status00.index!=0}">
									 style="display:none;"
									</c:if>
									>
                                  <tr>
                                    <td height="120" valign="top" ><p><strong>${item.content} </strong></p>
                                      <p>&nbsp; </p>
                                      <div style="background-color:#f5f5f5;font-size:14px;height:25px;">
                                      	<strong>评分标准及范文： </strong>
                                      </div>
                                      <div style="border:1px solid #cccccc;padding:10px;font-size:14px;">
                                          <p><strong><font color="#FF6600">评分标准</font></strong><br />
                                            ${item.scoringNorm}<br />
                                          </p>
                                          <p><font color="#009900">范文：${item.writingTemplate}</font></p>
                                      </div>
                                      <br />
                                      <br />
                                      <div style="background-color:#f5f5f5;font-size:14px;height:25px;"><strong>我的文章：</strong></div>
                                      <div style="border:1px solid #cccccc;padding:10px;font-size:14px;">${userAnswers[status00.index]}</div>
                                      <p></p>
                                      <p><strong></strong></p>
                                      <p><br />
                                      </p>
                                    </td>
                                  </tr>
                                </tbody>
                               </c:forEach>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                </table>
              </td>
              <td width="160" align="center" valign="top" style="padding-left:10px;">
              		<p><span class="f14px cRed"><img src="../images/Tip.gif" width="17" height="17" /> 给自己打个分数</span></p>                  
                    <label>
                    <div align="left">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
						<input type="hidden" name="itemSize" id="itemSzie" value="${itemSize}">
						<c:forEach items="${writingItems}" var="item" varStatus="status01">
	                        <tr>
	                          <td><strong>第${item.itemNum}题：</strong></td>
	                          <td>
	                          	<input name="score${status01.index}" onblur="javascript:checkValue(this,${item.score})" 
	                          		value="" type="text" size="6" />分
	                          </td>
	                        </tr>
							<tr>
							 <td colspan="2">（请输入0-<font color="#FF6600">${item.score}</font>之间的整数值）<br /></td>
							</tr>
							<tr>
							  <td colspan="2">
							  <!--  一些系统变量,系统得分、题号、id。。。-->
							  <input type="hidden" name="sysScore${status01.index}" id="sysScore${status01.index}" value="${systemScores[status01.index]}" />
							  <input type="hidden" name="itemNum${status01.index}" id="itemNum${status01.index}" value="${item.itemNum}" />
							  <input type="hidden" name="itemId${status01.index}" id="itemId${status01.index}" value="${item.id}"/>
							  </td>
							</tr>
						</c:forEach>
                        
                      </table>
                    </div>
                   
                    </label>
                    <a href="#" onclick="javascript:writingScoreMe();">
                    	<img src="../images/bnt_sub.gif" name="Image23" width="103" height="38" border="0" id="Image" />
                    </a>
                	<form action="../exam/subjectiveIyScore!parseResult.jhtml" method="post" name="writingForm" id="writingForm">
						<input type="hidden" value="" name="writingScore" id="writingScore"/> 
					</form>
                	<p><br/></p>
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
</body>
</html>
