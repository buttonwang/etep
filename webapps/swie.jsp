<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script language="JavaScript" src="../ky/js/jquery-pack.js"></script>
<script language="JavaScript" src="../ky/js/jquery.xml2json.pack.js"></script>
<script language="javascript" type="text/javascript">
<!--

function getStr(xml,tag){

          var json = $.xml2json(xml);  
            	
          var strInfo="";
          if (json.active&&json.active.activeLeftNum>0) {
             strInfo+="外教免费批改文章数还剩" + json.active.activeLeftNum + "篇。";                               					                               				                               			
          } else {
              strInfo+="请外教批改你的文章？Sure！";
         }

          if (json.article&&tag) {
             	strInfo+="<br>";
	          if (json.article.latestPayArticle) {		                                   					
	            	 strInfo+="&middot;" + json.article.latestPayArticle.articleTitle + " 正被批改中<br>  "; 
	           }
	          if (json.article.latestCorrectedArticle) {
	            	strInfo+="&middot;"+json.article.latestCorrectedArticle.articleTitle + " 已完成批改  "; 
	             }
	      }
		parent.document.getElementById('swieinfo').innerHTML=strInfo;
		if(parent.document.getElementById('img01')!=null)
			parent.document.getElementById('img01').style.display="";	         
} 	
getStr('${xmlStr}',${tag});

//-->
</script>