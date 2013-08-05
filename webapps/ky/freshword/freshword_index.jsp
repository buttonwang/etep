<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/freshword_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${userDataVO.processCategoryName}_</title>
		<link href="../css/base.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="../js/common.js"></script>
		<script type="text/javascript">
			function freshword_test(){
				document.newform.action="freshWordConfig!test.jhtml";
				document.newform.submit();
			}
		</script>
	</head>
	<body>
	<form action="" method="post" name="newform">
		<input name="id" type="hidden" value="${id}"/>
	</form>
		<%@ include file="include/freshword_top.jsp"%>
		
		<!-- 页面主题 -->
		<div id="main" class="wm2" >
		  <div class="TabContent">
		    <div id="myTab1_Content0">
		        <div class="nTab">
          		<!-- 标题开始 -->
          		<div class="TabTitle">
            		<ul id="myTab1">
              		<li class="active" onclick="nTabs(this,0);">生词训练</li>
            		</ul>
            		</div>
          		<!-- 内容开始 -->
          		<div class="TabContent">
            		<div id="myTab1_Content">
              		<div class="style1">
                		<table width="100%" border="0" cellpadding="5" cellspacing="0">
                		
                  		<tr>
                    		<td height="335" valign="top"><p align="center"><b><font size="3" color="#3366cc">生词训练 
                      		（当前共 ${newpage.totalCount}个生词） </font></b><br />
                      		</p>
                      		<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        		<tr>
                          		<td class="comm"><img src="../images/info.gif" align="absbottom" />小贴士：建议将全部单词学完至少一遍，再开始“测试”。</td>
                        		</tr>
                      		</table>
                      		<br />
                    		<table width="586" cellpadding="3" cellspacing="0" class="listab" style="margin-top:0px;">
                          		<tr class="question_info">
                            		<td><strong>生词:</strong></td>
                          		</tr>
                          		<c:forEach items="${newpage.result}" var="page">
                          		<tr>
                            		<td height="76" style="padding-left:30px;"><p><span class="Arial16px"><strong> </strong>${page.wordExtension.wordBasic.word}:</span><a href="#"><img src="../images/icon_duk.gif" width="16" height="16" /></a><br />
                            		<c:forEach items="${page.wordExtension.wordTypes}" var="newtype"><p>${newtype.code} <c:forEach items="${newtype.chineseMeanings}" var="newchin">${newchin.meaning}  </c:forEach></p></c:forEach></td>
                      			</tr>
                          		<tr>
                            		<td style="padding-left:30px;" class="huitext"><p><strong>例句与用法:</strong><br />
                            		<c:set var="i" value="1"></c:set>
                            		<c:forEach items="${page.wordExtension.wordTypes}" var="newtype">
                            			<c:forEach items="${newtype.chineseMeanings}" var="newchin">
                            				<c:forEach items="${newchin.exampleSentences}" var="newexam">
                            					<p><strong>${i}</strong>. ${newexam.content}<br/>
												${newexam.translation}
												</p>
                            					<c:set var="i" value="${i+1}"></c:set>
                            				</c:forEach>
                            			</c:forEach>
                            		</c:forEach>
                          		</tr>
								<tr>
                            		<td style="padding-left:30px;" class="huitext"><p><strong>常用搭配:</strong><br />
									<c:set value="${page.wordExtension.wordBasic.commonUsage}" var="commonUsage"/>
									<c:set var="usageList" value="${fn:split(commonUsage,';')}"/>
									<c:forEach items="${usageList}" var="usageItem"> 
									${usageItem} <br/>
									</c:forEach>


                            		
                          		</tr>
                          		</c:forEach>
                        		</table></td>
                    		<td rowspan="2" valign="top">
                    	<div class=" jihua biana9">
                      		<div class="tilte"><span class="f14px fB">生词列表</span>（点击单词即可学习）</div>
                     			 <div class="content">
                      				<table width="100%" border="0" cellpadding="3" cellspacing="0" class="listab">
                        			<c:forEach items="${newlist}" var="newword" varStatus="status">
                        				<c:if test="${(status.count+1) mod 2==0}">
                          					<tr>
                          				</c:if>
                            			<td class="line01"><p style="line-height:150%;">${status.count}</p></td>
                            			<td class="line01"><p style="line-height:150%;"><a href="freshWordFace.jhtml?pageNo=${status.count}">${newword.wordExtension.wordBasic.word}</a></p></td>
                          				<c:if test="${status.count!=1&&(status.count) mod 2==0}">
                          					</tr>
                          				</c:if>
                          			</c:forEach>
                        			</table>
                      			</div>
                    		</div>        
                      			 <div class="cyic bianad">
                      				<div class="cyic_img">
                        				<a href="#" onclick="freshword_test()"><img src="../images/btn_cs.gif" width="213" height="50" /></a> 
                        		    </div>
                    			</div>
                    		</td>
                  		</tr>
                  		<tr>
                    		<td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="pagetab">
                      		<tr>
                        		<td><div class="c"></div>
                            		<div class="turnpage"> <!--a href="#"  class="select">&lt;</a--> 
                            		<ambow:listnewword actionName="freshWordFace" type="2" totalCount="${newpage.totalCount}" 
					 				total="${newpage.totalPageCount}" num="${newpage.currentPageNo}"/></div></td>
                      		</tr>
                    		</table></td>
                  		</tr>
                		</table>
              		</div>
            		</div>
          		</div>
        		</div>
      		</div>
  		</div>
  		<div class="clear"></div>
		</div>					
		<%@ include file="include/freshword_foot.jsp"%>
	</body>
</html>