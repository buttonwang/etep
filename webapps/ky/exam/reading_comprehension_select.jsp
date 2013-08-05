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
<script language="JavaScript" src="../js/jquery-pack.js"></script>
<script LANGUAGE="JavaScript" src="../js/HighLightArray.js"></script>


<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT language="javascript" src="../js/floating.js" type="text/javascript"></SCRIPT>
<SCRIPT language="javascript" src="../js/exam.js" type="text/javascript"></SCRIPT>
<link href="../css/addWord.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/addWord.js"></script>

<script type='text/javascript' src="../../dwr/engine.js"></script>
<script type='text/javascript' src="../../dwr/interface/freshWordService.js"></script>
<script language="JavaScript" type="text/javascript">
	var processInstanceId="${viewControl.processInstance.id}";
</script>


<script language="JavaScript" type="text/javascript">
	var clueEffect;
	var tranEffect;
	var tanDivName;//用于区分不同的题
	
	function showClue(param1,param2,param3,name){
		var showDiv=document.getElementById(name);
		if(clueEffect==param3&&tanDivName==name){
			//取消效果
			showDiv.style.display="none";
			disHeightIt();
			clueEffect=-1;
			tanDivName=-1;
			}else{
			//生成效果
				var heightNum;
				disShowDiv();
				var clue="clue"+"::"+param1+"::"+param2+"::"+param3;
				var clueEle=document.getElementById(clue);
				showDiv.innerHTML=clueEle.innerHTML;
				showDiv.style.display="block";

				if(param3==2) {
					disHeightIt();
					clueEffect=param3;
					tanDivName=name;
					tranEffect=-1;
					return;
					}//最后一次不用高亮
				if(param3==0) heightNum=0;
				if(param3==1) heightNum=2;
				var height="height"+"::"+param1+"::"+param2+"::"+heightNum;
				var heightEle=document.getElementById(height);
				disHeightIt();
				//alert("x:"+heightEle.innerHTML);
				heightIt(heightEle.innerHTML);
				clueEffect=param3;
				tanDivName=name;
				tranEffect=-1;
				if(clueEffect!=-1&&param3==1){
					//第二次点击时foucus转变
					var ss=document.getElementById("contentTD");
					ss.focus();
					}
		}
		
	}
	//取消每个div的显示。。
	function disShowDiv(){
			var sizeObj=document.getElementById("itemSize");
			if(sizeObj==null){
				alert("对不起，该页数据有问题，itemSize不存在，请联系程序开发人员！");
				return ;
			}
			var size=sizeObj.value;
			for(var i=0;i<size;i++){
				var showDiv=document.getElementById("fanwen"+i);
				if(showDiv!=null)showDiv.style.display="none";
			}
	}
	
	var heightLightStr;
	var cuHeightLight;
	
	function heightIt(str){
		cuHeightLight = new HighLightArray({highlightStr:str,highLightCss:"w5678",highLightBackGroundCss:"s1234",sel:"span[v=highLight]"});
		heightLightStr=str;
		
	}

	function disHeightIt(){
		if(cuHeightLight!=null){
			//alert("a "+cuHeightLight);
			cuHeightLight.disHighLight();
			cuHeightLight.destroy();
			//alert("b");
		}
	}
	
	function showTran(param1,param2,param3,name){
		
		var showDiv=document.getElementById(name);
		var heightNum;
		if(tranEffect==param3&&tanDivName==name){
			//取消效果
			showDiv.style.display="none";
			disHeightIt();
			tranEffect=-1;
			tanDivName=-1;
			}else{
			//生成效果
				disShowDiv();
				showDiv.style.display="block";
				if(param3==2) {
					disHeightIt();
					var cTran=document.getElementById("contentTran");
					showDiv.innerHTML=cTran.innerHTML;
					tranEffect=param3;
					tanDivName=name;
					clueEffect=-1;
					return;
					}//最后一次不用高亮
				
				var tran="tran"+"::"+param1+"::"+param2+"::"+param3;
				var tranEle=document.getElementById(tran);
				showDiv.innerHTML=tranEle.innerHTML;
				//showDiv.style.display="block";

				if(param3==0) heightNum=1;
				if(param3==1) heightNum=3;
				var height="height"+"::"+param1+"::"+param2+"::"+heightNum;
				var heightEle=document.getElementById(height);
				disHeightIt();
				heightIt(heightEle.innerHTML);
				tranEffect=param3;
				tanDivName=name;
				clueEffect=-1;
		}
		
	}
</script>


<style>
.s1234{
	background:#FFFF00;
}
.w5678{
	color:red;
}
</style>
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
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm"><img src="../images/info.gif" align="absbottom" /><strong>Section II  Reading Comprehension</strong>
                    <p><strong>Part  A</strong><br />
                      <strong>Directions:</strong><br />
					${currentPage.instruction} <!--(40 points)--></p></td>
                  </tr>
                </table>
				<div id="content" name="content">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top" style="">
					  <c:set var="lengthCount" value="${'0'}"></c:set>
					  <c:forEach items="${currentPage.items}" var="item" varStatus="status">

					  <table width="100%" cellpadding="3" cellspacing="0"  class="essay">
                          
                      <tr>
                            <td width="100%" id="contentTD"><span v=highLight>${item.content}</span>
							</td>
                        </tr>
                          
                          
                      </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">

						<c:forEach items="${item.subItems}" var="subItem" varStatus="status2">
						
                          <tr class="question_info">
                            <td width="5%" class="f16px fB">
                            
                            <img 
                				<c:if test="${currentPage.isMark[status2.index]==1}" var="mark">
                					src="../images/icon_question.gif"
                					value="1"
                				</c:if>
                 				<c:if test="${mark==false }">
                 					src="../images/icon_noquestion.gif"
                					value="0"
                 				</c:if>
                  			border="0" align="absmiddle" style="cursor: hand" onclick="chgmark(this);"
                  			id="mark${status2.index}"/>
                            
                            <input type="hidden" name="mapkey${status2.index }" id="mapkey${status2.index }" value="${item.id}::${subItem.id} "/>
                            <input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
                            </td>
                            <td width="5%" class="f16px fB">
                            ${subItem.itemNum}
                            <c:if test="${subItem.enable==false}"><!-- 未出题的标示 -->
                            	<img src='../images/true.gif' width='16' height='15' align='texttop' />
                            </c:if>
                            <span id="disEnable${status2.index}" value="${subItem.enable}"/>
                            </td>
                          <td width="55%"><span v=highLight>${subItem.content}</span>
                          <c:if test="${viewControl.showAnswer}">
                          	<span lang=EN-US style='font-size:13.0pt;color:red'>StandAnswer:${subItem.correctAnswer}</span>
                          </c:if>	
                          </td>
                          <td width="15%"><c:if test="${currentPage.starInt[status2.index]>0||currentPage.starHalf[status2.index]>0}">
                                      	（
                              			<span class="cRed">
										<c:forEach   var="i"   begin="1"   end="${currentPage.starInt[status2.index]}"   step="1">  
          								    ★
  										</c:forEach>
  										<c:forEach   var="i"   begin="1"   end="${currentPage.starHalf[status2.index]}"   step="1">
  										    ☆
  										</c:forEach>
  										</span>  
  										）
  										</c:if>
  										</td>
                          <c:if test="${(viewControl.processInstance.node.nodeType ne 'PHASETEST')&&(viewControl.processInstance.node.nodeType ne 'EVALUATE') }">
                          <td width="10%">                          
						    <c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=1)}">
							<a href="javascript:void(null)" >
								<img src="../images/info_01.gif" alt="考点提示" onclick="showClue(${item.id},${subItem.id},0,'fanwen${status2.index}')" width="16" height="16" />
							</a>
							</c:if>	
						    <c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=2)}">
							<a href="javascript:void(null)">
								<img src="../images/info_02.gif" alt="文章相关处" onclick="showClue(${item.id},${subItem.id},1,'fanwen${status2.index}')" width="16" height="16" />
							</a>
						    </c:if>	
                            <c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=3)}">
							<a href="javascript:void(null)" >
								<img src="../images/info_03.gif" alt="详细解析" onclick="showClue(${item.id},${subItem.id},2,'fanwen${status2.index}')" width="16" height="16" />
							</a>
						    </c:if>	
						  </td>
							
                          <td width="10%">
							<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=1)}">
							<a href="javascript:void(null)" >
								<img src="../images/book_01.gif" alt="题干译文" onclick="showTran(${item.id},${subItem.id},0,'fanwen${status2.index}')" width="16" height="16" />
							</a>
							</c:if>	
							<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=2)}">
							<a href="javascript:void(null)" >
								<img src="../images/book_02.gif" alt="选项译文" onclick="showTran(${item.id},${subItem.id},1,'fanwen${status2.index}')" width="16" height="16" />
							</a>
							</c:if>	
							<c:if test="${viewControl.showModel!=1&&(currentPage.isRight[status2.index]==1||errorTimes[lengthCount]>=3)}">
							<a href="javascript:void(null)" >
								<img src="../images/book_03.gif" alt="文章译文" onclick="showTran(${item.id},${subItem.id},2,'fanwen${status2.index}')" width="16" height="16" />
							</a>
							</c:if>	
						  </td>
                          </c:if>  
                          </tr>
						  
						  <c:forEach items="${subItem.answerOptions}" var="answerOption" varStatus="status3">
                          <tr>
                            <td colspan="2">&nbsp;</td>
                            <td colspan="4">
							
							<input name="userAnswer${status2.index}" type="radio" id="userAnswer${status2.index}" value="${answerOption.code}" 
							<c:if test="${currentPage.userAnswer[status2.index]==answerOption.code}">
									checked
							</c:if>
							<c:if test="${viewControl.isFilter==true&&subItem.filterShow==false}">
									 disabled
							</c:if>
							<c:if test="${subItem.enable==false}">
									 disabled
							</c:if>
							/>
 							<c:if test="${status3.index==0}">[A]</c:if>
							<c:if test="${status3.index==1}">[B]</c:if>
							<c:if test="${status3.index==2}">[C]</c:if>
							<c:if test="${status3.index==3}">[D]</c:if>
							<span v=highLight>${answerOption.content}</span>
							<c:if test="${viewControl.showModel==2||viewControl.showModel==3}">
									<c:if test="${currentPage.isRight[status2.index]==1&&answerOption.code==currentPage.userAnswer[status2.index]}">
									 	<img src="../images/true.gif" width="16" height="15" align="texttop" />
									</c:if>
									<c:if test="${currentPage.isRight[status2.index]==0&&answerOption.code==currentPage.userAnswer[status2.index]}">
									 	<img src="../images/false.gif" width="16" height="15" align="texttop" />
									</c:if>
								</c:if>
							<br />
							  </td>
						  
                          </tr>
						  </c:forEach> 
						  <c:set var="lengthCount" value="${lengthCount+1}"></c:set>
						  <tr>
						   <td colspan="6"><div id="fanwen${status2.index}" style="display:none;" class="fanwen"><p id="show">根据试题中高亮内容可知这是一道细节题。</p></div></td>
						  </tr>
                          </c:forEach> 
                      </table>
					  </c:forEach>

					  </td>
                    </tr>
					<!--译文翻译要用的 一些材料put在这里 -->
					<div name="hiddenDiv" style="display:none;">
					   
					 <c:forEach items="${currentPage.items}" var="item" varStatus="status">
						 <div id="contentTran">${contentTransAlertStr}${item.contentTranslation} </div>
					 	<c:forEach items="${item.subItems}" var="subItem" varStatus="status2">
							<!--ydy:-->
							<c:forEach items="${clueStrs[status2.index]}" var="clueStr" varStatus="status4">
								<div id="clue::${item.id}::${subItem.id}::${status4.index}"> ${clueStr}</div>
							</c:forEach>
							<c:forEach items="${translationStrs[status2.index]}" var="translationStr" varStatus="status5">
								<div id="tran::${item.id}::${subItem.id}::${status5.index}"> ${translationStr}</div>
							</c:forEach>
							<c:forEach items="${heightStrs[status2.index]}" var="heightStr" varStatus="status6">
								<div id="height::${item.id}::${subItem.id}::${status6.index}"> ${heightStr}</div>
							</c:forEach>
						
 						</c:forEach> 
 					</c:forEach> 
					</div>
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
</body>
</html>
