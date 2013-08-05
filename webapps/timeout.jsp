<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网页超时</title>
<script>
if (window != top)  
	top.location.href = location.href; 
  var iCount=4;   
  function init(){
    document.getElementById("time").innerHTML=iCount;
	iCount--;
    if(iCount==-1){
    	window.opener='meizz';
    	window.close();
   	}   
  }  
   
  </script>
</head>
<body onload="setInterval('init()',1000); ">

	    <div style="width:900px;margin:15px 15px 15px 15px;background:#FFF;padding:5px;overflow:hidden;">
      <table width="90%" border="0" align="center">
        <tr style="font-size:14pt;font-weight:bold;color:#6293BB;">
          <td align="center"></td>
          <td height="68"  align="left" >
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h1>网页超时</h1>
          </td>
        </tr>

        <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
          <td width="22%" align="right"><img src="ky/images/filenotfound.jpg" width="128" height="128" /></td>
          <td width="71%">
            <div style="padding:10px 10px">
              <p>网页已超时，请重新登录学习平台，窗口<span id="time">5</span>秒后关闭！</p>
            </div>
          </td>
        </tr>

        <tr style="font-size:12pt;font-weight:normal;color:#6293BB;">
          <td width="22%" align="right"></td>
          <td width="71%" align=center>
            <div style="padding:10px 10px">
              <p>评测训练引擎开发组</p>
            </div>
          </td>
        </tr>
      </table>
    </div>
</body>
</html>