<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="ambow" prefix="ambow"%>  
<html>
<head>
<title>查看学习指导 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/admin.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/common.js"></script>
<script language="javascript" src="../js/admin.js" ></script>
<script type="text/javascript" src="../js/adminfck.js"></script>
<script type="text/javascript" src="../js/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
	function audio(){
		if(confirm('是否确认废弃该学习指导?')){
			window.location.href='${pageContext.request.contextPath}/admin/studyguide/itemStudyGuide!abandon.jhtml?studyGuide.id=${studyGuide.id}&subject=${subject}&grade=${grade}&code=${code}'
			alert('废弃操作成功！');
			};
	}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：学习指导 &gt; 查看学习指导  </td>
  </tr>
</table>
<table width="100%" border="0" align="center" >
	<tr>
		<td><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  bgcolor="#BEDAF1">
				<tr>
					<td width="50%" align="left"  class="txt12blueh">学习指导信息</td>
					<td width="50%" align="right" class="txt12blue" >
						<a href="itemStudyGuide!editParagraph.jhtml?studyGuide.id=${studyGuide.id }&paragraph.id=">增加段落</a>
	  					<a href="itemStudyGuide!modifyBefore.jhtml?studyGuide.id=${studyGuide.id}">修改学习指导</a>
  					</td>
				</tr>
			</table>
			<table id="show_table1" class="txt12555555line-height"  width="100%" border="0" align="center" 
					cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
				<tr>
				  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">编码：</td>
				    <td width="33%" align="left" bgcolor="#F7F7F7">${studyGuide.code}</td>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">名称：</td>
				    <td width="33%" align="left" bgcolor="#F7F7F7">${studyGuide.name}</td>
				</tr>
				<tr>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">导入文件：</td>
				    <td width="33%" align="left" bgcolor="#F7F7F7" class="txt12blue">
				    	<a href="/resource/studyguide/${studyGuide.importFile}" target="black">${studyGuide.importFile}</a>
				    </td>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状态：</td>
				    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
			    		<c:choose>
				    		<c:when test="${studyGuide.status == 0}">未审核</c:when>
				    		<c:when test="${studyGuide.status == 1}">已审核</c:when>
				    		<c:when test="${studyGuide.status == -1}">已废弃</c:when>
				    	</c:choose>
				    </td>
				</tr>
				<tr>
				  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">父节点：</td>
				    <td width="83%" align="left" bgcolor="#F7F7F7" colspan="3">
				    	${empty studyGuide.parent.name ? '无' : studyGuide.parent.name}
				    </td>
				</tr>
				<tr>
				  	<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">学科：</td>
				    <td width="33%" align="left" bgcolor="#F7F7F7">
				    	${studyGuide.subject.name }
				    </td>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">年级：</td>
					    <td width="33%" align="left" bgcolor="#F7F7F7">
					    	${studyGuide.grade.name }
				    </td>
				</tr>
				<tr>
			  		<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建人：</td>
			    	<td width="33%" align="left" bgcolor="#F7F7F7">${studyGuide.creater.username}</td>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">创建时间：</td>
				    <td width="33%" align="left" bgcolor="#F7F7F7">
				    	<fmt:formatDate value="${studyGuide.createdTime}" pattern="yyyy-MM-dd"/>
				    </td>
				</tr>
				<tr>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">修改人：</td>
				    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">${studyGuide.updater.username}</td>
				    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">修改时间：</td>
				    <td width="33%" align="left" valign="top" bgcolor="#F7F7F7">
				    	 <fmt:formatDate value="${studyGuide.updatedTime}" pattern="yyyy-MM-dd"/>
				    </td>
				</tr>
				<tr>
					<td width="17%" align="right" valign="top"  bgcolor="#F7F7F7"  class="txt12blue">学习指导内容：</td>
					<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${studyGuide.content}</td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
<c:if test="${not empty studyGuide.paragraphs}">
<table width="100%" border="0" align="center" >
	<c:forEach items="${studyGuide.paragraphs}" var="paragraph" varStatus="state">
			<tr>
				<td>
					<table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  class="tilte_bg">
					<tr>
						<td  width="50%" align="left" class="txt12blueh">段落信息 </td>
						<td align="right"  class="txt12blue">
							<a href="itemStudyGuide!editItem.jhtml?studyGuide.id=${studyGuide.id }&item.studyGuideParagraph.id=${paragraph.id }&item.id=">增加习题</a>
						  	<a href="itemStudyGuide!editParagraph.jhtml?studyGuide.id=${studyGuide.id }&paragraph.id=${paragraph.id }">修改段落</a>
						  	<a href="itemStudyGuide!delParagraph.jhtml?studyGuide.id=${studyGuide.id }&paragraph.id=${paragraph.id }">删除段落</a>
						</td>
					</tr>
					</table>
					<table id="show_subitem_${itemCount}" class="txt12555555line-height"  width="100%" border="0" align="center"
 			 			cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" >
 			 			<tr>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">段落标题：</td>		
			  				<td width="83%" align="left" bgcolor="#F7F7F7" colspan="3">${paragraph.title}</td>
			  			</tr>
 			 			<tr>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">段落内容：</td>		
			  				<td width="83%" align="left" bgcolor="#F7F7F7" colspan="3">${paragraph.content}</td>
			  			</tr>
						<tr>
							<td width="17%" align="right"  bgcolor="#F7F7F7"  class="txt12blue">顺序号：</td>
							<td width="83%" align="left"  bgcolor="#FFFFFF" colspan="3">${paragraph.orderNum}</td>
						</tr>
					</table>
		</td></tr>
		<c:forEach items="${paragraph.items}" var="item" varStatus="state">
		<tr>
				<td><table width="100%" border="0" align="center" cellpadding="6" cellspacing="0"  class="tilte_bg">
						<tr>
							<td  width="50%" align="left" class="txt12blueh">试题信息 </td>
							<td align="right"  class="txt12blue">
								<a href="itemStudyGuide!editItem.jhtml?studyGuide.id=${studyGuide.id }&item.id=${item.id }">修改习题</a>
							  	<a href="itemStudyGuide!delItem.jhtml?studyGuide.id=${studyGuide.id }&item.id=${item.id }">删除习题</a>
							</td>
						</tr>
					</table>
					<table class="txt12555555line-height"  width="100%" border="0" align="center"
 			 			cellpadding="6" cellspacing="1" bgcolor="#CCCCCC" >
			 			<tr>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">题干：</td>		
			  				<td width="83%" align="left" bgcolor="#F7F7F7" colspan="3">${item.content }</td>
			  			</tr>
			  			<tr>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">解析：</td>		
			  				<td width="83%" align="left" bgcolor="#F7F7F7" colspan="3">${item.analys }</td>
			  			</tr>
			  			<tr>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">答案：</td>		
			  				<td width="83%" align="left" bgcolor="#F7F7F7" colspan="3">${item.answer }</td>
			  			</tr>
			  			<tr>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">类型：</td>		
			  				<td width="33%" align="left" bgcolor="#F7F7F7">${item.type eq 1 ? '例题' : '习题'}</td>
			  				<td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">顺序号：</td>		
			  				<td width="33%" align="left" bgcolor="#F7F7F7">${item.orderNum}</td>
			  			</tr>
					</table>
		</td></tr>
	</c:forEach>
	</c:forEach>
	</table>
</c:if>
<table class="txt12555555line-height" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
  <tr> 
    <td align="center">
    	<input type="button" value="  审 核  " class="btn_2k3" <c:if test="${studyGuide==null}">disabled</c:if>
    	 onclick="javascript: window.location.href='itemStudyGuide!verify.jhtml?studyGuide.id=${studyGuide.id}';alert('审核已通过！');"/>
    	 &nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="button" value="  废 弃  " class="btn_2k3" <c:if test="${studyGuide==null}">disabled</c:if> 
    	 onclick="audio()"/>&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="button" value="  修 改  " class="btn_2k3" <c:if test="${studyGuide==null}">disabled</c:if> 
    	 onclick="javascript: window.location.href='itemStudyGuide!modifyBefore.jhtml?studyGuide.id=${studyGuide.id}';"/>
    	 &nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
</table>
</body>
</html>