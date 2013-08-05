<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/style_blue.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/tandiv.js"></script>
<script type="text/javascript" language="javascript1.2">
function redo(scope,flowItemId){	
	
		top.location.href="../exam/goExam.jhtml?nodeInstanceId="+flowItemId+"&examType=3&scope="+scope;	
}
</script>
</head>
<body>
<div class="wm720 nTab">
 <!-- 标题开始 -->
  <div class="TabTitle">
    <ul>
	<c:if test="${fn:length(nodeInstance.node.orderNum)!=8}">
	<c:if test="${evaluateNum !=0}">
      <li class="<c:if test="${nodeType =='EVALUATE'}">active</c:if><c:if test="${nodeType !='EVALUATE'}">normal</c:if>" ><a class="cBlack" href="consolidate!test.jhtml?orderNum=${orderNum}&nodeType=EVALUATE">评测</a></li>
	</c:if>
	</c:if>
	<c:if test="${phasetestNum !=0}">
      <li class="<c:if test="${nodeType =='PHASETEST'}">active</c:if><c:if test="${nodeType !='PHASETEST'}">normal</c:if>" ><a class="cBlack" href="consolidate!otherTest.jhtml?orderNum=${orderNum}&nodeType=PHASETEST">前测</a></li>
	</c:if>
	<c:if test="${practiceNum !=0}">
      <li class="<c:if test="${nodeType =='PRACTICE'}">active</c:if><c:if test="${nodeType !='PRACTICE'}">normal</c:if>" ><a class="cBlack" href="consolidate.jhtml?orderNum=${orderNum}&nodeType=PRACTICE">训练</a></li>
	</c:if>
	<c:if test="${unittestNum !=0}">
      <li class="<c:if test="${nodeType =='UNITTEST'}">active</c:if><c:if test="${nodeType !='UNITTEST'}">normal</c:if>" ><a class="cBlack" href="consolidate!otherTest.jhtml?orderNum=${orderNum}&nodeType=UNITTEST">后测</a></li>
	</c:if>
    </ul>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
    <div class="style1">
        <div class="con_right">
        <h1 class="til">
		<span>
		<c:if test="${nodeInstance == null}">“${userDataVO.processName}”</c:if>
		<c:if test="${nodeInstance != null}">“${nodeInstance.node.name}”</c:if>
		</span>已做训练统计分析：</h1>
		<c:if test="${nodeInstance == null || userDataVO.processStatus==-1}">
        <div class="box1 bg">
        	<h4><c:if test="${userDataVO.processStatus==-1}">恭喜你，已于<fmt:formatDate pattern="yyyy年MM月dd日" value="${processInstance.end_Time}" />完成全部学习任务！</c:if></h4>
			<c:if test="${nodeInstance == null}">
            	<ul>
                <li class="left"><b>总正确率：</b>${userDataVO.totalAccuracyRate}%（第${userDataVO.totalAccuracyRateOrder}名）&nbsp;&nbsp;&nbsp;&nbsp; </li>
                <li class="left"><b> 总掌握度：</b>${userDataVO.totalMasteryRate}% （第${userDataVO.totalMasteryRateOrder}名）</li>
            </ul>
			</c:if>
            <div class="clear"></div>
        </div>
		</c:if>
        <div class="blankW_9"></div>
        <div class="bg">
        	<div class="con_right_content fB box1" >题目数、星级题统计</div>
        	<table width="698" border="0" cellspacing="1">
        	  <tr>
        	    <td width="81">已训练总题数</td>
        	    <td width="54">对题总数</td>
        	    <td width="54">错题总数</td>
        	    <td width="68">未答题总数</td>
        	    <td width="68">疑问题总数</td>
        	    <td width="71"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="58"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="44"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="39"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="39"><img src="../images/star_m.gif" width="12" height="12" /></td>
        	    <td width="39"><img src="../images/star_m_k.gif" width="12" height="12" /></td>
        	    <td width="46"><img src="../images/smile_m.gif" width="10" height="10" /></td>
      	    </tr>
        	  <tr>
        	    <td>${totalReport['sum_correct_items']+totalReport['sum_incorrect_items']}</td>
        	    <td>${totalReport['sum_correct_items']}</td>
        	    <td>${totalReport['sum_incorrect_items']}</td>
        	    <td>${totalReport['sum_unfinished_items']}</td>
        	    <td>${totalReport['unsure_mark_items']}</td>
        	    <td>${totalReport['sum_five_star_items']}</td>
        	    <td>${totalReport['sum_four_star_items']}</td>
        	    <td>${totalReport['sum_three_star_items']}</td>
        	    <td>${totalReport['sum_two_star_items']}</td>
        	    <td>${totalReport['sum_one_star_items']}</td>
        	    <td>${totalReport['sum_half_star_items']}</td>
        	    <td>${totalReport['sum_zero_star_items']}</td>
      	    </tr>
      	  </table>
        </div>
 <div class="blankW_9"></div>
 <div class="bg box1 padding10px">
 	<div class="bg_left">
    	<div class="title_ts box1 left"><b><img src="../images/guanzhu_ico.gif" width="16" height="16" align="absmiddle" />需关注试题</b></div>
        <div class="title_xk left">
		<select id="selectType" name="selectType">
		  <c:if test="${totalReport['sum_two_star_items']>0}">
          <option value="2">二星</option>
		  </c:if>
		  <c:if test="${totalReport['sum_one_star_items']>0}">
          <option value="1">一星</option>
		  </c:if>
		  <c:if test="${totalReport['sum_half_star_items']>0}">
          <option value="0.5">半星</option>
		  </c:if>
		  <c:if test="${totalReport['sum_incorrect_items']>0}">
          <option value="12">错题</option>
		  </c:if>
		  <c:if test="${totalReport['sum_unfinished_items']>0}">
          <option value="11">未答题</option>
		  </c:if>
		  <c:if test="${totalReport['unsure_mark_items']>0}">
          <option value="15">疑问题</option>
		  </c:if>
		  <c:if test="${totalReport['sum_correct_items']>0}">
          <option value="14">做对题</option>
		  </c:if>
		  <c:if test="${totalReport['items_num']>0}">
          <option value="-1">全部题</option>
		  </c:if>
        </select></div>
        <div class="title_sk left">
		<c:if test="${totalReport['sum_two_star_items']>0 || totalReport['sum_one_star_items']>0 || totalReport['sum_half_star_items']>0 || totalReport['sum_incorrect_items']>0 || totalReport['sum_unfinished_items']>0 || totalReport['unsure_mark_items']>0 || totalReport['sum_correct_items']>0 || totalReport['items_num']>0}">
		<c:if test="${processStatus ==1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label></span>
		</c:if>
		<c:if test="${processStatus !=1}">
<span class="bbs1">
<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:redo(document.getElementById('selectType').value,<c:if test="${nodeInstance == null}">0</c:if><c:if test="${nodeInstance != null}">${nodeInstance.id}</c:if>);">重练</label></span>
		</c:if>
		</c:if>
        </div>
        <div class=" blank6"></div>
        <ul>
          <li class="weigh_box">
            <img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" />
            <div class="blank3"></div>
            <h1><span>五星题</span></h1>
            <h4>${totalReport['sum_five_star_items']}题</h4>
			<c:if test="${totalReport['sum_five_star_items']>0}">
            <h4>

		<c:if test="${processStatus ==1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label></span>
		</c:if>
		<c:if test="${processStatus !=1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:redo(5,<c:if test="${nodeInstance == null}">0</c:if><c:if test="${nodeInstance != null}">${nodeInstance.id}</c:if>);">重练</label></span>
		</c:if>

			</h4>
			</c:if>
            </li>
            <li class="weigh_box"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" />
              <div class="blank3"></div>
              <h1><span>四星题</span></h1>
              <h4>${totalReport['sum_four_star_items']}题</h4>
			  <c:if test="${totalReport['sum_four_star_items']>0}">
              <h4>
		<c:if test="${processStatus ==1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label></span>
		</c:if>
		<c:if test="${processStatus !=1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:redo(4,<c:if test="${nodeInstance == null}">0</c:if><c:if test="${nodeInstance != null}">${nodeInstance.id}</c:if>);">重练</label></span>
		</c:if>
			  </h4>
			  </c:if>
              </li>
            <li class="weigh_box"><img src="../images/star_m.gif" width="12" height="12" /> <img src="../images/star_m.gif" width="12" height="12" /> <img src="../images/star_m.gif" width="12" height="12" />
              <div class="blank3"></div>
              <h1><span>三星题</span></h1>
              <h4>${totalReport['sum_three_star_items']}题</h4>
			  <c:if test="${totalReport['sum_three_star_items']>0}">
              <h4>
		<c:if test="${processStatus ==1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick=" window.parent.consolidate_training( );">重练</label></span>
		</c:if>
		<c:if test="${processStatus !=1}">
		<span class="bbs1">
		<label class="bb1" onmouseout="this.className='bb1';" onmouseover="this.className='bb2';" onclick="javascript:redo(3,<c:if test="${nodeInstance == null}">0</c:if><c:if test="${nodeInstance != null}">${nodeInstance.id}</c:if>);">重练</label></span>
		</c:if>
			  </h4>
			  </c:if>
              </li>
        </ul>
        </div>
 	<div class="bg_right"><img src="../../ky/report/getImage.jhtml?_zero=${totalReport['sum_zero_star_items']==null?0:totalReport['sum_zero_star_items']}&_half=${totalReport['sum_half_star_items']==null?0:totalReport['sum_half_star_items']}&_one=${totalReport['sum_one_star_items']==null?0:totalReport['sum_one_star_items']}&_two=${totalReport['sum_two_star_items']==null?0:totalReport['sum_two_star_items']}&_three=${totalReport['sum_three_star_items']==null?0:totalReport['sum_three_star_items']}&_four=${totalReport['sum_four_star_items']==null?0:totalReport['sum_four_star_items']}&_five=${totalReport['sum_five_star_items']==null?0:totalReport['sum_five_star_items']}&_type=mpc" width="300" height="179" /><h1>星级题分布饼图</h1>
    </div> 
    <div class="clear"></div>
 </div>
         <div class="blankW_9"></div>
        <div class="bg">
        	<div class="con_right_content fB box1" >
        		<!--
        		已训练<c:if test="${nodeInstance == null}">“模块”</c:if>
        		<c:if test="${nodeInstance != null}">
	        		<c:if test="${fn:length(nodeInstance.node.orderNum)==2}">“章”</c:if>
	        		<c:if test="${fn:length(nodeInstance.node.orderNum)==5}">“节”</c:if>
	        		<c:if test="${fn:length(nodeInstance.node.orderNum)==8}">“试卷”</c:if>
        		</c:if>分析-->
        		详细数据分析（点击名称查看详情，点击数字开始重练）
        		<span class="plus">
        			<a href="javascript:void(null)" onclick="openShutManager(this,'prompt')">点击查看</a>
        		</span>
        	</div>
            <table width="698" border="0" cellspacing="1" id="prompt" style="display:none;">
              <tr>
                <td width="67">名称</td>
                <td width="42">掌握度</td>
                <td width="43">总题数</td>
                <td width="37">对题数</td>
                <td width="50">错题数</td>
                <td width="52">未答题数</td>
                <td width="52">疑问题数</td>
                <td width="62"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
                <td width="48"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
                <td width="39"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
                <td width="39"><img src="../images/star_m.gif" width="12" height="12" /><img src="../images/star_m.gif" width="12" height="12" /></td>
                <td width="39"><img src="../images/star_m.gif" width="12" height="12" /></td>
                <td width="39"><img src="../images/star_m_k.gif" width="12" height="12" /></td>
                <td width="46"><img src="../images/smile_m.gif" width="10" height="10" /></td>
              </tr>
			  <c:forEach items="${reportList}" var="report" varStatus="reportStatus">
              <tr>
                <td>
				<c:if test="${fn:length(report['order_num'])==11}">
				<a href="consolidate!training.jhtml?orderNum=${report['order_num']}&nodeType=PRACTICE" target="main" >
				</c:if>
				<c:if test="${fn:length(report['order_num'])!=11}">
				<a href="consolidate.jhtml?orderNum=${report['order_num']}&nodeType=PRACTICE" target="main" >
				</c:if>
				${report['name']}</a></td>
                <td>${report['mastery_rate']}%</td>
                <td>
				<c:if test="${report['items_num']>0}">
                    <a  
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(-1,${report['node_instance_id']});"</c:if> target="_self">
                </c:if>
				${report['sum_correct_items']+report['sum_incorrect_items']}
                <c:if test="${report['items_num']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_correct_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(14,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_correct_items']}
				<c:if test="${report['sum_correct_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_incorrect_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}">	 href="javascript:redo(12,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_incorrect_items']}
                <c:if test="${report['sum_incorrect_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_unfinished_items']>0}">
                    <a target="_self"
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}">	 href="javascript:redo(11,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_unfinished_items']}
                <c:if test="${report['sum_unfinished_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['unsure_mark_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}">	 href="javascript:redo(15,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['unsure_mark_items']}
                <c:if test="${report['unsure_mark_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_five_star_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}">  href="javascript:redo(5,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_five_star_items']}
                <c:if test="${report['sum_five_star_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_four_star_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(4,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_four_star_items']}
                <c:if test="${report['sum_four_star_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_three_star_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(3,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_three_star_items']}
                <c:if test="${report['sum_three_star_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_two_star_items']>0}">
                    <a target="_self"
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(2,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_two_star_items']}
                <c:if test="${report['sum_two_star_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_one_star_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(1,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_one_star_items']}
                <c:if test="${report['sum_one_star_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_half_star_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(0.5,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_half_star_items']}
                <c:if test="${report['sum_half_star_items']>0}">
                </a> 
                </c:if>
				</td>
                <td>
				<c:if test="${report['sum_zero_star_items']>0}">
                    <a target="_self" 
<c:if test="${processStatus ==1}">	onclick="window.parent.consolidate_training( );" href="#"</c:if>
<c:if test="${processStatus !=1}"> href="javascript:redo(0,${report['node_instance_id']});"</c:if>>
                </c:if>
				${report['sum_zero_star_items']}
                <c:if test="${report['sum_zero_star_items']>0}">
                </a> 
                </c:if>
				</td>
              </tr>
              </c:forEach>
            </table>
        </div>
  <hr  />
    <h1 class="cRed">*请特别注意掌握度低于60%的数据，需尽快提高。</h1>
          <div class="clear"></div>
        </div>
        </div>
        </div>
    </div>
  </div>
</div>

</body>
</html>
