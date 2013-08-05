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
<link href="../css/tanceng.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/tanceng.js"></script>

<SCRIPT language="javascript" src="../js/floating.js" type="text/javascript"></SCRIPT>
<SCRIPT language="javascript" src="../js/exam.js" type="text/javascript"></SCRIPT>

<link href="../css/addWord.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/addWord.js"></script>

<script type='text/javascript' src="../../dwr/engine.js"></script>
<script type='text/javascript' src="../../dwr/interface/freshWordService.js"></script>
<script language="JavaScript" type="text/javascript">
	var processInstanceId="${viewControl.processInstance.id}";
	$(document).ready(function() {
		//解决隐藏div的换行问题。2008-09-22
	    $('#contentTD p').css("display","inline");
	});

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
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="comm"><img src="../images/info.gif" align="absbottom" /><strong>Section II  Reading Comprehension</strong>
                      <br />
                      <p><strong>Part C</strong><br />
                      </p>
                      <p><strong>Directions: </strong><br />
						${currentPage.instruction}<!--(10 points)--> </p></td>
                  </tr>
                </table>
			<div id="content">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top" style="">
						<input type="hidden" name="itemSize" id="itemSize" value="${currentPage.size}"/>
						<c:forEach items="${currentPage.items}" var="item" varStatus="status">
						
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                                    <tr class="question_info">
                                      <td id="contentTD" width="729" colspan="5">${item.contentView}</td>
                                    </tr>
                                    
                                    
                        </table>
                        <c:if test="${viewControl.showAnswer}">	
						  <c:forEach items="${item.subItems}" var="subItem" varStatus="status1">						  	
                          		<span lang=EN-US style='font-size:13.0pt;color:red'>StandAnswer 第${subItem.itemNum}题: ${subItem.correctAnswer}</span><br/>                          	
                          </c:forEach>
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
</body>
</html>
