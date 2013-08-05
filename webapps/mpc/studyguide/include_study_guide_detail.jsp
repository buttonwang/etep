<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <div class="con_right2 wm720 right">
  <div class="tager">
    <div class="tager_in">
      <h1 class="f14px"><strong>知识讲解：</strong></h1>
      <a name="sgtop${s.id}" id="sgtop${s.id}"></a>
      <div class="blank6"></div>
      ${s.content}
      <div class="blank6"></div>
      <div>
      	<ul>
      	<c:forEach items="${s.paragraphs}" var="pt" varStatus="titleStatus">
      		<li><a href="javascript:void(null)" onclick="hrefTitle(${pt.id})">${pt.title2}</a></li>
      	</c:forEach>
      	</ul>
      </div>
    </div>
  </div>
  
  <div class="content" style="padding-left:0px; padding-right:0px;">
  	<c:forEach items="${s.paragraphs}" var="p" varStatus="state">
    	<div class="conso">
		<img src="../images/fatcow_103.png" align="absmiddle"/>
		<a name="pt${p.id}"></a>${p.title2}&nbsp;&nbsp;&nbsp;<a href="#sgtop${s.id}">返回顶部</a>
		<p>${p.content}</p>
		
		<div class="content_box" style="width:686px;"  id="litidiv${p.id}">
        	<div class="con_right_content1 f14px">
        		<a href="javascript:void(null)" onclick="openShutManager(this,'liti${p.id}'); replacePImg(this);" class="cBlack">&nbsp;&nbsp;
        			<img src="../images/fatcow_003.png" align="absmiddle" />【例题】</a>
	   		</div>
	   		
	   		<div id="liti${p.id}" style="display:block;">
          	<div style="width:666px;">
          	<c:set var="f" value="0"></c:set>
	   		<c:forEach items="${p.items}" var="i" varStatus="state">
		  		<c:if test="${i.type eq 1}">
		  			<c:set var="f" value="${f+1}"></c:set>
            		<h3>
            			<a href="javascript:void(null)" onclick="openShutManager(this,'exam${i.id}'); replaceIImg(this);" class="cBlack">
            			<img src="../images/fatcow_151.gif"  align="middle"/><b>[例${f}]</b></a>${i.content}
            		</h3>
            		<h4 class="blank6"></h4>
					<div id="exam${i.id}" style="display:block;">
						<p>详解：${i.analys}</p>
						<p>答案：${i.answer}</p>
					</div>
					<div class="blank6"></div>
				</c:if>
			</c:forEach>
			</div>
			</div>
			<script type="text/javascript">
				if (${f}==0) {$("#litidiv${p.id}").hide();}
			</script>
		</div>
		<div class="blank6"></div>
		<div class="content_box" style="width:686px;" id="xitidiv${p.id}">
        	<div class="con_right_content1 f14px">
						<a href="javascript:void(null)" onclick="openShutManager(this,'xiti${p.id}'); replacePImg(this);" class="cBlack itemHref">&nbsp;&nbsp;
						<img src="../images/fatcow_003.png" align="absmiddle" />【习题】</a>
			</div>
		
			<div id="xiti${p.id}" style="display:block;">
          	<div style="width:666px;">
          	<c:set var="f" value="0"></c:set>
			<c:forEach items="${p.items}" var="i" varStatus="state">
		  		<c:if test="${i.type eq 2}">
		  			<c:set var="f" value="${f+1}"></c:set>
            		<h3>
            			<b>[习${f}]</b>&nbsp;&nbsp;&nbsp;&nbsp;
            			<span class="cDGray">${i.content}</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(null)" onclick="openShutManager(this,'qa1more${i.id}')">详解</a>
						<a href="javascript:void(null)" onclick="openShutManager(this,'qa1amswer${i.id}')">答案</a>
					</h3>
					<h4 class="blank6"></h4>
					<div id="qa1more${i.id}" style="display:none;" class="qa_iframe">
						<span class="cRed">详解：</span>${i.analys}
					</div>
					<div id="qa1amswer${i.id}" style="display:none;" class="qa_iframe">
						<span class="cRed">正确答案：</span>${i.answer}
					</div>
					<h4 class="blank6"></h4>
		  		</c:if>
			</c:forEach>
			</div>
			</div>
			<script type="text/javascript">
				if (${f}==0) {$("#xitidiv${p.id}").hide();}
			</script>
   	  </div>
      </div>
    </c:forEach>
  </div>
  <div class="blankW9"></div>
  </div>