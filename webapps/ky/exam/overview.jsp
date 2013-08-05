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
<SCRIPT LANGUAGE="JavaScript" src="../js/common.js"></script>
<SCRIPT language="javascript" src="../js/floating.js" type="text/javascript"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/exam.js"></script>
</head>
<body onload=FloatingDIV();>

<!--页面头部-->
<jsp:include page="include_head.jsp"></jsp:include>
<!--    end   -->
<div id=sign02 style='z-index:32;
        position:absolute;
        left:expression((body.clientWidth-500)/2);
		display:none;' align='center'> 
  <table width="500" height="200" border="0" cellspacing="0">
    <tr> 
      <td align="center" class="comm" style="line-height:40px;"> <p><img src="../images/icon_alert.gif" align="bottom" /> 
          <span style="font-size:18px;font-family:黑体;" >休息一会……本卷剩余时间<span id="leftTimeSpanA">${viewControl.actualTimeStr2}</psan></span><br />
          <font color="#CC3300">本卷共 <font color="#FF0000">${viewControl.examItemNum}</font>道题，目前 <font color="#FF0000"><span id="sign02undoSize">${viewControl.undoItemNum}</span> 
          </font>道题未做；<font color="#FF0000"><span id="sign02mark">${viewControl.markItemNum}</span> </font>道题标记疑问</font>
        <div style="background-color:#ffffff;border:1px solid #eeeeee;padding:10px;font-weight:normal;text-align:left;"> 
          <strong>应对考场怯场10法</strong><br />
          <br />
          要学会兴奋转移法。怯场现象一旦发生，应立即转移兴奋点和注意力。如抬头向窗外远望，观赏大自然的美景；或用自我暗示法令自己冷静不紧张；或闲目养神，甚至伏案休息片刻等，都是行之有效的可取方法。<br />
         </div>
        <font color="#CC3300"> <br />
        <input type="submit" name="Submit2" id="sign02_button_1"  value=" 继续训练 " onClick="javascript:cacelPause();checkit('sign01');checkit('sign02')" />
        <input type="submit" name="Submit23" value=" 暂存退出 " onClick="javascript:this.disabled='true';document.getElementById('sign02_button_1').disabled='true';pause();" />
        </font></td>
    </tr>
  </table>
</DIV>
		<div id=sign03  style='z-index:33;
        position:absolute;
        left:expression((body.clientWidth-500)/2);
		display:none;' align='center'> 
  <table width="500" height="200" border="0" cellspacing="0">
    <tr> 
      <td align="center" class="comm" style="line-height:40px;"> <p><img src="../images/icon_alert.gif" align="bottom" /> 
          <span style="font-size:18px;font-family:黑体;">你确定要交卷吗？本卷剩余时间<span id="leftTimeSpanB">${viewControl.actualTimeStr2}</span></span><br />
          <font color="#CC3300">本卷共 <font color="#FF0000">${viewControl.examItemNum}</font>道题，目前 <font color="#FF0000"><span id="sign03undoSize">${viewControl.undoItemNum}</span> 
          </font>道题未做；<font color="#FF0000"><span id="sign03mark"></span>${viewControl.markItemNum}</font>道题标记疑问</font> 
        <div style="background-color:#ffffff;border:1px solid #eeeeee;padding:10px;font-weight:normal;text-align:left;"> 
          <li>点击“继续训练”将直接转到未做题目。<br />
          <li> 点击“确定交卷”将提交本卷，你将得到答题的结果。<br />
          <li>交卷后你还可以重新做本卷中“未做的题”。<br />
          <li>所有错题需要你重新训练直到正确。<br />
         </div>
        <font color="#CC3300"> <br />
        <input type="submit" name="Submit222" value=" 确定交卷 " onClick="javascript:this.disabled='true';document.getElementById('sign03_button_2').disabled='true';overViewSubmit();" />
        <input type="submit" name="Submit2" id="sign03_button_2" value=" 继续训练 " onClick="javascript:cacelPause();checkit('sign01');checkit('sign03');" />
        </font></td>
    </tr>
  </table>
</DIV>
  		 <div id=sign04  style='z-index:34;
            position:absolute;
            left:expression((body.clientWidth-500)/2);
            display:none;' align='center'> 
      <table width="500" height="200" border="0" cellspacing="0">
        <tr> 
          <td align="center" class="comm" style="line-height:40px;"> <p><img src="../images/icon_alert.gif" align="bottom" /> 
              <span style="font-size:18px;font-family:黑体;">本卷规定训练用时（${viewControl.examTimeStr2}）已到，请交卷。</span><br />
              <font color="#CC3300">本卷共 <font color="#FF0000">${viewControl.examItemNum} </font>道题，目前 <font color="#FF0000"><span id="sign04undoSize"></span> 
              </font>道题未做；<font color="#FF0000"><span id="sign04mark"></span></font>道题标记疑问</font> 
            <div style="background-color:#ffffff;border:1px solid #eeeeee;padding:10px;font-weight:normal;text-align:left;"> 
              <li> 请点击“确定交卷”提交本卷，你将得到答题的结果。
               <li> 交卷后你还可以重新做本卷中“未做的题”。所有错题需要你重新训练直到正确。
               <li> 请特别注意答题时间。
              
            </div>
            <font color="#CC3300"> <br />
            <input type="submit" name="Submit222" value=" 确定交卷 " onClick="javascript:this.disabled='true';submitPaper();" />
            </font></td>
        </tr>
      </table>
    </DIV>
<DIV id="sign01" style='z-index:20;position:absolute; 
         top:0px;
         left:0px;
        WIDTH:expression(document.body.scrollWidth); 
        HEIGHT:expression(document.body.scrollHeight);
	background-color:#eeeeee;
	filter:alpha(opacity=70);
	-moz-opacity: 0.7;
	opacity: 0.7;display:none;
       '  align='center'> 
       </div>

<jsp:include page="include_overview.jsp"></jsp:include>

<jsp:include page="include_bottom.jsp"></jsp:include>
<form id="examForm" action="../exam/doExam.jhtml" method="post">
	<input type="hidden" value="submit" name="nextAct" id="nextAct" />
	
</form>
</body>
</html>
