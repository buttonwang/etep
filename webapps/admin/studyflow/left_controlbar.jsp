<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %><html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body leftmargin="0" topmargin="0" class="">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td align="center" bgcolor="#F1F8FC"><a href="javascript:changeLeftWidth();"><img name="imgChangeLeftWidth" src="../images/arrow_left.gif" border=0 alt="伸缩"></a></td>
  </tr>
</table>
</body>
</html>

<script language=javascript> //written by wapen on 2001.07.23
<!--
//preload images:
if(document.images) 
{
	imgLeft = new Image(7,13); imgLeft.src = "../images/arrow_left.gif";
	imgRight = new Image(7,13); imgRight.src = "../images/arrow_right.gif";
}

function hiLite(imgName,imgObjName) 
{
	if(document.images) 
	{
  		document.images[imgName].src = eval(imgObjName + ".src");
  	}
}

//Chagne left frame's width
function changeLeftWidth()
{
	//alert();
	if(imgChangeLeftWidth.src.indexOf("left.gif") > 0)
	{
		//change the left frame's width
		//alert(parent.main.cols);
		parent.main.cols = "0,12,*";
		//change the img
		hiLite("imgChangeLeftWidth","imgRight");
		//imgChangeLeftWidth.src = "../images/arrow_right.gif";
		self.focus();
	}
	else
	{
		//change the left frame's width
		parent.main.cols = "190,12,*";
		//change the img
		hiLite("imgChangeLeftWidth","imgLeft");
		//imgChangeLeftWidth.src = "../images/arrow_left.gif";
		self.focus();
	}
}
-->
</script>