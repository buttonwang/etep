<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/tandiv.js"></script>

</head>
<body>
<div class="wm720 nTab">
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
		<c:if test="${allSize ==0}">
		恭喜你完成了本知识点的拓展练习！
		</c:if>
		<c:if test="${allSize >0}">
        <div class="bg">
                <h1>本知识点下还有<span>${allSize} </span>道新题可供拓展练习。拓展练习的结果不会影响当前进度和总体成绩，可放心学习。</h1>
        </div>
        <div class="blankW_6"></div>
        <jsp:include page="extra_item_info.jsp"></jsp:include>
        <h4><span class="bbs1"><button id="bb" class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.location.href='../exam/overView.jhtml?examType=6'">继续拓展练习</button> </span></h4>
		</c:if>  
<c:if test="${currentTestStatus != null}">
	<jsp:include page="extra_test_status.jsp"></jsp:include>
</c:if>
       
        </div>
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
