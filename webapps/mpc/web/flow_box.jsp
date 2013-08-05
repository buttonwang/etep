<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<link href="../css/index.css" rel="stylesheet" type="text/css" />
<STYLE type="text/css">
.btn_container { width: 40px; height: 65px; display: table;margin:11px 4px;}
.btn_pos { display: table-cell; vertical-align: middle;}
.btn_content{ text-align:center;}
.btn_content a{display:block;width:14px; padding:10px 13px;margin:auto;}
</STYLE>
	<!--[if IE]>
		<STYLE type="text/css">

		.btn_container { position: relative; }
		.btn_pos { position: absolute; top: 50%; }
		.btn_content { position: relative; top: -50%;text-align:center;}

		</STYLE>
	<![endif]-->
<script type="text/javascript">
<!--
i=40;
var scrollObj;
function scroll(objid, a){
	scrollObj = document.getElementById(objid);
	if(i){
		scrollObj.scrollLeft+=5*a;i--;
		setTimeout("scroll('"+objid+"', "+a+")",30);
	}
	else 
		i=40;
}
//-->
</script>
                 <div class="flow_box">
               	  <div class="flow_box_tit">
               	  <h1><b>${secondLevevNode.nodeGroup.name}</b></h1>
						<h2>数字代表训练</h2>
               	  </div>
                    <div class="flow_box_con">
                      <div class="btn_flow_l1" onmouseout="this.className='btn_flow_l1';" onmouseover="this.className='btn_flow_l2';"><a href="#" id="l" onclick="scroll('a2', -1)"><span>向前</span></a></div>
                        <div class="flow_box_con_c">
                        <div id="a2" class="outerDiv">
                        <div class="flow_boxs" style="width:${userDataVO.nodeListDivWidth}px;">
                        <ul>
                        
                        <c:forEach items="${userDataVO.nodeInstanceInfoList}" var="item" varStatus="itemStatus">
			           		<li class="${item.cssClassName}">
	                            <div class="btn_pos">
	                            <div class="btn_content">
		                        <c:if test="${item.tag==1}">
									<c:if test="${item.nodeStatus==3}">
		                       		<a href="../report/reportMain.jhtml?nodeInstanceId=${item.nodeInstanceId}&orderNum=${item.orderNum}&nodeType=${item.nodeType}&showListType=3" class="${item.cssClassName2}" target="_parent"  title="${item.titleInfo}"  style="cursor:hand">
				           			${item.nodeName}
				           			</a>
									</c:if>
									<c:if test="${item.nodeStatus!=3}">
										<c:if test="${item.nodeType=='EVALUATE'}">
										<a href="../report/reportMain.jhtml?nodeInstanceId=${item.nodeInstanceId}&orderNum=${item.orderNum}&nodeType=${item.nodeType}&showListType=2" class="${item.cssClassName2}" target="_parent"  title="${item.titleInfo}"  style="cursor:hand">
										${item.nodeName}
										</a>
										</c:if>
										<c:if test="${item.nodeType!='EVALUATE'}">
										<a href="../report/reportMain.jhtml?nodeInstanceId=${item.nodeInstanceId}&orderNum=${item.orderNum}&nodeType=${item.nodeType}&showListType=1" class="${item.cssClassName2}" target="_parent"  title="${item.titleInfo}"  style="cursor:hand">
										${item.nodeName}
										</a>
										</c:if>
									</c:if>
				           		</c:if>
								<c:if test="${item.tag==0}">
			           		 		<a href="#" class="${item.cssClassName2}" style="cursor:default"  title="${item.titleInfo}">${item.nodeName}</a>
			           		 	</c:if>
	                            </div></div>
	                        </li>
		               </c:forEach>
                        </ul>
                        </div></div></div>
                      <div class="btn_flow_r1" onmouseout="this.className='btn_flow_r1';" onmouseover="this.className='btn_flow_r2';"><a href="#" id="r" onclick="scroll('a2', 1)"><span>向后</span></a></div>
                    </div>
                    <div class="flow_box_bon">
                    	<ul>
                        <li class="flow_box_bon_l">
                        	 <c:if test="${upNodeId>0}">
		                   			<a class="cBlack" href="flowBox.jhtml?currentNodeId=${upNodeId}"><img src="../images/left.jpg"/>上一章</a>
		                    </c:if>
		                </li>
                        <li class="flow_box_bon_c">当前章：${secondLevevNode.name}</li>
                        <li class="flow_box_bon_r">
                       		 <c:if test="${downNodeId>0}">
		                   			<a class="cBlack" href="flowBox.jhtml?currentNodeId=${downNodeId}">下一章<img src="../images/right.jpg"/></a>
		                    </c:if>
                        </li>
                        </ul>
                  </div>
                </div>