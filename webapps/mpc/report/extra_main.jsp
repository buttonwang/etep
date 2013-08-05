<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>

</head>
<body>
<div class="wm720 nTab">
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
		<h1>恭喜你通过了本知识点下系统安排的全部学习任务！</h1>
		<c:if test="${allSize ==0}">
        <!--  首次进入无题 start -->
		<hr />
        <div class="bg">
                <h1 class="cRed">温馨提示：本知识点无新题可供拓展练习。建议你对本知识点下的学习任务进行“弱项强化”，提高掌握度。</h1>
        </div>
        <!--  首次进入无题 End-->
		</c:if>
        <c:if test="${allSize >0}">
        <!--  首次进入有题 start -->
        <hr />
        <div class="bg">
                <h1>作为奖励，你可以拓展练习本知识点下的 <span>${allSize} </span>道新题。拓展练习的结果不会影响当前进度和总体成绩，可放心学习。</h1>
        </div>
        <!--  首次进入有题 End-->
        <div class="blankW_6"></div>
                <jsp:include page="extra_item_info.jsp"></jsp:include>
        <div class="blankW_6"></div>
        <h4><span class="bbs1"><button id="bb" class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="window.parent.location.href='../exam/overView.jhtml?examType=6'">开始拓展练习</button>

        </span></h4>
		</c:if>
        </div>
        </div>
        </div>
    </div>
  </div>
</div> 
</body>
</html>
