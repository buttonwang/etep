<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
<link href="<%=request.getContextPath()%>/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>

<body>
<div class="nTab wm5">
  <!-- 标题开始 -->
  <div class="TabTitle">
    <ul id="myTab1">
      <li class="active">训练报告</li>
     <c:if test="${stuUserMap['test_status']!=null}"></c:if>
    </ul>
    <div class="user_text12">欢迎你，<span class="f14px fB">${stuUserMap['real_name']}</span> 同学</div>
  </div>
  <!-- 内容开始 -->
  <div class="TabContent">
    <div id="myTab1_Content0">
      <div class="style1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="middle">
             <c:if test="${stuUserMap['test_status']!=null}">                 
            <p><b><font  color="#3366cc"><span class="f14px">综合训练报告</span></font></b>（${stuUserMap['name']}）<br/>
            </p>
            </c:if>
                <table width="100%" height="264" border="0" cellpadding="0" cellspacing="0" class="listab">
                  <tr>
                    <td valign="top" height="25" colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="listab">
                      <tr>
                        <td width="17%" align="right"><strong>总题数：</strong></td>
                        <td width="15%">${stuUserMap['items_num']}</td>
                        <td width="23%" align="right"><strong>首答时间/成绩/正确率：</strong></td>
                        <td width="24%"><c:if test="${stuUserMap['test_status']!=null}"><fmt:formatDate value="${stuUserMap['first_test_time']}" pattern="yyyy-MM-dd HH:mm:ss"/>(&nbsp;${stuUserMap['first_test_score']}分;&nbsp;${stuUserMap['first_test_accuracy_rate']}% )</c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                        <td width="15%" align="right"><strong>掌握度：</strong></td>
                        <td width="6%"><c:if test="${stuUserMap['test_status']!=null}">${stuUserMap['mastery_rate']}%</c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                      </tr>
                      <tr>
                        <td align="right"><strong>试卷分值：</strong></td>
                        <td>${stuUserMap['total_score']}</td>
                        <td align="right"><strong>通过时间/成绩/正确率：</strong></td>
                        <td><c:if test="${stuUserMap['test_status']!=null}"><fmt:formatDate value="${stuUserMap['passed_time']}" pattern="yyyy-MM-dd HH:mm:ss"/>(&nbsp;${stuUserMap['passed_score']}分; ${stuUserMap['passed_accuracy_rate']}% )</c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                        <td align="right"><strong>错题数：</strong></td>
                        <td><c:if test="${stuUserMap['test_status']!=null}">${stuUserMap['sum_incorrect_items']}</c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                      </tr>
                      <tr>
                        <td align="right"><strong>标准时间：</strong></td>
                        <td>
                        <c:if test="${stuUserMap['test_status']!=null}">
                        <fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['answering_time']/(60*60)}" var="answeringTimeH" />
                      	<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['answering_time']%60}" var="answeringTimeM" />
                        <c:if test="${answeringTimeH!=0}">${answeringTimeH}小时</c:if><c:if test="${answeringTimeM!=0}">${answeringTimeM}分钟</c:if>	
                        </c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                        <td align="right"><strong>目前成绩/正确率：</strong></td>
                        <td><c:if test="${stuUserMap['test_status']!=null}">${stuUserMap['score']}分;&nbsp;${stuUserMap['accuracy_rate']}%</c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                        <td align="right"><strong>未答题数：</strong></td>
                        <td><c:if test="${stuUserMap['test_status']!=null}">${stuUserMap['sum_unfinished_items']}</c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                      </tr>
                      <tr>
                        <c:if test="${stuUserMap['test_status']==null}"> 
                        	<c:set value="${stuUserMap['right_rate_for_pass']}" var="right_rate_for_pass"/>
                        </c:if>
                        <c:if test="${stuUserMap['test_status']>=1&&stuUserMap['is_tested']==0}"> 
                        	<c:set value="${stuUserMap['right_rate_retraining']}" var="right_rate_for_pass"/>
                        </c:if>
                        <c:if test="${stuUserMap['is_tested']==1}"> 
                        	<c:set value="${stuUserMap['retraining_right_rate_test_faile']}" var="right_rate_for_pass"/>
                        </c:if>
                        <td align="right"><strong>正确率要求：</strong></td>
                        <td>${right_rate_for_pass}%</td>
                        <td align="right"><strong>本卷训练总用时：</strong></td>
                        <td>
                        <c:if test="${stuUserMap['test_status']!=null}">
                        	<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['time_used']/60}" var="timeUsedM" />
       						<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${stuUserMap['time_used']%60}" var="timeUsedS" />
                        	<c:if test="${timeUsedM!=0}">${timeUsedM}分钟</c:if>${timeUsedS}秒
                        </c:if><c:if test="${stuUserMap['test_status']==null}">--</c:if></td>
                        <td align="right"><strong>训练状态：</strong></td>
                        <td>
                        	<c:if test="${stuUserMap['node_status']==1}"><span class="cRed">未通过</span></c:if>
	                    	<c:if test="${stuUserMap['node_status']==2}"><font color="#009900">通过</font></c:if>
	                    	<c:if test="${stuUserMap['node_status']==0}"><font color="#009900">未作</font></c:if>
	                    	<c:if test="${stuUserMap['node_status']==3}"><font color="#009900">通过</font></c:if>
						</td>
                      </tr>
                    </table>                    
					</td>
                  </tr>
                   <c:if test="${stuUserMap['test_status']!=null}">
                  <tr>
                    <td width="373" valign="top">
                    <div class="bnt_xing"><span class="cRed">★★★★★</span>&nbsp;&nbsp;${stuUserMap['sum_five_star_items']} </div>
					<div class="bnt_xing"><span class="cRed">★★★★</span>&nbsp;&nbsp;${stuUserMap['sum_four_star_items']} </div>
            		<div class="bnt_xing"><span class="cRed">★★★</span>&nbsp;&nbsp;${stuUserMap['sum_three_star_items']} </div>
                    <c:if test="${(stuUserMap['sum_correct_items']+stuUserMap['sum_incorrect_items'])>0}"></c:if>           		
                    </td>
                    <td width="398" valign="middle"><img src="getImage.jhtml?_zero=${stuUserMap['sum_zero_star_items']}&_half=${stuUserMap['sum_half_star_items']}&_one=${stuUserMap['sum_one_star_items']}&_two=${stuUserMap['sum_two_star_items']}&_three=${stuUserMap['sum_three_star_items']}&_four=${stuUserMap['sum_four_star_items']}&_five=${stuUserMap['sum_five_star_items']}" /></td>
                  </tr>
                  </c:if>
                </table>
                <c:if test="${stuUserMap['test_status']!=null}">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" height="162">
                  <tr>
                    <td valign="top"></td>
                  </tr>
                  <tr>
                    <td height="162" valign="top"><p><b><font  color="#3366cc"><span class="f14px">历史训练报告</span></font></b>
                      （${stuUserMap['name']}）
                <table width="100%" cellpadding="3" cellspacing="0"  class="listab">
                          <tr class=" whitetr">
                            <td width="13%" align="center">历次训练</td>		                    
		                    <td width="10%" align="center">正确率</td>
		                    <td width="7%" align="center">掌握度</td>		                    
		                    <td width="7%" align="center">错题数</td>
		                    <td width="8%" align="center">未答题数</font></td>
		                    <td width="6%" align="center"><span class="cRed">★★★<br />
		                    ★★</span></td>
		                    <td width="8%" align="center"><span class="cRed">★★<br />
		                    ★★</span></td>
		                    <td width="7%" align="center"><span class="cRed">★★★</span></td>
		                    <td width="5%" align="center"><span class="cRed">★★</span></td>
		                    <td width="4%" align="center"><span class="cRed">★</span></td>
		                    <td width="3%" align="center"><span class="cRed">☆</span></td>
		                    <td width="5%" align="center"><img src="../images/smile.gif" width="20" height="20" />
                          </tr>
                       <c:forEach items="${historyRecordList}" var="recordMap" varStatus="itemStatus">
		                  <tr class=" whitetr">
		                    <td>第${historyRecordTotal}次</td>		                   
		                    <td align="center">${recordMap['accuracy_rate']}</td>
		                    <td align="center">${recordMap['mastery_rate']}</td>		                    
		                    <td align="center">${recordMap['sum_incorrect_items']}</td>
		                    <td align="center">${recordMap['sum_unfinished_items']}</td>
		                    <td align="center">${recordMap['sum_five_star_items']}</td>
		                    <td align="center">${recordMap['sum_four_star_items']}</td>
		                    <td align="center">${recordMap['sum_three_star_items']}</td>
		                    <td align="center">${recordMap['sum_two_star_items']}</td>
		                    <td align="center">${recordMap['sum_one_star_items']}</td>
		                    <td align="center">${recordMap['sum_half_star_items']}</td>
		                    <td align="center">${recordMap['sum_zero_star_items']}</td>
		                  </tr>
                      	  <c:set value="${historyRecordTotal-1}" var="historyRecordTotal"/>
	                  </c:forEach>
                      </table></td>
                  </tr>
                </table>
                 </c:if>
    		</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
