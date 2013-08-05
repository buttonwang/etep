<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title><link href="../css/admin.css" rel="stylesheet" type="text/css"><script src="../js/m.js"></script>
</head>
<body>
<form action="../policy/unitTestNodePolicy.jhtml"  method="post">
<input type="hidden" name="atype" value="update">
<input type="hidden" name="id" value="${unitTestNodePolicy.nodeId}">
<input type="hidden" name="unitTestNodePolicy.asfNode.id" value="${unitTestNodePolicy.asfNode.id}">
<table id="table12" class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
          <tr height="45">
            <td align="left" valign="middle" bgcolor="#FFFFFF" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>使用策略模板&nbsp;&nbsp;
               <input type="button" value="选择模板" class="btn_2k3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" name="radio" id="radio"/>继承父节点策略&nbsp;&nbsp;<input type="button" value=" 继承 " class="btn_2k3"/>&nbsp;&nbsp;
            </td>
          </tr>
          <tr>
            <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">重复训练范围</td>
            <td align="left" bgcolor="#FFFFFF" colspan="3">
                <select name="unitTestNodePolicy.retrainingScope">
                    <option value="0" ${unitTestNodePolicy.retrainingScope==0?"selected":""}>本级</option>
                    <option value="1" ${unitTestNodePolicy.retrainingScope==1?"selected":""}>上级</option>
                    <option value="2" ${unitTestNodePolicy.retrainingScope==2?"selected":""}>上上级</option>
                </select>
            </td>
          </tr>
          <tr>
            <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="300" border="0">
				<tr>
					<td ><input type="submit" class="btn_2k3" value="  保 存  " />
						&nbsp;&nbsp;
						<input type="button" opt="back"  value="  取 消  " class="btn_2k3"/></td>
				</tr>
			</table></td>
          </tr>
      </table>
</form>
 
</body>

</html>
