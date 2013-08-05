<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<title>爆破数理化</title>
</head>
<body>
<%
	long[][] arr = (long[][])request.getAttribute("arr");
	String[][] strArr = (String[][])request.getAttribute("strArr");
	long[] statArr = (long[])request.getAttribute("statArr");
	long[] stat = (long[])request.getAttribute("stat");
	int[] diff = (int[])request.getAttribute("diff");
	String point = (String)request.getAttribute("point");
	String subject = (String)request.getAttribute("subject");
	String grade = (String)request.getAttribute("grade");
%>
<table width="100%" border="0"  cellspacing="0"  bgcolor="#E9F0F6">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#E3E3E3">
      <!-- 显示记录列表 -->
      <tr align="center"  bgcolor="#F7F7F7" class="txt12blue">
        <td width="80" rowspan="3"><img src="<%=request.getContextPath()%>/admin/images/banner1.gif" width="100" height="60" /></td>
        <td colspan="3" rowspan="2" bgcolor="#F7F7F7">选择题</td>
        <td colspan="12">非选择题</td>
        <td rowspan="3"><strong>合计</strong></td>
      </tr>
      <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
        <td colspan="3" bgcolor="#F7F7F7">填空题</td>
        <td colspan="3" bgcolor="#F7F7F7">计算题</td>
        <td colspan="3" bgcolor="#F7F7F7">实验题</td>
        <td colspan="3" bgcolor="#F7F7F7">解答题</td>
      </tr>
      <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
        <td bgcolor="#F7F7F7">单选<br />
          (11)</td>
        <td bgcolor="#F7F7F7">多选<br />
          (12)</td>
        <td bgcolor="#F7F7F7">小计</td>
        <td bgcolor="#F7F7F7">填空<br />
          (33)</td>
        <td bgcolor="#F7F7F7">填空<br />
          (43)</td>
        <td bgcolor="#F7F7F7">小计</td>
        <td bgcolor="#F7F7F7">计算<br />
          (35)</td>
        <td bgcolor="#F7F7F7">计算<br />
          (45)</td>
        <td bgcolor="#F7F7F7">小计</td>
        <td bgcolor="#F7F7F7">实验<br />
          (36)</td>
        <td bgcolor="#F7F7F7">实验<br />
          (46)</td>
        <td bgcolor="#F7F7F7">小计</td>
        <td bgcolor="#F7F7F7">解答<br />
          (38)</td>
        <td bgcolor="#F7F7F7">解答<br />
          (48)</td>
        <td bgcolor="#F7F7F7">小计</td>
      </tr>
	  <%
	  	for(int i=0;i<diff.length;i++){
	  %>
      <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
        <td><%if(i==0){out.print("0");}else{out.print(diff[i-1]+1);}%>~<%=diff[i]%></td>
		<%
			for(int j=0;j<16;j++){	
		%>
        <td><%if(arr[i][j]!=0 && strArr[i][j] !=null){ %><a href="item!detail.jhtml?subject_code=<%=subject%>&grade_code=<%=grade%>&difficulty=<%=diff[i]%>&type_code=<%=strArr[i][j]%>&knowledgePoint=<%=point%>" target="_blank"><%}%><%=(arr[i][j]==0?"-":arr[i][j])%><%if(arr[i][j]!=0&&strArr[i][j]!=null){%></a><%}%></td>
		<%
			}
		%>
      </tr>
	  <%
	  	}
	  %>
      <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
        <td><strong>合计</strong></td>
        <td><%=statArr[0]%></td>
        <td><%=statArr[1]%></td>
        <td><strong><%=statArr[2]%></strong></td>
        <td><%=statArr[3]%></td>
        <td><%=statArr[4]%></td>
        <td><strong><%=statArr[5]%></strong></td>
        <td><%=statArr[6]%></td>
        <td><%=statArr[7]%></td>
        <td><strong><%=statArr[8]%></strong></td>
        <td><%=statArr[9]%></td>
        <td><%=statArr[10]%></td>
        <td><strong><%=statArr[11]%></strong></td>
        <td><%=statArr[12]%></td>
        <td><%=statArr[13]%></td>
        <td><strong><%=statArr[14]%></strong></td>
        <td><strong><%=statArr[15]%></strong></td>
      </tr>
      <tr align="center"  bgcolor="#FFFFFF" class="txt12blue">
        <td><strong>题型合计</strong></td>
        <td colspan="3"><strong>选择题 <%=stat[0]%> </strong></td>
        <td colspan="12"><strong>非选择题 <%=stat[1]%> </strong></td>
        <td><strong>共<%=stat[2]%></strong></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
