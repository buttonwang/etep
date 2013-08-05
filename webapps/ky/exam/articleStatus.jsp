<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="JavaScript" src="../js/jquery-pack.js"></script>
<script language="JavaScript" src="../js/jquery.xml2json.pack.js"></script>
<script language="javascript" type="text/javascript">
<!--
//alert('${xmlStr}');

function getStr(xml){

          var json = $.xml2json(xml);               	
          var strInfo="";
          var i=0;
          if (json.error) {
          	if(json.error==3)
          		location.href="swie.jhtml?tag=0";
          		return;                  					                               				                               			
          }
          if(json.articleTitle)
          	strInfo+="此文章";
          if(json.status){
          	if(json.status==0)
          		strInfo+="尚未支付";
          	else if(json.status==1||json.status==2)
          		strInfo+="正被批改中";
          	else if(json.status==3)
          		strInfo+="已完成批改";
          }
           if(json.articleTitle)
          	strInfo+="（详见批改系统“"+json.articleTitle+"”）";
          if(strInfo=='')
          	strInfo+="写作批改系统繁忙，请稍后再尝试刷新此页面。";
         
		parent.document.getElementById('swieinfo').innerHTML=strInfo;
		if(parent.document.getElementById('img02')!=null)
			parent.document.getElementById('img02').style.display="";
					         
}
	   	
	   	
getStr('${xmlStr}');

//-->
</script>