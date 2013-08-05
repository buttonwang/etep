<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" language="javascript">
function redo(flowItemId){
	var scope = document.getElementById("selectType").value;
	top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=6&scope="+scope;	
}
</script>


        <!-- 题目数统计开始-->
        <div class="blankW_9"></div>
 		<div class="bg">
        	<div class="con_right_content2 fB box1" >题目统计</div>
        	<table width="522" border="0" cellspacing="1">
        	  <tr>
        	    <td width="51" align="center">已拓展</td>
        	    <td width="50" align="center">对题</td>
        	    <td width="50" align="center">错题</td>
        	    <td width="50" align="center">未答题</td>
        	    <td width="50" align="center">疑问题</td>
       	      </tr>
        	  <tr>
        	    <td>${currentTestStatus.sumCorrectItems+currentTestStatus.sumIncorrectItems}</td>
        	    <td>${currentTestStatus.sumCorrectItems}</td>
        	    <td>${currentTestStatus.sumIncorrectItems}</td>
        	    <td>${currentTestStatus.sumUnfinishedItems}</td>
        	    <td>${currentTestStatus.unsureMarkItems}</td>
       	      </tr>
      	  </table>
 		</div>
        <h4 class="left">
        	<span class="bbs1"><button class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
        	 onclick="location.href='extra_answer_status.jsp?nodeInstanceId=${currentTestStatus.asfNodeInstance.id}&historyTestStatusId=0&num=0'">
        	  回顾全部已拓展题</button></span></h4>
        <div class="title_xk left">
	        <select id="selectType" name="selectType"> 
			 <c:if test="${currentTestStatus.sumIncorrectItems>0}">
	          <option value="12">错题</option>
			 </c:if>
			 <c:if test="${currentTestStatus.sumUnfinishedItems>0}">
	          <option value="11">未答题</option>
			 </c:if>
			 <c:if test="${currentTestStatus.sumCorrectItems>0}">
	          <option value="14">做对题</option>
			 </c:if>
			 <c:if test="${currentTestStatus.unsureMarkItems>0}">
	          <option value="15">疑问题</option>
			 </c:if>
	          <option value="-1">全部题</option>
	        </select>
        </div>
        <div class="title_sk left">
        	<span class="bbs1"><label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';"
            	 onclick="redo('${currentTestStatus.asfNodeInstance.id}')">重练</label></span>
        </div>
        <!-- 题目数统计结束-->
        <div class="bg">
        	<div class="con_right_content fB box1" >历次拓展数据（点击“回顾”查看当次答题详情）</div>
            <table width="698" border="0" cellspacing="1">
			  <tr>
			    <td width="70">拓展次数</td>
			    <td width="91">任务名称</td>
			    <td width="44">题数</td>
			    <td width="45">做对</td>
			    <td width="44">做错</td>
			    <td width="43">未答</td>
			    <td width="43">疑问</td>
			    <td width="45">总分</td>
			    <td width="43">得分</td>
			    <td width="43">正确率</td>
			    <td width="153">回顾</td>
			  </tr>
			  <c:forEach items="${historyTestStatusList}" var="historyTestStatus" varStatus="status">
			  <tr>
			    <td>第${status.index+1}次</td>
			    <td>${historyTestStatus.paperAssemItemTypeName}</td>
			    <td>${historyTestStatus.sumCorrectItems+historyTestStatus.sumIncorrectItems}</td>
			    <td>${historyTestStatus.sumCorrectItems}</td>
			    <td>${historyTestStatus.sumIncorrectItems}</td>
			    <td>${historyTestStatus.sumUnfinishedItems}</td>
			    <td>${historyTestStatus.unsureMarkItems}</td>
			    <td>${historyTestStatus.totalScore}</td>
			    <td>${historyTestStatus.score}</td>
			    <td>${historyTestStatus.accuracyRate}%</td>
			    <td><a href="extra_answer_status.jsp?nodeInstanceId=${historyTestStatus.asfNodeInstance.id}&historyTestStatusId=${historyTestStatus.id}&num=${status.index+1}"
			    	>回顾</a></td>
			  </tr>
			  </c:forEach>
			</table>
 		</div>
