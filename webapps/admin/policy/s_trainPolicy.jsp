<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*;" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%if(request.getAttribute("trainingPolicy")!=null){
		request.setAttribute("tp",request.getAttribute("trainingPolicy"));

%>
<div h=1> 
<table id="table13" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">全卷预览时间：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${tp.overviewTime}</td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">允许设置疑问标记：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${tp.allowUnsureMark}            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看解析：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${tp.whenToSeeAnalysis}            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">何时允许查看答案：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${tp.whenToCheckAnswer}            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否随机出题：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${tp.isRandom}            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">是否颠倒答案顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${tp.isRandomAnswerOptions}            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">通过正确率：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${tp.rightRateForPass*100}/100            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做通过正确率：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${tp.rightRateRetraining*100}/100</td>
        </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题类型：</td>
            <td width="33%" align="left" valign="top" bgcolor="#FFFFFF">${tp.retrainingItemType}            </td>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重做出题顺序：</td>
            <td width="33%" align="left" bgcolor="#FFFFFF">${tp.retrainingItemOrder}            </td>
          </tr>
      </table> 

</div>
<%} %> 