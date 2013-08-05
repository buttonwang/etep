<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title><link href="../css/admin.css" rel="stylesheet" type="text/css">
<script src="../js/m.js"></script>
</head>

<body>
<table id="table11" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
            <td align="left" bgcolor="#FFFFFF" colspan="3">
			${unitTestNodePolicy.retrainingScope==0?"本级":""}
			${unitTestNodePolicy.retrainingScope==1?"上一级":""}
			${unitTestNodePolicy.retrainingScope==2?"上两级":""}
			</td>
          </tr><tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="400" border="0">
      <tr>
        <td><input type="button" opt=back value="  返 回  " class="btn_2k3"/></td>
      </tr>
    </table></td>
  </tr>
      </table>
 
</body>

</html>
