<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="sigleAndMutilChoose" value="J4C11,J4C12,J4M11,J4P11,J4P12,S4C11,S4C12,S4M11,S4P11,S4P12"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关注</title>
<link rel="stylesheet" href="../../mpc/css/style_blue.css" type="text/css" />
<link rel="stylesheet" href="../css/thinkbox.css" type="text/css" />
<script type="text/javascript" src="../js/m.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/showHideSwap.js"></script>
<script type="text/javascript" src="../js/showAdmin.jsp.js"></script>
<script type="text/javascript" src="../js/thickbox2.js"></script>
</head>
<body>
<div id="contentLayout" class="wm950">
	<!--Satr left-->
	<div class="content_left wm950">
		<div class="content_bg">
			<!--yuanjiao-->
			<div class="yr_bg2">
				<div class=ye_r_t>
					<div class=ye_l_t>
						<div class=ye_r_b>
						<div class=ye_l_b></div>
						<div class=ye_l_b>
							<jsp:include page="include_item.jsp"/>	
							<div class="clear"></div>						
							
							<div class="content_box920">
							<div class="content_titer920">
								<ul>
									<li>
										<img src="../../mpc/images/note_ico.gif" width="16" height="14" />&nbsp;&nbsp;
										<b>学习笔记</b>&nbsp;&nbsp;&nbsp;&nbsp;
										<span class=" cGray">在某个用户名前打钩，其笔记内容将被自动载入输入框。</span>
									</li>
								</ul>
							</div>
							<div class="answers920">
								<!--添加笔记层开始-->
								<div class="content_bj_floatBox2"  id="note">
									<form action="attentionAdmin!saveNoteSummary.jhtml?p.para.itemId=${item.id}" 
										method="post" id="addNoteSummary" _t=jform>
									<table>
										<tr>
											<td>
												<input type="hidden" id="devote_i" name="p.para.devote" value="${itemExtraInfo.devote}" />
												<textarea class="padding10px box1 cDGray" name="p.para.noteSummary" id="noteSummary"
											 	cols="60" rows="4">${itemExtraInfo.noteSummary}</textarea>
											</td>
											<td>&nbsp;&nbsp;
												<span class="sub_r">
												<input type="image" src="../../mpc/images/sub.gif" width="73" height="62" id="submit_i"/> 
												</span>
											</td>
										</tr>
										<tr>
											<td colspan="2">精华笔记贡献者：<span id="devoteSpan">${itemExtraInfo.devote}</span></td>
										</tr>
									</table>
									</form>
									<div class="clear"></div>
								</div>
							</div>
									 
							<IFRAME id="learnNote_iframe" src="attentionAdmin!showAdmin.jhtml?p.para.itemId=${item.id}&p.para.jsp=include_LearnNote" 
							id="item_iframe" border=0 name=ye_xy marginWidth=0 frameSpacing=0 marginHeight=0 frameBorder=0 noResize width="100%" 
							scrolling=auto height="100%" vspale="0"  ></IFRAME>
							</div>
						</div>	
						</div>
						</div>
					</div>
				</div>
			<!--yuanjiao-->
		</div>
	</div>
	<!--End left-->
	<div class="clear"></div>
</div>
<div id="footde">
	<span>Copyright © 2010</span>DFDL
	<a href="" target="_blank"></a> <span>版权所有</span>
</div>
</body>
</html>
