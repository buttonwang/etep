<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/freshword_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${userDataVO.processCategoryName}_${userDataVO.processName}</title>
		<link href="../css/base.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="../js/common.js"></script>
</head>
<body>
<%@ include file="include/freshword_top.jsp"%>
  <!-- 页面主题 -->
<div id="main" class="wm2" >
  <div class="TabContent">
    <div id="myTab1_Content0">
        <div class="nTab">
          <!-- 标题开始 -->
          <div class="TabTitle">
            <ul id="myTab1">
              <li class="active">生词训练</li>
            </ul>
            </div>
          <!-- 内容开始 -->
          <div class="TabContent">
            <div id="myTab1_Content">
              <div class="style1">
                <table width="870" border="0" cellpadding="5" cellspacing="0">
                  <tr>
                    <td height="143" valign="top">
                      <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td class="comm"><img src="../images/info.gif" align="absbottom" />恭喜你，生词训练成功！通过本次训练你共掌握了${newpage.totalCount}个生词。</td>
                        </tr>
                      </table>
                      <br />
                    <table width="80%" align="center" cellpadding="3" cellspacing="0" class="listab" style="margin-top:0px;">
              <tr>
                            <td><table cellspacing="0" cellpadding="10" align="center" border="0">
                              <tbody>
                                <tr align="middle">
                                  <td><input onclick="javascript:this.disabled='true';window.location='../web/loadSessionVar.jhtml'" type="submit" value="  返回  " name="Sub" /></td>
                                </tr>
                              </tbody>
                            </table></td>
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
